/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.Interpolation;
import Reika.DragonAPI.Instantiable.Data.Immutable.DecimalPosition;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Instantiable.Effects.LightningBolt;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EMPSparkRenderer {

	public static final EMPSparkRenderer instance = new EMPSparkRenderer();

	private static final int SPARK_COUNT = 4;

	private static final Random rand = new Random();

	private ArrayList<EMPSpark> sparks = new ArrayList();
	private final HashSet<WorldLocation> locations = new HashSet();

	private final ArrayList<WorldLocation> selectableLocations = new ArrayList();

	private EMPSparkRenderer() {

	}

	public void addSparkingLocation(WorldLocation loc) {
		if (!selectableLocations.contains(loc))
			selectableLocations.add(loc);
	}

	public void removeSparkingLocation(WorldLocation loc) {
		selectableLocations.remove(loc);
	}

	public void tick() {
		Iterator<EMPSpark> it = sparks.iterator();
		while (it.hasNext()) {
			EMPSpark sp = it.next();
			if (sp.tick()) {
				it.remove();
				locations.remove(sp.location);
			}
		}
		if (!selectableLocations.isEmpty() && sparks.size() < SPARK_COUNT && rand.nextInt(4) == 0) {
			WorldLocation loc = selectableLocations.get(rand.nextInt(selectableLocations.size()));
			if (loc != null) {
				this.doAddLocation(loc);
			}
		}
	}
	/*
	public void addLocation(TileEntity te) {
		if (sparks.size() < SPARK_COUNT) {
			WorldLocation loc = new WorldLocation(te);
			this.doAddLocation(loc);
		}
	}*/

	private void doAddLocation(WorldLocation loc) {
		if (loc != null && !locations.contains(loc)) {
			sparks.add(new EMPSpark(loc));
			locations.add(loc);
		}
	}

	public void render(Tessellator v5, float ptick) {
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		ReikaRenderHelper.prepareGeoDraw(true);
		ReikaRenderHelper.disableEntityLighting();
		GL11.glPushMatrix();
		GL11.glTranslated(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
		for (EMPSpark sp : sparks) {
			if (sp.location.dimensionID == Minecraft.getMinecraft().theWorld.provider.dimensionId) {
				sp.render(v5, ptick);
			}
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	private static class EMPSpark {

		private final WorldLocation location;

		private final int LIFESPAN;
		private final Collection<LightningBolt> bolts = new ArrayList();
		private final Interpolation brightnessCurve = new Interpolation(false);

		private int age = 0;

		private EMPSpark(WorldLocation loc) {
			location = loc;

			int n = 1+rand.nextInt(4);
			for (int i = 0; i < n; i++) {
				bolts.add(this.createBolt());
			}

			LIFESPAN = 8+rand.nextInt(40);

			int a = 0;
			double f = 0.5+0.5*rand.nextDouble();
			brightnessCurve.addPoint(a, f);
			while (a < LIFESPAN) {
				a += Math.min(LIFESPAN-a, ReikaRandomHelper.getRandomBetween(4, 10));
				f = rand.nextDouble();
				brightnessCurve.addPoint(a, f);
			}
		}

		private LightningBolt createBolt() {
			double o = 0.0625;
			double r = 0.5+o;
			double x1 = -o+rand.nextDouble()*(1+o*2);
			double y1 = -o+rand.nextDouble()*(1+o*2);
			double z1 = -o+rand.nextDouble()*(1+o*2);
			double x2 = -o+rand.nextDouble()*(1+o*2);
			double y2 = -o+rand.nextDouble()*(1+o*2);
			double z2 = -o+rand.nextDouble()*(1+o*2);
			switch(ForgeDirection.VALID_DIRECTIONS[rand.nextInt(6)]) {
				case DOWN:
					y1 = y2 = -o;
					break;
				case UP:
					y1 = y2 = o;
					break;
				case EAST:
					x1 = x2 = o;
					break;
				case WEST:
					x1 = x2 = -o;
					break;
				case NORTH:
					z1 = z2 = -o;
					break;
				case SOUTH:
					z1 = z2 = o;
					break;
				default:
					break;
			}

			x1 += location.xCoord;
			x2 += location.xCoord;
			y1 += location.yCoord;
			y2 += location.yCoord;
			z1 += location.zCoord;
			z2 += location.zCoord;

			LightningBolt bolt = new LightningBolt(x1, y1, z1, x2, y2, z2, 6);
			bolt.scaleVelocity(1/64D);
			bolt.scaleVariance(1/12D);

			return bolt;
		}

		public void render(Tessellator v5, float ptick) {
			double f = brightnessCurve.getValue(age);
			for (LightningBolt bolt : bolts) {
				float w = GL11.glGetFloat(GL11.GL_LINE_WIDTH);
				GL11.glLineWidth(4);
				this.drawBolt(bolt, v5, 0x7000ffff, f);
				GL11.glLineWidth(w);
				this.drawBolt(bolt, v5, 0xffffffff, f);
				bolt.update();
			}
		}

		private void drawBolt(LightningBolt bolt, Tessellator v5, int color, double brightness) {
			v5.startDrawing(GL11.GL_LINE_STRIP);
			v5.setColorRGBA_I(color & 0xffffff, (int)(brightness*ReikaColorAPI.getAlpha(color)));
			for (int i = 0; i < bolt.nsteps; i++) {
				DecimalPosition p = bolt.getPosition(i);
				v5.addVertex(p.xCoord, p.yCoord, p.zCoord);
			}
			v5.draw();
		}

		private boolean tick() {
			age++;
			return age >= LIFESPAN;
		}

	}

}

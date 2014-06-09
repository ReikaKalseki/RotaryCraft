/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import java.awt.Color;
import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelCave;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;

public class RenderCaveFinder extends RotaryTERenderer {

	private static final double[][] depthColors = new double[256][3];
	private static final HashMap<TileEntityCaveFinder, Integer> lists = new HashMap();

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "cavetex.png";
	}

	private ModelCave caveModel = new ModelCave();

	public void renderTileEntityCaveFinderAt(TileEntityCaveFinder tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelCave var14;
		var14 = caveModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/cavetex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll(tile, null, 0, 0);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityCaveFinderAt((TileEntityCaveFinder)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
		this.renderPoints((TileEntityCaveFinder)tile, par2, par4, par6);
	}

	public void rebuildList(TileEntityCaveFinder te, double p2, double p4, double p6) {
		int id = GL11.glGenLists(1);
		GL11.glNewList(id, GL11.GL_COMPILE_AND_EXECUTE);
		this.drawPoints(te);
		GL11.glEndList();
		lists.put(te, id);
	}

	public void renderPoints(TileEntityCaveFinder te, double p2, double p4, double p6) {
		if (te == null)
			return;
		if (!te.isInWorld())
			return;
		if (!te.on)
			return;
		GL11.glTranslated(p2-te.xCoord, p4-te.yCoord, p6-te.zCoord);

		if (lists.containsKey(te)) {
			GL11.glCallList(lists.get(te));
		}
		else {
			this.rebuildList(te, p2, p4, p6);
		}
		GL11.glTranslated(-p2+te.xCoord, -p4+te.yCoord, -p6+te.zCoord);
	}

	private void drawPoints(TileEntityCaveFinder te) {
		ReikaRenderHelper.disableLighting();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glPointSize(2F);
		int cx = te.getSourceX();
		int cy = te.getSourceY();
		int cz = te.getSourceZ();
		int dx = te.getSourceX()-te.xCoord;
		int dy = te.getSourceY()-te.yCoord;
		int dz = te.getSourceZ()-te.zCoord;
		GL11.glBegin(GL11.GL_POINTS);
		int range = te.getRange();
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				for (int k = -range; k <= range; k++) {
					if (te.hasPointAt(i+range, j+range, k+range)) {
						int y = te.yCoord+j+dy;
						if (y >= 0 && y < 256) {
							double[] color = this.getColorForDepth(te.yCoord+j+dy);
							GL11.glColor3d(color[0], color[1], color[2]);
							GL11.glVertex3d(te.xCoord+i+dx, te.yCoord+j+dy, te.zCoord+k+dz);
						}
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glColor3d(1, 1, 1);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(cx-range, cy-range, cz-range);
		GL11.glVertex3d(cx+1+range, cy-range, cz-range);
		GL11.glVertex3d(cx+1+range, cy-range, cz+1+range);
		GL11.glVertex3d(cx-range, cy-range, cz+1+range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(cx-range, cy+1+range, cz-range);
		GL11.glVertex3d(cx+1+range, cy+1+range, cz-range);
		GL11.glVertex3d(cx+1+range, cy+1+range, cz+1+range);
		GL11.glVertex3d(cx-range, cy+1+range, cz+1+range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(cx-range, cy-range, cz-range);
		GL11.glVertex3d(cx-range, cy+1+range, cz-range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(cx+1+range, cy-range, cz-range);
		GL11.glVertex3d(cx+1+range, cy+1+range, cz-range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(cx+1+range, cy-range, cz+1+range);
		GL11.glVertex3d(cx+1+range, cy+1+range, cz+1+range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(cx-range, cy-range, cz+1+range);
		GL11.glVertex3d(cx-range, cy+1+range, cz+1+range);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		ReikaRenderHelper.enableLighting();
	}

	private double[] getColorForDepth(int y) {
		if (depthColors[y][0] != 0 || depthColors[y][1] != 0 || depthColors[y][2] != 0)
			return depthColors[y];
		else {
			double[] color = new double[3];
			int sc = 32;
			int vsc = 64;
			int hexcolor;
			hexcolor = Color.HSBtoRGB(((((Math.abs(y)-12)%vsc)))/(float)vsc, 1, 1);
			color[0] = ReikaColorAPI.HextoColorMultiplier(hexcolor, 0);
			color[1] = ReikaColorAPI.HextoColorMultiplier(hexcolor, 1);
			color[2] = ReikaColorAPI.HextoColorMultiplier(hexcolor, 2);
			depthColors[y] = color;
			return color;
		}
	}

	public void removeListFor(TileEntityCaveFinder te) {
		lists.remove(te);
	}

}

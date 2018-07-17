/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import java.util.List;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityWindEngine extends TileEntityEngine {

	private WindClearanceCheck clearance;

	private void dealBladeDamage(World world, int x, int y, int z, int meta) {
		int c = 0; int d = 0;
		int a = 0; int b = 0;
		if (meta < 2)
			b = 1;
		else
			a = 1;
		switch (meta) {
			case 0:
				c = 1;
				break;
			case 1:
				c = -1;
				break;
			case 2:
				d = 1;
				break;
			case 3:
				d = -1;
				break;
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x+c, y, z+d, x+1+c, y+1, z+1+d).expand(a, 1, b);
		List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase ent : in) {
			ent.attackEntityFrom(DamageSource.generic, 1);
		}
	}

	@Override
	protected void consumeFuel() {

	}

	@Override
	protected void internalizeFuel() {

	}

	private float getWindFactor(World world, int x, int y, int z, int meta) {
		/*
		if (world.provider.terrainType == WorldType.FLAT) {
			if (y < 4)
				return 0;
			float f = (y-4)/16F;
			if (InterfaceCache.IGALACTICWORLD.instanceOf(world.provider)) {
				IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
				f *= ig.getWindLevel();
			}
			if (f > 1)
				f = 1;
			return f;
		}
		else {
			if (y < 64)
				return 0;
			float f = (y-64)/16F;
			if (InterfaceCache.IGALACTICWORLD.instanceOf(world.provider)) {
				IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
				f *= ig.getWindLevel();
			}
			if (f > 1)
				f = 1;
			return f;
		}
		 */

		if (clearance == null)
			clearance = new WindClearanceCheck(this, 32, 3);
		clearance.tick(world);

		float f = 1F-clearance.getPenalty();
		if (InterfaceCache.IGALACTICWORLD.instanceOf(world.provider)) {
			IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
			f *= ig.getWindLevel();
		}
		return Math.min(1, f);
	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		int c = 0; int d = 0;
		int a = 0; int b = 0;
		if (meta < 2)
			b = 1;
		else
			a = 1;
		switch (meta) {
			case 0:
				c = 1;
				break;
			case 1:
				c = -1;
				break;
			case 2:
				d = 1;
				break;
			case 3:
				d = -1;
				break;
		}
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (!ReikaWorldHelper.softBlocks(world, x+a*i+c, y+j, z+b*i+d)) {
					omega = 0;
					return false;
				}
			}
		}/*
		for (int i = 1; i < 16; i++) {
			Block id = world.getBlock(x+c*i, y, z+d*i);
			if (id != Blocks.air) {
				if (id.getCollisionBoundingBoxFromPool(world, x+c*i, y, z+d*i) != null)
					return false;
			}
		}*/
		return true;
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier, float volume) {
		soundtick++;
		if (this.isMuffled(world, x, y, z)) {
			volume *= 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		SoundRegistry.WIND.playSoundAtBlock(world, x, y, z, 1.1F*volume, 1F*pitchMultiplier);
	}

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return (int)(EngineType.WIND.getSpeed()*this.getWindFactor(world, x, y, z, meta));
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		this.dealBladeDamage(world, x, y, z, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord).expand(1, 1, 1);
	}

	@Override
	public void onRotate() {
		clearance = null;
	}

	private static class WindClearanceCheck {

		private final Coordinate engine;
		private final ForgeDirection direction;

		private final int stepRatio;
		private final int searchDistance;

		private int step = 1;

		//private boolean[][] blockLocations;
		//private boolean[][] lastBlockLocations;

		private final double[] blockFraction;

		private boolean dirty;

		private float penalty;

		private WindClearanceCheck(TileEntityWindEngine te, int search, int step) {
			engine = new Coordinate(te);
			direction = te.getWriteDirection().getOpposite();

			searchDistance = search;
			stepRatio = step;

			/*
			blockLocations = new boolean[search][];
			lastBlockLocations = new boolean[search][];
			int r = 1+((step-1)/stepRatio);
			for (int i = 0; i < searchDistance; i++) {
				blockLocations[i] = new boolean[r*2+1];
				lastBlockLocations[i] = new boolean[r*2+1];
			}*/

			blockFraction = new double[searchDistance];

			for (int i = 1; i <= searchDistance; i++) {
				this.scanRow(te.worldObj, i);
			}
		}

		private void tick(World world) {
			this.scanRow(world, step);

			step++;
			if (step > searchDistance)
				step = 1;
		}

		private void scanRow(World world, int step) {
			int r = 1+((step-1)/stepRatio);
			ForgeDirection dir = ReikaDirectionHelper.getRightBy90(direction);

			//ReikaJavaLibrary.pConsole("Scanning row "+step+", center coord is "+engine.offset(direction, step));

			int blocked = 0;
			for (int i = -r; i <= r; i++) {
				//lastBlockLocations[step-1][i+r] = blockLocations[step-1][i+r];

				int dx = engine.xCoord+direction.offsetX*step+dir.offsetX*i;
				int dy = engine.yCoord;
				int dz = engine.zCoord+direction.offsetZ*step+dir.offsetZ*i;
				//blockLocations[step-1][i+r] = !ReikaWorldHelper.softBlocks(world, dx, dy, dz);

				if (!ReikaWorldHelper.softBlocks(world, dx, dy, dz)) {
					blocked++;
				}
			}

			double frac = blocked/(r*2D+1);
			//ReikaJavaLibrary.pConsole("Row "+step+" is "+(frac*100)+" % blocked.");
			if (blockFraction[step-1] != frac) {
				blockFraction[step-1] = frac;
				dirty = true;
			}
		}

		private void calculatePenalty() {
			penalty = 0;
			double max = 0;
			for (int i = 0; i < blockFraction.length; i++) {
				//double rowValue = 1+1D/searchDistance-((i+1)/(double)searchDistance);
				double pow = 1.2D-(i/(double)searchDistance);
				double rowValue = Math.pow(pow, 12);
				penalty += rowValue*blockFraction[i];
				max += rowValue;

				//penalty = (float)Math.max(penalty, blockFraction[i]);
			}
			penalty /= Math.sqrt(max);
			penalty = (float)Math.sqrt(penalty);
			//ReikaJavaLibrary.pConsole(penalty);
			//ReikaJavaLibrary.pConsole(penalty+" of "+max);
		}

		public float getPenalty() {
			if (dirty) {
				this.calculatePenalty();
				dirty = false;
			}
			return penalty;
		}

	}
}

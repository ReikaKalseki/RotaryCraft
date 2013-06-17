/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityGPR extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	/** A depth-by-width array of the discovered block IDs, materials, colors
	 * drawn downwards (first slots are top layer) */
	public int[][] ids = new int[256][81]; //from 0-16 -> centred on 8 (8 above and below)
	public Material[][] mats = new Material[256][81];
	public int[][] colors = new int[256][81]; //these three arrays take 52KB RAM collectively (assuming Mat'l is 32bits)
	public boolean xdir;


	private int oldmeta = 0;

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}

		if (yCoord > 96)
			return false;
		/*
        if (this.power < MINPOWER)
        	return false;*/

		return super.isUseableByPlayer(par1EntityPlayer);
	}

	public double getSpongy(World world, int x, int y, int z) {
		int range = (this.getBounds()[1]-this.getBounds()[0])/2;
		int numcave = 0;
		int numsolid = 0;
		boolean dungeon = false;
		boolean mineshaft = false;
		boolean stronghold = false;

		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				for (int k = y; k >= 0; k--) {
					int id = (world.getBlockId(x+i, k, z+j));
					if (ReikaWorldHelper.caveBlock(id))
						numcave++;
					else
						numsolid++;
					if (id == Block.web.blockID)
						mineshaft = true;
					if (id == Block.endPortal.blockID || id == Block.endPortalFrame.blockID)
						stronghold = true;
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", id));
				}
			}
		}
		double ans = (double)numcave/(double)(numcave+numsolid);
		return ans;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (y > 96)
			return;
		tickcount++;
		this.getSummativeSidedPower();
		power = omega * torque;
		if (power < MINPOWER)
			return;
		int[] bounds = this.getBounds();
		this.eval2(world, x, y, z, meta, bounds);
		this.idToColor(bounds, y);
	}

	private void idToColor(int[] bounds, int y) {
		for (int j = bounds[0]; j <= bounds[1]; j++) {
			for (int i = 0; i < y; i++) {
				colors[i][j] = ReikaWorldHelper.blockColors(ids[i][j], ConfigRegistry.GPRORES.getState());
			}
		}
	}

	private void eval2(World world, int x, int y, int z, int meta, int[] bounds) {
		int a = 0;
		if (xdir)
			a = 1;
		int b = 1-a;
		int diff = (bounds[1]-bounds[0])/2;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d", val[0], val[1]));
		if (a == 1) {
			for (int j = bounds[0]; j <= bounds[1]; j++) {
				for (int i = 0; i < y; i++) {
					ids[i][j] = world.getBlockId(x+j-bounds[0]-diff, y-i-1, z);
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d", x-j*a+diff/2, z-j*b));
					if (ids[i][j] == Block.endPortal.blockID)
						this.getPlacer().triggerAchievement(RotaryAchievements.GPRENDPORTAL.get());
				}
			}
		}
		else {
			for (int j = bounds[0]; j <= bounds[1]; j++) {
				for (int i = 0; i < y; i++) {
					ids[i][j] = world.getBlockId(x, y-i-1, z+j-bounds[0]-diff);
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d", x-j*a+diff/2, z-j*b));
					if (ids[i][j] == Block.endPortal.blockID)
						this.getPlacer().triggerAchievement(RotaryAchievements.GPRENDPORTAL.get());
				}
			}
		}
	}

	public int[] getBounds() { //Returns [low, hi]
		int[] val = {40,40};

		int range = this.getRange();
		if (range <= 0)
			return val;
		val[0] -= range;
		val[1] += range;
		/*
		long jetpower = EnumEngineType.JET.getPower();

		if (power >= EnumEngineType.AC.getPower() && power < jetpower) {
			val[0]--;
			val[1]++;
		}

		if (power >= EnumEngineType.SPORT.getPower() && power < jetpower) {
			val[0] -= 2;
			val[1] += 2;
		}

		if (power >= EnumEngineType.MICRO.getPower() && power < jetpower) {
			val[0] -= 3;
			val[1] += 3;
		}*/

		if (val[0] < 0)
			val[0] = 0;
		if (val[1] >= 80)
			val[1] = 80;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d", val[0], val[1]));
		return val;
	}

	public int getRange() {
		return 2*(int)ReikaMathLibrary.logbase(power-MINPOWER, 2);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.GPR.ordinal();
	}

	@Override
	public int getMaxRange() {
		return 40;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}

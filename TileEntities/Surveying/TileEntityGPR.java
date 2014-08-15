/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.BlockColorMapper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityGPR extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	/** A depth-by-width array of the discovered block IDs, materials, colors
	 * drawn downwards (first slots are top layer) */
	public Block[][] ids = new Block[256][81]; //from 0-16 -> centred on 8 (8 above and below)
	public int[][] metas = new int[256][81];

	public int[][] colors = new int[256][81]; //these three arrays take 52KB RAM collectively (assuming Mat'l is 32bits)
	public boolean xdir;

	private int offsetX;
	private int offsetY;
	private int offsetZ;


	private int oldmeta = 0;

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)	{
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false;
		if (yCoord > 96)
			return false;
		return true;
	}

	public void shift(ForgeDirection dir, int amt) {
		offsetX += dir.offsetX*amt;
		offsetY += dir.offsetY*amt;
		offsetZ += dir.offsetZ*amt;
	}

	public void resetOffset() {
		offsetX = offsetY = offsetZ = 0;
	}

	public ForgeDirection getGuiDirection() {
		return xdir ? ForgeDirection.SOUTH : ForgeDirection.EAST;
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
					Block id = (world.getBlock(x+i, k, z+j));
					if (ReikaWorldHelper.caveBlock(id))
						numcave++;
					else
						numsolid++;
					if (id == Blocks.web)
						mineshaft = true;
					if (id == Blocks.end_portal || id == Blocks.end_portal_frame)
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
		this.getSummativeSidedPower();
		power = (long)omega * (long)torque;
		if (power < MINPOWER)
			return;
		RotaryAchievements.GPR.triggerAchievement(this.getPlacer());
		if (tickcount == 0) {
			int[] bounds = this.getBounds();
			this.eval2(world, x+offsetX, y+offsetY, z+offsetZ, meta, bounds);
			if (world.isRemote)
				this.blockToColor(bounds, y);
			tickcount = 20;
		}
		tickcount--;
	}

	private void blockToColor(int[] bounds, int y) {
		for (int j = bounds[0]; j <= bounds[1]; j++) {
			for (int i = 0; i < y; i++) {
				colors[i][j] = this.getBlockColor(ids[i][j], metas[i][j]);
			}
		}
	}

	public int getBlockColor(Block id, int meta) {
		return BlockColorMapper.instance.getColorForBlock(id, meta);
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
					ids[i][j] = world.getBlock(x+j-bounds[0]-diff, y-i-1, z);
					metas[i][j] = world.getBlockMetadata(x+j-bounds[0]-diff, y-i-1, z);
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d", x-j*a+diff/2, z-j*b));
					if (ids[i][j] == Blocks.end_portal || ids[i][j] == Blocks.end_portal_frame)
						RotaryAchievements.GPRENDPORTAL.triggerAchievement(this.getPlacer());
					if (ids[i][j] == Blocks.mob_spawner)
						RotaryAchievements.GPRSPAWNER.triggerAchievement(this.getPlacer());
				}
			}
		}
		else {
			for (int j = bounds[0]; j <= bounds[1]; j++) {
				for (int i = 0; i < y; i++) {
					ids[i][j] = world.getBlock(x, y-i-1, z+j-bounds[0]-diff);
					metas[i][j] = world.getBlockMetadata(x, y-i-1, z+j-bounds[0]-diff);
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d", x-j*a+diff/2, z-j*b));
					if (ids[i][j] == Blocks.end_portal || ids[i][j] == Blocks.end_portal_frame)
						RotaryAchievements.GPRENDPORTAL.triggerAchievement(this.getPlacer());
					if (ids[i][j] == Blocks.mob_spawner)
						RotaryAchievements.GPRSPAWNER.triggerAchievement(this.getPlacer());
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
		return (int)(2*ReikaMathLibrary.logbase(power-MINPOWER, 2));
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GPR;
	}

	@Override
	public int getMaxRange() {
		return 40;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		offsetX = NBT.getInteger("xoff");
		offsetY = NBT.getInteger("yoff");
		offsetZ = NBT.getInteger("zoff");
	}

	@Override
	public void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("xoff", offsetX);
		NBT.setInteger("yoff", offsetY);
		NBT.setInteger("zoff", offsetZ);
	}
}
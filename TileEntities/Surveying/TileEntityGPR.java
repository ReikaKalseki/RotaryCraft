/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockVector;
import Reika.DragonAPI.Interfaces.TileEntity.GuiController;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.BlockColorMapper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGPR extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	/** A depth-by-width array of the discovered block IDs, materials, colors
	 * drawn downwards (first slots are top layer) */
	public BlockKey[][] blocks = new BlockKey[256][81]; //from 0-16 -> centred on 8 (8 above and below)

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

	public BlockVector getLookDirection() {
		return new BlockVector(xCoord+offsetX, yCoord+offsetY, zCoord+offsetZ, ReikaDirectionHelper.getRightBy90(this.getGuiDirection()));
	}

	public void shift(ForgeDirection dir, int amt) {
		if (amt == 0) {
			offsetX = offsetY = offsetZ = 0;
		}
		else {
			offsetX += dir.offsetX*amt;
			offsetY += dir.offsetY*amt;
			offsetZ += dir.offsetZ*amt;
		}
	}

	public void shiftInt(int amt) {
		this.shift(this.getGuiDirection(), amt);
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
			tickcount = 20;
		}
		tickcount--;
	}

	@SideOnly(Side.CLIENT)
	public int getColor(int x, int y) {
		return this.getBlockColor(blocks[x][y]);
	}

	@SideOnly(Side.CLIENT)
	private int getBlockColor(BlockKey bk) {
		return bk != null ? BlockColorMapper.instance.getColorForBlock(bk.blockID, bk.metadata) : BlockColorMapper.UNKNOWN_COLOR;
	}

	private void eval2(World world, int x, int y, int z, int meta, int[] bounds) {
		int a = 0;
		if (xdir)
			a = 1;
		int b = 1-a;
		int diff = (bounds[1]-bounds[0])/2;
		if (a == 1) {
			for (int j = bounds[0]; j <= bounds[1]; j++) {
				for (int i = 0; i < y; i++) {
					BlockKey bk = BlockKey.getAt(world, x+j-bounds[0]-diff, y-i-1, z);
					blocks[i][j] = bk;
					this.checkAchievements(bk);
				}
			}
		}
		else {
			for (int j = bounds[0]; j <= bounds[1]; j++) {
				for (int i = 0; i < y; i++) {
					BlockKey bk = BlockKey.getAt(world, x, y-i-1, z+j-bounds[0]-diff);
					blocks[i][j] = bk;
					this.checkAchievements(bk);
				}
			}
		}
	}

	private void checkAchievements(BlockKey bk) {
		if (bk.blockID == Blocks.end_portal || bk.blockID == Blocks.end_portal_frame)
			RotaryAchievements.GPRENDPORTAL.triggerAchievement(this.getPlacer());
		else if (bk.blockID == Blocks.mob_spawner)
			RotaryAchievements.GPRSPAWNER.triggerAchievement(this.getPlacer());
	}

	public int[] getBounds() { //Returns [low, hi]
		int[] val = {40,40};

		int range = this.getRange();
		if (range <= 0)
			return val;
		val[0] -= range;
		val[1] += range;
		if (val[0] < 0)
			val[0] = 0;
		if (val[1] >= 80)
			val[1] = 80;
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

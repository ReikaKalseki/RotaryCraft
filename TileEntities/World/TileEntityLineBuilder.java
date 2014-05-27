/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityLineBuilder extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation, DiscreteFunction {

	private ForgeDirection dir;

	private StepTimer timer = new StepTimer(40);
	private boolean isOut = false;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (power >= MINPOWER && torque >= MINTORQUE)
			phi = 1-timer.getFraction()-0.01F;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.LINEBUILDER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		timer.setCap(this.getOperationTime());
		timer.update();

		if (timer.checkCap()) {
			if (!world.isRemote) {
				this.shiftBlocks(world, x, y, z);
				phi = 0.5F;
			}
		}
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			read = ForgeDirection.EAST;
			dir = ForgeDirection.WEST;
			break;
		case 1:
			read = ForgeDirection.WEST;
			dir = ForgeDirection.EAST;
			break;
		case 2:
			read = ForgeDirection.SOUTH;
			dir = ForgeDirection.NORTH;
			break;
		case 3:
			read = ForgeDirection.NORTH;
			dir = ForgeDirection.SOUTH;
			break;
		case 4:	//moving up
			read = ForgeDirection.DOWN;
			dir = ForgeDirection.UP;
			break;
		case 5:	//moving down
			read = ForgeDirection.UP;
			dir = ForgeDirection.DOWN;
			break;
		}
	}

	private boolean shiftBlocks(World world, int x, int y, int z) {
		SoundRegistry.LINEBUILDER.playSoundAtBlock(world, x, y, z);
		if (!this.canShift(world, x, y, z))
			return false;
		ItemStack is = this.getNextBlockToAdd();
		if (is == null)
			return false;
		int r = this.getLineLength();
		for (int i = r; i > 0; i--) {
			int rx = x+dir.offsetX*i;
			int ry = y+dir.offsetY*i;
			int rz = z+dir.offsetZ*i;
			int rx2 = x+dir.offsetX*(i+1);
			int ry2 = y+dir.offsetY*(i+1);
			int rz2 = z+dir.offsetZ*(i+1);
			int id = world.getBlockId(rx, ry, rz);
			int meta = world.getBlockMetadata(rx, ry, rz);
			world.setBlock(rx2, ry2, rz2, id, meta, 3);
			world.setBlock(rx, ry, rz, 0);
		}
		int rx = x+dir.offsetX;
		int ry = y+dir.offsetY;
		int rz = z+dir.offsetZ;
		world.setBlock(rx, ry, rz, is.itemID, is.getItemDamage(), 3);
		SoundRegistry.LINEBUILDER.playSoundAtBlock(world, rx, ry, rz);
		return true;
	}

	public boolean canShift(World world, int x, int y, int z) {
		int r = this.getLineLength()+1;
		int rx = xCoord+dir.offsetX*r;
		int ry = yCoord+dir.offsetY*r;
		int rz = zCoord+dir.offsetZ*r;
		boolean softend = ReikaWorldHelper.softBlocks(world, rx, ry, rz);
		return softend && r <= this.getMaxRange() && r > 0;
	}

	public ItemStack getNextBlockToAdd() {
		ItemStack is = ReikaInventoryHelper.getNextBlockInInventory(inv, true);
		if (is == null)
			return null;
		return new ItemStack(ReikaItemHelper.getWorldBlockIDFromItem(is), 1, ReikaItemHelper.getWorldBlockMetaFromItem(is));
	}

	@Override
	public int getRange() {
		int range = this.getLineLength();
		return range;
	}

	public int getLineLength() {
		int len = 0;
		int i = 1;
		int rx = xCoord+dir.offsetX*i;
		int ry = yCoord+dir.offsetY*i;
		int rz = zCoord+dir.offsetZ*i;
		int id = worldObj.getBlockId(rx, ry, rz);
		if (id == Block.bedrock.blockID)
			return Integer.MIN_VALUE;
		if (!ReikaPlayerAPI.playerCanBreakAt(worldObj, rx, ry, rz, placer))
			return Integer.MIN_VALUE;
		int maxr = this.getMaxRange();
		TileEntity te = worldObj.getBlockTileEntity(rx, ry, rz);
		if (te != null)
			return Integer.MIN_VALUE;
		while (!ReikaWorldHelper.softBlocks(worldObj, rx, ry, rz) && i <= maxr) {
			i++;
			rx = xCoord+dir.offsetX*i;
			ry = yCoord+dir.offsetY*i;
			rz = zCoord+dir.offsetZ*i;
			id = worldObj.getBlockId(rx, ry, rz);
			if (id == Block.bedrock.blockID)
				return Integer.MIN_VALUE;
			if (!ReikaPlayerAPI.playerCanBreakAt(worldObj, rx, ry, rz, placer))
				return Integer.MIN_VALUE;
			TileEntity tile = worldObj.getBlockTileEntity(rx, ry, rz);
			if (tile != null)
				return Integer.MIN_VALUE;
		}
		return i-1;
	}

	@Override
	public int getMaxRange() {
		return Math.max(64, ConfigRegistry.LINEBUILDER.getValue());
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Blocks";
	}

	@Override
	public int getOperationTime() {
		int time = DurationRegistry.RAM.getOperationTime(omega);
		return Math.max(3, time);
	}

}

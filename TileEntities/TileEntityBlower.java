package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBlower extends TileEntityPowerReceiver {

	private ForgeDirection facing;

	private ItemStack toTransfer = null;
	private int fromSlot = -1;

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BLOWER;
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
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (MINPOWER > power || MINSPEED > omega)
			return;

		ForgeDirection dir = this.getFacingDir();
		ForgeDirection from = dir.getOpposite();

		TileEntity source = this.getAdjacentTileEntity(from);
		TileEntity target = this.getAdjacentTileEntity(dir);

		boolean hasSource = this.checkSource(from, source);
		boolean hasTarget = this.checkTarget(dir, target);

		if (hasSource && hasTarget)
			this.transferItem((IInventory)source, (IInventory)target);

	}

	private ArrayList<ItemStack> getAllTransferrables() {
		return null;
	}

	private void transferItem(IInventory source, IInventory target) {
		int added = ReikaInventoryHelper.addStackAndReturnCount(toTransfer, target);
		if (added > 0) {
			ReikaInventoryHelper.decrStack(fromSlot, source, added);
		}
	}

	private boolean checkTarget(ForgeDirection dir, TileEntity target) {
		if (target instanceof ISidedInventory) {
			ISidedInventory ii = (ISidedInventory)target;
			int slot = ReikaInventoryHelper.locateNonFullStackOf(toTransfer, target);
			slot = ReikaInventoryHelper.getFirstEmptySlot(ii);
			if (slot != -1) {
				if (ii.canInsertItem(slot, toTransfer, dir.getOpposite().ordinal())) {
					if (ii.isItemValidForSlot(slot, toTransfer))
						return true;
				}
			}
		}
		else if (target instanceof IInventory) {
			IInventory ii = (IInventory)target;
			int slot = ReikaInventoryHelper.getFirstEmptySlot(ii);
			slot = ReikaInventoryHelper.getFirstEmptySlot(ii);
			if (slot != -1) {
				if (ii.isItemValidForSlot(slot, toTransfer))
					return true;
			}
		}
		return false;
	}

	private boolean checkSource(ForgeDirection from, TileEntity source) {
		if (source instanceof ISidedInventory) {
			ISidedInventory ii = (ISidedInventory)source;
			int slot = ReikaInventoryHelper.getFirstNonEmptySlot(ii);
			if (slot != -1) {
				ItemStack is = ii.getStackInSlot(slot);
				if (ii.canExtractItem(slot, is, from.getOpposite().ordinal())) {
					toTransfer = is.copy();
					fromSlot = slot;
					return true;
				}
			}
		}
		else if (source instanceof IInventory) {
			IInventory ii = (IInventory)source;
			int slot = ReikaInventoryHelper.getFirstNonEmptySlot(ii);
			if (slot != -1) {
				ItemStack is = ii.getStackInSlot(slot);
				toTransfer = is.copy();
				fromSlot = slot;
				return true;
			}
		}
		return false;
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 4:
			ready = y-1;
			writey = y+1;
			facing = ForgeDirection.DOWN;
			break;
		case 5:
			ready = y+1;
			writey = y-1;
			facing = ForgeDirection.UP;
			break;
		case 3:
			readz = z-1;
			writez = z+1;
			facing = ForgeDirection.NORTH;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			facing = ForgeDirection.WEST;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			facing = ForgeDirection.SOUTH;
			break;
		case 0:
			readx = x+1;
			writex = x-1;
			facing = ForgeDirection.EAST;
			break;
		}
	}

	/** The side we are emitting items to */
	public ForgeDirection getFacingDir() {
		return facing != null ? facing : ForgeDirection.UP;
	}

}

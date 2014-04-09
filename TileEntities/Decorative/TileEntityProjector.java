/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityProjector extends InventoriedPowerReceiver implements RangedEffect {

	public static final int MAXCHANNELS = 24;
	public static final int DELAY = 400;

	public int channel = 0;
	public boolean on = false;
	public boolean emptySlide = true;

	private boolean lastPower = false;

	public boolean canProject(int x2, int y2, int z2) {

		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		if (power < MINPOWER) {
			on = false;
			return;
		}
		on = true;
		if (tickcount >= DELAY) {
			tickcount = 0;
			//this.cycleInv();
		}
		this.getChannelFromActiveSlide();
		if (ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower))
			this.cycleInv();
		lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
	}

	private void getChannelFromActiveSlide() {
		if (inv[0] == null) {
			emptySlide = true;
			channel = 0;
			return;
		}
		if (inv[0].itemID == Item.eyeOfEnder.itemID) {
			emptySlide = false;
			channel = -1;
		}
		if (inv[0].itemID == Item.pocketSundial.itemID) {
			emptySlide = false;
			channel = -3;
		}
		if (inv[0].itemID != ItemRegistry.SLIDE.getShiftedID()) {
			emptySlide = true;
			return;
		}
		emptySlide = false;
		if (inv[0].getItemDamage() == 24) {
			channel = -2;
		}
		else
			channel = inv[0].getItemDamage();
	}

	public String getCustomImagePath() {
		if (inv[0] == null || inv[0].stackTagCompound == null)
			return "";
		NBTTagCompound nbt = inv[0].stackTagCompound;
		return nbt.getString("file");
	}

	public void cycleInv() {
		ItemStack active = inv[0];
		for (int i = 0; i < inv.length-1; i++) {
			inv[i] = inv[i+1];
		}
		inv[inv.length-1] = active;
		SoundRegistry.PROJECTOR.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 1, 1);
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			read = ForgeDirection.EAST;
			break;
		case 1:
			read = ForgeDirection.WEST;
			break;
		case 2:
			read = ForgeDirection.NORTH;
			break;
		case 3:
			read = ForgeDirection.SOUTH;
			break;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public int getRange() {
		int x; int z;
		switch(this.getBlockMetadata()) {
		case 0:
			x = xCoord-1;
			while (x >= xCoord-12 && worldObj.getBlockId(x, yCoord, zCoord) == 0)
				x--;
			return x-xCoord+1;
		case 1:
			x = xCoord+1;
			while (x <= xCoord+12+1 && (worldObj.getBlockId(x, yCoord, zCoord) == 0 || Block.blocksList[worldObj.getBlockId(x, yCoord, zCoord)].isAirBlock(worldObj, x, yCoord, zCoord)))
				x++;
			return -(x-xCoord);
		case 2:
			z = zCoord+1;
			while (z <= zCoord+1+12 && worldObj.getBlockId(xCoord, yCoord, z) == 0)
				z++;
			return -(z-zCoord);
		case 3:
			z = zCoord-1;
			while (z >= zCoord-12 && worldObj.getBlockId(xCoord, yCoord, z) == 0)
				z--;
			return z-zCoord+1;
		default:
			return 0;
		}
	}

	public boolean canShow() {
		int r = this.getRange();
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int a = 0; int b = 0;
		switch(this.getBlockMetadata()) {
		case 0:
			x += r-1;
			a = 1;
			break;
		case 1:
			x -= r;
			a = 1;
			break;
		case 2:
			z -= r;
			b = 1;
			break;
		case 3:
			z += r-1;
			b = 1;
			break;
		}
		int x2 = x; int z2 = z;
		switch(this.getBlockMetadata()) {
		case 0:
			x2++;
			break;
		case 1:
			x2--;
			break;
		case 2:
			z2--;
			break;
		case 3:
			z2++;
			break;
		}
		World world = worldObj;
		for (int k = 0; k <= 4; k++) {
			for (int i = -3; i <= 3; i++) {
				int id = world.getBlockId(x+b*i, y+k, z+a*i);
				if (id == 0 || !Block.blocksList[id].isOpaqueCube()) {
					return false;
				}
				if (!this.canProject(x+b*i, y+k, z+a*i))
					return false;
			}
		}
		for (int k = 0; k <= 4; k++) {
			for (int i = -3; i <= 3; i++) {
				int id = world.getBlockId(x2+b*i, y+k, z2+a*i);
				if (id != 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PROJECTOR;
	}

	@Override
	public int getSizeInventory() {
		return MAXCHANNELS;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.itemID == ItemRegistry.SLIDE.getShiftedID();
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		channel = NBT.getInteger("ch");
		emptySlide = NBT.getBoolean("empty");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("ch", channel);
		NBT.setBoolean("empty", emptySlide);
	}

	@Override
	public int getMaxRange() {
		return 8;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canShow())
			return 15;
		return 0;
	}

}

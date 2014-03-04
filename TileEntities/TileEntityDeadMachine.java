/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.Interfaces.InertIInv;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDeadMachine extends InventoriedRCTileEntity implements InertIInv {

	private Icon[] tex = new Icon[6];
	private String[] img = new String[6];

	private int tileID;
	private int tileMeta;

	private Block parentBlock;

	public void setInvSize(int size) {
		inv = new ItemStack[size];
	}

	public void setBlock(Block b, int id, int meta) {
		parentBlock = b;
		tileID = id;
		tileMeta = meta;
	}

	public ArrayList<ItemStack> getDrops(int fortune) {
		ArrayList li = parentBlock.getBlockDropped(worldObj, xCoord, yCoord, zCoord, tileMeta, fortune);
		return li;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (parentBlock == null)
			parentBlock = Block.blocksList[tileID];
		//ReikaJavaLibrary.pConsole(tileID+":"+tileMeta);
	}

	@Override
	public String toString() {
		return "Disabled Machine (Imitating "+this.getImitatedMachine()+")"+" @ "+xCoord+", "+yCoord+", "+zCoord;
	}

	public String getImitatedMachine() {
		return Block.blocksList[tileID].getLocalizedName();
	}

	public ItemStack getOriginalTile() {
		return new ItemStack(tileID, 1, tileMeta);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return null;//MachineRegistry.DEAD;
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
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("id", tileID);
		NBT.setInteger("meta", tileMeta);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tileID = NBT.getInteger("id");
		tileMeta = NBT.getInteger("meta");
	}

}

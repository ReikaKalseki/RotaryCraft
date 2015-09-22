/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;
import Reika.RotaryCraft.Registry.BlockRegistry;

public class TileEntityDecoTank extends TileEntityBase {

	private Fluid f;

	private boolean[] flags = new boolean[TankFlags.list.length];

	@Override
	public Block getTileEntityBlockID() {
		return BlockRegistry.DECOTANK.getBlockInstance();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	protected String getTEName() {
		return BlockRegistry.DECOTANK.getBlockInstance().getLocalizedName();
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		ReikaNBTHelper.writeFluidToNBT(NBT, f);
		NBT.setInteger("flags", ReikaArrayHelper.booleanToBitflags(flags));
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		f = ReikaNBTHelper.getFluidFromNBT(NBT);
		flags = ReikaArrayHelper.booleanFromBitflags(NBT.getInteger("flags"), TankFlags.list.length);
	}

	public Fluid getFluid() {
		return f;
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public void setLiquid(ItemStack is) {
		if (is.stackTagCompound != null) {
			if (is.stackTagCompound.getInteger("level") >= ItemBlockDecoTank.FILL)
				f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
		}
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public boolean getFlag(TankFlags flag) {
		return flags[flag.ordinal()];
	}

	public int getFlags() {
		int meta = 0;
		for (int k = 0; k < TankFlags.list.length; k++) {
			TankFlags f = TankFlags.list[k];
			if (this.getFlag(f))
				meta = meta | f.getItemMetadataBit();
		}
		return meta;
	}

	public void setFlags(ItemStack stack) {
		for (int k = 0; k < TankFlags.list.length; k++) {
			TankFlags f = TankFlags.list[k];
			if (f.apply(stack)) {
				flags[k] = true;
			}
		}
		this.syncAllData(false);
	}

	public static enum TankFlags {
		CLEAR("Clear Glass", new ItemStack(Blocks.glass_pane)),
		NOCOLOR("Ignore Fluid Color", new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE)),
		LIGHTED("Glowing", new ItemStack(Items.glowstone_dust)),
		RESISTANT("Resistant", new ItemStack(Blocks.obsidian)),
		;

		public final String displayName;
		private final ItemStack toggleFlag;

		public static final TankFlags[] list = values();

		private TankFlags(String s, ItemStack is) {
			displayName = s;
			toggleFlag = is;
		}

		public boolean toggle(InventoryCrafting ics) {
			for (int i = 0; i < ics.getSizeInventory(); i++) {
				ItemStack in = ics.getStackInSlot(i);
				if (this.isItem(in))
					return true;
			}
			return false;
		}

		public boolean isItem(ItemStack is) {
			return ReikaItemHelper.matchStacks(is, toggleFlag);
		}

		public boolean apply(IBlockAccess world, int x, int y, int z) {
			TileEntityDecoTank te = (TileEntityDecoTank)world.getTileEntity(x, y, z);
			return te != null && te.getFlag(this);//this.apply(world.getBlockMetadata(x, y, z));
		}

		public boolean apply(ItemStack item) {
			return this.applyItem(item.getItemDamage());
		}

		public boolean applyItem(int meta) {
			return (meta & this.getItemMetadataBit()) != 0;
		}

		public int getItemMetadataBit() {
			return (1 << this.ordinal());
		}
	}

}

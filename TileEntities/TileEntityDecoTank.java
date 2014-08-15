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

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;
import Reika.RotaryCraft.Registry.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class TileEntityDecoTank extends TileEntityBase {

	private Fluid f;

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
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		f = ReikaNBTHelper.getFluidFromNBT(NBT);
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

}
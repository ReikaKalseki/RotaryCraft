/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank;

public class ItemBlockDecoTank extends ItemBlock implements Fillable {

	public static final int FILL = 25;

	public ItemBlockDecoTank(int par1) {
		super(par1);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public int getCapacity(ItemStack is) {
		return FILL;
	}

	@Override
	public int getCurrentFillLevel(ItemStack is) {
		return is.stackTagCompound != null && is.stackTagCompound.hasKey("level") ? is.stackTagCompound.getInteger("level") : 0;
	}

	@Override
	public int addFluid(ItemStack is, Fluid f, int amt) {
		if (this.isValidFluid(f, is) && !this.isFull(is)) {
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			int currentlevel = is.stackTagCompound.getInteger("level");
			int toadd = Math.min(amt, this.getCapacity(is)-currentlevel);
			is.stackTagCompound.setInteger("level", currentlevel+toadd);
			ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
			return toadd;
		}
		return 0;
	}

	@Override
	public boolean isFull(ItemStack is) {
		return this.getCurrentFillLevel(is) >= this.getCapacity(is);
	}

	@Override
	public void getSubItems(int id, CreativeTabs tab, List li) {
		li.add(new ItemStack(id, 1, 0));
		li.add(new ItemStack(id, 1, 1));
	}

	@Override
	public boolean isValidFluid(Fluid f, ItemStack is) {
		return is.stackTagCompound != null ? f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound)) : true;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		boolean flag = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
		if (flag) {
			int id = world.getBlockId(x, y, z);
			if (id == RotaryCraft.decoTank.blockID) {
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te instanceof TileEntityDecoTank) {
					((TileEntityDecoTank)te).setLiquid(stack);
				}
			}
		}
		return flag;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean vb) {
		if (is.stackTagCompound != null) {
			Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
			if (f != null && this.getCurrentFillLevel(is) >= FILL) {
				li.add("Full of "+f.getLocalizedName());
			}
			else {
				li.add("Empty");
			}
		}
		else {
			li.add("Empty");
		}
	}

	@Override
	public Fluid getCurrentFluid(ItemStack is) {
		return is.stackTagCompound != null ? ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound) : null;
	}

}

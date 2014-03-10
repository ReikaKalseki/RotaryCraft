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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFuelTank extends ItemRotaryTool implements Fillable {

	private static final ArrayList<Fluid> creativeFluids = new ArrayList();

	public ItemFuelTank(int ID, int index) {
		super(ID, index);
	}

	@Override
	public boolean isValidFluid(Fluid f, ItemStack is) {
		return is.stackTagCompound != null ? f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound)) : true;
	}

	@Override
	public int getCapacity(ItemStack is) {
		return 16000;
	}

	@Override
	public int getCurrentFillLevel(ItemStack is) {
		return is.stackTagCompound != null ? is.stackTagCompound.getInteger("fuel") : 0;
	}

	@Override
	public int addFluid(ItemStack is, Fluid f, int amt) {
		int fuel = 0;
		if (!this.isValidFluid(f)) {
			return 0;
		}
		if (is.stackTagCompound == null) {
			is.stackTagCompound = new NBTTagCompound();
			ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
		}
		else {
			fuel = is.stackTagCompound.getInteger("fuel");
			if (!f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound))) {
				return 0;
			}
		}
		int toadd = Math.min(amt, this.getCapacity(is)-fuel);
		is.stackTagCompound.setInteger("fuel", fuel+toadd);
		return toadd;
	}

	public boolean isValidFluid(Fluid f) {
		if (f == null)
			return false;
		if (f.equals(FluidRegistry.getFluid("fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(ItemRegistry.getEntryByID(par1).getStackOf());
		for (int i = 0; i < creativeFluids.size(); i++) {
			ItemStack is = ItemRegistry.getEntryByID(par1).getStackOf();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("fuel", this.getCapacity(is));
			ReikaNBTHelper.writeFluidToNBT(nbt, creativeFluids.get(i));
			is.stackTagCompound = nbt;
			par3List.add(is);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean verbose) {
		int i = is.getItemDamage();
		if (is.stackTagCompound != null)
			li.add(this.getDisplayTag(is.stackTagCompound));
	}

	private String getDisplayTag(NBTTagCompound nbt) {
		Fluid f = ReikaNBTHelper.getFluidFromNBT(nbt);
		String fluid = f != null ? f.getLocalizedName() : "Null Fluid";
		int amt = nbt.getInteger("fuel");
		String amount = String.format("%d", amt);
		return "Contents: "+amount+" mB of "+fluid;
	}

	private static void addCreativeFluid(String name) {
		Fluid f = FluidRegistry.getFluid(name);
		if (f != null)
			creativeFluids.add(f);
	}

	public static void initCreativeFluids() {
		creativeFluids.clear();
		addCreativeFluid("fuel");
		addCreativeFluid("jet fuel");
		addCreativeFluid("rc ethanol");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.stackTagCompound != null) {
			Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
			int amt = this.getCurrentFillLevel(is);
			int slot = ReikaInventoryHelper.locateIDInInventory(ItemRegistry.JETPACK.getShiftedID(), ep.inventory);
			if (slot == -1) {
				slot = ReikaInventoryHelper.locateIDInInventory(ItemRegistry.BEDPACK.getShiftedID(), ep.inventory);
			}
			if (slot != -1) {
				ItemStack jet = ep.inventory.getStackInSlot(slot);
				ItemJetPack item = (ItemJetPack)jet.getItem();
				int fuel = this.getCurrentFillLevel(is);
				int added = item.addFluid(jet, f, fuel);
				int newfuel = fuel-added;
				is.stackTagCompound.setInteger("fuel", newfuel);
				if (newfuel <= 0)
					is.stackTagCompound = null;
			}
		}
		return is;
	}

	@Override
	public boolean isFull(ItemStack is) {
		return this.getCurrentFillLevel(is) >= this.getCapacity(is);
	}

}

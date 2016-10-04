/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Interfaces.Fillable;
import Reika.RotaryCraft.Auxiliary.Interfaces.IntegratedGearboxable;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemIntegratedGearbox extends ItemRotaryTool implements Fillable {

	public ItemIntegratedGearbox(int index) {
		super(index);
		this.setMaxDamage(0);
		hasSubtypes = true;
	}

	public static ItemStack getIntegratedGearItem(int ratio, Fluid f) {
		if (ratio == 0)
			return null;
		int meta = Math.abs(ratio);
		meta = ReikaMathLibrary.logbase2(meta);
		//if (ratio > 0)
		//	meta += 4;
		//return ItemRegistry.GEARUPGRADE.getStackOfMetadata(meta);
		ItemStack is = ItemRegistry.GEARUPGRADE.getStackOfMetadata(meta);
		if (f != null) {
			ItemIntegratedGearbox i = (ItemIntegratedGearbox)is.getItem();
			i.addFluid(is, f, i.getCapacity(is));
		}
		return is;
	}

	public static int getRatioFromIntegratedGearItem(ItemStack is, boolean requireFill) {
		int meta = is.getItemDamage();
		if (meta == 0)
			return 0;
		if (requireFill && is.stackTagCompound == null)
			return 0;
		ItemIntegratedGearbox i = (ItemIntegratedGearbox)is.getItem();
		if (requireFill && !i.isFull(is))
			return 0;
		boolean pos = !requireFill || i.getCurrentFluid(is) == FluidRegistry.getFluid("rc lubricant");//meta >= 4;
		int ratio = ReikaMathLibrary.intpow2(2, meta);
		return pos ? ratio : -ratio;

	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof IntegratedGearboxable) {
			if (((IntegratedGearboxable)te).applyIntegratedGear(is)) {
				if (!ep.capabilities.isCreativeMode)
					is.stackSize--;
				if (is.stackSize == 0)
					is = null;
				ep.setCurrentItemOrArmor(0, is);
				return true;
			}
		}
		return false;
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		int base = super.getItemSpriteIndex(item);
		if (item.getItemDamage() > 0)
			base++;
		if (this.isFull(item)) {
			Fluid f = this.getCurrentFluid(item);
			if (f == FluidRegistry.getFluid("rc lubricant"))
				base++;
			else if (f == FluidRegistry.getFluid("rc liquid nitrogen"))
				base += 2;
		}
		return base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i <= 4; i++) {
			ItemStack is = new ItemStack(item, 1, i);
			par3List.add(is);

			if (i > 0) {
				ItemStack is2 = is.copy();
				this.addFluid(is2, FluidRegistry.getFluid("rc lubricant"), this.getCapacity(is2));
				par3List.add(is2);

				is2 = is.copy();
				this.addFluid(is2, FluidRegistry.getFluid("rc liquid nitrogen"), this.getCapacity(is2));
				par3List.add(is2);
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean vb) {
		super.addInformation(is, ep, li, vb);

		if (is.getItemDamage() > 0) {
			int ratio = getRatioFromIntegratedGearItem(is, false);
			li.add("Ratio: "+Math.abs(ratio)+"x");
			ratio = getRatioFromIntegratedGearItem(is, true);
			if (ratio > 0) {
				li.add("Torque Mode");
			}
			else if (ratio < 0) {
				li.add("Speed Mode");
			}
			else {
				li.add("Requires Fluid");
				if (is.stackTagCompound != null) {
					int amt = this.getCurrentFillLevel(is);
					Fluid f = this.getCurrentFluid(is);
					li.add("Is "+(amt*100F/this.getCapacity(is))+"% full of "+f.getLocalizedName(new FluidStack(f, amt)));
				}
			}
		}
		else {
			li.add("Requires Gears and Fluid");
		}
	}

	@Override
	public boolean isValidFluid(Fluid f, ItemStack is) {
		return is.stackTagCompound != null ? f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound)) : this.isValidFluid(f);
	}

	private boolean isValidFluid(Fluid f) {
		if (f.equals(FluidRegistry.getFluid("rc liquid nitrogen")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rc lubricant")))
			return true;
		return false;
	}

	@Override
	public int getCapacity(ItemStack is) {
		return 500;
	}

	@Override
	public int getCurrentFillLevel(ItemStack is) {
		return is.stackTagCompound != null ? is.stackTagCompound.getInteger("lvl") : 0;
	}

	@Override
	public int addFluid(ItemStack is, Fluid f, int amt) {
		int liq = 0;
		if (!this.isValidFluid(f)) {
			return 0;
		}
		if (is.stackTagCompound == null) {
			is.stackTagCompound = new NBTTagCompound();
			ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
		}
		else {
			liq = is.stackTagCompound.getInteger("lvl");
			if (!f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound))) {
				return 0;
			}
		}
		int toadd = Math.min(amt, this.getCapacity(is)-liq);
		is.stackTagCompound.setInteger("lvl", liq+toadd);
		return toadd;
	}

	@Override
	public boolean isFull(ItemStack is) {
		return is.stackTagCompound != null && this.getCurrentFillLevel(is) >= this.getCapacity(is);
	}

	private boolean canFill(ItemStack is) {
		if (!this.isFull(is)) {
			return true;
		}
		return false;
	}

	@Override
	public Fluid getCurrentFluid(ItemStack is) {
		return is.stackTagCompound != null ? ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound) : null;
	}

}

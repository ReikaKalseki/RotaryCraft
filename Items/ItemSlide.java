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

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSlide extends ItemBasic {

	public ItemSlide(int index) {
		super(index);
		maxStackSize = 1;
		hasSubtypes = true;
		this.setIndex(index);
	}

	@Override
	protected CreativeTabs getCreativePage() {
		return RotaryCraft.tabRotaryTools;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int i = 0; i < 24; i++) {
			ItemStack item = new ItemStack(par1, 1, i);
			par3List.add(item);
		}
		//Custom sprite
		ItemStack item = new ItemStack(par1, 1, 24);
		item.stackTagCompound = new NBTTagCompound();
		item.stackTagCompound.setString("file", "[NO FILE]");
		par3List.add(item);
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName();// + "." + d;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		String base = ItemRegistry.SLIDE.getBasicName();
		int d = is.getItemDamage();
		if (d < 24)
			base += " ("+d+")";
		else if (d == 24) {
			base += " (Custom)";
		}
		return base;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean v) {
		if (is.getItemDamage() == 24) {
			li.add("Custom Image file:");
			li.add(is.stackTagCompound.getString("file"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.getItemDamage() == 24) {
			ep.openGui(RotaryCraft.instance, GuiRegistry.SLIDE.ordinal(), world, 0, 0, 0);
		}
		return is;
	}

}
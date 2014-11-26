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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEngineUpgrade extends ItemRotaryTool {

	public ItemEngineUpgrade(int index) {
		super(index);
		hasSubtypes = true;
		maxStackSize = 16;
	}

	@Override
	public int getItemSpriteIndex(ItemStack is) {
		return super.getItemSpriteIndex(is)+is.getItemDamage();
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		return super.getUnlocalizedName(is)+"."+is.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof UpgradeableMachine) {
			UpgradeableMachine u = (UpgradeableMachine)te;
			if (u.canUpgradeWith(is)) {
				u.upgrade();
				if (!ep.capabilities.isCreativeMode)
					is.stackSize--;
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < Upgrades.values().length; i++) {
			ItemStack is = new ItemStack(par1, 1, i);
			if (i == 2) {
				ItemStack is2 = is.copy();
				is2.stackTagCompound = new NBTTagCompound();
				is2.stackTagCompound.setInteger("magnet", 720);
				par3List.add(is2);
			}
			par3List.add(is);
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean vb) {
		if (is.getItemDamage() == 2) {
			if (is.stackTagCompound != null) {
				int magnet = is.stackTagCompound.getInteger("magnet");
				if (is.stackTagCompound.hasKey("magnet")) {
					li.add(String.format("Magnetized to %d microTeslas", magnet));
				}
				if (magnet < 720) {
					li.add("Must be magnetized to 720 microTeslas to be used");
				}
			}
			else {
				li.add("Must be magnetized to 720 microTeslas to be used");
			}
		}
	}
	/*
	@Override
	public String getItemStackDisplayName(ItemStack is) {
		return Upgrades.values()[is.getItemDamage()].desc;
	}*/

	public static enum Upgrades {

		PERFORMANCE("upgrade.gasperf"),
		MAGNETOSTATIC1("upgrade.tier1"), //Made with ethanol
		MAGNETOSTATIC2("upgrade.tier2"), //Made in magnetizer
		MAGNETOSTATIC3("upgrade.tier3"), //Made with pulse jet ingot
		MAGNETOSTATIC4("upgrade.tier4"), //Made with 4MW extractor product
		MAGNETOSTATIC5("upgrade.tier5"), //Made with bedrock
		AFTERBURNER("upgrade.afterburn");

		private final String desc;

		private Upgrades(String d) {
			desc = d;
		}

		public String getName() {
			return StatCollector.translateToLocal(desc);
		}
	}

}

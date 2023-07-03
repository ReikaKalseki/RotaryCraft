/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import Reika.DragonAPI.Exception.UnreachableCodeException;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

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
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < Upgrades.list.length; i++) {
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean vb) {
		li.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(Upgrades.list[is.getItemDamage()].getDescription(), 170));
		if (is.getItemDamage() == 2) {
			if (is.stackTagCompound != null) {
				int magnet = is.stackTagCompound.getInteger("magnet");
				if (is.stackTagCompound.hasKey("magnet")) {
					li.add(String.format("Magnetized to %d microTeslas", magnet));
				}
				if (magnet < 720) {
					li.add("Must be magnetized to 720");
					li.add("microTeslas to be used");
				}
			}
			else {
				li.add("Must be magnetized to 720");
				li.add("microTeslas to be used");
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
		AFTERBURNER("upgrade.afterburn"),
		EFFICIENCY("upgrade.efficiency"),
		FLUX("upgrade.flux"),
		REDSTONE("upgrade.redstone"),
		LODESTONE("upgrade.lodestone"); //Requires ReC lodestone

		public static final Upgrades[] list = values();

		private final String desc;

		private Upgrades(String d) {
			desc = d;
		}

		public String getName() {
			return StatCollector.translateToLocal(desc);
		}

		public ItemStack getStack() {
			return ItemRegistry.UPGRADE.getStackOfMetadata(this.ordinal());
		}

		public String getDescription() {
			switch(this) {
				case PERFORMANCE:
					return "Upgrades "+RotaryNames.getEngineName(EngineType.GAS.ordinal())+"s to "+RotaryNames.getEngineName(EngineType.SPORT.ordinal())+"s";
				case AFTERBURNER:
					return "Adds afterburner capability to the "+RotaryNames.getEngineName(EngineType.JET.ordinal());
				case FLUX:
					return "Improves energy efficiency/capacity on shaft-to-electromagnetic converter engines";
				case LODESTONE:
					return "Increases "+MachineRegistry.MAGNETIZER.getName()+" effectivity";
				case REDSTONE:
					return "Adds integrated redstone clock";
				case MAGNETOSTATIC1:
				case MAGNETOSTATIC2:
				case MAGNETOSTATIC3:
				case MAGNETOSTATIC4:
				case MAGNETOSTATIC5:
					return "Converter engine upgrade, tier "+this.name().charAt(this.name().length()-1);
				case EFFICIENCY:
					return "Improves energy efficiency on converter engines.";
			}
			throw new UnreachableCodeException(this);
		}

		public static String getDescriptionList() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < list.length; i++) {
				if (i >= MAGNETOSTATIC1.ordinal() && i <= MAGNETOSTATIC5.ordinal())
					continue;
				sb.append(list[i].getName().replace("Upgrade", "").trim());
				sb.append(" - ");
				sb.append(list[i].getDescription());
				if (i < list.length-1)
					sb.append("\n");
			}
			return sb.toString();
		}
	}

}

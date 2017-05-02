/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import Reika.ChromatiCraft.Items.Tools.ItemFloatstoneBoots;
import Reika.ChromatiCraft.Registry.ChromaItems;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IArmorApiarist;

@Strippable(value={"forestry.api.apiculture.IArmorApiarist"})
public class ItemBedrockArmor extends ItemRotaryArmor implements IArmorApiarist {

	public ItemBedrockArmor(int tex, int render, int type) {
		super(RotaryCraft.BEDROCK, render, type, tex);
	}

	public static enum HelmetUpgrades {
		NIGHTVISION(),
		VISOR(),
		APIARIST(ModList.FORESTRY);

		public static final HelmetUpgrades[] list = values();

		public final boolean isAvailable;

		private HelmetUpgrades() {
			this(true);
		}

		private HelmetUpgrades(ModList mod) {
			this(mod.isLoaded());
		}

		private HelmetUpgrades(boolean b) {
			isAvailable = b;
		}

		public boolean existsOn(ItemStack is) {
			return is.stackTagCompound != null && is.stackTagCompound.getBoolean(this.getNBT());
		}

		private String getNBT() {
			return this.name().toLowerCase(Locale.ENGLISH);
		}

		public void enable(ItemStack is, boolean set) {
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setBoolean(this.getNBT(), set);
		}

		public ItemStack[] getUpgradeItems() {
			switch(this) {
				case NIGHTVISION:
					return new ItemStack[]{ItemRegistry.NVG.getStackOf()};
				case VISOR:
					return new ItemStack[]{new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.diamond), new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE)};
				case APIARIST:
					return ReikaArrayHelper.getArrayOf(ForestryHandler.CraftingMaterials.WOVENSILK.getItem(), 8);
			}
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 0);
		ReikaEnchantmentHelper.applyEnchantments(is, this.getDefaultEnchantments());
		li.add(is);

		if (((ItemArmor)id).armorType == 0) {
			ItemStack is3 = is.copy();

			for (int i = 0; i < HelmetUpgrades.list.length; i++) {
				HelmetUpgrades g = HelmetUpgrades.list[i];
				ItemStack is2 = is.copy();
				if (g.isAvailable) {
					g.enable(is2, true);
					g.enable(is3, true);
					li.add(is2);
				}
			}

			li.add(is3);
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean vb) {
		super.addInformation(is, ep, li, vb);
		for (int i = 0; i < HelmetUpgrades.list.length; i++) {
			HelmetUpgrades g = HelmetUpgrades.list[i];
			if (g.isAvailable && g.existsOn(is)) {
				li.add("Upgraded: "+g.name());
			}
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer ep, ItemStack is) {
		if (armorType == 0 && HelmetUpgrades.NIGHTVISION.existsOn(is)) {
			ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 0));
			ReikaEntityHelper.setNoPotionParticles(ep);
		}
		ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(Double.MAX_VALUE);
	}

	public HashMap<Enchantment, Integer> getDefaultEnchantments() {
		HashMap<Enchantment, Integer> map = new HashMap();
		if (ItemRegistry.getEntryByID(this).isBedrockArmor()) {
			switch(armorType) {
				case 0:
					map.put(Enchantment.projectileProtection, 4);
					map.put(Enchantment.respiration, 3);
					break;
				case 1:
					map.put(Enchantment.blastProtection, 4);
					break;
				case 2:
					map.put(Enchantment.fireProtection, 4);
					break;
				case 3:
					map.put(Enchantment.featherFalling, 4);
					break;
			}
		}
		return map;
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
		this.forceEnchantments(is, world, entity, par4);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem ei) {
		ItemStack is = ei.getEntityItem();
		HashMap<Enchantment, Integer> map = this.getDefaultEnchantments();
		for (Enchantment e : map.keySet()) {
			if (!ReikaEnchantmentHelper.hasEnchantment(e, is)) {
				ei.playSound("random.break", 1, 1);
				ei.setDead();
			}
		}
		return false;
	}

	private void forceEnchantments(ItemStack is, World world, Entity entity, int slot) {
		HashMap<Enchantment, Integer> map = this.getDefaultEnchantments();
		for (Enchantment e : map.keySet()) {
			if (!ReikaEnchantmentHelper.hasEnchantment(e, is)) {
				entity.playSound("random.break", 1, 1);
				if (entity instanceof EntityPlayer) {
					EntityPlayer ep = (EntityPlayer)entity;
					ep.inventory.setInventorySlotContents(slot, null);
					ep.attackEntityFrom(DamageSource.generic, 10);
					ReikaChatHelper.sendChatToPlayer(ep, "The damaged tool has broken.");
					is = null;
					break;
				}
			}
		}
	}

	@Override
	public boolean providesProtection() {
		return true;
	}

	@Override
	public boolean isVulnerableTo(DamageSource src) {
		return true;
	}

	@Override
	public boolean canBeDamaged() {
		return false;
	}

	@Override
	public double getDamageMultiplier(DamageSource src) {
		return src.isUnblockable() ? 0.75 : 0.35;
	}

	@Override
	public int getItemEnchantability()
	{
		return ConfigRegistry.PREENCHANT.getState() ? 0 : Items.iron_pickaxe.getItemEnchantability();
	}

	public static boolean isWearingFullSuitOf(EntityLivingBase e) {
		for (int i = 1; i < 5; i++) {
			ItemStack is = e.getEquipmentInSlot(i);
			if (!checkItem(is))
				return false;
		}
		return true;
	}

	private static boolean checkItem(ItemStack is) {
		if (is == null)
			return false;
		if (ModList.CHROMATICRAFT.isLoaded()) {
			if (checkFloatstoneBoots(is))
				return true;
		}
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir == null)
			return false;
		if (!ir.isBedrockTypeArmor())
			return false;
		return true;
	}

	@ModDependent(ModList.CHROMATICRAFT)
	private static boolean checkFloatstoneBoots(ItemStack is) {
		if (ChromaItems.FLOATBOOTS.matchWith(is)) {
			ItemStack in = ItemFloatstoneBoots.getSpecialItem(is);
			if (in != null) {
				return checkItem(in);
			}
		}
		return false;
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		return this == ItemRegistry.BEDHELM.getItemInstance() && HelmetUpgrades.NIGHTVISION.existsOn(item) ? 48 : super.getItemSpriteIndex(item);
	}

	@Override
	public boolean protectEntity(EntityLivingBase entity, ItemStack armor, String cause, boolean doProtect) {
		ItemStack head = entity.getEquipmentInSlot(4);
		ItemRegistry ir = head != null ? ItemRegistry.getEntry(head) : null;
		return ir != null && ir.isBedrockArmor() && HelmetUpgrades.APIARIST.existsOn(head);
	}

	@Override
	@Deprecated
	public boolean protectPlayer(EntityPlayer player, ItemStack armor, String cause, boolean doProtect) {
		return this.protectEntity(player, armor, cause, doProtect);
	}

	@Override
	public final void setDamage(ItemStack stack, int damage) {

	}

}

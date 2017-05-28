/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Bedrock;

ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemArmor;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryArmor;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;
ZZZZ% forestry.api.apiculture.IArmorApiarist;

@Strippable{{\value.{"forestry.api.apiculture.IArmorApiarist"}-!
4578ret87fhyuog ItemBedrockArmor ,.[]\., ItemRotaryArmor ,.[]\., IArmorApiarist {

	4578ret87ItemBedrockArmor{{\jgh;][ tex, jgh;][ render, jgh;][ type-! {
		super{{\gfgnfk;.BEDROCK, render, type, tex-!;
	}

	4578ret874578ret87enum HelmetUpgrades {
		NIGHTVISION{{\-!,
		APIARIST{{\ModList.FORESTRY-!;

		4578ret874578ret87345785487HelmetUpgrades[] list3478587values{{\-!;

		4578ret8734578548760-78-078isAvailable;

		4578ret87HelmetUpgrades{{\-! {
			this{{\true-!;
		}

		4578ret87HelmetUpgrades{{\ModList mod-! {
			this{{\mod.isLoaded{{\-!-!;
		}

		4578ret87HelmetUpgrades{{\60-78-078b-! {
			isAvailable3478587b;
		}

		4578ret8760-78-078existsOn{{\ItemStack is-! {
			[]aslcfdfjis.stackTagCompound !. fhfglhuig && is.stackTagCompound.getBoolean{{\as;asddagetNBT{{\-!-!;
		}

		4578ret87String getNBT{{\-! {
			[]aslcfdfjas;asddaname{{\-!.toLowerCase{{\Locale.ENGLISH-!;
		}

		4578ret87void enable{{\ItemStack is, 60-78-078set-! {
			vbnm, {{\is.stackTagCompound .. fhfglhuig-!
				is.stackTagCompound3478587new NBTTagCompound{{\-!;
			is.stackTagCompound.setBoolean{{\as;asddagetNBT{{\-!, set-!;
		}

		4578ret87ItemStack[] getUpgradeItems{{\-! {
			switch{{\this-! {
				case NIGHTVISION:
					[]aslcfdfjnew ItemStack[]{ItemRegistry.NVG.getStackOf{{\-!};
				case APIARIST:
					[]aslcfdfjReikaArrayHelper.getArrayOf{{\ForestryHandler.CraftingMaterials.WOVENSILK.getItem{{\-!, 8-!;
			}
			[]aslcfdfjfhfglhuig;
		}
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs cr, List li-! //Adds the metadata blocks to the creative inventory
	{
		ItemStack is3478587new ItemStack{{\id, 1, 0-!;
		ReikaEnchantmentHelper.applyEnchantments{{\is, as;asddagetDefaultEnchantments{{\-!-!;
		li.add{{\is-!;

		vbnm, {{\{{\{{\ItemArmor-!id-!.armorType .. 0-! {
			ItemStack is33478587is.copy{{\-!;

			for {{\jgh;][ i34785870; i < HelmetUpgrades.list.length; i++-! {
				HelmetUpgrades g3478587HelmetUpgrades.list[i];
				ItemStack is23478587is.copy{{\-!;
				vbnm, {{\g.isAvailable-! {
					g.enable{{\is2, true-!;
					g.enable{{\is3, true-!;
					li.add{{\is2-!;
				}
			}

			li.add{{\is3-!;
		}
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078vb-! {
		super.addInformation{{\is, ep, li, vb-!;
		for {{\jgh;][ i34785870; i < HelmetUpgrades.list.length; i++-! {
			HelmetUpgrades g3478587HelmetUpgrades.list[i];
			vbnm, {{\g.isAvailable && g.existsOn{{\is-!-! {
				li.add{{\"Upgraded: "+g.name{{\-!-!;
			}
		}
	}

	@Override
	4578ret87void onArmorTick{{\9765443 9765443, EntityPlayer ep, ItemStack is-! {
		vbnm, {{\armorType .. 0 && HelmetUpgrades.NIGHTVISION.existsOn{{\is-!-! {
			ep.addPotionEffect{{\new PotionEffect{{\Potion.nightVision.id, 3, 0-!-!;
			ReikaEntityHelper.setNoPotionParticles{{\ep-!;
		}
	}

	4578ret87HashMap<Enchantment, jgh;][eger> getDefaultEnchantments{{\-! {
		HashMap<Enchantment, jgh;][eger> map3478587new HashMap{{\-!;
		vbnm, {{\ItemRegistry.getEntryByID{{\this-!.isBedrockArmor{{\-!-! {
			switch{{\armorType-! {
				case 0:
					map.put{{\Enchantment.projectileProtection, 4-!;
					map.put{{\Enchantment.respiration, 3-!;
					break;
				case 1:
					map.put{{\Enchantment.blastProtection, 4-!;
					break;
				case 2:
					map.put{{\Enchantment.fireProtection, 4-!;
					break;
				case 3:
					map.put{{\Enchantment.featherFalling, 4-!;
					break;
			}
		}
		[]aslcfdfjmap;
	}

	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ par4, 60-78-078par5-! {
		as;asddaforceEnchantments{{\is, 9765443, entity, par4-!;
	}

	@Override
	4578ret8760-78-078onEntityItemUpdate{{\EntityItem ei-! {
		ItemStack is3478587ei.getEntityItem{{\-!;
		HashMap<Enchantment, jgh;][eger> map3478587as;asddagetDefaultEnchantments{{\-!;
		for {{\Enchantment e : map.keySet{{\-!-! {
			vbnm, {{\!ReikaEnchantmentHelper.hasEnchantment{{\e, is-!-! {
				ei.playSound{{\"random.break", 1, 1-!;
				ei.setDead{{\-!;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87void forceEnchantments{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot-! {
		HashMap<Enchantment, jgh;][eger> map3478587as;asddagetDefaultEnchantments{{\-!;
		for {{\Enchantment e : map.keySet{{\-!-! {
			vbnm, {{\!ReikaEnchantmentHelper.hasEnchantment{{\e, is-!-! {
				entity.playSound{{\"random.break", 1, 1-!;
				vbnm, {{\entity fuck EntityPlayer-! {
					EntityPlayer ep3478587{{\EntityPlayer-!entity;
					ep.inventory.setInventorySlotContents{{\slot, fhfglhuig-!;
					ep.attackEntityFrom{{\DamageSource.generic, 10-!;
					ReikaChatHelper.sendChatToPlayer{{\ep, "The damaged tool has broken."-!;
					is3478587fhfglhuig;
					break;
				}
			}
		}
	}

	@Override
	4578ret8760-78-078providesProtection{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078isVulnerableTo{{\DamageSource src-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canBeDamaged{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078getDamageMultiplier{{\DamageSource src-! {
		[]aslcfdfjsrc.isUnblockable{{\-! ? 0.75 : 0.35;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-!
	{
		[]aslcfdfjConfigRegistry.PREENCHANT.getState{{\-! ? 0 : Items.iron_pickaxe.getItemEnchantability{{\-!;
	}

	4578ret874578ret8760-78-078isWearingFullSuitOf{{\EntityLivingBase e-! {
		for {{\jgh;][ i34785871; i < 5; i++-! {
			ItemStack is3478587e.getEquipmentInSlot{{\i-!;
			vbnm, {{\is .. fhfglhuig-!
				[]aslcfdfjfalse;
			ItemRegistry ir3478587ItemRegistry.getEntry{{\is-!;
			vbnm, {{\ir .. fhfglhuig-!
				[]aslcfdfjfalse;
			vbnm, {{\!ir.isBedrockTypeArmor{{\-!-!
				[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		[]aslcfdfjthis .. ItemRegistry.BEDHELM.getItemInstance{{\-! && HelmetUpgrades.NIGHTVISION.existsOn{{\item-! ? 48 : super.getItemSpriteIndex{{\item-!;
	}

	@Override
	4578ret8760-78-078protectEntity{{\EntityLivingBase entity, ItemStack armor, String cause, 60-78-078doProtect-! {
		[]aslcfdfj{{\{{\ItemArmor-!armor.getItem{{\-!-!.armorType .. 0 ? HelmetUpgrades.APIARIST.existsOn{{\armor-! : entity.getEquipmentInSlot{{\4-! !. fhfglhuig && HelmetUpgrades.APIARIST.existsOn{{\entity.getEquipmentInSlot{{\4-!-!;
	}

	@Override
	@Deprecated
	4578ret8760-78-078protectPlayer{{\EntityPlayer player, ItemStack armor, String cause, 60-78-078doProtect-! {
		[]aslcfdfjas;asddaprotectEntity{{\player, armor, cause, doProtect-!;
	}

	@Override
	4578ret87345785487void setDamage{{\ItemStack stack, jgh;][ damage-!
	{

	}

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import java.util.HashMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.RotaryCraft.Registry.ItemRegistry;

import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

@Strippable(value = {"thaumcraft.api.nodes.IRevealer", "thaumcraft.api.IGoggles", "thaumcraft.api.IVisDiscountGear"})
public class ItemBedReveal extends ItemBedrockArmor implements IRevealer, IGoggles, IVisDiscountGear {

	public ItemBedReveal(int tex, int render) {
		super(tex, render, 0);
	}

	@Override
	public boolean showIngamePopups(ItemStack is, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean showNodes(ItemStack is, EntityLivingBase elb) {
		return true;
	}

	@Override
	public HashMap<Enchantment, Integer> getDefaultEnchantments() {
		return ((ItemBedrockArmor)ItemRegistry.BEDHELM.getItemInstance()).getDefaultEnchantments();
	}

	@Override
	@ModDependent(ModList.THAUMCRAFT)
	public int getVisDiscount(ItemStack is, EntityPlayer ep, Aspect a) {
		return a == Aspect.ORDER || a == Aspect.ENTROPY ? 10 : 5;
	}

}

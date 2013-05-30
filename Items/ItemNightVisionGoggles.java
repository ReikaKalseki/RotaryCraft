/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;

public class ItemNightVisionGoggles extends ItemRotaryArmor implements IArmorTextureProvider {

	public ItemNightVisionGoggles(int itemID, int texID) {
		super(itemID, RotaryCraft.NVGM, texID, 0, 97);
		this.setNoRepair();
		hasSubtypes = true;
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {
		if (is.getItemDamage() > 0) {
			ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 0));
			if (itemRand.nextInt(40) == 0) {
				ep.setCurrentItemOrArmor(4, new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1));
				if (is.getItemDamage() == 2) {
					ReikaChatHelper.clearChat();
					ReikaChatHelper.write("Night-Vision Goggles charge is very low (2 kJ)!");
				}
				if (is.getItemDamage() == 4) {
					ReikaChatHelper.clearChat();
					ReikaChatHelper.write("Night-Vision Goggles charge is low (4 kJ)!");
				}
				if (is.getItemDamage() == 16) {
					ReikaChatHelper.clearChat();
					ReikaChatHelper.write("Night-Vision Goggles charge is low (16 kJ)!");
				}
				if (is.getItemDamage() == 32) {
					ReikaChatHelper.clearChat();
					ReikaChatHelper.write("Night-Vision Goggles charge is low (32 kJ)!");
				}
			}
		}
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity e, int par4, boolean par5) {
		super.onUpdate(is, par2World, e, par4, par5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		par3List.add(new ItemStack(par1, 1, 8192));
	}

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		return "/Reika/RotaryCraft/Textures/Misc/NVGoggles.png";
	}

}

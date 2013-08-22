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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class ItemBedrockArmor extends ItemRotaryArmor {

	public ItemBedrockArmor(int ID, int tex, int render, int type) {
		super(ID, RotaryCraft.BEDROCK, render, type, tex);
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {

	}

	@Override
	public String getArmorTextureFile(ItemStack is) {
		ItemRegistry item = ItemRegistry.getEntry(is);
		switch(item) {
		case BEDHELM:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png";
		case BEDLEGS:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_2.png";
		case BEDCHEST:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png";
		case BEDBOOTS:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png";
		default:
			return "";
		}
	}

}

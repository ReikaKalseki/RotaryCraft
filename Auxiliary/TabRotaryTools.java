/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Instantiable.GUI.RegistryEnumCreativeTab;
import Reika.RotaryCraft.Registry.ItemRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabRotaryTools extends RegistryEnumCreativeTab {

	public TabRotaryTools() {
		super("RotaryCraft Tools");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
	{
		return ItemRegistry.SCREWDRIVER.getStackOf();
	}
}

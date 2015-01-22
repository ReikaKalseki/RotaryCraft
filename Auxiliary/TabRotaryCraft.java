/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.GUI.EnumCreativeTab;
import Reika.RotaryCraft.Registry.EngineType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabRotaryCraft extends EnumCreativeTab {

	public TabRotaryCraft() {
		super("RotaryCraft");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		return EngineType.JET.getCraftedProduct();
	}
}

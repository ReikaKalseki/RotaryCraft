/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public abstract class BlockWIP extends BlockBasicMachine {

	public BlockWIP(int par1) {
		super(par1, Material.iron);
	}

	@Override
	public final int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 12;
	}

	@Override
	public final Icon getIcon(int s, int meta) {
		return icons[0][s];
	}

	@Override
	public final void registerIcons(IconRegister par1IconRegister) {
		for (int i = 0; i < 6; i++)
			icons[0][i] = par1IconRegister.registerIcon("RotaryCraft:wip");
	}
}

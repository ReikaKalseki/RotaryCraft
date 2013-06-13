/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public abstract class BlockModelledMachine extends BlockBasicMachine {

	public BlockModelledMachine(int par1, Material par3Material) {
		super(par1, par3Material);
		//this.blockIndexInTexture = 2;
	}

	@Override
	public final boolean isOpaqueCube() {
		return false;
	}

	@Override
	public final boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public final int getRenderType() {
		return -1;
	}

	/** For disallowing this method in subclasses */
	public final int getBlockTextureFromSideAndMetadata(int s, int dmg) {
		return 255;
	}

	@Override
	public abstract void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z);

	@Override
	public final Icon getIcon(int s, int meta) {
		return icons[0][0];
	}

	@Override
	public final void registerIcons(IconRegister par1IconRegister) {
		icons[0][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
	}
}

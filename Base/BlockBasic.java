/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.RotaryCraft.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class BlockBasic extends Block implements SidedTextureIndex {

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public IIcon[][] icons = new IIcon[16][6];

	public BlockBasic(Material par3Material) {
		super(par3Material);
		this.setCreativeTab(DragonAPICore.isReikasComputer() || this.isAvailableInCreativeMode() ? RotaryCraft.tabRotary : null);
	}

	protected boolean isAvailableInCreativeMode() {
		return true;
	}

	@Override
	public int getRenderType() {
		return 0;//ClientProxy.BlockSheetTexRenderID;
	}

	public final String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}

	@Override
	public abstract IIcon getIcon(int s, int meta);
	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */

	@Override
	public abstract void registerBlockIcons(IIconRegister par1IconRegister);
}
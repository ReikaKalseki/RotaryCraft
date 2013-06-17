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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;

public abstract class BlockBasic extends Block implements SidedTextureIndex {

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public Icon[][] icons = new Icon[16][6];

	public BlockBasic(int par1, Material par3Material) {
		super(par1, par3Material);
		if (par1 != ExtraConfigIDs.MININGPIPE.getValue() && par1 != ExtraConfigIDs.LIGHTBLOCK.getValue() &&
				par1 != ExtraConfigIDs.BRIDGEBLOCK.getValue() && par1 != ExtraConfigIDs.BEDROCKSLICE.getValue() &&
				par1 != ExtraConfigIDs.BEAMBLOCK.getValue() && par1 != ExtraConfigIDs.CANOLA.getValue())
			this.setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	public int getRenderType() {
		return 0;//ClientProxy.BlockSheetTexRenderID;
	}

	public final String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}

	@Override
	public abstract Icon getIcon(int s, int meta);
	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	public abstract void registerIcons(IconRegister par1IconRegister);
}

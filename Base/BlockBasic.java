/*******************************************************************************
 * @author Reika
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
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.mod_RotaryCraft;

public abstract class BlockBasic extends Block implements SidedTextureIndex {

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public Icon[][] icons = new Icon[16][6];

	public BlockBasic(int par1, Material par3Material) {
		super(par1, par3Material);
		if (par1 != RotaryConfig.miningpipeid && par1 != RotaryConfig.lightblockid && par1 != RotaryConfig.lightbridgeid &&
				par1 != RotaryConfig.bedrocksliceid && par1 != RotaryConfig.beamblockid && par1 != RotaryConfig.canolaid)
			this.setCreativeTab(mod_RotaryCraft.tabRotary);
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

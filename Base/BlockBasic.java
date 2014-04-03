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
		if (this.isAvailableInCreativeMode(par1))
			this.setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	public int getRenderType() {
		return 0;//ClientProxy.BlockSheetTexRenderID;
	}

	public final String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}

	public boolean isAvailableInCreativeMode(int ID) {
		if (RotaryCraft.instance.isLocked())
			return false;
		if (ID == ExtraConfigIDs.MININGPIPE.getValue())
			return false;
		if (ID == ExtraConfigIDs.LIGHTBLOCK.getValue())
			return false;
		if (ID == ExtraConfigIDs.BRIDGEBLOCK.getValue())
			return false;
		if (ID == ExtraConfigIDs.BEDROCKSLICE.getValue())
			return false;
		if (ID == ExtraConfigIDs.BEAMBLOCK.getValue())
			return false;
		if (ID == ExtraConfigIDs.CANOLA.getValue())
			return false;
		return true;
	}

	@Override
	public abstract Icon getIcon(int s, int meta);
	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */

	@Override
	public abstract void registerIcons(IconRegister par1IconRegister);
}

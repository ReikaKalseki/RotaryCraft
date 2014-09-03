/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.EnchantmentRenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;

public class RenderForceField extends RenderProtectionDome
{
	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		super.renderTileEntityAt(tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			if (((TileEntityForceField)tile).hasEnchantments())
				EnchantmentRenderer.renderGlint(tile, model, null, par2, par4, par6);
		}
		else if (!tile.hasWorldObj()) {
			if (((TileEntityForceField)tile).hasEnchantments())
				EnchantmentRenderer.renderGlint(tile, model, null, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "forcetex2.png";
	}
}

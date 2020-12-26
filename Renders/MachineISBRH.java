/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2018
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.IBlockAccess;

import Reika.DragonAPI.Base.ISBRH;
import Reika.RotaryCraft.Base.RotaryModelBase;

@Deprecated
public class MachineISBRH extends ISBRH {

	protected MachineISBRH(int id) {
		super(id);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntitySpecialRenderer  tesr = TileEntityRendererDispatcher.instance.getSpecialRenderer(world.getTileEntity(x, y, z));
		if (tesr instanceof StaticModelRenderer) {
			RotaryModelBase rmb = ((StaticModelRenderer)tesr).getModel();
			//ISBRHModel model = new ISBRHModel();
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	public static interface StaticModelRenderer {

		public RotaryModelBase getModel();

	}

}

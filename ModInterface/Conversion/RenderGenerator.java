/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Conversion;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.ModInterface.ModelGenerator;

public class RenderGenerator extends RotaryTERenderer
{

	private ModelGenerator GeneratorModel = new ModelGenerator();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityGeneratorAt(TileEntityGenerator tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelGenerator var14;
		var14 = GeneratorModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/generatortex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;
		float var13;
		switch(var9) {
		case 2:
			var11 = 0;
			break;
		case 0:
			var11 = 180;
			break;
		case 1:
			var11 = 90;
			break;
		case 3:
			var11 = 270;
			break;
		}

		GL11.glRotatef(var11+180, 0.0F, 1.0F, 0.0F);
		var14.renderAll(tile, null, 0, 0);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityGeneratorAt((TileEntityGenerator)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "generatortex.png";
	}
}

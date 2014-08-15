/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

public class RenderPneumatic extends RotaryTERenderer
{

	private ModelPneumatic PneumaticModel = new ModelPneumatic();
	//private ModelPneumaticV PneumaticModelV = new ModelPneumaticV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityPneumaticEngineAt(TileEntityPneumaticEngine tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelPneumatic var14;
		var14 = PneumaticModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pneutex.png");

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

		GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
		var14.renderAll(tile, null, -tile.phi, 0);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityPneumaticEngineAt((TileEntityPneumaticEngine)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "pneutex.png";
	}
}
/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelClutch;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelVClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityClutch;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

public class RenderClutch extends RotaryTERenderer
{

	private ModelClutch ClutchModel = new ModelClutch();
	private ModelVClutch ClutchModelV = new ModelVClutch();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityClutchAt(TileEntityClutch tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelClutch var14;
		ModelVClutch var15;

		var14 = ClutchModel;
		var15 = ClutchModelV;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;	 //used to rotate the model about metadata

		if (tile.isInWorld()) {

			switch(tile.getBlockMetadata()) {
			case 0:
				var11 = 0;
				break;
			case 1:
				var11 = 180;
				break;
			case 2:
				var11 = 90;
				break;
			case 3:
				var11 = 270;
				break;
			case 4:
			case 5:
				var11 = 0;
				break;
			}

			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

		}
		float var13;
		if (tile.getBlockMetadata() < 4)
			var14.renderAll(tile, null, -tile.phi, 0);
		else {
			var15.renderAll(tile, null, tile.getBlockMetadata() == 5 ? tile.phi : -tile.phi, 0);
		}

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityClutchAt((TileEntityClutch)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "shafttex.png";
	}
}
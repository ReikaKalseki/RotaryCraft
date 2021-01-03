/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DM;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import Reika.RotaryCraft.Auxiliary.EnchantmentRenderer;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelWoodcutter;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityWoodcutter;

public class RenderWoodcutter extends RotaryTERenderer
{

	private ModelWoodcutter WoodcutterModel = new ModelWoodcutter();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityWoodcutterAt(TileEntityWoodcutter tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelWoodcutter var14;
		var14 = WoodcutterModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/woodcuttertex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;	 //used to rotate the model about metadata

		if (tile.isInWorld()) {
			switch(tile.getBlockMetadata()) {
				case 0:
					var11 = 180;
					break;
				case 1:
					var11 = 0;
					break;
				case 2:
					var11 = 270;
					break;
				case 3:
					var11 = 90;
					break;
			}

			GL11.glRotatef((float)var11-90, 0.0F, 1.0F, 0.0F);

		}
		float var13;

		if (tile.isInWorld()) {
			int c = tile.getJamColor();
			if (c != -1)
				GL11.glColor4f(ReikaColorAPI.getRed(c)/255F, ReikaColorAPI.getGreen(c)/255F, ReikaColorAPI.getBlue(c)/255F, 1);
		}

		var14.renderAll(tile, null, tile.phi);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityWoodcutterAt((TileEntityWoodcutter)tile, par2, par4, par6, par8);
		if (MinecraftForgeClient.getRenderPass() != 0 && tile.hasWorldObj()) {
			IORenderer.renderIO(tile, par2, par4, par6);
			if (((TileEntityWoodcutter)tile).getEnchantmentHandler().hasEnchantments())
				EnchantmentRenderer.renderGlint(tile, WoodcutterModel, null, par2, par4, par6);
		}
		else if (!tile.hasWorldObj()) {
			int var11 = 0;
			switch(tile.getBlockMetadata()) {
				case 0:
					var11 = 180;
					break;
				case 1:
					var11 = 0;
					break;
				case 2:
					var11 = 270;
					break;
				case 3:
					var11 = 90;
					break;
			}
			GL11.glRotatef((float)var11-90, 0.0F, 1.0F, 0.0F);
			if (((TileEntityWoodcutter)tile).getEnchantmentHandler().hasEnchantments())
				EnchantmentRenderer.renderGlint(tile, WoodcutterModel, null, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "woodcuttertex.png";
	}
}

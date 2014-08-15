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
import org.lwjgl.opengl.GL12;

public class RenderCompressor extends RotaryTERenderer
{

	private ModelCompressor CompressorModel = new ModelCompressor();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityCompressorAt(TileEntityAirCompressor tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelCompressor var14;

		var14 = CompressorModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/airtex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

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
				var11 = 0;
				break;
			case 3:
				var11 = 90;
				break;
			case 4:
				var11 = 180;
				break;
			case 5:
				var11 = 270;
				break;
			}

			if (tile.getBlockMetadata() < 2) {
				GL11.glRotatef(var11, 0, 0, 1);
				if (tile.getBlockMetadata() == 1)
					GL11.glTranslated(0, -2, 0);
			}
			else {
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glRotatef(var11, 0, 0, 1);
				GL11.glTranslated(0, -1, -1);
			}
		}

		float var13;

		var14.renderAll(tile, null, -tile.phi, 0);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityCompressorAt((TileEntityAirCompressor)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "airtex.png";
	}
}
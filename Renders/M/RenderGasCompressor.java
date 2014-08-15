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

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelGasCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGasCompressor extends RotaryTERenderer
{

	private ModelGasCompressor GasCompressorModel = new ModelGasCompressor();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityGasCompressorAt(TileEntityFluidCompressor tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelGasCompressor var14;
		var14 = GasCompressorModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/gascompressortex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll(tile, null, 0, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityGasCompressorAt((TileEntityFluidCompressor)tile, par2, par4, par6, par8);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "gascompressortex.png";
	}
}
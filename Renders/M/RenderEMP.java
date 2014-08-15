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
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelEMP;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderEMP extends RotaryTERenderer
{

	private ModelEMP EMPModel = new ModelEMP();
	//private ModelEMPV EMPModelV = new ModelEMPV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityEMPAt(TileEntityEMP tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelEMP var14;
		var14 = EMPModel;
		//ModelEMPV var15;
		//var14 = this.EMPModelV;
		if (tile.isLoading())
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/emptex2.png");
		else
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/emptex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		if (!tile.isInWorld()) {
			GL11.glScaled(1.125, 1.125, 1.125);
			GL11.glTranslatef(0, -0.25F, 0);
			GL11.glRotatef(-90, 0, 1, 0);
		}
		int var11 = 0;	 //used to rotate the model about metadata


		//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
		float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
		// if (tile.getBlockMetadata() < 4)

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
			this.renderTileEntityEMPAt((TileEntityEMP)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			if (((TileEntityEMP)tile).isLoading())
				this.renderCharging((TileEntityEMP)tile, par2, par4, par6);
		}
	}

	private void renderCharging(TileEntityEMP te, double par2, double par4, double par6) {

	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "emptex.png";
	}
}
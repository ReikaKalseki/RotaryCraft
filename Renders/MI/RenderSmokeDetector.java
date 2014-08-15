/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.MI;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelSmokeDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySmokeDetector;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderSmokeDetector extends RotaryTERenderer
{

	private ModelSmokeDetector SmokeDetectorModel = new ModelSmokeDetector();
	//private ModelSmokeDetectorV SmokeDetectorModelV = new ModelSmokeDetectorV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntitySmokeDetectorAt(TileEntitySmokeDetector tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
		{
			var9 = 0;
		}
		else
		{

			var9 = tile.getBlockMetadata();


			{
				//((BlockSmokeDetectorBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelSmokeDetector var14;
			var14 = SmokeDetectorModel;
			//ModelSmokeDetectorV var15;
			//var14 = this.SmokeDetectorModelV;
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/smokedetectortex.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (!tile.isInWorld()) {
				GL11.glScaled(2, 2, 2);
				GL11.glTranslatef(0, 0, 0);
				GL11.glRotatef(-90, 0, 1, 0);
			}
			int var11 = 0;	 //used to rotate the model about metadata

			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			// if (tile.getBlockMetadata() < 4)


			var14.renderAll(tile, null, 0, 0);
			// else
			//var15.renderAll(tile, );
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntitySmokeDetectorAt((TileEntitySmokeDetector)tile, par2, par4, par6, par8);
		//if (tile.isInWorld())
		//	IORenderer.renderIO((TileEntityIOMachine)tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "smokedetectortex.png";
	}
}
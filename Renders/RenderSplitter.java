/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.MultiModel;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelSplitter;
import Reika.RotaryCraft.Models.ModelSplitter2;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;

public class RenderSplitter extends RotaryTERenderer implements MultiModel
{

	private ModelSplitter SplitterModel = new ModelSplitter();
	private ModelSplitter2 SplitterModel2 = new ModelSplitter2();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntitySplitterAt(TileEntitySplitter tile, double par2, double par4, double par6, float par8)
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
				//((BlockSplitterBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{

			ModelSplitter var14 = SplitterModel;
			ModelSplitter2 var15 = SplitterModel2;

			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/beveltex.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;	 //used to rotate the model about metadata
			int dir = 1;
			int meta;
			if (tile.isInWorld()) {

				meta = tile.getBlockMetadata();

				switch(meta) {
				case 0:
					var11 = -90;
					break;
				case 1:
					var11 = 0;
					break;
				case 2:
					var11 = 90;
					break;
				case 3:
					var11 = 180;
					break;
				case 4:
					var11 = -90;
					break;
				case 5:
					var11 = 0;
					break;
				case 6:
					var11 = 90;
					break;
				case 7:
					var11 = 180;
					break;

				case 8:
					var11 = 270;
					break;
				case 9:
					var11 = 0;
					break;
				case 10:
					var11 = 90;
					break;
				case 11:
					var11 = 180;
					break;
				case 12:
					var11 = -90;
					break;
				case 13:
					var11 = 0;
					break;
				case 14:
					var11 = 90;
					break;
				case 15: //good
				var11 = 180;
				break;
				}



				GL11.glRotatef((float)var11-90, 0.0F, 1.0F, 0.0F);
			}
			else {
				meta = 0;
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			}
			//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			if (meta < 4 || (meta >= 8 && meta < 12))
				var14.renderAll(null, -tile.phi*dir);
			else
				var15.renderAll(null, -tile.phi*dir);
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
			this.renderTileEntitySplitterAt((TileEntitySplitter)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "beveltex.png";
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.MI;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelAerosolizer;
import Reika.RotaryCraft.TileEntities.World.TileEntityAerosolizer;

public class RenderAerosolizer extends RotaryTERenderer
{

	private ModelAerosolizer AerosolizerModel = new ModelAerosolizer();
	//private ModelAerosolizerV AerosolizerModelV = new ModelAerosolizerV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityAerosolizerAt(TileEntityAerosolizer tile, double par2, double par4, double par6, float par8)
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
				//((BlockAerosolizerBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelAerosolizer var14;
			var14 = AerosolizerModel;
			//ModelAerosolizerV var15;
			//var14 = this.AerosolizerModelV;
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/aerotex.png");

			GL11.glPushMatrix();
			// if (!tile.isInWorld())
			//GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;	 //used to rotate the model about metadata

			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			// if (tile.getBlockMetadata() < 4)
			var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(/*liqlevel > 0*/false), 0, 0);
			// else
			//var15.renderAll(tile, );
			//if (!tile.isInWorld())
			//GL11.glEnable(GL11.GL_LIGHTING);
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityAerosolizerAt((TileEntityAerosolizer)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.drawPotionLevels((TileEntityAerosolizer)tile, par2, par4, par6);
		}
	}

	private void drawPotionLevels(TileEntityAerosolizer tile, double par2, double par4, double par6) {
		GL11.glPushMatrix();

		Tessellator v5 = Tessellator.instance;
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();

		GL11.glTranslated(par2, par4, par6);

		for (int i = 0; i < 9; i++) {
			int lvl = tile.getPotionLevel(i);
			if (lvl > 0) {

				double h = 0.785+(0.075*lvl/tile.CAPACITY);
				double w = 0.25;

				double dx = 0.0625+(i%3)*(5/16D);
				double dy = 0.0625+(i/3)*(5/16D);
				v5.startDrawingQuads();

				v5.setColorRGBA_I(tile.getPotionColor(i), 192);

				v5.addVertex(dx, h, dy+w);
				v5.addVertex(dx+w, h, dy+w);
				v5.addVertex(dx+w, h, dy);
				v5.addVertex(dx, h, dy);

				v5.draw();
			}
		}

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "aerotex.png";
	}
}

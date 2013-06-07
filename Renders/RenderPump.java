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
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelPump;
import Reika.RotaryCraft.TileEntities.TileEntityPump;

public class RenderPump extends RotaryTERenderer
{

	private ModelPump PumpModel = new ModelPump();
	//private ModelPumpV PumpModelV = new ModelPumpV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityPumpAt(TileEntityPump tile, double par2, double par4, double par6, float par8)
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
				//((BlockPumpBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelPump var14;
			var14 = PumpModel;
			//ModelPumpV var15;
			//var14 = this.PumpModelV;
			if (tile.liquidID == 8 || tile.liquidID == 9)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pumptex2.png");
			else
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pumptex.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			if (tile.isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
				GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;	 //used to rotate the model about metadata

			if (tile.isInWorld()) {
				switch(tile.getBlockMetadata()) {
				case 0:
					var11 = 90;
					break;
				case 1:
					var11 = 0;
					break;
				}

				GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

			}
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			// if (tile.getBlockMetadata() < 4)
			Object[] pars = new Object[3];
			pars[0] = tile.liquidLevel > 0 && MinecraftForgeClient.getRenderPass() == 1;
			pars[1] = (tile.shouldRenderInPass(0) && MinecraftForgeClient.getRenderPass() == 0) || !tile.isInWorld();
			pars[2] = tile.damage > 400;
			var14.renderAll(ReikaJavaLibrary.makeListFromArray(pars), -tile.phi);
			// else
			//var15.renderAll();
			if (tile.isInWorld() || MinecraftForgeClient.getRenderPass() == 1)
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityPumpAt((TileEntityPump)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityPump tp = (TileEntityPump)te;
		if (tp.liquidID == 8 || tp.liquidID == 9)
			return "pumptex.png";
		return "pumptex2.png";
	}
}

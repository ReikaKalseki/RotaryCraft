/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;

public class RenderReservoir extends RotaryTERenderer
{

	private ModelReservoir ReservoirModel = new ModelReservoir();
	//private ModelReservoirV ReservoirModelV = new ModelReservoirV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityReservoirAt(TileEntityReservoir tile, double par2, double par4, double par6, float par8)
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
				//((BlockReservoirBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelReservoir var14;
			var14 = ReservoirModel;
			//ModelReservoirV var15;
			//var14 = this.ReservoirModelV;
			if (tile.liquidID == 8 || tile.liquidID == 9)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/reservoirtex.png");
			else /*if (tile.liquidID == 10 || tile.liquidID == 11)*/
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/reservoirtex2.png");
			// else if (tile.liquidID == tile.FUELID)
			//this.bindTextureByName("/Reika/RotaryCraft/reservoirtex3.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			if (tile.isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
				GL11.glEnable(GL11.GL_BLEND);
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
					var11 = 270;
					break;
				case 3:
					var11 = 90;
					break;
				}

				if (tile.getBlockMetadata() <= 3)
					GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
				else {
					GL11.glRotatef(var11, 1F, 0F, 0.0F);
					GL11.glTranslatef(0F, -1F, 1F);
					if (tile.getBlockMetadata() == 5)
						GL11.glTranslatef(0F, 0F, -2F);
				}
			}
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			// if (tile.getBlockMetadata() < 4)
			var14.renderAll(ReikaJavaLibrary.makeListFromArray(this.getConditions(tile)), 0);
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
			this.renderTileEntityReservoirAt((TileEntityReservoir)tile, par2, par4, par6, par8);
	}

	private Object[] getConditions(TileEntityReservoir te) {
		Object[] vals = new Object[3];
		vals[0] = te.liquidLevel > 0 && MinecraftForgeClient.getRenderPass() == 1;
		vals[1] = te.getLiquidScaled(14);
		vals[2] = (te.shouldRenderInPass(0) && MinecraftForgeClient.getRenderPass() == 0) || !te.isInWorld();
		return vals;
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityReservoir tr = (TileEntityReservoir)te;
		if (tr.liquidID == 8 || tr.liquidID == 9)
			return "reservoirtex.png";
		return "reservoirtex2.png";
	}
}

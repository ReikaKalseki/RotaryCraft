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
import Reika.RotaryCraft.Base.MultiModel;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelFlywheel;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;

public class RenderFlywheel extends RotaryTERenderer implements MultiModel
{

	private ModelFlywheel FlywheelModel = new ModelFlywheel();

	private int controlInt = 0;

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityFlywheelAt(TileEntityFlywheel tile, double par2, double par4, double par6, float par8)
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
				//((BlockFlywheelBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelFlywheel var14;
			var14 = FlywheelModel;
			if (tile.isInWorld())
			{
				switch(tile.getBlockMetadata()/4) {
				case 0:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex.png");
					break;
				case 1:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex2.png");
					break;
				case 2:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex3.png");
					break;
				case 3:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex4.png");
					break;
				}
			}
			else {
				switch(controlInt) {
				case 0:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex.png");
					break;
				case 1:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex2.png");
					break;
				case 2:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex3.png");
					break;
				case 3:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/flywheeltex4.png");
					break;
				}
			}

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			//GL11.glDisable(GL11.GL_LIGHTING);
			int var11 = 0;	 //used to rotate the model about metadata

			if (tile.isInWorld()) {

				switch(tile.getBlockMetadata()%4) {
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

				GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

			}
			//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			var14.renderAll(ReikaJavaLibrary.makeListFrom(tile.failed), -tile.phi);
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (par8 < -100) {
			controlInt = (int)-par8/1000;
			par8 = 0;
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.controlInt));
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityFlywheelAt((TileEntityFlywheel)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityFlywheel tile = (TileEntityFlywheel)te;
		if (tile.isInWorld())
		{
			switch(tile.getBlockMetadata()/4) {
			case 0:
				return "flywheeltex.png";
			case 1:
				return "flywheeltex2.png";
			case 2:
				return "flywheeltex3.png";
			case 3:
				return "flywheeltex4.png";
			}
		}
		else {
			switch(controlInt) {
			case 0:
				return "flywheeltex.png";
			case 1:
				return "flywheeltex2.png";
			case 2:
				return "flywheeltex3.png";
			case 3:
				return "flywheeltex4.png";
			}
		}
		return "flywheeltex.png";
	}
}

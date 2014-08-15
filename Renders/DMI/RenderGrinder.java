/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DMI;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelGrinder;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

public class RenderGrinder extends RotaryTERenderer
{

	private ModelGrinder GrinderModel = new ModelGrinder();
	//private ModelGrinderV GrinderModelV = new ModelGrinderV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityGrinderAt(TileEntityGrinder tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelGrinder var14;
		var14 = GrinderModel;
		//ModelGrinderV var15;
		//var14 = this.GrinderModelV;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/grindertex.png");

		this.setupGL(tile, par2, par4, par6);

		//GL11.glDisable(GL11.GL_LIGHTING);
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
		//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
		float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
		// if (tile.getBlockMetadata() < 4)


		var14.renderAll(tile, null, -tile.phi, 0);
		// else
		//var15.renderAll(tile, );
		//GL11.glEnable(GL11.GL_LIGHTING);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityGrinderAt((TileEntityGrinder)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "grindertex.png";
	}
}
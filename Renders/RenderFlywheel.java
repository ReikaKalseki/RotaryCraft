/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelFlywheel;
import Reika.RotaryCraft.Registry.Flywheels;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;

public class RenderFlywheel extends RotaryTERenderer
{

	private ModelFlywheel FlywheelModel = new ModelFlywheel();

	private static String getFlywheelTextureName(Flywheels f) {
		return f.name().toLowerCase(Locale.ENGLISH)+".png";
	}

	@Override
	protected String getTextureSubfolder() {
		return "Transmission/Flywheel/";
	}

	public void renderTileEntityFlywheelAt(TileEntityFlywheel tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelFlywheel var14;
		var14 = FlywheelModel;
		this.bindTextureByName(this.getTextureFolder()+this.getFlywheelTextureName(tile.getTypeOrdinal()));

		this.setupGL(tile, par2, par4, par6);

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
		var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(tile.failed), -tile.phi, 0);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8) {
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityFlywheelAt((TileEntityFlywheel)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityFlywheel tile = (TileEntityFlywheel)te;
		return this.getFlywheelTextureName(tile.getTypeOrdinal());
	}
}

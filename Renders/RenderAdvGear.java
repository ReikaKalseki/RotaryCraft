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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Models.ModelCVT;
import Reika.RotaryCraft.Models.Animated.ModelCoil;
import Reika.RotaryCraft.Models.Animated.ModelHighGear;
import Reika.RotaryCraft.Models.Animated.ModelWorm;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

public class RenderAdvGear extends RotaryTERenderer
{

	private ModelWorm wormModel = new ModelWorm();
	private ModelCVT cvtModel = new ModelCVT();
	private ModelCoil coilModel = new ModelCoil();
	private ModelHighGear highGearModel = new ModelHighGear();
	private int itemMetadata = 0;

	@Override
	protected String getTextureSubfolder() {
		return "Transmission/";
	}

	public void renderTileEntityAdvancedGearAt(TileEntityAdvancedGear tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelWorm var14 = wormModel;
		ModelCVT var15 = cvtModel;
		ModelCoil var16 = coilModel;
		ModelHighGear var17 = highGearModel;

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;	 //used to rotate the model about metadata

		if (tile.isInWorld()) {

			switch(tile.getBlockMetadata()%4) {
				case 0:
					var11 = 0;
					break;
				case 1:
					var11 = 180;
					break;
				case 2:
					var11 = 90;
					break;
				case 3:
					var11 = 270;
					break;
			}

			GL11.glRotatef((float)var11+180, 0.0F, 1.0F, 0.0F);

		}
		else {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.itemMetadata));
			GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
			switch(itemMetadata) {
				case 1:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/Shaft/shafttex.png");
					var14.renderAll(tile, null);
					break;
				case 2:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/cvttex.png");
					var15.renderAll(tile, null);
					break;
				case 3:
					if (tile.isBedrockCoil())
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/coiltex_bed.png");
					else
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/coiltex.png");
					var16.renderAll(tile, null);
					break;
				case 4:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/highgeartex.png");
					var17.renderAll(tile, null);
					break;
			}
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return;
		}

		float var13;
		switch (tile.getGearType()) {
			case WORM:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/Shaft/shafttex.png");
				var14.renderAll(tile, null, tile.phi);
				break;
			case CVT:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/cvttex.png");
				var15.renderAll(tile, null, tile.phi);
				if (tile.isInWorld()) {
					GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
					GL11.glDisable(GL11.GL_LIGHTING);
					ReikaRenderHelper.disableEntityLighting();
					FontRenderer fr = this.getFontRenderer();
					float var10 = 0.6666667F*0.8F;
					GL11.glScalef(var10, -var10, -var10);
					float var112 = 0.016666668F * var10;
					GL11.glTranslatef(0.0F, 0.61875F * var10, 0.20625F * var10);
					GL11.glRotated(-20, 1, 0, 0);
					GL11.glTranslatef(-0.175F, -0.545F, -0.19F); //was 0.1X
					GL11.glScalef(var112, -var112, var112);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDepthMask(false);
					GL11.glTranslatef(5, -48, 37);
					GL11.glScalef(2, 2, 2);
					String var15b;

					if (tile.getTicksExisted()/80%2 == 0) {
						var15b = RotaryAux.formatPower(tile.power);
						fr.drawString(var15b, -18, 70, 0xffffff);

						var15b = RotaryAux.formatTorque(tile.torque);
						fr.drawString(var15b, -18, 82, 0xffffff);

						var15b = RotaryAux.formatSpeed(tile.omega);
						fr.drawString(var15b, -18, 94, 0xffffff);
					}
					else {
						GL11.glScalef(2, 2, 2);
						GL11.glTranslatef(0.075F, 0.25F, 0.1F);
						int ratio = tile.getCVTRatio();
						if (ratio > 0) {
							var15b = "1:"+ratio;
						}
						else {
							var15b = -ratio+":1";
						}
						while(var15b.length() < 5) {
							var15b = " "+var15b;
						}
						fr.drawString(var15b, -10, 39, 0xffffff);
					}

					GL11.glPopAttrib();
				}
				break;
			case COIL:
				if (tile.isBedrockCoil())
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/coiltex_bed.png");
				else
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/coiltex.png");
				var16.renderAll(tile, null, tile.phi);
				break;
			case HIGH:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/highgeartex.png");
				var17.renderAll(tile, null, tile.phi);
				break;
		}

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (par8 <= -999F) {
			itemMetadata = (int)-par8/1000;
			par8 = 0F;
		}
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityAdvancedGearAt((TileEntityAdvancedGear)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher t) {
		if (!(t instanceof RenderFetcher))
			return "";
		TileEntityIOMachine te = (TileEntityIOMachine)t;
		if (te.isInWorld()) {
			if (te.getBlockMetadata() < 4)
				return "shafttex.png";
			else if (te.getBlockMetadata() < 8)
				return "cvttex.png";
			else if (te.getBlockMetadata() < 12)
				return "coiltex.png";
			else
				return "highgeartex.png";
		}
		else {
			if (itemMetadata == 1)
				return "shafttex.png";
			if (itemMetadata == 2)
				return "cvttex.png";
			if (itemMetadata == 3)
				return "coiltex.png";
			if (itemMetadata == 4)
				return "highgeartex.png";
			return "";
		}
	}
}

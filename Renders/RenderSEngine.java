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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.MultiModel;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelAC;
import Reika.RotaryCraft.Models.ModelCombustion;
import Reika.RotaryCraft.Models.ModelDC;
import Reika.RotaryCraft.Models.ModelHydro;
import Reika.RotaryCraft.Models.ModelJet;
import Reika.RotaryCraft.Models.ModelMicroTurbine;
import Reika.RotaryCraft.Models.ModelPerformance;
import Reika.RotaryCraft.Models.ModelSteam;
import Reika.RotaryCraft.Models.ModelWind;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

@SideOnly(Side.CLIENT)
public class RenderSEngine extends RotaryTERenderer implements MultiModel
{

	private ModelDC DCModel = new ModelDC();
	private ModelSteam SteamModel = new ModelSteam();
	private ModelCombustion CombModel = new ModelCombustion();
	private ModelAC ACModel = new ModelAC();
	private ModelPerformance PerfModel = new ModelPerformance();
	private ModelMicroTurbine MicroModel = new ModelMicroTurbine();
	private ModelJet JetModel = new ModelJet();
	private ModelHydro HydroModel = new ModelHydro();
	private ModelWind WindModel = new ModelWind();
	private int itemMetadata = 0;

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityEngineAt(TileEntityEngine tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
		{
			var9 = 0;
		}
		else
		{
			//
			var9 = tile.getBlockMetadata();

			//
			{
				//((BlockEngineBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelDC var14 = DCModel;
			ModelSteam var15 = SteamModel;
			ModelCombustion var16 = CombModel;
			ModelAC var17 = ACModel;
			ModelPerformance var18 = PerfModel;
			ModelMicroTurbine var19 = MicroModel;
			ModelJet var20 = JetModel;
			ModelHydro var21 = HydroModel;
			ModelWind var22 = WindModel;

			if (tile.isInWorld()) {
				if (tile.type == null)
					tile.type = EnumEngineType.DC;
				switch(tile.type) {
					case DC:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/dc.png");
						break;
					case WIND:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/windtex.png");
						break;
					case STEAM:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/steamtex.png");
						break;
					case GAS:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/combtex.png");
						break;
					case AC:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/actex.png");
						break;
					case SPORT:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/perftex.png");
						break;
					case HYDRO:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/hydrotex.png");
						break;
					case MICRO:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/microtex.png");
						break;
					case JET:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/jettex.png");
						break;
				}
			}
			else {
				switch(itemMetadata) {
					case 0:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/dc.png");
						break;
					case 1:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/windtex.png");
						break;
					case 2:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/steamtex.png");
						break;
					case 3:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/combtex.png");
						break;
					case 4:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/actex.png");
						break;
					case 5:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/perftex.png");
						break;
					case 6:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/hydrotex.png");
						break;
					case 7:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/microtex.png");
						break;
					case 8:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/jettex.png");
						break;
				}
			}

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
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
						var11 = 90;
						break;
					case 3:
						var11 = 270;
						break;
				}

				if (tile.type.isJetFueled())
					var11 += 90;

				GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

			}
			else {
				double s; double d;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.itemMetadata));
				GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
				switch(itemMetadata) {
					case 1:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/dc.png");
						var14.renderAll(null, 0);
						break;
					case 2:
						GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
						s = 0.7;
						d = 0.375;
						GL11.glScaled(s, s, s);
						double d2 = 0.2;
						GL11.glTranslated(0, d, 0);
						GL11.glTranslated(d2, 0, 0);
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/windtex.png");
						var22.renderAll(null, 0);
						GL11.glTranslated(0, -d, 0);
						GL11.glTranslated(-d2, 0, 0);
						GL11.glScaled(1D/s, 1D/s, 1D/s);
						GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
						break;
					case 3:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/steamtex.png");
						var15.renderAll(null, 0);
						break;
					case 4:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/combtex.png");
						var16.renderAll(null, 0);
						break;
					case 5:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/actex.png");
						var17.renderAll(null, 0);
						break;
					case 6:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/perftex.png");
						var18.renderAll(null, Float.MIN_NORMAL);
						break;
					case 7:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/hydrotex.png");
						s = 0.7;
						d = 0.375;
						GL11.glTranslated(0, d, 0);
						GL11.glScaled(s, s, s);
						var21.renderAll(null, 0);
						GL11.glScaled(1D/s, 1D/s, 1D/s);
						GL11.glTranslated(0, -d, 0);
						break;
					case 8:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/microtex.png");
						GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
						var19.renderAll(null, 0);
						break;
					case 9:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/jettex.png");
						GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
						var20.renderAll(null, 0);
						break;
				}
				if (tile.isInWorld())
					GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				//ReikaJavaLibrary.pConsole(this.itemMetadata);
				return;
			}
			//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			if (tile.type == null)
				tile.type = EnumEngineType.DC;
			switch (tile.type) {
				case DC:
					var14.renderAll(null, -tile.phi);
					break;
				case WIND:
					GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
					var22.renderAll(null, -tile.phi);
					GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
					break;
				case STEAM:
					var15.renderAll(null, -tile.phi);
					break;
				case GAS:
					var16.renderAll(null, -tile.phi);
					break;
				case AC:
					var17.renderAll(null, -tile.phi);
					break;
				case SPORT:
					var18.renderAll(null, -tile.phi);
					break;
				case HYDRO:
					var21.renderAll(null, -tile.phi);
					break;
				case MICRO:
					var19.renderAll(null, -tile.phi);
					break;
				case JET:
					var20.renderAll(null, -tile.phi);
					break;
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (par8 <= -999F) {
			itemMetadata = (int)-par8/1000;
			par8 = 0F;
		}
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityEngineAt((TileEntityEngine)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		if (te == null)
			return null;
		TileEntityEngine tile = (TileEntityEngine)te;
		if (tile.isInWorld()) {
			if (tile.type == null)
				tile.type = EnumEngineType.DC;
			switch(tile.type) {
				case DC:
					return "dc.png";
				case WIND:
					return "windtex.png";
				case STEAM:
					return "steamtex.png";
				case GAS:
					return "combtex.png";
				case AC:
					return "actex.png";
				case SPORT:
					return "perftex.png";
				case HYDRO:
					return "hydrotex.png";
				case MICRO:
					return "microtex.png";
				case JET:
					return "jettex.png";
			}
		}
		else {
			switch(itemMetadata) {
				case 0:
					return "dc.png";
				case 1:
					return "windtex.png";
				case 2:
					return "steamtex.png";
				case 3:
					return "combtex.png";
				case 4:
					return "actex.png";
				case 5:
					return "perftex.png";
				case 6:
					return "hydrotex.png";
				case 7:
					return "microtex.png";
				case 8:
					return "jettex.png";
			}
		}
		return null;
	}
}

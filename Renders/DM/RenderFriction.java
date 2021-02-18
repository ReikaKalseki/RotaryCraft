/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DM;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.HeatRippleRenderer;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelFriction;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFurnaceHeater;

public class RenderFriction extends RotaryTERenderer
{

	private ModelFriction FrictionModel = new ModelFriction();

	@Override
	protected String getTextureSubfolder() {
		return "Friction/";
	}

	public void renderTileEntityFurnaceHeaterAt(TileEntityFurnaceHeater tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 3;
		else
			var9 = tile.getBlockMetadata();

		ModelFriction var14;
		var14 = FrictionModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Friction/frictiontex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;
		float var13;
		switch(var9) {
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

		if (tile.isInWorld()) {
			if (tile.getTemperature() >= 1600) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Friction/frictiontex-5.png");
			}
			else if (tile.getTemperature() >= 1300) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Friction/frictiontex-4.png");
			}
			else if (tile.getTemperature() >= 1000) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Friction/frictiontex-3.png");
			}
			else if (tile.getTemperature() >= 700) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Friction/frictiontex-2.png");
			}
			else if (tile.getTemperature() >= 400) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Friction/frictiontex-1.png");
			}
		}

		GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
		var14.renderAll(tile, null, -tile.phi);
		if (tile.isInWorld()) {
			GL11.glTranslated(0, 1, -0.5);
			float f = tile.getTemperature()*0.75F/tile.MAXTEMP;
			if (tile.getReadDirection() != null) {
				double dx = tile.getReadDirection().getOpposite().offsetX*0.5;
				double dz = tile.getReadDirection().getOpposite().offsetZ*0.5;
				HeatRippleRenderer.instance.addHeatRippleEffectIfLOS(tile, tile.xCoord+0.5+dx, tile.yCoord+0.5, tile.zCoord+0.5+dz, f, 0.08F, 35F, 0);
			}
		}

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityFurnaceHeaterAt((TileEntityFurnaceHeater)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderHotSide((TileEntityFurnaceHeater)tile, par2, par4, par6);
		}
	}

	private void renderHotSide(TileEntityFurnaceHeater tile, double par2, double par4, double par6) {
		if (!tile.hasFurnace())
			return;
		Tessellator v5 = Tessellator.instance;
		if (tile.getTemperature() > 1000)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/Misc/hotfurnace_2.png");
		else if (tile.getTemperature() > 750)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/Misc/hotfurnace_1.png");
		else if (tile.getTemperature() > 500)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/Misc/hotfurnace_0.png");
		else
			return;
		v5.startDrawingQuads();
		switch(tile.getBlockMetadata()) {
			case 0:
				v5.addVertexWithUV(par2+0.001, par4, par6+1, 0, 1);
				v5.addVertexWithUV(par2+0.001, par4, par6, 1, 1);
				v5.addVertexWithUV(par2+0.001, par4+1, par6, 1, 0);
				v5.addVertexWithUV(par2+0.001, par4+1, par6+1, 0, 0);
				break;
			case 1:
				v5.addVertexWithUV(par2+0.999, par4, par6, 0, 1);
				v5.addVertexWithUV(par2+0.999, par4, par6+1, 1, 1);
				v5.addVertexWithUV(par2+0.999, par4+1, par6+1, 1, 0);
				v5.addVertexWithUV(par2+0.999, par4+1, par6, 0, 0);
				break;
			case 2:
				v5.addVertexWithUV(par2, par4, par6+0.001, 0, 1);
				v5.addVertexWithUV(par2+1, par4, par6+0.001, 1, 1);
				v5.addVertexWithUV(par2+1, par4+1, par6+0.001, 1, 0);
				v5.addVertexWithUV(par2, par4+1, par6+0.001, 0, 0);
				break;
			case 3:
				v5.addVertexWithUV(par2, par4, par6+0.999, 0, 0);
				v5.addVertexWithUV(par2+1, par4, par6+0.999, 1, 0);
				v5.addVertexWithUV(par2+1, par4+1, par6+0.999, 1, 1);
				v5.addVertexWithUV(par2, par4+1, par6+0.999, 0, 1);
				break;
		}
		v5.draw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "frictiontex.png";
	}
}

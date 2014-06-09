/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DM;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;

public class RenderPump extends RotaryTERenderer
{

	private ModelPump PumpModel = new ModelPump();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityPumpAt(TileEntityPump tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelPump var14;
		var14 = PumpModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pumptex.png");

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
				var11 = 90;
				break;
			case 1:
				var11 = 0;
				break;
			}

			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

		}

		float var13;
		Object[] pars = new Object[3];
		pars[0] = tile.getLevel() > 0 && MinecraftForgeClient.getRenderPass() == 1;
		pars[1] = (tile.shouldRenderInPass(0) && MinecraftForgeClient.getRenderPass() == 0) || !tile.isInWorld();
		pars[2] = tile.damage > 400;
		var14.renderAll(tile, ReikaJavaLibrary.makeListFromArray(pars), -tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile)) {
			this.renderTileEntityPumpAt((TileEntityPump)tile, par2, par4, par6, par8);
		}
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			this.renderLiquid(tile, par2, par4, par6);
		}
	}

	private void renderLiquid(TileEntity tile, double par2, double par4, double par6) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor3f(1, 1, 1);
		GL11.glTranslated(par2, par4, par6);
		TileEntityPump tr = (TileEntityPump)tile;
		if (tr.getLevel() > 0 && tr.isInWorld()) {
			Fluid f = tr.getLiquid();
			ReikaLiquidRenderer.bindFluidTexture(f);
			Icon ico = f.getIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double inset = 0.125;
			double offset = inset*(du-u);
			u += offset;
			v += offset;
			du -= offset;
			dv -= offset;
			double h = 0.625;
			Tessellator v5 = Tessellator.instance;
			if (f.getLuminosity() > 0)
				ReikaRenderHelper.disableLighting();
			v5.startDrawingQuads();
			v5.setNormal(0, 1, 0);
			v5.addVertexWithUV(inset, h, inset, u, v);
			v5.addVertexWithUV(1-inset, h, inset, du, v);
			v5.addVertexWithUV(1-inset, h, 1-inset, du, dv);
			v5.addVertexWithUV(inset, h, 1-inset, u, dv);
			v5.draw();
			ReikaRenderHelper.enableLighting();
		}
		GL11.glTranslated(-par2, -par4, -par6);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "pumptex.png";
	}
}

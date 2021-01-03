/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.MI;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelObsidian;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;

public class RenderObsidian extends RotaryTERenderer
{
	private ModelObsidian ObsidianModel = new ModelObsidian();
	//private ModelObsidianV ObsidianModelV = new ModelObsidianV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityObsidianAt(TileEntityObsidianMaker tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelObsidian var14;
		var14 = ObsidianModel;
		//ModelObsidianV var15;
		//var14 = this.ObsidianModelV;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F+tile.overred, 1.0F+tile.overgreen, 1.0F+tile.overblue, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		float var13;

		var14.renderAll(tile, null);

		if (tile.isInWorld() || MinecraftForgeClient.getRenderPass() == 1)
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityObsidianAt((TileEntityObsidianMaker)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			this.renderInternalTexture((TileEntityObsidianMaker)tile, par2, par4, par6);
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	private void renderInternalTexture(TileEntityObsidianMaker te, double par2, double par4, double par6) {

		int i = 0;
		double h = 0;
		double maxh = 0.6875;
		if (te.getWater() > 0)
			i += 1;
		if (te.getLava() > 0)
			i += 2;
		float u = 0;
		float v = 0;
		float du = 0;
		float dv = 0;

		switch(i) {
			case 0:
				return;
			case 1: {
				ReikaTextureHelper.bindTerrainTexture();
				IIcon ico = FluidRegistry.WATER.getIcon();
				u = ico.getMinU();
				v = ico.getMinV();
				du = ico.getMaxU();
				dv = ico.getMaxV();
				h = maxh*te.getWater()/te.CAPACITY;
				break;
			}
			case 2: {
				ReikaTextureHelper.bindTerrainTexture();
				IIcon ico = FluidRegistry.LAVA.getIcon();
				u = ico.getMinU();
				v = ico.getMinV();
				du = ico.getMaxU();
				dv = ico.getMaxV();
				h = maxh*te.getLava()/te.CAPACITY;
				break;
			}
			case 3:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantex.png");
				u = 78/128F;
				v = 0;
				du = u+14/128F;
				dv = v+14/128F;
				h = maxh;
				break;
		}

		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushMatrix();
		GL11.glTranslated(par2, par4, par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		if ((i&2) != 0) {
			ReikaRenderHelper.disableEntityLighting();
		}
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();

		Tessellator v5 = Tessellator.instance;

		v5.startDrawingQuads();
		v5.setBrightness(te.getBlockType().getMixedBrightnessForBlock(te.worldObj, te.xCoord, te.yCoord, te.zCoord));
		if ((i&2) != 0)
			v5.setBrightness(240);
		v5.setColorOpaque_I(0xffffff);

		v5.addVertexWithUV(0, h, 1, u, dv);
		v5.addVertexWithUV(1, h, 1, du, dv);
		v5.addVertexWithUV(1, h, 0, du, v);
		v5.addVertexWithUV(0, h, 0, u, v);

		v5.draw();

		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "obsidiantex.png";
	}
}

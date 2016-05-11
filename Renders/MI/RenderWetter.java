/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.MI;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelWetter;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityWetter;

public class RenderWetter extends RotaryTERenderer {

	private final ModelWetter wetterModel = new ModelWetter();

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "wettertex.png";
	}

	public void renderTileEntityWetterAt(TileEntityWetter tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelWetter var14;
		var14 = wetterModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/wettertex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		var14.renderAll(tile, null, tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile)) {
			this.renderTileEntityWetterAt((TileEntityWetter)tile, par2, par4, par6, par8);
			this.renderItem((TileEntityWetter)tile, par2, par4, par6, par8);
		}
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderLiquid((TileEntityWetter)tile, par2, par4, par6, par8);
		}
	}

	private void renderItem(TileEntityWetter te, double par2, double par4, double par6, float par8) {
		EntityItem ei = te.getItem();
		if (ei != null) {
			ei.age = 0;
			ei.hoverStart = 0;
			ei.setAngles(0, 0);
			GL11.glPushMatrix();
			double ang = 0;//((te.getTicksExisted()+par8)*3D)%360;
			double dy = -0.3125;//0.0625*Math.sin(Math.toRadians(ang*2));
			GL11.glTranslated(0.5, 0.625+dy, 0.5);
			GL11.glTranslated(par2, par4, par6);
			GL11.glRotated(ang, 0, 1, 0);
			double s = 3;
			GL11.glScaled(s, s, s);
			GL11.glTranslated(-par2, -par4, -par6);

			Render r = RenderManager.instance.getEntityClassRenderObject(EntityItem.class);
			r.doRender(ei, par2, par4, par6, 0, 0);

			GL11.glPopMatrix();
		}
	}

	private void renderLiquid(TileEntityWetter te, double par2, double par4, double par6, float par8) {
		if (te.getContainedFluid() != null) {

			FluidStack liquid = new FluidStack(te.getContainedFluid(), 1);
			int amount = te.getLevel();
			if (amount == 0)
				return;
			if (amount > te.getCapacity())
				amount = te.getCapacity();

			int[] displayList = ReikaLiquidRenderer.getGLLists(liquid, te.worldObj, false);

			if (displayList == null) {
				return;
			}

			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			BlendMode.DEFAULT.apply();

			ReikaLiquidRenderer.bindFluidTexture(te.getContainedFluid());
			ReikaLiquidRenderer.setFluidColor(liquid);

			GL11.glTranslated(par2, par4, par6);

			GL11.glTranslated(0, 0.125, 0);

			GL11.glTranslated(0, 0.001, 0);
			GL11.glScaled(1, 0.92, 1);
			GL11.glScaled(0.99, 0.875, 0.99);

			GL11.glCallList(displayList[(int)(amount / ((double)te.getCapacity()) * (ReikaLiquidRenderer.LEVELS - 1))]);

			GL11.glPopAttrib();
			GL11.glPopMatrix();


			/*
			GL11.glPushMatrix();
			GL11.glTranslated(par2, par4, par6);
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			BlendMode.DEFAULT.apply();

			Tessellator v5 = Tessellator.instance;
			IIcon ico = te.getContainedFluid().getStillIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = te.getLevel()/(double)te.getCapacity();
			GL11.glColor4f(1, 1, 1, 1);
			ReikaTextureHelper.bindTerrainTexture();
			v5.startDrawingQuads();
			v5.setColorOpaque_I(0xffffff);
			int b = te.getContainedFluid().getLuminosity() >= 12 ? 240 : te.getBlockType().getMixedBrightnessForBlock(te.worldObj, te.xCoord, te.yCoord, te.zCoord);
			v5.setBrightness(b);
			v5.addVertexWithUV(0, h, 1, u, dv);
			v5.addVertexWithUV(1, h, 1, du, dv);
			v5.addVertexWithUV(1, h, 0, du, v);
			v5.addVertexWithUV(0, h, 0, u, v);
			v5.draw();

			GL11.glPopAttrib();
			GL11.glPopMatrix();*/
		}
	}

}

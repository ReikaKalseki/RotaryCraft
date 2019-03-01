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
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Effects.EntityFluidFX;
import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelSpillway;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySpillway;

public class RenderSpillway extends RotaryTERenderer
{

	private ModelSpillway SpillwayModel = new ModelSpillway();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntitySpillwayAt(TileEntitySpillway tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelSpillway var14;

		var14 = SpillwayModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/spillwaytex.png");

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
					var11 = 180;
					break;
				case 1:
					var11 = 0;
					break;
				case 2:
					var11 = 90;
					break;
				case 3:
					var11 = 270;
					break;
			}

			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

		}

		float var13;

		var14.renderAll(tile, null);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntitySpillwayAt((TileEntitySpillway)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			GL11.glPushMatrix();
			GL11.glTranslated(par2, par4, par6);
			this.renderLiquid((TileEntitySpillway)tile, par8);
			if (((TileEntitySpillway)tile).isActive())
				this.renderParticles((TileEntitySpillway)tile, par8);
			GL11.glPopMatrix();
		}
	}

	private void renderParticles(TileEntitySpillway te, float par8) {
		if (!Minecraft.getMinecraft().isGamePaused() && te.getRandom().nextInt(2) == 0) {
			ForgeDirection dir = te.getDrainSide();
			double v = 0.03125;
			double px = te.xCoord+0.5+dir.offsetX*0.5;
			double pz = te.zCoord+0.5+dir.offsetZ*0.5;
			if (dir.offsetX != 0)
				pz = ReikaRandomHelper.getRandomPlusMinus(pz, 0.4);
			if (dir.offsetZ != 0)
				px = ReikaRandomHelper.getRandomPlusMinus(px, 0.4);
			EntityFluidFX fx = new EntityFluidFX(te.worldObj, px, te.yCoord+1, pz, -v*dir.offsetX, 0, -v*dir.offsetZ, FluidRegistry.WATER);
			fx.setGravity(0.0625F).setScale(0.5F).setLife(30-te.getLevel()*15/8000);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
		Tessellator v5 = Tessellator.instance;
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		BlendMode.DEFAULT.apply();
		boolean flip = false;

		GL11.glPushMatrix();
		int r = 0;
		switch(te.getBlockMetadata()) {
			case 0:
				r = 180;
				GL11.glTranslated(1, 0, 0);
				break;
			case 1:
				r = 0;
				GL11.glTranslated(0, 0, 1);
				break;
			case 2:
				r = 90;
				GL11.glTranslated(1, 0, 1);
				flip = true;
				break;
			case 3:
				r = 270;
				flip = true;
				break;
		}
		GL11.glRotatef(r+90, 0.0F, 1.0F, 0.0F);

		ReikaTextureHelper.bindTerrainTexture();
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.setColorOpaque_I(0xffffff);
		IIcon ico = FluidRegistry.WATER.getFlowingIcon();
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		double z0 = 0.125;
		double z1 = 6.2/16D;
		float v0 = ico.getInterpolatedV(z0*16);
		float v1 = ico.getInterpolatedV(z1*16);
		float dv = ico.getMaxV();
		double h0 = 1-0.03125+0.005;
		double h1 = 0.78+0.005;
		double h2 = 0.0625+0.005;
		if (flip) {
			v5.addVertexWithUV(0, h0, 1-z0, u, v0);
			v5.addVertexWithUV(1, h0, 1-z0, du, v0);
			v5.addVertexWithUV(1, h0, 1, du, v);
			v5.addVertexWithUV(0, h0, 1, u, v);

			v5.addVertexWithUV(0, h1, 1-z1, u, v1);
			v5.addVertexWithUV(1, h1, 1-z1, du, v1);
			v5.addVertexWithUV(1, h0, 1-z0, du, v0);
			v5.addVertexWithUV(0, h0, 1-z0, u, v0);

			v5.addVertexWithUV(0, h1, 1-z1, u, v1);
			v5.addVertexWithUV(1, h1, 1-z1, du, v1);
			v5.addVertexWithUV(1, h2, 1-z1, du, dv);
			v5.addVertexWithUV(0, h2, 1-z1, u, dv);
		}
		else {
			v5.addVertexWithUV(0, h0, z0, u, v0);
			v5.addVertexWithUV(1, h0, z0, du, v0);
			v5.addVertexWithUV(1, h0, 0, du, v);
			v5.addVertexWithUV(0, h0, 0, u, v);

			v5.addVertexWithUV(0, h1, z1, u, v1);
			v5.addVertexWithUV(1, h1, z1, du, v1);
			v5.addVertexWithUV(1, h0, z0, du, v0);
			v5.addVertexWithUV(0, h0, z0, u, v0);

			v5.addVertexWithUV(0, h1, z1, u, v1);
			v5.addVertexWithUV(1, h1, z1, du, v1);
			v5.addVertexWithUV(1, h2, z1, du, dv);
			v5.addVertexWithUV(0, h2, z1, u, dv);
		}
		v5.draw();

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	private void renderLiquid(TileEntitySpillway te, float par8) {
		int amt = te.getLevel();
		if (amt <= 0)
			return;
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();
		double h = 0.03125+11.5/16D*amt/te.CAPACITY;
		Fluid f = FluidRegistry.WATER;

		ReikaLiquidRenderer.bindFluidTexture(f);
		IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.setColorOpaque_I(0xffffff);
		v5.addVertexWithUV(0, h, 1, u, dv);
		v5.addVertexWithUV(1, h, 1, du, dv);
		v5.addVertexWithUV(1, h, 0, du, v);
		v5.addVertexWithUV(0, h, 0, u, v);
		v5.draw();
		GL11.glPopAttrib();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "spillwaytex.png";
	}
}

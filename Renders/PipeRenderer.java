/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.Auxiliary.Interfaces.RenderableDuct;
import Reika.RotaryCraft.Base.RotaryTERenderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PipeRenderer extends RotaryTERenderer {

	protected void renderLiquid(RenderableDuct tile, double par2, double par4, double par6, ForgeDirection dir) {
		Fluid f = tile.getFluidType();
		if (f == null)
			return;

		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;
		double in = 0.5+size-0.01;
		double in2 = 0.5-size+0.01;
		double dd2 = in-in2;

		IIcon ico = tile.getFluidType().getIcon();
		ReikaLiquidRenderer.bindFluidTexture(f);
		if (f.getLuminosity() > 0)
			ReikaRenderHelper.disableLighting();
		float u = ico.getMinU();
		float v = ico.getMinV();
		float u2 = ico.getMaxU();
		float v2 = ico.getMaxV();
		double du = dd2*(u2-u)/4D;

		GL11.glTranslated(par2, par4, par6);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor3f(1, 1, 1);

		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		v5.setNormal(dir.offsetX, dir.offsetY, dir.offsetZ);
		//this.faceBrightness(ForgeDirection.DOWN, v5);
		if (!tile.isConnectionValidForSide(dir)) {
			switch(dir) {
			case UP:
				v5.addVertexWithUV(in2, in, in, u, v2);
				v5.addVertexWithUV(in, in, in, u2, v2);
				v5.addVertexWithUV(in, in, in2, u2, v);
				v5.addVertexWithUV(in2, in, in2, u, v);
				break;
			case DOWN:
				v5.addVertexWithUV(in2, in2, in2, u, v);
				v5.addVertexWithUV(in, in2, in2, u2, v);
				v5.addVertexWithUV(in, in2, in, u2, v2);
				v5.addVertexWithUV(in2, in2, in, u, v2);
				break;
			case SOUTH:
				v5.addVertexWithUV(in, in, in, u, v);
				v5.addVertexWithUV(in2, in, in, u2, v);
				v5.addVertexWithUV(in2, in2, in, u2, v2);
				v5.addVertexWithUV(in, in2, in, u, v2);
				break;
			case NORTH:
				v5.addVertexWithUV(in, in2, in2, u, v2);
				v5.addVertexWithUV(in2, in2, in2, u2, v2);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in, in, in2, u, v);
				break;
			case EAST:
				v5.addVertexWithUV(in, in2, in, u, v2);
				v5.addVertexWithUV(in, in2, in2, u2, v2);
				v5.addVertexWithUV(in, in, in2, u2, v);
				v5.addVertexWithUV(in, in, in, u, v);
				break;
			case WEST:
				v5.addVertexWithUV(in2, in, in, u, v);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in2, in2, in2, u2, v2);
				v5.addVertexWithUV(in2, in2, in, u, v2);
			default:
				break;
			}
		}
		else { //is connected on side
			switch(dir) {
			case DOWN:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, in2, in, u, v);
				v5.addVertexWithUV(in2, in2, in2, u2, v);
				v5.addVertexWithUV(in2, 0, in2, u2, v+du);
				v5.addVertexWithUV(in2, 0, in, u, v+du);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, 0, in, u, v+du);
				v5.addVertexWithUV(in, 0, in2, u2, v+du);
				v5.addVertexWithUV(in, in2, in2, u2, v);
				v5.addVertexWithUV(in, in2, in, u, v);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(in, 0, in2, u, v+du);
				v5.addVertexWithUV(in2, 0, in2, u2, v+du);
				v5.addVertexWithUV(in2, in2, in2, u2, v);
				v5.addVertexWithUV(in, in2, in2, u, v);

				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(in, in2, in, u, v);
				v5.addVertexWithUV(in2, in2, in, u2, v);
				v5.addVertexWithUV(in2, 0, in, u2, v+du);
				v5.addVertexWithUV(in, 0, in, u, v+du);
				break;
			case UP:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, 1, in, u, v+du);
				v5.addVertexWithUV(in2, 1, in2, u2, v+du);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in2, in, in, u, v);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, in, in, u, v);
				v5.addVertexWithUV(in, in, in2, u2, v);
				v5.addVertexWithUV(in, 1, in2, u2, v+du);
				v5.addVertexWithUV(in, 1, in, u, v+du);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(in, in, in2, u, v);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in2, 1, in2, u2, v+du);
				v5.addVertexWithUV(in, 1, in2, u, v+du);

				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(in, 1, in, u, v+du);
				v5.addVertexWithUV(in2, 1, in, u2, v+du);
				v5.addVertexWithUV(in2, in, in, u2, v);
				v5.addVertexWithUV(in, in, in, u, v);
				break;
			case NORTH:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, in2, 0, u, v2);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(in2, in, in2, u+du, v);
				v5.addVertexWithUV(in2, in, 0, u, v);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, in, 0, u, v);
				v5.addVertexWithUV(in, in, in2, u+du, v);
				v5.addVertexWithUV(in, in2, in2, u+du, v2);
				v5.addVertexWithUV(in, in2, 0, u, v2);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(in2, in, 0, u, v2);
				v5.addVertexWithUV(in2, in, in2, u+du, v2);
				v5.addVertexWithUV(in, in, in2, u+du, v);
				v5.addVertexWithUV(in, in, 0, u, v);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(in, in2, 0, u, v);
				v5.addVertexWithUV(in, in2, in2, u+du, v);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(in2, in2, 0, u, v2);
				break;
			case SOUTH:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, in, 1, u, v);
				v5.addVertexWithUV(in2, in, in, u+du, v);
				v5.addVertexWithUV(in2, in2, in, u+du, v2);
				v5.addVertexWithUV(in2, in2, 1, u, v2);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, in2, 1, u, v2);
				v5.addVertexWithUV(in, in2, in, u+du, v2);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(in, in, 1, u, v);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(in, in, 1, u, v);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(in2, in, in, u+du, v2);
				v5.addVertexWithUV(in2, in, 1, u, v2);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(in2, in2, 1, u, v2);
				v5.addVertexWithUV(in2, in2, in, u+du, v2);
				v5.addVertexWithUV(in, in2, in, u+du, v);
				v5.addVertexWithUV(in, in2, 1, u, v);
				break;
			case EAST:
				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(1, in, in, u, v);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(in, in2, in, u+du, v2);
				v5.addVertexWithUV(1, in2, in, u, v2);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(1, in2, in2, u, v2);
				v5.addVertexWithUV(in, in2, in2, u+du, v2);
				v5.addVertexWithUV(in, in, in2, u+du, v);
				v5.addVertexWithUV(1, in, in2, u, v);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(1, in, in2, u, v2);
				v5.addVertexWithUV(in, in, in2, u+du, v2);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(1, in, in, u, v);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(1, in2, in, u, v);
				v5.addVertexWithUV(in, in2, in, u+du, v);
				v5.addVertexWithUV(in, in2, in2, u+du, v2);
				v5.addVertexWithUV(1, in2, in2, u, v2);
				break;
			case WEST:
				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(0, in2, in, u, v2);
				v5.addVertexWithUV(in2, in2, in, u+du, v2);
				v5.addVertexWithUV(in2, in, in, u+du, v);
				v5.addVertexWithUV(0, in, in, u, v);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(0, in, in2, u, v);
				v5.addVertexWithUV(in2, in, in2, u+du, v);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(0, in2, in2, u, v2);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(0, in, in, u, v);
				v5.addVertexWithUV(in2, in, in, u+du, v);
				v5.addVertexWithUV(in2, in, in2, u+du, v2);
				v5.addVertexWithUV(0, in, in2, u, v2);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(0, in2, in2, u, v2);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(in2, in2, in, u+du, v);
				v5.addVertexWithUV(0, in2, in, u, v);
				break;
			default:
				break;
			}

		}
		if (tile.isConnectedToNonSelf(dir)) {
			v5.setNormal(dir.offsetX, dir.offsetY, dir.offsetZ);
			switch(dir) {
			case UP:
				v5.addVertexWithUV(in2, 0.99, in, u, v2);
				v5.addVertexWithUV(in, 0.99, in, u2, v2);
				v5.addVertexWithUV(in, 0.99, in2, u2, v);
				v5.addVertexWithUV(in2, 0.99, in2, u, v);
				break;
			case DOWN:
				v5.addVertexWithUV(in2, 0.01, in2, u, v);
				v5.addVertexWithUV(in, 0.01, in2, u2, v);
				v5.addVertexWithUV(in, 0.01, in, u2, v2);
				v5.addVertexWithUV(in2, 0.01, in, u, v2);
				break;
			case SOUTH:
				v5.addVertexWithUV(in, in, 0.99, u, v);
				v5.addVertexWithUV(in2, in, 0.99, u2, v);
				v5.addVertexWithUV(in2, in2, 0.99, u2, v2);
				v5.addVertexWithUV(in, in2, 0.99, u, v2);
				break;
			case NORTH:
				v5.addVertexWithUV(in, in2, 0.01, u, v2);
				v5.addVertexWithUV(in2, in2, 0.01, u2, v2);
				v5.addVertexWithUV(in2, in, 0.01, u2, v);
				v5.addVertexWithUV(in, in, 0.01, u, v);
				break;
			case EAST:
				v5.addVertexWithUV(0.99, in2, in, u, v2);
				v5.addVertexWithUV(0.99, in2, in2, u2, v2);
				v5.addVertexWithUV(0.99, in, in2, u2, v);
				v5.addVertexWithUV(0.99, in, in, u, v);
				break;
			case WEST:
				v5.addVertexWithUV(0.01, in, in, u, v);
				v5.addVertexWithUV(0.01, in, in2, u2, v);
				v5.addVertexWithUV(0.01, in2, in2, u2, v2);
				v5.addVertexWithUV(0.01, in2, in, u, v2);
			default:
				break;
			}
		}
		v5.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		ReikaRenderHelper.enableLighting();
		GL11.glTranslated(-par2, -par4, -par6);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "";
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		RenderableDuct te = (RenderableDuct)tile;
		if (!tile.hasWorldObj()) {
			ReikaTextureHelper.bindTerrainTexture();
			double s = 1;
			double sy = 1.05;
			GL11.glScaled(s, sy, s);
			//this.renderBlock(te, par2, par4, par6);
			ClientProxy.pipe.renderBlockInInventory(te, par2, par4, par6);
			GL11.glScaled(1/s, 1/sy, 1/s);
		}
	}

	private void renderBlock(RenderableDuct te, double par2, double par4, double par6) {
		IIcon ico = te.getBlockIcon();
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		GL11.glTranslated(par2, par4, par6);
		Tessellator v5 = Tessellator.instance;

		float f = 0.6F;
		GL11.glColor4f(f, f, f, 1);
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(0, 0, 1, u, v);
		v5.addVertexWithUV(1, 0, 1, du, v);
		v5.addVertexWithUV(1, 1, 1, du, dv);
		v5.addVertexWithUV(0, 1, 1, u, dv);
		v5.draw();

		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(0, 1, 0, u, dv);
		v5.addVertexWithUV(1, 1, 0, du, dv);
		v5.addVertexWithUV(1, 0, 0, du, v);
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.draw();

		f = 0.4F;
		GL11.glColor4f(f, f, f, 1);
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(1, 1, 0, u, dv);
		v5.addVertexWithUV(1, 1, 1, du, dv);
		v5.addVertexWithUV(1, 0, 1, du, v);
		v5.addVertexWithUV(1, 0, 0, u, v);
		v5.draw();

		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.addVertexWithUV(0, 0, 1, du, v);
		v5.addVertexWithUV(0, 1, 1, du, dv);
		v5.addVertexWithUV(0, 1, 0, u, dv);
		v5.draw();

		f = 1F;
		GL11.glColor4f(f, f, f, 1);
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(0, 1, 1, u, dv);
		v5.addVertexWithUV(1, 1, 1, du, dv);
		v5.addVertexWithUV(1, 1, 0, du, v);
		v5.addVertexWithUV(0, 1, 0, u, v);
		v5.draw();

		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.addVertexWithUV(1, 0, 0, du, v);
		v5.addVertexWithUV(1, 0, 1, du, dv);
		v5.addVertexWithUV(0, 0, 1, u, dv);
		v5.draw();

		//-----------------------------------

		double g = 0.35;
		double g1 = g/2;
		double g2 = 1-g/2;

		ico = Blocks.wool.getIcon(0, ReikaDyeHelper.BLACK.getWoolMeta());
		u = ico.getMinU();
		v = ico.getMinV();
		du = ico.getMaxU();
		dv = ico.getMaxV();
		float uu = du-u;
		float vv = dv-v;
		u += g1*uu;
		du -= g1*uu;
		v += g1*vv;
		dv -= g1*vv;

		f = 0.6F;
		GL11.glColor4f(f, f, f, 1);
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(g1, g1, 1.001, u, v);
		v5.addVertexWithUV(g2, g1, 1.001, du, v);
		v5.addVertexWithUV(g2, g2, 1.001, du, dv);
		v5.addVertexWithUV(g1, g2, 1.001, u, dv);
		v5.draw();

		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(g1, g2, -0.001, u, dv);
		v5.addVertexWithUV(g2, g2, -0.001, du, dv);
		v5.addVertexWithUV(g2, g1, -0.001, du, v);
		v5.addVertexWithUV(g1, g1, -0.001, u, v);
		v5.draw();

		f = 0.4F;
		GL11.glColor4f(f, f, f, 1);
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(1.001, g2, g1, u, dv);
		v5.addVertexWithUV(1.001, g2, g2, du, dv);
		v5.addVertexWithUV(1.001, g1, g2, du, v);
		v5.addVertexWithUV(1.001, g1, g1, u, v);
		v5.draw();

		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(-0.001, g1, g1, u, v);
		v5.addVertexWithUV(-0.001, g1, g2, du, v);
		v5.addVertexWithUV(-0.001, g2, g2, du, dv);
		v5.addVertexWithUV(-0.001, g2, g1, u, dv);
		v5.draw();

		f = 1F;
		GL11.glColor4f(f, f, f, 1);
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(g1, 1.001, g2, u, dv);
		v5.addVertexWithUV(g2, 1.001, g2, du, dv);
		v5.addVertexWithUV(g2, 1.001, g1, du, v);
		v5.addVertexWithUV(g1, 1.001, g1, u, v);
		v5.draw();

		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(g1, -0.001, g1, u, v);
		v5.addVertexWithUV(g2, -0.001, g1, du, v);
		v5.addVertexWithUV(g2, -0.001, g2, du, dv);
		v5.addVertexWithUV(g1, -0.001, g2, u, dv);
		v5.draw();
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private void renderFace(RenderableDuct tile, double x, double y, double z, ForgeDirection dir) {
		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;

		IIcon ico = tile.getBlockIcon();
		float u = ico.getMinU();
		float v = ico.getMinV();
		float u2 = ico.getMaxU();
		float v2 = ico.getMaxV();

		float ddu = u2-u;
		float ddv = v2-v;
		float uo = u;
		float vo = v;
		float u2o = u2;
		float v2o = v2;

		u += ddu*(1-size)/5;
		v += ddv*(1-size)/5;
		u2 -= ddu*(1-size)/5;
		v2 -= ddv*(1-size)/5;

		float du = ddu*dd;
		float dv = ddv*dd;

		float lx = dd+dl;
		float ly = dd+dl;
		float mx = 1-dd-dl;
		float my = 1-dd-dl;

		IIcon gico = Blocks.glass.getIcon(0, 0);
		float gu = gico.getMinU();
		float gv = gico.getMinV();
		float gu2 = gico.getMaxU();
		float gv2 = gico.getMaxV();
		float dgu = gu2-gu;
		float dgv = gv2-gv;

		float guu = gu+dgu*dl;
		float gvv = gv+dgv*dl;

		gu += dgu/8;
		gv += dgv/8;
		gu2 -= dgu/8;
		gv2 -= dgv/8;

		Tessellator v5 = Tessellator.instance;
		//GL11.glTranslated(x, y, z);

		this.faceBrightness(dir, v5);
		switch(dir) {
		case DOWN:
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v2);

			v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u, 		v2-dv);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd+dl, 		u2, 	v2-dv);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd-dl, 	u2, 	v+dv);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u, 		v+dv);

			v5.addVertexWithUV(mx, 1-dd, ly, gu2, gv);
			v5.addVertexWithUV(lx, 1-dd, ly, gu, gv);
			v5.addVertexWithUV(lx, 1-dd, my, gu, gv2);
			v5.addVertexWithUV(mx, 1-dd, my, gu2, gv2);
			break;
		case NORTH:
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
			v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);

			v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);

			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	dd+dl, 	1-dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u, 		v2-dv);

			v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u, 		v+dv);
			v5.addVertexWithUV(1-dd, 	1-dd-dl, 	1-dd, 	u2, 	v+dv);
			v5.addVertexWithUV(1-dd, 	1-dd, 		1-dd, 	u2, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u, 		v);

			v5.addVertexWithUV(mx, my, 1-dd, gu2, gv2);
			v5.addVertexWithUV(lx, my, 1-dd, gu, gv2);
			v5.addVertexWithUV(lx, ly, 1-dd, gu, gv);
			v5.addVertexWithUV(mx, ly, 1-dd, gu2, gv);
			break;
		case EAST:
			v5.addVertexWithUV(1-dd, 		1-dd, 	dd, 		u, 		v);
			v5.addVertexWithUV(1-dd, 		1-dd, 	dd+dl, 		u+du, 	v);
			v5.addVertexWithUV(1-dd, 		dd, 	dd+dl, 		u+du, 	v2);
			v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
			v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);

			v5.addVertexWithUV(1-dd, 		dd+dl, 	dd, 		u, 		v2-dv);
			v5.addVertexWithUV(1-dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd, 		1-dd, 		dd, 	u, 		v);
			v5.addVertexWithUV(1-dd, 		1-dd, 		1-dd, 	u2, 	v);
			v5.addVertexWithUV(1-dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
			v5.addVertexWithUV(1-dd, 		1-dd-dl, 	dd, 	u, 		v+dv);

			v5.addVertexWithUV(1-dd, ly, mx, gu2, gv);
			v5.addVertexWithUV(1-dd, ly, lx, gu, gv);
			v5.addVertexWithUV(1-dd, my, lx, gu, gv2);
			v5.addVertexWithUV(1-dd, my, mx, gu2, gv2);
			break;
		case WEST:
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u+du, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u+du, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);

			v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);

			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);

			v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
			v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
			v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u2, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 		dd, 	u, 		v);

			v5.addVertexWithUV(dd, my, mx, gu2, gv2);
			v5.addVertexWithUV(dd, my, lx, gu, gv2);
			v5.addVertexWithUV(dd, ly, lx, gu, gv);
			v5.addVertexWithUV(dd, ly, mx, gu2, gv);
			break;
		case UP:
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);

			v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v);

			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	dd+dl, 		u2, 	v2-dv);
			v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u, 		v2-dv);

			v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u, 		v+dv);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd-dl, 	u2, 	v+dv);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);

			v5.addVertexWithUV(mx, dd, my, gu2, gv2);
			v5.addVertexWithUV(lx, dd, my, gu, gv2);
			v5.addVertexWithUV(lx, dd, ly, gu, gv);
			v5.addVertexWithUV(mx, dd, ly, gu2, gv);
			break;
		case SOUTH:
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v);
			v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);

			v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
			v5.addVertexWithUV(1-dd, 	dd+dl, 	dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(dd, 		1-dd, 		dd, 		u, 		v);
			v5.addVertexWithUV(1-dd, 	1-dd, 		dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd-dl, 	dd, 	u2, 	v+dv);
			v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);

			v5.addVertexWithUV(mx, ly, dd, gu2, gv);
			v5.addVertexWithUV(lx, ly, dd, gu, gv);
			v5.addVertexWithUV(lx, my, dd, gu, gv2);
			v5.addVertexWithUV(mx, my, dd, gu2, gv2);
			break;
		default:
			break;
		}

		//GL11.glTranslated(-x, -y, -z);
	}

	private void faceBrightness(ForgeDirection dir, Tessellator v5) {
		float f = 1;
		switch(dir.getOpposite()) {
		case DOWN:
			f = 0.4F;
			break;
		case EAST:
			f = 0.5F;
			break;
		case NORTH:
			f = 0.65F;
			break;
		case SOUTH:
			f = 0.65F;
			break;
		case UP:
			f = 1F;
			break;
		case WEST:
			f = 0.5F;
			break;
		default:
			break;
		}
		v5.setColorOpaque_F(f, f, f);
	}
}
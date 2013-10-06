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

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class PipeRenderer extends RotaryTERenderer {

	public void renderPipe(TileEntityPiping tile, MachineRegistry m, double par2, double par4, double par6) {

		//		this.bindTextureByName("/Reika/RotaryCraft/Textures/terrain/piping.png");
		ReikaTextureHelper.bindTerrainTexture();

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		int var11 = 0;
		float var13;

		for (int i = 0; i < 6; i++) {
			this.renderFace(tile, par2, par4, par6, dirs[i]);
		}

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void renderLiquid(TileEntityPiping tile, double par2, double par4, double par6) {
		float size = 0.75F/2F;
		float window = 0.5F/2F;

		if (!tile.hasLiquid() && !tile.isInWorld())
			return;
		Fluid fluid = tile.getLiquidType();
		FluidStack liquid = new FluidStack(fluid, 1);

		int[] displayList = ReikaLiquidRenderer.getGLLists(liquid, tile.worldObj, false);

		if (displayList == null) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		ReikaLiquidRenderer.bindFluidTexture(liquid);
		ReikaLiquidRenderer.setFluidColor(liquid);

		GL11.glTranslated(par2, par4, par6);

		GL11.glTranslated(size/3, size/3, size/3);
		GL11.glScaled(size*2, size*2, size*2);
		GL11.glScaled(0.99, 0.99, 0.99);
		GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);

		for (int i = 0; i < 6; i++) {
			if (tile.isConnectionValidForSide(dirs[i])) {
				double dy = 0;
				switch(dirs[i]) {
				case DOWN:
					dy = size*2-size*2-(size-window)-0.06;
					GL11.glTranslated(0, dy, 0);
					GL11.glScaled(0.99, (size-window)*1.5, 0.99);
					GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);
					GL11.glScaled(1D/0.99, 1D/((size-window)*1.4), 1D/0.99);
					GL11.glTranslated(0, -dy, 0);
					break;
				case EAST:
					dy = size/3+size*2+(size-window)-0.01;
					GL11.glTranslated(dy, 0, 0);
					GL11.glScaled((size-window)*1.5, 0.99, 0.99);
					GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);
					GL11.glScaled(1D/((size-window)*1.4), 1D/0.99, 1D/0.99);
					GL11.glTranslated(-dy, 0, 0);
					break;
				case NORTH:
					dy = size*2-size*2-(size-window)-0.04;
					GL11.glTranslated(0, 0, dy);
					GL11.glScaled(0.99, 0.99, (size-window)*1.5);
					GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);
					GL11.glScaled(1D/0.99, 1D/0.99, 1D/((size-window)*1.4));
					GL11.glTranslated(0, 0, -dy);
					break;
				case SOUTH:
					dy = size/3+size*2+(size-window)-0.01;
					GL11.glTranslated(0, 0, dy);
					GL11.glScaled(0.99, 0.99, (size-window)*1.5);
					GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);
					GL11.glScaled(1D/0.99, 1D/0.99, 1D/((size-window)*1.4));
					GL11.glTranslated(0, 0, -dy);
					break;
				case UP:
					dy = size/3+size*2+(size-window);
					GL11.glTranslated(0, dy-0.01, 0);
					GL11.glScaled(0.99, (size-window)*1.5, 0.99);
					GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);
					GL11.glScaled(1D/0.99, 1D/((size-window)*1.4), 1D/0.99);
					GL11.glTranslated(0, -dy, 0);
					break;
				case WEST:
					dy = size*2-size*2-(size-window)-0.05;
					GL11.glTranslated(dy, 0, 0);
					GL11.glScaled((size-window)*1.5, 0.99, 0.99);
					GL11.glCallList(displayList[ReikaLiquidRenderer.LEVELS-1]);
					GL11.glScaled(1D/((size-window)*1.4), 1D/0.99, 1D/0.99);
					GL11.glTranslated(-dy, 0, 0);
					break;
				default:
					break;
				}
			}
		}

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return null;
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile)) {
			this.renderPipe((TileEntityPiping)tile, ((RotaryCraftTileEntity)tile).getMachine(), par2, par4, par6);
			if (MinecraftForgeClient.getRenderPass() == 1) {
				this.renderLiquid((TileEntityPiping) tile, par2, par4, par6);
			}
		}
	}

	private void renderFace(TileEntityPiping tile, double par2, double par4, double par6, ForgeDirection dir) {
		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;

		Icon ico = tile.getBlockIcon();
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

		Icon gico = Block.glass.getIcon(0, 0);
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

		Tessellator v5 = new Tessellator();
		v5.startDrawingQuads();

		int dx = tile.xCoord+dir.offsetX;
		int dy = tile.yCoord+dir.offsetY;
		int dz = tile.zCoord+dir.offsetZ;
		int br = tile.getBlockType().getMixedBrightnessForBlock(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		v5.setBrightness(br);

		if (tile.isInWorld() && tile.isConnectionValidForSide(dir)) {
			switch(dir) {
			case DOWN:
				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size+dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size+dd, 	0.5+size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size-dd, 	0.5+size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size-dd, 	0.5+size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	1, 				0.5+size, 	gu+dgu*size*2, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	1, 				0.5+size, 	gu, gv+dgv*size);*/

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size+dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size+dd, 	0.5-size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size-dd, 	0.5-size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size-dd, 	0.5-size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	1, 				0.5-size, 	gu+dgu*size*2, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	1, 				0.5-size, 	gu, gv+dgv*size);*/
				break;
			case EAST:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5+window, 	u2o, 	v+dv);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5+size, 		u2o, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+size, 		u2, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+window, 	u2, 	v+dv);

				v5.addVertexWithUV(1, 			0.5+size, 	0.5-window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5-size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-size, 		u2, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-window, 	u2, 	v2-dv);

				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size-dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size+dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5-size+dd, 	0.5+size, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5+window, 	u2o, 	v+dv);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5+size, 		u2o, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+size, 		u2, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+window, 	u2, 	v+dv);

				v5.addVertexWithUV(1, 			0.5-size, 	0.5-window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5-size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-size, 		u2, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-window, 	u2, 	v2-dv);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size+dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5-size+dd, 	0.5-size, 	u2-du, vo);
				break;
			case NORTH:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0.5+size, 	u2-du, v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+size, 	u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	1, 			u2, 	v2o);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	1, 			u2-du, v2o);

				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0.5+size, 	u+du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+size, 	u, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	1, 			u, vo);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	1, 			u+du, vo);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5+size+dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5+size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5+size+dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5+size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	1, 			u2, vo);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	1, 			u2-du, vo);

				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0.5+size, 	u+du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+size, 	u, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	1, 			u, vo);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	1, 			u+du, vo);
				break;
			case SOUTH:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0, 			u2-du, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0, 			u2, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-size, 	u2, 	v2);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0.5-size, 	u2-du, 	v2);

				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0, 			u+du, 	v2o);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0, 			u, 		v2o);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-size, 	u, 		v2);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0.5-size, 	u+du, 	v2);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5-size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5-size-dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5-size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5-size-dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0, 			u2-du, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0, 			u2, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-size, 	u2, 	v2);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0.5-size, 	u2-du, 	v2);

				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0, 			u+du, 	v2o);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0, 			u, 		v2o);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-size, 	u, 		v2);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0.5-size, 	u+du, 	v2);
				break;
			case UP:
				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size-dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size-dd, 	0.5+size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size+dd, 	0.5+size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size+dd, 	0.5+size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0, 				0.5+size, 	gu, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	0, 				0.5+size, 	gu+dgu*size*2, gv+dgv*size);*/

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size-dd, 	0.5-size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size+dd, 	0.5-size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size+dd, 	0.5-size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0, 				0.5-size, 	gu, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	0, 				0.5-size, 	gu+dgu*size*2, gv+dgv*size);*/
				break;
			case WEST:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5+window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5+size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+size, 		u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+window, 	u2, 	v2-dv);

				v5.addVertexWithUV(0, 			0.5+size, 	0.5-window, 	u2o, 	v+dv);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5-size, 		u2o, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-size, 		u2, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-window, 	u2, 	v+dv);

				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size-dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size-dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5-size+dd, 	0.5+size, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5+window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5+size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+size, 		u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+window, 	u2, 	v2-dv);

				v5.addVertexWithUV(0, 			0.5-size, 	0.5-window, 	u2o, 	v+dv);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5-size, 		u2o, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-size, 		u2, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-window, 	u2, 	v+dv);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size-dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5-size+dd, 	0.5-size, 	u2-du, vo);
				break;
			default:
				break;
			}
		}
		else {
			this.faceBrightness(dir, v5);
			switch(dir) {
			case DOWN:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd+dl, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd-dl, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
				}

				v5.addVertexWithUV(mx, 1-dd, ly, gu2, gv);
				v5.addVertexWithUV(lx, 1-dd, ly, gu, gv);
				v5.addVertexWithUV(lx, 1-dd, my, gu, gv2);
				v5.addVertexWithUV(mx, 1-dd, my, gu2, gv2);
				break;
			case NORTH:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u, 		v);
				}

				v5.addVertexWithUV(mx, ly, 1-dd, gu2, gv);
				v5.addVertexWithUV(lx, ly, 1-dd, gu, gv);
				v5.addVertexWithUV(lx, my, 1-dd, gu, gv2);
				v5.addVertexWithUV(mx, my, 1-dd, gu2, gv2);
				break;
			case EAST:
				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 		dd, 	dd+dl, 		u+du, 	v2);
					v5.addVertexWithUV(1-dd, 		1-dd, 	dd+dl, 		u+du, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 	dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(1-dd, 		dd+dl, 	dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(1-dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 		1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 		dd, 	u, 		v);
				}

				v5.addVertexWithUV(1-dd, ly, mx, gu2, gv);
				v5.addVertexWithUV(1-dd, ly, lx, gu, gv);
				v5.addVertexWithUV(1-dd, my, lx, gu, gv2);
				v5.addVertexWithUV(1-dd, my, mx, gu2, gv2);
				break;
			case WEST:
				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u+du, 	v2);
					v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
					v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 		dd, 	u, 		v);
				}

				v5.addVertexWithUV(dd, ly, mx, gu2, gv);
				v5.addVertexWithUV(dd, ly, lx, gu, gv);
				v5.addVertexWithUV(dd, my, lx, gu, gv2);
				v5.addVertexWithUV(dd, my, mx, gu2, gv2);
				break;
			case UP:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd+dl, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd-dl, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);
				}

				v5.addVertexWithUV(mx, dd, ly, gu2, gv);
				v5.addVertexWithUV(lx, dd, ly, gu, gv);
				v5.addVertexWithUV(lx, dd, my, gu, gv2);
				v5.addVertexWithUV(mx, dd, my, gu2, gv2);
				break;
			case SOUTH:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd+dl, 	dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd-dl, 	dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 		dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 		dd, 		u, 		v);
				}

				v5.addVertexWithUV(mx, ly, dd, gu2, gv);
				v5.addVertexWithUV(lx, ly, dd, gu, gv);
				v5.addVertexWithUV(lx, my, dd, gu, gv2);
				v5.addVertexWithUV(mx, my, dd, gu2, gv2);
				break;
			default:
				break;
			}
		}
		v5.draw();
	}

	private void faceBrightness(ForgeDirection dir, Tessellator v5) {
		float f = 1;
		switch(dir) {
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

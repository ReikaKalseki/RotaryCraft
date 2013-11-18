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

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
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
			//this.renderFace(tile, par2, par4, par6, dirs[i]);
		}

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void renderLiquid(TileEntityPiping tile, double par2, double par4, double par6, ForgeDirection dir) {
		if (!tile.hasLiquid())
			return;
		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;
		double in = 0.5+size-0.01;
		double in2 = 0.5-size+0.01;
		double dd2 = in-in2;

		Fluid f = tile.getLiquidType();
		if (f == null)
			return;
		Icon ico = tile.getLiquidType().getIcon();
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

		Tessellator v5 = new Tessellator();
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
		return null;
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		//if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile)) {
		//	if (MinecraftForgeClient.getRenderPass() == 0) {
		//		this.renderPipe((TileEntityPiping)tile, ((RotaryCraftTileEntity)tile).getMachine(), par2, par4, par6);
		//	}
		//}
		if (MinecraftForgeClient.getRenderPass() == 1) {
			for (int i = 0; i < 6; i++) {
				this.renderLiquid((TileEntityPiping) tile, par2, par4, par6, dirs[i]);
			}
		}
	}

	private void faceBrightness(ForgeDirection dir, Tessellator v5) {
		float f = 1;
		switch(dir.getOpposite()) {
		case DOWN:
			f = 0.6F;
			break;
		case EAST:
			f = 0.7F;
			break;
		case NORTH:
			f = 0.85F;
			break;
		case SOUTH:
			f = 0.85F;
			break;
		case UP:
			f = 1F;
			break;
		case WEST:
			f = 0.7F;
			break;
		default:
			break;
		}
		v5.setColorOpaque_F(f, f, f);
	}
}

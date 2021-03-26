/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Rendering.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelReservoir;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityGroundHydrator;

public class RenderHydrator extends RotaryTERenderer
{

	private ModelReservoir ReservoirModel = new ModelReservoir();

	public void renderTileEntityGroundHydratorAt(TileEntityGroundHydrator tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelReservoir var14;
		var14 = ReservoirModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/hydratortex.png");

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
					var11 = 270;
					break;
				case 3:
					var11 = 90;
					break;
			}

			if (tile.getBlockMetadata() <= 3)
				GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
			else {
				GL11.glRotatef(var11, 1F, 0F, 0.0F);
				GL11.glTranslatef(0F, -1F, 1F);
				if (tile.getBlockMetadata() == 5)
					GL11.glTranslatef(0F, 0F, -2F);
			}
		}

		float var13;

		if (tile.isInWorld()) {
			for (int i = 2; i < 6; i++) {
				var14.renderSide(tile, dirs[i]);
			}
			var14.renderSide(tile, ForgeDirection.DOWN);
		}
		else {
			var14.renderAll(tile, null);
		}

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		TileEntityGroundHydrator tr = (TileEntityGroundHydrator)tile;
		if (this.doRenderModel(tr)) {
			this.renderTileEntityGroundHydratorAt(tr, par2, par4, par6, par8);
			GL11.glEnable(GL11.GL_BLEND);
			BlendMode.DEFAULT.apply();
			this.renderCover(tr, par2, par4, par6);
		}

		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();
		if (MinecraftForgeClient.getRenderPass() == 1) {
			this.renderLiquid(tr, par2, par4, par6);
			if (tr.getLevel() > 0)
				this.renderOverlay(tr, par2, par4, par6);
		}
		GL11.glPopAttrib();
	}

	private void renderOverlay(TileEntityGroundHydrator te, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		int r = te.getRange();
		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		ReikaTextureHelper.bindTerrainTexture();
		IIcon ico = RotaryCraft.hydratorOverlay;
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		for (int i = -r; i <= r; i++) {
			for (int k = -r; k <= r; k++) {
				Block b = te.worldObj.getBlock(te.xCoord+i, te.yCoord, te.zCoord+k);
				int meta = te.worldObj.getBlockMetadata(te.xCoord+i, te.yCoord, te.zCoord+k);
				if (te.affectsBlock(b, meta)) {
					int a = 192-16*(Math.abs(i)+Math.abs(k));
					v5.setColorRGBA_I(0xffffff, a);
					double h = b.getBlockBoundsMaxY()+0.005;
					v5.addVertexWithUV(i+0, h, k+1, u, dv);
					v5.addVertexWithUV(i+1, h, k+1, du, dv);
					v5.addVertexWithUV(i+1, h, k+0, du, v);
					v5.addVertexWithUV(i+0, h, k+0, u, v);
				}
			}
		}
		v5.draw();
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private void renderCover(TileEntityGroundHydrator tr, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		ReikaTextureHelper.bindTerrainTexture();
		IIcon ico = RotaryCraft.woodLattice;
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		float h = 0.99F;
		float dd = 0;//.03125F;
		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		v5.setNormal(0, 1, 0);
		v5.addVertexWithUV(dd, h, 1-dd, u, dv);
		v5.addVertexWithUV(1-dd, h, 1-dd, du, dv);
		v5.addVertexWithUV(1-dd, h, dd, du, v);
		v5.addVertexWithUV(dd, h, dd, u, v);
		v5.draw();
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private void renderLiquid(TileEntityGroundHydrator tr, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		Fluid f = tr.getFluid();
		if (f != null) {
			ReikaLiquidRenderer.bindFluidTexture(f);
			IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = this.getFillAmount(tr);
			Tessellator v5 = Tessellator.instance;
			v5.startDrawingQuads();
			v5.setNormal(0, 1, 0);

			v5.addVertexWithUV(0, h, 1, u, dv);
			v5.addVertexWithUV(1, h, 1, du, dv);
			v5.addVertexWithUV(1, h, 0, du, v);
			v5.addVertexWithUV(0, h, 0, u, v);
			v5.draw();
			ReikaRenderHelper.enableLighting();
		}
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private double getFillAmount(TileEntityGroundHydrator tr) {
		return 0.0625+14D/16D*tr.getLevel()/1000;
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "hydratortex.png";
	}
}

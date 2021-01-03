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

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Data.DynamicAverage;
import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Rendering.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

public class RenderReservoir extends RotaryTERenderer
{

	private ModelReservoir ReservoirModel = new ModelReservoir();

	private static DynamicAverage average = new DynamicAverage();

	public void renderTileEntityReservoirAt(TileEntityReservoir tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelReservoir var14;
		var14 = ReservoirModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/reservoirtex.png");

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
				if (!tile.isConnectedOnSide(dirs[i])) {
					var14.renderSide(tile, dirs[i]);
				}
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
		TileEntityReservoir tr = (TileEntityReservoir)tile;
		if (this.doRenderModel(tr)) {
			this.renderTileEntityReservoirAt(tr, par2, par4, par6, par8);
			GL11.glEnable(GL11.GL_BLEND);
			BlendMode.DEFAULT.apply();
			if (tr.isCovered) {
				this.renderCover(tr, par2, par4, par6);
			}
		}

		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();
		if (MinecraftForgeClient.getRenderPass() == 1 || !((RotaryCraftTileEntity)tile).isInWorld()) {
			this.renderLiquid(tile, par2, par4, par6);
		}
		GL11.glPopAttrib();
	}

	private void renderCover(TileEntityReservoir tr, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		ReikaTextureHelper.bindTerrainTexture();
		IIcon ico = Blocks.glass.getIcon(0, 0);
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

	private void renderLiquid(TileEntity tile, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		TileEntityReservoir tr = (TileEntityReservoir)tile;
		Fluid f = tr.getFluid();
		if (f != null) {
			if (!f.equals(FluidRegistry.LAVA)) {
				GL11.glEnable(GL11.GL_BLEND);
			}
			ReikaLiquidRenderer.bindFluidTexture(f);
			IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = this.getFillAmount(tr);
			Tessellator v5 = Tessellator.instance;
			if (f.getLuminosity() > 0 && tile.hasWorldObj())
				ReikaRenderHelper.disableLighting();
			v5.startDrawingQuads();
			v5.setNormal(0, 1, 0);
			v5.setColorOpaque_I(tr.getFluidRenderColor());

			TileEntityReservoir tr1 = null;
			TileEntityReservoir tr2 = null;
			TileEntityReservoir tr3 = null;
			TileEntityReservoir tr4 = null;
			TileEntityReservoir tr6 = null;
			TileEntityReservoir tr7 = null;
			TileEntityReservoir tr8 = null;
			TileEntityReservoir tr9 = null;

			if (tr.hasNearbyReservoir(1)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord-1, tr.yCoord, tr.zCoord+1);
				if (teb instanceof TileEntityReservoir) {
					tr1 = (TileEntityReservoir)teb;
					if (tr1.getFluid() != f)
						tr1 = null;
				}
			}
			if (tr.hasNearbyReservoir(2)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord, tr.yCoord, tr.zCoord+1);
				if (teb instanceof TileEntityReservoir) {
					tr2 = (TileEntityReservoir)teb;
					if (tr2.getFluid() != f)
						tr2 = null;
				}
			}
			if (tr.hasNearbyReservoir(3)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord+1, tr.yCoord, tr.zCoord+1);
				if (teb instanceof TileEntityReservoir) {
					tr3 = (TileEntityReservoir)teb;
					if (tr3.getFluid() != f)
						tr3 = null;
				}
			}
			if (tr.hasNearbyReservoir(4)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord-1, tr.yCoord, tr.zCoord);
				if (teb instanceof TileEntityReservoir) {
					tr4 = (TileEntityReservoir)teb;
					if (tr4.getFluid() != f)
						tr4 = null;
				}
			}
			if (tr.hasNearbyReservoir(6)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord+1, tr.yCoord, tr.zCoord);
				if (teb instanceof TileEntityReservoir) {
					tr6 = (TileEntityReservoir)teb;
					if (tr6.getFluid() != f)
						tr6 = null;
				}
			}
			if (tr.hasNearbyReservoir(7)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord-1, tr.yCoord, tr.zCoord-1);
				if (teb instanceof TileEntityReservoir) {
					tr7 = (TileEntityReservoir)teb;
					if (tr7.getFluid() != f)
						tr7 = null;
				}
			}
			if (tr.hasNearbyReservoir(8)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord, tr.yCoord, tr.zCoord-1);
				if (teb instanceof TileEntityReservoir) {
					tr8 = (TileEntityReservoir)teb;
					if (tr8.getFluid() != f)
						tr8 = null;
				}
			}
			if (tr.hasNearbyReservoir(9)) {
				TileEntity teb = tr.worldObj.getTileEntity(tr.xCoord+1, tr.yCoord, tr.zCoord-1);
				if (teb instanceof TileEntityReservoir) {
					tr9 = (TileEntityReservoir)teb;
					if (tr9.getFluid() != f)
						tr9 = null;
				}
			}

			average.clear();
			average.add(h);
			if (tr1 != null)
				average.add(this.getFillAmount(tr1));
			if (tr2 != null)
				average.add(this.getFillAmount(tr2));
			if (tr4 != null)
				average.add(this.getFillAmount(tr4));
			double hmp = average.getAverage();

			average.clear();
			average.add(h);
			if (tr3 != null)
				average.add(this.getFillAmount(tr3));
			if (tr2 != null)
				average.add(this.getFillAmount(tr2));
			if (tr6 != null)
				average.add(this.getFillAmount(tr6));
			double hpp = average.getAverage();

			average.clear();
			average.add(h);
			if (tr8 != null)
				average.add(this.getFillAmount(tr8));
			if (tr9 != null)
				average.add(this.getFillAmount(tr9));
			if (tr6 != null)
				average.add(this.getFillAmount(tr6));
			double hpm = average.getAverage();

			average.clear();
			average.add(h);
			if (tr7 != null)
				average.add(this.getFillAmount(tr7));
			if (tr8 != null)
				average.add(this.getFillAmount(tr8));
			if (tr4 != null)
				average.add(this.getFillAmount(tr4));
			double hmm = average.getAverage();

			v5.addVertexWithUV(0, hmp, 1, u, dv);
			v5.addVertexWithUV(1, hpp, 1, du, dv);
			v5.addVertexWithUV(1, hpm, 0, du, v);
			v5.addVertexWithUV(0, hmm, 0, u, v);
			v5.draw();
			if (tile.hasWorldObj())
				ReikaRenderHelper.enableLighting();
		}
		GL11.glTranslated(-par2, -par4, -par6);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private double getFillAmount(TileEntityReservoir tr) {
		return 0.0625+14D/16D*tr.getLevel()/tr.CAPACITY;
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "reservoirtex.png";
	}
}

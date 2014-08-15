/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderReservoir extends RotaryTERenderer
{

	private ModelReservoir ReservoirModel = new ModelReservoir();

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
		if (tile.isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			GL11.glEnable(GL11.GL_BLEND);
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
			var14.renderAll(tile, null, 0, 0);
		}

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		TileEntityReservoir tr = (TileEntityReservoir)tile;
		if (this.isValidMachineRenderpass(tr)) {
			this.renderTileEntityReservoirAt(tr, par2, par4, par6, par8);
			if (tr.isInWorld() && tr.isCovered) {
				this.renderCover(tr, par2, par4, par6);
			}
		}

		if (MinecraftForgeClient.getRenderPass() == 1 || !((RotaryCraftTileEntity)tile).isInWorld()) {
			this.renderLiquid(tile, par2, par4, par6);
		}
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
		int amt = tr.getLevel();
		Fluid f = tr.getFluid();
		if (f != null && amt > 0) {
			if (!f.equals(FluidRegistry.LAVA)) {
				GL11.glEnable(GL11.GL_BLEND);
			}
			ReikaLiquidRenderer.bindFluidTexture(f);
			IIcon ico = f.getIcon();
			if (ico == null) {
				RotaryCraft.logger.logError("Fluid "+f.getID()+" ("+f.getLocalizedName()+") exists (block ID "+f.getBlock()+") but has no icon! Registering bedrock texture as a placeholder!");
				f.setIcons(Blocks.bedrock.getIcon(0, 0));
				ico = Blocks.bedrock.getIcon(0, 0);
			}
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = 0.0625+14D/16D*amt/tr.CAPACITY;
			Tessellator v5 = Tessellator.instance;
			if (f.getLuminosity() > 0 && tile.hasWorldObj())
				ReikaRenderHelper.disableLighting();
			v5.startDrawingQuads();
			v5.setNormal(0, 1, 0);
			v5.addVertexWithUV(0, h, 1, u, dv);
			v5.addVertexWithUV(1, h, 1, du, dv);
			v5.addVertexWithUV(1, h, 0, du, v);
			v5.addVertexWithUV(0, h, 0, u, v);
			v5.draw();
			if (tile.hasWorldObj())
				ReikaRenderHelper.enableLighting();
		}
		GL11.glTranslated(-par2, -par4, -par6);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "reservoirtex.png";
	}
}
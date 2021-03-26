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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Interfaces.Item.AnimatedSpritesheet;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelDryingBed;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDryingBed;

public class RenderDryingBed extends RotaryTERenderer {

	private final ModelDryingBed model = new ModelDryingBed();

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "dryingbedtex.png";
	}

	public void renderTileEntityDryingBedAt(TileEntityDryingBed tile, double par2, double par4, double par6, float par8)
	{
		ModelDryingBed var14;
		var14 = model;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/dryingbedtex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;
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
		TileEntityDryingBed te = (TileEntityDryingBed)tile;
		if (this.doRenderModel(te))
			this.renderTileEntityDryingBedAt(te, par2, par4, par6, par8);
		GL11.glPushMatrix();
		GL11.glTranslated(par2, par4, par6);
		if (te.isInWorld()) {
			this.renderItem(te);
		}
		if (te.isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			this.renderFluid(te);
		}
		GL11.glPopMatrix();
	}

	private void renderItem(TileEntityDryingBed te) {
		ItemStack is = te.getStackInSlot(0);
		if (is != null) {
			float thick = 0.0625F;
			Tessellator v5 = Tessellator.instance;
			//int col = 0;
			//int row = 0;
			//ItemRenderer.renderItemIn2D(v5, 0.0625F+0.0625F*col, 0.0625F*row, 0.0625F*col, 0.0625F+0.0625F*row, 256, 256, thick);
			GL11.glEnable(GL11.GL_BLEND);
			//GL11.glDisable(GL11.GL_ALPHA_TEST);
			//GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glColor4f(1, 1, 1, 1.2F*te.progress/400F);
			GL11.glColor4f(1, 1, 1, 1);

			IItemRenderer iir = MinecraftForgeClient.getItemRenderer(is, ItemRenderType.INVENTORY);
			Item item = is.getItem();
			if (item instanceof IndexedItemSprites) {
				IndexedItemSprites iis = (IndexedItemSprites)item;
				ReikaTextureHelper.bindTexture(iis.getTextureReferenceClass(), iis.getTexture(is));
				int index = iis.getItemSpriteIndex(is);
				int row = index/16;
				int col = index%16;

				if (item instanceof AnimatedSpritesheet) {
					AnimatedSpritesheet a = (AnimatedSpritesheet)item;
					if (a.useAnimatedRender(is)) {
						col = a.getColumn(is);
						int offset = (int)((System.currentTimeMillis()/32/a.getFrameSpeed())%a.getFrameCount(is));
						row = a.getBaseRow(is)+offset;
					}
				}

				float u = col/16F;
				float v = row/16F;

				double b = 0.25;
				double dx = 0.125;
				double dz = 0.905;
				double dy = 0.965-te.progress*0.00005;
				double s = 0.8;
				GL11.glPushMatrix();
				//GL11.glRotated(ang, 1, 0, 0);
				GL11.glTranslated(dx, dy, dz);
				//GL11.glTranslated(0, b, 0);
				GL11.glRotatef(-90, 1, 0, 0);
				//GL11.glTranslated(0, -b, 0);
				GL11.glScaled(s, s, s);
				if (Minecraft.getMinecraft().gameSettings.fancyGraphics)
					ItemRenderer.renderItemIn2D(v5, 0.0625F*col, 0.0625F*row, 0.0625F+0.0625F*col, 0.0625F+0.0625F*row, 256, 256, thick);
				else {
					v5.startDrawingQuads();
					v5.addVertexWithUV(0, 0, 0, 0.0625F*col, 0.0625F+0.0625F*row);
					v5.addVertexWithUV(1, 0, 0, 0.0625F+0.0625F*col, 0.0625F+0.0625F*row);
					v5.addVertexWithUV(1, 1, 0, 0.0625F+0.0625F*col, 0.0625F*row);
					v5.addVertexWithUV(0, 1, 0, 0.0625F*col, 0.0625F*row);
					v5.draw();
				}
				GL11.glPopMatrix();
			}
			else if (iir != null) {
				;//iir.renderItem(ItemRenderType.INVENTORY, is, new RenderBlocks());
			}
			else {
				if (ReikaItemHelper.isBlock(is))
					ReikaTextureHelper.bindTerrainTexture();
				else
					ReikaTextureHelper.bindItemTexture();
				IIcon ico = item.getIcon(is, MinecraftForgeClient.getRenderPass());
				if (ico == null)
					return;
				float u = ico.getMinU();
				float v = ico.getMinV();
				float du = ico.getMaxU();
				float dv = ico.getMaxV();

				double b = 0.65;
				double dx = 0.1;
				double dz = 0.125;
				double dy = 0.925-te.progress*0.00005;
				double s = 0.8;
				GL11.glPushMatrix();
				//GL11.glRotated(ang, 1, 0, 0);
				GL11.glTranslated(dx, dy, dz);
				//GL11.glTranslated(0, b, 0);
				GL11.glRotatef(90, 1, 0, 0);
				//GL11.glTranslated(0, -b, 0);
				GL11.glScaled(s, s, s);
				if (Minecraft.getMinecraft().gameSettings.fancyGraphics)
					ItemRenderer.renderItemIn2D(v5, u, v, du, dv, 256, 256, thick);
				else {
					v5.startDrawingQuads();
					v5.addVertexWithUV(0, 1, 0, u, v);
					v5.addVertexWithUV(1, 1, 0, du, v);
					v5.addVertexWithUV(1, 0, 0, du, dv);
					v5.addVertexWithUV(0, 0, 0, u, dv);
					v5.draw();
				}
				GL11.glPopMatrix();
			}
			GL11.glDisable(GL11.GL_BLEND);
			//GL11.glEnable(GL11.GL_CULL_FACE);
			//GL11.glEnable(GL11.GL_ALPHA_TEST);

		}
	}

	private void renderFluid(TileEntityDryingBed tile) {
		Fluid f = tile.getFluid();
		if (f != null) {
			ReikaTextureHelper.bindTerrainTexture();
			Tessellator v5 = Tessellator.instance;
			IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			int l = tile.getLevel();
			if (!f.equals(FluidRegistry.LAVA)) {
				GL11.glEnable(GL11.GL_BLEND);
			}
			double h = l > 0 ? 0.8125+l*0.125/tile.getCapacity() : 0.5;
			if (f.getLuminosity() > 0 && tile.hasWorldObj())
				ReikaRenderHelper.disableLighting();
			v5.startDrawingQuads();
			v5.setColorOpaque_I(0xffffff);
			v5.addVertexWithUV(0+0.0625, h, 1-0.0625, u, v);
			v5.addVertexWithUV(1-0.0625, h, 1-0.0625, du, v);
			v5.addVertexWithUV(1-0.0625, h, 0+0.0625, du, dv);
			v5.addVertexWithUV(0+0.0625, h, 0+0.0625, u, dv);
			v5.draw();
			ReikaRenderHelper.enableLighting();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

}

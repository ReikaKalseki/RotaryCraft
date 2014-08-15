/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DMI;

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelFillingStation;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFillingStation;

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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFillingStation extends RotaryTERenderer
{

	private ModelFillingStation FillingStationModel = new ModelFillingStation();
	//private ModelFillingStationV FillingStationModelV = new ModelFillingStationV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityFillingStationAt(TileEntityFillingStation tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelFillingStation var14;
		var14 = FillingStationModel;
		//ModelFillingStationV var15;
		//var14 = this.FillingStationModelV;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/fillingtex.png");

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
				var11 = 270;
				break;
			case 3:
				var11 = 90;
				break;
			}

			GL11.glRotatef((float)var11-90, 0.0F, 1.0F, 0.0F);
		}

		var14.renderAll(tile, null, -tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityFillingStationAt((TileEntityFillingStation)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			this.renderLiquid(tile, par2, par4, par6);
		}
		this.renderItem(tile, par2, par4, par6);
	}

	private void renderLiquid(TileEntity tile, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		TileEntityFillingStation tr = (TileEntityFillingStation)tile;
		double dx = 0;
		double dz = 0;
		double ddx = 0;
		double ddz = 0;
		switch(tr.getBlockMetadata()) {
		case 0:
			dx = 0.25;
			break;
		case 1:
			ddx = 0.25;
			break;
		case 2:
			dz = 0.25;
			break;
		case 3:
			ddz = 0.25;
			break;
		}
		if (!tr.isEmpty() && tr.isInWorld()) {
			Fluid f = tr.getFluid();
			if (!f.equals(FluidRegistry.LAVA)) {
				GL11.glEnable(GL11.GL_BLEND);
			}
			ReikaLiquidRenderer.bindFluidTexture(f);
			IIcon ico = f.getIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = 0.0625+14D/16D*tr.getLevel()/tr.CAPACITY;
			Tessellator v5 = Tessellator.instance;
			if (f.getLuminosity() > 0)
				ReikaRenderHelper.disableLighting();
			v5.startDrawingQuads();
			v5.setNormal(0, 1, 0);
			v5.addVertexWithUV(dx+0, h, -ddz+1, u, dv);
			v5.addVertexWithUV(-ddx+1, h, -ddz+1, du, dv);
			v5.addVertexWithUV(-ddx+1, h, dz+0, du, v);
			v5.addVertexWithUV(dx+0, h, dz+0, u, v);
			v5.draw();
			ReikaRenderHelper.enableLighting();
		}
		GL11.glTranslated(-par2, -par4, -par6);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void renderItem(TileEntity tile, double par2, double par4, double par6) {
		TileEntityFillingStation fs = (TileEntityFillingStation)tile;
		if (!fs.isInWorld())
			return;
		ItemStack is = fs.getItemForRender();
		if (is == null)
			return;

		double in = 0.125;
		double xoff = 0;
		double zoff = 0;

		float var11 = 0;
		switch(tile.getBlockMetadata()) {
		case 0:
			var11 = 180;
			break;
		case 1:
			var11 = 0;
			xoff = 1;
			zoff = -1;
			break;
		case 2:
			var11 = 270;
			in = -in;
			break;
		case 3:
			var11 = 90;
			xoff = 1;
			zoff = 1;
			in = -in;
			break;
		}

		GL11.glTranslated(par2, par4, par6);

		GL11.glRotatef(var11-90, 0.0F, 1.0F, 0.0F);

		GL11.glTranslated(xoff, 0, zoff);

		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();

		Item item = is.getItem();
		IItemRenderer iir = MinecraftForgeClient.getItemRenderer(is, ItemRenderType.INVENTORY);
		if (item instanceof IndexedItemSprites && !(item instanceof ItemBlockPlacer)) {
			IndexedItemSprites iis = (IndexedItemSprites)item;
			ReikaTextureHelper.bindTexture(iis.getTextureReferenceClass(), iis.getTexture(is));
			int index = iis.getItemSpriteIndex(is);
			int row = index/16;
			int col = index%16;

			float u = col/16F;
			float v = row/16F;

			float du = u+0.0625F;
			float dv = v+0.0625F;

			v5.addVertexWithUV(0, 0, in, u, dv);
			v5.addVertexWithUV(-1, 0, in, du, dv);
			v5.addVertexWithUV(-1, 1, in, du, v);
			v5.addVertexWithUV(0, 1, in, u, v);
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
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();

			v5.addVertexWithUV(0, 0, in, u, dv);
			v5.addVertexWithUV(-1, 0, in, du, dv);
			v5.addVertexWithUV(-1, 1, in, du, v);
			v5.addVertexWithUV(0, 1, in, u, v);
		}

		v5.draw();

		GL11.glTranslated(-xoff, 0, -zoff);

		GL11.glRotatef(-var11+90, 0.0F, 1.0F, 0.0F);

		GL11.glTranslated(-par2, -par4, -par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "fillingtex.png";
	}
}
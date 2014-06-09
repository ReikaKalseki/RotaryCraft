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

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelGrindstone;
import Reika.RotaryCraft.TileEntities.TileEntityGrindstone;

public class RenderGrindstone extends RotaryTERenderer
{

	private ModelGrindstone GrindstoneModel = new ModelGrindstone();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityGrindstoneAt(TileEntityGrindstone tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelGrindstone var14;
		var14 = GrindstoneModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/grindstonetex.png");

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
				var11 = 90;
				break;
			}
			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
		}

		float var13;

		var14.renderAll(tile, null, tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityGrindstoneAt((TileEntityGrindstone)tile, par2, par4, par6, par8);
		this.renderTool((TileEntityGrindstone)tile, par2, par4, par6);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	private void renderTool(TileEntityGrindstone tile, double par2, double par4, double par6) {
		ItemStack is = tile.getStackInSlot(0);
		if (is == null)
			return;
		if (ReikaItemHelper.isBlock(is) || is.getItem() instanceof ItemBlockPlacer)
			return;

		double in = 0.125;
		double off = 0;
		float ang = 12;

		float var11 = 0;
		switch(tile.getBlockMetadata()) {
		case 0:
			var11 = 0;
			ang = -12;
			break;
		case 1:
			var11 = 90;
			off = 0.0625;
			break;
		}


		GL11.glTranslated(par2, par4, par6);
		GL11.glTranslated(0, 0, off);

		GL11.glRotatef(var11-90, 0.0F, 1.0F, 0.0F);

		Tessellator v5 = Tessellator.instance;
		float thick = 0.0625F;
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

			double b = 0.25;
			double dy = 0.5;
			GL11.glRotated(ang, 1, 0, 0);
			GL11.glTranslated(0, dy, 0);
			GL11.glTranslated(0, b, 0);
			GL11.glRotatef(-45, 0, 0, 1);
			GL11.glTranslated(0, -b, 0);
			ItemRenderer.renderItemIn2D(v5, 0.0625F+0.0625F*col, 0.0625F*row, 0.0625F*col, 0.0625F+0.0625F*row, 256, 256, thick);
			GL11.glTranslated(0, b, 0);
			GL11.glRotatef(45, 0, 0, 1);
			GL11.glTranslated(0, -b, 0);
			GL11.glTranslated(0, -dy, 0);
			GL11.glRotated(-ang, 1, 0, 0);
		}
		else if (iir != null) {
			;//iir.renderItem(ItemRenderType.INVENTORY, is, new RenderBlocks());
		}
		else {
			if (ReikaItemHelper.isBlock(is))
				ReikaTextureHelper.bindTerrainTexture();
			else
				ReikaTextureHelper.bindItemTexture();
			Icon ico = item.getIcon(is, MinecraftForgeClient.getRenderPass());
			if (ico == null)
				return;
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();

			double b = 0.65;
			double dy = -0.25;
			GL11.glRotated(ang, 1, 0, 0);
			GL11.glTranslated(0, dy, 0);
			GL11.glTranslated(0, b, 0);
			GL11.glRotatef(45, 0, 0, 1);
			GL11.glTranslated(0, -b, 0);
			ItemRenderer.renderItemIn2D(v5, u, v, du, dv, 256, 256, thick);
			GL11.glTranslated(0, b, 0);
			GL11.glRotatef(-45, 0, 0, 1);
			GL11.glTranslated(0, -b, 0);
			GL11.glTranslated(0, -dy, 0);
			GL11.glRotated(-ang, 1, 0, 0);
		}

		GL11.glRotatef(-var11+90, 0.0F, 1.0F, 0.0F);

		GL11.glTranslated(0, 0, -off);
		GL11.glTranslated(-par2, -par4, -par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "grindstonetex.png";
	}
}

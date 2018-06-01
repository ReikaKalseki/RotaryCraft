/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank.TankFlags;

public class DecoTankItemRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		RenderBlocks rb = (RenderBlocks)data[0];
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTerrainTexture();
		//GL11.glDisable(GL11.GL_CULL_FACE);
		Block b = BlockRegistry.DECOTANK.getBlockInstance();
		if (item.stackTagCompound != null) {
			Fluid f = ReikaNBTHelper.getFluidFromNBT(item.stackTagCompound);
			if (f != null) {
				IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
				if (f.getLuminosity() == 15 || TankFlags.LIGHTED.apply(item))
					GL11.glDisable(GL11.GL_LIGHTING);

				float u = ico.getMinU();
				float du = ico.getMaxU();
				float v = ico.getMinV();
				float dv = ico.getMaxV();
				Tessellator v5 = Tessellator.instance;
				float dx = -0.5F, dy = -0.5F, dz = -0.5F;
				v5.startDrawingQuads();
				v5.addTranslation(dx, dy, dz);
				if (TankFlags.NOCOLOR.apply(item))
					v5.setColorOpaque_I(0xffffff);
				else {
					int c = f.getColor();
					if (c == 0xffffff && f.canBePlacedInWorld()) {
						EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
						if (ep != null) {
							c = f.getBlock().colorMultiplier(ep.worldObj, MathHelper.floor_double(ep.posX), MathHelper.floor_double(ep.posY), MathHelper.floor_double(ep.posZ));
						}
					}
					v5.setColorOpaque_I(c);
				}
				v5.setBrightness(240);
				v5.addVertexWithUV(0, 1, 1, u, dv);
				v5.addVertexWithUV(1, 1, 1, du, dv);
				v5.addVertexWithUV(1, 1, 0, du, v);
				v5.addVertexWithUV(0, 1, 0, u, v);

				v5.addVertexWithUV(0, 0, 0, u, v);
				v5.addVertexWithUV(1, 0, 0, du, v);
				v5.addVertexWithUV(1, 0, 1, du, dv);
				v5.addVertexWithUV(0, 0, 1, u, dv);

				v5.addVertexWithUV(0, 0, 1, u, dv);
				v5.addVertexWithUV(0, 1, 1, du, dv);
				v5.addVertexWithUV(0, 1, 0, du, v);
				v5.addVertexWithUV(0, 0, 0, u, v);

				v5.addVertexWithUV(1, 0, 0, u, v);
				v5.addVertexWithUV(1, 1, 0, du, v);
				v5.addVertexWithUV(1, 1, 1, du, dv);
				v5.addVertexWithUV(1, 0, 1, u, dv);

				v5.addVertexWithUV(0, 0, 1, u, v);
				v5.addVertexWithUV(1, 0, 1, du, v);
				v5.addVertexWithUV(1, 1, 1, du, dv);
				v5.addVertexWithUV(0, 1, 1, u, dv);

				v5.addVertexWithUV(0, 1, 0, u, dv);
				v5.addVertexWithUV(1, 1, 0, du, dv);
				v5.addVertexWithUV(1, 0, 0, du, v);
				v5.addVertexWithUV(0, 0, 0, u, v);
				v5.addTranslation(-dx, -dy, -dz);
				v5.draw();
			}
		}
		rb.renderBlockAsItem(b, item.getItemDamage(), 1);
		GL11.glPopAttrib();
	}


}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.IItemRenderer;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078DecoTank.TankFlags;

4578ret87fhyuog DecoTankItemRenderer ,.[]\., IItemRenderer {

	@Override
	4578ret8760-78-078handleRenderType{{\ItemStack item, ItemRenderType type-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078shouldUseRenderHelper{{\ItemRenderType type, ItemStack item, ItemRendererHelper helper-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void renderItem{{\ItemRenderType type, ItemStack item, Object... data-! {
		RenderBlocks rb3478587{{\RenderBlocks-!data[0];
		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		//GL11.glDisable{{\GL11.GL_CULL_FACE-!;
		Block b3478587BlockRegistry.DECOTANK.getBlockInstance{{\-!;
		vbnm, {{\item.stackTagCompound !. fhfglhuig-! {
			Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\item.stackTagCompound-!;
			vbnm, {{\f !. fhfglhuig-! {
				IIcon ico3478587f.getStillIcon{{\-!;
				vbnm, {{\f.getLuminosity{{\-! .. 15 || TankFlags.LIGHTED.apply{{\item-!-!
					GL11.glDisable{{\GL11.GL_LIGHTING-!;
				float u3478587ico.getMinU{{\-!;
				float du3478587ico.getMaxU{{\-!;
				float v3478587ico.getMinV{{\-!;
				float dv3478587ico.getMaxV{{\-!;
				Tessellator v53478587Tessellator.instance;
				float dx3478587-0.5F, dy3478587-0.5F, dz3478587-0.5F;
				v5.startDrawingQuads{{\-!;
				v5.addTranslation{{\dx, dy, dz-!;
				vbnm, {{\TankFlags.NOCOLOR.apply{{\item-!-!
					v5.setColorOpaque_I{{\0xffffff-!;
				else
					v5.setColorOpaque_I{{\f.getColor{{\-!-!;
				v5.setBrightness{{\240-!;
				v5.addVertexWithUV{{\0, 1, 1, u, dv-!;
				v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
				v5.addVertexWithUV{{\1, 1, 0, du, v-!;
				v5.addVertexWithUV{{\0, 1, 0, u, v-!;

				v5.addVertexWithUV{{\0, 0, 0, u, v-!;
				v5.addVertexWithUV{{\1, 0, 0, du, v-!;
				v5.addVertexWithUV{{\1, 0, 1, du, dv-!;
				v5.addVertexWithUV{{\0, 0, 1, u, dv-!;

				v5.addVertexWithUV{{\0, 0, 1, u, dv-!;
				v5.addVertexWithUV{{\0, 1, 1, du, dv-!;
				v5.addVertexWithUV{{\0, 1, 0, du, v-!;
				v5.addVertexWithUV{{\0, 0, 0, u, v-!;

				v5.addVertexWithUV{{\1, 0, 0, u, v-!;
				v5.addVertexWithUV{{\1, 1, 0, du, v-!;
				v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
				v5.addVertexWithUV{{\1, 0, 1, u, dv-!;

				v5.addVertexWithUV{{\0, 0, 1, u, v-!;
				v5.addVertexWithUV{{\1, 0, 1, du, v-!;
				v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
				v5.addVertexWithUV{{\0, 1, 1, u, dv-!;

				v5.addVertexWithUV{{\0, 1, 0, u, dv-!;
				v5.addVertexWithUV{{\1, 1, 0, du, dv-!;
				v5.addVertexWithUV{{\1, 0, 0, du, v-!;
				v5.addVertexWithUV{{\0, 0, 0, u, v-!;
				v5.addTranslation{{\-dx, -dy, -dz-!;
				v5.draw{{\-!;
			}
		}
		rb.renderBlockAsItem{{\b, item.getItemDamage{{\-!, 1-!;
		GL11.glPopAttrib{{\-!;
	}


}

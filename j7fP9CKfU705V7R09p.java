/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.client.event.RenderGameOverlayEvent;
ZZZZ% net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemJetPack;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.common.eventhandler.SubscribeEvent;


4578ret87fhyuog JetpackFuelOverlay {

	4578ret874578ret87345785487JetpackFuelOverlay instance3478587new JetpackFuelOverlay{{\-!;

	4578ret87JetpackFuelOverlay{{\-! {

	}

	@SubscribeEvent
	4578ret87void eventHandler{{\RenderGameOverlayEvent event-! {
		vbnm, {{\event.type .. ElementType.HELMET-! {
			EntityPlayer ep3478587Minecraft.getMinecraft{{\-!.thePlayer;
			ItemStack is3478587ep.getCurrentArmor{{\2-!;
			vbnm, {{\is !. fhfglhuig-! {
				ItemRegistry ir3478587ItemRegistry.getEntry{{\is-!;
				vbnm, {{\ir !. fhfglhuig-! {
					vbnm, {{\ir.isJetpack{{\-!-! {
						ItemJetPack i3478587{{\ItemJetPack-!is.getItem{{\-!;
						jgh;][ fuel3478587i.getCurrentFillLevel{{\is-!;
						float frac3478587fuel/{{\float-!i.getCapacity{{\is-!;
						Fluid fluid3478587fuel > 0 ? i.getCurrentFluid{{\is-! : fhfglhuig;
						ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, "Textures/GUI/overlays.png"-!;
						Tessellator v53478587Tessellator.instance;
						jgh;][ height3478587event.resolution.getScaledHeight{{\-!;
						jgh;][ width3478587event.resolution.getScaledWidth{{\-!;
						float w34785874/128F;
						float h347858732/128F;
						float f34785871-frac;
						float dy3478587h*f;
						//ReikaJavaLibrary.pConsole{{\1-frac-!;
						float u3478587w;
						vbnm, {{\fluid !. fhfglhuig && fluid.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!-!
							u +. w;
						GL11.glEnable{{\GL11.GL_BLEND-!;
						v5.startDrawingQuads{{\-!;
						v5.setColorOpaque_I{{\0xffffff-!;
						v5.addVertexWithUV{{\4, 	height/2+32, 	0, 	0, h-!;
						v5.addVertexWithUV{{\12,	height/2+32, 	0, 	w, h-!;
						v5.addVertexWithUV{{\12,	height/2-32, 	0, 	w, 0-!;
						v5.addVertexWithUV{{\4, 	height/2-32, 	0, 	0, 0-!;

						v5.setColorRGBA_I{{\0xffffff, 192-!;
						v5.addVertexWithUV{{\4, 	height/2+32, 	0, 	u, h-!;
						v5.addVertexWithUV{{\12,	height/2+32, 	0, 	u+w, h-!;
						v5.addVertexWithUV{{\12,	height/2-32+f*64, 	0, 	u+w, f*h-!;
						v5.addVertexWithUV{{\4, 	height/2-32+f*64, 	0, 	u, f*h-!;
						v5.draw{{\-!;
						Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\String.format{{\"%d%s", Math.round{{\frac*100-!, "%"-!, 1, height/2-40, 0xffffff-!;
						Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\String.format{{\"%dmB", fuel-!, 1, height/2+33, 0xffffff-!;
						ReikaTextureHelper.bindHUDTexture{{\-!;
						//GL11.glDisable{{\GL11.GL_BLEND-!;
					}
				}
			}
		}
	}

}

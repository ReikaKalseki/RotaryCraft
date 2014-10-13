/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


public class JetpackFuelOverlay {

	public static final JetpackFuelOverlay instance = new JetpackFuelOverlay();

	private JetpackFuelOverlay() {

	}

	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent event) {
		if (event.type == ElementType.HELMET) {
			EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
			ItemStack is = ep.getCurrentArmor(2);
			if (is != null) {
				ItemRegistry ir = ItemRegistry.getEntry(is);
				if (ir != null) {
					if (ir.isJetpack()) {
						ItemJetPack i = (ItemJetPack)is.getItem();
						int fuel = i.getCurrentFillLevel(is);
						float frac = fuel/(float)i.getCapacity(is);
						Fluid fluid = fuel > 0 ? i.getCurrentFluid(is) : null;
						ReikaTextureHelper.bindTexture(RotaryCraft.class, "Textures/GUI/overlays.png");
						Tessellator v5 = Tessellator.instance;
						int height = event.resolution.getScaledHeight();
						int width = event.resolution.getScaledWidth();
						float w = 4/128F;
						float h = 32/128F;
						float f = 1-frac;
						float dy = h*f;
						//ReikaJavaLibrary.pConsole(1-frac);
						float u = w;
						if (fluid != null && fluid.equals(FluidRegistry.getFluid("jet fuel")))
							u += w;
						GL11.glEnable(GL11.GL_BLEND);
						v5.startDrawingQuads();
						v5.setColorOpaque_I(0xffffff);
						v5.addVertexWithUV(4, 	height/2+32, 	0, 	0, h);
						v5.addVertexWithUV(12,	height/2+32, 	0, 	w, h);
						v5.addVertexWithUV(12,	height/2-32, 	0, 	w, 0);
						v5.addVertexWithUV(4, 	height/2-32, 	0, 	0, 0);

						v5.setColorRGBA_I(0xffffff, 192);
						v5.addVertexWithUV(4, 	height/2+32, 	0, 	u, h);
						v5.addVertexWithUV(12,	height/2+32, 	0, 	u+w, h);
						v5.addVertexWithUV(12,	height/2-32+f*64, 	0, 	u+w, f*h);
						v5.addVertexWithUV(4, 	height/2-32+f*64, 	0, 	u, f*h);
						v5.draw();
						Minecraft.getMinecraft().fontRenderer.drawString(String.format("%d%s", (int)(frac*100), "%"), 1, height/2-40, 0xffffff);
						Minecraft.getMinecraft().fontRenderer.drawString(String.format("%dmB", fuel), 1, height/2+33, 0xffffff);
						ReikaTextureHelper.bindHUDTexture();
						GL11.glDisable(GL11.GL_BLEND);
					}
				}
			}
		}
	}

}

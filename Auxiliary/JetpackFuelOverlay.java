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

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


public class JetpackFuelOverlay {

	public static final JetpackFuelOverlay instance = new JetpackFuelOverlay();

	private JetpackFuelOverlay() {

	}

	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent event) {
		//if (event.type == ElementType.HEALTH) {
		EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
		ItemStack is = ep.getCurrentArmor(2);
		if (is != null) {
			ItemRegistry ir = ItemRegistry.getEntry(is);
			if (ir != null) {
				if (ir.isJetpack()) {
					ItemJetPack i = (ItemJetPack)is.getItem();
					int fuel = i.getCurrentFillLevel(is);
					float frac = fuel/(float)i.getCapacity(is);
					ReikaTextureHelper.bindTexture(RotaryCraft.class, "Textures/GUI/overlays.png");
					Tessellator v5 = Tessellator.instance;
					int height = event.resolution.getScaledHeight();
					int width = event.resolution.getScaledWidth();
					float w = 4/128F;
					float h = 32/128F;
					float f = 1-frac;
					float dy = h*f;
					//ReikaJavaLibrary.pConsole(1-frac);
					v5.startDrawingQuads();
					v5.addVertexWithUV(4, 	height/2+32, 	0, 	0, h);
					v5.addVertexWithUV(12,	height/2+32, 	0, 	w, h);
					v5.addVertexWithUV(12,	height/2-32, 	0, 	w, 0);
					v5.addVertexWithUV(4, 	height/2-32, 	0, 	0, 0);

					v5.addVertexWithUV(4, 	height/2+32, 	0, 	w, h);
					v5.addVertexWithUV(12,	height/2+32, 	0, 	w*2, h);
					v5.addVertexWithUV(12,	height/2-32+f*64, 	0, 	w*2, f*h);
					v5.addVertexWithUV(4, 	height/2-32+f*64, 	0, 	w, f*h);
					v5.draw();
					Minecraft.getMinecraft().fontRenderer.drawString(String.format("%d%s", (int)(frac*100), "%"), 1, height/2-40, 0xffffff);
					Minecraft.getMinecraft().fontRenderer.drawString(String.format("%dmB", fuel), 1, height/2+33, 0xffffff);
					ReikaTextureHelper.bindHUDTexture();
				}
			}
		}
		//}
	}

}
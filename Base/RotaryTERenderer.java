/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.awt.Color;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.TileEntityRenderBase;
import Reika.DragonAPI.Interfaces.TextureFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.RotaryCraft.GLListData;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RotaryTERenderer extends TileEntityRenderBase implements TextureFetcher {

	//public abstract void createLists();

	private void callList(RotaryCraftTileEntity te, int i) {
		try {
			int li = GLListData.getMachineRenderList(te.getMachine(), i);
			GL11.glCallList(li);
		}
		catch (ClassCastException e) {

		}
		catch (NullPointerException e) {

		}
	}

	@Override
	public final String getTextureFolder() {
		return "/Reika/RotaryCraft/Textures/TileEntityTex/";
	}

	protected void renderFaceColors(TileEntityIOMachine te, double p2, double p4, double p6) {
		double offset = 0.0625;
		int alpha = te.iotick;
		Color[] colors = RotaryAux.sideColors;
		ReikaRenderHelper.prepareGeoDraw(true);
		Tessellator v5 = new Tessellator();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[1].getRed(), colors[1].getGreen(), colors[1].getBlue(), alpha);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[1].getRed(), colors[1].getGreen(), colors[1].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[2].getRed(), colors[2].getGreen(), colors[2].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[2].getRed(), colors[2].getGreen(), colors[2].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[3].getRed(), colors[3].getGreen(), colors[3].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[3].getRed(), colors[3].getGreen(), colors[3].getBlue(), (int)(alpha/2.4));
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[5].getRed(), colors[5].getGreen(), colors[5].getBlue(), alpha);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[5].getRed(), colors[5].getGreen(), colors[5].getBlue(), alpha/3);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.draw();

		for (int i = 0; i < 6; i++) {
			int a = 0; int b = 0; int c = 0;
			switch(i) {
			case 0:
				b = -3;
				break;
			case 1:
				b = 3;
				break;
			case 2:
				c = -3;
				break;
			case 3:
				c = 3;
				break;
			case 4:
				a = -3;
				break;
			case 5:
				a = 3;
				break;
			}
			v5.startDrawing(GL11.GL_LINES);
			v5.setColorRGBA(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), alpha);
			v5.addVertex(p2+0.5, p4+0.5, p6+0.5);
			v5.addVertex(p2+0.5+a, p4+0.5+b, p6+0.5+c);
			v5.draw();
		}

		ReikaRenderHelper.exitGeoDraw();
	}
}

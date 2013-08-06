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
import java.awt.image.BufferedImage;
import java.util.HashMap;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.TextureFetcher;
import Reika.DragonAPI.Libraries.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RotaryTERenderer extends TileEntitySpecialRenderer implements TextureFetcher {

	/*
	public RotaryTERenderer(TileEntity tile) {
		te = tile;
	}*/

	BufferedImage tex = null;
	HashMap textures = new HashMap();
	float phi = 0;

	@Override
	public void bindTextureByName(String file) {/*
        RenderEngine renderengine = this.tileEntityRenderer.renderEngine;
        //String filename = RotaryCraft.class.getResource(file).getPath();
        //ReikaJavaLibrary.pConsole(getClass().getClassLoader().getResource(".").getPath());
        String filename;
        if (this.getClass() == null)
        	return;
        if (this.getClass().getClassLoader() == null)
        	return;
        if (this.getClass().getClassLoader().getResource(".") == null)
        	filename = null;
        else {
	        String base = getClass().getClassLoader().getResource(".").getPath();
	        String path = base.substring(1, base.length()-1);
	        filename = path+file;
        }
        //ReikaJavaLibrary.pConsole(filename);
        if (renderengine != null)
        {
        	int img = 0;
        	String name = this.getImageFileName();
        	if (textures.containsKey(name)) {
        		img = (Integer)textures.get(name);
        		//ReikaJavaLibrary.pConsole(68);
        	}
        	else {
        		tex = ReikaPNGLoader.readTextureImage(filename, RotaryAux.tileentdir+name);
            	img = renderengine.allocateAndSetupTexture(tex);
        		textures.put(name, img);
        	}
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, img);
        }*/
		String filename = file.substring(18, file.length());
		super.bindTextureByName(file);
	}

	//public abstract String getImageFileName(RotaryCraftTileEntity te);

	public final boolean isValidMachineRenderpass(RotaryCraftTileEntity te) {
		if (!te.isInWorld())
			return true;
		int pass = MinecraftForgeClient.getRenderPass();
		if (!te.shouldRenderInPass(pass))
			return false;
		if (pass == 0)
			return true;
		if (pass == 1)
			return (te.hasModelTransparency());
		return false;
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

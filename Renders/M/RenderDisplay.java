/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelDisplay;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityDisplay;

public class RenderDisplay extends RotaryTERenderer {

	private ModelDisplay displayModel = new ModelDisplay();

	public void renderTileEntityDisplayAt(TileEntityDisplay tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelDisplay var14;
		var14 = displayModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/displaytex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 1;	 //used to rotate the model about metadata
		switch(var9) {
		case 0:
			var11 = 0;
			break;
		case 1:
			var11 = 180;
			break;
		case 2:
			var11 = 90;
			break;
		case 3:
			var11 = 270;
			break;
		}
		GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
		var14.renderAll(tile, null, -tile.phi, 0);
		GL11.glScaled(1, var11, 1);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityDisplayAt((TileEntityDisplay)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			if (((TileEntityDisplay)tile).canDisplay() && ((TileEntityDisplay)tile).hasSpace()) {
				((TileEntityDisplay)tile).loadColorData();
				int dir = 0;
				int dx = 0;
				int dz = 0;
				switch(tile.getBlockMetadata()) {
				case 0:
					dir = 270;
					dx = 1;
					break;
				case 1:
					dir = 90;
					dz = 1;
					break;
				case 2:
					dir = 180;
					dz = 1;
					dx = 1;
					break;
				case 3:
					dir = 0;
					break;
				}
				GL11.glPushMatrix();
				GL11.glTranslated(par2, par4, par6);
				GL11.glTranslated(dx, 0, dz);
				GL11.glRotatef(dir, 0, 1, 0);
				this.renderScreen((TileEntityDisplay)tile, par2, par4, par6);
				this.renderText((TileEntityDisplay)tile, par2, par4, par6);
				GL11.glRotatef(-dir, 0, 1, 0);
				GL11.glTranslated(-dx, 0, -dz);
				GL11.glPopMatrix();
			}
		}
	}

	private void renderScreen(TileEntityDisplay tile, double par2, double par4,	double par6) {
		if (tile == null)
			return;
		GL11.glTranslated(0, 0, 0.495);
		ReikaRenderHelper.prepareGeoDraw(true);
		Tessellator v5 = Tessellator.instance;
		int r = tile.getRed();
		int g = tile.getGreen();
		int b = tile.getBlue();
		int br = tile.getBorderRed();
		int bg = tile.getBorderGreen();
		int bb = tile.getBorderBlue();
		v5.startDrawingQuads();
		v5.setColorRGBA(r, g, b, 96);
		v5.addVertex(-2, 4, 0);
		v5.addVertex(3, 4, 0);
		v5.addVertex(3, 1, 0);
		v5.addVertex(-2, 1, 0);
		v5.draw();

		double dd = 0.03125;
		double dx = dd;
		double dy = dd;
		double dz = 0;

		GL11.glTranslated(0, 0, 0.0005);

		v5.startDrawingQuads();
		v5.setColorRGBA(br, bg, bb, 255);
		v5.addVertex(-2, 4, 0);
		v5.addVertex(3, 4, 0);
		v5.addVertex(3, 4-dy, 0);
		v5.addVertex(-2, 4-dy, 0);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(br, bg, bb, 255);
		v5.addVertex(-2, 1, 0);
		v5.addVertex(3, 1, 0);
		v5.addVertex(3, 1+dy, 0);
		v5.addVertex(-2, 1+dy, 0);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(br, bg, bb, 255);
		v5.addVertex(3, 4, 0);
		v5.addVertex(3, 1, 0);
		v5.addVertex(3-dx, 1, 0);
		v5.addVertex(3-dx, 4, 0);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(br, bg, bb, 255);
		v5.addVertex(-2, 4, 0);
		v5.addVertex(-2, 1, 0);
		v5.addVertex(-2+dx, 1, 0);
		v5.addVertex(-2+dx, 4, 0);
		v5.draw();


		v5.startDrawing(GL11.GL_LINES);
		v5.setColorRGBA(br, bg, bb, 32);
		float vspacing = 0.0625F;
		float hspacing = 0.25F;
		for (float k = 1+vspacing; k < 4; k += vspacing) {
			v5.addVertex(-2, k, 0);
			v5.addVertex(3, k, 0);
		}
		for (float k = -2+hspacing; k < 3; k += hspacing) {
			v5.addVertex(k, 4, 0);
			v5.addVertex(k, 1, 0);
		}
		v5.draw();

		GL11.glTranslated(0, 0, -0.0005);

		ReikaRenderHelper.exitGeoDraw();
		GL11.glTranslated(0, 0, -0.495);
	}

	private void renderText(TileEntityDisplay tile, double par2, double par4, double par6) {
		if (tile == null)
			return;
		if (!tile.hasList())
			return;
		FontRenderer f = this.getFontRenderer();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glPushMatrix();
		GL11.glScaled(1, -1, 1);
		double sc = 0.02;
		GL11.glScaled(sc, sc, sc);
		GL11.glTranslated(0, -50, 25);
		GL11.glTranslated(0, -tile.displayHeight*tile.lineHeight, 0);
		int dd = 100-tile.charWidth/4;
		int dx = -dd;
		int dz = 0;
		GL11.glTranslated(dx, 0, dz);

		List<String> cache = tile.getMessageForDisplay();

		long core = tile.getTick();//System.currentTimeMillis();
		float scroll = cache.size() > tile.displayHeight ? (core*4)%(180*cache.size())/180F : 0;
		int linescroll = scroll-(int)scroll > 0.5F ? (int)scroll+1 : (int)scroll;//tile.getRoundedScroll();
		//ReikaJavaLibrary.pConsole(tile.getMessageLine(0));
		int len = ReikaMathLibrary.extrema(cache.size()-1, tile.displayHeight+linescroll-1, "min");
		for (int i = linescroll; i < len+1; i++) {
			String s2 = cache.get(i);
			//ReikaJavaLibrary.pConsole("Printing line "+i+" of "+tile.getMessageLength()+": "+tile.getMessageLine(i));
			//f.drawString(tile.getMessageLine(i), 1, -1+(int)((i-scroll)*tile.lineHeight), tile.getTextColor());
			//f.drawSplitString(s, 1, -1+(int)((i-scroll)*tile.lineHeight), tile.displayWidth*f.FONT_HEIGHT, tile.getTextColor());
			f.drawString(s2, 1, -1+(int)((i-scroll)*tile.lineHeight), tile.getTextColor());
			GL11.glTranslated(0, 0, -0.2875);
			//f.drawString(tile.getMessageLine(i), 1, -1+(int)((i-scroll)*tile.lineHeight), tile.getTextColor());
			f.drawString(s2, 1, -1+(int)((i-scroll)*tile.lineHeight), tile.getTextColor());
			GL11.glTranslated(0, 0, 0.2875);

		}
		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "displaytex.png";
	}
}

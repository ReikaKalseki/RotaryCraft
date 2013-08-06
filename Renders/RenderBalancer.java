/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import java.awt.Color;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.ModInterface.TileEntityPressureBalancer;
import Reika.RotaryCraft.Models.ModelFullBlock;

public class RenderBalancer extends RotaryTERenderer
{

	private ModelFullBlock BalancerModel = new ModelFullBlock();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityPressureBalancerAt(TileEntityPressureBalancer tile, double par2, double par4, double par6, float par8)
	{
		ModelFullBlock var14;
		var14 = BalancerModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/balancertex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;
		float var13;

		var14.renderAll(null, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityPressureBalancerAt((TileEntityPressureBalancer)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			//this.renderFaceColors((TileEntityPressureBalancer)tile, par2, par4, par6);
		}
	}

	private void renderFaceColors(TileEntityPressureBalancer tile, double par2, double par4, double par6) {

		double out = 0.001;
		double in = 0.3125;

		int alpha = 255;

		GL11.glTranslated(par2, par4, par6);

		ReikaRenderHelper.prepareGeoDraw(alpha < 255);

		Tessellator v5 = new Tessellator();

		Color[] colors = RotaryAux.sideColors;

		int up = ForgeDirection.UP.ordinal();
		int down = ForgeDirection.DOWN.ordinal();

		int minx = ForgeDirection.WEST.ordinal();
		int maxx = ForgeDirection.EAST.ordinal();

		int minz = ForgeDirection.NORTH.ordinal();
		int maxz = ForgeDirection.SOUTH.ordinal();

		v5.startDrawingQuads();
		v5.setColorRGBA(colors[up].getRed(), colors[up].getGreen(), colors[up].getBlue(), alpha);
		v5.addVertex(in, 1+out, 1-in);
		v5.addVertex(1-in, 1+out, 1-in);
		v5.addVertex(1-in, 1+out, in);
		v5.addVertex(in, 1+out, in);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(colors[down].getRed(), colors[down].getGreen(), colors[down].getBlue(), alpha);
		v5.addVertex(in, -out, in);
		v5.addVertex(1-in, -out, in);
		v5.addVertex(1-in, -out, 1-in);
		v5.addVertex(in, -out, 1-in);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(colors[minx].getRed(), colors[minx].getGreen(), colors[minx].getBlue(), alpha);
		v5.addVertex(-out, in, in);
		v5.addVertex(-out, 1-in, in);
		v5.addVertex(-out, 1-in, 1-in);
		v5.addVertex(-out, in, 1-in);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(colors[maxx].getRed(), colors[maxx].getGreen(), colors[maxx].getBlue(), alpha);
		v5.addVertex(1+out, in, in);
		v5.addVertex(1+out, 1-in, in);
		v5.addVertex(1+out, 1-in, 1-in);
		v5.addVertex(1+out, in, 1-in);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(colors[minz].getRed(), colors[minz].getGreen(), colors[minz].getBlue(), alpha);
		v5.addVertex(in, in, -out);
		v5.addVertex(in, 1-in, -out);
		v5.addVertex(1-in, 1-in, -out);
		v5.addVertex(1-in, in, -out);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorRGBA(colors[maxz].getRed(), colors[maxz].getGreen(), colors[maxz].getBlue(), alpha);
		v5.addVertex(in, in, 1+out);
		v5.addVertex(in, 1-in, 1+out);
		v5.addVertex(1-in, 1-in, 1+out);
		v5.addVertex(1-in, in, 1+out);
		v5.draw();

		ReikaRenderHelper.exitGeoDraw();

		GL11.glTranslated(-par2, -par4, -par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "balancertex.png";
	}
}

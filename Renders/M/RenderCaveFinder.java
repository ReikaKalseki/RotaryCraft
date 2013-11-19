/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import java.awt.Color;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelCave;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;

public class RenderCaveFinder extends RotaryTERenderer {

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "cavetex.png";
	}

	private ModelCave caveModel = new ModelCave();
	//private ModelBlockV caveModelV = new ModelBlockV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityCaveFinderAt(TileEntityCaveFinder tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelCave var14;
		var14 = caveModel;
		//ModelBlockV var15;
		//var14 = this.caveModelV;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/cavetex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
		float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
		// if (tile.getBlockMetadata() < 4)

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
			this.renderTileEntityCaveFinderAt((TileEntityCaveFinder)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
		this.renderPoints((TileEntityCaveFinder)tile, par2, par4, par6);
	}

	public void renderPoints(TileEntityCaveFinder te, double p2, double p4, double p6) {
		if (te == null)
			return;
		if (!te.isInWorld())
			return;
		if (!te.on)
			return;
		int range = te.getRange();
		ReikaRenderHelper.disableLighting();
		GL11.glPushMatrix();
		GL11.glTranslated(p2, p4+2, p6+1);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glPopMatrix();
		//GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glPointSize(2F);
		//ReikaJavaLibrary.pConsole(te.getSourceZ());
		GL11.glTranslated(-te.xCoord+te.getSourceX(), -te.yCoord+te.getSourceY(), -te.zCoord+te.getSourceZ());
		GL11.glBegin(GL11.GL_POINTS);
		for (int i = -range; i <= range+1; i++) {
			for (int j = -range; j <= range+1; j++) {
				for (int k = -range; k <= range+1; k++) {
					if (ReikaWorldHelper.cornerHasAirAdjacent(te.worldObj, i+te.getSourceX(), j+te.getSourceY(), k+te.getSourceZ())) {
						//ReikaJavaLibrary.pConsole(range+"    "+i+"  "+j+"  "+k);

						double[] color = new double[3];
						int sc = 32;
						int vsc = 64;
						int hexcolor;
						//hexcolor = Color.HSBtoRGB(((Math.abs(i+te.getSourceX())%sc))/(float)sc, 1, 1);
						hexcolor = Color.HSBtoRGB(((((Math.abs(j+te.getSourceY())-12)%vsc)))/(float)vsc, 1, 1);
						//hexcolor = Color.HSBtoRGB((((Math.abs(i+te.getSourceX())+(Math.abs(j+te.getSourceY()))+(Math.abs(k+te.getSourceZ())))%(sc*3)))/(float)sc, 1, 1);
						color[0] = ReikaRenderHelper.HextoColorMultiplier(hexcolor, 0);
						color[1] = ReikaRenderHelper.HextoColorMultiplier(hexcolor, 1);
						color[2] = ReikaRenderHelper.HextoColorMultiplier(hexcolor, 2);
						GL11.glColor3d(color[0], color[1], color[2]);
						GL11.glVertex3d(p2+i, p4+j, p6+k);
						double spread = 0*0.03125/8;
						if (spread > 0) {
							for (double m = -spread; m <= spread; m+= spread/2D) {
								for (double l = -spread; l <= spread; l+= spread/2D) {
									for (double n = -spread; n <= spread; n+= spread/2D) {
										GL11.glVertex3d(p2+i+m, p4+j+l, p6+k+n);
									}
								}
							}
						}
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glColor3d(1, 1, 1);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(p2-range, p4-range, p6-range);
		GL11.glVertex3d(p2+1+range, p4-range, p6-range);
		GL11.glVertex3d(p2+1+range, p4-range, p6+1+range);
		GL11.glVertex3d(p2-range, p4-range, p6+1+range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(p2-range, p4+1+range, p6-range);
		GL11.glVertex3d(p2+1+range, p4+1+range, p6-range);
		GL11.glVertex3d(p2+1+range, p4+1+range, p6+1+range);
		GL11.glVertex3d(p2-range, p4+1+range, p6+1+range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(p2-range, p4-range, p6-range);
		GL11.glVertex3d(p2-range, p4+1+range, p6-range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(p2+1+range, p4-range, p6-range);
		GL11.glVertex3d(p2+1+range, p4+1+range, p6-range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(p2+1+range, p4-range, p6+1+range);
		GL11.glVertex3d(p2+1+range, p4+1+range, p6+1+range);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(p2-range, p4-range, p6+1+range);
		GL11.glVertex3d(p2-range, p4+1+range, p6+1+range);
		GL11.glEnd();
		GL11.glTranslated(te.xCoord-te.getSourceX(), te.yCoord-te.getSourceY(), te.zCoord-te.getSourceZ());
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		ReikaRenderHelper.enableLighting();
	}

}

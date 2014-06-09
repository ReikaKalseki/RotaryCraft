/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.MI;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelCCTV;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCCTV;

public class RenderCCTV extends RotaryTERenderer {

	private ModelCCTV CCTVModel = new ModelCCTV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityCCTVAt(TileEntityCCTV tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
		{
			var9 = 0;
		}
		else
		{

			var9 = tile.getBlockMetadata();


			{
				//((BlockForceFieldBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelCCTV var14;
			var14 = CCTVModel;

			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/cctvtex.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (!tile.isInWorld()) {
				GL11.glScaled(2, 2, 2);
				GL11.glTranslatef(0, -0.75F, 0);
				GL11.glRotatef(-90, 0, 1, 0);
			}
			int var11 = 1;	 //used to rotate the model about metadata
			int var12 = 0;
			if (tile.isInWorld()) {
				if (tile.getBlockMetadata() == 1) {
					var11 = -1;
					var12 = 2;
					GL11.glFrontFace(GL11.GL_CW);
				}
			}
			GL11.glTranslated(0, var12, 0);
			GL11.glScaled(1, var11, 1);
			var14.renderAll(tile, null, -tile.phi, 0);
			GL11.glScaled(1, var11, 1);
			GL11.glTranslated(0, -var12, 0);
			GL11.glFrontFace(GL11.GL_CCW);

			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		TileEntityCCTV cc = (TileEntityCCTV)tile;
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityCCTVAt(cc, par2, par4, par6, par8);
		if (cc.isInWorld())
			this.renderColors((TileEntityCCTV)tile, par2, par4, par6);
	}

	private void renderColors(TileEntityCCTV tile, double par2,	double par4, double par6) {
		ReikaRenderHelper.prepareGeoDraw(false);
		Tessellator v5 = Tessellator.instance;
		for (int i = 0; i < 3; i++) {
			if (tile.colors[i] != -1) {
				v5.startDrawing(GL11.GL_LINE_LOOP);
				int[] rgb = {ReikaDyeHelper.getColorFromDamage(tile.colors[i]).getRed(),
						ReikaDyeHelper.getColorFromDamage(tile.colors[i]).getGreen(), ReikaDyeHelper.getColorFromDamage(tile.colors[i]).getBlue()};
				v5.setColorOpaque(rgb[0], rgb[1], rgb[2]);
				double[] dd = ReikaPhysicsHelper.polarToCartesian(0.37, tile.theta, tile.phi);
				double[] dd2 = ReikaPhysicsHelper.polarToCartesian(-0.37, tile.theta, tile.phi);
				//ReikaJavaLibrary.pConsole(dd[0]+"  "+dd[1]+"  "+dd[2]);
				double dy = 0.38;
				double dy2 = 0.38;
				double d1 = 0.5+dd[2];
				double d2 = 0.25-dd[1]+dy;
				double d3 = 0.40625+dd[0];
				double d12 = 0.5+dd[2];
				double d22 = 0.25-dd[1]+dy2;
				double d32 = 0.40625+dd[0];
				v5.addVertex(par2+d12, par4+d22, par6+d32);
				v5.addVertex(par2+d1, par4+d2, par6+d3);
				v5.draw();
			}
		}
		ReikaRenderHelper.exitGeoDraw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "cctvtex.png";
	}
}

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

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Turret.ModelLaserGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLaserGun;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderLaserGun extends RotaryTERenderer {

	private ModelLaserGun railgunModel = new ModelLaserGun();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityLaserGunAt(TileEntityLaserGun tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelLaserGun var14;
		var14 = railgunModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/lasertex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
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
		int a = tile.getBlockMetadata() == 0 ? -1 : 1;
		var14.renderAll(tile, null, -tile.phi, a*tile.theta);
		GL11.glScaled(1, var11, 1);
		GL11.glTranslated(0, -var12, 0);
		GL11.glFrontFace(GL11.GL_CCW);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityLaserGunAt((TileEntityLaserGun)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderLaser((TileEntityLaserGun)tile, par2, par4, par6);
		}
	}

	private void renderLaser(TileEntityLaserGun tile, double par2, double par4,	double par6) {
		if (tile == null)
			return;
		if (!tile.isInWorld())
			return;
		int r = tile.getRange();
		if (r <= 0)
			return;
		double voff = 0.75;
		double h = 0.0625;
		GL11.glDisable(GL11.GL_CULL_FACE);
		double dx = r*Math.cos(Math.toRadians(tile.theta))*Math.cos(Math.toRadians(-tile.phi+90));
		double dy = r*Math.sin(Math.toRadians(tile.theta));
		double dz = r*Math.cos(Math.toRadians(tile.theta))*Math.sin(Math.toRadians(-tile.phi+90));

		double dd0 = 0.0625*Math.cos(Math.toRadians(-tile.phi));
		double dd1 = 0.0625*Math.sin(Math.toRadians(-tile.phi));

		if (tile.getBlockMetadata() == 1) {
			voff = 0.25;
			h = -h;
		}

		ReikaRenderHelper.prepareGeoDraw(false);
		int[] rgb = {255, 0, 0};
		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		v5.setColorOpaque(rgb[0], rgb[1], rgb[2]);
		v5.addVertex(par2+0.5-dd0, par4+voff, par6+0.5-dd1);
		v5.addVertex(par2+0.5+dd0, par4+voff, par6+0.5+dd1);
		v5.addVertex(par2+dx-dd0, par4+dy, par6+dz-dd1);
		v5.addVertex(par2+dx+dd0, par4+dy, par6+dz+dd1);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorOpaque(rgb[0], rgb[1], rgb[2]);
		v5.addVertex(par2+0.5-dd0, par4+voff+h, par6+0.5-dd1);
		v5.addVertex(par2+0.5+dd0, par4+voff+h, par6+0.5+dd1);
		v5.addVertex(par2+dx-dd0, par4+dy, par6+dz-dd1);
		v5.addVertex(par2+dx+dd0, par4+dy, par6+dz+dd1);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorOpaque(rgb[0], rgb[1], rgb[2]);
		v5.addVertex(par2+0.5-dd0, par4+voff, par6+0.5-dd1);
		v5.addVertex(par2+0.5+dd0, par4+voff+h, par6+0.5+dd1);
		v5.addVertex(par2+dx-dd0, par4+dy, par6+dz-dd1);
		v5.addVertex(par2+dx+dd0, par4+dy, par6+dz+dd1);
		v5.draw();

		v5.startDrawingQuads();
		v5.setColorOpaque(rgb[0], rgb[1], rgb[2]);
		v5.addVertex(par2+0.5-dd0, par4+voff+h, par6+0.5-dd1);
		v5.addVertex(par2+0.5+dd0, par4+voff, par6+0.5+dd1);
		v5.addVertex(par2+dx-dd0, par4+dy, par6+dz-dd1);
		v5.addVertex(par2+dx+dd0, par4+dy, par6+dz+dd1);
		v5.draw();

		GL11.glEnable(GL11.GL_CULL_FACE);
		ReikaRenderHelper.exitGeoDraw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "lasertex.png";
	}
}
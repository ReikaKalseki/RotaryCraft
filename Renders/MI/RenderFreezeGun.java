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

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Turret.ModelFreezeGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityFreezeGun;

public class RenderFreezeGun extends RotaryTERenderer {

	private ModelFreezeGun freezegunModel = new ModelFreezeGun();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityFreezeGunAt(TileEntityFreezeGun tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelFreezeGun var14;
		var14 = freezegunModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/freezeguntex.png");

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
			this.renderTileEntityFreezeGunAt((TileEntityFreezeGun)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			//this.renderIce((TileEntityFreezeGun)tile, par2, par4, par6);
		}
	}/*

	private void renderIce(TileEntityFreezeGun tile, double par2, double par4, double par6) {
		if (tile == null)
			return;
		if (!tile.isInWorld())
			return;
		if (tile.frozen == null)
			return;
		//ReikaJavaLibrary.pConsole(tile.frozen.size());
		ReikaRenderHelper.prepareGeoDraw(true);
		Tessellator v5 = new Tessellator();
		int[] rgb = {255,255,255};
		for (int i = 0; i < tile.frozen.size(); i++) {
			EntityLivingBase e = tile.frozen.get(i);
			ReikaJavaLibrary.pConsole(e.getEntityName());
			double dx = e.posX-tile.xCoord;
			double dy = e.posY-tile.yCoord;
			double dz = e.posZ-tile.zCoord;
			v5.startDrawing(GL11.GL_LINE_LOOP);
			v5.setColorRGBA(rgb[0], rgb[1], rgb[2], 255);
			v5.addVertex(par2+dx, par4+dy, par6+dz);
			v5.addVertex(dx, dy+10, dz);
			v5.addVertex(dx+10, dy+10, dz);
			v5.addVertex(dx+10, dy, dz);
			v5.draw();
		}
		ReikaRenderHelper.exitGeoDraw();
	}*/

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "freezeguntex.png";
	}
}

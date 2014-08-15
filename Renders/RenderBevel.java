/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Models.Animated.ModelBevel;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderBevel extends RotaryTERenderer
{

	private ModelBevel BevelModel = new ModelBevel();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityBevelAt(TileEntityBevelGear tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelBevel var14;

		var14 = BevelModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/beveltex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata
		int var12 = 0;
		int var13 = 0;
		int dir = 1;
		if (tile.isInWorld()) {

			switch(tile.direction) {
			case 0:
				var11 = 90; var12 = 0; var13 = 0;
				break;
			case 1:
				var11 = 180; var12 = 0; var13 = 0;
				break;
			case 2:
				var11 = 270; var12 = 0; var13 = 0;
				break;
			case 3:
				var11 = 0; var12 = 0; var13 = 0;
				break;
			case 4:
				var11 = 0; var12 = 0; var13 = 0;
				dir = -1;
				break;
			case 5:
				var11 = 90; var12 = 0; var13 = 0;
				dir = -1;
				break;
			case 6:
				var11 = 180; var12 = 0; var13 = 0;
				dir = -1;
				break;
			case 7:
				var11 = 270; var12 = 0; var13 = 0;
				dir = -1;
				break;
			case 8:
				var11 = 0; var12 = 270; var13 = 0;
				GL11.glTranslatef(0F, 1F, 1F);
				dir = -1;
				break;
			case 9:
				var11 = 90; var12 = 270; var13 = 0;
				GL11.glTranslatef(1F, 1F, -0F);
				dir = -1;
				break;
			case 10:
				var11 = 180; var12 = 270; var13 = 0;
				GL11.glTranslatef(0F, 1F, -1F);
				dir = -1;
				break;
			case 11:
				var11 = -90; var12 = 270; var13 = 0;
				GL11.glTranslatef(-1F, 1F, -0F);
				dir = -1;
				break;
			case 12:
				var11 = 0; var12 = 90; var13 = 0;
				GL11.glTranslatef(0F, 1F, -1F);
				dir = -1;
				break;
			case 13:
				var11 = 90; var12 = 90; var13 = 0;
				GL11.glTranslatef(-1F, 1F, -0F);
				break;
			case 14:
				var11 = 180; var12 = 90; var13 = 0;
				GL11.glTranslatef(0F, 1F, 1F);
				dir = -1;
				break;
			case 15:
				var11 = -90; var12 = 90; var13 = 0;
				GL11.glTranslatef(1F, 1F, -0F);
				dir = -1;
				break;
			case 16:
				var11 = 0; var12 = 90; var13 = 0;
				GL11.glTranslatef(0F, 1F, -1F);
				dir = -1;
				break;
			case 17:
				var11 = 90; var12 = 90; var13 = 0;
				GL11.glTranslatef(-1F, 1F, -0F);
				dir = -1;
				break;
			case 18:
				var11 = 180; var12 = 90; var13 = 0;
				GL11.glTranslatef(0F, 1F, 1F);
				dir = -1;
				break;
			case 19:
				var11 = -90; var12 = 90; var13 = 0;
				GL11.glTranslatef(1F, 1F, -0F);
				dir = -1;
				break;
			case 20:
				var11 = 0; var12 = 270; var13 = 0;
				GL11.glTranslatef(0F, 1F, 1F);
				break;
			case 21:
				var11 = 90; var12 = 270; var13 = 0;
				GL11.glTranslatef(1F, 1F, -0F);
				break;
			case 22:
				var11 = 180; var12 = 270; var13 = 0;
				GL11.glTranslatef(0F, 1F, -1F);
				break;
			case 23:
				var11 = -90; var12 = 270; var13 = 0;
				GL11.glTranslatef(-1F, 1F, 0F);
				break;
			}


			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(var12, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(var13, 0.0F, 0.0F, 1.0F);

		}
		else {
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
		}

		//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;

		var14.renderAll(tile, null, tile.phi*dir, 0);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityBevelAt((TileEntityBevelGear)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			//this.renderCompass(tile, par2, par4, par6);
			if (((TileEntityIOMachine)tile).iotick > 64 && ConfigRegistry.COLORBLIND.getState())
				this.renderFaceNumbers((TileEntityBevelGear)tile, par2, par4, par6);
			else
				this.renderFaceColors((TileEntityIOMachine) tile, par2, par4, par6);
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	private void renderFaceNumbers(TileEntityBevelGear tile, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		ReikaRenderHelper.disableLighting();
		double s = 0.0625;
		double d = 0.53;
		for (int i = 0; i < 6; i++) {
			double l = -0.07;
			int rx = 0;
			int ry = 0;
			int rz = 0;
			double dx = 0;
			double dy = 0;
			double dz = 0;
			switch(i) {
			case 0:
				rx = 90;
				l = 0.07;
				break;
			case 1:
				rx = 90;
				dy = 1;
				break;
			case 2:
				rz = 180;
				dy = 1;
				dx = 1;
				break;
			case 3:
				dz = 1;
				l = 0.07;
				break;
			case 4:
				ry = 90;
				rz = 180;
				dy = 1;
				break;
			case 5:
				ry = -90;
				rz = 180;
				dy = 1;
				dx = 1;
				dz = 1;
				break;
			}
			GL11.glTranslated(dx, 0, 0);
			GL11.glTranslated(0, dy, 0);
			GL11.glTranslated(0, 0, dz);
			GL11.glRotated(rx, 1, 0, 0);
			GL11.glRotated(ry, 0, 1, 0);
			GL11.glRotated(rz, 0, 0, 1);
			GL11.glTranslated(d, 0.28, l);
			GL11.glScaled(s, s, s);
			//Minecraft.getMinecraft().fontRenderer.drawString("0", 0, 0, 0xffffff);
			ReikaGuiAPI.instance.drawCenteredStringNoShadow(this.getFontRenderer(), String.valueOf(i), 0, 0, 0xffffff);
			GL11.glScaled(1/s, 1/s, 1/s);
			GL11.glTranslated(-d, -0.28, -l);
			GL11.glRotated(-rz, 0, 0, 1);
			GL11.glRotated(-ry, 0, 1, 0);
			GL11.glRotated(-rx, 1, 0, 0);
			GL11.glTranslated(0, 0, -dz);
			GL11.glTranslated(0, -dy, 0);
			GL11.glTranslated(-dx, 0, 0);
		}
		ReikaRenderHelper.enableLighting();
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private void renderCompass(TileEntity te, double p2, double p4, double p6) {
		TileEntityIOMachine io = (TileEntityIOMachine)te;
		ReikaRenderHelper.prepareGeoDraw(false);
		double vo = 1.05;
		int[] rgb = {255, 255, 0};
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_BLEND);
		Tessellator v5 = Tessellator.instance;
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(rgb[0], rgb[1], rgb[2], io.iotick);
		v5.addVertex(p2-0.5, p4+vo, p6+0.5);
		v5.addVertex(p2+1.5, p4+vo, p6+0.5);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(rgb[0], rgb[1], rgb[2], io.iotick);
		v5.addVertex(p2+0.5, p4+vo, p6-0.5);
		v5.addVertex(p2+0.5, p4+vo, p6+1.5);
		v5.draw();
		v5.startDrawing(GL11.GL_LINES);
		v5.setColorRGBA(rgb[0], rgb[1], rgb[2], io.iotick);
		v5.addVertex(p2+0.35, p4+vo, p6-0.75);
		v5.addVertex(p2+0.35, p4+vo, p6-1.25);
		v5.draw();
		v5.startDrawing(GL11.GL_LINES);
		v5.setColorRGBA(rgb[0], rgb[1], rgb[2], io.iotick);
		v5.addVertex(p2+0.35, p4+vo, p6-1.25);
		v5.addVertex(p2+0.65, p4+vo, p6-0.75);
		v5.draw();
		v5.startDrawing(GL11.GL_LINES);
		v5.setColorRGBA(rgb[0], rgb[1], rgb[2], io.iotick);
		v5.addVertex(p2+0.65, p4+vo, p6-0.75);
		v5.addVertex(p2+0.65, p4+vo, p6-1.25);
		v5.draw();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		ReikaRenderHelper.exitGeoDraw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "beveltex.png";
	}
}
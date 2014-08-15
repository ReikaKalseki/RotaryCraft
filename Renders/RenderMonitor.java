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
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelMonitor;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMonitor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderMonitor extends RotaryTERenderer
{

	private ModelMonitor MonitorModel = new ModelMonitor();

	public void renderTileEntityMonitorAt(TileEntityMonitor tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();
		ModelMonitor var14;
		var14 = MonitorModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/monitortex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		if (tile.isInWorld()) {
			switch(tile.getBlockMetadata()) {
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

			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

		}

		float var13;

		var14.renderAll(tile, null, -tile.phi, 0);
		if (tile.isInWorld()) {
			GL11.glDisable(GL11.GL_LIGHTING);
			FontRenderer var17 = this.getFontRenderer();
			float var10 = 0.6666667F*1.2F;
			GL11.glScalef(var10, -var10, -var10);
			var11 = (int)(0.016666668F * var10);
			float var112 = 0.016666668F * var10;
			GL11.glTranslatef(0.0F, 0.5F * var10, 0.07F * var10);
			GL11.glScalef(var112, -var112, var112);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(false);
			GL11.glTranslatef(5, -48, 37);
			String var15;

			for (int i = 0; i < 2; i++) {
				GL11.glTranslatef(-10*i, 0, -37*2*i-9*i);
				if (i == 1)
					GL11.glScalef(-1, 1, 1);
				var17.drawString("Power:", -37, 140, 0xffffff);
				long power = tile.power;
				if (power < 1000)
					var15 = String.format("%dW", tile.power);
				else if (power < 1000000)
					var15 = String.format("%.3fkW", tile.power/1000D);
				else if (power < 1000000000)
					var15 = String.format("%.3fMW", tile.power/1000000D);
				else
					var15 = String.format("%.3fGW", tile.power/1000000000D);
				var17.drawString(var15, -28, 148, 0xffffff);

				var17.drawString("Torque:", -37, 164, 0xffffff);
				int torque = tile.torque;
				if (torque < 1000)
					var15 = String.format("%d Nm", tile.torque);
				else if (torque < 1000000)
					var15 = String.format("%.3f kNm", tile.torque/1000D);
				else
					var15 = String.format("%.3f MNm", tile.torque/1000000D);
				var17.drawString(var15, -28, 172, 0xffffff);

				var17.drawString("Speed:", -37, 188, 0xffffff);
				int omega = tile.omega;
				if (omega < 1000)
					var15 = String.format("%d rad/s", tile.omega);
				else if (omega < 1000000)
					var15 = String.format("%d krad/s", tile.omega/1000);
				else
					var15 = String.format("%d Mrad/s", tile.omega/1000000);
				var17.drawString(var15, -28, 196, 0xffffff);

			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_LIGHTING);
		}

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityMonitorAt((TileEntityMonitor)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "monitortex.png";
	}
}
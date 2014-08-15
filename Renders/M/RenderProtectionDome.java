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
import Reika.RotaryCraft.Base.TileEntity.TileEntityProtectionDome;
import Reika.RotaryCraft.Models.ModelDomeEmitter;
import Reika.RotaryCraft.Registry.ConfigRegistry;

import java.awt.Color;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderProtectionDome extends RotaryTERenderer
{

	protected final ModelDomeEmitter model = new ModelDomeEmitter();

	public void renderTileEntityProtectionDomeAt(TileEntityProtectionDome tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelDomeEmitter var14;
		var14 = model;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll(tile, null, 0, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityProtectionDomeAt((TileEntityProtectionDome)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			if (ConfigRegistry.RENDERFORCEFIELD.getState())
				this.renderField(((TileEntityProtectionDome)tile), par2+0.5, par4+0.5, par6+0.5);
		}
	}

	protected void renderField(TileEntityProtectionDome te, double x, double y, double z) {
		if (!te.isInWorld())
			return;
		if (te.getRange() <= 0)
			return;
		Color c = te.getDomeColor();
		ReikaRenderHelper.prepareGeoDraw(false);
		int color = c.getRGB();
		for (double k = -te.getRange(); k <= te.getRange(); k += 0.5*te.getRange()/8)
			ReikaRenderHelper.renderCircle(Math.sqrt(te.getRange()*te.getRange()-k*k), x, y+k, z, color, 15);
		for (int k = 0; k < 360; k += 15)
			ReikaRenderHelper.renderVCircle(te.getRange(), x, y, z, color, k, 15);

		double ang = 7;
		ReikaRenderHelper.renderLine(x, y, z, x, y+te.getRange(), z, color);
		for (int k = 0; k < 360; k += 15) {
			ReikaRenderHelper.renderVCircle(0.125, x, y+0.5, z, color, (System.nanoTime()/7500000)%360+k, 15);
			ReikaRenderHelper.renderLine(x, y, z, x+te.getRange()*Math.sin(Math.toRadians(ang)*Math.cos(Math.toRadians(k))), y+te.getRange()-Math.sin(Math.toRadians(ang)), z+te.getRange()*Math.sin(Math.toRadians(ang)*Math.sin(Math.toRadians(k))), color);
		}
		ReikaRenderHelper.exitGeoDraw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "containtex.png";
	}
}
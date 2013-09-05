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

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.RotaryCraft.Auxiliary.EnchantmentRenderer;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelForce;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;

public class RenderForceField extends RotaryTERenderer
{

	private ModelForce ForceFieldModel = new ModelForce();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityForceFieldAt(TileEntityForceField tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();
		ModelForce var14;
		var14 = ForceFieldModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/forcetex2.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

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
			this.renderTileEntityForceFieldAt((TileEntityForceField)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			if (ConfigRegistry.RENDERFORCEFIELD.getState())
				this.renderField(((TileEntityForceField)tile), par2+0.5, par4+0.5, par6+0.5);
			if (((TileEntityForceField)tile).hasEnchantments())
				EnchantmentRenderer.renderShine(0, 0, 0, par2, par4, par6);
		}
	}

	private void renderField(TileEntityForceField te, double x, double y, double z) {
		if (!te.isInWorld())
			return;
		if (te.getRange() <= 0)
			return;
		int[] color = te.RGB;
		for (double k = -te.getRange(); k <= te.getRange(); k += 0.5*te.getRange()/8)
			ReikaRenderHelper.renderCircle(Math.sqrt(te.getRange()*te.getRange()-k*k), x, y+k, z, color);
		for (int k = 0; k < 360; k += 15)
			ReikaRenderHelper.renderVCircle(te.getRange(), x, y, z, color, ReikaPhysicsHelper.degToRad(k));

		double ang = 7;
		ReikaRenderHelper.renderLine(x, y, z, x, y+te.getRange(), z, color);
		for (int k = 0; k < 360; k += 15) {
			ReikaRenderHelper.renderVCircle(0.125, x, y+0.5, z, color, Math.toRadians((System.nanoTime()/7500000)%360+k));
			ReikaRenderHelper.renderLine(x, y, z, x+te.getRange()*Math.sin(Math.toRadians(ang)*Math.cos(Math.toRadians(k))), y+te.getRange()-Math.sin(Math.toRadians(ang)), z+te.getRange()*Math.sin(Math.toRadians(ang)*Math.sin(Math.toRadians(k))), color);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "forcetex2.png";
	}
}

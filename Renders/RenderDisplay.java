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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelBlock;
import Reika.RotaryCraft.TileEntities.TileEntityDisplay;

public class RenderDisplay extends RotaryTERenderer {

	private ModelBlock displayModel = new ModelBlock();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityDisplayAt(TileEntityDisplay tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelBlock var14;
		var14 = displayModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/displaytex.png");

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
		var14.renderAll(null, -tile.phi);
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
			this.renderTileEntityDisplayAt((TileEntityDisplay)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderText((TileEntityDisplay)tile, par2, par4, par6);
		}
	}

	private void renderText(TileEntityDisplay tile, double par2, double par4, double par6) {
		if (tile == null)
			return;
		if (!tile.hasList())
			return;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glScaled(1, -1, 1);
		GL11.glTranslated(par2, par4, par6);
		FontRenderer f = this.getFontRenderer();
		float var10 = 0.6666667F*1.2F;
		GL11.glScalef(var10, -var10, -var10);
		int var11 = (int)(0.016666668F * var10);
		float var112 = 0.016666668F * var10;
		GL11.glTranslatef(0.0F, 0.5F * var10, 0.07F * var10);
		GL11.glScalef(var112, -var112, var112);
		//GL11.glNormal3f(0.0F, 0.0F, -1.0F * var11);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDepthMask(false);
		GL11.glTranslatef(5, -48, -55);
		String var15;
		int len = ReikaMathLibrary.extrema(tile.getMessageLength(), tile.displayHeight, "min");
		for (int i = tile.getScrollPos(); i < len; i++) {
			//ReikaJavaLibrary.pConsole("Printing line "+i+" of "+tile.getMessageLength()+": "+tile.getMessageLine(i));
			f.drawString(tile.getMessageLine(i), 0, 200+i*12, 0xffffff);
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "displaytex.png";
	}
}

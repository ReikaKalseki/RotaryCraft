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

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelMirror;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;

public class RenderMirror extends RotaryTERenderer {

	private ModelMirror mirrorModel = new ModelMirror();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityMirrorAt(TileEntityMirror tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelMirror var14;
		var14 = mirrorModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/mirrortex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 1;	 //used to rotate the model about metadata
		int var12 = 0;
		if (!tile.isInWorld()) {
			GL11.glRotatef(-90, 0, 1, 0);
		}
		GL11.glTranslated(0, var12, 0);
		GL11.glScaled(1, var11, 1);
		var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(tile.broken), -tile.phi, 0);
		GL11.glScaled(1, var11, 1);
		GL11.glTranslated(0, -var12, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityMirrorAt((TileEntityMirror)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "mirrortex.png";
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Turret.ModelMultiCannon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityMultiCannon;

public class RenderMultiCannon extends RotaryTERenderer {

	private ModelMultiCannon MultiCannonModel = new ModelMultiCannon();

	public void renderTileEntityMultiCannonAt(TileEntityMultiCannon tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelMultiCannon var14;
		var14 = MultiCannonModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/multicannontex.png");

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
		var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(tile.getSpinAngle()/3D), -tile.phi, a*tile.theta);
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
		if (tile == null)
			return;
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityMultiCannonAt((TileEntityMultiCannon)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "MultiCannontex.png";
	}
}

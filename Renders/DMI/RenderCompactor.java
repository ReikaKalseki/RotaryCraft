/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DMI;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelCompactor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;

public class RenderCompactor extends RotaryTERenderer
{

	private ModelCompactor CompactorModel = new ModelCompactor();

	public void renderTileEntityCompactorAt(TileEntityCompactor tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelCompactor var14;
		var14 = CompactorModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/compactortex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;
		if (tile.isInWorld()) {
			switch(tile.getBlockMetadata()) {
			case 0:
				var11 = 180;
				break;
			case 1:
				var11 = 0;
				break;
			case 2:
				var11 = 270;
				break;
			case 3:
				var11 = 90;
				break;
			}

			if (tile.getBlockMetadata() <= 3)
				GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
			else {
				GL11.glRotatef(var11, 1F, 0F, 0.0F);
				GL11.glTranslatef(0F, -1F, 1F);
				if (tile.getBlockMetadata() == 5)
					GL11.glTranslatef(0F, 0F, -2F);
			}
		}

		float var13;

		float p = tile.phi;
		if (!tile.isInWorld())
			p = 1;
		var14.renderAll(tile, null, p, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityCompactorAt((TileEntityCompactor)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "compactortex.png";
	}
}

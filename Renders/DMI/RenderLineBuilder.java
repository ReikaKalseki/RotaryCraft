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

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelRam;
import Reika.RotaryCraft.TileEntities.World.TileEntityLineBuilder;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderLineBuilder extends RotaryTERenderer
{

	private ModelRam LineBuilderModel = new ModelRam();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityLineBuilderAt(TileEntityLineBuilder tile, double par2, double par4, double par6, float par8)
	{
		ModelRam var14;
		var14 = LineBuilderModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/ramtex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;
		float var13;
		int meta = 0;

		if (tile.isInWorld()) {
			meta = tile.getBlockMetadata();
			switch(meta) {
			case 1:
				var11 = 0;
				break;
			case 0:
				var11 = 180;
				break;
			case 2:
				var11 = 270;
				break;
			case 3:
				var11 = 90;
				break;
			case 5:
				var11 = 270;
				break;
			case 4:
				var11 = 90;
				break;
			}
		}

		if (meta <= 3)
			GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
		else {
			GL11.glRotatef(var11, 1F, 0F, 0.0F);
			GL11.glTranslatef(0F, -1F, 1F);
			if (meta == 4)
				GL11.glTranslatef(0F, 0F, -2F);
		}

		var14.renderAll(tile, null, tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityLineBuilderAt((TileEntityLineBuilder)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "ramtex.png";
	}
}
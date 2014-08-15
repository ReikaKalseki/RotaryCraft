/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DM;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelBedrockBreaker;
import Reika.RotaryCraft.Models.Animated.ModelBedrockBreakerV;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBedrockBreaker;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderBedrockBreaker extends RotaryTERenderer
{
	private ModelBedrockBreaker bbm = new ModelBedrockBreaker();
	private ModelBedrockBreakerV bbmV = new ModelBedrockBreakerV();

	public void renderTileEntityBedrockBreakerAt(TileEntityBedrockBreaker tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelBedrockBreaker var14;
		ModelBedrockBreakerV var15;

		var14 = bbm;
		var15 = bbmV;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/bedrocktex.png");

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
				var11 = -270;
				break;
			case 3:
				var11 = -90;
				break;
			case 4:
				var11 = 270;
				break;
			case 5:
				var11 = 90;
				break;
			}

			if (tile.getBlockMetadata() <= 3)
				GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
			else {
				if (tile.getBlockMetadata() == 4)
					GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(var11, 0F, 0F, 1F);
				GL11.glTranslatef(-1F, -1F, 0F);
				if (tile.getBlockMetadata() == 5)
					GL11.glTranslatef(2F, 0F, 0F);
			}
		}
		else {
			GL11.glEnable(GL11.GL_LIGHTING);
		}

		float var13;

		ArrayList li = ReikaJavaLibrary.makeListFrom(tile.getStep());
		if (tile.isInWorld() && tile.getBlockMetadata() > 3) {
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/bedrockvtex.png");
			var15.renderAll(tile, li, tile.phi, 0);
		}
		else
			var14.renderAll(tile, li, tile.phi, 0);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityBedrockBreakerAt((TileEntityBedrockBreaker)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityBedrockBreaker tb = (TileEntityBedrockBreaker)te;
		if (tb.isInWorld() && tb.getBlockMetadata() > 3)
			return "bedrockvtex.png";
		return "bedrocktex.png";
	}
}
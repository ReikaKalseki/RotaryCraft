/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelPulseFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;

public class RenderPulseFurnace extends RotaryTERenderer
{

	private ModelPulseFurnace PulseFurnaceModel = new ModelPulseFurnace();

	public void renderTileEntityPulseFurnaceAt(TileEntityPulseFurnace tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelPulseFurnace var14;
		var14 = PulseFurnaceModel;
		int temp = tile.temperature;
		int maxtemp = tile.MAXTEMP;

		if (temp < maxtemp/10)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pulsetex.png");
		else if (temp < maxtemp/5)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pulsetexhot-1.png");
		else if (temp < maxtemp/2)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pulsetexhot0.png");
		else if (temp < maxtemp/1.2)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pulsetexhot2.png");
		else
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/pulsetexhot3.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
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
				GL11.glRotatef((float)var11+0, 0.0F, 1.0F, 0.0F);
			else {
				GL11.glRotatef(var11, 1F, 0F, 0.0F);
				GL11.glTranslatef(0F, -1F, 1F);
				if (tile.getBlockMetadata() == 5)
					GL11.glTranslatef(0F, 0F, -2F);
			}
		}

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
			this.renderTileEntityPulseFurnaceAt((TileEntityPulseFurnace)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityPulseFurnace tp = (TileEntityPulseFurnace)te;
		int temp = tp.temperature;
		int maxtemp = tp.MAXTEMP;
		if (temp < maxtemp/10)
			return "pulsetex.png";
		else if (temp < maxtemp/5)
			return "pulsetexhot-1.png";
		else if (temp < maxtemp/2)
			return "pulsetexhot0.png";
		else if (temp < maxtemp/1.2)
			return "pulsetexhot2.png";
		else
			return "pulsetexhot3.png";
	}
}

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

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelObsidian;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderObsidian extends RotaryTERenderer
{
	private ModelObsidian ObsidianModel = new ModelObsidian();
	//private ModelObsidianV ObsidianModelV = new ModelObsidianV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityObsidianAt(TileEntityObsidianMaker tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelObsidian var14;
		var14 = ObsidianModel;
		//ModelObsidianV var15;
		//var14 = this.ObsidianModelV;
		if (tile.getWater() > 0 && tile.getLava() > 0)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantex.png");
		else if (tile.getWater() <= 0)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantexlava.png");
		else if (tile.getLava() <= 0)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantexwater.png");

		GL11.glPushMatrix();
		if (tile.isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F+tile.overred, 1.0F+tile.overgreen, 1.0F+tile.overblue, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		float var13;

		Object[] pars = new Object[2];
		pars[0] = (MinecraftForgeClient.getRenderPass() == 1 && (tile.getWater() > 0 || tile.getLava() > 0));
		pars[1] = (tile.shouldRenderInPass(0) && MinecraftForgeClient.getRenderPass() == 0) || !tile.isInWorld();
		var14.renderAll(tile, ReikaJavaLibrary.makeListFromArray(pars), 0, 0);

		if (tile.isInWorld() || MinecraftForgeClient.getRenderPass() == 1)
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityObsidianAt((TileEntityObsidianMaker)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityObsidianMaker teo = (TileEntityObsidianMaker)te;
		if (teo.getWater() > 0 && teo.getLava() > 0)
			return "obsidiantex.png";
		else if (teo.getWater() <= 0)
			return "obsidiantexlava.png";
		else if (teo.getLava() <= 0)
			return "obsidiantexwater.png";
		return null;
	}
}
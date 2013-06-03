/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Auxiliary.ReikaBlockRenderer;
import Reika.DragonAPI.Libraries.ReikaRenderHelper;
import Reika.RotaryCraft.Models.ModelBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCustomTNT extends Render
{
	private RenderBlocks blockRenderer = new RenderBlocks();
	ModelBlock blockModel = new ModelBlock();

	public RenderCustomTNT()
	{
		shadowSize = 0.5F;
	}

	public void renderCustomTNT(EntityCustomTNT cTNT, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		float var10;
		if (cTNT.fuse == 0)
			cTNT.fuse = 80;
		if (cTNT.fuse - par9 + 1.0F < 10.0F) {
			var10 = 1.0F - (cTNT.fuse - par9 + 1.0F) / 10.0F;

			if (var10 < 0.0F)
				var10 = 0.0F;

			if (var10 > 1.0F)
				var10 = 1.0F;

			var10 *= var10;
			var10 *= var10;
			float var11 = 1.0F + var10 * 0.3F;
			GL11.glScalef(var11, var11, var11);
		}

		var10 = (1.0F - (cTNT.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
		//blockRenderer.renderStandardBlockWithColorMultiplier(Block.tnt, 0, 0, 0, 0, 0, 0)
		this.loadTexture("/Reika/RotaryCraft/Textures/Entity/tnt.png");
		if (cTNT.fuse / 5 % 2 == 0)
		{
			ReikaRenderHelper.prepareGeoDraw(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
			blockRenderer.renderBlockAsItem(Block.tnt, 0, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			ReikaRenderHelper.exitGeoDraw();
		}
		else {
			ReikaBlockRenderer.instance.renderBlockEntityAt(cTNT, par2, par4, par6);
		}
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probability, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderCustomTNT((EntityCustomTNT)par1Entity, par2, par4, par6, par8, par9);
	}
}

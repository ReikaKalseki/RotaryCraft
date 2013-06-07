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
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.MultiModel;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelBedrockBreaker;
import Reika.RotaryCraft.Models.ModelBedrockBreakerV;
import Reika.RotaryCraft.TileEntities.TileEntityBedrockBreaker;

public class RenderBedrockBreaker extends RotaryTERenderer implements MultiModel
{
	private ModelBedrockBreaker bbm = new ModelBedrockBreaker();
	private ModelBedrockBreakerV bbmV = new ModelBedrockBreakerV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityBedrockBreakerAt(TileEntityBedrockBreaker tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
		{
			var9 = 0;
		}
		else
		{

			var9 = tile.getBlockMetadata();


			{
				//((BlockBedrockBreakerBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelBedrockBreaker var14;
			ModelBedrockBreakerV var15;

			if (true)
			{
				var14 = bbm;
				var15 = bbmV;/*
        		if (!ModLoader.getMinecraftInstance().isGamePaused && tile.isInWorld()) {
                var14.Shape3.rotateAngleX += tile.phi;
                var14.Shape3a.rotateAngleX += tile.phi;
                var14.Shape3b.rotateAngleX += tile.phi;
                var14.Shape3c.rotateAngleX += tile.phi;
                var14.Shape3d.rotateAngleX += tile.phi;
                var14.Shape3e.rotateAngleX += tile.phi;
                var14.Shape3f.rotateAngleX += tile.phi;
                var14.Shape3g.rotateAngleX += tile.phi;
                var14.Shape3h.rotateAngleX += tile.phi;
                var14.Shape3i.rotateAngleX += tile.phi;
                var14.Shape3j.rotateAngleX += tile.phi;
                var14.Shape3k.rotateAngleX += tile.phi;
                var14.Shape3cc.rotateAngleX += tile.phi;
                var14.Shape3l.rotateAngleX += tile.phi;
                var14.Shape3m.rotateAngleX += tile.phi;
                var14.Shape3n.rotateAngleX += tile.phi;
                var14.Shape3o.rotateAngleX += tile.phi;
                var14.Shape3p.rotateAngleX += tile.phi;
                var14.Shape3q.rotateAngleX += tile.phi;
                var14.Shape3r.rotateAngleX += tile.phi;
                var14.Shape3s.rotateAngleX += tile.phi;
                var14.Shape3t.rotateAngleX += tile.phi;
                var14.Shape3u.rotateAngleX += tile.phi;
                var14.Shape3v.rotateAngleX += tile.phi;
                var14.Shape3w.rotateAngleX += tile.phi;
                var14.Shape3x.rotateAngleX += tile.phi;
                var14.Shape3y.rotateAngleX += tile.phi;
                var14.Shape3z.rotateAngleX += tile.phi;
                var14.Shape3aa.rotateAngleX += tile.phi;
                var14.Shape3bb.rotateAngleX += tile.phi;
                var14.Shape3cca.rotateAngleX += tile.phi;
                var14.Shape3dd.rotateAngleX += tile.phi;
        		}*/

				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/bedrocktex.png");
			}

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

			//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			if (tile.isInWorld() && tile.getBlockMetadata() > 3) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/bedrockvtex.png");
				var15.renderAll(null, tile.phi);
			}
			else
				var14.renderAll(null, tile.phi);
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
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

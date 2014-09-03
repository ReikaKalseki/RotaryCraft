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
import Reika.RotaryCraft.Models.ModelScreen;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityScreen;

public class RenderCCTVScreen extends RotaryTERenderer
{

	private ModelScreen CCTVScreenModel = new ModelScreen();
	//private ModelCCTVScreenV CCTVScreenModelV = new ModelCCTVScreenV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityScreenAt(TileEntityScreen tile, double par2, double par4, double par6, float par8)
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
				//((BlockCCTVScreenBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelScreen var14;
			var14 = CCTVScreenModel;
			//ModelCCTVScreenV var15;
			//var14 = this.CCTVScreenModelV;
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/screentex.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1F, 1F, 1F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;	 //used to rotate the model about metadata

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

				GL11.glRotatef((float)var11-90, 0.0F, 1.0F, 0.0F);

			}
			float var13;


			var14.renderAll(tile, null, tile.phi, 0);
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
			this.renderTileEntityScreenAt((TileEntityScreen)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "screentex.png";
	}
}

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
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.MultiModel;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelCross;
import Reika.RotaryCraft.Models.ModelShaft;
import Reika.RotaryCraft.Models.ModelShaftV;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;

public class RenderShaft extends RotaryTERenderer implements MultiModel
{

	private ModelShaft ShaftModel = new ModelShaft();
	private ModelShaftV VShaftModel = new ModelShaftV();
	private ModelCross crossModel = new ModelCross();


	private int itemMetadata;

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityShaftAt(TileEntityShaft tile, double par2, double par4, double par6, float par8)
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
				//((BlockShaftBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{

			ModelShaft var14 = ShaftModel;
			ModelShaftV var15 = VShaftModel;
			ModelCross var16 = crossModel;

			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
			if (!tile.isInWorld()) {
				switch(itemMetadata) {
					case 1:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexw.png");
						break;
					case 2:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexs.png");
						break;
					case 3:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
						break;
					case 4:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexd.png");
						break;
					case 5:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexb.png");
						break;
				}
			}
			else {
				if (tile.type == null)
					return;
				switch(tile.type) {
					case WOOD:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexw.png");
						break;
					case STONE:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexs.png");
						break;
					case STEEL:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
						break;
					case DIAMOND:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexd.png");
						break;
					case BEDROCK:
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexb.png");
						break;
				}
			}

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;	 //used to rotate the model about metadata

			int meta;
			boolean failed = false;
			if (tile.isInWorld()) {
				failed = tile.failed;
				meta = tile.getBlockMetadata();

				switch(meta) {
					case 0:
						var11 = 0;
						break;
					case 1:
						var11 = 180;
						break;
					case 2:
						var11 = 90;
						break;
					case 3:
						var11 = 270;
						break;
				}
				GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
			}
			else {
				if (itemMetadata != -1)
					meta = 0;
				else {
					meta = 6;
				}
			}
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			if (meta <= 3)
				var14.renderAll(ReikaJavaLibrary.makeListFrom(failed), -tile.phi);
			else if (meta <= 5) {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));
				var15.renderAll(ReikaJavaLibrary.makeListFrom(failed), -tile.phi);
			}
			else {
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/crosstex.png"); // has own tex
				switch(tile.getBlockMetadata()) {
					case 0:
						var16.renderAll(null, -tile.crossphi2, -tile.crossphi1); //4-way symmetry, really, so no need to change direction
						break;
					case 6:
						var16.renderAll(null, -tile.crossphi2, -tile.crossphi1); //4-way symmetry, really, so no need to change direction
						break;
					case 7:
						var16.renderAll(null, tile.crossphi2, -tile.crossphi1); //4-way symmetry, really, so no need to change direction
						break;
					case 8:
						var16.renderAll(null, tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
						break;
					case 9:
						var16.renderAll(null, -tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
						break;
				}
			}

			GL11.glEnable(GL11.GL_LIGHTING);
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (par8 == -10000F) {
			itemMetadata = -1;
			par8 = 0;
		}
		else {
			itemMetadata = (int)-par8/1000;
			par8 = 0F;
		}
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityShaftAt((TileEntityShaft)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		//ReikaJavaLibrary.pConsole(te);
		if (te == null)
			return null;
		String name;
		TileEntityShaft tile = (TileEntityShaft)te;
		if (!tile.isInWorld()) {
			switch(itemMetadata) {
				case 1:
					name = "shafttexw.png";
					break;
				case 2:
					name = "shafttexs.png";
					break;
				case 3:
					name = "shafttex.png";
					break;
				case 4:
					name = "shafttexd.png";
					break;
				case 5:
					name = "shafttexb.png";
					break;
				default:
					name = "crosstex.png";
			}
		}
		else {
			if (tile.getBlockMetadata() > 5)
				return "crosstex.png";
			String p;
			if (tile.getBlockMetadata() > 3)
				p = "v";
			else
				p = "";
			//ReikaJavaLibrary.pConsole(tile.type);
			switch(tile.type) {
				case WOOD:
					name = p+"shafttexw.png";
					break;
				case STONE:
					name = p+"shafttexs.png";
					break;
				case STEEL:
					name = p+"shafttex.png";
					break;
				case DIAMOND:
					name = p+"shafttexd.png";
					break;
				case BEDROCK:
					name = p+"shafttexb.png";
					break;
				default:
					name = p+"crosstex.png";
			}
		}
		return name;
	}
}

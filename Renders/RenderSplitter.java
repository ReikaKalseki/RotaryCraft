/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelSplitter;
import Reika.RotaryCraft.Models.Animated.ModelSplitter2;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

public class RenderSplitter extends RotaryTERenderer
{

	private ModelSplitter SplitterModel = new ModelSplitter();
	private ModelSplitter2 SplitterModel2 = new ModelSplitter2();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntitySplitterAt(TileEntitySplitter tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelSplitter var14 = SplitterModel;
		ModelSplitter2 var15 = SplitterModel2;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/splittertex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;	 //used to rotate the model about metadata
		int dir = 1;
		int meta;
		if (tile.isInWorld()) {

			meta = tile.getBlockMetadata();

			switch(meta) {
			case 0:
				var11 = -90;
				break;
			case 1:
				var11 = 0;
				break;
			case 2:
				var11 = 90;
				break;
			case 3:
				var11 = 180;
				break;
			case 4:
				var11 = -90;
				break;
			case 5:
				var11 = 0;
				break;
			case 6:
				var11 = 90;
				break;
			case 7:
				var11 = 180;
				break;

			case 8:
				var11 = 270;
				break;
			case 9:
				var11 = 0;
				break;
			case 10:
				var11 = 90;
				break;
			case 11:
				var11 = 180;
				break;
			case 12:
				var11 = -90;
				break;
			case 13:
				var11 = 0;
				break;
			case 14:
				var11 = 90;
				break;
			case 15: //good
				var11 = 180;
				break;
			}

			GL11.glRotatef((float)var11-90, 0.0F, 1.0F, 0.0F);
		}
		else {
			meta = 0;
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		}

		float var13;

		if (meta < 4 || (meta >= 8 && meta < 12))
			var14.renderAll(tile, null, -tile.phi*dir, 0);
		else
			var15.renderAll(tile, null, -tile.phi*dir, 0);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntitySplitterAt((TileEntitySplitter)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "splittertex.png";
	}
}
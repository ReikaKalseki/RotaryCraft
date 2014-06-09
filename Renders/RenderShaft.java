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

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelCross;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelShaft;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelShaftV;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public class RenderShaft extends RotaryTERenderer
{
	protected ModelShaft ShaftModel = new ModelShaft();
	protected ModelShaftV VShaftModel = new ModelShaftV();
	private ModelCross crossModel = new ModelCross();

	private int itemMetadata;

	public void renderTileEntityShaftAt(TileEntityShaft tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

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
			if (tile.getShaftType() == null)
				return;
			switch(tile.getShaftType()) {
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

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;

		int meta;
		boolean failed = false;
		if (tile.isInWorld()) {
			failed = tile.failed();
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
		float var13;

		int dir = 1;
		if (meta == 5)
			dir = -1;
		if (meta <= 3)
			var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(failed), -tile.phi, 0);
		else if (meta <= 5) {
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));
			var15.renderAll(tile, ReikaJavaLibrary.makeListFrom(failed), -tile.phi*dir, 0);
		}
		else {
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/crosstex.png"); // has own tex
			switch(tile.getBlockMetadata()) {
			case 0:
				var16.renderAll(tile, null, -tile.crossphi2, 0); //4-way symmetry, really, so no need to change direction
				break;
			case 6:
				var16.renderAll(tile, null, -tile.crossphi2, 0); //4-way symmetry, really, so no need to change direction
				break;
			case 7:
				var16.renderAll(tile, null, tile.crossphi2, 0); //4-way symmetry, really, so no need to change direction
				break;
			case 8:
				var16.renderAll(tile, null, tile.crossphi2, 0); //4-way symmetry, really, so no need to change direction
				break;
			case 9:
				var16.renderAll(tile, null, -tile.crossphi2, 0); //4-way symmetry, really, so no need to change direction
				break;
			}
		}

		this.closeGL(tile);
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
			switch(tile.getShaftType()) {
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

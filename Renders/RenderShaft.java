/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
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

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;

		boolean failed = false;
		if (tile.isInWorld()) {
			failed = tile.failed();
			int meta = tile.getBlockMetadata();

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
		float var13;

		if (tile.isCross()) {
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/crosstex.png"); // has own tex
			switch(tile.getBlockMetadata()) {
				case 0:
					var16.renderAll(tile, null, -tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
					break;
				case 6:
					var16.renderAll(tile, null, -tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
					break;
				case 7:
					var16.renderAll(tile, null, tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
					break;
				case 8:
					var16.renderAll(tile, null, tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
					break;
				case 9:
					var16.renderAll(tile, null, -tile.crossphi2, tile.crossphi1); //4-way symmetry, really, so no need to change direction
					break;
			}
		}
		else if (tile.isVertical()) {
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));
			var15.renderAll(tile, ReikaJavaLibrary.makeListFrom(failed), -tile.phi, 0);
		}
		else {
			var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(failed), -tile.phi, 0);
		}

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
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
		String tex = tile.getShaftType().getBaseShaftTexture();
		if (tile.isCross()) {
			tex = "crosstex.png";
		}
		else if (tile.isVertical()) {
			tex = "v"+tex;
		}
		return tex;
	}
}

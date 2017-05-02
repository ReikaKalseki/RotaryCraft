/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Conversion;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

public class RenderDynamo extends RotaryTERenderer
{

	private ModelDynamo2 StaticModel = new ModelDynamo2();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityStaticAt(TileEntityDynamo tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 2;
		else
			var9 = tile.getBlockMetadata();

		ModelDynamo2 var14;
		var14 = StaticModel;

		String s = "/Reika/RotaryCraft/Textures/TileEntityTex/dynamotex";

		if (tile.isInWorld() && tile.power > 0)
			s = s+"2";
		if (tile.isUpgraded())
			s = s+"_u";
		s = s+".png";

		ReikaTextureHelper.bindTexture(RotaryCraft.class, s);

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;

		if (tile.isInWorld()) {

			switch(tile.getBlockMetadata()) {
				case 0:
					var11 = 0;
					break;
				case 1:
					var11 = 180;
					break;
				case 2:
					var11 = 0;
					break;
				case 3:
					var11 = 90;
					break;
				case 4:
					var11 = 180;
					break;
				case 5:
					var11 = 270;
					break;
			}

			if (tile.getBlockMetadata() < 2) {
				GL11.glRotatef(var11, 0, 0, 1);
				if (tile.getBlockMetadata() == 1)
					GL11.glTranslated(0, -2, 0);
			}
			else {
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glRotatef(var11, 0, 0, 1);
				GL11.glTranslated(0, -1, -1);
			}
		}

		var14.renderAll(tile, null, -tile.phi, 0);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityStaticAt((TileEntityDynamo)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "dynamotex.png";
	}
}

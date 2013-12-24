/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DM;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelHydraulicPump;
import Reika.RotaryCraft.Models.Animated.ModelHydraulicTurbine;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityHydraulicPump;

public class RenderHydraulic extends RotaryTERenderer
{

	private ModelHydraulicPump HydraulicModel = new ModelHydraulicPump();
	private ModelHydraulicTurbine HydraulicModel2 = new ModelHydraulicTurbine();
	private int itemMetadata = 0;

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityHydraulicPumpAt(TileEntityHydraulicPump tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 3;
		else
			var9 = tile.getBlockMetadata();

		ModelHydraulicPump var14;
		var14 = HydraulicModel;
		ModelHydraulicTurbine var15;
		var15 = HydraulicModel2;

		boolean turbine = tile.isInWorld() ? tile.isTurbine() : itemMetadata == 2;

		if (turbine)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/hydraulictex2.png");
		else
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/hydraulictex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;

		if (tile.isInWorld()) {
			switch(tile.getBlockMetadata()) {
			case 0:
			case 6:
				var11 = 0;
				break;
			case 1:
			case 7:
				var11 = 180;
				break;
			case 2:
			case 8:
				var11 = 90;
				break;
			case 3:
			case 9:
				var11 = 270;
				break;
			case 4:
			case 10:
				var11 = 270;
				break;
			case 5:
			case 11:
				var11 = 90;
				break;
			}

			if (!turbine)
				var11 += 180;

			int meta = tile.getBlockMetadata();
			boolean vertical = ReikaMathLibrary.isValueInsideBoundsIncl(4, 5, meta) || ReikaMathLibrary.isValueInsideBoundsIncl(10, 11, meta);

			if (!vertical)
				GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
			else {
				GL11.glRotatef(var11, 1F, 0F, 0.0F);
				GL11.glTranslatef(0F, -1F, 1F);
				if (tile.getBlockMetadata() == 4 || tile.getBlockMetadata() == 11)
					GL11.glTranslatef(0F, 0F, -2F);
			}
		}

		if (turbine)
			var15.renderAll(null, -tile.phi, 0);
		else
			var14.renderAll(null, -tile.phi, 0);

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (par8 <= -999F) {
			itemMetadata = (int)-par8/1000;
			par8 = 0F;
		}
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityHydraulicPumpAt((TileEntityHydraulicPump)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "hydraulictex.png";
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.DM;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelShaft;
import Reika.RotaryCraft.Models.Animated.ShaftOnly.ModelShaftV;
import Reika.RotaryCraft.Renders.RenderShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPortalShaft;

public class RenderPortalShaft extends RenderShaft {

	public void renderTileEntityPortalShaftAt(TileEntityPortalShaft tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelShaft var14 = ShaftModel;
		ModelShaftV var15 = VShaftModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");

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

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;

		int meta;
		boolean failed = false;
		meta = tile.getBlockMetadata();
		if (tile.isInWorld()) {
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

		int dir = 1;
		if (meta == 5)
			dir = -1;
		double s = tile.isInWorld() && (tile.isEnteringPortal() || tile.isExitingPortal()) ? 1.5 : 1;
		double d = tile.isInWorld() ? tile.isEnteringPortal() ? 0.25 : tile.isExitingPortal() ? -0.25 : 0 : 0;
		double ss = 0.5;
		if (meta <= 3) {
			var14.renderMount(tile);
			GL11.glTranslated(-d, 0, 0);
			GL11.glScaled(s, 1, 1);
			var14.renderShaft(tile, -tile.phi);
			GL11.glScaled(1/s, 1, 1);
			GL11.glTranslated(d, 0, 0);
		}
		else if (meta <= 5) {
			d += 0.5*dir;
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));
			var15.renderMount(tile);
			GL11.glTranslated(0, -d*dir, 0);
			GL11.glScaled(1, s, 1);
			var15.renderShaft(tile, -tile.phi*dir);
			GL11.glScaled(1, 1/s, 1);
			GL11.glTranslated(0, d*dir, 0);
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityPortalShaftAt((TileEntityPortalShaft)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		if (te == null)
			return null;
		String name;
		TileEntityPortalShaft tile = (TileEntityPortalShaft)te;
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
		return name;
	}
}

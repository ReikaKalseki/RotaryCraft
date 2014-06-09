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
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Models.ModelCVT;
import Reika.RotaryCraft.Models.Animated.ModelCoil;
import Reika.RotaryCraft.Models.Animated.ModelHighGear;
import Reika.RotaryCraft.Models.Animated.ModelWorm;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

public class RenderAdvGear extends RotaryTERenderer
{

	private ModelWorm wormModel = new ModelWorm();
	private ModelCVT cvtModel = new ModelCVT();
	private ModelCoil coilModel = new ModelCoil();
	private ModelHighGear highGearModel = new ModelHighGear();
	private int itemMetadata = 0;

	public void renderTileEntityAdvancedGearAt(TileEntityAdvancedGear tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelWorm var14 = wormModel;
		ModelCVT var15 = cvtModel;
		ModelCoil var16 = coilModel;
		ModelHighGear var17 = highGearModel;

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;	 //used to rotate the model about metadata

		if (tile.isInWorld()) {

			switch(tile.getBlockMetadata()%4) {
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

			GL11.glRotatef((float)var11+180, 0.0F, 1.0F, 0.0F);

		}
		else {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.itemMetadata));
			GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
			switch(itemMetadata) {
			case 1:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
				var14.renderAll(tile, null, 0, 0);
				break;
			case 2:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/cvttex.png");
				var15.renderAll(tile, null, 0, 0);
				break;
			case 3:
				if (tile.isBedrockCoil())
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/coiltex_bed.png");
				else
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/coiltex.png");
				var16.renderAll(tile, null, 0, 0);
				break;
			case 4:
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/highgeartex.png");
				var17.renderAll(tile, null, 0, 0);
				break;
			}
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return;
		}

		float var13;
		switch (tile.getGearType()) {
		case WORM:
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
			var14.renderAll(tile, null, tile.phi, 0);
			break;
		case CVT:
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/cvttex.png");
			var15.renderAll(tile, null, tile.phi, 0);
			break;
		case COIL:
			if (tile.isBedrockCoil())
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/coiltex_bed.png");
			else
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/coiltex.png");
			var16.renderAll(tile, null, tile.phi, 0);
			break;
		case HIGH:
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/highgeartex.png");
			var17.renderAll(tile, null, tile.phi, 0);
			break;
		}

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
			this.renderTileEntityAdvancedGearAt((TileEntityAdvancedGear)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher t) {
		if (!(t instanceof RenderFetcher))
			return "";
		TileEntityIOMachine te = (TileEntityIOMachine)t;
		if (te.isInWorld()) {
			if (te.getBlockMetadata() < 4)
				return "shafttex.png";
			else if (te.getBlockMetadata() < 8)
				return "cvttex.png";
			else if (te.getBlockMetadata() < 12)
				return "coiltex.png";
			else
				return "highgeartex.png";
		}
		else {
			if (itemMetadata == 1)
				return "shafttex.png";
			if (itemMetadata == 2)
				return "cvttex.png";
			if (itemMetadata == 3)
				return "coiltex.png";
			if (itemMetadata == 4)
				return "highgeartex.png";
			return "";
		}
	}
}

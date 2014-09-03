/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.EnchantmentRenderer;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Models.ModelHarvester;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityMobHarvester;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHarvester extends RotaryTERenderer
{

	private ModelHarvester HarvesterModel = new ModelHarvester();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityMobHarvesterAt(TileEntityMobHarvester tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelHarvester var14;
		var14 = HarvesterModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/harvestertex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll(tile, null, 0, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityMobHarvesterAt((TileEntityMobHarvester)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			ReikaAABBHelper.renderAABB(((TileEntityMobHarvester)tile).getBox(), par2, par4, par6, tile.xCoord, tile.yCoord, tile.zCoord, ((TileEntityIOMachine)tile).iotick, 255, 127, 0, true);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			this.renderLaser((TileEntityMobHarvester)tile, par2, par4, par6);
			if (((TileEntityMobHarvester)tile).hasEnchantments())
				//EnchantmentRenderer.renderShine(0, 0, 0, par2, par4, par6);
				EnchantmentRenderer.renderGlint(tile, HarvesterModel, null, par2, par4, par6);
		}
		else if (!tile.hasWorldObj()) {
			if (((TileEntityMobHarvester)tile).hasEnchantments())
				EnchantmentRenderer.renderGlint(tile, HarvesterModel, null, par2, par4, par6);
		}
	}

	private void renderLaser(TileEntityMobHarvester harv, double par2, double par4, double par6) {
		if (harv.inbox == null)
			return;
		ReikaRenderHelper.prepareGeoDraw(true);
		boolean laser1 = harv.inbox.size() > 0;
		boolean laser2 = harv.inbox.size() == 1 && (harv.inbox.get(0) instanceof EntityPlayer || harv.inbox.get(0) instanceof EntityVillager);
		if (laser1 && !laser2 && !ReikaEntityHelper.allAreDead(harv.inbox, true)) {
			ReikaAABBHelper.renderAABB(harv.getLaser(), par2, par4, par6, harv.xCoord, harv.yCoord, harv.zCoord, -960, 255, 0, 0, false);
			ReikaAABBHelper.renderAABB(harv.getLaser().expand(0.125, 0.001, 0.125), par2, par4, par6, harv.xCoord, harv.yCoord, harv.zCoord, -192, 255, 128, 128, false);
		}
		ReikaRenderHelper.exitGeoDraw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "harvestertex.png";
	}
}

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

import net.minecraft.client.model.ModelChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderScaleChest extends RotaryTERenderer
{
	/** The normal small chest model. */
	private ModelChest chestModel = new ModelChest();

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityScaleableChestAt(TileEntityScaleableChest te, double par2, double par4, double par6, float par8)
	{
		int i;

		if (!te.isInWorld())
			i = 0;
		else
			i = te.getBlockMetadata();
		ModelChest modelchest = chestModel;
		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/chest.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		float var11 = 0;
		if (te.isInWorld()) {
			switch(te.getBlockMetadata()) {
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
				var11 = 90;
				break;
			}
			GL11.glRotatef(var11+90, 0.0F, 1.0F, 0.0F);
		}
		if (te.isInWorld())
			GL11.glColor3d(1+((double)te.getNumPowerChanges())/te.POWERCHANGEAGE, 1, 1);
		short short1 = 0;
		if (i == 2)
			short1 = 180;
		if (i == 3)
			short1 = 0;
		if (i == 4)
			short1 = 90;
		if (i == 5)
			short1 = -90;
		//GL11.glRotatef(-var11, 0.0F, 1.0F, 0.0F);
		float f1 = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * par8;
		float f2;
		f1 = 1.0F - f1;
		f1 = 1.0F - f1 * f1 * f1;
		modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
		GL11.glRotatef(short1, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		//modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
		modelchest.renderAll();
		if (te.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityScaleableChestAt((TileEntityScaleableChest)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
		}
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "chest.png";
	}
}

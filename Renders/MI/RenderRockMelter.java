/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.MI;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelLavaMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderRockMelter extends RotaryTERenderer
{

	private ModelLavaMaker LavaMakerModel = new ModelLavaMaker();

	public void renderTileEntityLavaMakerAt(TileEntityLavaMaker tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelLavaMaker var14;
		var14 = LavaMakerModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/lavamakertex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		if (tile.isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		if (tile.isInWorld()) {
			switch(tile.getBlockMetadata()) {
			case 0:
				var11 = 0;
				break;
			case 1:
				var11 = 180;
				break;
			case 2:
				var11 = 270;
				break;
			case 3:
				var11 = 90;
				break;
			}

			if (tile.getBlockMetadata() <= 3)
				GL11.glRotatef((float)var11+90, 0.0F, 1.0F, 0.0F);
			else {
				GL11.glRotatef(var11, 1F, 0F, 0.0F);
				GL11.glTranslatef(0F, -1F, 1F);
				if (tile.getBlockMetadata() == 5)
					GL11.glTranslatef(0F, 0F, -2F);
			}
		}

		float var13;

		var14.renderAll(tile, ReikaJavaLibrary.makeListFrom(tile.hasStone()), -tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityLavaMakerAt((TileEntityLavaMaker)tile, par2, par4, par6, par8);

		if (MinecraftForgeClient.getRenderPass() == 0) {
			this.renderLiquid(tile, par2, par4, par6);
		}
	}

	private void renderLiquid(TileEntity tile, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		TileEntityLavaMaker tr = (TileEntityLavaMaker)tile;
		if (!tr.isEmpty() && tr.isInWorld()) {
			Fluid f = tr.getContainedFluid();
			ReikaLiquidRenderer.bindFluidTexture(f);
			IIcon ico = f.getIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = 0.1875+12D/16D*tr.getLevel()/tr.CAPACITY;
			Tessellator v5 = Tessellator.instance;
			ReikaRenderHelper.disableLighting();
			v5.startDrawingQuads();
			v5.setNormal(0, 1, 0);
			v5.addVertexWithUV(0, h, 1, u, dv);
			v5.addVertexWithUV(1, h, 1, du, dv);
			v5.addVertexWithUV(1, h, 0, du, v);
			v5.addVertexWithUV(0, h, 0, u, v);
			v5.draw();
			ReikaRenderHelper.enableLighting();
		}
		GL11.glTranslated(-par2, -par4, -par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "lavamakertex.png";
	}
}
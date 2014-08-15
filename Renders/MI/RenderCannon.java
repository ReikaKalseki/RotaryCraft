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
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;
import Reika.RotaryCraft.Models.ModelCannon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityBlockCannon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderCannon extends RotaryTERenderer
{

	private ModelCannon CannonModel = new ModelCannon();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityLaunchCannonAt(TileEntityLaunchCannon tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelCannon var14;
		var14 = CannonModel;

		if (tile instanceof TileEntityTNTCannon)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/tntcannontex.png");
		if (tile instanceof TileEntityBlockCannon)
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/blockcannontex.png");

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
			this.renderTileEntityLaunchCannonAt((TileEntityLaunchCannon)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			if (((TileEntityLaunchCannon)tile).targetMode)
				this.renderMarker((TileEntityLaunchCannon)tile, par2, par4, par6);
		}
	}

	private void renderMarker(TileEntityLaunchCannon tile, double par2, double par4, double par6) {
		ReikaRenderHelper.prepareGeoDraw(true);
		Tessellator v5 = Tessellator.instance;
		double dx = tile.target[0]-tile.xCoord;
		double dy = tile.target[1]-tile.yCoord;
		double dz = tile.target[2]-tile.zCoord;
		float i = 0.5F; float j = 0.5F;
		double length = 25;
		double width = 4;
		double height = 4;
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz);
		v5.addVertex(par2+dx, par4+dy+length, par6+dz);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx+0.5, par4+dy+height, par6+dz);
		v5.addVertex(par2+dx+0.5, par4+dy+length, par6+dz);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx-0.5, par4+dy+height, par6+dz);
		v5.addVertex(par2+dx-0.5, par4+dy+length, par6+dz);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz+0.5);
		v5.addVertex(par2+dx, par4+dy+length, par6+dz+0.5);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz-0.5);
		v5.addVertex(par2+dx, par4+dy+length, par6+dz-0.5);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx-width, par4+dy+height, par6+dz);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx+width, par4+dy+height, par6+dz);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz-width);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz+width);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx-width*0.71, par4+dy+height, par6+dz-width*0.71);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx+width*0.71, par4+dy+height, par6+dz+width*0.71);
		v5.draw();
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(0, 255, 0, 255);
		v5.addVertex(par2+dx+width*0.71, par4+dy+height, par6+dz-width*0.71);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx-width*0.71, par4+dy+height, par6+dz+width*0.71);
		v5.draw();

		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx-width, par4+dy+height, par6+dz);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx+width, par4+dy+height, par6+dz);
		v5.draw();
		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz-width);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz+width);
		v5.draw();
		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx+width, par4+dy+height, par6+dz);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx-width, par4+dy+height, par6+dz);
		v5.draw();
		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz+width);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx, par4+dy+height, par6+dz-width);
		v5.draw();

		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx-width*0.71, par4+dy+height, par6+dz-width*0.71);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx+width*0.71, par4+dy+height, par6+dz+width*0.71);
		v5.draw();
		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx+width*0.71, par4+dy+height, par6+dz-width*0.71);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx-width*0.71, par4+dy+height, par6+dz+width*0.71);
		v5.draw();
		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx-width*0.71, par4+dy+height, par6+dz+width*0.71);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx+width*0.71, par4+dy+height, par6+dz-width*0.71);
		v5.draw();
		v5.startDrawing(GL11.GL_POLYGON);
		v5.setColorRGBA(0, 255, 0, 127);
		v5.addVertex(par2+dx+width*0.71, par4+dy+height, par6+dz+width*0.71);
		v5.addVertex(par2+dx, par4+dy, par6+dz);
		v5.addVertex(par2+dx-width*0.71, par4+dy+height, par6+dz-width*0.71);
		v5.draw();
		ReikaRenderHelper.exitGeoDraw();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		if (te instanceof TileEntityTNTCannon)
			return "tntcannontex.png";
		if (te instanceof TileEntityBlockCannon)
			return "blockcannontex.png";
		return "";
	}
}
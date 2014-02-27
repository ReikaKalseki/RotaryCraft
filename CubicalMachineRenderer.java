/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CubicalMachineRenderer implements ISimpleBlockRenderingHandler {

	public final int renderID;
	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public CubicalMachineRenderer(int ID) {
		renderID = ID;
	}

	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb) {
		Tessellator v5 = Tessellator.instance;
		b.setBlockBoundsForItemRender();
		rb.setRenderBoundsFromBlock(b);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		v5.startDrawingQuads();
		v5.setNormal(0.0F, -1.0F, 0.0F);
		rb.renderFaceYNeg(b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(b, 0, meta));
		v5.draw();
		v5.startDrawingQuads();
		v5.setNormal(0.0F, 1.0F, 0.0F);
		rb.renderFaceYPos(b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(b, 1, meta));
		v5.draw();
		v5.startDrawingQuads();
		v5.setNormal(0.0F, 0.0F, -1.0F);
		rb.renderFaceZNeg(b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(b, 2, meta));
		v5.draw();
		v5.startDrawingQuads();
		v5.setNormal(0.0F, 0.0F, 1.0F);
		rb.renderFaceZPos(b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(b, 3, meta));
		v5.draw();
		v5.startDrawingQuads();
		v5.setNormal(-1.0F, 0.0F, 0.0F);
		rb.renderFaceXNeg(b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(b, 4, meta));
		v5.draw();
		v5.startDrawingQuads();
		v5.setNormal(1.0F, 0.0F, 0.0F);
		rb.renderFaceXPos(b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(b, 5, meta));
		v5.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelId, RenderBlocks rb) {
		rb.renderStandardBlock(b, x, y, z);

		Tessellator v5 = Tessellator.instance;
		//ReikaRenderHelper.prepareGeoDraw(true);
		int l = b.getMixedBrightnessForBlock(world, x, y, z);

		v5.setBrightness(rb.renderMaxY < 1.0D ? l : b.getMixedBrightnessForBlock(world, x, y+1, z));
		v5.setColorOpaque_F(0, 0, 1);
		v5.setNormal(0, 1, 0);
		GL11.glColor4f(1, 1, 1, 1);
		v5.addVertex(x, y+2, z+1);
		v5.addVertex(x+1, y+2, z+1);
		v5.addVertex(x+1, y+2, z);
		v5.addVertex(x, y+2, z);
		//ReikaRenderHelper.exitGeoDraw();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

}

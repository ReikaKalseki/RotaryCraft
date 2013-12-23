/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHydraulicLine;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class HydraulicLineRenderer implements ISimpleBlockRenderingHandler {

	public final int renderID;
	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public HydraulicLineRenderer(int ID) {
		renderID = ID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator v5 = Tessellator.instance;
		TileEntityHydraulicLine line = (TileEntityHydraulicLine)world.getBlockTileEntity(x, y, z);
		v5.setColorOpaque(255, 255, 255);
		for (int i = 0; i < 6; i++) {
			this.renderFace(line, world, x, y, z, dirs[i]);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

	public void renderBlockInInventory(TileEntityHydraulicLine tile, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(0, 1, 0);
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			World world = Minecraft.getMinecraft().theWorld;
			this.doRenderFace(tile, world, dir);
		}
		Tessellator.instance.draw();
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private void renderFace(TileEntityHydraulicLine tile, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		Tessellator v5 = Tessellator.instance;
		v5.addTranslation(x, y, z);
		int br = BlockRegistry.HYDRAULIC.getBlockVariable().getMixedBrightnessForBlock(world, x, y, z);
		v5.setBrightness(br);

		this.doRenderFace(tile, world, dir);

		v5.addTranslation(-x, -y, -z);
	}

	private void doRenderFace(TileEntityHydraulicLine tile, IBlockAccess world, ForgeDirection dir) {
		Tessellator v5 = Tessellator.instance;
		Icon ico = Block.sand.getIcon(0, 0);
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		double size = 0.25;

		switch(dir) {
		case DOWN:
			break;
		case EAST:
			break;
		case NORTH:
			break;
		case SOUTH:
			break;
		case UP:
			break;
		case WEST:
			break;
		default:
			break;
		}
		v5.addVertexWithUV(0.5-size, 0.5+size, 0.5+size, u, v);
		v5.addVertexWithUV(0.5+size, 0.5+size, 0.5+size, du, v);
		v5.addVertexWithUV(0.5+size, 0.5+size, 0.5-size, du, dv);
		v5.addVertexWithUV(0.5-size, 0.5+size, 0.5-size, u, dv);
	}

}

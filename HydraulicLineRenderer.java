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

import Reika.RotaryCraft.Blocks.BlockHydraulicLine;
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
			ForgeDirection dir = dirs[i];
			boolean flag = false;
			if (line.isConnectedToMachine(dir)) {
				flag = true;
				this.renderConnection(line, world, x, y, z, dir, 0.25);
			}
			else if (line.isConnectedOnSide(dir)) {
				this.renderConnection(line, world, x, y, z, dir, 0.15);
			}
			this.renderFace(line, world, x, y, z, dir, flag);
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

	private void renderConnection(TileEntityHydraulicLine tile, IBlockAccess world, int x, int y, int z, ForgeDirection dir, double size) {
		Tessellator v5 = Tessellator.instance;
		v5.addTranslation(x, y, z);
		int br = BlockRegistry.HYDRAULIC.getBlockVariable().getMixedBrightnessForBlock(world, x, y, z);
		v5.setBrightness(br);

		int color = dir == tile.getInput() ? 5 : 14;
		Icon ico;
		if (size == 0.25)
			ico = ((BlockHydraulicLine)BlockRegistry.HYDRAULIC.getBlockVariable()).connectionIcon;
		else if (dir == tile.getInput())
			ico = ((BlockHydraulicLine)BlockRegistry.HYDRAULIC.getBlockVariable()).inIcon;
		else
			ico = ((BlockHydraulicLine)BlockRegistry.HYDRAULIC.getBlockVariable()).outIcon;

		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		/*
		if (dir.offsetY == 0) {
			float f = 0.1875F;
			v += (dv-v)*f;
			dv -= (dv-v)*f;
		}
		else {
			float f = 0.1875F;
			u += (du-u)*f;
			du -= (du-u)*f;
		}*/

		switch(dir) {
		case EAST:
			this.faceBrightness(ForgeDirection.NORTH, v5);
			v5.addVertexWithUV(0.5+0.1875, 0.5+size, 0.5-size, u, dv);
			v5.addVertexWithUV(1, 0.5+size, 0.5-size, du, dv);
			v5.addVertexWithUV(1, 0.5-size, 0.5-size, du, v);
			v5.addVertexWithUV(0.5+0.1875, 0.5-size, 0.5-size, u, v);

			this.faceBrightness(ForgeDirection.SOUTH, v5);
			v5.addVertexWithUV(0.5+0.1875, 0.5-size, 0.5+size, u, v);
			v5.addVertexWithUV(1, 0.5-size, 0.5+size, du, v);
			v5.addVertexWithUV(1, 0.5+size, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5+0.1875, 0.5+size, 0.5+size, u, dv);

			this.faceBrightness(ForgeDirection.DOWN, v5);
			v5.addVertexWithUV(0.5+0.1875, 0.5+size, 0.5+size, u, v);
			v5.addVertexWithUV(1, 0.5+size, 0.5+size, du, v);
			v5.addVertexWithUV(1, 0.5+size, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5+0.1875, 0.5+size, 0.5-size, u, dv);

			this.faceBrightness(ForgeDirection.UP, v5);
			v5.addVertexWithUV(0.5+0.1875, 0.5-size, 0.5-size, u, dv);
			v5.addVertexWithUV(1, 0.5-size, 0.5-size, du, dv);
			v5.addVertexWithUV(1, 0.5-size, 0.5+size, du, v);
			v5.addVertexWithUV(0.5+0.1875, 0.5-size, 0.5+size, u, v);

			this.faceBrightness(ForgeDirection.EAST, v5);
			v5.addVertexWithUV(1, 0.5+size, 0.5-size, u, dv);
			v5.addVertexWithUV(1, 0.5+size, 0.5+size, du, dv);
			v5.addVertexWithUV(1, 0.5-size, 0.5+size, du, v);
			v5.addVertexWithUV(1, 0.5-size, 0.5-size, u, v);
			break;
		case WEST:
			this.faceBrightness(ForgeDirection.NORTH, v5);
			v5.addVertexWithUV(0, 0.5+size, 0.5-size, u, dv);
			v5.addVertexWithUV(0.5-0.1875, 0.5+size, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5-0.1875, 0.5-size, 0.5-size, du, v);
			v5.addVertexWithUV(0, 0.5-size, 0.5-size, u, v);

			this.faceBrightness(ForgeDirection.SOUTH, v5);
			v5.addVertexWithUV(0, 0.5-size, 0.5+size, u, v);
			v5.addVertexWithUV(0.5-0.1875, 0.5-size, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-0.1875, 0.5+size, 0.5+size, du, dv);
			v5.addVertexWithUV(0, 0.5+size, 0.5+size, u, dv);

			this.faceBrightness(ForgeDirection.DOWN, v5);
			v5.addVertexWithUV(0, 0.5+size, 0.5+size, u, v);
			v5.addVertexWithUV(0.5-0.1875, 0.5+size, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-0.1875, 0.5+size, 0.5-size, du, dv);
			v5.addVertexWithUV(0, 0.5+size, 0.5-size, u, dv);

			this.faceBrightness(ForgeDirection.UP, v5);
			v5.addVertexWithUV(0, 0.5-size, 0.5-size, u, dv);
			v5.addVertexWithUV(0.5-0.1875, 0.5-size, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5-0.1875, 0.5-size, 0.5+size, du, v);
			v5.addVertexWithUV(0, 0.5-size, 0.5+size, u, v);

			this.faceBrightness(ForgeDirection.WEST, v5);
			v5.addVertexWithUV(0, 0.5-size, 0.5-size, u, v);
			v5.addVertexWithUV(0, 0.5-size, 0.5+size, du, v);
			v5.addVertexWithUV(0, 0.5+size, 0.5+size, du, dv);
			v5.addVertexWithUV(0, 0.5+size, 0.5-size, u, dv);
			break;
		case NORTH:
			this.faceBrightness(ForgeDirection.EAST, v5);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0, u, dv);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0.5-0.1875, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0.5-0.1875, du, v);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0, u, v);

			this.faceBrightness(ForgeDirection.WEST, v5);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0, u, v);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0.5-0.1875, du, v);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0.5-0.1875, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0, u, dv);

			this.faceBrightness(ForgeDirection.NORTH, v5);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0, u, dv);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0, du, v);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0, u, v);

			//v -= (dv-v)*0.25F;
			//u += (du-u)*0.0625F;
			//du -= (du-u)*0.0625F;
			this.faceBrightness(ForgeDirection.DOWN, v5);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0.5-0.1875, u, v);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0.5-0.1875, du, v);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0, u, dv);

			this.faceBrightness(ForgeDirection.UP, v5);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0, u, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0.5-0.1875, du, v);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0.5-0.1875, u, v);
			break;
		case SOUTH:
			this.faceBrightness(ForgeDirection.EAST, v5);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0.5+0.1875, u, dv);
			v5.addVertexWithUV(0.5+size, 0.5+size, 1, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 1, du, v);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0.5+0.1875, u, v);

			this.faceBrightness(ForgeDirection.WEST, v5);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0.5+0.1875, u, v);
			v5.addVertexWithUV(0.5-size, 0.5-size, 1, du, v);
			v5.addVertexWithUV(0.5-size, 0.5+size, 1, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0.5+0.1875, u, dv);

			this.faceBrightness(ForgeDirection.SOUTH, v5);
			v5.addVertexWithUV(0.5-size, 0.5-size, 1, u, v);
			v5.addVertexWithUV(0.5+size, 0.5-size, 1, du, v);
			v5.addVertexWithUV(0.5+size, 0.5+size, 1, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size, 1, u, dv);

			//dv += (dv-v)*0.25F;
			//u += (du-u)*0.0625F;
			//du -= (du-u)*0.0625F;
			this.faceBrightness(ForgeDirection.DOWN, v5);
			v5.addVertexWithUV(0.5-size, 0.5+size, 1, u, v);
			v5.addVertexWithUV(0.5+size, 0.5+size, 1, du, v);
			v5.addVertexWithUV(0.5+size, 0.5+size, 0.5+0.1875, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size, 0.5+0.1875, u, dv);

			this.faceBrightness(ForgeDirection.UP, v5);
			v5.addVertexWithUV(0.5-size, 0.5-size, 0.5+0.1875, u, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 0.5+0.1875, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size, 1, du, v);
			v5.addVertexWithUV(0.5-size, 0.5-size, 1, u, v);
			break;
		case UP:
			this.faceBrightness(ForgeDirection.EAST, v5);
			v5.addVertexWithUV(0.5+size, 1, 0.5-size, u, v);
			v5.addVertexWithUV(0.5+size, 1, 0.5+size, du, v);
			v5.addVertexWithUV(0.5+size, 0.5+0.1875, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5+0.1875, 0.5-size, u, dv);

			this.faceBrightness(ForgeDirection.WEST, v5);
			v5.addVertexWithUV(0.5-size, 0.5+0.1875, 0.5-size, u, dv);
			v5.addVertexWithUV(0.5-size, 0.5+0.1875, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5-size, 1, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-size, 1, 0.5-size, u, v);

			this.faceBrightness(ForgeDirection.NORTH, v5);
			v5.addVertexWithUV(0.5-size, 1, 0.5-size, u, v);
			v5.addVertexWithUV(0.5+size, 1, 0.5-size, du, v);
			v5.addVertexWithUV(0.5+size, 0.5+0.1875, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+0.1875, 0.5-size, u, dv);

			this.faceBrightness(ForgeDirection.SOUTH, v5);
			v5.addVertexWithUV(0.5-size, 0.5+0.1875, 0.5+size, u, dv);
			v5.addVertexWithUV(0.5+size, 0.5+0.1875, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5+size, 1, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-size, 1, 0.5+size, u, v);

			this.faceBrightness(ForgeDirection.DOWN, v5);
			v5.addVertexWithUV(0.5-size, 1, 0.5+size, u, dv);
			v5.addVertexWithUV(0.5+size, 1, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5+size, 1, 0.5-size, du, v);
			v5.addVertexWithUV(0.5-size, 1, 0.5-size, u, v);
			break;
		case DOWN:
			this.faceBrightness(ForgeDirection.EAST, v5);
			v5.addVertexWithUV(0.5+size, 0.5-0.1875, 0.5-size, u, v);
			v5.addVertexWithUV(0.5+size, 0.5-0.1875, 0.5+size, du, v);
			v5.addVertexWithUV(0.5+size, 0, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5+size, 0, 0.5-size, u, dv);

			this.faceBrightness(ForgeDirection.WEST, v5);
			v5.addVertexWithUV(0.5-size, 0, 0.5-size, u, dv);
			v5.addVertexWithUV(0.5-size, 0, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5-0.1875, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-size, 0.5-0.1875, 0.5-size, u, v);

			this.faceBrightness(ForgeDirection.NORTH, v5);
			v5.addVertexWithUV(0.5-size, 0.5-0.1875, 0.5-size, u, v);
			v5.addVertexWithUV(0.5+size, 0.5-0.1875, 0.5-size, du, v);
			v5.addVertexWithUV(0.5+size, 0, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5-size, 0, 0.5-size, u, dv);

			this.faceBrightness(ForgeDirection.SOUTH, v5);
			v5.addVertexWithUV(0.5-size, 0, 0.5+size, u, dv);
			v5.addVertexWithUV(0.5+size, 0, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-0.1875, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-size, 0.5-0.1875, 0.5+size, u, v);

			this.faceBrightness(ForgeDirection.UP, v5);
			v5.addVertexWithUV(0.5-size, 0, 0.5-size, u, v);
			v5.addVertexWithUV(0.5+size, 0, 0.5-size, du, v);
			v5.addVertexWithUV(0.5+size, 0, 0.5+size, du, dv);
			v5.addVertexWithUV(0.5-size, 0, 0.5+size, u, dv);
			break;
		default:
			break;
		}

		v5.addTranslation(-x, -y, -z);
	}

	public void renderBlockInInventory(TileEntityHydraulicLine tile, double par2, double par4, double par6) {
		GL11.glTranslated(par2, par4, par6);
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(0, 1, 0);
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			World world = Minecraft.getMinecraft().theWorld;
			this.doRenderFace(tile, world, dir, false);
		}
		Tessellator.instance.draw();
		GL11.glTranslated(-par2, -par4, -par6);
	}

	private void renderFace(TileEntityHydraulicLine tile, IBlockAccess world, int x, int y, int z, ForgeDirection dir, boolean flip) {
		Tessellator v5 = Tessellator.instance;
		v5.addTranslation(x, y, z);
		int br = BlockRegistry.HYDRAULIC.getBlockVariable().getMixedBrightnessForBlock(world, x, y, z);
		v5.setBrightness(br);

		this.doRenderFace(tile, world, dir, flip);

		v5.addTranslation(-x, -y, -z);
	}

	private void doRenderFace(TileEntityHydraulicLine tile, IBlockAccess world, ForgeDirection dir, boolean flip) {
		Tessellator v5 = Tessellator.instance;
		Icon ico = ((BlockHydraulicLine)BlockRegistry.HYDRAULIC.getBlockVariable()).centerIcon;
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		double size = flip ? 0.25 : 0.1875;
		double inset = 0.1875;
		this.faceBrightness(dir.getOpposite(), v5);

		if (flip) {

			switch(dir) {
			case EAST:
				v5.addVertexWithUV(0.5+inset, 0.5-size, 0.5-size, u, v);
				v5.addVertexWithUV(0.5+inset, 0.5-size, 0.5+size, du, v);
				v5.addVertexWithUV(0.5+inset, 0.5+size, 0.5+size, du, dv);
				v5.addVertexWithUV(0.5+inset, 0.5+size, 0.5-size, u, dv);
				break;
			case WEST:
				v5.addVertexWithUV(0.5-inset, 0.5+size, 0.5-size, u, dv);
				v5.addVertexWithUV(0.5-inset, 0.5+size, 0.5+size, du, dv);
				v5.addVertexWithUV(0.5-inset, 0.5-size, 0.5+size, du, v);
				v5.addVertexWithUV(0.5-inset, 0.5-size, 0.5-size, u, v);
				break;
			case NORTH:
				v5.addVertexWithUV(0.5-size, 0.5-size, 0.5-inset, u, v);
				v5.addVertexWithUV(0.5+size, 0.5-size, 0.5-inset, du, v);
				v5.addVertexWithUV(0.5+size, 0.5+size, 0.5-inset, du, dv);
				v5.addVertexWithUV(0.5-size, 0.5+size, 0.5-inset, u, dv);
				break;
			case SOUTH:
				v5.addVertexWithUV(0.5-size, 0.5+size, 0.5+inset, u, dv);
				v5.addVertexWithUV(0.5+size, 0.5+size, 0.5+inset, du, dv);
				v5.addVertexWithUV(0.5+size, 0.5-size, 0.5+inset, du, v);
				v5.addVertexWithUV(0.5-size, 0.5-size, 0.5+inset, u, v);
				break;
			case UP:
				v5.addVertexWithUV(0.5-size, 0.5+inset, 0.5-size, u, dv);
				v5.addVertexWithUV(0.5+size, 0.5+inset, 0.5-size, du, dv);
				v5.addVertexWithUV(0.5+size, 0.5+inset, 0.5+size, du, v);
				v5.addVertexWithUV(0.5-size, 0.5+inset, 0.5+size, u, v);
				break;
			case DOWN:
				v5.addVertexWithUV(0.5-size, 0.5-inset, 0.5+size, u, v);
				v5.addVertexWithUV(0.5+size, 0.5-inset, 0.5+size, du, v);
				v5.addVertexWithUV(0.5+size, 0.5-inset, 0.5-size, du, dv);
				v5.addVertexWithUV(0.5-size, 0.5-inset, 0.5-size, u, dv);
				break;
			default:
				break;
			}
		}
		else {

			switch(dir) {
			case EAST:
				v5.addVertexWithUV(0.5+inset, 0.5+size, 0.5-size, u, dv);
				v5.addVertexWithUV(0.5+inset, 0.5+size, 0.5+size, du, dv);
				v5.addVertexWithUV(0.5+inset, 0.5-size, 0.5+size, du, v);
				v5.addVertexWithUV(0.5+inset, 0.5-size, 0.5-size, u, v);
				break;
			case WEST:
				v5.addVertexWithUV(0.5-inset, 0.5-size, 0.5-size, u, v);
				v5.addVertexWithUV(0.5-inset, 0.5-size, 0.5+size, du, v);
				v5.addVertexWithUV(0.5-inset, 0.5+size, 0.5+size, du, dv);
				v5.addVertexWithUV(0.5-inset, 0.5+size, 0.5-size, u, dv);
				break;
			case NORTH:
				v5.addVertexWithUV(0.5-size, 0.5+size, 0.5-inset, u, dv);
				v5.addVertexWithUV(0.5+size, 0.5+size, 0.5-inset, du, dv);
				v5.addVertexWithUV(0.5+size, 0.5-size, 0.5-inset, du, v);
				v5.addVertexWithUV(0.5-size, 0.5-size, 0.5-inset, u, v);
				break;
			case SOUTH:
				v5.addVertexWithUV(0.5-size, 0.5-size, 0.5+inset, u, v);
				v5.addVertexWithUV(0.5+size, 0.5-size, 0.5+inset, du, v);
				v5.addVertexWithUV(0.5+size, 0.5+size, 0.5+inset, du, dv);
				v5.addVertexWithUV(0.5-size, 0.5+size, 0.5+inset, u, dv);
				break;
			case UP:
				v5.addVertexWithUV(0.5-size, 0.5+inset, 0.5+size, u, v);
				v5.addVertexWithUV(0.5+size, 0.5+inset, 0.5+size, du, v);
				v5.addVertexWithUV(0.5+size, 0.5+inset, 0.5-size, du, dv);
				v5.addVertexWithUV(0.5-size, 0.5+inset, 0.5-size, u, dv);
				break;
			case DOWN:
				v5.addVertexWithUV(0.5-size, 0.5-inset, 0.5-size, u, dv);
				v5.addVertexWithUV(0.5+size, 0.5-inset, 0.5-size, du, dv);
				v5.addVertexWithUV(0.5+size, 0.5-inset, 0.5+size, du, v);
				v5.addVertexWithUV(0.5-size, 0.5-inset, 0.5+size, u, v);
				break;
			default:
				break;
			}
		}
	}

	private void faceBrightness(ForgeDirection dir, Tessellator v5) {
		float f = 1;
		switch(dir.getOpposite()) {
		case DOWN:
			f = 0.4F;
			break;
		case EAST:
			f = 0.5F;
			break;
		case NORTH:
			f = 0.65F;
			break;
		case SOUTH:
			f = 0.65F;
			break;
		case UP:
			f = 1F;
			break;
		case WEST:
			f = 0.5F;
			break;
		default:
			break;
		}
		v5.setColorOpaque_F(f, f, f);
	}

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.ISBRH;
import Reika.DragonAPI.Interfaces.Block.ConnectedTextureGlass;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank.TankFlags;


public class ConnectedGlassRenderer implements ISBRH {

	public final int renderID;
	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public static int renderPass = 0;

	public ConnectedGlassRenderer(int ID) {
		renderID = ID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks rb) {
		Tessellator v5 = Tessellator.instance;

		GL11.glColor3f(1, 1, 1);
		v5.startDrawingQuads();

		ConnectedTextureGlass b = (ConnectedTextureGlass)block;

		boolean render5 = b.renderCentralTextureForItem(metadata);

		IIcon ico = b.getIconForEdge(metadata, 0);
		IIcon ico2 = b.getIconForEdge(metadata, 5);
		float u = ico.getMinU();
		float du = ico.getMaxU();
		float v = ico.getMinV();
		float dv = ico.getMaxV();

		float u2 = ico2.getMinU();
		float du2 = ico2.getMaxU();
		float v2 = ico2.getMinV();
		float dv2 = ico2.getMaxV();

		float dx = -0.5F;
		float dy = -0.5F;
		float dz = -0.5F;
		v5.addTranslation(dx, dy, dz);

		this.setFaceBrightness(v5, ForgeDirection.UP);
		v5.addVertexWithUV(1, 1, 0, u, v);
		v5.addVertexWithUV(0, 1, 0, du, v);
		v5.addVertexWithUV(0, 1, 1, du, dv);
		v5.addVertexWithUV(1, 1, 1, u, dv);

		if (render5) {
			v5.addVertexWithUV(1, 1, 0, u2, v2);
			v5.addVertexWithUV(0, 1, 0, du2, v2);
			v5.addVertexWithUV(0, 1, 1, du2, dv2);
			v5.addVertexWithUV(1, 1, 1, u2, dv2);
		}

		this.setFaceBrightness(v5, ForgeDirection.DOWN);
		v5.addVertexWithUV(0, 0, 0, du, v);
		v5.addVertexWithUV(1, 0, 0, u, v);
		v5.addVertexWithUV(1, 0, 1, u, dv);
		v5.addVertexWithUV(0, 0, 1, du, dv);

		if (render5) {
			v5.addVertexWithUV(0, 0, 0, du2, v2);
			v5.addVertexWithUV(1, 0, 0, u2, v2);
			v5.addVertexWithUV(1, 0, 1, u2, dv2);
			v5.addVertexWithUV(0, 0, 1, du2, dv2);
		}

		this.setFaceBrightness(v5, ForgeDirection.EAST);
		v5.addVertexWithUV(1, 0, 0, du, v);
		v5.addVertexWithUV(1, 1, 0, u, v);
		v5.addVertexWithUV(1, 1, 1, u, dv);
		v5.addVertexWithUV(1, 0, 1, du, dv);

		if (render5) {
			v5.addVertexWithUV(1, 0, 0, du2, v2);
			v5.addVertexWithUV(1, 1, 0, u2, v2);
			v5.addVertexWithUV(1, 1, 1, u2, dv2);
			v5.addVertexWithUV(1, 0, 1, du2, dv2);
		}

		this.setFaceBrightness(v5, ForgeDirection.WEST);
		v5.addVertexWithUV(0, 1, 0, u, v);
		v5.addVertexWithUV(0, 0, 0, du, v);
		v5.addVertexWithUV(0, 0, 1, du, dv);
		v5.addVertexWithUV(0, 1, 1, u, dv);

		if (render5) {
			v5.addVertexWithUV(0, 1, 0, u2, v2);
			v5.addVertexWithUV(0, 0, 0, du2, v2);
			v5.addVertexWithUV(0, 0, 1, du2, dv2);
			v5.addVertexWithUV(0, 1, 1, u2, dv2);
		}

		this.setFaceBrightness(v5, ForgeDirection.SOUTH);
		v5.addVertexWithUV(0, 1, 1, u, v);
		v5.addVertexWithUV(0, 0, 1, du, v);
		v5.addVertexWithUV(1, 0, 1, du, dv);
		v5.addVertexWithUV(1, 1, 1, u, dv);

		if (render5) {
			v5.addVertexWithUV(0, 1, 1, u2, v2);
			v5.addVertexWithUV(0, 0, 1, du2, v2);
			v5.addVertexWithUV(1, 0, 1, du2, dv2);
			v5.addVertexWithUV(1, 1, 1, u2, dv2);
		}

		this.setFaceBrightness(v5, ForgeDirection.NORTH);
		v5.addVertexWithUV(0, 0, 0, du, v);
		v5.addVertexWithUV(0, 1, 0, u, v);
		v5.addVertexWithUV(1, 1, 0, u, dv);
		v5.addVertexWithUV(1, 0, 0, du, dv);

		if (render5) {
			v5.addVertexWithUV(0, 0, 0, du2, v2);
			v5.addVertexWithUV(0, 1, 0, u2, v2);
			v5.addVertexWithUV(1, 1, 0, u2, dv2);
			v5.addVertexWithUV(1, 0, 0, du2, dv2);
		}

		v5.addTranslation(-dx, -dy, -dz);

		v5.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks rb) {
		Tessellator v5 = Tessellator.instance;
		ConnectedTextureGlass b = (ConnectedTextureGlass)block;
		v5.addTranslation(x, y, z);

		int mix = block.getMixedBrightnessForBlock(world, x, y, z);
		v5.setBrightness(mix);

		if (renderPass == 0) {
			ArrayList<Integer> li = b.getEdgesForFace(world, x, y, z, ForgeDirection.UP);
			this.setFaceBrightness(v5, ForgeDirection.UP);
			if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.UP.ordinal()))
				for (int i = 0; i < li.size(); i++) {
					int edge = li.get(i);
					IIcon ico = b.getIconForEdge(world, x, y, z, edge);
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					v5.addVertexWithUV(1, 1, 0, u, v);
					v5.addVertexWithUV(0, 1, 0, du, v);
					v5.addVertexWithUV(0, 1, 1, du, dv);
					v5.addVertexWithUV(1, 1, 1, u, dv);
				}



			li = b.getEdgesForFace(world, x, y, z, ForgeDirection.DOWN);
			this.setFaceBrightness(v5, ForgeDirection.DOWN);
			if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.DOWN.ordinal()))
				for (int i = 0; i < li.size(); i++) {
					int edge = li.get(i);
					IIcon ico = b.getIconForEdge(world, x, y, z, edge);
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					v5.addVertexWithUV(0, 0, 0, du, v);
					v5.addVertexWithUV(1, 0, 0, u, v);
					v5.addVertexWithUV(1, 0, 1, u, dv);
					v5.addVertexWithUV(0, 0, 1, du, dv);
				}


			li = b.getEdgesForFace(world, x, y, z, ForgeDirection.EAST);
			this.setFaceBrightness(v5, ForgeDirection.EAST);
			if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.EAST.ordinal()))
				for (int i = 0; i < li.size(); i++) {
					int edge = li.get(i);
					IIcon ico = b.getIconForEdge(world, x, y, z, edge);
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					v5.addVertexWithUV(1, 0, 0, du, v);
					v5.addVertexWithUV(1, 1, 0, u, v);
					v5.addVertexWithUV(1, 1, 1, u, dv);
					v5.addVertexWithUV(1, 0, 1, du, dv);
				}

			li = b.getEdgesForFace(world, x, y, z, ForgeDirection.WEST);
			this.setFaceBrightness(v5, ForgeDirection.WEST);
			if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.WEST.ordinal()))
				for (int i = 0; i < li.size(); i++) {
					int edge = li.get(i);
					IIcon ico = b.getIconForEdge(world, x, y, z, edge);
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					v5.addVertexWithUV(0, 1, 0, u, v);
					v5.addVertexWithUV(0, 0, 0, du, v);
					v5.addVertexWithUV(0, 0, 1, du, dv);
					v5.addVertexWithUV(0, 1, 1, u, dv);
				}

			li = b.getEdgesForFace(world, x, y, z, ForgeDirection.SOUTH);
			this.setFaceBrightness(v5, ForgeDirection.SOUTH);
			if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.SOUTH.ordinal()))
				for (int i = 0; i < li.size(); i++) {
					int edge = li.get(i);
					IIcon ico = b.getIconForEdge(world, x, y, z, edge);
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					v5.addVertexWithUV(0, 1, 1, u, v);
					v5.addVertexWithUV(0, 0, 1, du, v);
					v5.addVertexWithUV(1, 0, 1, du, dv);
					v5.addVertexWithUV(1, 1, 1, u, dv);
				}

			li = b.getEdgesForFace(world, x, y, z, ForgeDirection.NORTH);
			this.setFaceBrightness(v5, ForgeDirection.NORTH);
			if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.NORTH.ordinal()))
				for (int i = 0; i < li.size(); i++) {
					int edge = li.get(i);
					IIcon ico = b.getIconForEdge(world, x, y, z, edge);
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					v5.addVertexWithUV(0, 0, 0, du, v);
					v5.addVertexWithUV(0, 1, 0, u, v);
					v5.addVertexWithUV(1, 1, 0, u, dv);
					v5.addVertexWithUV(1, 0, 0, du, dv);
				}
		}
		else if (block == BlockRegistry.DECOTANK.getBlockInstance()) {
			double d = 0.001;

			TileEntityDecoTank tile = (TileEntityDecoTank)world.getTileEntity(x, y, z);
			if (tile != null) {
				Fluid f = tile.getFluid();
				if (f != null) {
					if (f.getLuminosity() >= 10 || TankFlags.LIGHTED.apply(world, x, y, z)) {
						v5.setBrightness(240);
						//ReikaRenderHelper.disableLighting();
					}
					else {
						v5.setBrightness(mix);
					}
					if (TankFlags.NOCOLOR.apply(world, x, y, z))
						v5.setColorOpaque_I(0xffffff);
					else
						v5.setColorOpaque_I(f.getColor());
					IIcon ico = f.getIcon();
					float u = ico.getMinU();
					float du = ico.getMaxU();
					float v = ico.getMinV();
					float dv = ico.getMaxV();
					float uu = du-u;
					float vv = dv-v;
					float dx = uu/16F;
					float dz = vv/16F;

					double maxx = world.getBlock(x+1, y, z) == block ? 1 : 1-d;
					double minx = world.getBlock(x-1, y, z) == block ? 0 : d;
					double maxy = world.getBlock(x, y+1, z) == block ? 1 : 1-d;
					double miny = world.getBlock(x, y-1, z) == block ? 0 : d;
					double maxz = world.getBlock(x, y, z+1) == block ? 1 : 1-d;
					double minz = world.getBlock(x, y, z-1) == block ? 0 : d;

					if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.UP.ordinal())) {
						if (f.getLuminosity() < 10)
							this.setFaceBrightness(v5, ForgeDirection.UP);
						else
							v5.setBrightness(240);
						v5.addVertexWithUV(maxx, maxy, minz, u, v);
						v5.addVertexWithUV(minx, maxy, minz, du, v);
						v5.addVertexWithUV(minx, maxy, maxz, du, dv);
						v5.addVertexWithUV(maxx, maxy, maxz, u, dv);
					}

					if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.DOWN.ordinal())) {
						if (f.getLuminosity() < 10)
							this.setFaceBrightness(v5, ForgeDirection.DOWN);
						else
							v5.setBrightness(240);
						v5.addVertexWithUV(minx, miny, minz, du, v);
						v5.addVertexWithUV(maxx, miny, minz, u, v);
						v5.addVertexWithUV(maxx, miny, maxz, u, dv);
						v5.addVertexWithUV(minx, miny, maxz, du, dv);
					}

					if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.EAST.ordinal())) {
						if (f.getLuminosity() < 10)
							this.setFaceBrightness(v5, ForgeDirection.EAST);
						else
							v5.setBrightness(240);
						v5.addVertexWithUV(maxx, miny, minz, du, v);
						v5.addVertexWithUV(maxx, maxy, minz, u, v);
						v5.addVertexWithUV(maxx, maxy, maxz, u, dv);
						v5.addVertexWithUV(maxx, miny, maxz, du, dv);
					}

					if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.WEST.ordinal())) {
						if (f.getLuminosity() < 10)
							this.setFaceBrightness(v5, ForgeDirection.WEST);
						else
							v5.setBrightness(240);
						v5.addVertexWithUV(minx, maxy, minz, u, v);
						v5.addVertexWithUV(minx, miny, minz, du, v);
						v5.addVertexWithUV(minx, miny, maxz, du, dv);
						v5.addVertexWithUV(minx, maxy, maxz, u, dv);
					}

					if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.SOUTH.ordinal())) {
						if (f.getLuminosity() < 10)
							this.setFaceBrightness(v5, ForgeDirection.SOUTH);
						else
							v5.setBrightness(240);
						v5.addVertexWithUV(d, 1-d, 1-d, u, v);
						v5.addVertexWithUV(d, d, 1-d, du, v);
						v5.addVertexWithUV(1-d, d, 1-d, du, dv);
						v5.addVertexWithUV(1-d, 1-d, 1-d, u, dv);
					}

					if (block.shouldSideBeRendered(world, x, y, z, ForgeDirection.NORTH.ordinal())) {
						if (f.getLuminosity() < 10)
							this.setFaceBrightness(v5, ForgeDirection.NORTH);
						else
							v5.setBrightness(240);
						v5.addVertexWithUV(minx, miny, minz, du, v);
						v5.addVertexWithUV(minx, maxy, minz, u, v);
						v5.addVertexWithUV(maxx, maxy, minz, u, dv);
						v5.addVertexWithUV(maxx, miny, minz, du, dv);
					}
				}
			}
			//ReikaRenderHelper.enableLighting();
		}

		v5.addVertex(0, 0, 0);
		v5.addVertex(0, 0, 0);
		v5.addVertex(0, 0, 0);
		v5.addVertex(0, 0, 0);
		v5.addTranslation(-x, -y, -z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int model) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

	private void setFaceBrightness(Tessellator v5, ForgeDirection dir) {
		float f = 1;
		switch(dir) {
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

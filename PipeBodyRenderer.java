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

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.RenderableDuct;
import Reika.RotaryCraft.Registry.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PipeBodyRenderer implements ISimpleBlockRenderingHandler {

	public final int renderID;
	public int renderPass;
	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public PipeBodyRenderer(int ID) {
		renderID = ID;
		//MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks rb) {
		Tessellator v5 = Tessellator.instance;

		GL11.glColor3f(1, 1, 1);
		double s = 1.25;
		GL11.glScaled(s, s, s);
		ReikaTextureHelper.bindTerrainTexture();
		v5.startDrawingQuads();

		IIcon ico = block.getIcon(0, metadata);
		IIcon gico = block.getIcon(1, metadata);
		if (ico == null)
			ico = BlockRegistry.LIGHT.getBlockInstance().getIcon(0, 0);
		if (gico == null)
			gico = BlockRegistry.LIGHT.getBlockInstance().getIcon(0, 0);

		float dx = -0.5F;
		float dy = -0.5F;
		float dz = -0.5F;
		v5.addTranslation(dx, dy, dz);
		v5.setNormal(0, 0.75F, 0);

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			this.renderInventoryFace(ico, gico, dir);
		}

		v5.addTranslation(-dx, -dy, -dz);

		v5.draw();
		GL11.glScaled(1D/s, 1D/s, 1D/s);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator.instance.addVertex(0, 0, 0);
		Tessellator.instance.addVertex(0, 0, 0);
		Tessellator.instance.addVertex(0, 0, 0);
		Tessellator.instance.addVertex(0, 0, 0);
		RenderableDuct tile = (RenderableDuct)world.getTileEntity(x, y, z);
		for (int i = 0; i < 6; i++) {
			switch(renderPass) {
			case 0:
				this.renderFace(tile, world, x, y, z, dirs[i]);
				break;
			case 1:
				if (tile.isFluidPipe()) {
					this.renderLiquid(tile, x, y, z, dirs[i]);
				}
				break;
			}
		}
		Tessellator.instance.addVertex(0, 0, 0);
		Tessellator.instance.addVertex(0, 0, 0);
		Tessellator.instance.addVertex(0, 0, 0);
		Tessellator.instance.addVertex(0, 0, 0);
		return true;
	}

	/** Rendering outside the main render block. */
	@SubscribeEvent
	public void finalRender(RenderWorldEvent.Post evt) {
		int i = evt.renderer.posX;
		int j = evt.renderer.posY;
		int k = evt.renderer.posZ;
		ChunkCache cache = evt.chunkCache;
		for (int y = j; y < j+16; y++) {
			for (int z = k; z < k+16; z++) {
				for (int x = i; x < i+16; x++) {
					Block block = cache.getBlock(x, y, z);
					int meta = cache.getBlockMetadata(x, y, z);
					if (block.hasTileEntity(meta)) {
						TileEntity te = cache.getTileEntity(x, y, z);
						if (te instanceof RenderableDuct) {
							RenderableDuct tile = (RenderableDuct)te;
							Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
							for (int f = 0; f < 6; f++) {
								switch(evt.pass) {
								case 0:
									;//this.renderFace(tile, cache, x, y, z, dirs[f]);
									break;
								case 1:
									if (tile.isFluidPipe()) {
										//this.renderLiquid(tile, x, y, z, dirs[f]);
										Tessellator.instance.addVertex(-2*x+3, -2*y+3, -2*z+3);
										Tessellator.instance.addVertex(-x, -y, -z);
										Tessellator.instance.addVertex(0, 0, 0);
										Tessellator.instance.addVertex(x, y, z);
										Tessellator.instance.addVertex(x+3, y+3, z+3);
										Tessellator.instance.addVertex(2*x+3, 2*y+3, 2*z+3);
										ReikaJavaLibrary.pConsole(Tessellator.instance.isDrawing);
									}
									break;
								}
							}
							Tessellator.instance.draw();
						}
					}
				}
			}
		}
	}

	@Override
	public boolean shouldRender3DInInventory(int model) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

	private void renderOverlay(RenderableDuct tile, IBlockAccess world, int x, int y, int z, ForgeDirection dir, IIcon ico) {
		if (tile.isConnectionValidForSide(dir))
			return;
		Tessellator v5 = Tessellator.instance;
		v5.draw();
		ReikaTextureHelper.bindTerrainTexture();
		float size = 0.75F/2F;

		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		v5.startDrawingQuads();
		v5.setColorOpaque(255, 255, 255);
		v5.setBrightness(240);
		v5.addTranslation(x, y, z);
		v5.setNormal(dir.offsetX, dir.offsetY, dir.offsetZ);
		switch(dir) {
		case DOWN:
			v5.addVertexWithUV(0.5+size, 0.5-size-0.001, 0.5+size, u, v);
			v5.addVertexWithUV(0.5-size, 0.5-size-0.001, 0.5+size, du, v);
			v5.addVertexWithUV(0.5-size, 0.5-size-0.001, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5+size, 0.5-size-0.001, 0.5-size, u, dv);
			break;
		case EAST:
			break;
		case NORTH:
			break;
		case SOUTH:
			break;
		case UP:
			v5.addVertexWithUV(0.5+size, 0.5+size+0.001, 0.5-size, u, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size+0.001, 0.5-size, du, dv);
			v5.addVertexWithUV(0.5-size, 0.5+size+0.001, 0.5+size, du, v);
			v5.addVertexWithUV(0.5+size, 0.5+size+0.001, 0.5+size, u, v);
			break;
		case WEST:
			break;
		default:
			break;
		}
		v5.addTranslation(-x, -y, -z);
		v5.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		v5.startDrawingQuads();
	}

	private void renderLiquid(RenderableDuct tile, int x, int y, int z, ForgeDirection dir) {
		Fluid f = tile.getFluidType();
		if (f == null)
			return;
		Tessellator v5 = Tessellator.instance;
		//TesselatorVertexState st = ReikaRenderHelper.getTessellatorState();
		Entity e = Minecraft.getMinecraft().renderViewEntity;
		v5.draw();
		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;
		double in = 0.5+size-0.01;
		double in2 = 0.5-size+0.01;
		double dd2 = in-in2;

		IIcon ico = f.getIcon();
		float u = ico.getMinU();
		float v = ico.getMinV();
		float u2 = ico.getMaxU();
		float v2 = ico.getMaxV();
		double du = dd2*(u2-u)/4D;

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		v5.startDrawingQuads();
		v5.addTranslation(x, y, z);
		int mix = tile.getPipeBlockType().getMixedBrightnessForBlock(Minecraft.getMinecraft().theWorld, x, y, z);
		ReikaLiquidRenderer.bindFluidTexture(f);
		v5.setColorOpaque(255, 255, 255);
		if (f.getLuminosity() > 0) {
			v5.setBrightness(240);
			//ReikaRenderHelper.disableLighting();
		}
		else {
			v5.setBrightness(mix);
		}

		v5.setNormal(dir.offsetX, -dir.offsetY, -dir.offsetZ);
		//this.faceBrightness(ForgeDirection.DOWN, v5);
		if (!tile.isConnectionValidForSide(dir)) {
			switch(dir) {
			case DOWN:
				v5.addVertexWithUV(in2, in, in, u, v2);
				v5.addVertexWithUV(in, in, in, u2, v2);
				v5.addVertexWithUV(in, in, in2, u2, v);
				v5.addVertexWithUV(in2, in, in2, u, v);
				break;
			case UP:
				v5.addVertexWithUV(in2, in2, in2, u, v);
				v5.addVertexWithUV(in, in2, in2, u2, v);
				v5.addVertexWithUV(in, in2, in, u2, v2);
				v5.addVertexWithUV(in2, in2, in, u, v2);
				break;
			case NORTH:
				v5.addVertexWithUV(in, in, in, u, v);
				v5.addVertexWithUV(in2, in, in, u2, v);
				v5.addVertexWithUV(in2, in2, in, u2, v2);
				v5.addVertexWithUV(in, in2, in, u, v2);
				break;
			case SOUTH:
				v5.addVertexWithUV(in, in2, in2, u, v2);
				v5.addVertexWithUV(in2, in2, in2, u2, v2);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in, in, in2, u, v);
				break;
			case EAST:
				v5.addVertexWithUV(in, in2, in, u, v2);
				v5.addVertexWithUV(in, in2, in2, u2, v2);
				v5.addVertexWithUV(in, in, in2, u2, v);
				v5.addVertexWithUV(in, in, in, u, v);
				break;
			case WEST:
				v5.addVertexWithUV(in2, in, in, u, v);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in2, in2, in2, u2, v2);
				v5.addVertexWithUV(in2, in2, in, u, v2);
			default:
				break;
			}
		}
		else { //is connected on side
			switch(dir) {
			case UP:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, in2, in, u, v);
				v5.addVertexWithUV(in2, in2, in2, u2, v);
				v5.addVertexWithUV(in2, 0, in2, u2, v+du);
				v5.addVertexWithUV(in2, 0, in, u, v+du);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, 0, in, u, v+du);
				v5.addVertexWithUV(in, 0, in2, u2, v+du);
				v5.addVertexWithUV(in, in2, in2, u2, v);
				v5.addVertexWithUV(in, in2, in, u, v);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(in, 0, in2, u, v+du);
				v5.addVertexWithUV(in2, 0, in2, u2, v+du);
				v5.addVertexWithUV(in2, in2, in2, u2, v);
				v5.addVertexWithUV(in, in2, in2, u, v);

				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(in, in2, in, u, v);
				v5.addVertexWithUV(in2, in2, in, u2, v);
				v5.addVertexWithUV(in2, 0, in, u2, v+du);
				v5.addVertexWithUV(in, 0, in, u, v+du);
				break;
			case DOWN:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, 1, in, u, v+du);
				v5.addVertexWithUV(in2, 1, in2, u2, v+du);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in2, in, in, u, v);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, in, in, u, v);
				v5.addVertexWithUV(in, in, in2, u2, v);
				v5.addVertexWithUV(in, 1, in2, u2, v+du);
				v5.addVertexWithUV(in, 1, in, u, v+du);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(in, in, in2, u, v);
				v5.addVertexWithUV(in2, in, in2, u2, v);
				v5.addVertexWithUV(in2, 1, in2, u2, v+du);
				v5.addVertexWithUV(in, 1, in2, u, v+du);

				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(in, 1, in, u, v+du);
				v5.addVertexWithUV(in2, 1, in, u2, v+du);
				v5.addVertexWithUV(in2, in, in, u2, v);
				v5.addVertexWithUV(in, in, in, u, v);
				break;
			case SOUTH:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, in2, 0, u, v2);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(in2, in, in2, u+du, v);
				v5.addVertexWithUV(in2, in, 0, u, v);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, in, 0, u, v);
				v5.addVertexWithUV(in, in, in2, u+du, v);
				v5.addVertexWithUV(in, in2, in2, u+du, v2);
				v5.addVertexWithUV(in, in2, 0, u, v2);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(in2, in, 0, u, v2);
				v5.addVertexWithUV(in2, in, in2, u+du, v2);
				v5.addVertexWithUV(in, in, in2, u+du, v);
				v5.addVertexWithUV(in, in, 0, u, v);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(in, in2, 0, u, v);
				v5.addVertexWithUV(in, in2, in2, u+du, v);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(in2, in2, 0, u, v2);
				break;
			case NORTH:
				v5.setNormal(-1, 0, 0);
				v5.addVertexWithUV(in2, in, 1, u, v);
				v5.addVertexWithUV(in2, in, in, u+du, v);
				v5.addVertexWithUV(in2, in2, in, u+du, v2);
				v5.addVertexWithUV(in2, in2, 1, u, v2);

				v5.setNormal(1, 0, 0);
				v5.addVertexWithUV(in, in2, 1, u, v2);
				v5.addVertexWithUV(in, in2, in, u+du, v2);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(in, in, 1, u, v);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(in, in, 1, u, v);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(in2, in, in, u+du, v2);
				v5.addVertexWithUV(in2, in, 1, u, v2);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(in2, in2, 1, u, v2);
				v5.addVertexWithUV(in2, in2, in, u+du, v2);
				v5.addVertexWithUV(in, in2, in, u+du, v);
				v5.addVertexWithUV(in, in2, 1, u, v);
				break;
			case EAST:
				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(1, in, in, u, v);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(in, in2, in, u+du, v2);
				v5.addVertexWithUV(1, in2, in, u, v2);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(1, in2, in2, u, v2);
				v5.addVertexWithUV(in, in2, in2, u+du, v2);
				v5.addVertexWithUV(in, in, in2, u+du, v);
				v5.addVertexWithUV(1, in, in2, u, v);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(1, in, in2, u, v2);
				v5.addVertexWithUV(in, in, in2, u+du, v2);
				v5.addVertexWithUV(in, in, in, u+du, v);
				v5.addVertexWithUV(1, in, in, u, v);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(1, in2, in, u, v);
				v5.addVertexWithUV(in, in2, in, u+du, v);
				v5.addVertexWithUV(in, in2, in2, u+du, v2);
				v5.addVertexWithUV(1, in2, in2, u, v2);
				break;
			case WEST:
				v5.setNormal(0, 0, 1);
				v5.addVertexWithUV(0, in2, in, u, v2);
				v5.addVertexWithUV(in2, in2, in, u+du, v2);
				v5.addVertexWithUV(in2, in, in, u+du, v);
				v5.addVertexWithUV(0, in, in, u, v);

				v5.setNormal(0, 0, -1);
				v5.addVertexWithUV(0, in, in2, u, v);
				v5.addVertexWithUV(in2, in, in2, u+du, v);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(0, in2, in2, u, v2);

				v5.setNormal(0, 1, 0);
				v5.addVertexWithUV(0, in, in, u, v);
				v5.addVertexWithUV(in2, in, in, u+du, v);
				v5.addVertexWithUV(in2, in, in2, u+du, v2);
				v5.addVertexWithUV(0, in, in2, u, v2);

				v5.setNormal(0, -1, 0);
				v5.addVertexWithUV(0, in2, in2, u, v2);
				v5.addVertexWithUV(in2, in2, in2, u+du, v2);
				v5.addVertexWithUV(in2, in2, in, u+du, v);
				v5.addVertexWithUV(0, in2, in, u, v);
				break;
			default:
				break;
			}

		}
		if (tile.isConnectedToNonSelf(dir)) {
			v5.setNormal(dir.offsetX, dir.offsetY, dir.offsetZ);
			switch(dir) {
			case DOWN:
				v5.addVertexWithUV(in2, 0.99, in, u, v2);
				v5.addVertexWithUV(in, 0.99, in, u2, v2);
				v5.addVertexWithUV(in, 0.99, in2, u2, v);
				v5.addVertexWithUV(in2, 0.99, in2, u, v);
				break;
			case UP:
				v5.addVertexWithUV(in2, 0.01, in2, u, v);
				v5.addVertexWithUV(in, 0.01, in2, u2, v);
				v5.addVertexWithUV(in, 0.01, in, u2, v2);
				v5.addVertexWithUV(in2, 0.01, in, u, v2);
				break;
			case NORTH:
				v5.addVertexWithUV(in, in, 0.99, u, v);
				v5.addVertexWithUV(in2, in, 0.99, u2, v);
				v5.addVertexWithUV(in2, in2, 0.99, u2, v2);
				v5.addVertexWithUV(in, in2, 0.99, u, v2);
				break;
			case SOUTH:
				v5.addVertexWithUV(in, in2, 0.01, u, v2);
				v5.addVertexWithUV(in2, in2, 0.01, u2, v2);
				v5.addVertexWithUV(in2, in, 0.01, u2, v);
				v5.addVertexWithUV(in, in, 0.01, u, v);
				break;
			case EAST:
				v5.addVertexWithUV(0.99, in2, in, u, v2);
				v5.addVertexWithUV(0.99, in2, in2, u2, v2);
				v5.addVertexWithUV(0.99, in, in2, u2, v);
				v5.addVertexWithUV(0.99, in, in, u, v);
				break;
			case WEST:
				v5.addVertexWithUV(0.01, in, in, u, v);
				v5.addVertexWithUV(0.01, in, in2, u2, v);
				v5.addVertexWithUV(0.01, in2, in2, u2, v2);
				v5.addVertexWithUV(0.01, in2, in, u, v2);
			default:
				break;
			}
		}
		v5.addTranslation(-x, -y, -z);
		v5.draw();
		GL11.glDisable(GL11.GL_CULL_FACE);
		v5.startDrawingQuads();
		//v5.setVertexState(st);

		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	public void renderBlockInInventory(RenderableDuct tile, double par2, double par4, double par6) {
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

	private void renderFace(RenderableDuct tile, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		if (tile == null)
			return;
		Tessellator v5 = Tessellator.instance;

		v5.addTranslation(x, y, z);
		int br = tile.getPipeBlockType().getMixedBrightnessForBlock(world, x, y, z);
		v5.setBrightness(br);

		this.doRenderFace(tile, world, dir);
		v5.addTranslation(-x, -y, -z);

	}

	private void doRenderFace(RenderableDuct tile, IBlockAccess world, ForgeDirection dir) {
		Tessellator v5 = Tessellator.instance;

		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;

		IIcon ico = tile.getBlockIcon();
		if (ico == null)
			ico = BlockRegistry.LIGHT.getBlockInstance().getIcon(0, 0);
		float u = ico.getMinU();
		float v = ico.getMinV();
		float u2 = ico.getMaxU();
		float v2 = ico.getMaxV();

		float ddu = u2-u;
		float ddv = v2-v;
		float uo = u;
		float vo = v;
		float u2o = u2;
		float v2o = v2;

		u += ddu*(1-size)/5;
		v += ddv*(1-size)/5;
		u2 -= ddu*(1-size)/5;
		v2 -= ddv*(1-size)/5;

		float du = ddu*dd;
		float dv = ddv*dd;

		float lx = dd+dl;
		float ly = dd+dl;
		float mx = 1-dd-dl;
		float my = 1-dd-dl;

		IIcon gico = tile.getGlassIcon();
		if (gico == null)
			gico = BlockRegistry.LIGHT.getBlockInstance().getIcon(0, 0);
		float gu = gico.getMinU();
		float gv = gico.getMinV();
		float gu2 = gico.getMaxU();
		float gv2 = gico.getMaxV();
		float dgu = gu2-gu;
		float dgv = gv2-gv;
		float guu = gu+dgu/2;
		float gvv = gv+dgv/2;

		gu += dgu/8;
		gv += dgv/8;
		gu2 -= dgu/8;
		gv2 -= dgv/8;

		if (world != null && tile.isConnectionValidForSide(dir)) {
			switch(dir) {
			case DOWN:
				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size+dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5+size+dd, 	0.5+size, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 		0.5+size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size, 		0.5+size, 	u2-du, v);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5+size-dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size-dd, 	u2-du, v);

				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5-size+dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size+dd, 	u2-du, v);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size+dd, 	0.5-size, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 		0.5+size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size, 		0.5-size, 	u2-du, v);

				v5.addVertexWithUV(0.5-size+dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size+dd, 	0.5-size, 	u2-du, vo);
				break;
			case EAST:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+window, 	u2, 	v+dv);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+size, 		u2, 	v);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5+size, 		u2o, 	v);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5+window, 	u2o, 	v+dv);

				v5.addVertexWithUV(1, 			0.5+size, 	0.5-window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5-size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-size, 		u2, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-window, 	u2, 	v2-dv);

				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size-dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size+dd, 	0.5-size+dd, 	0.5+size, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 		0.5-size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size+dd, 	0.5-size, 		0.5+size, 	u2-du, v);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5+window, 	u2o, 	v+dv);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5+size, 		u2o, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+size, 		u2, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+window, 	u2, 	v+dv);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-window, 	u2, 	v2-dv);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-size, 		u2, 	v2);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5-size, 		u2o, 	v2);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5-window, 	u2o, 	v2-dv);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size-dd, 	0.5-size, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 		0.5+size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size, 		0.5-size, 	u2-du, v);

				v5.addVertexWithUV(0.5+size+dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5-size+dd, 	0.5-size, 	u2-du, vo);
				break;
			case NORTH:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0.5+size, 	u2-du, v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+size, 	u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	1, 			u2, 	v2o);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	1, 			u2-du, v2o);

				v5.addVertexWithUV(0.5+window, 	0.5+size, 	1, 			u+du, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	1, 			u, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+size, 	u, v);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0.5+size, 	u+du, v);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5+size+dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size+dd, 	u2-du, v);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5+size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5+size+dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5+size+dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size+dd, 	u2-du, v);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	1, 			u2-du, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	1, 			u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0.5+size, 	u2-du, v);

				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0.5+size, 	u+du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+size, 	u, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	1, 			u, vo);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	1, 			u+du, vo);
				break;
			case SOUTH:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0.5-size, 	u2-du, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-size, 	u2, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0, 			u2, 	v2o);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0, 			u2-du, 	v2o);

				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0, 			u+du, 	v2o);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0, 			u, 		v2o);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-size, 	u, 		v2);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0.5-size, 	u+du, 	v2);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5-size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5-size-dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size-dd, 	u2-du, v);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5-size-dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size-dd, 	u2-du, v);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5-size-dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0, 			u2-du, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0, 			u2, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-size, 	u2, 	v2);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0.5-size, 	u2-du, 	v2);

				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0.5-size, 	u+du, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-size, 	u, 		v2);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0, 			u, 		v2o);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0, 			u+du, 	v2o);
				break;
			case UP:
				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size-dd, 	0.5+size, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 		0.5-size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size, 		0.5+size, 	u2-du, v);

				v5.addVertexWithUV(0.5-size+dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size-dd, 	0.5+size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size+dd, 	0.5+size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size+dd, 	0.5+size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0, 				0.5+size, 	gu, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	0, 				0.5+size, 	gu+dgu*size*2, gv+dgv*size);*/

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5-size+dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size+dd, 	u2-du, v);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5+size-dd, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size-dd, 	u2-du, v);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5-size-dd, 	0.5-size, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 		0.5-size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				break;
			case WEST:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5+window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5+size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+size, 		u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+window, 	u2, 	v2-dv);

				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-window, 	u2, 	v+dv);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-size, 		u2, 	v);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5-size, 		u2o, 	v);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5-window, 	u2o, 	v+dv);

				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size-dd, 	0.5+size, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 		0.5+size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size, 		0.5+size, 	u2-du, v);

				v5.addVertexWithUV(0.5-size-dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5-size+dd, 	0.5+size, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+window, 	u2, 	v2-dv);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+size, 		u2, 	v2);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5+size, 		u2o, 	v2);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5+window, 	u2o, 	v2-dv);

				v5.addVertexWithUV(0, 			0.5-size, 	0.5-window, 	u2o, 	v+dv);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5-size, 		u2o, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-size, 		u2, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-window, 	u2, 	v+dv);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size-dd, 	0.5-size+dd, 	0.5-size, 	u2-du, vo);
				v5.addVertexWithUV(0.5-size, 		0.5-size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size-dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				break;
			default:
				break;
			}
		}
		else {
			this.faceBrightness(dir, v5);
			switch(dir) {
			case DOWN:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u, 		v2-dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd+dl, 		u2, 	v2-dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd-dl, 	u2, 	v+dv);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u, 		v+dv);
				}

				v5.addVertexWithUV(mx, 1-dd, ly, gu2, gv);
				v5.addVertexWithUV(lx, 1-dd, ly, gu, gv);
				v5.addVertexWithUV(lx, 1-dd, my, gu, gv2);
				v5.addVertexWithUV(mx, 1-dd, my, gu2, gv2);

				if (tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(mx+0.5, 1-dd, ly, gu2, gv);
					v5.addVertexWithUV(lx+0.5, 1-dd, ly, gu, gv);
					v5.addVertexWithUV(lx+0.5, 1-dd, my, gu, gv2);
					v5.addVertexWithUV(mx+0.5, 1-dd, my, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(mx-0.5, 1-dd, ly, gu2, gv);
					v5.addVertexWithUV(lx-0.5, 1-dd, ly, gu, gv);
					v5.addVertexWithUV(lx-0.5, 1-dd, my, gu, gv2);
					v5.addVertexWithUV(mx-0.5, 1-dd, my, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(mx, 1-dd, ly+0.5, gu2, gv);
					v5.addVertexWithUV(lx, 1-dd, ly+0.5, gu, gv);
					v5.addVertexWithUV(lx, 1-dd, my+0.5, gu, gv2);
					v5.addVertexWithUV(mx, 1-dd, my+0.5, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(mx, 1-dd, ly-0.5, gu2, gv);
					v5.addVertexWithUV(lx, 1-dd, ly-0.5, gu, gv);
					v5.addVertexWithUV(lx, 1-dd, my-0.5, gu, gv2);
					v5.addVertexWithUV(mx, 1-dd, my-0.5, gu2, gv2);
				}
				break;
			case NORTH:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u, 		v);
				}

				v5.addVertexWithUV(mx, my, 1-dd, gu2, gv2);
				v5.addVertexWithUV(lx, my, 1-dd, gu, gv2);
				v5.addVertexWithUV(lx, ly, 1-dd, gu, gv);
				v5.addVertexWithUV(mx, ly, 1-dd, gu2, gv);

				if (tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(mx+0.5, my, 1-dd, gu2, gv2);
					v5.addVertexWithUV(lx+0.5, my, 1-dd, gu, gv2);
					v5.addVertexWithUV(lx+0.5, ly, 1-dd, gu, gv);
					v5.addVertexWithUV(mx+0.5, ly, 1-dd, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(mx-0.5, my, 1-dd, gu2, gv2);
					v5.addVertexWithUV(lx-0.5, my, 1-dd, gu, gv2);
					v5.addVertexWithUV(lx-0.5, ly, 1-dd, gu, gv);
					v5.addVertexWithUV(mx-0.5, ly, 1-dd, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(mx, my+0.5, 1-dd, gu2, gv2);
					v5.addVertexWithUV(lx, my+0.5, 1-dd, gu, gv2);
					v5.addVertexWithUV(lx, ly+0.5, 1-dd, gu, gv);
					v5.addVertexWithUV(mx, ly+0.5, 1-dd, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(mx, my-0.5, 1-dd, gu2, gv2);
					v5.addVertexWithUV(lx, my-0.5, 1-dd, gu, gv2);
					v5.addVertexWithUV(lx, ly-0.5, 1-dd, gu, gv);
					v5.addVertexWithUV(mx, ly-0.5, 1-dd, gu2, gv);
				}
				break;
			case EAST:
				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(1-dd, 		1-dd, 	dd, 		u, 		v);
					v5.addVertexWithUV(1-dd, 		1-dd, 	dd+dl, 		u+du, 	v);
					v5.addVertexWithUV(1-dd, 		dd, 	dd+dl, 		u+du, 	v2);
					v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(1-dd, 		dd+dl, 	dd, 		u, 		v2-dv);
					v5.addVertexWithUV(1-dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(1-dd, 		1-dd, 		dd, 	u, 		v);
					v5.addVertexWithUV(1-dd, 		1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
				}

				v5.addVertexWithUV(1-dd, ly, mx, gu2, gv);
				v5.addVertexWithUV(1-dd, ly, lx, gu, gv);
				v5.addVertexWithUV(1-dd, my, lx, gu, gv2);
				v5.addVertexWithUV(1-dd, my, mx, gu2, gv2);

				if (tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(1-dd, ly, mx+0.5, gu2, gv);
					v5.addVertexWithUV(1-dd, ly, lx+0.5, gu, gv);
					v5.addVertexWithUV(1-dd, my, lx+0.5, gu, gv2);
					v5.addVertexWithUV(1-dd, my, mx+0.5, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(1-dd, ly, mx-0.5, gu2, gv);
					v5.addVertexWithUV(1-dd, ly, lx-0.5, gu, gv);
					v5.addVertexWithUV(1-dd, my, lx-0.5, gu, gv2);
					v5.addVertexWithUV(1-dd, my, mx-0.5, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(1-dd, ly-0.5, mx, gu2, gv);
					v5.addVertexWithUV(1-dd, ly-0.5, lx, gu, gv);
					v5.addVertexWithUV(1-dd, my-0.5, lx, gu, gv2);
					v5.addVertexWithUV(1-dd, my-0.5, mx, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(1-dd, ly+0.5, mx, gu2, gv);
					v5.addVertexWithUV(1-dd, ly+0.5, lx, gu, gv);
					v5.addVertexWithUV(1-dd, my+0.5, lx, gu, gv2);
					v5.addVertexWithUV(1-dd, my+0.5, mx, gu2, gv2);
				}
				break;
			case WEST:
				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u+du, 	v2);
					v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
					v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 		dd, 	u, 		v);
				}

				v5.addVertexWithUV(dd, my, mx, gu2, gv2);
				v5.addVertexWithUV(dd, my, lx, gu, gv2);
				v5.addVertexWithUV(dd, ly, lx, gu, gv);
				v5.addVertexWithUV(dd, ly, mx, gu2, gv);

				if (tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, my, mx+0.5, gu2, gv2);
					v5.addVertexWithUV(dd, my, lx+0.5, gu, gv2);
					v5.addVertexWithUV(dd, ly, lx+0.5, gu, gv);
					v5.addVertexWithUV(dd, ly, mx+0.5, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, my, mx-0.5, gu2, gv2);
					v5.addVertexWithUV(dd, my, lx-0.5, gu, gv2);
					v5.addVertexWithUV(dd, ly, lx-0.5, gu, gv);
					v5.addVertexWithUV(dd, ly, mx-0.5, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, my-0.5, mx, gu2, gv2);
					v5.addVertexWithUV(dd, my-0.5, lx, gu, gv2);
					v5.addVertexWithUV(dd, ly-0.5, lx, gu, gv);
					v5.addVertexWithUV(dd, ly-0.5, mx, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, my+0.5, mx, gu2, gv2);
					v5.addVertexWithUV(dd, my+0.5, lx, gu, gv2);
					v5.addVertexWithUV(dd, ly+0.5, lx, gu, gv);
					v5.addVertexWithUV(dd, ly+0.5, mx, gu2, gv);
				}
				break;
			case UP:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd+dl, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd-dl, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);
				}

				v5.addVertexWithUV(mx, dd, my, gu2, gv2);
				v5.addVertexWithUV(lx, dd, my, gu, gv2);
				v5.addVertexWithUV(lx, dd, ly, gu, gv);
				v5.addVertexWithUV(mx, dd, ly, gu2, gv);

				if (tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(mx+0.5, dd, my, gu2, gv2);
					v5.addVertexWithUV(lx+0.5, dd, my, gu, gv2);
					v5.addVertexWithUV(lx+0.5, dd, ly, gu, gv);
					v5.addVertexWithUV(mx+0.5, dd, ly, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(mx-0.5, dd, my, gu2, gv2);
					v5.addVertexWithUV(lx-0.5, dd, my, gu, gv2);
					v5.addVertexWithUV(lx-0.5, dd, ly, gu, gv);
					v5.addVertexWithUV(mx-0.5, dd, ly, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(mx, dd, my+0.5, gu2, gv2);
					v5.addVertexWithUV(lx, dd, my+0.5, gu, gv2);
					v5.addVertexWithUV(lx, dd, ly+0.5, gu, gv);
					v5.addVertexWithUV(mx, dd, ly+0.5, gu2, gv);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(mx, dd, my-0.5, gu2, gv2);
					v5.addVertexWithUV(lx, dd, my-0.5, gu, gv2);
					v5.addVertexWithUV(lx, dd, ly-0.5, gu, gv);
					v5.addVertexWithUV(mx, dd, ly-0.5, gu2, gv);
				}
				break;
			case SOUTH:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v);
					v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
					v5.addVertexWithUV(1-dd, 	dd+dl, 	dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd, 		dd, 		u, 		v);
					v5.addVertexWithUV(1-dd, 	1-dd, 		dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 	1-dd-dl, 	dd, 	u2, 	v+dv);
					v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
				}

				v5.addVertexWithUV(mx, ly, dd, gu2, gv);
				v5.addVertexWithUV(lx, ly, dd, gu, gv);
				v5.addVertexWithUV(lx, my, dd, gu, gv2);
				v5.addVertexWithUV(mx, my, dd, gu2, gv2);

				if (tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(mx+0.5, ly, dd, gu2, gv);
					v5.addVertexWithUV(lx+0.5, ly, dd, gu, gv);
					v5.addVertexWithUV(lx+0.5, my, dd, gu, gv2);
					v5.addVertexWithUV(mx+0.5, my, dd, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(mx-0.5, ly, dd, gu2, gv);
					v5.addVertexWithUV(lx-0.5, ly, dd, gu, gv);
					v5.addVertexWithUV(lx-0.5, my, dd, gu, gv2);
					v5.addVertexWithUV(mx-0.5, my, dd, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(mx, ly+0.5, dd, gu2, gv);
					v5.addVertexWithUV(lx, ly+0.5, dd, gu, gv);
					v5.addVertexWithUV(lx, my+0.5, dd, gu, gv2);
					v5.addVertexWithUV(mx, my+0.5, dd, gu2, gv2);
				}
				if (tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(mx, ly-0.5, dd, gu2, gv);
					v5.addVertexWithUV(lx, ly-0.5, dd, gu, gv);
					v5.addVertexWithUV(lx, my-0.5, dd, gu, gv2);
					v5.addVertexWithUV(mx, my-0.5, dd, gu2, gv2);
				}
				break;
			default:
				break;
			}
		}
		if (tile.isConnectedToNonSelf(dir)) {
			switch(dir) {
			case DOWN:
				v5.addVertexWithUV(dd, 		1, 	1-dd, 		u, 		v);
				v5.addVertexWithUV(dd+dl, 	1, 	1-dd, 		u+du, 	v);
				v5.addVertexWithUV(dd+dl, 	1, 	dd, 		u+du, 	v2);
				v5.addVertexWithUV(dd, 		1, 	dd, 		u, 		v2);

				v5.addVertexWithUV(1-dd-dl, 1, 	1-dd, 		u2-du, 	v);
				v5.addVertexWithUV(1-dd, 	1, 	1-dd, 		u2, 	v);
				v5.addVertexWithUV(1-dd, 	1, 	dd, 		u2, 	v2);
				v5.addVertexWithUV(1-dd-dl, 1, 	dd, 		u2-du, 	v2);

				v5.addVertexWithUV(dd, 		1, 	dd+dl, 		u, 		v2-dv);
				v5.addVertexWithUV(1-dd, 	1, 	dd+dl, 		u2, 	v2-dv);
				v5.addVertexWithUV(1-dd, 	1, 	dd, 		u2, 	v2);
				v5.addVertexWithUV(dd, 		1, 	dd, 		u, 		v2);

				v5.addVertexWithUV(dd, 		1, 	1-dd, 		u, 		v);
				v5.addVertexWithUV(1-dd, 	1, 	1-dd, 		u2, 	v);
				v5.addVertexWithUV(1-dd, 	1, 	1-dd-dl, 	u2, 	v+dv);
				v5.addVertexWithUV(dd, 		1, 	1-dd-dl, 	u, 		v+dv);
				break;
			case UP:
				v5.addVertexWithUV(dd, 		0, 	dd, 		u, 		v2);
				v5.addVertexWithUV(dd+dl, 	0, 	dd, 		u+du, 	v2);
				v5.addVertexWithUV(dd+dl, 	0, 	1-dd, 		u+du, 	v);
				v5.addVertexWithUV(dd, 		0, 	1-dd, 		u, 		v);

				v5.addVertexWithUV(1-dd-dl, 0, 	dd, 		u2-du, 	v2);
				v5.addVertexWithUV(1-dd, 	0, 	dd, 		u2, 	v2);
				v5.addVertexWithUV(1-dd, 	0, 	1-dd, 		u2, 	v);
				v5.addVertexWithUV(1-dd-dl, 0, 	1-dd, 		u2-du, 	v);

				v5.addVertexWithUV(dd, 		0, 	dd, 		u, 		v2);
				v5.addVertexWithUV(1-dd, 	0, 	dd, 		u2, 	v2);
				v5.addVertexWithUV(1-dd, 	0, 	dd+dl, 		u2, 	v2-dv);
				v5.addVertexWithUV(dd, 		0, 	dd+dl, 		u, 		v2-dv);

				v5.addVertexWithUV(dd, 		0, 	1-dd-dl, 	u, 		v+dv);
				v5.addVertexWithUV(1-dd, 	0, 	1-dd-dl, 	u2, 	v+dv);
				v5.addVertexWithUV(1-dd, 	0, 	1-dd, 		u2, 	v);
				v5.addVertexWithUV(dd, 		0, 	1-dd, 		u, 		v);
				break;
			case SOUTH:
				v5.addVertexWithUV(dd, 		1-dd, 	0, 		u, 		v);
				v5.addVertexWithUV(dd+dl, 	1-dd, 	0, 		u+du, 	v);
				v5.addVertexWithUV(dd+dl, 	dd, 	0, 		u+du, 	v2);
				v5.addVertexWithUV(dd, 		dd, 	0, 		u, 		v2);

				v5.addVertexWithUV(1-dd-dl, 1-dd, 	0, 		u2-du, 	v);
				v5.addVertexWithUV(1-dd, 	1-dd, 	0, 		u2, 	v);
				v5.addVertexWithUV(1-dd, 	dd, 	0, 		u2, 	v2);
				v5.addVertexWithUV(1-dd-dl, dd, 	0, 		u2-du, 	v2);

				v5.addVertexWithUV(dd, 		dd+dl, 	0, 		u, 		v2-dv);
				v5.addVertexWithUV(1-dd, 	dd+dl, 	0, 		u2, 	v2-dv);
				v5.addVertexWithUV(1-dd, 	dd, 	0, 		u2, 	v2);
				v5.addVertexWithUV(dd, 		dd, 	0, 		u, 		v2);

				v5.addVertexWithUV(dd, 		1-dd, 		0, 		u, 		v);
				v5.addVertexWithUV(1-dd, 	1-dd, 		0, 		u2, 	v);
				v5.addVertexWithUV(1-dd, 	1-dd-dl, 	0, 	u2, 	v+dv);
				v5.addVertexWithUV(dd, 		1-dd-dl, 	0, 	u, 		v+dv);
				break;
			case NORTH:
				v5.addVertexWithUV(dd, 		dd, 	1, 		u, 		v2);
				v5.addVertexWithUV(dd+dl, 	dd, 	1, 		u+du, 	v2);
				v5.addVertexWithUV(dd+dl, 	1-dd, 	1, 		u+du, 	v);
				v5.addVertexWithUV(dd, 		1-dd, 	1, 		u, 		v);

				v5.addVertexWithUV(1-dd-dl, dd, 	1, 		u2-du, 	v2);
				v5.addVertexWithUV(1-dd, 	dd, 	1, 		u2, 	v2);
				v5.addVertexWithUV(1-dd, 	1-dd, 	1, 		u2, 	v);
				v5.addVertexWithUV(1-dd-dl, 1-dd, 	1, 		u2-du, 	v);

				v5.addVertexWithUV(dd, 		dd, 	1, 		u, 		v2);
				v5.addVertexWithUV(1-dd, 	dd, 	1, 		u2, 	v2);
				v5.addVertexWithUV(1-dd, 	dd+dl, 	1, 		u2, 	v2-dv);
				v5.addVertexWithUV(dd, 		dd+dl, 	1, 		u, 		v2-dv);

				v5.addVertexWithUV(dd, 		1-dd-dl, 	1, 	u, 		v+dv);
				v5.addVertexWithUV(1-dd, 	1-dd-dl, 	1, 	u2, 	v+dv);
				v5.addVertexWithUV(1-dd, 	1-dd, 		1, 	u2, 	v);
				v5.addVertexWithUV(dd, 		1-dd, 		1, 	u, 		v);
				break;
			case EAST:
				v5.addVertexWithUV(1, 		1-dd, 	dd, 		u, 		v);
				v5.addVertexWithUV(1, 		1-dd, 	dd+dl, 		u+du, 	v);
				v5.addVertexWithUV(1, 		dd, 	dd+dl, 		u+du, 	v2);
				v5.addVertexWithUV(1, 		dd, 	dd, 		u, 		v2);

				v5.addVertexWithUV(1,		1-dd, 	1-dd-dl, 	u2-du, 	v);
				v5.addVertexWithUV(1,		1-dd, 	1-dd, 		u2, 	v);
				v5.addVertexWithUV(1,		dd, 	1-dd, 		u2, 	v2);
				v5.addVertexWithUV(1,		dd, 	1-dd-dl, 	u2-du, 	v2);

				v5.addVertexWithUV(1,		dd+dl, 	dd, 		u, 		v2-dv);
				v5.addVertexWithUV(1,		dd+dl, 	1-dd, 		u2, 	v2-dv);
				v5.addVertexWithUV(1,		dd, 	1-dd, 		u2, 	v2);
				v5.addVertexWithUV(1,		dd, 	dd, 		u, 		v2);

				v5.addVertexWithUV(1,		1-dd, 		dd, 	u, 		v);
				v5.addVertexWithUV(1,		1-dd, 		1-dd, 	u2, 	v);
				v5.addVertexWithUV(1,		1-dd-dl, 	1-dd, 	u2, 	v+dv);
				v5.addVertexWithUV(1,		1-dd-dl, 	dd, 	u, 		v+dv);
				break;
			case WEST:
				v5.addVertexWithUV(0, 		dd, 	dd, 		u, 		v2);
				v5.addVertexWithUV(0, 		dd, 	dd+dl, 		u+du, 	v2);
				v5.addVertexWithUV(0, 		1-dd, 	dd+dl, 		u+du, 	v);
				v5.addVertexWithUV(0, 		1-dd, 	dd, 		u, 		v);

				v5.addVertexWithUV(0, 		dd, 	1-dd-dl, 	u2-du, 	v2);
				v5.addVertexWithUV(0, 		dd, 	1-dd, 		u2, 	v2);
				v5.addVertexWithUV(0, 		1-dd, 	1-dd, 		u2, 	v);
				v5.addVertexWithUV(0, 		1-dd, 	1-dd-dl, 	u2-du, 	v);

				v5.addVertexWithUV(0, 		dd, 	dd, 		u, 		v2);
				v5.addVertexWithUV(0, 		dd, 	1-dd, 		u2, 	v2);
				v5.addVertexWithUV(0, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
				v5.addVertexWithUV(0, 		dd+dl, 	dd, 		u, 		v2-dv);

				v5.addVertexWithUV(0, 		1-dd-dl, 	dd, 	u, 		v+dv);
				v5.addVertexWithUV(0, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
				v5.addVertexWithUV(0, 		1-dd, 		1-dd, 	u2, 	v);
				v5.addVertexWithUV(0, 		1-dd, 		dd, 	u, 		v);
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

	private void renderInventoryFace(IIcon ico, IIcon gico, ForgeDirection dir) {
		Tessellator v5 = Tessellator.instance;

		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;

		float u = ico.getMinU();
		float v = ico.getMinV();
		float u2 = ico.getMaxU();
		float v2 = ico.getMaxV();

		float ddu = u2-u;
		float ddv = v2-v;
		float uo = u;
		float vo = v;
		float u2o = u2;
		float v2o = v2;

		u += ddu*(1-size)/5;
		v += ddv*(1-size)/5;
		u2 -= ddu*(1-size)/5;
		v2 -= ddv*(1-size)/5;

		float du = ddu*dd;
		float dv = ddv*dd;

		float lx = dd+dl;
		float ly = dd+dl;
		float mx = 1-dd-dl;
		float my = 1-dd-dl;

		float gu = gico.getMinU();
		float gv = gico.getMinV();
		float gu2 = gico.getMaxU();
		float gv2 = gico.getMaxV();
		float dgu = gu2-gu;
		float dgv = gv2-gv;

		float guu = gu+dgu*dl;
		float gvv = gv+dgv*dl;

		gu += dgu/8;
		gv += dgv/8;
		gu2 -= dgu/8;
		gv2 -= dgv/8;

		this.faceBrightness(dir, v5);
		switch(dir) {
		case DOWN:
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v2);

			v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u, 		v2-dv);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd+dl, 		u2, 	v2-dv);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd-dl, 	u2, 	v+dv);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u, 		v+dv);

			v5.addVertexWithUV(mx, 1-dd, ly, gu2, gv);
			v5.addVertexWithUV(lx, 1-dd, ly, gu, gv);
			v5.addVertexWithUV(lx, 1-dd, my, gu, gv2);
			v5.addVertexWithUV(mx, 1-dd, my, gu2, gv2);
			break;
		case NORTH:
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
			v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);

			v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);

			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v2);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	dd+dl, 	1-dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u, 		v2-dv);

			v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u, 		v+dv);
			v5.addVertexWithUV(1-dd, 	1-dd-dl, 	1-dd, 	u2, 	v+dv);
			v5.addVertexWithUV(1-dd, 	1-dd, 		1-dd, 	u2, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u, 		v);

			v5.addVertexWithUV(mx, my, 1-dd, gu2, gv2);
			v5.addVertexWithUV(lx, my, 1-dd, gu, gv2);
			v5.addVertexWithUV(lx, ly, 1-dd, gu, gv);
			v5.addVertexWithUV(mx, ly, 1-dd, gu2, gv);
			break;
		case EAST:
			v5.addVertexWithUV(1-dd, 		1-dd, 	dd, 		u, 		v);
			v5.addVertexWithUV(1-dd, 		1-dd, 	dd+dl, 		u+du, 	v);
			v5.addVertexWithUV(1-dd, 		dd, 	dd+dl, 		u+du, 	v2);
			v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
			v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);

			v5.addVertexWithUV(1-dd, 		dd+dl, 	dd, 		u, 		v2-dv);
			v5.addVertexWithUV(1-dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd, 		1-dd, 		dd, 	u, 		v);
			v5.addVertexWithUV(1-dd, 		1-dd, 		1-dd, 	u2, 	v);
			v5.addVertexWithUV(1-dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
			v5.addVertexWithUV(1-dd, 		1-dd-dl, 	dd, 	u, 		v+dv);

			v5.addVertexWithUV(1-dd, ly, mx, gu2, gv);
			v5.addVertexWithUV(1-dd, ly, lx, gu, gv);
			v5.addVertexWithUV(1-dd, my, lx, gu, gv2);
			v5.addVertexWithUV(1-dd, my, mx, gu2, gv2);
			break;
		case WEST:
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u+du, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u+du, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);

			v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);

			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);

			v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
			v5.addVertexWithUV(dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
			v5.addVertexWithUV(dd, 		1-dd, 		1-dd, 	u2, 	v);
			v5.addVertexWithUV(dd, 		1-dd, 		dd, 	u, 		v);

			v5.addVertexWithUV(dd, my, mx, gu2, gv2);
			v5.addVertexWithUV(dd, my, lx, gu, gv2);
			v5.addVertexWithUV(dd, ly, lx, gu, gv);
			v5.addVertexWithUV(dd, ly, mx, gu2, gv);
			break;
		case UP:
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd+dl, 	dd, 	1-dd, 		u+du, 	v);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);

			v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd-dl, dd, 	1-dd, 		u2-du, 	v);

			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd, 	dd, 	dd+dl, 		u2, 	v2-dv);
			v5.addVertexWithUV(dd, 		dd, 	dd+dl, 		u, 		v2-dv);

			v5.addVertexWithUV(dd, 		dd, 	1-dd-dl, 	u, 		v+dv);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd-dl, 	u2, 	v+dv);
			v5.addVertexWithUV(1-dd, 	dd, 	1-dd, 		u2, 	v);
			v5.addVertexWithUV(dd, 		dd, 	1-dd, 		u, 		v);

			v5.addVertexWithUV(mx, dd, my, gu2, gv2);
			v5.addVertexWithUV(lx, dd, my, gu, gv2);
			v5.addVertexWithUV(lx, dd, ly, gu, gv);
			v5.addVertexWithUV(mx, dd, ly, gu2, gv);
			break;
		case SOUTH:
			v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
			v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v);
			v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);

			v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
			v5.addVertexWithUV(1-dd, 	dd+dl, 	dd, 		u2, 	v2-dv);
			v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
			v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);

			v5.addVertexWithUV(dd, 		1-dd, 		dd, 		u, 		v);
			v5.addVertexWithUV(1-dd, 	1-dd, 		dd, 		u2, 	v);
			v5.addVertexWithUV(1-dd, 	1-dd-dl, 	dd, 	u2, 	v+dv);
			v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);

			v5.addVertexWithUV(mx, ly, dd, gu2, gv);
			v5.addVertexWithUV(lx, ly, dd, gu, gv);
			v5.addVertexWithUV(lx, my, dd, gu, gv2);
			v5.addVertexWithUV(mx, my, dd, gu2, gv2);
			break;
		default:
			break;
		}
	}

}
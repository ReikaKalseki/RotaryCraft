package Reika.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import Reika.RotaryCraft.Base.TileEntityPiping;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class PipeBodyRenderer implements ISimpleBlockRenderingHandler {

	//add marker array to TileEntityPiping - cache connectivity of each side, maybe even
	//update with onNeighborBlockChange in BlockPiping

	public final int renderID;
	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public PipeBodyRenderer(int ID) {
		renderID = ID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntityPiping tile = (TileEntityPiping)world.getBlockTileEntity(x, y, z);
		for (int i = 0; i < 6; i++) {
			this.renderFace(tile, x, y, z, dirs[i]);
		}
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

	private void renderFace(TileEntityPiping tile, int x, int y, int z, ForgeDirection dir) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		float size = 0.75F/2F;
		float window = 0.5F/2F;
		float dl = size-window;
		float dd = 0.5F-size;

		Icon ico = tile.getBlockIcon();
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

		Icon gico = Block.glass.getIcon(0, 0);
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

		Tessellator v5 = Tessellator.instance;
		v5.addTranslation(x, y, z);

		int dx = tile.xCoord+dir.offsetX;
		int dy = tile.yCoord+dir.offsetY;
		int dz = tile.zCoord+dir.offsetZ;
		int br = tile.getBlockType().getMixedBrightnessForBlock(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		v5.setBrightness(br);

		if (tile.isInWorld() && tile.isConnectionValidForSide(dir)) {
			switch(dir) {
			case DOWN:
				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size+dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size+dd, 	0.5+size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size-dd, 	0.5+size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size-dd, 	0.5+size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	1, 				0.5+size, 	gu+dgu*size*2, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	1, 				0.5+size, 	gu, gv+dgv*size);*/

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size+dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size+dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size+dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size+dd, 	0.5-size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5+size-dd, 	0.5-size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5+size-dd, 	0.5-size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	1, 				0.5-size, 	gu+dgu*size*2, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	1, 				0.5-size, 	gu, gv+dgv*size);*/
				break;
			case EAST:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5+window, 	u2o, 	v+dv);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5+size, 		u2o, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+size, 		u2, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+window, 	u2, 	v+dv);

				v5.addVertexWithUV(1, 			0.5+size, 	0.5-window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(1, 			0.5+size, 	0.5-size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-size, 		u2, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-window, 	u2, 	v2-dv);

				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size-dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size+dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5-size+dd, 	0.5+size, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5+window, 	u2o, 	v+dv);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5+size, 		u2o, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+size, 		u2, 	v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+window, 	u2, 	v+dv);

				v5.addVertexWithUV(1, 			0.5-size, 	0.5-window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(1, 			0.5-size, 	0.5-size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-size, 		u2, 	v2);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-window, 	u2, 	v2-dv);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5+size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size+dd, 	0.5+size-dd, 	0.5-size, 	u2-du, vo);

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

				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0.5+size, 	u+du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5+size, 	u, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	1, 			u, vo);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	1, 			u+du, vo);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5+size+dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5+size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5+size+dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5+size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	1, 			u2, vo);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	1, 			u2-du, vo);

				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0.5+size, 	u+du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5+size, 	u, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	1, 			u, vo);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	1, 			u+du, vo);
				break;
			case SOUTH:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0, 			u2-du, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0, 			u2, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 	0.5-size, 	u2, 	v2);
				v5.addVertexWithUV(0.5+window, 	0.5+size, 	0.5-size, 	u2-du, 	v2);

				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0, 			u+du, 	v2o);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0, 			u, 		v2o);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-size, 	u, 		v2);
				v5.addVertexWithUV(0.5-window, 	0.5+size, 	0.5-size, 	u+du, 	v2);

				this.faceBrightness(ForgeDirection.EAST, v5);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5+size-dd, 	0.5-size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size+dd, 	0.5-size-dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5+size-dd, 	0.5-size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size+dd, 	0.5-size-dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0, 			u2-du, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0, 			u2, 	v2o);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 	0.5-size, 	u2, 	v2);
				v5.addVertexWithUV(0.5+window, 	0.5-size, 	0.5-size, 	u2-du, 	v2);

				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0, 			u+du, 	v2o);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0, 			u, 		v2o);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-size, 	u, 		v2);
				v5.addVertexWithUV(0.5-window, 	0.5-size, 	0.5-size, 	u+du, 	v2);
				break;
			case UP:
				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size-dd, 	0.5+size, 	u2-du, vo);

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

				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5+size, 	0.5-size-dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.WEST, v5);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size-dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5+size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5+size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5+size-dd, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size+dd, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 		0.5-size, 		u2, v);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5-size, 		u2, vo);
				v5.addVertexWithUV(0.5-size, 	0.5-size-dd, 	0.5-size+dd, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5+size, 		0.5-size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size+dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size-dd, 	0.5-size, 	u2-du, vo);
				/*
				v5.addVertexWithUV(0.5-size+dd, 	0.5-size+dd, 	0.5-size, 	gu+dgu*size*2, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0.5-size+dd, 	0.5-size, 	gu, gv);
				v5.addVertexWithUV(0.5+size-dd, 	0, 				0.5-size, 	gu, gv+dgv*size);
				v5.addVertexWithUV(0.5-size+dd, 	0, 				0.5-size, 	gu+dgu*size*2, gv+dgv*size);*/
				break;
			case WEST:
				this.faceBrightness(ForgeDirection.DOWN, v5);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5+window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5+size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+size, 		u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5+window, 	u2, 	v2-dv);

				v5.addVertexWithUV(0, 			0.5+size, 	0.5-window, 	u2o, 	v+dv);
				v5.addVertexWithUV(0, 			0.5+size, 	0.5-size, 		u2o, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-size, 		u2, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5+size, 	0.5-window, 	u2, 	v+dv);

				this.faceBrightness(ForgeDirection.SOUTH, v5);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size-dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size-dd, 	0.5+size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size-dd, 	0.5-size, 		0.5+size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5+size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size+dd, 	0.5+size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5-size+dd, 	0.5+size, 	u2-du, vo);

				this.faceBrightness(ForgeDirection.UP, v5);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5+window, 	u2o, 	v2-dv);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5+size, 		u2o, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+size, 		u2, 	v2);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5+window, 	u2, 	v2-dv);

				v5.addVertexWithUV(0, 			0.5-size, 	0.5-window, 	u2o, 	v+dv);
				v5.addVertexWithUV(0, 			0.5-size, 	0.5-size, 		u2o, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-size, 		u2, 	v);
				v5.addVertexWithUV(0.5-size, 	0.5-size, 	0.5-window, 	u2, 	v+dv);

				this.faceBrightness(ForgeDirection.NORTH, v5);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5+size-dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5+size-dd, 	0.5-size, 	u2-du, vo);

				v5.addVertexWithUV(0.5-size-dd, 	0.5-size, 		0.5-size, 	u2-du, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size, 		0.5-size, 	u2, v);
				v5.addVertexWithUV(0.5-size, 		0.5-size+dd, 	0.5-size, 	u2, vo);
				v5.addVertexWithUV(0.5-size-dd, 	0.5-size+dd, 	0.5-size, 	u2-du, vo);
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
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	1-dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd+dl, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		1-dd, 	dd+dl, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd-dl, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd-dl, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	1-dd, 		u, 		v);
				}

				v5.addVertexWithUV(mx, 1-dd, ly, gu2, gv);
				v5.addVertexWithUV(lx, 1-dd, ly, gu, gv);
				v5.addVertexWithUV(lx, 1-dd, my, gu, gv2);
				v5.addVertexWithUV(mx, 1-dd, my, gu2, gv2);
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

				v5.addVertexWithUV(mx, ly, 1-dd, gu2, gv);
				v5.addVertexWithUV(lx, ly, 1-dd, gu, gv);
				v5.addVertexWithUV(lx, my, 1-dd, gu, gv2);
				v5.addVertexWithUV(mx, my, 1-dd, gu2, gv2);
				break;
			case EAST:
				if (!tile.isConnectionValidForSide(ForgeDirection.SOUTH)) {
					v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 		dd, 	dd+dl, 		u+du, 	v2);
					v5.addVertexWithUV(1-dd, 		1-dd, 	dd+dl, 		u+du, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 	dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.NORTH)) {
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd-dl, 	u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(1-dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 		dd, 	1-dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(1-dd, 		dd+dl, 	dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(1-dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 		1-dd, 		1-dd, 	u2, 	v);
					v5.addVertexWithUV(1-dd, 		1-dd, 		dd, 	u, 		v);
				}

				v5.addVertexWithUV(1-dd, ly, mx, gu2, gv);
				v5.addVertexWithUV(1-dd, ly, lx, gu, gv);
				v5.addVertexWithUV(1-dd, my, lx, gu, gv2);
				v5.addVertexWithUV(1-dd, my, mx, gu2, gv2);
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

				v5.addVertexWithUV(dd, ly, mx, gu2, gv);
				v5.addVertexWithUV(dd, ly, lx, gu, gv);
				v5.addVertexWithUV(dd, my, lx, gu, gv2);
				v5.addVertexWithUV(dd, my, mx, gu2, gv2);
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

				v5.addVertexWithUV(mx, dd, ly, gu2, gv);
				v5.addVertexWithUV(lx, dd, ly, gu, gv);
				v5.addVertexWithUV(lx, dd, my, gu, gv2);
				v5.addVertexWithUV(mx, dd, my, gu2, gv2);
				break;
			case SOUTH:
				if (!tile.isConnectionValidForSide(ForgeDirection.WEST)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(dd+dl, 	dd, 	dd, 		u+du, 	v2);
					v5.addVertexWithUV(dd+dl, 	1-dd, 	dd, 		u+du, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 	dd, 		u, 		v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.EAST)) {
					v5.addVertexWithUV(1-dd-dl, dd, 	dd, 		u2-du, 	v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	1-dd, 	dd, 		u2, 	v);
					v5.addVertexWithUV(1-dd-dl, 1-dd, 	dd, 		u2-du, 	v);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.UP)) {
					v5.addVertexWithUV(dd, 		dd, 	dd, 		u, 		v2);
					v5.addVertexWithUV(1-dd, 	dd, 	dd, 		u2, 	v2);
					v5.addVertexWithUV(1-dd, 	dd+dl, 	dd, 		u2, 	v2-dv);
					v5.addVertexWithUV(dd, 		dd+dl, 	dd, 		u, 		v2-dv);
				}

				if (!tile.isConnectionValidForSide(ForgeDirection.DOWN)) {
					v5.addVertexWithUV(dd, 		1-dd-dl, 	dd, 	u, 		v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd-dl, 	dd, 	u2, 	v+dv);
					v5.addVertexWithUV(1-dd, 	1-dd, 		dd, 		u2, 	v);
					v5.addVertexWithUV(dd, 		1-dd, 		dd, 		u, 		v);
				}

				v5.addVertexWithUV(mx, ly, dd, gu2, gv);
				v5.addVertexWithUV(lx, ly, dd, gu, gv);
				v5.addVertexWithUV(lx, my, dd, gu, gv2);
				v5.addVertexWithUV(mx, my, dd, gu2, gv2);
				break;
			default:
				break;
			}
		}
		v5.addTranslation(-x, -y, -z);
	}

	private void faceBrightness(ForgeDirection dir, Tessellator v5) {
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

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.EnumLook;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.TileEntities.TileEntityFlooder;
import Reika.RotaryCraft.TileEntities.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;

public class PipeRenderer extends RotaryTERenderer {

	@SuppressWarnings("unused")
	public void renderPipe(TileEntityPiping te, MachineRegistry m, double p2, double p4, double p6) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)p2-0.5F, (float)p4 + 1.5F, (float)p6 + 1.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		Tessellator v5 = new Tessellator();
		double t = 0.0625;
		double mx = 0.33; double my = 0.33; double mz = 0.33;
		double nx = 0.67; double ny = 0.67; double nz = 0.67;
		double dx = 0; double dy = 0;
		double dd = 0.45*(nx-mx)*3; double dd2 = 0.45*(ny-my)*3; double dd3 = 0.45*(nz-mz)*3;
		switch(te.getMachine()) {
		case HOSE:
			dx = 0;
			dy = 0;
			break;
		case PIPE:
			dx = 0.5;
			dy = 0;
			break;
		case FUELLINE:
			dx = 0;
			dy = 0.5;
			break;
		case SPILLER:
			dx = 0.5;
			dy = 0.5;
			break;
		default:
			break;
		}
		this.bindTextureByName("/Reika/RotaryCraft/Textures/Terrain/piping.png");
		if (te.isConnectionValidForIDAndSide(EnumLook.UP))
			my = 0;
		if (te.isConnectionValidForIDAndSide(EnumLook.DOWN))
			ny = 1;
		if (te.isConnectionValidForIDAndSide(EnumLook.PLUSX))
			nx = 1;
		if (te.isConnectionValidForIDAndSide(EnumLook.MINZ))
			nz = 1;
		if (te.isConnectionValidForIDAndSide(EnumLook.MINX))
			mx = 0;
		if (te.isConnectionValidForIDAndSide(EnumLook.PLUSZ))
			mz = 0;
		v5.startDrawingQuads();
		if (MinecraftForgeClient.getRenderPass() == 0) {
			//ReikaJavaLibrary.pConsole(my);
			RenderHelper.disableStandardItemLighting();
			if (true || mz > 0) {
				if (true) {
					v5.addVertexWithUV(mx, my+t, mz, dx, dy+dd2);
					v5.addVertexWithUV(nx, my+t, mz, dx+dd, dy+dd2);
					v5.addVertexWithUV(nx, my, mz, dx+dd, dy);
					v5.addVertexWithUV(mx, my, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, ny, mz, dx, dy);
					v5.addVertexWithUV(nx, ny, mz, dx+dd, dy);
					v5.addVertexWithUV(nx, ny-t, mz, dx+dd, dy);
					v5.addVertexWithUV(mx, ny-t, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, mz, dx, dy);
					v5.addVertexWithUV(mx, ny, mz, dx, dy+dd2);
					v5.addVertexWithUV(mx+t, ny, mz, dx, dy+dd2);
					v5.addVertexWithUV(mx+t, my, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, my, mz, dx, dy);
					v5.addVertexWithUV(nx, ny, mz, dx, dy+dd2);
					v5.addVertexWithUV(nx-t, ny, mz, dx, dy+dd2);
					v5.addVertexWithUV(nx-t, my, mz, dx, dy);
				}
			}
			if (true || nz < 1) {
				if (true) {
					v5.addVertexWithUV(mx, ny-t, nz, dx, dy);
					v5.addVertexWithUV(nx, ny-t, nz, dx+dd, dy);
					v5.addVertexWithUV(nx, ny, nz, dx+dd, dy);
					v5.addVertexWithUV(mx, ny, nz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, nz, dx, dy);
					v5.addVertexWithUV(nx, my, nz, dx+dd, dy);
					v5.addVertexWithUV(nx, my+t, nz, dx+dd, dy);
					v5.addVertexWithUV(mx, my+t, nz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, nz, dx, dy);
					v5.addVertexWithUV(mx, ny, nz, dx, dy+dd2);
					v5.addVertexWithUV(mx+t, ny, nz, dx, dy+dd2);
					v5.addVertexWithUV(mx+t, my, nz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, my, nz, dx, dy);
					v5.addVertexWithUV(nx, ny, nz, dx, dy+dd2);
					v5.addVertexWithUV(nx-t, ny, nz, dx, dy+dd2);
					v5.addVertexWithUV(nx-t, my, nz, dx, dy);
				}
			}
			if (true || my > 0) {
				if (true) {
					v5.addVertexWithUV(mx, my, mz, dx, dy);
					v5.addVertexWithUV(nx, my, mz, dx+dd, dy);
					v5.addVertexWithUV(nx, my, mz+t, dx+dd, dy);
					v5.addVertexWithUV(mx, my, mz+t, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, nz, dx, dy);
					v5.addVertexWithUV(nx, my, nz, dx+dd, dy);
					v5.addVertexWithUV(nx, my, nz-t, dx+dd, dy);
					v5.addVertexWithUV(mx, my, nz-t, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, mz, dx, dy);
					v5.addVertexWithUV(mx, my, nz, dx, dy);
					v5.addVertexWithUV(mx+t, my, nz, dx, dy);
					v5.addVertexWithUV(mx+t, my, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, my, mz, dx, dy);
					v5.addVertexWithUV(nx, my, nz, dx, dy);
					v5.addVertexWithUV(nx-t, my, nz, dx, dy);
					v5.addVertexWithUV(nx-t, my, mz, dx, dy);
				}
			}
			if (true || ny < 1) {
				if (true) {
					v5.addVertexWithUV(mx, ny, mz, dx, dy);
					v5.addVertexWithUV(nx, ny, mz, dx+dd, dy);
					v5.addVertexWithUV(nx, ny, mz+t, dx+dd, dy);
					v5.addVertexWithUV(mx, ny, mz+t, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, ny, nz, dx, dy);
					v5.addVertexWithUV(nx, ny, nz, dx+dd, dy);
					v5.addVertexWithUV(nx, ny, nz-t, dx+dd, dy);
					v5.addVertexWithUV(mx, ny, nz-t, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, ny, mz, dx, dy);
					v5.addVertexWithUV(mx, ny, nz, dx, dy);
					v5.addVertexWithUV(mx+t, ny, nz, dx, dy);
					v5.addVertexWithUV(mx+t, ny, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, ny, mz, dx, dy);
					v5.addVertexWithUV(nx, ny, nz, dx, dy);
					v5.addVertexWithUV(nx-t, ny, nz, dx, dy);
					v5.addVertexWithUV(nx-t, ny, mz, dx, dy);
				}
			}
			if (true || mx > 0) {
				if (true) {
					v5.addVertexWithUV(mx, my, mz, dx, dy);
					v5.addVertexWithUV(mx, my, nz, dx, dy);
					v5.addVertexWithUV(mx, my+t, nz, dx, dy);
					v5.addVertexWithUV(mx, my+t, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, ny, mz, dx, dy);
					v5.addVertexWithUV(mx, ny, nz, dx, dy);
					v5.addVertexWithUV(mx, ny-t, nz, dx, dy);
					v5.addVertexWithUV(mx, ny-t, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, mz, dx, dy);
					v5.addVertexWithUV(mx, ny, mz, dx, dy+dd2);
					v5.addVertexWithUV(mx, ny, mz+t, dx, dy+dd2);
					v5.addVertexWithUV(mx, my, mz+t, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(mx, my, nz, dx, dy);
					v5.addVertexWithUV(mx, ny, nz, dx, dy+dd2);
					v5.addVertexWithUV(mx, ny, nz-t, dx, dy+dd2);
					v5.addVertexWithUV(mx, my, nz-t, dx, dy);
				}
			}
			if (true || nx < 1) {
				if (true) {
					v5.addVertexWithUV(nx, my, mz, dx, dy);
					v5.addVertexWithUV(nx, my, nz, dx, dy);
					v5.addVertexWithUV(nx, my+t, nz, dx, dy);
					v5.addVertexWithUV(nx, my+t, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, ny, mz, dx, dy);
					v5.addVertexWithUV(nx, ny, nz, dx, dy);
					v5.addVertexWithUV(nx, ny-t, nz, dx, dy);
					v5.addVertexWithUV(nx, ny-t, mz, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, my, mz, dx, dy);
					v5.addVertexWithUV(nx, ny, mz, dx, dy+dd2);
					v5.addVertexWithUV(nx, ny, mz+t, dx, dy+dd2);
					v5.addVertexWithUV(nx, my, mz+t, dx, dy);
				}
				if (true) {
					v5.addVertexWithUV(nx, my, nz, dx, dy);
					v5.addVertexWithUV(nx, ny, nz, dx, dy+dd2);
					v5.addVertexWithUV(nx, ny, nz-t, dx, dy+dd2);
					v5.addVertexWithUV(nx, my, nz-t, dx, dy);
				}
			}
		}
		else {
			this.bindTextureByName("/Reika/RotaryCraft/Textures/Terrain/liquids.png");
			GL11.glEnable(GL11.GL_CULL_FACE);
			int a = 255;
			boolean liq = false;
			switch(te.getMachine()) {
			case HOSE:
				TileEntityHose h = (TileEntityHose)te;
				if (h.lubricant > 0)
					liq = true;
				break;
			case PIPE:
				TileEntityPipe p = (TileEntityPipe)te;
				if (p.liquidLevel > 0)
					liq = true;
				if (p.liquidID == Block.lavaStill.blockID || p.liquidID == Block.lavaMoving.blockID)
					dy = 0.5;
				break;
			case FUELLINE:
				TileEntityFuelLine f = (TileEntityFuelLine)te;
				if (f.fuel > 0)
					liq = true;
				break;
			case SPILLER:
				dy = 0;
				TileEntityFlooder s = (TileEntityFlooder)te;
				if (s.liquidLevel > 0)
					liq = true;
				if (s.liquidID == Block.lavaStill.blockID || s.liquidID == Block.lavaMoving.blockID)
					dy = 0.5;
				break;
			default:
				break;
			}
			if (liq) {
				RenderHelper.disableStandardItemLighting();
				v5.addVertexWithUV(mx+t, my+t, mz, dx, dy);
				v5.addVertexWithUV(mx+t, ny-t, mz, dx, dy+dd2);
				v5.addVertexWithUV(nx-t, ny-t, mz, dx+dd, dy+dd2);
				v5.addVertexWithUV(nx-t, my+t, mz, dx+dd, dy);

				v5.addVertexWithUV(nx-t, my+t, nz, dx+dd, dy);
				v5.addVertexWithUV(nx-t, ny-t, nz, dx+dd, dy+dd2);
				v5.addVertexWithUV(mx+t, ny-t, nz, dx, dy+dd2);
				v5.addVertexWithUV(mx+t, my+t, nz, dx, dy);

				v5.addVertexWithUV(mx+t, my, mz+t, dx, dy);
				v5.addVertexWithUV(nx-t, my, mz+t, dx+dd, dy);
				v5.addVertexWithUV(nx-t, my, nz-t, dx+dd, dy+dd3);
				v5.addVertexWithUV(mx+t, my, nz-t, dx, dy+dd3);

				v5.addVertexWithUV(mx+t, ny, nz-t, dx, dy+dd3);
				v5.addVertexWithUV(nx-t, ny, nz-t, dx+dd, dy+dd3);
				v5.addVertexWithUV(nx-t, ny, mz+t, dx+dd, dy);
				v5.addVertexWithUV(mx+t, ny, mz+t, dx, dy);

				v5.addVertexWithUV(mx, my+t, nz-t, dx, dy+dd3);
				v5.addVertexWithUV(mx, ny-t, nz-t, dx+dd2, dy+dd3);
				v5.addVertexWithUV(mx, ny-t, mz+t, dx+dd2, dy);
				v5.addVertexWithUV(mx, my+t, mz+t, dx, dy);

				v5.addVertexWithUV(nx, my+t, mz+t, dx, dy);
				v5.addVertexWithUV(nx, ny-t, mz+t, dx+dd2, dy);
				v5.addVertexWithUV(nx, ny-t, nz-t, dx+dd2, dy+dd3);
				v5.addVertexWithUV(nx, my+t, nz-t, dx, dy+dd3);
			}
		}
		v5.draw();
		GL11.glEnable(GL11.GL_CULL_FACE);
		if (te.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return null;
	}

	public void renderTileEntityPipingAt(TileEntityPiping tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
		{
			var9 = 0;
		}
		else
		{

			var9 = tile.getBlockMetadata();


			{
				//((BlockVacuumBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/vactex.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;
			float var13;
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			Tessellator v5 = new Tessellator();
			v5.startDrawingQuads();
			v5.addVertexWithUV(par2+0, par4+0, par6+0, 0, 0);
			v5.addVertexWithUV(par2+1, par4+0, par6+0, 1, 0);
			v5.addVertexWithUV(par2+1, par4+1, par6+0, 1, 1);
			v5.addVertexWithUV(par2+0, par4+1, par6+0, 0, 1);
			v5.draw();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			//this.renderTileEntityPipingAt((TileEntityPiping)tile, par2, par4, par6, par8);
			this.renderPipe((TileEntityPiping)tile, ((RotaryCraftTileEntity)tile).getMachine(), par2, par4, par6);
	}

}

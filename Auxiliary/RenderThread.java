/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Base.TileEntityRenderBase;

public class RenderThread implements Runnable {

	private TileEntityBase te;
	private TileEntityRenderBase renderer;

	private double cameraX;
	private double cameraY;
	private double cameraZ;
	private float cameraAngle;

	public RenderThread setTileEntity(TileEntityBase tile) {
		te = tile;
		return this;
	}

	public RenderThread setRenderer(TileEntityRenderBase render) {
		renderer = render;
		return this;
	}

	public RenderThread setParams(double par2, double par4, double par6, float par8) {
		cameraAngle = par8;
		cameraX = par2;
		cameraY = par4;
		cameraZ = par6;
		return this;
	}

	@Override
	public void run() {
		renderer.renderTileEntityAt(te, cameraX, cameraY, cameraZ, cameraAngle);
	}

}

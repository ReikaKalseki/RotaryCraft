/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

import net.minecraft.tileentity.TileEntity;

/** If you have an object that wishes to control a CVT, use this interface. You are responsible for getting the TE instance yourself.
 * The TE ({@link TileEntityAdvancedGear}) has a setController method. Only one controller per CVT, or you may get strange behavior. */
public interface CVTController {

	/** Fetch the CVT instance */
	public TileEntity getCVT();

	/** Whether the controls should be applied */
	public boolean isActive();

	/** The ratio chosen */
	public int getControlledRatio();

	/** True for torque mode, false for speed mode */
	public boolean isTorque();

}
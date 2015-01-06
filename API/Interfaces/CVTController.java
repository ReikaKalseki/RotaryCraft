/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.tileentity.TileEntity;

/** If you have an object that wishes to control a CVT, use this interface. You are responsible for getting the TileEntity instance yourself.
 * The TileEntityAdvancedGear has a setController method. Only one controller per CVT, or you may get strange behavior. */
public interface CVTController {

	/** Fetch the CVT instance */
	public TileEntity getCVT();

	/** Whether the controls should be applied; if this returns false, the CVT behaves as normal */
	public boolean isActive();

	/** The ratio chosen. It is clamped to [1,32]. */
	public int getControlledRatio();

	/** True for torque mode, false for speed mode */
	public boolean isTorque();

}

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

/** Use this on TileEntities to make them have special behavior if hit with a discharge from the Van De Graff generator. */
public interface Shockable {

	/** Called when the VDG hits the TileEntity with a bolt. Args: Charge in the bolt, distance of hit */
	public void onDischarge(int charge, double range);

	/** The minimum charge the VDG must attain before attempting to hit the TileEntity */
	public int getMinDischarge();

	/** Can this TileEntity accept discharges from a range greater than one meter (block) */
	public boolean canDischargeLongRange();

	/** Where in the block to aim the bolt tip (X-axis). Normal range 0-1. */
	public float getAimX();

	/** Where in the block to aim the bolt tip (Y-axis). Normal range 0-1. */
	public float getAimY();

	/** Where in the block to aim the bolt tip (Z-axis). Normal range 0-1. */
	public float getAimZ();

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;


public interface ShaftPowerEmitter {

	public int getOmega();

	public int getTorque();

	public long getPower();

	public int[] getOutputBlocksX();

	public int[] getOutputBlocksY();

	public int[] getOutputBlocksZ();

	/** x,y,z to write to */
	public boolean canWriteToBlock(int x, int y, int z);

	public boolean isEmitting();

}

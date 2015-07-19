/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;


/** This is not intended for you to use, but a helper bridge class for PowerTransferHelper */
public interface IOMachine/* extends ShaftMachine*/ {

	public int getWriteX();
	public int getWriteY();
	public int getWriteZ();

	public int getWriteX2();
	public int getWriteY2();
	public int getWriteZ2();

}

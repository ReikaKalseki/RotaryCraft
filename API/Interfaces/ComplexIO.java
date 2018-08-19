/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2018
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import net.minecraftforge.common.util.ForgeDirection;


public interface ComplexIO {

	public int getSpeedToSide(ForgeDirection dir);
	public int getTorqueToSide(ForgeDirection dir);

}

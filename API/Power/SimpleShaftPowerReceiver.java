/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Power;

import net.minecraftforge.common.util.ForgeDirection;

/** Implementing this gives you extremely basic boolean sensitivity to RC power. Used for things like switches and detectors, and unsuitable
 * for actual machines due to the total lack of sensitivity to the amount of power or its direction. */
public interface SimpleShaftPowerReceiver {

	public void setPowered(boolean power);

	public boolean canReadFrom(ForgeDirection dir);

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraftforge.common.util.ForgeDirection;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMachine;

/** Implement this to make your block capable of generating RC power. */
public interface ShaftPowerEmitter extends ShaftMachine, PowerGenerator {

	/** Side to write to */
	public boolean canWriteTo(ForgeDirection dir);

	/** Whether your machine is emitting power right now */
	public boolean isEmitting();

}

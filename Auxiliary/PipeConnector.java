/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.RotaryCraft.Registry.MachineRegistry;

public interface PipeConnector {

	public boolean canConnectToPipe(MachineRegistry m);

	/** Side is relative to the piping block (so DOWN means the block is below the pipe); p is the pipe type */
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side);

}

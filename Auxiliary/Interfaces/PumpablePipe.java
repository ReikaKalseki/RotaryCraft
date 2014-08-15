/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraftforge.common.util.ForgeDirection;


public interface PumpablePipe {

	public int getFluidLevel();

	public boolean canTransferTo(PumpablePipe p, ForgeDirection dir);

	public void transferFrom(PumpablePipe from, int amt);

}
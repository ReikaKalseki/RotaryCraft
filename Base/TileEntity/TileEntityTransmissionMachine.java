/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.world.World;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public abstract class TileEntityTransmissionMachine extends TileEntityIOMachine {

	protected void readFromSplitter(World world, int x, int y, int z, TileEntitySplitter spl) { //Complex enough to deserve its own function
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (x == spl.getWriteX() && z == spl.getWriteZ()) { //We are the inline
			omega = spl.omega; //omega always constant
			if (sratio == 1) { //Even split, favorbent irrelevant
				torque = spl.torque/2;
				return;
			}
			if (favorbent) {
				torque = spl.torque/sratio;
			}
			else {
				torque = (int)(spl.torque*((sratio-1D))/sratio);
			}
		}
		else if (x == spl.getWriteX2() && z == spl.getWriteZ2()) { //We are the bend
			omega = spl.omega; //omega always constant
			if (sratio == 1) { //Even split, favorbent irrelevant
				torque = spl.torque/2;
				return;
			}
			if (favorbent) {
				torque = (int)(spl.torque*((sratio-1D)/(sratio)));
			}
			else {
				torque = spl.torque/sratio;
			}
		}
		else { //We are not one of its write-to blocks
			torque = 0;
			omega = 0;
			power = 0;
			return;
		}
	}

}

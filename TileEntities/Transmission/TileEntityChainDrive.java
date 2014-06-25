/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.MachineRegistry;


public class TileEntityChainDrive extends TileEntityBeltHub {

	@Override
	public int getTorque(int input) {
		if (input > 16384) {
			ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.break");
			this.reset();
			this.resetOther();
		}
		return input;
	}

	@Override
	public int getOmega(int input) {
		if (input > 65536) {
			worldObj.setBlock(xCoord, yCoord, zCoord, 0);
			this.resetOther();
			worldObj.createExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 2, true);
		}
		return input;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CHAIN;
	}

	@Override
	public int[] getBeltColor() {
		return new int[]{80, 80, 80};
	}

	@Override
	public ItemStack getBeltItem() {
		return ItemStacks.chain.copy();
	}

}

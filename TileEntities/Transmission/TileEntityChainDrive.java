/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
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
		input = super.getTorque(input);
		if (input > this.getMaxTorque()) {
			ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.break");
			this.reset();
			this.resetOther();
		}
		return this.isSplitting() ? input/2 : input;
	}

	@Override
	public int getOmega(int input) {
		input = super.getOmega(input);
		if (input > this.getMaxSmoothSpeed()) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
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

	@Override
	public int getMaxTorque() {
		return 16384;
	}

	@Override
	public int getMaxSmoothSpeed() {
		return 65536;
	}

}

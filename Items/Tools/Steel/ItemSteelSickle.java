/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Steel;

import Reika.RotaryCraft.Base.ItemSickleBase;

public class ItemSteelSickle extends ItemSickleBase {

	public ItemSteelSickle(int index) {
		super(index);
	}

	@Override
	public int getLeafRange() {
		return 4;
	}

	@Override
	public int getCropRange() {
		return 4;
	}

	@Override
	public int getPlantRange() {
		return 4;
	}

	@Override
	public boolean canActAsShears() {
		return false;
	}

}
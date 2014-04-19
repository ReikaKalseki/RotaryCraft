/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import Reika.RotaryCraft.Base.ItemSickleBase;

public class ItemBedrockSickle extends ItemSickleBase {

	public ItemBedrockSickle(int ID, int index) {
		super(ID, index);
	}

	@Override
	public int getLeafRange() {
		return 6;
	}

	@Override
	public int getCropRange() {
		return 8;
	}

	@Override
	public int getPlantRange() {
		return 7;
	}

	@Override
	public boolean canActAsShears() {
		return true;
	}

}

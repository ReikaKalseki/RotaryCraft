/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;


public interface SodiumSolarUpgrades {

	boolean isActive();

	public static interface SodiumSolarReceiver extends SodiumSolarUpgrades {

		public void tick(int mirrorCount, float totalBrightness);

		public int getTemperature();

	}

	public static interface SodiumSolarOutput extends SodiumSolarUpgrades {

		int receiveSodium(int amt);

	}

}

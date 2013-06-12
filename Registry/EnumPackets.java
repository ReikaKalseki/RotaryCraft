/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;

public enum EnumPackets {

	BORER(0, 3),
	BEVEL(4),
	SPLITTER(6, 7),
	SPAWNER(8),
	DETECTOR(9),
	HEATER(10),
	CVT(11),
	CANNON(12, 14),
	SONIC(15, 16),
	FORCE(17),
	CHEST(18),
	COIL(19, 20),
	MUSIC(21, 28),
	VACUUM(29),
	WINDER(30),
	PROJECTOR(31),
	CONTAINMENT(32),
	ITEMCANNON(33, 35);

	private int min;
	private int max;

	private EnumPackets(int l, int h)
	{
		min = l;
		max = h;
	}

	private EnumPackets(int id)
	{
		min = id;
		max = id;
	}

	public int getMinValue() {
		return min;
	}

	public int getMaxValue() {
		return max;
	}

	public boolean isLongPacket() {
		if (this == SONIC)
			return true;
		return false;
	}

	public int getNumberDataInts() {
		if (this == MUSIC)
			return 4;
		if (this == CANNON)
			return 2;
		if (this == VACUUM)
			return 0;
		if (this == WINDER)
			return 0;
		if (this == PROJECTOR)
			return 0;
		return 1;
	}

	public boolean hasOneID() {
		return (max == min);
	}

	public static EnumPackets getEnum(int index) {
		for (EnumPackets e : EnumPackets.values()) {
			if (ReikaMathLibrary.isValueInsideBoundsIncl(e.getMinValue(), e.getMaxValue(), index))
				return e;
		}
		ReikaJavaLibrary.pConsole("Index "+index+" does not correspond to an existing packet classification!");
		return null;
	}

}

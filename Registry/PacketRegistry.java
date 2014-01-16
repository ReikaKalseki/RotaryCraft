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

import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;

public enum PacketRegistry {

	BORER(0, 3),
	BEVEL(4),
	SPLITTER(6, 7),
	SPAWNER(8),
	DETECTOR(9),
	HEATER(10),
	CVT(12, 14),
	CANNON(15, 17),
	SONIC(18, 19),
	FORCE(20),
	CHEST(21),
	COIL(22, 23),
	MUSIC(24, 31),
	VACUUM(32),
	WINDER(33),
	PROJECTOR(34),
	CONTAINMENT(35),
	ITEMCANNON(36, 38),
	MIRROR(39),
	SAFEPLAYER(40),
	ENGINEBACKFIRE(41),
	MUSICPARTICLE(42),
	DISPLAY(43, 45),
	CHESTRELOAD(46, 47),
	REDGEAR(48),
	TERRAFORMER(49),
	PNEUMATIC(50, 55),
	JETPACK(56),
	FERTILIZER(57),
	GRAVELGUN(58),
	SLIDE(59),
	POWERBUS(60, 63);

	private int min;
	private int max;

	private PacketRegistry(int l, int h)
	{
		min = l;
		max = h;
	}

	private PacketRegistry(int id)
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
		if (this == REDGEAR)
			return 2;
		return 1;
	}

	public boolean hasOneID() {
		return (max == min);
	}

	public static PacketRegistry getEnum(int index) {
		for (PacketRegistry e : PacketRegistry.values()) {
			if (ReikaMathLibrary.isValueInsideBoundsIncl(e.getMinValue(), e.getMaxValue(), index))
				return e;
		}
		ReikaJavaLibrary.pConsole("Index "+index+" does not correspond to an existing packet classification!");
		return null;
	}

}

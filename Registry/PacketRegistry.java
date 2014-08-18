/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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
	CANNON(15, 18),
	SONIC(19, 20),
	FORCE(21),
	CHEST(22),
	COIL(23, 24),
	MUSIC(25, 32),
	VACUUM(33),
	WINDER(34),
	PROJECTOR(35),
	CONTAINMENT(36),
	ITEMCANNON(37, 39),
	MIRROR(40),
	SAFEPLAYER(41),
	ENGINEBACKFIRE(42),
	MUSICPARTICLE(43),
	REDGEAR(48),
	TERRAFORMER(49),
	PNEUMATIC(50, 52),
	JETPACK(53, 55),
	FERTILIZER(56),
	GRAVELGUN(57),
	SLIDE(58),
	POWERBUS(59, 62),
	PARTICLES(63),
	BLOWER(64, 67),
	DEFOLIATOR(68),
	GPR(69);

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
		if (this == DEFOLIATOR)
			return 3;
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

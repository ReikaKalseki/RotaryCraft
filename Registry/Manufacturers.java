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

import Reika.DragonAPI.RegistrationException;
import Reika.RotaryCraft.RotaryCraft;

public enum Manufacturers {

	BEDROCKBREAKER("", ""),
	DCENGINE("", ""),
	WINDENGINE("", ""),
	STEAMENGINE("", ""),
	GASENGINE("", ""),
	ACENGINE("", ""),
	PERFENGINE("", ""),
	HYDROENGINE("", ""),
	MICROENGINE("", ""),
	JETENGINE("", "");

	public static final Manufacturers[] list = Manufacturers.values();

	private String makerName;
	private String desc;

	private Manufacturers(String n, String d) {
		makerName = n;
		desc = d;
	}

	public static boolean hasSubMakers(MachineRegistry m) {
		if (m == MachineRegistry.ENGINE)
			return true;
		if (m == MachineRegistry.ADVANCEDGEARS)
			return true;
		return false;
	}

	public static Manufacturers getSpecificMaker(MachineRegistry m, int offset) {
		if (!hasSubMakers(m))
			throw new RegistrationException(RotaryCraft.instance, "Machine "+m.getName()+" does not have sub-makers!");
		if (m == MachineRegistry.ENGINE)
			return getEngineName(m, offset);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return getAdvGearName(m, offset);
		return null;
	}

	private static Manufacturers getAdvGearName(MachineRegistry m, int offset) {
		return null;
	}

	private static Manufacturers getEngineName(MachineRegistry m, int offset) {
		return null;
	}

	public static Manufacturers getMaker(MachineRegistry m) {
		if (hasSubMakers(m))
			return getSpecificMaker(m, 0);
		return null;
	}

	public String getName() {
		return makerName;
	}

	public String getPartDesc() {
		return desc;
	}

}

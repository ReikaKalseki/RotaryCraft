/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.world.IBlockAccess;

/** Implement this on a block class that is supposed to act as an environmental heat source or sink (eg ice, lava, water, plasma) */
public interface EnvironmentalHeatSource {

	/** Returns the general type of thermal source the object is. Informs the approximate temperature. */
	public SourceType getSourceType(IBlockAccess iba, int x, int y, int z);

	/** If this returns false, the temperature effects do not apply. */
	public boolean isActive(IBlockAccess iba, int x, int y, int z);

	public static enum SourceType {
		/** Icy cold objects. Around -15C. */
		ICY(-15),

		/** Cool water. Around 12C. */
		WATER(12),

		/** Fire temperature; around 300C. */
		FIRE(300),

		/** Lava-hot. Around 1200C. */
		LAVA(1200),

		/** Ambient; effectively a no-op. */
		AMBIENT(25),

		/** Cryogenic, 77K (liquid N2) or less. */
		CRYO(77-273),

		/** Lightning and star surfaces are around this temperature. */
		PLASMA(8000),

		/** Temperatures seen only in star cores, fusion reactors, and nuclear explosions. */
		NUCLEAR(150000000);

		public final int approxTemperature;

		private static final TreeMap<Integer, SourceType> temperatureMap = new TreeMap();

		private SourceType(int t) {
			approxTemperature = t;

		}

		public static SourceType getByTemperature(int temp) {
			if (temp > 1000000)
				return NUCLEAR;
			else if (temp > 4000)
				return PLASMA;
			Entry<Integer, SourceType> colder = temperatureMap.floorEntry(temp);
			Entry<Integer, SourceType> hotter = temperatureMap.ceilingEntry(temp);
			if (colder == null)
				return hotter.getValue();
			else if (hotter == null)
				return colder.getValue();
			return temp-colder.getKey() < hotter.getKey()-temp ? colder.getValue() : hotter.getValue();
		}

		public boolean isHot() {
			return approxTemperature > 30;
		}

		public boolean isCold() {
			return approxTemperature <= 0;
		}

		static {
			for (SourceType s : values()) {
				temperatureMap.put(s.approxTemperature, s);
			}
		}
	}

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Locale;

import net.minecraft.util.StatCollector;

public enum Variables {
	PRESSURE(),
	TEMPERATURE(),
	SPEED(),
	TORQUE(),
	POWER(),
	RANGE(),
	DAMAGED(),
	FUEL();

	private String getText() {
		return StatCollector.translateToLocal("label."+this.name().toLowerCase(Locale.ENGLISH));
	}

	@Override
	public String toString() {
		return this.getText();
	}
}

package Reika.RotaryCraft.Auxiliary;

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
		return StatCollector.translateToLocal("label."+this.name().toLowerCase());
	}

	@Override
	public String toString() {
		return this.getText();
	}
}

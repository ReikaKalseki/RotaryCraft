package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.ProgressBar.DurationCallback;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;

public class MachineProgressBar implements DurationCallback {

	public final float fraction;
	public final DiscreteFunction tile;

	public MachineProgressBar(DiscreteFunction te) {
		this(te, 1);
	}

	public MachineProgressBar(DiscreteFunction te, float f) {
		tile = te;
		fraction = f;
	}

	@Override
	public int getDuration() {
		return (int)(tile.getOperationTime()*fraction);
	}

}

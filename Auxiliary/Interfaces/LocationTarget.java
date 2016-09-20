package Reika.RotaryCraft.Auxiliary.Interfaces;

import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;


public interface LocationTarget {

	public void setTarget(WorldLocation loc);

	public WorldLocation getTarget();

}

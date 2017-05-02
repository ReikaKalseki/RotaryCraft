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

import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;


public interface LocationTarget {

	public void setTarget(WorldLocation loc);

	public WorldLocation getTarget();

}

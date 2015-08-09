/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Power;

public interface PowerTracker {

	public long getMaxGennablePower();

	public long getRealMaxPower();

	public boolean isLooping();

	public boolean contains(PowerGenerator te);

	public boolean calledFrom(ShaftMerger sm);

	public boolean passesThrough(ShaftMerger sm);

	public boolean isEngineSpam();

}

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


/** If your machine merges two shafts, implement this to avoid being the source of a loop exploit or StackOverflow */
public interface ShaftMerger {

	public void onPowerLooped(PowerTracker pwr);

	public void fail();

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityVanDeGraff;

public class VDGAttackEvent extends TileEntityEvent {

	public final int attackDamage;
	public final int tileCharge;

	public VDGAttackEvent(TileEntityVanDeGraff te, int chg, int dmg) {
		super(te);

		attackDamage = dmg;
		tileCharge = chg;
	}
}

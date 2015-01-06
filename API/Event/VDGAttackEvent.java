/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;

public class VDGAttackEvent extends TileEntityEvent {

	public final int attackDamage;
	public final int tileCharge;

	public VDGAttackEvent(TileEntity te, int chg, int dmg) {
		super(te);

		attackDamage = dmg;
		tileCharge = chg;
	}
}

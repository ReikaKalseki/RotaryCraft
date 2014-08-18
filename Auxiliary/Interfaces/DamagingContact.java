/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.util.DamageSource;

public interface DamagingContact {

	public boolean canDealDamage();

	public int getContactDamage();

	public DamageSource getDamageType();

}

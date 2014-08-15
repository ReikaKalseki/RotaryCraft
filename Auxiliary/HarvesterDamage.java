/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.RotaryCraft.TileEntities.Farming.TileEntityMobHarvester;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EntityDamageSource;

public class HarvesterDamage extends EntityDamageSource {

	private final TileEntityMobHarvester tile;

	public HarvesterDamage(TileEntityMobHarvester te) {
		super("player", te.getPlacer());
		tile = te;
	}

	public boolean isTileEqual(TileEntityMobHarvester te) {
		return te != null && te.equals(tile);
	}

	public int getLootingLevel() {
		return tile.getEnchantment(Enchantment.looting);
	}

	public boolean hasInfinity() {
		return tile.getEnchantment(Enchantment.infinity) > 0;
	}

}
/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EntityDamageSource;

import Reika.RotaryCraft.TileEntities.Farming.TileEntityMobHarvester;

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
		return tile.getEnchantmentHandler().getEnchantment(Enchantment.looting);
	}

	public boolean hasInfinity() {
		return tile.getEnchantmentHandler().hasEnchantment(Enchantment.infinity);
	}

}

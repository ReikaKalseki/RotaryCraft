/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

public class TileEntityWindEngine extends TileEntityEngine {

	private void dealBladeDamage(World world, int x, int y, int z, int meta) {
		int c = 0; int d = 0;
		int a = 0; int b = 0;
		if (meta < 2)
			b = 1;
		else
			a = 1;
		switch (meta) {
		case 0:
			c = 1;
			break;
		case 1:
			c = -1;
			break;
		case 2:
			d = 1;
			break;
		case 3:
			d = -1;
			break;
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x+c, y, z+d, x+1+c, y+1, z+1+d).expand(a, 1, b);
		List in = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < in.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)in.get(i);
			ent.attackEntityFrom(DamageSource.generic, 1);
		}
	}

	@Override
	protected void consumeFuel() {

	}

	@Override
	protected void internalizeFuel() {

	}

	private float getWindFactor(World world, int x, int y, int z, int meta) {
		if (world.provider.terrainType == WorldType.FLAT) {
			if (y < 4)
				return 0;
			float f = (y-4)/16F;
			if (f > 1)
				f = 1;
			return f;
		}
		else {
			if (y < 62)
				return 0;
			float f = (y-62)/62F;
			if (f > 1)
				f = 1;
			return f;
		}
	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		int c = 0; int d = 0;
		int a = 0; int b = 0;
		if (meta < 2)
			b = 1;
		else
			a = 1;
		switch (meta) {
		case 0:
			c = 1;
			break;
		case 1:
			c = -1;
			break;
		case 2:
			d = 1;
			break;
		case 3:
			d = -1;
			break;
		}
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				Block id = world.getBlock(x+a*i+c, y+j, z+b*i+d);
				if (!ReikaWorldHelper.softBlocks(id)) {
					omega = 0;
					return false;
				}
			}
		}
		for (int i = 1; i < 16; i++) {
			Block id = world.getBlock(x+c*i, y, z+d*i);
			if (id != Blocks.air) {
				if (id.getCollisionBoundingBoxFromPool(world, x+c*i, y, z+d*i) != null)
					return false;
			}
		}
		return true;
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier) {
		soundtick++;
		if (!ConfigRegistry.ENGINESOUNDS.getState())
			return;
		float volume = 1;
		if (this.isMuffled(world, x, y, z)) {
			volume = 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		SoundRegistry.WIND.playSoundAtBlock(world, x, y, z, 1.1F*volume, 1F*pitchMultiplier);
	}

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return (int)(EngineType.WIND.getSpeed()*this.getWindFactor(world, x, y, z, meta));
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		this.dealBladeDamage(world, x, y, z, meta);
	}
}
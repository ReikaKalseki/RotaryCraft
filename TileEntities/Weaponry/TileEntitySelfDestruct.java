/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySelfDestruct extends TileEntityPowerReceiver {

	private boolean lastHasPower;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SELFDESTRUCT.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		boolean hasPower = power > 0;
		if (lastHasPower && !hasPower)
			this.destroy(world, x, y, z);
		else
			lastHasPower = hasPower;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public void destroy(World world, int x, int y, int z) {
		tickcount++;
		int n = 8;
		int count = 32;
		double rx = x+0.5+rand.nextInt(2*n+1)-n;
		double ry = y+0.5+rand.nextInt(2*n+1)-n;
		double rz = z+0.5+rand.nextInt(2*n+1)-n;
		world.createExplosion(null, rx, ry, rz, 4F, tickcount > count);
		for (int i = 0; i < 32; i++)
			world.spawnParticle("lava", rx+rand.nextInt(7)-3, ry+rand.nextInt(7)-3, rz+rand.nextInt(7)-3, 0, 0, 0);
		if (tickcount > count) {
			world.newExplosion(null, x+0.5, y+0.5, z+0.5, 12F, true, true);
			ReikaWorldHelper.temperatureEnvironment(world, x, y, z, 1000);
		}/*
		EntityCreeper e = new EntityCreeper(world);
		e.posX = rx;
		e.posZ = rz;
		e.posY = world.getTopSolidOrLiquidBlock((int)rx, (int)rz)+1;
		e.addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 10));
		world.spawnEntityInWorld(e);*/
	}

}

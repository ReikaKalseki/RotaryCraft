/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.ElectricDischarge;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityVanDeGraff extends TileEntityPowerReceiver {

	//In coloumbs
	private int charge;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	private void shock(EntityLivingBase e) {
		ElectricDischarge d = new ElectricDischarge(charge, e);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.VANDEGRAFF.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}

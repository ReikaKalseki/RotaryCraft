/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidStack;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityPressureBalancer extends RotaryCraftTileEntity {

	private int waterLevel;
	private int lavaLevel;
	private LiquidStack[] liquids = new LiquidStack[2];

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BALANCER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getLiquids(world, x, y, z, meta);
		this.convertLiquids();
	}

	private void getLiquids(World world, int x, int y, int z, int meta) {

	}

	private void convertLiquids() {

	}

}

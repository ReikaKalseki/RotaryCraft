/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Interfaces.InertIInv;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityParticleEmitter extends TileEntitySpringPowered implements OneSlotMachine, InertIInv {

	public ReikaParticleHelper particleType = ReikaParticleHelper.SMOKE;
	public double pX = 0;
	public double pY = 0;
	public double pZ = 0;
	public int particlesPerTick = 3;

	public boolean useRedstone = false;

	@Override
	public int getBaseDischargeTime() {
		return 600;
	}

	public boolean canEmit(World world, int x, int y, int z) {
		if (!this.hasCoil())
			return false;
		return !useRedstone || world.isBlockIndirectlyGettingPowered(x, y, z);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.updateCoil();
		if (this.canEmit(world, x, y, z)) {
			for (int i = 0; i < particlesPerTick; i++) {
				particleType.spawnAt(world, x+rand.nextDouble(), y+2+rand.nextDouble()*4, z+rand.nextDouble(), pX, pY, pZ);
			}
		}
	}

	private void updateCoil() {
		if (!this.hasCoil()) {
			return;
		}
		tickcount++;
		if (tickcount > this.getUnwindTime()) {
			ItemStack is = this.getDecrementedCharged();
			inv[0] = is;
			tickcount = 0;
		}
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PARTICLE;
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
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("type", particleType.ordinal());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		particleType = ReikaParticleHelper.values()[NBT.getInteger("type")];
	}

	public boolean isParticleValid(ReikaParticleHelper p) {
		if (p == ReikaParticleHelper.DRIPLAVA)
			return false;
		if (p == ReikaParticleHelper.DRIPWATER)
			return false;
		if (p == ReikaParticleHelper.SUSPEND)
			return false;
		if (p == ReikaParticleHelper.SNOWSHOVEL)
			return false;
		return true;
	}

}

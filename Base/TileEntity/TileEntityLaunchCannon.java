/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;

public abstract class TileEntityLaunchCannon extends InventoriedPowerReceiver implements DiscreteFunction, ConditionalOperation {

	public int velocity;
	public int phi;
	public int theta;

	public boolean targetMode = false;

	public int[] target = new int[3];

	@Override
	public final int getSizeInventory() {
		return 11;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	protected abstract boolean fire(World world, int x, int y, int z);

	public abstract int getMaxLaunchVelocity();

	public abstract int getMaxTheta();

	public abstract double getMaxLaunchDistance();

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		velocity = NBT.getInteger("svelocity");
		phi = NBT.getInteger("sphi");
		theta = NBT.getInteger("stheta");
		targetMode = NBT.getBoolean("istarget");
		target = NBT.getIntArray("targetxyz");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("svelocity", velocity);
		NBT.setInteger("sphi", phi);
		NBT.setInteger("stheta", theta);
		NBT.setBoolean("istarget", targetMode);
		NBT.setIntArray("targetxyz", target);
	}

	public int getOperationTime() {
		return 10;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Ammunition";
	}

}

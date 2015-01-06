/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
public class TileEntityPneumaticEngine extends EnergyToPowerBase {

	public static final int maxMJ = 36000;

	private StepTimer sound = new StepTimer(72);

	public TileEntityPneumaticEngine() {
		super();
		if (ModList.PNEUMATICRAFT.isLoaded()) {

		}
		sound.setTick(sound.getCap());
	}

	@Override
	public boolean isValidSupplier(TileEntity te) {
		if (te == null)
			return false;
		if (te.getClass().getSimpleName().contains("class name"))
			return true;
		return false;
	}

	@Override
	protected int getIdealConsumedUnitsPerTick() {
		return MathHelper.ceiling_double_int(this.getMJPerTick());
	}

	private float getMJPerTick() {
		return (float)(this.getPowerLevel()/ReikaBuildCraftHelper.getWattsPerMJ());
	}

	@Override
	public int getMaxStorage() {
		return maxMJ;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PNEUENGINE;
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
		super.updateTileEntity();

		if (!ModList.PNEUMATICRAFT.isLoaded())
			return;

		if (DragonAPICore.debugtest) {
			//fill energy buffer
		}
		this.getIOSides(world, x, y, z, meta);

		/*
		if (!world.isRemote)
			storedEnergy = (int)pp.getEnergyStored();
		if (storedEnergy < 0)
			storedEnergy = (int)pp.getMaxEnergyStored();
		 */

		this.updateSpeed();
		if (!world.isRemote) {
			sound.update();

			if (power > 0) {
				if (sound.checkCap())
					SoundRegistry.PNEUMATIC.playSoundAtBlock(world, x, y, z, this.isMuffled() ? 0.3F : 1.2F, 1);
			}
		}

		this.basicPowerReceiver();
	}

	@Override
	protected void usePower() {
		float amt = this.getMJPerTick();
		if (ModList.PNEUMATICRAFT.isLoaded())
			;//drain amt energy
	}

	public boolean isPipeConnected(ForgeDirection with) {
		switch(this.getBlockMetadata()) {
		case 0:
			return with == ForgeDirection.NORTH;
		case 1:
			return with == ForgeDirection.WEST;
		case 2:
			return with == ForgeDirection.SOUTH;
		case 3:
			return with == ForgeDirection.EAST;
		}
		return false;
	}

	@Override
	public long getMaxPower() {
		return ModList.PNEUMATICRAFT.isLoaded() ? Long.MAX_VALUE : 0;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		if (ModList.PNEUMATICRAFT.isLoaded())
			;//write to NBT
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		if (ModList.PNEUMATICRAFT.isLoaded())
			;//read from NBT
	}

	@Override
	public String getUnitDisplay() {
		return "atm";
	}

	@Override
	public int getPowerColor() {
		return ReikaColorAPI.RGBtoHex(50, 170, 255);
	}

	//@Override
	//public int getMaxSpeedBase(int tier) {
	//	return 7+4*tier;
	//}

}

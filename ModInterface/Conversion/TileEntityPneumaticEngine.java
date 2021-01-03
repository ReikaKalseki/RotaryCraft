/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Conversion;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import Reika.DragonAPI.ModInteract.Power.ReikaPneumaticHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import pneumaticCraft.api.tileentity.AirHandlerSupplier;
import pneumaticCraft.api.tileentity.IAirHandler;
import pneumaticCraft.api.tileentity.IPneumaticMachine;

@Strippable(value={"pneumaticCraft.api.tileentity.IPneumaticMachine"})
public class TileEntityPneumaticEngine extends EnergyToPowerBase implements IPneumaticMachine {

	private IAirHandler air;
	private static final int maxAir = 30000;

	private StepTimer sound = new StepTimer(72);

	public TileEntityPneumaticEngine() {
		super();
		if (ModList.PNEUMATICRAFT.isLoaded()) {
			air = AirHandlerSupplier.getAirHandler(10, 12, maxAir);
		}
		sound.setTick(sound.getCap());
	}

	@Override
	public boolean isValidSupplier(TileEntity te) {
		if (te == null)
			return false;
		if (te.getClass().getSimpleName().startsWith("pneumaticCraft.common.tileentity"))
			return true;
		return false;
	}

	@Override
	protected int getIdealConsumedUnitsPerTick() {
		return this.getAirPerTick();
	}

	private int getAirPerTick() {
		return ReikaPneumaticHelper.getWattsPerAir();
	}

	@Override
	public int getMaxStorage() {
		return maxAir;
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
			air.addAir(5, this.getConnection());
		}
		this.getIOSides(world, x, y, z, meta);

		air.updateEntityI();

		storedEnergy = air.getCurrentAir(this.getConnection());
		if (storedEnergy < 0) {
			storedEnergy = 0;
		}

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
	protected void usePower(float mult) {
		int amt = (int)(this.getAirPerTick()*mult);
		if (ModList.PNEUMATICRAFT.isLoaded())
			air.addAir(-amt, this.getConnection()); //drain amt energy
	}

	public boolean isConnectedTo(ForgeDirection with) {
		return with == this.getConnection();
	}

	private ForgeDirection getConnection() {
		switch(this.getBlockMetadata()) {
			case 0:
				return ForgeDirection.NORTH;
			case 1:
				return ForgeDirection.WEST;
			case 2:
				return ForgeDirection.SOUTH;
			case 3:
				return ForgeDirection.EAST;
			default:
				return ForgeDirection.UNKNOWN;
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		if (ModList.PNEUMATICRAFT.isLoaded())
			air.writeToNBTI(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		if (ModList.PNEUMATICRAFT.isLoaded())
			air.readFromNBTI(NBT);
	}

	@Override
	public void validate() {
		super.validate();
		if (ModList.PNEUMATICRAFT.isLoaded())
			air.validateI(this);
	}

	@Override
	public String getUnitDisplay() {
		return "mL";
	}

	@Override
	public int getPowerColor() {
		return ReikaColorAPI.RGBtoHex(50, 170, 255);
	}

	@Override
	@ModDependent(ModList.PNEUMATICRAFT)
	public IAirHandler getAirHandler() {
		return air;
	}

	//@Override
	//public int getMaxSpeedBase(int tier) {
	//	return 7+4*tier;
	//}

}

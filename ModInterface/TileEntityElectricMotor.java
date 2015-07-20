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

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.ModInteract.Power.ReikaEUHelper;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
//@Strippable(value = {"universalelectricity.api.electricity.IVoltageInput", "universalelectricity.api.energy.IEnergyInterface"})
@Strippable(value = {"ic2.api.energy.tile.IEnergySink"})
public class TileEntityElectricMotor extends EnergyToPowerBase implements PowerGenerator, SimpleProvider, IEnergySink {

	public static int CAPACITY = 90000;

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
		return MachineRegistry.ELECTRICMOTOR;
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
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		read = this.getFacing();
		write = read.getOpposite();

		this.updateSpeed();
		if (!world.isRemote) {
			tickcount++;
			if (power > 0) {
				if (tickcount >= 294) {
					tickcount = 0;
					SoundRegistry.ELECTRIC.playSoundAtBlock(world, x, y, z, this.isMuffled() ? 0.08F : 0.2F, 0.51F);
				}
			}
		}

		this.basicPowerReceiver();
	}

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

	@Override
	public int getMaxStorage() {
		return CAPACITY;
	}

	@Override
	protected int getIdealConsumedUnitsPerTick() {
		return MathHelper.ceiling_double_int(power/ReikaEUHelper.getWattsPerEU());
	}

	@Override
	public String getUnitDisplay() {
		return "EU";
	}

	@Override
	public int getPowerColor() {
		return ReikaColorAPI.RGBtoHex(255, 220, 0);
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection dir) {
		return (dir == this.getFacing() || dir == ForgeDirection.DOWN) && this.isValidSupplier(emitter);
	}

	@Override
	public double getDemandedEnergy() {
		return this.getMaxStorage()-this.getStoredPower();
	}

	@Override
	public int getSinkTier() {
		//ReikaJavaLibrary.pConsole(ReikaEUHelper.getIC2TierFromPower(this.getTierPower(this.getTier())));
		return 5;//this.getScaledTier();
	}

	private int getScaledTier() {
		return ReikaEUHelper.getIC2TierFromPower(this.getTierPower());
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		int tier = ReikaEUHelper.getIC2TierFromEUVoltage(voltage);
		int tier1 = this.getScaledTier();
		//if (tier != tier1) {
		//	this.onWrongVoltage(tier, tier1);
		//	//ReikaJavaLibrary.pConsole(tier+":"+tier1);
		//	return 0;
		//}
		double add = Math.min(amount, this.getMaxStorage()-storedEnergy);
		storedEnergy += add;
		return amount-add;
	}

	private void onWrongVoltage(int tier, int correct) {
		int over = tier-correct;
		if (over > 2 && TileEntityGenerator.hardModeEU) {
			worldObj.newExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 9F, true, true);
		}
		else if (over > 1) {
			ReikaParticleHelper.SMOKE.spawnAroundBlock(worldObj, xCoord, yCoord, zCoord, 12);
			ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.fizz");
		}
		else if (over == 1) {
			if (rand.nextInt(5) == 0) {
				ReikaParticleHelper.SMOKE.spawnAroundBlock(worldObj, xCoord, yCoord, zCoord, 12);
				ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.fizz");
			}
			if (rand.nextInt(15) == 0)
				SoundRegistry.ELECTRIC.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 0.2F, 2F);
		}
	}

	@Override
	@ModDependent(ModList.IC2)
	public boolean isValidSupplier(TileEntity te) {
		return te instanceof IEnergySource || te instanceof IEnergyConductor || te instanceof IEnergyStorage;
	}

	@Override
	public void onFirstTick(World world, int x, int y, int z) {
		this.getIOSides(world, x, y, z, this.getBlockMetadata());
		if (!world.isRemote && ModList.IC2.isLoaded())
			this.addTileToNet();
	}

	@ModDependent(ModList.IC2)
	private void addTileToNet() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	}

	@Override
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalidate) {
		if (!world.isRemote && ModList.IC2.isLoaded())
			this.removeTileFromNet();
	}

	@ModDependent(ModList.IC2)
	private void removeTileFromNet() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
	}
}

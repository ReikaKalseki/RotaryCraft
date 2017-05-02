/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.ChromatiCraft.API.Interfaces.WorldRift;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Interfaces.TileEntity.InertIInv;
import Reika.DragonAPI.Interfaces.TileEntity.PartialInventory;
import Reika.DragonAPI.Interfaces.TileEntity.PartialTank;
import Reika.DragonAPI.Interfaces.TileEntity.ToggleTile;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.API.Interfaces.CVTController;
import Reika.RotaryCraft.API.Interfaces.CVTController.CVTControllable;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityAdvancedGear extends TileEntity1DTransmitter implements ISidedInventory, PowerGenerator, PartialInventory, PartialTank,
PipeConnector, IFluidHandler, ToggleTile, CVTControllable {

	private boolean isReleasing = false;
	private int releaseTorque = 0;
	private int releaseOmega = 0;
	/** Stored energy, in joules */
	private long energy;

	public static final int WORMRATIO = 16;

	private CVTController controller;

	private ItemStack[] belts = new ItemStack[31];

	private final HybridTank lubricant = new HybridTank("advgear", 20000);

	private final CVTState[] cvtState = new CVTState[2];

	public boolean isRedstoneControlled;

	private boolean isBedrockCoil = false;

	private boolean isCreative;

	private StepTimer redstoneTimer = new StepTimer(40);

	public boolean torquemode = true;

	private boolean enabled = true;

	public static long getMaxStorageCapacity(boolean bedrock) {
		return bedrock ? 240L*ReikaMathLibrary.longpow(10, 12) : 720*ReikaMathLibrary.intpow2(10, 6);
	}

	public static String getMaxStorageCapacityFormatted(boolean bedrock) {
		long max = getMaxStorageCapacity(bedrock);
		return String.format("%.3f%sJ", ReikaMathLibrary.getThousandBase(max), ReikaEngLibrary.getSIPrefix(max));
	}

	public static String getRequiredInputPower() {
		return "CEIL2((log_2(energy))^4)";
	}

	public static String getRequiredInputTorque() {
		return "CEIL2((log_2(energy))^3)";
	}

	public static int getOutputCap(boolean bedrock) {
		return bedrock ? 4096 : 1024;
	}

	public static String getOutputFunction() {
		return "CEIL2_pseudo(SQRT(energy)/4)";
	}

	public void setController(CVTController c) {
		controller = c;
	}

	public long getMaxStorageCapacity() {
		return this.getGearType().storesEnergy() ? this.getMaxStorageCapacity(isBedrockCoil) : 0;
	}

	public void setBedrock(boolean bedrock) {
		isBedrockCoil = bedrock;
	}

	public void setCreative(boolean flag) {
		isCreative = flag;
	}

	public int getMaximumEmission() {
		return isCreative ? Integer.MAX_VALUE : getOutputCap(this.isBedrockCoil()); //still the 16x increase in the coil item
	}

	public int getReleaseTorque() {
		return releaseTorque;
	}

	public int getReleaseOmega() {
		return releaseOmega;
	}

	public void setReleaseTorque(int torque) {
		releaseTorque = Math.min(this.getTorqueCap(), Math.min(this.getMaximumEmission(), torque));
	}

	public void setReleaseOmega(int omega) {
		releaseOmega = Math.min(this.getMaximumEmission(), omega);
	}

	public enum GearType {
		WORM(),
		CVT(),
		COIL(),
		HIGH();

		public static final GearType[] list = values();

		public boolean isLubricated() {
			return this == CVT || this.consumesLubricant();
		}

		public boolean consumesLubricant() {
			return this == HIGH;
		}

		public boolean hasLosses() {
			return this == WORM;
		}

		public boolean storesEnergy() {
			return this == COIL;
		}

		public boolean hasInventory() {
			return this == CVT;
		}
	}

	public GearType getGearType() {
		int meta = this.getBlockMetadata();
		return GearType.list[meta/4];
	}

	public int getLubricant() {
		return lubricant.getLevel();
	}

	public void addLubricant(int amt) {
		lubricant.addLiquid(amt, FluidRegistry.getFluid("rc lubricant"));
	}

	public boolean hasLubricant() {
		return !lubricant.isEmpty();
	}

	public boolean canAcceptAnotherLubricantBucket() {
		return lubricant.getLevel()+1000 <= lubricant.getCapacity();
	}

	//-ve ratio is torque mode for cvt
	@Override
	protected void readFromSplitter(World world, int x, int y, int z, TileEntitySplitter spl) { //Complex enough to deserve its own function
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (this.getGearType() == GearType.WORM || this.getGearType() == GearType.CVT && this.getEffectiveRatio() < 0) {
			if (x == spl.getWriteX() && z == spl.getWriteZ()) { //We are the inline
				omega = Math.abs((int)(spl.omega/this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega))); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = Math.abs((int)(spl.torque/2*this.getEffectiveRatio()));
					return;
				}
				if (favorbent) {
					torque = Math.abs((int)(spl.torque/sratio*this.getEffectiveRatio()));
				}
				else {
					torque = Math.abs((int)(this.getEffectiveRatio()*(int)(spl.torque*((sratio-1D)/(sratio)))));
				}
			}
			else if (x == spl.getWriteX2() && z == spl.getWriteZ2()) { //We are the bend
				omega = Math.abs((int)(spl.omega/this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega))); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = Math.abs((int)(spl.torque/2*this.getEffectiveRatio()));
					return;
				}
				if (favorbent) {
					torque = Math.abs((int)(this.getEffectiveRatio()*(int)(spl.torque*((sratio-1D)/(sratio)))));
				}
				else {
					torque = Math.abs((int)(spl.torque/sratio*this.getEffectiveRatio()));
				}
			}
			else { //We are not one of its write-to blocks
				torque = 0;
				omega = 0;
				power = 0;
				return;
			}
		}
		else if (this.getGearType() == GearType.HIGH) {
			if (this.hasLubricant()) {
				if (this.getEffectiveRatio() < 0) {
					if (x == spl.getWriteX() && z == spl.getWriteZ()) { //We are the inline
						omega = -(int)(spl.omega/this.getEffectiveRatio()); //omega always constant
						if (sratio == 1) { //Even split, favorbent irrelevant
							torque = Math.abs((int)(spl.torque/2*this.getEffectiveRatio()));
						}
						else if (favorbent) {
							torque = Math.abs((int)(spl.torque/sratio*this.getEffectiveRatio()));
						}
						else {
							torque = Math.abs((int)(this.getEffectiveRatio()*(int)(spl.torque*((sratio-1D)/(sratio)))));
						}
					}
					else if (x == spl.getWriteX2() && z == spl.getWriteZ2()) { //We are the bend
						omega = -(int)(spl.omega/this.getEffectiveRatio()); //omega always constant
						if (sratio == 1) { //Even split, favorbent irrelevant
							torque = Math.abs((int)(spl.torque/2*this.getEffectiveRatio()));
						}
						else if (favorbent) {
							torque = Math.abs((int)(this.getEffectiveRatio()*(int)(spl.torque*((sratio-1D)/(sratio)))));
						}
						else {
							torque = Math.abs((int)(spl.torque/sratio*this.getEffectiveRatio()));
						}
					}
					else { //We are not one of its write-to blocks
						torque = 0;
						omega = 0;
						power = 0;
						return;
					}
				}
				else {
					if (x == spl.getWriteX() && z == spl.getWriteZ()) { //We are the inline
						omega = (int)(spl.omega*this.getEffectiveRatio()); //omega always constant
						if (sratio == 1) { //Even split, favorbent irrelevant
							torque = (int)(spl.torque/2/this.getEffectiveRatio());
						}
						else if (favorbent) {
							torque = (int)(spl.torque/sratio/this.getEffectiveRatio());
						}
						else {
							torque = (int)((spl.torque*((sratio-1D))/sratio)/(this.getEffectiveRatio()));
						}
					}
					else if (x == spl.getWriteX2() && z == spl.getWriteZ2()) { //We are the bend
						omega = (int)(spl.omega*this.getEffectiveRatio()); //omega always constant
						if (sratio == 1) { //Even split, favorbent irrelevant
							torque = (int)(spl.torque/2/this.getEffectiveRatio());
						}
						else if (favorbent) {
							torque = (int)(spl.torque*((sratio-1D)/(sratio))/this.getEffectiveRatio());
						}
						else {
							torque = (int)(spl.torque/sratio/this.getEffectiveRatio());
						}
					}
					else { //We are not one of its write-to blocks
						torque = 0;
						omega = 0;
						power = 0;
						return;
					}
				}
				if (omega > 0 && (worldObj.getTotalWorldTime()&4) == 4)
					lubricant.removeLiquid((int)ReikaMathLibrary.logbase(Math.max(omega, torque), 2));
			}
			else {
				omega = torque = 0;
			}
		}
		else {
			if (x == spl.getWriteX() && z == spl.getWriteZ()) { //We are the inline
				omega = (int)(spl.omega*this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega)); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = (int)(spl.torque/2/this.getEffectiveRatio());
				}
				else if (favorbent) {
					torque = (int)(spl.torque/sratio/this.getEffectiveRatio());
				}
				else {
					torque = (int)((spl.torque*((sratio-1D))/sratio)/(this.getEffectiveRatio()));
				}
			}
			else if (x == spl.getWriteX2() && z == spl.getWriteZ2()) { //We are the bend
				omega = (int)(spl.omega*this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega)); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = (int)(spl.torque/2/this.getEffectiveRatio());
				}
				else if (favorbent) {
					torque = (int)(spl.torque*((sratio-1D)/(sratio))/this.getEffectiveRatio());
				}
				else {
					torque = (int)(spl.torque/sratio/this.getEffectiveRatio());
				}
			}
			else { //We are not one of its write-to blocks
				torque = 0;
				omega = 0;
				power = 0;
			}
		}
	}

	private double getEffectiveRatio() {
		GearType type = this.getGearType();
		if (type == GearType.COIL)
			return 1;
		if (type == GearType.WORM)
			return WORMRATIO;
		if (type == GearType.HIGH)
			return torquemode ? -256 : 256;
		return this.getCVTRatio();
	}

	private int getCVTRatio() {
		if (isRedstoneControlled) {
			int ratio = this.getCVTState(this.hasRedstoneSignal()).gearRatio;
			return (int)Math.signum(ratio)*Math.min(Math.abs(ratio), this.getMaxRatio());
		}
		else {
			return ratio;
		}
	}

	private double getPowerLossFraction(int speed) {
		if (this.getGearType() == GearType.WORM)
			return (128-4*ReikaMathLibrary.logbase(speed, 2))/100;
		return 1;
	}

	public double getCurrentLoss() {
		return this.getPowerLossFraction(omegain);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		if (this.getGearType() == GearType.WORM)
			return false;
		return this.isPlayerAccessible(var1);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		if (this.getGearType() == GearType.CVT) {
			if (controller != null && controller.isActive() && controller.getCVT().equals(this)) {
				boolean torque = controller.isTorque();
				int r = MathHelper.clamp_int(controller.getControlledRatio(), 1, 32);
				ratio = torque ? r : -r;
			}
		}
		if (this.getGearType().storesEnergy())
			this.store(world, x, y, z, meta);
		else
			this.transferPower(world, x, y, z, meta);
		power = (long)omega*(long)torque;
		//ReikaJavaLibrary.pConsole(torque+" @ "+omega);

		this.basicPowerReceiver();

		if (this.getGearType().storesEnergy()) {
			redstoneTimer.update();
			if (redstoneTimer.checkCap())
				ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
		}
	}

	public boolean isBedrockCoil() {
		return isBedrockCoil;
	}

	private void store(World world, int x, int y, int z, int meta) {
		this.transferPower(world, x, y, z, meta);
		isReleasing = enabled && this.hasRedstoneSignal();
		//ReikaJavaLibrary.pConsole(energy/20+"/"+this.getMaxStorageCapacity(), Side.SERVER);
		if (!isCreative && !world.isRemote && energy/20 >= this.getMaxStorageCapacity()) {
			this.overChargeExplosion(world, x, y, z);
		}
		if (!isReleasing) {
			torque = omega = 0;
			power = 0;
			if (energy + ((long)torquein*(long)omegain) < 0 || energy + ((long)torquein*(long)omegain) > Long.MAX_VALUE) {
				this.destroy(world, x, y, z);
			}
			else if (!isCreative) {
				long pwr = (long)torquein*(long)omegain;
				//if (torquein >= this.getChargingTorque() && pwr >= this.getChargingPower())
				if (torquein >= this.getChargingTorque() && pwr >= this.getChargingPower())
					energy += pwr;
			}
		}
		else if (energy > 0 && releaseTorque > 0 && releaseOmega > 0) {
			releaseTorque = Math.min(releaseTorque, this.getTorqueCap());
			torque = releaseTorque;
			omega = releaseOmega;
			power = (long)torque*(long)omega;
			if (this.getTicksExisted()%26 == 0)
				SoundRegistry.COIL.playSoundAtBlock(this);
			if (!isCreative)
				energy -= power;
			if (energy < 0)
				energy = 0;
		}
		else {
			torque = 0;
			omega = 0;
			power = 0;
		}
	}

	public long getChargingPower() {
		return energy >= 20 ? ReikaMathLibrary.ceil2exp(ReikaMathLibrary.intpow2(ReikaMathLibrary.logbase2(energy/20), 4)) : 1;
	}

	public int getChargingTorque() {
		return energy >= 20 ? ReikaMathLibrary.ceil2exp(ReikaMathLibrary.intpow2(ReikaMathLibrary.logbase2(energy/20), 3))/2 : 1;
	}

	public int getTorqueCap() {
		return ReikaMathLibrary.ceilPseudo2Exp((int)Math.ceil(Math.sqrt(energy/20)/4));
	}

	private void overChargeExplosion(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		int num = isBedrockCoil ? 24 : 3;
		int pow = isBedrockCoil ? 12 : 8;
		int r = isBedrockCoil ? 9 : 1;
		for (int i = 0; i < num; i++) {
			double rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
			double ry = ReikaRandomHelper.getRandomPlusMinus(y, r);
			double rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
			world.createExplosion(null, rx, ry, rz, 8, ConfigRegistry.BLOCKDAMAGE.getState());
		}
		world.createExplosion(null, x, y, z, pow, ConfigRegistry.BLOCKDAMAGE.getState());
	}

	private void destroy(World world, int x, int y, int z) {
		for (int i = 0; i < 16; i++)
			ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 5, 0.2F);
		ReikaParticleHelper.EXPLODE.spawnAroundBlock(world, x, y, z, 2);
		int r = 20;
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				for (int k = -r; k <= r; k++) {
					double dd = ReikaMathLibrary.py3d(i, j*2, k);
					if (dd <= r+0.5) {
						if (world.getBlock(x+i, y+j, z+k) != Blocks.bedrock) {
							world.setBlockToAir(x+i, y+j, z+k);
							world.markBlockForUpdate(x+i, y+j, z+k);
						}
					}
					if (!world.isRemote && rand.nextInt(8) == 0)
						ReikaWorldHelper.ignite(world, x+i, y+j, z+k);
				}
			}
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		while (metadata > 3)
			metadata -= 4;
		super.getIOSides(world, x, y, z, metadata, false);
	}

	private void calculateRatio() {
		int sign = 1;
		if (ratio < 0)
			sign = -1;
		if (Math.abs(ratio) > this.getMaxRatio())
			ratio = this.getMaxRatio()*sign;
		if (ratio == 0)
			ratio = 1;
	}

	public void setRatio(int ratio) {
		if (ratio == 0) {
			this.ratio = 1;
		}
		else {
			int sign = ratio < 0 ? -1 : 1;
			int maxrat = Math.min(Math.abs(ratio), this.getMaxRatio());
			this.ratio = maxrat*sign;
		}
	}

	public int getMaxRatio() {
		if (belts[0] == null)
			return 1;
		if (belts[0].getItem() != ItemStacks.belt.getItem() || belts[0].getItemDamage() != ItemStacks.belt.getItemDamage())
			return 1;
		for (int i = 1; i <= 2; i++) {
			if (belts[i] == null)
				return 2;
			if (belts[i].getItem() != ItemStacks.belt.getItem() || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 2;
		}
		for (int i = 3; i <= 6; i++) {
			if (belts[i] == null)
				return 4;
			if (belts[i].getItem() != ItemStacks.belt.getItem() || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 4;
		}
		for (int i = 7; i <= 14; i++) {
			if (belts[i] == null)
				return 8;
			if (belts[i].getItem() != ItemStacks.belt.getItem() || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 8;
		}
		for (int i = 15; i <= 30; i++) {
			if (belts[i] == null)
				return 16;
			if (belts[i].getItem() != ItemStacks.belt.getItem() || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 16;
		}
		return 32;
	}

	@Override
	protected void readFromCross(TileEntityShaft cross) {
		if (cross.isWritingTo(this)) {
			omega = cross.readomega[0];
			if (this.getGearType() == GearType.WORM)
				omega = (int)((((omega / WORMRATIO)*(100-4*ReikaMathLibrary.logbase(omega, 2)+28)))/100);
			torque = cross.readtorque[0];
			if (this.getGearType() == GearType.WORM)
				torque = torque * WORMRATIO;
		}
		else if (cross.isWritingTo2(this)) {
			omega = cross.readomega[1];
			if (this.getGearType() == GearType.WORM)
				omega = (int)((((omega / WORMRATIO)*(100-4*ReikaMathLibrary.logbase(omega, 2)+28)))/100);
			torque = cross.readtorque[1];
			if (this.getGearType() == GearType.WORM)
				torque = torque * WORMRATIO;
		}
		else {
			omega = torque = 0;
			return; //not its output
		}
	}

	@Override
	protected void transferPower(World world, int x, int y, int z, int meta) {
		this.calculateRatio();
		if (worldObj.isRemote && !RotaryAux.getPowerOnClient)
			return;
		performRatio = true;
		omegain = torquein = 0;
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		int dx = x+read.offsetX;
		int dy = y+read.offsetY;
		int dz = z+read.offsetZ;
		MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
		TileEntity te = isCentered ? this.getAdjacentTileEntity(read) : world.getTileEntity(dx, dy, dz);
		if (this.isProvider(te)) {
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.isCross()) {
					this.readFromCross(devicein);
					return;
				}
				else if (devicein.isWritingToCoordinate(x, y, z)) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te instanceof SimpleProvider) {
				this.copyStandardPower(te);
			}
			if (te instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
				if (sp.isEmitting() && sp.canWriteTo(read.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}

			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.isSplitting()) {
					this.readFromSplitter(world, x, y, z, devicein);
					performRatio = false;
					torquein = torque;
					omegain = omega;
					//ReikaJavaLibrary.pConsole(torque+" @ "+omega, Side.SERVER);
					return;
				}
				else if (devicein.isWritingToCoordinate(x, y, z)) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		else if (te instanceof WorldRift) {
			WorldRift sr = (WorldRift)te;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null)
				this.transferPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta);
		}
		else {
			omega = 0;
			torque = 0;
			power = 0;
			return;
		}

		if (performRatio) {
			switch(this.getGearType()) {
				case WORM:
					omega = (int)((omegain / WORMRATIO)*this.getPowerLossFraction(omegain));
					if (torquein <= RotaryConfig.torquelimit/WORMRATIO)
						torque = torquein * WORMRATIO;
					else {
						torque = RotaryConfig.torquelimit;
						world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
					}
					break;
				case CVT:
					int ratio = this.getCVTRatio();
					if (this.hasLubricant()) {
						boolean speed = true;
						if (ratio > 0) {
							if (omegain <= RotaryConfig.omegalimit/ratio)
								omega = omegain * ratio;
							else {
								omega = RotaryConfig.omegalimit;
								world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
								world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
							}
							torque = torquein / ratio;
						}
						else {
							if (torquein <= RotaryConfig.torquelimit/-ratio)
								torque = torquein * -ratio;
							else {
								torque = RotaryConfig.torquelimit;
								world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
								world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
							}
							omega = omegain / -ratio;
						}
					}
					else {
						omega = torque = 0;
					}
					break;
				case COIL:

					break;
				case HIGH:
					if (this.hasLubricant()) {
						if (torquemode) {
							if (torquein <= RotaryConfig.torquelimit/256)
								torque = torquein*256;
							else {
								torque = RotaryConfig.torquelimit;
								world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
								world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
							}
							omega = omegain/256;
						}
						else {
							torque = torquein/256;
							if (omegain <= RotaryConfig.omegalimit/256)
								omega = omegain*256;
							else {
								omega = RotaryConfig.omegalimit;
								world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
								world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
							}
						}
						if (omega > 0 && (world.getTotalWorldTime()&4) == 4)
							lubricant.removeLiquid((int)(DifficultyEffects.LUBEUSAGE.getChance()*ReikaMathLibrary.logbase(Math.max(omega, torque), 2)));
					}
					else {
						omega = torque = 0;
					}
					break;
			}
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("ratio", ratio);
		NBT.setLong("e", energy);
		NBT.setInteger("relo", releaseOmega);
		NBT.setInteger("relt", releaseTorque);
		NBT.setBoolean("redstone", isRedstoneControlled);
		NBT.setInteger("cvton", this.getCVTState(true).ordinal());
		NBT.setInteger("cvtoff", this.getCVTState(false).ordinal());
		NBT.setBoolean("bedrock", isBedrockCoil);
		NBT.setBoolean("creative", isCreative);
		NBT.setBoolean("trq", torquemode);

		NBT.setBoolean("t_enable", enabled);

		lubricant.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		ratio = NBT.getInteger("ratio");
		energy = NBT.getLong("e");
		releaseOmega = NBT.getInteger("relo");
		releaseTorque = NBT.getInteger("relt");
		isRedstoneControlled = NBT.getBoolean("redstone");
		cvtState[0] = CVTState.list[NBT.getInteger("cvtoff")];
		cvtState[1] = CVTState.list[NBT.getInteger("cvton")];
		isBedrockCoil = NBT.getBoolean("bedrock");
		isCreative = NBT.getBoolean("creative");
		torquemode = NBT.getBoolean("trq");

		if (NBT.hasKey("t_enable"))
			enabled = NBT.getBoolean("t_enable");

		lubricant.readFromNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < belts.length; i++) {
			if (belts[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				belts[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items", NBTTypes.COMPOUND.ID);
		belts = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < belts.length) {
				belts[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return belts.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return belts[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return ReikaInventoryHelper.decrStackSize(this, var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return ReikaInventoryHelper.getStackInSlotOnClosing(this, var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		belts[var1] = var2;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return this.getGearType() == GearType.CVT && ReikaItemHelper.matchStacks(itemstack, ItemStacks.belt);
	}

	public final String getInventoryName() {
		return this.getMultiValuedName();
	}

	public void openInventory() {}

	public void closeInventory() {}

	@Override
	public final boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public final void markDirty() {
		blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);

		if (this.getBlockType() != Blocks.air) {
			worldObj.func_147453_f(xCoord, yCoord, zCoord, this.getBlockType());
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
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
		return MachineRegistry.ADVANCEDGEARS;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (this.getGearType().storesEnergy()) {
			int level = (int)(15L*(energy+power)/20L/this.getMaxStorageCapacity()); //+power to "anticipate" next tick
			return level;
		}
		return 0;
	}

	@Override
	public void onEMP() {
		//super.onEMP();
	}

	public int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided(this);
	}

	public boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isItemValidForSlot(i, is);
	}

	public final String getInvName() {
		return this.getMultiValuedName();
	}

	public long getEnergy() {
		return energy;
	}

	public void setEnergyFromNBT(NBTTagCompound NBT) {
		energy = NBT.getLong("energy");
	}

	@Override
	public long getMaxPower() {
		if (this.getGearType() != GearType.COIL)
			return 0;
		return power > 0 ? releaseOmega*releaseTorque : 0;
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		if (this.getGearType() == GearType.COIL)
			return new PowerSourceList().addSource(this);
		else
			return super.getPowerSources(io, caller);
	}

	public void incrementCVTState(boolean on) {
		int i = on ? 1 : 0;
		cvtState[i] = this.getCVTState(on).next();
		while (!this.getCVTState(on).isValid(this)) {
			cvtState[i] = this.getCVTState(on).next();
		}
	}

	private CVTState getCVTState(boolean on) {
		int i = on ? 1 : 0;
		return cvtState[i] != null ? cvtState[i] : CVTState.S1;
	}

	public String getCVTString(boolean on) {
		return this.getCVTState(on).toString();
	}

	private static enum CVTState {
		S1(1),
		S2(2),
		S4(4),
		S8(8),
		S16(16),
		S32(32),
		T1(-1),
		T2(-2),
		T4(-4),
		T8(-8),
		T16(-16),
		T32(-32);

		public final int gearRatio;

		public static final CVTState[] list = values();

		private CVTState(int ratio) {
			gearRatio = ratio;
		}

		public CVTState next() {
			if (this.ordinal() == list.length-1)
				return list[0];
			else
				return list[this.ordinal()+1];
		}

		public boolean isValid(TileEntityAdvancedGear te) {
			int abs = Math.abs(gearRatio);
			int max = Math.abs(te.getMaxRatio());
			return max >= abs;
		}

		@Override
		public String toString() {
			return Math.abs(gearRatio)+"x "+(gearRatio > 0 ? "Speed" : "Torque");
		}
	}

	@Override
	public final boolean hasInventory() {
		return this.getGearType().hasInventory();
	}

	@Override
	public final boolean hasTank() {
		return this.getGearType().isLubricated();
	}

	public boolean isCreative() {
		return isCreative;
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
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.getGearType().consumesLubricant() ? FluidRegistry.getFluid("rc lubricant").equals(fluid) : false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return this.getGearType().consumesLubricant() ? new FluidTankInfo[]{lubricant.getInfo()} : null;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return this.getGearType().consumesLubricant() ? m == MachineRegistry.HOSE : false;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.getGearType().consumesLubricant() ? p == MachineRegistry.HOSE : false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.canFill(from, resource.getFluid()) ? lubricant.fill(resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return this.getGearType().isLubricated() ? Flow.INPUT : Flow.NONE;
	}

	public void setLubricantFromNBT(NBTTagCompound NBT) {
		lubricant.setContents(NBT.getInteger("lube"), FluidRegistry.getFluid("rc lubricant"));
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enable) {
		enabled = enable;
		this.syncAllData(false);
	}

	@Override
	public int getItemMetadata() {
		return this.getGearType().ordinal();
	}
}

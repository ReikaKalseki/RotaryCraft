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

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.Power.ReikaEUHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.RCToModConverter;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

//@Strippable(value = {"universalelectricity.api.electricity.IVoltageOutput", "universalelectricity.api.energy.IEnergyInterface"})
@Strippable(value = {"ic2.api.energy.tile.IEnergySource"})
public class TileEntityGenerator extends PoweredLiquidReceiver implements IEnergySource, RCToModConverter, UpgradeableMachine, NBTMachine {

	//public static final int OUTPUT_VOLTAGE = 24000;
	//public static final float POWER_FACTOR = 0.875F;

	private ForgeDirection facingDir;

	public static final boolean hardModeEU = ConfigRegistry.HARDEU.getState();

	private boolean upgraded;

	public ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GENERATOR;
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
		this.getIOSides(world, x, y, z, meta);
		if (hardModeEU) {
			if (tank.isEmpty()) {
				omega = torque = 0;
				power = 0;
				return;
			}
			else {
				if (power > 0) {
					tank.removeLiquid(1);

					int t = upgraded ? 4096 : 1024;
					if (torque > t) {
						if (ReikaRandomHelper.doWithChance((torque-t)/20000D)) {
							if (rand.nextInt(upgraded ? 16 : 4) == 0) {
								this.delete();
								world.newExplosion(null, x+0.5, y+0.5, z+0.5, 3, true, true);
							}
							else {
								ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz");
							}
						}
					}
				}
			}
		}

		this.getPower(false);
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
			case 0:
				facingDir = ForgeDirection.NORTH;
				break;
			case 1:
				facingDir = ForgeDirection.WEST;
				break;
			case 2:
				facingDir = ForgeDirection.SOUTH;
				break;
			case 3:
				facingDir = ForgeDirection.EAST;
				break;
		}
		read = facingDir;
		write = read.getOpposite();
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		return direction == this.getFacing().getOpposite();
	}

	@Override
	public double getOfferedEnergy() {
		return (power-this.getPowerLoss())/ReikaEUHelper.getWattsPerEU()*ConfigRegistry.getConverterEfficiency();
	}

	private double getPowerLoss() {
		int t = upgraded ? 512 : 128;
		return torque > t ? ReikaMathLibrary.intpow2(torque-t, 2)*(upgraded ? 0.0625 : 0.125) : 0;
	}

	@Override
	public void drawEnergy(double amount) {

	}

	@Override
	public void onFirstTick(World world, int x, int y, int z) {
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

	@Override
	public int getSourceTier() {
		return ReikaEUHelper.getIC2TierFromPower(this.getOfferedEnergy());
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return f != null && (f.equals(FluidRegistry.getFluid("ic2coolant")) || f.equals(FluidRegistry.getFluid("rc liquid nitrogen")));
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from == ForgeDirection.DOWN;
	}

	@Override
	public int getCapacity() {
		return 6000;
	}

	@Override
	public void upgrade(ItemStack is) {
		upgraded = true;
	}

	public boolean isUpgraded() {
		return upgraded;
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		return !upgraded && ItemRegistry.UPGRADE.matchItem(item) && item.getItemDamage() == Upgrades.FLUX.ordinal();
	}

	public final void setDataFromItemStackTag(NBTTagCompound nbt) {
		if (nbt != null) {
			upgraded = nbt.getBoolean("upgrade");
		}
	}

	public final NBTTagCompound getTagsToWriteToStack() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("upgrade", upgraded);
		return nbt;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("upgrade", upgraded);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		upgraded = NBT.getBoolean("upgrade");
	}

	@Override
	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		return new ArrayList();
	}

	@Override
	public ArrayList<String> getDisplayTags(NBTTagCompound NBT) {
		return new ArrayList();
	}
}

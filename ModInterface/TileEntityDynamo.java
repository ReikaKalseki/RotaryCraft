/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.Power.ReikaRFHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.RCToModConverter;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

@Strippable(value = {"cofh.api.energy.IEnergyHandler"})
public class TileEntityDynamo extends TileEntityPowerReceiver implements IEnergyHandler, RCToModConverter, UpgradeableMachine, NBTMachine {

	private ForgeDirection facingDir;

	private boolean upgraded;

	public static final int MAXTORQUE = 1024;
	public static final int MAXTORQUE_UPGRADE = 2048;
	public static final int MAXOMEGA = 8192;

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
		return MachineRegistry.DYNAMO;
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
		this.getPower(false);

		if ((world.getWorldTime()&31) == 0)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		int writex = x+write.offsetX;
		int writey = y+write.offsetY;
		int writez = z+write.offsetZ;
		if (power > 0) {
			Block b = world.getBlock(writex, writey, writez);
			if (b != Blocks.air) {
				int metadata = world.getBlockMetadata(writex, writey, writez);
				if (b.hasTileEntity(metadata)) {
					TileEntity tile = world.getTileEntity(writex, writey, writez);
					if (tile instanceof IEnergyHandler) {
						IEnergyHandler rc = (IEnergyHandler)tile;
						if (rc.canConnectEnergy(facingDir.getOpposite())) {
							int rf = this.getGenRF();
							float used = rc.receiveEnergy(facingDir.getOpposite(), rf, false);
						}
					}
					else if (tile instanceof IEnergyReceiver) {
						IEnergyReceiver rc = (IEnergyReceiver)tile;
						if (rc.canConnectEnergy(facingDir.getOpposite())) {
							int rf = this.getGenRF();
							float used = rc.receiveEnergy(facingDir.getOpposite(), rf, false);
						}
					}
				}
			}
		}
	}

	public int getGenRF() {
		int tq = Math.min(torque, upgraded ? MAXTORQUE_UPGRADE : MAXTORQUE);
		int om = Math.min(omega, MAXOMEGA);
		long pwr = (long)tq*(long)om;
		return (int)(pwr/ReikaRFHelper.getWattsPerRF()*ConfigRegistry.getConverterEfficiency());
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
			case 2:
				facingDir = ForgeDirection.SOUTH;
				break;
			case 3:
				facingDir = ForgeDirection.EAST;
				break;
			case 4:
				facingDir = ForgeDirection.NORTH;
				break;
			case 5:
				facingDir = ForgeDirection.WEST;
				break;
			case 1:
				facingDir = ForgeDirection.DOWN;
				break;
			case 0:
				facingDir = ForgeDirection.UP;
				break;
		}
		write = facingDir;
		read = write.getOpposite();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (this.canConnectEnergy(from)) {
			int rf = (int)(power/ReikaRFHelper.getWattsPerRF());
			//return rf;
		}
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from == facingDir;
	}

	private ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
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
	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		return new ArrayList();
	}

	@Override
	public ArrayList<String> getDisplayTags(NBTTagCompound NBT) {
		return new ArrayList();
	}

}

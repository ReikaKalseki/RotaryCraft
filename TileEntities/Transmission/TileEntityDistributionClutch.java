package Reika.RotaryCraft.TileEntities.Transmission;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.ChromatiCraft.API.Interfaces.WorldRift;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Interfaces.TileEntity.GuiController;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.RotaryCraft.API.Interfaces.ComplexIO;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

import mrtjp.projectred.api.IBundledTile;
import mrtjp.projectred.api.ProjectRedAPI;

@Strippable(value={"mrtjp.projectred.api.IBundledTile"})
public class TileEntityDistributionClutch extends TileEntityTransmissionMachine implements ComplexIO, GuiController, IBundledTile {

	private int requestedTorques[] = new int[4];
	private int outputTorques[] = new int[4];
	private boolean enabledSides[] = new boolean[4];

	private ControlMode control = ControlMode.GUI;

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		if (read == null)
			return new PowerSourceList();
		return PowerSourceList.getAllFrom(worldObj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller);
	}

	@Override
	public void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		if (dir == read) {
			for (int i = 2; i < 6; i++) {
				ForgeDirection dir2 = dirs[i];
				if (this.isOutputtingToSide(dir2)) {
					c.add(this.getAdjacentTileEntity(dir2));
				}
			}
		}
	}

	public boolean isOutputtingToSide(ForgeDirection dir) {
		return enabledSides[dir.ordinal()-2] && (requestedTorques[dir.ordinal()-2] > 0 || outputTorques[dir.ordinal()-2] > 0);
	}

	@Override
	public boolean canProvidePower() {
		return true;
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
		return MachineRegistry.DISTRIBCLUTCH;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.updateControl(world, x, y, z);
		this.intakePower(world, x, y, z, meta);
		omega = omegain;
		this.distributePower(world, x, y, z);
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta){
			case 0:
				read = ForgeDirection.EAST;
				break;
			case 1:
				read = ForgeDirection.WEST;
				break;
			case 2:
				read = ForgeDirection.SOUTH;
				break;
			case 3:
				read = ForgeDirection.NORTH;
				break;
		}
		write = read.getOpposite();
	}

	private void updateControl(World world, int x, int y, int z) {
		enabledSides[write.ordinal()-2] = true;
		enabledSides[read.ordinal()-2] = false;
		if (control == ControlMode.REDSTONE) {
			int redstone = world.getBlockPowerInput(x, y, z);
			enabledSides[ForgeDirection.NORTH.ordinal()-2] = (redstone & 1) != 0;
			enabledSides[ForgeDirection.EAST.ordinal()-2] = (redstone & 2) != 0;
			enabledSides[ForgeDirection.SOUTH.ordinal()-2] = (redstone & 4) != 0;
			enabledSides[ForgeDirection.WEST.ordinal()-2] = (redstone & 8) != 0;
		}
		else if (control == ControlMode.BUNDLEDREDSTONE) {
			enabledSides[ForgeDirection.NORTH.ordinal()-2] = this.getBundledInput(world, x, y, z, ReikaDyeHelper.RED) != 0;
			enabledSides[ForgeDirection.EAST.ordinal()-2] = this.getBundledInput(world, x, y, z, ReikaDyeHelper.YELLOW) != 0;
			enabledSides[ForgeDirection.SOUTH.ordinal()-2] = this.getBundledInput(world, x, y, z, ReikaDyeHelper.BLUE) != 0;
			enabledSides[ForgeDirection.WEST.ordinal()-2] = this.getBundledInput(world, x, y, z, ReikaDyeHelper.GREEN) != 0;
		}
	}

	private void distributePower(World world, int x, int y, int z) {
		int leftover = torquein;
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			outputTorques[dir.ordinal()-2] = 0;
			if (this.isOutputtingToSide(dir)) {
				int amt = this.calculateOutputTorque(dir, leftover);
				leftover -= amt;
				outputTorques[dir.ordinal()-2] = amt;
			}
		}
		outputTorques[write.ordinal()-2] = leftover;
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (this.getAdjacentTileEntity(dir) != null) {
				int speed = this.getSpeedToSide(dir);
				int torque = this.getTorqueToSide(dir);
				this.writeToPowerReceiver(dir, speed, torque);
			}
		}
	}

	private void intakePower(World world, int x, int y, int z, int meta) {
		if (!RotaryAux.getPowerOnClient && world.isRemote)
			return;
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
			if (te instanceof ComplexIO) {
				ComplexIO pwr = (ComplexIO)te;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
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
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.isSplitting()) {
					this.readFromSplitter(world, x, y, z, devicein);
					torquein = torque;
					omegain = omega;
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
				this.intakePower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta);
		}
		else {
			omega = torque = 0;
		}
		/*
		 		if (this.isProvider(te)) {
			this.processTileSimply(te, m, xCoord, yCoord, zCoord);
		}
		else if (te instanceof SpaceRift) {
			SpaceRift sr = (SpaceRift)te;
			WorldLocation loc = sr.getLinkTarget();
			TileEntity other = sr.getTileEntityFrom(read);
			this.processTileSimply(other, MachineRegistry.getMachine(loc.move(read, 1)), loc.xCoord, loc.yCoord, loc.zCoord);
		}
		else {
			omega = torque = 0;
		}
		 */
	}

	private int calculateOutputTorque(ForgeDirection dir, int available) {
		return Math.min(requestedTorques[dir.ordinal()-2], available);
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setIntArray("output", outputTorques);
		NBT.setIntArray("req", requestedTorques);
		NBT.setInteger("sides", ReikaArrayHelper.booleanToBitflags(enabledSides));

		NBT.setInteger("control", control.ordinal());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		requestedTorques = NBT.getIntArray("req");
		outputTorques = NBT.getIntArray("output");
		enabledSides = ReikaArrayHelper.booleanFromBitflags(NBT.getInteger("sides"), 4);

		control = ControlMode.list[NBT.getInteger("control")];
	}

	@Override
	public int getSpeedToSide(ForgeDirection dir) {
		return this.isOutputtingToSide(dir) ? omega : 0;
	}

	@Override
	public int getTorqueToSide(ForgeDirection dir) {
		return outputTorques[dir.ordinal()-2];
	}

	public void setSideEnabled(ForgeDirection dir, boolean flag) {
		enabledSides[dir.ordinal()-2] = flag;
		this.syncAllData(false);
	}

	public boolean isSideEnabled(ForgeDirection dir) {
		return enabledSides[dir.ordinal()-2];
	}

	public int getTorqueRequest(ForgeDirection dir) {
		return requestedTorques[dir.ordinal()-2];
	}

	public void setTorqueRequest(ForgeDirection dir, int amt) {
		requestedTorques[dir.ordinal()-2] = amt;
		this.syncAllData(false);
	}

	public void setTorqueRequests(int[] vals) {
		for (int i = 0; i < 4; i++) {
			requestedTorques[i] = vals[i];
		}
		this.syncAllData(false);
	}

	@Override
	public byte[] getBundledSignal(int dir) {
		return new byte[16];
	}

	@Override
	public boolean canConnectBundled(int side) {
		return control == ControlMode.BUNDLEDREDSTONE;
	}

	@ModDependent(ModList.PROJRED)
	private int getBundledInput(World world, int x, int y, int z, ReikaDyeHelper color) {
		int ret = 0;
		for (int i = 0; i < 6; i++) {
			byte[] data = ProjectRedAPI.transmissionAPI.getBundledInput(world, x, y, z, i);
			int at = data != null ? data[color.ordinal()] & 255 : 0;
			if (at > ret)
				ret = at;
		}
		return ret;
	}

	public void stepMode() {
		control = control.next();
		enabledSides = new boolean[4];
		this.triggerBlockUpdate();
	}

	public static enum ControlMode {
		GUI,
		REDSTONE,
		BUNDLEDREDSTONE,
		COMPUTER;

		private static final ControlMode[] list = values();

		public ControlMode next() {
			return this.ordinal() == list.length-1 ? list[0] : list[this.ordinal()+1];
		}
	}

}

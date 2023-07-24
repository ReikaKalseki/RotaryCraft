/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.ChromatiCraft.API.Interfaces.WorldRift;
import Reika.DragonAPI.APIPacketHandler.PacketIDs;
import Reika.DragonAPI.DragonAPIInit;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.RotaryCraft.API.Interfaces.RCPipe;
import Reika.RotaryCraft.Auxiliary.Interfaces.CachedConnection;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeRenderConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PumpablePipe;
import Reika.RotaryCraft.Auxiliary.Interfaces.RenderableDuct;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;

public abstract class TileEntityPiping extends RotaryCraftTileEntity implements RenderableDuct, CachedConnection, BreakAction, PumpablePipe, RCPipe {

	private static final HashSet<Class> nonInteractableClasses = new HashSet();
	private static final HashSet<Class> interactableClasses = new HashSet();

	private static final int CAPACITY_LIMIT = 1000000000; //1 billion mB to prevent overflow

	public static final int UPPRESSURE = 40;
	public static final int HORIZPRESSURE = 20;
	public static final int DOWNPRESSURE = 0;

	private static final int MAXPRESSURE = 2400000;

	private boolean[] connections = new boolean[6];
	private boolean[] interaction = new boolean[6];

	private int connectionDelay = 0;

	public final int getPressure() {
		Fluid f = this.getFluidType();
		int amt = this.getFluidLevel();
		if (f == null || amt <= 0)
			return 101300;
		//p = rho*R*T approximation
		long ret;
		if (f.isGaseous())
			ret = 101300+(128*(int)(amt/1000D*f.getTemperature()*Math.abs(f.getDensity())/1000D));
		else
			ret = 101300+amt*24;
		return (int)Math.min(Integer.MAX_VALUE, ret);
	}

	public int getMaxPressure() {
		return MAXPRESSURE;
	}

	private void overpressure(World world, int x, int y, int z) {
		Fluid f = this.getFluidType();
		if (f.canBePlacedInWorld()) {
			world.setBlock(x, y, z, f.getBlock());
		}
		else {
			world.setBlockToAir(x, y, z);
		}
		world.markBlockForUpdate(x, y, z);
		world.notifyBlockOfNeighborChange(x, y, z, f.getBlock());
	}

	public abstract int getFluidLevel();

	public abstract boolean canConnectToPipe(MachineRegistry p, ForgeDirection side);

	protected abstract void setFluid(Fluid f);

	protected abstract void setLevel(int amt);

	protected abstract boolean interactsWithMachines();

	protected abstract void onIntake(TileEntity te);

	public abstract boolean canReceiveFromPipeOn(ForgeDirection side);

	public abstract boolean canEmitToPipeOn(ForgeDirection side);

	public abstract boolean canIntakeFromIFluidHandler(ForgeDirection side);

	public abstract boolean canOutputToIFluidHandler(ForgeDirection side);

	public final boolean canIntakeFluid(Fluid f) {
		if (f == null)
			return false;
		return this.isValidFluid(f) && (this.getFluidType() == null || this.getFluidLevel() == 0 || this.getFluidType().equals(f));
	}

	public abstract boolean isValidFluid(Fluid f);

	private StepTimer flowTimer = new StepTimer(this.getTickDelay());

	public static int getTickDelay() {
		int cfg = Math.max(ConfigRegistry.FLOWSPEED.getValue(), 1);
		if (cfg > 5)
			cfg = 5;
		return 6-cfg;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (this.getTicksExisted() < 5) {
			this.syncAllData(true);
			world.markBlockForUpdate(x, y, z);
		}
		Fluid f = this.getFluidType();
		//if (!world.isRemote) {
		flowTimer.update();
		if (flowTimer.checkCap()) {
			this.intakeFluid(world, x, y, z);
			this.dumpContents(world, x, y, z);
		}
		//}
		if (this.getFluidLevel() <= 0) {
			this.setLevel(0);
			this.setFluid(null);
		}
		Fluid f2 = this.getFluidType();
		if (f != f2) {
			this.syncAllData(true);
			world.markBlockForUpdate(x, y, z);
		}

		if (this.getPressure() > this.getMaxPressure()) {
			if (world.isRemote) {
				ReikaPacketHelper.sendUpdatePacket(DragonAPIInit.packetChannel, PacketIDs.EXPLODE.ordinal(), this, PacketTarget.server);
			}
			else {
				this.overpressure(world, x, y, z);
			}
		}

		if (!world.isRemote) {
			if (connectionDelay > 0) {
				connectionDelay--;
				if (connectionDelay == 0) {
					this.recomputeConnections(world, x, y, z);
				}
			}
		}
	}

	@Override
	protected final void onFirstTick(World world, int x, int y, int z) {
		this.recomputeConnections(world, x, y, z);
	}

	@Override
	public int getPacketDelay() {
		return 4*super.getPacketDelay();
	}

	@Override
	public final int getRedstoneOverride() {
		return 0;
	}

	protected void queueConnectionEvaluation(int delay) {
		connectionDelay = 2;
	}

	protected final boolean canInteractWith(World world, int x, int y, int z, ForgeDirection side) {
		if (!connections[side.ordinal()])
			return false;
		int dx = x+side.offsetX;
		int dy = y+side.offsetY;
		int dz = z+side.offsetZ;
		Block id = world.getBlock(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		if (id == Blocks.air)
			return false;
		MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
		if (m != null && m.isPipe())
			return this.hasReciprocalConnectivity((TileEntityPiping)this.getAdjacentTileEntity(side), side);
		TileEntity te = this.getAdjacentTileEntity(side);
		if (te instanceof WorldRift) {
			return true;
		}
		return this.interactsWithMachines() && this.isInteractableTile(te, side);
	}

	private boolean isInteractableTile(TileEntity te, ForgeDirection side) {
		if (te == null)
			return false;
		if (te instanceof PipeRenderConnector) {
			return ((PipeRenderConnector)te).canConnectToPipeOnSide(side);
		}
		if (te instanceof IFluidHandler) {
			Class c = te.getClass();
			if (interactableClasses.contains(c))
				return true;
			if (nonInteractableClasses.contains(c))
				return false;
			String name = c.getSimpleName().toLowerCase(Locale.ENGLISH);
			if (name.contains("conduit") || name.contains("fluidduct") || name.contains("pipe") || name.contains("multipart")) {
				nonInteractableClasses.add(c);
				return false;
			}
			interactableClasses.add(c);
			return true;
		}
		return false;
	}

	public final int getPipeIntake(int otherlevel) {
		return Math.min(CAPACITY_LIMIT-this.getFluidLevel(), TransferAmount.QUARTER.getTransferred(otherlevel));
	}

	public final int getPipeOutput(int max) {
		return Math.min(TransferAmount.QUARTER.getTransferred(max), this.getFluidLevel()-5);
	}

	private final void dumpContents(World world, int x, int y, int z) {
		Fluid f = this.getFluidType();
		if (this.getFluidLevel() <= 1 || f == null)
			return;
		for (int i = 0; i < 6; i++) {
			int level = this.getFluidLevel();
			if (level <= 0) {
				this.setFluid(null);
				return;
			}
			ForgeDirection dir = dirs[i];
			if (interaction[i]) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				TileEntity te = world.getTileEntity(dx, dy, dz);

				if (te instanceof WorldRift) {
					if (world.isRemote)
						continue;
					WorldLocation loc = ((WorldRift)te).getLinkTarget();
					if (loc != null) {
						te = ((WorldRift)te).getTileEntityFrom(dir);
						if (te == null)
							continue;
						dx = te.xCoord;
						dy = te.yCoord;
						dz = te.zCoord;
						world = te.worldObj;
					}
				}

				if (te instanceof TileEntityPiping) {
					TileEntityPiping tp = (TileEntityPiping)te;
					if (this.hasReciprocalConnectivity(tp, dir) && this.canEmitToPipeOn(dir) && tp.canReceiveFromPipeOn(dir.getOpposite())) {
						//ReikaJavaLibrary.pConsole(dir, this.getSide() == Side.SERVER && this instanceof TileEntitySeparatorPipe);
						if (tp.canIntakeFluid(f)) {
							int otherlevel = tp.getFluidLevel();
							int dL = level-otherlevel;
							int toadd = this.getPipeOutput(dL);
							if (toadd > 0) {
								tp.addFluid(toadd);
								this.removeLiquid(toadd);
							}
						}
					}
				}
				else if (te instanceof PipeConnector) {
					PipeConnector pc = (PipeConnector)te;
					Flow flow = pc.getFlowForSide(dir.getOpposite());
					if (flow.canIntake) {
						int toadd = this.getPipeOutput(this.getFluidLevel());
						//int toadd = pc.getFluidRemoval().getTransferred(this.getLiquidLevel());
						if (toadd > 0) {
							FluidStack fs = new FluidStack(f, toadd);
							int added = pc.fill(dir.getOpposite(), fs, true);
							//ReikaJavaLibrary.pConsole(added, Side.SERVER);
							if (added > 0) {
								//ReikaJavaLibrary.pConsole(toadd+":"+added+":"+this.getLiquidLevel(), Side.SERVER);
								this.removeLiquid(added);
							}
						}
					}
				}
				else if (te instanceof IFluidHandler && this.canOutputToIFluidHandler(dir)) {
					IFluidHandler fl = (IFluidHandler)te;
					if (fl.canFill(dir.getOpposite(), f)) {
						int toadd = this.getPipeOutput(this.getFluidLevel());
						if (toadd > 0) {
							int added = fl.fill(dir.getOpposite(), new FluidStack(f, toadd), true);
							if (added > 0) {
								this.removeLiquid(added);
							}
						}
					}
				}
			}
		}
	}

	public final int removeLiquid(int max) {
		int has = this.getFluidLevel();
		int rem = Math.min(max, has);
		this.setLevel(has-rem);
		return rem;
	}

	public final void addFluid(int toadd) {
		this.setLevel(this.getFluidLevel()+toadd);
	}

	public final boolean addFluid(Fluid f, int toadd) {
		Fluid has = this.getFluidType();
		if (has != null && has != f)
			return false;
		this.setFluid(f);
		this.addFluid(toadd);
		return true;
	}

	private final void intakeFluid(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (interaction[i]) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				TileEntity te = world.getTileEntity(dx, dy, dz);

				if (te instanceof WorldRift) {
					if (world.isRemote)
						continue;
					WorldLocation loc = ((WorldRift)te).getLinkTarget();
					if (loc != null) {
						te = ((WorldRift)te).getTileEntityFrom(dir);
						if (te == null)
							continue;
						dx = te.xCoord;
						dy = te.yCoord;
						dz = te.zCoord;
						world = te.worldObj;
					}
				}

				if (te instanceof TileEntityPiping) {
					TileEntityPiping tp = (TileEntityPiping)te;
					if (this.hasReciprocalConnectivity(tp, dir) && this.canReceiveFromPipeOn(dir) && tp.canEmitToPipeOn(dir.getOpposite())) {
						Fluid f = tp.getFluidType();
						int amt = tp.getFluidLevel();
						int dL = amt-this.getFluidLevel();
						int todrain = this.getPipeIntake(dL);
						if (todrain > 0 && this.canIntakeFluid(f)) {
							this.setFluid(f);
							this.addFluid(todrain);
							tp.removeLiquid(todrain);
							//ReikaJavaLibrary.pConsole("Transferring "+todrain+", have "+this.getFluidLevel(), Side.SERVER, this.getAdjacentTileEntity(ForgeDirection.UP) == null);
							this.onIntake(te);
						}
					}
				}
				else if (te instanceof PipeConnector) {
					PipeConnector pc = (PipeConnector)te;
					Flow flow = pc.getFlowForSide(dir.getOpposite());
					if (flow.canOutput) {
						if (pc instanceof TileEntityPump) {
							int max = ((TileEntityPump)pc).getMaxBackPressure();
							if (max <= this.getFluidLevel())
								continue;
						}
						FluidStack fs = pc.drain(dir.getOpposite(), Integer.MAX_VALUE, false);
						if (fs != null) {
							int level = this.getFluidLevel();
							int todrain = this.getPipeIntake(fs.amount-level);
							if (todrain > 0) {
								if (this.canIntakeFluid(fs.getFluid())) {
									this.addFluid(todrain);
									this.setFluid(fs.getFluid());
									pc.drain(dir.getOpposite(), todrain, true);
									this.onIntake(te);
									//ReikaJavaLibrary.pConsole("Transferring "+todrain+", have "+this.getFluidLevel(), Side.SERVER);
								}
							}
						}
					}
				}
				else if (te instanceof IFluidHandler && this.canIntakeFromIFluidHandler(dir)) {
					IFluidHandler fl = (IFluidHandler)te;
					FluidStack fs = fl.drain(dir.UNKNOWN, Integer.MAX_VALUE, false);
					//ReikaJavaLibrary.pConsole(fs);
					if (fs != null) {
						int level = this.getFluidLevel();
						int todrain = this.getPipeIntake(fs.amount-level);
						if (todrain > 0) {
							if (this.canIntakeFluid(fs.getFluid())) {
								this.setFluid(fs.getFluid());
								this.onIntake(te);
								int drained = fl.drain(dir.UNKNOWN, todrain, true).amount;
								this.addFluid(drained);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public final void animateWithTick(World world, int x, int y, int z) {}

	@Override
	public final boolean hasModelTransparency() {
		return false;
	}

	/** Direction is relative to the piping block (so DOWN means the block is below the pipe) */
	public boolean isConnectionValidForSide(ForgeDirection dir) {
		if (dir.offsetX == 0 && MinecraftForgeClient.getRenderPass() != 1)
			dir = dir.getOpposite();
		return connections[dir.ordinal()];
	}

	public boolean isConnectedDirectly(ForgeDirection dir) {
		return connections[dir.ordinal()];
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1);
	}

	@Override
	public final void onEMP() {}

	public abstract IIcon getBlockIcon();

	public abstract boolean hasLiquid();

	public abstract Fluid getFluidType();

	public void recomputeConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			connections[i] = this.shouldTryToConnect(dirs[i]);
			interaction[i] = this.canInteractWith(world, x, y, z, dirs[i]);
			world.func_147479_m(x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
		}
		this.syncAllData(true);
		world.markBlockForUpdate(x, y, z);
		world.func_147479_m(x, y, z);
	}

	public void deleteFromAdjacentConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = x+dir.offsetY;
			int dz = x+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == this.getTile()) {
				TileEntityPiping te = (TileEntityPiping)world.getTileEntity(dx, dy, dz);
				te.connections[dir.getOpposite().ordinal()] = false;
				world.func_147479_m(dx, dy, dz);
			}
		}
	}

	public void addToAdjacentConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = x+dir.offsetY;
			int dz = x+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == this.getTile()) {
				TileEntityPiping te = (TileEntityPiping)world.getTileEntity(dx, dy, dz);
				te.connections[dir.getOpposite().ordinal()] = true;
				world.func_147479_m(dx, dy, dz);
			}
		}
	}

	public boolean shouldTryToConnect(ForgeDirection dir) {
		int x = xCoord+dir.offsetX;
		int y = yCoord+dir.offsetY;
		int z = zCoord+dir.offsetZ;
		MachineRegistry m = this.getTile();
		MachineRegistry m2 = MachineRegistry.getMachine(worldObj, x, y, z);
		if (m != null && !m.isPipe() && m == m2)
			return true;
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if (tile instanceof WorldRift) {
			return true;
		}
		if (tile instanceof TileEntityPiping)
			return this.hasReciprocalConnectivity((TileEntityPiping)tile, dir);
		else if (tile instanceof PipeConnector) {
			PipeConnector pc = (PipeConnector)tile;
			return pc.canConnectToPipe(this.getTile()) && pc.canConnectToPipeOnSide(this.getTile(), dir.getOpposite());
		}
		else if (this.interactsWithMachines() && this.isInteractableTile(tile, dir))
			return true;
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setByte("conn", ReikaArrayHelper.booleanToByteBitflags(connections));

		ReikaNBTHelper.writeFluidToNBT(NBT, this.getFluidType());
		NBT.setInteger("level", this.getFluidLevel());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		boolean update = false;

		boolean[] old = new boolean[connections.length];
		System.arraycopy(connections, 0, old, 0, old.length);

		connections = ReikaArrayHelper.booleanFromByteBitflags(NBT.getByte("conn"), 6);

		update = !Arrays.equals(old, connections);

		Fluid f = ReikaNBTHelper.getFluidFromNBT(NBT);
		update = update || f != this.getFluidType();
		this.setFluid(f);
		this.setLevel(NBT.getInteger("level"));

		if (worldObj != null && update)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBT.setInteger("conndelay", connectionDelay);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		connectionDelay = NBT.getInteger("conndelay");
	}

	public boolean isConnectedToNonSelf(ForgeDirection dir) {
		if (!this.isConnectionValidForSide(dir))
			return false;
		if (dir.offsetX == 0 && MinecraftForgeClient.getRenderPass() != 1)
			dir = dir.getOpposite();
		int dx = xCoord+dir.offsetX;
		int dy = yCoord+dir.offsetY;
		int dz = zCoord+dir.offsetZ;
		World world = worldObj;
		Block b = world.getBlock(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		return b != this.getTile().getBlock() || meta != this.getTile().getBlockMetadata();
	}

	@Override
	public IIcon getGlassIcon() {
		return Blocks.glass.getIcon(0, 0);
	}

	@Override
	public final boolean isFluidPipe() {
		return true;
	}

	@Override
	public IIcon getOverlayIcon() {
		return null;
	}

	public void breakBlock() {
		this.deleteFromAdjacentConnections(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public final boolean canTransferTo(PumpablePipe p, ForgeDirection dir) {
		return p instanceof TileEntityPiping && this.hasReciprocalConnectivity((TileEntityPiping)p, dir);
	}

	public final boolean hasReciprocalConnectivity(TileEntityPiping te, ForgeDirection dir) {
		return te != null && this.canConnectToPipe(te.getTile(), dir) && te.canConnectToPipe(this.getTile(), dir.getOpposite());
	}

	@Override
	public final void transferFrom(PumpablePipe from, int amt) {
		TileEntityPiping te = (TileEntityPiping)from;
		this.setLevel(this.getFluidLevel()+amt);
		this.setFluid(te.getFluidType()); {
			te.setLevel(te.getFluidLevel()-amt);
			if (te.getFluidLevel() == 0)
				te.setFluid(null);
		}
	}

	public void onPlacedAgainst(ForgeDirection dir) {

	}

	public final boolean allowExternalHeating() {
		return false;
	}

	public final boolean allowHeatExtraction() {
		return false;
	}

	public final boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public final double heatEnergyPerDegree() {
		double base = super.heatEnergyPerDegree();
		if (this.getFluidType() != null) {
			base += this.getFluidType().getDensity();
		}
		return base;
	}

	public static enum TransferAmount {
		UNITY(),
		BUCKET(),
		QUARTER(),
		FORCEDQUARTER(),
		ALL();

		public int getTransferred(int max) {
			if (max <= 0)
				return 0;
			switch(this) {
				case ALL:
					return max;
				case FORCEDQUARTER:
					return max/4+1;
				case QUARTER:
					return max/4;
				case UNITY:
					return 1;
				case BUCKET:
					return max > 1000 ? 1000 : max;
				default:
					return 1;
			}
		}
	}

	public static enum Flow {
		INPUT(true, false),
		OUTPUT(false, true),
		DUAL(true, true),
		NONE(false, false);

		public final boolean canIntake;
		public final boolean canOutput;

		public static final Flow[] list = values();

		private Flow(boolean in, boolean out) {
			canIntake = in;
			canOutput = out;
		}
	}
}

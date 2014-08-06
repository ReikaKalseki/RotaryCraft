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

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.CachedConnection;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeRenderConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.RenderableDuct;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class TileEntityPiping extends RotaryCraftTileEntity implements RenderableDuct, CachedConnection {

	private boolean[] connections = new boolean[6];

	public abstract int getFluidLevel();

	public abstract boolean canConnectToPipe(MachineRegistry p);

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
		if (!world.isRemote) {
			flowTimer.update();
			if (flowTimer.checkCap()) {
				this.intakeFluid(world, x, y, z);
				this.dumpContents(world, x, y, z);
			}
		}
		if (this.getFluidLevel() <= 0) {
			this.setLevel(0);
			this.setFluid(null);
		}
	}

	@Override
	public int getPacketDelay() {
		return 4*super.getPacketDelay();
	}

	@Override
	public final int getRedstoneOverride() {
		return 0;
	}

	protected final boolean canInteractWith(World world, int x, int y, int z, ForgeDirection side) {
		if (!connections[side.ordinal()])
			return false;
		int dx = x+side.offsetX;
		int dy = y+side.offsetY;
		int dz = z+side.offsetZ;
		int id = world.getBlockId(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		if (id == 0)
			return false;
		MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
		if (m != null && m.isPipe())
			return this.canConnectToPipe(m);
		return this.interactsWithMachines() && this.isInteractableTile(this.getAdjacentTileEntity(side), side);
	}

	private boolean isInteractableTile(TileEntity te, ForgeDirection side) {
		if (te == null)
			return false;
		if (te instanceof PipeRenderConnector) {
			return ((PipeRenderConnector)te).canConnectToPipeOnSide(side);
		}
		if (te instanceof IFluidHandler) {
			String name = te.getClass().getSimpleName().toLowerCase();
			return !name.contains("conduit") && !name.contains("pipe") && !name.contains("multipart");
		}
		return false;
	}

	public final int getPipeIntake(int otherlevel) {
		return TransferAmount.QUARTER.getTransferred(otherlevel);
	}

	public final int getPipeOutput(int max) {
		return Math.min(TransferAmount.QUARTER.getTransferred(max), this.getFluidLevel()-5);
	}

	public void dumpContents(World world, int x, int y, int z) {
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
			if (this.canInteractWith(world, x, y, z, dir)) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				TileEntity te = world.getBlockTileEntity(dx, dy, dz);
				if (te instanceof TileEntityPiping) {
					TileEntityPiping tp = (TileEntityPiping)te;
					if (this.canConnectToPipe(tp.getMachine()) && this.canEmitToPipeOn(dir) && tp.canReceiveFromPipeOn(dir.getOpposite())) {
						//ReikaJavaLibrary.pConsole(dir, this.getSide() == Side.SERVER && this instanceof TileEntitySeparatorPipe);
						if (tp.canIntakeFluid(f)) {
							int otherlevel = tp.getFluidLevel();
							int dL = level-otherlevel;
							int toadd = this.getPipeOutput(dL);
							if (toadd > 0) {
								this.addFluid(toadd);
								tp.removeLiquid(toadd);
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

	public final void removeLiquid(int toremove) {
		this.setLevel(this.getFluidLevel()-toremove);
	}

	public final void addFluid(int toadd) {
		this.setLevel(this.getFluidLevel()+toadd);
	}

	public void intakeFluid(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (this.canInteractWith(world, x, y, z, dir)) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				TileEntity te = world.getBlockTileEntity(dx, dy, dz);
				if (te instanceof TileEntityPiping) {
					TileEntityPiping tp = (TileEntityPiping)te;
					if (this.canConnectToPipe(tp.getMachine()) && this.canReceiveFromPipeOn(dir) && tp.canEmitToPipeOn(dir.getOpposite())) {
						Fluid f = tp.getFluidType();
						int amt = tp.getFluidLevel();
						int dL = amt-this.getFluidLevel();
						int todrain = this.getPipeIntake(dL);
						if (todrain > 0 && this.canIntakeFluid(f)) {
							this.setFluid(f);
							this.addFluid(todrain);
							tp.removeLiquid(todrain);
							this.onIntake(te);
						}
					}
				}
				else if (te instanceof PipeConnector) {
					PipeConnector pc = (PipeConnector)te;
					Flow flow = pc.getFlowForSide(dir.getOpposite());
					if (flow.canOutput) {
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
								}
							}
						}
					}
				}
				else if (te instanceof IFluidHandler && this.canIntakeFromIFluidHandler(dir)) {
					IFluidHandler fl = (IFluidHandler)te;
					FluidStack fs = fl.drain(dir.UNKNOWN, 16000, false);
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

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1);
	}

	@Override
	public final void onEMP() {}

	public abstract Icon getBlockIcon();

	public abstract boolean hasLiquid();

	public abstract Fluid getFluidType();

	public void recomputeConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			connections[i] = this.shouldTryToConnect(dirs[i]);
			world.markBlockForRenderUpdate(x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
		}
		world.markBlockForRenderUpdate(x, y, z);
	}

	public void deleteFromAdjacentConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = x+dir.offsetY;
			int dz = x+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == this.getMachine()) {
				TileEntityPiping te = (TileEntityPiping)world.getBlockTileEntity(dx, dy, dz);
				te.connections[dir.getOpposite().ordinal()] = false;
				world.markBlockForRenderUpdate(dx, dy, dz);
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
			if (m == this.getMachine()) {
				TileEntityPiping te = (TileEntityPiping)world.getBlockTileEntity(dx, dy, dz);
				te.connections[dir.getOpposite().ordinal()] = true;
				world.markBlockForRenderUpdate(dx, dy, dz);
			}
		}
	}

	public boolean shouldTryToConnect(ForgeDirection dir) {
		int x = xCoord+dir.offsetX;
		int y = yCoord+dir.offsetY;
		int z = zCoord+dir.offsetZ;
		MachineRegistry m = this.getMachine();
		MachineRegistry m2 = MachineRegistry.getMachine(worldObj, x, y, z);
		if (m != null && !m.isPipe() && m == m2)
			return true;
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityPiping)
			return ((TileEntityPiping) tile).canConnectToPipe(m);
		else if (tile instanceof PipeConnector) {
			PipeConnector pc = (PipeConnector)tile;
			return pc.canConnectToPipe(this.getMachine()) && pc.canConnectToPipeOnSide(this.getMachine(), dir.getOpposite());
		}
		else if (this.isInteractableTile(tile, dir))
			return true;
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		for (int i = 0; i < 6; i++) {
			NBT.setBoolean("conn"+i, connections[i]);
		}

		ReikaNBTHelper.writeFluidToNBT(NBT, this.getFluidType());
		NBT.setInteger("level", this.getFluidLevel());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		for (int i = 0; i < 6; i++) {
			connections[i] = NBT.getBoolean("conn"+i);
		}

		this.setFluid(ReikaNBTHelper.getFluidFromNBT(NBT));
		this.setLevel(NBT.getInteger("level"));
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
		int id = world.getBlockId(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		return id != this.getMachine().getBlockID() || meta != this.getMachine().getMachineMetadata();
	}

	@Override
	public boolean needsToCauseBlockUpdates() {
		return true;
	}

	@Override
	public Icon getGlassIcon() {
		return Block.glass.getIcon(0, 0);
	}

	@Override
	public final boolean isFluidPipe() {
		return true;
	}

	@Override
	public Icon getOverlayIcon() {
		return null;
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

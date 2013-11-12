/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

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
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class TileEntityPiping extends RotaryCraftTileEntity {

	private boolean[] connections = new boolean[6];

	public abstract int getLiquidLevel();

	public abstract boolean canConnectToPipe(MachineRegistry p);

	protected abstract void setFluid(Fluid f);

	protected abstract void setLevel(int amt);

	protected abstract boolean interactsWithMachines();

	protected abstract void onIntake(TileEntity te);

	public abstract boolean canReceiveFromPipeOn(ForgeDirection side);

	public abstract boolean canEmitToPipeOn(ForgeDirection side);

	public final boolean canIntakeFluid(Fluid f) {
		if (f == null)
			return false;
		return this.isValidFluid(f) && (this.getLiquidType() == null || this.getLiquidLevel() == 0 || this.getLiquidType().equals(f));
	}

	public abstract boolean isValidFluid(Fluid f);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.intakeFluid(world, x, y, z);
		this.dumpContents(world, x, y, z);
		if (this.getLiquidLevel() <= 0) {
			this.setLevel(0);
			this.setFluid(null);
		}
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
		return this.interactsWithMachines() && Block.blocksList[id].hasTileEntity(meta);
	}

	public final int getPipeIntake(int otherlevel) {
		return TransferAmount.FORCEDQUARTER.getTransferred(otherlevel);
	}

	public final int getPipeOutput(int max) {
		return TransferAmount.FORCEDQUARTER.getTransferred(max);
	}

	public void dumpContents(World world, int x, int y, int z) {
		Fluid f = this.getLiquidType();
		if (this.getLiquidLevel() <= 0 || f == null)
			return;
		for (int i = 0; i < 6; i++) {
			int level = this.getLiquidLevel();
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
					if (this.canConnectToPipe(tp.getMachine())) {
						if (tp.canIntakeFluid(f)) {
							int otherlevel = tp.getLiquidLevel();
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
						int toadd = this.getPipeOutput(this.getLiquidLevel());
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
				else if (te instanceof IFluidHandler && dir != ForgeDirection.UP) {
					IFluidHandler fl = (IFluidHandler)te;
					if (fl.canFill(dir.getOpposite(), f)) {
						int toadd = this.getPipeOutput(this.getLiquidLevel());
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
		this.setLevel(this.getLiquidLevel()-toremove);
	}

	public final void addFluid(int toadd) {
		this.setLevel(this.getLiquidLevel()+toadd);
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
					if (this.canConnectToPipe(tp.getMachine())) {
						Fluid f = tp.getLiquidType();
						int amt = tp.getLiquidLevel();
						int dL = amt-this.getLiquidLevel();
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
							int level = this.getLiquidLevel();
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
				else if (te instanceof IFluidHandler && dir == ForgeDirection.UP) {
					IFluidHandler fl = (IFluidHandler)te;
					FluidStack fs = fl.drain(dir.getOpposite(), Integer.MAX_VALUE, false);
					if (fs != null) {
						int level = this.getLiquidLevel();
						int todrain = this.getPipeIntake(fs.amount-level);
						if (todrain > 0) {
							if (this.canIntakeFluid(fs.getFluid())) {
								fl.drain(dir.getOpposite(), todrain, true);
								this.addFluid(todrain);
								this.setFluid(fs.getFluid());
								this.onIntake(te);
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

	public abstract Fluid getLiquidType();

	public void recomputeConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			connections[i] = this.isConnected(dirs[i]);
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

	private boolean isConnected(ForgeDirection dir) {
		int x = xCoord+dir.offsetX;
		int y = yCoord+dir.offsetY;
		int z = zCoord+dir.offsetZ;
		MachineRegistry m = this.getMachine();
		MachineRegistry m2 = MachineRegistry.getMachine(worldObj, x, y, z);
		if (m == m2)
			return true;
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityPiping)
			return ((TileEntityPiping) tile).canConnectToPipe(m);
		if (tile instanceof IFluidHandler)
			return true;
		if (!(tile instanceof PipeConnector))
			return false;
		PipeConnector pc = (PipeConnector)tile;
		return pc.canConnectToPipe(this.getMachine()) && pc.canConnectToPipeOnSide(this.getMachine(), dir);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		for (int i = 0; i < 6; i++) {
			NBT.setBoolean("conn"+i, connections[i]);
		}

		ReikaNBTHelper.writeFluidToNBT(NBT, this.getLiquidType());
		NBT.setInteger("level", this.getLiquidLevel());
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

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

	public enum TransferAmount {
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

	public enum Flow {
		INPUT(true, false),
		OUTPUT(false, true),
		DUAL(true, true),
		NONE(false, false);

		public final boolean canIntake;
		public final boolean canOutput;

		private Flow(boolean in, boolean out) {
			canIntake = in;
			canOutput = out;
		}
	}
}

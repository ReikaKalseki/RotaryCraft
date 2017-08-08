/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.Data.WeightedRandom;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaMystcraftHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler.SoilType;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;


public class TileEntityGroundHydrator extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler {

	private static final double[][] AREA = {
		{1, 1, 1, 1, 2, 2, 3, 2, 2, 1, 1, 1, 1},
		{1, 1, 1, 2, 2, 3, 4, 3, 2, 2, 1, 1, 1},
		{1, 1, 2, 3, 5, 6, 6, 6, 5, 3, 2, 1, 1},
		{1, 2, 3, 4, 6, 7, 7, 7, 6, 4, 3, 2, 1},
		{2, 2, 4, 6, 7, 8, 8, 8, 7, 6, 4, 2, 2},
		{2, 3, 6, 7, 8, 9, 9, 9, 8, 7, 6, 3, 2},
		{3, 4, 6, 7, 8, 9, 0, 9, 8, 7, 6, 4, 3},
		{2, 3, 6, 7, 8, 9, 9, 9, 8, 7, 6, 3, 2},
		{2, 2, 4, 6, 7, 8, 8, 8, 7, 6, 4, 2, 2},
		{1, 2, 3, 5, 6, 7, 7, 7, 6, 5, 3, 2, 1},
		{1, 1, 2, 3, 4, 6, 6, 6, 4, 3, 2, 1, 1},
		{1, 1, 1, 2, 2, 3, 4, 3, 2, 2, 1, 1, 1},
		{1, 1, 1, 1, 2, 2, 3, 2, 2, 1, 1, 1, 1},
	};

	private static final WeightedRandom<Coordinate> coordinateRand = WeightedRandom.fromArray(AREA);

	public static final int FLUID_PER_BLOCK = 25;

	private final HybridTank tank = new HybridTank("hydrator", 1000);

	public static int getRange() {
		return (AREA.length-1)/2;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.HYDRATOR;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (!world.isRemote && tank.getLevel() >= FLUID_PER_BLOCK && rand.nextInt(this.getTickRate(world)) == 0) {
			Coordinate c = coordinateRand.getRandomEntry().offset(x, y, z);
			Block b = c.getBlock(world);
			boolean flag = false;
			if (b == Blocks.farmland) {
				flag = ReikaWorldHelper.hydrateFarmland(world, c.xCoord, c.yCoord, c.zCoord, false);
			}
			else if (b == ForestryHandler.BlockEntry.SOIL.getBlock()) {
				int gmeta = c.getBlockMetadata(world);
				SoilType type = ForestryHandler.SoilType.getTypeFromMeta(gmeta);
				if (type == SoilType.HUMUS) {
					flag = this.refreshHumus(world, c.xCoord, c.yCoord, c.zCoord, gmeta);
				}
				else if (type == SoilType.BOG_EARTH) {
					if (rand.nextInt(4) == 0)
						flag = this.matureBog(world, c.xCoord, c.yCoord, c.zCoord, gmeta);
				}
			}
			if (flag) {
				tank.removeLiquid(FLUID_PER_BLOCK);
			}
		}
	}

	private int getTickRate(World world) {
		return ModList.MYSTCRAFT.isLoaded() && ReikaMystcraftHelper.isMystAge(world) && ReikaMystcraftHelper.isSymbolPresent(world, "EnvAccel") ? 1 : 2;
	}

	private boolean refreshHumus(World world, int x, int y, int z, int meta) {
		int type = meta & 0x3;
		int grade = meta >> 2;

		if (grade == 0)
			return false;

		grade--;
		meta = grade << 2 | type;
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		return true;
	}

	private boolean matureBog(World world, int x, int y, int z, int meta) {
		int type = meta & 0x3;
		int maturity = meta >> 2;

		if (maturity >= 3)
			return false;

		maturity++;
		meta = maturity << 2 | type;
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.canFill(from, resource.getFluid()) ? tank.fill(resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return /*from != ForgeDirection.UP && */fluid == FluidRegistry.WATER;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return Flow.INPUT;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return ReikaAABBHelper.getBlockAABB(this).expand(this.getRange(), 0.5, this.getRange());
	}

	public static boolean affectsBlock(Block b, int meta) {
		return b == Blocks.farmland || b == ForestryHandler.BlockEntry.SOIL.getBlock();
	}

}

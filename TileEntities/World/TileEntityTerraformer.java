/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.ItemReq;
import Reika.DragonAPI.Instantiable.Data.ObjectWeb;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.ColumnArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.SelectableTiles;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import buildcraft.api.core.IAreaProvider;

public class TileEntityTerraformer extends InventoriedPowerLiquidReceiver implements SelectableTiles, DiscreteFunction {

	private static final ObjectWeb<BiomeGenBase> transforms = new ObjectWeb();
	private static final HashMap<BiomeStep, Collection<ItemReq>> itemReqs = new HashMap();
	private static final HashMap<BiomeStep, Integer> powerReqs = new HashMap();
	private static final HashMap<BiomeStep, FluidStack> liquidReqs = new HashMap();

	private final ColumnArray coords = new ColumnArray();
	private Comparator<Coordinate> positionComparator;

	private BiomeGenBase target;

	@Override
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.TERRAFORMER;
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
	protected void onFirstTick(World world, int x, int y, int z) {
		positionComparator = new PositionComparator(this);
		this.getCoordsFromIAP(world, x, y, z);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		tickcount++;

		//ReikaJavaLibrary.pConsoleSideOnly(String.format("Tick %2d: ", tickcount)+coords, Side.SERVER);
		//ReikaJavaLibrary.pConsole(this.getValidTargetBiomes(this.getCentralBiome()));
		//ReikaJavaLibrary.pConsole(coords.getSize(), Side.SERVER);
		/*
		if (coords.isEmpty()) {
			for (int i = -16; i <= 16; i++) {
				for (int j = -16; j <= 16; j++) {
					coords.add(x+i, z+j);
				}
			}
			}*/

		//ReikaJavaLibrary.pConsole(coords, Side.SERVER);

		if (coords.isEmpty()) {
			return;
		}

		if (!this.hasRedstoneSignal())
			return;

		//ReikaJavaLibrary.pConsole(String.format("Tick %2d: ", tickcount)+this.getOperationTime(), Side.SERVER);

		if (tickcount >= this.getOperationTime()) {
			int index = rand.nextInt(coords.getSize());
			Coordinate xz = coords.getNthColumn(index);
			if (!world.isRemote) {
				if (this.setBiome(world, xz.xCoord, xz.zCoord)) {
					//ReikaJavaLibrary.pConsole(Arrays.toString(xz), Side.SERVER);
					//ReikaJavaLibrary.pConsole("Removing "+x+", "+z);
					coords.remove(index);
				}
				tickcount = 0;
			}
		}
	}

	private void getCoordsFromIAP(World world, int x, int y, int z) {
		coords.clear();
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			TileEntity te = world.getTileEntity(dx, dy, dz);
			if (InterfaceCache.AREAPROVIDER.instanceOf(te)) {
				IAreaProvider iap = (IAreaProvider)te;
				for (int mx = iap.xMin(); mx <= iap.xMax(); mx++) {
					for (int mz = iap.zMin(); mz <= iap.zMax(); mz++) {
						this.addCoordinate(mx, mz, false);
					}
				}
				iap.removeFromWorld();
				coords.sort(positionComparator);
				return;
			}
		}
	}

	public int[] getUniqueID() {
		return new int[]{xCoord, yCoord, zCoord};
	}

	private boolean setBiome(World world, int x, int z) {
		if (!world.isRemote && !ReikaPlayerAPI.playerCanBreakAt((WorldServer)world, x, yCoord, z, this.getServerPlacer()))
			return false;
		BiomeGenBase from = world.getBiomeGenForCoords(x, z);
		if (!this.isValidTarget(from)) {
			return false;
		}
		if (!DragonAPICore.debugtest && !this.getReqsForTransform(from, target))
			return false;
		//ReikaJavaLibrary.pConsole("Setting biome @ "+x+", "+z+" to "+target.biomeName);
		if (this.modifyBlocks())
			ReikaWorldHelper.setBiomeAndBlocksForXZ(world, x, z, target);
		else
			ReikaWorldHelper.setBiomeForXZ(world, x, z, target);
		return true;
	}

	public boolean modifyBlocks() {
		return ConfigRegistry.BIOMEBLOCKS.getState() && ReikaInventoryHelper.checkForItem(Items.diamond, inv);
	}

	private void addCoordinate(int x, int z, boolean sort) {
		if (this.hasRedstoneSignal())
			return;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x, z);
		if (coords.add(x, z)) {
			RotaryCraft.logger.debug("Added coordinate "+x+"x, "+z+"z to "+this);
			if (sort)
				coords.sort(positionComparator);
		}
	}

	public void addTile(int x, int y, int z) {
		this.addCoordinate(x, z, true);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	private boolean isValidTarget(BiomeGenBase from) {
		return transforms.isDirectionallyConnectedTo(from, target);
	}

	public FluidStack getReqLiquidForTransform(BiomeGenBase from, BiomeGenBase to) {
		BiomeStep li = new BiomeStep(from, to);
		FluidStack liq = liquidReqs.get(li);
		return liq;
	}

	public ArrayList<ItemStack> getItemsForTransform(BiomeGenBase from, BiomeGenBase to) {
		BiomeStep li = new BiomeStep(from, to);
		ArrayList<ItemStack> is = new ArrayList();
		Collection<ItemReq> req = itemReqs.get(li);
		if (req != null) {
			for (ItemReq r : req) {
				is.add(r.asItemStack());
			}
		}
		return is;
	}

	private boolean getReqsForTransform(BiomeGenBase from, BiomeGenBase to) { //test and consume resources
		BiomeStep li = new BiomeStep(from, to);
		int min = powerReqs.get(li);
		FluidStack liq = liquidReqs.get(li);
		Collection<ItemReq> items = itemReqs.get(li);

		if (power < min)
			return false;

		if (liq != null) {
			if (tank.getLevel() < liq.amount)
				return false;
		}

		if (items != null) {
			for (ItemReq is : items) {
				if (!ReikaInventoryHelper.checkForItemStack(is.itemID, is.metadata, inv))
					return false;
			}

			//We have everything by this point
			for (ItemReq is : items) {
				if (is.callAndConsume()) {
					int slot = ReikaInventoryHelper.locateInInventory(is.itemID, is.metadata, inv);
					ReikaInventoryHelper.decrStack(slot, inv);
				}
			}
		}
		if (liq != null)
			tank.removeLiquid(liq.amount);
		return true;
	}

	private static void addBiomeTransformation(BiomeGenBase from, BiomeGenBase to, int power, FluidStack liq, ItemReq... items) {
		ArrayList<BiomeGenBase> li = new ArrayList();
		li.add(from);
		li.addAll(ReikaBiomeHelper.getChildBiomes(from));
		for (BiomeGenBase from_ : li) {
			BiomeStep step = new BiomeStep(from_, to);
			transforms.addDirectionalConnection(from_, to);
			itemReqs.put(step, ReikaJavaLibrary.makeListFromArray(items));
			powerReqs.put(step, power);
			liquidReqs.put(step, liq);
		}
	}

	static {
		addBiomeTransformation(BiomeGenBase.desert, BiomeGenBase.savanna, 65536, FluidRegistry.getFluidStack("water", 30), new ItemReq(Blocks.tallgrass, 1, 0.5F), new ItemReq(Blocks.sapling, 4, 0.05F));
		addBiomeTransformation(BiomeGenBase.savanna, BiomeGenBase.plains, 32768, FluidRegistry.getFluidStack("water", 20), new ItemReq(Blocks.tallgrass, 1, 0.3F));
		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.forest, 131072, FluidRegistry.getFluidStack("water", 10), new ItemReq(Blocks.sapling, 0, 0.5F), new ItemReq(Blocks.sapling, 2, 0.2F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.jungle, 262144, FluidRegistry.getFluidStack("water", 50), new ItemReq(Blocks.sapling, 0, 0.4F), new ItemReq(Blocks.sapling, 0, 0.6F), new ItemReq(Blocks.tallgrass, 2, 0.3F));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.swampland, 32768, FluidRegistry.getFluidStack("water", 100), new ItemReq(Blocks.sapling, 0, 0.1F), new ItemReq(Blocks.red_mushroom, 0.05F), new ItemReq(Blocks.brown_mushroom, 0.15F));
		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.ocean, 131072, FluidRegistry.getFluidStack("water", 500));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.frozenOcean, 1024, null, new ItemReq(Blocks.ice, 1));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.extremeHills, 65536, null, new ItemReq(Blocks.sapling, 0, 0.05F));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.icePlains, 8192, null, new ItemReq(Blocks.snow, 1), new ItemReq(Blocks.sapling, 0, 0.05F));
		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.plains, 524288, null, new ItemReq(Blocks.tallgrass, 1, 0.7F));

		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.mushroomIsland, 1048576, null, new ItemReq(Blocks.dirt, 1), new ItemReq(Blocks.mycelium, 1), new ItemReq(Blocks.red_mushroom, 0.9F), new ItemReq(Blocks.brown_mushroom, 0.9F)); //?

		addBiomeTransformation(BiomeGenBase.mushroomIsland, BiomeGenBase.extremeHills, 262144, null, new ItemReq(Blocks.grass, 0.125F), new ItemReq(Blocks.sapling, 0, 0.05F), new ItemReq(Blocks.tallgrass, 1, 0.25F)); //?

		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.taiga, 131072, null, new ItemReq(Blocks.sapling, 1, 0.25F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.coldTaiga, 131072, null, new ItemReq(Blocks.snow, 0.3F), new ItemReq(Blocks.sapling, 1, 0.25F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.roofedForest, 65536, FluidRegistry.getFluidStack("water", 40), new ItemReq(Blocks.sapling, 5, 0.5F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.birchForest, 32768, null, new ItemReq(Blocks.sapling, 2, 0.25F));

		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.coldTaiga, 32768, null, new ItemReq(Blocks.snow, 0.3F));
		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.icePlains, 65536, null, new ItemReq(Blocks.snow, 1), new ItemReq(Blocks.sapling, 0, 0.05F));
		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.forest, 131072, null, new ItemReq(Blocks.sapling, 0, 0.4F), new ItemReq(Blocks.sapling, 2, 0.1F));
		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.megaTaiga, 32768, FluidRegistry.getFluidStack("water", 20), new ItemReq(Blocks.sapling, 1, 0.1F));

		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.frozenOcean, 32768, FluidRegistry.getFluidStack("water", 100), new ItemReq(Blocks.ice, 1));


		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.savanna, 65536, null, new ItemReq(Blocks.sapling, 4, 0.05F));
		addBiomeTransformation(BiomeGenBase.savanna, BiomeGenBase.desert, 65536, null, new ItemReq(Blocks.sand, 1), new ItemReq(Blocks.sandstone, 0.5F), new ItemReq(Blocks.cactus, 0.1F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.plains, 262144, null, new ItemReq(Blocks.tallgrass, 1, 0.8F));
		addBiomeTransformation(BiomeGenBase.jungle, BiomeGenBase.forest, 65536, null, new ItemReq(Blocks.sapling, 0, 0.5F), new ItemReq(Blocks.sapling, 2, 0.2F));

		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.plains, 262144, null, new ItemReq(Blocks.tallgrass, 1, 0.8F), new ItemReq(Blocks.dirt, 0.8F));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.swampland, 524288, null, new ItemReq(Blocks.sapling, 0, 0.1F), new ItemReq(Blocks.red_mushroom, 0.05F), new ItemReq(Blocks.brown_mushroom, 0.15F), new ItemReq(Blocks.grass, 0.125F));

		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.ocean, 524288, null);
		addBiomeTransformation(BiomeGenBase.extremeHills, BiomeGenBase.plains, 262144, null, new ItemReq(Blocks.tallgrass, 1, 0.6F));

		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.taiga, 65536, null, new ItemReq(Blocks.sapling, 1, 0.4F));
		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.icePlains, 65536, null, new ItemReq(Blocks.sapling, 0, 0.05F), new ItemReq(Blocks.dirt, 1), new ItemReq(Blocks.grass, 0.125F));

		addBiomeTransformation(BiomeGenBase.desert, BiomeGenBase.mesa, 32768, null, new ItemReq(Blocks.clay, 0.2F));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.deepOcean, 1024, FluidRegistry.getFluidStack("water", 200));

		addBiomeTransformation(BiomeGenBase.mesa, BiomeGenBase.desert, 16384, null, new ItemReq(Blocks.sand, 0.5F), new ItemReq(Blocks.sandstone, 0.1F));

		addBiomeTransformation(BiomeGenBase.roofedForest, BiomeGenBase.forest, 32768, null, new ItemReq(Blocks.sapling, 0, 0.5F), new ItemReq(Blocks.sapling, 2, 0.2F));
		addBiomeTransformation(BiomeGenBase.birchForest, BiomeGenBase.forest, 32768, null, new ItemReq(Blocks.sapling, 0, 0.5F));
	}

	public BiomeGenBase getTarget() {
		return target;
	}

	public void setTarget(BiomeGenBase tg) {
		target = tg;
	}

	public Collection<BiomeGenBase> getValidTargetBiomes(BiomeGenBase start) {
		return transforms.getChildren(start);
	}

	public BiomeGenBase getCentralBiome() {
		return worldObj.getBiomeGenForCoords(xCoord, zCoord);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		int tg = NBT.getInteger("tg");
		if (tg != -1)
			target = BiomeGenBase.biomeList[tg];
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		if (target != null)
			NBT.setInteger("tg", target.biomeID);
		else
			NBT.setInteger("tg", -1);
	}

	/** Returns the valid transformations registered to the terraformer. */
	public static ArrayList<BiomeTransform> getTransformList() {
		ArrayList<BiomeTransform> li = new ArrayList();
		for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			BiomeGenBase start = BiomeGenBase.biomeList[i];
			if (transforms.hasNode(start)) {
				Collection<BiomeGenBase> tgs = transforms.getChildren(start);
				for (BiomeGenBase to : tgs) {
					BiomeStep step = new BiomeStep(start, to);
					long power = powerReqs.get(step);
					FluidStack fs = liquidReqs.get(step);
					Collection<ItemReq> items = itemReqs.get(step);
					li.add(new BiomeTransform(step, power, fs, items));
				}
			}
		}
		return li;
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public int getCapacity() {
		return 24000;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.TERRAFORMER.getOperationTime(omega);
	}

	public static final class BiomeStep {
		public final BiomeGenBase start;
		public final BiomeGenBase finish;

		private BiomeStep(BiomeGenBase in, BiomeGenBase out) {
			start = in;
			finish = out;
		}

		@Override
		public int hashCode() {
			return start.hashCode()^finish.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof BiomeStep) {
				BiomeStep b = (BiomeStep)o;
				return b.start == start && b.finish == finish;
			}
			return false;
		}
	}

	public static final class BiomeTransform {

		public final BiomeStep change;
		public final long power;
		private final FluidStack fluid;
		private final Collection<ItemReq> items;

		private BiomeTransform(BiomeStep step, long power, FluidStack fs, Collection<ItemReq> li) {
			change = step;
			this.power = power;
			fluid = fs;
			items = li;
		}

		public FluidStack getFluid() {
			return fluid != null ? fluid.copy() : null;
		}

		public Collection<ItemReq> getItems() {
			return Collections.unmodifiableCollection(items);
		}
	}

	private static class PositionComparator implements Comparator<Coordinate> {

		private final Coordinate origin;

		private PositionComparator(TileEntityTerraformer te) {
			origin = new Coordinate(te);
		}

		@Override
		public int compare(Coordinate o1, Coordinate o2) {
			if (o1.equals(o2))
				return 0;
			else if (o1.equals(origin))
				return Integer.MAX_VALUE;
			else if (o2.equals(origin))
				return Integer.MIN_VALUE;
			else
				return (o1.xCoord+o1.zCoord)-(o2.xCoord+o2.zCoord);
		}

	}
}

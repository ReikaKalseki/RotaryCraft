/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Instantiable.ItemReq;
import Reika.DragonAPI.Instantiable.ObjectWeb;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityTerraformer extends TileEntityInventoriedPowerReceiver implements PipeConnector {

	private static final ObjectWeb transforms = new ObjectWeb(BiomeGenBase.class);
	private static final HashMap<List<BiomeGenBase>, List<ItemReq>> itemReqs = new HashMap<List<BiomeGenBase>, List<ItemReq>>();
	private static final HashMap<List<BiomeGenBase>, Integer> powerReqs = new HashMap<List<BiomeGenBase>, Integer>();
	private static final HashMap<List<BiomeGenBase>, LiquidStack> liquidReqs = new HashMap<List<BiomeGenBase>, LiquidStack>();

	private ItemStack[] inv = new ItemStack[54];

	private int waterLevel = 0;

	private List<int[]> coords = new ArrayList<int[]>();

	private BiomeGenBase target;

	@Override
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.TERRAFORMER.ordinal();
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
		//ReikaWorldHelper.setBiomeForXZ(world, -158, 301, BiomeGenBase.taiga);
		if (coords.isEmpty())
			return;

		if (this.operationComplete(tickcount, 0)) {
			int index = par5Random.nextInt(coords.size());
			int[] xz = coords.get(index);
			this.setBiome(world, xz[0], xz[1]);
			coords.remove(index);
			tickcount = 0;
		}
	}

	private void setBiome(World world, int x, int z) {
		BiomeGenBase from = world.getBiomeGenForCoords(x, z);
		if (from != this.getCentralBiome())
			return;
		if (!this.isValidTarget(from))
			return;
		if (!this.getReqsForTransform(from, target))
			return;
		ReikaWorldHelper.setBiomeForXZ(world, x, z, target);
	}

	public void addCoordinate(int x, int z) {
		coords.add(new int[]{x,z});
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return p == MachineRegistry.PIPE;
	}

	private boolean isValidTarget(BiomeGenBase from) {
		return transforms.isDirectionallyConnectedTo(from, target);
	}

	public LiquidStack getReqLiquidForTransform(BiomeGenBase from, BiomeGenBase to) {
		List<BiomeGenBase> li = ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to});
		LiquidStack liq = liquidReqs.get(li);
		return liq;
	}

	public List<ItemStack> getItemsForTransform(BiomeGenBase from, BiomeGenBase to) {
		List<BiomeGenBase> li = ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to});
		List<ItemStack> is = new ArrayList<ItemStack>();
		List<ItemReq> req = itemReqs.get(li);
		for (int i = 0; i < req.size(); i++) {
			is.add(req.get(i).asItemStack());
		}
		return is;
	}

	private boolean getReqsForTransform(BiomeGenBase from, BiomeGenBase to) { //test and consume resources
		List<BiomeGenBase> li = ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to});
		int min = powerReqs.get(li);
		LiquidStack liq = liquidReqs.get(li);
		List<ItemReq> items = itemReqs.get(li);

		if (power < min)
			return false;

		if (waterLevel < liq.amount)
			return false;

		for (int i = 0; i < items.size(); i++) {
			ItemReq is = items.get(i);
			if (!ReikaInventoryHelper.checkForItemStack(is.itemID, is.metadata, inv))
				return false;
		}

		//We have everything
		for (int i = 0; i < items.size(); i++) {
			ItemReq is = items.get(i);
			if (is.callAndConsume()) {
				int slot = ReikaInventoryHelper.locateInInventory(is.itemID, is.metadata, inv);
				ReikaInventoryHelper.decrStack(slot, inv);
			}
		}
		waterLevel -= liq.amount;
		return true;
	}

	private static void addBiomeTransformation(BiomeGenBase from, BiomeGenBase to, int power, LiquidStack liq, ItemReq... items) {
		transforms.addDirectionalConnection(from, to);
		itemReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to}), ReikaJavaLibrary.makeListFromArray(items));
		powerReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to}), power);
		liquidReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to}), liq);
	}

	static {
		addBiomeTransformation(BiomeGenBase.desert, BiomeGenBase.plains, 0, LiquidDictionary.getLiquid("Water", 30), new ItemReq(Block.tallGrass.blockID, 1, 0.8F));
		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.forest, 0, LiquidDictionary.getLiquid("Water", 10), new ItemReq(Block.sapling.blockID, 0, 0.5F), new ItemReq(Block.sapling.blockID, 2, 0.2F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.jungle, 0, LiquidDictionary.getLiquid("Water", 50), new ItemReq(Block.sapling.blockID, 0, 0.4F), new ItemReq(Block.sapling.blockID, 0, 0.6F), new ItemReq(Block.tallGrass.blockID, 2, 0.3F));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.swampland, 0, LiquidDictionary.getLiquid("Water", 100), new ItemReq(Block.sapling.blockID, 0, 0.1F), new ItemReq(Block.mushroomRed, 0.05F), new ItemReq(Block.mushroomBrown, 0.15F));
		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.ocean, 0, LiquidDictionary.getLiquid("Water", 500));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.frozenOcean, 0, null, new ItemReq(Block.ice, 1));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.extremeHills, 0, null, new ItemReq(Block.sapling.blockID, 0, 0.05F));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.icePlains, 0, null, new ItemReq(Block.blockSnow, 1), new ItemReq(Block.sapling.blockID, 0, 0.05F));
		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.plains, 0, null, new ItemReq(Block.tallGrass.blockID, 1, 0.7F));

		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.mushroomIsland, 0, null, new ItemReq(Block.dirt, 1), new ItemReq(Block.mycelium, 1), new ItemReq(Block.mushroomRed, 0.9F), new ItemReq(Block.mushroomBrown, 0.9F)); //?

		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.taiga, 0, null, new ItemReq(Block.blockSnow, 0.3F), new ItemReq(Block.sapling.blockID, 1, 0.25F));
		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.icePlains, 0, null, new ItemReq(Block.blockSnow, 1), new ItemReq(Block.sapling.blockID, 0, 0.05F));
		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.frozenOcean, 0, LiquidDictionary.getLiquid("Water", 100), new ItemReq(Block.ice, 1));


		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.desert, 0, null, new ItemReq(Block.sand, 1), new ItemReq(Block.sandStone, 0.5F), new ItemReq(Block.cactus, 0.1F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.plains, 0, null, new ItemReq(Block.tallGrass, 0.8F));
		addBiomeTransformation(BiomeGenBase.jungle, BiomeGenBase.forest, 0, null, new ItemReq(Block.sapling.blockID, 0, 0.5F), new ItemReq(Block.sapling.blockID, 2, 0.2F));

		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.plains, 0, null, new ItemReq(Block.tallGrass, 0.8F), new ItemReq(Block.dirt, 0.8F));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.swampland, 0, null, new ItemReq(Block.sapling.blockID, 0, 0.1F), new ItemReq(Block.mushroomRed, 0.05F), new ItemReq(Block.mushroomBrown, 0.15F), new ItemReq(Block.grass, 0.125F));

		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.ocean, 0, null);
		addBiomeTransformation(BiomeGenBase.extremeHills, BiomeGenBase.plains, 0, null, new ItemReq(Block.tallGrass, 0.6F));

		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.forest, 0, null, new ItemReq(Block.sapling.blockID, 0, 0.4F), new ItemReq(Block.sapling.blockID, 2, 0.1F));

		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.taiga, 0, null, new ItemReq(Block.sapling.blockID, 1, 0.4F));
		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.icePlains, 0, null, new ItemReq(Block.sapling.blockID, 0, 0.05F), new ItemReq(Block.dirt, 1), new ItemReq(Block.grass, 0.125F));
	}

	public BiomeGenBase getTarget() {
		return target;
	}

	public void setTarget(BiomeGenBase tg) {
		target = tg;
	}

	public List<BiomeGenBase> getValidTargetBiomes(BiomeGenBase start) {
		return transforms.getChildren(start);
	}

	public BiomeGenBase getCentralBiome() {
		return worldObj.getBiomeGenForCoords(xCoord, zCoord);
	}
}

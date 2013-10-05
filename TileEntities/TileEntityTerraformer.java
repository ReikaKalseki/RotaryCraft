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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.ColumnArray;
import Reika.DragonAPI.Instantiable.ItemReq;
import Reika.DragonAPI.Instantiable.ObjectWeb;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.SelectableTiles;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityTerraformer extends TileEntityInventoriedPowerReceiver implements PipeConnector, SelectableTiles {

	private static final ObjectWeb transforms = new ObjectWeb(BiomeGenBase.class);
	private static final HashMap<List<BiomeGenBase>, List<ItemReq>> itemReqs = new HashMap<List<BiomeGenBase>, List<ItemReq>>();
	private static final HashMap<List<BiomeGenBase>, Integer> powerReqs = new HashMap<List<BiomeGenBase>, Integer>();
	private static final HashMap<List<BiomeGenBase>, LiquidStack> liquidReqs = new HashMap<List<BiomeGenBase>, LiquidStack>();

	private ItemStack[] inv = new ItemStack[54];

	private int waterLevel = 0;
	private int oldLevel;

	private ColumnArray coords = new ColumnArray();

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
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
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
		this.getSummativeSidedPower();
		this.getWater(world, x, y, z);
		tickcount++;

		//ReikaJavaLibrary.pConsoleSideOnly(String.format("Tick %2d: ", tickcount)+coords, Side.SERVER);
		//ReikaJavaLibrary.pConsole(this.getValidTargetBiomes(this.getCentralBiome()));

		if (coords.isEmpty()) {
			return;
		}

		if (!world.isBlockIndirectlyGettingPowered(x, y, z))
			return;

		//ReikaJavaLibrary.pConsole(String.format("Tick %2d: ", tickcount)+this.operationComplete(tickcount, 0));

		if (this.operationComplete(tickcount, 0)) {
			int index = par5Random.nextInt(coords.getSize());
			int[] xz = coords.getNthColumn(index);
			if (this.setBiome(world, xz[0], xz[1])) {
				//ReikaJavaLibrary.pConsole("Removing "+x+", "+z);
				coords.remove(index);
			}
			tickcount = 0;
		}
	}

	public int[] getUniqueID() {
		return new int[]{xCoord, yCoord, zCoord};
	}

	private void getWater(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			int dx = x+dirs[i].offsetX;
			int dy = y+dirs[i].offsetY;
			int dz = z+dirs[i].offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.PIPE)
				this.interPipe(world, dx, dy, dz);
		}
	}

	private void interPipe(World world, int x, int y, int z) {
		TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z);
		if (tile != null && (tile.liquidID == 9 && tile.liquidLevel > 0)) {
			oldLevel = tile.liquidLevel;
			tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
			waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
		}
	}

	private boolean setBiome(World world, int x, int z) {
		BiomeGenBase from = world.getBiomeGenForCoords(x, z);
		if (!this.isValidTarget(from))
			return false;
		if (!this.getReqsForTransform(from, target))
			return false;
		//ReikaJavaLibrary.pConsole("Setting biome @ "+x+", "+z+" to "+target.biomeName);
		if (this.modifyBlocks())
			ReikaWorldHelper.setBiomeAndBlocksForXZ(world, x, z, target);
		else
			ReikaWorldHelper.setBiomeForXZ(world, x, z, target);
		ReikaWorldHelper.causeAdjacentUpdates(world, x, yCoord, z);
		world.markBlockRangeForRenderUpdate(x, 0, z, x, world.provider.getHeight(), z);
		return true;
	}

	public boolean modifyBlocks() {
		return DragonAPICore.isReikasComputer() && ConfigRegistry.BIOMEBLOCKS.getState() && ReikaInventoryHelper.checkForItem(Item.diamond.itemID, inv);
	}

	private void addCoordinate(int x, int z) {
		if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			return;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x, z);
		RotaryCraft.logger.debug("Added coordinate "+x+"x, "+z+"z to "+this);
		coords.add(x, z);
	}

	public void addTile(int x, int y, int z) {
		this.addCoordinate(x, z);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
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

		if (liq != null) {
			if (waterLevel < liq.amount)
				return false;
		}

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
		if (liq != null)
			waterLevel -= liq.amount;
		return true;
	}

	private static void addBiomeTransformation(BiomeGenBase from, BiomeGenBase to, int power, LiquidStack liq, ItemReq... items) {
		List<BiomeGenBase> li = ReikaBiomeHelper.getAllAssociatedBiomes(from);
		for (int i = 0; i < li.size(); i++) {
			BiomeGenBase from_ = li.get(i);
			transforms.addDirectionalConnection(from_, to);
			itemReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from_, to}), ReikaJavaLibrary.makeListFromArray(items));
			powerReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from_, to}), power);
			liquidReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from_, to}), liq);
		}
	}

	static {
		addBiomeTransformation(BiomeGenBase.desert, BiomeGenBase.plains, 65536, LiquidDictionary.getLiquid("Water", 30), new ItemReq(Block.tallGrass.blockID, 1, 0.8F));
		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.forest, 131072, LiquidDictionary.getLiquid("Water", 10), new ItemReq(Block.sapling.blockID, 0, 0.5F), new ItemReq(Block.sapling.blockID, 2, 0.2F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.jungle, 262144, LiquidDictionary.getLiquid("Water", 50), new ItemReq(Block.sapling.blockID, 0, 0.4F), new ItemReq(Block.sapling.blockID, 0, 0.6F), new ItemReq(Block.tallGrass.blockID, 2, 0.3F));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.swampland, 32768, LiquidDictionary.getLiquid("Water", 100), new ItemReq(Block.sapling.blockID, 0, 0.1F), new ItemReq(Block.mushroomRed, 0.05F), new ItemReq(Block.mushroomBrown, 0.15F));
		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.ocean, 131072, LiquidDictionary.getLiquid("Water", 500));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.frozenOcean, 0, null, new ItemReq(Block.ice, 1));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.extremeHills, 65536, null, new ItemReq(Block.sapling.blockID, 0, 0.05F));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.icePlains, 8192, null, new ItemReq(Block.blockSnow, 1), new ItemReq(Block.sapling.blockID, 0, 0.05F));
		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.plains, 524288, null, new ItemReq(Block.tallGrass.blockID, 1, 0.7F));

		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.mushroomIsland, 1048576, null, new ItemReq(Block.dirt, 1), new ItemReq(Block.mycelium, 1), new ItemReq(Block.mushroomRed, 0.9F), new ItemReq(Block.mushroomBrown, 0.9F)); //?

		addBiomeTransformation(BiomeGenBase.mushroomIsland, BiomeGenBase.extremeHills, 262144, null, new ItemReq(Block.grass, 0.125F), new ItemReq(Block.sapling.blockID, 0, 0.05F), new ItemReq(Block.tallGrass.blockID, 1, 0.25F)); //?

		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.taiga, 131072, null, new ItemReq(Block.blockSnow, 0.3F), new ItemReq(Block.sapling.blockID, 1, 0.25F));
		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.icePlains, 65536, null, new ItemReq(Block.blockSnow, 1), new ItemReq(Block.sapling.blockID, 0, 0.05F));
		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.frozenOcean, 32768, LiquidDictionary.getLiquid("Water", 100), new ItemReq(Block.ice, 1));


		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.desert, 131072, null, new ItemReq(Block.sand, 1), new ItemReq(Block.sandStone, 0.5F), new ItemReq(Block.cactus, 0.1F));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.plains, 262144, null, new ItemReq(Block.tallGrass.blockID, 1, 0.8F));
		addBiomeTransformation(BiomeGenBase.jungle, BiomeGenBase.forest, 65536, null, new ItemReq(Block.sapling.blockID, 0, 0.5F), new ItemReq(Block.sapling.blockID, 2, 0.2F));

		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.plains, 262144, null, new ItemReq(Block.tallGrass.blockID, 1, 0.8F), new ItemReq(Block.dirt, 0.8F));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.swampland, 524288, null, new ItemReq(Block.sapling.blockID, 0, 0.1F), new ItemReq(Block.mushroomRed, 0.05F), new ItemReq(Block.mushroomBrown, 0.15F), new ItemReq(Block.grass, 0.125F));

		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.ocean, 524288, null);
		addBiomeTransformation(BiomeGenBase.extremeHills, BiomeGenBase.plains, 262144, null, new ItemReq(Block.tallGrass.blockID, 1, 0.6F));

		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.forest, 131072, null, new ItemReq(Block.sapling.blockID, 0, 0.4F), new ItemReq(Block.sapling.blockID, 2, 0.1F));

		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.taiga, 65536, null, new ItemReq(Block.sapling.blockID, 1, 0.4F));
		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.icePlains, 65536, null, new ItemReq(Block.sapling.blockID, 0, 0.05F), new ItemReq(Block.dirt, 1), new ItemReq(Block.grass, 0.125F));
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

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		waterLevel = NBT.getInteger("water");

		int tg = NBT.getInteger("tg");
		if (tg != -1)
			target = BiomeGenBase.biomeList[tg];
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("water", waterLevel);

		if (target != null)
			NBT.setInteger("tg", target.biomeID);
		else
			NBT.setInteger("tg", -1);


		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/** Returns the valid transformations registered to the terraformer.
	 * Return format: Object[5]:<br>
	 * O[0] = Start Biome<br>
	 * O[1] = Target Biome<br>
	 * O[2] = Required Power<br>
	 * O[3] = Required Liquid (as LiquidStack)<br>
	 * O[4] = Required items (as List of ItemReq) */
	public static ArrayList<Object[]> getTransformList() {
		ArrayList<Object[]> li = new ArrayList<Object[]>();
		for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			BiomeGenBase start = BiomeGenBase.biomeList[i];
			if (transforms.hasNode(start)) {
				List<BiomeGenBase> tgs = transforms.getChildren(start);
				for (int j = 0; j < tgs.size(); j++) {
					BiomeGenBase to = tgs.get(j);
					Object[] o = new Object[5];
					o[0] = start;
					o[1] = to;
					o[2] = powerReqs.get(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{start, to}));
					o[3] = liquidReqs.get(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{start, to}));
					o[4] = itemReqs.get(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{start, to}));
					li.add(o);
				}
			}
		}
		return li;
	}
}

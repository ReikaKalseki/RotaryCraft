/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.BCMachineHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;

public class TileEntityPressureBalancer extends RotaryCraftTileEntity implements PipeConnector, ITankContainer {

	public static final int CAPACITY = 240*LiquidContainerRegistry.BUCKET_VOLUME;

	private int waterLevel;
	private int lavaLevel;
	private int lubeLevel;
	private int jetFuelLevel;

	private static final LiquidStack[] liq = {
		LiquidDictionary.getLiquid("Water", 0), LiquidDictionary.getLiquid("Lava", 0),
		LiquidDictionary.getLiquid("Jet Fuel", 0), LiquidDictionary.getLiquid("Lubricant", 0)
	};

	enum Tanks {
		WATER(),
		LAVA(),
		FUEL(),
		LUBE();

		public static final Tanks[] tankList = values();

		public int getLiquidID() {
			return liq[this.ordinal()].itemID;
		}
	}

	private LiquidTank[] storage;

	private int oldlube;
	private int oldfuel;
	private int oldwater;
	private int oldlava;

	private SideState[] sides = new SideState[ForgeDirection.values().length];

	private boolean[] makeForge = new boolean[Tanks.tankList.length];

	public TileEntityPressureBalancer() {
		super();
		storage = new LiquidTank[Tanks.tankList.length];
		for (int i = 0; i < Tanks.tankList.length; i++) {
			storage[i] = new LiquidTank(liq[i], CAPACITY);
		}
		for (int i = 0; i < 6; i++) {
			sides[i] = SideState.values()[0];
		}
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
		return 0;//return MachineRegistry.BALANCER.ordinal();
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
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.values()[i].getOpposite();
			//ReikaJavaLibrary.pConsole(sides[i]+" on "+FMLCommonHandler.instance().getEffectiveSide()+" on "+RotaryAux.sideColorNames[i]);
			switch(sides[i]) {
			case RCWATERIN:
				this.transferPipe(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, 9);
				break;
			case RCLAVAIN:
				this.transferPipe(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, 11);
				break;
			case RCLUBEIN:
				this.transferHose(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				break;
			case RCFUELIN:
				this.transferFuel(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				break;

			case RCWATEROUT:
				this.addToPipe(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, 9);
				break;
			case RCLAVAOUT:
				this.addToPipe(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, 11);
				break;
			case RCLUBEOUT:
				this.addToHose(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				break;
			case RCFUELOUT:
				this.addToFuel(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				break;

			case FORGEWATERIN:
				break;
			case FORGELAVAIN:
				break;
			case FORGELUBEIN:
				break;
			case FORGEFUELIN:
				break;

			case FORGEWATEROUT:
				break;
			case FORGELAVAOUT:
				break;
			case FORGELUBEOUT:
				break;
			case FORGEFUELOUT:
				break;
			}
		}

		//ReikaJavaLibrary.pConsole(lubeLevel+" on "+FMLCommonHandler.instance().getEffectiveSide());

		//ReikaJavaLibrary.pConsoleSideOnly(waterLevel+" to "+storage[Tanks.WATER.ordinal()].getLiquid().amount, Side.SERVER);
		this.convertLiquids();
	}

	public boolean isToForge(int i) {
		return makeForge[i];
	}

	public void swapConversion(int i) {
		makeForge[i] = !makeForge[i];
	}

	private void convertLiquids() {
		for (int i = 0; i < Tanks.tankList.length; i++) {
			if (makeForge[i]) {
				int level = this.getLiquid(Tanks.tankList[i]);
				if (level > 0) {
					this.emptyLiquid(Tanks.tankList[i]);
					storage[i].fill(new LiquidStack(Tanks.tankList[i].getLiquidID(), level, 0), true);
				}
			}
			else {
				int level = this.getLiquid(Tanks.tankList[i]);
				if (level < CAPACITY && storage[i].getLiquid() != null) {
					//ReikaJavaLibrary.pConsoleSideOnly(Tanks.tankList[i].toString()+" to "+level+" + "+storage[i].getLiquid().amount, Side.SERVER);
					int amt = storage[i].getLiquid().amount;
					this.addLiquid(Tanks.tankList[i], amt);
					storage[i].drain(amt, true);
				}
			}
		}
	}

	private int getLiquid(Tanks tank) {
		switch(tank) {
		case WATER:
			return waterLevel;
		case LAVA:
			return lavaLevel;
		case FUEL:
			return jetFuelLevel;
		case LUBE:
			return lubeLevel;
		}
		return 0;
	}

	private void addLiquid(Tanks tank, int amount) {
		//ReikaJavaLibrary.pConsoleSideOnly("Tank "+tank+" + "+amount+"  ("+waterLevel+")", Side.SERVER);
		switch(tank) {
		case WATER:
			waterLevel += amount;
		case LAVA:
			lavaLevel += amount;
		case FUEL:
			jetFuelLevel += amount;
		case LUBE:
			lubeLevel += amount;
		}
	}

	private void emptyLiquid(Tanks tank) {
		switch(tank) {
		case WATER:
			waterLevel = 0;
		case LAVA:
			lavaLevel = 0;
		case FUEL:
			jetFuelLevel = 0;
		case LUBE:
			lubeLevel = 0;
		}
	}

	private void addToPipe(World world, int x, int y, int z, int liq) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z);
			if (tile.liquidID == liq) {
				if (liq == 9) {
					if (waterLevel <= 0)
						return;
					waterLevel--;
				}
				else if (liq == 1) {
					if (lavaLevel <= 0)
						return;
					lavaLevel--;
				}
				tile.liquidLevel++;
			}
			else if (tile.liquidID == -1) {
				if (liq == 9) {
					if (waterLevel <= 0)
						return;
					waterLevel--;
				}
				else if (liq == 1) {
					if (lavaLevel <= 0)
						return;
					lavaLevel--;
				}
				tile.liquidID = liq;
				tile.liquidLevel++;
			}
		}
	}

	private void addToHose(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z);
			if (lubeLevel <= 0)
				return;
			lubeLevel--;
			tile.lubricant++;
		}
	}

	private void addToFuel(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z);
			if (jetFuelLevel <= 0)
				return;
			jetFuelLevel--;
			tile.fuel++;
		}
	}

	private void transferPipe(World world, int x, int y, int z, int liq) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z);
			this.interPipe(tile, liq);
		}
	}

	private void interPipe(TileEntityPipe tile, int liquid) {
		if (tile != null) {
			if (tile.liquidID == 9 && liquid == 9) {
				if (tile.liquidLevel > waterLevel && tile.liquidLevel > 0) {
					oldwater = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-waterLevel)/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+(oldwater-waterLevel)/4+1, 0, "max");
				}
			}
			if (tile.liquidID == 11 && liquid == 11) {
				if (tile.liquidLevel > lavaLevel && tile.liquidLevel > 0) {
					oldlava = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-lavaLevel)/4-1, 0, "max");
					lavaLevel = ReikaMathLibrary.extrema(lavaLevel+(oldlava-lavaLevel)/4+1, 0, "max");
				}
			}
		}
	}

	private void transferHose(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z);
			this.interHose(tile);
		}
	}

	private void transferFuel(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z);
			this.interLine(tile);
		}
	}

	private void interLine(TileEntityFuelLine tile) {
		if (tile != null) {
			if (tile.fuel > jetFuelLevel) {
				oldfuel = tile.fuel;
				tile.fuel = ReikaMathLibrary.extrema(tile.fuel-(tile.fuel-jetFuelLevel)/4, 0, "max");
				jetFuelLevel = ReikaMathLibrary.extrema(jetFuelLevel+(oldfuel-jetFuelLevel)/4, 0, "max");
			}
		}
	}

	private void interHose(TileEntityHose tile) {
		if (tile != null) {
			if (tile.lubricant > lubeLevel) {
				oldlube = tile.lubricant;
				tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-(tile.lubricant-lubeLevel)/4, 0, "max");
				lubeLevel = ReikaMathLibrary.extrema(lubeLevel+(oldlube-lubeLevel)/4, 0, "max");
			}
		}
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		if (sides == null)
			return false;
		switch(side) {
		case DOWN:
			return sides[ForgeDirection.DOWN.ordinal()].connectsTo(p);
		case MINX:
			return sides[ForgeDirection.WEST.ordinal()].connectsTo(p);
		case MINZ:
			return sides[ForgeDirection.NORTH.ordinal()].connectsTo(p);
		case PLUSX:
			return sides[ForgeDirection.SOUTH.ordinal()].connectsTo(p);
		case PLUSZ:
			return sides[ForgeDirection.EAST.ordinal()].connectsTo(p);
		case UP:
			return sides[ForgeDirection.UP.ordinal()].connectsTo(p);
		}
		return true;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		int tank = this.getTankFromDirection(from.getOpposite());
		if (tank == -1)
			return null;
		return this.drain(tank, maxDrain, doDrain);
	}

	private int getTankFromDirection(ForgeDirection from) {
		//ReikaJavaLibrary.pConsoleSideOnly(from+" ("+from.ordinal()+") in "+Arrays.toString(sides)+" -> "+sides[from.ordinal()]+" ("+RotaryAux.sideColorNames[from.ordinal()]+")", Side.SERVER);
		switch(sides[from.ordinal()]) {
		case FORGEWATEROUT:
			return Tanks.WATER.ordinal();
		case FORGELAVAOUT:
			return Tanks.LAVA.ordinal();
		case FORGELUBEOUT:
			return Tanks.LUBE.ordinal();
		case FORGEFUELOUT:
			return Tanks.FUEL.ordinal();
		default:
			return -1;
		}
	}

	@Override
	public LiquidStack drain(int tankindex, int maxDrain, boolean doDrain) {
		return storage[tankindex].drain(maxDrain, doDrain);
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return storage;
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		int tank = this.getTankFromDirection(direction);
		if (tank == -1)
			return null;
		return storage[tank];
	}

	public int getWaterScaled(int px) {
		if (makeForge[Tanks.WATER.ordinal()]) {
			if (storage[Tanks.WATER.ordinal()].getLiquid() == null)
				return 0;
			return storage[Tanks.WATER.ordinal()].getLiquid().amount*px/CAPACITY;
		}
		else
			return px*waterLevel/CAPACITY;
	}

	public int getLavaScaled(int px) {
		if (makeForge[Tanks.LAVA.ordinal()]) {
			if (storage[Tanks.LAVA.ordinal()].getLiquid() == null)
				return 0;
			return storage[Tanks.LAVA.ordinal()].getLiquid().amount*px/CAPACITY;
		}
		else
			return px*lavaLevel/CAPACITY;
	}

	public int getFuelScaled(int px) {
		if (makeForge[Tanks.FUEL.ordinal()]) {
			if (storage[Tanks.FUEL.ordinal()].getLiquid() == null)
				return 0;
			return storage[Tanks.FUEL.ordinal()].getLiquid().amount*px/CAPACITY;
		}
		else
			return px*jetFuelLevel/CAPACITY;
	}

	public int getLubeScaled(int px) {
		if (makeForge[Tanks.LUBE.ordinal()]) {
			if (storage[Tanks.LUBE.ordinal()].getLiquid() == null)
				return 0;
			return storage[Tanks.LUBE.ordinal()].getLiquid().amount*px/CAPACITY;
		}
		else
			return px*lubeLevel/CAPACITY;
	}

	enum SideState {
		RCWATEROUT(false, Item.bucketWater, ItemStacks.pipe),
		RCLAVAOUT(false, Item.bucketLava, ItemStacks.pipe),
		RCLUBEOUT(false, ItemRegistry.BUCKET.getStackOfMetadata(0), ItemStacks.hose),
		RCFUELOUT(false, ItemRegistry.BUCKET.getStackOfMetadata(1), ItemStacks.fuelline),
		RCWATERIN(true, Item.bucketWater, ItemStacks.pipe),
		RCLAVAIN(true, Item.bucketLava, ItemStacks.pipe),
		RCLUBEIN(true, ItemRegistry.BUCKET.getStackOfMetadata(0), ItemStacks.hose),
		RCFUELIN(true, ItemRegistry.BUCKET.getStackOfMetadata(1), ItemStacks.fuelline),
		FORGEWATEROUT(false, Block.waterMoving, BCMachineHandler.getInstance().getTank()),
		FORGELAVAOUT(false, Block.lavaMoving, BCMachineHandler.getInstance().getTank()),
		FORGELUBEOUT(false, RotaryCraft.lubricant, BCMachineHandler.getInstance().getTank()),
		FORGEFUELOUT(false, RotaryCraft.jetFuel, BCMachineHandler.getInstance().getTank()),
		FORGEWATERIN(true, Block.waterMoving, BCMachineHandler.getInstance().getTank()),
		FORGELAVAIN(true, Block.lavaMoving, BCMachineHandler.getInstance().getTank()),
		FORGELUBEIN(true, RotaryCraft.lubricant, BCMachineHandler.getInstance().getTank()),
		FORGEFUELIN(true, RotaryCraft.jetFuel, BCMachineHandler.getInstance().getTank());

		private boolean isIn;
		private ItemStack icon;
		private ItemStack pipe;

		public static final SideState[] stateList = values();

		private SideState(boolean in, Block icon, ItemStack tofrom) {
			this(in, new ItemStack(icon), tofrom);
		}

		private SideState(boolean in, Item icon, ItemStack tofrom) {
			this(in, new ItemStack(icon), tofrom);
		}

		private SideState(boolean in, ItemStack is, ItemStack tofrom) {
			isIn = in;
			icon = is;
			pipe = tofrom;
		}

		public boolean canAcceptLiquid() {
			return isIn;
		}

		public boolean isForge() {
			return this.name().startsWith("FORGE");
		}

		public boolean isRC() {
			return this.name().startsWith("RC");
		}

		public boolean connectsTo(MachineRegistry p) {
			if (!this.isRC())
				return false;
			if (p == MachineRegistry.PIPE) {
				return this.isRCLava() || this.isRCWater();
			}
			if (p == MachineRegistry.FUELLINE) {
				return this == RCFUELOUT || this == RCFUELIN;
			}
			if (p == MachineRegistry.HOSE) {
				return this == RCLUBEOUT || this == RCLUBEIN;
			}
			return false;
		}

		private boolean isRCWater() {
			return this == RCWATEROUT || this == RCWATERIN;
		}

		private boolean isRCLava() {
			return this == RCLAVAOUT || this == RCLAVAIN;
		}

		public static SideState getNextState(SideState s) {
			if (s.ordinal() == SideState.stateList.length-1)
				return SideState.stateList[0];
			else {
				return SideState.stateList[s.ordinal()+1];
			}
		}

		public ItemStack getItemForIcon() {
			if (icon == null)
				return null;
			return icon.copy();
		}

		public ItemStack getItemForPiping() {
			if (pipe == null)
				return null;
			return pipe.copy();
		}
	}

	public SideState getStateOfSide(ForgeDirection s) {
		return sides[s.ordinal()];
	}

	private void setStateOfSide(ForgeDirection s, SideState state) {
		sides[s.ordinal()] = state;
	}

	public void incrementStateOnSide(ForgeDirection side) {
		this.setStateOfSide(side, SideState.getNextState(this.getStateOfSide(side)));
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("lube", lubeLevel);
		NBT.setInteger("water", waterLevel);
		NBT.setInteger("lava", lavaLevel);
		NBT.setInteger("fuel", jetFuelLevel);

		for (int i = 0; i < 6; i++) {
			NBT.setInteger("side"+i, sides[i].ordinal());
		}

		for (int i = 0; i < 4; i++) {
			NBT.setBoolean("forge"+i, makeForge[i]);
		}

		for (int i = 0; i < Tanks.tankList.length; i++) {
			if (storage[i].getLiquid() != null) {
				NBT.setTag("tank"+i, storage[i].getLiquid().writeToNBT(new NBTTagCompound()));
			}
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		lubeLevel = NBT.getInteger("lube");
		waterLevel = NBT.getInteger("water");
		lavaLevel = NBT.getInteger("lava");
		jetFuelLevel = NBT.getInteger("fuel");

		if (lubeLevel < 0) {
			lubeLevel = 0;
		}
		if (waterLevel < 0) {
			waterLevel = 0;
		}
		if (lavaLevel < 0) {
			lavaLevel = 0;
		}
		if (jetFuelLevel < 0) {
			jetFuelLevel = 0;
		}

		sides = new SideState[6];

		for (int i = 0; i < 6; i++) {
			sides[i] = SideState.values()[NBT.getInteger("side"+i)];
		}

		makeForge = new boolean[4];

		for (int i = 0; i < 4; i++) {
			makeForge[i] = NBT.getBoolean("forge"+i);
		}

		for (int i = 0; i < Tanks.tankList.length; i++) {
			if (NBT.hasKey("tank"+i)) {
				storage[i].setLiquid(LiquidStack.loadLiquidStackFromNBT(NBT.getCompoundTag("tank"+i)));
			}
		}
	}

}

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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RecipesPulseFurnace;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Models.ModelPulseFurnace;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityPulseFurnace extends TileEntityInventoriedPowerReceiver implements TemperatureTE, PipeConnector {
	private ItemStack inv[];

	/** The number of ticks that the current item has been cooking for */
	public int pulseFurnaceCookTime;

	public static final int CAPACITY = 64;
	public static final int MAXFUEL = 64;
	public static final int MAXTEMP = 1000; //1370C = melting steel, 800C = 90% strength loss
	public static final int SMELTTICKS = 100;

	public boolean idle = false;

	public int fuelLevel;
	public int waterLevel;
	public int temperature = 0;	//damage player, start fires, etc


	private int tickcount2 = 0;
	public int smelttick = 0;

	private int soundtick = 2000;

	boolean flag2 = false;
	/*
	    Minecraft m = Minecraft.getMinecraft();
	    WorldServer ws = m.getIntegratedServer().worldServerForDimension(m.thePlayer.dimension);*/

	public TileEntityPulseFurnace()
	{
		inv = new ItemStack[3];
		pulseFurnaceCookTime = 0;
		//SMELTTEMPS.put(new ItemStack(Item.ingotIron.itemID, 1, 0), Integer.valueOf(900));//900C for steelmaking
		//SMELTTEMPS.put(new ItemStack(Block.obsidian.blockID, 1, 0), Integer.valueOf(800)); //650-900C melting obsidian
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 2;
	}

	public void testIdle() {
		idle = (!this.canSmelt() && omega > MINSPEED);
	}

	public void drawFuel(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		if (fuelLevel < MAXFUEL) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y-1, z);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
		}
	}

	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		if (waterLevel < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
				if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
		}
	}

	public int getSmeltNumber(ItemStack is) {
		int num = is.stackSize;
		if (is.itemID != RotaryCraft.shaftcraft.itemID || is.getItemDamage() != 1) //if not making steel
			return 1;/*
	    	int a = par5Random.nextInt(2);
	    	int b = par5Random.nextInt(2);
	    	if (a == 0 && b == 0)
	    		return 1;
	    	if (num > 62)
	    		return 1;
	    	else
	    		return 2;*/
		return ReikaInventoryHelper.addUpToStack(is, 1, 5);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inv.length;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inv[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
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

		pulseFurnaceCookTime = NBT.getShort("CookTime");
		waterLevel = NBT.getInteger("water");
		fuelLevel = NBT.getInteger("fuel");
		temperature = NBT.getInteger("temp");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setShort("CookTime", (short)pulseFurnaceCookTime);
		NBT.setInteger("water", waterLevel);
		NBT.setInteger("fuel", fuelLevel);
		NBT.setInteger("temp", temperature);
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

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		return (pulseFurnaceCookTime * par1) / 20;
	}

	public int getFuelScaled(int par1)
	{
		return (fuelLevel * par1) / MAXFUEL;
	}

	public int getTempScaled(int par1)
	{
		return (temperature * par1) / MAXTEMP;
	}

	public int getWaterScaled(int par1)
	{
		return (waterLevel * par1) / CAPACITY;
	}

	public int getFireScaled(int par1)
	{
		return (smelttick * par1) / SMELTTICKS;
	}

	public void getFuel(World world, int x, int y, int z, int meta) {
		if (tickcount2 >= 100) {
			fuelLevel--;
			tickcount2 = 0;
		}
	}

	public void heatAmbient(World world, int x, int y, int z, int meta) {
		if (fuelLevel > 0 && this.canSmelt())
			temperature += ReikaMathLibrary.extrema((MAXTEMP-temperature)/8, 4, "max");

		if (waterLevel > 0) {
			if (par5Random.nextInt(3) == 0)
				waterLevel -= (temperature*2/MAXTEMP)*RotaryConfig.MILLIBUCKET;
			temperature -= temperature/64;
			if (waterLevel <= 0)
				waterLevel = 0;
		}
		if (temperature < 0)
			temperature = 0;
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		if (biome == BiomeGenBase.frozenOcean || biome == BiomeGenBase.frozenRiver ||
				biome == BiomeGenBase.iceMountains || biome == BiomeGenBase.icePlains ||
				biome == BiomeGenBase.taiga || biome == BiomeGenBase.taigaHills)
			temperature -= 4;
		else if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills ||
				biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills)
			temperature -= 1;
		else if (biome != BiomeGenBase.hell) //do not cool in the nether
			temperature -= 2;
	}

	public void smeltHeat() {
		//	this.temperature += 10*melttemp/980;	//980 kJ per degree kelvin
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		if (temperature > 915)
			world.spawnParticle("lava", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), 0, 0, 0);
		ReikaWorldHelper.temperatureEnvironment(world, x, y, z, temperature);
		if (temperature > MAXTEMP) {
			this.overheat(world, x, y, z);
		}

	}

	public int getReqTemps(ItemStack is) {
		if (is == null)
			return -1;
		int id = is.itemID;
		int meta = is.getItemDamage();
		if (id == RotaryCraft.shaftcraft.itemID) {
			if (meta == 9)
				return 900;
			if (meta == 10)
				return 780;
		}
		switch (id) {
		case 49:
			return 800; //obsidian melting
		case 265:
			return 900;	//steelmaking
		case 145:	//anvil (31 iron)
		case 154: //hopper (5 iron)
		case 256:
		case 257:
		case 258:
		case 267:
		case 292:
		case 306:
		case 307:
		case 308:
		case 309:
		case 325:
		case 326:
		case 327:
		case 328:
		case 330:
		case 335:
		case 380:
		case 302:
		case 303:
		case 304:
		case 305:
		case 259:
		case 66:
		case 28:
		case 101:
		case 157:	//activator rails (6 iron)
			return 780;	//iron /2
		case 283:
		case 284:
		case 285:
		case 286:
		case 294:
		case 314:
		case 315:
		case 316:
		case 317:
		case 27:
			return 530;	//gold /2

		}
		return -1;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.testIdle();
		soundtick++;
		boolean flag1 = false;
		int reqtemp = this.getReqTemps(inv[0]);
		this.getLiq(world, x, y, z, meta);
		this.drawFuel(world, x, y, z, meta);
		if (tickcount >= 20) {
			this.heatAmbient(world, x, y, z, meta);
			tickcount = 0;
		}
		if (this.canSmelt()) {
			if (soundtick >= 18 && temperature > reqtemp && reqtemp != -1) {
				soundtick = 0;
				SoundRegistry.playSoundAtBlock(SoundRegistry.PULSEJET, world, x, y, z, 1, 1);
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("$$");
			if (!flag2)
				this.getFuel(world, x, y, z, meta);
			this.updateTemperature(world, x, y, z, meta);
		}

		tickcount++;
		tickcount2++;
		if (temperature > reqtemp && reqtemp != -1)
			smelttick++;
		else
			smelttick = 0;
		if (smelttick < SMELTTICKS && !flag2)
			return;
		flag2 = true;
		smelttick = 0;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));
		if (!worldObj.isRemote) {
			flag1 = true;
			if (this.canSmelt()) {
				pulseFurnaceCookTime++;
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
				if (pulseFurnaceCookTime >= 20) {
					pulseFurnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
					//flag2 = false;
				}
			}
			else
				pulseFurnaceCookTime = 0;
		}
		if (flag1)
			this.onInventoryChanged();
	}

	/** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
	private boolean canSmelt() {
		this.getPowerBelow();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", power));
		if (power <= 0 || omega < MINSPEED)
			return false;
		if (inv[0] == null)
			return false;
		if (this.hasScrap()) {
			if (inv[0].stackSize < 9 && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage() && inv[0].itemID == ItemStacks.scrap.itemID)
				return false;
			if (inv[2] != null) {
				if (inv[2].itemID != this.getCraftedScrapIngot().itemID || inv[2].getItemDamage() != this.getCraftedScrapIngot().getItemDamage())
					return false;
				if (inv[2].stackSize >= this.getInventoryStackLimit() || inv[2].stackSize >= inv[2].getMaxStackSize())
					return false;
			}
			return true;
		}
		//if (this.fuelLevel <= 0)
		//return false;
		int mintemp = this.getReqTemps(inv[0]);
		//if (mintemp == -1 || mintemp > this.temperature)
		//	return false;

		ItemStack itemstack = RecipesPulseFurnace.smelting().getSmeltingResult(inv[0]);
		if (itemstack == null)
			return false;
		if (inv[2] == null)
			return true;
		if (!inv[2].isItemEqual(itemstack))
			return false;
		if (inv[2].stackSize < this.getInventoryStackLimit() && inv[2].stackSize < inv[2].getMaxStackSize())
			return true;
		return inv[2].stackSize < itemstack.getMaxStackSize();
	}

	/** Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack */
	public void smeltItem() {
		if (!this.canSmelt())
			return;
		if (this.hasScrap()) {
			this.smeltScrap();
			return;
		}
		flag2 = false;
		this.smeltHeat();
		ItemStack itemstack = RecipesPulseFurnace.smelting().getSmeltingResult(inv[0]);
		if (inv[2] == null)
			inv[2] = itemstack.copy();
		else if (inv[2].itemID == itemstack.itemID)
			inv[2].stackSize += this.getSmeltNumber(inv[2]);
		//  if (inv[0].getItem().func_46056_k())
		//   inv[0] = new ItemStack(inv[0].getItem().setFull3D());
		//  else
		inv[0].stackSize--;
		if (inv[0].stackSize <= 0)
			inv[0] = null;
	}

	private void smeltScrap() {
		int size = 1;
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage())
			size = 9;
		inv[0].stackSize -= size;
		ItemStack i = this.getCraftedScrapIngot();
		ReikaInventoryHelper.addOrSetStack(i.itemID, 1, i.getItemDamage(), inv, 2);
		if (inv[0].stackSize <= 0)
			inv[0] = null;
		RotaryAchievements.RECYCLE.triggerAchievement(this.getPlacer());
	}

	private ItemStack getCraftedScrapIngot() {
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage())
			return ItemStacks.steelingot;
		if (inv[0].itemID == ItemStacks.ironscrap.itemID && inv[0].getItemDamage() == ItemStacks.ironscrap.getItemDamage())
			return new ItemStack(Item.ingotIron);
		return null;
	}

	private boolean hasScrap() {
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage()) {
			return true;
		}
		if ((inv[0].itemID == ItemStacks.ironscrap.itemID && inv[0].getItemDamage() == ItemStacks.ironscrap.getItemDamage())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelPulseFurnace();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PULSEJET.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		if (slot != 0)
			return false;
		return RecipesPulseFurnace.smelting().getSmeltingResult(is) != null;
	}

	@Override
	public int getThermalDamage() {
		return (temperature)/100;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canSmelt())
			return 15;
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return side != EnumLook.DOWN;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	public void overheat(World world, int x, int y, int z) {
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 17, true, 1.5F, false, ConfigRegistry.BLOCKDAMAGE.getState(), 12F);
	}
}

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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.LiquidRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;

public class TileEntityLiquidConverter extends RotaryCraftTileEntity implements PipeConnector, ITankContainer, GuiController {

	public static int CAPACITY = 240*LiquidContainerRegistry.BUCKET_VOLUME;

	private LiquidTank tank = new LiquidTank(CAPACITY);

	private boolean isToForge;

	private int waterLevel;
	private int lavaLevel;
	private int lubeLevel;
	private int jetFuelLevel;

	private int oldlube;
	private int oldfuel;
	private int oldwater;
	private int oldlava;

	private LiquidRegistry liquid = LiquidRegistry.WATER;

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
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
		return MachineRegistry.LIQUIDCONVERTER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public void setLiquid(LiquidRegistry l) {
		liquid = l;
	}

	public LiquidRegistry getLiquid() {
		return liquid;
	}

	public LiquidStack getLiquidControlStack() {
		return liquid.getForgeLiquid();
	}

	public boolean isToForge() {
		return isToForge;
	}

	public int getToForgeAsInt() {
		if (isToForge)
			return 1;
		else
			return 0;
	}

	public void setToForge(boolean is) {
		isToForge = is;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

		if (tank.getLiquid() != null && tank.getLiquid().amount <= 0)
			tank.setLiquid(null);

		ForgeDirection[] dirs = ForgeDirection.values();
		if (isToForge) {
			for (int i = 0; i < dirs.length; i++) {
				switch(liquid) {
				case WATER:
					this.transferPipe(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ, 9);
					break;
				case LAVA:
					this.transferPipe(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ, 11);
					break;
				case LUBRICANT:
					this.transferHose(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
					break;
				case JETFUEL:
					this.transferFuel(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
					break;
				}
			}
			switch(liquid) {
			case WATER:
				if (waterLevel > 0) {
					tank.fill(LiquidDictionary.getLiquid("Water", waterLevel), true);
					waterLevel = 0;
				}
				break;
			case LAVA:
				if (lavaLevel > 0) {
					tank.fill(LiquidDictionary.getLiquid("Lava", lavaLevel), true);
					lavaLevel = 0;
				}
				break;
			case LUBRICANT:
				if (lubeLevel >= 0) {
					tank.fill(LiquidDictionary.getLiquid("Lubricant", lubeLevel), true);
					lubeLevel = 0;
				}
				break;
			case JETFUEL:
				if (jetFuelLevel >= 0) {
					tank.fill(LiquidDictionary.getLiquid("Jet Fuel", jetFuelLevel), true);
					jetFuelLevel = 0;
				}
				break;
			}
			if (tank.getLiquid() != null && tank.getLiquid().amount > 0) {
				for (int i = 0; i < dirs.length; i++) {
					TileEntity te = world.getBlockTileEntity(x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
					if (te instanceof ITankContainer) {
						int liq = ((ITankContainer) te).fill(dirs[i].getOpposite(), tank.getLiquid(), true);
						tank.drain(liq, true);
						if (tank.getLiquid() == null || tank.getLiquid().amount <= 0) {
							break;
						}
					}
				}
			}
		}
		else {
			if (tank.getLiquid() != null) {
				int liq = tank.getLiquid().amount;
				switch(liquid) {
				case WATER:
					liq = ReikaMathLibrary.extrema(liq, CAPACITY-waterLevel, "absmin");
					if (tank.getLiquid().isLiquidEqual(LiquidDictionary.getCanonicalLiquid("Water"))) {
						waterLevel += liq;
						tank.drain(liq, true);
					}
					break;
				case LAVA:
					liq = ReikaMathLibrary.extrema(liq, CAPACITY-lavaLevel, "absmin");
					if (tank.getLiquid().isLiquidEqual(LiquidDictionary.getCanonicalLiquid("Lava"))) {
						lavaLevel += liq;
						tank.drain(liq, true);
					}
					break;
				case LUBRICANT:
					liq = ReikaMathLibrary.extrema(liq, CAPACITY-lubeLevel, "absmin");
					if (tank.getLiquid().isLiquidEqual(LiquidDictionary.getCanonicalLiquid("Lubricant"))) {
						lubeLevel += liq;
						tank.drain(liq, true);
					}
					break;
				case JETFUEL:
					liq = ReikaMathLibrary.extrema(liq, CAPACITY-jetFuelLevel, "absmin");
					if (tank.getLiquid().isLiquidEqual(LiquidDictionary.getCanonicalLiquid("Jet Fuel"))) {
						jetFuelLevel += liq;
						tank.drain(liq, true);
					}
					break;
				}
			}
			for (int i = 0; i < dirs.length; i++) {
				switch(liquid) {
				case WATER:
					this.addToPipe(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ, 9);
					break;
				case LAVA:
					this.addToPipe(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ, 11);
					break;
				case LUBRICANT:
					this.addToHose(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
					break;
				case JETFUEL:
					this.addToFuel(world, x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
					break;
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		lubeLevel = NBT.getInteger("lube");
		waterLevel = NBT.getInteger("water");
		lavaLevel = NBT.getInteger("lava");
		jetFuelLevel = NBT.getInteger("fuel");

		liquid = LiquidRegistry.liquidList[NBT.getInteger("liq")];

		isToForge = NBT.getBoolean("forge");

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

		if (NBT.hasKey("internalLiquid")) {
			tank.setLiquid(new LiquidStack(NBT.getInteger("liquidId"), NBT.getInteger("internalLiquid")));
		}
		else if (NBT.hasKey("tank")) {
			tank.setLiquid(LiquidStack.loadLiquidStackFromNBT(NBT.getCompoundTag("tank")));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setInteger("lube", lubeLevel);
		NBT.setInteger("water", waterLevel);
		NBT.setInteger("lava", lavaLevel);
		NBT.setInteger("fuel", jetFuelLevel);

		NBT.setInteger("liq", liquid.ordinal());
		NBT.setBoolean("forge", isToForge);

		if (tank.getLiquid() != null) {
			NBT.setTag("tank", tank.getLiquid().writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return this.fill(0, resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		if (isToForge)
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.drain(0, maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if (!isToForge)
			return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[]{tank};
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		return tank;
	}

	private void addToPipe(World world, int x, int y, int z, int liq) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z);
			if (tile.liquidID == liq) {
				if (liq == 9) {
					tile.liquidLevel += waterLevel*RotaryConfig.MILLIBUCKET;
					waterLevel = 0;
				}
				else if (liq == 1) {
					tile.liquidLevel += lavaLevel*RotaryConfig.MILLIBUCKET;
					lavaLevel = 0;
				}
			}
			else if (tile.liquidID == -1) {
				tile.liquidID = liq;
				if (liq == 9) {
					tile.liquidLevel += waterLevel*RotaryConfig.MILLIBUCKET;
					waterLevel = 0;
				}
				else if (liq == 1) {
					tile.liquidLevel += lavaLevel*RotaryConfig.MILLIBUCKET;
					lavaLevel = 0;
				}
			}
		}
	}

	private void addToHose(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z);
			tile.lubricant += lubeLevel*RotaryConfig.MILLIBUCKET;
			lubeLevel = 0;
		}
	}

	private void addToFuel(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z);
			tile.fuel += jetFuelLevel*RotaryConfig.MILLIBUCKET;
			jetFuelLevel = 0;
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

	public LiquidRegistry getNextLiquid() {
		if (liquid.ordinal() == LiquidRegistry.liquidList.length-1)
			return LiquidRegistry.liquidList[0];
		else
			return LiquidRegistry.liquidList[liquid.ordinal()+1];
	}

}

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
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.EnumLook;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;

public class TileEntityPressureBalancer extends RotaryCraftTileEntity implements PipeConnector, ITankContainer {

	public static final int CAPACITY = 2400*LiquidContainerRegistry.BUCKET_VOLUME;

	private int waterLevel;
	private int lavaLevel;
	private int lubeLevel;
	private int jetFuelLevel;
	private LiquidStack[] liq = {
			LiquidDictionary.getLiquid("Water", 0), LiquidDictionary.getLiquid("Lava", 0),
			LiquidDictionary.getLiquid("Jet Fuel", 0), LiquidDictionary.getLiquid("Lubricant", 0)
	};

	private LiquidTank storage;

	private int oldlube;
	private int oldfuel;
	private int oldwater;
	private int oldlava;

	public TileEntityPressureBalancer() {
		super();
		storage = new LiquidTank(liq[this.getBlockMetadata()].copy(), CAPACITY);
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
		return MachineRegistry.BALANCER.ordinal();
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
		this.getLiquids(world, x, y, z, meta);
		this.convertLiquids();
		//ReikaJavaLibrary.pConsoleSideOnly(storage.getLiquid().itemID, Side.SERVER);
		//storage[0].fill(new LiquidStack(Block.waterStill.blockID, LiquidContainerRegistry.BUCKET_VOLUME, 0), true);
		//storage[1].fill(new LiquidStack(Block.lavaStill.blockID, LiquidContainerRegistry.BUCKET_VOLUME, 0), true);
		//storage[2].fill(new LiquidStack(RotaryCraft.jetFuel, LiquidContainerRegistry.BUCKET_VOLUME), true);
		//storage[3].fill(new LiquidStack(RotaryCraft.lubricant, LiquidContainerRegistry.BUCKET_VOLUME), true);
	}

	private void getLiquids(World world, int x, int y, int z, int meta) {
		this.transferHose(world, x, y, z);
		this.transferFuel(world, x, y, z);
		this.transferPipe(world, x, y, z);
	}

	public void transferPipe(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
			this.interPipe(tile);
		}
	}

	private void interPipe(TileEntityPipe tile) {
		if (tile != null) {
			if (tile.liquidID == 9) {
				if (tile.liquidLevel > waterLevel && tile.liquidLevel > 0) {
					oldwater = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-waterLevel)/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+(oldwater-waterLevel)/4+1, 0, "max");
				}
			}
			if (tile.liquidID == 11) {
				if (tile.liquidLevel > lavaLevel && tile.liquidLevel > 0) {
					oldlava = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-lavaLevel)/4-1, 0, "max");
					lavaLevel = ReikaMathLibrary.extrema(lavaLevel+(oldlava-lavaLevel)/4+1, 0, "max");
				}
			}
		}
	}

	public void transferHose(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x+1, y, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x-1, y, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y+1, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y-1, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z+1);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z-1);
			this.interHose(tile);
		}
	}

	public void transferFuel(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x+1, y, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x-1, y, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y+1, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y-1, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z+1);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z-1);
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

	private void convertLiquids() {
		if (jetFuelLevel > 0) {
			jetFuelLevel--;
			storage.fill(new LiquidStack(RotaryCraft.jetFuel.itemID, LiquidContainerRegistry.BUCKET_VOLUME, 0), true);
			//jetfuel.amount++;
		}
		if (waterLevel > 0) {
			waterLevel--;
			storage.fill(new LiquidStack(Block.waterStill.blockID, LiquidContainerRegistry.BUCKET_VOLUME, 0), true);
			//water.amount++;
		}
		if (lavaLevel > 0) {
			lavaLevel--;
			storage.fill(new LiquidStack(Block.lavaStill.blockID, LiquidContainerRegistry.BUCKET_VOLUME, 0), true);
			//lava.amount++;
		}
		if (lubeLevel > 0) {
			lubeLevel--;
			storage.fill(new LiquidStack(RotaryCraft.lubricant.itemID, LiquidContainerRegistry.BUCKET_VOLUME, 0), true);
			//lubricant.amount++;
		}
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
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
		return this.drain(0, maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankindex, int maxDrain, boolean doDrain) {
		return storage.drain(maxDrain, doDrain);
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[]{storage};
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if (type.equals(storage.getLiquid()))
			return storage;
		return null;
	}

	public int getWaterScaled(int px) {
		return storage.getLiquid().amount*px*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
		//return px*waterLevel*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
	}

	public int getLavaScaled(int px) {
		return storage.getLiquid().amount*px*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
		//return px*lavaLevel*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
	}

	public int getFuelScaled(int px) {
		return storage.getLiquid().amount*px*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
		//return px*jetFuelLevel*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
	}

	public int getLubeScaled(int px) {
		return storage.getLiquid().amount*px*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
		//return px*lubeLevel*LiquidContainerRegistry.BUCKET_VOLUME/CAPACITY;
	}

}

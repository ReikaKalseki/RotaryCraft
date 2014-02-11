/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public class TorqueUsage {
	public static int torque;
	private static List<TileEntity> TEList = new ArrayList<TileEntity>(); //list to keep track of already considered shaft components and avoid loops
	private static List<Boolean> TECheck = new ArrayList<Boolean>();
	private static List<Double> TERatio = new ArrayList<Double>();
	private static TileEntity tile;
	private static TileEntity lasttile;

	public static int getTorque(World world, int x, int y, int z) {
		tile = world.getBlockTileEntity(x, y, z);
		lasttile = tile;
		TEList = new ArrayList<TileEntity>();
		TECheck = new ArrayList<Boolean>();
		TERatio = new ArrayList<Double>();
		torque = 0;
		addToList(tile, 1);
		TECheck.set(0, true);
		addToList(((TileEntityIOMachine) tile).getOutput());
		while (TECheck.contains(false)) {
			tile = TEList.get(TECheck.lastIndexOf(false));
			recursiveFind(world, tile);
		}
		if (torque < 0)
			torque = 0;
		return torque;
	}

	private static void recursiveFind(World world, TileEntity tile) {
		lasttile = tile;
		TECheck.set(TEList.indexOf(tile), true);
		if (tile instanceof TileEntityTransmissionMachine) { //true if the considered tile is a Transmission tile and is getting power from an already examined block
			if (tile instanceof TileEntitySplitter) { //check if splitter
				if (((TileEntitySplitter)tile).getBlockMetadata() < 8) { //check if merge mode or split mode (true if in merge mode)
					if (TEList.contains(world.getBlockTileEntity(((TileEntitySplitter)tile).readx, tile.yCoord, ((TileEntitySplitter)tile).readz))) { //reads both inputs and if something is connected to the other input, it adds it as a negative torque "consumption"
						TileEntity sc = world.getBlockTileEntity(((TileEntitySplitter)tile).readx, tile.yCoord, ((TileEntitySplitter)tile).readz);
						if (((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque > 0) torque -= ((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque;
					}
					else if (TEList.contains(world.getBlockTileEntity(((TileEntitySplitter)tile).readx2, tile.yCoord, ((TileEntitySplitter)tile).readz2))) {
						TileEntity sc = world.getBlockTileEntity(((TileEntitySplitter)tile).readx2, tile.yCoord, ((TileEntitySplitter)tile).readz2);
						if (((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque > 0) torque -= ((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque;
					}
					TileEntity di = world.getBlockTileEntity(((TileEntitySplitter)tile).writex, tile.yCoord, ((TileEntitySplitter)tile).writez);
					if (!TEList.contains(di) && isPoweredFrom(world, di)) {
						addToList(di); //records the following tile inside the list
					}
				}
				else {
					TileEntity di = world.getBlockTileEntity(((TileEntitySplitter)tile).writeinline[0], tile.yCoord, ((TileEntitySplitter)tile).writeinline[1]); //records both outputs
					TileEntity di2 = world.getBlockTileEntity(((TileEntitySplitter)tile).writebend[0], tile.yCoord, ((TileEntitySplitter)tile).writebend[1]);
					if (!TEList.contains(di) && isPoweredFrom(world, di)) { //calls the recursion first with one output, then with the other
						addToList(di);
					}
					if (!TEList.contains(di2) && isPoweredFrom(world, di2)) {
						addToList(di2);
					}
				}
			}
			else if (tile instanceof TileEntityFlywheel) {
				torque += TERatio.get(TEList.indexOf(tile))*((TileEntityFlywheel) tile).oppTorque;
			}
			else if (tile instanceof TileEntityClutch) {
				if (world.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord)) {
					if (!TEList.contains(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
						addToList(((TileEntityIOMachine) tile).getOutput());
					}
				}
			}
			else if (tile instanceof TileEntityGearbox) {
				if (!TEList.contains(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
					if (((TileEntityGearbox) tile).reduction) {
						addToList(((TileEntityIOMachine) tile).getOutput(), TERatio.get(TEList.indexOf(tile))/((TileEntityGearbox) tile).ratio);
					}
					else {
						addToList(((TileEntityIOMachine) tile).getOutput(), TERatio.get(TEList.indexOf(tile))*((TileEntityGearbox) tile).ratio);
					}
				}
			}
			else if (tile instanceof TileEntityAdvancedGear) {
				switch(((TileEntityAdvancedGear) tile).getGearType()) {
				case WORM:
					if (!TEList.contains(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
						addToList(((TileEntityIOMachine) tile).getOutput(), TERatio.get(TEList.indexOf(tile))/16);
					}
				case CVT:
					if (!TEList.contains(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
						if (((TileEntityAdvancedGear) tile).ratio > 0) {
							addToList(((TileEntityIOMachine) tile).getOutput(), TERatio.get(TEList.indexOf(tile))*((TileEntityAdvancedGear) tile).ratio);
						}
						else {
							addToList(((TileEntityIOMachine) tile).getOutput(), TERatio.get(TEList.indexOf(tile))/-((TileEntityAdvancedGear) tile).ratio);
						}
					}
				case COIL:
					double added = Math.sqrt(2*((TileEntityAdvancedGear)tile).getEnergy())/20;
					if (((TileEntityAdvancedGear) tile).isBedrockCoil())
						added *= 16;
					torque += added;
				}
			}
			else if (tile instanceof TileEntityShaft) {
				if (((TileEntityShaft) tile).isCross()) {
					TileEntity di1 = world.getBlockTileEntity(((TileEntityShaft) tile).readx, tile.yCoord, ((TileEntityShaft) tile).readz);
					TileEntity di2 = world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord, ((TileEntityShaft) tile).writez2);
					if (TEList.contains(di1) && TECheck.get(TEList.indexOf(di1))) {
						if (!TEList.contains(world.getBlockTileEntity(((TileEntityShaft) tile).writex, tile.yCoord, ((TileEntityShaft) tile).writez)) && isPoweredFrom(world, world.getBlockTileEntity(((TileEntityShaft) tile).writex, tile.yCoord, ((TileEntityShaft) tile).writez))); {
							addToList(world.getBlockTileEntity(((TileEntityShaft) tile).writex, tile.yCoord, ((TileEntityShaft) tile).writez));
						}
					}
					else if (TEList.contains(di2) && TECheck.get(TEList.indexOf(di2))) {
						if (!TEList.contains(world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord, ((TileEntityShaft) tile).writez2)) && isPoweredFrom(world, world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord, ((TileEntityShaft) tile).writez2))); {
							addToList(world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord, ((TileEntityShaft) tile).writez2));
						}
					}
				}
				else {
					if (!TEList.contains(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) { //if it's anything else, it just gets its current output and then calls the recursion
						addToList(((TileEntityIOMachine) tile).getOutput());
					}
				}
			}
			else {
				if (!TEList.contains(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) { //if it's anything else, it just gets its current output and then calls the recursion
					addToList(((TileEntityIOMachine) tile).getOutput());
				}
			}
		}
		if (tile instanceof TileEntityPowerReceiver) { //if the tile is a power Receiver, it stores its minimum torque requirement
			if (tile instanceof TileEntityExtractor) { //if it's an extractor, it stores the torque requirement of the most demanding WORKING stage
				int rtorque = ((TileEntityPowerReceiver)tile).torque;
				int mintorque = 0;
				for(int i = 0; i < 4; i++) {
					if (((TileEntityPowerReceiver)tile).machine.getMinTorque(i) <= rtorque) {
						mintorque = (Math.max(mintorque, ((TileEntityPowerReceiver) tile).machine.getMinTorque(i)));
					}
				}
				torque += TERatio.get(TEList.indexOf(tile))*mintorque;
			}
			else if (tile instanceof TileEntityBeltHub) {
				if (!((TileEntityBeltHub) tile).isEmitting()) {
					TileEntity di = world.getBlockTileEntity(((TileEntityBeltHub) tile).getTargetX(),((TileEntityBeltHub) tile).getTargetY(),((TileEntityBeltHub) tile).getTargetZ());
					if (di != null) {
						addToList(di);
						TECheck.set(TEList.indexOf(di), true);
						TileEntity dii = world.getBlockTileEntity(((TileEntityBeltHub)di).writex, ((TileEntityBeltHub)di).writey, ((TileEntityBeltHub)di).writez);
						if (!TEList.contains(dii) && isPoweredFrom(world, dii)) {
							lasttile=di;
							addToList(dii);
						}
					}
				}
			}
			else
				torque += Math.max(1, TERatio.get(TEList.indexOf(tile))*((TileEntityPowerReceiver) tile).MINTORQUE);
		}
	}

	private static void addToList(TileEntity tile) {
		if (!TEList.contains(tile) && tile != null) {
			TEList.add(tile);
			TECheck.add(false);
			TERatio.add(TERatio.get(TEList.indexOf(lasttile)));
		}
	}

	private static void addToList(TileEntity tile, double ratio) {
		if (!TEList.contains(tile) && tile != null) {
			TEList.add(tile);
			TECheck.add(false);
			TERatio.add(ratio);
		}
	}

	private static boolean isPoweredFrom(World world, TileEntity tile) {
		boolean isPoweredFrom = false;
		if (tile instanceof TileEntityIOMachine)
			isPoweredFrom = (TEList.contains(((TileEntityIOMachine) tile).getInput()) || TEList.contains(world.getBlockTileEntity(((TileEntityIOMachine) tile).readx, tile.yCoord, ((TileEntityIOMachine) tile).readz)) || TEList.contains(world.getBlockTileEntity(((TileEntityIOMachine) tile).readx2, tile.yCoord, ((TileEntityIOMachine) tile).readz2)));
		return isPoweredFrom;
	}
}

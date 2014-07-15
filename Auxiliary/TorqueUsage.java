/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBusController;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class TorqueUsage {
	private static int torque;
	private static Map<TileEntity, Boolean> TEMap = new HashMap<TileEntity, Boolean>();
	private static Map<TileEntity, Double> TEMapR = new HashMap<TileEntity, Double>();
	private static TileEntityFlywheel readingTile;

	public static int getTorque(TileEntityFlywheel te) {
		readingTile = te;
		TEMap.clear();
		TEMapR.clear();
		torque = 0;
		addToList(readingTile, readingTile, 1);
		TEMap.put(readingTile, true);
		addToList(((TileEntityIOMachine) readingTile).getOutput(), readingTile);
		while (TEMap.containsValue(false)) {
			Multimap <Boolean, TileEntity> invert = ArrayListMultimap.create();
			Multimaps.invertFrom(Multimaps.forMap(TEMap), invert);
			recursiveFind(te.worldObj, (TileEntity)invert.get(false).toArray()[0], te);
		}
		TEMap.clear();
		if (torque < 0)
			torque = 0;
		return torque;
	}

	private static void recursiveFind(World world, TileEntity tile, TileEntityFlywheel reader) {
		TEMap.put(tile,true);
		if (tile instanceof TileEntityTransmissionMachine) { //true if the considered tile is a Transmission tile and is getting power from an already examined block
			if (tile instanceof TileEntitySplitter) { //check if splitter
				TileEntitySplitter spl = (TileEntitySplitter)tile;
				if (!spl.isSplitting()) { //check if merge mode or split mode (true if in merge mode)
					TileEntity read = spl.getReadTileEntity();
					TileEntity read2 = spl.getReadTileEntity2();
					TileEntity write = spl.getWriteTileEntity();
					if (TEMap.containsKey(read)) { //reads both inputs and if something is connected to the other input, it adds it as a negative torque "consumption"
						TileEntity sc = read;
						TileEntityIOMachine scio = (TileEntityIOMachine)sc;
						if (spl.torque - scio.torque > 0)
							torque -= spl.torque - scio.torque;
					}
					else if (TEMap.containsKey(read2)) {
						TileEntity sc = read2;
						TileEntityIOMachine scio = (TileEntityIOMachine)sc;
						if (spl.torque - scio.torque > 0)
							torque -= spl.torque - scio.torque;
					}
					TileEntity di = write;
					if (!TEMap.containsKey(di) && isPoweredFrom(world, di)) {
						addToList(di, tile); //records the following tile inside the list
					}
				}
				else {
					TileEntity di = world.getBlockTileEntity(spl.writeinline[0], tile.yCoord, spl.writeinline[1]); //records both outputs
					TileEntity di2 = world.getBlockTileEntity(spl.writebend[0], tile.yCoord, spl.writebend[1]);
					if (!TEMap.containsKey(di) && isPoweredFrom(world, di)) { //calls the recursion first with one output, then with the other
						addToList(di, tile);
					}
					if (!TEMap.containsKey(di2) && isPoweredFrom(world, di2)) {
						addToList(di2, tile);
					}
				}
			}
			else if (tile instanceof TileEntityFlywheel) {
				torque += TEMapR.get(tile)*((TileEntityFlywheel) tile).getOppTorque(readingTile);
			}
			else if (tile instanceof TileEntityClutch) {
				TileEntityClutch clu = (TileEntityClutch)tile;
				if (world.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord)) {
					if (!TEMap.containsKey(clu.getOutput()) && isPoweredFrom(world, clu.getOutput())) {
						addToList(clu.getOutput(), tile);
					}
				}
			}
			else if (tile instanceof TileEntityGearbox) {
				TileEntityGearbox gbx = (TileEntityGearbox)tile;
				if (!TEMap.containsKey(gbx.getOutput()) && isPoweredFrom(world, gbx.getOutput())) {
					if (((TileEntityGearbox) tile).reduction) {
						addToList(gbx.getOutput(), tile, TEMapR.get(tile)/((TileEntityGearbox) tile).getRatio());
					}
					else {
						addToList(gbx.getOutput(), tile, TEMapR.get(tile)*((TileEntityGearbox) tile).getRatio());
					}
				}
			}
			else if (tile instanceof TileEntityAdvancedGear) {
				TileEntityAdvancedGear adv = (TileEntityAdvancedGear)tile;
				switch(adv.getGearType()) {
				case WORM:
					if (!TEMap.containsKey(adv.getOutput()) && isPoweredFrom(world, adv.getOutput())) {
						addToList(adv.getOutput(), tile, TEMapR.get(tile)/16);
					}
					break;
				case CVT:
					if (!TEMap.containsKey(adv.getOutput()) && isPoweredFrom(world, adv.getOutput())) {
						if (adv.getRatio() > 0) {
							addToList(adv.getOutput(), tile, TEMapR.get(tile)*adv.getRatio());
						}
						else {
							addToList(adv.getOutput(), tile, TEMapR.get(tile)/-adv.getRatio());
						}
					}
					break;
				case COIL:
					double amt = Math.sqrt(2*adv.getEnergy());
					if (adv.isBedrockCoil())
						amt *= 16;
					torque += amt;
					break;
				case HIGH:
					if (!TEMap.containsKey(adv.getOutput()) && isPoweredFrom(world, adv.getOutput())) {
						double D = adv.torquemode ? 256D : 1/256D;
						addToList(adv.getOutput(), tile, TEMapR.get(tile)/D);
					}
				}
			}
			else if (tile instanceof TileEntityShaft) {
				TileEntityShaft sha = (TileEntityShaft)tile;
				if (((TileEntityShaft)tile).isCross()) {
					TileEntity di1 = sha.getReadTileEntity();
					TileEntity di2 = sha.getReadTileEntity2();
					TileEntity write = sha.getWriteTileEntity();
					TileEntity write2 = sha.getWriteTileEntity2();
					if (TEMap.containsKey(di1) && TEMap.get(di1) == true) {
						if (!TEMap.containsKey(write)
								&& isPoweredFrom(world, write)) {
							addToList(write, tile);
						}
					}
					else if (TEMap.containsKey(di2) && TEMap.get(di2) == true) {
						if (!TEMap.containsKey(write2)
								&& isPoweredFrom(world, write2)) {
							addToList(write2, tile);
						}
					}
				}
				else {
					if (!TEMap.containsKey(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) { //if it's anything else, it just gets its current output and then calls the recursion
						addToList(((TileEntityIOMachine) tile).getOutput(), tile);
					}
				}
			}
			else {
				if (!TEMap.containsKey(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) { //if it's anything else, it just gets its current output and then calls the recursion
					addToList(((TileEntityIOMachine) tile).getOutput(), tile);
				}
			}
		}
		if (tile instanceof TileEntityPowerReceiver) { //if the tile is a power Receiver, it stores its minimum torque requirement
			if (tile instanceof TileEntityExtractor) { //if it's an extractor, it stores the torque requirement of the most demanding WORKING stage
				TileEntityExtractor ex = (TileEntityExtractor)tile;
				int rtorque = ex.torque;
				int mintorque = 0;
				for(int i = 0; i < 4; i++) {
					if (ex.machine.getMinTorque(i) <= rtorque) {
						mintorque = (Math.max(mintorque, ex.machine.getMinTorque(i)));
					}
				}
				torque += TEMapR.get(tile)*mintorque;
			}
			else if (tile instanceof TileEntityBeltHub) {
				TileEntityBeltHub hub = (TileEntityBeltHub)tile;
				if (!hub.isEmitting) {
					TileEntity di = world.getBlockTileEntity(hub.getTargetX(), hub.getTargetY(), hub.getTargetZ());
					if (di != null && di instanceof TileEntityBeltHub) {
						TileEntityBeltHub h2 = (TileEntityBeltHub)di;
						TileEntity write = h2.getWriteTileEntity();
						addToList(di, tile);
						TEMap.put(di, true);
						TileEntity dii = write;
						if (!TEMap.containsKey(dii) && isPoweredFrom(world, dii)) {
							addToList(dii, di);
						}
					}
				}
			}
			else if (tile instanceof TileEntityBusController){
				manageBus(world, (TileEntityBusController)tile);
			}
			else
				torque += Math.max(TEMapR.get(tile)*((TileEntityPowerReceiver) tile).MINTORQUE, 1);
		}
		if (tile instanceof ShaftPowerReceiver) {
			torque += ((ShaftPowerReceiver)tile).getMinTorque(reader.torque);
		}
	}

	private static void manageBus(World world, TileEntityBusController tile) {
		ShaftPowerBus bus = tile.getBus();
		List<TileEntityPowerBus> blocks = bus.getBlocks();
		for (int i = 0; i < blocks.size(); i++) {
			TileEntityPowerBus te = null;
			if (blocks.get(i) instanceof TileEntityPowerBus) {
				te = blocks.get(i);
				for (int k = 2; k < 6; k++) {
					ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[k];
					if (te.canOutputToSide(dir)) {
						TileEntity out = world.getBlockTileEntity(te.xCoord+dir.offsetX, te.yCoord+dir.offsetY, te.zCoord+dir.offsetZ);
						if (out != null && out instanceof TileEntityIOMachine) {
							TileEntityIOMachine io = (TileEntityIOMachine)out;
							TileEntity read = io.getReadTileEntity();
							TileEntity read2 = io.getReadTileEntity2();
							TileEntity read3 = io.getReadTileEntity3();
							TileEntity read4 = io.getReadTileEntity4();
							if ((io.getInput() == te || read == te || read2 == te || read3 == te || read4 == te)) {
								double ratio = te.getAbsRatio(dir);
								if (!te.isSideSpeedMode(dir))
									ratio = 1D/ratio;
								addToList(out, tile, ratio*TEMapR.get(tile));
							}
						}
					}
				}
			}
		}
	}

	private static void addToList(TileEntity tile, TileEntity lasttile) {
		if (!TEMap.containsKey(tile) && tile != null) {
			if (TEMapR.get(lasttile)!= null)
				TEMapR.put(tile,TEMapR.get(lasttile));
			else TEMapR.put(tile,1.0);
			TEMap.put(tile,false);
		}
	}

	private static void addToList(TileEntity tile, TileEntity lasttile, double ratio) {
		if (!TEMap.containsKey(tile) && tile != null) {
			TEMap.put(tile, false);
			TEMapR.put(tile, ratio);
		}
	}

	private static boolean isPoweredFrom(World world, TileEntity tile) {
		boolean isPoweredFrom = false;
		if (tile instanceof TileEntityIOMachine) {
			TileEntityIOMachine io = (TileEntityIOMachine)tile;
			TileEntity read = io.getReadTileEntity();
			TileEntity read2 = io.getReadTileEntity2();
			TileEntity read3 = io.getReadTileEntity3();
			TileEntity read4 = io.getReadTileEntity4();
			isPoweredFrom = (TEMap.containsKey(io.getInput()) || TEMap.containsKey(read) || TEMap.containsKey(read2) || TEMap.containsKey(read3) || TEMap.containsKey(read4));
		}
		return isPoweredFrom;
	}


	public static int recursiveCount(World world, TileEntity tile, int count) {
		count++;
		if (tile instanceof TileEntitySplitter) {
			TileEntitySplitter spl = (TileEntitySplitter)tile;
			TileEntity write = spl.getWriteTileEntity();
			TileEntity write2 = spl.getWriteTileEntity2();
			if (!spl.isSplitting()) {
				TileEntity di = write;
				if (di != null && di instanceof TileEntityIOMachine) {
					TileEntityIOMachine io = ((TileEntityIOMachine)di);
					TileEntity read = io.getReadTileEntity();
					TileEntity read2 = io.getReadTileEntity2();
					if (io.getInput() == tile || read == tile || read2 == tile) {
						count = recursiveCount(world, di, count);
					}
				}
			}
			else {
				TileEntity di = write;
				TileEntity di2 = write2;
				if (di != null && di instanceof TileEntityIOMachine) {
					TileEntityIOMachine io = ((TileEntityIOMachine)di);
					TileEntity read = io.getReadTileEntity();
					TileEntity read2 = io.getReadTileEntity2();
					if (io.getInput() == tile || read == tile || read2 == tile) {
						count = recursiveCount(world, di, count);
					}
				}
				if (di2 != null && di2 instanceof TileEntityIOMachine) {
					TileEntityIOMachine io = ((TileEntityIOMachine)di2);
					TileEntity read = io.getReadTileEntity();
					TileEntity read2 = io.getReadTileEntity2();
					if (io.getInput() == tile || read == tile || read == tile) {
						count = recursiveCount(world, di2, count);
					}
				}
			}
		}
		else if (tile instanceof TileEntityClutch) {
			if (world.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord)) {
				TileEntity di = ((TileEntityIOMachine) tile).getOutput();
				if (di != null && di instanceof TileEntityIOMachine) {
					if (((TileEntityIOMachine) di).getInput() == tile) {
						count = recursiveCount(world, di, count);
					}
				}
			}
		}
		else if (tile instanceof TileEntityBeltHub) {
			TileEntityBeltHub hub = (TileEntityBeltHub)tile;
			if (!((TileEntityBeltHub) tile).isEmitting) {
				TileEntity di = world.getBlockTileEntity(hub.getTargetX(), hub.getTargetY(),hub.getTargetZ());
				if (di != null) {
					TileEntityBeltHub h2 = (TileEntityBeltHub)di;
					TileEntity write = h2.getWriteTileEntity();
					TileEntity dii = write;
					if (dii != null && dii instanceof TileEntityIOMachine) {
						if (((TileEntityIOMachine) dii).getInput() == di) {
							count = recursiveCount(world, dii, count);
						}
					}
				}
			}
		}
		else {
			TileEntity di = ((TileEntityIOMachine) tile).getOutput();
			if (di != null && di instanceof TileEntityIOMachine) {
				if (((TileEntityIOMachine) di).getInput() == tile) {
					count = recursiveCount(world, di, count);
				}
			}
		}
		return count;
	}
}

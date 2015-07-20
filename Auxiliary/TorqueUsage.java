/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Instantiable.Data.Maps.TileEntityCache;
import Reika.RotaryCraft.API.Power.PowerAcceptor;
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

public class TorqueUsage {

	private static int torque;
	private static TileEntityCache<Boolean> TEMap = new TileEntityCache();
	private static TileEntityCache<Double> TEMapR = new TileEntityCache();
	private static TileEntityFlywheel readingTile;

	public static int getTorque(TileEntityFlywheel te) {
		readingTile = te;
		TEMap.clear();
		TEMapR.clear();
		torque = 0;
		addToList(readingTile, readingTile, 1);
		TEMap.put(readingTile, true);
		addToList(readingTile.getWriteTileEntity(), readingTile);
		while (TEMap.containsValue(false)) {
			//Multimap <Boolean, WorldLocation> invert = ArrayListMultimap.create();
			//Multimaps.invertFrom(Multimaps.forMap(TEMap), invert);
			//recursiveFind(te.worldObj, (TileEntity)invert.get(false).toArray()[0], te);

			List<WorldLocation> c = (List<WorldLocation>)TEMap.invert().get(false);
			if (c != null && !c.isEmpty())
				recursiveFind(te.worldObj, c.get(0).getTileEntity(), te);
		}
		TEMap.clear();
		if (torque < 0)
			torque = 0;
		return torque;
	}

	private static void recursiveFind(World world, TileEntity tile, TileEntityFlywheel reader) {
		TEMap.put(tile, true);
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
					TileEntity di = spl.getAdjacentTileEntity(spl.getWriteDirection()); //records both outputs
					TileEntity di2 = spl.getAdjacentTileEntity(spl.getWriteDirection2());
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
				if (clu.isOutputEnabled()) {
					if (!TEMap.containsKey(clu.getWriteTileEntity()) && isPoweredFrom(world, clu.getWriteTileEntity())) {
						addToList(clu.getWriteTileEntity(), tile);
					}
				}
			}
			else if (tile instanceof TileEntityGearbox) {
				TileEntityGearbox gbx = (TileEntityGearbox)tile;
				if (!TEMap.containsKey(gbx.getWriteTileEntity()) && isPoweredFrom(world, gbx.getWriteTileEntity())) {
					if (((TileEntityGearbox) tile).reduction) {
						addToList(gbx.getWriteTileEntity(), tile, TEMapR.get(tile)/((TileEntityGearbox) tile).getRatio());
					}
					else {
						addToList(gbx.getWriteTileEntity(), tile, TEMapR.get(tile)*((TileEntityGearbox) tile).getRatio());
					}
				}
			}
			else if (tile instanceof TileEntityAdvancedGear) {
				TileEntityAdvancedGear adv = (TileEntityAdvancedGear)tile;
				switch(adv.getGearType()) {
				case WORM:
					if (!TEMap.containsKey(adv.getWriteTileEntity()) && isPoweredFrom(world, adv.getWriteTileEntity())) {
						addToList(adv.getWriteTileEntity(), tile, TEMapR.get(tile)/16);
					}
					break;
				case CVT:
					if (!TEMap.containsKey(adv.getWriteTileEntity()) && isPoweredFrom(world, adv.getWriteTileEntity())) {
						if (adv.getRatio() > 0) {
							addToList(adv.getWriteTileEntity(), tile, TEMapR.get(tile)*adv.getRatio());
						}
						else {
							addToList(adv.getWriteTileEntity(), tile, TEMapR.get(tile)/-adv.getRatio());
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
					if (!TEMap.containsKey(adv.getWriteTileEntity()) && isPoweredFrom(world, adv.getWriteTileEntity())) {
						double D = adv.torquemode ? 256D : 1/256D;
						addToList(adv.getWriteTileEntity(), tile, TEMapR.get(tile)/D);
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
					if (!TEMap.containsKey(((TileEntityIOMachine) tile).getWriteLocation()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getWriteTileEntity())) { //if it's anything else, it just gets its current output and then calls the recursion
						addToList(((TileEntityIOMachine) tile).getWriteTileEntity(), tile);
					}
				}
			}
			else {
				//ReikaJavaLibrary.pConsole(((TileEntityIOMachine) tile).getWriteTileEntity()+" & "+!TEMap.containsKey(((TileEntityIOMachine) tile).getWriteTileEntity())+" & "+isPoweredFrom(world, ((TileEntityIOMachine) tile).getWriteTileEntity()));
				if (!TEMap.containsKey(((TileEntityIOMachine) tile).getWriteLocation()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getWriteTileEntity())) { //if it's anything else, it just gets its current output and then calls the recursion
					addToList(((TileEntityIOMachine) tile).getWriteTileEntity(), tile);
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
					TileEntity di = world.getTileEntity(hub.getTargetX(), hub.getTargetY(), hub.getTargetZ());
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
			else {
				int min = ((TileEntityPowerReceiver) tile).getMachine().isModConversionEngine() ? 1024 : 1;
				torque += Math.max(TEMapR.get(tile)*((TileEntityPowerReceiver) tile).MINTORQUE, min);
			}
		}
		if (tile instanceof PowerAcceptor) {
			torque += Math.max(((PowerAcceptor)tile).getMinTorque(reader.torque), 1);
		}
	}

	private static void manageBus(World world, TileEntityBusController tile) {
		ShaftPowerBus bus = tile.getBus();
		Collection<TileEntityPowerBus> blocks = bus.getBlocks();
		for (TileEntityPowerBus te : blocks) {
			for (int k = 2; k < 6; k++) {
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[k];
				if (te.canOutputToSide(dir)) {
					TileEntity out = world.getTileEntity(te.xCoord+dir.offsetX, te.yCoord+dir.offsetY, te.zCoord+dir.offsetZ);
					if (out != null && out instanceof TileEntityIOMachine) {
						TileEntityIOMachine io = (TileEntityIOMachine)out;
						TileEntity read = io.getReadTileEntity();
						TileEntity read2 = io.getReadTileEntity2();
						TileEntity read3 = io.getReadTileEntity3();
						TileEntity read4 = io.getReadTileEntity4();
						if ((io.getReadTileEntity() == te || read == te || read2 == te || read3 == te || read4 == te)) {
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

	private static void addToList(TileEntity tile, TileEntity lasttile) {
		if (tile != null && !TEMap.containsKey(tile)) {

			if (TEMapR.get(lasttile) != null)
				TEMapR.put(tile, TEMapR.get(lasttile));
			else
				TEMapR.put(tile, 1D);

			TEMap.put(tile, false);
		}
	}

	private static void addToList(TileEntity tile, TileEntity lasttile, double ratio) {
		if (!TEMap.containsKey(tile) && tile != null) {
			TEMap.put(tile, false);
			TEMapR.put(tile, ratio);
		}
	}

	private static boolean isPoweredFrom(World world, TileEntity tile) {
		if (tile instanceof TileEntityIOMachine) {
			TileEntityIOMachine io = (TileEntityIOMachine)tile;
			WorldLocation read = io.getReadLocation();
			WorldLocation read2 = io.getReadLocation2();
			WorldLocation read3 = io.getReadLocation3();
			WorldLocation read4 = io.getReadLocation4();
			return TEMap.containsKey(read) || TEMap.containsKey(read2) || TEMap.containsKey(read3) || TEMap.containsKey(read4);
		}
		else if (tile instanceof PowerAcceptor) {
			PowerAcceptor acc = (PowerAcceptor)tile;
			if (acc.isReceiving()) {
				for (int i = 0; i < 6; i++) {
					ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
					WorldLocation loc = new WorldLocation(tile).move(dir, 1);
					if (acc.canReadFrom(dir) && TEMap.containsKey(loc))
						return true;
				}
			}
		}
		return false;
	}

	public static int recursiveCount(TileEntityIOMachine tile) {
		return recursiveCount(tile, tile.getWriteDirection(), 0);
	}

	private static int recursiveCount(TileEntityIOMachine tile, ForgeDirection dir, int count) {
		Collection<TileEntity> c = new HashSet();
		HashSet<WorldLocation> pass = new HashSet();
		tile.getAllOutputs(c, dir.getOpposite());
		for (TileEntity te : c) {
			WorldLocation loc = new WorldLocation(te);
			if (pass.contains(loc))
				continue;
			pass.add(loc);
			if (te instanceof TileEntityIOMachine) {
				TileEntityIOMachine io = (TileEntityIOMachine)te;
				if (io.canTransmitPower()) {
					if (io.getWriteDirection() != null) {
						count = recursiveCount(io, io.getWriteDirection(), count);
					}
					if (io.getWriteDirection2() != null) {
						count = recursiveCount(io, io.getWriteDirection2(), count);
					}

					if (io instanceof TileEntityAdvancedGear) {
						if (((TileEntityAdvancedGear)io).getGearType().storesEnergy())
							count++;
					}
				}
			}
			else if (te instanceof PowerAcceptor) {
				count++;
			}
		}
		count++;
		return count;
	}
}

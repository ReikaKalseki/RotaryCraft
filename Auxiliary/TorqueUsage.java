package Reika.RotaryCraft.Auxiliary;

import java.util.HashMap;
import java.util.Map;

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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class TorqueUsage {
	private static int torque;
	private static Map<TileEntity, Boolean> TEMap = new HashMap<TileEntity, Boolean> ();
	private static Map<TileEntity, Double> TEMapR = new HashMap<TileEntity, Double> ();
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
			recursiveFind(te.worldObj, (TileEntity)invert.get(false).toArray()[0]);
		}
		TEMap.clear();
		if (torque < 0)
			torque = 0;
		return torque;
	}

	private static void recursiveFind(World world, TileEntity tile) {
		TEMap.put(tile,true);
		if (tile instanceof TileEntityTransmissionMachine) { //true if the considered tile is a Transmission tile and is getting power from an already examined block
			if (tile instanceof TileEntitySplitter) { //check if splitter
				if (((TileEntitySplitter)tile).getBlockMetadata() <= 7) { //check if merge mode or split mode (true if in merge mode)
					if (TEMap.containsKey(world.getBlockTileEntity(((TileEntitySplitter)tile).readx, tile.yCoord, ((TileEntitySplitter)tile).readz))) { //reads both inputs and if something is connected to the other input, it adds it as a negative torque "consumption"
						TileEntity sc = world.getBlockTileEntity(((TileEntitySplitter)tile).readx, tile.yCoord, ((TileEntitySplitter)tile).readz);
						if (((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque > 0)
							torque -= ((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque;
					}
					else if (TEMap.containsKey(world.getBlockTileEntity(((TileEntitySplitter)tile).readx2, tile.yCoord, ((TileEntitySplitter)tile).readz2))) {
						TileEntity sc = world.getBlockTileEntity(((TileEntitySplitter)tile).readx2, tile.yCoord, ((TileEntitySplitter)tile).readz2);
						if (((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque > 0)
							torque -= ((TileEntityTransmissionMachine) tile).torque - ((TileEntityIOMachine) sc).torque;
					}
					TileEntity di = world.getBlockTileEntity(((TileEntitySplitter)tile).writex, tile.yCoord, ((TileEntitySplitter)tile).writez);
					if (!TEMap.containsKey(di) && isPoweredFrom(world, di)) {
						addToList(di, tile); //records the following tile inside the list
					}
				}
				else {
					TileEntity di = world.getBlockTileEntity(((TileEntitySplitter)tile).writeinline[0], tile.yCoord, ((TileEntitySplitter)tile).writeinline[1]); //records both outputs
					TileEntity di2 = world.getBlockTileEntity(((TileEntitySplitter)tile).writebend[0], tile.yCoord, ((TileEntitySplitter)tile).writebend[1]);
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
				if (world.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord)) {
					if (!TEMap.containsKey(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
						addToList(((TileEntityIOMachine) tile).getOutput(), tile);
					}
				}
			}
			else if (tile instanceof TileEntityGearbox) {
				if (!TEMap.containsKey(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
					if (((TileEntityGearbox) tile).reduction) {
						addToList(((TileEntityIOMachine) tile).getOutput(), tile, TEMapR.get(tile)/((TileEntityGearbox) tile).ratio);
					}
					else {
						addToList(((TileEntityIOMachine) tile).getOutput(), tile, TEMapR.get(tile)*((TileEntityGearbox) tile).ratio);
					}
				}
			}
			else if (tile instanceof TileEntityAdvancedGear) {
				switch(((TileEntityAdvancedGear) tile).getGearType()) {
				case WORM:
					if (!TEMap.containsKey(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
						addToList(((TileEntityIOMachine) tile).getOutput(), tile, TEMapR.get(tile)/16);
					}
				case CVT:
					if (!TEMap.containsKey(((TileEntityIOMachine) tile).getOutput()) && isPoweredFrom(world, ((TileEntityIOMachine) tile).getOutput())) {
						if (((TileEntityAdvancedGear) tile).ratio>0) {
							addToList(((TileEntityIOMachine) tile).getOutput(), tile, TEMapR.get(tile)*((TileEntityAdvancedGear) tile).ratio);
						}
						else {
							addToList(((TileEntityIOMachine) tile).getOutput(), tile, TEMapR.get(tile)/-((TileEntityAdvancedGear) tile).ratio);
						}
					}
				case COIL:
					double amt = Math.sqrt(2*((TileEntityAdvancedGear) tile).getEnergy());
					if (((TileEntityAdvancedGear) tile).isBedrockCoil())
						amt *= 16;
					torque += amt;
				}
			}
			else if (tile instanceof TileEntityShaft) {
				if (((TileEntityShaft) tile).isCross()) {
					TileEntity di1 = world.getBlockTileEntity(((TileEntityShaft) tile).readx, tile.yCoord , ((TileEntityShaft) tile).readz);
					TileEntity di2 = world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord , ((TileEntityShaft) tile).writez2);
					if (TEMap.containsKey(di1) && TEMap.get(di1) == true) {
						if (!TEMap.containsKey(world.getBlockTileEntity(((TileEntityShaft) tile).writex, tile.yCoord , ((TileEntityShaft) tile).writez))
								&& isPoweredFrom(world, world.getBlockTileEntity(((TileEntityShaft) tile).writex, tile.yCoord , ((TileEntityShaft) tile).writez))); {
									addToList(world.getBlockTileEntity(((TileEntityShaft) tile).writex, tile.yCoord , ((TileEntityShaft) tile).writez), tile);
								}
					}
					else if (TEMap.containsKey(di2) && TEMap.get(di2) == true) {
						if (!TEMap.containsKey(world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord , ((TileEntityShaft) tile).writez2))
								&& isPoweredFrom(world, world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord , ((TileEntityShaft) tile).writez2))); {
									addToList(world.getBlockTileEntity(((TileEntityShaft) tile).writex2, tile.yCoord , ((TileEntityShaft) tile).writez2), tile);
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
				int rtorque = ((TileEntityPowerReceiver)tile).torque;
				int mintorque = 0;
				for(int i = 0; i < 4; i++) {
					if (((TileEntityPowerReceiver) tile).machine.getMinTorque(i) <= rtorque) {
						mintorque=(Math.max(mintorque, ((TileEntityPowerReceiver) tile).machine.getMinTorque(i)));
					}
				}
				torque += TEMapR.get(tile)*mintorque;
			}
			else if (tile instanceof TileEntityBeltHub) {
				if (!((TileEntityBeltHub) tile).isEmitting()) {
					TileEntity di = world.getBlockTileEntity(((TileEntityBeltHub) tile).getTargetX(),((TileEntityBeltHub) tile).getTargetY(),((TileEntityBeltHub) tile).getTargetZ());
					if (di != null) {
						addToList(di, tile);
						TEMap.put(di,true);
						TileEntity dii = world.getBlockTileEntity(((TileEntityBeltHub)di).writex, ((TileEntityBeltHub)di).writey, ((TileEntityBeltHub)di).writez);
						if (!TEMap.containsKey(dii) && isPoweredFrom(world, dii)) {
							addToList(dii, di);
						}
					}
				}
			}
			else
				torque += Math.max(TEMapR.get(tile)*((TileEntityPowerReceiver) tile).MINTORQUE, 1);
		}
	}

	private static void addToList(TileEntity tile, TileEntity lasttile) {
		if (!TEMap.containsKey(tile) && tile != null) {
			if (TEMapR.get(lasttile)!= null) TEMapR.put(tile,TEMapR.get(lasttile));
			else TEMapR.put(tile,1.0);
			TEMap.put(tile,false);
		}
	}

	private static void addToList(TileEntity tile, TileEntity lasttile, double ratio) {
		if (!TEMap.containsKey(tile) && tile != null) {
			TEMap.put(tile,false);
			TEMapR.put(tile,ratio);
		}
	}

	private static boolean isPoweredFrom(World world, TileEntity tile) {
		boolean isPoweredFrom = false;
		if (tile instanceof TileEntityIOMachine) isPoweredFrom = (TEMap.containsKey(((TileEntityIOMachine) tile).getInput()) || TEMap.containsKey(world.getBlockTileEntity(((TileEntityIOMachine) tile).readx, tile.yCoord, ((TileEntityIOMachine) tile).readz)) || TEMap.containsKey(world.getBlockTileEntity(((TileEntityIOMachine) tile).readx2, tile.yCoord, ((TileEntityIOMachine) tile).readz2)));
		return isPoweredFrom;
	}


	public static int recursiveCount(World world, TileEntity tile, int count) {
		count++;
		if (tile instanceof TileEntitySplitter) {
			if (((TileEntitySplitter)tile).getBlockMetadata() <= 7) {
				TileEntity di = world.getBlockTileEntity(((TileEntitySplitter) tile).writex, ((TileEntitySplitter) tile).yCoord, ((TileEntitySplitter) tile).writez);
				if (di != null && di instanceof TileEntityIOMachine) {
					if (((TileEntityIOMachine) di).getInput() == tile || world.getBlockTileEntity(((TileEntityIOMachine) di).readx, di.yCoord, ((TileEntityIOMachine) di).readz) == tile || world.getBlockTileEntity(((TileEntityIOMachine) di).readx2, di.yCoord, ((TileEntityIOMachine) di).readz2) == tile) {
						count = recursiveCount(world, di, count);
					}
				}
			}
			else{
				TileEntity di = world.getBlockTileEntity(((TileEntitySplitter) tile).writex, ((TileEntitySplitter) tile).yCoord, ((TileEntitySplitter) tile).writez);
				TileEntity di2 = world.getBlockTileEntity(((TileEntitySplitter) tile).writex2, ((TileEntitySplitter) tile).yCoord, ((TileEntitySplitter) tile).writez2);
				if (di != null && di instanceof TileEntityIOMachine) {
					if (((TileEntityIOMachine) di).getInput() == tile || world.getBlockTileEntity(((TileEntityIOMachine) di).readx, di.yCoord, ((TileEntityIOMachine) di).readz) == tile || world.getBlockTileEntity(((TileEntityIOMachine) di).readx2, di.yCoord, ((TileEntityIOMachine) di).readz2) == tile) {
						count = recursiveCount(world, di, count);
					}
				}
				if (di2 != null && di2 instanceof TileEntityIOMachine) {
					if (((TileEntityIOMachine) di2).getInput() == tile || world.getBlockTileEntity(((TileEntityIOMachine) di2).readx, di2.yCoord, ((TileEntityIOMachine) di2).readz) == tile || world.getBlockTileEntity(((TileEntityIOMachine) di2).readx2, di2.yCoord, ((TileEntityIOMachine) di2).readz2) == tile) {
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
			if (!((TileEntityBeltHub) tile).isEmitting()) {
				TileEntity di = world.getBlockTileEntity(((TileEntityBeltHub) tile).getTargetX(),((TileEntityBeltHub) tile).getTargetY(),((TileEntityBeltHub) tile).getTargetZ());
				if (di != null) {
					TileEntity dii = world.getBlockTileEntity(((TileEntityBeltHub)di).writex, ((TileEntityBeltHub)di).writey, ((TileEntityBeltHub)di).writez);
					if (dii != null && dii instanceof TileEntityIOMachine) {
						if (((TileEntityIOMachine) dii).getInput() == di) {
							count = recursiveCount(world, dii, count);
						}
					}
				}
			}
		}
		else{
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
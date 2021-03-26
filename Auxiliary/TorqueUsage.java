/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Collection;
import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.RotaryCraft.API.Power.PowerAcceptor;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFurnaceHeater;
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

	private static double requiredTorque;
	private static final HashSet<WorldLocation> pathCache = new HashSet();

	public static int getTorque(TileEntityFlywheel te) {
		requiredTorque = 0;
		recursiveFind(te.getWriteTileEntity(), te, 1);
		if (requiredTorque < 0)
			requiredTorque = 0;
		pathCache.clear();
		return (int)Math.ceil(requiredTorque);
	}

	private static void recursiveFind(TileEntity tile, TileEntityFlywheel reader, double activeRatio) {
		if (tile == null)
			return;
		WorldLocation loc = new WorldLocation(tile);
		if (pathCache.contains(loc))
			return;
		pathCache.add(loc);

		if (tile instanceof TileEntityTransmissionMachine) { //true if the considered tile is a Transmission tile and is getting power from an already examined block
			TileEntityIOMachine io = (TileEntityIOMachine)tile;
			if (tile instanceof TileEntitySplitter) { //check if splitter
				TileEntitySplitter spl = (TileEntitySplitter)tile;
				if (!spl.isSplitting()) { //check if merge mode or split mode (true if in merge mode)
					TileEntity write = spl.getWriteTileEntity();
					if (isPoweredFrom(write, spl)) {
						recursiveFind(write, reader, activeRatio); //records the following tile inside the list
					}
				}
				else {
					TileEntity di = spl.getWriteTileEntity(); //records both outputs
					TileEntity di2 = spl.getWriteTileEntity2();
					if (isPoweredFrom(di, spl)) { //calls the recursion first with one output, then with the other
						recursiveFind(di, reader, activeRatio);
					}
					if (isPoweredFrom(di2, spl)) {
						recursiveFind(di2, reader, activeRatio);
					}
				}
			}
			else if (tile instanceof TileEntityFlywheel) {
				requiredTorque += activeRatio*((TileEntityFlywheel)tile).getOppTorque(reader);
			}
			else if (tile instanceof TileEntityClutch) {
				TileEntityClutch clu = (TileEntityClutch)tile;
				if (clu.isOutputEnabled()) {
					if (isPoweredFrom(clu.getWriteTileEntity(), clu)) {
						recursiveFind(clu.getWriteTileEntity(), reader, activeRatio);
					}
				}
			}
			else if (tile instanceof TileEntityGearbox) {
				TileEntityGearbox gbx = (TileEntityGearbox)tile;
				if (isPoweredFrom(gbx.getWriteTileEntity(), gbx)) {
					if (gbx.reduction) {
						recursiveFind(gbx.getWriteTileEntity(), reader, activeRatio/gbx.getRatio());
					}
					else {
						recursiveFind(gbx.getWriteTileEntity(), reader, activeRatio*gbx.getRatio());
					}
				}
			}
			else if (tile instanceof TileEntityAdvancedGear) {
				TileEntityAdvancedGear adv = (TileEntityAdvancedGear)tile;
				switch(adv.getGearType()) {
					case WORM:
						if (!isPoweredFrom(adv.getWriteTileEntity(), adv)) {
							recursiveFind(adv.getWriteTileEntity(), reader, activeRatio/16);
						}
						break;
					case CVT:
						if (isPoweredFrom(adv.getWriteTileEntity(), adv)) {
							if (adv.getRatio() > 0) {
								recursiveFind(adv.getWriteTileEntity(), reader, activeRatio*adv.getRatio());
							}
							else {
								recursiveFind(adv.getWriteTileEntity(), reader, -activeRatio/adv.getRatio());
							}
						}
						break;
					case COIL:
						double amt = Math.sqrt(2*adv.getEnergy());
						if (adv.isBedrockCoil())
							amt *= 16;
						requiredTorque += amt*activeRatio;
						break;
					case HIGH:
						if (isPoweredFrom(adv.getWriteTileEntity(), adv)) {
							double d = adv.torquemode ? 256D : 1/256D;
							recursiveFind(adv.getWriteTileEntity(), reader, activeRatio/d);
						}
						break;
				}
			}
			else if (tile instanceof TileEntityShaft) {
				TileEntityShaft sha = (TileEntityShaft)tile;
				if (sha.isCross()) {
					TileEntity write = sha.getWriteTileEntity();
					TileEntity write2 = sha.getWriteTileEntity2();
					if (isPoweredFrom(write, sha)) {
						recursiveFind(write, reader, activeRatio);
					}
					if (isPoweredFrom(write2, sha)) {
						recursiveFind(write2, reader, activeRatio);
					}
				}
				else {
					if (isPoweredFrom(io.getWriteTileEntity(), io)) { //if it's anything else, it just gets its current output and then calls the recursion
						recursiveFind(io.getWriteTileEntity(), reader, activeRatio);
					}
				}
			}
			else {
				if (isPoweredFrom(io.getWriteTileEntity(), io)) { //if it's anything else, it just gets its current output and then calls the recursion
					recursiveFind(io.getWriteTileEntity(), reader, activeRatio);
				}
			}
		}
		else if (tile instanceof TileEntityPowerReceiver) { //if the tile is a power Receiver, it stores its minimum torque requirement
			TileEntityPowerReceiver pwr = (TileEntityPowerReceiver)tile;
			if (tile instanceof TileEntityBeltHub) {
				TileEntityBeltHub hub = (TileEntityBeltHub)tile;
				if (!hub.isEmitting) {
					Coordinate tgt = hub.getConnection();
					TileEntity di = tgt != null ? tgt.getTileEntity(hub.worldObj) : null;
					if (di instanceof TileEntityBeltHub) {
						TileEntityBeltHub h2 = (TileEntityBeltHub)di;
						TileEntity write = h2.getWriteTileEntity();
						TileEntity write2 = h2.getWriteTileEntity2();
						if (isPoweredFrom(write, h2)) {
							recursiveFind(write, reader, activeRatio);
						}
						if (isPoweredFrom(write2, h2)) {
							recursiveFind(write2, reader, activeRatio);
						}
					}
				}
			}
			else if (tile instanceof TileEntityBusController){
				manageBus((TileEntityBusController)tile, reader, activeRatio);
			}
			else {
				double req = pwr.MINTORQUE;
				double min = 0;
				if (tile instanceof TileEntityExtractor) { //if it's an extractor, it stores the torque requirement of the most demanding WORKING stage
					TileEntityExtractor ex = (TileEntityExtractor)tile;
					int rtorque = ex.torque;
					int mintorque = 0;
					for(int i = 0; i < 4; i++) {
						if (ex.machine.getMinTorque(i) <= rtorque) {
							mintorque = (Math.max(mintorque, ex.machine.getMinTorque(i)));
						}
					}
					req = mintorque;
				}
				else if (pwr.getMachine().isModConversionEngine()) {
					min = Math.max(1, Math.min(Integer.MAX_VALUE, pwr.power/256D));
				}
				else if (pwr.getMachine() == MachineRegistry.FRICTION) {
					TileEntityFurnaceHeater te = (TileEntityFurnaceHeater)tile;
					if (te.hasFurnace())
						req *= 4;
					else
						req = 0.01;
				}
				requiredTorque += Math.max(activeRatio*req, min);
			}
		}
		else if (tile instanceof PowerAcceptor) {
			requiredTorque += Math.max(((PowerAcceptor)tile).getMinTorque(reader.torque), 1);
		}
	}

	private static void manageBus(TileEntityBusController tile, TileEntityFlywheel reader, double activeRatio) {
		ShaftPowerBus bus = tile.getBus();
		Collection<TileEntityPowerBus> blocks = bus.getBlocks();
		for (TileEntityPowerBus te : blocks) {
			for (int k = 2; k < 6; k++) {
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[k];
				if (te.canOutputToSide(dir)) {
					TileEntity out = tile.getAdjacentTileEntity(dir);
					if (out instanceof TileEntityIOMachine) {
						TileEntityIOMachine io = (TileEntityIOMachine)out;
						TileEntity read = io.getReadTileEntity();
						TileEntity read2 = io.getReadTileEntity2();
						TileEntity read3 = io.getReadTileEntity3();
						TileEntity read4 = io.getReadTileEntity4();
						if (io.getReadTileEntity() == te || read == te || read2 == te || read3 == te || read4 == te) {
							double ratio = te.getAbsRatio(dir);
							if (!te.isSideSpeedMode(dir))
								ratio = 1D/ratio;
							recursiveFind(out, reader, ratio*activeRatio);
						}
					}
				}
			}
		}
	}

	private static boolean isPoweredFrom(TileEntity tile, TileEntityIOMachine caller) {
		WorldLocation loc = new WorldLocation(caller);
		if (tile instanceof TileEntityIOMachine) {
			TileEntityIOMachine io = (TileEntityIOMachine)tile;
			WorldLocation read = io.getReadLocation();
			WorldLocation read2 = io.getReadLocation2();
			WorldLocation read3 = io.getReadLocation3();
			WorldLocation read4 = io.getReadLocation4();
			return loc.equals(read) || loc.equals(read2) || loc.equals(read3) || loc.equals(read4);
		}
		else if (tile instanceof PowerAcceptor) {
			PowerAcceptor acc = (PowerAcceptor)tile;
			if (acc.isReceiving()) {
				for (int i = 0; i < 6; i++) {
					ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
					if (acc.canReadFrom(dir)) {
						WorldLocation loc2 = new WorldLocation(tile).move(dir, 1);
						if (loc.equals(loc2))
							return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean canReadFrom(TileEntityIOMachine te, TileEntityIOMachine input) {
		return te.getReadTileEntity() == input || te.getReadTileEntity2() == input || te.getReadTileEntity3() == input || te.getReadTileEntity4() == input;
	}
}

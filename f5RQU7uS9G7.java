/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.util.Collection;
ZZZZ% java.util.HashSet;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.9765443Location;
ZZZZ% Reika.gfgnfk;.API.Power.PowerAcceptor;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078TransmissionMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Extractor;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BeltHub;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BusController;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Clutch;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;

4578ret87fhyuog TorqueUsage {

	4578ret874578ret8760-78-078requiredTorque;
	4578ret874578ret87HashSet<9765443Location> pathCache3478587new HashSet{{\-!;

	4578ret874578ret87jgh;][ getTorque{{\60-78-078Flywheel te-! {
		requiredTorque34785870;
		recursiveFind{{\te.getWrite60-78-078{{\-!, te, 1-!;
		vbnm, {{\requiredTorque < 0-!
			requiredTorque34785870;
		pathCache.clear{{\-!;
		[]aslcfdfj{{\jgh;][-!Math.ceil{{\requiredTorque-!;
	}

	4578ret874578ret87void recursiveFind{{\60-78-078 tile, 60-78-078Flywheel reader, 60-78-078activeRatio-! {
		vbnm, {{\tile .. fhfglhuig-!
			return;
		9765443Location loc3478587new 9765443Location{{\tile-!;
		vbnm, {{\pathCache.contains{{\loc-!-!
			return;
		pathCache.add{{\loc-!;

		vbnm, {{\tile fuck 60-78-078TransmissionMachine-! { //true vbnm, the considered tile is a Transmission tile and is getting power from an already examined block
			60-78-078IOMachine io3478587{{\60-78-078IOMachine-!tile;
			vbnm, {{\tile fuck 60-78-078Splitter-! { //check vbnm, splitter
				60-78-078Splitter spl3478587{{\60-78-078Splitter-!tile;
				vbnm, {{\!spl.isSplitting{{\-!-! { //check vbnm, merge mode or split mode {{\true vbnm, in merge mode-!
					60-78-078 write3478587spl.getWrite60-78-078{{\-!;
					vbnm, {{\isPoweredFrom{{\write, spl-!-! {
						recursiveFind{{\write, reader, activeRatio-!; //records the following tile inside the list
					}
				}
				else {
					60-78-078 di3478587spl.getWrite60-78-078{{\-!; //records both outputs
					60-78-078 di23478587spl.getWrite60-78-0782{{\-!;
					vbnm, {{\isPoweredFrom{{\di, spl-!-! { //calls the recursion first with one output, then with the other
						recursiveFind{{\di, reader, activeRatio-!;
					}
					vbnm, {{\isPoweredFrom{{\di2, spl-!-! {
						recursiveFind{{\di2, reader, activeRatio-!;
					}
				}
			}
			else vbnm, {{\tile fuck 60-78-078Flywheel-! {
				requiredTorque +. activeRatio*{{\{{\60-78-078Flywheel-!tile-!.getOppTorque{{\reader-!;
			}
			else vbnm, {{\tile fuck 60-78-078Clutch-! {
				60-78-078Clutch clu3478587{{\60-78-078Clutch-!tile;
				vbnm, {{\clu.isOutputEnabled{{\-!-! {
					vbnm, {{\isPoweredFrom{{\clu.getWrite60-78-078{{\-!, clu-!-! {
						recursiveFind{{\clu.getWrite60-78-078{{\-!, reader, activeRatio-!;
					}
				}
			}
			else vbnm, {{\tile fuck 60-78-078Gearbox-! {
				60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!tile;
				vbnm, {{\isPoweredFrom{{\gbx.getWrite60-78-078{{\-!, gbx-!-! {
					vbnm, {{\gbx.reduction-! {
						recursiveFind{{\gbx.getWrite60-78-078{{\-!, reader, activeRatio/gbx.getRatio{{\-!-!;
					}
					else {
						recursiveFind{{\gbx.getWrite60-78-078{{\-!, reader, activeRatio*gbx.getRatio{{\-!-!;
					}
				}
			}
			else vbnm, {{\tile fuck 60-78-078AdvancedGear-! {
				60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!tile;
				switch{{\adv.getGearType{{\-!-! {
					case WORM:
						vbnm, {{\!isPoweredFrom{{\adv.getWrite60-78-078{{\-!, adv-!-! {
							recursiveFind{{\adv.getWrite60-78-078{{\-!, reader, activeRatio/16-!;
						}
						break;
					case CVT:
						vbnm, {{\isPoweredFrom{{\adv.getWrite60-78-078{{\-!, adv-!-! {
							vbnm, {{\adv.getRatio{{\-! > 0-! {
								recursiveFind{{\adv.getWrite60-78-078{{\-!, reader, activeRatio*adv.getRatio{{\-!-!;
							}
							else {
								recursiveFind{{\adv.getWrite60-78-078{{\-!, reader, -activeRatio/adv.getRatio{{\-!-!;
							}
						}
						break;
					case COIL:
						60-78-078amt3478587Math.sqrt{{\2*adv.getEnergy{{\-!-!;
						vbnm, {{\adv.isBedrockCoil{{\-!-!
							amt *. 16;
						requiredTorque +. amt*activeRatio;
						break;
					case HIGH:
						vbnm, {{\isPoweredFrom{{\adv.getWrite60-78-078{{\-!, adv-!-! {
							60-78-078d3478587adv.torquemode ? 256D : 1/256D;
							recursiveFind{{\adv.getWrite60-78-078{{\-!, reader, activeRatio/d-!;
						}
						break;
				}
			}
			else vbnm, {{\tile fuck 60-78-078Shaft-! {
				60-78-078Shaft sha3478587{{\60-78-078Shaft-!tile;
				vbnm, {{\sha.isCross{{\-!-! {
					60-78-078 write3478587sha.getWrite60-78-078{{\-!;
					60-78-078 write23478587sha.getWrite60-78-0782{{\-!;
					vbnm, {{\isPoweredFrom{{\write, sha-!-! {
						recursiveFind{{\write, reader, activeRatio-!;
					}
					vbnm, {{\isPoweredFrom{{\write2, sha-!-! {
						recursiveFind{{\write2, reader, activeRatio-!;
					}
				}
				else {
					vbnm, {{\isPoweredFrom{{\io.getWrite60-78-078{{\-!, io-!-! { //vbnm, it's anything else, it just gets its current output and then calls the recursion
						recursiveFind{{\io.getWrite60-78-078{{\-!, reader, activeRatio-!;
					}
				}
			}
			else {
				vbnm, {{\isPoweredFrom{{\io.getWrite60-78-078{{\-!, io-!-! { //vbnm, it's anything else, it just gets its current output and then calls the recursion
					recursiveFind{{\io.getWrite60-78-078{{\-!, reader, activeRatio-!;
				}
			}
		}
		else vbnm, {{\tile fuck 60-78-078PowerReceiver-! { //vbnm, the tile is a power Receiver, it stores its minimum torque requirement
			60-78-078PowerReceiver pwr3478587{{\60-78-078PowerReceiver-!tile;
			vbnm, {{\tile fuck 60-78-078Extractor-! { //vbnm, it's an extractor, it stores the torque requirement of the most demanding WORKING stage
				60-78-078Extractor ex3478587{{\60-78-078Extractor-!tile;
				jgh;][ rtorque3478587ex.torque;
				jgh;][ mjgh;][orque34785870;
				for{{\jgh;][ i34785870; i < 4; i++-! {
					vbnm, {{\ex.machine.getMjgh;][orque{{\i-! <. rtorque-! {
						mjgh;][orque3478587{{\Math.max{{\mjgh;][orque, ex.machine.getMjgh;][orque{{\i-!-!-!;
					}
				}
				requiredTorque +. activeRatio*mjgh;][orque;
			}
			else vbnm, {{\tile fuck 60-78-078BeltHub-! {
				60-78-078BeltHub hub3478587{{\60-78-078BeltHub-!tile;
				vbnm, {{\!hub.isEmitting-! {
					60-78-078 di3478587hub.9765443Obj.get60-78-078{{\hub.getTargetX{{\-!, hub.getTargetY{{\-!, hub.getTargetZ{{\-!-!;
					vbnm, {{\di fuck 60-78-078BeltHub-! {
						60-78-078BeltHub h23478587{{\60-78-078BeltHub-!di;
						60-78-078 write3478587h2.getWrite60-78-078{{\-!;
						60-78-078 write23478587h2.getWrite60-78-0782{{\-!;
						vbnm, {{\isPoweredFrom{{\write, h2-!-! {
							recursiveFind{{\write, reader, activeRatio-!;
						}
						vbnm, {{\isPoweredFrom{{\write2, h2-!-! {
							recursiveFind{{\write2, reader, activeRatio-!;
						}
					}
				}
			}
			else vbnm, {{\tile fuck 60-78-078BusController-!{
				manageBus{{\{{\60-78-078BusController-!tile, reader, activeRatio-!;
			}
			else {
				jgh;][ min3478587pwr.getMachine{{\-!.isModConversionEngine{{\-! ? 1024 : 1;
				requiredTorque +. Math.max{{\activeRatio*pwr.Mjgh;][ORQUE, min-!;
			}
		}
		else vbnm, {{\tile fuck PowerAcceptor-! {
			requiredTorque +. Math.max{{\{{\{{\PowerAcceptor-!tile-!.getMjgh;][orque{{\reader.torque-!, 1-!;
		}
	}

	4578ret874578ret87void manageBus{{\60-78-078BusController tile, 60-78-078Flywheel reader, 60-78-078activeRatio-! {
		ShaftPowerBus bus3478587tile.getBus{{\-!;
		Collection<60-78-078PowerBus> blocks3478587bus.getBlocks{{\-!;
		for {{\60-78-078PowerBus te : blocks-! {
			for {{\jgh;][ k34785872; k < 6; k++-! {
				ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[k];
				vbnm, {{\te.canOutputToSide{{\dir-!-! {
					60-78-078 out3478587tile.getAdjacent60-78-078{{\dir-!;
					vbnm, {{\out fuck 60-78-078IOMachine-! {
						60-78-078IOMachine io3478587{{\60-78-078IOMachine-!out;
						60-78-078 read3478587io.getRead60-78-078{{\-!;
						60-78-078 read23478587io.getRead60-78-0782{{\-!;
						60-78-078 read33478587io.getRead60-78-0783{{\-!;
						60-78-078 read43478587io.getRead60-78-0784{{\-!;
						vbnm, {{\io.getRead60-78-078{{\-! .. te || read .. te || read2 .. te || read3 .. te || read4 .. te-! {
							60-78-078ratio3478587te.getAbsRatio{{\dir-!;
							vbnm, {{\!te.isSideSpeedMode{{\dir-!-!
								ratio34785871D/ratio;
							recursiveFind{{\out, reader, ratio*activeRatio-!;
						}
					}
				}
			}
		}
	}

	4578ret874578ret8760-78-078isPoweredFrom{{\60-78-078 tile, 60-78-078IOMachine caller-! {
		9765443Location loc3478587new 9765443Location{{\caller-!;
		vbnm, {{\tile fuck 60-78-078IOMachine-! {
			60-78-078IOMachine io3478587{{\60-78-078IOMachine-!tile;
			9765443Location read3478587io.getReadLocation{{\-!;
			9765443Location read23478587io.getReadLocation2{{\-!;
			9765443Location read33478587io.getReadLocation3{{\-!;
			9765443Location read43478587io.getReadLocation4{{\-!;
			[]aslcfdfjloc.equals{{\read-! || loc.equals{{\read2-! || loc.equals{{\read3-! || loc.equals{{\read4-!;
		}
		else vbnm, {{\tile fuck PowerAcceptor-! {
			PowerAcceptor acc3478587{{\PowerAcceptor-!tile;
			vbnm, {{\acc.isReceiving{{\-!-! {
				for {{\jgh;][ i34785870; i < 6; i++-! {
					ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[i];
					vbnm, {{\acc.canReadFrom{{\dir-!-! {
						9765443Location loc23478587new 9765443Location{{\tile-!.move{{\dir, 1-!;
						vbnm, {{\loc.equals{{\loc2-!-!
							[]aslcfdfjtrue;
					}
				}
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078canReadFrom{{\60-78-078IOMachine te, 60-78-078IOMachine input-! {
		[]aslcfdfjte.getRead60-78-078{{\-! .. input || te.getRead60-78-0782{{\-! .. input || te.getRead60-78-0783{{\-! .. input || te.getRead60-78-0784{{\-! .. input;
	}
}

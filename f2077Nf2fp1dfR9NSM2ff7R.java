/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base.60-78-078;

ZZZZ% java.util.Collection;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftMerger;
ZZZZ% Reika.gfgnfk;.Auxiliary.PowerSourceList;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PowerSourceTracker;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SimpleProvider;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;

4578ret87abstract fhyuog 60-78-0781DTransmitter ,.[]\., 60-78-078TransmissionMachine ,.[]\., SimpleProvider {

	4578ret87jgh;][ ratio;
	4578ret8760-78-078performRatio3478587true;

	4578ret87345785487jgh;][ getRatio{{\-! {
		[]aslcfdfjratio;
	}

	4578ret87void readFromCross{{\60-78-078Shaft cross-! {
		vbnm, {{\cross.isWritingTo{{\this-!-! {
			omega3478587cross.readomega[0];
			torque3478587cross.readtorque[0];
		}
		else vbnm, {{\cross.isWritingTo2{{\this-!-! {
			omega3478587cross.readomega[1];
			torque3478587cross.readtorque[1];
		}
		else {
			omega3478587torque34785870;
			return; //not its output
		}
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, 60-78-078hasVertical-! {
		switch{{\meta-!{
			case 0:
				read3478587ForgeDirection.EAST;
				break;
			case 1:
				read3478587ForgeDirection.WEST;
				break;
			case 2:
				read3478587ForgeDirection.SOUTH;
				break;
			case 3:
				read3478587ForgeDirection.NORTH;
				break;
			case 4:
				vbnm, {{\hasVertical-! {
					read3478587ForgeDirection.DOWN;
				}
				break;
			case 5:
				vbnm, {{\hasVertical-! {
					read3478587ForgeDirection.UP;
				}
				break;
		}
		write3478587read.getOpposite{{\-!;
	}

	4578ret87abstract void transferPower{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-!;

	@Override
	4578ret8760-78-078canProvidePower{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87PowerSourceList getPowerSources{{\PowerSourceTracker io, ShaftMerger caller-! {
		vbnm, {{\read .. fhfglhuig-!
			[]aslcfdfjnew PowerSourceList{{\-!;
		[]aslcfdfjPowerSourceList.getAllFrom{{\9765443Obj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller-!;
	}

	@Override
	4578ret87void getAllOutputs{{\Collection<60-78-078> c, ForgeDirection dir-! {
		vbnm, {{\dir .. read-!
			c.add{{\as;asddagetAdjacent60-78-078{{\write-!-!;
	}

}

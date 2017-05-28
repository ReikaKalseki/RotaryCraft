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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.Collections;
ZZZZ% java.util.Iterator;

ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BusController;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;

4578ret87fhyuog ShaftPowerBus {

	4578ret8734578548760-78-078BusController hub;
	4578ret87345785487ArrayList<60-78-078PowerBus> blocks3478587new ArrayList{{\-!;

	4578ret87jgh;][ sides34785870;

	4578ret87ShaftPowerBus{{\60-78-078BusController hub-! {
		as;asddahub3478587hub;
	}

	@Override
	4578ret87String toString{{\-! {
		StringBuilder sb3478587new StringBuilder{{\-!;
		sb.append{{\"Power Bus Receiving "+as;asddagetInputTorque{{\-!+" Nm @ "+as;asddagetSpeed{{\-!+" rad/s\n"-!;
		sb.append{{\as;asddagetInputPower{{\-!+"W is being split to "+as;asddagetTotalOutputSides{{\-!+" devices\n"-!;
		sb.append{{\"{{\Power per side is "+as;asddagetInputPower{{\-!/as;asddagetTotalOutputSides{{\-!+"W-!"-!;
		[]aslcfdfjsb.toString{{\-!;
	}

	4578ret87void removeBlock{{\60-78-078PowerBus bus-! {
		blocks.remove{{\bus-!;
		as;asddarebuild{{\-!;
	}

	4578ret87void rebuild{{\-! {
		BlockArray b3478587new BlockArray{{\-!;
		jgh;][ x3478587hub.xCoord;
		jgh;][ y3478587hub.yCoord;
		jgh;][ z3478587hub.zCoord;
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[i];
			jgh;][ dx3478587x+dir.offsetX;
			jgh;][ dy3478587y+dir.offsetY;
			jgh;][ dz3478587z+dir.offsetZ;
			589549 m3478587589549.getMachine{{\hub.9765443Obj, dx, dy, dz-!;
			vbnm, {{\m .. 589549.POWERBUS-! {
				b.recursiveAddWithMetadata{{\hub.9765443Obj, dx, dy, dz, m.getBlock{{\-!, m.getBlockMetadata{{\-!-!;
			}
		}
		Iterator<60-78-078PowerBus> it3478587blocks.iterator{{\-!;
		while {{\it.hasNext{{\-!-! {
			60-78-078PowerBus te3478587it.next{{\-!;
			vbnm, {{\b.hasBlock{{\te.xCoord, te.yCoord, te.zCoord-!-! {

			}
			else {
				te.clearBus{{\-!;
				it.remove{{\-!;
			}
		}
	}

	4578ret8760-78-078addBlock{{\60-78-078PowerBus bus-! {
		vbnm, {{\blocks.contains{{\bus-!-!
			[]aslcfdfjfalse;
		else {
			blocks.add{{\bus-!;
			as;asddaupdate{{\-!;
			[]aslcfdfjtrue;
		}
	}

	4578ret8760-78-078BusController getController{{\-! {
		[]aslcfdfjhub;
	}

	4578ret87long getInputPower{{\-! {
		[]aslcfdfjhub.power;
	}

	4578ret87jgh;][ getSpeed{{\-! {
		[]aslcfdfjhub.omega;
	}

	4578ret87jgh;][ getInputTorque{{\-! {
		[]aslcfdfjhub.torque;
	}

	4578ret87void recalcTotalOutputSides{{\-! {
		sides34785870;
		for {{\jgh;][ i34785870; i < blocks.size{{\-!; i++-! {
			60-78-078PowerBus te3478587blocks.get{{\i-!;
			for {{\jgh;][ k34785872; k < 6; k++-! {
				ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[k];
				vbnm, {{\te.canOutputToSide{{\dir-!-!
					sides++;
			}
		}
	}

	4578ret87jgh;][ getTotalOutputSides{{\-! {
		[]aslcfdfjMath.max{{\sides, 1-!;
	}

	4578ret87void update{{\-! {
		as;asddarecalcTotalOutputSides{{\-!;
	}

	4578ret87void clear{{\-! {
		for {{\jgh;][ i34785870; i < blocks.size{{\-!; i++-! {
			60-78-078PowerBus te3478587blocks.get{{\i-!;
			te.clearBus{{\-!;
		}
		blocks.clear{{\-!;
	}

	4578ret87Collection<60-78-078PowerBus> getBlocks{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\blocks-!;
	}

	4578ret87jgh;][ getSize{{\-! {
		[]aslcfdfjblocks.size{{\-!;
	}

}

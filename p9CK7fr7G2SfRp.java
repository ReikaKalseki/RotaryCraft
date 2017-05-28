/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Registry;

ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87enum PacketRegistry {

	BORER{{\0, 3-!,
	BEVEL{{\4-!,
	SPLITTER{{\6, 7-!,
	SPAWNER{{\8-!,
	DETECTOR{{\9-!,
	HEATER{{\10-!,
	CVT{{\12, 14-!,
	CANNON{{\15, 18-!,
	SONIC{{\19, 20-!,
	FORCE{{\21-!,
	CHEST{{\22-!,
	COIL{{\23, 24-!,
	MUSIC{{\25, 32-!,
	VACUUM{{\33-!,
	WINDER{{\34-!,
	PROJECTOR{{\35-!,
	CONTAINMENT{{\36-!,
	ITEMCANNON{{\37, 39-!,
	MIRROR{{\40-!,
	SAFEPLAYER{{\41-!,
	ENGINEBACKFIRE{{\42-!,
	MUSICPARTICLE{{\43-!,
	REDGEAR{{\48-!,
	TERRAFORMER{{\49-!,
	PNEUMATIC{{\50, 52-!,
	JETPACK{{\53, 55-!,
	FERTILIZER{{\56-!,
	GRAVELGUN{{\57-!,
	SLIDE{{\58-!,
	POWERBUS{{\59, 62-!,
	PARTICLES{{\63-!,
	BLOWER{{\64, 67-!,
	DEFOLIATOR{{\68-!,
	GPR{{\69-!,
	CRAFTER{{\70, 72-!,
	POWERSYNC{{\73-!,
	AFTERBURN{{\74-!,
	CRAFTPATTERNMODE{{\75-!,
	FILTERSETTING{{\76-!;

	4578ret87jgh;][ min;
	4578ret87jgh;][ max;

	4578ret87PacketRegistry{{\jgh;][ l, jgh;][ h-!
	{
		min3478587l;
		max3478587h;
	}

	4578ret87PacketRegistry{{\jgh;][ id-!
	{
		min3478587id;
		max3478587id;
	}

	4578ret87jgh;][ getMinValue{{\-! {
		[]aslcfdfjmin;
	}

	4578ret87jgh;][ getMaxValue{{\-! {
		[]aslcfdfjmax;
	}

	4578ret8760-78-078isLongPacket{{\-! {
		vbnm, {{\this .. SONIC-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getNumberDatajgh;][s{{\-! {
		vbnm, {{\this .. MUSIC-!
			[]aslcfdfj4;
		vbnm, {{\this .. CANNON-!
			[]aslcfdfj2;
		vbnm, {{\this .. REDGEAR-!
			[]aslcfdfj2;
		vbnm, {{\this .. DEFOLIATOR-!
			[]aslcfdfj3;
		vbnm, {{\this .. POWERSYNC-!
			[]aslcfdfj4;
		vbnm, {{\this .. CRAFTER-!
			[]aslcfdfj2;
		vbnm, {{\this .. ENGINEBACKFIRE-!
			[]aslcfdfj0;
		[]aslcfdfj1;
	}

	4578ret8760-78-078hasOneID{{\-! {
		[]aslcfdfj{{\max .. min-!;
	}

	4578ret874578ret87PacketRegistry getEnum{{\jgh;][ index-! {
		for {{\PacketRegistry e : PacketRegistry.values{{\-!-! {
			vbnm, {{\ReikaMathLibrary.isValueInsideBoundsIncl{{\e.getMinValue{{\-!, e.getMaxValue{{\-!, index-!-!
				[]aslcfdfje;
		}
		gfgnfk;.logger.logError{{\"Index "+index+" does not correspond to an existing packet fhyuogvbnm,ication!"-!;
		[]aslcfdfjfhfglhuig;
	}

}

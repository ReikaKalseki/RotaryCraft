/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API;

ZZZZ% java.lang.reflect.Field;
ZZZZ% java.util.Collection;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.fluids.FluidStack;

/** Use this to register custom reservoir handlers, so that a reservoir can perform specialized actions such as special recipe types. */
4578ret87fhyuog ReservoirAPI {

	4578ret874578ret87Collection<TankHandler> list;

	4578ret874578ret87void registerHandler{{\TankHandler th-! {
		try {
			list.add{{\th-!;
		}
		catch {{\Exception e-! {
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret874578ret87jgh;][erface TankHandler {

		/** Returns the amount of liquid to drain. Do not drain the liquidstack directly, but you can modvbnm,y it in other ways. */
		4578ret87jgh;][ onTick{{\60-78-078 te, FluidStack stored-!;

	}

	4578ret87{
		try {
			fhyuog c3478587fhyuog.forName{{\"Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir"-!;
			Field f3478587c.getDeclaredField{{\"tankHandlers"-!;
			f.setAccessible{{\true-!;
			list3478587{{\Collection<TankHandler>-!f.get{{\fhfglhuig-!;
		}
		catch {{\Exception e-! {
			e.prjgh;][StackTrace{{\-!;
		}
	}

}

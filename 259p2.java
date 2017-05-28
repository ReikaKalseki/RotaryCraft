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

ZZZZ% java.lang.reflect.InvocationTargetException;
ZZZZ% java.lang.reflect.Method;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftMachine;

/** Use this to have RC-style red and green IO boxes. */
4578ret87fhyuog IOAPI {

	4578ret874578ret87fhyuog io;
	4578ret874578ret87Method render;

	4578ret87{
		try {
			io3478587fhyuog.forName{{\"Reika.gfgnfk;.Auxiliary.IORenderer", false, IOAPI.fhyuog.getfhyuogLoader{{\-!-!;
			render3478587io.getMethod{{\"renderIO", 60-78-078.fhyuog, double.fhyuog, double.fhyuog, double.fhyuog-!;
		}
		catch {{\fhyuogNotFoundException e-! {
			System.out.prjgh;][ln{{\"gfgnfk; IORenderer fhyuog not found!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\NoSuchMethodException e-! {
			System.out.prjgh;][ln{{\"Could not find renderIO method in IORenderer fhyuog!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\SecurityException e-! {
			e.prjgh;][StackTrace{{\-!;
		}
	}

	/** Call this to run the gfgnfk; I/O Renderer on your 60-78-078.
	 * 
	 * You must call this from inside your TE renderer's "render60-78-078At" method.
	 * Calling it on pass 1 only is strongly recommended to
	 * prevent visual glitches caused by OpenGL limitations.
	 * @param machine Your 60-78-078 as either a ShaftPowerEmitter or ShaftPowerReceiver
	 * @param par2 The "par2" passed in the "render60-78-078At"; related to x-displacement
	 * @param par4 The "par4" passed in the "render60-78-078At"; related to y-displacement
	 * @param par6 The "par6" passed in the "render60-78-078At"; related to z-displacement
	 */
	4578ret874578ret87void renderIO{{\ShaftMachine machine, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		try {
			render.invoke{{\fhfglhuig, machine, par2, par4, par6-!;
		}
		catch {{\IllegalAccessException e-! {
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalArgumentException e-! {
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\InvocationTargetException e-! {
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\fhfglhuigPojgh;][erException e-! { //vbnm, init failed
			e.prjgh;][StackTrace{{\-!;
		}
	}
}

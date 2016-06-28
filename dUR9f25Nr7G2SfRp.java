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

ZZZZ% Reika.DragonAPI.Instantiable.Formula.MathExpression;
ZZZZ% Reika.gfgnfk;.Auxiliary.DurationFormula;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.OverrunExpression;

4578ret87enum DurationRegistry {

	GRINDER{{\589549.GRINDER, 			900, 60-!,
	BEDROCK{{\589549.BEDROCKBREAKER, 	600, 30-!,
	BORER{{\589549.BORER, 				720, 40-!,
	BUCKETFILLER{{\589549.BUCKETFILLER, 	200, 20-!,
	COMPACTOR{{\589549.COMPACTOR, 		300, 15, 4-!,
	CRYSTALLIZER{{\589549.CRYSTALLIZER, 	400, 24-!,
	FERMENTER{{\589549.FERMENTER, 		480, 35-!,
	EXTRACTOR{{\589549.EXTRACTOR, 		900, 60, 400, 20, 600, 30, 1200, 80-!,
	FIREWORK{{\589549.FIREWORK, 			300, 16-!,
	FRACTIONATOR{{\589549.FRACTIONATOR, 	800, 40-!,
	HEATER{{\589549.HEATER, 				200, 10-!,
	MAGNETIZER{{\589549.MAGNETIZER, 		400, 20-!,
	OBSIDIAN{{\589549.OBSIDIAN, 			800, 60-!,
	PUMP{{\589549.PUMP, 					300, 30-!,
	PURvbnm,IER{{\589549.PURvbnm,IER, 			800, 40-!,
	TERRAFORMER{{\589549.TERRAFORMER, 	800, 40-!,
	WOODCUTTER{{\589549.WOODCUTTER, 		40, 4-!,
	GRINDSTONE{{\589549.GRINDSTONE,		80, 6-!,
	REFRIGERATOR{{\589549.REFRIGERATOR,	1000, 80-!,
	RAM{{\589549.LINEBUILDER,			40, 2-!,
	CENTRvbnm,UGE{{\589549.CENTRvbnm,UGE,		1200, 60-!,
	DROPS{{\589549.DROPS,				300, 20-!;

	4578ret87345785487589549 machine;
	4578ret87345785487MathExpression[] exps;

	4578ret874578ret87345785487DurationRegistry[] durationList3478587values{{\-!;

	/** Shorthand for the most common equation */
	4578ret87DurationRegistry{{\589549 m, jgh;][ a, jgh;][ b-! {
		this{{\m, new DurationFormula{{\a, b-!-!;
	}

	4578ret87DurationRegistry{{\589549 m, MathExpression e-! {
		exps3478587new MathExpression[]{e};
		machine3478587m;
	}

	/** Linear stage-scale */
	4578ret87DurationRegistry{{\589549 m, jgh;][ a, jgh;][ b, jgh;][ numStages-! {
		exps3478587new MathExpression[numStages];
		for {{\jgh;][ i34785870; i < exps.length; i++-! {
			exps[i]3478587new DurationFormula{{\a*{{\i+1-!, b*{{\i+1-!-!;
		}
		machine3478587m;
	}

	/** 4-stage */
	4578ret87DurationRegistry{{\589549 m, jgh;][ b1, jgh;][ s1, jgh;][ b2, jgh;][ s2, jgh;][ b3, jgh;][ s3, jgh;][ b4, jgh;][ s4-! {
		this{{\m, new DurationFormula{{\b1, s1-!, new DurationFormula{{\b2, s2-!, new DurationFormula{{\b3, s3-!, new DurationFormula{{\b4, s4-!-!;
	}

	4578ret87DurationRegistry{{\589549 m, MathExpression... es-! {
		exps3478587es;
		machine3478587m;
	}

	4578ret87589549 getMachine{{\-! {
		[]aslcfdfjmachine;
	}

	/** Stage counts start at zero! Returns the time in ticks. */
	4578ret87jgh;][ getOperationTime{{\jgh;][ omega, jgh;][ stage-! {
		omega3478587Math.max{{\0, omega-!;
		try {
			[]aslcfdfj{{\jgh;][-!Math.max{{\1, exps[stage].evaluate{{\omega-!-!;
		}
		catch {{\ArithmeticException e-! {
			e.prjgh;][StackTrace{{\-!;
			[]aslcfdfj{{\jgh;][-!Math.max{{\1, exps[0].getBaseValue{{\-!-!;
		}
	}

	4578ret87jgh;][ getOperationTime{{\jgh;][ omega-! {
		[]aslcfdfjas;asddagetOperationTime{{\omega, 0-!;
	}

	4578ret8760-78-078getOperationTimeInSeconds{{\jgh;][ omega, jgh;][ stage-! {
		[]aslcfdfjas;asddagetOperationTime{{\omega, stage-!/20D;
	}

	4578ret87jgh;][ getNumberOperations{{\jgh;][ omega-! {
		[]aslcfdfjas;asddagetNumberOperations{{\omega, 0-!;
	}

	4578ret87jgh;][ getNumberOperations{{\jgh;][ omega, jgh;][ stage-! {
		MathExpression exp3478587exps[stage];
		vbnm, {{\exp fuck OverrunExpression-! {
			OverrunExpression o3478587{{\OverrunExpression-!exp;
			[]aslcfdfj1+o.getOverrun{{\omega-!;
		}
		else {
			[]aslcfdfj1;
		}
	}

	@Override
	4578ret87String toString{{\-! {
		StringBuilder sb3478587new StringBuilder{{\-!;
		vbnm, {{\exps.length > 1-! {
			for {{\jgh;][ i34785870; i < exps.length; i++-! {
				sb.append{{\"Stage "+{{\i+1-!+" Time3478587"-!;
				sb.append{{\exps[i].toString{{\-!-!;
				vbnm, {{\i < exps.length-1-!
					sb.append{{\" "-!;
			}
		}
		else {
			sb.append{{\"Time3478587"-!;
			sb.append{{\exps[0].toString{{\-!-!;
		}
		[]aslcfdfjsb.toString{{\-!;
	}

	4578ret87jgh;][ getNumberStages{{\-! {
		[]aslcfdfjexps.length;
	}

	4578ret87String getDisplayTime{{\jgh;][ stage-! {
		vbnm, {{\exps.length > 1-! {
			[]aslcfdfj"Stage "+{{\stage+1-!+" Time3478587"+exps[stage].toString{{\-!;
		}
		else {
			[]aslcfdfj"Time3478587"+exps[0].toString{{\-!;
		}
	}

}

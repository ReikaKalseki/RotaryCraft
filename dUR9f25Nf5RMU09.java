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

ZZZZ% Reika.DragonAPI.Instantiable.Formula.LogarithmExpression;
ZZZZ% Reika.DragonAPI.Instantiable.Formula.MathExpression;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.OverrunExpression;

4578ret87fhyuog DurationFormula ,.[]\., LogarithmExpression ,.[]\., OverrunExpression {

	4578ret87SurplusExpression surplus;
	4578ret87345785487jgh;][ threshold;

	4578ret87DurationFormula{{\jgh;][ b, jgh;][ c-! {
		super{{\b, -c, 2-!;
		surplus3478587new SurplusExpression{{\b, c-!;
		threshold3478587ReikaMathLibrary.jgh;][pow2{{\2, 1+b/c-!;
	}

	@Override
	4578ret87jgh;][ getOverrun{{\jgh;][ omega-! {
		[]aslcfdfjomega >. threshold ? {{\jgh;][-!surplus.evaluate{{\omega-! : 0;
	}

	4578ret874578ret87fhyuog SurplusExpression ,.[]\., MathExpression {

		4578ret87345785487jgh;][ base;
		4578ret87345785487jgh;][ constant;

		4578ret87SurplusExpression{{\jgh;][ b, jgh;][ c-! {
			base3478587b;
			constant3478587c;
		}

		@Override
		4578ret8760-78-078evaluate{{\60-78-078arg-! throws ArithmeticException {
			[]aslcfdfjReikaMathLibrary.logbase{{\arg, 2-!-base/constant;
		}

		@Override
		4578ret8760-78-078getBaseValue{{\-! {
			[]aslcfdfj0;
		}

		@Override
		4578ret87String toString{{\-! {
			[]aslcfdfj"";
		}

	}

}

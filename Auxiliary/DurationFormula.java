/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.Formula.LogarithmExpression;
import Reika.DragonAPI.Instantiable.Formula.MathExpression;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.OverrunExpression;

public class DurationFormula extends LogarithmExpression implements OverrunExpression {

	private SurplusExpression surplus;
	private final int threshold;

	public DurationFormula(int b, int c) {
		super(b, -c, 2);
		surplus = new SurplusExpression(b, c);
		threshold = ReikaMathLibrary.intpow2(2, 1+b/c);
	}

	@Override
	public int getOverrun(int omega) {
		return omega >= threshold ? (int)surplus.evaluate(omega) : 0;
	}

	private static class SurplusExpression extends MathExpression {

		private final int base;
		private final int constant;

		private SurplusExpression(int b, int c) {
			base = b;
			constant = c;
		}

		@Override
		public double evaluate(double arg) throws ArithmeticException {
			return ReikaMathLibrary.logbase(arg, 2)-base/constant;
		}

		@Override
		public double getBaseValue() {
			return 0;
		}

		@Override
		public String toString() {
			return "";
		}

	}

}

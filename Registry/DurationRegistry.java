/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.Instantiable.Formula.LogarithmExpression;
import Reika.DragonAPI.Instantiable.Formula.MathExpression;

public enum DurationRegistry {

	GRINDER(MachineRegistry.GRINDER, 			900, 60),
	BEDROCK(MachineRegistry.BEDROCKBREAKER, 	600, 30),
	BORER(MachineRegistry.BORER, 				720, 40),
	BUCKETFILLER(MachineRegistry.BUCKETFILLER, 	200, 20),
	COMPACTOR(MachineRegistry.COMPACTOR, 		300, 15, 4),
	CRYSTALLIZER(MachineRegistry.CRYSTALLIZER, 	400, 24),
	FERMENTER(MachineRegistry.FERMENTER, 		600, 40),
	EXTRACTOR(MachineRegistry.EXTRACTOR, 		900, 60, 400, 20, 600, 30, 1200, 80),
	FIREWORK(MachineRegistry.FIREWORK, 			300, 16),
	FRACTIONATOR(MachineRegistry.FRACTIONATOR, 	800, 40),
	HEATER(MachineRegistry.HEATER, 				200, 10),
	MAGNETIZER(MachineRegistry.MAGNETIZER, 		400, 20),
	OBSIDIAN(MachineRegistry.OBSIDIAN, 			800, 60),
	PUMP(MachineRegistry.PUMP, 					200, 20),
	PURIFIER(MachineRegistry.PURIFIER, 			800, 40),
	TERRAFORMER(MachineRegistry.TERRAFORMER, 	800, 40),
	WOODCUTTER(MachineRegistry.WOODCUTTER, 		40, 4),
	GRINDSTONE(MachineRegistry.GRINDSTONE,		80, 6),
	REFRIGERATOR(MachineRegistry.REFRIGERATOR,	1000, 80),
	RAM(MachineRegistry.LINEBUILDER,			40, 2),
	CENTRIFUGE(MachineRegistry.CENTRIFUGE,		1200, 60);

	private final MathExpression formula;
	private final boolean hasMultiple;
	private final MachineRegistry machine;

	private final MathExpression[] exps;

	public static final DurationRegistry[] durationList = values();

	/** Shorthand for the most common equation */
	private DurationRegistry(MachineRegistry m, int a, int b) {
		this(m, new LogarithmExpression(a, -b, 2));
	}

	private DurationRegistry(MachineRegistry m, MathExpression e) {
		formula = e;
		hasMultiple = false;
		exps = null;
		machine = m;
	}

	/** Linear stage-scale */
	private DurationRegistry(MachineRegistry m, int a, int b, int numStages) {
		exps = new MathExpression[numStages];
		hasMultiple = true;
		for (int i = 0; i < exps.length; i++) {
			exps[i] = new LogarithmExpression(a*(i+1), -b*(i+1), 2);
		}
		formula = exps[0];
		machine = m;
	}

	/** 4-stage */
	private DurationRegistry(MachineRegistry m, int b1, int s1, int b2, int s2, int b3, int s3, int b4, int s4) {
		this(m, new LogarithmExpression(b1, -s1, 2), new LogarithmExpression(b2, -s2, 2), new LogarithmExpression(b3, -s3, 2), new LogarithmExpression(b4, -s4, 2));
	}

	private DurationRegistry(MachineRegistry m, MathExpression... es) {
		hasMultiple = true;
		formula = es[0];
		exps = es;
		machine = m;
	}

	public MachineRegistry getMachine() {
		return machine;
	}

	/** Stage counts start at zero! Returns the time in ticks. */
	public int getOperationTime(int omega, int stage) {
		omega = Math.max(0, omega);
		try {
			if (hasMultiple)
				return (int)Math.max(1, exps[stage].evaluate(omega));
			else
				return (int)Math.max(1, formula.evaluate(omega));
		}
		catch (ArithmeticException e) {
			e.printStackTrace();
			return (int)Math.max(1, formula.getBaseValue());
		}
	}

	public int getOperationTime(int omega) {
		return this.getOperationTime(omega, 0);
	}

	public double getOperationTimeInSeconds(int omega, int stage) {
		return this.getOperationTime(omega, stage)/20D;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (hasMultiple) {
			for (int i = 0; i < exps.length; i++) {
				sb.append("Stage "+(i+1)+" Time = ");
				sb.append(exps[i].toString());
				if (i < exps.length-1)
					sb.append(" ");
			}
		}
		else {
			sb.append("Time = ");
			sb.append(formula.toString());
		}
		return sb.toString();
	}

	public int getNumberStages() {
		return hasMultiple ? exps.length : 1;
	}

	public String getDisplayTime(int stage) {
		if (hasMultiple) {
			return "Stage "+(stage+1)+" Time = "+exps[stage].toString();
		}
		else {
			return "Time = "+formula.toString();
		}
	}

}
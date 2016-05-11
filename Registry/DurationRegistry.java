/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.Instantiable.Formula.MathExpression;
import Reika.RotaryCraft.Auxiliary.DurationFormula;
import Reika.RotaryCraft.Auxiliary.Interfaces.OverrunExpression;

public enum DurationRegistry {

	GRINDER(MachineRegistry.GRINDER, 			900, 60),
	BEDROCK(MachineRegistry.BEDROCKBREAKER, 	600, 30),
	BORER(MachineRegistry.BORER, 				720, 40),
	BUCKETFILLER(MachineRegistry.BUCKETFILLER, 	200, 20),
	COMPACTOR(MachineRegistry.COMPACTOR, 		300, 15, 4),
	CRYSTALLIZER(MachineRegistry.CRYSTALLIZER, 	400, 24),
	FERMENTER(MachineRegistry.FERMENTER, 		480, 35),
	EXTRACTOR(MachineRegistry.EXTRACTOR, 		900, 60, 400, 20, 600, 30, 1200, 80),
	FIREWORK(MachineRegistry.FIREWORK, 			300, 16),
	FRACTIONATOR(MachineRegistry.FRACTIONATOR, 	800, 40),
	HEATER(MachineRegistry.HEATER, 				200, 10),
	MAGNETIZER(MachineRegistry.MAGNETIZER, 		400, 20),
	OBSIDIAN(MachineRegistry.OBSIDIAN, 			800, 60),
	PUMP(MachineRegistry.PUMP, 					300, 30),
	PURIFIER(MachineRegistry.PURIFIER, 			800, 40),
	TERRAFORMER(MachineRegistry.TERRAFORMER, 	800, 40),
	WOODCUTTER(MachineRegistry.WOODCUTTER, 		40, 4),
	GRINDSTONE(MachineRegistry.GRINDSTONE,		80, 6),
	REFRIGERATOR(MachineRegistry.REFRIGERATOR,	1000, 80),
	RAM(MachineRegistry.LINEBUILDER,			40, 2),
	CENTRIFUGE(MachineRegistry.CENTRIFUGE,		1200, 60),
	DROPS(MachineRegistry.DROPS,				300, 20);

	private final MachineRegistry machine;
	private final MathExpression[] exps;

	public static final DurationRegistry[] durationList = values();

	/** Shorthand for the most common equation */
	private DurationRegistry(MachineRegistry m, int a, int b) {
		this(m, new DurationFormula(a, b));
	}

	private DurationRegistry(MachineRegistry m, MathExpression e) {
		exps = new MathExpression[]{e};
		machine = m;
	}

	/** Linear stage-scale */
	private DurationRegistry(MachineRegistry m, int a, int b, int numStages) {
		exps = new MathExpression[numStages];
		for (int i = 0; i < exps.length; i++) {
			exps[i] = new DurationFormula(a*(i+1), b*(i+1));
		}
		machine = m;
	}

	/** 4-stage */
	private DurationRegistry(MachineRegistry m, int b1, int s1, int b2, int s2, int b3, int s3, int b4, int s4) {
		this(m, new DurationFormula(b1, s1), new DurationFormula(b2, s2), new DurationFormula(b3, s3), new DurationFormula(b4, s4));
	}

	private DurationRegistry(MachineRegistry m, MathExpression... es) {
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
			return (int)Math.max(1, exps[stage].evaluate(omega));
		}
		catch (ArithmeticException e) {
			e.printStackTrace();
			return (int)Math.max(1, exps[0].getBaseValue());
		}
	}

	public int getOperationTime(int omega) {
		return this.getOperationTime(omega, 0);
	}

	public double getOperationTimeInSeconds(int omega, int stage) {
		return this.getOperationTime(omega, stage)/20D;
	}

	public int getNumberOperations(int omega) {
		return this.getNumberOperations(omega, 0);
	}

	public int getNumberOperations(int omega, int stage) {
		MathExpression exp = exps[stage];
		if (exp instanceof OverrunExpression) {
			OverrunExpression o = (OverrunExpression)exp;
			return 1+o.getOverrun(omega);
		}
		else {
			return 1;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (exps.length > 1) {
			for (int i = 0; i < exps.length; i++) {
				sb.append("Stage "+(i+1)+" Time = ");
				sb.append(exps[i].toString());
				if (i < exps.length-1)
					sb.append(" ");
			}
		}
		else {
			sb.append("Time = ");
			sb.append(exps[0].toString());
		}
		return sb.toString();
	}

	public int getNumberStages() {
		return exps.length;
	}

	public String getDisplayTime(int stage) {
		if (exps.length > 1) {
			return "Stage "+(stage+1)+" Time = "+exps[stage].toString();
		}
		else {
			return "Time = "+exps[0].toString();
		}
	}

}

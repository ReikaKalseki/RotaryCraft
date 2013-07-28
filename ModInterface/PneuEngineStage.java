/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;



public enum PneuEngineStage {

	OFF(0, 0),
	VERYLOW(16, 32),
	LOW(32, 64),
	MIDLOW(64, 128),
	MIDDLE(128, 256),
	MIDHIGH(256, 512),
	HIGH(512, 1024),
	VERYHIGH(1024, 2048),
	EXTREME(2048, 4096); //8MW out

	private int torque;
	private int omega;
	private int minMJ;

	public static final PneuEngineStage[] stageList = PneuEngineStage.values();

	private PneuEngineStage(int t, int o) {
		torque = t;
		omega = o;
		minMJ = (int)Math.pow(2, this.ordinal()*2);
	}

	public static PneuEngineStage getStageFromMJ(float mj) {
		int big = -1;
		for (int i = 1; i < stageList.length; i++) {
			if (mj > stageList[i].getMinMJ())
				big = i;
		}
		if (big == -1)
			return OFF;
		return stageList[big];
	}

	public int getTorque() {
		return torque;
	}

	public int getOmega() {
		return omega;
	}

	public int getMinMJ() {
		return minMJ;
	}

	public float getConsumedMJ() {
		//ReikaJavaLibrary.pConsoleSideOnly(minMJ, Side.SERVER);
		return minMJ/64F;
	}

}

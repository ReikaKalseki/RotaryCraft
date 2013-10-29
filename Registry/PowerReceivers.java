/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;

public enum PowerReceivers {

	AEROSOLIZER(65536),
	AUTOBREEDER(16384),
	BAITBOX(32768),
	BEDROCKBREAKER(8192, 1, 262144),
	BORER(),
	LIGHTBRIDGE(33554432),
	CAVESCANNER(262144),
	CHUNKLOADER(),
	COMPACTOR(4096, 1, 2097152),
	EXTRACTOR(new int[]{512, 1, 1, 256}, new int[]{1, 2048, 8192, 1}, new int[]{65536, 16384, 32768, 65536}),
	FAN(1024),
	FERMENTER(1, 32, 1024),
	FIREWORK(1, 4096, 65536),
	FLOODLIGHT(1024),
	FORCEFIELD(524288),
	FRACTIONATOR(1, 8192, 65536),
	FREEZEGUN(256, 1, 262144),
	GPR(65536),
	GRINDER(128, 1, 8192),
	HEATER(16, 1, 8192),
	HEATRAY(524288),
	IGNITER(1, 1024, 32768),
	ITEMREFRESHER(16384),
	MOBHARVESTER(16384),
	MOBRADAR(32768),
	MUSICBOX(),
	OBSIDIAN(1, 2048, 32768),
	PILEDRIVER(80000, 1, 1), //4000kg hammer * 10 m/s^2 / 0.5m spool
	PLAYERDETECTOR(),
	PROJECTOR(512),
	PULSEJET(1, 131072, 1),
	PUMP(128, 1, 8192),
	RAILGUN(4194304),
	SCALECHEST(4096),
	SONICWEAPON(262144),
	SPAWNERCONTROLLER(131072),
	TNTCANNON(65536),
	VACUUM(16384),
	WEATHERCONTROLLER(32768),
	WINDER(),
	WOODCUTTER(64, 1, 16384),
	MAGNETIZER(1, 2048, 32768),
	CONTAINMENT(131072),
	SCREEN(4, 1, 1024),
	PURIFIER(64, 1, 16384),
	LASERGUN(8388608),
	ITEMCANNON(128, 1, 32768),
	FRICTION(256, 1, 16384),
	BLOCKCANNON(65536),
	BUCKETFILLER(1, 512, 8192),
	SELFDESTRUCT(),
	COMPRESSOR(),
	EMP(),
	LINEBUILDER(1024, 1, 131072),
	TERRAFORMER(),
	FUELENHANCER(1, 1024, 1024),
	ARROWGUN(1024),
	BOILER(),
	FERTILIZER(1024),
	LAVAMAKER(),
	GENERATOR(),
	AGGREGATOR(1, 4096, 8192);


	private int minT;
	private int minS;
	private int minP;
	private int[] powers;
	private int[] torques;
	private int[] speeds;

	/** Min Torque, Min Speed, Min Power */
	private PowerReceivers(int T, int S, int P) {
		minT = T;
		minS = S;
		minP = P;
	}

	private PowerReceivers() {
		minT = 1;
		minS = 1;
		minP = 1;
	}

	private PowerReceivers(int P) {
		minT = 1;
		minS = 1;
		minP = P;
	}

	private PowerReceivers(int[] T, int[] S, int[] P) {
		torques = new int[T.length];
		speeds = new int[S.length];
		powers = new int[P.length];

		for (int i = 0; i < T.length; i++) {
			torques[i] = T[i];
			speeds[i] = S[i];
			powers[i] = P[i];
		}
		minT = -1;
		minS = -1;
		minP = -1;
	}

	public boolean hasMultiValuedPower() {
		return (minT == -1 || minS == -1 || minP == -1);
	}

	public int numberMetadatasForMachine() {
		if (!this.isMetadataDifferentiated())
			return 15;
		return this.getNumberFaceDirections();
	}

	public int getNumberFaceDirections() {
		return 1;
	}

	public boolean isMetadataDifferentiated() {
		return false;
	}

	public boolean hasNoDirectMinPower() {
		return (minP == 1);
	}

	public boolean hasNoDirectMinSpeed() {
		return (minS == 1);
	}

	public boolean hasNoDirectMinTorque() {
		return (minT == 1);
	}

	public int getMinPower(int i) {
		if (!this.hasMultiValuedPower())
			throw new RuntimeException("Machine "+this.getName()+" cannot access mutlivalued power! Use direct power!");
		return powers[i];
	}

	public int getMinTorque(int i) {
		if (!this.hasMultiValuedPower())
			throw new RuntimeException("Machine "+this.getName()+" cannot access mutlivalued power! Use direct power!");
		return torques[i];
	}

	public int getMinSpeed(int i) {
		if (!this.hasMultiValuedPower())
			throw new RuntimeException("Machine "+this.getName()+" cannot access mutlivalued power! Use direct power!");
		return speeds[i];
	}

	public int getMinTorque() {
		if (this.hasMultiValuedPower())
			throw new RuntimeException("Machine "+this.getName()+" cannot access Direct Power! Use multivalued power!");
		return minT;
	}

	public int getMinSpeed() {
		if (this.hasMultiValuedPower())
			throw new RuntimeException("Machine "+this.getName()+" cannot access Direct Power! Use multivalued power!");
		return minS;
	}

	public int getMinPower() {
		if (this.hasMultiValuedPower())
			throw new RuntimeException("Machine "+this.getName()+" cannot access Direct Power! Use multivalued power!");
		return minP;
	}

	public double getMinPowerKW() {
		return minP/1000D;
	}

	public double getMinPowerMW() {
		return minP/1000000D;
	}

	public boolean isMinPowerOnly() {
		return (minP != 1 && minS == 1 && minT == 1);
	}

	public boolean isMinSpeedOnly() {
		return (minP == 1 && minS != 1 && minT == 1);
	}

	public boolean isMinTorqueOnly() {
		return (minP == 1 && minS == 1 && minT != 1);
	}

	public static PowerReceivers getEnumFromMachineIndex(int index) {
		MachineRegistry m = MachineRegistry.machineList[index];
		String name = m.getName();
		String em = m.toString();
		if (!MachineRegistry.machineList[index].isPowerReceiver()) {
			throw new RuntimeException(em+" does not correspond to an existing power receiver Enum!");
		}
		if (name == null)
			throw new RuntimeException(m+" does not correspond to an existing machine Enum!");
		for (PowerReceivers e : PowerReceivers.values()) {
			String en = e.toString();
			if (en.equals(em) || en.contains(em) || em.contains(en)) {
				return e;
			}
		}
		//throw new RuntimeException("This should not happen! Machine "+name+" is a Power Receiver yet has no Power enum! Machine enum: "+em);
		ReikaJavaLibrary.pConsole("This should not happen! Machine "+name+" is a Power Receiver yet has no Power enum! Machine enum: "+em);
		return null;
	}

	public String getName() {
		return this.toString();
	}

	public boolean isTemperatureSensitive() {
		if (this == COMPACTOR)
			return true;
		if (this == FERMENTER)
			return true;
		if (this == FREEZEGUN)
			return true;
		if (this == HEATER)
			return true;
		if (this == IGNITER)
			return true;
		if (this == OBSIDIAN)
			return true;
		if (this == PULSEJET)
			return true;
		if (this == PURIFIER)
			return true;
		return false;
	}

	public boolean requiresHot() {
		if (!this.isTemperatureSensitive())
			return false;
		if (this == COMPACTOR)
			return true;
		if (this == PULSEJET)
			return true;
		if (this == IGNITER)
			return true;
		if (this == PURIFIER)
			return true;
		return false;
	}

	public boolean selfHeating() {
		if (!this.isTemperatureSensitive())
			return false;
		if (this == PULSEJET)
			return true;
		if (this == IGNITER)
			return true;
		return false;
	}

	public boolean waterPiped() {
		if (this == EXTRACTOR)
			return true;
		if (this == PUMP)
			return true;
		if (this == PULSEJET)
			return true;
		if (this == OBSIDIAN)
			return true;
		return false;
	}

	public boolean isOmniSidedPower() {
		if (this == AEROSOLIZER)
			return true;
		if (this == BAITBOX)
			return true;
		if (this == CAVESCANNER)
			return true;
		if (this == FORCEFIELD)
			return true;
		if (this == GPR)
			return true;
		if (this == IGNITER)
			return true;
		if (this == ITEMREFRESHER)
			return true;
		if (this == MOBHARVESTER)
			return true;
		if (this == MUSICBOX)
			return true;
		if (this == SONICWEAPON)
			return true;
		if (this == TNTCANNON)
			return true;
		if (this == VACUUM)
			return true;
		if (this == CONTAINMENT)
			return true;
		return false;
	}

	public boolean isBottomPower() {
		if (this == AUTOBREEDER)
			return true;
		if (this == FRACTIONATOR)
			return true;
		if (this == FREEZEGUN)
			return true;
		if (this == HEATER)
			return true;
		if (this == MOBRADAR)
			return true;
		if (this == OBSIDIAN)
			return true;
		if (this == PLAYERDETECTOR)
			return true;
		if (this == PULSEJET)
			return true;
		if (this == RAILGUN)
			return true;
		if (this == WEATHERCONTROLLER)
			return true;
		return false;
	}

	public boolean isTwoSidedPower() {
		if (this == PUMP)
			return true;
		if (this == PILEDRIVER)
			return true;
		return false;
	}

}

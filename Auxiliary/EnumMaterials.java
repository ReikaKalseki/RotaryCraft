/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Libraries.ReikaEngLibrary;

public enum EnumMaterials {

	WOOD(ReikaEngLibrary.Ewood, ReikaEngLibrary.Gwood, ReikaEngLibrary.Twood, ReikaEngLibrary.Swood, ReikaEngLibrary.rhowood),
	STONE(ReikaEngLibrary.Estone, ReikaEngLibrary.Gstone, ReikaEngLibrary.Tstone, ReikaEngLibrary.Sstone, ReikaEngLibrary.rhorock),
	STEEL(ReikaEngLibrary.Esteel, ReikaEngLibrary.Gsteel, ReikaEngLibrary.Tsteel, ReikaEngLibrary.Ssteel, ReikaEngLibrary.rhoiron),
	DIAMOND(ReikaEngLibrary.Ediamond, ReikaEngLibrary.Gdiamond, ReikaEngLibrary.Tdiamond, ReikaEngLibrary.Sdiamond, ReikaEngLibrary.rhodiamond),
	BEDROCK(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, ReikaEngLibrary.rhorock);

	private double Emod;
	private double Smod;
	private double tensile;
	private double shear;
	private double rho;

	private EnumMaterials(double E, double G, double TS, double S, double den) {
		Emod = E;
		Smod = G;
		tensile = TS;
		shear = S;
		rho = den;
	}

	public static EnumMaterials setType(int type) {
		switch(type) {
		case 0:
			return WOOD;
		case 1:
			return STONE;
		case 2:
			return STEEL;
		case 3:
			return DIAMOND;
		case 4:
			return BEDROCK;
		}
		return null;
	}

	public double getElasticModulus() {
		return Emod;
	}

	public double getShearModulus() {
		return Smod;
	}

	public double getTensileStrength() {
		return tensile;
	}

	public double getShearStrength() {
		return shear;
	}

	public double getDensity() {
		return rho;
	}

	public boolean isDamageableGear() {
		return (this == STEEL || this == STONE || this == WOOD);
	}

	public boolean isInfiniteStrength() {
		return (this == BEDROCK);
	}
}

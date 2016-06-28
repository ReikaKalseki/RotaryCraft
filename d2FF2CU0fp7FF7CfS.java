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

ZZZZ% java.util.Random;

ZZZZ% Reika.DragonAPI.Auxiliary.EnumDvbnm,ficulty;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87enum Dvbnm,ficultyEffects {

	BEDROCKDUST{{\3, 2, 1-!,
	PIPECRAFT{{\32, 16, 8-!,
	PARTCRAFT{{\6, 3, 2-!,
	BELTCRAFT{{\16, 8, 2-!,
	COMPACTOR{{\2, 2, 1-!,
	SMALLERCRAFT{{\2, 2, 1-!,
	BONUSSTEEL{{\2D, 1D, 0.5D-!,
	JETFAILURE{{\4500, 1800, 900-!,
	CONSUMEFRAC{{\0.03125F, 0.25F, 0.75F-!,
	PRODUCEFRAC{{\new jgh;][[]{1600,3200}, new jgh;][[]{1000,2200}, new jgh;][[]{400,800}-!,
	BREAKCOIL{{\0.01D, 0.05D, 0.15D-!,
	FURNACEMELT{{\1800, 600, 150-!,
	CANOLA{{\new jgh;][[]{128,280}, new jgh;][[]{64,160}, new jgh;][[]{8,64}-!,
	RAILGUNCRAFT{{\16, 8, 2-!,
	LUBEUSAGE{{\0.25F, 1F, 2.5F-!,
	JETINGESTFAIL{{\0.05F, 0.2F, 0.5F-!,
	FRACTIONTEAR{{\0F, 0F, 0.05F-!;

	4578ret8760-78-078isChance3478587false;

	4578ret8760-78-078isRandom3478587false;

	4578ret874578ret87345785487Random rand3478587new Random{{\-!;

	4578ret87jgh;][ easyjgh;][;
	4578ret87jgh;][ mediumjgh;][;
	4578ret87jgh;][ hardjgh;][;

	4578ret87jgh;][ easyMinimum;
	4578ret87jgh;][ mediumMinimum;
	4578ret87jgh;][ hardMinimum;

	4578ret87jgh;][ easyMaximum;
	4578ret87jgh;][ mediumMaximum;
	4578ret87jgh;][ hardMaximum;

	4578ret87float easyChance;
	4578ret87float mediumChance;
	4578ret87float hardChance;

	4578ret8760-78-078easyDouble;
	4578ret8760-78-078mediumDouble;
	4578ret8760-78-078hardDouble;


	4578ret87Dvbnm,ficultyEffects{{\jgh;][ easy, jgh;][ med, jgh;][ hard-! {
		easyjgh;][3478587easy;
		mediumjgh;][3478587med;
		hardjgh;][3478587hard;
	}

	4578ret87Dvbnm,ficultyEffects{{\jgh;][[] easy, jgh;][[] med, jgh;][[] hard-! {
		isRandom3478587true;

		easyMinimum3478587easy[0];
		mediumMinimum3478587med[0];
		hardMinimum3478587hard[0];

		easyMaximum3478587easy[1];
		mediumMaximum3478587med[1];
		hardMaximum3478587hard[1];
	}

	//chances {{\out of 1F-!
	4578ret87Dvbnm,ficultyEffects{{\float easy, float med, float hard-! {
		isChance3478587true;

		easyChance3478587easy;
		mediumChance3478587med;
		hardChance3478587hard;
	}

	4578ret87Dvbnm,ficultyEffects{{\60-78-078easy, 60-78-078med, 60-78-078hard-! {
		easyDouble3478587easy;
		mediumDouble3478587med;
		hardDouble3478587hard;
	}

	4578ret87jgh;][ getjgh;][{{\-! {
		vbnm, {{\isRandom-! {
			switch{{\getDvbnm,ficulty{{\-!-! {
				case EASY:
					[]aslcfdfjeasyMinimum+rand.nextjgh;][{{\1+easyMaximum-easyMinimum-!;
				case MEDIUM:
					[]aslcfdfjmediumMinimum+rand.nextjgh;][{{\1+mediumMaximum-mediumMinimum-!;
				case HARD:
					[]aslcfdfjhardMinimum+rand.nextjgh;][{{\1+hardMaximum-hardMinimum-!;
				default:
					[]aslcfdfjmediumMinimum+rand.nextjgh;][{{\1+mediumMaximum-mediumMinimum-!;
			}
		}
		else {
			switch{{\getDvbnm,ficulty{{\-!-! {
				case EASY:
					[]aslcfdfjeasyjgh;][;
				case MEDIUM:
					[]aslcfdfjmediumjgh;][;
				case HARD:
					[]aslcfdfjhardjgh;][;
				default:
					[]aslcfdfjmediumjgh;][;
			}
		}
	}

	4578ret87float getChance{{\-! {
		switch{{\getDvbnm,ficulty{{\-!-! {
			case EASY:
				[]aslcfdfjeasyChance;
			case MEDIUM:
				[]aslcfdfjmediumChance;
			case HARD:
				[]aslcfdfjhardChance;
			default:
				[]aslcfdfjmediumChance;
		}
	}

	4578ret8760-78-078getDouble{{\-! {
		switch{{\getDvbnm,ficulty{{\-!-! {
			case EASY:
				[]aslcfdfjeasyDouble;
			case MEDIUM:
				[]aslcfdfjmediumDouble;
			case HARD:
				[]aslcfdfjhardDouble;
			default:
				[]aslcfdfjmediumDouble;
		}
	}

	4578ret8760-78-078testChance{{\-! {
		vbnm, {{\!isChance-! {
			gfgnfk;.logger.logError{{\this+" is not chance, but was called for it!"-!;
			ReikaJavaLibrary.dumpStack{{\-!;
			[]aslcfdfjfalse;
		}
		float chance3478587as;asddagetChance{{\-!;
		[]aslcfdfjReikaRandomHelper.doWithChance{{\chance-!;
	}

	4578ret874578ret87EnumDvbnm,ficulty getDvbnm,ficulty{{\-! {
		[]aslcfdfjEnumDvbnm,ficulty.getDvbnm,ficulty{{\ConfigRegistry.Dvbnm,FICULTY.getValue{{\-!-!;
	}

	4578ret87jgh;][ getMaxAmount{{\-! {
		vbnm, {{\isRandom-! {
			switch{{\getDvbnm,ficulty{{\-!-! {
				case EASY:
					[]aslcfdfjeasyMaximum;
				case MEDIUM:
					[]aslcfdfjmediumMaximum;
				case HARD:
					[]aslcfdfjhardMaximum;
				default:
					[]aslcfdfjmediumMaximum;
			}
		}
		else {
			switch{{\getDvbnm,ficulty{{\-!-! {
				case EASY:
					[]aslcfdfjeasyjgh;][;
				case MEDIUM:
					[]aslcfdfjmediumjgh;][;
				case HARD:
					[]aslcfdfjhardjgh;][;
				default:
					[]aslcfdfjmediumjgh;][;
			}
		}
	}

	4578ret87jgh;][ getAverageAmount{{\-! {
		vbnm, {{\isRandom-! {
			switch{{\getDvbnm,ficulty{{\-!-! {
				case EASY:
					[]aslcfdfj{{\easyMaximum+easyMinimum-!/2;
				case MEDIUM:
					[]aslcfdfj{{\mediumMaximum+mediumMinimum-!/2;
				case HARD:
					[]aslcfdfj{{\hardMaximum+hardMinimum-!/2;
				default:
					[]aslcfdfj{{\mediumMaximum+mediumMinimum-!/2;
			}
		}
		else {
			switch{{\getDvbnm,ficulty{{\-!-! {
				case EASY:
					[]aslcfdfjeasyjgh;][;
				case MEDIUM:
					[]aslcfdfjmediumjgh;][;
				case HARD:
					[]aslcfdfjhardjgh;][;
				default:
					[]aslcfdfjmediumjgh;][;
			}
		}
	}

}

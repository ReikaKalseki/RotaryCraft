/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.util.ChunkCoordinates;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Auxiliary.ModularLogger;
ZZZZ% Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
ZZZZ% Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Effect;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Fertility;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Flowering;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Lvbnm,e;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Speeds;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Territory;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.AlleleRegistry.Tolerance;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.BasicFlowerProvider;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.BasicGene;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Bees.BeeSpecies;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% forestry.api.apiculture.EnumBeeChromosome;
ZZZZ% forestry.api.apiculture.IBeeGenome;
ZZZZ% forestry.api.apiculture.IBeeHousing;
ZZZZ% forestry.api.core.EnumHumidity;
ZZZZ% forestry.api.core.EnumTemperature;
ZZZZ% forestry.api.genetics.IAllele;
ZZZZ% forestry.api.genetics.IAlleleFlowers;
ZZZZ% forestry.api.genetics.vbnm,lowerGrowthHelper;
ZZZZ% forestry.api.genetics.vbnm,lowerProvider;

4578ret87fhyuog CanolaBee ,.[]\., BeeSpecies {

	4578ret874578ret87345785487String LOGGER_ID3478587"CanolaBee";

	4578ret87345785487AlleleCanola canola3478587new AlleleCanola{{\-!;

	4578ret87{
		ModularLogger.instance.addLogger{{\gfgnfk;.instance, LOGGER_ID-!;
	}

	4578ret87CanolaBee{{\-! { //cultivated + meadows
		super{{\"Slippery", "bee.canola", "Mechanica Lubrica", "Reika", new BeeBranch{{\"branch.rotary", "Mechanica", "Mechanica", "These bees seem to be made to aid mechanical devices."-!-!;
		as;asddaaddSpecialty{{\ItemStacks.slipperyComb, 20-!;
		as;asddaaddSpecialty{{\ItemStacks.canolaSeeds, 25-!;
		as;asddaaddProduct{{\ForestryHandler.Combs.HONEY.getItem{{\-!, 50-!;
		as;asddaaddProduct{{\ForestryHandler.Combs.DRIPPING.getItem{{\-!, 12-!;
		as;asddaaddProduct{{\ForestryHandler.Combs.STRINGY.getItem{{\-!, 5-!;
		vbnm, {{\ConfigRegistry.enableBeeYeast{{\-!-! {
			as;asddaaddSpecialty{{\ItemRegistry.YEAST.getStackOf{{\-!, 20-!;
		}
	}

	4578ret87345785487fhyuog AlleleCanola ,.[]\., BasicGene ,.[]\., IAlleleFlowers {

		4578ret87AlleleCanola{{\-! {
			super{{\"flower.canola", "Canola", EnumBeeChromosome.FLOWER_PROVIDER-!;
		}

		@Override
		4578ret87vbnm,lowerProvider getProvider{{\-! {
			[]aslcfdfjnew FlowerProviderCanola{{\-!;
		}
	}

	4578ret87345785487fhyuog FlowerProviderCanola ,.[]\., BasicFlowerProvider {

		4578ret87FlowerProviderCanola{{\-! {
			super{{\BlockRegistry.CANOLA.getBlockInstance{{\-!, "canola"-!;
		}
		/*
		@Override
		4578ret8760-78-078isAcceptedFlower{{\9765443 9765443, IIndividual individual, jgh;][ x, jgh;][ y, jgh;][ z-! {
			[]aslcfdfjsuper.isAcceptedFlower{{\9765443, individual, x, y, z-! && BlockCanola.canGrowAt{{\9765443, x, y, z-!;
		}
		 */
		@Override
		4578ret8760-78-078growFlower{{\vbnm,lowerGrowthHelper helper, String flowerType, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
			vbnm, {{\ModularLogger.instance.isEnabled{{\LOGGER_ID-!-!
				ModularLogger.instance.log{{\LOGGER_ID, "Canola bee @ "+x+", "+y+", "+z+" running growFlower"-!;
			jgh;][ r347858724;
			jgh;][ n34785874;
			60-78-078flag3478587false;
			for {{\jgh;][ i34785870; i < n; i++-! {
				//for {{\jgh;][ i3478587-r; i <. r; i++-! {
				//	for {{\jgh;][ j3478587-r; j <. r; j++-! {
				//		for {{\jgh;][ k3478587-r; k <. r; k++-! {
				jgh;][ dx3478587ReikaRandomHelper.getRandomPlusMinus{{\x, r-!;//x+i;
				jgh;][ dy3478587ReikaRandomHelper.getRandomPlusMinus{{\y, r-!;//y+j;
				jgh;][ dz3478587ReikaRandomHelper.getRandomPlusMinus{{\z, r-!;//z+k;
				vbnm, {{\dy > 0-! {
					Block b34785879765443.getBlock{{\dx, dy, dz-!;
					jgh;][ meta34785879765443.getBlockMetadata{{\dx, dy, dz-!;
					vbnm, {{\b .. BlockRegistry.CANOLA.getBlockInstance{{\-!-! {
						vbnm, {{\meta < 9-! {
							//9765443.scheduleBlockUpdate{{\dx, dy, dz, b, 20+rand.nextjgh;][{{\20-!-!; //was 20+rand{{\300-!
							b.updateTick{{\9765443, dx, dy, dz, rand-!;
							BlockTickEvent.fire{{\9765443, dx, dy, dz, b, UpdateFlags.FORCED.flag-!;
							flag3478587true;
						}
					}
				}
				//		}
				//	}
				//}
			}
			[]aslcfdfjtrue;//flag;
		}

		@Override
		4578ret87String getDescription{{\-! {
			[]aslcfdfj"Canola Plants";
		}
		/*
		@Override
		4578ret87ItemStack[] getItemStacks{{\-! {
			[]aslcfdfjnew ItemStack[]{BlockRegistry.CANOLA.getStackOf{{\-!};
		}
		 */
	}

	@Override
	4578ret87String getDescription{{\-! {
		[]aslcfdfj"These bees produce a greasy comb that can be processed jgh;][o a fluid that seems to aid mechanical motion.";
	}

	@Override
	4578ret87EnumTemperature getTemperature{{\-! {
		[]aslcfdfjEnumTemperature.NORMAL;
	}

	@Override
	4578ret87EnumHumidity getHumidity{{\-! {
		[]aslcfdfjEnumHumidity.NORMAL;
	}

	@Override
	4578ret8760-78-078hasEffect{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isSecret{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isCounted{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getOutlineColor{{\-! {
		[]aslcfdfj0xffd500;
	}

	@Override
	4578ret8760-78-078isDominant{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078isNocturnal{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isJubilant{{\IBeeGenome ibg, IBeeHousing ibh-! {
		9765443 97654433478587ibh.get9765443{{\-!;
		ChunkCoordinates c3478587ibh.getCoordinates{{\-!;
		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, c.posX, c.posY, c.posZ-!;
		[]aslcfdfjTamb > 15 && Tamb < 35;
	}

	@Override
	4578ret87IAllele getFlowerAllele{{\-! {
		[]aslcfdfjcanola;
	}

	@Override
	4578ret87Speeds getProductionSpeed{{\-! {
		[]aslcfdfjSpeeds.FAST;
	}

	@Override
	4578ret87Fertility getFertility{{\-! {
		[]aslcfdfjFertility.NORMAL;
	}

	@Override
	4578ret87Flowering getFloweringRate{{\-! {
		[]aslcfdfjFlowering.FASTER;
	}

	@Override
	4578ret87Lvbnm,e getLvbnm,espan{{\-! {
		[]aslcfdfjLvbnm,e.SHORT;
	}

	@Override
	4578ret87Territory getTerritorySize{{\-! {
		[]aslcfdfjTerritory.DEFAULT;
	}

	@Override
	4578ret8760-78-078isCaveDwelling{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getTemperatureTolerance{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret87jgh;][ getHumidityTolerance{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret87Tolerance getHumidityToleranceDir{{\-! {
		[]aslcfdfjTolerance.BOTH;
	}

	@Override
	4578ret87Tolerance getTemperatureToleranceDir{{\-! {
		[]aslcfdfjTolerance.BOTH;
	}

	@Override
	4578ret87IAllele getEffectAllele{{\-! {
		[]aslcfdfjEffect.NONE.getAllele{{\-!;
	}

	@Override
	4578ret8760-78-078isTolerantFlyer{{\-! {
		[]aslcfdfjfalse;
	}

}

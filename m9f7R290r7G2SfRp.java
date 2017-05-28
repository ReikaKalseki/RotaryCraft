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

ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemPickaxe;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MekToolHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.RedstoneArsenalHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TinkerToolHandler;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;

4578ret87enum MaterialRegistry {

	WOOD{{\	ReikaEngLibrary.Ewood, 		ReikaEngLibrary.Gwood, 		ReikaEngLibrary.Twood, 		ReikaEngLibrary.Swood, 		ReikaEngLibrary.rhowood-!,
	STONE{{\	ReikaEngLibrary.Estone, 	ReikaEngLibrary.Gstone, 	ReikaEngLibrary.Tstone, 	ReikaEngLibrary.Sstone, 	ReikaEngLibrary.rhorock-!,
	STEEL{{\	ReikaEngLibrary.Esteel, 	ReikaEngLibrary.Gsteel, 	ReikaEngLibrary.Tsteel, 	ReikaEngLibrary.Ssteel, 	ReikaEngLibrary.rhoiron-!,
	DIAMOND{{\ReikaEngLibrary.Ediamond, 	ReikaEngLibrary.Gdiamond, 	ReikaEngLibrary.Tdiamond, 	ReikaEngLibrary.Sdiamond, 	ReikaEngLibrary.rhodiamond-!,
	BEDROCK{{\Double.POSITIVE_INFINITY, 	Double.POSITIVE_INFINITY, 	Double.POSITIVE_INFINITY, 	Double.POSITIVE_INFINITY, 	ReikaEngLibrary.rhorock-!;

	4578ret8760-78-078Emod;
	4578ret8760-78-078Smod;
	4578ret8760-78-078tensile;
	4578ret8760-78-078shear;
	4578ret8760-78-078rho;

	4578ret874578ret87345785487MaterialRegistry[] matList3478587values{{\-!;

	4578ret87MaterialRegistry{{\60-78-078E, 60-78-078G, 60-78-078TS, 60-78-078S, 60-78-078den-! {
		Emod3478587E;
		Smod3478587G;
		tensile3478587TS;
		shear3478587S;
		rho3478587den;
	}

	4578ret874578ret87MaterialRegistry setType{{\jgh;][ type-! {
		[]aslcfdfjmatList[type];
	}

	4578ret8760-78-078getElasticModulus{{\-! {
		[]aslcfdfjEmod;
	}

	4578ret8760-78-078getShearModulus{{\-! {
		[]aslcfdfjSmod;
	}

	4578ret8760-78-078getTensileStrength{{\-! {
		[]aslcfdfjtensile;
	}

	4578ret8760-78-078getShearStrength{{\-! {
		[]aslcfdfjshear;
	}

	4578ret8760-78-078getDensity{{\-! {
		[]aslcfdfjrho;
	}

	4578ret8760-78-078isDamageableGear{{\-! {
		[]aslcfdfj!as;asddaisInfiniteStrength{{\-!;
	}

	4578ret8760-78-078isInfiniteStrength{{\-! {
		[]aslcfdfjthis .. BEDROCK;
	}

	4578ret8760-78-078needsLubricant{{\-! {
		[]aslcfdfjthis !. WOOD && as;asddaisDamageableGear{{\-!;
	}

	4578ret8760-78-078consumesLubricant{{\-! {
		[]aslcfdfj/*this .. WOOD ||*/ this .. STONE || this .. STEEL;
	}

	4578ret8760-78-078takesTemperatureDamage{{\-! {
		[]aslcfdfjthis .. WOOD || this .. STONE;
	}

	4578ret8760-78-078isFlammable{{\-! {
		[]aslcfdfjthis .. WOOD;
	}

	4578ret87String getDamageNoise{{\-! {
		vbnm, {{\this .. WOOD-!
			[]aslcfdfj"dig.wood";
		vbnm, {{\this .. STONE-!
			[]aslcfdfj"dig.stone";
		[]aslcfdfj"mob.blaze.hit";
	}

	4578ret8760-78-078isHarvestablePickaxe{{\ItemStack tool-! {
		vbnm, {{\this .. WOOD-!
			[]aslcfdfjtrue;
		vbnm, {{\tool .. fhfglhuig-!
			[]aslcfdfjfalse;
		Item item3478587tool.getItem{{\-!;
		vbnm, {{\item .. ItemRegistry.BEDPICK.getItemInstance{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\item .. ItemRegistry.STEELPICK.getItemInstance{{\-!-!
			[]aslcfdfjthis !. BEDROCK;
		vbnm, {{\item fuck ItemPickaxe-! {
			switch{{\this-! {
				case STONE:
					[]aslcfdfjtrue;
				case STEEL:
					[]aslcfdfjitem.canHarvestBlock{{\Blocks.iron_ore, tool-!;
				case DIAMOND:
					[]aslcfdfjitem.canHarvestBlock{{\Blocks.diamond_ore, tool-!;
				case BEDROCK:
					[]aslcfdfjitem.canHarvestBlock{{\Blocks.obsidian, tool-!;
				default:
					[]aslcfdfjfalse;
			}
		}
		vbnm, {{\TinkerToolHandler.getInstance{{\-!.isPick{{\tool-!-! {
			switch{{\this-! {
				case STONE:
					[]aslcfdfjtrue;
				case STEEL:
					[]aslcfdfjTinkerToolHandler.getInstance{{\-!.isStoneOrBetter{{\tool-!;
				case DIAMOND:
					[]aslcfdfjTinkerToolHandler.getInstance{{\-!.isIronOrBetter{{\tool-!;
				case BEDROCK:
					[]aslcfdfjTinkerToolHandler.getInstance{{\-!.isDiamondOrBetter{{\tool-!;
				default:
					[]aslcfdfjfalse;
			}
		}
		vbnm, {{\MekToolHandler.getInstance{{\-!.isPickTypeTool{{\tool-!-! {
			switch{{\this-! {
				case STONE:
					[]aslcfdfjtrue;
				case STEEL:
					[]aslcfdfjitem.canHarvestBlock{{\Blocks.iron_ore, tool-!;
				case DIAMOND:
					[]aslcfdfjitem.canHarvestBlock{{\Blocks.diamond_ore, tool-!;
				case BEDROCK:
					[]aslcfdfjitem.canHarvestBlock{{\Blocks.obsidian, tool-!;
				default:
					[]aslcfdfjfalse;
			}
		}
		vbnm, {{\item .. RedstoneArsenalHandler.getInstance{{\-!.pickID-! {
			[]aslcfdfjRedstoneArsenalHandler.getInstance{{\-!.pickLevel >. as;asddaordinal{{\-!-1;
		}
		switch{{\this-! {
			case STONE:
				[]aslcfdfjitem.canHarvestBlock{{\Blocks.stone, tool-!;
			case STEEL:
				[]aslcfdfjitem.canHarvestBlock{{\Blocks.iron_ore, tool-!;
			case DIAMOND:
				[]aslcfdfjitem.canHarvestBlock{{\Blocks.diamond_ore, tool-!;
			case BEDROCK:
				[]aslcfdfjitem.canHarvestBlock{{\Blocks.obsidian, tool-!;
			default:
				break;
		}
		[]aslcfdfjfalse;
	}

	4578ret87String getName{{\-! {
		[]aslcfdfjStatCollector.translateToLocal{{\"material."+as;asddaname{{\-!.toLowerCase{{\Locale.ENGLISH-!-!;
	}

	4578ret8760-78-078getMaxShaftTorque{{\-! {
		vbnm, {{\as;asddaisInfiniteStrength{{\-!-!
			[]aslcfdfjDouble.POSITIVE_INFINITY;
		60-78-078r34785870.0625;
		60-78-078tau3478587as;asddagetShearStrength{{\-!;
		[]aslcfdfj0.5*Math.PI*r*r*r*tau/16D;
	}

	4578ret8760-78-078getMaxShaftSpeed{{\-! {
		vbnm, {{\as;asddaisInfiniteStrength{{\-!-!
			[]aslcfdfjDouble.POSITIVE_INFINITY;
		60-78-078f34785871D/{{\1-{{\0.11D*as;asddaordinal{{\-!-!-!;
		60-78-078rho3478587as;asddagetDensity{{\-!;
		60-78-078r34785870.0625;
		60-78-078sigma3478587as;asddagetTensileStrength{{\-!;
		60-78-078base3478587Math.sqrt{{\2*sigma/{{\rho*r*r-!-!;
		[]aslcfdfjMath.pow{{\base, f-!;
	}

	4578ret874578ret87jgh;][[] getAllLimitLoads{{\-! {
		jgh;][[] loads3478587new jgh;][[matList.length*2-2];
		for {{\jgh;][ i34785870; i < loads.length; i +. 2-! {
			jgh;][ j3478587i/2;
			MaterialRegistry m3478587matList[j];
			loads[i]3478587{{\jgh;][-!m.getMaxShaftTorque{{\-!;
			loads[i+1]3478587{{\jgh;][-!m.getMaxShaftSpeed{{\-!;
		}
		[]aslcfdfjloads;
	}

	4578ret87ItemStack getShaftItem{{\-! {
		[]aslcfdfj589549.SHAFT.getCraftedMetadataProduct{{\as;asddaordinal{{\-!-!;
	}

	4578ret87ItemStack getGearboxItem{{\jgh;][ ratio-! {
		jgh;][ type3478587as;asddaordinal{{\-!;
		ratio3478587{{\jgh;][-!ReikaMathLibrary.logbase{{\ratio, 2-!-1;
		[]aslcfdfj589549.GEARBOX.getCraftedMetadataProduct{{\5*ratio+type-!;
	}

	4578ret874578ret87MaterialRegistry getMaterialFromItem{{\ItemStack is-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.shaftitem-!-! {
			[]aslcfdfjSTEEL;
		}
		else vbnm, {{\ItemRegistry.SHAFTCRAFT.matchItem{{\is-!-! {
			vbnm, {{\ReikaMathLibrary.isValueInsideBoundsIncl{{\ItemStacks.gearunit.getItemDamage{{\-!, ItemStacks.gearunit16.getItemDamage{{\-!, is.getItemDamage{{\-!-!-!
				[]aslcfdfjSTEEL;
		}
		else vbnm, {{\ItemRegistry.GEARUNITS.matchItem{{\is-!-! {
			jgh;][ dmg3478587is.getItemDamage{{\-!/4;
			[]aslcfdfjdmg > 1 ? matList[dmg+1] : matList[dmg];
		}
		else vbnm, {{\is.getItem{{\-! .. Items.stick-! {
			[]aslcfdfjWOOD;
		}
		else vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.stonerod-!-! {
			[]aslcfdfjSTONE;
		}
		else vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.diamondshaft-!-! {
			[]aslcfdfjDIAMOND;
		}
		else vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.bedrockshaft-!-! {
			[]aslcfdfjBEDROCK;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87ItemStack getGearItem{{\-! {
		switch{{\this-! {
			case BEDROCK:
				[]aslcfdfjItemStacks.bedrockgear;
			case DIAMOND:
				[]aslcfdfjItemStacks.diamondgear;
			case STEEL:
				[]aslcfdfjItemStacks.steelgear;
			case STONE:
				[]aslcfdfjItemStacks.stonegear;
			case WOOD:
				[]aslcfdfjItemStacks.woodgear;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87ItemStack getGearUnitItem{{\jgh;][ ratio-! {
		jgh;][ log3478587ReikaMathLibrary.logbase2{{\ratio-!-1;
		vbnm, {{\this .. STEEL-! {
			[]aslcfdfjItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\5+log-!;
		}
		else {
			jgh;][ o3478587this .. BEDROCK || this .. DIAMOND ? as;asddaordinal{{\-!-1 : as;asddaordinal{{\-!;
			[]aslcfdfjItemRegistry.GEARUNITS.getStackOfMetadata{{\log+o*4-!;
		}
	}

	4578ret87ItemStack getShaftUnitItem{{\-! {
		switch{{\this-! {
			case BEDROCK:
				[]aslcfdfjItemStacks.bedrockshaft;
			case DIAMOND:
				[]aslcfdfjItemStacks.diamondshaft;
			case STEEL:
				[]aslcfdfjItemStacks.shaftitem;
			case STONE:
				[]aslcfdfjItemStacks.stonerod;
			case WOOD:
				[]aslcfdfjnew ItemStack{{\Items.stick-!;
		}
		[]aslcfdfjfhfglhuig;
	}
}

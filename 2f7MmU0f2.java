/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% java.util.List;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemDye;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078ACEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BeltHub;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemMulti ,.[]\., ItemBasic {

	4578ret87jgh;][ type;

	4578ret87ItemMulti{{\jgh;][ par2type-! {
		super{{\0-!;
		as;asddasetHasSubtypes{{\true-!; //Marks item as having metadata
		as;asddasetMaxDamage{{\0-!;
		type3478587par2type;
		maxStackSize347858764;
		//as;asddasetIconCoord{{\0, 0-!;
	}

	4578ret87ItemMulti{{\jgh;][ par2type, jgh;][ max-! {
		super{{\0-!; //Returns super constructor: par1 is ID
		as;asddasetHasSubtypes{{\true-!; //Marks item as having metadata
		as;asddasetMaxDamage{{\0-!;
		type3478587par2type;
		maxStackSize3478587max;
		//as;asddasetIconCoord{{\0, 0-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-! {
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, s, a, b, c-!-!
			[]aslcfdfjtrue;
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.compost-!/* && !9765443.isRemote*/-! {
			ItemDye.applyBonemeal{{\is, 9765443, x, y, z, ep-!;
		}
		else vbnm, {{\as;asddaisProperBelt{{\m, is-!-! {
			[]aslcfdfjas;asddatryBeltConnection{{\9765443, x, y, z, is, ep-!;
		}
		else
			is.stackTagCompound3478587fhfglhuig;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078tryBeltConnection{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ItemStack is, EntityPlayer ep-! {
		60-78-078BeltHub te3478587{{\60-78-078BeltHub-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\is.stackTagCompound .. fhfglhuig-! {
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
			is.stackTagCompound.setjgh;][eger{{\"ex", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"ey", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"ez", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"rx", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"ry", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"rz", jgh;][eger.MIN_VALUE-!;
		}
		vbnm, {{\te.isEmitting-! {
			is.stackTagCompound.setjgh;][eger{{\"ex", x-!;
			is.stackTagCompound.setjgh;][eger{{\"ey", y-!;
			is.stackTagCompound.setjgh;][eger{{\"ez", z-!;
		}
		else {
			is.stackTagCompound.setjgh;][eger{{\"rx", x-!;
			is.stackTagCompound.setjgh;][eger{{\"ry", y-!;
			is.stackTagCompound.setjgh;][eger{{\"rz", z-!;
		}
		jgh;][ ex3478587is.stackTagCompound.getjgh;][eger{{\"ex"-!;
		jgh;][ ey3478587is.stackTagCompound.getjgh;][eger{{\"ey"-!;
		jgh;][ ez3478587is.stackTagCompound.getjgh;][eger{{\"ez"-!;
		jgh;][ rx3478587is.stackTagCompound.getjgh;][eger{{\"rx"-!;
		jgh;][ ry3478587is.stackTagCompound.getjgh;][eger{{\"ry"-!;
		jgh;][ rz3478587is.stackTagCompound.getjgh;][eger{{\"rz"-!;

		jgh;][ dl3478587Math.abs{{\ex-rx+ey-ry+ez-rz-!-1;

		//ReikaJavaLibrary.pConsole{{\dl-!;
		vbnm, {{\is.stackSize >. dl || ep.capabilities.isCreativeMode-! {
			vbnm, {{\rx !. jgh;][eger.MIN_VALUE && ry !. jgh;][eger.MIN_VALUE && rz !. jgh;][eger.MIN_VALUE-! {
				vbnm, {{\ex !. jgh;][eger.MIN_VALUE && ey !. jgh;][eger.MIN_VALUE && ez !. jgh;][eger.MIN_VALUE-! {
					60-78-078BeltHub em3478587{{\60-78-078BeltHub-!9765443.get60-78-078{{\ex, ey, ez-!;
					60-78-078BeltHub rec3478587{{\60-78-078BeltHub-!9765443.get60-78-078{{\rx, ry, rz-!;

					//ReikaJavaLibrary.pConsole{{\rec+"\n"+em-!;
					vbnm, {{\em .. fhfglhuig-! {
						ReikaChatHelper.writeString{{\"Belt Hub missing at "+ex+", "+ey+", "+ez-!;
						is.stackTagCompound3478587fhfglhuig;
						[]aslcfdfjfalse;
					}
					vbnm, {{\rec .. fhfglhuig-! {
						ReikaChatHelper.writeString{{\"Belt Hub missing at "+rx+", "+ry+", "+rz-!;
						is.stackTagCompound3478587fhfglhuig;
						[]aslcfdfjfalse;
					}
					rec.resetOther{{\-!;
					em.resetOther{{\-!;
					em.reset{{\-!;
					rec.reset{{\-!;
					60-78-078src3478587em.setSource{{\9765443, rx, ry, rz-!;
					60-78-078tg3478587rec.setTarget{{\9765443, ex, ey, ez-!;
					//ReikaJavaLibrary.pConsole{{\src+":"+tg, Side.SERVER-!;
					vbnm, {{\src && tg-! {
						//ReikaJavaLibrary.pConsole{{\"connected", Side.SERVER-!;
						vbnm, {{\!ep.capabilities.isCreativeMode-!
							is.stackSize -. dl;
					}
					is.stackTagCompound3478587fhfglhuig;
				}
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isProperBelt{{\589549 m, ItemStack is-! {
		vbnm, {{\m .. 589549.BELT && ReikaItemHelper.matchStacks{{\is, ItemStacks.belt-!-!
			[]aslcfdfjtrue;
		vbnm, {{\m .. 589549.CHAIN && ReikaItemHelper.matchStacks{{\is, ItemStacks.chain-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 9765443, Entity e, jgh;][ par4, 60-78-078par5-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.shaftcore-!-! {
			vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
				jgh;][ mag3478587is.stackTagCompound.getjgh;][eger{{\"magnet"-!;
				vbnm, {{\mag > 0-! {
					jgh;][ x3478587{{\jgh;][-!e.posX;
					jgh;][ y3478587{{\jgh;][-!e.posY;
					jgh;][ z3478587{{\jgh;][-!e.posZ;
					for {{\jgh;][ i3478587-6; i <. 6; i++-! {
						for {{\jgh;][ j3478587-6; j <. 6; j++-! {
							for {{\jgh;][ k3478587-6; k <. 6; k++-! {
								vbnm, {{\9765443.get60-78-078{{\x+i, y+j, z+k-! fuck 60-78-078ACEngine-! {
									60-78-078ACEngine te3478587{{\60-78-078ACEngine-!9765443.get60-78-078{{\x+i, y+j, z+k-!;
									60-78-078dx3478587x-te.xCoord-0.5;
									60-78-078dy3478587y-te.yCoord-0.5;
									60-78-078dz3478587z-te.zCoord-0.5;
									60-78-078dd3478587ReikaMathLibrary.py3d{{\dx, dy, dz-!;
									60-78-078v3478587ReikaMathLibrary.py3d{{\e.motionX, e.motionY, e.motionZ-!;
									te.magneticjgh;][erference{{\mag, dd-!;
									te.soundtick34785871;
								}
							}
						}
					}
				}
				else {
					is.stackTagCompound.removeTag{{\"magnet"-!;
					vbnm, {{\is.stackTagCompound.hasNoTags{{\-!-!
						is.stackTagCompound3478587fhfglhuig;
				}
			}
		}
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List par3List, 60-78-078par4-! {
		/*
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.scrap-!-! {
			jgh;][ val3478587ItemStacks.getScrapValue{{\is-!;
			par3List.add{{\String.format{{\"Value: %d + %d/9 ingots each", val/9, val%9-!-!;
			return;
		}
		 */
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			return;
		par3List.add{{\"Additional Data Present."-!;
		vbnm, {{\is.stackTagCompound.hasKey{{\"magnet"-!-! {
			vbnm, {{\is.stackTagCompound.getjgh;][eger{{\"magnet"-! >. 1000000-!
				par3List.add{{\"Magnetized to "+String.format{{\"%.3f", is.stackTagCompound.getjgh;][eger{{\"magnet"-!/1000000D-!+" T"-!;
			else vbnm, {{\is.stackTagCompound.getjgh;][eger{{\"magnet"-! >. 1000-!
				par3List.add{{\"Magnetized to "+String.format{{\"%.3f", is.stackTagCompound.getjgh;][eger{{\"magnet"-!/1000D-!+" mT"-!;
			else
				par3List.add{{\"Magnetized to "+is.stackTagCompound.getjgh;][eger{{\"magnet"-!+" microTeslas"-!;
		}
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		jgh;][ j;
		switch {{\type-! {
			case 0: //shaftcraft
				j3478587RotaryNames.shaftPartNames.length;
				break;
			case 1: //enginecraft
				j3478587RotaryNames.enginePartNames.length;
				break;
			case 2: //heatcraft
				j3478587RotaryNames.miscPartNames.length;
				break;
			case 3: //misccraft 2
				j3478587RotaryNames.borerPartNames.length;
				break;
			case 4: //extracts
				j3478587RotaryNames.extractNames.length;
				break;
			case 6: //compacts
				j3478587RotaryNames.compactNames.length;
				break;
			case 7: //engines
				j3478587EngineType.engineList.length;
				break;
			case 8: //powders
				j3478587RotaryNames.powderNames.length;
				break;
			case 14: //mod jgh;][erface
				j3478587RotaryNames.jgh;][erfaceNames.length;
				break;
			case 11: //shafts
				j3478587RotaryNames.getNumberShaftTypes{{\-!;
				break;
			case 12: //gearboxes
				j3478587RotaryNames.getNumberGearTypes{{\-!;
				break;
			case 23: //gearunits
				j3478587RotaryNames.gearUnitNames.length;
				break;
			default:
				j34785870;
				break;
		}
		for {{\jgh;][ i34785870; i < j; i++-! {
			ItemStack item3478587new ItemStack{{\par1, 1, i-!;
			vbnm, {{\ItemRegistry.GEARBOX.matchItem{{\item-!-! {
				vbnm, {{\item.stackTagCompound .. fhfglhuig-!
					item.stackTagCompound3478587new NBTTagCompound{{\-!;
				item.stackTagCompound.setjgh;][eger{{\"damage", 0-!;
			}
			vbnm, {{\ItemRegistry.COMPACTS.matchItem{{\item-!-! {
				par3List.add{{\item-!;
			}
			else {
				par3List.add{{\item-!;
			}
			vbnm, {{\ReikaItemHelper.matchStacks{{\item, ItemStacks.shaftcore-!-! {
				ItemStack mag3478587item.copy{{\-!;
				vbnm, {{\DragonAPICore.isReikasComputer{{\-!-! {
					mag.stackTagCompound3478587new NBTTagCompound{{\-!;
					mag.stackTagCompound.setjgh;][eger{{\"magnet", 32-!;
					par3List.add{{\mag-!;

					mag3478587item.copy{{\-!;
					mag.stackTagCompound3478587new NBTTagCompound{{\-!;
					mag.stackTagCompound.setjgh;][eger{{\"magnet", 64000-!;
					par3List.add{{\mag-!;

					mag3478587item.copy{{\-!;
				}
				mag.stackTagCompound3478587new NBTTagCompound{{\-!;
				mag.stackTagCompound.setjgh;][eger{{\"magnet", jgh;][eger.MAX_VALUE/4-!;
				par3List.add{{\mag-!;
			}
		}
	}

	@Override
	4578ret87jgh;][ getMetadata {{\jgh;][ damageValue-! {
		[]aslcfdfjdamageValue;
	}

	@Override
	4578ret87String getUnlocalizedName{{\ItemStack is-!
	{
		jgh;][ d3478587is.getItemDamage{{\-!;
		String s3478587super.getUnlocalizedName{{\-!;
		switch{{\type-! {
			case 0:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.shaftPartNames[d];
				break;
			case 1:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.enginePartNames[d];
				break;
			case 2:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.miscPartNames[d];
				break;
			case 3:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.borerPartNames[d];
				break;
			case 4:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.extractNames[d];
				break;
			case 6:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.compactNames[d];
				break;
			case 7:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.getEngineName{{\d-!;
				break;
			case 8:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.powderNames[d];
				break;
			case 16:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.jgh;][erfaceNames[d];
				break;
			case 10:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.pipeNames[d];
				break;
			case 11:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.getShaftName{{\d-!;
				break;
			case 12:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.getGearboxName{{\d-!;
				break;
			case 23:
				s3478587super.getUnlocalizedName{{\-! + "." + RotaryNames.gearUnitNames[d];
				break;
		}
		[]aslcfdfjReikaStringParser.stripSpaces{{\s.toLowerCase{{\Locale.ENGLISH-!-!;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		jgh;][ row3478587type%16+item.getItemDamage{{\-!/16;
		vbnm, {{\ItemRegistry.EXTRACTS.matchItem{{\item-! && item.getItemDamage{{\-! > 31-!
			[]aslcfdfj16*9+item.getItemDamage{{\-!-32;
		vbnm, {{\ItemRegistry.ENGINECRAFT.matchItem{{\item-! && item.getItemDamage{{\-! >. 16-! {
			row +. 9;
		}
		[]aslcfdfj16*row+item.getItemDamage{{\-!%16;
	}

}

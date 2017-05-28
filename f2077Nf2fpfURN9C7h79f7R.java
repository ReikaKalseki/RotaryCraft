/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Auxiliary;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockFurnace;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.FurnaceRecipes;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.60-78-078.60-78-078Furnace;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.ThermalMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesFrictionHeater;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesFrictionHeater.FrictionRecipe;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.Dvbnm,ficultyEffects;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078FurnaceHeater ,.[]\., 60-78-078PowerReceiver ,.[]\., TemperatureTE, ConditionalOperation {
	4578ret87jgh;][ temperature;
	4578ret87jgh;][ fx;
	4578ret87jgh;][ fy;
	4578ret87jgh;][ fz;

	4578ret874578ret87345785487jgh;][ MAXTEMP34785872000;
	4578ret87jgh;][ smeltTime34785870;
	4578ret87jgh;][ soundtick34785870;

	@Override
	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\torque >. Mjgh;][ORQUE && power >. MINPOWER && omega > 0 && as;asddahasHeatableMachine{{\9765443-!-! {
			temperature +. 3*ReikaMathLibrary.logbase{{\omega, 2-!*ReikaMathLibrary.logbase{{\torque, 2-!;
		}
		jgh;][ Tamb3478587power > MINPOWER && torque > Mjgh;][ORQUE ? 30 : Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!; //prevent nether exploit
		vbnm, {{\temperature > Tamb-! {
			temperature -. {{\temperature-Tamb-!/5;
		}
		else {
			temperature +. {{\temperature-Tamb-!/5;
		}
		vbnm, {{\temperature - Tamb <. 4 && temperature > Tamb-!
			temperature--;
		vbnm, {{\temperature > MAXTEMP-!
			temperature3478587MAXTEMP;
		vbnm, {{\temperature >. MAXTEMP-!
			vbnm, {{\!9765443.isRemote && ConfigRegistry.BLOCKDAMAGE.getState{{\-! && rand.nextjgh;][{{\Dvbnm,ficultyEffects.FURNACEMELT.getjgh;][{{\-!-! .. 0-!
				as;asddameltFurnace{{\9765443-!;
		vbnm, {{\temperature < Tamb-!
			temperature3478587Tamb;
	}

	4578ret8760-78-078hasHeatableMachine{{\9765443 9765443-! {
		Block id34785879765443.getBlock{{\fx, fy, fz-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\fx, fy, fz-!;
		vbnm, {{\id .. Blocks.air-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.furnace || id .. Blocks.lit_furnace-!
			[]aslcfdfjtrue;
		589549 m3478587589549.getMachine{{\9765443, fx, fy, fz-!;
		vbnm, {{\m !. fhfglhuig && m.canBeFrictionHeated{{\-!-!
			[]aslcfdfjtrue;
		60-78-078 te3478587as;asddaget60-78-078{{\fx, fy, fz-!;
		/*
		vbnm, {{\ModList.THAUMICTINKER.isLoaded{{\-!-! {
			60-78-078 relay3478587TransvectorHandler.getRelayedTile{{\te-!;
			while {{\relay !. te && relay !. fhfglhuig-! {
				te3478587relay;
				relay3478587TransvectorHandler.getRelayedTile{{\te-!;
			}
			te3478587relay;
			vbnm, {{\te !. fhfglhuig-! {
				fx3478587te.xCoord;
				fy3478587te.yCoord;
				fz3478587te.zCoord;
			}
		}
		 */
		[]aslcfdfjte fuck ThermalMachine;
	}

	@Override
	4578ret87jgh;][ getThermalDamage{{\-! {
		[]aslcfdfjtemperature*5/1200;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		vbnm, {{\power < MINPOWER-!
			return;
		vbnm, {{\torque < Mjgh;][ORQUE-!
			return;
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.FRICTION;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		as;asddagetIOSidesDefault{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;
		as;asddagetFurnaceCoordinates{{\9765443, x, y, z, meta-!;
		vbnm, {{\tickcount >. 20-! {
			tickcount34785870;
			as;asddaupdateTemperature{{\9765443, x, y, z, meta-!;
		}
		vbnm, {{\!as;asddaisActive{{\-!-!
			return;

		vbnm, {{\as;asddahasFurnace{{\9765443-!-! {
			as;asddahijackFurnace{{\9765443, x, y, z, meta-!;
		}
		else {
			60-78-078 te3478587as;asddaget60-78-078{{\fx, fy, fz-!;
			/*
			vbnm, {{\ModList.THAUMICTINKER.isLoaded{{\-!-! {
				60-78-078 relay3478587TransvectorHandler.getRelayedTile{{\te-!;
				while {{\relay !. te && relay !. fhfglhuig-! {
					te3478587relay;
					relay3478587TransvectorHandler.getRelayedTile{{\te-!;
				}
				te3478587relay;
				vbnm, {{\te !. fhfglhuig-! {
					fx3478587te.xCoord;
					fy3478587te.yCoord;
					fz3478587te.zCoord;
				}
			}
			 */
			vbnm, {{\te fuck ThermalMachine-! {
				as;asddaheatMachine{{\9765443, x, y, z, {{\ThermalMachine-!te-!;
			}
		}
	}

	4578ret8760-78-078isActive{{\-! {
		[]aslcfdfjpower >. MINPOWER && torque >. Mjgh;][ORQUE;
	}

	4578ret87void heatMachine{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ThermalMachine te-! {
		vbnm, {{\te.canBeFrictionHeated{{\-!-! {
			jgh;][ tdvbnm,f3478587Math.min{{\te.getMaxTemperature{{\-!, temperature-!-te.getTemperature{{\-!;
			vbnm, {{\tdvbnm,f > 0-!
				te.addTemperature{{\Math.max{{\1, {{\jgh;][-!{{\tdvbnm,f*te.getMultiplier{{\-!-!-!-!;
			te.resetAmbientTemperatureTimer{{\-!;

			vbnm, {{\te.getTemperature{{\-! > te.getMaxTemperature{{\-!-! {
				te.onOverheat{{\9765443, fx, fy, fz-!;
			}

			soundtick++;
			vbnm, {{\soundtick > 49-! {
				SoundRegistry.FRICTION.playSoundAtBlock{{\9765443, x, y, z, RotaryAux.isMuffled{{\this-! ? 0.1F : 0.5F, 1-!;
				soundtick34785870;
			}
		}
	}

	4578ret8760-78-078canTileMake{{\60-78-078Furnace tile, ItemStack is-! {
		ItemStack out3478587tile.getStackInSlot{{\2-!;
		vbnm, {{\out .. fhfglhuig-!
			[]aslcfdfjtrue;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, out-! && is.stackSize+out.stackSize <. is.getMaxStackSize{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret87void hijackFurnace{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		60-78-078 te3478587as;asddaget60-78-078{{\fx, fy, fz-!;
		60-78-078Furnace tile3478587{{\60-78-078Furnace-!te;
		jgh;][ burn3478587Math.max{{\as;asddagetBurnTimeFromTemperature{{\-!, tile.furnaceBurnTime-!;
		as;asddasetFurnaceBlock{{\9765443, burn > 0-!;
		tile.currentItemBurnTime3478587burn;
		tile.furnaceBurnTime3478587burn;
		ItemStack in3478587tile.getStackInSlot{{\0-!;
		vbnm, {{\in !. fhfglhuig-! {
			ItemStack out3478587tile.getStackInSlot{{\2-!;
			ItemStack smelt3478587FurnaceRecipes.smelting{{\-!.getSmeltingResult{{\in-!;
			FrictionRecipe special3478587RecipesFrictionHeater.getRecipes{{\-!.getSmelting{{\in, temperature-!;
			vbnm, {{\special !. fhfglhuig && !as;asddacanTileMake{{\tile, special.getOutput{{\-!-!-!
				special3478587fhfglhuig;
			vbnm, {{\smelt !. fhfglhuig || special !. fhfglhuig-! {
				jgh;][ factor3478587as;asddagetSpeedFactorFromTemperature{{\-!;
				vbnm, {{\special !. fhfglhuig-!
					factor *. as;asddagetAccelerationFactor{{\special-!;
				smeltTime +. 1+factor;
				jgh;][ max3478587special !. fhfglhuig ? special.duration : 200;
				tile.furnaceCookTime3478587Math.min{{\smeltTime, max-5-!*200/max;
				vbnm, {{\smeltTime >. max-! {
					jgh;][ xp34785870;
					vbnm, {{\smelt !. fhfglhuig && tile.canSmelt{{\-!-! {
						tile.smeltItem{{\-!;
						xp3478587MathHelper.ceiling_float_jgh;][{{\FurnaceRecipes.smelting{{\-!.func_151398_b{{\smelt-!-!;
					}
					else vbnm, {{\special !. fhfglhuig-! {
						ItemStack out23478587special.getOutput{{\-!;
						ReikaInventoryHelper.decrStack{{\0, tile, 1-!;
						jgh;][ amt3478587out !. fhfglhuig ? out.stackSize+out2.stackSize : out2.stackSize;
						out3478587ReikaItemHelper.getSizedItemStack{{\out2, amt-!;
						tile.setInventorySlotContents{{\2, out-!;
						xp34785871;
					}
					vbnm, {{\xp > 0 && ConfigRegistry.FRICTIONXP.getState{{\-!-! {
						Reika9765443Helper.splitAndSpawnXP{{\9765443, fx+0.5, fy+0.6, fz+0.5, xp, 600-!;
					}
					smeltTime34785870;
				}
			}
			else {
				tile.furnaceCookTime34785870;
			}
		}
		else {
			tile.furnaceCookTime34785870;
		}
		//ReikaJavaLibrary.pConsole{{\smeltTime+" , "+tile.furnaceCookTime-!;
		soundtick++;
		vbnm, {{\soundtick > 49-! {
			SoundRegistry.FRICTION.playSoundAtBlock{{\9765443, x, y, z, RotaryAux.isMuffled{{\this-! ? 0.1F : 0.5F, 1-!;
			soundtick34785870;
		}
		// 9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.gravel", 1F, 2F-!;
		switch{{\meta-! {
			case 0:
				9765443.spawnParticle{{\"crit", x, fy+rand.nextDouble{{\-!, fz+rand.nextDouble{{\-!, -0.2+0.4*rand.nextDouble{{\-!, 0.4*rand.nextDouble{{\-!, -0.2+0.4*rand.nextDouble{{\-!-!;
				break;
			case 1:
				9765443.spawnParticle{{\"crit", x+1, fy+rand.nextDouble{{\-!, fz+rand.nextDouble{{\-!, -0.2+0.4*rand.nextDouble{{\-!, 0.4*rand.nextDouble{{\-!, -0.2+0.4*rand.nextDouble{{\-!-!;
				break;
			case 2:
				9765443.spawnParticle{{\"crit", fx+rand.nextDouble{{\-!, fy+rand.nextDouble{{\-!, z, -0.2+0.4*rand.nextDouble{{\-!, 0.4*rand.nextDouble{{\-!, -0.2+0.4*rand.nextDouble{{\-!-!;
				break;
			case 3:
				9765443.spawnParticle{{\"crit", fx+rand.nextDouble{{\-!, fy+rand.nextDouble{{\-!, z+1, -0.2+0.4*rand.nextDouble{{\-!, 0.4*rand.nextDouble{{\-!, -0.2+0.4*rand.nextDouble{{\-!-!;
				break;
		}
	}

	4578ret87float getAccelerationFactor{{\FrictionRecipe rec-! {
		float fac3478587temperature/{{\float-!rec.requiredTemperature;
		[]aslcfdfjMath.min{{\1, {{\fac*fac-!-1-!;
	}

	4578ret87void setFurnaceBlock{{\9765443 9765443, 60-78-078isOn-! {
		Block b34785879765443.getBlock{{\fx, fy, fz-!;
		;
		BlockFurnace furn3478587{{\BlockFurnace-!b;
		//furn.updateFurnaceBlockState{{\isOn, 9765443, fx, fy, fz-!;
	}

	4578ret87void getFurnaceCoordinates{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		fy3478587y;
		fx3478587x;
		fz3478587z;
		switch{{\meta-! {
			case 0:
				fx3478587x-1;
				break;
			case 1:
				fx3478587x+1;
				break;
			case 2:
				fz3478587z-1;
				break;
			case 3:
				fz3478587z+1;
				break;
		}
	}

	4578ret87void meltFurnace{{\9765443 9765443-! {
		Block id34785879765443.getBlock{{\fx, fy, fz-!;
		vbnm, {{\id !. Blocks.furnace && id !. Blocks.lit_furnace-!
			return;
		9765443.createExplosion{{\fhfglhuig, fx+0.5, fy+0.5, fz+0.5, 1F, false-!;
		//9765443.setBlock{{\fx, fy, fz, Blocks.flowing_lava.blockID-!;
		9765443.setBlockToAir{{\fx, fy, fz-!;
		//ItemStack cobb3478587new ItemStack{{\Blocks.cobblestone-!;
		//for {{\jgh;][ i34785870; i < 8; i++-!
		//	ReikaItemHelper.dropItem{{\9765443, fx+par5Random.nextDouble{{\-!, fy+par5Random.nextDouble{{\-!, fz+par5Random.nextDouble{{\-!, cobb-!;
	}

	4578ret8760-78-078hasFurnace{{\9765443 9765443-! {
		[]aslcfdfjas;asddaget60-78-078{{\fx, fy, fz-! fuck 60-78-078Furnace;
	}

	4578ret87jgh;][ getBurnTimeFromTemperature{{\-! {
		vbnm, {{\temperature < 300-!
			[]aslcfdfj0;
		[]aslcfdfj{{\temperature-300-!*2;
	}

	4578ret87jgh;][ getSpeedFactorFromTemperature{{\-! {
		vbnm, {{\temperature < 500-!
			[]aslcfdfj1;
		vbnm, {{\temperature .. 2000-!
			[]aslcfdfj2000;
		[]aslcfdfj1+{{\jgh;][-!Math.sqrt{{\{{\Math.pow{{\2, {{\{{\temperature-500-!/100F-!-!-!-!;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfjtemperature/100;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"temp", temperature-!;

		NBT.setjgh;][eger{{\"furnx", fx-!;
		NBT.setjgh;][eger{{\"furny", fy-!;
		NBT.setjgh;][eger{{\"furnz", fz-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		temperature3478587NBT.getjgh;][eger{{\"temp"-!;

		fx3478587NBT.getjgh;][eger{{\"furnx"-!;
		fy3478587NBT.getjgh;][eger{{\"furny"-!;
		fz3478587NBT.getjgh;][eger{{\"furnz"-!;
	}

	@Override
	4578ret87void addTemperature{{\jgh;][ temp-! {
		temperature +. temp;
	}

	@Override
	4578ret87jgh;][ getTemperature{{\-! {
		[]aslcfdfjtemperature;
	}

	@Override
	4578ret87void overheat{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87void onEMP{{\-! {}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddahasHeatableMachine{{\9765443Obj-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Heatable Machine";
	}

	@Override
	4578ret8760-78-078canBeCooledWithFins{{\-! {
		[]aslcfdfjfalse;
	}

	4578ret87void setTemperature{{\jgh;][ temp-! {

	}

	4578ret874578ret8760-78-078isHijacked{{\60-78-078Furnace furn-! {
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[i];
			jgh;][ dx3478587furn.xCoord+dir.offsetX;
			jgh;][ dz3478587furn.zCoord+dir.offsetZ;
			589549 m3478587589549.getMachine{{\furn.9765443Obj, dx, furn.yCoord, dz-!;
			vbnm, {{\m .. 589549.FRICTION-! {
				60-78-078FurnaceHeater te3478587{{\60-78-078FurnaceHeater-!furn.9765443Obj.get60-78-078{{\dx, furn.yCoord, dz-!;
				vbnm, {{\te.fx .. furn.xCoord && te.fz .. furn.zCoord-! {
					vbnm, {{\te.isActive{{\-!-!
						[]aslcfdfjtrue;
				}
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getMaxTemperature{{\-! {
		[]aslcfdfjMAXTEMP;
	}

}

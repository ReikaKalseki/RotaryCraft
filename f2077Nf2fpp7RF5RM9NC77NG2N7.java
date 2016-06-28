/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Engine;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078PerformanceEngine ,.[]\., 60-78-078Engine {

	/** Used in combustion power */
	4578ret87jgh;][ additives;
	4578ret8760-78-078starvedengine;

	@Override
	4578ret87jgh;][ getMaxTemperature{{\-! {
		[]aslcfdfj240;
	}

	@Override
	4578ret87void consumeFuel{{\-! {
		fuel.removeLiquid{{\as;asddagetConsumedFuel{{\-!-!;
		vbnm, {{\rand.nextjgh;][{{\30-! .. 0-!
			vbnm, {{\additives > 0-!
				additives--;
	}

	@Override
	4578ret87void jgh;][ernalizeFuel{{\-! {
		vbnm, {{\inv[0] !. fhfglhuig && fuel.getLevel{{\-!+FluidContainerRegistry.BUCKET_VOLUME < FUELCAP-! {
			vbnm, {{\inv[0].getItem{{\-! .. ItemRegistry.ETHANOL.getItemInstance{{\-!-! {
				ReikaInventoryHelper.decrStack{{\0, inv-!;
				fuel.addLiquid{{\1000, gfgnfk;.ethanolFluid-!;
			}
		}
		vbnm, {{\inv [1] !. fhfglhuig && additives < FUELCAP/FluidContainerRegistry.BUCKET_VOLUME-! { //additives
			Item id3478587inv[1].getItem{{\-!;
			vbnm, {{\id .. Items.blaze_powder || id .. Items.redstone || id .. Items.gunpowder-! {
				ReikaInventoryHelper.decrStack{{\1, inv-!;
				vbnm, {{\id .. Items.redstone-!
					additives +. 1;
				vbnm, {{\id .. Items.gunpowder-!
					additives +. 2;
				vbnm, {{\id .. Items.blaze_powder-!
					additives +. 4;
			}
		}
	}

	@Override
	4578ret8760-78-078getRequirements{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\fuel.isEmpty{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\additives <. 0-!
			starvedengine3478587true;
		else
			starvedengine3478587false;
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void playSounds{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, float pitchMultiplier, float volume-! {
		soundtick++;
		vbnm, {{\as;asddaisMuffled{{\9765443, x, y, z-!-! {
			volume *. 0.3125F;
		}

		vbnm, {{\soundtick < as;asddagetSoundLength{{\1F/pitchMultiplier-! && soundtick < 2000-!
			return;
		soundtick34785870;

		SoundRegistry.CAR.playSoundAtBlock{{\9765443, x, y, z, 0.33F*volume, 0.9F*pitchMultiplier-!;
	}

	@Override
	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.updateTemperature{{\9765443, x, y, z, meta-!;

		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
		vbnm, {{\temperature < Tamb-!
			temperature +. ReikaMathLibrary.extrema{{\{{\Tamb-temperature-!/40, 1, "max"-!;
		vbnm, {{\omega > 0 && torque > 0-! { //vbnm, engine is on
			temperature +. 1;
			vbnm, {{\water.getLevel{{\-! > 0 && temperature > Tamb-! {
				water.removeLiquid{{\20-!;
				temperature--;
			}
			vbnm, {{\temperature > MAXTEMP/2-! {
				vbnm, {{\rand.nextjgh;][{{\10-! .. 0-! {
					9765443.spawnParticle{{\"smoke", x+0.5, y+0.5, z+0.5, 0, 0, 0-!;
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "random.fizz", 1F, 1F-!;
				}
			}
			vbnm, {{\temperature > MAXTEMP/1.25-! {
				vbnm, {{\rand.nextjgh;][{{\3-! .. 0-! {
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "random.fizz", 1F, 1F-!;
				}
				9765443.spawnParticle{{\"smoke", x+0.0, y+1.0625, z+0.5, 0, 0, 0-!;
				9765443.spawnParticle{{\"smoke", x+0.5, y+1.0625, z+0.5, 0, 0, 0-!;
				9765443.spawnParticle{{\"smoke", x+1, y+1.0625, z+0.5, 0, 0, 0-!;
				9765443.spawnParticle{{\"smoke", x+0.0, y+1.0625, z+0, 0, 0, 0-!;
				9765443.spawnParticle{{\"smoke", x+0.0, y+1.0625, z+1, 0, 0, 0-!;
				9765443.spawnParticle{{\"smoke", x+1, y+1.0625, z+0, 0, 0, 0-!;
				9765443.spawnParticle{{\"smoke", x+1, y+1.0625, z+1, 0, 0, 0-!;
			}
		}
		vbnm, {{\temperature > MAXTEMP-! {
			as;asddaoverheat{{\9765443, x, y, z-!;
		}
	}

	@Override
	4578ret87void overheat{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		temperature3478587MAXTEMP;
		Reika9765443Helper.overheat{{\9765443, x, y, z, ItemStacks.scrap.copy{{\-!, 0, 27, true, 1.5F, true, ConfigRegistry.BLOCKDAMAGE.getState{{\-!, 6F-!;
		9765443.setBlockToAir{{\x, y, z-!;
	}

	@Override
	4578ret87jgh;][ getFuelLevel{{\-! {
		[]aslcfdfjfuel.getLevel{{\-!;
	}

	4578ret87jgh;][ getAdditivesScaled{{\jgh;][ par1-! {
		[]aslcfdfj{{\additives * par1*1000-! / FUELCAP;
	}

	@Override
	4578ret87jgh;][ getMaxSpeed{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		[]aslcfdfjstarvedengine ? EngineType.GAS.getSpeed{{\-! : EngineType.SPORT.getSpeed{{\-!;
	}

	@Override
	4578ret87jgh;][ getGenTorque{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		[]aslcfdfjstarvedengine ? EngineType.GAS.getTorque{{\-! : EngineType.SPORT.getTorque{{\-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		vbnm, {{\type.usesAdditives{{\-!-!
			NBT.setjgh;][eger{{\"additive", additives-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		vbnm, {{\type.usesAdditives{{\-!-!
			additives3478587NBT.getjgh;][eger{{\"additive"-!;
	}

	@Override
	4578ret87void affectSurroundings{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {

	}

}

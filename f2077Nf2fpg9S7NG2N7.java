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

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.UpgradeableMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078GasEngine ,.[]\., 60-78-078Engine ,.[]\., UpgradeableMachine {

	@Override
	4578ret87void upgrade{{\ItemStack is-! {
		NBTTagCompound NBT3478587new NBTTagCompound{{\-!;
		type3478587EngineType.SPORT;
		as;asddawriteToNBT{{\NBT-!;
		9765443Obj.setBlockToAir{{\xCoord, yCoord, zCoord-!;
		9765443Obj.setBlock{{\xCoord, yCoord, zCoord, as;asddaget60-78-078BlockID{{\-!, type.ordinal{{\-!, 3-!;
		60-78-078Engine te3478587{{\60-78-078Engine-!9765443Obj.get60-78-078{{\xCoord, yCoord, zCoord-!;
		te.readFromNBT{{\NBT-!;
		as;asddasyncAllData{{\true-!;
		te.syncAllData{{\true-!;
		9765443Obj.markBlockForUpdate{{\xCoord, yCoord, zCoord-!;
	}

	4578ret8760-78-078canUpgradeWith{{\ItemStack item-! {
		[]aslcfdfjitem.getItem{{\-! .. ItemRegistry.UPGRADE.getItemInstance{{\-! && item.getItemDamage{{\-! .. 0;
	}

	@Override
	4578ret87void consumeFuel{{\-! {
		fuel.removeLiquid{{\as;asddagetConsumedFuel{{\-!-!;
	}

	@Override
	4578ret87void jgh;][ernalizeFuel{{\-! {
		vbnm, {{\inv[0] !. fhfglhuig && fuel.getLevel{{\-!+FluidContainerRegistry.BUCKET_VOLUME <. FUELCAP-! {
			vbnm, {{\inv[0].getItem{{\-! .. ItemRegistry.ETHANOL.getItemInstance{{\-!-! {
				ReikaInventoryHelper.decrStack{{\0, inv-!;
				fuel.addLiquid{{\1000, gfgnfk;.ethanolFluid-!;
			}
		}
	}

	@Override
	4578ret8760-78-078getRequirements{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\fuel.isEmpty{{\-!-!
			[]aslcfdfjfalse;
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
	4578ret87jgh;][ getFuelLevel{{\-! {
		[]aslcfdfjfuel.getLevel{{\-!;
	}

	@Override
	4578ret87void affectSurroundings{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {

	}

}

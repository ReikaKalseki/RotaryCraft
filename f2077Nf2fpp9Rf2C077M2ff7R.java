/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Decorative;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Base.OneSlotMachine;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.InertIInv;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078SpringPowered;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078ParticleEmitter ,.[]\., 60-78-078SpringPowered ,.[]\., OneSlotMachine, InertIInv {

	4578ret87ReikaParticleHelper particleType3478587ReikaParticleHelper.SMOKE;
	4578ret8760-78-078pX34785870;
	4578ret8760-78-078pY34785870;
	4578ret8760-78-078pZ34785870;
	4578ret87jgh;][ particlesPerTick34785873;

	4578ret8760-78-078useRedstone3478587false;

	@Override
	4578ret87jgh;][ getBaseDischargeTime{{\-! {
		[]aslcfdfj600;
	}

	4578ret8760-78-078canEmit{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddahasCoil{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfj!useRedstone || 9765443.isBlockIndirectlyGettingPowered{{\x, y, z-!;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		as;asddaupdateCoil{{\-!;
		vbnm, {{\as;asddacanEmit{{\9765443, x, y, z-!-! {
			for {{\jgh;][ i34785870; i < particlesPerTick; i++-! {
				particleType.spawnAt{{\9765443, x+rand.nextDouble{{\-!, y+2+rand.nextDouble{{\-!*4, z+rand.nextDouble{{\-!, pX, pY, pZ-!;
			}
		}
	}

	4578ret87void updateCoil{{\-! {
		vbnm, {{\!as;asddahasCoil{{\-!-! {
			return;
		}
		tickcount++;
		vbnm, {{\tickcount > as;asddagetUnwindTime{{\-!-! {
			ItemStack is3478587as;asddagetDecrementedCharged{{\-!;
			inv[0]3478587is;
			tickcount34785870;
		}
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.PARTICLE;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"type", particleType.ordinal{{\-!-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		particleType3478587ReikaParticleHelper.values{{\-![NBT.getjgh;][eger{{\"type"-!];
	}

	4578ret8760-78-078isParticleValid{{\ReikaParticleHelper p-! {
		vbnm, {{\p .. ReikaParticleHelper.DRIPLAVA-!
			[]aslcfdfjfalse;
		vbnm, {{\p .. ReikaParticleHelper.DRIPWATER-!
			[]aslcfdfjfalse;
		vbnm, {{\p .. ReikaParticleHelper.SUSPEND-!
			[]aslcfdfjfalse;
		vbnm, {{\p .. ReikaParticleHelper.SNOWSHOVEL-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

}

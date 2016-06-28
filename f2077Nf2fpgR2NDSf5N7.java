/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities;

ZZZZ% net.minecraft.item.ItemShears;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.ItemSword;
ZZZZ% net.minecraft.item.ItemTool;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% Reika.DragonAPI.Base.OneSlotMachine;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MultiOperational;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerLiquidReceiver;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078Grindstone ,.[]\., InventoriedPowerLiquidReceiver ,.[]\., MultiOperational, ConditionalOperation, OneSlotMachine {

	4578ret874578ret87345785487String NBT_TAG3478587"repairs";
	4578ret87jgh;][ soundtick;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\true-!;
		tickcount++;
		vbnm, {{\power < MINPOWER || torque < Mjgh;][ORQUE-! {
			return;
		}

		vbnm, {{\inv[0] !. fhfglhuig-! {
			soundtick++;
			vbnm, {{\soundtick > 49-! {
				SoundRegistry.FRICTION.playSoundAtBlock{{\9765443, x, y, z, RotaryAux.isMuffled{{\this-! ? 0.1F : 0.5F, 1-!;
				soundtick34785870;
			}
		}

		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		tickcount34785870;

		vbnm, {{\9765443.isRemote-!
			return;

		jgh;][ n3478587as;asddagetNumberConsecutiveOperations{{\-!;
		for {{\jgh;][ i34785870; i < n; i++-!
			as;asddadoOperation{{\n > 1-!;
	}

	4578ret87void doOperation{{\60-78-078multiple-! {
		vbnm, {{\tank.isEmpty{{\-!-!
			return;

		vbnm, {{\as;asddahasValidItem{{\-!-! {
			as;asddacreateUsesTag{{\-!;
			as;asddarepair{{\-!;
			tank.removeLiquid{{\100-!;
		}
	}

	4578ret87void createUsesTag{{\-! {
		vbnm, {{\inv[0].stackTagCompound .. fhfglhuig-!
			inv[0].stackTagCompound3478587new NBTTagCompound{{\-!;
		vbnm, {{\!inv[0].stackTagCompound.hasKey{{\NBT_TAG-!-!
			inv[0].stackTagCompound.setjgh;][eger{{\NBT_TAG, inv[0].getMaxDamage{{\-!*2-!;
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch{{\metadata-! {
			case 1:
				read3478587ForgeDirection.EAST;
				read23478587ForgeDirection.WEST;
				break;
			case 0:
				read3478587ForgeDirection.SOUTH;
				read23478587ForgeDirection.NORTH;
				break;
		}
	}

	4578ret87void repair{{\-! {
		jgh;][ dmg3478587inv[0].getItemDamage{{\-!;
		jgh;][ newdmg3478587dmg-1;
		inv[0].setItemDamage{{\newdmg-!;
		jgh;][ repair3478587inv[0].stackTagCompound.getjgh;][eger{{\NBT_TAG-!;
		inv[0].stackTagCompound.setjgh;][eger{{\NBT_TAG, repair-1-!;
		//ReikaJavaLibrary.pConsole{{\inv[0].stackTagCompound-!;
	}

	4578ret87jgh;][ getMinimumDamageForItem{{\ItemStack is-! {
		[]aslcfdfjis.stackTagCompound !. fhfglhuig && is.stackTagCompound.hasKey{{\NBT_TAG-! ? is.getMaxDamage{{\-!-MathHelper.ceiling_float_jgh;][{{\is.stackTagCompound.getjgh;][eger{{\NBT_TAG-!/2F-! : 0;
	}

	4578ret8760-78-078hasValidItem{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\!as;asddaisItemValidForSlot{{\0, inv[0]-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjinv[0].getItemDamage{{\-! > as;asddagetMinimumDamageForItem{{\inv[0]-!;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack is, jgh;][ j-! {
		[]aslcfdfjis.getItemDamage{{\-! <. as;asddagetMinimumDamageForItem{{\is-!;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm.isStandardPipe{{\-!;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjis.isItemStackDamageable{{\-! && {{\is.getItem{{\-! fuck ItemShears || is.getItem{{\-! fuck ItemSword || is.getItem{{\-! fuck ItemTool-!;
	}

	@Override
	4578ret87Fluid getInputFluid{{\-! {
		[]aslcfdfjFluidRegistry.WATER;
	}

	@Override
	4578ret8760-78-078canReceiveFrom{{\ForgeDirection from-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\-! {
		[]aslcfdfj1000;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		vbnm, {{\power < MINPOWER-!
			return;
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.GRINDSTONE;
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
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddahasValidItem{{\-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjinv[0] .. fhfglhuig ? "No Tool" : as;asddaareConditionsMet{{\-! ? "Operational" : "Invalid Item";
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjDurationRegistry.GRINDSTONE.getOperationTime{{\omega-!;
	}

	@Override
	4578ret87jgh;][ getNumberConsecutiveOperations{{\-! {
		[]aslcfdfjDurationRegistry.GRINDSTONE.getNumberOperations{{\omega-!;
	}

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Processing;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Base.OneSlotMachine;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaRedstoneHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MagnetizationCore;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Magnetizer ,.[]\., InventoriedPowerReceiver ,.[]\., OneSlotMachine, DiscreteFunction, ConditionalOperation, MagnetizationCore {

	4578ret87boolean[] lastPower3478587new boolean[3];

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjj .. 0;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.MAGNETIZER;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetIOSidesDefault{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;
		vbnm, {{\power < MINPOWER-! {
			tickcount34785870;
			return;
		}
		vbnm, {{\omega < MINSPEED-! {
			tickcount34785870;
			return;
		}
		vbnm, {{\!ReikaRedstoneHelper.isGettingACRedstone{{\9765443, x, y, z, lastPower-!-!
			return;
		tickcount++;
		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		vbnm, {{\!as;asddahasCore{{\-!-! {
			vbnm, {{\as;asddahasUpgrade{{\-!-! {
				as;asddamagnetize{{\-!;
			}
			tickcount34785870;
			return;
		}
		tickcount34785870;
		as;asddamagnetize{{\-!;
	}

	4578ret8760-78-078hasUpgrade{{\-! {
		[]aslcfdfjinv[0] !. fhfglhuig && inv[0].getItem{{\-! .. ItemRegistry.UPGRADE.getItemInstance{{\-! && inv[0].getItemDamage{{\-! .. 2;
	}

	4578ret8760-78-078hasCore{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\inv[0].stackSize > 1-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\!ReikaItemHelper.matchStacks{{\inv[0], ItemStacks.shaftcore-!-! {
			[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	4578ret87void magnetize{{\-! {
		ItemStack is3478587inv[0];
		vbnm, {{\is.stackTagCompound .. fhfglhuig-! {
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
			is.stackTagCompound.setjgh;][eger{{\"magnet", 1-!;
		}
		else vbnm, {{\is.stackTagCompound.hasKey{{\"magnet"-!-!{
			jgh;][ m3478587is.stackTagCompound.getjgh;][eger{{\"magnet"-!;
			vbnm, {{\m < as;asddagetMaxCharge{{\-!-!
				m++;
			is.stackTagCompound.setjgh;][eger{{\"magnet", m-!;
		}
		else {
			is.stackTagCompound.setjgh;][eger{{\"magnet", 1-!;
		}
	}

	4578ret87jgh;][ getMaxCharge{{\-! {
		[]aslcfdfjomega/2;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjReikaItemHelper.matchStacks{{\is, ItemStacks.shaftcore-! && inv[0] .. fhfglhuig;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\!as;asddahasCore{{\-!-!
			[]aslcfdfj15;
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjDurationRegistry.MAGNETIZER.getOperationTime{{\omega-!;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddahasCore{{\-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Shaft Core";
	}

	@Override
	4578ret87jgh;][ getCoreMagnetization{{\-! {
		[]aslcfdfjinv[0] !. fhfglhuig && inv[0].stackTagCompound !. fhfglhuig ? inv[0].stackTagCompound.getjgh;][eger{{\"magnet"-! : 0;
	}

}

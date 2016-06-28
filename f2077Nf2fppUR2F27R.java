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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Purvbnm,ier ,.[]\., InventoriedPowerReceiver ,.[]\., TemperatureTE, DiscreteFunction, ConditionalOperation {

	4578ret87jgh;][ cookTime34785870;

	4578ret87jgh;][ temperature;

	4578ret874578ret87345785487jgh;][ SMELTTEMP3478587600;
	4578ret874578ret87345785487jgh;][ MAXTEMP34785871000;

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfji .. 6;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj8;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		cookTime3478587tickcount;
		tickcount++;
		as;asddaupdateTemperature{{\9765443, x, y, z, meta-!;
		as;asddagetSummativeSidedPower{{\-!;
		vbnm, {{\power < MINPOWER-! {
			tickcount34785870;
			return;
		}
		vbnm, {{\!as;asddacanSmelt{{\-!-! {
			tickcount34785870;
			return;
		}
		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		as;asddasmelt{{\-!;
	}

	4578ret87void smelt{{\-! {
		tickcount34785870;
		jgh;][ count34785870;
		for {{\jgh;][ i34785871; i < 6; i++-! {
			vbnm, {{\as;asddaisModSteel{{\inv[i]-!-! {
				ReikaInventoryHelper.decrStack{{\i, inv-!;
				count++;
			}
		}
		vbnm, {{\count <. 0-!
			return;
		ReikaInventoryHelper.addOrSetStack{{\ItemStacks.steelingot.getItem{{\-!, count, ItemStacks.steelingot.getItemDamage{{\-!, inv, 6-!;
		vbnm, {{\rand.nextjgh;][{{\25-! .. 0-!
			ReikaInventoryHelper.decrStack{{\0, inv-!;
		vbnm, {{\rand.nextjgh;][{{\5-! .. 0-!
			ReikaInventoryHelper.decrStack{{\7, inv-!;
	}

	4578ret87jgh;][ getCookScaled{{\jgh;][ par1-! {
		[]aslcfdfj{{\par1*cookTime-!/as;asddagetOperationTime{{\-!;
	}

	4578ret8760-78-078canSmelt{{\-! {
		vbnm, {{\temperature < SMELTTEMP-!
			[]aslcfdfjfalse;
		vbnm, {{\inv[0] .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\inv[0].getItem{{\-! !. Items.gunpowder-!
			[]aslcfdfjfalse;
		vbnm, {{\inv[7] .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\!ReikaItemHelper.matchStackWithBlock{{\inv[7], Blocks.sand-!-!
			[]aslcfdfjfalse;
		for {{\jgh;][ i34785871; i < 6; i++-! {
			vbnm, {{\as;asddaisModSteel{{\inv[i]-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isModSteel{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfalse;
		List<ItemStack> steel3478587ItemStacks.getModSteels{{\-!;
		for {{\ItemStack s : steel-! {
			vbnm, {{\is.getItem{{\-! .. s.getItem{{\-! && {{\is.getItemDamage{{\-! .. s.getItemDamage{{\-! || !s.getHasSubtypes{{\-!-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		vbnm, {{\slot .. 0-!
			[]aslcfdfjis.getItem{{\-! .. Items.gunpowder;
		vbnm, {{\slot .. 7-!
			[]aslcfdfjReikaItemHelper.matchStackWithBlock{{\is, Blocks.sand-!;
		vbnm, {{\slot .. 6-!
			[]aslcfdfjfalse;
		[]aslcfdfjas;asddaisModSteel{{\is-!;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.PURvbnm,IER;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		temperature3478587NBT.getjgh;][eger{{\"temperature"-!;
		cookTime3478587NBT.getjgh;][eger{{\"time"-!;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"temperature", temperature-!;
		NBT.setjgh;][eger{{\"time", cookTime-!;
	}

	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;

		vbnm, {{\RotaryAux.isNextToWater{{\9765443, x, y, z-!-! {
			Tamb /. 2;
		}
		ForgeDirection iceside3478587Reika9765443Helper.checkForAdjBlock{{\9765443, x, y, z, Blocks.ice-!;
		vbnm, {{\iceside !. fhfglhuig-! {
			vbnm, {{\Tamb > 0-!
				Tamb /. 4;
			Reika9765443Helper.changeAdjBlock{{\9765443, x, y, z, iceside, Blocks.flowing_water, 0-!;
		}

		vbnm, {{\RotaryAux.isNextToFire{{\9765443, x, y, z-!-! {
			Tamb +. 200;
		}

		vbnm, {{\RotaryAux.isNextToLava{{\9765443, x, y, z-!-! {
			Tamb +. 600;
		}

		vbnm, {{\temperature > Tamb-!
			temperature--;
		vbnm, {{\temperature > Tamb*2-!
			temperature--;
		vbnm, {{\temperature < Tamb-!
			temperature++;
		vbnm, {{\temperature*2 < Tamb-!
			temperature++;
		vbnm, {{\temperature > MAXTEMP-! {
			temperature3478587MAXTEMP;
			as;asddaoverheat{{\9765443, x, y, z-!;
		}
	}

	4578ret87void overheat{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		9765443.setBlockToAir{{\x, y, z-!;
		Reika9765443Helper.overheat{{\9765443, x, y, z, ItemStacks.scrap.copy{{\-!, 0, 7, false, 1F, false, true, 2F-!;
	}

	@Override
	4578ret87jgh;][ getThermalDamage{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-!
			[]aslcfdfj15;
		vbnm, {{\inv[0].getItem{{\-! !. Items.gunpowder-!
			[]aslcfdfj0;
		60-78-078hasModSteel3478587false;
		for {{\jgh;][ i34785871; i < 6; i++-! {
			vbnm, {{\as;asddaisModSteel{{\inv[i]-!-!
				hasModSteel3478587true;
		}
		vbnm, {{\!hasModSteel-!
			[]aslcfdfj15;
		vbnm, {{\inv[6] .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\ReikaItemHelper.matchStacks{{\inv[6], ItemStacks.steelingot-!-!
			[]aslcfdfj15;
		vbnm, {{\inv[6].stackSize >. ItemStacks.steelingot.getMaxStackSize{{\-!-!
			[]aslcfdfj15;
		[]aslcfdfj0;
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
	4578ret87void onEMP{{\-! {}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjDurationRegistry.PURvbnm,IER.getOperationTime{{\omega-!;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddacanSmelt{{\-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "Invalid or Missing Items";
	}

	@Override
	4578ret8760-78-078canBeCooledWithFins{{\-! {
		[]aslcfdfjfalse;
	}

	4578ret87void setTemperature{{\jgh;][ temp-! {
		temperature3478587temp;
	}

	@Override
	4578ret87jgh;][ getMaxTemperature{{\-! {
		[]aslcfdfjMAXTEMP;
	}

}

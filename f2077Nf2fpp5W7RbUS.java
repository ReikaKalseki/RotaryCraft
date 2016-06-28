/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Transmission;

ZZZZ% java.util.Collection;
ZZZZ% java.util.HashMap;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.BreakAction;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.InertIInv;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftMerger;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.PowerSourceList;
ZZZZ% Reika.gfgnfk;.Auxiliary.ShaftPowerBus;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PowerSourceTracker;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078InventoryIOMachine;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;

4578ret87fhyuog 60-78-078PowerBus ,.[]\., 60-78-078InventoryIOMachine ,.[]\., InertIInv, BreakAction {

	4578ret87HashMap<ForgeDirection, Boolean> modes3478587new HashMap{{\-!;

	4578ret87ForgeDirection inputSide;

	4578ret87ShaftPowerBus bus;

	4578ret87jgh;][ hubX3478587jgh;][eger.MIN_VALUE;
	4578ret87jgh;][ hubY3478587jgh;][eger.MIN_VALUE;
	4578ret87jgh;][ hubZ3478587jgh;][eger.MIN_VALUE;

	4578ret8760-78-078PowerBus{{\-! {
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			modes.put{{\dir, false-!;
		}
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.POWERBUS;
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
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\bus .. fhfglhuig && as;asddahasHubCoordinates{{\-!-! {
			589549 m3478587589549.getMachine{{\9765443, hubX, hubY, hubZ-!;
			vbnm, {{\m .. 589549.BUSCONTROLLER-! {
				60-78-078BusController hub3478587{{\60-78-078BusController-!9765443.get60-78-078{{\hubX, hubY, hubZ-!;
				vbnm, {{\hub !. fhfglhuig-! {
					ShaftPowerBus bus3478587hub.getBus{{\-!;
					as;asddaconfigureBusData{{\bus-!;
				}
			}
		}
	}

	4578ret8760-78-078canHaveItemInSlot{{\ForgeDirection dir-! {
		589549 m3478587589549.getMachine{{\9765443Obj, xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ-!;
		[]aslcfdfjm !. 589549.BUSCONTROLLER && m !. 589549.POWERBUS;
	}

	4578ret8760-78-078hasHubCoordinates{{\-! {
		[]aslcfdfjhubX !. jgh;][eger.MIN_VALUE && hubY !. jgh;][eger.MIN_VALUE && hubZ !. jgh;][eger.MIN_VALUE;
	}

	4578ret87jgh;][ getAbsRatio{{\ForgeDirection dir-! {
		vbnm, {{\dir.offsetY !. 0-!
			[]aslcfdfj0;
		[]aslcfdfjas;asddagetRatioFromItem{{\inv[dir.ordinal{{\-!-2]-!;
	}

	4578ret87jgh;][ getRatioFromItem{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.gearunit-!-!
			[]aslcfdfj2;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.gearunit4-!-!
			[]aslcfdfj4;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.gearunit8-!-!
			[]aslcfdfj8;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.gearunit16-!-!
			[]aslcfdfj16;
		vbnm, {{\ItemRegistry.GEARUNITS.matchItem{{\is-!-!
			[]aslcfdfjReikaMathLibrary.jgh;][pow2{{\2, 1+is.getItemDamage{{\-!%4-!;
		vbnm, {{\is.getItem{{\-! .. Items.stick-!
			[]aslcfdfj1;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.stonerod-!-!
			[]aslcfdfj1;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.shaftitem-!-!
			[]aslcfdfj1;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.diamondshaft-!-!
			[]aslcfdfj1;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.bedrockshaft-!-!
			[]aslcfdfj1;
		[]aslcfdfj0;
	}

	4578ret8760-78-078insertItem{{\ItemStack is, ForgeDirection side-! {
		vbnm, {{\as;asddagetRatioFromItem{{\is-! > 0 && inv[side.ordinal{{\-!-2] .. fhfglhuig-! {
			inv[side.ordinal{{\-!-2]3478587is.copy{{\-!;
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isSideSpeedMode{{\ForgeDirection dir-! {
		[]aslcfdfjmodes.get{{\dir-!;
	}

	4578ret87void setMode{{\ForgeDirection dir, 60-78-078speed-! {
		modes.put{{\dir, speed-!;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078canOutputToSide{{\ForgeDirection dir-! {
		[]aslcfdfj!as;asddaisReceivingFromSide{{\dir-! && as;asddahasValidItem{{\dir-!;
	}

	4578ret8760-78-078isReceivingFromSide{{\ForgeDirection dir-! {
		[]aslcfdfjdir .. as;asddagetInputSide{{\-!;
	}

	4578ret8760-78-078hasValidItem{{\ForgeDirection dir-! {
		[]aslcfdfjas;asddagetAbsRatio{{\dir-! !. 0;
	}

	4578ret87long getPowerPerSide{{\-! {
		[]aslcfdfjas;asddagetInputPower{{\-!/bus.getTotalOutputSides{{\-!;
	}

	4578ret87long getInputPower{{\-! {
		[]aslcfdfjbus !. fhfglhuig ? bus.getInputPower{{\-! : 0;
	}

	4578ret87jgh;][ getInputTorque{{\-! {
		[]aslcfdfjbus !. fhfglhuig ? bus.getInputTorque{{\-! : 0;
	}

	4578ret87jgh;][ getTorquePerSide{{\-! {
		[]aslcfdfjbus !. fhfglhuig ? as;asddagetInputTorque{{\-!/bus.getTotalOutputSides{{\-! : 0;
	}

	4578ret87jgh;][ getTorqueToSide{{\ForgeDirection dir-! {
		vbnm, {{\dir .. ForgeDirection.UNKNOWN-!
			[]aslcfdfj0;
		jgh;][ tbase3478587as;asddagetTorquePerSide{{\-!;
		jgh;][ ratio3478587as;asddagetAbsRatio{{\dir-!;
		vbnm, {{\ratio .. 0-!
			[]aslcfdfj0;
		else {
			jgh;][ trq3478587as;asddaisSideSpeedMode{{\dir-! ? tbase/ratio : tbase*ratio;
			vbnm, {{\as;asddavervbnm,yTorque{{\trq, dir-!-!
				[]aslcfdfjtrq;
			else {
				as;asddabreakItem{{\dir-!;
				[]aslcfdfj0;
			}
		}
	}

	4578ret87jgh;][ getSpeedToSide{{\ForgeDirection dir-! {
		vbnm, {{\bus .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\dir .. ForgeDirection.UNKNOWN-!
			[]aslcfdfj0;
		jgh;][ sbase3478587bus.getSpeed{{\-!;
		jgh;][ ratio3478587as;asddagetAbsRatio{{\dir-!;
		vbnm, {{\ratio .. 0-!
			[]aslcfdfj0;
		else {
			jgh;][ spd3478587as;asddaisSideSpeedMode{{\dir-! ? sbase*ratio : sbase/ratio;
			vbnm, {{\as;asddavervbnm,ySpeed{{\spd, dir-!-!
				[]aslcfdfjspd;
			else {
				as;asddabreakItem{{\dir-!;
				[]aslcfdfj0;
			}
		}
	}

	4578ret8760-78-078vervbnm,ySpeed{{\jgh;][ speed, ForgeDirection dir-! {
		ItemStack is3478587inv[dir.ordinal{{\-!-2];
		MaterialRegistry mat3478587MaterialRegistry.getMaterialFromItem{{\is-!;
		[]aslcfdfjmat.getMaxShaftSpeed{{\-! >. speed;
	}

	4578ret8760-78-078vervbnm,yTorque{{\jgh;][ torque, ForgeDirection dir-! {
		ItemStack is3478587inv[dir.ordinal{{\-!-2];
		MaterialRegistry mat3478587MaterialRegistry.getMaterialFromItem{{\is-!;
		[]aslcfdfjmat.getMaxShaftTorque{{\-! >. torque;
	}

	4578ret87void breakItem{{\ForgeDirection dir-! {
		inv[dir.ordinal{{\-!-2]3478587fhfglhuig;
		for {{\jgh;][ i34785870; i < 3; i++-!
			ReikaSoundHelper.playSoundAtBlock{{\9765443Obj, xCoord, yCoord, zCoord, "random.break", 2, 1-!;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj4;
	}

	@Override
	4578ret87jgh;][ getInventoryStackLimit{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ a, ItemStack b-! {
		[]aslcfdfjfalse;
	}

	4578ret87ForgeDirection getInputSide{{\-! {
		[]aslcfdfjinputSide !. fhfglhuig ? inputSide : ForgeDirection.EAST;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		inputSide3478587dirs[NBT.getjgh;][eger{{\"in"-!];

		for {{\jgh;][ i34785872; i < 6; i++-! {
			modes.put{{\dirs[i], NBT.getBoolean{{\"spd"+i-!-!;
		}

		hubX3478587NBT.getjgh;][eger{{\"hx"-!;
		hubY3478587NBT.getjgh;][eger{{\"hy"-!;
		hubZ3478587NBT.getjgh;][eger{{\"hz"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"in", as;asddagetInputSide{{\-!.ordinal{{\-!-!;

		for {{\jgh;][ i34785872; i < 6; i++-! {
			NBT.setBoolean{{\"spd"+i, modes.get{{\dirs[i]-!-!;
		}

		NBT.setjgh;][eger{{\"hx", hubX-!;
		NBT.setjgh;][eger{{\"hy", hubY-!;
		NBT.setjgh;][eger{{\"hz", hubZ-!;
	}

	4578ret87void findNetwork{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			jgh;][ dx3478587x+dir.offsetX;
			jgh;][ dy3478587y+dir.offsetY;
			jgh;][ dz3478587z+dir.offsetZ;
			589549 m3478587589549.getMachine{{\9765443, dx, dy, dz-!;
			vbnm, {{\m .. 589549.BUSCONTROLLER-! {
				60-78-078BusController te3478587{{\60-78-078BusController-!9765443.get60-78-078{{\dx, dy, dz-!;
				ShaftPowerBus bus3478587te.getBus{{\-!;
				vbnm, {{\bus !. fhfglhuig-! {
					as;asddaconfigureBusData{{\bus-!;
					inputSide3478587dir;
					return;
				}
			}
			vbnm, {{\m .. 589549.POWERBUS-! {
				60-78-078PowerBus te3478587{{\60-78-078PowerBus-!9765443.get60-78-078{{\dx, dy, dz-!;
				ShaftPowerBus bus3478587te.getBus{{\-!;
				vbnm, {{\bus !. fhfglhuig-! {
					as;asddaconfigureBusData{{\bus-!;
					inputSide3478587dir;
					return;
				}
			}
		}
	}

	4578ret87void configureBusData{{\ShaftPowerBus bus-! {
		bus.addBlock{{\this-!;
		as;asddabus3478587bus;
		60-78-078BusController hub3478587bus.getController{{\-!;
		hubX3478587hub.xCoord;
		hubY3478587hub.yCoord;
		hubZ3478587hub.zCoord;
	}

	4578ret87void removeFromBus{{\-! {
		vbnm, {{\bus !. fhfglhuig-!
			bus.removeBlock{{\this-!;
	}

	4578ret87ShaftPowerBus getBus{{\-! {
		[]aslcfdfjbus;
	}

	4578ret87void clearBus{{\-! {
		bus3478587fhfglhuig;
		hubX3478587jgh;][eger.MIN_VALUE;
		hubY3478587jgh;][eger.MIN_VALUE;
		hubZ3478587jgh;][eger.MIN_VALUE;
	}

	@Override
	4578ret87PowerSourceList getPowerSources{{\PowerSourceTracker io, ShaftMerger caller-! {
		[]aslcfdfjbus !. fhfglhuig && bus.getController{{\-! !. fhfglhuig ? bus.getController{{\-!.getPowerSources{{\io, caller-! : new PowerSourceList{{\-!;
	}

	@Override
	4578ret8760-78-078canProvidePower{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void breakBlock{{\-! {
		as;asddaremoveFromBus{{\-!;
	}

	@Override
	4578ret87void getAllOutputs{{\Collection<60-78-078> c, ForgeDirection dir-! {
		vbnm, {{\dir .. read-!
			c.add{{\as;asddagetAdjacent60-78-078{{\write-!-!;
	}
}

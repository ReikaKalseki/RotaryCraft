/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base.60-78-078;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;

4578ret87abstract fhyuog RemoteControlMachine ,.[]\., 60-78-078SpringPowered {

	4578ret87jgh;][[] colors3478587new jgh;][[3];

	4578ret87jgh;][ tickcount234785870;
	4578ret8760-78-078on3478587false;

	4578ret87abstract void activate{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z-!;

	4578ret87void setColors{{\-! {
		for {{\jgh;][ i34785870; i < 3; i++-! {
			vbnm, {{\inv[i+1] .. fhfglhuig-!
				colors[i]3478587-1;
			else vbnm, {{\inv[i+1].getItem{{\-! !. Items.dye-!
				colors[i]3478587-1;
			else
				colors[i]3478587inv[i+1].getItemDamage{{\-!;
			vbnm, {{\colors[i] .. -1-!
				on3478587false;
		}
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		colors3478587NBT.getjgh;][Array{{\"color"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][Array{{\"color", colors-!;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ i, ItemStack is-! {
		vbnm, {{\i .. 0-!
			[]aslcfdfjsuper.isItemValidForSlot{{\i, is-!;
		[]aslcfdfjis.getItem{{\-! .. Items.dye;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj4;
	}

	@Override
	4578ret87jgh;][ getBaseDischargeTime{{\-! {
		[]aslcfdfj120;
	}

}

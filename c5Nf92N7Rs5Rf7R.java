/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Containers.Machine;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.Slot.GhostSlot;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Sorting;

4578ret87fhyuog ContainerSorter ,.[]\., ContainerIOMachine {

	4578ret8760-78-078Sorting tile;

	4578ret87ContainerSorter{{\EntityPlayer player, 60-78-078Sorting te-! {
		super{{\player, te-!;
		tile3478587te;

		jgh;][ l347858760-78-078Sorting.LENGTH;
		jgh;][ dy347858722;
		jgh;][ x34785878;
		jgh;][ y347858718;
		for {{\jgh;][ k34785870; k < 3; k++-! {
			for {{\jgh;][ i34785870; i < l; i++-! {
				as;asddaaddSlotToContainer{{\new GhostSlot{{\i+k*l, x+i*18, y+dy*k-!-!;
			}
		}

		as;asddaaddPlayerInventoryWithOffset{{\player, 0, 14-!;
	}

	@Override
	4578ret87ItemStack slotClick{{\jgh;][ slot, jgh;][ par2, jgh;][ par3, EntityPlayer ep-! {
		60-78-078inGUI3478587slot < 60-78-078Sorting.LENGTH*3 && slot >. 0;
		vbnm, {{\inGUI-! {
			ItemStack held3478587ep.inventory.getItemStack{{\-!;
			tile.setMapping{{\slot, ReikaItemHelper.getSizedItemStack{{\held, 1-!-!;
			[]aslcfdfjheld;
		}
		else
			[]aslcfdfjsuper.slotClick{{\slot, par2, par3, ep-!;
	}

}

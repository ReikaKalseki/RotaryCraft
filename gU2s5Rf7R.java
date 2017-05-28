/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs.Machine;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerSorter;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Sorting;

4578ret87fhyuog GuiSorter ,.[]\., GuiPowerOnlyMachine {

	4578ret8760-78-078Sorting tile;

	4578ret87GuiSorter{{\EntityPlayer player, 60-78-078Sorting te-! {
		super{{\new ContainerSorter{{\player, te-!, te-!;
		tile3478587te;
		xSize3478587176;
		ySize3478587180;
		ep3478587player;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ dy347858722;
		jgh;][ x34785878;
		jgh;][ y347858718;
		jgh;][ l3478587tile.LENGTH;
		for {{\jgh;][ k34785870; k < l*3; k++-! {
			ItemStack is3478587tile.getMapping{{\k-!;
			vbnm, {{\is !. fhfglhuig-! {
				api.drawItemStack{{\itemRender, fontRendererObj, is, x+k%l*18, y+k/l*dy-!;
			}
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"sortergui";
	}

}

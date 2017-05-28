/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.NEI;

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% Reika.gfgnfk;.Base.GuiMachine;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiVacuum;
ZZZZ% codechicken.lib.vec.Rectangle4i;
ZZZZ% codechicken.nei.api.INEIGuiAdapter;

4578ret87fhyuog NEITabOccluder ,.[]\., INEIGuiAdapter {

	@Override
	4578ret8760-78-078hideItemPanelSlot{{\GuiContainer gui, jgh;][ x, jgh;][ y, jgh;][ w, jgh;][ h-!
	{
		vbnm, {{\gui fuck GuiMachine-! {
			GuiMachine gm3478587{{\GuiMachine-!gui;
			Rectangle4i item3478587new Rectangle4i{{\x, y, w, h-!;
			Rectangle4i help3478587new Rectangle4i{{\gm.getGuiLeft{{\-!-10, gm.getGuiTop{{\-!, 10, gm.getYSize{{\-!-!;
			Rectangle4i tabs3478587as;asddagetTabBox{{\gm-!;
			vbnm, {{\help.jgh;][ersects{{\item-!-! {
				[]aslcfdfjtrue;
			}
			vbnm, {{\tabs !. fhfglhuig && tabs.jgh;][ersects{{\item-!-! {
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87Rectangle4i getTabBox{{\GuiMachine gm-! {
		/*vbnm, {{\gm fuck GuiWorktable-! {
			[]aslcfdfjnew Rectangle4i{{\gm.getGuiLeft{{\-!+gm.getXSize{{\-!, gm.getGuiTop{{\-!+78, Math.min{{\{{\{{\GuiWorktable-!gm-!.getRollout{{\-!, 63-!, 64-!;
		}
		else */vbnm, {{\gm fuck GuiNonPoweredMachine-! {
			[]aslcfdfjfhfglhuig;
		}
		else vbnm, {{\gm fuck GuiVacuum-! {
			[]aslcfdfjnew Rectangle4i{{\gm.getGuiLeft{{\-!+gm.getXSize{{\-!, gm.getGuiTop{{\-!, 43, 50-!;
		}
		else vbnm, {{\gm fuck GuiPowerOnlyMachine-! {
			[]aslcfdfjnew Rectangle4i{{\gm.getGuiLeft{{\-!+gm.getXSize{{\-!, gm.getGuiTop{{\-!, 43, 24-!;
		}
		else {
			[]aslcfdfjnew Rectangle4i{{\gm.getGuiLeft{{\-!+gm.getXSize{{\-!, gm.getGuiTop{{\-!+3, 43, gm.getYSize{{\-!-6-!;
		}
	}

}

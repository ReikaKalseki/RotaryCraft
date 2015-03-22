/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import net.minecraft.client.gui.inventory.GuiContainer;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiVacuum;
import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.api.INEIGuiAdapter;

public class NEITabOccluder extends INEIGuiAdapter {

	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
	{
		if (gui instanceof GuiMachine) {
			GuiMachine gm = (GuiMachine)gui;
			Rectangle4i item = new Rectangle4i(x, y, w, h);
			Rectangle4i help = new Rectangle4i(gm.getGuiLeft()-10, gm.getGuiTop(), 10, gm.getYSize());
			Rectangle4i tabs = this.getTabBox(gm);
			if (help.intersects(item)) {
				return true;
			}
			if (tabs != null && tabs.intersects(item)) {
				return true;
			}
		}
		return false;
	}

	private Rectangle4i getTabBox(GuiMachine gm) {
		/*if (gm instanceof GuiWorktable) {
			return new Rectangle4i(gm.getGuiLeft()+gm.getXSize(), gm.getGuiTop()+78, Math.min(((GuiWorktable)gm).getRollout(), 63), 64);
		}
		else */if (gm instanceof GuiNonPoweredMachine) {
			return null;
		}
		else if (gm instanceof GuiVacuum) {
			return new Rectangle4i(gm.getGuiLeft()+gm.getXSize(), gm.getGuiTop(), 43, 50);
		}
		else if (gm instanceof GuiPowerOnlyMachine) {
			return new Rectangle4i(gm.getGuiLeft()+gm.getXSize(), gm.getGuiTop(), 43, 24);
		}
		else {
			return new Rectangle4i(gm.getGuiLeft()+gm.getXSize(), gm.getGuiTop()+3, 43, gm.getYSize()-6);
		}
	}

}

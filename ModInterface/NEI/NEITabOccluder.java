package Reika.RotaryCraft.ModInterface.NEI;

import Reika.RotaryCraft.Base.GuiMachine;

import net.minecraft.client.gui.inventory.GuiContainer;
import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.api.INEIGuiAdapter;

public class NEITabOccluder extends INEIGuiAdapter {

	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
	{
		if (gui instanceof GuiMachine) {
			GuiMachine gm = (GuiMachine)gui;
			Rectangle4i item = new Rectangle4i(x, y, w, h);
			Rectangle4i tabs = new Rectangle4i(gm.getGuiLeft()-10, gm.getGuiTop(), gm.getXSize()+43, gm.getYSize());
			if (tabs.intersects(item)) {
				return true;
			}
		}
		return false;
	}

}
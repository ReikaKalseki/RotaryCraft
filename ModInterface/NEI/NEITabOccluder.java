package Reika.RotaryCraft.ModInterface.NEI;

import net.minecraft.client.gui.inventory.GuiContainer;
import Reika.RotaryCraft.Base.GuiMachine;
import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.api.INEIGuiAdapter;

public class NEITabOccluder extends INEIGuiAdapter {

	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
	{
		if (gui instanceof GuiMachine) {
			GuiMachine gm = (GuiMachine)gui;
			Rectangle4i item = new Rectangle4i(x, y, w, h);
			Rectangle4i tabs = new Rectangle4i(gui.guiLeft-10, gui.guiTop, gm.getXSize()+43, gm.getYSize());
			if (tabs.intersects(item)) {
				return true;
			}
		}
		return false;
	}

}

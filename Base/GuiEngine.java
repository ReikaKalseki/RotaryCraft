package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;


public abstract class GuiEngine extends GuiNonPoweredMachine {

	protected final TileEntityEngine eng;

	protected GuiEngine(Container par1Container, TileEntityEngine te, EntityPlayer p5ep) {
		super(par1Container, te);

		ep = p5ep;
		eng = te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		if (eng.getEngineType().burnsFuel()) {
			int j = (width - xSize) / 2;
			int k = (height - ySize) / 2;
			super.drawGuiContainerForegroundLayer(a, b);
			int x = api.getMouseRealX();
			int y = api.getMouseRealY();
			int dx = this.getFuelBarXPos();
			int dy = this.getFuelBarYPos();
			if (api.isMouseInBox(j+dx, j+dx+this.getFuelBarXSize(), k+dy, k+dy+this.getFuelBarYSize())) {
				int time = eng.getFuelDuration();
				String color = "";
				if (time == 0)
					color = EnumChatFormatting.GRAY.toString();
				else if (time < 30)
					color = EnumChatFormatting.RED.toString();
				else if (time < 45)
					color = EnumChatFormatting.GOLD.toString();
				else if (time < 60)
					color = EnumChatFormatting.YELLOW.toString();
				String sg = String.format("%sFuel: %s", color, ReikaFormatHelper.getSecondsAsClock(time));
				api.drawTooltipAt(fontRendererObj, sg, x-j, y-k);
			}
		}
	}

	protected abstract int getFuelBarXPos();
	protected abstract int getFuelBarYPos();

	protected abstract int getFuelBarXSize();
	protected abstract int getFuelBarYSize();

}

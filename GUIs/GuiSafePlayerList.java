package Reika.RotaryCraft.GUIs;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class GuiSafePlayerList extends GuiScreen {

	private int xSize = 400;
	private int ySize = 210;

	private String playerName;

	private String activePlayer;

	private TileEntityAimedCannon te;
	private List<String> playerList;

	private EntityPlayer ep;

	public GuiSafePlayerList(EntityPlayer e, TileEntityAimedCannon tile) {
		ep = e;
		te = tile;
		playerList = te.getCopyOfSafePlayerList();
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int colsize = 10;
		int width = 80;
		for (int i = 0; i < playerList.size(); i++) {
			int dx = (i/colsize)*width;
			int dy = (i%colsize)*20;
			//ReikaJavaLibrary.pConsole("Loading Player "+(i+1)+" of "+playerList.size()+" = "+playerList.get(i));
			buttonList.add(new GuiButton(i, j+dx, k+dy, width, 20, playerList.get(i)));
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		activePlayer = playerList.get(button.id);
		ReikaPacketHelper.sendStringPacket(RotaryCraft.packetChannel, PacketRegistry.SAFEPLAYER.getMinValue(), activePlayer, te);
		playerList.remove(button.id);
		this.initGui();
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{

		super.drawScreen(x, y, f);
	}

}

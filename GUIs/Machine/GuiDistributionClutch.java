/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityDistributionClutch;

public class GuiDistributionClutch extends GuiNonPoweredMachine
{
	private TileEntityDistributionClutch clutch;

	private final GuiTextField[] selectedAmounts = new GuiTextField[4];
	private final int[] selected = new int[4];
	private final boolean[] enabled = new boolean[4];

	public GuiDistributionClutch(EntityPlayer p5ep, TileEntityDistributionClutch clu) {
		super(new CoreContainer(p5ep, clu), clu);
		clutch = clu;
		ySize = 110;
		xSize = 160;
		ep = p5ep;
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			int idx = dir.ordinal()-2;
			selected[idx] = clutch.getTorqueRequest(dir);
			enabled[idx] = clutch.isSideEnabled(dir);
		}
	}

	private void addEntryForSide(int j, int k, ForgeDirection dir) {
		String file = "/Reika/RotaryCraft/Textures/GUI/buttons.png";
		String s = dir.name().substring(0, 1);
		int u = 9;
		int v = 54;
		int idx = dir.ordinal()-2;
		boolean enable = true;
		boolean out = enabled[idx];//clutch.isOutputtingToSide(dir);
		if (clutch.getWriteDirection() == dir || clutch.getReadDirection() == dir) {
			u = 0;
			enable = false;
		}
		else if (out) {
			u = 0;
			v = 63;
		}
		int h = 20;
		int x = j+18;
		int y = k+dir.ordinal()*h;
		ImagedGuiButton gb = new ImagedGuiButton(idx, x, y, 9, 9, u, v, file, RotaryCraft.class);
		gb.enabled = enable;
		buttonList.add(gb);
		selectedAmounts[idx] = new GuiTextField(fontRendererObj, x+15, y-h/2+6, 80, h-4);
		selectedAmounts[idx].setMaxStringLength(10);
		selectedAmounts[idx].setFocused(false);
		selectedAmounts[idx].setEnabled(enable);
		int amt = selected[idx];//clutch.getTorqueRequest(dir);
		selectedAmounts[idx].setText(String.valueOf(amt));
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2-2;
		int k = (height - ySize) / 2 - 12;

		for (int i = 2; i < 6; i++) {
			this.addEntryForSide(j, k, ForgeDirection.VALID_DIRECTIONS[i]);
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		for (int idx = 0; idx < selectedAmounts.length; idx++)
			selectedAmounts[idx].textboxKeyTyped(c, i);
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		for (int idx = 0; idx < selectedAmounts.length; idx++)
			selectedAmounts[idx].mouseClicked(i, j, k);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		boolean packet = false;
		for (int idx = 0; idx < selectedAmounts.length; idx++) {
			if (selectedAmounts[idx].isFocused()) {
				selected[idx] = this.parseInt(selectedAmounts[idx]);
				packet = true;
			}
		}
		if (packet)
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.DISTRIBCLUTCHPOWER.ordinal(), clutch, selected[0], selected[1], selected[2], selected[3]);
	}

	private int parseInt(GuiTextField g) {
		if (g.getText().isEmpty()) {
			return 0;
		}
		if (!g.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(g.getText())) {
			g.deleteFromCursor(-1);
			return 0;
		}
		return ReikaJavaLibrary.safeIntParse(g.getText());
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 4) {
			enabled[button.id] = !enabled[button.id];
			this.initGui();
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.DISTRIBCLUTCH.ordinal(), clutch, button.id, enabled[button.id] ? 1 : 0);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b) {
		super.drawGuiContainerForegroundLayer(a, b);

		int j = (width - xSize) / 2-2;
		int k = (height - ySize) / 2 - 12;

		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			fontRendererObj.drawString(dir.name(), 119, 29+(i-2)*20, 4210752);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		for (int idx = 0; idx < selectedAmounts.length; idx++)
			selectedAmounts[idx].drawTextBox();
	}

	@Override
	protected String getGuiTexture() {
		return "distribclutchgui";
	}
}

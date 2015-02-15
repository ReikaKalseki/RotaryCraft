/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Containers.ContainerJet;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;

public class GuiJet extends GuiNonPoweredMachine
{
	private final TileEntityEngine eng;
	private final TileEntityJetEngine jet;
	private boolean burn;

	public GuiJet(EntityPlayer p5ep, TileEntityEngine te)
	{
		super(new ContainerJet(p5ep, te), te);
		eng = te;
		xSize = 176;
		ySize = 166;
		ep = p5ep;
		jet = eng instanceof TileEntityJetEngine ? (TileEntityJetEngine)eng : null;
		burn = jet != null && jet.canAfterBurn() && jet.burnerActive();
	}

	@Override
	public void initGui() {
		super.initGui();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		if (jet != null) {
			int u = jet.burnerActive() ? 36 : 0;
			int v = jet.canAfterBurn() ? 72 : 90;
			buttonList.add(new ImagedGuiButton(0, j+32, k+36, 36, 18, u, v, "Textures/GUI/buttons.png", RotaryCraft.class));
		}
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		super.actionPerformed(b);

		if (b.id == 0 && jet != null && jet.canAfterBurn()) {
			burn = !burn;
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.AFTERBURN.getMinValue(), eng, burn ? 1 : 0);
			jet.setBurnerActive(burn);
			this.initGui();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerForegroundLayer(a, b);
		int x = api.getMouseRealX();
		int y = api.getMouseRealY();
		if (api.isMouseInBox(j+84, j+90, k+16, k+71)) {
			int time = eng.getFuelDuration();
			String sg = String.format("Fuel: %s", ReikaFormatHelper.getSecondsAsClock(time));
			api.drawTooltipAt(fontRendererObj, sg, x-j, y-k);
		}

		if (jet != null) {
			if (api.isMouseInBox(j+32, j+68, k+36, k+54)) {
				api.drawTooltipAt(fontRendererObj, "Afterburner", x-j, y-k);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i2 = eng.getFuelScaled(54);
		this.drawTexturedModalRect(j+85, k+71-i2, 207, 55-i2, 5, i2);
	}

	@Override
	protected String getGuiTexture() {
		return "jetgui";
	}
}

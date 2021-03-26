/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerBlastFurnace;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;

public class GuiBlastFurnace extends GuiNonPoweredMachine {
	private final TileEntityBlastFurnace blast;

	public GuiBlastFurnace(EntityPlayer p5ep, TileEntityBlastFurnace te) {
		super(new ContainerBlastFurnace(p5ep, te), te);
		blast = te;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int u = blast.leaveLastItem ? 54 : 42;
		String tip = blast.leaveLastItem ? "Leave one item" : "Consume all items";
		int v = 96;
		buttonList.add(new ImagedGuiButton(0, j+124, k+20, 12, 12, u, v, "Textures/GUI/buttons.png", tip, 0xffffff, false, RotaryCraft.class));
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		super.actionPerformed(b);

		if (b.id == 0) {
			blast.leaveLastItem = !blast.leaveLastItem;
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.BLASTLEAVEONE.ordinal(), blast, blast.leaveLastItem ? 1 : 0);
			this.initGui();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		int c = 0;
		if (blast.getTemperature() >= 1000)
			c = 1;
		api.drawCenteredStringNoShadow(fontRendererObj, String.valueOf(blast.getTemperature())+"C", 17+c, 6, 4210752);

		/*
		for (int i = 0; i < inventorySlots.inventorySlots.size(); i++) {
			Slot s = (Slot)inventorySlots.inventorySlots.get(i);
			if (s.inventory == blast && s.getClass() == Slot.class) {
				int idx = s.slotNumber;
				int clr = 0xff000000 | (blast.lockedSlots[idx] ? 0xff0000 : 0x00b000);
				api.drawRectFrame(s.xDisplayPosition, s.yDisplayPosition, 16, 16, clr);
			}
		}
		 */
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i1 = blast.getCookScaled(24);
		this.drawTexturedModalRect(j+119, k+34, 176, 14, i1+1, 16);
		int i2 = blast.getTemperatureScaled(54);
		this.drawTexturedModalRect(j + 11, k + 70-i2, 176, 86-i2, 10, i2);

		for (int i = 0; i < inventorySlots.inventorySlots.size(); i++) {
			Slot s = (Slot)inventorySlots.inventorySlots.get(i);
			if (s.inventory == blast && s.getClass() == Slot.class) {
				int idx = s.slotNumber;
				int clr = 0x50000000 | (blast.lockedSlots[idx] ? 0xff0000 : 0x00b000);
				drawRect(j+s.xDisplayPosition, k+s.yDisplayPosition, j+s.xDisplayPosition+16, k+s.yDisplayPosition+16, clr);
			}
		}
	}

	@Override
	protected String getGuiTexture() {
		return "blastfurngui2";
	}
}

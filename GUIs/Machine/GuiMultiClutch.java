/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GuiMultiClutch extends GuiNonPoweredMachine {

	private TileEntityMultiClutch multi;

	public GuiMultiClutch(EntityPlayer ep, TileEntityMultiClutch te) {
		super(new CoreContainer(ep, te), te);
		this.ep = ep;
		xSize = 176;
		ySize = 148;
		multi = te;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String tex = "/Reika/RotaryCraft/Textures/GUI/buttons.png";

		for (int i = 0; i < 16; i++) {
			buttonList.add(new ImagedGuiButton(i, j+18+70*(i/8)+15, k+20+16*(i%8)-1, 35, 9, 256-18, 256-62, tex, RotaryCraft.class));
		}
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		if (b.id < 16) {
			int side = multi.getNextSideForState(b.id);
			multi.setSideOfState(b.id, side);
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.REDGEAR.getMinValue(), multi, b.id, side);
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		for (int i = 0; i < 16; i++) {
			api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Items.redstone), 3+70*(i/8), 15+16*(i%8));
		}

		for (int i = 0; i < 16; i++) {
			fontRendererObj.drawString(String.format("%d", i), 18+70*(i/8), 20+16*(i%8), 0);
			Color color = RotaryAux.sideColors[multi.getSideOfState(i)];
			int border = 0xff000000;
			if (color == Color.BLACK)
				border = 0xffffffff;
			this.drawRect(18+70*(i/8)+14, 20+16*(i%8)-2, 18+70*(i/8)+51, 20+16*(i%8)+9, border);
			this.drawRect(18+70*(i/8)+15, 20+16*(i%8)-1, 18+70*(i/8)+50, 20+16*(i%8)+8, 0xff000000+color.getRGB());
		}
	}

	@Override
	public String getGuiTexture() {
		return "multigui";
	}

}
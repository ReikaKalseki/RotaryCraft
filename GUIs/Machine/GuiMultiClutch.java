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

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;

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
	protected void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		if (b.id < 16) {
			int side = multi.getNextSideForState(b.id);
			multi.setSideOfState(b.id, side);
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.REDGEAR.getMinValue(), multi, b.id, side);
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int pwr = multi.worldObj.getBlockPowerInput(multi.xCoord, multi.yCoord, multi.zCoord);
		for (int i = 0; i < 16; i++) {
			int x = 3+70*(i/8);
			int y = 15+16*(i%8);
			api.drawItemStack(itemRender, fontRendererObj, new ItemStack(i == pwr ? Items.glowstone_dust : Items.redstone), x, y);
		}

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(1, 1, 1, 1);
		for (int i = 0; i < 16; i++) {
			fontRendererObj.drawString(String.format("%d", i), 18+70*(i/8), 20+16*(i%8), 0);
			int idx = multi.getSideOfState(i);
			Color color = RotaryAux.sideColors[idx];
			int border = 0xff000000;
			if (color == Color.BLACK)
				border = 0xffffffff;
			this.drawRect(18+70*(i/8)+14, 20+16*(i%8)-2, 18+70*(i/8)+51, 20+16*(i%8)+9, border);
			this.drawRect(18+70*(i/8)+15, 20+16*(i%8)-1, 18+70*(i/8)+50, 20+16*(i%8)+8, 0xff000000+color.getRGB());
			String s = ReikaStringParser.capFirstChar(ForgeDirection.VALID_DIRECTIONS[idx].name());
			fontRendererObj.drawString(s, 18+70*(i/8)+16, 20+16*(i%8), idx == 0 || idx == 2 ? 0x000000 : 0xffffff);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected String getGuiTexture() {
		return "multigui";
	}

}

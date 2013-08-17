/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.ItemIconButton;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.DragonAPI.ModInteract.BCMachineHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.ModInterface.TileEntityLiquidConverter;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class GuiLiquidConverter extends GuiNonPoweredMachine {

	TileEntityLiquidConverter liquid;

	public GuiLiquidConverter(EntityPlayer ep, TileEntityLiquidConverter te) {
		super(new CoreContainer(ep, te), te);
		liquid = te;
		xSize = 176;
		ySize = 78;
		this.ep = ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		buttonList.add(new ItemIconButton(0, j+16, k+20, 0, liquid.getLiquid().getForgeLiquid().asItemStack(), itemRenderer));

		if (liquid.isToForge())
			buttonList.add(new ItemIconButton(1, j+16, k+24+28, 0, BCMachineHandler.getInstance().getTank(), itemRenderer));
		else {
			switch(liquid.getLiquid()) {
			case WATER:
			case LAVA:
				buttonList.add(new ItemIconButton(1, j+16, k+24+28, 0, MachineRegistry.PIPE.getCraftedProduct(), itemRenderer));
				break;
			case LUBRICANT:
				buttonList.add(new ItemIconButton(1, j+16, k+24+28, 0, MachineRegistry.HOSE.getCraftedProduct(), itemRenderer));
				break;
			case JETFUEL:
				buttonList.add(new ItemIconButton(1, j+16, k+24+28, 0, MachineRegistry.FUELLINE.getCraftedProduct(), itemRenderer));
				break;
			}
		}
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		if (b.id == 0) {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.LIQUID.getMinValue(), liquid, ep, liquid.getNextLiquid().ordinal());
		}
		else if (b.id == 1) {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.LIQUID.getMinValue()+1, liquid, ep, 1-liquid.getToForgeAsInt());
		}

		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par2, int par3) {
		super.drawGuiContainerForegroundLayer(par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		fontRenderer.drawString("Target Liquid Type", 40, 24, 0);
		fontRenderer.drawString("Target Pipe System", 40, 56, 0);
	}

	@Override
	public String getGuiTexture() {
		return "liquidgui";
	}

}

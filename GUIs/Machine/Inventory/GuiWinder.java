/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiOneSlotInv;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

public class GuiWinder extends GuiOneSlotInv
{
	private TileEntityWinder Winder;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;
	private boolean input;

	public GuiWinder(EntityPlayer p5ep, TileEntityWinder te)
	{
		super(p5ep, new OneSlotContainer(p5ep, te), te);
		Winder = te;
		xSize = 176;
		ySize = 166;
		ep = p5ep;
		input = te.winding;
	}

	@Override
	public void initGui() {
		super.initGui();
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		if (input)
			buttonList.add(new GuiButton(0, var5+xSize/2-35, var6+ySize/2-26, 65, 20, "Input Mode"));
		else
			buttonList.add(new GuiButton(0, var5+xSize/2-35, var6+ySize/2-26, 65, 20, "Output Mode"));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id != 0)
			return;
		ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.WINDER.getMinValue(), Winder);
		input = !input;
		this.initGui();
	}
}
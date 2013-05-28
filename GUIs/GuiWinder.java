/*******************************************************************************
 * @author Reika
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
import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnumPackets;
import Reika.RotaryCraft.Base.GuiOneSlotInv;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;

public class GuiWinder extends GuiOneSlotInv
{
    private TileEntityWinder Winder;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;
    private boolean input;

    public GuiWinder(EntityPlayer player, TileEntityWinder tile)
    {
        super(new OneSlotContainer(player, tile), tile);
        Winder = tile;
        xSize = 176;
    	ySize = 166;
    	this.player = player;
    	input = tile.winding;
    }

	@Override
	public void initGui() {
		super.initGui();
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
		buttonList.clear();
		if (input)
			buttonList.add(new GuiButton(0, var5+xSize/2-35, var6+ySize/2-26, 65, 20, "Input Mode"));
		else
			buttonList.add(new GuiButton(0, var5+xSize/2-35, var6+ySize/2-26, 65, 20, "Output Mode"));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, EnumPackets.WINDER.getMinValue(), Winder, player);
		input = !input;
		this.initGui();
	}
}

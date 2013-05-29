/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;

public class GuiOneSlotInv extends GuiMachine {

	protected IInventory iinv;
	public GuiOneSlotInv(Container par1Container, RotaryCraftTileEntity te) {
		super(par1Container, te);
		iinv = (IInventory)te;
        xSize = 176;
    	ySize = 166;
	}

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected final void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/basic_gui_oneslot.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
    }

    @Override
	protected final void drawGuiContainerForegroundLayer(int a, int b)
    {
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), xSize-58, (ySize - 96) + 3, 4210752);
        ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, tile.getMultiValuedName(), xSize/2, 6, 4210752);
    }

	@Override
	protected final void drawPowerTab(int j, int k) {}

}

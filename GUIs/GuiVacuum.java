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
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnumPackets;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiVacuum extends GuiPowerOnlyMachine
{
    private IInventory upperVacuumInventory;
    private IInventory lowerVacuumInventory;
    private TileEntityVacuum vac;
    EntityPlayer ep;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public GuiVacuum(EntityPlayer player, TileEntityVacuum te)
    {
        super(new ContainerVacuum(player, te), te);
        upperVacuumInventory = player.inventory;
        lowerVacuumInventory = te;
        allowUserInput = false;
        short var3 = 222;
        int var4 = var3 - 108;
        inventoryRows = te.getSizeInventory() / 9;
        ySize = var4 + inventoryRows * 18;
        vac = te;
        ep = player;
    }

	@Override
	public void initGui() {
		super.initGui();
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiButton(0, var5+xSize-1, var6+32, 43, 20, "Get XP"));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, EnumPackets.VACUUM.getMinValue(), vac, ep);
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRenderer.drawString(StatCollector.translateToLocal(vac.getName()), 8, 6, 4210752);
        fontRenderer.drawString("XP: "+String.format("%d", vac.experience), 120, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal(upperVacuumInventory.getInvName()), 8, ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String var4 = "/gui/container.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(var4);
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, xSize, inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var5, var6 + inventoryRows * 18 + 17, 0, 126, xSize, 96);

        this.drawPowerTab(var5, var6);
    }
}

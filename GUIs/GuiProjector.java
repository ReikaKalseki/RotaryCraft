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

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnumPackets;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerProjector;
import Reika.RotaryCraft.TileEntities.TileEntityProjector;

public class GuiProjector extends GuiPowerOnlyMachine {

	private TileEntityProjector proj;

	public GuiProjector(EntityPlayer player, TileEntityProjector te) {
		super(new ContainerProjector(player, te), te);
    	ySize = 222;
    	proj = te;
    	ep = player;
	}

	@Override
	public void initGui() {
		super.initGui();
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
		buttonList.clear();
		String file = "/Reika/RotaryCraft/Textures/GUI/buttons.png";
		//buttonList.add(new ImagedGuiButton(0, var5+xSize/2-9, var6+ySize/2-49, 18, 18, "", 18, 18, 0, false, file));
	}

    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        fontRenderer.drawString(proj.getName(), 5, 5, 4210752);
    }

	@Override
	public void actionPerformed(GuiButton button) {
		ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, EnumPackets.PROJECTOR.getMinValue(), proj, ep);
		this.initGui();
	}

    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/projectorgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        this.drawPowerTab(j, k);
    }

}

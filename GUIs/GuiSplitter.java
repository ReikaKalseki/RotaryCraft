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
import net.minecraft.src.ModLoader;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;

public class GuiSplitter extends GuiNonPoweredMachine
{
	public int mode;

	private TileEntitySplitter splitter;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private EntityPlayer player;
	int x;
	int y;

	public GuiSplitter(EntityPlayer player, TileEntitySplitter Splitter)
	{
		super(new CoreContainer(player, Splitter), Splitter);
		splitter = Splitter;
		ySize = 140;
		this.player = player;
		mode = splitter.splitmode;
	}

	@Override
	public void initGui() {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, j+8, -1+k+32, 72, 20, "31:1 Inline"));
		buttonList.add(new GuiButton(1, j+8, -1+k+52, 72, 20, "15:1 Inline"));
		buttonList.add(new GuiButton(2, j+8, -1+k+72, 72, 20, "7:1 Inline"));
		buttonList.add(new GuiButton(3, j+8, -1+k+92, 72, 20, "3:1 Inline"));

		buttonList.add(new GuiButton(8, j+52, -1+k+114, 72, 20, "1:1 Even"));

		buttonList.add(new GuiButton(4, j+96, -1+k+32, 72, 20, "1:31 Bend"));
		buttonList.add(new GuiButton(5, j+96, -1+k+52, 72, 20, "1:15 Bend"));
		buttonList.add(new GuiButton(6, j+96, -1+k+72, 72, 20, "1:7 Bend"));
		buttonList.add(new GuiButton(7, j+96, -1+k+92, 72, 20, "1:3 Bend"));

		//this.buttonList.add(new GuiButton(9, j+52, -1+k+140, 72, 20, "Merge"));
	}

	public void updateMode(int mode) {
		splitter.splitmode = mode;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id <= 8) {
			//this.updateMode(button.id);
			mode = button.id;
			ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 6, splitter, player, mode);
		}
		if (button.id == 9)
			ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 7, splitter, player, 0);
		this.updateScreen();

	}

	public void refreshScreen() {
		int lastx = x;
		int lasty = y;
		mc.thePlayer.closeScreen();
		ModLoader.openGUI(player, new GuiSplitter(player, splitter));
		Mouse.setCursorPosition(lastx, lasty);

	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		x = Mouse.getX();
		y = Mouse.getY();
	}

    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, tile.getName(), j+xSize/2, k+5, 4210752);
    }

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		String i = "/Reika/RotaryCraft/Textures/GUI/splittergui.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		//int i1 = splitterInventory.getCookProgressScaled(48);
		//drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);
	}

}

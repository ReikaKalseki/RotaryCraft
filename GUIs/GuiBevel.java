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

import org.lwjgl.input.Mouse;

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnumPackets;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.TileEntityGearBevel;

public class GuiBevel extends GuiNonPoweredMachine
{
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int posn;

	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int in;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int out;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private boolean[] isValid = {true, true, true, true, true, true};

	private TileEntityGearBevel bevel;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;

	public GuiBevel(EntityPlayer p5ep, TileEntityGearBevel GearBevel)
	{
		super(new CoreContainer(p5ep, GearBevel), GearBevel);
		bevel = GearBevel;
		ySize = 192;
		ep = p5ep;
		posn = GearBevel.direction;
		this.getIOFromDirection();
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2-2;
		int k = (height - ySize) / 2 - 12;

		String file = "/Reika/RotaryCraft/Textures/GUI/bevelgui2.png";
		int px = 176;
		for (int i = 0; i < 6; i++) {
			if (in == i)
				buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px+18, i*18, 0, file));
			else
				buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px, i*18, 0, file));
		}
		for (int i = 0; i < 6; i++) {
			if (isValid[i]) {
				if (out == i)
					buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px+18, i*18, 0, file));
				else
					buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px, i*18, 0, file));
			}
			else
				buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, 212, 0, 0, file));
		}
	}

	public void getIOFromDirection() {
		switch(posn) {

		}
	}

	public void getDirectionFromIO() {
		System.out.print(bevel.colorNames[in]+" to "+bevel.colorNames[out]+" -> data: ");
		switch(in) {
			case 0:
				switch(out) {
					case 2:
						posn = 13;
						break;
					case 3:
						posn = 15;
						break;
					case 4:
						posn = 12;
						break;
					case 5:
						posn = 14;
						break;
				}
				break;
			case 1:
				switch(out) {
					case 2:
						posn = 21;
						break;
					case 3:
						posn = 23;
						break;
					case 4:
						posn = 20;
						break;
					case 5:
						posn = 22;
						break;
				}
				break;
			case 2:
				switch(out) {
					case 0:
						posn = 17;
						break;
					case 1:
						posn = 9;
						break;
					case 4:
						posn = 5;
						break;
					case 5:
						posn = 1;
						break;
				}
				break;
			case 3:
				switch(out) {
					case 0:
						posn = 19;
						break;
					case 1:
						posn = 11;
						break;
					case 4:
						posn = 3;
						break;
					case 5:
						posn = 7;
						break;
				}
				break;
			case 4:
				switch(out) {
					case 0:
						posn = 16;
						break;
					case 1:
						posn = 8;
						break;
					case 2:
						posn = 0;
						break;
					case 3:
						posn = 4;
						break;
				}
				break;
			case 5:
				switch(out) {
					case 0:
						posn = 18;
						break;
					case 1:
						posn = 10;
						break;
					case 2:
						posn = 6;
						break;
					case 3:
						posn = 2;
						break;
				}
				break;
		}
	}

	private void setValidStates() {
		for (int i = 0; i < 6; i++)
			isValid[i] = true;
		isValid[in] = false;
		if (in%2 == 0)
			isValid[in+1] = false;
		else
			isValid[in-1] = false;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 6) {
			in = button.id;
			this.setValidStates();
		}
		else {
			if (!isValid[button.id-6])
				return;
			out = button.id-6;
		}
		this.getDirectionFromIO();
		this.initGui();
		ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, EnumPackets.BEVEL.getMinValue(), bevel, ep, posn);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		x = Mouse.getX();
		y = Mouse.getY();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		fontRenderer.drawString("Input Side", 24, 32, 4210752);
		fontRenderer.drawString("Output Side", 99, 32, 4210752);
	}

	@Override
	public String getGuiTexture() {
		return "bevelgui2";
	}
}

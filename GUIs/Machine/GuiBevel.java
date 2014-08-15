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
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Mouse;

public class GuiBevel extends GuiNonPoweredMachine
{
	/** Side colors:
	 * 
	 * Cyan y-1; blue y+1; yellow -z; black +z; orange -x; magenta +x;<br>
	 * 0 y-1; 1 y+1; 2 -z; 3 +z; 4 -x; 5 +x;
	 * 
	 */
	private int posn;

	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int in;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int out;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private boolean[] isValid = {true, true, true, true, true, true};



	private TileEntityBevelGear bevel;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;

	public GuiBevel(EntityPlayer p5ep, TileEntityBevelGear GearBevel)
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
			String s = ForgeDirection.VALID_DIRECTIONS[i].name().substring(0, 1);
			if (in == i)
				buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px+18, i*18, s, 0, false, file, RotaryCraft.class));
			else
				buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px, i*18, s, 0, false, file, RotaryCraft.class));
		}
		for (int i = 0; i < 6; i++) {
			String s = ForgeDirection.VALID_DIRECTIONS[i].name().substring(0, 1);
			if (isValid[i]) {
				if (out == i)
					buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px+18, i*18, s, 0, false, file, RotaryCraft.class));
				else
					buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px, i*18, s, 0, false, file, RotaryCraft.class));
			}
			else
				buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, 212, 0, s, 0, false, file, RotaryCraft.class));
		}
	}

	public void getIOFromDirection() {
		switch(posn) {
		case 0:
			in = 4;
			out = 2;
			break;
		case 1:
			in = 2;
			out = 5;
			break;
		case 2:
			in = 5;
			out = 3;
			break;
		case 3:
			in = 3;
			out = 4;
			break;
		case 4:
			in = 4;
			out = 3;
			break;
		case 5:
			in = 2;
			out = 4;
			break;
		case 6:
			in = 5;
			out = 2;
			break;
		case 7:
			in = 3;
			out = 5;
			break;

		case 8:
			in = 4;
			out = 1;
			break;
		case 9:
			in = 2;
			out = 1;
			break;
		case 10:
			in = 5;
			out = 1;
			break;
		case 11:
			in = 3;
			out = 1;
			break;

		case 12:
			in = 0;
			out = 4;
			break;
		case 13:
			in = 0;
			out = 2;
			break;
		case 14:
			in = 0;
			out = 5;
			break;
		case 15:
			in = 0;
			out = 3;
			break;

		case 16:
			in = 4;
			out = 0;
			break;
		case 17:
			in = 2;
			out = 0;
			break;
		case 18:
			in = 5;
			out = 0;
			break;
		case 19:
			in = 3;
			out = 0;
			break;

		case 20:
			in = 1;
			out = 4;
			break;
		case 21:
			in = 1;
			out = 2;
			break;
		case 22:
			in = 1;
			out = 5;
			break;
		case 23:
			in = 1;
			out = 3;
			break;
		}
	}

	public void getDirectionFromIO() {
		//System.out.println(RotaryAux.sideColorNames[in]+" to "+RotaryAux.sideColorNames[out]+" -> data: ");
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
		//System.out.println(posn);
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
		else if (button.id < 24000) {
			if (!isValid[button.id-6])
				return;
			out = button.id-6;
		}
		this.getDirectionFromIO();
		this.initGui();
		bevel.direction = posn;
		ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.BEVEL.getMinValue(), bevel, posn);
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

		fontRendererObj.drawString("Input Side", 24, 32, 4210752);
		fontRendererObj.drawString("Output Side", 99, 32, 4210752);

		int j = (width - xSize) / 2-2;
		int k = (height - ySize) / 2 - 12;

		if (ConfigRegistry.COLORBLIND.getState()) {
			for (int i = 0; i < 6; i++) {
				fontRendererObj.drawString(String.valueOf(i), 30, 49+i*22, 0);
			}

			for (int i = 0; i < 6; i++) {
				fontRendererObj.drawString(String.valueOf(i), xSize-68, 49+i*22, 0);
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "bevelgui2";
	}
}
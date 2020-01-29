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

import org.apache.commons.lang3.tuple.ImmutablePair;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;

public class GuiBevel extends GuiNonPoweredMachine
{
	/** Side colors:
	 *
	 * Cyan y-1; blue y+1; yellow -z; black +z; orange -x; magenta +x;<br>
	 * 0 y-1; 1 y+1; 2 -z; 3 +z; 4 -x; 5 +x;
	 *
	 */
	private int posn;
	private ForgeDirection in;
	private ForgeDirection out;

	private TileEntityBevelGear bevel;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	public GuiBevel(EntityPlayer p5ep, TileEntityBevelGear GearBevel) {
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
			if (true || TileEntityBevelGear.isValid(ForgeDirection.VALID_DIRECTIONS[i], out)) {
				if (in.ordinal() == i)
					buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px+18, i*18, s, 0, false, file, RotaryCraft.class));
				else
					buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px, i*18, s, 0, false, file, RotaryCraft.class));
			}
			else {
				buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, 212, 0, s, 0, false, file, RotaryCraft.class));
			}
		}
		for (int i = 0; i < 6; i++) {
			String s = ForgeDirection.VALID_DIRECTIONS[i].name().substring(0, 1);
			if (TileEntityBevelGear.isValid(in, ForgeDirection.VALID_DIRECTIONS[i])) {
				if (out.ordinal() == i)
					buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px+18, i*18, s, 0, false, file, RotaryCraft.class));
				else
					buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px, i*18, s, 0, false, file, RotaryCraft.class));
			}
			else
				buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, 212, 0, s, 0, false, file, RotaryCraft.class));
		}
	}

	public void getIOFromDirection() {
		ImmutablePair<ForgeDirection, ForgeDirection> dirs = TileEntityBevelGear.getDirectionMap().get(posn);
		if (dirs == null) {
			RotaryCraft.logger.logError("Bevel was set to invalid direction value "+posn+"!");
			return;
		}
		in = dirs.left;
		out = dirs.right;
	}

	public void getDirectionFromIO() {
		if (!TileEntityBevelGear.isValid(in, out)) {
			RotaryCraft.logger.logError("Bevel was set to invalid state "+in+" > "+out+"!");
			return;
		}
		posn = TileEntityBevelGear.getDirectionMap().inverse().get(new ImmutablePair(in, out));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 6) {
			in = ForgeDirection.VALID_DIRECTIONS[button.id];
			if (!TileEntityBevelGear.isValid(in, out))
				out = in.offsetY != 0 ? ForgeDirection.EAST : ForgeDirection.DOWN;
		}
		else if (button.id < 24000) {
			if (!TileEntityBevelGear.isValid(in, ForgeDirection.VALID_DIRECTIONS[button.id-6]))
				return;
			out = ForgeDirection.VALID_DIRECTIONS[button.id-6];
		}
		this.getDirectionFromIO();
		this.initGui();
		bevel.direction = posn;
		ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.BEVEL.ordinal(), bevel, posn);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b) {
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
	protected String getGuiTexture() {
		return "bevelgui2";
	}
}

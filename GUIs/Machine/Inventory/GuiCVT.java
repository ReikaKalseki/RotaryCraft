/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerCVT;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear.CVTMode;

public class GuiCVT extends GuiNonPoweredMachine
{
	private TileEntityAdvancedGear cvt;
	public int ratio;
	private boolean reduction;
	private CVTMode mode;
	private int buttontimer = 0;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private GuiTextField input;

	//Make gui look cool (like connecting spindles with belts)

	public GuiCVT(EntityPlayer p5ep, TileEntityAdvancedGear AdvancedGear)
	{
		super(new ContainerCVT(p5ep, AdvancedGear), AdvancedGear);
		cvt = AdvancedGear;
		ySize = 237;
		xSize = 240;
		ep = p5ep;
		ratio = cvt.getRatio();
		if (ratio > cvt.getMaxRatio())
			ratio = cvt.getMaxRatio();
		reduction = ratio < 0;
		mode = cvt.getMode();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.ratio));
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;

		input = null;

		switch(mode) {
			case AUTO:
				input = new GuiTextField(fontRendererObj, j+xSize/2+24, k+48, 76, 16);
				input.setFocused(false);
				input.setMaxStringLength(9);
				break;
			case MANUAL:
				if (ratio > 0)
					buttonList.add(new GuiButton(1, j+xSize/2-6, -1+k+64, 80, 20, "Speed"));
				else
					buttonList.add(new GuiButton(1, j+xSize/2-6, -1+k+64, 80, 20, "Torque"));
				input = new GuiTextField(fontRendererObj, j+xSize/2+24, k+39, 26, 16);
				input.setFocused(false);
				input.setMaxStringLength(3);
				break;
			case REDSTONE:
				buttonList.add(new GuiButton(1, j+xSize/2+25, -1+k+44, 71, 20, ""));
				buttonList.add(new GuiButton(2, j+xSize/2+25, -1+k+67, 71, 20, ""));
				break;
		}

		buttonList.add(new GuiButton(0, j+xSize/2+84, -1+k+19, 20, 20, ""));
	}

	@Override
	protected void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		if (input != null)
			input.textboxKeyTyped(c, i);
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (input != null)
			input.mouseClicked(i, j, k);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (buttontimer > 0)
			return;
		else
			buttontimer = 8;
		if (button.id == 0) {
			mode = mode.next();
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.CVTMODE.ordinal(), cvt);
		}

		switch(mode) {
			case AUTO:
				break;
			case MANUAL:
				if (button.id == 1) {
					if (ratio > cvt.getMaxRatio())
						ratio = cvt.getMaxRatio();
					ratio = -ratio;
					reduction = ratio < 0;
					ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.CVTRATIO.ordinal(), cvt, ratio);
				}
				break;
			case REDSTONE:
				if (button.id == 1) {
					cvt.incrementCVTState(true);
					ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.CVTREDSTONESTATE.ordinal(), cvt, 1);
				}
				if (button.id == 2) {
					cvt.incrementCVTState(false);
					ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.CVTREDSTONESTATE.ordinal(), cvt, 0);
				}
				break;
		}

		super.updateScreen();
		this.initGui();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (input != null) {
			if (input.getText().isEmpty())
				return;
			PacketRegistry p = mode == CVTMode.AUTO ? PacketRegistry.CVTTARGET : PacketRegistry.CVTRATIO;
			int val = 1;
			if (!(input.getText().matches("^[0-9 ]+$"))) {
				input.deleteFromCursor(-1);
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, p.ordinal(), cvt, val);
				return;
			}
			val = ReikaJavaLibrary.safeIntParse(input.getText());
			if (mode == CVTMode.MANUAL && reduction)
				val = -val;
			if (val != 0)
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, p.ordinal(), cvt, val);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		if (buttontimer > 0)
			buttontimer--;
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerForegroundLayer(a, b);

		if (cvt.hasLubricant()) {
			Fluid f = FluidRegistry.getFluid("rc lubricant");
			IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
			ReikaLiquidRenderer.bindFluidTexture(f);
			GL11.glColor4f(1, 1, 1, 1);
			this.drawTexturedModelRectFromIcon(186, 89, ico, 16, 48);
		}

		if (api.isMouseInBox(j+185, j+202, k+88, k+149)) {
			String s = "Lubricant";
			api.drawTooltipAt(fontRendererObj, s, -j+api.getMouseRealX()+55-fontRendererObj.getStringWidth(s), -k+api.getMouseRealY());
		}

		switch(mode) {
			case REDSTONE:
				api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Blocks.redstone_torch), 129, 31);
				api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Blocks.unlit_redstone_torch), 129, 54);

				this.drawCenteredString(fontRendererObj, cvt.getCVTString(true), 188, 37, 0xffffff);
				this.drawCenteredString(fontRendererObj, cvt.getCVTString(false), 188, 60, 0xffffff);

				int dy = 17;
				int dx = -14;
				fontRendererObj.drawString("Belt Ratio:", xSize/2-32+dx, 31+dy, 4210752);

				api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Items.redstone), xSize/2+94, 7);
				break;
			case MANUAL:
				if (!input.isFocused()) {
					fontRendererObj.drawString(String.format("%d", Math.abs(cvt.getRatio())), xSize/2+36, 31, 0xffffffff);
				}
				fontRendererObj.drawString("Belt Ratio:", xSize/2-32, 31, 4210752);
				this.drawCenteredString(fontRendererObj, "M", xSize/2+102, 12, 0xffffffff);
				break;
			case AUTO:
				if (!input.isFocused()) {
					fontRendererObj.drawString(String.format("%d", Math.abs(cvt.getTargetTorque())), xSize/2+36, 40, 0xffffffff);
				}
				fontRendererObj.drawString("Target Torque:", xSize/2-48, 40, 4210752);
				fontRendererObj.drawString(String.format("Current Input: %d Nm", cvt.getTorqueIn()), xSize/2-30, 60, 4210752);
				int r = cvt.getRatio();
				fontRendererObj.drawString(String.format("Current Ratio: %dx (%s)", Math.abs(r), r < 0 ? "Torque" : "Speed"), xSize/2-30, 72, 4210752);
				api.drawItemStack(itemRender, fontRendererObj, ItemStacks.pcb, xSize/2+94, 8);
				break;
		}

		if (api.isMouseInBox(j+xSize/2+92, j+xSize/2+112, -1+k+7, -1+k+27)) {
			String s = "Control Mode";
			api.drawTooltipAt(fontRendererObj, s, api.getMouseRealX()-5-fontRendererObj.getStringWidth(s), api.getMouseRealY());
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		if (input != null)
			input.drawTextBox();

		switch(mode) {
			case MANUAL:
				if (ratio > cvt.getMaxRatio())
					api.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", cvt.getMaxRatio()), j+xSize/2+88, k+31, 0xff0000);
				else if (ratio == 0)
					api.drawCenteredStringNoShadow(fontRendererObj, "(1)", j+xSize/2+88, k+31, 0xff0000);
				else
					api.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", Math.abs(cvt.getRatio())), j+xSize/2+88, k+31, 4210752);
				break;
			case AUTO:
				break;
			case REDSTONE:
				break;
		}
	}

	@Override
	protected String getGuiTexture() {
		return mode == CVTMode.REDSTONE ? "cvtgui2" : "cvtgui";
	}

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerCVT;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

public class GuiCVT extends GuiNonPoweredMachine
{
	private TileEntityAdvancedGear cvt;
	public int ratio;
	private boolean reduction;
	private boolean redstone;
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
		redstone = cvt.isRedstoneControlled;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.ratio));
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;

		if (redstone) {
			buttonList.add(new GuiButton(1, j+xSize/2+25, -1+k+44, 71, 20, ""));
			buttonList.add(new GuiButton(2, j+xSize/2+25, -1+k+67, 71, 20, ""));
		}
		else {
			if (ratio > 0)
				buttonList.add(new GuiButton(1, j+xSize/2-6, -1+k+64, 80, 20, "Speed"));
			else
				buttonList.add(new GuiButton(1, j+xSize/2-6, -1+k+64, 80, 20, "Torque"));
			input = new GuiTextField(fontRendererObj, j+xSize/2+24, k+39, 26, 16);
			input.setFocused(false);
			input.setMaxStringLength(3);
		}

		buttonList.add(new GuiButton(0, j+xSize/2+84, -1+k+19, 20, 20, ""));
	}

	@Override
	protected void keyTyped(char c, int i){
		super.keyTyped(c, i);
		if (!redstone)
			input.textboxKeyTyped(c, i);
	}

	@Override
	protected void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		if (!redstone)
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
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CVT.getMinValue(), cvt, 0);
			redstone = !redstone;
		}

		if (redstone) {
			if (button.id == 1) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CVT.getMinValue()+1, cvt, 0);
				cvt.incrementCVTState(true);
			}
			if (button.id == 2) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CVT.getMinValue()+2, cvt, 0);
				cvt.incrementCVTState(false);
			}
		}
		else {
			if (button.id == 1) {
				if (ratio > cvt.getMaxRatio())
					ratio = cvt.getMaxRatio();
				ratio = -ratio;
				reduction = ratio < 0;
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CVT.getMinValue()+1, cvt, ratio);
			}
		}

		super.updateScreen();
		this.initGui();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (redstone) {

		}
		else {
			if (input.getText().isEmpty())
				return;
			if (!(input.getText().matches("^[0-9 ]+$"))) {
				ratio = 1;
				input.deleteFromCursor(-1);
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CVT.getMinValue()+1, cvt, ratio);
				return;
			}
			ratio = ReikaJavaLibrary.safeIntParse(input.getText());
			if (reduction)
				ratio = -ratio;
			if (ratio != 0)
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CVT.getMinValue()+1, cvt, ratio);
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
		int dy = redstone ? 17 : 0;
		int dx = redstone ? -14 : 0;
		fontRendererObj.drawString("Belt Ratio:", xSize/2-32+dx, 31+dy, 4210752);

		if (cvt.hasLubricant()) {
			Fluid f = FluidRegistry.getFluid("rc lubricant");
			IIcon ico = f.getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
			GL11.glColor4f(1, 1, 1, 1);
			this.drawTexturedModelRectFromIcon(186, 89, ico, 16, 48);
		}

		if (api.isMouseInBox(j+185, j+202, k+88, k+149)) {
			String s = "Lubricant";
			api.drawTooltipAt(fontRendererObj, s, -j+api.getMouseRealX()+55-fontRendererObj.getStringWidth(s), -k+api.getMouseRealY());
		}

		api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Items.redstone), xSize/2+94, 7);

		if (api.isMouseInBox(j+xSize/2+92, j+xSize/2+112, -1+k+7, -1+k+27)) {
			String s = "Redstone Control";
			api.drawTooltipAt(fontRendererObj, s, api.getMouseRealX()-5-fontRendererObj.getStringWidth(s), api.getMouseRealY());
		}

		if (redstone) {
			api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Blocks.redstone_torch), 129, 31);
			api.drawItemStack(itemRender, fontRendererObj, new ItemStack(Blocks.unlit_redstone_torch), 129, 54);

			this.drawCenteredString(fontRendererObj, cvt.getCVTString(true), 188, 37, 0xffffff);
			this.drawCenteredString(fontRendererObj, cvt.getCVTString(false), 188, 60, 0xffffff);
		}
		else {
			if (!input.isFocused()) {
				fontRendererObj.drawString(String.format("%d", Math.abs(cvt.getRatio())), xSize/2+36, 31, 0xffffffff);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		if (redstone) {

		}
		else {
			input.drawTextBox();
			if (ratio > cvt.getMaxRatio())
				api.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", cvt.getMaxRatio()), j+xSize/2+88, k+31, 0xff0000);
			else if (ratio == 0)
				api.drawCenteredStringNoShadow(fontRendererObj, "(1)", j+xSize/2+88, k+31, 0xff0000);
			else
				api.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", Math.abs(cvt.getRatio())), j+xSize/2+88, k+31, 4210752);
		}
	}

	@Override
	protected String getGuiTexture() {
		return redstone ? "cvtgui2" : "cvtgui";
	}

}

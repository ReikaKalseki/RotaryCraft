/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.ModInterface.TileEntityPressureBalancer.SideState;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class GuiPressureBalancer extends GuiNonPoweredMachine
{
	private TileEntityPressureBalancer PressureBalancer;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;

	public GuiPressureBalancer(EntityPlayer p5ep, TileEntityPressureBalancer te)
	{
		super(new CoreContainer(p5ep, te), te);
		PressureBalancer = te;
		xSize = 176;
		ySize = 156;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String tex = "/Reika/RotaryCraft/Textures/GUI/buttons.png";

		for (int i = 0; i < 6; i++)
			buttonList.add(new ImagedGuiButton(i, j+84, k+31+20*i, 18, 18, 256-18, 256-72, tex));

		buttonList.add(new ImagedGuiButton(6, j+9, k+20, 18, 62, 256-18, 256-62, tex));
		buttonList.add(new ImagedGuiButton(7, j+37, k+20, 18, 62, 256-18, 256-62, tex));

		buttonList.add(new ImagedGuiButton(8, j+9, k+87, 18, 62, 256-18, 256-62, tex));
		buttonList.add(new ImagedGuiButton(9, j+37, k+87, 18, 62, 256-18, 256-62, tex));
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		this.initGui();
		if (b.id < 6) {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.BALANCER.getMinValue(), PressureBalancer, ep, b.id);
		}
		else if (b.id < 10) {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.BALANCER.getMinValue()+1, PressureBalancer, ep, b.id-6);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		int color;

		if (PressureBalancer.isToForge(0))
			color = 0xff00ff00;
		else
			color = 0xffff0000;
		this.drawRect(29, 78, 32, 81, color);

		if (PressureBalancer.isToForge(1))
			color = 0xff00ff00;
		else
			color = 0xffff0000;
		this.drawRect(57, 78, 60, 81, color);

		if (PressureBalancer.isToForge(2))
			color = 0xff00ff00;
		else
			color = 0xffff0000;
		this.drawRect(29, 145, 32, 148, color);

		if (PressureBalancer.isToForge(3))
			color = 0xff00ff00;
		else
			color = 0xffff0000;
		this.drawRect(57, 145, 60, 148, color);

		Color[] colors = new Color[RotaryAux.sideColors.length];
		System.arraycopy(RotaryAux.sideColors, 0, colors, 0, colors.length);
		Color sc = colors[3];
		colors[3] = colors[5];
		colors[5] = sc;

		String tex = "/Reika/RotaryCraft/Textures/GUI/"+this.getGuiTexture()+".png";

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.values()[i];
			SideState s = PressureBalancer.getStateOfSide(dir);

			GL11.glColor4d(1, 1, 1, 1);
			mc.renderEngine.bindTexture(tex);

			if (s.canAcceptLiquid()) {
				this.drawTexturedModalRect(125, 31+20*i, 24, 156, 24, 18);
			}
			else {
				this.drawTexturedModalRect(125, 31+20*i, 0, 156, 24, 18);
			}
		}

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.values()[i];

			SideState s = PressureBalancer.getStateOfSide(dir);

			this.drawRect(85, 32+20*i, 85+16, 32+16+20*i, 0xc0000000+colors[dir.ordinal()].getRGB());

			ItemStack liq = s.getItemForIcon();
			ItemStack pipe = s.getItemForPiping();
			ReikaGuiAPI.instance.drawItemStack(itemRenderer, fontRenderer, liq, 108, 32+20*i);
			ReikaGuiAPI.instance.drawItemStack(itemRenderer, fontRenderer, pipe, 150, 32+20*i);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int w = PressureBalancer.getWaterScaled(60);
		int lv = PressureBalancer.getLavaScaled(60);
		int f = PressureBalancer.getFuelScaled(60);
		int lb = PressureBalancer.getLubeScaled(60);

		this.drawTexturedModalRect(j+10, k+81-w, 176, 160-w, 17, w);
		this.drawTexturedModalRect(j+38, k+81-w, 193, 160-w, 17, lv);
		this.drawTexturedModalRect(j+10, k+148-w, 210, 160-w, 17, f);
		this.drawTexturedModalRect(j+38, k+148-w, 227, 160-w, 17, lb);

	}

	@Override
	public String getGuiTexture() {
		return "balancergui2";
	}
}

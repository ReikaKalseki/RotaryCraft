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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;

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
		ySize = 172;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		buttonList.add(new ImagedGuiButton(0, j+25, k+13, 18, 72, 256-18, 256-72, 0, "/Reika/RotaryCraft/Textures/GUI/buttons.png"));
		buttonList.add(new ImagedGuiButton(1, j+61, k+13, 18, 72, 256-18, 256-72, 0, "/Reika/RotaryCraft/Textures/GUI/buttons.png"));
		buttonList.add(new ImagedGuiButton(2, j+97, k+13, 18, 72, 256-18, 256-72, 0, "/Reika/RotaryCraft/Textures/GUI/buttons.png"));
		buttonList.add(new ImagedGuiButton(3, j+133, k+13, 18, 72, 256-18, 256-72, 0, "/Reika/RotaryCraft/Textures/GUI/buttons.png"));
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
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
		int w = PressureBalancer.getWaterScaled(70);
		int lv = PressureBalancer.getLavaScaled(70);
		int f = PressureBalancer.getFuelScaled(70);
		int lb = PressureBalancer.getLubeScaled(70);
		//ReikaJavaLibrary.pConsole(w);
		this.drawTexturedModalRect(j+26, k+84-w, 176, 95-w, 16, w);
		this.drawTexturedModalRect(j+62, k+84-w, 193, 95-w, 16, lv);
		this.drawTexturedModalRect(j+98, k+84-w, 210, 95-w, 16, f);
		this.drawTexturedModalRect(j+134, k+84-w, 227, 95-w, 16, lb);

	}

	@Override
	public String getGuiTexture() {
		return "balancergui";
	}
}

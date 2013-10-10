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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerFermenter;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;

public class GuiFermenter extends GuiMachine
{
	private TileEntityFermenter ferm;

	public GuiFermenter(EntityPlayer p5ep, TileEntityFermenter Fermenter)
	{
		super(new ContainerFermenter(p5ep, Fermenter), Fermenter);
		ferm = Fermenter;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		boolean red = ferm.worldObj.isBlockIndirectlyGettingPowered(ferm.xCoord, ferm.yCoord, ferm.zCoord);
		if (red) {
			ReikaGuiAPI.instance.drawItemStack(itemRenderer, fontRenderer, ItemStacks.sludge, 154, 6);
		}
		else {
			ReikaGuiAPI.instance.drawItemStack(itemRenderer, fontRenderer, ItemRegistry.YEAST.getStackOf(), 154, 6);
		}
		fontRenderer.drawString("Target", 119, 10, 0);

		GL11.glColor4f(1, 1, 1, 1);
		ReikaLiquidRenderer.bindFluidTexture(FluidRegistry.WATER);
		int h = 16*ferm.getLevel()/ferm.CAPACITY;
		int dy = red ? 18 : 0;
		dy = 0;
		this.drawTexturedModelRectFromIcon(55, 35+16-h+dy, FluidRegistry.WATER.getIcon(), 16, h);
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

		int i1 = ferm.getCookProgressScaled(48);
		this.drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);

		int i2 = ferm.getTemperatureScaled(54);
		if (i2 > 54)
			i2 = 54;
		this.drawTexturedModalRect(j+24, k+70-i2, 177, 86-i2, 9, i2);
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-4);

		long frac = (ferm.power*29L)/ferm.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-144, 0, 0, (int)frac, 4);

		frac = (int)(ferm.omega*29L)/ferm.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-84, 0, 0, (int)frac, 4);

		frac = (int)(ferm.torque*29L)/ferm.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-24, 0, 0, (int)frac, 4);

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", ferm.power, ferm.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "fermentergui";
	}
}

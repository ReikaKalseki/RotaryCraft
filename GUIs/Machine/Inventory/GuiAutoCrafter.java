/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerAutoCrafter;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityAutoCrafter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAutoCrafter extends GuiPowerOnlyMachine
{
	private TileEntityAutoCrafter crafter;

	public GuiAutoCrafter(EntityPlayer p5ep, TileEntityAutoCrafter te)
	{
		super(new ContainerAutoCrafter(p5ep, te), te);
		ySize = 222;
		crafter = te;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		//buttonList.add(new GuiButton(0, var5+xSize-1, var6+32, 43, 20, "Get XP"));
		if (!crafter.continuous) {
			for (int i = 0; i < 18; i++) {
				int dx = var5+(i%9)*18+7;
				int dy = i < 9 ? var6+13 : var6+75;
				buttonList.add(new ImagedGuiButton(i, dx, dy, 18, 4, 176, 6, this.getGuiTexture(), RotaryCraft.class));
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 1000)
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CRAFTER.getMinValue(), crafter, button.id);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		GL11.glEnable(GL11.GL_BLEND);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		int x = 7;
		int y = 36;
		for (int i = 0; i < 18; i++) {
			if (crafter.crafting[i] > 0) {
				int dx = x+(i%9)*18;
				int dy = i >= 9 ? y+62 : y;
				float alpha = crafter.crafting[i]/2F;
				GL11.glColor4f(1, 1, 1, alpha);
				this.drawTexturedModalRect(dx, dy, 176, 11, 18, 9);
				//ReikaJavaLibrary.pConsole(i);
			}
		}
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		String var4 = "/gui/container.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//mc.renderEngine.bindTexture(GuiContainer.field_110408_a);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
		this.drawPowerTab(var5, var6);
	}

	@Override
	protected String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/craftergui2.png";
	}
}

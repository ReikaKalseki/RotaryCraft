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
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerVacuum;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiVacuum extends GuiPowerOnlyMachine
{
	private TileEntityVacuum vac;

	/**
	 * window height is calculated with this values, the more rows, the heigher
	 */
	private int inventoryRows = 0;

	public GuiVacuum(EntityPlayer p5ep, TileEntityVacuum te)
	{
		super(new ContainerVacuum(p5ep, te), te);
		allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		inventoryRows = te.getSizeInventory() / 9;
		ySize = var4 + inventoryRows * 18;
		vac = te;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, var5+xSize-1, var6+32, 43, 20, "Get XP"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id == 0)
			ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.VACUUM.getMinValue(), vac, PacketTarget.server);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		fontRendererObj.drawString("XP: "+String.format("%d", vac.getExperience()), 150-fontRendererObj.getStringWidth(String.format("%d", vac.getExperience())), 6, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		String var4 = "/gui/container.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//mc.renderEngine.bindTexture(GuiContainer.field_110408_a);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, inventoryRows * 18 + 17);
		this.drawTexturedModalRect(var5, var6 + inventoryRows * 18 + 17, 0, 126, xSize, 96);

		this.drawPowerTab(var5, var6);
	}

	@Override
	protected String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/basicstorage.png";
	}
}

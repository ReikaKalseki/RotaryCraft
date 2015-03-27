/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.IO.PacketTarget.ServerTarget;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Containers.ContainerCraftingPattern;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern.RecipeMode;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCraftingPattern extends GuiContainer {

	private final EntityPlayer player;

	public GuiCraftingPattern(EntityPlayer p5ep, World par2World)
	{
		super(new ContainerCraftingPattern(p5ep, par2World));
		player = p5ep;
	}

	private ItemStack getItem() {
		return player.getCurrentEquippedItem();
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, j+6, k+6, 20, 20, ""));
	}

	@Override
	public void actionPerformed(GuiButton b) {
		if (ItemRegistry.CRAFTPATTERN.matchItem(this.getItem())) {
			RecipeMode next = ItemCraftPattern.getMode(this.getItem()).next();
			ItemCraftPattern.setMode(this.getItem(), next);
			((ContainerCraftingPattern)player.openContainer).clearRecipe();
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CRAFTPATTERNMODE.getMinValue(), new ServerTarget(), next.ordinal());
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		if (ItemRegistry.CRAFTPATTERN.matchItem(this.getItem()))
			ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, ItemCraftPattern.getMode(this.getItem()).displayName, xSize/2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		if (ItemRegistry.CRAFTPATTERN.matchItem(this.getItem()))
			ReikaGuiAPI.instance.drawItemStack(itemRender, ItemCraftPattern.getMode(this.getItem()).getIcon(), 8, 8);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		String var4 = "/Reika/RotaryCraft/Textures/GUI/patterngui.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
	}
}

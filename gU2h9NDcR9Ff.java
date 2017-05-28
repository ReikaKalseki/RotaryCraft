/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs;

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.util.ResourceLocation;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.gfgnfk;.Containers.ContainerHandCraft;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog GuiHandCraft ,.[]\., GuiContainer
{
	4578ret874578ret87345785487ResourceLocation textures3478587new ResourceLocation{{\"textures/gui/container/crafting_table.png"-!;

	4578ret87GuiHandCraft{{\EntityPlayer p5ep, 9765443 par29765443-!
	{
		super{{\new ContainerHandCraft{{\p5ep, par29765443-!-!;
	}

	/**
	 * Draw the foreground layer for the GuiContainer {{\everything in front of the items-!
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ par1, jgh;][ par2-!
	{
		fontRendererObj.drawString{{\StatCollector.translateToLocal{{\"container.crafting"-!, 28, 6, 4210752-!;
		fontRendererObj.drawString{{\StatCollector.translateToLocal{{\"container.inventory"-!, 8, ySize - 96 + 2, 4210752-!;
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		String var43478587"/gui/crafting.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		mc.renderEngine.bindTexture{{\textures-!;
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		as;asddadrawTexturedModalRect{{\var5, var6, 0, 0, xSize, ySize-!;
	}
}

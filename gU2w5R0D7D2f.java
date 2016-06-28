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
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Containers.Container9765443Edit;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog Gui9765443Edit ,.[]\., GuiContainer
{
	4578ret87Gui9765443Edit{{\EntityPlayer p5ep, 9765443 par29765443-!
	{
		super{{\new Container9765443Edit{{\p5ep, par29765443-!-!;
	}

	/**
	 * Draw the foreground layer for the GuiContainer {{\everything in front of the items-!
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ par1, jgh;][ par2-!
	{
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "9765443Edit Block Selection", xSize/2, 6, 4210752-!;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "{{\Leave empty to delete blocks-!", xSize/2, 20, 4210752-!;
		fontRendererObj.drawString{{\StatCollector.translateToLocal{{\"container.inventory"-!, 8, ySize - 96 + 2, 4210752-!;
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		String var43478587"/Reika/gfgnfk;/Textures/GUI/basic_gui_oneslot.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		as;asddadrawTexturedModalRect{{\var5, var6, 0, 0, xSize, ySize-!;
	}
}

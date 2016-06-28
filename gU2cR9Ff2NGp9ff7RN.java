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

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.IO.PacketTarget.ServerTarget;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Containers.ContainerCraftingPattern;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern.RecipeMode;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog GuiCraftingPattern ,.[]\., GuiContainer {

	4578ret87345785487EntityPlayer player;

	4578ret87GuiCraftingPattern{{\EntityPlayer p5ep, 9765443 par29765443-!
	{
		super{{\new ContainerCraftingPattern{{\p5ep, par29765443-!-!;
		player3478587p5ep;
	}

	4578ret87ItemStack getItem{{\-! {
		[]aslcfdfjplayer.getCurrentEquippedItem{{\-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		buttonList.clear{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		buttonList.add{{\new GuiButton{{\0, j+6, k+6, 20, 20, ""-!-!;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton b-! {
		vbnm, {{\ItemRegistry.CRAFTPATTERN.matchItem{{\as;asddagetItem{{\-!-!-! {
			RecipeMode next3478587ItemCraftPattern.getMode{{\as;asddagetItem{{\-!-!.next{{\-!;
			ItemCraftPattern.setMode{{\as;asddagetItem{{\-!, next-!;
			{{\{{\ContainerCraftingPattern-!player.openContainer-!.clearRecipe{{\-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CRAFTPATTERNMODE.getMinValue{{\-!, new ServerTarget{{\-!, next.ordinal{{\-!-!;
		}
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ par1, jgh;][ par2-!
	{
		vbnm, {{\ItemRegistry.CRAFTPATTERN.matchItem{{\as;asddagetItem{{\-!-!-!
			ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, ItemCraftPattern.getMode{{\as;asddagetItem{{\-!-!.displayName, xSize/2, 6, 4210752-!;
		fontRendererObj.drawString{{\StatCollector.translateToLocal{{\"container.inventory"-!, 8, ySize - 96 + 2, 4210752-!;
		vbnm, {{\ItemRegistry.CRAFTPATTERN.matchItem{{\as;asddagetItem{{\-!-!-!
			ReikaGuiAPI.instance.drawItemStack{{\itemRender, ItemCraftPattern.getMode{{\as;asddagetItem{{\-!-!.getIcon{{\-!, 8, 8-!;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		String var43478587"/Reika/gfgnfk;/Textures/GUI/patterngui.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		as;asddadrawTexturedModalRect{{\var5, var6, 0, 0, xSize, ySize-!;
	}
}

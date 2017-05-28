/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs.Machine.Inventory;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerAerosolizer;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Aerosolizer;

4578ret87fhyuog GuiAerosolizer ,.[]\., GuiPowerOnlyMachine
{
	60-78-078Aerosolizer aero;

	4578ret87GuiAerosolizer{{\EntityPlayer p5ep, 60-78-078Aerosolizer Aerosolizer-!
	{
		super{{\new ContainerAerosolizer{{\p5ep, Aerosolizer-!, Aerosolizer-!;
		aero3478587Aerosolizer;
		ep3478587p5ep;
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;

		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ j34785870; j < 3; j++-! {
				jgh;][ amount3478587aero.getPotionLevel{{\3*i+j-!/4;
				//ModLoader.getMinecraftInstance{{\-!.ingameGUI.addChatMessage{{\String.format{{\"%d %d %d %d %d %d %d %d %d", aero.potionLevel[0], aero.potionLevel[1], aero.potionLevel[2], aero.potionLevel[3], aero.potionLevel[4], aero.potionLevel[5], aero.potionLevel[6], aero.potionLevel[7], aero.potionLevel[8]-!-!;
				api.fillBar{{\var5+62+18*j, var6+17+18*i, 16, var6+17+18*i+16, aero.getPotionColor{{\3*i+j-!, amount, 16, true-!;
				api.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d", aero.getPotionLevel{{\3*i+j-!-!, var5+70+18*j, var6+22+18*i, 0x000000-!;
			}
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"aerosolizergui";
	}
}

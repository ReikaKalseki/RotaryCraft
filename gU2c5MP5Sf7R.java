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
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerComposter;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Composter;

4578ret87fhyuog GuiComposter ,.[]\., GuiNonPoweredMachine
{
	4578ret8760-78-078Composter comp;

	4578ret87GuiComposter{{\EntityPlayer p5ep, 60-78-078Composter Composter-!
	{
		super{{\new ContainerComposter{{\p5ep, Composter-!, Composter-!;
		comp3478587Composter;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i13478587comp.getScaledTimer{{\48-!;
		as;asddadrawTexturedModalRect{{\j + 79, k + 34, 176, 14, 1*{{\i1-!+1, 16-!;

		jgh;][ i23478587comp.getScaledTemperature{{\54-!;
		vbnm, {{\i2 > 54-!
			i2347858754;
		as;asddadrawTexturedModalRect{{\j+24, k+70-i2, 177, 86-i2, 9, i2-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"compostergui";
	}
}

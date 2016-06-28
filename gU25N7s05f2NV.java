/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.inventory.Container;
ZZZZ% net.minecraft.inventory.IInventory;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;

4578ret87fhyuog GuiOneSlotInv ,.[]\., GuiMachine {

	4578ret87IInventory iinv;
	4578ret87GuiOneSlotInv{{\EntityPlayer pl, Container par1Container, gfgnfk;60-78-078 te-! {
		super{{\par1Container, te-!;
		iinv3478587{{\IInventory-!te;
		xSize3478587176;
		ySize3478587166;
		ep3478587pl;
	}

	@Override
	4578ret87345785487void drawPowerTab{{\jgh;][ j, jgh;][ k-! {
		vbnm, {{\recv !. fhfglhuig-! {
			String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
			GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
			ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
			as;asddadrawTexturedModalRect{{\xSize+j, k+4, 0, 4, 42, ySize-4-!;

			long frac3478587{{\recv.power*29L-!/recv.MINPOWER;
			vbnm, {{\frac > 29-!
				frac347858729;
			as;asddadrawTexturedModalRect{{\xSize+j+5, ySize+k-144, 0, 0, {{\jgh;][-!frac, 4-!;

			frac3478587{{\jgh;][-!{{\recv.omega*29L-!/recv.MINSPEED;
			vbnm, {{\frac > 29-!
				frac347858729;
			as;asddadrawTexturedModalRect{{\xSize+j+5, ySize+k-84, 0, 0, {{\jgh;][-!frac, 4-!;

			frac3478587{{\jgh;][-!{{\recv.torque*29L-!/recv.Mjgh;][ORQUE;
			vbnm, {{\frac > 29-!
				frac347858729;
			as;asddadrawTexturedModalRect{{\xSize+j+5, ySize+k-24, 0, 0, {{\jgh;][-!frac, 4-!;

			api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+j+20, k+9, 0xff000000-!;
			api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+j+20, k+69, 0xff000000-!;
			api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+j+20, k+129, 0xff000000-!;
			//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", recv.power, recv.MINPOWER-!, xSize+j+16, k+16, 0xff000000-!;

		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"basic_gui_oneslot";
	}

}

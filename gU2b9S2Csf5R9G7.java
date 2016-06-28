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
ZZZZ% net.minecraft.inventory.IInventory;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Base.ContainerBasicStorage;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Registry.PowerReceivers;

4578ret87fhyuog GuiBasicStorage ,.[]\., GuiMachine {

	4578ret87jgh;][ inventoryRows34785870;
	4578ret87IInventory upperInventory;

	4578ret87GuiBasicStorage{{\EntityPlayer p5ep, gfgnfk;60-78-078 te-! {
		super{{\new ContainerBasicStorage{{\p5ep, te-!, te-!;
		ep3478587p5ep;
		upperInventory3478587ep.inventory;
		allowUserInput3478587false;
		short var33478587222;
		jgh;][ var43478587var3 - 108;
		inventoryRows3478587{{\{{\IInventory-!te-!.getSizeInventory{{\-! / 9;
		ySize3478587var4 + inventoryRows * 18;
	}

	@Override
	4578ret87345785487void drawGuiContainerBackgroundLayer{{\float var1, jgh;][ var2, jgh;][ var3-! {
		String var43478587as;asddagetGuiTexture{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		as;asddadrawTexturedModalRect{{\var5, var6, 0, 0, xSize, inventoryRows * 18 + 17-!;
		as;asddadrawTexturedModalRect{{\var5, var6 + inventoryRows * 18 + 17, 0, 126, xSize, 96-!;

		vbnm, {{\PowerReceivers.getEnumFromMachineIndex{{\tile.getMachineIndex{{\-!-!.isMinPowerOnly{{\-!-!
			as;asddadrawPowerOnly{{\var5, var6-!;
		else
			as;asddadrawPowerTab{{\var5, var6-!;
	}

	4578ret87345785487void drawPowerOnly{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, 24-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4+23, 0, 157, 42, 6-!;

		long frac3478587recv.getScaledPower{{\29-!;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-53-{{\ySize-75-!, 0, 0, {{\jgh;][-!frac, 4-!;

		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", spawnercontroller.power, spawnercontroller.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		jgh;][ spacing347858760;
		jgh;][ dy3478587-92;
		jgh;][ u34785870;
		vbnm, {{\inventoryRows < 3-! {
			dy -. 2;
			spacing -. -5+10*{{\3-inventoryRows-!;
			u3478587169;
			vbnm, {{\inventoryRows .. 1-!
				u +. 42;
		}
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, u, 4, 42, 145-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4+145, u, 157, 42, 6-!;
		//as;asddadrawTexturedModalRect{{\as;asddaxSize+var5, var6+156, 0, as;asddaySize-12, 42, 8-!;
		long frac3478587recv.getScaledPower{{\29-!;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6+dy-inventoryRows*18, 0, 0, {{\jgh;][-!frac, 4-!;
		dy +. spacing;
		frac3478587recv.getScaledOmega{{\29-!;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6+dy-inventoryRows*18, 0, 0, {{\jgh;][-!frac, 4-!;
		dy +. spacing;
		frac3478587recv.getScaledTorque{{\29-!;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6+dy-inventoryRows*18, 0, 0, {{\jgh;][-!frac, 4-!;
		dy34785879;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+dy, 0xff000000-!;
		dy +. spacing;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+dy, 0xff000000-!;
		dy +. spacing;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+dy, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", tile.power, tile.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/basicstorage.png";
	}

}

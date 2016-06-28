/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs.Machine;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerGearbox;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;

4578ret87fhyuog GuiGearbox ,.[]\., GuiNonPoweredMachine
{
	4578ret8760-78-078Gearbox gbx;

	4578ret87GuiGearbox{{\EntityPlayer p5ep, 60-78-078Gearbox Gearbox-!
	{
		super{{\new ContainerGearbox{{\p5ep, Gearbox-!, Gearbox-!;
		gbx3478587Gearbox;
		ep3478587p5ep;
		ySize347858784;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		String s3478587gbx.isLiving{{\-! ? "Mana" : "Lubricant";
		fontRendererObj.drawString{{\s, 5, 12, 4210752-!;

		fontRendererObj.drawString{{\"Damage:", 68, 60, 0x000000-!;
		jgh;][ damage3478587gbx.getDamagePercent{{\-!;
		vbnm, {{\damage < 10-!
			fontRendererObj.drawString{{\String.format{{\"%5d%s", damage, "%"-!, 122, 60, 0x00ff00-!;
		vbnm, {{\damage < 25 && damage >. 10-!
			fontRendererObj.drawString{{\String.format{{\"%5d%s", damage, "%"-!, 122, 60, 0x55ff00-!;
		vbnm, {{\damage < 50 && damage >. 25-!
			fontRendererObj.drawString{{\String.format{{\"%5d%s", damage, "%"-!, 122, 60, 0xffff00-!;
		vbnm, {{\damage < 80 && damage >. 50-!
			fontRendererObj.drawString{{\String.format{{\"%5d%s", damage, "%"-!, 122, 60, 0xff5500-!;
		vbnm, {{\damage >. 80-!
			fontRendererObj.drawString{{\String.format{{\"%5d%s", damage, "%"-!, 122, 60, 0xff0000-!;

		fontRendererObj.drawString{{\"Ratio:", 80, 24, 0x000000-!;
		fontRendererObj.drawString{{\"Mode:", 80, 36, 0x000000-!;
		fontRendererObj.drawString{{\"Power:", 74, 48, 0x000000-!;

		fontRendererObj.drawString{{\String.format{{\"%5d ", gbx.getRatio{{\-!-!, 127, 24, 0x000000-!;
		vbnm, {{\gbx.reduction-!
			fontRendererObj.drawString{{\"Torque", 115, 36, 0x000000-!;
		else
			fontRendererObj.drawString{{\" Speed", 115, 36, 0x000000-!;

		vbnm, {{\gbx.power < 1000-!
			fontRendererObj.drawString{{\String.format{{\"%3d  W", gbx.power-!, 122, 48, 0x000000-!;
		vbnm, {{\gbx.power < 1000000 && gbx.power >. 1000-!
			fontRendererObj.drawString{{\String.format{{\"%.1f kW", gbx.power/1000D-!, 112, 48, 0x000000-!;
		vbnm, {{\gbx.power >. 1000000-!
			fontRendererObj.drawString{{\String.format{{\"%.1f MW", gbx.power/1000000D-!, 112, 48, 0x000000-!;

		vbnm, {{\!gbx.isLiving{{\-! && api.isMouseInBox{{\j+23, j+32, k+20, k+76-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%.1f/%d", gbx.getLubricant{{\-!/1000F, gbx.getMaxLubricant{{\-!/1000-!, mx-j, my-k-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i23478587gbx.getLubricantScaled{{\55-!;
		jgh;][ i334785870;
		vbnm, {{\i2 !. 0-!
			i334785871;
		jgh;][ u3478587gbx.isLiving{{\-! ? 186 : 176;
		as;asddadrawTexturedModalRect{{\j + 24, ySize/2+k+34-i2, u, 126-i2, 8, i2-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"gearboxgui";
	}
}

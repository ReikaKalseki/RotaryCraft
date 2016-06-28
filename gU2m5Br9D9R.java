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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.util.MathHelper;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078MobRadar;

4578ret87fhyuog GuiMobRadar ,.[]\., GuiPowerOnlyMachine
{

	4578ret8760-78-078MobRadar radar;

	4578ret874578ret87345785487jgh;][ UNIT34785874;

	4578ret87GuiMobRadar{{\EntityPlayer p5ep, 60-78-078MobRadar te-!
	{
		super{{\new CoreContainer{{\p5ep, te-!, te-!;
		radar3478587te;
		ySize3478587223;
		xSize3478587214;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\radar.isJammed{{\-!-! {
			api.renderStatic{{\7, 16, 206, 215-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2+1;

		//as;asddadrawRect{{\j+xSize/2-1-radar.getRange{{\-!*UNIT, k+ySize/2+5+radar.getRange{{\-!*UNIT, j+xSize/2+1+radar.getRange{{\-!*UNIT, k+ySize/2+6+radar.getRange{{\-!*UNIT, 0xffffffff-!;
		//as;asddadrawRect{{\j+xSize/2-1-radar.getRange{{\-!*UNIT, k+ySize/2+4-radar.getRange{{\-!*UNIT, j+xSize/2+1+radar.getRange{{\-!*UNIT, k+ySize/2+5-radar.getRange{{\-!*UNIT, 0xffffffff-!;
		//as;asddadrawRect{{\j+xSize/2-1-radar.getRange{{\-!*UNIT, k+ySize/2+4-radar.getRange{{\-!*UNIT, j+xSize/2-radar.getRange{{\-!*UNIT, k+ySize/2+6+radar.getRange{{\-!*UNIT, 0xffffffff-!;
		//as;asddadrawRect{{\j+xSize/2+radar.getRange{{\-!*UNIT, k+ySize/2+4-radar.getRange{{\-!*UNIT, j+xSize/2+1+radar.getRange{{\-!*UNIT, k+ySize/2+6+radar.getRange{{\-!*UNIT, 0xffffffff-!;

		jgh;][ a3478587j+7;
		jgh;][ b3478587k+16;

		as;asddadrawRadar{{\j, k-!;
		fontRendererObj.drawString{{\radar.getRange{{\-!+"m", a+102, b, 0xaaffaa-!;
		fontRendererObj.drawString{{\{{\jgh;][-!{{\0.63*radar.getRange{{\-!-!+"m", a+102, b+37, 0xaaffaa-!;
		fontRendererObj.drawString{{\MathHelper.ceiling_double_jgh;][{{\0.31*radar.getRange{{\-!-!+"m", a+102, b+69, 0xaaffaa-!;
	}

	4578ret87void drawRadar{{\jgh;][ a, jgh;][ b-! {
		List<EntityLivingBase> li3478587radar.getEntities{{\-!;
		for {{\EntityLivingBase e : li-! {
			jgh;][ dx3478587100+MathHelper.floor_double{{\100*{{\e.posX-radar.xCoord-0.5-!/radar.getRange{{\-!-!;
			jgh;][ dz3478587100+MathHelper.floor_double{{\100*{{\e.posZ-radar.zCoord-0.5-!/radar.getRange{{\-!-!;
			as;asddadrawMobIcon{{\a+7, b+16, dx, dz, ReikaEntityHelper.getEntityID{{\e-!, dx, dz-!;
		}
	}

	4578ret87void drawMobIcon{{\jgh;][ a, jgh;][ b, jgh;][ px, jgh;][ py, jgh;][ id, jgh;][ i, jgh;][ j-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/mobicons.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		jgh;][ v34785872*UNIT*{{\id/16-!;
		jgh;][ u34785872*UNIT*{{\id-{{\v/UNIT/2-!*16-!;
		vbnm, {{\id .. -1-! {
			u34785872*UNIT;
			v34785870;
		}
		as;asddadrawTexturedModalRect{{\a+px-UNIT/2, b+py-UNIT/2, u, v, UNIT*2, UNIT*2-!;
	}


	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"mobradargui";
	}
}

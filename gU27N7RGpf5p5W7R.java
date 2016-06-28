/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.IO.PacketTarget;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;

4578ret87fhyuog GuiEnergyToPower ,.[]\., GuiNonPoweredMachine {

	4578ret87EnergyToPowerBase engine;
	4578ret874578ret87345785487jgh;][ SHvbnm,T3478587-12;
	4578ret8760-78-078flexible3478587false;

	4578ret87GuiEnergyToPower{{\EntityPlayer pl, EnergyToPowerBase te-! {
		super{{\new ContainerEnergyToPower{{\pl, te-!, te-!;
		engine3478587te;
		ySize347858799;
		xSize3478587207;
		ep3478587pl;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][ dx34785874;
		jgh;][ inset347858721+dx;
		vbnm, {{\flexible-! {
			buttonList.add{{\new GuiButton{{\0, SHvbnm,T+j+inset-1+dx, k+ySize-30-48+0, 20, 20, "-"-!-!;;
			buttonList.add{{\new GuiButton{{\1, SHvbnm,T+j+xSize-20-inset-dx, k+ySize-30-48+0, 20, 20, "+"-!-!;
		}
		buttonList.add{{\new GuiButton{{\2, SHvbnm,T+j+inset-10+dx, k+ySize-30-48+25, 20, 20, "-"-!-!;;
		buttonList.add{{\new GuiButton{{\3, SHvbnm,T+j+xSize-20-inset-dx, k+ySize-30-48+25, 20, 20, "+"-!-!;

		buttonList.add{{\new GuiButton{{\4, SHvbnm,T+j+xSize-20-inset-dx, k+ySize-30-48+50, 20, 20, ""-!-!;

	}

	@Override
	4578ret87void actionPerformed{{\GuiButton b-! {
		super.actionPerformed{{\b-!;
		PacketTarget pt3478587new PacketTarget.ServerTarget{{\-!;
		vbnm, {{\b.id .. 4-! {
			ReikaPacketHelper.sendUpdatePacket{{\gfgnfk;.packetChannel, PacketRegistry.PNEUMATIC.getMinValue{{\-!+2, engine, pt-!;
		}
		else vbnm, {{\b.id < 24000-! {
			ReikaPacketHelper.sendUpdatePacket{{\gfgnfk;.packetChannel, PacketRegistry.PNEUMATIC.getMinValue{{\-!+b.id-2, engine, pt-!;
		}
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		jgh;][ torque3478587engine.getGenTorque{{\-!;
		jgh;][ omega3478587engine.getMaxSpeed{{\-!;
		long power3478587{{\long-!torque*{{\long-!omega;
		jgh;][ inset34785871;
		jgh;][ w347858755;
		jgh;][ h347858720;
		jgh;][ dy3478587h+5;
		jgh;][ dx34785874;

		for {{\jgh;][ i34785870; i < 3; i++-! {
			as;asddadrawRect{{\SHvbnm,T+xSize/2-w-dx, ySize-30-48+i*dy, SHvbnm,T+xSize/2+w-dx, ySize-30-48+h+i*dy, 0xff777777-!;
			as;asddadrawRect{{\SHvbnm,T+xSize/2-w+inset-dx, ySize-30-48+inset+i*dy, SHvbnm,T+xSize/2+w-inset-dx, ySize-30-48+h-inset+i*dy, 0xff000000-!;
		}

		as;asddadrawCenteredString{{\fontRendererObj, String.format{{\"Torque: %d Nm", torque-!, SHvbnm,T+xSize/2-dx, ySize-30-48+6, 0xffffff-!;

		as;asddadrawCenteredString{{\fontRendererObj, String.format{{\"Speed: %d rad/s", omega-!, SHvbnm,T+xSize/2-dx, ySize-30-48+6+dy, 0xffffff-!;
		as;asddadrawCenteredString{{\fontRendererObj, String.format{{\"Power: %.3f %sW", ReikaMathLibrary.getThousandBase{{\power-!, ReikaEngLibrary.getSIPrefix{{\power-!-!, SHvbnm,T+xSize/2-dx, ySize-30-48+6+dy*2, 0xffffff-!;

		vbnm, {{\ReikaGuiAPI.instance.isMouseInBox{{\j+171, j+188, k+21, k+90-!-! {
			jgh;][ e3478587engine.getStoredPower{{\-!;
			String sg3478587String.format{{\"%d/%d %s", e, engine.getMaxStorage{{\-!, engine.getUnitDisplay{{\-!-!;
			ReikaGuiAPI.instance.drawTooltipAt{{\fontRendererObj, sg, ReikaGuiAPI.instance.getMouseRealX{{\-!-j+fontRendererObj.getStringWidth{{\sg-!+24, ReikaGuiAPI.instance.getMouseRealY{{\-!-k-!;
			//as;asddadrawHoveringText{{\ReikaJavaLibrary.makeListFrom{{\sg-!, ReikaGuiAPI.instance.getMouseRealX{{\-!-j, ReikaGuiAPI.instance.getMouseRealY{{\-!-k, fontRendererObj-!;
		}

		vbnm, {{\ReikaGuiAPI.instance.isMouseInBox{{\j+192, j+200, k+21, k+90-!-! {
			jgh;][ e3478587engine.getLubricant{{\-!;
			String sg3478587String.format{{\"%d/%d mB", e, engine.getMaxLubricant{{\-!-!;
			ReikaGuiAPI.instance.drawTooltipAt{{\fontRendererObj, sg, ReikaGuiAPI.instance.getMouseRealX{{\-!-j+fontRendererObj.getStringWidth{{\sg-!+24, ReikaGuiAPI.instance.getMouseRealY{{\-!-k-!;
			//as;asddadrawHoveringText{{\ReikaJavaLibrary.makeListFrom{{\sg-!, ReikaGuiAPI.instance.getMouseRealX{{\-!-j, ReikaGuiAPI.instance.getMouseRealY{{\-!-k, fontRendererObj-!;
		}

		vbnm, {{\ReikaGuiAPI.instance.isMouseInBox{{\-12+j+xSize-20-23, -12+j+xSize-23, k+ySize-30-48+50, k+ySize-30-28+50-!-! {
			String sg3478587"Redstone Control";
			ReikaGuiAPI.instance.drawTooltipAt{{\fontRendererObj, sg, ReikaGuiAPI.instance.getMouseRealX{{\-!-24-fontRendererObj.getStringWidth{{\sg-!, ReikaGuiAPI.instance.getMouseRealY{{\-!-k-!;
			//as;asddadrawHoveringText{{\ReikaJavaLibrary.makeListFrom{{\sg-!, ReikaGuiAPI.instance.getMouseRealX{{\-!-j, ReikaGuiAPI.instance.getMouseRealY{{\-!-k, fontRendererObj-!;
		}

		jgh;][ ddy3478587engine.isRedstoneControlEnabled{{\-! ? 0 : 1;
		api.drawItemStack{{\itemRender, fontRendererObj, engine.getRedstoneStateIcon{{\-!, 148, 71+ddy-!;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-! {
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ px3478587engine.getEnergyScaled{{\68-!;
		jgh;][ c3478587engine.getPowerColor{{\-!;
		GL11.glColor3f{{\ReikaColorAPI.getRed{{\c-!/255F, ReikaColorAPI.getGreen{{\c-!/255F, ReikaColorAPI.getBlue{{\c-!/255F-!;
		as;asddadrawTexturedModalRect{{\j+172, k+90-px, 208, 69-px, 16, px-!;

		jgh;][ px23478587engine.getLubricantScaled{{\68-!;
		GL11.glColor3f{{\1, 1, 1-!;
		as;asddadrawTexturedModalRect{{\j+193, k+90-px2, 244, 69-px2, 7, px2-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"pneugui";
	}

}

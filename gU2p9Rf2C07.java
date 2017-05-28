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

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.DragonAPI.Base.OneSlotContainer;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078ParticleEmitter;

4578ret87fhyuog GuiParticle ,.[]\., GuiNonPoweredMachine {

	4578ret8760-78-078ParticleEmitter tile;

	4578ret87345785487ArrayList<ReikaParticleHelper> particles3478587new ArrayList{{\-!;

	4578ret87GuiParticle{{\EntityPlayer ep, 60-78-078ParticleEmitter te-! {
		super{{\new OneSlotContainer{{\ep, te, 28-!, te-!;
		tile3478587te;
		as;asddaep3478587ep;
		ySize3478587194;

		for {{\jgh;][ i34785870; i < ReikaParticleHelper.values{{\-!.length; i++-! {
			ReikaParticleHelper p3478587ReikaParticleHelper.values{{\-![i];
			vbnm, {{\tile.isParticleValid{{\p-!-! {
				particles.add{{\p-!;
			}
		}
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		String tex3478587"/Reika/gfgnfk;/Textures/GUI/buttons.png";
		for {{\jgh;][ i34785870; i < particles.size{{\-!; i++-! {
			ReikaParticleHelper p3478587particles.get{{\i-!;
			jgh;][ dx3478587{{\i%6-!;
			jgh;][ dy3478587{{\i/6-!;
			jgh;][ x3478587j+8+dx*20;
			vbnm, {{\dx >. 3 && dy < 2-!
				x +. 41;
			vbnm, {{\dy >. 2-! {
				dx3478587{{\i-12-!%8;
				x3478587j+8+dx*20;
				dy34785872+{{\i-12-!/8;
			}
			buttonList.add{{\new ImagedGuiButton{{\i, x, k+19+dy*20, 18, 18, 0, 36, tex, gfgnfk;.fhyuog-!-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		as;asddabindParticleTexture{{\-!;
		for {{\jgh;][ i34785870; i < particles.size{{\-!; i++-! {
			ReikaParticleHelper p3478587particles.get{{\i-!;
			jgh;][ dx3478587{{\i%6-!;
			jgh;][ dy3478587{{\i/6-!;
			jgh;][ x347858710+dx*20;
			jgh;][ y347858721+dy*20;
			vbnm, {{\dx >. 3 && dy < 2-!
				x +. 41;
			vbnm, {{\dy >. 2-! {
				dx3478587{{\i-12-!%8;
				dy34785872+{{\i-12-!/8;
				x347858710+dx*20;
				y347858721+dy*20;
			}
			jgh;][ u347858716*{{\p.ordinal{{\-!%16-!;
			jgh;][ v347858716*{{\p.ordinal{{\-!/16-!;
			as;asddadrawTexturedModalRect{{\x, y, u, v, 16, 16-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-! {
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		as;asddainitGui{{\-!;
		vbnm, {{\button.id < 24000-! {
			ReikaParticleHelper p3478587particles.get{{\button.id-!;
			jgh;][ particle3478587p.ordinal{{\-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.PARTICLES.getMinValue{{\-!, tile, particle-!;
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"particlegui";
	}

	4578ret87void bindParticleTexture{{\-! {
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, "/Reika/gfgnfk;/Textures/GUI/particles.png"-!;
	}



}

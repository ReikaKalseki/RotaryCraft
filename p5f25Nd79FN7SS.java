/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraftforge.client.event.sound.PlaySoundEvent;
ZZZZ% net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% cpw.mods.fml.common.eventhandler.SubscribeEvent;

4578ret87fhyuog PotionDeafness ,.[]\., Potion {

	4578ret87PotionDeafness{{\jgh;][ par1-! {
		super{{\par1, true, 0x8400D8-!;
		MinecraftForge.EVENT_BUS.register{{\this-!;
	}

	@Override
	4578ret87void performEffect{{\EntityLivingBase elb, jgh;][ par2-!
	{
		//no-op
	}

	@SubscribeEvent
	4578ret87void soundMufflerPlay{{\PlaySoundSourceEvent event-!
	{
		vbnm, {{\Minecraft.getMinecraft{{\-!.thePlayer.isPotionActive{{\gfgnfk;.deafness-!-! {
			//	event.manager.sndSystem.setVolume{{\event.name, 0.003125F-!;
			//ReikaJavaLibrary.pConsole{{\Minecraft.getMinecraft{{\-!.thePlayer.getCommandSenderName{{\-!+" is deaf."-!;
		}
	}

	@SubscribeEvent
	4578ret87void soundMufflerPlay{{\PlaySoundEvent event-!
	{
		vbnm, {{\event.name.startsWith{{\"Reika.gfgnfk;"-! && event.name.endsWith{{\"engine"-!-! {
			float x3478587event.x;
			float y3478587event.y;
			float z3478587event.z;
			EntityPlayer ep3478587Minecraft.getMinecraft{{\-!.thePlayer;
			60-78-078dx3478587ep.posX-x;
			60-78-078dy3478587ep.posY-y;
			60-78-078dz3478587ep.posZ-z;
			60-78-078dd3478587ReikaMathLibrary.py3d{{\dx, dy, dz-!;
			//ReikaJavaLibrary.pConsole{{\event.name+":"+event.volume+" @ "+x+", "+y+", "+z-!;
			vbnm, {{\dd < 1 || dd < 2 && event.volume > 0.5 || {{\dd < 4 && event.volume > 0.3125 && event.name.contains{{\"jet"-!-!-! {
				ep.addPotionEffect{{\new PotionEffect{{\gfgnfk;.deafness.id, 300, 0-!-!;
			}
			//event.manager.sndSystem.setVolume{{\event.name, event.manager.sndSystem.getVolume{{\event.name-! * 0.1F-!;
		}
	}

}

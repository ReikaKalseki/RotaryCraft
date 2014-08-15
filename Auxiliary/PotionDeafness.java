/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PotionDeafness extends Potion {

	public PotionDeafness(int par1) {
		super(par1, true, 0x8400D8);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void performEffect(EntityLivingBase elb, int par2)
	{
		//no-op
	}

	@SubscribeEvent
	public void soundMufflerPlay(PlaySoundSourceEvent event)
	{
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(RotaryCraft.deafness)) {
			//	event.manager.sndSystem.setVolume(event.name, 0.003125F);
			//ReikaJavaLibrary.pConsole(Minecraft.getMinecraft().thePlayer.getCommandSenderName()+" is deaf.");
		}
	}

	@SubscribeEvent
	public void soundMufflerPlay(PlaySoundEvent event)
	{
		if (event.name.startsWith("Reika.RotaryCraft") && event.name.endsWith("engine")) {
			float x = event.x;
			float y = event.y;
			float z = event.z;
			EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
			double dx = ep.posX-x;
			double dy = ep.posY-y;
			double dz = ep.posZ-z;
			double dd = ReikaMathLibrary.py3d(dx, dy, dz);
			//ReikaJavaLibrary.pConsole(event.name+":"+event.volume+" @ "+x+", "+y+", "+z);
			if (dd < 1 || dd < 2 && event.volume > 0.5 || (dd < 4 && event.volume > 0.3125 && event.name.contains("jet"))) {
				ep.addPotionEffect(new PotionEffect(RotaryCraft.deafness.id, 300, 0));
			}
			//event.manager.sndSystem.setVolume(event.name, event.manager.sndSystem.getVolume(event.name) * 0.1F);
		}
	}

}
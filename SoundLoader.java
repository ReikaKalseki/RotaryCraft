/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;

public class SoundLoader {

	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event) {
		ReikaJavaLibrary.spamConsole(SoundRegistry.soundList[0]);
		for (int i = 0; i < SoundRegistry.soundList.length; i++) {
			try {
				event.manager.soundPoolSounds.addSound(SoundRegistry.soundList[i].getPath(), SoundRegistry.soundList[i].getURL());
				ReikaJavaLibrary.pConsole(SoundRegistry.soundList[i]);
			}
			catch (Exception e) {
				ReikaJavaLibrary.pConsole("SOUND NOT FOUND");
				throw new RuntimeException("Sound file "+SoundRegistry.soundList[i].getName()+" not found!");
			}
		}
	}
}

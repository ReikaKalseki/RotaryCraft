/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class SoundLoader {

	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event) {
		for (int i = 0; i < SoundRegistry.soundList.length; i++) {
			try {
				event.manager.soundPoolSounds.addSound(SoundRegistry.soundList[i].getPath(), SoundRegistry.soundList[i].getURL());
			}
			catch (Exception e) {
				throw new RuntimeException("Sound file "+SoundRegistry.soundList[i].getName()+" not found!");
			}
		}
	}
}

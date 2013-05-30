/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.io.File;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class RotarySounds {

	public static void addSounds() {
		Minecraft mc = Minecraft.getMinecraft();
		if (!soundsFound())
			throw new RuntimeException("RotaryCraft sound files are not properly installed! Read the README file!");
		mc.installResource("sound3/Reika/RotaryCraft/jetengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/jetengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/gasengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/gasengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/microengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/microengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/steamengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/steamengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/elecengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/elecengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/hydroengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/hydroengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/windengine.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/windengine.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/pulsejet.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/pulsejet.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/pump.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/pump.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/piledriver.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/piledriver.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/sprinkler.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/sprinkler.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/smokealarm.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/smokealarm.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/knockback.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/knockback.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/flywheel.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/flywheel.ogg"));

		mc.installResource("sound3/Reika/RotaryCraft/music/basslo.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/basslo.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/basshi.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/basshi.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/plinglo.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/plinglo.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/plinghi.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/plinghi.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/harplo.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/harplo.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/harphi.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/harphi.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/harp.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/harp.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/bass.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/bass.ogg"));
		mc.installResource("sound3/Reika/RotaryCraft/music/pling.ogg",
				new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/music/pling.ogg"));
	}

	private static boolean soundsFound() {
		Minecraft mc = Minecraft.getMinecraft();
		File f = new File(mc.mcDataDir, "resources/sound3/mod/Reika/RotaryCraft/");
		return f.exists();
	}
}

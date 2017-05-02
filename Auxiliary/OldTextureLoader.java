/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Calendar;
import java.util.EnumMap;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.IntHashMap;
import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.RotaryCraft.Blocks.BlockFlywheel;
import Reika.RotaryCraft.Blocks.BlockGearbox;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class OldTextureLoader {

	public static final OldTextureLoader instance = new OldTextureLoader();

	private final EnumMap<MachineRegistry, IconSide> machineIcons = new EnumMap(MachineRegistry.class);
	private final IntHashMap textureIndices = new IntHashMap();

	private OldTextureLoader() {
		machineIcons.put(MachineRegistry.SHAFT, new IconSide(8).addSideTexture(2, 9).addSideTexture(3, 9));
		machineIcons.put(MachineRegistry.GEARBOX, new IconSide(8).addSideTexture(0, 10).addSideTexture(1, 10).addSideTexture(2, 9).addSideTexture(3, 9));
		machineIcons.put(MachineRegistry.BEDROCKBREAKER, new IconSide(1).addSideTexture(2, 2).addSideTexture(3, 3).addSideTexture(1, 4));
		machineIcons.put(MachineRegistry.FERMENTER, new IconSide(20).addSideTexture(2, 21).addSideTexture(3, 22));
		machineIcons.put(MachineRegistry.CLUTCH, new IconSide(25).addSideTexture(2, 26).addSideTexture(3, 26));
		machineIcons.put(MachineRegistry.BEVELGEARS, new IconSide(27).addSideTexture(3, 28).addSideTexture(5, 28).addSideTexture(1, 28));
		machineIcons.put(MachineRegistry.FLOODLIGHT, new IconSide(29).addSideTexture(3, 31).addSideTexture(4, 30).addSideTexture(5, 30).addSideTexture(0, 30).addSideTexture(1, 30));
		machineIcons.put(MachineRegistry.SPLITTER, new IconSide(1).addSideTexture(1, 17).addSideTexture(0, 18).addSideTexture(3, 19).addSideTexture(4, 19).addSideTexture(5, 19));
		machineIcons.put(MachineRegistry.GRINDER, new IconSide(49).addSideTexture(2, 50).addSideTexture(3, 51));
		machineIcons.put(MachineRegistry.HEATRAY, new IconSide(53).addSideTexture(2, 54));
		machineIcons.put(MachineRegistry.BORER, new IconSide(57).addSideTexture(2, 58).addSideTexture(3, 59));
		machineIcons.put(MachineRegistry.FLYWHEEL, new IconSide(23).addSideTexture(2, 24));
		machineIcons.put(MachineRegistry.ENGINE, new IconSide(1).addSideTexture(2, 14).addSideTexture(3, 15));
		machineIcons.put(MachineRegistry.DYNAMOMETER, new IconSide(34).addSideTexture(2, 35).addSideTexture(3, 35));
	}

	public boolean loadOldTextures() {
		if (ReikaObfuscationHelper.isDeObfEnvironment())
			return false;
		if (!DragonOptions.APRIL.getState())
			return false;
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) == Calendar.APRIL && c.get(Calendar.DAY_OF_MONTH) <= 2;
	}

	public IIcon getOldTexture(Block b, int meta, int side) {
		return this.getIconByIndex(this.getIndex(b, meta, side));
	}

	public int getIndex(Block b, int meta, int side) {
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(b, meta);
		IconSide ics = machineIcons.get(m);
		int index = ics == null ? -1 : ics.sideTextures.containsKey(side) ? ics.sideTextures.get(side) : ics.fallback;
		if (b instanceof BlockGearbox && index == 10) {
			index += meta;
		}
		if (b instanceof BlockFlywheel) {
			index += 16*(meta/4);
		}
		return index;
	}

	private IIcon getIconByIndex(int index) {
		IIcon ico = null;
		if (index >= 0) {
			ico = (IIcon)textureIndices.lookup(index);
		}
		else {
			ico = (IIcon)textureIndices.lookup(-1);
		}
		return ico != null ? ico : (IIcon)textureIndices.lookup(-1);
	}

	public void reloadOldTextures(TextureMap map) {
		if (map.getTextureType() == 0) {
			for (int i = 0; i < 256; i++) {
				textureIndices.addKey(i, map.registerIcon(this.getName(i)));
			}

			textureIndices.addKey(-1, map.registerIcon(this.getName(255)));
		}
	}

	private String getName(int idx) {
		int row = idx / 16;
		int col = idx % 16;
		return "rotarycraft:old/tile"+col+"_"+row;
	}

	private static class IconSide {

		public final int fallback;
		private HashMap<Integer, Integer> sideTextures = new HashMap();

		private IconSide(int f) {
			fallback = f;
		}

		private IconSide addSideTexture(int side, int tex) {
			sideTextures.put(side, tex);
			return this;
		}

		public int getTexture(int idx) {
			return sideTextures.containsKey(idx) ? sideTextures.get(idx) : 255;
		}

	}

}

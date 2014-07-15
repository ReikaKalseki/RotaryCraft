/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import Reika.DragonAPI.ModInteract.ReikaThaumHelper;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class MachineAspectMapper {

	public static final MachineAspectMapper instance = new MachineAspectMapper();

	private final HashMap<MachineKey, AspectList> data = new HashMap();

	private MachineAspectMapper() {

		this.addAspect(MachineRegistry.BEDROCKBREAKER, Aspect.VOID, 2);
		this.addAspect(MachineRegistry.BEDROCKBREAKER, Aspect.STONE, 2);
		this.addAspect(MachineRegistry.BEDROCKBREAKER, Aspect.MINE, 8);

		this.addAspect(MachineRegistry.FERMENTER, Aspect.EXCHANGE, 2);
		this.addAspect(MachineRegistry.FERMENTER, Aspect.PLANT, 1);
		this.addAspect(MachineRegistry.FERMENTER, Aspect.TREE, 1);

		this.addAspect(MachineRegistry.FLOODLIGHT, Aspect.LIGHT, 8);

		this.addAspect(MachineRegistry.GRINDER, Aspect.ENTROPY, 5);

		this.addAspect(MachineRegistry.HEATRAY, Aspect.FIRE, 8);
		this.addAspect(MachineRegistry.HEATRAY, Aspect.WEAPON, 6);

		this.addAspect(MachineRegistry.BORER, Aspect.MINE, 8);

		this.addAspect(MachineRegistry.LIGHTBRIDGE, Aspect.LIGHT, 4);
		this.addAspect(MachineRegistry.LIGHTBRIDGE, Aspect.TRAVEL, 4);

		this.addAspect(MachineRegistry.PUMP, Aspect.WATER, 4);
		this.addAspect(MachineRegistry.PUMP, Aspect.FIRE, 4);
		this.addAspect(MachineRegistry.PUMP, Aspect.MINE, 2);

		this.addAspect(MachineRegistry.RESERVOIR, Aspect.VOID, 8);

		this.addAspect(MachineRegistry.AEROSOLIZER, Aspect.AURA, 4);
		this.addAspect(MachineRegistry.AEROSOLIZER, Aspect.POISON, 4);
		this.addAspect(MachineRegistry.AEROSOLIZER, Aspect.TRAVEL, 4);
		this.addAspect(MachineRegistry.AEROSOLIZER, Aspect.SENSES, 4);

		this.addAspect(MachineRegistry.EXTRACTOR, Aspect.GREED, 2);
		this.addAspect(MachineRegistry.EXTRACTOR, Aspect.HARVEST, 4);

		this.addAspect(MachineRegistry.PULSEJET, Aspect.FIRE, 4);
		this.addAspect(MachineRegistry.PULSEJET, Aspect.CRAFT, 2);

		this.addAspect(MachineRegistry.COMPACTOR, Aspect.CRYSTAL, 4);
		this.addAspect(MachineRegistry.COMPACTOR, Aspect.EXCHANGE, 2);

		this.addAspect(MachineRegistry.FAN, Aspect.PLANT, 2);
		this.addAspect(MachineRegistry.FAN, Aspect.HARVEST, 4);
		this.addAspect(MachineRegistry.FAN, Aspect.TRAVEL, 4);

		this.addAspect(MachineRegistry.FRACTIONATOR, Aspect.EXCHANGE, 4);
		this.addAspect(MachineRegistry.FRACTIONATOR, Aspect.ENERGY, 4);

		this.addAspect(MachineRegistry.GPR, Aspect.SENSES, 8);

		this.addAspect(MachineRegistry.OBSIDIAN, Aspect.DARKNESS, 2);
		this.addAspect(MachineRegistry.OBSIDIAN, Aspect.STONE, 5);
		this.addAspect(MachineRegistry.OBSIDIAN, Aspect.ARMOR, 2);

		this.addAspect(MachineRegistry.PILEDRIVER, Aspect.MINE, 6);

		this.addAspect(MachineRegistry.VACUUM, Aspect.VOID, 8);
		this.addAspect(MachineRegistry.VACUUM, Aspect.TRAVEL, 8);

		this.addAspect(MachineRegistry.FIREWORK, Aspect.CRAFT, 3);
		this.addAspect(MachineRegistry.FIREWORK, Aspect.SENSES, 2);
		this.addAspect(MachineRegistry.FIREWORK, Aspect.LIGHT, 2);

		this.addAspect(MachineRegistry.SPRINKLER, Aspect.WATER, 4);
		this.addAspect(MachineRegistry.SPRINKLER, Aspect.PLANT, 4);

		this.addAspect(MachineRegistry.WOODCUTTER, Aspect.TREE, 8);
		this.addAspect(MachineRegistry.WOODCUTTER, Aspect.HARVEST, 4);

		this.addAspect(MachineRegistry.SPAWNERCONTROLLER, Aspect.DARKNESS, 2);
		this.addAspect(MachineRegistry.SPAWNERCONTROLLER, Aspect.BEAST, 8);
		this.addAspect(MachineRegistry.SPAWNERCONTROLLER, Aspect.UNDEAD, 4);

		this.addAspect(MachineRegistry.PLAYERDETECTOR, Aspect.MAN, 4);

		this.addAspect(MachineRegistry.HEATER, Aspect.FIRE, 6);
		this.addAspect(MachineRegistry.HEATER, Aspect.ENERGY, 2);

		this.addAspect(MachineRegistry.BAITBOX, Aspect.SENSES, 6);
		this.addAspect(MachineRegistry.BAITBOX, Aspect.BEAST, 4);
		this.addAspect(MachineRegistry.BAITBOX, Aspect.TRAVEL, 2);

		this.addAspect(MachineRegistry.AUTOBREEDER, Aspect.BEAST, 6);

		this.addAspect(MachineRegistry.SMOKEDETECTOR, Aspect.FIRE, 2);
		this.addAspect(MachineRegistry.SMOKEDETECTOR, Aspect.SENSES, 3);

		this.addAspect(MachineRegistry.MOBRADAR, Aspect.SENSES, 6);
		this.addAspect(MachineRegistry.MOBRADAR, Aspect.BEAST, 2);

		this.addAspect(MachineRegistry.ADVANCEDGEARS, 1, Aspect.EXCHANGE, 8);
		this.addAspect(MachineRegistry.ADVANCEDGEARS, 2, Aspect.EXCHANGE, 4);

		this.addAspect(MachineRegistry.TNTCANNON, Aspect.FLIGHT, 4);
		this.addAspect(MachineRegistry.TNTCANNON, Aspect.WEAPON, 6);

		this.addAspect(MachineRegistry.SONICWEAPON, Aspect.WEAPON, 8);
		this.addAspect(MachineRegistry.SONICWEAPON, Aspect.SENSES, 8);

		this.addAspect(MachineRegistry.BLASTFURNACE, Aspect.ORDER, 5);
		this.addAspect(MachineRegistry.BLASTFURNACE, Aspect.EXCHANGE, 5);
		this.addAspect(MachineRegistry.BLASTFURNACE, Aspect.FIRE, 2);

		this.addAspect(MachineRegistry.FORCEFIELD, Aspect.ARMOR, 8);

		this.addAspect(MachineRegistry.MUSICBOX, Aspect.SENSES, 6);

		this.addAspect(MachineRegistry.MOBHARVESTER, Aspect.WEAPON, 2);
		this.addAspect(MachineRegistry.MOBHARVESTER, Aspect.HARVEST, 4);

		this.addAspect(MachineRegistry.PROJECTOR, Aspect.LIGHT, 2);
	}

	private void addAspect(MachineRegistry m, Aspect a, int amt) {
		MachineKey key = new MachineKey(m);
		AspectList al = data.get(key);
		if (al == null) {
			al = new AspectList();
			data.put(key, al);
		}
		al.merge(a, amt);
		//ReikaJavaLibrary.pConsole(this.data.get(key));
	}

	private void addAspect(MachineRegistry m, int offset, Aspect a, int amt) {
		MachineKey key = new MachineKey(m, offset);
		AspectList al = data.get(key);
		if (al == null) {
			al = new AspectList();
			data.put(key, al);
		}
		al.merge(a, amt);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (MachineKey m : data.keySet()) {
			sb.append(m+": "+ReikaThaumHelper.aspectsToString(data.get(m)));
			sb.append("; ");
		}
		sb.append("]");
		return sb.toString();
	}

	private static class MachineKey {
		public final MachineRegistry machine;
		public final int offset;

		private MachineKey(MachineRegistry m) {
			this(m, 0);
		}

		private MachineKey(MachineRegistry m, int offset) {
			machine = m;
			this.offset = offset;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof MachineKey) {
				MachineKey mk = (MachineKey)o;
				return mk.machine == machine && mk.offset == offset;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return machine.ordinal()+offset*2048;
		}

		@Override
		public String toString() {
			return machine+"%"+offset;
		}
	}

	private AspectList getAspects(MachineRegistry m, int offset) {
		ItemStack is = this.getItem(m, offset);
		AspectList al = ThaumcraftApiHelper.generateTags(is.itemID, is.getItemDamage());
		if (al == null)
			al = new AspectList();
		al.merge(Aspect.MOTION, 4);
		al.merge(Aspect.METAL, 8);
		al.merge(Aspect.MECHANISM, 8);
		if (m == MachineRegistry.ENGINE) {
			al.merge(Aspect.MOTION, 15);
			al.merge(Aspect.ENERGY, 15);
		}
		if (m == MachineRegistry.GEARBOX) {
			al.merge(Aspect.EXCHANGE, 8);
		}
		if (m.isTransmissionMachine()) {
			al.merge(Aspect.MOTION, 8);
		}
		if (m.hasNBTVariants()) {
			al.merge(Aspect.EXCHANGE, 1);
		}
		AspectList prekey = data.get(new MachineKey(m, offset));
		if (prekey != null) {
			for (Aspect a : prekey.aspects.keySet()) {
				int amt = prekey.aspects.get(a);
				al.merge(a, amt);
			}
		}
		return al;
	}

	private ItemStack getItem(MachineRegistry m, int offset) {
		return m.hasSubdivisions() ? m.getCraftedMetadataProduct(offset) : m.getCraftedProduct();
	}

	public void register() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.hasSubdivisions()) {
				int n = m.getNumberSubtypes();
				for (int k = 0; k < n; k++) {
					ItemStack is = m.getCraftedMetadataProduct(i);
					ThaumcraftApi.registerObjectTag(is.itemID, is.getItemDamage(), this.getAspects(m, k));
					ThaumcraftApi.registerObjectTag(m.getBlockID(), k, this.getAspects(m, k));
				}
			}
			else {
				ItemStack is = m.getCraftedProduct();
				ThaumcraftApi.registerObjectTag(is.itemID, is.getItemDamage(), this.getAspects(m, 0));
				ThaumcraftApi.registerObjectTag(m.getBlockID(), m.getMachineMetadata(), this.getAspects(m, 0));
			}
		}
	}

}

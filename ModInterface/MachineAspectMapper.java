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

import Reika.DragonAPI.ModInteract.ReikaThaumHelper;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

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
		this.addAspect(MachineRegistry.SONICWEAPON, Aspect.AURA, 2);

		this.addAspect(MachineRegistry.BLASTFURNACE, Aspect.ORDER, 5);
		this.addAspect(MachineRegistry.BLASTFURNACE, Aspect.EXCHANGE, 5);
		this.addAspect(MachineRegistry.BLASTFURNACE, Aspect.FIRE, 2);

		this.addAspect(MachineRegistry.FORCEFIELD, Aspect.ARMOR, 8);

		this.addAspect(MachineRegistry.MUSICBOX, Aspect.SENSES, 6);

		this.addAspect(MachineRegistry.MOBHARVESTER, Aspect.WEAPON, 2);
		this.addAspect(MachineRegistry.MOBHARVESTER, Aspect.HARVEST, 4);

		this.addAspect(MachineRegistry.PROJECTOR, Aspect.LIGHT, 2);

		this.addAspect(MachineRegistry.RAILGUN, Aspect.TRAVEL, 5);
		this.addAspect(MachineRegistry.RAILGUN, Aspect.WEAPON, 8);

		this.addAspect(MachineRegistry.WEATHERCONTROLLER, Aspect.WEATHER, 8);
		this.addAspect(MachineRegistry.WEATHERCONTROLLER, Aspect.WATER, 2);

		//this.addAspect(MachineRegistry.REFRESHER, Aspect.TIME, 4); not native to ThaumCraft

		this.addAspect(MachineRegistry.FREEZEGUN, Aspect.WEAPON, 8);
		this.addAspect(MachineRegistry.FREEZEGUN, Aspect.ICE, 6);

		this.addAspect(MachineRegistry.CAVESCANNER, Aspect.SENSES, 8);

		this.addAspect(MachineRegistry.SCALECHEST, Aspect.VOID, 8);

		this.addAspect(MachineRegistry.IGNITER, Aspect.FIRE, 8);
		this.addAspect(MachineRegistry.IGNITER, Aspect.WEAPON, 2);

		this.addAspect(MachineRegistry.MAGNETIZER, Aspect.ENERGY, 2);

		this.addAspect(MachineRegistry.CONTAINMENT, Aspect.TRAP, 8);

		this.addAspect(MachineRegistry.PURIFIER, Aspect.EXCHANGE, 6);

		this.addAspect(MachineRegistry.LASERGUN, Aspect.FIRE, 8);
		this.addAspect(MachineRegistry.LASERGUN, Aspect.WEAPON, 8);
		this.addAspect(MachineRegistry.LASERGUN, Aspect.LIGHT, 4);

		this.addAspect(MachineRegistry.ITEMCANNON, Aspect.TRAVEL, 6);

		this.addAspect(MachineRegistry.LANDMINE, Aspect.TRAP, 8);
		this.addAspect(MachineRegistry.LANDMINE, Aspect.WEAPON, 8);

		this.addAspect(MachineRegistry.FRICTION, Aspect.FIRE, 2);
		this.addAspect(MachineRegistry.FRICTION, Aspect.EXCHANGE, 2);

		this.addAspect(MachineRegistry.BLOCKCANNON, Aspect.WEAPON, 4);
		this.addAspect(MachineRegistry.BLOCKCANNON, Aspect.TRAVEL, 3);

		this.addAspect(MachineRegistry.BUCKETFILLER, Aspect.EXCHANGE, 2);

		this.addAspect(MachineRegistry.MIRROR, Aspect.LIGHT, 6);
		this.addAspect(MachineRegistry.MIRROR, Aspect.CRYSTAL, 3);

		this.addAspect(MachineRegistry.SOLARTOWER, Aspect.ENERGY, 8);

		this.addAspect(MachineRegistry.SPYCAM, Aspect.SENSES, 8);

		this.addAspect(MachineRegistry.SELFDESTRUCT, Aspect.TRAP, 8);
		this.addAspect(MachineRegistry.SELFDESTRUCT, Aspect.WEAPON, 6);

		this.addAspect(MachineRegistry.COMPRESSOR, Aspect.AIR, 4);

		this.addAspect(MachineRegistry.PNEUENGINE, Aspect.AIR, 4);

		this.addAspect(MachineRegistry.LAMP, Aspect.LIGHT, 8);

		this.addAspect(MachineRegistry.EMP, Aspect.WEAPON, 12);
		this.addAspect(MachineRegistry.EMP, Aspect.ENERGY, 40);
		this.addAspect(MachineRegistry.EMP, Aspect.ENTROPY, 12);
		this.addAspect(MachineRegistry.EMP, Aspect.AURA, 20);

		this.addAspect(MachineRegistry.LINEBUILDER, Aspect.TRAVEL, 2);

		this.addAspect(MachineRegistry.BEAMMIRROR, Aspect.LIGHT, 4);

		this.addAspect(MachineRegistry.TERRAFORMER, Aspect.EARTH, 4);
		this.addAspect(MachineRegistry.TERRAFORMER, Aspect.WATER, 4);
		this.addAspect(MachineRegistry.TERRAFORMER, Aspect.ICE, 4);
		this.addAspect(MachineRegistry.TERRAFORMER, Aspect.FIRE, 4);
		this.addAspect(MachineRegistry.TERRAFORMER, Aspect.AIR, 4);

		this.addAspect(MachineRegistry.SORTING, Aspect.MIND, 2);

		this.addAspect(MachineRegistry.FUELENHANCER, Aspect.ENERGY, 3);
		this.addAspect(MachineRegistry.FUELENHANCER, Aspect.CRAFT, 3);

		this.addAspect(MachineRegistry.ARROWGUN, Aspect.WEAPON, 4);
		this.addAspect(MachineRegistry.ARROWGUN, Aspect.FLIGHT, 1);

		this.addAspect(MachineRegistry.BOILER, Aspect.WATER, 4);

		this.addAspect(MachineRegistry.STEAMTURBINE, Aspect.WATER, 4);

		this.addAspect(MachineRegistry.FERTILIZER, Aspect.PLANT, 6);
		this.addAspect(MachineRegistry.FERTILIZER, Aspect.WATER, 4);

		this.addAspect(MachineRegistry.LAVAMAKER, Aspect.FIRE, 6);
		this.addAspect(MachineRegistry.LAVAMAKER, Aspect.STONE, 4);

		this.addAspect(MachineRegistry.AGGREGATOR, Aspect.WATER, 8);
		this.addAspect(MachineRegistry.AGGREGATOR, Aspect.AIR, 4);

		this.addAspect(MachineRegistry.AIRGUN, Aspect.WEAPON, 4);
		this.addAspect(MachineRegistry.AIRGUN, Aspect.AIR, 4);
		this.addAspect(MachineRegistry.AIRGUN, Aspect.TRAVEL, 4);

		this.addAspect(MachineRegistry.SONICBORER, Aspect.WEAPON, 1);
		this.addAspect(MachineRegistry.SONICBORER, Aspect.AIR, 6);
		this.addAspect(MachineRegistry.SONICBORER, Aspect.MINE, 8);
		this.addAspect(MachineRegistry.SONICBORER, Aspect.ENTROPY, 2);

		this.addAspect(MachineRegistry.FUELENGINE, Aspect.ENERGY, 8);
		this.addAspect(MachineRegistry.FUELENGINE, Aspect.FIRE, 3);
		this.addAspect(MachineRegistry.FUELENGINE, Aspect.WATER, 1);

		this.addAspect(MachineRegistry.FILLINGSTATION, Aspect.WATER, 1);
		this.addAspect(MachineRegistry.FILLINGSTATION, Aspect.EXCHANGE, 1);

		this.addAspect(MachineRegistry.BELT, Aspect.CLOTH, 4);

		this.addAspect(MachineRegistry.VANDEGRAFF, Aspect.WEAPON, 6);
		this.addAspect(MachineRegistry.VANDEGRAFF, Aspect.EXCHANGE, 2);
		this.addAspect(MachineRegistry.VANDEGRAFF, Aspect.ENERGY, 4);

		this.addAspect(MachineRegistry.DEFOLIATOR, Aspect.PLANT, 4);
		this.addAspect(MachineRegistry.DEFOLIATOR, Aspect.POISON, 8);
		this.addAspect(MachineRegistry.DEFOLIATOR, Aspect.AURA, 2);

		this.addAspect(MachineRegistry.BIGFURNACE, Aspect.FIRE, 8);
		this.addAspect(MachineRegistry.BIGFURNACE, Aspect.STONE, 2);
		this.addAspect(MachineRegistry.BIGFURNACE, Aspect.CRAFT, 4);

		this.addAspect(MachineRegistry.DISTILLER, Aspect.CRAFT, 3);

		this.addAspect(MachineRegistry.CRYSTALLIZER, Aspect.CRYSTAL, 4);
		this.addAspect(MachineRegistry.CRYSTALLIZER, Aspect.ICE, 8);

		this.addAspect(MachineRegistry.PARTICLE, Aspect.LIGHT, 2);
		this.addAspect(MachineRegistry.PARTICLE, Aspect.SENSES, 4);

		this.addAspect(MachineRegistry.LAWNSPRINKLER, Aspect.WATER, 6);
		this.addAspect(MachineRegistry.LAWNSPRINKLER, Aspect.PLANT, 6);

		this.addAspect(MachineRegistry.GRINDSTONE, Aspect.TOOL, 6);
		this.addAspect(MachineRegistry.GRINDSTONE, Aspect.ORDER, 4);

		this.addAspect(MachineRegistry.BLOWER, Aspect.TRAVEL, 8);

		this.addAspect(MachineRegistry.REFRIGERATOR, Aspect.ICE, 8);
		this.addAspect(MachineRegistry.REFRIGERATOR, Aspect.AIR, 4);

		this.addAspect(MachineRegistry.GASTANK, Aspect.VOID, 12);
		this.addAspect(MachineRegistry.GASTANK, Aspect.AIR, 6);

		this.addAspect(MachineRegistry.CRAFTER, Aspect.CRAFT, 8);

		this.addAspect(MachineRegistry.COMPOSTER, Aspect.PLANT, 4);
		this.addAspect(MachineRegistry.COMPOSTER, Aspect.EXCHANGE, 4);
		this.addAspect(MachineRegistry.COMPOSTER, Aspect.LIFE, 2);

		this.addAspect(MachineRegistry.RAILGUN, Aspect.FLIGHT, 5);
		this.addAspect(MachineRegistry.RAILGUN, Aspect.WEAPON, 8);

		this.addAspect(MachineRegistry.CENTRIFUGE, Aspect.CRAFT, 4);
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
		AspectList al = ThaumcraftApiHelper.generateTags(is.getItem(), is.getItemDamage());
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
		if (m.isModConversionEngine() || m.isEnergyToPower()) {
			al.merge(Aspect.EXCHANGE, 4);
			al.merge(Aspect.ENERGY, 6);
		}
		if (m.isEnchantable()) {
			//al.merge(Aspect.MAGIC, 2);
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
					ThaumcraftApi.registerObjectTag(is.getItem(), is.getItemDamage(), this.getAspects(m, k));
					ThaumcraftApi.registerObjectTag(m.getBlock(), k, this.getAspects(m, k));
				}
			}
			else {
				ItemStack is = m.getCraftedProduct();
				ThaumcraftApi.registerObjectTag(is.getItem(), is.getItemDamage(), this.getAspects(m, 0));
				ThaumcraftApi.registerObjectTag(m.getBlock(), m.getMachineMetadata(), this.getAspects(m, 0));
			}
		}
	}

}
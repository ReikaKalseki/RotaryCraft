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
import Reika.RotaryCraft.Registry.MachineRegistry;

public class MachineAspectMapper {

	public static final MachineAspectMapper instance = new MachineAspectMapper();

	private final HashMap<MachineKey, AspectList> data = new HashMap();

	private MachineAspectMapper() {

		this.addAspect(MachineRegistry.BEDROCKBREAKER, Aspect.VOID, 2);
		this.addAspect(MachineRegistry.BEDROCKBREAKER, Aspect.STONE, 2);
		this.addAspect(MachineRegistry.BEDROCKBREAKER, Aspect.MINE, 4);

		this.addAspect(MachineRegistry.FERMENTER, Aspect.EXCHANGE, 2);
		this.addAspect(MachineRegistry.FERMENTER, Aspect.PLANT, 1);


	}

	private void addAspect(MachineRegistry m, Aspect a, int amt) {
		AspectList al = data.get(m);
		if (al == null) {
			al = new AspectList();
			data.put(new MachineKey(m), new AspectList());
		}
		al.merge(a, amt);
	}

	private void addAspect(MachineRegistry m, int offset, Aspect a, int amt) {
		AspectList al = data.get(m);
		if (al == null) {
			data.put(new MachineKey(m, offset), new AspectList());
		}
		al.merge(a, amt);
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

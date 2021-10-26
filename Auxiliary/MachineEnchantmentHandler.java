package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;

public final class MachineEnchantmentHandler {

	private final HashMap<Enchantment, Integer> data = new HashMap();
	private final HashSet<Integer> filters = new HashSet();

	public MachineEnchantmentHandler addFilter(Enchantment e) {
		return this.addFilter(e.effectId);
	}

	public MachineEnchantmentHandler addFilter(int id) {
		filters.add(id);
		return this;
	}

	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantment(e) > 0;
	}

	public int getEnchantment(Enchantment e) {
		Integer get = data.get(e);
		return get != null ? get.intValue() : 0;
	}

	public boolean hasEnchantments() {
		return !data.isEmpty();
	}

	public boolean setEnchantment(Enchantment e, int level) {
		if (this.isEnchantValid(e)) {
			data.put(e, level);
			return true;
		}
		return false;
	}

	public void clear() {
		data.clear();
	}

	public NBTTagList writeToNBT() {
		NBTTagList li = new NBTTagList();
		for (Entry<Enchantment, Integer> e : data.entrySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("id", e.getKey().effectId);
			tag.setInteger("lvl", e.getValue());
			li.appendTag(tag);
		}
		return li;
	}

	public void readFromNBT(NBTTagList NBT) {
		data.clear();
		for (Object o : NBT.tagList) {
			NBTTagCompound tag = (NBTTagCompound)o;
			Enchantment e = Enchantment.enchantmentsList[tag.getInteger("id")];
			int lvl = tag.getInteger("lvl");
			data.put(e, lvl);
		}
	}

	public boolean isEnchantValid(Enchantment e) {
		return filters.isEmpty() || filters.contains(e.effectId);
	}

	public boolean applyEnchants(ItemStack is) {
		boolean flag = false;
		for (Entry<Enchantment, Integer> e : ReikaEnchantmentHelper.getEnchantments(is).entrySet()) {
			Enchantment ec = e.getKey();
			int has = this.getEnchantment(ec);
			if (has < e.getValue())
				flag |= this.setEnchantment(ec, e.getValue());
		}
		return flag;
	}

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList();
		for (int id : filters) {
			li.add(Enchantment.enchantmentsList[id]);
		}
		return li;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return Collections.unmodifiableMap(data);
	}

}

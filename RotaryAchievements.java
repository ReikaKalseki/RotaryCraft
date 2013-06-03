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

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import Reika.RotaryCraft.Auxiliary.AchievementDescriptions;
import Reika.RotaryCraft.Auxiliary.EnumEngineType;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum RotaryAchievements {

	MAKESTEEL(24000, "Steelmaker", -2, -1, ItemStacks.steelingot, AchievementList.acquireIron, false),
	MAKEYEAST(24001, "Fermenter", -2, -2, RotaryCraft.yeast, null, false),
	EXTRACTOR(24002, "Processor", -2, -3, ItemStacks.lapisoredust, null, false),
	BORER(24003, "Boring", -2, -4, MachineRegistry.BORER.getCraftedProduct(), null, false),
	RECYCLE(24004, "Green", -2, -5, ItemStacks.scrap, null, false),
	MAKEJET(24005, "Jet Engine", -2, -6, new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.JET.ordinal()), null, true),
	MAKERAILGUN(24006, "Overkill", -2, -7, MachineRegistry.RAILGUN.getCraftedProduct(), null, true),
	SUCKEDINTOJET(24007, "Pulverized", -2, -8, Item.feather, MAKEJET.ordinal(), false);

	private int id;
	private String label;
	private int x;
	private int y;
	private ItemStack icon;
	private Achievement parent;
	private boolean special;
	private String desc;
	private int selfReference = -1;

	public static final RotaryAchievements[] list = RotaryAchievements.values();

	private RotaryAchievements(int num, String l, int xc, int yc, ItemStack tag, Achievement p, boolean s) {
		id = num;
		l = label;
		x = xc;
		y = yc;
		icon = tag;
		parent = p;
		special = s;
		desc = AchievementDescriptions.labels[num-24000];
	}

	private RotaryAchievements(int num, String l, int xc, int yc, Item tag, Achievement p, boolean s) {
		id = num;
		l = label;
		x = xc;
		y = yc;
		icon = new ItemStack(tag);
		parent = p;
		special = s;
		desc = AchievementDescriptions.labels[num-24000];
	}

	private RotaryAchievements(int num, String l, int xc, int yc, Block tag, Achievement p, boolean s) {
		id = num;
		l = label;
		x = xc;
		y = yc;
		icon = new ItemStack(tag);
		parent = p;
		special = s;
		desc = AchievementDescriptions.labels[num-24000];
	}

	private RotaryAchievements(int num, String l, int xc, int yc, Item tag, int p, boolean s) {
		id = num;
		l = label;
		x = xc;
		y = yc;
		icon = new ItemStack(tag);
		selfReference = p;
		special = s;
		desc = AchievementDescriptions.labels[num-24000];
	}

	public static void registerAcheivements() {
		RotaryCraft.achievements = new Achievement[RotaryAchievements.list.length];
		for (int i = 0; i < list.length; i++) {
			Achievement parent;
			if (list[i].isSelfReferenced())
				parent = list[i].getSelfReference();
			else
				parent = list[i].parent;
			RotaryCraft.achievements[i] = new Achievement(list[i].id, list[i].getName(), list[i].x, list[i].y, list[i].icon, list[i].parent).registerAchievement();
			if (list[i].isSpecial())
				RotaryCraft.achievements[i].setSpecial();
			//ModLoader.addAchievementDesc(RotaryCraft.achievements[i], list[i].getName(), list[i].getDesc());

		}
	}

	public boolean isSelfReferenced() {
		return selfReference != -1;
	}

	public Achievement getSelfReference() {
		return RotaryCraft.achievements[this.referencedOrdinal()];
	}

	public int referencedOrdinal() {
		return selfReference;
	}

	public String getDesc() {
		return desc;
	}

	public String getName() {
		return label;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public boolean isSpecial() {
		return special;
	}

}

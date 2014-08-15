/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.RotaryCraft.RotaryCraft;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

public enum ExtraConfigIDs implements IDRegistry {

	//ACHIEVEMENT("Extra IDs", "Achievement Base ID", 24000, null),

	FREEZEID("Other IDs", "Freeze Potion ID", 35, Potion.class),
	GROWTHID("Other IDs", "Growth Hormone ID", 36, Potion.class);
	//DEAFID("Other IDs", "Deafness ID", 37, Potion.class);

	private String name;
	private String category;
	private int defaultID;
	private Class type;

	public static final ExtraConfigIDs[] idList = ExtraConfigIDs.values();

	private ExtraConfigIDs(String cat, String n, int d, Class c) {
		name = n;
		category = cat;
		defaultID = d;
		type = c;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public int getDefaultID() {
		return defaultID;
	}

	public boolean isBlock() {
		return type == Blocks.class;
	}

	public boolean isItem() {
		return type == Item.class;
	}

	public int getValue() {
		return RotaryCraft.config.getOtherID(this.ordinal());
	}

	public int getShiftedValue() {
		return RotaryCraft.config.getOtherID(this.ordinal())+256;
	}

	@Override
	public String getConfigName() {
		return this.getName();
	}

	public boolean isDummiedOut() {
		return type == null;
	}
}
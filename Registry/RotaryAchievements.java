/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.AchievementDescriptions;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RCAchievementPage;

public enum RotaryAchievements {

	MAKESTEEL("Steelmaker", 				4, -2, ItemStacks.steelingot, AchievementList.acquireIron, false),
	MAKEYEAST("Fermenter", 					4, -1, MachineRegistry.FERMENTER.getCraftedProduct(), null, false),
	EXTRACTOR("Processor", 					4, 0, MachineRegistry.EXTRACTOR.getCraftedProduct(), null, false),
	BORER("Getting Bored", 					4, 1, MachineRegistry.BORER.getCraftedProduct(), null, false),
	RECYCLE("Green", 						3, -2, MachineRegistry.PULSEJET.getCraftedProduct(), null, false),
	MAKEJET("Jet Engine", 					3, -1, new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.JET.ordinal()), null, true),
	MAKERAILGUN("Overkill", 				3, 0, MachineRegistry.RAILGUN.getCraftedProduct(), null, true),
	SUCKEDINTOJET("This Really Sucks", 		3, 1, Item.rottenFlesh, MAKEJET.ordinal(), false),
	BEDROCKBREAKER("Unbreakable?", 			2, -2, MachineRegistry.BEDROCKBREAKER.getCraftedProduct(), MAKESTEEL.ordinal(), false), //break bedrock with
	STEAMENGINE("Steam", 					2, -1, new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.STEAM.ordinal()), MAKESTEEL.ordinal(), false), //turn on
	STEELSHAFT("Engaged", 					2, 0, new ItemStack(RotaryCraft.shaftitems.itemID, 1, MaterialRegistry.STEEL.ordinal()), MAKESTEEL.ordinal(), false), //make
	CVT("Adaptability", 					2, 1, new ItemStack(RotaryCraft.advgearitems.itemID, 1, 1), STEELSHAFT.ordinal(), false), //make
	BEDROCKSHAFT("Unbreakable",  			1, -2, new ItemStack(RotaryCraft.shaftitems.itemID, 1, MaterialRegistry.BEDROCK.ordinal()), STEELSHAFT.ordinal(), false), //make
	BEDROCKTOOLS("Durability",  			1, -1, ItemRegistry.BEDPICK.getStackOf(), BEDROCKBREAKER.ordinal(), false), //make
	JETFUEL("Liquid Power",  				1, 0, MachineRegistry.FRACTIONATOR.getCraftedProduct(), null, false), //make
	JETCHICKEN("Doing It Wrong",  			1, 1, Item.feather, MAKEJET.ordinal(), false), //suck 50 chickens into jet engine
	JETFAIL("...Oops",  					0, -2, Block.tnt, MAKEJET.ordinal(), false), //cause violent failure
	LIGHTFALL("Oh nooooo",  				0, -1, MachineRegistry.LIGHTBRIDGE.getCraftedProduct(), null, false), //light bridge turns off, drops you to death
	SPRINKLER("Green Thumb",  				0, 0, MachineRegistry.SPRINKLER.getCraftedProduct(), null, false), //turn on
	FLOODLIGHT("Illuminating",  			0, 1, MachineRegistry.FLOODLIGHT.getCraftedProduct(), null, false), //turn on at Light 15
	DAMAGEGEARS("Grind My Gears", 			-1, -2, ItemStacks.scrap, null, false),
	DIAMONDGEARS("Crystal",  				-1, -1, new ItemStack(RotaryCraft.gbxitems.itemID, 1, RotaryNames.gearboxItemNames.length-2), DAMAGEGEARS.ordinal(), false), //make
	MRADS32("Overspeed",  					-1, 0, Item.sugar, null, true), //transmit power at 32Mrad/s
	GIGAWATT("Overpowered",  				-1, 1, Block.blockRedstone, null, true), //transmit 1GW of power in one shaft w/o breaking
	TNTCANNON("Sharpshooter", 				-2, -2, MachineRegistry.TNTCANNON.getCraftedProduct(), null, true), //hit mob at >= 100 blocks with TNT cannon
	RAILDRAGON("Blown Out of the Sky", 		-2, -1, Block.dragonEgg, MAKERAILGUN.ordinal(), true), //kill dragon with railgun
	RAILKILLED("Too Close For Comfort", 	-2, 0, new ItemStack(Item.skull.itemID, 1, 0), MAKERAILGUN.ordinal(), false), //kill self with railgun
	GRAVELGUN("Sniped",  					-2, 1, ItemRegistry.GRAVELGUN.getStackOf(), null, false), //one hit kill with
	LANDMINE("Watch Your Step", 			-3, -2, MachineRegistry.LANDMINE.getCraftedProduct(), null, false), //step on
	GPRENDPORTAL("Who Needs Ender Eyes?", 	-3, -1, Block.endPortalFrame, null, true), //gpr thru end portal
	NETHERHEATRAY("Boom Miner", 			-3, 0, MachineRegistry.HEATRAY.getCraftedProduct(), null, true), //dig 500m with heat ray in nether
	GPRSPAWNER("Spawner",					-3, 1, Block.mobSpawner, null, true);

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

	private RotaryAchievements(String l, int xc, int yc, ItemStack tag, Achievement p, boolean s) {
		id = ExtraConfigIDs.ACHIEVEMENT.getValue()+this.ordinal();
		label = l;
		x = xc;
		y = yc;
		icon = tag;
		parent = p;
		special = s;
		desc = AchievementDescriptions.getDesc(this.ordinal());
	}

	private RotaryAchievements(String l, int xc, int yc, Item tag, Achievement p, boolean s) {
		this(l, xc, yc, new ItemStack(tag), p, s);
	}

	private RotaryAchievements(String l, int xc, int yc, Block tag, Achievement p, boolean s) {
		this(l, xc, yc, new ItemStack(tag), p, s);
	}

	private RotaryAchievements(String l, int xc, int yc, Item tag, int p, boolean s) {
		this(l, xc, yc, new ItemStack(tag), p, s);
	}

	private RotaryAchievements(String l, int xc, int yc, Block tag, int p, boolean s) {
		this(l, xc, yc, new ItemStack(tag), p, s);
	}

	private RotaryAchievements(String l, int xc, int yc, ItemStack tag, int p, boolean s) {
		id = ExtraConfigIDs.ACHIEVEMENT.getValue()+this.ordinal();
		label = l;
		x = xc;
		y = yc;
		icon = tag;
		selfReference = p;
		special = s;
		desc = AchievementDescriptions.getDesc(this.ordinal());
	}

	public Achievement get() {
		return RotaryCraft.achievements[this.ordinal()];
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
			if (ConfigRegistry.LOGLOADING.getState()) {
				ReikaJavaLibrary.pConsole("ROTARYCRAFT: Registering achievement "+list[i].name()+" with ingame name \""+list[i].getName()+"\".");
			}
		}
		AchievementPage.registerAchievementPage(new RCAchievementPage("RotaryCraft", RotaryCraft.achievements));
	}

	public String getKey() {
		return "achievement."+this.name()+".desc";
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

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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.AchievementPage;
import Reika.DragonAPI.Exception.IDConflictException;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.AchievementAuxiliary;
import Reika.RotaryCraft.Auxiliary.RCAchievementPage;

public enum RotaryAchievements {

	MAKESTEEL("Steelmaker", 				4, -2, AchievementList.acquireIron, false),
	MAKEYEAST("Fermenter", 					4, -1, null, false),
	EXTRACTOR("Processor", 					4, 0, null, false),
	BORER("Getting Bored", 					4, 1, null, false),
	RECYCLE("Green", 						3, -2, null, false),
	MAKEJET("Jet Engine", 					3, -1, null, true),
	MAKERAILGUN("Overkill", 				3, 0, null, true),
	SUCKEDINTOJET("This Really Sucks", 		3, 1, MAKEJET.ordinal(), false),
	BEDROCKBREAKER("Unbreakable?", 			2, -2, MAKESTEEL.ordinal(), false), //break bedrock with
	STEAMENGINE("Steam", 					2, -1, MAKESTEEL.ordinal(), false), //turn on
	STEELSHAFT("Engaged", 					2, 0, MAKESTEEL.ordinal(), false), //make
	CVT("Adaptability", 					2, 1, STEELSHAFT.ordinal(), false), //make
	BEDROCKSHAFT("Unbreakable",  			1, -2, STEELSHAFT.ordinal(), false), //make
	BEDROCKTOOLS("Durability",  			1, -1, BEDROCKBREAKER.ordinal(), false), //make
	JETFUEL("Liquid Power",  				1, 0, null, false), //make
	JETCHICKEN("Doing It Wrong",  			1, 1, MAKEJET.ordinal(), false), //suck 50 chickens into jet engine
	JETFAIL("...Oops",  					0, -2, MAKEJET.ordinal(), false), //cause violent failure
	LIGHTFALL("Oh nooooo",  				0, -1, null, false), //light bridge turns off, drops you to death
	SPRINKLER("Green Thumb",  				0, 0, null, false), //turn on
	FLOODLIGHT("Illuminating",  			0, 1, null, false), //turn on at Light 15
	DAMAGEGEARS("Grind My Gears", 			-1, -2, null, false),
	DIAMONDGEARS("Crystal",  				-1, -1, DAMAGEGEARS.ordinal(), false), //make
	MRADS32("Overspeed",  					-1, 0, null, true), //transmit power at 32Mrad/s
	GIGAWATT("Overpowered",  				-1, 1, null, true), //transmit 1GW of power in one shaft w/o breaking
	TNTCANNON("Sharpshooter", 				-2, -2, null, true), //hit mob at >= 100 blocks with TNT cannon
	RAILDRAGON("Blown Out of the Sky", 		-2, -1, MAKERAILGUN.ordinal(), true), //kill dragon with railgun
	RAILKILLED("Too Close For Comfort", 	-2, 0, MAKERAILGUN.ordinal(), false), //kill self with railgun
	GRAVELGUN("Sniped",  					-2, 1, null, false), //one hit kill with
	LANDMINE("Watch Your Step", 			-3, -2, null, false), //step on
	GPRENDPORTAL("Who Needs Ender Eyes?", 	-3, -1, null, true), //gpr thru end portal
	NETHERHEATRAY("Boom Miner", 			-3, 0, null, true), //dig 500m with heat ray in nether
	GPRSPAWNER("Spawner",					-3, 1, null, true),
	CUTKNOT("Cutting the Knot",				-3, 2, null, true);

	//private int id;
	private String label;
	private int x;
	private int y;
	private ItemStack icon;
	private Achievement parent;
	private boolean special;
	//private String desc;
	private int selfReference = -1;

	public static final RotaryAchievements[] list = RotaryAchievements.values();

	private RotaryAchievements(String l, int xc, int yc, Achievement p, boolean s) {
		//id = RotaryConfig.achievementIDs[this.ordinal()];
		label = l;
		x = xc;
		y = yc;
		parent = p;
		special = s;
		//desc = AchievementAuxiliary.getDesc(this.ordinal());
	}

	private RotaryAchievements(String l, int xc, int yc, int p, boolean s) {
		//id = RotaryConfig.achievementIDs[this.ordinal()];
		label = l;
		x = xc;
		y = yc;
		selfReference = p;
		special = s;
		//desc = AchievementAuxiliary.getDesc(this.ordinal());
	}

	public Achievement get() {
		return RotaryCraft.achievements[this.ordinal()];
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public void triggerAchievement(EntityPlayer ep) {
		if (!ConfigRegistry.ACHIEVEMENTS.getState())
			return;
		if (ep == null) {
			ReikaChatHelper.write("Player does not exist to receive their achievement \""+this+"\"!");
			ReikaJavaLibrary.pConsole("Player does not exist to receive their achievement \""+this+"\"!");
		}
		else {
			ep.triggerAchievement(this.get());
		}
	}

	public static void registerAcheivements() {
		RotaryCraft.achievements = new Achievement[RotaryAchievements.list.length];
		for (int i = 0; i < list.length; i++) {
			Achievement parent;
			if (list[i].isSelfReferenced())
				parent = list[i].getSelfReference();
			else
				parent = list[i].parent;
			list[i].icon = AchievementAuxiliary.icons[i];
			RotaryCraft.achievements[i] = new Achievement(RotaryCraft.config.achievementIDs[i], list[i].getName(), list[i].x, list[i].y, list[i].icon, list[i].parent);
			int id = RotaryCraft.achievements[i].statId;
			if (StatList.getOneShotStat(id) != null)
				throw new IDConflictException(RotaryCraft.instance, "The mod's achievement IDs are conflicting with another at ID "+id+" (trying to overwrite "+StatList.getOneShotStat(id).statName+").\nCheck the config file and change them.");
			RotaryCraft.achievements[i].registerAchievement();
			if (list[i].isSpecial())
				RotaryCraft.achievements[i].setSpecial();
			RotaryCraft.logger.log("Registering achievement "+list[i].name()+" with ID "+RotaryCraft.achievements[i].statId+" and ingame name \""+list[i].getName()+"\" (slot "+i+").");
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
		return AchievementAuxiliary.getDesc(this.ordinal());
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

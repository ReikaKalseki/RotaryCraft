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

import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RCAchievementPage;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public enum RotaryAchievements {

	MAKESTEEL(		0, 0,	ItemStacks.steelingot, 											null,			false),
	MAKEYEAST(		2, -2,	ItemRegistry.YEAST,				 								MAKESTEEL,		false),
	EXTRACTOR(		2, 0,	ItemStacks.goldoreflakes, 										MAKESTEEL,		false),
	PCB(			0, 4,	ItemStacks.pcb,													MAKESTEEL,		false),
	PUMP(			-6, 0,	MachineRegistry.PUMP,											MAKESTEEL,		false),
	GPR(			-2, 4,	MachineRegistry.GPR,											PCB,			false),
	BORER(			2, 4,	MachineRegistry.BORER, 											PCB,			false),
	JETFUEL(		4, -4,	ItemRegistry.BUCKET.getStackOfMetadata(1), 						MAKEYEAST,		false), //make
	RECYCLE(		4, -8,	ItemStacks.scrap, 												JETFUEL,		false),
	JETENGINE(		6, -4,	EngineType.JET.getItem(), 										JETFUEL,		true),
	MAKERAILGUN(	0, 6,	MachineRegistry.RAILGUN, 										PCB,			true),
	SUCKEDINTOJET(	6, -8,	Items.rotten_flesh, 											JETENGINE,		false),
	BEDROCKBREAKER(	-4, 2,	ItemStacks.bedrockdust, 										MAKESTEEL,		false), //break bedrock with
	STEAMENGINE(	-8, 0,	EngineType.STEAM.getItem(), 									PUMP,			false), //turn on
	STEELSHAFT(		-2, -2,	MaterialRegistry.STEEL.getShaftItem(), 							MAKESTEEL,		false), //make
	CVT(			-2, -4,	MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(1), 	STEELSHAFT,		false), //make
	BEDROCKSHAFT(	-4, 4,	MaterialRegistry.BEDROCK.getShaftItem(), 						BEDROCKBREAKER,	false), //make
	BEDROCKTOOLS(	-6, 2,	ItemRegistry.BEDPICK,			 								BEDROCKBREAKER, false), //make
	JETCHICKEN(		8, -4,	Items.feather, 													JETENGINE,		false), //suck 50 chickens into jet engine
	JETFAIL(		8, -2,	Blocks.fire, 													JETENGINE,		false), //cause violent failure
	LIGHTFALL(		8, -6,	MachineRegistry.LIGHTBRIDGE, 									JETENGINE,		false), //light bridge turns off, drops you to death
	SPRINKLER(		-6, -2,	MachineRegistry.SPRINKLER, 										PUMP,			false), //turn on
	FLOODLIGHT(		-1, -1,	MachineRegistry.FLOODLIGHT, 									MAKESTEEL,		false), //turn on at Light 15
	DAMAGEGEARS(	-4, -2,	ItemStacks.gearunit, 											STEELSHAFT,		false),
	DIAMONDGEARS(	-4, -4,	MaterialRegistry.DIAMOND.getGearItem(8), 						DAMAGEGEARS,	false), //make
	MRADS32(		2, -6,	ItemRegistry.METER,												JETFUEL,		true), //transmit power at 32Mrad/s
	GIGAWATT(		6, 0,	Blocks.redstone_block, 											JETENGINE,		true), //transmit 1GW of power in one shaft w/o breaking
	RAILDRAGON(		2, 6,	Blocks.dragon_egg, 												MAKERAILGUN,	true), //kill dragon with railgun
	RAILKILLED(		0, 8,	new ItemStack(Items.skull, 1, 0), 								MAKERAILGUN,	false), //kill self with railgun
	GRAVELGUN(		0, -4,	ItemRegistry.GRAVELGUN,											MAKESTEEL,		false), //one hit kill with
	LANDMINE(		2, 2,	MachineRegistry.LANDMINE, 										MAKESTEEL,		false), //step on
	NETHERHEATRAY(	4, -2,	MachineRegistry.HEATRAY, 										JETFUEL,		true), //dig 500m with heat ray in nether
	GPRSPAWNER(		-2, 6,	ItemRegistry.SPAWNER, 											GPR,			true),
	GPRENDPORTAL(	-2, 8,	Blocks.end_portal_frame, 										GPRSPAWNER,		true), //gpr thru end portal
	CUTKNOT(		4, 4,	ItemStacks.drill, 												BORER,			true),
	RAREEXTRACT(	4, 0,	ExtractorModOres.getFlakeProduct(ModOreList.PLATINUM),			EXTRACTOR,		true),
	MASSIVEHIT(		0, -8,	Items.flint,													GRAVELGUN,		true),
	OVERPRESSURE(	-8, 2,	MachineRegistry.COOLINGFIN,										STEAMENGINE,	false),
	DOUBLEKILL(		-2, -6, Items.arrow,													GRAVELGUN,		true);

	public static final RotaryAchievements[] list = values();

	public final RotaryAchievements dependency;
	public final int xPosition;
	public final int yPosition;
	public final boolean isSpecial;
	private final ItemStack iconItem;

	private RotaryAchievements(int x, int y, Item icon, RotaryAchievements preReq, boolean special) {
		this(x, y, new ItemStack(icon), preReq, special);
	}

	private RotaryAchievements(int x, int y, Block icon, RotaryAchievements preReq, boolean special) {
		this(x, y, new ItemStack(icon), preReq, special);
	}

	private RotaryAchievements(int x, int y, ItemRegistry icon, RotaryAchievements preReq, boolean special) {
		this(x, y, icon.getStackOf(), preReq, special);
	}

	private RotaryAchievements(int x, int y, MachineRegistry icon, RotaryAchievements preReq, boolean special) {
		this(x, y, icon.getCraftedProduct(), preReq, special);
	}

	private RotaryAchievements(int x, int y, ItemStack icon, RotaryAchievements preReq, boolean special) {
		xPosition = x;
		yPosition = y;
		dependency = preReq;
		iconItem = icon;
		isSpecial = special;
	}

	public Achievement get() {
		return RotaryCraft.achievements[this.ordinal()];
	}

	public void triggerAchievement(EntityPlayer ep) {
		if (!ConfigRegistry.ACHIEVEMENTS.getState())
			return;
		if (ep == null) {
			//ReikaChatHelper.write("Player does not exist to receive their achievement \""+this+"\"!");
			//ReikaJavaLibrary.pConsole("Player does not exist to receive their achievement \""+this+"\"!");
			RotaryCraft.logger.debug("Player does not exist to receive their achievement \""+this+"\"!");
		}
		else {
			ep.triggerAchievement(this.get());
		}
	}

	public boolean hasDependency() {
		return dependency != null;
	}

	public static void registerAchievements() {
		//ReikaJavaLibrary.pConsole(Arrays.toString(RotaryCraft.config.achievementIDs));
		for (int i = 0; i < list.length; i++) {
			RotaryAchievements a = list[i];
			int id = RotaryCraft.config.achievementIDs[i];
			Achievement dep = a.hasDependency() ? a.dependency.get() : null;
			Achievement ach = new Achievement(a.name().toLowerCase(), a.name().toLowerCase(), a.xPosition, a.yPosition, a.iconItem, dep);
			//ReikaJavaLibrary.pConsole(a+":"+id+":"+StatList.getOneShotStat(id));
			//if (StatList.getOneShotStat(id) != null)
			//	throw new IDConflictException(RotaryCraft.instance, "The mod's achievement IDs are conflicting with another at ID "+id+" ("+a+" is trying to overwrite "+StatList.getOneShotStat(id).statName+").\nCheck the config file and change them.");
			if (a.isSpecial)
				ach.setSpecial();
			RotaryCraft.achievements[i] = ach;
			ach.registerStat();
			RotaryCraft.logger.log("Registering achievement "+a+" with ID "+id+" and ingame name \""+a+"\" (slot "+i+").");
		}
		AchievementPage.registerAchievementPage(new RCAchievementPage("RotaryCraft", RotaryCraft.achievements));
	}

}
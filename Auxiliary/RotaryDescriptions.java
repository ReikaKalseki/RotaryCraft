/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.RotaryCraft.PowerReceivers;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityFan;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPileDriver;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;

public final class RotaryDescriptions {

	public static final String[] machineInfo = {

		"Page "+GuiHandbook.INFOSTART+" - Terms and Physics Explanations\n"+
				"Page "+GuiHandbook.ENGINESTART+" - Engines\n"+
				"Page "+GuiHandbook.TRANSSTART+" - Transmission\n"+
				"Page "+GuiHandbook.MACHINESTART+" - Machines\n"+
				"Page "+GuiHandbook.TOOLSTART+" - Tools\n"+
				"Page "+GuiHandbook.CRAFTSTART+" - Crafting Items\n"+
				"Page "+GuiHandbook.RESOURCESTART+" - Resource Items\n"+
				"Page "+GuiHandbook.MISCSTART+" - Misc",
				"",
				"",
				"",
				"",
				"",
				"",
				"",

				"W/kW/MW - Watts/Kilowatts/Megawatts - a unit of power (energy per unit time). 1W = 1 Joule per second.\n\n"+
						"Nm - Newton-meters - a unit of torque (force times distance).\n\n"+
						"rad/s - Radians per second. A unit of angular (rotational) velocity. 1 rad/s = 9.55 rpm.\n\n"+
						"Radian - A unit of angle, equal to 180/pi (57.3) degrees; there are 6.28 radians in a full circle."+
						""+
						""+
						""+
						"",


						"Torque is the 'strength' of a turn; greater torques can exert greater loads. Therefore, a machine that requires more force " +
								"to operate, such as the Compactor, will naturally require more torque than one like the Woodcutter.\n\n" +
								"Angular velocity is the rotational speed. For example, a machine that mixes things quickly, such as the " +
								"Fractionator, will require a greater angular velocity than one which mixes them more slowly, like the Fermenter.",

								"",
								"",
								"",
								"",
								"",
								"",


								"Some important information on operating some machines and some essential tools.",

								"All inline gearboxes need to be lubricated to maintain their ability to transmit power. Operated without lubricant, they will " +
										"gradually wear down and transmit less and less torque, and thus less power. To keep them lubricated, simply run a line of " +
										"lubricant hose from a grinder to the gearbox, put some seeds in the grinder, and watch the hose transmit the oil (or just use " +
										"buckets to carry it). Gearboxes do " +
										"consume the lubricant, and more of it at higher speeds, so keep an eye on the meter in the GUI.",

										"These actually carry the canola oil lubricant from the grinder to the gearboxes. The transmission is done automatically, and " +
												"they will connect to any grinder or gearbox automatically as well.",

												"Find some canola seeds in tall grass, plant them on farmland, and watch them grow. When covered in little yellow flowers, the " +
														"plants are ready to be harvested and will drop 1-4 seeds. They share the same requirements as wheat in terms of light level, " +
														"water supply, and so on. Canola plants require lots of water, so do not be surprised if they die on dry farmland. Canola seeds " +
														"can also be compacted into a dense clump for easier storage.",

														"The angular transducer is an indispensable tool for those with large or complex shaft systems. Right click any engine, gearbox, " +
																"or machine with it, and you will be informed of the power and speed going in or out.",

																"This is for reorienting machines which were either misplaced or were difficult to place correctly. Shift-clicking will often " +
																		"perform a different function, like toggling between split and merge modes on the "+MachineRegistry.SPLITTER.getName()+
																		". The screwdriver will also reorient pistons and stairs.",

																		"Pipes carry water or lava from pumps to machines or reservoirs. A pipe can hold an unlimited amount internally, but can only " +
																				"store one liquid at a time, and must be emptied before changing contents.",

																				"Fuel lines carry jet fuel to the engines and furnaces that require it. Once connected to a source, they will automatically start " +
																						"to fill themselves from it, and if connected to a target, will automatically empty themselves into it. They have an infinite " +
																						"capacity, so caution is recommended when breaking them, to avoid destroying large quantities of stored fuel.",


																						"Perhaps the most crucial of all the machines, engines are used to supply power. " +
																								"Each has their own torque and speed characteristic, but they all generally are high " +
																								"speed and low torque. Each engine also has its own requirements in terms of energy supply, " +
																								"and some engines have their own special quirks that it is wise to know.",

																								"The direct current (DC) engine is the simplest to make and operate. All it requires to " +
																										"function is a continuous redstone signal of any strength. This ease comes at a cost, though - " +
																										"these engines are the weakest of all, outputting "+
																										EnumEngineType.DC.getTorque()+" Nm of torque at "+EnumEngineType.DC.getSpeed()+" rad/s for "+
																										EnumEngineType.DC.getPowerKW()+"kW of power.",

																										"The wind turbine uses wind to generate rotational power. Due to the low density of air, it does not generate much torque "+
																												"(only "+EnumEngineType.WIND.getTorque()+" Nm), but its design allows it to rotate at the rather rapid speed of "+
																												EnumEngineType.WIND.getSpeed()+" rad/s, producing a total of "+EnumEngineType.WIND.getPowerKW()+"kW at max efficiency. Note " +
																												"that the efficiency of this engine is only maximized when it is high up and out in the open. Objects and blocks can " +
																												"obstruct the blades of this engine.",

																												"The steam engine operates continuously if given a source of heat (fire or lava) below it and water " +
																														"on any other side. Slightly stronger than the DC engine, they provide "+EnumEngineType.STEAM.getTorque()+
																														" Nm at "+EnumEngineType.STEAM.getSpeed()+" rad/s ("+EnumEngineType.STEAM.getPowerKW()+" kW).",

																														"The gasoline engine, true to its name, runs on...ethanol. Simply load in ethanol crystals and watch " +
																																"them burn to produce "+EnumEngineType.GAS.getTorque()+" Nm of torque at "+EnumEngineType.GAS.getSpeed()+
																																" rad/s, or "+EnumEngineType.GAS.getPowerKW()+"kW of power.",

																																"The alternating current (AC) engine, like its DC cousin, requires a redstone signal to perform. " +
																																		"However, this one needs an alternating signal, best supplied with a redstone clock. Faster cycle times " +
																																		"correlate to more consistent power output, with 2-clocks providing a constant supply of "+EnumEngineType.AC.getTorque()+
																																		"Nm at "+EnumEngineType.AC.getSpeed()+" rad/s ("+EnumEngineType.AC.getPowerKW()+"kW). This engine requires a magnetized shaft " +
																																		"core, which it will gradually de-magnetize.",

																																		"The performance engine, like the gasoline engine, runs on ethanol, but it also requires water " +
																																				"coolant and can only reach its maximum output when given the correct additives to its fuel supply - redstone, " +
																																				"gunpowder, or blaze powder, each being more effective than the last. " +
																																				"Fully supplied and maintained, these engines will produce "+EnumEngineType.SPORT.getTorque()+
																																				"Nm of torque at "+EnumEngineType.SPORT.getSpeed()+" rad/s, a total of "+EnumEngineType.SPORT.getPowerKW()+"kW. ",

																																				"The hydrokinetic engine converts the energy of falling water into shaft power. Note that this machine can only " +
																																						"rotate clockwise, and therefore the waterfall must be positioned accordingly. Additionally, adjacent blocks can " +
																																						"obstruct the paddles from rotating. Finally, this engine's power output is dependent on how far the water is falling. " +
																																						"A 3-block fall will produce minimal power, but a 64+ block fall can produce "+EnumEngineType.HYDRO.getTorque()+" Nm at " +
																																						EnumEngineType.HYDRO.getSpeed()+" rad/s, the highest torque of any engine, totalling "+EnumEngineType.HYDRO.getPowerKW()+"kW.",


																																						"The microturbine is rather unique. When given a constant supply of fuel, it supplies very little torque ("+
																																								EnumEngineType.MICRO.getTorque()+"Nm) "+"but does it at an enormous speed of "+EnumEngineType.MICRO.getSpeed()+
																																								" rad/s, for a total output of just over two megawatts ("+EnumEngineType.MICRO.getStringPowerMW()+"MW).",

																																								"The most powerful engine of all, the gas turbine is a monster, outputting power comparable to that of an engine " +
																																										"of a commerical jet airliner...and with sound to match. These engines will, when fuelled, provide "+EnumEngineType.JET.getTorque()+
																																										"Nm of torque at "+EnumEngineType.JET.getSpeed()+" rad/s ("+EnumEngineType.JET.getStringPowerMW()+"MW). Be warned, unless you want to find out what it feels like to go through a blender, stay AWAY " +
																																										"from the front of this one while in operation! Neither you nor your engine will fare well.",

																																										"",

																																										"",

																																										"",

																																										"",

																																										"",

																																										"",


																																										"The possibilities would be rather limited if power produced by the engines had to be consumed at the source. Fortunately, " +
																																												"it is simple and easy to transport this power, almost losslessly, across any distance you care to. One important thing to " +
																																												"note about many of these is that they are directional - they transmit power in only one direction. Machines will render a green " +
																																												"cage around their input block(s) and a red one around their output block(s).",

																																												"The most basic power-transmission device is the shaft. Nothing fancy, just carries the power from one place to another, " +
																																														"with no changes to either the torque or the speed. Pay attention, though, to the direction to avoid headaches.",

																																														"Gearboxes are used to change the ratio of torque to speed, while keeping the power constant. They come in 4 ratios - " +
																																																"2:1, 4:1, 8:1, and 16:1, and will affect the torque and speed accordingly. Reduction gears increase the torque and " +
																																																"decrease the speed; acceleration gears do the opposite. Be warned: these need to be lubricated to work at their full " +
																																																"potential; unlubricated gearboxes will gradually wear down, and reduce the transmitted torque accordingly.",

																																																"Bevel gears are used to change the direction of power transmission without changing the torque or speed. Unlike their " +
																																																		"inline cousins, they do not need lubricant, and will operate continuously and losslessly.",

																																																		"The shaft junction does exactly what it sounds like. In 'merge' mode, it will take two engines' outputs and combine them, " +
																																																				"outputting the sum of the torques at the input speed. Note that trying to combine inputs of different speeds will merely " +
																																																				"result in a shower of sparks. In 'split' mode, they will take an input and send some torque each way, at the input speed.",

																																																				"The clutch is basically a gated shaft - when powered by redstone, it transmits its input torque and speed unchanged. Unpower " +
																																																						"it, and it outputs nothing.",

																																																						"The dynamometer will display its input torque, speed, and power while outputting them to the other side.",

																																																						"Flywheels store rotational kinetic energy. They take some time to spin up to the input speed, but when they do, they keep " +
																																																								"spinning for some time, providing a more steady power output to a machine than a shaft hooked up to a varying input. " +
																																																								"Heavier materials make better, more efficient - and more powerful - flywheels. Spinning one of these too rapidly will result " +
																																																								"in a violent and destructive failure.",


																																																								"The worm gear is a fast way to increase shaft torque without using a large number of intermediate gears, creating an " +
																																																										"equivalent 16:1 ratio. However, worm gears are inefficient and will lose some of their power, leading to a much slower " +
																																																										"output speed. This loss rate goes up as the input speed does.",

																																																										"A CVT (continuously variable transmission) is essentially a dynamic gearbox. Rather than using toothed wheels, it uses a " +
																																																												"series of belts to change the gear ratio. This gives them the advantage of being efficient and highly flexible, allowing you " +
																																																												"to select the desired gear ratio on demand. Note that higher ratios require more belts, with each power of 2 doubling the " +
																																																												"maximum ratio (capped at 32:1).",

																																																												"The industrial coil stores energy in a large spring that can be unwound on command. Due to the fact that its output torque and " +
																																																														"speed can be chosen at will, it can be used to store energy for later or as a sort of capacitor, to store low-power energy " +
																																																														"accumulated over a long time and then release it in one powerful burst.",

																																																														"",

																																																														"",

																																																														"",

																																																														"",

																																																														"",


																																																														"Machines are what actually use all this power generated by the engines. They vary in purpose and power requirements, and " +
																																																																"many of them, especially ones that perform distinct operations, will run faster at higher speeds.",

																																																																"The bedrock breaker will, when sufficiently powered, gradually grind away at the bedrock blocks in front of it with a " +
																																																																		"4-block range. When each block is consumed, it will expel a piece of bedrock dust, which can be used to make the highest tier " +
																																																																		"of tools or shafts and gears - bedrock. This machine's operational speed goes up as the input speed does.",

																																																																		"The fermenter is how you actually produce the ethanol for the engines that require it. Simply load in nearly any plant matter " +
																																																																				"and power it, and it will break it down into a blue-green sludge. Dry that out in a furnace, and you will get crystals of " +
																																																																				"ethanol. This machine's operational speed goes up as the input speed does.",

																																																																				"The grinder does exactly what its name suggests - it grinds things. Stone to cobblestone, cobble to gravel, gravel to sand, " +
																																																																						"that sort of thing. It also plays a critical role in the lubrication of gearboxes - it grinds the canola seeds into an oil " +
																																																																						"usable as lubricant. This machine's operational speed goes up as the input speed does.",

																																																																						"The floodlight creates a beam of light that illuminates everything in its path until blocked by an opaque, solid block. " +
																																																																								"Perfect for lighting up large spaces where point-source light sources like torches will simply not cut it. The floodlight " +
																																																																								"requires "+String.format("%d", TileEntityFloodlight.FALLOFF)+"W of power for each light level produced, to a maximum of " +
																																																																								String.format("%d", TileEntityFloodlight.FALLOFF*16)+"W for light as bright as direct sunlight.",

																																																																								"One of the most powerful and expensive of the machines, the heat ray creates a beam of " +
																																																																										"fiery death and destruction for as long as its range allows. While it is hard to supply enough power to turn the thing on, " +
																																																																										"let alone unlock some of its more powerful capabilities, it, fully powered, is capable of burning down a biome kilometers " +
																																																																										"away if you let it. Extra power to this machine, in addition to increasing range, also " +
																																																																										"increases the range and strength of its ability to melt rock, light TNT...",

																																																																										"The boring machine is rather interesting. It will dig tunnels in the direction it is facing for as long as you " +
																																																																												"like. With the GUI, you can control the tunnel shape from a number of designs, and, if the " +
																																																																												"cobblestone and dirt spewing out of the machine bothers you, disable the item drops. Harder materials require more " +
																																																																												"power and torque to dig - "+String.format("%d", TileEntityBorer.DIGPOWER)+"W per hardness points of the blocks in front of it, " +
																																																																												"and up to "+String.format("%d",TileEntityBorer.OBSIDIANTORQUE)+" Nm of torque to cut through the harder materials. The borer will " +
																																																																												"ensure that the tunnel roof does not collapse by replacing sand and gravel.",

																																																																												"The pile driver repeatedly impacts the ground to break it apart, digging a deep, narrow shaft. This shaft will go through any " +
																																																																														"liquids in its path, and will go through everything but bedrock. Multiple impacts are required to break some harder materials, " +
																																																																														"and the vibration will dislodge things like paintings, uproot saplings, and disorient players. Beware: Being in the path of the " +
																																																																														"mining pile will end exactly as you may imagine. This machine's power requirements go up the deeper it digs - "+
																																																																														String.format("%d", TileEntityPileDriver.BASEPOWER)+"W per meter depth.",

																																																																														"The potion aerosolizer has nine internal tanks which can store up to 64 bottles of potion each. These effects are dispersed " +
																																																																																"through the entire room the machine is in, and applied to all entities within. Filling more than one slot with the same potion " +
																																																																																"type will result in upgraded effects.",

																																																																																"This machine will, when its enormous power requirement is " +
																																																																																		"satisfied, emit a beam of solid, tangible light which can be walked upon and will support any mass placed on it. Nothing " +
																																																																																		"can destroy this beam, but it will be blocked by any non-air block. Note that it requires bright light above it - " +
																																																																																		"or a view of the sky - in order to be able to make the beam.",

																																																																																		"The extractor is used to get more material out of ore. First, it grinds ore to dust, then mixes it with " +
																																																																																				"water to form a slurry, then presses that slurry through a filter to obtain a liquid solution, then burns the result to " +
																																																																																				"obtain ore flakes. These flakes can be smelted in a furnace to obtain a unit of the ore's material. Each stage in the extractor " +
																																																																																				"has a 50% chance of doubling the output. The machine's requirements are complex and vary from stage to stage, but a supply of " +
																																																																																				"water and "+String.format("%d", PowerReceivers.EXTRACTOR.getMinPower(0))+"W with "+String.format("%d", PowerReceivers.EXTRACTOR.getMinTorque(0))+
																																																																																				" Nm of torque at "+String.format("%d", PowerReceivers.EXTRACTOR.getMinSpeed(2))+" rad/s will run all four stages.",

																																																																																				"The pulse jet is a powerful furnace. While it will not smelt the ordinary recipes, it will melt obsidian into glass " +
																																																																																						"that is immune to TNT, melt scrap metal and tools back into ingots, " +
																																																																																						"and make steel more efficiently than a standard furnace. It will get VERY hot, and will " +
																																																																																						"ignite nearby flammable materials - keeping it cooled with water is a necessity. While there is " +
																																																																																						"no minimum power requirement, the jet requires high shaft speeds. The pulse jet consumes jet fuel to operate, and a rather " +
																																																																																						"large amount of it. This machine receives power from below.",

																																																																																						"The pump will, when provided with power, will suck up liquids " +
																																																																																								"from the surrounding area and pump them into pipes, for transport to a machine that can use it. Water is often used as coolant, " +
																																																																																								"while lava is more often pumped to a reservoir for convenient access. Excess power to the pump generates pressure, which is " +
																																																																																								"essential for the operation of sprinklers.",

																																																																																								"The reservoir is simply a tank for storing liquid, either water or lava, with a maximum capacity of "+
																																																																																										String.format("%d", TileEntityReservoir.CAPACITY)+" cubic meters (buckets). It can be right-clicked with a bucket to add or " +
																																																																																										"remove liquid, and if it is full of water, glass bottles can be filled from it.",

																																																																																										"The fan emits a stream of air that will push entities around. With greater input power, heavier entities can be moved, and " +
																																																																																												"all entities will accelerate faster. A minimum of "+String.format("%d", PowerReceivers.FAN.getMinPower())+"W of power is required, and " +
																																																																																												"power levels greater than "+String.format("%d", TileEntityFan.MAXPOWER)+"W of power will have no increased effect due to airflow " +
																																																																																												"limitations.",

																																																																																												"The compactor is capable of compressing coal until it becomes anthracite, then prismane, then lonsdaleite, then diamond. Each " +
																																																																																														"stage will require four of the input items per unit output. All steps require enormous pressures of "+
																																																																																														String.format("%d", TileEntityCompactor.REQPRESS)+"kPa and temperatures of greater than "+
																																																																																														String.format("%d", TileEntityCompactor.REQTEMP)+"C to take place. The compactor will " +
																																																																																														"gradually heat or cool depending on its surroundings, and pressure is built up by applying high torques.",

																																																																																														"The auto-breeder will lure in animals, and when they get close, feed them until they " +
																																																																																																"feel like breeding. With this machine, you can quickly amass large herds. Each animal " +
																																																																																																"has its own desired food item; without that item, the machine will fail to attract " +
																																																																																																"or feed the animal. Wolves, for example, particularly like raw pork. The food item " +
																																																																																																"will be consumed when the animal enters breeding mode.",

																																																																																																"The bait box will attract or repel mobs of all kinds, provided it contains the " +
																																																																																																		"appropriate items to do so. For example, Endermen are attracted by grass blocks and " +
																																																																																																		"the opportunity to take them, but are repelled by the faces on soul sand staring back " +
																																																																																																		"at them. These items are not consumed.",

																																																																																																		"The firework machine will, when given the appropriate ingredients with which to make " +
																																																																																																				"fireworks, craft random fireworks out of the possible recipes and launch them " +
																																																																																																				"automatically. When given pre-made firework stars, it will use those preferentially. " +
																																																																																																				"With excess power input, the machine is capable of conserving ingredients and thus " +
																																																																																																				"getting far more than one firework out of the same amount of ingredients.",

																																																																																																				"The fractionator distills various items and refines them into jet fuel for use in some " +
																																																																																																						"of the engines and machines. Jet fuel requires tar, netherrack dust, coal, blaze powder, " +
																																																																																																						"ethanol, and magma cream. Additionally, ghast tears are used as a solvent, but are " +
																																																																																																						"not consumed like the other items. Fuel lines connect only to the top of this machine.",

																																																																																																						"The Ground-Penetrating Radar is capable of reading the densities and other properties " +
																																																																																																								"of the blocks below it, and from that determining what blocks lie below. This allows " +
																																																																																																								"it to draw a cross-sectional map of caves, underground structures, and other materials. " +
																																																																																																								"Larger power inputs will give it a wider field of view, topping out at 82 blocks wide.",

																																																																																																								"Tired of blowing up your compactors and steam engines by overheating them? The heater " +
																																																																																																										"will heat itself to a temperature you specify, and when placed below a machine, will " +
																																																																																																										"warm it to a temperature near its own. The heater will consume any fuel that the standard " +
																																																																																																										"furnace can, with stronger fuels providing more heat, up to 800C from one lava bucket.",

																																																																																																										"The obsidian factory will mix piped-in water and lava to produce obsidian. Be careful; " +
																																																																																																												"if the machine is left full of only lava too long, it will start to overheat and will " +
																																																																																																												"eventually melt.",

																																																																																																												"The player detector does exactly what its name implies - when a player comes within its " +
																																																																																																														"configurable range, it will emit a redstone signal. The maximum range is determined by " +
																																																																																																														"the power input, and the reaction time of the detector - the delay between a player " +
																																																																																																														"entering a range and the signal output - is dependent on the speed input.",


																																																																																																														"The spawner controller hacks into the functioning of a monster spawner, allowing you to " +
																																																																																																																"either shut it down completely, or to adjust the spawn rate as you see fit. The minimum " +
																																																																																																																"allowable spawn rate is determined by the power input.",

																																																																																																																"The sprinkler will, when supplied with water, spray it out on the blocks below, watering " +
																																																																																																																		"crops and speeding up their growth, hydrating farmland, and extinguishing fires. Higher " +
																																																																																																																		"water pressures - created by larger power inputs to the pumps - result in increased range.",

																																																																																																																		"The item vacuum draws items and XP orbs towards it, and will absorb them into its " +
																																																																																																																				"internal storage when they get close. To extract the stored XP, right-click it with a " +
																																																																																																																				"screwdriver while sneaking.",

																																																																																																																				"The woodcutter will cut away at the trunk of a tree, causing the rest of the tree to " +
																																																																																																																						"collapse. In particularly densely forested areas, this will also knock over some adjacent " +
																																																																																																																						"trees; in jungles, the foliage is so dense that the machine automatically compensates " +
																																																																																																																						"and only cuts down the center of the tree to avoid biome-wide destruction.",

																																																																																																																						"The engine controller is as simple as it sounds. Simply place it below an engine, and apply redstone power, and the engine will " +
																																																																																																																								"shut itself down.",

																																																																																																																								"The mob radar can detect mobs in a large radius around it, and is unobstructed by blocks in the way. It will display their " +
																																																																																																																										"positions on the NSEW plane, signified with the appropriate icon. If you have a motion tracker in your inventory, the radar " +
																																																																																																																										"will also render a HUD of sorts to alert you to mob positions near you.",

																																																																																																																										"The coil winder is used to wind up coil items, to store mechanical energy for later use. It can also use previously-wound coils " +
																																																																																																																												"as an energy source, and provide a small amount of power ("+TileEntityWinder.UNWINDTORQUE+" N/m at "+TileEntityWinder.UNWINDSPEED+
																																																																																																																												" rad/s).",

																																																																																																																												"The TNT cannon fires primed TNT either with a given velocity (specified by speed, launch angle, and compass heading), or to " +
																																																																																																																														"a specific target coordinate. It requires a continuous supply of TNT to continue operation.",


																																																																																																																														"The sonic weapon is a ranged defense weapon designed to protect against hostile mobs and players. At higher and higher volumes " +
																																																																																																																																"(made possible by higher input torques), it can cause more and more debilitating effects on those in its effect radius, from " +
																																																																																																																																"confusion to blindness to drowning to death. This machine follows the inverse-square law, and the severity of its effects " +
																																																																																																																																"decrease accordingly.",

																																																																																																																																"The blast furnace is the main way to make steel. Simply supply carbon (in the form of coal) at the left, gunpowder at the " +
																																																																																																																																		"bottom-left, and fill any or all of the middle nine slots with iron ingots. Heat the machine to "+
																																																																																																																																		TileEntityBlastFurnace.SMELTTEMP+"C to start the alloying process. "+
																																																																																																																																		"The machine will operate more efficiently with more slots full of iron, and will give higher bonuses (up to a 49% chance of a " +
																																																																																																																																		"bonus with size up to 100% (11% chance). Note that the coal is not fuel - an external heat source is required.",

																																																																																																																																		"The force field emits a solid sphere of energy that repels all hostile mobs and projectiles. Note that it is only solid in one " +
																																																																																																																																				"direction - threats may leave but cannot re-enter. However, due to the large magnetic field, arrows will be diverted even if " +
																																																																																																																																				"launched from inside the sphere. This machine needs a clear view above it equal to the selected radius.",

																																																																																																																																				"The music box is essentially an integrated noteblock circuit. Simply program it with music - either using one of the demo " +
																																																																																																																																						"tracks or by writing your own, and give it power to run the music. A redstone pulse will cycle the music once; for looping " +
																																																																																																																																						"music, use shaft power.",

																																																																																																																																						"The mob harvester is designed to kill mobs as if a player was, so that normally player-only drops (such as Blaze Rods) can " +
																																																																																																																																								"be automatically farmed. The machine will deal damage to mobs directly above it, with the amount depending on the power input.",

																																																																																																																																								"The projector, designed to be paired with CCTV, outputs an image against a wall. Note that the wall must be 6 blocks wide and " +
																																																																																																																																										"5 blocks tall, and must be unobstructed.",

																																																																																																																																										"The railgun is a very powerful defence weapon. It uses strong magnetic fields to accelerate a heavy metallic projectile at the " +
																																																																																																																																												"target, dealing very large amounts of damage. Note that more massive projectiles deal much more damage, and can destroy large " +
																																																																																																																																												"areas of terrain. The railgun will automatically target the nearest visible mob; for maximum efficiency, mount it where it has " +
																																																																																																																																												"a clear view of the surrounding area. You may also invert the railgun to target below itself rather than above.",

																																																																																																																																												"The silver iodide cannon is used to control the weather. It has a built-in light sensor to determine the current weather and " +
																																																																																																																																														"if set to do so, will fire the appropriate chemicals into the air to alter it. Note that it has a cooldown time, and must have " +
																																																																																																																																														"a clear view of the sky.",


																																																																																																																																														"The item refresher keeps nearby items from despawning. Note that items that have been maintained for a long time will " +
																																																																																																																																																"immediately despawn if the machine is destroyed or loses power.",

																																																																																																																																																"The cave scanner functions much like a laser 3D scanner. It sends pulses into the ground and can detect the interface between " +
																																																																																																																																																		"air and other blocks, which it will render in the world. This allows you to map caves from the surface. Its field of view is " +
																																																																																																																																																		"16 blocks by default, and can be moved around by right-clicking the block. Right-click will push it in the direction you are " +
																																																																																																																																																		"looking, and shift-right-clicking will push it in the opposite direction you are looking. Caves are color coded based on depth.",

																																																																																																																																																		"The scaleable chest allows you to store your things in a small space. Its inventory size scales with the amount of power it " +
																																																																																																																																																				"receives, up to a maximum of "+TileEntityScaleableChest.MAXSIZE+" stacks. If the power input drops or is lost "+
																																																																																																																																																				"while there are items in the now-removed slots, they will remain hidden and inaccessible until sufficient power is restored.",

																																																																																																																																																				"The flooder is designed to fill a depression with liquid piped into it. Each bucket worth pumped in equates to one placed " +
																																																																																																																																																						"liquid block. It will fill the lowest layers first.",

																																																																																																																																																						"The smoke detector will sound an alarm when it detects a nearby fire block, and will also beep occasionally when the coil " +
																																																																																																																																																								"powering it is running low.",

																																																																																																																																																								"The firestarter allows you to ignite flammable materials in a large radius, and is therefore very effective at clearing areas " +
																																																																																																																																																										"in forests or tall grass. Due to its ability to ignite creatures, it can also serve as a powerful, if rather dangerous, form of " +
																																																																																																																																																										"defence.",

																																																																																																																																																										"The freeze gun is a nonlethal defence weapon. It automatically targets threats, and will freeze them in a block of ice, " +
																																																																																																																																																												"completely immobilizing them without causing them other harm. Ice is more effective ammunition than snowballs. Like the Railgun, " +
																																																																																																																																																												"this machine can be oriented upside-down and is most effective with a clear view of the surroundings. Note that frozen mobs " +
																																																																																																																																																												"can still attack if approached.",

																																																																																																																																																												"The magnetizer uses a rapidly rotating shaft to create a magnetic field inside a shaft core. This is one of the critical " +
																																																																																																																																																														"components of an AC Electric Engine.",


																																																																																																																																																														"The containment field is complementary to the Force Field - while that keeps things out, this keeps them in. Note that hostile " +
																																																																																																																																																																"mobs which fire projectiles, like Skeletons and the Wither, can still hit targets outside the field without leaving it " +
																																																																																																																																																																"themselves. This machine needs a clear view above it equal to the selected radius.",

																																																																																																																																																																"The CCTV monitor is how CCTV cameras are viewed. Simply add 3 dyes as the color code - matching one of the existing CCTV " +
																																																																																																																																																																		"cameras in the world - and shift-right-click it to use the camera.",

																																																																																																																																																																		"The CCTV camera allows you to see areas that you would otherwise not have safe access to visit yourself. Simply provide it a " +
																																																																																																																																																																				"charged wind spring and three dyes as a color code, and you can view the world through its lens with the aid of a CCTV Monitor. " +
																																																																																																																																																																				"To exit camera mode, press \"\\\". The camera can be reoriented with a screwdriver (shift-clicking for inclination).",

																																																																																																																																																																				"The steel purifier is used to transform basic low-grade steel into pure HSLA steel for use in machines. Like the Blast Furnace, " +
																																																																																																																																																																						"it requires gunpowder and temperatures over "+TileEntityPurifier.SMELTTEMP+" C.",

																																																																																																																																																																						"",

																																																																																																																																																																						"",

																																																																																																																																																																						"",

																																																																																																																																																																						"",


																																																																																																																																																																						"These are the tools powered by wound-up coils. Their coil depletes slightly with each use. To recharge them, craft them with " +
																																																																																																																																																																								"a new coil and you will get the old coil back, which can be used again.",

																																																																																																																																																																								"The wind spring is used to store mechanical energy. Once wound up with the coil winder, it can be used in another winder to " +
																																																																																																																																																																										"provide a small amount of power, or it can be used to run various tools. Note that when charging coils, they run the risk of " +
																																																																																																																																																																										"breaking, a risk which gets larger and larger as they are wound tighter and tighter.",

																																																																																																																																																																										"The ultrasound sends high-frequency noise through blocks to determine their density. From this, it is able to detect nearby " +
																																																																																																																																																																												"pockets of low density, such as air, or variations in density, like ore veins. It can also detect the silverfish inside " +
																																																																																																																																																																												"infested blocks.",

																																																																																																																																																																												"The motion tracker emits a sonar and radar pulse to detect any creatures and evaluate their distance and speed. From this, it " +
																																																																																																																																																																														"can inform you of nearby mobs (including if they are targeting you), even through walls.",

																																																																																																																																																																														"The vacuum is essentially a handheld version of the Item Vacuum machine. Right-clicking with it will draw items and XP towards " +
																																																																																																																																																																																"you, assuming you have the inventory space for it. Shift-right-clicking on a storage block can be used to empty it all at once.",

																																																																																																																																																																																"The knockback gun deals no direct damage, but can be used to send mobs flying back and keep them away. Using shift-right-click, " +
																																																																																																																																																																																		"its powerful sound blast can also be used to punch out connected areas of loose material, like gravel, leaves, and ores.",

																																																																																																																																																																																		"The gravel gun launches pieces of flint at high speed at a target. At higher charge levels, the pieces can deal enough damage to " +
																																																																																																																																																																																				"kill most mobs in one hit. Due to its low mass and enormous velocity, the flint is not affected by gravity over its trajectory.",

																																																																																																																																																																																				"The fireball launcher fires ghast-style fireballs at a target. It can be charged like a bow, up to maximum power at dark blue. " +
																																																																																																																																																																																						"Higher charge levels mean a more powerful explosion, up to twice that of a TNT blast.",


																																																																																																																																																																																						"The handheld crafting unit is exactly as the name implies; it can be used as a portable crafting table without having to place " +
																																																																																																																																																																																								"it down.",

																																																																																																																																																																																								"Night-vision goggles allow you to see clearly, even in full blackness. However, they require a helmet armor slot and provide no " +
																																																																																																																																																																																										"defensive protection. To alleviate this, they can be crafted with a diamond helmet to make a night-vision helmet.",

																																																																																																																																																																																										"Buckets of lubricant or jet fuel can be made by placing empty buckets in the appropriate slots of the grinder or fractionator. " +
																																																																																																																																																																																												"They can then be carried to a machine that needs them, to save on piping for long distances.",

																																																																																																																																																																																												"Bedrock tools are made from bedrock ingots. They have infinite durability, and are faster than the diamond tools while being " +
																																																																																																																																																																																														"just as effective at harvesting blocks. Also, because of their hardness, a bedrock pickaxe can cleanly harvest any block, as if " +
																																																																																																																																																																																														"it was permanently enchanted with Silk Touch.",

																																																																																																																																																																																														"The TNT cannon targeting aid allows you to easily choose the target of the TNT cannon. Simply look at your target, placing your " +
																																																																																																																																																																																																"crosshairs directly on it, and the TNT cannon, if it is set to target mode, will target the block you are looking at. This tool " +
																																																																																																																																																																																																"may not work for extremely far targets, but they are outside the TNT cannon's range anyways.",

																																																																																																																																																																																																"",

																																																																																																																																																																																																"",

																																																																																																																																																																																																"",


																																																																																																																																																																																																"These are the basic items required to craft nearly any machine.",

																																																																																																																																																																																																"The steel ingot, made by smelting an iron ingot in a blast or pulse jet furnace, is the basis for all the machines, as only " +
																																																																																																																																																																																																		"steel has sufficient tensile and shear strength to handle the loads applied by the machines.",

																																																																																																																																																																																																		"Like its name suggests, the base panel is a sheet of steel, often used to form the base of a machine or engine, or occasionally " +
																																																																																																																																																																																																				"to craft other items.",

																																																																																																																																																																																																				"The shaft is a critical part in any machine that produces or transmits power, and is also used to craft gear assemblies.",

																																																																																																																																																																																																				"The gear is the other fundamental part used in crafting power transmission devices.",

																																																																																																																																																																																																				"Gear units come in 4 types - 2x, 4x, 8x, and 16x. Each is used to create the corresponding gearbox, and to craft some other " +
																																																																																																																																																																																																						"parts.",

																																																																																																																																																																																																						"The mount is the basic unit of most of the power transmission devices.",

																																																																																																																																																																																																						"Scrap can be crafted from several steel or iron items and can be melted down in the pulse jet furnace to produce the original " +
																																																																																																																																																																																																								"ingots, which can be reused. Note that one unit of iron scrap is equivalent to one iron ingot, but nine steel scrap are " +
																																																																																																																																																																																																								"required for one steel ingot.",


																																																																																																																																																																																																								"The impeller is used to move fluids around, and is the core part of pumps, fans, and any engines with liquid fuel. It is " +
																																																																																																																																																																																																										"also used to make compressors and turbines.",

																																																																																																																																																																																																										"The compressor in the turbine engines compresses the air intake and acheive greater power. It is also critical to " +
																																																																																																																																																																																																												"crafting the turbine.",

																																																																																																																																																																																																												"The turbine extracts energy from the airflow inside the engines.",

																																																																																																																																																																																																												"The diffuser is the air intake for jet engines, used to slow the flow for the compressor.",

																																																																																																																																																																																																												"The combustor is a critical part of the jet engines, as it is what burns the fuel and adds heat to the airflow.",

																																																																																																																																																																																																												"The radiator dissipates heat from gasoline-fueled engines; without one, they would quickly fail.",

																																																																																																																																																																																																												"The condensor is used to cool steam back into water for reuse in the engine.",

																																																																																																																																																																																																												"Gold coil, when electrified, will create a magnetic field. If the current alternates, the magnetic field will rotate. This " +
																																																																																																																																																																																																														"is the driving force behind the AC engine.",


																																																																																																																																																																																																														"The cylinder is the core of a gasoline-fueled engine, where the fuel is actually consumed and the power is generated.",

																																																																																																																																																																																																														"Paddle panels are used to generate torque from falling water.",

																																																																																																																																																																																																														"A shaft core connects a high-torque rotating part like a panel to a central shaft.",

																																																																																																																																																																																																														"The ignition unit lights the fuel in an engine, allowing combustion to occur and power to be generated.",

																																																																																																																																																																																																														"The propeller blade is used to catch airflow and convert its energy into shaft power.",

																																																																																																																																																																																																														"The hub is the attachment point for propeller blades.",

																																																																																																																																																																																																														"",

																																																																																																																																																																																																														"",


																																																																																																																																																																																																														"",

																																																																																																																																																																																																														"The focusing barrel is part of the heat ray, and keeps the beam coherent and straight.",

																																																																																																																																																																																																														"The lens is focuses the beam of the heat ray into something capable of lighting everything in its sights on fire.",

																																																																																																																																																																																																														"The power module converts shaft power into temperature and pressure inside the heat ray, essential to creating the beam.",

																																																																																																																																																																																																														"The laser core actually generates the heat ray beam, once supplied with sufficient power to ionize the gas inside.",

																																																																																																																																																																																																														"The drill is used to craft some machines that are required to grind or break blocks, like the boring machine and the extractor.",

																																																																																																																																																																																																														"The pressure head is an essential part of the compactor, as it is what delivers the high temperatures and pressures to the target. " +
																																																																																																																																																																																																																"Due to the extreme conditions it is subjected to, it can only be made with the hardest, strongest materials.",

																																																																																																																																																																																																																"The flywheel core, when mounted, creates a flywheel to store rotational energy.",


																																																																																																																																																																																																																"The radar unit will detect entities or blocks around it, depending on the needs of the " +
																																																																																																																																																																																																																		"machine it is connected to. To reduce noise and enhance signal quality, it is made of " +
																																																																																																																																																																																																																		"some of the hardest material available - obsidian, and has a gold antenna.",

																																																																																																																																																																																																																		"The sonar unit emits pulses of ultrasonic noise and analyzes the echoes to determine the contours of the surroundings and the " +
																																																																																																																																																																																																																				"presence of any objects - or entities - within.",

																																																																																																																																																																																																																				"More complicated machines must be able to process input data; for this purpose, a circuit board is used.",

																																																																																																																																																																																																																				"The screen is a display output for a machine.",

																																																																																																																																																																																																																				"The mixer is rather self-explanatory; it is used in machines that mix two or more ingredients.",

																																																																																																																																																																																																																				"The saw is used as a cutting device, employed most prominently in the grinder and woodcutter. Like one would expect, machines " +
																																																																																																																																																																																																																						"that incorporate it are rather dangerous to touch.",

																																																																																																																																																																																																																						"Netherrack dust is made from grinding netherrack blocks in the grinder, while tar is made by grinding soul sand. Both of these are " +
																																																																																																																																																																																																																								"required constituents of jet fuel due to their hydrocarbon content.",

																																																																																																																																																																																																																								"Make 20 stacks of wooden stairs? No problem; various wood items can be put in the grinder and ground into sawdust, which can be " +
																																																																																																																																																																																																																										"made back into wood or, to save sugarcane, into paper.",


																																																																																																																																																																																																																										"A bearing is used to keep a rotating shaft steady and reduce friction as it rotates.",

																																																																																																																																																																																																																										"Belts connect shafts without using toothed gears. Their nature allows them to be moved around and to create variable equivalent " +
																																																																																																																																																																																																																												"gear ratios.",

																																																																																																																																																																																																																												"Ball bearings are the main component of a shaft bearing.",

																																																																																																																																																																																																																												"Brake discs are used to slow a shaft down, or to provide torque resistance.",

																																																																																																																																																																																																																												"A worm gear is a crucial part in making the worm gear machine.",

																																																																																																																																																																																																																												"Gears can be crafted from materials other than steel. Wood, Stone, Diamond, and Bedrock can also be used.",

																																																																																																																																																																																																																												"Shafts can also be crafted from wood, stone, diamond, or bedrock. Use a stick as the shaft part in a wood shaft. Stronger " +
																																																																																																																																																																																																																														"materials allow for more torque to be transmitted at higher speeds. Overloading a shaft will cause it to violently fail.",

																																																																																																																																																																																																																														"Gear units can be made of the alternative gear types. Stronger gears wear down more slowly - diamond and bedrock gears do not " +
																																																																																																																																																																																																																																"wear down at all. Simply craft them using the same base materials as a shaft, but subtituting a gear unit for the rod/shaft.",


																																																																																																																																																																																																																																"The linear induction motor is used to accelerate a magnetic projectile to high speed, and requires a large amount of energy to " +
																																																																																																																																																																																																																																		"operate.",

																																																																																																																																																																																																																																		"The tension coil is used to store mechanical energy to be released later.",

																																																																																																																																																																																																																																		"",

																																																																																																																																																																																																																																		"",

																																																																																																																																																																																																																																		"",

																																																																																																																																																																																																																																		"",

																																																																																																																																																																																																																																		"",

																																																																																																																																																																																																																																		"",


																																																																																																																																																																																																																																		"These are the resource-type items.",

																																																																																																																																																																																																																																		"Bedrock dust is produced when bedrock breakers completely grind away a bedrock block. This is an essential part of the compactor, " +
																																																																																																																																																																																																																																				"as bedrock is the only material hard enough to withstand the enormous pressures and temperatures. Additionally, with the" +
																																																																																																																																																																																																																																				"MineralTools mod, it can be used to craft Bedrock Tools, the best tier available.",

																																																																																																																																																																																																																																				"Ore dust, slurry, and solution are intermediate products of the extractor. Slurry and solution require high input shaft speeds " +
																																																																																																																																																																																																																																						"and a constant water supply, while the dust requires high input torque. None of these items has any other use.",

																																																																																																																																																																																																																																						"Ore flakes are the fourth stage output of the extractor. These can be smelted in an ordinary furnace to make ingots or gems of " +
																																																																																																																																																																																																																																								"the source ore's material. Creation of this product requires high torque.",

																																																																																																																																																																																																																																								"Anthracite, Prismane, and Lonsdaleite are intermediate stages of the coal compression process. Anthracite and Lonsdaleite can " +
																																																																																																																																																																																																																																										"also be crafted into decorative blocks.",//Anthracite can also be used as fuel in the furnace, lasting 32 items.",

																																																																																																																																																																																																																																										"Decorative blocks are made from 9 units of the respective materials, such as steel, anthracite, or lonsdaleite. They have no " +
																																																																																																																																																																																																																																										"functional purpose.",

																																																																																																																																																																																																																																										"Blast Glass is made by smelting obsidian in a pulse jet furnace, and while transparent like glass, will resist nearly any " +
																																																																																																																																																																																																																																												"explosion. Like ordinary glass, the blocks can also be crafted into blast glass panes.",

																																																																																																																																																																																																																																												"If you are lucky enough to dig through a dungeon, mineshaft, or stronghold with a Boring machine or Pile Driver, the spawner, " +
																																																																																																																																																																																																																																														"rather than being completely destroyed, will drop as a usable item, and will retain its mob type. This is an invaluable asset " +
																																																																																																																																																																																																																																														"for mob farms, especially when combined with the spawner controller.",


																																																																																																																																																																																																																																														"Yeast is cultivated in the fermenter by adding sugar and water to dirt. It grows best at temperatures between 20 and 40 " +
																																																																																																																																																																																																																																																"degrees, peaking at 25 degrees. Outside this range, it will grow slowly or not at all. Be careful - at temperatures above " +
																																																																																																																																																																																																																																																"60C, yeast will die. Ethanol sludge is made from adding yeast to plant matter and water. It is created most rapidly at around " +
																																																																																																																																																																																																																																																"35C.",

																																																																																																																																																																																																																																																"Ethanol crystals can be made by drying out ethanol sludge in a furnace. These crystals can then be used as fuel in the ethanol " +
																																																																																																																																																																																																																																																		"and performance engines.",

																																																																																																																																																																																																																																																		"A bedrock ingot is used in the crafting of bedrock tools.",

																																																																																																																																																																																																																																																		"Silver ingots are used to make silver compounds. Silver is an occasional bonus from the extractor when processing gold.",

																																																																																																																																																																																																																																																		"Salt is made from seawater, and is a source of iodine.",

																																																																																																																																																																																																																																																		"Ammonium Nitrate is a useful ingredient in explosives.",

																																																																																																																																																																																																																																																		"Silver iodide is made from silver and iodine (from salt), and is used by the machine of the same name to induce rain.",

																																																																																																																																																																																																																																																		"Aluminum powder can be used with iron ingots to start a thermite reaction in the firestarter, providing more heat than any " +
																																																																																																																																																																																																																																																				"other fuel. It is an occasional bonus from the extractor when processing iron.",


																																																																																																																																																																																																																																																				"Railgun ammunition is made from a ferromagnetic core and surrounded with heavy materials. Denser materials mean a more damaging " +
																																																																																																																																																																																																																																																						"impact. Each tier is crafted from adding mass to the previous one.",

																																																																																																																																																																																																																																																						"Projector Slides are inserted into the projector to display images.",

																																																																																																																																																																																																																																																						"",

																																																																																																																																																																																																																																																						"",

																																																																																																																																																																																																																																																						"",

																																																																																																																																																																																																																																																						"",

																																																																																																																																																																																																																																																						"",

																																																																																																																																																																																																																																																						"",
	};

	public static final String[] machineNotes = {
		"",

		"",

		"",

		"",

		"",

		"",

		"",

		"",


		"",

		"",

		"",

		"",

		"",

		"",

		"",

		"",


		"",

		"",

		"",

		"",

		"",

		"",

		"",

		"",


		"",

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fkW\nPower Source: Redstone\nRequires Consumables: No\nRisks: None", EnumEngineType.DC.getTorque(), EnumEngineType.DC.getSpeed(), EnumEngineType.DC.getPowerKW()),

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fkW\nPower Source: Wind\nRequires Consumables: No\nRisks: Spinning Blades\nNotes: Requires 16 blocks of free space in front of the blades, optimal efficiency at 128 m altitude", EnumEngineType.WIND.getTorque(), EnumEngineType.WIND.getSpeed(), EnumEngineType.WIND.getPowerKW()),

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fkW\nPower Source: Water and Heat\nRequires Consumables: Water\nRisks: Overheating at 150C", EnumEngineType.STEAM.getTorque(), EnumEngineType.STEAM.getSpeed(), EnumEngineType.STEAM.getPowerKW()),

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fkW\nPower Source: Ethanol\nRequires Consumables: Ethanol\nRisks: None", EnumEngineType.GAS.getTorque(), EnumEngineType.GAS.getSpeed(), EnumEngineType.GAS.getPowerKW()),

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fkW\nPower Source: Redstone Clock and Magnetized Shaft Core\nRequires Consumables: No\nRisks: None", EnumEngineType.AC.getTorque(), EnumEngineType.AC.getSpeed(), EnumEngineType.AC.getPowerKW()),

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fkW\nPower Source: Ethanol And Additives\nRequires Consumables: Ethanol and Redstone/Gunpowder/Blaze Powder\nRisks: Overheating at 1000C", EnumEngineType.SPORT.getTorque(), EnumEngineType.SPORT.getSpeed(), EnumEngineType.SPORT.getPowerKW()),

		String.format("Max Torque: %d Nm\nMax Speed: %d rad/s\nMax Power: %.3fkW\nPower Source: Falling Water\nRequires Consumables: No\nRisks: Spinning Paddles\nNotes: Requires tall waterfalls to acheive full power", EnumEngineType.HYDRO.getTorque(), EnumEngineType.HYDRO.getSpeed(), EnumEngineType.HYDRO.getPowerKW()),



		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fMW\nPower Source: Jet Fuel\nRequires Consumables: Jet Fuel\nRisks: None", EnumEngineType.MICRO.getTorque(), EnumEngineType.MICRO.getSpeed(), EnumEngineType.MICRO.getPowerMW()),

		String.format("Torque: %d Nm\nSpeed: %d rad/s\nPower: %.3fMW\nPower Source: Jet Fuel\nRequires Consumables: Jet Fuel\nRisks: Ingestion", EnumEngineType.JET.getTorque(), EnumEngineType.JET.getSpeed(), EnumEngineType.JET.getPowerMW()),

		"",

		"",

		"",

		"",

		"",

		"",


		"",

		"",

		"",

		"",

		"",

		"",

		"",

		"",


		"",

		"",

		"",

		"",

		"",

		"",

		"",

		"",


		"",

		String.format("Required Power: %dW\nRequired Torque: %d Nm\nRequired Speed: %d rad/s\nPower Input: Back", PowerReceivers.BEDROCKBREAKER.getMinPower(), PowerReceivers.BEDROCKBREAKER.getMinTorque(), 1),

		String.format("Required Power: %dW\nRequired Torque: %d Nm\nRequired Speed: %d rad/s\nPower Input: Back", PowerReceivers.FERMENTER.getMinPower(), 1, PowerReceivers.FERMENTER.getMinSpeed()),

		String.format("Required Power: %dW\nRequired Torque: %d Nm\nRequired Speed: %d rad/s\nPower Input: Back", PowerReceivers.GRINDER.getMinPower(), PowerReceivers.GRINDER.getMinTorque(), 1),

		String.format("Required Power: %dW\nPower Input: Back", PowerReceivers.FLOODLIGHT.getMinPower()),

		String.format("Required Power: %dW\nRange = 8+Power/%d m\nPower Input: Back", PowerReceivers.HEATRAY.getMinPower(), TileEntityHeatRay.FALLOFF),

		String.format("Required Power: Varies, Up to %dW\nRequired Torque: Varies, Up to %d Nm\nPower Input: Back", TileEntityBorer.DIGPOWER*500, TileEntityBorer.OBSIDIANTORQUE),

		String.format("Required Power: %dW per meter\nPower Input: Back or Front", TileEntityPileDriver.BASEPOWER),

		String.format("Required Power: %dW\nPower Input: Any horizontal side or bottom", PowerReceivers.AEROSOLIZER.getMinPower()),

		String.format("Required Power: %dW\nPower Input: Back", PowerReceivers.LIGHTBRIDGE.getMinPower()),

		String.format("Stage 1 Power: %dW\nStage 2 Power: %dW\nStage 3 Power: %dW\nStage 4 Power: %dW\n", PowerReceivers.EXTRACTOR.getMinPower(0), PowerReceivers.EXTRACTOR.getMinPower(1), PowerReceivers.EXTRACTOR.getMinPower(2), PowerReceivers.EXTRACTOR.getMinPower(3))+
		String.format("Stage 1 Torque: %dNm\nStage 4 Torque: %dNm\n", PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinTorque(3))+
		String.format("Stage 2 Speed: %drad/s\nStage 3 Speed: %drad/s\n", PowerReceivers.EXTRACTOR.getMinSpeed(1), PowerReceivers.EXTRACTOR.getMinSpeed(2))+
		"Power Input: Bottom\nNotes: Stages 2 and 3 require a water supply",

		String.format("Required Speed: %d rad/s\nPower Input: Bottom\nMax Temperature: %dC", PowerReceivers.PULSEJET.getMinSpeed(), TileEntityPulseFurnace.MAXTEMP),

		String.format("Required Power: %d W\nPower Input: Front or Back", PowerReceivers.PUMP.getMinPower()),

		String.format("Capacity: %d m^3", TileEntityReservoir.CAPACITY),

		String.format("Required Power: %dW\nRange = 8+(Power-%d)/%d m\nPower Input: Back", PowerReceivers.FAN.getMinPower(), PowerReceivers.FAN.getMinPower(), TileEntityFan.FALLOFF),

		String.format("Required Power: %dW\nRequired Torque: %d Nm\nRequired Pressure: %dkPa\nRequired Temperature: %dC\nMax Pressure: %dkPa\nMax Temperature: %dC\nPower Input: Back", PowerReceivers.COMPACTOR.getMinPower(), PowerReceivers.COMPACTOR.getMinTorque(), TileEntityCompactor.REQPRESS, TileEntityCompactor.REQTEMP, TileEntityCompactor.MAXPRESSURE, TileEntityCompactor.MAXTEMP),

		String.format("Required Power: %dW\nRange = 8+(Power-%d)/%d m\nPower Input: Bottom", PowerReceivers.AUTOBREEDER.getMinPower(), PowerReceivers.AUTOBREEDER.getMinPower(), TileEntityAutoBreeder.FALLOFF),

		String.format("Required Power: %dW\nRange = 8+(Power-%d)/%d m\nPower Input: Any side", PowerReceivers.BAITBOX.getMinPower(), PowerReceivers.BAITBOX.getMinPower(), TileEntityBaitBox.FALLOFF),

		String.format("Required Power: %dW\nRequired Speed: %d rad/s\nPower Input: Any side", PowerReceivers.FIREWORK.getMinPower(), PowerReceivers.FIREWORK.getMinSpeed()),

		String.format("Required Power: %dW\nRequired Speed: %d rad/s\nPower Input: Bottom", PowerReceivers.FRACTIONATOR.getMinPower(), PowerReceivers.FRACTIONATOR.getMinSpeed()),

		String.format("Required Power: %dW\nField-of-View = 1+2*log_2(power-%d) m\nPower Input: Back", PowerReceivers.GPR.getMinPower(), PowerReceivers.GPR.getMinPower()),

		String.format("Required Power: %dW\nMax Attainable Temperature: %dC\nPower Input: Bottom", PowerReceivers.HEATER.getMinPower(), TileEntityHeater.MAXTEMP),

		String.format("Required Power: %dW\nRequired Speed: %d rad/s\nMaximum Temperature: %dC\nLiquid Capacity: %d m^3\nPower Input: Any horizontal side", PowerReceivers.OBSIDIAN.getMinPower(), PowerReceivers.OBSIDIAN.getMinSpeed(), TileEntityObsidianMaker.MAXTEMP, TileEntityObsidianMaker.CAPACITY),

		String.format("Required Power: %dW\nRange = (Power) m\nPower Input: Bottom\nReaction Time: %d-(Speed/%d)", TileEntityPlayerDetector.FALLOFF, TileEntityPlayerDetector.BASESPEED, TileEntityPlayerDetector.SPEEDFACTOR),


		String.format("Required Power: %dW\nMin Delay: %d-log_2(Speed)\nPower Input: Spawner Block", PowerReceivers.SPAWNERCONTROLLER.getMinPower(), TileEntitySpawnerController.BASEDELAY),

		String.format("Internal Capacity: %d m^3\nRange: Water Pressure/400", TileEntitySprinkler.CAPACITY),

		String.format("Required Power: %dW\nRange = 8+Power/%d m\nPower Input: Any side", PowerReceivers.VACUUM.getMinPower(), PowerReceivers.VACUUM.getMinPower()),

		String.format("Required Power: %dW\nRequired Torque: %d Nm\nPower Input: Back", PowerReceivers.WOODCUTTER.getMinPower(), PowerReceivers.WOODCUTTER.getMinTorque()),

		"Method of activation: Redstone",

		String.format("Required Power: %dW\nRange = 8+(Power-%d)/%d m\nPower Input: Bottom", PowerReceivers.MOBRADAR.getMinPower(), PowerReceivers.MOBRADAR.getMinPower(), TileEntityMobRadar.FALLOFF),

		"Maximum energy acheivable: Torque input",

		String.format("Required Power: %dW\nRequired Torque: %d Nm\nPower Input: Any horizontal side or bottom", PowerReceivers.TNTCANNON.getMinPower(), PowerReceivers.TNTCANNON.getMinTorque()),


		String.format("Required Power: %dW\nRange = 8+(Power-%d)/%d m\nBlindness at: %d W/m^2\nConfusion at: %d W/m^2\nDrowning at: %d W/m^2, Lethality at: %d W/m^2\nPower Input: Any side", PowerReceivers.SONICWEAPON.getMinPower(), PowerReceivers.SONICWEAPON.getMinPower(), TileEntitySonicWeapon.FALLOFF, TileEntitySonicWeapon.EYEDAMAGE, TileEntitySonicWeapon.BRAINDAMAGE, TileEntitySonicWeapon.LUNGDAMAGE, TileEntitySonicWeapon.LETHALVOLUME),

		"Requires: Coal/Charcoal in left slot, Iron in middle slots\nBonuses: Chance increases as the number of filled slots rises, " +
				"max 49%\nMax bonus size: Increases as the number of filled slots rises, max 100% (doubling)",

				String.format("Required Power: %dW\nRange = 2+(Power-%d)/%d m\nPower Input: Any side", PowerReceivers.FORCEFIELD.getMinPower(), PowerReceivers.FORCEFIELD.getMinPower(), TileEntityForceField.FALLOFF),

				String.format("Activation: Redstone for one cycle, shaft power for looped music\nLoop Power: %d \nData Entry Mode: Sequential\nPower Input: Any side", TileEntityMusicBox.LOOPPOWER),

				String.format("Required Power: %dW\nDamage Dealt: log_2((2+power/%d)^6)\nPower Input: Any horizontal side or bottom", PowerReceivers.MOBHARVESTER.getMinPower(), PowerReceivers.MOBHARVESTER.getMinPower()),

				String.format("Required Power: %dW\nPower Input: Back", PowerReceivers.PROJECTOR.getMinPower()),

				String.format("Required Power: %dW\nRequired Torque: 512*SQRT(mass) Nm\nPower Input: Bottom or top, orientation dependent", PowerReceivers.RAILGUN.getMinPower()),

				String.format("Required Power: %dW\nPower Input: Bottom", PowerReceivers.WEATHERCONTROLLER.getMinPower()),


				String.format("Required Power: %dW\nRange = 4+(Power-%d)/%d m\nPower Input: Any side", PowerReceivers.ITEMREFRESHER.getMinPower(), PowerReceivers.ITEMREFRESHER.getMinPower(), TileEntityItemRefresher.FALLOFF),

				String.format("Required Power: %dW\nMove the selected region by right-clicking the block\nPower Input: Any side", PowerReceivers.CAVESCANNER.getMinPower()),

				String.format("Required Power: %dW\nInventory Size: 9+(Power-%d)/%d slots\nMax Size: %d\nPower Input: Bottom", PowerReceivers.SCALECHEST.getMinPower(), PowerReceivers.SCALECHEST.getMinPower(), TileEntityScaleableChest.FALLOFF, TileEntityScaleableChest.MAXSIZE),

				"Requires piped liquid to operate",

				"Requires a charged windspring as a power source",

				String.format("Required Power: %dW\nRange = Temperature/50 m\nAcceptable fuels: Coal, Blaze Powder, Wood, Lava, Thermite\nPower Input: Any side", PowerReceivers.IGNITER.getMinPower()),

				String.format("Required Power: %dW\nRequired Torque: %d Nm\nPower Input: Bottom or top, orientation dependent", PowerReceivers.FREEZEGUN.getMinPower(), PowerReceivers.FREEZEGUN.getMinTorque()),

				String.format("Required Power: %dW\nRequired Speed: %d rad/s\nPower Input: Back", PowerReceivers.MAGNETIZER.getMinPower(), PowerReceivers.MAGNETIZER.getMinSpeed()),


				String.format("Required Power: %dW\nRange = 2+(Power-%d)/%d m\nPower Input: Any side\nRequires %dW to restrain a Wither and %dW to restrain an EnderDragon", PowerReceivers.CONTAINMENT.getMinPower(), PowerReceivers.CONTAINMENT.getMinPower(), TileEntityContainment.FALLOFF, TileEntityContainment.WITHERPOWER, TileEntityContainment.DRAGONPOWER),

				String.format("Required Power: %dW\nRequired Torque: %d Nm\nPower Input: Bottom", PowerReceivers.SCREEN.getMinPower(), PowerReceivers.SCREEN.getMinTorque()),

				"",

				String.format("Required Power: %dW\nRequired Torque: %d Nm\nRequired Temperature: %dC\nPower Input: Any Side", PowerReceivers.PURIFIER.getMinPower(), PowerReceivers.PURIFIER.getMinTorque(), TileEntityPurifier.SMELTTEMP),

				"",

				"",

				"",

				"",


				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",


				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",


				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",

				"",
	};

}

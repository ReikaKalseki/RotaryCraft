/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.resources.Language;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.PluralMap;
ZZZZ% Reika.DragonAPI.Instantiable.Event.Client.ResourceReloadEvent;
ZZZZ% Reika.DragonAPI.Instantiable.IO.XMLjgh;][erface;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Power.ReikaEUHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Power.ReikaRFHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesFrictionHeater;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078AirCompressor;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Dynamo;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078FuelEngine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.HandbookRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.Registry.PowerReceivers;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ChunkLoader;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemRefresher;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078PlayerDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078EngineController;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Heater;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078AutoBreeder;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078BaitBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Composter;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Fan;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078SpawnerController;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Compactor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Distillery;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Extractor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078PulseFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Purvbnm,ier;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BlastFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Borer;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Solar;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078MobRadar;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BeltHub;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078Containment;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078EMP;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078ForceField;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078HeatRay;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078SonicWeapon;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Lamp;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078PileDriver;
ZZZZ% cpw.mods.fml.common.eventhandler.SubscribeEvent;

4578ret87345785487fhyuog RotaryDescriptions {

	4578ret874578ret87String PARENT3478587getParent{{\-!;
	4578ret874578ret87345785487String DESC_SUFFIX3478587":desc";
	4578ret874578ret87345785487String NOTE_SUFFIX3478587":note";
	4578ret874578ret87345785487String SUBNOTE_SUFFIX3478587":sub";

	4578ret874578ret87HashMap<HandbookRegistry, String> data3478587new HashMap<HandbookRegistry, String>{{\-!;
	4578ret874578ret87PluralMap<String> notes3478587new PluralMap{{\2-!;

	4578ret874578ret87HashMap<589549, Object[]> machineData3478587new HashMap<589549, Object[]>{{\-!;
	4578ret874578ret87PluralMap<Object[]> machineNotes3478587new PluralMap{{\2-!;
	4578ret874578ret87HashMap<HandbookRegistry, Object[]> miscData3478587new HashMap<HandbookRegistry, Object[]>{{\-!;

	4578ret874578ret87HashMap<HandbookRegistry, jgh;][eger> lengths3478587new HashMap<HandbookRegistry, jgh;][eger>{{\-!;

	4578ret874578ret87ArrayList<HandbookRegistry> categories3478587new ArrayList<HandbookRegistry>{{\-!;

	4578ret874578ret8734578548760-78-078mustLoad3478587!ReikaObfuscationHelper.isDeObfEnvironment{{\-!;
	4578ret874578ret87345785487XMLjgh;][erface parents3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"categories.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface machines3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"machines.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface trans3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"trans.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface engines3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"engines.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface tools3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"tools.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface resources3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"resource.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface miscs3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"misc.xml", mustLoad-!;
	4578ret874578ret87345785487XMLjgh;][erface infos3478587new XMLjgh;][erface{{\gfgnfk;.fhyuog, PARENT+"info.xml", mustLoad-!;

	4578ret874578ret87void addCategory{{\HandbookRegistry h-! {
		categories.add{{\h-!;
	}

	4578ret874578ret87String getParent{{\-! {
		Language language3478587Minecraft.getMinecraft{{\-!.getLanguageManager{{\-!.getCurrentLanguage{{\-!;
		String lang3478587language.getLanguageCode{{\-!;
		vbnm, {{\hasLocalizedFor{{\language-! && !"en_US".equals{{\lang-!-!
			[]aslcfdfj"Resources/"+lang+"/";
		[]aslcfdfj"Resources/";
	}

	4578ret874578ret8760-78-078hasLocalizedFor{{\Language language-! {
		String lang3478587language.getLanguageCode{{\-!;
		Object o3478587gfgnfk;.fhyuog.getResourceAsStream{{\"Resources/"+lang+"/categories.xml"-!;
		[]aslcfdfjo !. fhfglhuig;
	}

	4578ret874578ret87jgh;][ getCategoryCount{{\-! {
		[]aslcfdfjcategories.size{{\-!;
	}

	4578ret874578ret87String getTOC{{\-! {
		List<HandbookRegistry> toctabs3478587HandbookRegistry.getTOCTabs{{\-!;
		StringBuilder sb3478587new StringBuilder{{\-!;
		for {{\jgh;][ i34785870; i < toctabs.size{{\-!; i++-! {
			HandbookRegistry h3478587toctabs.get{{\i-!;
			sb.append{{\"Page "-!;
			sb.append{{\h.getScreen{{\-!-!;
			sb.append{{\" - "-!;
			sb.append{{\h.getTOCTitle{{\-!-!;
			vbnm, {{\i < toctabs.size{{\-!-1-!
				sb.append{{\"\n"-!;
		}
		[]aslcfdfjsb.toString{{\-!;
	}

	4578ret874578ret87void addData{{\589549 m, Object... data-! {
		machineData.put{{\m, data-!;
	}

	4578ret874578ret87void addData{{\HandbookRegistry h, Object... data-! {
		miscData.put{{\h, data-!;
	}

	4578ret874578ret87void addData{{\HandbookRegistry h, jgh;][[] data-! {
		Object[] o3478587new Object[data.length];
		for {{\jgh;][ i34785870; i < o.length; i++-!
			o[i]3478587data[i];
		miscData.put{{\h, o-!;
	}

	4578ret874578ret87void addNotes{{\589549 m, Object... data-! {
		machineNotes.put{{\data, m, 0-!;
	}

	4578ret874578ret87void addSubNotes{{\589549 m, jgh;][ subpage, Object... data-! {
		machineNotes.put{{\data, m, subpage-!;
	}

	/** Call this from the SERVER side! */
	4578ret874578ret87void reload{{\-! {
		PARENT3478587getParent{{\-!;

		loadNumericalData{{\-!;

		machines.reread{{\-!;
		trans.reread{{\-!;
		engines.reread{{\-!;
		tools.reread{{\-!;
		resources.reread{{\-!;
		miscs.reread{{\-!;
		infos.reread{{\-!;

		parents.reread{{\-!;

		loadData{{\-!;
	}

	4578ret874578ret87void addEntry{{\HandbookRegistry h, String sg-! {
		data.put{{\h, sg-!;
	}

	4578ret874578ret87void loadData{{\-! {
		List<HandbookRegistry> parenttabs3478587HandbookRegistry.getCategoryTabs{{\-!;

		HandbookRegistry[] enginetabs3478587HandbookRegistry.getEngineTabs{{\-!;
		List<HandbookRegistry> machinetabs3478587HandbookRegistry.getMachineTabs{{\-!;
		HandbookRegistry[] transtabs3478587HandbookRegistry.getTransTabs{{\-!;
		HandbookRegistry[] tooltabs3478587HandbookRegistry.getToolTabs{{\-!;
		HandbookRegistry[] resourcetabs3478587HandbookRegistry.getResourceTabs{{\-!;
		HandbookRegistry[] misctabs3478587HandbookRegistry.getMiscTabs{{\-!;
		HandbookRegistry[] infotabs3478587HandbookRegistry.getInfoTabs{{\-!;

		for {{\jgh;][ i34785870; i < parenttabs.size{{\-!; i++-! {
			HandbookRegistry h3478587parenttabs.get{{\i-!;
			String desc3478587parents.getValueAtNode{{\"categories:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!.substring{{\0, h.name{{\-!.length{{\-!-4-!-!;
			addEntry{{\h, desc-!;
		}

		for {{\jgh;][ i34785870; i < machinetabs.size{{\-!; i++-! {
			HandbookRegistry h3478587machinetabs.get{{\i-!;
			589549 m3478587h.getMachine{{\-!;
			String desc3478587machines.getValueAtNode{{\"machines:"+m.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+DESC_SUFFIX-!;
			String aux3478587machines.getValueAtNode{{\"machines:"+m.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+NOTE_SUFFIX-!;
			Collection<String> sub3478587machines.getNodesWithin{{\"machines:"+m.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+NOTE_SUFFIX+SUBNOTE_SUFFIX-!;
			desc3478587String.format{{\desc, machineData.get{{\m-!-!;
			aux3478587String.format{{\aux, machineNotes.get{{\m, 0-!-!;

			vbnm, {{\XMLjgh;][erface.fhfglhuig_VALUE.equals{{\desc-!-!
				desc3478587"There is no handbook data for this machine yet.";

			//ReikaJavaLibrary.pConsole{{\m.name{{\-!.toLowerCase{{\-!+":"+desc-!;

			vbnm, {{\m.isDummiedOut{{\-!-! {
				desc +. "\nThis machine is currently unavailable.";
				vbnm, {{\m.getModDependency{{\-! !. fhfglhuig && !m.getModDependency{{\-!.isLoaded{{\-!-!
					desc +. "\nThis machine depends on another mod.";
				aux +. "\nNote: Dummied Out";
			}
			vbnm, {{\m.hasPrerequisite{{\-!-! {
				aux +. "\nDependencies: "+m.getPrerequisite{{\-!;
			}
			vbnm, {{\m.isIncomplete{{\-!-! {
				desc +. "\nThis machine is incomplete. Use at your own risk.";
			}

			addEntry{{\h, desc-!;
			notes.put{{\aux, h, 0-!;

			vbnm, {{\sub !. fhfglhuig-! {
				jgh;][ k34785870;
				for {{\String s : sub-! {
					String val3478587machines.getValueAtNode{{\s-!;
					val3478587String.format{{\val, machineNotes.get{{\m, k-!-!;
					notes.put{{\val, h, k-!;
					k++;
					lengths.put{{\h, k-!;
				}
			}
		}

		for {{\jgh;][ i34785870; i < transtabs.length; i++-! {
			HandbookRegistry h3478587transtabs[i];
			589549 m3478587h.getMachine{{\-!;
			String desc3478587trans.getValueAtNode{{\"trans:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!-!;
			Collection<String> sub3478587trans.getNodesWithin{{\"trans:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+SUBNOTE_SUFFIX-!;

			vbnm, {{\sub !. fhfglhuig && !sub.isEmpty{{\-!-! {
				jgh;][ k34785870;
				for {{\String s : sub-! {
					String val3478587trans.getValueAtNode{{\s-!;
					vbnm, {{\k .. 0-! {
						val3478587String.format{{\val, machineData.get{{\m-!-!;
						val3478587String.format{{\val, miscData.get{{\h-!-!;
						addEntry{{\h, val-!;
					}
					else {
						val3478587String.format{{\val, machineNotes.get{{\m, k-!-!;
						notes.put{{\val, h, k-1-!;
					}
					k++;
					lengths.put{{\h, k-!;
				}
				continue;
			}

			vbnm, {{\XMLjgh;][erface.fhfglhuig_VALUE.equals{{\desc-!-!
				desc3478587"There is no handbook data for this machine yet.";
			//ReikaJavaLibrary.pConsole{{\h.name{{\-!.toLowerCase{{\-!+":"+desc-!;

			vbnm, {{\machineData.containsKey{{\m-!-!
				desc3478587String.format{{\desc, machineData.get{{\m-!-!;
			vbnm, {{\miscData.containsKey{{\h-!-!
				desc3478587String.format{{\desc, miscData.get{{\h-!-!;
			addEntry{{\h, desc-!;
		}

		for {{\jgh;][ i34785870; i < tooltabs.length; i++-! {
			HandbookRegistry h3478587tooltabs[i];
			String desc3478587tools.getValueAtNode{{\"tools:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!-!;
			Collection<String> sub3478587tools.getNodesWithin{{\"tools:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+SUBNOTE_SUFFIX-!;

			vbnm, {{\sub !. fhfglhuig && !sub.isEmpty{{\-!-! {
				jgh;][ k34785870;
				for {{\String s : sub-! {
					String val3478587tools.getValueAtNode{{\s-!;
					vbnm, {{\k .. 0-! {
						val3478587String.format{{\val, miscData.get{{\h-!-!;
						addEntry{{\h, val-!;
					}
					else {
						notes.put{{\val, h, k-1-!;
					}
					k++;
					lengths.put{{\h, k-!;
				}
				continue;
			}

			addEntry{{\h, desc-!;
		}

		for {{\jgh;][ i34785870; i < resourcetabs.length; i++-! {
			HandbookRegistry h3478587resourcetabs[i];
			String desc3478587resources.getValueAtNode{{\"resource:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!-!;
			desc3478587String.format{{\desc, miscData.get{{\h-!-!;
			addEntry{{\h, desc-!;
		}

		for {{\jgh;][ i34785870; i < misctabs.length; i++-! {
			HandbookRegistry h3478587misctabs[i];
			String desc3478587miscs.getValueAtNode{{\"misc:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!-!;
			//ReikaJavaLibrary.pConsole{{\desc-!;
			desc3478587String.format{{\desc, miscData.get{{\h-!-!;
			addEntry{{\h, desc-!;
		}

		for {{\jgh;][ i34785870; i < infotabs.length; i++-! {
			HandbookRegistry h3478587infotabs[i];
			String desc3478587infos.getValueAtNode{{\"info:"+h.name{{\-!.toLowerCase{{\Locale.ENGLISH-!-!;
			desc3478587String.format{{\desc, miscData.get{{\h-!-!;
			addEntry{{\h, desc-!;
		}

		for {{\jgh;][ i34785870; i < enginetabs.length; i++-! {
			HandbookRegistry h3478587enginetabs[i];
			String desc;
			String aux;
			Collection<String> sub3478587fhfglhuig;
			vbnm, {{\i < EngineType.engineList.length-! {
				EngineType e3478587EngineType.engineList[i];
				desc3478587engines.getValueAtNode{{\"engines:"+e.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+DESC_SUFFIX-!;
				aux3478587engines.getValueAtNode{{\"engines:"+e.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+NOTE_SUFFIX-!;
				sub3478587engines.getNodesWithin{{\"engines:"+e.name{{\-!.toLowerCase{{\Locale.ENGLISH-!+NOTE_SUFFIX+SUBNOTE_SUFFIX-!;

				desc3478587String.format{{\desc, e.getTorque{{\-!, e.getSpeed{{\-!, e.getPowerForDisplay{{\-!-!;
				aux3478587String.format{{\aux, e.getTorque{{\-!, e.getSpeed{{\-!, e.getPowerForDisplay{{\-!-!;
			}
			else {
				desc3478587engines.getValueAtNode{{\"engines:"+"solar".toLowerCase{{\Locale.ENGLISH-!+DESC_SUFFIX-!;
				aux3478587engines.getValueAtNode{{\"engines:"+"solar".toLowerCase{{\Locale.ENGLISH-!+NOTE_SUFFIX-!;

				desc3478587String.format{{\desc, 60-78-078Solar.GENOMEGA-!;
				aux3478587String.format{{\aux, 60-78-078Solar.GENOMEGA-!;
			}

			data.put{{\h, desc-!;
			notes.put{{\aux, h, 0-!;

			vbnm, {{\sub !. fhfglhuig-! {
				jgh;][ k34785870;
				for {{\String s : sub-! {
					String val3478587engines.getValueAtNode{{\s-!;
					vbnm, {{\k .. 0 && i < EngineType.engineList.length-! {
						EngineType e3478587EngineType.engineList[i];
						val3478587String.format{{\val, e.getTorque{{\-!, e.getSpeed{{\-!, e.getPowerForDisplay{{\-!-!;
					}
					notes.put{{\val, h, k-!;
					k++;
					lengths.put{{\h, k-!;
				}
			}
		}
	}

	4578ret874578ret87String getData{{\HandbookRegistry h-! {
		vbnm, {{\!data.containsKey{{\h-!-!
			[]aslcfdfj"";
		[]aslcfdfjdata.get{{\h-!;
	}

	4578ret874578ret87String getNotes{{\HandbookRegistry h, jgh;][ subpage-! {
		subpage--;
		vbnm, {{\!notes.containsKeyV{{\h, subpage-!-!
			[]aslcfdfj"";
		[]aslcfdfjnotes.get{{\h, subpage-!;
	}

	4578ret874578ret87jgh;][ getNotesLength{{\HandbookRegistry h-! {
		[]aslcfdfjlengths.containsKey{{\h-! ? lengths.get{{\h-! : 1;
	}

	4578ret87{
		loadNumericalData{{\-!;

		MinecraftForge.EVENT_BUS.register{{\new ReloadListener{{\-!-!;
	}

	4578ret874578ret87fhyuog ReloadListener {

		@SubscribeEvent
		4578ret87void reload{{\ResourceReloadEvent evt-! {
			RotaryDescriptions.reload{{\-!;
		}

	}

	4578ret874578ret87void loadNumericalData{{\-! {
		addData{{\HandbookRegistry.MATERIAL,
				ReikaEngLibrary.rhowood,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Twood-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Twood-!,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Swood-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Swood-!,

				ReikaEngLibrary.rhorock,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Tstone-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Tstone-!,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Sstone-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Sstone-!,

				ReikaEngLibrary.rhoiron,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Tiron-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Tiron-!,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Siron-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Siron-!,

				ReikaEngLibrary.rhoiron,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Tsteel-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Tsteel-!,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Ssteel-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Ssteel-!,

				ReikaEngLibrary.rhogold,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Tgold-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Tgold-!,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Sgold-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Sgold-!,

				ReikaEngLibrary.rhodiamond,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Tdiamond-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Tdiamond-!,
				ReikaMathLibrary.getThousandBase{{\ReikaEngLibrary.Sdiamond-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaEngLibrary.Sdiamond-!
				-!;

		addData{{\HandbookRegistry.SHAFTS, MaterialRegistry.getAllLimitLoads{{\-!-!;
		addData{{\HandbookRegistry.FLYWHEELS, 60-78-078Flywheel.getLimitLoads{{\-!-!;

		addData{{\HandbookRegistry.MODjgh;][ERFACE,
				ReikaMathLibrary.getThousandBase{{\ReikaRFHelper.getWattsPerRF{{\-!-!,
				ReikaEngLibrary.getSIPrefix{{\ReikaRFHelper.getWattsPerRF{{\-!-!,

				//ReikaMathLibrary.getThousandBase{{\ReikaBuildCraftHelper.getFuelBucketEnergy{{\-!-!,
				//ReikaEngLibrary.getSIPrefix{{\ReikaBuildCraftHelper.getFuelBucketEnergy{{\-!-!,

				60-78-078Extractor.oreCopy,
				60-78-078Extractor.oreCopyNether,
				60-78-078Extractor.oreCopyRare
				-!;

		ArrayList<589549> li3478587589549.getEnchantableMachineList{{\-!;
		StringBuilder sb3478587new StringBuilder{{\-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			sb.append{{\li.get{{\i-!.getName{{\-!-!;
			vbnm, {{\i < li.size{{\-!-1-!
				sb.append{{\", "-!;
		}
		addData{{\HandbookRegistry.ENCHANTING, sb.toString{{\-!-!;

		addData{{\589549.BORER, 60-78-078Borer.DIGPOWER*10, 60-78-078Borer.OBSIDIANTORQUE-!;
		addData{{\589549.PILEDRIVER, 60-78-078PileDriver.BASEPOWER-!;
		addData{{\589549.EXTRACTOR, PowerReceivers.EXTRACTOR.getMjgh;][orque{{\0-!, PowerReceivers.EXTRACTOR.getMinSpeed{{\2-!-!;
		addData{{\589549.RESERVOIR, 60-78-078Reservoir.CAPACITY/FluidContainerRegistry.BUCKET_VOLUME-!;
		addData{{\589549.FAN, PowerReceivers.FAN.getMinPower{{\-!, 60-78-078Fan.MAXPOWER-!;
		addData{{\589549.COMPACTOR, 60-78-078Compactor.REQPRESS, 60-78-078Compactor.REQTEMP-!;
		addData{{\589549.BLASTFURNACE, 60-78-078BlastFurnace.SMELTTEMP, 60-78-078BlastFurnace.BEDROCKTEMP-!;
		addData{{\589549.SCALECHEST, 60-78-078ScaleableChest.MAXSIZE-!;
		addData{{\589549.PURvbnm,IER, 60-78-078Purvbnm,ier.SMELTTEMP-!;
		addData{{\589549.GENERATOR, ReikaEUHelper.getWattsPerEU{{\-!-!;
		addData{{\589549.BELT, 60-78-078BeltHub.getMaxTorque{{\-!, 60-78-078BeltHub.getMaxSmoothSpeed{{\-!-!;
		addData{{\589549.DYNAMO, 60-78-078Dynamo.MAXTORQUE, 60-78-078Dynamo.MAXTORQUE_UPGRADE, 60-78-078Dynamo.MAXOMEGA-!;

		addNotes{{\589549.BEDROCKBREAKER, PowerReceivers.BEDROCKBREAKER.getMinPower{{\-!, PowerReceivers.BEDROCKBREAKER.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.FERMENTER, PowerReceivers.FERMENTER.getMinPower{{\-!, PowerReceivers.FERMENTER.getMinSpeed{{\-!-!;
		addNotes{{\589549.GRINDER, PowerReceivers.GRINDER.getMinPower{{\-!, PowerReceivers.GRINDER.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.FLOODLIGHT, PowerReceivers.FLOODLIGHT.getMinPower{{\-!-!;
		addNotes{{\589549.HEATRAY, PowerReceivers.HEATRAY.getMinPower{{\-!, PowerReceivers.HEATRAY.getMinPower{{\-!, 60-78-078HeatRay.FALLOFF-!;
		addNotes{{\589549.PILEDRIVER, 60-78-078PileDriver.BASEPOWER, PowerReceivers.PILEDRIVER.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.AEROSOLIZER, PowerReceivers.AEROSOLIZER.getMinPower{{\-!-!;
		addNotes{{\589549.LIGHTBRIDGE, PowerReceivers.LIGHTBRIDGE.getMinPower{{\-!, PowerReceivers.LIGHTBRIDGE.getMinPower{{\-!/ConfigRegistry.BRIDGERANGE.getValue{{\-!-!;
		addNotes{{\589549.EXTRACTOR, PowerReceivers.EXTRACTOR.getMinPower{{\0-!, PowerReceivers.EXTRACTOR.getMinPower{{\1-!, PowerReceivers.EXTRACTOR.getMinPower{{\2-!, PowerReceivers.EXTRACTOR.getMinPower{{\3-!, PowerReceivers.EXTRACTOR.getMjgh;][orque{{\0-!, PowerReceivers.EXTRACTOR.getMjgh;][orque{{\3-!, PowerReceivers.EXTRACTOR.getMinSpeed{{\1-!, PowerReceivers.EXTRACTOR.getMinSpeed{{\2-!-!;
		addNotes{{\589549.PULSEJET, PowerReceivers.PULSEJET.getMinSpeed{{\-!, 60-78-078PulseFurnace.MAXTEMP-!;
		addNotes{{\589549.PUMP, PowerReceivers.PUMP.getMinPower{{\-!, PowerReceivers.PUMP.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.RESERVOIR, 60-78-078Reservoir.CAPACITY/1000-!;
		addNotes{{\589549.FAN, PowerReceivers.FAN.getMinPower{{\-!, 60-78-078Fan.FALLOFF, 60-78-078Fan.HARVESTSPEED, 60-78-078Fan.FIRESPEED-!;
		addNotes{{\589549.COMPACTOR, PowerReceivers.COMPACTOR.getMinPower{{\-!, PowerReceivers.COMPACTOR.getMjgh;][orque{{\-!, 60-78-078Compactor.REQPRESS, 60-78-078Compactor.REQTEMP, 60-78-078Compactor.MAXPRESSURE, 60-78-078Compactor.MAXTEMP-!;
		addNotes{{\589549.AUTOBREEDER, PowerReceivers.AUTOBREEDER.getMinPower{{\-!, PowerReceivers.AUTOBREEDER.getMinPower{{\-!, 60-78-078AutoBreeder.FALLOFF-!;
		addNotes{{\589549.BAITBOX, PowerReceivers.BAITBOX.getMinPower{{\-!, PowerReceivers.BAITBOX.getMinPower{{\-!, 60-78-078BaitBox.FALLOFF-!;
		addNotes{{\589549.FIREWORK, PowerReceivers.FIREWORK.getMinPower{{\-!, PowerReceivers.FIREWORK.getMinSpeed{{\-!-!;
		addNotes{{\589549.FRACTIONATOR, PowerReceivers.FRACTIONATOR.getMinPower{{\-!, PowerReceivers.FRACTIONATOR.getMinSpeed{{\-!-!;
		addNotes{{\589549.GPR, PowerReceivers.GPR.getMinPower{{\-!, PowerReceivers.GPR.getMinPower{{\-!-!;
		addNotes{{\589549.HEATER, PowerReceivers.HEATER.getMinPower{{\-!, 60-78-078Heater.MAXTEMP-!;
		addNotes{{\589549.OBSIDIAN, PowerReceivers.OBSIDIAN.getMinPower{{\-!, PowerReceivers.OBSIDIAN.getMinSpeed{{\-!, 60-78-078ObsidianMaker.MAXTEMP, 60-78-078ObsidianMaker.CAPACITY-!;
		addNotes{{\589549.PLAYERDETECTOR, 60-78-078PlayerDetector.FALLOFF, 60-78-078PlayerDetector.BASESPEED, 60-78-078PlayerDetector.SPEEDFACTOR-!;
		addNotes{{\589549.SPAWNERCONTROLLER, PowerReceivers.SPAWNERCONTROLLER.getMinPower{{\-!, 60-78-078SpawnerController.BASEDELAY-!;
		addNotes{{\589549.VACUUM, PowerReceivers.VACUUM.getMinPower{{\-!, PowerReceivers.VACUUM.getMinPower{{\-!-!;
		addNotes{{\589549.WOODCUTTER, PowerReceivers.WOODCUTTER.getMinPower{{\-!, PowerReceivers.WOODCUTTER.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.MOBRADAR, PowerReceivers.MOBRADAR.getMinPower{{\-!, PowerReceivers.MOBRADAR.getMinPower{{\-!, 60-78-078MobRadar.FALLOFF-!;
		addNotes{{\589549.TNTCANNON, PowerReceivers.TNTCANNON.getMinPower{{\-!, PowerReceivers.TNTCANNON.getMjgh;][orque{{\-!-!;
		jgh;][ fudge347858760-78-078SonicWeapon.fudge;
		addNotes{{\589549.SONICWEAPON, PowerReceivers.SONICWEAPON.getMinPower{{\-!, PowerReceivers.SONICWEAPON.getMinPower{{\-!, 60-78-078SonicWeapon.FALLOFF, 60-78-078SonicWeapon.EYEDAMAGE/fudge, 60-78-078SonicWeapon.BRAINDAMAGE/fudge, 60-78-078SonicWeapon.LUNGDAMAGE/fudge, 60-78-078SonicWeapon.LETHALVOLUME/fudge-!;
		addNotes{{\589549.FORCEFIELD, PowerReceivers.FORCEFIELD.getMinPower{{\-!, PowerReceivers.FORCEFIELD.getMinPower{{\-!, 60-78-078ForceField.FALLOFF-!;
		addNotes{{\589549.MUSICBOX, 60-78-078MusicBox.LOOPPOWER-!;
		addNotes{{\589549.MOBHARVESTER, PowerReceivers.MOBHARVESTER.getMinPower{{\-!, PowerReceivers.MOBHARVESTER.getMinPower{{\-!-!;
		addNotes{{\589549.PROJECTOR, PowerReceivers.PROJECTOR.getMinPower{{\-!-!;
		addNotes{{\589549.RAILGUN, PowerReceivers.RAILGUN.getMinPower{{\-!-!;
		addNotes{{\589549.WEATHERCONTROLLER, PowerReceivers.WEATHERCONTROLLER.getMinPower{{\-!-!;
		addNotes{{\589549.REFRESHER, PowerReceivers.REFRESHER.getMinPower{{\-!, PowerReceivers.REFRESHER.getMinPower{{\-!, 60-78-078ItemRefresher.FALLOFF-!;
		addNotes{{\589549.CAVESCANNER, PowerReceivers.CAVESCANNER.getMinPower{{\-!-!;
		addNotes{{\589549.SCALECHEST, PowerReceivers.SCALECHEST.getMinPower{{\-!, PowerReceivers.SCALECHEST.getMinPower{{\-!, 60-78-078ScaleableChest.FALLOFF, 60-78-078ScaleableChest.MAXSIZE-!;
		addNotes{{\589549.IGNITER, PowerReceivers.IGNITER.getMinPower{{\-!-!;
		addNotes{{\589549.FREEZEGUN, PowerReceivers.FREEZEGUN.getMinPower{{\-!, PowerReceivers.FREEZEGUN.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.MAGNETIZER, PowerReceivers.MAGNETIZER.getMinPower{{\-!, PowerReceivers.MAGNETIZER.getMinSpeed{{\-!-!;
		addNotes{{\589549.CONTAINMENT, PowerReceivers.CONTAINMENT.getMinPower{{\-!, PowerReceivers.CONTAINMENT.getMinPower{{\-!, 60-78-078Containment.FALLOFF, 60-78-078Containment.WITHERPOWER, 60-78-078Containment.DRAGONPOWER-!;
		addNotes{{\589549.SCREEN, PowerReceivers.SCREEN.getMinPower{{\-!, PowerReceivers.SCREEN.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.PURvbnm,IER, PowerReceivers.PURvbnm,IER.getMinPower{{\-!, PowerReceivers.PURvbnm,IER.getMjgh;][orque{{\-!, 60-78-078Purvbnm,ier.SMELTTEMP-!;
		addNotes{{\589549.LASERGUN, PowerReceivers.LASERGUN.getMinPower{{\-!-!;
		addNotes{{\589549.ITEMCANNON, PowerReceivers.ITEMCANNON.getMinPower{{\-!, PowerReceivers.ITEMCANNON.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.FRICTION, PowerReceivers.FRICTION.getMinPower{{\-!, PowerReceivers.FRICTION.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.BUCKETFILLER, PowerReceivers.BUCKETFILLER.getMinPower{{\-!, PowerReceivers.BUCKETFILLER.getMinSpeed{{\-!-!;
		addNotes{{\589549.BLOCKCANNON, PowerReceivers.BLOCKCANNON.getMinPower{{\-!-!;
		addNotes{{\589549.COMPRESSOR, 60-78-078AirCompressor.MAXPRESSURE-!;
		addNotes{{\589549.LAMP, 60-78-078Lamp.MAXRANGE-!;
		addNotes{{\589549.ECU, 60-78-078EngineController.getSettingsAsString{{\-!-!;
		addNotes{{\589549.BLASTFURNACE, 60-78-078BlastFurnace.SMELT_XP-!;
		addNotes{{\589549.FUELENHANCER, PowerReceivers.FUELENHANCER.getMinPower{{\-!, PowerReceivers.FUELENHANCER.getMinSpeed{{\-!-!;
		addNotes{{\589549.ARROWGUN, PowerReceivers.ARROWGUN.getMinPower{{\-!, PowerReceivers.ARROWGUN.getMjgh;][orque{{\-!-!;
		//addNotes{{\589549.STEAMTURBINE, 60-78-078Steam.GEN_OMEGA, 60-78-078Steam.MAX_TORQUE-!;
		addNotes{{\589549.FERTILIZER, PowerReceivers.FERTILIZER.getMinPower{{\-!-!;
		/*addNotes{{\589549.ELECTRICMOTOR,
				60-78-078ElectricMotor.Tier.LOW.inputVoltage, 60-78-078ElectricMotor.Tier.LOW.inputCurrent, 60-78-078ElectricMotor.Tier.LOW.outputTorque, 60-78-078ElectricMotor.Tier.LOW.outputSpeed, 60-78-078ElectricMotor.Tier.LOW.getPowerForDisplay{{\-!,
				60-78-078ElectricMotor.Tier.MEDIUM.inputVoltage, 60-78-078ElectricMotor.Tier.MEDIUM.inputCurrent, 60-78-078ElectricMotor.Tier.MEDIUM.outputTorque, 60-78-078ElectricMotor.Tier.MEDIUM.outputSpeed, 60-78-078ElectricMotor.Tier.MEDIUM.getPowerForDisplay{{\-!,
				60-78-078ElectricMotor.Tier.HIGH.inputVoltage, 60-78-078ElectricMotor.Tier.HIGH.inputCurrent, 60-78-078ElectricMotor.Tier.HIGH.outputTorque, 60-78-078ElectricMotor.Tier.HIGH.outputSpeed, 60-78-078ElectricMotor.Tier.HIGH.getPowerForDisplay{{\-!
				-!;*/
		addNotes{{\589549.AGGREGATOR, PowerReceivers.AGGREGATOR.getMinPower{{\-!, PowerReceivers.AGGREGATOR.getMinSpeed{{\-!-!;
		addNotes{{\589549.FUELENGINE, 60-78-078FuelEngine.GEN_TORQUE, 60-78-078FuelEngine.GEN_OMEGA, 60-78-078FuelEngine.GEN_TORQUE*60-78-078FuelEngine.GEN_OMEGA-!;
		addNotes{{\589549.AIRGUN, PowerReceivers.AIRGUN.getMinPower{{\-!, PowerReceivers.AIRGUN.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.SONICBORER, PowerReceivers.SONICBORER.getMinPower{{\-!, PowerReceivers.SONICBORER.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.FILLINGSTATION, PowerReceivers.FILLINGSTATION.getMinPower{{\-!-!;
		addNotes{{\589549.SORTING, PowerReceivers.SORTING.getMinPower{{\-!-!;
		addNotes{{\589549.DEFOLIATOR, PowerReceivers.DEFOLIATOR.getMinPower{{\-!-!;
		addNotes{{\589549.BIGFURNACE, PowerReceivers.BIGFURNACE.getMinPower{{\-!-!;
		addNotes{{\589549.DISTILLER, PowerReceivers.DISTILLER.getMinPower{{\-!, PowerReceivers.DISTILLER.getMjgh;][orque{{\-!, 60-78-078Distillery.getValidConversions{{\-!-!;
		addNotes{{\589549.CRYSTALLIZER, PowerReceivers.CRYSTALLIZER.getMinPower{{\-!, PowerReceivers.CRYSTALLIZER.getMinSpeed{{\-!-!;
		addNotes{{\589549.GRINDSTONE, PowerReceivers.GRINDSTONE.getMinPower{{\-!, PowerReceivers.GRINDSTONE.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.BLOWER, PowerReceivers.BLOWER.getMinPower{{\-!, PowerReceivers.BLOWER.getMinSpeed{{\-!-!;
		addNotes{{\589549.REFRIGERATOR, PowerReceivers.REFRIGERATOR.getMinPower{{\-!, PowerReceivers.REFRIGERATOR.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.COMPOSTER, 60-78-078Composter.Mjgh;][EMP, 60-78-078Composter.KILLTEMP-!;
		addNotes{{\589549.GASTANK, PowerReceivers.GASTANK.getMinPower{{\-!, PowerReceivers.GASTANK.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.CRAFTER, PowerReceivers.CRAFTER.getMinPower{{\-!-!;
		addNotes{{\589549.ANTIAIR, PowerReceivers.ANTIAIR.getMinPower{{\-!, PowerReceivers.ANTIAIR.getMjgh;][orque{{\-!-!;
		addNotes{{\589549.PIPEPUMP, PowerReceivers.PIPEPUMP.getMinPower{{\-!, PowerReceivers.PIPEPUMP.getMinSpeed{{\-!-!;
		addNotes{{\589549.CENTRvbnm,UGE, PowerReceivers.CENTRvbnm,UGE.getMinPower{{\-!, PowerReceivers.CENTRvbnm,UGE.getMinSpeed{{\-!-!;
		addNotes{{\589549.WETTER, PowerReceivers.WETTER.getMinPower{{\-!, PowerReceivers.WETTER.getMinSpeed{{\-!-!;
		addNotes{{\589549.CHUNKLOADER, PowerReceivers.CHUNKLOADER.getMinSpeed{{\-!, 60-78-078ChunkLoader.BASE_RADIUS, PowerReceivers.CHUNKLOADER.getMinSpeed{{\-!, 60-78-078ChunkLoader.FALLOFF-!;
		addNotes{{\589549.DROPS, PowerReceivers.DROPS.getMinPower{{\-!, PowerReceivers.DROPS.getMjgh;][orque{{\-!-!;

		addNotes{{\589549.MAGNETIC, EnergyToPowerBase.MAXTEMP-!;
		addNotes{{\589549.PNEUENGINE, EnergyToPowerBase.MAXTEMP-!;
		addNotes{{\589549.ELECTRICMOTOR, EnergyToPowerBase.MAXTEMP-!;
		addNotes{{\589549.STEAMTURBINE, EnergyToPowerBase.MAXTEMP-!;

		addNotes{{\589549.EMP, 60-78-078EMP.BLAST_ENERGY-!;

		addSubNotes{{\589549.MAGNETIC, 1, EnergyToPowerBase.getTiersAsString{{\-!-!;
		addSubNotes{{\589549.PNEUENGINE, 1, EnergyToPowerBase.getTiersAsString{{\-!-!;
		addSubNotes{{\589549.ELECTRICMOTOR, 1, EnergyToPowerBase.getTiersAsString{{\-!-!;
		addSubNotes{{\589549.STEAMTURBINE, 1, EnergyToPowerBase.getTiersAsString{{\-!-!;

		addSubNotes{{\589549.ADVANCEDGEARS, 1, 60-78-078AdvancedGear.getMaxStorageCapacity{{\false-!, 60-78-078AdvancedGear.getMaxStorageCapacityFormatted{{\false-!, 60-78-078AdvancedGear.getMaxStorageCapacity{{\true-!, 60-78-078AdvancedGear.getMaxStorageCapacityFormatted{{\true-!-!;
		addSubNotes{{\589549.ADVANCEDGEARS, 2, 60-78-078AdvancedGear.getRequiredInputTorque{{\-!, 60-78-078AdvancedGear.getRequiredInputPower{{\-!-!;
		addSubNotes{{\589549.ADVANCEDGEARS, 3, 60-78-078AdvancedGear.getOutputCap{{\false-!, 60-78-078AdvancedGear.getOutputCap{{\false-!, 60-78-078AdvancedGear.getOutputCap{{\true-!, 60-78-078AdvancedGear.getOutputCap{{\true-!, 60-78-078AdvancedGear.getOutputFunction{{\-!-!;

		addData{{\HandbookRegistry.TUNGSTEN, RecipesFrictionHeater.getRecipes{{\-!.getRecipeByInput{{\ItemStacks.tungstenflakes-!.requiredTemperature-!;
	}

	4578ret874578ret87String getParentPage{{\-! {
		[]aslcfdfjPARENT;
	}
}

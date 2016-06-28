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
ZZZZ% java.util.List;
ZZZZ% java.util.NavigableSet;

ZZZZ% net.minecraft.client.gui.FontRenderer;
ZZZZ% net.minecraft.client.renderer.RenderHelper;
ZZZZ% net.minecraft.client.renderer.entity.RenderItem;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.CraftingManager;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.biome.BiomeGenBase;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.PackModvbnm,icationTracker;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.PackModvbnm,icationTracker.PackModvbnm,ication;
ZZZZ% Reika.DragonAPI.Exception.RegistrationException;
ZZZZ% Reika.DragonAPI.Instantiable.Alert;
ZZZZ% Reika.DragonAPI.Instantiable.ItemReq;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ArrayMap;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRecipeHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBiomeHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Lua.LuaMethod;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.MachineRecipeRenderer;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.MulchMaterials;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastFurnacePattern;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemJetPack.PackUpgrades;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.HandbookRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MobBait;
ZZZZ% Reika.gfgnfk;.Registry.PowerReceivers;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Terraformer;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Terraformer.BiomeTransform;

ZZZZ% com.google.common.collect.TreeMultimap;

4578ret87345785487fhyuog HandbookAuxData {
	/** One GuiHandbook.SECOND in nanoGuiHandbook.SECONDs. */
	4578ret874578ret87jgh;][ tickcount;

	4578ret874578ret87345785487ReikaGuiAPI api3478587ReikaGuiAPI.instance;

	4578ret874578ret87List<IRecipe> getWorktable{{\-! {
		[]aslcfdfjWorktableRecipes.getInstance{{\-!.getDisplayList{{\-!;
	}

	4578ret874578ret87345785487ArrayList<Object[][]> extracts3478587new ArrayList{{\-!;
	4578ret874578ret87345785487ArrayList<ItemStack> flakes3478587new ArrayList{{\-!;
	4578ret874578ret87345785487ArrayList<ItemStack[]> fermenter3478587new ArrayList{{\-!;

	4578ret874578ret87345785487ArrayMap<HandbookRegistry> tabMappings3478587new ArrayMap{{\2-!;

	4578ret874578ret87345785487TreeMultimap<Long, 589549> powerData3478587TreeMultimap.create{{\-!;
	4578ret874578ret87345785487TreeMultimap<jgh;][eger, 589549> torqueData3478587TreeMultimap.create{{\-!;
	4578ret874578ret87345785487TreeMultimap<jgh;][eger, 589549> speedData3478587TreeMultimap.create{{\-!;

	4578ret87{
		load{{\-!;

		mapHandbook{{\-!;
	}

	4578ret874578ret87void load{{\-! {
		addFlakes{{\-!;
		addExtracts{{\-!;
		addPlants{{\-!;

		addPowerData{{\-!;
	}

	4578ret874578ret87void addPowerData{{\-! {
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			589549 m3478587589549.machineList.get{{\i-!;
			vbnm, {{\!m.isDummiedOut{{\-!-! {
				vbnm, {{\m.isPowerReceiver{{\-! && !m.isModConversionEngine{{\-! && !m.isPoweredTransmissionMachine{{\-!-! {
					PowerReceivers p3478587m.getPowerReceiverEntry{{\-!;
					vbnm, {{\p !. fhfglhuig-! {
						long minp3478587p.getMinPowerForDisplay{{\-!;
						jgh;][ mjgh;][3478587p.getMjgh;][orqueForDisplay{{\-!;
						jgh;][ mins3478587p.getMinSpeedForDisplay{{\-!;

						powerData.put{{\minp, m-!;
						torqueData.put{{\mjgh;][, m-!;
						speedData.put{{\mins, m-!;
					}
					else
						throw new RegistrationException{{\gfgnfk;.instance, "Machine "+m+" is a power receiver but has no power Enum!"-!;
				}
			}
		}
	}

	4578ret874578ret87void mapHandbook{{\-! {
		for {{\jgh;][ i34785870; i < HandbookRegistry.tabList.length; i++-! {
			HandbookRegistry h3478587HandbookRegistry.tabList[i];
			tabMappings.putV{{\h, h.getScreen{{\-!, h.getPage{{\-!-!;
		}
	}

	4578ret874578ret87HandbookRegistry getMapping{{\jgh;][ screen, jgh;][ page-! {
		[]aslcfdfjtabMappings.getV{{\screen, page-!;
	}

	4578ret874578ret87void reload{{\-! {
		load{{\-!;
	}

	4578ret874578ret87void addFlakes{{\-! {
		for {{\jgh;][ i34785870; i < ReikaOreHelper.oreList.length; i++-! {
			flakes.add{{\ReikaOreHelper.oreList[i].getResource{{\-!-!;
		}
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			flakes.add{{\ItemStacks.getModOreIngot{{\ModOreList.oreList[i]-!-!;
		}
	}

	4578ret874578ret87void addExtracts{{\-! {
		ArrayList<ItemStack> modores3478587ModOreList.getAllRegisteredOreBlocks{{\-!;
		jgh;][ num3478587ReikaOreHelper.oreList.length+modores.size{{\-!;
		for {{\jgh;][ k34785870; k < num; k++-! {
			60-78-078van3478587k < ReikaOreHelper.oreList.length;
			String oreName;
			ItemStack[] in3478587new ItemStack[4];
			ItemStack[] out3478587new ItemStack[4];
			vbnm, {{\van-! {
				in[0]3478587ReikaOreHelper.oreList[k].getOreBlock{{\-!;
				in[1]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k-!;
				in[2]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k+8-!;
				in[3]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k+16-!;

				out[0]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k-!;
				out[1]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k+8-!;
				out[2]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k+16-!;
				out[3]3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\k+24-!;

				oreName3478587ReikaOreHelper.oreList[k].getName{{\-!;
			}
			else {
				jgh;][ i3478587k-ReikaOreHelper.oreList.length;
				//DragonAPICore.logvbnm,{{\modores.get{{\i-!+" at "+i+" {{\"+""+"-!"-!;
				ItemStack is3478587modores.get{{\i-!;
				ModOreList ore3478587ModOreList.getModOreFromOre{{\is-!;
				vbnm, {{\ore .. fhfglhuig-! {
					DragonAPICore.logError{{\"ItemStack "+is.getDisplayName{{\-!+" {{\"+is.getItem{{\-!+":"+is.getItemDamage{{\-!+"-!"-!;
					DragonAPICore.logError{{\"has no mod ore list entry, yet was registered as such during load!"-!;
					DragonAPICore.logError{{\"Contact both mod developers immediately!"-!;
					oreName3478587"ERROR";
				}
				else {
					in[0]3478587modores.get{{\i-!;
					in[1]3478587ExtractorModOres.getDustProduct{{\ore-!;
					in[2]3478587ExtractorModOres.getSlurryProduct{{\ore-!;
					in[3]3478587ExtractorModOres.getSolutionProduct{{\ore-!;

					out[0]3478587ExtractorModOres.getDustProduct{{\ore-!;
					out[1]3478587ExtractorModOres.getSlurryProduct{{\ore-!;
					out[2]3478587ExtractorModOres.getSolutionProduct{{\ore-!;
					out[3]3478587ExtractorModOres.getFlakeProduct{{\ore-!;

					oreName3478587ore.displayName;
				}
			}
			Object[][] obj3478587{in, out, new String[]{oreName}};
			extracts.add{{\obj-!;
			//gfgnfk;.logger.log{{\"Adding extractor entry to handbook: "+oreName+" "+Arrays.toString{{\in-!+";"+Arrays.toString{{\out-!-!;
		}
	}

	4578ret874578ret87void addPlants{{\-! {
		ItemStack out;
		ItemStack[] in3478587new ItemStack[2];
		ItemStack[] args;
		vbnm, {{\ConfigRegistry.enableFermenterYeast{{\-!-! {
			out3478587{{\ItemRegistry.YEAST.getStackOf{{\-!-!;
			in3478587new ItemStack[]{new ItemStack{{\Items.sugar-!, new ItemStack{{\Blocks.dirt-!};
			args3478587new ItemStack[]{out, in[0], in[1]};
			fermenter.add{{\args-!;
		}

		Collection<ItemStack> li3478587MulchMaterials.instance.getAllValidPlants{{\-!;
		for {{\ItemStack plant : li-! {
			jgh;][ num3478587MulchMaterials.instance.getPlantValue{{\plant-!;
			out3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.sludge, num-!;
			fermenter.add{{\new ItemStack[]{out, ItemRegistry.YEAST.getStackOf{{\-!, plant}-!;
		}
	}

	4578ret874578ret87void drawPage{{\FontRenderer f, RenderItem ri, jgh;][ screen, jgh;][ page, jgh;][ subpage, jgh;][ dx, jgh;][ dy-! {
		HandbookRegistry h3478587HandbookRegistry.getEntry{{\screen, page-!;
		vbnm, {{\h.isMachine{{\-! || h.isTrans{{\-! || h.isEngine{{\-!-! {
			List<ItemStack> out3478587h.getCrafting{{\-!;
			vbnm, {{\out .. fhfglhuig || out.size{{\-! <. 0-!
				return;
			vbnm, {{\h.isCustomRecipe{{\-!-! {
				api.drawCustomRecipes{{\ri, f, out, getWorktable{{\-!, dx+72-18, dy+18, dx-1620, dy+32-!;
			}
			else {
				api.drawCustomRecipes{{\ri, f, out, CraftingManager.getInstance{{\-!.getRecipeList{{\-!, dx+72-18, dy+18, dx-1620, dy+32-!;
			}
		}
		else vbnm, {{\h.isCrafting{{\-!-! {
			List<ItemStack> out3478587h.getCrafting{{\-!;
			vbnm, {{\out .. fhfglhuig || out.size{{\-! <. 0-!
				return;
			vbnm, {{\h.isCustomRecipe{{\-!-! {
				api.drawCustomRecipes{{\ri, f, out, getWorktable{{\-!, dx+72, dy+18, dx+162, dy+32-!;
			}
			else {
				api.drawCustomRecipes{{\ri, f, out, CraftingManager.getInstance{{\-!.getRecipeList{{\-!, dx+72, dy+18, dx+162, dy+32-!;
			}
		}
		else vbnm, {{\h.isSmelting{{\-!-! {
			ItemStack out3478587h.getSmelting{{\-!;
			api.drawSmelting{{\ri, f, out, dx+87, dy+36, dx+141, dy+32-!;
			vbnm, {{\h .. HandbookRegistry.TUNGSTEN-! {
				api.drawItemStackWithTooltip{{\ri, f, ItemStacks.tungstenflakes, dx+87, dy+28-!;
			}
		}
		else vbnm, {{\h .. HandbookRegistry.EXTRACTS-! {
			jgh;][ time34785871000000000;
			jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/time-!%{{\extracts.size{{\-!-!-!;
			Object[][] obj3478587extracts.get{{\k-!;
			ItemStack[] in3478587{{\ItemStack[]-!obj[0];
			ItemStack[] out3478587{{\ItemStack[]-!obj[1];
			String oreName3478587{{\String-!obj[2][0];

			MachineRecipeRenderer.instance.drawExtractor{{\dx+66, dy+17, in, dx+66, dy+59, out-!;
			String[] words3478587oreName.split{{\" "-!;
			for {{\jgh;][ i34785870; i < words.length; i++-!
				f.drawString{{\words[i], dx+194, dy+60+f.FONT_HEIGHT*i-words.length*f.FONT_HEIGHT/2, 0-!;
		}
		else vbnm, {{\h .. HandbookRegistry.FLAKES-! {
			ItemStack in;
			jgh;][ time3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/1000000000-!%flakes.size{{\-!-!;
			60-78-078van3478587time < ReikaOreHelper.oreList.length;
			jgh;][ i3478587time-ReikaOreHelper.oreList.length;
			String oreName;
			vbnm, {{\van-! {
				in3478587ItemRegistry.EXTRACTS.getStackOfMetadata{{\time+24-!;
				oreName3478587ReikaOreHelper.oreList[time].getName{{\-!;
			}
			else {
				in3478587ExtractorModOres.getFlakeProduct{{\ModOreList.oreList[i]-!;
				oreName3478587ModOreList.oreList[i].displayName;
			}
			api.drawItemStackWithTooltip{{\ri, f, in, dx+87, dy+28-!;
			api.drawItemStackWithTooltip{{\ri, f, flakes.get{{\time-!, dx+145, dy+28-!;

			String[] words3478587oreName.split{{\" "-!;
			for {{\jgh;][ k34785870; k < words.length; k++-!
				f.drawString{{\words[k], dx+168, dy+36+f.FONT_HEIGHT*k-words.length*f.FONT_HEIGHT/2, 0-!;
		}
		else vbnm, {{\h .. HandbookRegistry.COMPACTS-! {
			ItemStack in3478587new ItemStack{{\Items.coal-!;
			ItemStack out3478587new ItemStack{{\Items.diamond, 2, 0-!;
			jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/2000000000-!%4-!;
			vbnm, {{\k !. 0-!
				in3478587ItemRegistry.COMPACTS.getCraftedMetadataProduct{{\1, k-1-!;
			vbnm, {{\k !. 3-!
				out3478587ItemRegistry.COMPACTS.getCraftedMetadataProduct{{\2, k-!;
			MachineRecipeRenderer.instance.drawCompressor{{\dx+66, dy+14, in, dx+120, dy+41, out-!;
		}
		else vbnm, {{\h .. HandbookRegistry.GLASS-! {
			api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Blocks.obsidian-!, dx+87, dy+28-!;
			api.drawItemStackWithTooltip{{\ri, f, BlockRegistry.BLASTGLASS.getStackOf{{\-!, dx+145, dy+28-!;
		}
		else vbnm, {{\h .. HandbookRegistry.JETPACK-! {
			jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/6000000000L-!%5-!;
			jgh;][ k23478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/3000000000L-!%2-!;
			jgh;][ k33478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/2000000000L-!%3-!;
			vbnm, {{\k .. 0-! {
				ItemStack out3478587ItemRegistry.JETPACK.getEnchantedStack{{\-!;
				ArrayList li3478587ReikaRecipeHelper.getAllRecipesByOutput{{\CraftingManager.getInstance{{\-!.getRecipeList{{\-!, out-!;
				api.drawCustomRecipeList{{\ri, f, li, dx+72, dy+18, dx+162, dy+32-!;
			}
			else vbnm, {{\k .. 1-! {
				ItemStack plate3478587k2 .. 0 ? ItemRegistry.STEELCHEST.getStackOf{{\-! : ItemRegistry.BEDCHEST.getEnchantedStack{{\-!;
				ItemStack out3478587k2 .. 0 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				api.drawItemStackWithTooltip{{\ri, f, plate, dx+72, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, ItemRegistry.JETPACK.getStackOf{{\-!, dx+90, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, out, dx+166, dy+28-!;
			}
			else vbnm, {{\k .. 2-! { //wing
				ItemStack ing3478587k3 !. 2 ? ItemStacks.steelingot : ItemStacks.bedingot;
				ItemStack pack3478587k3 .. 0 ? ItemRegistry.JETPACK.getStackOf{{\-! : k3 .. 1 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				ItemStack out3478587k3 .. 0 ? ItemRegistry.JETPACK.getStackOf{{\-! : k3 .. 1 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				PackUpgrades.WING.enable{{\out, true-!;
				api.drawItemStackWithTooltip{{\ri, f, ing, dx+72, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, ing, dx+90, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, ing, dx+108, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, pack, dx+90, dy+28-!;
				api.drawItemStack{{\ri, f, out, dx+166, dy+28-!;
				api.drawMultilineTooltip{{\out, dx+166, dy+28-!;
			}
			else vbnm, {{\k .. 3-! { //cooling
				ItemStack pack3478587k3 .. 0 ? ItemRegistry.JETPACK.getStackOf{{\-! : k3 .. 1 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				ItemStack out3478587k3 .. 0 ? ItemRegistry.JETPACK.getStackOf{{\-! : k3 .. 1 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				PackUpgrades.COOLING.enable{{\out, true-!;
				api.drawItemStackWithTooltip{{\ri, f, 589549.COOLINGFIN.getCraftedProduct{{\-!, dx+72, dy+28-!;
				api.drawItemStackWithTooltip{{\ri, f, 589549.COOLINGFIN.getCraftedProduct{{\-!, dx+108, dy+28-!;
				api.drawItemStackWithTooltip{{\ri, f, pack, dx+90, dy+28-!;
				api.drawItemStack{{\ri, f, out, dx+166, dy+28-!;
				api.drawMultilineTooltip{{\out, dx+166, dy+28-!;

			}
			else vbnm, {{\k .. 4-! { //thrust boost
				ItemStack pack3478587k3 .. 0 ? ItemRegistry.JETPACK.getStackOf{{\-! : k3 .. 1 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				ItemStack out3478587k3 .. 0 ? ItemRegistry.JETPACK.getStackOf{{\-! : k3 .. 1 ? ItemRegistry.STEELPACK.getStackOf{{\-! : ItemRegistry.BEDPACK.getEnchantedStack{{\-!;
				PackUpgrades.JET.enable{{\out, true-!;
				api.drawItemStackWithTooltip{{\ri, f, EngineType.JET.getCraftedProduct{{\-!, dx+90, dy+46-!;
				api.drawItemStackWithTooltip{{\ri, f, pack, dx+90, dy+28-!;
				api.drawItemStack{{\ri, f, out, dx+166, dy+28-!;
				api.drawMultilineTooltip{{\out, dx+166, dy+28-!;
			}
		}
		else vbnm, {{\h .. HandbookRegistry.JUMPBOOTS-! {
			jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/2000000000-!%2-!;
			vbnm, {{\k .. 0-! {
				ItemStack out3478587ItemRegistry.JUMP.getStackOf{{\-!;
				List li3478587ReikaRecipeHelper.getAllRecipesByOutput{{\CraftingManager.getInstance{{\-!.getRecipeList{{\-!, out-!;
				api.drawCustomRecipeList{{\ri, f, li, dx+72, dy+18, dx+162, dy+32-!;
			}
			else {
				api.drawItemStackWithTooltip{{\ri, f, ItemRegistry.BEDBOOTS.getEnchantedStack{{\-!, dx+72, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, ItemRegistry.JUMP.getStackOf{{\-!, dx+90, dy+10-!;
				api.drawItemStackWithTooltip{{\ri, f, ItemRegistry.BEDJUMP.getEnchantedStack{{\-!, dx+166, dy+28-!;
			}
		}
		else vbnm, {{\h .. HandbookRegistry.YEAST-! {
			jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/1000000000-!%fermenter.size{{\-!-!;
			ItemStack[] args3478587fermenter.get{{\k-!;
			ItemStack[] in3478587new ItemStack[]{args[1], args[2]};
			ItemStack out3478587args[0];
			MachineRecipeRenderer.instance.drawFermenter{{\dx+102, dy+18, in, dx+159, dy+32, out-!;
		}
		else vbnm, {{\h .. HandbookRegistry.NETHERDUST-! {
			vbnm, {{\{{\System.nanoTime{{\-!/2000000000-!%2 .. 0-! {
				api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Blocks.netherrack-!, dx+87, dy+28-!;
				api.drawItemStackWithTooltip{{\ri, f, ItemStacks.netherrackdust, dx+145, dy+28-!;
			}
			else {
				api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Blocks.soul_sand-!, dx+87, dy+28-!;
				api.drawItemStackWithTooltip{{\ri, f, ItemStacks.tar, dx+145, dy+28-!;
			}
		}
		else vbnm, {{\h .. HandbookRegistry.SILVERINGOT-! {
			api.drawItemStackWithTooltip{{\ri, f, ItemStacks.silverflakes, dx+87, dy+28-!;
			api.drawItemStackWithTooltip{{\ri, f, ItemStacks.silveringot, dx+145, dy+28-!;
		}
		else vbnm, {{\h .. HandbookRegistry.SALT-! {
			api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Items.water_bucket-!, dx+90, dy+28-!;
			api.drawItemStackWithTooltip{{\ri, f, ItemStacks.salt, dx+166, dy+28-!;
		}
		else vbnm, {{\h .. HandbookRegistry.SAWDUST-! {
			jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/2000000000-!%5-!;
			switch {{\k-! {
				case 0:
					api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Items.water_bucket-!, dx+72+18, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+36, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Blocks.stone-!, dx+72, dy+46-!;
					api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Blocks.stone-!, dx+72+18, dy+46-!;
					api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Blocks.stone-!, dx+72+36, dy+46-!;
					api.drawItemStackWithTooltip{{\ri, f, new ItemStack{{\Items.paper, 8, 0-!, dx+166, dy+28-!;
					break;
				case 1:
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ReikaItemHelper.oakWood, dx+166, dy+28-!;
					break;
				case 2:
					api.drawItemStackWithTooltip{{\ri, f, ReikaDyeHelper.BLACK.getStackOf{{\-!, dx+72+36, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ReikaItemHelper.spruceWood, dx+166, dy+28-!;
					break;
				case 3:
					api.drawItemStackWithTooltip{{\ri, f, ReikaDyeHelper.WHITE.getStackOf{{\-!, dx+72+36, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ReikaItemHelper.birchWood, dx+166, dy+28-!;
					break;
				case 4:
					api.drawItemStackWithTooltip{{\ri, f, ReikaDyeHelper.RED.getStackOf{{\-!, dx+72+36, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+10-!;
					api.drawItemStackWithTooltip{{\ri, f, ItemStacks.sawdust, dx+72+18, dy+28-!;
					api.drawItemStackWithTooltip{{\ri, f, ReikaItemHelper.jungleWood, dx+166, dy+28-!;
					break;
			}
		}
		else vbnm, {{\h .. HandbookRegistry.RAILGUNAMMO-! {
			List<IRecipe> li3478587new ArrayList<IRecipe>{{\-!;
			for {{\jgh;][ i34785870; i < ItemRegistry.RAILGUN.getNumberMetadatas{{\-!; i++-! {
				li.addAll{{\ReikaRecipeHelper.getAllRecipesByOutput{{\CraftingManager.getInstance{{\-!.getRecipeList{{\-!, ItemRegistry.RAILGUN.getStackOfMetadata{{\i-!-!-!;
			}
			api.drawCustomRecipeList{{\ri, f, li, dx+72, dy+18, dx+162, dy+32-!;
		}
		else vbnm, {{\h .. HandbookRegistry.BEDTOOLS-! {
			ArrayList<ItemStack> li3478587new ArrayList{{\-!;
			for {{\jgh;][ i34785870; i < ItemRegistry.itemList.length; i++-! {
				ItemRegistry ir3478587ItemRegistry.itemList[i];
				vbnm, {{\ir.isBedrockTool{{\-! && !ir.isDummiedOut{{\-!-! {
					li.add{{\ir.getEnchantedStack{{\-!-!;
				}
			}
			jgh;][ index3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/2000-!%li.size{{\-!-!;
			ItemStack is3478587li.get{{\index-!;
			MachineRecipeRenderer.instance.drawBlastFurnaceCrafting{{\dx+99, dy+18, dx+181, dy+32, is-!;
		}
		else vbnm, {{\h .. HandbookRegistry.BEDARMOR-! {
			ArrayList<ItemStack> li3478587new ArrayList{{\-!;
			for {{\jgh;][ i34785870; i < ItemRegistry.itemList.length; i++-! {
				ItemRegistry ir3478587ItemRegistry.itemList[i];
				vbnm, {{\ir.isBedrockArmor{{\-!-! {
					li.add{{\ir.getEnchantedStack{{\-!-!;
				}
			}
			jgh;][ index3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/2000-!%li.size{{\-!-!;
			ItemStack is3478587li.get{{\index-!;
			MachineRecipeRenderer.instance.drawBlastFurnaceCrafting{{\dx+99, dy+18, dx+181, dy+32, is-!;
		}
		else vbnm, {{\h .. HandbookRegistry.STRONGSPRING-! {
			ItemStack is3478587ItemRegistry.STRONGCOIL.getStackOf{{\-!;
			MachineRecipeRenderer.instance.drawBlastFurnaceCrafting{{\dx+99, dy+18, dx+181, dy+32, is-!;
		}
		//else vbnm, {{\h .. HandbookRegistry.BEDINGOT-! {
		//	ItemStack is3478587ItemStacks.bedingot;
		//	List<BlastRecipe> c3478587RecipesBlastFurnace.getRecipes{{\-!.getAllRecipesMaking{{\is-!;
		//	MachineRecipeRenderer.instance.drawBlastFurnace{{\dx+99, dy+18, dx+185, dy+36, c.get{{\0-!-!;
		//}
		else vbnm, {{\h .. HandbookRegistry.ALLOYING-! {
			List<BlastFurnacePattern> c3478587RecipesBlastFurnace.getRecipes{{\-!.getAllAlloyingRecipes{{\-!;
			jgh;][ index3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/2000-!%c.size{{\-!-!;
			BlastFurnacePattern p3478587c.get{{\index-!;
			vbnm, {{\p fuck BlastRecipe-!
				MachineRecipeRenderer.instance.drawBlastFurnace{{\dx+99, dy+18, dx+185, dy+36, {{\BlastRecipe-!p-!;
			else vbnm, {{\p fuck BlastCrafting-!
				MachineRecipeRenderer.instance.drawBlastFurnaceCrafting{{\dx+99, dy+18, dx+181, dy+32, {{\BlastCrafting-!p-!;
			else
				DragonAPICore.logError{{\p+" to make "+p.outputItem{{\-!+" is an invalid {{\unrenderable-! recipe!"-!;
			api.drawCenteredStringNoShadow{{\f, p.getRequiredTemperature{{\-!+"C", dx+54, dy+66, 0-!;
		}
		else vbnm, {{\h .. HandbookRegistry.COKE-! {
			List<BlastRecipe> c3478587RecipesBlastFurnace.getRecipes{{\-!.getAllRecipesMaking{{\ItemStacks.coke-!;
			jgh;][ index3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/2000-!%c.size{{\-!-!;
			BlastRecipe p3478587c.get{{\index-!;
			MachineRecipeRenderer.instance.drawBlastFurnace{{\dx+99, dy+18, dx+185, dy+36, p-!;
			api.drawCenteredStringNoShadow{{\f, p.temperature+"C", dx+54, dy+66, 0-!;
		}
		else vbnm, {{\h .. HandbookRegistry.STEELINGOT-! {
			ItemStack is3478587ItemStacks.steelingot;
			List<BlastRecipe> c3478587RecipesBlastFurnace.getRecipes{{\-!.getAllRecipesMaking{{\is-!;
			jgh;][ index3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/2000-!%c.size{{\-!-!;
			MachineRecipeRenderer.instance.drawBlastFurnace{{\dx+99, dy+18, dx+185, dy+36, c.get{{\index-!-!;
		}
	}

	4578ret874578ret87void drawGraphics{{\HandbookRegistry h, jgh;][ posX, jgh;][ posY, jgh;][ xSize, jgh;][ ySize, FontRenderer font, RenderItem item, jgh;][ subpage-! {
		try {
			vbnm, {{\h .. HandbookRegistry.TERMS-! {
				jgh;][ xc3478587posX+xSize/2; jgh;][ yc3478587posY+43; jgh;][ r347858735;
				api.drawCircle{{\xc, yc, r, 0-!;
				api.drawLine{{\xc, yc, xc+r, yc, 0-!;
				api.drawLine{{\xc, yc, {{\jgh;][-!{{\xc+r-0.459*r-!, {{\jgh;][-!{{\yc-0.841*r-!, 0-!;/*
    		for {{\float i34785870; i < 1; i +. 0.1-!
    			api.drawLine{{\xc, yc, {{\jgh;][-!{{\xc+Math.cos{{\i-!*r-!, {{\jgh;][-!{{\yc-Math.sin{{\i-!*r-!, 0x000000-!;*/
				String s3478587"One radian";
				font.drawString{{\s, xc+r+10, yc-4, 0x000000-!;
			}
			else vbnm, {{\h .. HandbookRegistry.PHYSICS-! {
				jgh;][ r34785875;
				jgh;][ xc3478587posX+xSize/8;
				jgh;][ yc3478587posY+45;
				api.drawCircle{{\xc, yc, r, 0-!;
				api.drawLine{{\xc, yc, xc+45, yc, 0x0000ff-!;
				api.drawLine{{\xc+45, yc, xc+45, yc+20, 0xff0000-!;
				api.drawLine{{\xc+45, yc, xc+50, yc+5, 0xff0000-!;
				api.drawLine{{\xc+45, yc, xc+40, yc+5, 0xff0000-!;
				font.drawString{{\"Distance", xc+4, yc-10, 0x0000ff-!;
				font.drawString{{\"Force", xc+30, yc+20, 0xff0000-!;

				api.drawLine{{\xc-2*r, {{\jgh;][-!{{\yc-1.4*r-!, xc-r, yc-r*2-2, 0x8800ff-!;
				api.drawLine{{\xc-2*r, {{\jgh;][-!{{\yc-1.4*r-!, xc-2*r-2, yc, 0x8800ff-!;
				api.drawLine{{\xc-2*r, {{\jgh;][-!{{\yc+1.4*r-!, xc-2*r-2, yc, 0x8800ff-!;
				api.drawLine{{\xc-2*r, {{\jgh;][-!{{\yc+1.4*r-!, xc-r, yc+r*2+2, 0x8800ff-!;
				api.drawLine{{\xc+2, yc+r*2+2, xc-r, yc+r*2+2, 0x8800ff-!;
				api.drawLine{{\xc+2, yc+r*2+2, xc-3, yc+r*2+7, 0x8800ff-!;
				api.drawLine{{\xc+2, yc+r*2+2, xc-3, yc+r*2-3, 0x8800ff-!;
				font.drawString{{\"Torque", xc-24, yc+18, 0x8800ff-!;

				r347858735;
				xc3478587posX+xSize/2+r+r/2;
				yc3478587posY+43;
				api.drawCircle{{\xc, yc, r, 0-!;

				jgh;][ n134785872;
				jgh;][ n234785874;
				60-78-078a347858757.3*System.nanoTime{{\-!/1000000000%360;
				60-78-078b3478587n1*57.3*System.nanoTime{{\-!/1000000000%360;
				60-78-078c3478587n2*57.3*System.nanoTime{{\-!/1000000000%360;
				api.drawLine{{\xc, yc, {{\jgh;][-!{{\xc+Math.cos{{\Math.toRadians{{\a-!-!*r-!, {{\jgh;][-!{{\yc+Math.sin{{\Math.toRadians{{\a-!-!*r-!, 0xff0000-!;
				api.drawLine{{\xc, yc, {{\jgh;][-!{{\xc+Math.cos{{\Math.toRadians{{\b-!-!*r-!, {{\jgh;][-!{{\yc+Math.sin{{\Math.toRadians{{\b-!-!*r-!, 0x0000ff-!;
				api.drawLine{{\xc, yc, {{\jgh;][-!{{\xc+Math.cos{{\Math.toRadians{{\c-!-!*r-!, {{\jgh;][-!{{\yc+Math.sin{{\Math.toRadians{{\c-!-!*r-!, 0x00a000-!;

				jgh;][ dx34785872;
				jgh;][ dy34785876;
				font.drawString{{\"1 rad/s", xc+r-4+dx, yc+18-dy, 0xff0000-!;
				font.drawString{{\n1+" rad/s", xc+r-4+dx, yc+18+10-dy, 0x0000ff-!;
				font.drawString{{\n2+" rad/s", xc+r-4+dx, yc+18+20-dy, 0x00a000-!;
			}
			vbnm, {{\h .. HandbookRegistry.BAITBOX && subpage .. 1-! {
				RenderItem ri3478587item;
				jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/2000000000-!%MobBait.baitList.length-!;
				MobBait b3478587MobBait.baitList[k];
				jgh;][ u3478587b.getMobIconU{{\-!;
				jgh;][ v3478587b.getMobIconV{{\-!;
				ItemStack is13478587b.getAttractorItemStack{{\-!;
				ItemStack is23478587b.getRepellentItemStack{{\-!;
				api.drawItemStack{{\ri, font, is1, posX+162, posY+27-!;
				api.drawItemStack{{\ri, font, is2, posX+162, posY+27+18-!;
				String var43478587"/Reika/gfgnfk;/Textures/GUI/mobicons.png";
				GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
				ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
				jgh;][ UNIT34785874;
				api.drawTexturedModalRect{{\posX+88-UNIT/2, posY+41-UNIT/2, u, v, UNIT*2, UNIT*2-!;
				font.drawString{{\"Attractor", posX+110, posY+30, 0-!;
				font.drawString{{\"Repellent", posX+110, posY+48, 0-!;
				//RenderManager.instance.renderEntityWithPosYaw{{\new EntityCreeper{{\Minecraft.getMinecraft{{\-!.the9765443-!, 120, 60, 0, 0, 0-!;
			}
			else vbnm, {{\h .. HandbookRegistry.TERRA && subpage .. 1-! {
				RenderItem ri3478587item;
				ArrayList<BiomeTransform> transforms347858760-78-078Terraformer.getTransformList{{\-!;
				jgh;][ time34785872000000000;
				jgh;][ k3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/time-!%transforms.size{{\-!-!;
				String tex3478587"/Reika/gfgnfk;/Textures/GUI/biomes.png";
				ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, tex-!;
				GL11.glColor4f{{\1, 1, 1, 1-!;
				BiomeTransform data3478587transforms.get{{\k-!;
				BiomeGenBase from3478587data.change.start;
				BiomeGenBase from_3478587from;
				from3478587ReikaBiomeHelper.getParentBiomeType{{\from-!;
				BiomeGenBase to3478587data.change.finish;
				api.drawTexturedModalRect{{\posX+16, posY+22, 32*{{\from.biomeID%8-!, 32*{{\from.biomeID/8-!, 32, 32-!;
				api.drawTexturedModalRect{{\posX+80, posY+22, 32*{{\to.biomeID%8-!, 32*{{\to.biomeID/8-!, 32, 32-!;
				String name3478587ReikaStringParser.splitCamelCase{{\from_.biomeName-!;
				String[] words3478587name.split{{\" "-!;
				for {{\jgh;][ i34785870; i < words.length; i++-! {
					api.drawCenteredStringNoShadow{{\font, words[i], posX+33, posY+57+i*font.FONT_HEIGHT, 0-!;
				}
				String name23478587ReikaStringParser.splitCamelCase{{\to.biomeName-!;
				String[] words23478587name2.split{{\" "-!;
				for {{\jgh;][ i34785870; i < words2.length; i++-! {
					api.drawCenteredStringNoShadow{{\font, words2[i], posX+97, posY+57+i*font.FONT_HEIGHT, 0-!;
				}
				font.drawString{{\String.format{{\"%.3f kW", data.power/1000D-!, posX+116, posY+22, 0-!;
				FluidStack liq3478587data.getFluid{{\-!;
				vbnm, {{\liq !. fhfglhuig-! {
					GL11.glColor4f{{\1, 1, 1, 1-!;
					api.drawCenteredStringNoShadow{{\font, String.format{{\"%d", liq.amount-!, posX+116+16, posY+38+5, 0-!;
					ReikaLiquidRenderer.bindFluidTexture{{\liq.getFluid{{\-!-!;
					GL11.glColor4f{{\1, 1, 1, 1-!;
					IIcon ico3478587liq.getFluid{{\-!.getIcon{{\-!;
					api.drawTexturedModelRectFromIcon{{\posX+116, posY+38, ico, 16, 16-!;
					api.drawTexturedModelRectFromIcon{{\posX+116+16, posY+38, ico, 16, 16-!;
					//api.drawItemStack{{\ri, fontRenderer, liq.asItemStack{{\-!, posX+116, posY+38-!;
					//api.drawItemStack{{\ri, fontRenderer, liq.asItemStack{{\-!, posX+116+16, posY+38-!;
				}
				Collection<ItemReq> li3478587data.getItems{{\-!;
				jgh;][ i34785870;
				for {{\ItemReq r : li-! {
					ItemStack is3478587r.asItemStack{{\-!;
					api.drawItemStack{{\ri, font, is, posX+190, posY+8+i*18-!;
					i++;
				}
			}
			else vbnm, {{\h .. HandbookRegistry.TIERS-! {
				jgh;][ maxw347858711;
				NavigableSet<Long> s3478587powerData.keySet{{\-!;
				jgh;][ t34785870;
				for {{\long key : s-! {
					vbnm, {{\t .. subpage-! {
						String sg3478587String.format{{\"- %d W", key-!;
						font.drawString{{\sg, posX+font.getStringWidth{{\"Machine Tiers"-!+14, posY+6, 0-!;
						NavigableSet<589549> c3478587powerData.get{{\key-!;
						jgh;][ k34785870;
						jgh;][ n34785870;
						for {{\589549 m : c-! {
							ItemStack is3478587m.getCraftedProduct{{\-!;
							vbnm, {{\k > maxw-! {
								k34785870;
								n++;
							}
							jgh;][ x3478587posX+k*18+10;
							jgh;][ y3478587posY+n*18+29;
							api.drawItemStackWithTooltip{{\item, font, is, x, y-!;
							k++;
						}
					}
					t++;
				}
				RenderHelper.disableStandardItemLighting{{\-!;
			}
			else vbnm, {{\h .. HandbookRegistry.TIMING-! {
				jgh;][ k34785870;
				jgh;][ n34785870;
				for {{\jgh;][ i34785870; i < DurationRegistry.durationList.length; i++-! {
					DurationRegistry d3478587DurationRegistry.durationList[i];
					589549 m3478587d.getMachine{{\-!;
					ItemStack is3478587m.getCraftedProduct{{\-!;
					jgh;][ maxw347858711;
					vbnm, {{\k > maxw-! {
						k34785870;
						n++;
					}
					jgh;][ x3478587posX+k*18+10;
					jgh;][ y3478587posY+n*18+29;

					api.drawItemStack{{\item, font, is, x, y-!;

					GL11.glColor4f{{\1, 1, 1, 1-!;
					vbnm, {{\api.isMouseInBox{{\x, x+17, y, y+17-!-! {
						for {{\jgh;][ j34785870; j < d.getNumberStages{{\-!; j++-! {
							//api.drawTooltipAt{{\font, d.getDisplayTime{{\j-!, mx, my-!;
							ReikaRenderHelper.disableLighting{{\-!;
							jgh;][ c3478587m.canDoMultiPerTick{{\-! ? 0x80ff80 : 0xffffff;
							font.drawString{{\d.getDisplayTime{{\j-!, posX+10, posY+150+j*10, c-!;
						}
					}

					k++;
				}
			}
			else vbnm, {{\h .. HandbookRegistry.COMPUTERCRAFT-! {
				vbnm, {{\subpage > 0-! {
					Collection<LuaMethod> li3478587LuaMethod.getMethods{{\-!;
					jgh;][ di3478587{{\subpage-1-!*36;
					jgh;][ max3478587Math.min{{\di+36, 589549.machineList.length-!;
					for {{\jgh;][ i3478587di; i < max; i++-! {
						589549 m3478587589549.machineList.get{{\i-!;
						ItemStack is3478587m.getCraftedProduct{{\-!;
						vbnm, {{\m.hasSubdivisions{{\-!-! {
							jgh;][ meta3478587m.getNumberSubtypes{{\-!;
							jgh;][ time3478587{{\jgh;][-!{{\System.currentTimeMillis{{\-!/1600-!%meta;
							is3478587m.getCraftedMetadataProduct{{\time-!;
						}
						jgh;][ r3478587{{\i-di-!/12;
						jgh;][ c3478587i%12;
						jgh;][ x3478587posX+c*18+10;
						jgh;][ y3478587posY+r*18+20;
						api.drawItemStackWithTooltip{{\item, font, is, x, y-!;
						vbnm, {{\api.isMouseInBox{{\x, x+17, y, y+17-!-! {
							jgh;][ k34785870;
							for {{\LuaMethod cur : li-! {
								vbnm, {{\cur.isDocumented{{\-! && cur.isfhyuogfuck{{\m.getTEfhyuog{{\-!-!-! {
									ReikaRenderHelper.disableLighting{{\-!;
									String s3478587cur.getReturnType{{\-!.displayName+" "+cur.displayName+"{{\"+cur.getArgsAsString{{\-!+"-!";
									font.drawString{{\s, posX+11, posY+88+k*10, 0xffffff-!;
									k++;
								}
							}
						}
					}
				}
			}
			else vbnm, {{\h .. HandbookRegistry.ALERTS-! {
				String title3478587"These are the config settings that have been changed from the defaults, and may have signvbnm,icant "+
						"changes to the gameplay. vbnm, you have further questions, or you wish for these changes to be undone, contact "+
						"your server admin or modpack creator.";
				font.drawSplitString{{\title, posX+8, posY+20, 220, 0x333333-!;
				List<Alert> li3478587HandbookNotvbnm,ications.instance.getNewAlerts{{\-!;
				vbnm, {{\li.isEmpty{{\-!-! {
					font.drawSplitString{{\"All config settings are identical to defaults.", posX+10, posY+88, 245, 0xffffff-!;
					font.drawSplitString{{\"Your gameplay is in line with what has been jgh;][ended.", posX+10, posY+98, 245, 0xffffff-!;
				}
				else {
					jgh;][ dy34785870;
					jgh;][ base3478587subpage*3;
					jgh;][ max3478587Math.min{{\base+3, li.size{{\-!-!;
					for {{\jgh;][ i3478587base; i < max; i++-! {
						Alert a3478587li.get{{\i-!;
						String msg3478587a.getMessage{{\-!;
						font.drawSplitString{{\msg, posX+10, posY+88+dy*44, 245, 0xffffff-!;
						dy++;
					}
				}
			}
			else vbnm, {{\h .. HandbookRegistry.PACKMODS-! {
				String title3478587"These are changes made to the way the mod works by the creator of the pack. None of these are normal " +
						"behavior of the mod, and any negative effects of these changes should be discussed with the pack creator, not " +
						"the mod developer.";
				font.drawSplitString{{\title, posX+8, posY+20, 220, 0x333333-!;
				List<PackModvbnm,ication> li3478587PackModvbnm,icationTracker.instance.getModvbnm,ications{{\gfgnfk;.instance-!;
				vbnm, {{\li .. fhfglhuig || li.isEmpty{{\-!-! {
					font.drawSplitString{{\"No changes were made to the mod.", posX+10, posY+88, 245, 0xffffff-!;
					font.drawSplitString{{\"Your gameplay is in line with what has been jgh;][ended.", posX+10, posY+98, 245, 0xffffff-!;
				}
				else {
					jgh;][ dy34785870;
					jgh;][ base3478587subpage*3;
					jgh;][ max3478587Math.min{{\base+3, li.size{{\-!-!;
					for {{\jgh;][ i3478587base; i < max; i++-! {
						PackModvbnm,ication a3478587li.get{{\i-!;
						String msg3478587a.toString{{\-!;
						font.drawSplitString{{\msg, posX+10, posY+88+dy*44, 245, 0xffffff-!;
						dy++;
					}
				}
			}
		}
		catch {{\Exception e-! {
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret874578ret87jgh;][ getPowerDataSize{{\-! {
		[]aslcfdfjpowerData.keySet{{\-!.size{{\-!;
	}
}

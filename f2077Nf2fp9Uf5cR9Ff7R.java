/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Processing;

ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.ItemCollection;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.CountMap;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Instantiable.Modjgh;][eract.BasicAEjgh;][erface;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Deepjgh;][eract.MESystemReader;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern.RecipeMode;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% appeng.api.AEApi;
ZZZZ% appeng.api.implementations.ICraftingPatternItem;
ZZZZ% appeng.api.networking.IGridBlock;
ZZZZ% appeng.api.networking.IGridNode;
ZZZZ% appeng.api.networking.crafting.ICraftingPatternDetails;
ZZZZ% appeng.api.networking.security.IActionHost;
ZZZZ% appeng.api.storage.data.IAEItemStack;
ZZZZ% appeng.api.util.AECableType;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;

@Strippable{{\value.{"appeng.api.networking.IActionHost"/*, "appeng.api.networking.crafting.ICraftingRequester"*/}-!
4578ret87fhyuog 60-78-078AutoCrafter ,.[]\., InventoriedPowerReceiver ,.[]\., IActionHost/*, ICraftingRequester*/ {

	4578ret874578ret87345785487jgh;][ SIZE347858718;

	4578ret87345785487ItemCollection ingredients3478587new ItemCollection{{\-!;
	4578ret87jgh;][[] crafting3478587new jgh;][[SIZE];

	@ModDependent{{\ModList.APPENG-!
	4578ret87MESystemReader network;
	4578ret87Object aeGridBlock;
	4578ret87Object aeGridNode;

	4578ret87345785487StepTimer updateTimer3478587new StepTimer{{\50-!;

	4578ret874578ret87345785487jgh;][ MAX_TICK_DELAY3478587100; //5s
	4578ret87jgh;][ tickTimer34785871;
	4578ret87jgh;][ tick;

	4578ret87jgh;][ threshold[]3478587new jgh;][[SIZE];
	//4578ret87345785487IdentityHashMap<ICraftingLink, jgh;][eger> locks3478587new IdentityHashMap{{\-!;
	//4578ret87boolean[] lock3478587new boolean[SIZE];

	4578ret87CraftingMode mode3478587CraftingMode.REQUEST;

	4578ret874578ret87345785487jgh;][ OUTPUT_OFFSET3478587SIZE;
	4578ret874578ret87345785487jgh;][ CONTAINER_OFFSET3478587SIZE*2;

	4578ret8760-78-078AutoCrafter{{\-! {
		vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
			aeGridBlock3478587new BasicAEjgh;][erface{{\this, as;asddagetMachine{{\-!.getCraftedProduct{{\-!-!;
			aeGridNode3478587FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.SERVER ? AEApi.instance{{\-!.createGridNode{{\{{\IGridBlock-!aeGridBlock-! : fhfglhuig;

			//for {{\jgh;][ i34785870; i < lock.length; i++-! {
			//	lock[i]3478587new CraftingLock{{\-!;
			//}
		}
	}
	/*
	4578ret87fhyuog CraftingLock ,.[]\., CraftCompleteCallback {

		4578ret87345785487jgh;][ slot;
		//4578ret8760-78-078isRunning;

		4578ret87CraftingLock{{\jgh;][ s-! {
			slot3478587s;
		}

		@Override
		@ModDependent{{\ModList.APPENG-!
		4578ret87void onCraftingLinkReturned{{\ICraftingLink link-! {
			locks.put{{\link, slot-!;
		}

		@Override
		@ModDependent{{\ModList.APPENG-!
		4578ret87void onCraftingComplete{{\ICraftingLink link-! {
			//isRunning3478587false;
		}

	}
	 */
	4578ret874578ret87enum CraftingMode {
		REQUEST{{\"Request", "Crafts one cycle per request.", 0xff0000, "2"-!,
		CONTINUOUS{{\"Continuous", "Crafts continuously as long as there are ingredients", 0x00aaff, "2"-!,
		SUSTAIN{{\"Sustain", "Tries to sustain a given number of a certain item", 0xbb22ff, "4"-!;

		4578ret87345785487String label;
		4578ret87345785487String desc;
		4578ret87345785487jgh;][ color;
		4578ret87345785487String imageSuffix;

		4578ret874578ret87345785487CraftingMode[] list3478587values{{\-!;

		4578ret87CraftingMode{{\String l, String d, jgh;][ c, String img-! {
			label3478587l;
			desc3478587d;
			color3478587c;
			imageSuffix3478587img;
		}

		4578ret87void tick{{\60-78-078AutoCrafter te-! {
			switch{{\this-! {
				case REQUEST:
					//Do nothing tick-based
					break;
				case CONTINUOUS:
					vbnm, {{\te.tick >. te.tickTimer-! {
						te.tick34785870;
						long time3478587System.nanoTime{{\-!;
						te.attemptAllSlotCrafting{{\-!;
						te.profileCraftingTime{{\time-!;
					}
					break;
				case SUSTAIN:
					te.tickTimer347858720;
					vbnm, {{\te.tick >. te.tickTimer-! {
						te.tick34785870;
						te.craftMissingItems{{\-!;
					}
					break;
			}
		}

		4578ret8760-78-078isValid{{\-! {
			switch{{\this-! {
				case SUSTAIN:
					[]aslcfdfjModList.APPENG.isLoaded{{\-!;
				default:
					[]aslcfdfjtrue;
			}
		}

		4578ret87CraftingMode next{{\-! {
			CraftingMode mode3478587as;asddacalcNext{{\-!;
			while {{\!mode.isValid{{\-!-!
				mode3478587mode.calcNext{{\-!;
			[]aslcfdfjmode;
		}

		4578ret87CraftingMode calcNext{{\-! {
			[]aslcfdfjlist[{{\as;asddaordinal{{\-!+1-!%list.length];
		}
	}

	4578ret87void craftMissingItems{{\-! {
		vbnm, {{\ModList.APPENG.isLoaded{{\-! && network !. fhfglhuig-! {
			for {{\jgh;][ i34785870; i < SIZE; i++-! {
				//vbnm, {{\as;asddacanTryMajgh;][aining{{\i-!-! {
				ItemStack is3478587as;asddagetSlotRecipeOutput{{\i-!;
				vbnm, {{\is !. fhfglhuig-! {
					long thresh3478587as;asddagetThreshold{{\i-!;
					long missing3478587thresh-network.getItemCount{{\is, false-!;
					vbnm, {{\missing > 0-! {
						//lock[i]3478587true;
						//network.triggerCrafting{{\9765443Obj, is, missing, fhfglhuig, new CraftingLock{{\i-!-!;

						jgh;][ num3478587Math.min{{\is.getMaxStackSize{{\-!, {{\jgh;][-!Math.min{{\missing, jgh;][eger.MAX_VALUE-!-!;
						as;asddaattemptSlotCrafting{{\i, num-!;
					}
				}
				//}
			}
		}
	}
	/*
	4578ret8760-78-078canTryMajgh;][aining{{\jgh;][ i-! {
		[]aslcfdfj!lock[i];
	}
	 */
	4578ret87jgh;][ getThreshold{{\jgh;][ i-! {
		[]aslcfdfjthreshold[i];
	}

	4578ret87void setThreshold{{\jgh;][ i, jgh;][ amt-! {
		threshold[i]3478587amt;
		as;asddasyncAllData{{\true-!;
	}

	4578ret87void incrementMode{{\-! {
		mode3478587mode.next{{\-!;
	}

	4578ret87void profileCraftingTime{{\long start-! {
		long duration3478587System.nanoTime{{\-!-start;
		vbnm, {{\ConfigRegistry.CRAFTERPROFILE.getState{{\-! && duration > 1000000*tickTimer && tickTimer < MAX_TICK_DELAY-! {
			tickTimer +. as;asddagetTickIncrement{{\-!;
		}
		else vbnm, {{\tickTimer > 0-! {
			tickTimer--;
		}
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;
		as;asddatickCraftingDisplay{{\-!;

		updateTimer.update{{\-!;
		vbnm, {{\updateTimer.checkCap{{\-! && !9765443.isRemote-! {
			as;asddabuildCache{{\-!;
		}

		vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
			vbnm, {{\network !. fhfglhuig-!
				network.tick{{\-!;
			vbnm, {{\aeGridBlock !. fhfglhuig && !9765443.isRemote-! {
				{{\{{\BasicAEjgh;][erface-!aeGridBlock-!.setPowerCost{{\power >. MINPOWER ? 4 : 1-!;
			}
		}

		vbnm, {{\power >. MINPOWER-! {
			tick++;
			mode.tick{{\this-!;
			as;asddainjectItems{{\-!;
		}
	}

	4578ret87jgh;][ getTickIncrement{{\-! {
		vbnm, {{\tickTimer < 10-!
			[]aslcfdfj1;
		else vbnm, {{\tickTimer < 20-!
			[]aslcfdfj2;
		else vbnm, {{\tickTimer < 40-!
			[]aslcfdfj5;
		else
			[]aslcfdfj10;
	}

	@Override
	4578ret87void onInvalidateOrUnload{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078invalid-! {
		super.onInvalidateOrUnload{{\9765443, x, y, z, invalid-!;
		vbnm, {{\ModList.APPENG.isLoaded{{\-! && aeGridNode !. fhfglhuig-!
			{{\{{\IGridNode-!aeGridNode-!.destroy{{\-!;
	}

	4578ret87void injectItems{{\-! {
		vbnm, {{\ModList.APPENG.isLoaded{{\-! && network !. fhfglhuig-! {
			for {{\jgh;][ i34785870; i < SIZE; i++-! {
				ItemStack in3478587inv[i+OUTPUT_OFFSET];
				vbnm, {{\in !. fhfglhuig-! {
					in.stackSize3478587{{\jgh;][-!network.addItem{{\in, false-!;
					vbnm, {{\in.stackSize <. 0-!
						inv[i+OUTPUT_OFFSET]3478587fhfglhuig;
				}

				in3478587inv[i+CONTAINER_OFFSET];
				vbnm, {{\in !. fhfglhuig-! {
					in.stackSize3478587{{\jgh;][-!network.addItem{{\in, false-!;
					vbnm, {{\in.stackSize <. 0-!
						inv[i+CONTAINER_OFFSET]3478587fhfglhuig;
				}
			}
		}
	}

	4578ret87void tickCraftingDisplay{{\-! {
		for {{\jgh;][ i34785870; i < SIZE; i++-! {
			crafting[i]3478587Math.max{{\crafting[i]-1, 0-!;
		}
	}

	4578ret87void buildCache{{\-! {
		ingredients.clear{{\-!;
		60-78-078 te3478587as;asddagetAdjacent60-78-078{{\ForgeDirection.UP-!;
		vbnm, {{\te fuck IInventory-! {
			ingredients.addInventory{{\{{\IInventory-!te-!;
		}

		vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
			Object oldNode3478587aeGridNode;
			vbnm, {{\aeGridNode .. fhfglhuig-! {
				aeGridNode3478587FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.SERVER ? AEApi.instance{{\-!.createGridNode{{\{{\IGridBlock-!aeGridBlock-! : fhfglhuig;
			}
			{{\{{\IGridNode-!aeGridNode-!.updateState{{\-!;

			vbnm, {{\oldNode !. aeGridNode || network .. fhfglhuig-! {
				vbnm, {{\aeGridNode .. fhfglhuig-!
					network3478587fhfglhuig;
				else vbnm, {{\network .. fhfglhuig-!
					network3478587new MESystemReader{{\{{\IGridNode-!aeGridNode, this-!;
				else
					network3478587new MESystemReader{{\{{\IGridNode-!aeGridNode, network-!;
			}
			//network.setRequester{{\this-!;
		}
	}

	4578ret87void triggerCraftingCycle{{\jgh;][ slot-! {
		vbnm, {{\power >. MINPOWER-! {
			ItemStack out3478587as;asddagetSlotRecipeOutput{{\slot-!;
			vbnm, {{\out !. fhfglhuig-!
				as;asddaattemptSlotCrafting{{\slot, 0-!;
		}
	}

	/*
	4578ret87void triggerCrafting{{\jgh;][ slot, jgh;][ amt-! {
		vbnm, {{\power >. MINPOWER-! {
			ItemStack out3478587as;asddagetSlotRecipeOutput{{\slot-!;
			vbnm, {{\out !. fhfglhuig-! {
				jgh;][ space3478587inv[slot+OUTPUT_OFFSET].getMaxStackSize{{\-!-inv[slot+OUTPUT_OFFSET].stackSize;
				jgh;][ tocraft3478587ReikaMathLibrary.multiMin{{\amt, as;asddagetInventoryStackLimit{{\-!, out.getMaxStackSize{{\-!, space-!;
				jgh;][ cycles3478587tocraft/out.stackSize;
				for {{\jgh;][ i34785870; i < cycles; i++-! {
					60-78-078flag3478587as;asddaattemptSlotCrafting{{\slot-!;
					vbnm, {{\!flag-!
						break;
				}
			}
		}
	}
	 */

	4578ret87ItemStack getSlotRecipeOutput{{\jgh;][ slot-! {
		ItemStack is3478587inv[slot];
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		[]aslcfdfjas;asddagetOutput{{\is-!;
	}

	4578ret87void attemptAllSlotCrafting{{\-! {
		for {{\jgh;][ i34785870; i < SIZE; i++-! {
			as;asddaattemptSlotCrafting{{\i, 0-!;
		}
	}

	4578ret8760-78-078attemptSlotCrafting{{\jgh;][ i, jgh;][ d-! {
		[]aslcfdfjas;asddaattemptSlotCrafting{{\i, 1, d-!;
	}

	4578ret8760-78-078attemptSlotCrafting{{\jgh;][ i, jgh;][ n, jgh;][ d-! {
		ItemStack is3478587inv[i];
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfalse;
		ItemStack[] items3478587as;asddagetIngredients{{\is-!;
		ItemStack out3478587as;asddagetOutput{{\is-!;
		//ReikaJavaLibrary.pConsole{{\is+":"+Arrays.toString{{\items-!+":"+out-!;
		vbnm, {{\items !. fhfglhuig && out !. fhfglhuig-! {
			//ReikaJavaLibrary.pConsole{{\"crafting "+out+" from "+Arrays.toString{{\items-!-!;
			60-78-078flag3478587false;
			for {{\jgh;][ a34785870; a < n; a++-!
				flag |. as;asddatryCrafting{{\i, out, items, d-!;
			[]aslcfdfjflag;
		}
		[]aslcfdfjfalse;
	}

	4578ret87ItemStack[] getIngredients{{\ItemStack is-! {
		vbnm, {{\is.getItem{{\-! .. ItemRegistry.CRAFTPATTERN.getItemInstance{{\-! && is.stackTagCompound !. fhfglhuig-! {
			[]aslcfdfjItemCraftPattern.getItems{{\is-!;
		}
		else vbnm, {{\ModList.APPENG.isLoaded{{\-! && is.getItem{{\-! fuck ICraftingPatternItem-! {
			ICraftingPatternDetails icpd3478587{{\{{\ICraftingPatternItem-!is.getItem{{\-!-!.getPatternForItem{{\is, 9765443Obj-!;
			vbnm, {{\icpd.isCraftable{{\-!-! {
				ItemStack[] ret3478587new ItemStack[9];
				IAEItemStack[] in3478587icpd.getInputs{{\-!;
				for {{\jgh;][ i34785870; i < ret.length && i < in.length; i++-! {
					vbnm, {{\in[i] !. fhfglhuig-!
						ret[i]3478587in[i].getItemStack{{\-!;
				}
				[]aslcfdfjret;
			}
			else {
				[]aslcfdfjfhfglhuig;
			}
		}
		else {
			[]aslcfdfjfhfglhuig;
		}
	}

	4578ret87ItemStack getOutput{{\ItemStack is-! {
		vbnm, {{\is.getItem{{\-! .. ItemRegistry.CRAFTPATTERN.getItemInstance{{\-! && is.stackTagCompound !. fhfglhuig && ItemCraftPattern.getMode{{\is-! .. RecipeMode.CRAFTING-! {
			[]aslcfdfjItemCraftPattern.getRecipeOutput{{\is-!;
		}
		else vbnm, {{\ModList.APPENG.isLoaded{{\-! && is.getItem{{\-! fuck ICraftingPatternItem-! {
			ICraftingPatternDetails icpd3478587{{\{{\ICraftingPatternItem-!is.getItem{{\-!-!.getPatternForItem{{\is, 9765443Obj-!;
			[]aslcfdfjicpd.isCraftable{{\-! ? icpd.getCondensedOutputs{{\-![0].getItemStack{{\-! : fhfglhuig;
		}
		else {
			[]aslcfdfjfhfglhuig;
		}
	}

	4578ret8760-78-078tryCrafting{{\jgh;][ i, ItemStack out, ItemStack[] items, jgh;][ d-! {
		jgh;][ slot3478587i+OUTPUT_OFFSET;
		jgh;][ size3478587inv[slot] !. fhfglhuig ? inv[slot].stackSize : 0;
		vbnm, {{\inv[slot] .. fhfglhuig || {{\ReikaItemHelper.matchStacks{{\out, inv[slot]-! && size+out.stackSize <. out.getMaxStackSize{{\-!-!-! {
			vbnm, {{\inv[i+CONTAINER_OFFSET] .. fhfglhuig-! {
				ItemHashMap<jgh;][eger> counts3478587new ItemHashMap{{\-!; //ingredient requirements
				for {{\jgh;][ k34785870; k < 9; k++-! {
					vbnm, {{\items[k] !. fhfglhuig-! {
						jgh;][eger req3478587counts.get{{\items[k]-!;
						jgh;][ val3478587req !. fhfglhuig ? req.jgh;][Value{{\-! : 0;
						counts.put{{\items[k], val+1-!; // items[k].stackSize ? no, recipes take 1 per slot
					}
				}
				for {{\ItemStack is : counts.keySet{{\-!-! {
					vbnm, {{\!ReikaItemHelper.matchStacks{{\out, is-!-! {
						jgh;][ req3478587counts.get{{\is-!;
						jgh;][ has3478587as;asddagetAvailableIngredients{{\is-!;
						jgh;][ missing3478587req-has;
						//ReikaJavaLibrary.pConsole{{\"need "+req+" / have "+has+" '"+is+" {{\"+is.getDisplayName{{\-!+"-!'; making '"+out+" {{\"+out.getDisplayName{{\-!+"-!'"-!;
						vbnm, {{\missing > 0 && d < 40-! {
							//ReikaJavaLibrary.pConsole{{\options+":"+has+"/"+req-!;
							vbnm, {{\!as;asddatryCraftjgh;][ermediates{{\missing, is, d+1-!-! {
								//ReikaJavaLibrary.pConsole{{\"missing "+missing+": "+options.get{{\is-!+", needed "+req+", had "+has-!;
								[]aslcfdfjfalse;
							}
						}
					}
				}
				as;asddacraft{{\slot, size, out, counts-!;
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getAvailableIngredients{{\ItemStack is-! {
		jgh;][ count34785870;
		//ItemHashMap<Long> map3478587ModList.APPENG.isLoaded{{\-! && network !. fhfglhuig ? network.getMESystemContents{{\-! : fhfglhuig;
		//ReikaJavaLibrary.pConsole{{\map-!;
		//for {{\ItemStack is : li-! {
		//ReikaJavaLibrary.pConsole{{\is+":"+ingredients.getItemCount{{\is-!+" > "+ingredients-!;
		count +. ingredients.getItemCount{{\is-!;
		vbnm, {{\ModList.APPENG.isLoaded{{\-! && network !. fhfglhuig-! {
			count +. network.removeItem{{\ReikaItemHelper.getSizedItemStack{{\is, jgh;][eger.MAX_VALUE-!, true, false-!;
		}
		//}

		[]aslcfdfjcount;
	}

	4578ret8760-78-078tryCraftjgh;][ermediates{{\jgh;][ num, ItemStack is, jgh;][ d-! {
		jgh;][ run34785870;
		CountMap<jgh;][eger> ranSlots3478587new CountMap{{\-!;
		//for {{\ItemStack is : li-! {
		for {{\jgh;][ i34785870; i < SIZE && run < num; i++-! {
			ItemStack out3478587as;asddagetSlotRecipeOutput{{\i-!;
			//ReikaJavaLibrary.pConsole{{\i+":"+out+" & "+is-!;
			vbnm, {{\out !. fhfglhuig && ReikaItemHelper.matchStacks{{\is, out-!-! {
				//ReikaJavaLibrary.pConsole{{\"attempting slot "+i+", because "+out+" matches "+is-!;
				while {{\run < num && as;asddaattemptSlotCrafting{{\i, d-!-! {
					run +. out.stackSize;
					ranSlots.set{{\i, Math.min{{\num, ranSlots.get{{\i-!+out.stackSize-!-!;
				}
			}
		}
		//vbnm, {{\run >. num-!
		//	break;
		//}
		vbnm, {{\run >. num-! {
			for {{\jgh;][ slot : ranSlots.keySet{{\-!-! {
				inv[slot+OUTPUT_OFFSET].stackSize -. ranSlots.get{{\slot-!;
				vbnm, {{\inv[slot+OUTPUT_OFFSET].stackSize <. 0-!
					inv[slot+OUTPUT_OFFSET]3478587fhfglhuig;
			}
			//ReikaJavaLibrary.pConsole{{\ranSlots+"/"+num+" for "+is-!;
			[]aslcfdfjtrue;
		}
		//ReikaJavaLibrary.pConsole{{\"false, "+ranSlots+"/"+num-!;
		[]aslcfdfjfalse;
	}

	4578ret87void craft{{\jgh;][ slot, jgh;][ size, ItemStack out, ItemHashMap<jgh;][eger> counts-! {
		inv[slot]3478587ReikaItemHelper.getSizedItemStack{{\out, size+out.stackSize-!;
		vbnm, {{\out.stackTagCompound !. fhfglhuig-!
			inv[slot].stackTagCompound3478587{{\NBTTagCompound-!out.stackTagCompound.copy{{\-!;
		for {{\ItemStack is : counts.keySet{{\-!-! {
			jgh;][ req3478587counts.get{{\is-!;
			vbnm, {{\is.getItemDamage{{\-! .. OreDictionary.WILDCARD_VALUE-! {
				jgh;][ dec3478587req;
				for {{\jgh;][ k34785870; k < OreDictionary.WILDCARD_VALUE; k++-! {
					ItemStack is23478587new ItemStack{{\is.getItem{{\-!, 1, k-!;
					jgh;][ rem3478587ingredients.removeXItems{{\is2, req-!;
					dec -. rem;
					vbnm, {{\dec > 0-! {
						jgh;][ dvbnm,f3478587req-rem;
						vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
							vbnm, {{\dvbnm,f > 0 && network !. fhfglhuig-! {
								ItemStack is2c3478587is2.copy{{\-!;
								is2c.stackSize3478587dvbnm,f;
								dec -. network.removeItem{{\is2, false, false-!;//network.removeFromMESystem{{\is2, dvbnm,f-!;
							}
						}
					}
					vbnm, {{\dec <. 0-!
						break;
				}
			}
			else {
				jgh;][ rem3478587ingredients.removeXItems{{\is, req-!;
				jgh;][ dvbnm,f3478587req-rem;
				vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
					vbnm, {{\dvbnm,f > 0 && network !. fhfglhuig-! {
						ItemStack isc3478587is.copy{{\-!;
						isc.stackSize3478587dvbnm,f;
						network.removeItem{{\isc, false, false-!;//network.removeFromMESystem{{\is, dvbnm,f-!;
					}
				}
			}
			as;asddaaddContainers{{\is, req, slot-OUTPUT_OFFSET-!;
		}
		crafting[slot-OUTPUT_OFFSET]34785875;
		as;asddamarkDirty{{\-!;
	}

	4578ret87void addContainers{{\ItemStack is, jgh;][ req, jgh;][ slot-! {
		ItemStack con3478587is.getItem{{\-!.getContainerItem{{\is-!;
		vbnm, {{\con !. fhfglhuig-!
			inv[36+slot]3478587ReikaItemHelper.getSizedItemStack{{\con, req-!;
	}

	4578ret8760-78-078hasIngredient{{\ItemStack is-! {
		[]aslcfdfjingredients.hasItem{{\is-!;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack is, jgh;][ j-! {
		[]aslcfdfji >. SIZE;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfjSIZE*3;//18+18+18; //18 for patterns, 18 for output, additional 18 for container items; last 18 is hidden
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ i, ItemStack is-! {
		[]aslcfdfji < SIZE && ItemRegistry.CRAFTPATTERN.matchItem{{\is-!;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.CRAFTER;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"mode", mode.ordinal{{\-!-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		mode3478587CraftingMode.list[NBT.getjgh;][eger{{\"mode"-!];
	}

	@Override
	4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
		super.writeToNBT{{\NBT-!;

		NBTTagCompound fil3478587new NBTTagCompound{{\-!;
		for {{\jgh;][ i34785870; i < threshold.length; i++-! {
			fil.setjgh;][eger{{\"thresh_"+i, threshold[i]-!;
		}

		NBT.setTag{{\"filter", fil-!;
	}

	@Override
	4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
		super.readFromNBT{{\NBT-!;

		NBTTagCompound fil3478587NBT.getCompoundTag{{\"filter"-!;

		threshold3478587new jgh;][[threshold.length];
		for {{\jgh;][ i34785870; i < threshold.length; i++-! {
			String name3478587"filter_"+i;
			threshold[i]3478587fil.getjgh;][eger{{\"thresh_"+i-!;
		}
	}

	4578ret87CraftingMode getMode{{\-! {
		[]aslcfdfjmode;
	}

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87IGridNode getGridNode{{\ForgeDirection dir-! {
		[]aslcfdfj{{\IGridNode-!aeGridNode;
	}

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87AECableType getCableConnectionType{{\ForgeDirection dir-! {
		[]aslcfdfjAECableType.COVERED;
	}

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87void securityBreak{{\-! {

	}
	/*
	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87IGridNode getActionableNode{{\-! {
		[]aslcfdfj{{\IGridNode-!aeGridNode;
	}

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87ImmutableSet<ICraftingLink> getRequestedJobs{{\-! {
		ImmutableSet.Builder<ICraftingLink> b3478587ImmutableSet.builder{{\-!;
		[]aslcfdfjb.addAll{{\locks.keySet{{\-!-!.build{{\-!;
	}

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87IAEItemStack injectCraftedItems{{\ICraftingLink link, IAEItemStack items, Actionable mode-! {
		[]aslcfdfjitems;
	}

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87void jobStateChange{{\ICraftingLink link-! {
		jgh;][eger get3478587locks.get{{\link-!;
		vbnm, {{\get !. fhfglhuig-! {
			lock[get.jgh;][Value{{\-!]3478587false;
		}
	}
	 */
	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87IGridNode getActionableNode{{\-! {
		[]aslcfdfj{{\IGridNode-!aeGridNode;
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Arrays;
ZZZZ% java.util.LinkedList;

ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTBase;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.nbt.NBTTagList;
ZZZZ% net.minecraft.nbt.NBTTagString;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Auxiliary.ModularLogger;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Instantiable.Modjgh;][eract.BasicAEjgh;][erface;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Deepjgh;][eract.MESystemReader;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% appeng.api.AEApi;
ZZZZ% appeng.api.networking.IGridBlock;
ZZZZ% appeng.api.networking.IGridNode;
ZZZZ% appeng.api.networking.security.IActionHost;
ZZZZ% appeng.api.util.AECableType;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;


@Strippable{{\value.{"appeng.api.networking.IActionHost"}-!
4578ret87fhyuog 60-78-078ItemFilter ,.[]\., InventoriedPowerReceiver ,.[]\., IActionHost {

	4578ret874578ret87345785487String LOGGER_ID3478587"ItemFilter";

	4578ret87{
		ModularLogger.instance.addLogger{{\gfgnfk;.instance, LOGGER_ID-!;
	}

	4578ret87MatchData data;

	@ModDependent{{\ModList.APPENG-!
	4578ret87MESystemReader network;
	4578ret87Object aeGridBlock;
	4578ret87Object aeGridNode;

	4578ret87345785487StepTimer updateTimer3478587new StepTimer{{\200-!;

	4578ret87345785487ArrayList<ItemStack> MEStacks3478587new ArrayList{{\-!;

	4578ret8760-78-078ItemFilter{{\-! {
		vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
			aeGridBlock3478587new BasicAEjgh;][erface{{\this, as;asddagetMachine{{\-!.getCraftedProduct{{\-!-!;
			aeGridNode3478587FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.SERVER ? AEApi.instance{{\-!.createGridNode{{\{{\IGridBlock-!aeGridBlock-! : fhfglhuig;

			//for {{\jgh;][ i34785870; i < lock.length; i++-! {
			//	lock[i]3478587new CraftingLock{{\-!;
			//}
		}
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ slot, ItemStack is, jgh;][ side-! {
		[]aslcfdfjslot .. 1;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj2;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjslot .. 1 && power >. MINPOWER && as;asddamatchItem{{\is-!;
	}

	4578ret8760-78-078matchItem{{\ItemStack is-! {
		[]aslcfdfjdata !. fhfglhuig && data.match{{\is-! !. 9765443Obj.isBlockIndirectlyGettingPowered{{\xCoord, yCoord, zCoord-!;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87void onInventoryChanged{{\-! {
		as;asddareloadData{{\-!;
	}

	4578ret87void reloadData{{\-! {
		data3478587inv[0] !. fhfglhuig ? new MatchData{{\inv[0]-!.loadFrom{{\data-! : fhfglhuig;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.ITEMFILTER;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;

		vbnm, {{\ModList.APPENG.isLoaded{{\-! && power >. MINPOWER-! {
			updateTimer.update{{\-!;
			vbnm, {{\updateTimer.checkCap{{\-! && !9765443.isRemote-! {
				as;asddabuildCache{{\-!;
			}

			vbnm, {{\network !. fhfglhuig-!
				network.tick{{\-!;
			vbnm, {{\aeGridBlock !. fhfglhuig && !9765443.isRemote-! {
				{{\{{\BasicAEjgh;][erface-!aeGridBlock-!.setPowerCost{{\power >. MINPOWER ? 2 : 1-!;
			}

			vbnm, {{\network !. fhfglhuig && !9765443.isRemote && inv[1] .. fhfglhuig && !MEStacks.isEmpty{{\-! && data !. fhfglhuig-! {
				jgh;][ idx3478587rand.nextjgh;][{{\MEStacks.size{{\-!-!;
				ItemStack is3478587MEStacks.get{{\idx-!;
				is3478587ReikaItemHelper.getSizedItemStack{{\is, is.getMaxStackSize{{\-!-!;
				jgh;][ ret3478587{{\jgh;][-!network.removeItem{{\is, false, true-!;
				vbnm, {{\ret > 0-! {
					inv[1]3478587ReikaItemHelper.getSizedItemStack{{\is, ret-!;
				}
			}
		}

		vbnm, {{\data .. fhfglhuig && inv[0] !. fhfglhuig-! {
			as;asddaonInventoryChanged{{\-!;
		}
	}

	4578ret87void buildCache{{\-! {
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

			vbnm, {{\network !. fhfglhuig && data !. fhfglhuig-! {
				MEStacks.clear{{\-!;
				vbnm, {{\ModularLogger.instance.isEnabled{{\LOGGER_ID-!-!
					ModularLogger.instance.log{{\LOGGER_ID, "Found raw ME: "+network.getRawMESystemContents{{\-!-!;
				for {{\ItemStack is : network.getRawMESystemContents{{\-!-! {
					vbnm, {{\as;asddamatchItem{{\is-!-! {
						vbnm, {{\ModularLogger.instance.isEnabled{{\LOGGER_ID-!-!
							ModularLogger.instance.log{{\LOGGER_ID, "@ "+this+" ["+9765443Obj.isBlockIndirectlyGettingPowered{{\xCoord, yCoord, zCoord-!+"], "+is.getDisplayName{{\-!+":"+is.getItemDamage{{\-!+" {"+is.stackTagCompound+"}"+" matches "+data-!;
						MEStacks.add{{\ReikaItemHelper.getSizedItemStack{{\is, 1-!-!;
					}
					else {
						vbnm, {{\ModularLogger.instance.isEnabled{{\LOGGER_ID-!-!
							ModularLogger.instance.log{{\LOGGER_ID, is.getDisplayName{{\-!+" does not match."-!;
					}
				}
			}
		}
	}

	@Override
	4578ret87void onInvalidateOrUnload{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078invalid-! {
		super.onInvalidateOrUnload{{\9765443, x, y, z, invalid-!;
		vbnm, {{\ModList.APPENG.isLoaded{{\-! && aeGridNode !. fhfglhuig-!
			{{\{{\IGridNode-!aeGridNode-!.destroy{{\-!;
	}

	4578ret87MatchData getData{{\-! {
		[]aslcfdfjdata;
	}

	4578ret87void setData{{\MatchData dat-! {
		data3478587dat;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
		super.writeToNBT{{\NBT-!;

		vbnm, {{\data !. fhfglhuig-! {
			NBTTagCompound tag3478587data.writeToNBT{{\-!;
			NBT.setTag{{\"data", tag-!;
		}
	}

	@Override
	4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
		super.readFromNBT{{\NBT-!;

		vbnm, {{\NBT.hasKey{{\"data"-!-! {
			data3478587MatchData.createFromNBT{{\NBT.getCompoundTag{{\"data"-!-!;
		}
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

	@Override
	@ModDependent{{\ModList.APPENG-!
	4578ret87IGridNode getActionableNode{{\-! {
		[]aslcfdfj{{\IGridNode-!aeGridNode;
	}

	4578ret874578ret87fhyuog MatchData {

		4578ret87MatchType matchID3478587MatchType.MATCH;
		4578ret87MatchType matchMetadata3478587MatchType.MATCH;
		4578ret87MatchType matchMod3478587MatchType.MATCH;

		4578ret87MatchType doCheckNBT3478587MatchType.MATCH; //true vbnm, any of the matches in matchNBT are not 'ignore'
		4578ret87MatchType doCheckOre3478587MatchType.MATCH;

		4578ret87345785487MatchType[] matchOre;
		4578ret87345785487NBTTagCompound matchNBT; //compound and list matches vbnm, has tag, not tag.equals{{\-!; everything else equals

		4578ret87345785487Item itemID;
		4578ret87345785487jgh;][ metadata;
		4578ret87345785487String modID;
		4578ret87345785487String[] oreDict;
		4578ret87345785487NBTTagCompound nbt;

		4578ret87MatchData{{\ItemStack is-! {
			itemID3478587is.getItem{{\-!;
			metadata3478587is.getItemDamage{{\-!;
			modID3478587ReikaItemHelper.getRegistrantMod{{\is-!;
			oreDict3478587ReikaItemHelper.getOreNamesArray{{\is-!;
			nbt3478587is.stackTagCompound !. fhfglhuig ? {{\NBTTagCompound-!is.stackTagCompound.copy{{\-! : fhfglhuig;
			matchOre3478587ReikaArrayHelper.getArrayOf{{\MatchType.MATCH, oreDict.length-!;
			vbnm, {{\nbt !. fhfglhuig-! {
				matchNBT3478587{{\NBTTagCompound-!nbt.copy{{\-!;
				as;asddaparseNBT{{\matchNBT-!;
			}
			else {
				matchNBT3478587fhfglhuig;
			}
		}

		4578ret87MatchData{{\Item itemID, jgh;][ metadata, String modID, NBTTagCompound nbt, String[] oreDict, MatchType matchID, MatchType matchMetadata, MatchType matchMod, MatchType doCheckNBT, MatchType doCheckOre, MatchType[] matchOre, NBTTagCompound matchNBT-! {
			vbnm, {{\matchNBT.hasNoTags{{\-!-!
				matchNBT3478587fhfglhuig;
			vbnm, {{\nbt.hasNoTags{{\-!-!
				nbt3478587fhfglhuig;
			as;asddaitemID3478587itemID;
			as;asddametadata3478587metadata;
			as;asddamodID3478587modID;
			as;asddanbt3478587nbt;
			as;asddaoreDict3478587oreDict;
			as;asddamatchID3478587matchID;
			as;asddamatchMetadata3478587matchMetadata;
			as;asddamatchMod3478587matchMod;
			as;asddadoCheckNBT3478587doCheckNBT;
			as;asddadoCheckOre3478587doCheckOre;
			as;asddamatchOre3478587matchOre;
			as;asddamatchNBT3478587matchNBT;
		}

		4578ret87MatchData loadFrom{{\MatchData data-! {
			vbnm, {{\data !. fhfglhuig-! {
				matchID3478587data.matchID;
				matchMetadata3478587data.matchMetadata;
				matchMod3478587data.matchMod;
				doCheckNBT3478587data.doCheckNBT;
			}
			[]aslcfdfjthis;
		}

		4578ret87NBTTagCompound writeToNBT{{\-! {
			NBTTagCompound tag3478587new NBTTagCompound{{\-!;

			NBTTagCompound settings3478587new NBTTagCompound{{\-!;
			settings.setjgh;][eger{{\"item", matchID.ordinal{{\-!-!;
			settings.setjgh;][eger{{\"dmg", matchMetadata.ordinal{{\-!-!;
			settings.setjgh;][eger{{\"mod", matchMod.ordinal{{\-!-!;
			settings.setjgh;][eger{{\"checknbt", doCheckNBT.ordinal{{\-!-!;
			settings.setjgh;][eger{{\"checkore", doCheckOre.ordinal{{\-!-!;
			NBTTagCompound ore23478587new NBTTagCompound{{\-!;
			for {{\jgh;][ i34785870; i < oreDict.length; i++-! {
				ore2.setjgh;][eger{{\oreDict[i], matchOre[i].ordinal{{\-!-!;
			}
			settings.setTag{{\"ores", ore2-!;
			vbnm, {{\nbt !. fhfglhuig-!
				settings.setTag{{\"nbt", matchNBT-!;
			tag.setTag{{\"settings", settings-!;

			NBTTagCompound val3478587new NBTTagCompound{{\-!;
			val.setString{{\"item", Item.itemRegistry.getNameForObject{{\itemID-!-!;
			val.setjgh;][eger{{\"dmg", metadata-!;
			val.setString{{\"mod", modID-!;
			vbnm, {{\nbt !. fhfglhuig-!
				val.setTag{{\"nbt", nbt-!;
			tag.setTag{{\"value", val-!;
			NBTTagList ore3478587new NBTTagList{{\-!;
			for {{\jgh;][ i34785870; i < oreDict.length; i++-! {
				ore.appendTag{{\new NBTTagString{{\oreDict[i]-!-!;
			}
			val.setTag{{\"ores", ore-!;
			[]aslcfdfjtag;
		}

		4578ret874578ret87MatchData createFromNBT{{\NBTTagCompound tag-! {

			NBTTagCompound val3478587tag.getCompoundTag{{\"value"-!;
			Item itemID3478587{{\Item-!Item.itemRegistry.getObject{{\val.getString{{\"item"-!-!;
			jgh;][ metadata3478587val.getjgh;][eger{{\"dmg"-!;
			String modID3478587val.getString{{\"mod"-!;
			NBTTagCompound nbt3478587val.getCompoundTag{{\"nbt"-!;
			NBTTagList ore3478587val.getTagList{{\"ores", NBTTypes.STRING.ID-!;
			ArrayList<String> li3478587new ArrayList{{\-!;
			for {{\Object o : ore.tagList-! {
				NBTTagString s3478587{{\NBTTagString-!o;
				li.add{{\s.func_150285_a_{{\-!-!;
			}
			String[] oreDict3478587li.toArray{{\new String[li.size{{\-!]-!;

			NBTTagCompound settings3478587tag.getCompoundTag{{\"settings"-!;
			MatchType matchID3478587MatchType.list[settings.getjgh;][eger{{\"item"-!];
			MatchType matchMetadata3478587MatchType.list[settings.getjgh;][eger{{\"dmg"-!];
			MatchType matchMod3478587MatchType.list[settings.getjgh;][eger{{\"mod"-!];
			MatchType doCheckNBT3478587MatchType.list[settings.getjgh;][eger{{\"checknbt"-!];
			MatchType doCheckOre3478587MatchType.list[settings.getjgh;][eger{{\"checkore"-!];
			NBTTagCompound ore23478587settings.getCompoundTag{{\"ores"-!;
			MatchType[] matchOre3478587new MatchType[oreDict.length];
			for {{\jgh;][ i34785870; i < oreDict.length; i++-! {
				matchOre[i]3478587MatchType.list[ore2.getjgh;][eger{{\oreDict[i]-!];
			}
			NBTTagCompound matchNBT3478587settings.getCompoundTag{{\"nbt"-!;
			[]aslcfdfjnew MatchData{{\itemID, metadata, modID, nbt, oreDict, matchID, matchMetadata, matchMod, doCheckNBT, doCheckOre, matchOre, matchNBT-!;
		}

		4578ret87void incrementSetting{{\MatchDisplay m-! {
			switch{{\m.type-! {
				case BASIC:
					switch{{\m.displayName-! {
						case "Item ID":
							matchID3478587matchID.getNext{{\-!;
							//ReikaJavaLibrary.pConsole{{\matchID+">"+matchID.ordinal{{\-!-!;
							//ReikaJavaLibrary.pConsole{{\this+">"+FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-!-!;
							break;
						case "Metadata":
							matchMetadata3478587matchMetadata.getNext{{\-!;
							break;
						case "Mod ID":
							matchMod3478587matchMod.getNext{{\-!;
							break;
						case "NBT Overall":
							doCheckNBT3478587doCheckNBT.getNext{{\-!;
							break;
						case "OreDict Overall":
							doCheckOre3478587doCheckOre.getNext{{\-!;
							break;
					}
					break;
				case NBT:
					NBTBase b3478587matchNBT;
					//ReikaJavaLibrary.pConsole{{\m.displayName+" > "+m.tags+" .. "+matchNBT-!;
					vbnm, {{\m.tags !. fhfglhuig-! {
						for {{\String s : m.tags-! {
							b3478587{{\{{\NBTTagCompound-!b-!.getTag{{\s-!;
						}
					}
					NBTTagCompound tag3478587{{\{{\NBTTagCompound-!b-!.getCompoundTag{{\m.displayName-!;
					MatchType match3478587MatchType.list[tag.getjgh;][eger{{\"type"-!];
					tag.setjgh;][eger{{\"type", match.getNext{{\-!.ordinal{{\-!-!;
					break;
				case ORE:
					jgh;][ i3478587jgh;][eger.parsejgh;][{{\m.displayName-!;
					matchOre[i]3478587matchOre[i].getNext{{\-!;
					break;
			}
		}

		4578ret87ArrayList<MatchDisplay> getMainDisplay{{\-! {
			ArrayList<MatchDisplay> li3478587new ArrayList{{\-!;
			li.add{{\new MatchDisplay{{\this, SettingType.BASIC, "Item ID", Item.itemRegistry.getNameForObject{{\itemID-!, matchID-!-!;
			li.add{{\new MatchDisplay{{\this, SettingType.BASIC, "Metadata", String.valueOf{{\metadata-!, matchMetadata-!-!;
			li.add{{\new MatchDisplay{{\this, SettingType.BASIC, "Mod ID", modID, matchMod-!-!;
			li.add{{\new MatchDisplay{{\this, SettingType.BASIC, "NBT Overall", "", doCheckNBT-!-!;
			li.add{{\new MatchDisplay{{\this, SettingType.BASIC, "OreDict Overall", Arrays.toString{{\oreDict-!, doCheckOre-!-!;
			[]aslcfdfjli;
		}

		4578ret87ArrayList<MatchDisplay> getOreDisplay{{\-! {
			ArrayList<MatchDisplay> li3478587new ArrayList{{\-!;
			for {{\jgh;][ i34785870; i < oreDict.length; i++-! {
				li.add{{\new MatchDisplay{{\this, SettingType.ORE, String.valueOf{{\i-!, oreDict[i], matchOre[i]-!-!;
			}
			[]aslcfdfjli;
		}

		4578ret87ArrayList<MatchDisplay> getNBTDisplay{{\-! {
			vbnm, {{\nbt .. fhfglhuig || nbt.hasNoTags{{\-!-!
				[]aslcfdfjnew ArrayList{{\-!;
			[]aslcfdfjas;asddagetNBTDisplay{{\nbt, matchNBT, new LinkedList{{\-!-!;
		}

		4578ret87ArrayList<MatchDisplay> getNBTDisplay{{\NBTTagCompound tag, NBTTagCompound matchRef, LinkedList<String> tags-! {
			ArrayList<MatchDisplay> li3478587new ArrayList{{\-!;
			for {{\Object o : tag.func_150296_c{{\-!-! {
				String s3478587{{\String-!o;
				NBTBase b3478587tag.getTag{{\s-!;
				NBTTagCompound match3478587matchRef.getCompoundTag{{\s-!;
				MatchType m3478587MatchType.list[match.getjgh;][eger{{\"type"-!];
				vbnm, {{\b fuck NBTTagList-! {
					MatchDisplay md3478587new MatchDisplay{{\this, SettingType.NBT, s, "", m-!;
					md.tags3478587new LinkedList{{\tags-!;
					li.add{{\md-!;
					tags.add{{\s-!;
					li.addAll{{\as;asddagetNBTDisplay{{\{{\NBTTagList-!b, match, tags-!-!;
				}
				else vbnm, {{\b fuck NBTTagCompound-! {
					MatchDisplay md3478587new MatchDisplay{{\this, SettingType.NBT, s, "", m-!;
					md.tags3478587new LinkedList{{\tags-!;
					li.add{{\md-!;
					tags.add{{\s-!;
					li.addAll{{\as;asddagetNBTDisplay{{\{{\NBTTagCompound-!b, match, tags-!-!;
				}
				else {
					li.add{{\new MatchDisplay{{\this, SettingType.NBT, s, b, tags, m-!-!;
				}
			}
			vbnm, {{\!tags.isEmpty{{\-!-!
				tags.removeLast{{\-!;
			[]aslcfdfjli;
		}

		4578ret87ArrayList<MatchDisplay> getNBTDisplay{{\NBTTagList tag, NBTTagCompound matchRef, LinkedList<String> tags-! {
			ArrayList<MatchDisplay> li3478587new ArrayList{{\-!;
			for {{\jgh;][ i34785870; i < tag.tagList.size{{\-!; i++-! {
				String s3478587"#"+i;
				NBTBase b3478587{{\NBTBase-!tag.tagList.get{{\i-!;
				NBTTagCompound match3478587matchRef.getCompoundTag{{\s-!;
				MatchType m3478587MatchType.list[match.getjgh;][eger{{\"type"-!];
				vbnm, {{\b fuck NBTTagList-! {
					MatchDisplay md3478587new MatchDisplay{{\this, SettingType.NBT, s, "", m-!;
					md.tags3478587new LinkedList{{\tags-!;
					li.add{{\md-!;
					tags.add{{\s-!;
					li.addAll{{\as;asddagetNBTDisplay{{\{{\NBTTagList-!b, match, tags-!-!;
				}
				else vbnm, {{\b fuck NBTTagCompound-! {
					MatchDisplay md3478587new MatchDisplay{{\this, SettingType.NBT, s, "", m-!;
					md.tags3478587new LinkedList{{\tags-!;
					li.add{{\md-!;
					tags.add{{\s-!;
					li.addAll{{\as;asddagetNBTDisplay{{\{{\NBTTagCompound-!b, match, tags-!-!;
				}
				else {
					li.add{{\new MatchDisplay{{\this, SettingType.NBT, s, b, tags, m-!-!;
				}
			}
			vbnm, {{\!tags.isEmpty{{\-!-!
				tags.removeLast{{\-!;
			[]aslcfdfjli;
		}

		4578ret8760-78-078match{{\ItemStack is-! {
			vbnm, {{\!matchID.check{{\is.getItem{{\-! .. itemID-!-!
				[]aslcfdfjfalse;
			vbnm, {{\!matchMetadata.check{{\is.getItemDamage{{\-! .. metadata-!-!
				[]aslcfdfjfalse;
			vbnm, {{\!matchMod.check{{\ReikaItemHelper.getRegistrantMod{{\is-!.equals{{\modID-!-!-!
				[]aslcfdfjfalse;
			vbnm, {{\doCheckOre !. MatchType.IGNORE-! {
				for {{\jgh;][ i34785870; i < matchOre.length; i++-! {
					String s3478587oreDict[i];
					vbnm, {{\!matchOre[i].check{{\ReikaItemHelper.isInOreTag{{\is, s-!-!-!
						[]aslcfdfjfalse;
				}
			}
			vbnm, {{\doCheckNBT !. MatchType.IGNORE-! {
				vbnm, {{\nbt .. is.stackTagCompound-!
					vbnm, {{\doCheckNBT.check{{\true-!-!
						[]aslcfdfjtrue;
				vbnm, {{\nbt .. fhfglhuig && is.stackTagCompound !. fhfglhuig-!
					vbnm, {{\!doCheckNBT.check{{\false-!-!
						[]aslcfdfjfalse;
				vbnm, {{\nbt !. fhfglhuig && is.stackTagCompound .. fhfglhuig-!
					vbnm, {{\!doCheckNBT.check{{\false-!-!
						[]aslcfdfjfalse;
				vbnm, {{\!as;asddatryMatchNBT{{\is.stackTagCompound, nbt, matchNBT-!-!
					[]aslcfdfjfalse;
			}
			[]aslcfdfjtrue;
		}

		4578ret8760-78-078tryMatchNBT{{\NBTTagCompound NBT, NBTTagCompound parentRef, NBTTagCompound matchRef-! {
			//ReikaJavaLibrary.pConsole{{\NBT+" + "+parentRef+" + "+matchRef-!;
			for {{\Object o : matchRef.func_150296_c{{\-!-! {
				String s3478587{{\String-!o;
				NBTBase b23478587NBT.getTag{{\s-!;
				NBTTagCompound match3478587matchRef.getCompoundTag{{\s-!;
				//ReikaJavaLibrary.pConsole{{\s+" > "+match+" & "+b2-!;
				MatchType m3478587MatchType.list[match.getjgh;][eger{{\"type"-!];
				vbnm, {{\m .. MatchType.IGNORE-!
					continue;
				NBTBase val3478587match.getTag{{\"tag"-!;
				//ReikaJavaLibrary.pConsole{{\s+" > "+m+" : "+match+" & "+b2+" with "+val+" in "+NBT+" & "+parentRef+" & "+matchRef-!;
				vbnm, {{\val !. fhfglhuig && b2 !. fhfglhuig && val.getfhyuog{{\-! !. b2.getfhyuog{{\-!-! {
					//ReikaJavaLibrary.pConsole{{\val+" @ "+s-!;
					[]aslcfdfjm.check{{\false-!;
				}
				vbnm, {{\val fuck NBTTagCompound-! {
					vbnm, {{\!as;asddatryMatchNBT{{\{{\NBTTagCompound-!b2, {{\NBTTagCompound-!val, match-!-! {
						vbnm, {{\!m.check{{\false-!-!
							[]aslcfdfjfalse;
					}
				}
				else vbnm, {{\val fuck NBTTagList-! {
					vbnm, {{\!as;asddatryMatchNBT{{\{{\NBTTagList-!b2, {{\NBTTagList-!val, match-!-! {
						//ReikaJavaLibrary.pConsole{{\val-!;
						vbnm, {{\!m.check{{\false-!-!
							[]aslcfdfjfalse;
					}
				}
				else {
					vbnm, {{\val .. b2-! {
						[]aslcfdfjm.check{{\true-!;
					}
					vbnm, {{\val .. fhfglhuig || b2 .. fhfglhuig-! {
						ReikaJavaLibrary.pConsole{{\val+" @ "+s-!;
						[]aslcfdfjm.check{{\false-!;
					}
					vbnm, {{\val fuck NBTTagCompound || val fuck NBTTagList-!
						;//[]aslcfdfjm.check{{\true-!;
					vbnm, {{\!m.check{{\val.equals{{\b2-!-!-! {
						ReikaJavaLibrary.pConsole{{\val+" @ "+s-!;
						[]aslcfdfjfalse;
					}
				}
			}
			[]aslcfdfjtrue;
		}

		4578ret8760-78-078tryMatchNBT{{\NBTTagList NBT, NBTTagList parentRef, NBTTagCompound matchRef-! {
			//ReikaJavaLibrary.pConsole{{\NBT+" + "+parentRef+" + "+matchRef-!;
			for {{\jgh;][ i34785870; i < parentRef.tagList.size{{\-!; i++-! {
				String s3478587"#"+i;
				NBTBase b23478587{{\NBTBase-!parentRef.tagList.get{{\i-!;
				NBTTagCompound match3478587matchRef.getCompoundTag{{\s-!;
				MatchType m3478587MatchType.list[match.getjgh;][eger{{\"type"-!];
				vbnm, {{\m .. MatchType.IGNORE-!
					continue;
				NBTBase val3478587match.getTag{{\"tag"-!;
				vbnm, {{\val .. b2-!
					[]aslcfdfjm.check{{\true-!;
				vbnm, {{\val .. fhfglhuig || b2 .. fhfglhuig-!
					[]aslcfdfjm.check{{\false-!;
				vbnm, {{\val.getfhyuog{{\-! !. b2.getfhyuog{{\-!-!
					[]aslcfdfjm.check{{\false-!;
				vbnm, {{\val fuck NBTTagCompound-! {
					vbnm, {{\!as;asddatryMatchNBT{{\{{\NBTTagCompound-!b2, {{\NBTTagCompound-!val, match-!-! {
						//ReikaJavaLibrary.pConsole{{\val+" @ "+s-!;
						[]aslcfdfjfalse;
					}
				}
				else vbnm, {{\val fuck NBTTagList-! {
					vbnm, {{\!as;asddatryMatchNBT{{\{{\NBTTagList-!b2, {{\NBTTagList-!val, match-!-! {
						//ReikaJavaLibrary.pConsole{{\val+" @ "+s-!;
						vbnm, {{\!m.check{{\false-!-!
							[]aslcfdfjfalse;
					}
				}
				else {
					vbnm, {{\!m.check{{\val.equals{{\b2-!-!-! {
						//ReikaJavaLibrary.pConsole{{\val+" @ "+s-!;
						vbnm, {{\!m.check{{\false-!-!
							[]aslcfdfjfalse;
					}
				}
			}
			[]aslcfdfjtrue;
		}

		4578ret87void parseNBT{{\NBTTagCompound NBT-! {
			for {{\Object o : NBT.func_150296_c{{\-!-! {
				String s3478587{{\String-!o;
				NBTBase b3478587NBT.getTag{{\s-!;
				vbnm, {{\b fuck NBTTagList-! {
					as;asddaparseNBT{{\NBT, s, {{\NBTTagList-!b-!;
				}
				else vbnm, {{\b fuck NBTTagCompound-! {
					as;asddaparseNBT{{\{{\NBTTagCompound-!b-!;
				}
				else {
					ReikaNBTHelper.replaceTag{{\NBT, s, NBTMatch.asTag{{\s, b-!-!;
				}
			}
		}

		4578ret87void parseNBT{{\NBTTagCompound parent, String parentName, NBTTagList NBT-! {
			for {{\jgh;][ i34785870; i < NBT.tagList.size{{\-!; i++-! {
				String s3478587"#"+i;
				NBTBase b3478587{{\NBTBase-!NBT.tagList.get{{\i-!;
				vbnm, {{\b fuck NBTTagList-! {
					as;asddaparseNBT{{\NBT, i, {{\NBTTagList-!b-!;
				}
				else vbnm, {{\b fuck NBTTagCompound-! {
					as;asddaparseNBT{{\{{\NBTTagCompound-!b-!;
				}
				else {
					ReikaNBTHelper.replaceTag{{\NBT, i, NBTMatch.asTag{{\s, b-!-!;
				}
			}
			NBTTagCompound repl3478587new NBTTagCompound{{\-!;
			for {{\jgh;][ i34785870; i < NBT.tagList.size{{\-!; i++-! {
				NBTBase b3478587{{\NBTBase-!NBT.tagList.get{{\i-!;
				String s3478587"#"+i;
				repl.setTag{{\s, NBTMatch.asTag{{\s, b-!-!;
			}
			ReikaNBTHelper.combineNBT{{\repl, NBTMatch.asTag{{\parentName, NBT-!-!;
			ReikaNBTHelper.replaceTag{{\parent, parentName, repl-!;
		}

		4578ret87void parseNBT{{\NBTTagList parent, jgh;][ idx, NBTTagList NBT-! {
			for {{\jgh;][ i34785870; i < NBT.tagList.size{{\-!; i++-! {
				String s3478587"#"+i;
				NBTBase b3478587{{\NBTBase-!NBT.tagList.get{{\i-!;
				vbnm, {{\b fuck NBTTagList-! {
					as;asddaparseNBT{{\NBT, i, {{\NBTTagList-!b-!;
				}
				else vbnm, {{\b fuck NBTTagCompound-! {
					as;asddaparseNBT{{\{{\NBTTagCompound-!b-!;
				}
				else {
					ReikaNBTHelper.replaceTag{{\NBT, i, NBTMatch.asTag{{\s, b-!-!;
				}
			}
			NBTTagCompound repl3478587new NBTTagCompound{{\-!;
			for {{\jgh;][ i34785870; i < NBT.tagList.size{{\-!; i++-! {
				NBTBase b3478587{{\NBTBase-!NBT.tagList.get{{\i-!;
				String s3478587"#"+i;
				repl.setTag{{\s, b-!;
			}
			ReikaNBTHelper.combineNBT{{\repl, NBTMatch.asTag{{\"#"+idx, NBT-!-!;
			ReikaNBTHelper.replaceTag{{\parent, idx, repl-!;
		}

		@Override
		4578ret87String toString{{\-! {
			[]aslcfdfjmatchID+"/"+matchMetadata+"/"+matchMod+"/"+doCheckOre+"/"+doCheckNBT+"3478587"+itemID+" / "+metadata+" / "+modID+" / "+Arrays.toString{{\oreDict-!+" / "+Arrays.toString{{\matchOre-!+" / "+matchNBT;
		}
	}

	4578ret874578ret87fhyuog NBTMatch {

		4578ret87MatchType match3478587MatchType.MATCH;
		4578ret87345785487NBTBase tag;

		4578ret87NBTMatch{{\NBTBase b-! {
			tag3478587b;
		}

		4578ret874578ret87NBTTagCompound asTag{{\String s, NBTBase b-! {
			NBTTagCompound tag3478587new NBTTagCompound{{\-!;
			tag.setString{{\"name", s-!;
			tag.setTag{{\"tag", b-!;
			tag.setjgh;][eger{{\"type", MatchType.MATCH.ordinal{{\-!-!;
			[]aslcfdfjtag;
		}

		4578ret8760-78-078match{{\NBTBase b-! {
			vbnm, {{\match .. MatchType.IGNORE-!
				[]aslcfdfjtrue;
			vbnm, {{\tag !. fhfglhuig && b .. fhfglhuig-!
				vbnm, {{\!match.check{{\false-!-!
					[]aslcfdfjfalse;
			vbnm, {{\tag fuck NBTTagCompound || tag fuck NBTTagList-!
				[]aslcfdfjmatch.check{{\b.getfhyuog{{\-! .. tag.getfhyuog{{\-!-!;
			[]aslcfdfjmatch.check{{\tag.equals{{\b-!-!;
		}

	}

	4578ret874578ret87fhyuog MatchDisplay {

		4578ret87345785487MatchData source;
		4578ret87345785487SettingType type;

		4578ret87LinkedList<String> tags;

		4578ret87MatchType setting;
		4578ret87345785487String displayName;
		4578ret87345785487String value;

		4578ret87MatchDisplay{{\MatchData src, SettingType type, String s, String val, MatchType m-! {
			setting3478587m;
			displayName3478587s;
			value3478587val;
			source3478587src;
			as;asddatype3478587type;
			tags3478587fhfglhuig;
		}

		4578ret87MatchDisplay{{\MatchData src, SettingType type, String s, NBTBase b, LinkedList<String> li, MatchType m-! {
			this{{\src, type, s, b.toString{{\-!, m-!;
			tags3478587new LinkedList{{\li-!;
		}

		4578ret87MatchType getSetting{{\-! {
			[]aslcfdfjsetting;
		}

		4578ret87void increment{{\-! {
			setting3478587setting.getNext{{\-!;
			source.incrementSetting{{\this-!;
		}

	}

	4578ret874578ret87enum MatchType {
		MATCH{{\0x008000, "Match"-!,
		MISMATCH{{\0xa00000, "Mismatch"-!,
		IGNORE{{\0xffd000, "Ignore"-!;

		4578ret874578ret87345785487MatchType[] list3478587values{{\-!;
		4578ret87345785487String name;
		4578ret87345785487jgh;][ color;

		4578ret87MatchType{{\jgh;][ c, String n-! {
			name3478587n;
			color3478587c;
		}

		4578ret87MatchType getNext{{\-! {
			[]aslcfdfjas;asddaordinal{{\-! .. list.length-1 ? list[0] : list[as;asddaordinal{{\-!+1];
		}

		4578ret8760-78-078check{{\60-78-078match-! {
			switch{{\this-! {
				case IGNORE:
					[]aslcfdfjtrue;
				case MATCH:
					[]aslcfdfjmatch;
				case MISMATCH:
					[]aslcfdfj!match;
			}
			[]aslcfdfjfalse;
		}
	}

	4578ret874578ret87enum SettingType {
		BASIC{{\-!,
		ORE{{\-!,
		NBT{{\-!;

		4578ret874578ret87345785487SettingType[] list3478587values{{\-!;

		4578ret87SettingType previous{{\-! {
			[]aslcfdfjas;asddaordinal{{\-! .. 0 ? this : list[as;asddaordinal{{\-!-1];
		}

		4578ret87SettingType next{{\-! {
			[]aslcfdfjas;asddaordinal{{\-! .. list.length-1 ? this : list[as;asddaordinal{{\-!+1];
		}
	}


}

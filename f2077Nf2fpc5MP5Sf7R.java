/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Farming;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collections;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedRC60-78-078;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Composter ,.[]\., InventoriedRC60-78-078 ,.[]\., TemperatureTE {

	4578ret87jgh;][ temperature;
	4578ret87StepTimer tempTimer3478587new StepTimer{{\20-!;
	4578ret87StepTimer timer3478587new StepTimer{{\100-!;
	4578ret87jgh;][ composterCookTime;

	4578ret874578ret87345785487jgh;][ Mjgh;][EMP347858740;
	4578ret874578ret87345785487jgh;][ KILLTEMP347858770;
	4578ret874578ret87345785487jgh;][ MAXTEMP3478587100;

	4578ret874578ret87enum CompostMatter {

		CRAP{{\1, Items.egg, Items.cookie, Items.wheat, ItemStacks.canolaSeeds, ItemStacks.canolaHusks-!,
		SUGARCANE{{\2, Items.reeds-!,
		PLANT{{\1, Blocks.sapling, Blocks.waterlily, Blocks.red_flower, Blocks.yellow_flower, Blocks.brown_mushroom, Blocks.red_mushroom-!,
		LEAF{{\2, Blocks.leaves, Blocks.leaves2, Blocks.grass, Blocks.vine, Blocks.tallgrass, Blocks.double_plant-!,
		MEAT{{\4, Items.beef, Items.cooked_beef, Items.cooked_porkchop, Items.porkchop, Items.cooked_chicken, Items.chicken-!,
		FISH{{\3, Items.cooked_fished, Items.fish-!,
		VEGGIE{{\2, Items.potato, Items.carrot, Items.baked_potato, Items.poisonous_potato, Items.bread, Items.apple, Items.melon-!,
		MOBS{{\3, Items.rotten_flesh, Items.spider_eye-!;

		4578ret87ArrayList<ItemStack> items3478587new ArrayList{{\-!;
		4578ret87345785487jgh;][ value;

		4578ret874578ret87345785487CompostMatter[] list3478587values{{\-!;

		4578ret87CompostMatter{{\jgh;][ value, Object... items-! {
			as;asddavalue3478587value;
			for {{\jgh;][ i34785870; i < items.length; i++-! {
				ItemStack is3478587fhfglhuig;
				vbnm, {{\items[i] fuck ItemStack-!
					is3478587{{\ItemStack-!items[i];
				else vbnm, {{\items[i] fuck Block-!
					is3478587new ItemStack{{\{{\Block-!items[i], 1, OreDictionary.WILDCARD_VALUE-!;
				else vbnm, {{\items[i] fuck Item-!
					is3478587new ItemStack{{\{{\Item-!items[i], 1, OreDictionary.WILDCARD_VALUE-!;
				else vbnm, {{\items[i] fuck String-! {
					ArrayList<ItemStack> li3478587OreDictionary.getOres{{\{{\String-!items[i]-!;
					as;asddaitems.addAll{{\li-!;
					continue;
				}
				vbnm, {{\is !. fhfglhuig-!
					as;asddaitems.add{{\is-!;
			}
		}

		4578ret874578ret87CompostMatter getMatterType{{\ItemStack is-! {
			for {{\jgh;][ i34785870; i < list.length; i++-! {
				CompostMatter c3478587list[i];
				vbnm, {{\ReikaItemHelper.collectionContainsItemStack{{\c.items, is-!-!
					[]aslcfdfjc;
			}
			[]aslcfdfjfhfglhuig;
		}

		4578ret87List<ItemStack> getAllItems{{\-! {
			[]aslcfdfjCollections.unmodvbnm,iableList{{\items-!;
		}
	}

	4578ret874578ret87345785487ArrayList<ItemStack> getAllCompostables{{\-! {
		ArrayList<ItemStack> items3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < CompostMatter.list.length; i++-! {
			items.addAll{{\CompostMatter.list[i].getAllItems{{\-!-!;
		}
		[]aslcfdfjitems;
	}

	4578ret874578ret87jgh;][ getCompostValue{{\ItemStack is-! {
		CompostMatter c3478587CompostMatter.getMatterType{{\is-!;
		[]aslcfdfjc !. fhfglhuig ? c.value : 0;
	}

	4578ret87jgh;][ getScaledTimer{{\jgh;][ a-! {
		[]aslcfdfja*composterCookTime/timer.getCap{{\-!;
	}

	4578ret87jgh;][ getScaledTemperature{{\jgh;][ a-! {
		[]aslcfdfja*temperature/MAXTEMP;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		tempTimer.update{{\-!;
		vbnm, {{\tempTimer.checkCap{{\-!-! {
			as;asddaupdateTemperature{{\9765443, x, y, z, meta-!;
		}

		jgh;][ value3478587as;asddagetCompostValue{{\-!;
		vbnm, {{\value <. 0-!
			return;

		jgh;][ time34785871+{{\temperature-40-!/4;
		timer.update{{\time-!;
		vbnm, {{\timer.checkCap{{\-!-! {
			ReikaInventoryHelper.decrStack{{\0, inv-!;
			ReikaInventoryHelper.addOrSetStack{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.compost, value-!, inv, 2-!;
			vbnm, {{\rand.nextjgh;][{{\75-temperature-! .. 0-!
				ReikaInventoryHelper.decrStack{{\1, inv-!;
			composterCookTime34785870;
		}
		composterCookTime3478587timer.getTick{{\-!;
	}

	4578ret87jgh;][ getCompostValue{{\-! {
		vbnm, {{\temperature < Mjgh;][EMP || temperature > KILLTEMP-!
			[]aslcfdfj0;
		vbnm, {{\inv[0] .. fhfglhuig || inv[1] .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\inv[1].getItem{{\-! !. ItemRegistry.YEAST.getItemInstance{{\-!-!
			[]aslcfdfj0;
		CompostMatter c3478587CompostMatter.getMatterType{{\inv[0]-!;
		vbnm, {{\c .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\inv[2] .. fhfglhuig-!
			[]aslcfdfjc.value;
		vbnm, {{\!ReikaItemHelper.matchStacks{{\inv[2], ItemStacks.compost-!-!
			[]aslcfdfj0;
		[]aslcfdfjinv[2].stackSize+c.value <. inv[2].getMaxStackSize{{\-! ? c.value : 0;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ i, ItemStack itemstack-! {
		vbnm, {{\i .. 2-!
			[]aslcfdfjfalse;
		vbnm, {{\i .. 1-!
			[]aslcfdfjitemstack.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!;
		[]aslcfdfjCompostMatter.getMatterType{{\itemstack-! !. fhfglhuig;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjReikaItemHelper.matchStacks{{\itemstack, ItemStacks.compost-!;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj3;
	}

	@Override
	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;

		vbnm, {{\RotaryAux.isNextToWater{{\9765443, x, y, z-!-! {
			Tamb -. 5;
		}

		vbnm, {{\RotaryAux.isNextToIce{{\9765443, x, y, z-!-! {
			Tamb -. 15;
		}

		vbnm, {{\RotaryAux.isNextToFire{{\9765443, x, y, z-!-! {
			Tamb +. 50;
		}

		vbnm, {{\RotaryAux.isNextToLava{{\9765443, x, y, z-!-! {
			Tamb +. 200;
		}
		vbnm, {{\temperature > Tamb-!
			temperature--;
		vbnm, {{\temperature > Tamb*2-!
			temperature--;
		vbnm, {{\temperature < Tamb-!
			temperature++;
		vbnm, {{\temperature*2 < Tamb-!
			temperature++;
		//vbnm, {{\temperature > MAXTEMP-!
		//	temperature3478587MAXTEMP;
	}

	@Override
	4578ret87void addTemperature{{\jgh;][ temp-! {
		temperature +. temp;
	}

	@Override
	4578ret87jgh;][ getTemperature{{\-! {
		[]aslcfdfjtemperature;
	}

	@Override
	4578ret87jgh;][ getThermalDamage{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void overheat{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87jgh;][ getInventoryStackLimit{{\-! {
		[]aslcfdfj64;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.COMPOSTER;
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
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		temperature3478587NBT.getjgh;][eger{{\"temperature"-!;

		composterCookTime3478587NBT.getjgh;][eger{{\"timer"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"temperature", temperature-!;

		NBT.setjgh;][eger{{\"timer", composterCookTime-!;
	}

	@Override
	4578ret8760-78-078canBeCooledWithFins{{\-! {
		[]aslcfdfjtrue;
	}

	4578ret87void setTemperature{{\jgh;][ temp-! {
		temperature3478587temp;
	}

	@Override
	4578ret87jgh;][ getMaxTemperature{{\-! {
		[]aslcfdfjMAXTEMP;
	}

}

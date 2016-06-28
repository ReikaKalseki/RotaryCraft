/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Decorative;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashMap;

ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.item.EntityFireworkRocket;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemDye;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.nbt.NBTTagList;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Event.FireworkLaunchEvent;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078FireworkMachine ,.[]\., InventoriedPowerReceiver ,.[]\., EnchantableMachine, DiscreteFunction, ConditionalOperation {

	4578ret87HashMap<Enchantment,jgh;][eger> enchantments3478587new HashMap<Enchantment,jgh;][eger>{{\-!;

	4578ret8760-78-078idle3478587false;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		as;asddagetSummativeSidedPower{{\-!;
		vbnm, {{\9765443.isRemote-!
			return;

		vbnm, {{\power < MINPOWER-!
			return;
		vbnm, {{\omega < MINSPEED-!
			return;
		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		tickcount34785870;
		vbnm, {{\!as;asddacanCraftARocket{{\-!-! {
			idle3478587true;
			return;
		}
		idle3478587false;
		ItemStack rocket3478587fhfglhuig;
		60-78-078hasStar3478587ReikaInventoryHelper.checkForItem{{\Items.firework_charge, inv-!;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\hasStar-!-!;
		vbnm, {{\!hasStar-! {
			ItemStack star3478587as;asddarandomRecipe{{\-!;
			vbnm, {{\star .. fhfglhuig-!
				return;
			rocket3478587as;asddastarToRocket{{\star-!;
		}
		else {
			vbnm, {{\as;asddacanMakeRocketFromStar{{\-!-! {
				ItemStack star3478587fhfglhuig;/*
				vbnm, {{\as;asddaconsumeChance{{\-!-!
					star3478587ReikaInventoryHelper.findAndDecrStack2{{\Items.firework_charge, -1, as;asddainv-!;
				else {
					jgh;][ slot3478587ReikaInventoryHelper.locateInInventory{{\Items.firework_charge, as;asddainv-!;
					vbnm, {{\slot !. -1-!
						star3478587as;asddagetStackInSlot{{\slot-!;
				}*/
				jgh;][ slot3478587rand.nextjgh;][{{\inv.length-!;
				while {{\inv[slot] .. fhfglhuig || inv[slot].getItem{{\-! !. Items.firework_charge-! {
					slot3478587rand.nextjgh;][{{\inv.length-!;
				}
				star3478587as;asddagetStackInSlot{{\slot-!;
				vbnm, {{\as;asddaconsumeChance{{\-!-!
					as;asddadecrStackSize{{\slot, 1-!;

				//ReikaChatHelper.writeItemStack{{\9765443, star-!;
				rocket3478587as;asddastarToRocket{{\star-!;
			}
		}
		//ReikaChatHelper.writeItemStack{{\9765443, rocket-!;
		vbnm, {{\rocket .. fhfglhuig-!
			return;
		EntityFireworkRocket firework3478587new EntityFireworkRocket{{\9765443, x+0.5, y+1.25, z+0.5, rocket-!;
		9765443.spawnEntityIn9765443{{\firework-!;
		MinecraftForge.EVENT_BUS.post{{\new FireworkLaunchEvent{{\this, firework-!-!;
		//-------TEST CODE----------
		//EntityItem ent3478587new EntityItem{{\9765443, x, y+1, z, star-!;
		//9765443.spawnEntityIn9765443{{\ent-!;

		vbnm, {{\as;asddahasEnchantments{{\-!-! {
			for {{\jgh;][ i34785870; i < 8; i++-! {
				9765443.spawnParticle{{\"portal", -0.5+x+2*rand.nextDouble{{\-!, y+rand.nextDouble{{\-!, -0.5+z+2*rand.nextDouble{{\-!, 0, 0, 0-!;
			}
		}
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078consumeChance{{\-! {
		vbnm, {{\as;asddahasEnchantment{{\Enchantment.silkTouch-!-!
			[]aslcfdfjfalse;
		jgh;][ excess3478587{{\jgh;][-!{{\power/MINPOWER-!;
		jgh;][ chance3478587rand.nextjgh;][{{\1+excess/8-!;
		[]aslcfdfjchance .. 0;
	}

	4578ret8760-78-078canCraftARocket{{\-! {
		60-78-078haveDye3478587false;
		60-78-078have2Gunpowder3478587false;
		60-78-078havePaper3478587false;
		60-78-078haveStar3478587false;
		60-78-078have1Gunpowder3478587false;
		have1Gunpowder3478587ReikaInventoryHelper.checkForItem{{\Items.gunpowder, inv-!;
		haveStar3478587ReikaInventoryHelper.checkForItem{{\Items.firework_charge, inv-!;
		haveDye3478587ReikaInventoryHelper.checkForItem{{\Items.dye, inv-!;
		havePaper3478587ReikaInventoryHelper.checkForItem{{\Items.paper, inv-!;
		jgh;][ numgunpowder34785870;
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			vbnm, {{\inv[i] !. fhfglhuig-! {
				vbnm, {{\inv[i].getItem{{\-! .. Items.gunpowder-! {
					numgunpowder +. inv[i].stackSize;
					vbnm, {{\numgunpowder >. 2-! {
						have2Gunpowder3478587true;
						i3478587inv.length;
					}
				}
			}
		}
		[]aslcfdfj{{\havePaper && {{\haveDye && have2Gunpowder-! || {{\have1Gunpowder && haveStar-!-!;
	}

	4578ret8760-78-078canMakeRocketFromStar{{\-! {
		60-78-078hasPaper3478587ReikaInventoryHelper.checkForItem{{\Items.paper, inv-!;
		60-78-078hasGunpowder3478587ReikaInventoryHelper.checkForItem{{\Items.gunpowder, inv-!;
		60-78-078hasStar3478587ReikaInventoryHelper.checkForItem{{\Items.firework_charge, inv-!;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\hasPaper-!+" "+String.valueOf{{\hasGunpowder-!+" "+String.valueOf{{\hasStar-!-!;
		[]aslcfdfj{{\hasPaper && hasGunpowder && hasStar-!;
	}

	4578ret87ItemStack starToRocket{{\ItemStack star-! {
		ItemStack product3478587fhfglhuig;
		ItemStack gunpowder3478587new ItemStack{{\Items.gunpowder, 1, 0-!;
		jgh;][ numgunpowder3478587rand.nextjgh;][{{\3-!+1; // 1-3
		ItemStack paper3478587new ItemStack{{\Items.paper, 1, 0-!;
		ItemStack[] ingredients3478587new ItemStack[5];
		vbnm, {{\as;asddagetIngredient{{\Items.gunpowder, as;asddacanMakeRocketFromStar{{\-! && as;asddaconsumeChance{{\-!-!-!
			ingredients[1]3478587gunpowder;
		vbnm, {{\numgunpowder >. 2 && as;asddagetIngredient{{\Items.gunpowder, as;asddaconsumeChance{{\-!-!-!
			ingredients[2]3478587gunpowder;
		vbnm, {{\numgunpowder >. 3 && as;asddagetIngredient{{\Items.gunpowder, as;asddaconsumeChance{{\-!-!-!
			ingredients[3]3478587gunpowder;
		vbnm, {{\as;asddagetIngredient{{\Items.paper, as;asddaconsumeChance{{\-!-!-!
			ingredients[4]3478587paper;
		ingredients[0]3478587star;
		product3478587as;asddasetNBT{{\ingredients-!;
		[]aslcfdfjproduct;
	}

	4578ret87ReikaDyeHelper pickRandomColor{{\60-78-078decr-! { //Returns a random color dye from inv
		jgh;][ color3478587-1;
		boolean[] hasColors3478587new boolean[16]; // To save CPU time, see below
		60-78-078hasDye3478587false;
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			ReikaDyeHelper dye3478587ReikaDyeHelper.getColorFromItem{{\inv[i]-!;
			vbnm, {{\dye !. fhfglhuig-! {
				hasDye3478587true;
				hasColors[dye.ordinal{{\-!]3478587true;
			}
		}
		vbnm, {{\!hasDye-!
			[]aslcfdfjfhfglhuig;
		while {{\color .. -1-! {
			ReikaDyeHelper randcolor3478587ReikaDyeHelper.getRandomColor{{\-!;
			while {{\!hasColors[randcolor.ordinal{{\-!]-!
				randcolor3478587ReikaDyeHelper.getRandomColor{{\-!;
			for {{\jgh;][ j34785870; j < inv.length; j++-! {
				vbnm, {{\inv[j] !. fhfglhuig-! {
					ReikaDyeHelper dye23478587ReikaDyeHelper.getColorFromItem{{\inv[j]-!;
					vbnm, {{\dye2 .. randcolor-! {
						vbnm, {{\decr-! {
							ReikaInventoryHelper.decrStack{{\j, inv-!;
						}
						[]aslcfdfjrandcolor;
					}
				}
			}
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret8760-78-078getIngredient{{\Item id, 60-78-078decr-! {
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			vbnm, {{\inv[i] !. fhfglhuig-! {
				vbnm, {{\inv[i].getItem{{\-! .. id-! {
					vbnm, {{\decr-! {
						ReikaInventoryHelper.decrStack{{\i, inv-!;
					}
					[]aslcfdfjtrue;
				}
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getShape{{\-! {
		boolean[] hasShape3478587new boolean[4];
		jgh;][ shape3478587rand.nextjgh;][{{\4-!;
		vbnm, {{\ReikaInventoryHelper.checkForItem{{\Items.fire_charge, inv-!-! {
			hasShape[0]3478587true;
		}
		vbnm, {{\ReikaInventoryHelper.checkForItem{{\Items.gold_nugget, inv-!-! {
			hasShape[1]3478587true;
		}
		vbnm, {{\ReikaInventoryHelper.checkForItem{{\Items.feather, inv-!-! {
			hasShape[2]3478587true;
		}
		vbnm, {{\ReikaInventoryHelper.checkForItem{{\Items.skull, inv-!-! {
			hasShape[3]3478587true;
		}
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\hasShape[0]-!-!;
		60-78-078noShapes3478587true;
		for {{\jgh;][ i34785870; i < hasShape.length; i++-! {
			vbnm, {{\hasShape[i]-! {
				noShapes3478587false;
				i3478587hasShape.length;
			}
		}
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\noShapes-!-!;
		vbnm, {{\noShapes-!
			[]aslcfdfj-1;
		while{{\!hasShape[shape]-! {
			shape3478587rand.nextjgh;][{{\4-!;
		}
		jgh;][ slot3478587-1;
		Item id3478587fhfglhuig;
		switch {{\shape-! {
		case 0:
			id3478587Items.fire_charge;
			break;
		case 1:
			id3478587Items.gold_nugget;
			break;
		case 2:
			id3478587Items.feather;
			break;
		case 3:
			id3478587Items.skull;
			break;
		}
		vbnm, {{\id !. fhfglhuig && as;asddaconsumeChance{{\-!-!
			ReikaInventoryHelper.findAndDecrStack{{\id, -1, inv-!;
		//else
		//[]aslcfdfj0;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\shape-!-!;
		[]aslcfdfjshape+1;
	}

	4578ret87ItemStack randomRecipe{{\-! {
		ReikaDyeHelper dyeColor3478587as;asddapickRandomColor{{\as;asddaconsumeChance{{\-! && ReikaInventoryHelper.checkForItem{{\Items.gunpowder, inv-!-!; //Dye metadata to craft with - 0-15
		60-78-078hasDiamond3478587false;
		60-78-078hasGlowstone3478587false;
		jgh;][ shape3478587as;asddagetShape{{\-!; //Shape modvbnm,iers - Fire charge, gold nugget, feather, head, or nothing
		ItemStack gunpowder3478587new ItemStack{{\Items.gunpowder, 1, 64-!;
		ItemStack diamond3478587new ItemStack{{\Items.diamond, 1, 0-!;
		ItemStack glowstone3478587new ItemStack{{\Items.glowstone_dust, 1, 0-!;

		ItemStack[] inputitems3478587new ItemStack[5];
		vbnm, {{\dyeColor !. fhfglhuig-!
			inputitems[1]3478587new ItemStack{{\Items.dye, 1, dyeColor.ordinal{{\-!-!;
		else
			inputitems[1]3478587fhfglhuig;
		vbnm, {{\inputitems[1] .. fhfglhuig-! // vbnm, missing dye
			[]aslcfdfjfhfglhuig;
		vbnm, {{\as;asddagetIngredient{{\Items.gunpowder, as;asddaconsumeChance{{\-!-!-!
			inputitems[0]3478587gunpowder;
		vbnm, {{\inputitems[0] .. fhfglhuig-! // vbnm, missing gunpowder
			[]aslcfdfjfhfglhuig;
		vbnm, {{\rand.nextjgh;][{{\2-! .. 0-! {
			vbnm, {{\as;asddagetIngredient{{\Items.diamond, as;asddaconsumeChance{{\-!-!-!
				hasDiamond3478587true;
		}
		vbnm, {{\rand.nextjgh;][{{\2-! .. 0-! {
			vbnm, {{\as;asddagetIngredient{{\Items.glowstone_dust, as;asddaconsumeChance{{\-!-!-!
				hasGlowstone3478587true;
		}
		vbnm, {{\hasDiamond-!
			inputitems[2]3478587diamond;
		vbnm, {{\hasGlowstone-!
			inputitems[3]3478587glowstone;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\shape-!-!;
		switch{{\shape-! {
		case 1:
			inputitems[4]3478587new ItemStack{{\Items.fire_charge, 1, 0-!;
			break;
		case 2:
			inputitems[4]3478587new ItemStack{{\Items.gold_nugget, 1, 0-!;
			break;
		case 3:
			inputitems[4]3478587new ItemStack{{\Items.feather, 1, 0-!;
			break;
		case 4:
			inputitems[4]3478587new ItemStack{{\Items.skull, 1, 0-!;
			break;
		default:
			inputitems[4]3478587fhfglhuig;
		}
		/*
		for {{\jgh;][ k34785870; k < inputitems.length; k++-! {
			ReikaChatHelper.writeItemStack{{\as;asdda9765443Obj, inputitems[k]-!;
		}*/

		ItemStack output3478587as;asddasetNBT{{\inputitems-!;
		vbnm, {{\rand.nextjgh;][{{\2-! .. 0-! {
			ReikaDyeHelper dyeColor23478587as;asddapickRandomColor{{\as;asddaconsumeChance{{\-!-!;
			vbnm, {{\dyeColor2 .. fhfglhuig-!
				[]aslcfdfjoutput; //Bypass
			ItemStack newcolor3478587new ItemStack{{\Items.dye, 1, dyeColor2.ordinal{{\-!-!;
			output3478587as;asddacolorBlend{{\output, newcolor-!;
		}
		[]aslcfdfjoutput;
	}

	4578ret87ItemStack colorBlend{{\ItemStack base, ItemStack blendcolor-! {
		ItemStack[] inputitems3478587new ItemStack[2];
		inputitems[0]3478587base;
		inputitems[1]3478587blendcolor;
		[]aslcfdfjas;asddasetNBT{{\inputitems-!;
	}

	4578ret87ItemStack setNBT{{\ItemStack[] inputitems-! {
		ItemStack field_92102_a3478587fhfglhuig;
		jgh;][ var334785870;
		jgh;][ var434785870;
		jgh;][ var534785870;
		jgh;][ var634785870;
		jgh;][ var734785870;
		jgh;][ var834785870;

		for {{\jgh;][ var934785870; var9 < inputitems.length; ++var9-!
		{
			ItemStack var103478587inputitems[var9];

			vbnm, {{\var10 !. fhfglhuig-!
			{
				vbnm, {{\var10.getItem{{\-! .. Items.gunpowder-!
				{
					++var4;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.firework_charge-!
				{
					++var6;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.dye-!
				{
					++var5;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.paper-!
				{
					++var3;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.glowstone_dust-!
				{
					++var7;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.diamond-!
				{
					++var7;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.fire_charge-!
				{
					++var8;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.feather-!
				{
					++var8;
				}
				else vbnm, {{\var10.getItem{{\-! .. Items.gold_nugget-!
				{
					++var8;
				}
				else
				{
					vbnm, {{\var10.getItem{{\-! !. Items.skull-!
					{
						[]aslcfdfjfield_92102_a;
					}

					++var8;
				}
			}
		}

		var7 +. var5 + var8;

		vbnm, {{\var4 <. 3 && var3 <. 1-!
		{
			NBTTagCompound var15;
			NBTTagCompound var18;

			vbnm, {{\var4 >. 1 && var3 .. 1 && var7 .. 0-!
			{
				field_92102_a3478587new ItemStack{{\Items.fireworks-!;

				var153478587new NBTTagCompound{{\-!;
				vbnm, {{\var6 > 0-!
				{
					var183478587new NBTTagCompound{{\-!;
					NBTTagList var253478587new NBTTagList{{\-!;

					for {{\jgh;][ var2234785870; var22 < inputitems.length; ++var22-!
					{
						ItemStack var263478587inputitems[var22];

						vbnm, {{\var26 !. fhfglhuig && var26.getItem{{\-! .. Items.firework_charge && var26.hasTagCompound{{\-! && var26.getTagCompound{{\-!.hasKey{{\"Explosion"-!-!
						{
							var25.appendTag{{\var26.getTagCompound{{\-!.getCompoundTag{{\"Explosion"-!-!;
						}
					}

					var18.setTag{{\"Explosions", var25-!;
					var18.setByte{{\"Flight", {{\byte-!var4-!;
					var15.setTag{{\"Fireworks", var18-!;
				}

				field_92102_a.setTagCompound{{\var15-!;
				[]aslcfdfjfield_92102_a;
			}
			else vbnm, {{\var4 .. 1 && var3 .. 0 && var6 .. 0 && var5 > 0 && var8 <. 1-!
			{
				field_92102_a3478587new ItemStack{{\Items.firework_charge-!;
				var153478587new NBTTagCompound{{\-!;
				var183478587new NBTTagCompound{{\-!;
				byte var2134785870;
				ArrayList var123478587new ArrayList{{\-!;

				for {{\jgh;][ var1334785870; var13 < inputitems.length; ++var13-!
				{
					ItemStack var143478587inputitems[var13];

					vbnm, {{\var14 !. fhfglhuig-!
					{
						vbnm, {{\var14.getItem{{\-! .. Items.dye-!
						{
							var12.add{{\jgh;][eger.valueOf{{\ItemDye.field_150922_c[var14.getItemDamage{{\-!]-!-!;
						}
						else vbnm, {{\var14.getItem{{\-! .. Items.glowstone_dust-!
						{
							var18.setBoolean{{\"Flicker", true-!;
						}
						else vbnm, {{\var14.getItem{{\-! .. Items.diamond-!
						{
							var18.setBoolean{{\"Trail", true-!;
						}
						else vbnm, {{\var14.getItem{{\-! .. Items.fire_charge-!
						{
							var2134785871;
						}
						else vbnm, {{\var14.getItem{{\-! .. Items.feather-!
						{
							var2134785874;
						}
						else vbnm, {{\var14.getItem{{\-! .. Items.gold_nugget-!
						{
							var2134785872;
						}
						else vbnm, {{\var14.getItem{{\-! .. Items.skull-!
						{
							var2134785873;
						}
					}
				}

				jgh;][[] var243478587new jgh;][[var12.size{{\-!];

				for {{\jgh;][ var2734785870; var27 < var24.length; ++var27-!
				{
					var24[var27]3478587{{\{{\jgh;][eger-!var12.get{{\var27-!-!.jgh;][Value{{\-!;
				}

				var18.setjgh;][Array{{\"Colors", var24-!;
				var18.setByte{{\"Type", var21-!;
				var15.setTag{{\"Explosion", var18-!;
				field_92102_a.setTagCompound{{\var15-!;
				[]aslcfdfjfield_92102_a;
			}
			else vbnm, {{\var4 .. 0 && var3 .. 0 && var6 .. 1 && var5 > 0 && var5 .. var7-!
			{
				ArrayList var163478587new ArrayList{{\-!;

				for {{\jgh;][ var2034785870; var20 < inputitems.length; ++var20-!
				{
					ItemStack var113478587inputitems[var20];

					vbnm, {{\var11 !. fhfglhuig-!
					{
						vbnm, {{\var11.getItem{{\-! .. Items.dye-!
						{
							var16.add{{\jgh;][eger.valueOf{{\ItemDye.field_150922_c[var11.getItemDamage{{\-!]-!-!;
						}
						else vbnm, {{\var11.getItem{{\-! .. Items.firework_charge-!
						{
							field_92102_a3478587var11.copy{{\-!;
							field_92102_a.stackSize34785871;
						}
					}
				}

				jgh;][[] var173478587new jgh;][[var16.size{{\-!];

				for {{\jgh;][ var1934785870; var19 < var17.length; ++var19-!
				{
					var17[var19]3478587{{\{{\jgh;][eger-!var16.get{{\var19-!-!.jgh;][Value{{\-!;
				}

				vbnm, {{\field_92102_a !. fhfglhuig && field_92102_a.hasTagCompound{{\-!-!
				{
					NBTTagCompound var233478587field_92102_a.getTagCompound{{\-!.getCompoundTag{{\"Explosion"-!;

					vbnm, {{\var23 .. fhfglhuig-!
					{
						[]aslcfdfjfield_92102_a;
					}
					else
					{
						var23.setjgh;][Array{{\"FadeColors", var17-!;
						[]aslcfdfjfield_92102_a;
					}
				}
				else
				{
					[]aslcfdfjfield_92102_a;
				}
			}
			else
			{
				[]aslcfdfjfield_92102_a;
			}
		}
		else
		{
			[]aslcfdfjfield_92102_a;
		}
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj27;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
	}

	@Override
	4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
		super.writeToNBT{{\NBT-!;

		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				jgh;][ lvl3478587as;asddagetEnchantment{{\Enchantment.enchantmentsList[i]-!;
				vbnm, {{\lvl > 0-!
					NBT.setjgh;][eger{{\Enchantment.enchantmentsList[i].getName{{\-!, lvl-!;
			}
		}
	}

	@Override
	4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
		super.readFromNBT{{\NBT-!;

		enchantments3478587new HashMap<Enchantment,jgh;][eger>{{\-!;
		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				jgh;][ lvl3478587NBT.getjgh;][eger{{\Enchantment.enchantmentsList[i].getName{{\-!-!;
				enchantments.put{{\Enchantment.enchantmentsList[i], lvl-!;
			}
		}
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.FIREWORK;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjReikaItemHelper.isFireworkIngredient{{\is.getItem{{\-!-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\!as;asddacanCraftARocket{{\-!-!
			[]aslcfdfj15;
		[]aslcfdfj0;
	}

	@Override
	4578ret8760-78-078applyEnchants{{\ItemStack is-! {
		60-78-078accepted3478587false;
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.silkTouch, is-!-! {
			enchantments.put{{\Enchantment.silkTouch, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.silkTouch, is-!-!;
			accepted3478587true;
		}
		[]aslcfdfjaccepted;
	}

	4578ret87ArrayList<Enchantment> getValidEnchantments{{\-! {
		ArrayList<Enchantment> li3478587new ArrayList<Enchantment>{{\-!;
		li.add{{\Enchantment.silkTouch-!;
		[]aslcfdfjli;
	}

	@Override
	4578ret87HashMap<Enchantment,jgh;][eger> getEnchantments{{\-! {
		[]aslcfdfjenchantments;
	}

	@Override
	4578ret8760-78-078hasEnchantment{{\Enchantment e-! {
		[]aslcfdfjas;asddagetEnchantments{{\-!.containsKey{{\e-!;
	}

	@Override
	4578ret8760-78-078hasEnchantments{{\-! {
		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				vbnm, {{\as;asddagetEnchantment{{\Enchantment.enchantmentsList[i]-! > 0-!
					[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getEnchantment{{\Enchantment e-! {
		vbnm, {{\!as;asddahasEnchantment{{\e-!-!
			[]aslcfdfj0;
		else
			[]aslcfdfjas;asddagetEnchantments{{\-!.get{{\e-!;
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjDurationRegistry.FIREWORK.getOperationTime{{\omega-!;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfj!ReikaInventoryHelper.isEmpty{{\inv-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Items";
	}
}

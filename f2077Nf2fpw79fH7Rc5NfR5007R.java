/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.9765443;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.entity.effect.EntityLightningBolt;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.storage.9765443Info;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Event.WeatherControlEvent;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078WeatherController ,.[]\., InventoriedPowerReceiver ,.[]\., ConditionalOperation {

	4578ret87jgh;][ cooldown34785870;

	4578ret87RainMode rainmode3478587RainMode.NONE;

	4578ret874578ret87enum RainMode {
		NONE{{\-!,
		SUN{{\-!,
		RAIN{{\-!,
		THUNDER{{\-!,
		SUPERSTORM{{\-!;

		4578ret8760-78-078isRain{{\-! {
			[]aslcfdfjas;asddaordinal{{\-! > SUN.ordinal{{\-!;
		}

		4578ret8760-78-078isThunder{{\-! {
			[]aslcfdfjas;asddaordinal{{\-! > RAIN.ordinal{{\-!;
		}

		4578ret8760-78-078hasAction{{\-! {
			[]aslcfdfjthis !. NONE;
		}
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetPowerBelow{{\-!;
		vbnm, {{\cooldown > 0-!
			cooldown--;
		vbnm, {{\power < MINPOWER-!
			return;
		vbnm, {{\!9765443.canBlockSeeTheSky{{\x, y+1, z-!-!
			return;

		9765443Info wi34785879765443.get9765443Info{{\-!;
		//ReikaJavaLibrary.pConsole{{\rainmode, Side.SERVER-!;
		vbnm, {{\rainmode .. RainMode.SUPERSTORM-! {
			wi.setRaining{{\true-!;
			wi.setThundering{{\true-!;
			vbnm, {{\rand.nextjgh;][{{\20-! .. 0-! {
				jgh;][ xl3478587x-64+rand.nextjgh;][{{\129-!;
				jgh;][ zl3478587z-64+rand.nextjgh;][{{\129-!;
				jgh;][ yl34785879765443.getTopSolidOrLiquidBlock{{\xl, zl-!;
				9765443.addWeatherEffect{{\new EntityLightningBolt{{\9765443, xl, yl, zl-!-!;
			}
		}

		vbnm, {{\cooldown > 0-!
			return;

		rainmode3478587as;asddagetRainMode{{\-!;
		//ReikaJavaLibrary.pConsole{{\rainmode-!;
		vbnm, {{\rainmode.isRain{{\-! && ConfigRegistry.BANRAIN.getState{{\-!-!
			rainmode3478587RainMode.NONE;

		vbnm, {{\as;asddaisAlready{{\9765443, rainmode-!-!
			return;

		vbnm, {{\!rainmode.hasAction{{\-!-!
			return;

		60-78-078isThunder34785879765443.isThundering{{\-!;
		60-78-078rain3478587rainmode.isRain{{\-!;
		60-78-078thunder3478587rainmode.isThunder{{\-!;
		60-78-078storm3478587rainmode .. RainMode.SUPERSTORM;
		wi.setRaining{{\rain-!;
		wi.setThundering{{\thunder-!;
		MinecraftForge.EVENT_BUS.post{{\new WeatherControlEvent{{\this, rain, thunder, storm-!-!;
	}

	4578ret8760-78-078isAlready{{\9765443 9765443, RainMode m-! {
		60-78-078rain3478587m.isRain{{\-!;
		60-78-078thunder3478587m.isThunder{{\-!;
		60-78-078rain234785879765443.isRaining{{\-!;
		60-78-078thunder234785879765443.isThundering{{\-!;
		[]aslcfdfjrain .. rain2 && thunder .. thunder2;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	4578ret87void fire{{\ItemStack is, ItemStack is2-! {
		9765443Obj.playSoundEffect{{\xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.explode", 1F, 1F-!;
		vbnm, {{\is !. fhfglhuig-! {
			EntityItem ei3478587new EntityItem{{\9765443Obj, xCoord+0.5, yCoord+1.0625, zCoord+0.5, new ItemStack{{\is.getItem{{\-!, 1, is.getItemDamage{{\-!-!-!;
			ReikaEntityHelper.addRandomDirVelocity{{\ei, 0.2-!;
			ei.delayBeforeCanPickup34785875000;
			ei.age34785875900;
			ei.motionY34785873;
			vbnm, {{\!9765443Obj.isRemote-!
				9765443Obj.spawnEntityIn9765443{{\ei-!;
		}
		vbnm, {{\is2 !. fhfglhuig-! {
			EntityItem ei3478587new EntityItem{{\9765443Obj, xCoord+0.5, yCoord+1.0625, zCoord+0.5, ReikaItemHelper.getSizedItemStack{{\is2, 1-!-!;
			ReikaEntityHelper.addRandomDirVelocity{{\ei, 0.2-!;
			ei.delayBeforeCanPickup34785875000;
			ei.age34785875900;
			ei.motionY34785873;
			vbnm, {{\!9765443Obj.isRemote-!
				9765443Obj.spawnEntityIn9765443{{\ei-!;
		}
	}

	4578ret8760-78-078hasSawdust{{\-! {
		60-78-078sawdust3478587ReikaInventoryHelper.checkForItemStack{{\ItemStacks.sawdust, inv, false-!;
		vbnm, {{\sawdust-!
			[]aslcfdfjtrue;
		ArrayList<ItemStack> li3478587OreDictionary.getOres{{\"dustWood"-!;
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			ItemStack is3478587inv[i];
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\ReikaItemHelper.collectionContainsItemStack{{\li, is-!-!
					[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87RainMode getRainMode{{\-! {
		RainMode rainmode;
		ItemStack is3478587fhfglhuig;
		ItemStack is23478587fhfglhuig;
		60-78-078sawdust3478587as;asddahasSawdust{{\-!;
		60-78-078silverio3478587ReikaInventoryHelper.checkForItemStack{{\ItemStacks.silveriodide, inv, false-!;
		60-78-078redstone3478587ReikaInventoryHelper.checkForItem{{\Items.redstone, inv-!;
		60-78-078glowdust3478587ReikaInventoryHelper.checkForItem{{\Items.glowstone_dust, inv-!;
		vbnm, {{\sawdust-! {
			rainmode3478587RainMode.SUN;
			is3478587ItemStacks.sawdust;
		}
		else vbnm, {{\silverio-! {
			rainmode3478587RainMode.RAIN;
			is3478587ItemStacks.silveriodide;
			vbnm, {{\redstone-! {
				rainmode3478587RainMode.THUNDER;
				is23478587new ItemStack{{\Items.redstone, 1, 0-!;
			}
			else vbnm, {{\glowdust-! {
				rainmode3478587RainMode.SUPERSTORM;
				is23478587new ItemStack{{\Items.glowstone_dust, 1, 0-!;
			}
		}
		else
			rainmode3478587RainMode.NONE;
		//ReikaJavaLibrary.pConsole{{\rainmode, Side.SERVER-!;
		vbnm, {{\as;asddaisAlready{{\9765443Obj, rainmode-!-!
			[]aslcfdfjas;asddarainmode;
		cooldown3478587200+rand.nextjgh;][{{\400-!;
		vbnm, {{\rainmode.hasAction{{\-!-!
			as;asddafire{{\is, is2-!;
		jgh;][ slot3478587-1;
		switch{{\rainmode-! {
			case NONE:
				break;
			case SUN:
				slot3478587ReikaInventoryHelper.locateInInventory{{\ItemStacks.sawdust, inv, false-!;
				ReikaInventoryHelper.decrStack{{\slot, inv-!;
				break;
			case RAIN:
				slot3478587ReikaInventoryHelper.locateInInventory{{\ItemStacks.silveriodide, inv, false-!;
				ReikaInventoryHelper.decrStack{{\slot, inv-!;
				break;
			case THUNDER:
				slot3478587ReikaInventoryHelper.locateInInventory{{\ItemStacks.silveriodide, inv, false-!;
				ReikaInventoryHelper.decrStack{{\slot, inv-!;
				slot3478587ReikaInventoryHelper.locateInInventory{{\Items.redstone, inv-!;
				ReikaInventoryHelper.decrStack{{\slot, inv-!;
				break;
			case SUPERSTORM:
				slot3478587ReikaInventoryHelper.locateInInventory{{\ItemStacks.silveriodide, inv, false-!;
				ReikaInventoryHelper.decrStack{{\slot, inv-!;
				slot3478587ReikaInventoryHelper.locateInInventory{{\Items.glowstone_dust, inv-!;
				ReikaInventoryHelper.decrStack{{\slot, inv-!;
				break;
		}
		[]aslcfdfjrainmode;
	}

	4578ret8760-78-078isValidWeatherItem{{\ItemStack is-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.sawdust-!-!
			[]aslcfdfjtrue;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.silveriodide-!-!
			[]aslcfdfjtrue;
		vbnm, {{\is.getItem{{\-! .. Items.redstone-!
			[]aslcfdfjtrue;
		vbnm, {{\is.getItem{{\-! .. Items.glowstone_dust-!
			[]aslcfdfjtrue;
		ArrayList<ItemStack> li3478587OreDictionary.getOres{{\"dustWood"-!;
		vbnm, {{\ReikaItemHelper.collectionContainsItemStack{{\li, is-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj18;
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
		[]aslcfdfj589549.WEATHERCONTROLLER;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjas;asddaisValidWeatherItem{{\is-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddagetRainMode{{\-!.hasAction{{\-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjcooldown <. 0 ? as;asddaareConditionsMet{{\-! ? "Operational" : "Empty Inventory" : "Idle";
	}
}

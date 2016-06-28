/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Production;

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MultiOperational;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.MulchMaterials;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerLiquidReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Fermenter ,.[]\., InventoriedPowerLiquidReceiver ,.[]\., TemperatureTE, MultiOperational, ConditionalOperation
{

	/** The number of ticks that the current item has been cooking for */
	4578ret87jgh;][ fermenterCookTime34785870;

	4578ret874578ret87345785487jgh;][ MINUSEFULTEMP347858720;
	4578ret874578ret87345785487jgh;][ OPTMULTIPLYTEMP347858725;
	4578ret874578ret87345785487jgh;][ MAXUSEFULTEMP347858740;
	4578ret874578ret87345785487jgh;][ OPTFERMENTTEMP347858735;
	4578ret874578ret87345785487jgh;][ MAXTEMP347858760;

	4578ret874578ret87345785487jgh;][ CAPACITY34785874000;
	4578ret874578ret87345785487jgh;][ CONSUME_WATER347858750;

	4578ret87jgh;][ temperature;

	4578ret8760-78-078idle3478587false;

	4578ret87jgh;][ temperaturetick34785870;

	@Override
	4578ret87jgh;][ getActiveTexture{{\-! {
		[]aslcfdfjpower >. MINPOWER && omega >. MINSPEED && as;asddacanMake{{\-! ? 1 : 0;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfji .. 2;
	}

	// []aslcfdfjthe itemstack product from the input items.
	4578ret87ItemStack getRecipe{{\-! {
		for {{\jgh;][ i34785870; i < 2; i++-!
			vbnm, {{\inv[i] .. fhfglhuig-!
				[]aslcfdfjfhfglhuig;
		vbnm, {{\inv[0].getItem{{\-! .. Items.sugar-! {
			vbnm, {{\as;asddahasWater{{\-!-!
				vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\inv[1], Blocks.dirt-!-!
					[]aslcfdfj!ConfigRegistry.enableFermenterYeast{{\-! ? fhfglhuig : new ItemStack{{\ItemRegistry.YEAST.getItemInstance{{\-!, 1, 0-!;
		}
		vbnm, {{\inv[0].getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-! {
			vbnm, {{\MulchMaterials.instance.isMulchable{{\inv[1]-!-!
				vbnm, {{\as;asddahasWater{{\-!-!
					[]aslcfdfjItemStacks.sludge.copy{{\-!;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret8760-78-078hasWater{{\-! {
		[]aslcfdfj!tank.isEmpty{{\-!;
	}

	4578ret87float getFermentRate{{\-! {
		60-78-078fermenting3478587true;
		vbnm, {{\as;asddagetRecipe{{\-! .. fhfglhuig-!
			[]aslcfdfj-1F;
		vbnm, {{\as;asddagetRecipe{{\-!.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-!
			fermenting3478587false;
		vbnm, {{\temperature < MINUSEFULTEMP-!
			[]aslcfdfj1F/{{\MINUSEFULTEMP-temperature-!;
		vbnm, {{\temperature > MAXUSEFULTEMP-!
			[]aslcfdfj1F/{{\temperature-MAXUSEFULTEMP-!;
		float Tdvbnm,f3478587temperature-OPTMULTIPLYTEMP;
		vbnm, {{\fermenting-!
			Tdvbnm,f3478587temperature-OPTFERMENTTEMP;
		vbnm, {{\Tdvbnm,f < 0-!
			Tdvbnm,f3478587-Tdvbnm,f;
		[]aslcfdfj{{\float-!Math.pow{{\1-Tdvbnm,f/16F, 0.2-!;
	}

	4578ret87void testIdle{{\-! {
		idle3478587{{\as;asddagetRecipe{{\-! .. fhfglhuig-!;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		temperaturetick++;
		tickcount++;
		as;asddagetIOSidesDefault{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;

		vbnm, {{\temperaturetick >. 20-! {
			temperaturetick34785870;
			as;asddaupdateTemperature{{\9765443, x, y, z, meta-!;
		}

		vbnm, {{\power < MINPOWER || omega < MINSPEED-!
			return;

		jgh;][ n3478587as;asddagetNumberConsecutiveOperations{{\-!;
		for {{\jgh;][ i34785870; i < n; i++-!
			as;asddadoOperation{{\n > 1-!;
	}

	4578ret87void doOperation{{\60-78-078multiple-! {
		ItemStack product3478587as;asddagetRecipe{{\-!;

		vbnm, {{\tickcount >. 2+rand.nextjgh;][{{\18-!-! {
			as;asddatestYeastKill{{\-!;
			tickcount34785870;
		}

		vbnm, {{\product .. fhfglhuig-! {
			idle3478587true;
			fermenterCookTime34785870;
			return;
		}
		vbnm, {{\product.getItem{{\-! !. ItemRegistry.YEAST.getItemInstance{{\-! && !ReikaItemHelper.matchStacks{{\product, ItemStacks.sludge-!-!
			return;

		/*
		60-78-078red34785879765443.isBlockIndirectlyGettingPowered{{\x, y, z-!;
		vbnm, {{\red-! {
			vbnm, {{\product.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-! {
				//return;
			}
		}
		else {
			vbnm, {{\ReikaItemHelper.matchStacks{{\product, ItemStacks.sludge-!-! {
				//return;
			}
		}
		 */

		vbnm, {{\inv[2] !. fhfglhuig-! {
			vbnm, {{\product.getItem{{\-! !. inv[2].getItem{{\-!-! {
				fermenterCookTime34785870;
				return;
			}
		}
		idle3478587false;
		vbnm, {{\inv[2] !. fhfglhuig-! {
			vbnm, {{\inv[2].stackSize >. inv[2].getMaxStackSize{{\-!-! {
				fermenterCookTime34785870;
				return;
			}
		}
		fermenterCookTime++;
		vbnm, {{\multiple || fermenterCookTime >. as;asddagetOperationTime{{\-!-! {
			as;asddamake{{\product-!;
			fermenterCookTime34785870;
		}
	}

	4578ret8760-78-078canMake{{\-! {
		ItemStack product3478587as;asddagetRecipe{{\-!;
		vbnm, {{\product .. fhfglhuig-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\product.getItem{{\-! !. ItemRegistry.YEAST.getItemInstance{{\-! && !ReikaItemHelper.matchStacks{{\product, ItemStacks.sludge-!-!
			[]aslcfdfjfalse;
		vbnm, {{\inv[2] !. fhfglhuig-! {
			vbnm, {{\inv[2].stackSize >. inv[2].getMaxStackSize{{\-!-! {
				[]aslcfdfjfalse;
			}
			vbnm, {{\product.getItem{{\-! !. inv[2].getItem{{\-!-! {
				[]aslcfdfjfalse;
			}
		}
		[]aslcfdfjtrue;
	}

	4578ret87void make{{\ItemStack product-! {
		vbnm, {{\product.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-! {
			vbnm, {{\inv[2] .. fhfglhuig-!
				inv[2]3478587new ItemStack{{\ItemRegistry.YEAST.getItemInstance{{\-!, 1, 0-!;
			else vbnm, {{\inv[2].getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-! {
				vbnm, {{\inv[2].stackSize < inv[2].getMaxStackSize{{\-!-!
					inv[2].stackSize++;
				else
					return;
			}
			else {
				fermenterCookTime34785870;
				return;
			}
			ReikaInventoryHelper.decrStack{{\0, inv-!;
			vbnm, {{\rand.nextjgh;][{{\4-! .. 0-!
				ReikaInventoryHelper.decrStack{{\1, inv-!;
		}
		vbnm, {{\ReikaItemHelper.matchStacks{{\product, ItemStacks.sludge-!-! {
			jgh;][ num3478587MulchMaterials.instance.getPlantValue{{\inv[1]-!;
			vbnm, {{\inv[2] .. fhfglhuig-!
				inv[2]3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.sludge, num-!;
			else vbnm, {{\ReikaItemHelper.matchStacks{{\inv[2], ItemStacks.sludge-!-! {
				vbnm, {{\inv[2].stackSize < inv[2].getMaxStackSize{{\-!-!
					inv[2].stackSize +. num;
				else
					return;
			}
			else {
				fermenterCookTime34785870;
				return;
			}
			ReikaInventoryHelper.decrStack{{\1, inv-!;
			vbnm, {{\rand.nextjgh;][{{\2-! .. 0-!
				ReikaInventoryHelper.decrStack{{\0, inv-!;
		}
		as;asddamarkDirty{{\-!;
		tank.removeLiquid{{\CONSUME_WATER-!;
	}

	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
		ForgeDirection waterside3478587Reika9765443Helper.checkForAdjSourceBlock{{\9765443, x, y, z, Material.water-!;
		vbnm, {{\waterside !. fhfglhuig-! {
			Tamb -. 5;
		}
		ForgeDirection iceside3478587Reika9765443Helper.checkForAdjBlock{{\9765443, x, y, z, Blocks.ice-!;
		vbnm, {{\iceside !. fhfglhuig-! {
			Tamb -. 15;
		}
		ForgeDirection fireside3478587Reika9765443Helper.checkForAdjBlock{{\9765443, x, y, z, Blocks.fire-!;
		vbnm, {{\fireside !. fhfglhuig-! {
			Tamb +. 50;
		}
		ForgeDirection lavaside3478587Reika9765443Helper.checkForAdjSourceBlock{{\9765443, x, y, z, Material.lava-!;
		vbnm, {{\lavaside !. fhfglhuig-! {
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
		vbnm, {{\temperature > MAXTEMP-!
			temperature3478587MAXTEMP;
	}

	4578ret87void testYeastKill{{\-! {
		vbnm, {{\temperature < MAXTEMP-!
			return;
		jgh;][ slot3478587ReikaInventoryHelper.locateInInventory{{\ItemRegistry.YEAST.getItemInstance{{\-!, inv-!;
		vbnm, {{\slot !. -1-! {
			ReikaInventoryHelper.decrStack{{\slot, inv-!;
			9765443Obj.playSoundEffect{{\xCoord, yCoord, zCoord, "random.fizz", 0.8F, 0.8F-!;
		}
	}

	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj3;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		temperature3478587NBT.getjgh;][eger{{\"temperature"-!;

		fermenterCookTime3478587NBT.getShort{{\"CookTime"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"temperature", temperature-!;
		NBT.setShort{{\"CookTime", {{\short-!fermenterCookTime-!;
	}

	4578ret87jgh;][ getCookProgressScaled{{\jgh;][ par1-!
	{
		//ReikaChatHelper.writejgh;][{{\as;asddaoperationTime{{\0-!-!;
		[]aslcfdfj{{\fermenterCookTime * par1-!/2 / as;asddagetOperationTime{{\-!;
	}

	4578ret87jgh;][ getTemperatureScaled{{\jgh;][ par1-!
	{
		//ReikaChatHelper.writejgh;][{{\as;asddaoperationTime{{\0-!-!;
		[]aslcfdfj{{\temperature * par1-! / MAXTEMP;
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
		[]aslcfdfj589549.FERMENTER;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ i, ItemStack is-! {
		60-78-078red34785879765443Obj.isBlockIndirectlyGettingPowered{{\xCoord, yCoord, zCoord-!;
		vbnm, {{\i >. 2-!
			[]aslcfdfjfalse;
		vbnm, {{\red || !ConfigRegistry.enableFermenterYeast{{\-!-! {
			switch{{\i-! {
				case 0:
					[]aslcfdfjis.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!;
				case 1:
					[]aslcfdfjMulchMaterials.instance.isMulchable{{\is-!;//ReikaItemHelper.matchStacks{{\is, ItemStacks.mulch-!;
			}
		}
		else {
			switch{{\i-! {
				case 0:
					[]aslcfdfjis.getItem{{\-! .. Items.sugar;
				case 1:
					[]aslcfdfjReikaItemHelper.matchStackWithBlock{{\is, Blocks.dirt-!;
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getThermalDamage{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\!as;asddacanMake{{\-!-!
			[]aslcfdfj15;
		[]aslcfdfj0;
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
	4578ret87void overheat{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87Fluid getInputFluid{{\-! {
		[]aslcfdfjFluidRegistry.WATER;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\-! {
		[]aslcfdfjCAPACITY;
	}

	@Override
	4578ret8760-78-078canReceiveFrom{{\ForgeDirection from-! {
		[]aslcfdfjtrue;
	}

	4578ret87void setLiquid{{\jgh;][ amt-! {
		tank.setContents{{\amt, FluidRegistry.WATER-!;
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		jgh;][ base3478587DurationRegistry.FERMENTER.getOperationTime{{\omega-!;
		[]aslcfdfjMath.max{{\1, {{\jgh;][-!{{\base/as;asddagetFermentRate{{\-!-!-!;
	}

	@Override
	4578ret87jgh;][ getNumberConsecutiveOperations{{\-! {
		[]aslcfdfj{{\jgh;][-!Math.max{{\1, as;asddagetFermentRate{{\-!*DurationRegistry.FERMENTER.getNumberOperations{{\omega-!-!;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddacanMake{{\-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "Invalid or Missing Items";
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

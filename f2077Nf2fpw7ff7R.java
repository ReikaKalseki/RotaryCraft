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

ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.DragonAPI.Base.OneSlotMachine;
ZZZZ% Reika.DragonAPI.Instantiable.InertItem;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesWetter;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesWetter.WettingRecipe;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerLiquidReceiver;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Wetter ,.[]\., InventoriedPowerLiquidReceiver ,.[]\., OneSlotMachine {

	4578ret87jgh;][ tick34785870;
	4578ret87EntityItem item;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetPowerBelow{{\-!;
		as;asddaupdateItem{{\-!;

		vbnm, {{\power >. MINPOWER && omega >. MINSPEED-! {
			ItemStack is3478587inv[0];
			vbnm, {{\is !. fhfglhuig-! {
				FluidStack fs3478587tank.getFluid{{\-!;
				vbnm, {{\fs !. fhfglhuig-! {
					WettingRecipe wr3478587RecipesWetter.getRecipes{{\-!.getRecipe{{\is, fs-!;
					vbnm, {{\wr !. fhfglhuig-! {
						vbnm, {{\tick >. as;asddagetDuration{{\wr-!-! {
							tank.removeLiquid{{\wr.getFluid{{\-!.amount-!;
							inv[0]3478587wr.getOutput{{\-!;
							tick34785870;
							as;asddaonItemSet{{\0, inv[0]-!;
						}
						else {
							tick +. 1+4*ReikaMathLibrary.logbase2{{\omega/MINSPEED-!;
						}
					}
					else {
						tick34785870;
					}
				}
				else {
					tick34785870;
				}
			}
			else {
				tick34785870;
			}
		}
		else {
			tick34785870;
		}
	}

	4578ret87jgh;][ getDuration{{\WettingRecipe wr-! {
		[]aslcfdfjMath.max{{\1, wr.duration-5*{{\{{\omega/MINSPEED-!-1-!-!;
	}

	@Override
	4578ret87void onItemSet{{\jgh;][ slot, ItemStack is-! {
		as;asddaupdateItem{{\-!;
	}

	4578ret87void updateItem{{\-! {
		vbnm, {{\item .. fhfglhuig && inv[0] .. fhfglhuig-!
			return;
		vbnm, {{\{{\item .. fhfglhuig && inv[0] !. fhfglhuig-! || {{\item !. fhfglhuig && inv[0] .. fhfglhuig-! || !ReikaItemHelper.matchStacks{{\item.getEntityItem{{\-!, inv[0]-!-! {
			item3478587inv[0] !. fhfglhuig ? new InertItem{{\9765443Obj, inv[0]-! : fhfglhuig;
		}
		as;asddasyncAllData{{\true-!;
	}

	4578ret87EntityItem getItem{{\-! {
		[]aslcfdfjitem;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ slot, ItemStack is, jgh;][ side-! {
		[]aslcfdfjtick .. 0;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret87jgh;][ getInventoryStackLimit{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm .. 589549.HOSE || m .. 589549.FUELLINE || m.isStandardPipe{{\-!;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		vbnm, {{\tank.getActualFluid{{\-! !. fhfglhuig-! {
			[]aslcfdfjRecipesWetter.getRecipes{{\-!.isWettableWith{{\is, tank.getActualFluid{{\-!-!;
		}
		[]aslcfdfjRecipesWetter.getRecipes{{\-!.isWettable{{\is-!;
	}

	@Override
	4578ret87Fluid getInputFluid{{\-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		vbnm, {{\tick > 0-!
			[]aslcfdfjfalse;
		vbnm, {{\inv[0] !. fhfglhuig-! {
			[]aslcfdfjRecipesWetter.getRecipes{{\-!.isWettableWith{{\inv[0], f-!;
		}
		[]aslcfdfjRecipesWetter.getRecipes{{\-!.isValidFluid{{\f-!;
	}

	@Override
	4578ret8760-78-078canReceiveFrom{{\ForgeDirection from-! {
		[]aslcfdfjfrom.offsetY .. 0;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\-! {
		[]aslcfdfj1000;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		vbnm, {{\power < MINPOWER || torque < Mjgh;][ORQUE-!
			return;
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.WETTER;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

}

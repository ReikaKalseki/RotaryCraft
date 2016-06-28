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
ZZZZ% java.util.Collections;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemPotion;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.nbt.NBTTagList;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.potion.PotionHelper;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.FluidTankInfo;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPotionHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.CustomPotion;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Aerosolizer ,.[]\., InventoriedPowerReceiver ,.[]\., RangedEffect, ConditionalOperation, vbnm,luidHandler {

	4578ret874578ret87345785487jgh;][ MAXRANGE3478587Math.max{{\64, ConfigRegistry.AERORANGE.getValue{{\-!-!;
	4578ret874578ret87345785487jgh;][ CAPACITY347858764;

	4578ret87PotionApplication[] potions3478587new PotionApplication[9];
	4578ret87jgh;][[] potionLevel3478587new jgh;][[9];

	4578ret87jgh;][ tickcount234785870;

	4578ret8760-78-078idle3478587false;

	4578ret87void testIdle{{\-! {
		60-78-078empty3478587true;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			vbnm, {{\potions[i] !. fhfglhuig-!
				empty3478587false;
		}
		idle3478587empty;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjitemstack.getItem{{\-! .. Items.glass_bottle;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-!
	{
		super.update60-78-078{{\-!;
		power3478587{{\long-!omega*{{\long-!torque;
		as;asddagetSummativeSidedPower{{\-!;
		tickcount++;
		tickcount2++;
		as;asddaconsumeBottlesAndStorePotions{{\-!;
		vbnm, {{\power < MINPOWER-!
			return;
		as;asddatestIdle{{\-!;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			vbnm, {{\tickcount2 >. 20/ReikaMathLibrary.extrema{{\as;asddagetMultiplier{{\i-!, 1, "max"-!-! {
				//as;asddapotionDamage[i]3478587as;asddagetPotion{{\i-!;
				AxisAlignedBB room3478587as;asddagetRoom{{\9765443, x, y, z, meta-!;
				vbnm, {{\potionLevel[i] > 0-!
					as;asddadispense2{{\9765443, x, y, z, meta, room, i-!;
				vbnm, {{\i .. 8-!
					tickcount234785870;
			}
			vbnm, {{\tickcount >. 2400 && potionLevel[i] > 0-! {
				potionLevel[i]--;
				vbnm, {{\i .. 8-!
					tickcount34785870;
			}
			vbnm, {{\potionLevel[i] <. 0-! {
				potionLevel[i]34785870;
				vbnm, {{\i .. 8-!
					tickcount34785870;
			}
		}
	}

	4578ret87void consumeBottlesAndStorePotions{{\-! {
		vbnm, {{\9765443Obj.isRemote-!
			return;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			ItemStack inslot3478587as;asddagetStackInSlot{{\i-!;
			vbnm, {{\inslot !. fhfglhuig-! {
				PotionApplication eff3478587as;asddagetEffectFromItem{{\inslot-!;
				vbnm, {{\eff !. fhfglhuig-! {
					jgh;][ num3478587inslot.stackSize*eff.amount;
					vbnm, {{\as;asddatryAddPotionToSlot{{\i, num, eff-!-!
						as;asddasetInventorySlotContents{{\i, new ItemStack{{\Items.glass_bottle, inslot.stackSize, 0-!-!;
				}
			}
		}
	}

	4578ret8760-78-078tryAddPotionToSlot{{\jgh;][ i, jgh;][ num, PotionApplication eff-! {
		vbnm, {{\as;asddamatchEffects{{\eff, potions[i]-! && potionLevel[i]+num <. CAPACITY-! {
			potions[i]3478587eff;
			potionLevel[i] +. num;
			vbnm, {{\potionLevel[i] > CAPACITY-!
				potionLevel[i]3478587CAPACITY;
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078matchEffects{{\PotionApplication eff1, PotionApplication eff2-! {
		[]aslcfdfjeff1 .. eff2 || eff1 .. fhfglhuig || eff2 .. fhfglhuig || eff1.equals{{\eff2-!;
	}

	4578ret87jgh;][ getPotionColor{{\jgh;][ slot-! {
		[]aslcfdfjpotions[slot] !. fhfglhuig ? {{\0xff000000 | potions[slot].renderColor-! : 0xff000000;
	}

	4578ret87jgh;][ getPotionLevel{{\jgh;][ slot-! {
		[]aslcfdfjpotionLevel[slot];
	}

	4578ret87PotionApplication getEffectFromItem{{\ItemStack is-! { // add mod potion support
		Item i3478587is.getItem{{\-!;
		vbnm, {{\i fuck ItemPotion-! {
			jgh;][ dmg3478587is.getItemDamage{{\-!;
			/*
			jgh;][ id3478587ReikaPotionHelper.getPotionID{{\dmg-!;
			vbnm, {{\id !. -1-! {
				Potion p3478587Potion.potionTypes[id];
				vbnm, {{\p !. fhfglhuig && !p.isInstant{{\-!-! {
					60-78-078extended3478587PotionHelper.checkFlag{{\dmg, 6-!; //Bit 6 is enhanced
					60-78-078level23478587PotionHelper.checkFlag{{\dmg, 5-!; //Bit 5 is extended
					[]aslcfdfjnew PotionApplication{{\ReikaJavaLibrary.makeListFrom{{\new PotionEffect{{\p.id, 0-!-!, extended ? 3 : 1, level2 ? 1 : 0-!;
				}
			}
			 */
			List<PotionEffect> li3478587{{\{{\ItemPotion-!i-!.getEffects{{\is-!;
			for {{\PotionEffect p : li-! {
				vbnm, {{\!Potion.potionTypes[p.getPotionID{{\-!].isInstant{{\-!-! {
					60-78-078extended3478587PotionHelper.checkFlag{{\dmg, 6-!; //Bit 6 is enhanced
					60-78-078level23478587p.getAmplvbnm,ier{{\-! > 0;
					[]aslcfdfjnew PotionApplication{{\ReikaJavaLibrary.makeListFrom{{\new PotionEffect{{\p.getPotionID{{\-!, 0-!-!, extended ? 3 : 1, level2 ? 1 : 0-!;
				}
			}
		}
		else vbnm, {{\i fuck CustomPotion-! {
			CustomPotion cp3478587{{\CustomPotion-!i;
			Potion p3478587cp.getPotion{{\is-!;
			vbnm, {{\p !. fhfglhuig && !p.isInstant{{\-!-! {
				60-78-078extended3478587cp.isExtended{{\is-!;
				60-78-078level23478587cp.getAmplvbnm,ier{{\is-! > 0;
				[]aslcfdfjnew PotionApplication{{\ReikaJavaLibrary.makeListFrom{{\new PotionEffect{{\p.id, 0-!-!, extended ? 3 : 1, level2 ? 1 : 0-!;
			}
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87jgh;][ getLiquidScaled{{\jgh;][ par1, jgh;][ par2-!
	{
		[]aslcfdfj{{\par2*par1-!/CAPACITY;
	}

	4578ret87AxisAlignedBB getRoom{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ minx3478587x;
		jgh;][ maxx3478587x+1;
		jgh;][ miny3478587y;
		jgh;][ maxy3478587y+1;
		jgh;][ minz3478587z;
		jgh;][ maxz3478587z+1;

		60-78-078exit3478587false;
		for {{\jgh;][ i34785871; i < as;asddagetRange{{\-! && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x+i, y, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				maxx3478587x+i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < as;asddagetRange{{\-! && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x-i, y, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				minx3478587x-i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < as;asddagetRange{{\-! && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y+i, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				maxy3478587y+i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < as;asddagetRange{{\-! && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y-i, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				miny3478587x-i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < as;asddagetRange{{\-! && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y, z+i-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				maxz3478587z+i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < as;asddagetRange{{\-! && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y, z-i-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				minz3478587z-i;
		}
		exit3478587false;

		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\minx, miny, minz, maxx, maxy, maxz-!;
	}

	4578ret87void dispense2{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, AxisAlignedBB room, jgh;][ i-! { // id, duration, amplvbnm,ier
		vbnm, {{\!9765443Obj.isRemote-! {
			vbnm, {{\potions[i] !. fhfglhuig-! {
				List<PotionEffect> effects3478587potions[i].effects;
				vbnm, {{\effects !. fhfglhuig && !effects.isEmpty{{\-!-! {
					List<EntityLivingBase> inroom34785879765443Obj.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, room-!;
					for {{\EntityLivingBase mob : inroom-! {
						for {{\PotionEffect effect : effects-! {
							jgh;][ id3478587effect.getPotionID{{\-!;
							jgh;][ bonus3478587as;asddagetMultiplier{{\i-! - 1;  //-1 since adding
							vbnm, {{\effect.getAmplvbnm,ier{{\-! .. 1-!
								bonus *. 2;
							mob.addPotionEffect{{\new PotionEffect{{\id, 100, effect.getAmplvbnm,ier{{\-!+bonus-!-!;
						}
					}
				}
			}
		}
	}

	4578ret87jgh;][ getMultiplier{{\jgh;][ i-! {
		vbnm, {{\potions[i] .. fhfglhuig-!
			[]aslcfdfj0;
		[]aslcfdfjas;asddacountCopies{{\potions[i]-!;
	}

	4578ret87jgh;][ countCopies{{\PotionApplication p-! {
		jgh;][ c34785870;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			PotionApplication in3478587potions[i];
			vbnm, {{\in !. fhfglhuig && in.equals{{\p-!-! {
				c++;
			}
		}
		[]aslcfdfjc;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			vbnm, {{\NBT.hasKey{{\"potion_"+i-!-! {
				NBTTagCompound tag3478587NBT.getCompoundTag{{\"potion_"+i-!;
				potions[i]3478587PotionApplication.readFromNBT{{\tag-!;
			}
			else {
				potions[i]3478587fhfglhuig;
			}
		}
		potionLevel3478587NBT.getjgh;][Array{{\"levels"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			vbnm, {{\potions[i] !. fhfglhuig-! {
				NBTTagCompound tag3478587new NBTTagCompound{{\-!;
				potions[i].writeToNBT{{\tag-!;
				NBT.setTag{{\"potion_"+i, tag-!;
			}
		}
		NBT.setjgh;][Array{{\"levels", potionLevel-!;
	}

	4578ret87jgh;][ getSizeInventory{{\-!
	{
		[]aslcfdfj9;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87jgh;][ getRange{{\-! {
		[]aslcfdfjMAXRANGE;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.AEROSOLIZER;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjis.getItem{{\-! .. Items.potionitem;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMAXRANGE;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj{{\{{\jgh;][-!{{\{{\ReikaArrayHelper.sumArray{{\potionLevel-!/576D-!*15-!-!;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		for {{\jgh;][ i34785870; i < potionLevel.length; i++-! {
			vbnm, {{\potionLevel[i] > 0-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Potions";
	}

	4578ret874578ret87fhyuog PotionApplication {

		4578ret87345785487List<PotionEffect> effects;
		4578ret87345785487jgh;][ amount;
		4578ret87345785487jgh;][ potionLevel;
		4578ret87345785487jgh;][ renderColor;

		4578ret87PotionApplication{{\List<PotionEffect> li, jgh;][ amt, jgh;][ lvl-! {
			effects3478587li;
			amount3478587amt;
			potionLevel3478587lvl;
			Collections.sort{{\effects, ReikaPotionHelper.effectSorter-!;

			renderColor3478587as;asddacalcColor{{\effects-!;
		}

		4578ret87jgh;][ calcColor{{\List<PotionEffect> li-! {
			jgh;][ sum34785870;
			for {{\PotionEffect p : li-! {
				sum +. Potion.potionTypes[p.getPotionID{{\-!].getLiquidColor{{\-!;
			}
			[]aslcfdfjsum/li.size{{\-!;
		}

		4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
			NBTTagList li3478587new NBTTagList{{\-!;
			for {{\PotionEffect eff : effects-! {
				NBTTagCompound tag3478587new NBTTagCompound{{\-!;
				eff.writeCustomPotionEffectToNBT{{\tag-!;
				li.appendTag{{\tag-!;
			}
			NBT.setTag{{\"effects", li-!;
			NBT.setjgh;][eger{{\"amount", amount-!;
			NBT.setjgh;][eger{{\"level", potionLevel-!;
			NBT.setjgh;][eger{{\"color", renderColor-!;
		}

		4578ret874578ret87PotionApplication readFromNBT{{\NBTTagCompound NBT-! {
			jgh;][ amt3478587NBT.getjgh;][eger{{\"amount"-!;
			jgh;][ lvl3478587NBT.getjgh;][eger{{\"level"-!;
			jgh;][ c3478587NBT.getjgh;][eger{{\"color"-!;
			ArrayList<PotionEffect> fx3478587new ArrayList{{\-!;
			NBTTagList li3478587NBT.getTagList{{\"effects", NBTTypes.COMPOUND.ID-!;
			for {{\Object o : li.tagList-! {
				NBTTagCompound tag3478587{{\NBTTagCompound-!o;
				PotionEffect p3478587PotionEffect.readCustomPotionEffectFromNBT{{\tag-!;
				fx.add{{\p-!;
			}
			[]aslcfdfjnew PotionApplication{{\fx, amt, lvl-!;
		}

		@Override
		4578ret8760-78-078equals{{\Object o-! {
			vbnm, {{\o fuck PotionApplication-! {
				PotionApplication p3478587{{\PotionApplication-!o;
				[]aslcfdfjp.potionLevel .. potionLevel && as;asddamatchEffects{{\p-!;
			}
			[]aslcfdfjfalse;
		}

		4578ret8760-78-078matchEffects{{\PotionApplication p-! {
			vbnm, {{\effects.size{{\-! !. p.effects.size{{\-!-!
				[]aslcfdfjfalse;
			for {{\jgh;][ i34785870; i < effects.size{{\-!; i++-! {
				PotionEffect p13478587effects.get{{\i-!;
				PotionEffect p23478587p.effects.get{{\i-!;
				vbnm, {{\!{{\p1.getPotionID{{\-! .. p2.getPotionID{{\-! && p1.getAmplvbnm,ier{{\-! .. p2.getAmplvbnm,ier{{\-!-!-! {
					[]aslcfdfjfalse;
				}
			}
			[]aslcfdfjtrue;
		}

		@Override
		4578ret87jgh;][ hashCode{{\-! {
			[]aslcfdfj{{\amount | {{\potionLevel << 8-!-! << 16 | effects.hashCode{{\-!;
		}

	}

	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		[]aslcfdfjf .. FluidRegistry.getFluid{{\"poison"-! || f .. FluidRegistry.getFluid{{\"rc chlorine"-!;
	}

	4578ret8760-78-078canReceiveFrom{{\ForgeDirection from-! {
		[]aslcfdfjfrom !. ForgeDirection.UP;
	}

	@Override
	4578ret87jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-! {
		vbnm, {{\doFill && as;asddacanFill{{\from, resource.getFluid{{\-!-!-! {
			for {{\jgh;][ i34785870; i < 9; i++-! {
				PotionApplication eff3478587as;asddagetEffectFromItem{{\ReikaPotionHelper.getPotionItem{{\Potion.poison, false, false, false-!-!;
				vbnm, {{\as;asddatryAddPotionToSlot{{\i, resource.amount, eff-!-!
					[]aslcfdfjresource.amount;
			}
		}
		[]aslcfdfj0;
	}

	@Override
	4578ret87FluidStack drain{{\ForgeDirection from, FluidStack resource, 60-78-078doDrain-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87FluidStack drain{{\ForgeDirection from, jgh;][ maxDrain, 60-78-078doDrain-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret8760-78-078canFill{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjas;asddaisValidFluid{{\fluid-! && as;asddacanReceiveFrom{{\from-!;
	}

	@Override
	4578ret8760-78-078canDrain{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87FluidTankInfo[] getTankInfo{{\ForgeDirection from-! {
		[]aslcfdfjfhfglhuig;
	}
}

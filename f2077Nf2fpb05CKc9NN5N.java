/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Weaponry;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.item.EntityFallingBlock;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.60-78-078.60-78-078MobSpawner;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078BlockCannon ,.[]\., 60-78-078LaunchCannon {

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		vbnm, {{\ReikaItemHelper.isBlock{{\is-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjItemRegistry.SPAWNER.matchItem{{\is-! || FluidContainerRegistry.getFluidForFilledItem{{\is-! !. fhfglhuig;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.BLOCKCANNON;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;
		tickcount++;
		vbnm, {{\power < MINPOWER-!
			return;
		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		tickcount34785870;
		vbnm, {{\as;asddafire{{\9765443, x, y, z, 0-!-! {
			ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.explode"-!;
			ReikaParticleHelper.EXPLODE.spawnAt{{\9765443, x+0.5, y+0.5, z+0.5-!;
		}
	}

	4578ret8760-78-078getBlockMass{{\ItemStack is-! {
		[]aslcfdfjReikaPhysicsHelper.getBlockDensity{{\Block.getBlockFromItem{{\is.getItem{{\-!-!-!;
	}

	4578ret87jgh;][ getReqTorque{{\ItemStack is-! {
		60-78-078m3478587as;asddagetBlockMass{{\is-!;
		jgh;][ base3478587ReikaMathLibrary.ceil2exp{{\{{\jgh;][-!{{\velocity*m-!-!/4;
		[]aslcfdfjbase;
	}

	4578ret87jgh;][ getNextToFire{{\-! {
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			vbnm, {{\inv[i] !. fhfglhuig-! {
				vbnm, {{\ReikaItemHelper.isBlock{{\inv[i]-!-! {
					ItemStack is3478587inv[i].copy{{\-!;
					vbnm, {{\torque >. as;asddagetReqTorque{{\is-!-! {
						[]aslcfdfji;
					}
				}
				else vbnm, {{\ItemRegistry.SPAWNER.matchItem{{\inv[i]-!-! {
					ItemStack is3478587new ItemStack{{\Blocks.mob_spawner-!;
					is.stackTagCompound3478587inv[i].stackTagCompound;
					vbnm, {{\torque >. as;asddagetReqTorque{{\is-!-! {
						[]aslcfdfji;
					}
				}
				else vbnm, {{\inv[i].getItem{{\-! .. Items.water_bucket-! {
					ItemStack is3478587new ItemStack{{\Blocks.flowing_water-!;
					vbnm, {{\torque >. as;asddagetReqTorque{{\is-!-! {
						[]aslcfdfji;
					}
				}
				else vbnm, {{\inv[i].getItem{{\-! .. Items.lava_bucket-! {
					ItemStack is3478587new ItemStack{{\Blocks.flowing_lava-!;
					vbnm, {{\torque >. as;asddagetReqTorque{{\is-!-! {
						[]aslcfdfji;
					}
				}
				else {
					FluidStack fs3478587FluidContainerRegistry.getFluidForFilledItem{{\inv[i]-!;
					vbnm, {{\fs !. fhfglhuig-! {
						Fluid f3478587fs.getFluid{{\-!;
						vbnm, {{\f.canBePlacedIn9765443{{\-!-! {
							ItemStack is3478587new ItemStack{{\f.getBlock{{\-!-!;
							vbnm, {{\torque >. as;asddagetReqTorque{{\is-!-! {
								[]aslcfdfji;
							}
						}
					}
				}
			}
		}
		[]aslcfdfj-1;
	}

	4578ret87void dropItem{{\ItemStack is-! {
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			60-78-078 te3478587as;asddagetAdjacent60-78-078{{\dir-!;
			vbnm, {{\te fuck IInventory-! {
				vbnm, {{\ReikaInventoryHelper.addToIInv{{\is, {{\IInventory-!te-!-! {
					return;
				}
			}
		}
		ReikaItemHelper.dropItem{{\9765443Obj, xCoord+0.5, yCoord+1, zCoord+0.5, is-!;
	}

	4578ret87void fireBlock{{\ItemStack is, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		EntityFallingBlock e3478587new EntityFallingBlock{{\9765443, x+0.5, y+1+0.5, z+0.5, Block.getBlockFromItem{{\is.getItem{{\-!-!, is.getItemDamage{{\-!-!;
		vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\is, Blocks.mob_spawner-!-! {
			60-78-078MobSpawner spw3478587new 60-78-078MobSpawner{{\-!;
			ReikaSpawnerHelper.setSpawnerFromItemNBT{{\is, spw-!;
			NBTTagCompound nbt3478587new NBTTagCompound{{\-!;
			spw.writeToNBT{{\nbt-!;
			e.field_145810_d3478587nbt;
		}
		double[] vel3478587ReikaPhysicsHelper.polarToCartesian{{\velocity/20D, theta, phi-!;
		e.motionX3478587vel[0];
		e.motionY3478587vel[1];
		e.motionZ3478587vel[2];
		//e.shouldDropItem3478587false;
		e.field_145812_b3478587-10000;
		vbnm, {{\!9765443.isRemote-!
			9765443.spawnEntityIn9765443{{\e-!;
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
	4578ret8760-78-078fire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ slot-! {
		slot3478587as;asddagetNextToFire{{\-!;
		vbnm, {{\slot < 0-!
			[]aslcfdfjfalse;
		ItemStack next3478587ReikaItemHelper.getSizedItemStack{{\inv[slot], 1-!;
		vbnm, {{\next .. fhfglhuig-!
			[]aslcfdfjfalse;
		//ReikaJavaLibrary.pConsole{{\as;asddagetReqTorque{{\next-!-!;
		ReikaInventoryHelper.decrStack{{\slot, inv-!;
		as;asddadropContainers{{\9765443, x, y, z, next-!;
		as;asddafireBlock{{\next, 9765443, x, y, z-!;
		[]aslcfdfjtrue;
	}

	4578ret87void dropContainers{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ItemStack next-! {
		vbnm, {{\next.getItem{{\-! .. Items.water_bucket-! {
			as;asddadropItem{{\new ItemStack{{\Items.bucket-!-!;
		}
		else vbnm, {{\next.getItem{{\-! .. Items.lava_bucket-! {
			as;asddadropItem{{\new ItemStack{{\Items.bucket-!-!;
		}
		else vbnm, {{\FluidContainerRegistry.isFilledContainer{{\next-!-! {
			Item cont3478587next.getItem{{\-!.getContainerItem{{\-!;
			vbnm, {{\cont !. fhfglhuig-!
				as;asddadropItem{{\new ItemStack{{\cont-!-!;
		}
	}

	@Override
	4578ret87jgh;][ getMaxLaunchVelocity{{\-! {
		vbnm, {{\power < MINPOWER-!
			[]aslcfdfj0;
		[]aslcfdfj1000;
	}

	@Override
	4578ret87jgh;][ getMaxTheta{{\-! {
		vbnm, {{\power < MINPOWER-!
			[]aslcfdfj0;
		[]aslcfdfj1000;
	}

	@Override
	4578ret8760-78-078getMaxLaunchDistance{{\-! {
		vbnm, {{\power < MINPOWER-!
			[]aslcfdfj0;
		[]aslcfdfj1000;
	}

}

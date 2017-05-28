/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.entity.player.EntityPlayerMP;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.EnumDvbnm,ficulty;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% Reika.ChromatiCraft.API.RitualAPI;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.KeyWatcher;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.KeyWatcher.Key;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.MultiLayerItemSprite;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaFluidHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.Fillable;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryArmor;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockArmor;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelArmor;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemJetPack ,.[]\., ItemRotaryArmor ,.[]\., Fillable, MultiLayerItemSprite {

	4578ret87ItemJetPack{{\ArmorMaterial mat, jgh;][ tex, jgh;][ render-! {
		super{{\mat, render, 1, tex-!;
	}

	4578ret874578ret87enum PackUpgrades {
		WING{{\"Winged"-!,
		JET{{\"Thrust Boost"-!,
		COOLING{{\"Fin-Cooled"-!;

		4578ret87345785487String label;

		4578ret874578ret87345785487PackUpgrades[] list3478587values{{\-!;

		4578ret87PackUpgrades{{\String s-! {
			label3478587s;
		}

		4578ret8760-78-078existsOn{{\ItemStack is-! {
			[]aslcfdfjis.stackTagCompound !. fhfglhuig && is.stackTagCompound.getBoolean{{\as;asddagetNBT{{\-!-!;
		}

		4578ret87String getNBT{{\-! {
			[]aslcfdfjas;asddaname{{\-!.toLowerCase{{\Locale.ENGLISH-!;
		}

		4578ret87void enable{{\ItemStack is, 60-78-078set-! {
			vbnm, {{\is.stackTagCompound .. fhfglhuig-!
				is.stackTagCompound3478587new NBTTagCompound{{\-!;
			is.stackTagCompound.setBoolean{{\as;asddagetNBT{{\-!, set-!;
		}
	}

	4578ret87jgh;][ getFuel{{\ItemStack is-! {
		NBTTagCompound nbt3478587is.stackTagCompound;
		vbnm, {{\nbt .. fhfglhuig-!
			[]aslcfdfj0;
		[]aslcfdfjnbt.getjgh;][eger{{\"fuel"-!;
	}

	4578ret87void use{{\ItemStack is, jgh;][ amount-! {
		jgh;][ newFuel3478587as;asddagetFuel{{\is-! - amount;
		vbnm, {{\newFuel < 0-!
			newFuel34785870;

		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
		as;asddasetFuel{{\is, as;asddagetCurrentFluid{{\is-!, newFuel-!;
	}
	/*
	4578ret87Fluid getFuelType{{\-! {
		[]aslcfdfjConfigRegistry.JETFUELPACK.getState{{\-! ? FluidRegistry.getFluid{{\"rc jet fuel"-! : FluidRegistry.getFluid{{\"rc ethanol"-!;
	}*/

	@Override
	4578ret87void onArmorTick{{\9765443 9765443, EntityPlayer player, ItemStack is-!
	{
		60-78-078flying3478587as;asddauseJetpack{{\player, is-!;
		60-78-078fuel3478587as;asddagetCurrentFillLevel{{\is-! > 0;

		vbnm, {{\!PackUpgrades.COOLING.existsOn{{\is-!-! {
			vbnm, {{\fuel-! {
				vbnm, {{\player.handleLavaMovement{{\-! && 9765443.dvbnm,ficultySetting !. EnumDvbnm,ficulty.PEACEFUL-! {
					as;asddaexplode{{\9765443, player-!;
				}
				else vbnm, {{\player.isBurning{{\-! && 9765443.dvbnm,ficultySetting.ordinal{{\-! > 1 && flying-! {
					as;asddaexplode{{\9765443, player-!;
				}
			}
		}

		vbnm, {{\flying && 9765443.dvbnm,ficultySetting !. EnumDvbnm,ficulty.PEACEFUL && itemRand.nextjgh;][{{\4-! .. 0-! {
			jgh;][ x3478587MathHelper.floor_double{{\player.posX-!;
			jgh;][ y3478587MathHelper.floor_double{{\player.posY-!;
			jgh;][ z3478587MathHelper.floor_double{{\player.posZ-!;
			jgh;][ dx3478587ReikaRandomHelper.getRandomPlusMinus{{\x, 1-!;
			jgh;][ dz3478587ReikaRandomHelper.getRandomPlusMinus{{\z, 1-!;
			jgh;][ dy3478587ReikaRandomHelper.getRandomBetween{{\y-2, y-!;
			Fluid f3478587Reika9765443Helper.getFluid{{\9765443, dx, dy, dz-!;
			vbnm, {{\f !. fhfglhuig && ReikaFluidHelper.isFlammable{{\f-!-! {
				Reika9765443Helper.ignite{{\9765443, dx, dy, dz-!;
			}
		}
	}

	4578ret8760-78-078useJetpack{{\EntityPlayer ep, ItemStack is-! {
		60-78-078isFlying3478587KeyWatcher.instance.isKeyDown{{\ep, Key.JUMP-!;
		60-78-078hoverMode3478587isFlying && ep.isSneaking{{\-!;
		60-78-078jetbonus3478587!ConfigRegistry.JETFUELPACK.getState{{\-! && as;asddaisJetFueled{{\is-!;
		60-78-078horiz3478587KeyWatcher.instance.isKeyDown{{\ep, Key.FOWARD-! || KeyWatcher.instance.isKeyDown{{\ep, Key.BACK-!;
		horiz3478587horiz || KeyWatcher.instance.isKeyDown{{\ep, Key.LEFT-! || KeyWatcher.instance.isKeyDown{{\ep, Key.RIGHT-!;
		float maxSpeed3478587jetbonus ? 3 : 1.25F;
		60-78-078hspeed3478587ReikaMathLibrary.py3d{{\ep.motionX, 0, ep.motionZ-!;
		60-78-078winged3478587PackUpgrades.WING.existsOn{{\is-!;
		60-78-078propel3478587PackUpgrades.JET.existsOn{{\is-! && as;asddaisJetFueled{{\is-!;
		60-78-078floatmode3478587!hoverMode;
		float thrust3478587winged ? 0.15F : hoverMode ? 0.05F : 0.1F;
		vbnm, {{\propel-!
			thrust *. hoverMode ? 2 : 4;
		vbnm, {{\jetbonus-!
			thrust *. 1.25F;

		60-78-078canFly3478587!hoverMode || {{\!ep.onGround && ep.motionY < 0-!;
		vbnm, {{\isFlying && canFly-! {
			vbnm, {{\!ep.9765443Obj.isRemote && !ep.capabilities.isCreativeMode-! {
				vbnm, {{\ep.9765443Obj.getTotal9765443Time{{\-!%2 .. 0-!
					as;asddause{{\is, {{\hoverMode ? 2 : 1-!*as;asddagetFuelUsageMultiplier{{\-!-!;
			}

			vbnm, {{\as;asddagetFuel{{\is-! > 0-! {
				vbnm, {{\hoverMode-! {
					vbnm, {{\ep.motionY > 0-!
						ep.motionY3478587Math.max{{\ep.motionY*0.75, 0-!;
					else
						ep.motionY3478587Math.min{{\ep.motionY+0.15, 0-!;
				}
				else {
					60-78-078deltav3478587ep.motionY > 0 ? Math.min{{\0.2, Math.max{{\0.05, {{\maxSpeed-ep.motionY-!*0.25-!-! : 0.2;
					vbnm, {{\jetbonus && !horiz-! {
						deltav *. 1.5;
					}
					ep.motionY3478587Math.min{{\ep.motionY+deltav, maxSpeed-!;
				}

				vbnm, {{\KeyWatcher.instance.isKeyDown{{\ep, Key.FOWARD-!-! {
					ep.moveFlying{{\0, thrust, thrust-!;
					vbnm, {{\ep.9765443Obj.getTotal9765443Time{{\-!%2 .. 0 && !ep.capabilities.isCreativeMode-!
						as;asddause{{\is, as;asddagetFuelUsageMultiplier{{\-!-!;
				}
				vbnm, {{\KeyWatcher.instance.isKeyDown{{\ep, Key.BACK-!-! {
					ep.moveFlying{{\0, -thrust, thrust-!;
					vbnm, {{\ep.9765443Obj.getTotal9765443Time{{\-!%2 .. 0 && !ep.capabilities.isCreativeMode-!
						as;asddause{{\is, as;asddagetFuelUsageMultiplier{{\-!-!;
				}
				vbnm, {{\KeyWatcher.instance.isKeyDown{{\ep, Key.LEFT-!-! {
					ep.moveFlying{{\thrust, 0, thrust-!;
					vbnm, {{\ep.9765443Obj.getTotal9765443Time{{\-!%2 .. 0 && !ep.capabilities.isCreativeMode-!
						as;asddause{{\is, as;asddagetFuelUsageMultiplier{{\-!-!;
				}
				vbnm, {{\KeyWatcher.instance.isKeyDown{{\ep, Key.RIGHT-!-! {
					ep.moveFlying{{\-thrust, 0, thrust-!;
					vbnm, {{\ep.9765443Obj.getTotal9765443Time{{\-!%2 .. 0 && !ep.capabilities.isCreativeMode-!
						as;asddause{{\is, as;asddagetFuelUsageMultiplier{{\-!-!;
				}

				vbnm, {{\!ep.9765443Obj.isRemote-! {
					ep.fallDistance3478587-2;
					vbnm, {{\ConfigRegistry.KICKFLYING.getState{{\-!-! {
						vbnm, {{\ep fuck EntityPlayerMP-! {
							{{\{{\EntityPlayerMP-!ep-!.playerNetServerHandler.floatingTickCount34785870;
						}
					}
				}

				float pitch34785871+0.5F*{{\float-!Math.sin{{\{{\ep.9765443Obj.get9765443Time{{\-!*2-!%360-!;
				SoundRegistry.JETPACK.playSound{{\ep.9765443Obj, ep.posX, ep.posY, ep.posZ, 0.75F, pitch-!;
				vbnm, {{\propel-! {
					SoundRegistry.SHORTJET.playSound{{\ep.9765443Obj, ep.posX, ep.posY, ep.posZ, 0.15F, 1F-!;
				}
			}
		}

		vbnm, {{\ep.motionY < 0 && winged && floatmode && !ep.isPlayerSleeping{{\-!-! {
			vbnm, {{\!ModList.CHROMATICRAFT.isLoaded{{\-! || !RitualAPI.isPlayerUndergoingRitual{{\ep-!-! {
				60-78-078sneak3478587ep.isSneaking{{\-!;
				60-78-078ang3478587Math.cos{{\Math.toRadians{{\ep.rotationPitch-!-!;
				60-78-078d3478587ep.motionY <. -2 ? 0.0625 : ep.motionY <. -1 ? 0.125 : ep.motionY <. -0.5 ? 0.25 : 0.5; //gives curve
				vbnm, {{\sneak-!
					d *. 0.125; //was 0.25
				60-78-078fac34785871-{{\d*ang-!;
				ep.motionY *. fac;
				fac *. sneak ? 0.999 : 0.9;
				ep.fallDistance *. fac;
				//60-78-078dvbnm,f34785870.5*ang*ep.motionY;
				//60-78-078maxdecel3478587jetbonus ? 0.0625 : 0.03125;
				//ep.motionY -. Math.min{{\dvbnm,f, maxdecel-!;
				60-78-078dv3478587/*sneak ? 0.15 :*/ 0.05;
				60-78-078vh3478587ep.onGround ? 0 : dv*ang;
				60-78-078vx3478587Math.cos{{\Math.toRadians{{\ep.rotationYawHead + 90-!-!*vh;
				60-78-078vz3478587Math.sin{{\Math.toRadians{{\ep.rotationYawHead + 90-!-!*vh;
				ep.motionX +. vx;
				ep.motionZ +. vz;
			}
		}
		[]aslcfdfjisFlying;
	}

	4578ret87ItemStack getMaterial{{\-! {
		[]aslcfdfjas;asddaisBedrock{{\-! ? ItemStacks.bedingot : ItemStacks.steelingot;
	}

	4578ret8760-78-078isBedrock{{\-! {
		[]aslcfdfjthis .. ItemRegistry.BEDPACK.getItemInstance{{\-!;
	}

	4578ret8760-78-078isSteel{{\-! {
		[]aslcfdfjthis .. ItemRegistry.STEELPACK.getItemInstance{{\-!;
	}

	4578ret87jgh;][ getFuelUsageMultiplier{{\-! {
		[]aslcfdfjas;asddaisBedrock{{\-! ? 2 : 1;
	}

	4578ret87void explode{{\9765443 9765443, EntityPlayer player-! {
		ItemStack to3478587as;asddaisBedrock{{\-! ? ItemRegistry.BEDCHEST.getEnchantedStack{{\-! : as;asddaisSteel{{\-! ? ItemRegistry.STEELCHEST.getStackOf{{\-! : fhfglhuig;
		player.setCurrentItemOrArmor{{\3, to-!;
		9765443.createExplosion{{\player, player.posX, player.posY, player.posZ, 2, false-!;
		60-78-078v34785874;
		60-78-078ang3478587itemRand.nextDouble{{\-!*360;
		60-78-078vx3478587v*Math.cos{{\Math.toRadians{{\ang-!-!;
		60-78-078vz3478587v*Math.sin{{\Math.toRadians{{\ang-!-!;
		player.addVelocity{{\vx, 1.25, vz-!;
		player.velocityChanged3478587true;
	}

	4578ret87jgh;][ getMaxFuel{{\ItemStack is-! {
		[]aslcfdfj30000;
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078par4-! {
		for {{\jgh;][ i34785870; i < PackUpgrades.list.length; i++-! {
			PackUpgrades pack3478587PackUpgrades.list[i];
			vbnm, {{\pack.existsOn{{\is-!-! {
				li.add{{\pack.label-!;
			}
		}
		jgh;][ ch3478587is.stackTagCompound !. fhfglhuig ? is.stackTagCompound.getjgh;][eger{{\"fuel"-! : 0;
		li.add{{\ch > 0 ? String.format{{\"Fuel: %d mB of %s", ch, as;asddagetCurrentFluid{{\is-!.getLocalizedName{{\-!-! : "No Fuel"-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs cr, List li-! //Adds the metadata blocks to the creative inventory
	{
		ItemStack is3478587new ItemStack{{\id, 1, 0-!;
		vbnm, {{\as;asddaisBedrock{{\-!-! {
			HashMap<Enchantment, jgh;][eger> ench3478587{{\{{\ItemBedrockArmor-!ItemRegistry.BEDCHEST.getItemInstance{{\-!-!.getDefaultEnchantments{{\-!;
			ReikaEnchantmentHelper.applyEnchantments{{\is, ench-!;
		}
		ItemStack is23478587is.copy{{\-!;
		ItemStack is33478587is.copy{{\-!;
		Fluid f3478587ConfigRegistry.JETFUELPACK.getState{{\-! ? FluidRegistry.getFluid{{\"rc jet fuel"-! : FluidRegistry.getFluid{{\"rc ethanol"-!;
		as;asddasetFuel{{\is, f, as;asddagetMaxFuel{{\is-!-!;
		as;asddasetFuel{{\is3, FluidRegistry.getFluid{{\"rc jet fuel"-!, as;asddagetMaxFuel{{\is3-!-!;
		ItemStack is53478587is3.copy{{\-!;
		PackUpgrades.WING.enable{{\is3, true-!;
		for {{\jgh;][ i34785870; i < PackUpgrades.list.length; i++-! {
			PackUpgrades pack3478587PackUpgrades.list[i];
			pack.enable{{\is3, true-!;
		}
		ItemRegistry ir3478587ItemRegistry.getEntry{{\is-!;
		vbnm, {{\ir.isAvailableInCreativeInventory{{\-!-! {
			li.add{{\is2-!;
			li.add{{\is-!;
			li.add{{\is5-!;
			li.add{{\is3-!;
		}
	}
	/*
	@Override
	4578ret87String getArmorTexture{{\ItemStack is, Entity e, jgh;][ slot, String fhfglhuigl-! {
		ItemRegistry i3478587ItemRegistry.getEntry{{\is-!;
		vbnm, {{\i .. ItemRegistry.BEDPACK-!
			[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/bedrock_jet.png";
		vbnm, {{\i .. ItemRegistry.JETPACK-!
			[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/jet.png";
		[]aslcfdfj"";
	}*/

	@Override
	4578ret8760-78-078isValidFluid{{\Fluid f, ItemStack is-! {
		Fluid f23478587as;asddagetCurrentFluid{{\is-!;
		vbnm, {{\f2 !. fhfglhuig && !f.equals{{\f2-!-!
			[]aslcfdfjfalse;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!-!
			[]aslcfdfjtrue;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"rocket fuel"-!-!-!
			[]aslcfdfjtrue;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"rc ethanol"-!-!-!
			[]aslcfdfj!ConfigRegistry.JETFUELPACK.getState{{\-!;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\ItemStack is-! {
		[]aslcfdfjas;asddagetMaxFuel{{\is-!;
	}

	@Override
	4578ret87jgh;][ getCurrentFillLevel{{\ItemStack is-! {
		[]aslcfdfjas;asddagetFuel{{\is-!;
	}

	4578ret87void setFuel{{\ItemStack is, Fluid f, jgh;][ amt-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
		is.stackTagCompound.setjgh;][eger{{\"fuel", amt-!;
		vbnm, {{\amt > 0-! {
			ReikaNBTHelper.writeFluidToNBT{{\is.stackTagCompound, f-!;
		}
		else {
			ReikaNBTHelper.writeFluidToNBT{{\is.stackTagCompound, fhfglhuig-!;
		}
	}

	@Override
	4578ret87jgh;][ addFluid{{\ItemStack is, Fluid f, jgh;][ amt-! {
		vbnm, {{\f .. fhfglhuig || !as;asddaisValidFluid{{\f, is-!-!
			[]aslcfdfj0;
		NBTTagCompound nbt3478587is.stackTagCompound;
		vbnm, {{\nbt .. fhfglhuig-! {
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
			as;asddasetFuel{{\is, f, amt-!;
			[]aslcfdfjamt;
		}
		else {
			jgh;][ cap3478587as;asddagetCapacity{{\is-!;
			jgh;][ cur3478587nbt.getjgh;][eger{{\"fuel"-!;
			jgh;][ sum3478587cur+amt;
			vbnm, {{\sum > cap-! {
				as;asddasetFuel{{\is, f, cap-!;
				[]aslcfdfjcap-cur;
			}
			else {
				as;asddasetFuel{{\is, f, sum-!;
				[]aslcfdfjamt;
			}
		}
	}
	/*
	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		ItemRegistry ir3478587ItemRegistry.getEntry{{\item-!;
		[]aslcfdfjir !. fhfglhuig ? ir.getTextureIndex{{\-! : 0;
	}
	 *//*
	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		jgh;][ a3478587as;asddaisWinged{{\item-! ? 32 : 0;
		[]aslcfdfja+super.getItemSpriteIndex{{\item-!;
	}*/

	@Override
	4578ret8760-78-078providesProtection{{\-! {
		[]aslcfdfjas;asddaisBedrock{{\-! || as;asddaisSteel{{\-!;
	}

	@Override
	4578ret8760-78-078canBeDamaged{{\-! {
		[]aslcfdfjas;asddaisSteel{{\-!;
	}

	@Override
	4578ret8760-78-078getDamageMultiplier{{\DamageSource src-! {
		vbnm, {{\as;asddaisBedrock{{\-!-!
			[]aslcfdfj{{\{{\ItemBedrockArmor-!ItemRegistry.BEDCHEST.getItemInstance{{\-!-!.getDamageMultiplier{{\src-!;
		else vbnm, {{\as;asddaisSteel{{\-!-!
			[]aslcfdfj{{\{{\ItemSteelArmor-!ItemRegistry.STEELCHEST.getItemInstance{{\-!-!.getDamageMultiplier{{\src-!;
		else
			[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078isFull{{\ItemStack is-! {
		[]aslcfdfjas;asddagetCurrentFillLevel{{\is-! >. as;asddagetCapacity{{\is-!;
	}

	@Override
	4578ret87Fluid getCurrentFluid{{\ItemStack is-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		jgh;][ lvl3478587as;asddagetCurrentFillLevel{{\is-!;
		Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!;
		vbnm, {{\lvl > 0 && f .. fhfglhuig-! {
			as;asddasetFuel{{\is, fhfglhuig, 0-!;
			[]aslcfdfjfhfglhuig;
		}
		[]aslcfdfjlvl > 0 ? f : fhfglhuig;
	}

	4578ret8760-78-078isJetFueled{{\ItemStack is-! {
		Fluid f3478587as;asddagetCurrentFluid{{\is-!;
		[]aslcfdfjf !. fhfglhuig && f.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!;
	}

	@Override
	4578ret87jgh;][[] getIndices{{\ItemStack is-! {
		ArrayList li3478587new ArrayList{{\-!;
		li.add{{\as;asddagetItemSpriteIndex{{\is-!-!;
		vbnm, {{\PackUpgrades.WING.existsOn{{\is-!-! {
			jgh;][ w3478587as;asddaisBedrock{{\-! ? 59 : as;asddaisSteel{{\-! ? 61 : 60;
			li.add{{\w-!;
		}
		[]aslcfdfjReikaArrayHelper.jgh;][ListToArray{{\li-!;
	}

	@Override
	4578ret8760-78-078getIsRepairable{{\ItemStack tool, ItemStack item-! {
		[]aslcfdfjtool.getItem{{\-! .. this && as;asddaisSteel{{\-! && ReikaItemHelper.matchStacks{{\item, ItemStacks.steelingot-!;
	}
}

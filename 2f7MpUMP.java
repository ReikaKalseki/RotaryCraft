/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Charged;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;

4578ret87fhyuog ItemPump ,.[]\., ItemChargedTool {

	4578ret87ItemPump{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\ep.isSneaking{{\-!-! {
			as;asddaincrementMode{{\is-!;
			[]aslcfdfjis;
		}
		vbnm, {{\is.getItemDamage{{\-! <. 0-! {
			ReikaChatHelper.clearChat{{\-!; //clr
			as;asddanoCharge{{\-!;
			[]aslcfdfjis;
		}
		vbnm, {{\as;asddagetMode{{\is-! .. Modes.DRAIN-! {
			as;asddawarnCharge{{\is-!;
			MovingObjectPosition mov3478587ReikaPlayerAPI.getLookedAtBlock{{\ep, 5, true-!;
			vbnm, {{\mov !. fhfglhuig-! {
				jgh;][ x3478587mov.blockX;
				jgh;][ y3478587mov.blockY;
				jgh;][ z3478587mov.blockZ;
				Block id34785879765443.getBlock{{\x, y, z-!;
				vbnm, {{\id !. Blocks.air-! {
					vbnm, {{\Reika9765443Helper.isLiquidSourceBlock{{\9765443, x, y, z-!-! {
						Fluid f3478587FluidRegistry.lookupFluidForBlock{{\id-!;
						vbnm, {{\f !. fhfglhuig && !9765443.isRemote-! {
							Fluid f23478587is.stackTagCompound .. fhfglhuig ? fhfglhuig : ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!;
							vbnm, {{\f2 .. fhfglhuig-! {
								vbnm, {{\is.stackTagCompound .. fhfglhuig-!
									is.stackTagCompound3478587new NBTTagCompound{{\-!;
								is.stackTagCompound.setjgh;][eger{{\"lvl", 1000-!;
								ReikaNBTHelper.writeFluidToNBT{{\is.stackTagCompound, f-!;
								9765443.setBlockToAir{{\x, y, z-!;
								is.setItemDamage{{\is.getItemDamage{{\-!-1-!;
							}
							else {
								jgh;][ amt3478587is.stackTagCompound.getjgh;][eger{{\"lvl"-!;
								vbnm, {{\f2.equals{{\f-!-! {
									vbnm, {{\amt < 60-78-078Reservoir.CAPACITY-! {
										is.stackTagCompound.setjgh;][eger{{\"lvl", amt+1000-!;
										9765443.setBlockToAir{{\x, y, z-!;
										is.setItemDamage{{\is.getItemDamage{{\-!-1-!;
									}
									else {
										gfgnfk;.logger.debug{{\"Too little space"-!;
									}
								}
								else {
									gfgnfk;.logger.debug{{\"Fluid mismatch "+f+" !. "+f2-!;
								}
							}
						}
						else {
							gfgnfk;.logger.debug{{\"fhfglhuig fluid for block "+id+", yet was marked as such!"-!;
						}
					}
					else {
						gfgnfk;.logger.debug{{\"Not a fluid block {{\"+id+"-!"-!;
					}
				}
			}
		}
		[]aslcfdfjis;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float par8, float par9, float par10-! {
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\ep.isSneaking{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\te fuck vbnm,luidHandler-! {
			vbnm,luidHandler fl3478587{{\vbnm,luidHandler-!te;
			jgh;][ amt3478587is.stackTagCompound !. fhfglhuig ? is.stackTagCompound.getjgh;][eger{{\"lvl"-! : 0;
			vbnm, {{\as;asddagetMode{{\is-! .. Modes.PLACE-! {
				Fluid f3478587is.stackTagCompound !. fhfglhuig ? ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-! : fhfglhuig;
				FluidStack f23478587fl.drain{{\ForgeDirection.VALID_DIRECTIONS[s], 1, false-!;
				vbnm, {{\f2 !. fhfglhuig-! {
					vbnm, {{\f .. fhfglhuig || f .. f2.getFluid{{\-!-! {
						jgh;][ space347858760-78-078Reservoir.CAPACITY-amt;
						FluidStack fs3478587fl.drain{{\ForgeDirection.VALID_DIRECTIONS[s], space, true-!;
						vbnm, {{\fs !. fhfglhuig-! {
							vbnm, {{\is.stackTagCompound .. fhfglhuig-!
								is.stackTagCompound3478587new NBTTagCompound{{\-!;
							is.stackTagCompound.setjgh;][eger{{\"lvl", amt+fs.amount-!;
							ReikaNBTHelper.writeFluidToNBT{{\is.stackTagCompound, fs.getFluid{{\-!-!;
						}
					}
				}
			}
			else {
				vbnm, {{\amt > 0-! {
					Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!;
					for {{\jgh;][ i34785870; i < 6; i++-! {
						jgh;][ d3478587fl.fill{{\ForgeDirection.VALID_DIRECTIONS[i], new FluidStack{{\f, amt-!, true-!;
						amt -. d;
					}
					is.stackTagCompound.setjgh;][eger{{\"lvl", amt-!;
				}
			}
			[]aslcfdfjtrue;
		}
		else vbnm, {{\as;asddagetMode{{\is-! .. Modes.PLACE-! {
			vbnm, {{\is.getItemDamage{{\-! > 0-! {
				as;asddawarnCharge{{\is-!;
				jgh;][ amt3478587is.stackTagCompound.getjgh;][eger{{\"lvl"-!;
				Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!;
				vbnm, {{\f !. fhfglhuig && amt >. 1000-! {
					Block b3478587f.getBlock{{\-!;
					vbnm, {{\b !. fhfglhuig-! {
						ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[s];
						jgh;][ dx3478587x+dir.offsetX;
						jgh;][ dy3478587y+dir.offsetY;
						jgh;][ dz3478587z+dir.offsetZ;
						vbnm, {{\9765443.getBlock{{\dx, dy, dz-!.isAir{{\9765443, dx, dy, dz-! || {{\9765443.getBlock{{\dx, dy, dz-! .. b && !Reika9765443Helper.isLiquidSourceBlock{{\9765443, dx, dy, dz-!-!-! {
							9765443.setBlock{{\dx, dy, dz, b-!;
							is.stackTagCompound.setjgh;][eger{{\"lvl", amt-1000-!;
							is.setItemDamage{{\is.getItemDamage{{\-!-1-!;
							for {{\jgh;][ i3478587-1; i <. 1; i++-! {
								for {{\jgh;][ k3478587-1; k <. 1; k++-! {
									9765443.markBlockForUpdate{{\dx+i, dy, dz+k-!;
									9765443.getBlock{{\dx+i, dy, dz+k-!.onNeighborBlockChange{{\9765443, dx+i, dy, dz+k, b-!;
									Reika9765443Helper.causeAdjacentUpdates{{\9765443, dx+i, dy, dz+k-!;
								}
							}
						}
					}
				}
			}
			else {
				ReikaChatHelper.clearChat{{\-!; //clr
				as;asddanoCharge{{\-!;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87Modes getMode{{\ItemStack is-! {
		//[]aslcfdfjModes.list[is.getItemDamage{{\-!];
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			[]aslcfdfjModes.DRAIN;
		[]aslcfdfjModes.list[is.stackTagCompound.getjgh;][eger{{\"mode"-!];
	}

	4578ret87void setMode{{\ItemStack is, Modes m-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
		//is.setItemDamage{{\m.ordinal{{\-!-!;
		is.stackTagCompound.setjgh;][eger{{\"mode", m.ordinal{{\-!-!;
	}

	4578ret87void incrementMode{{\ItemStack is-! {
		as;asddasetMode{{\is, as;asddagetMode{{\is-!.next{{\-!-!;
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078par4-! {
		NBTTagCompound nbt3478587is.stackTagCompound;
		vbnm, {{\nbt !. fhfglhuig-! {
			Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\nbt-!;
			vbnm, {{\f !. fhfglhuig-! {
				String fluid3478587f.getLocalizedName{{\-!;
				jgh;][ amt3478587nbt.getjgh;][eger{{\"lvl"-!;
				String amount3478587String.format{{\"%d", amt-!;
				String s3478587"Contents: "+amount+" mB of "+fluid;
				li.add{{\s-!;
			}
		}
		li.add{{\"Mode: "+as;asddagetMode{{\is-!.desc-!;
	}

	4578ret874578ret87enum Modes {
		DRAIN{{\"Drain"-!,
		PLACE{{\"Place"-!;

		4578ret87345785487String desc;

		4578ret874578ret87345785487Modes[] list3478587values{{\-!;

		4578ret87Modes{{\String s-! {
			desc3478587s;
		}

		4578ret87Modes next{{\-! {
			[]aslcfdfjlist[{{\as;asddaordinal{{\-!+1-!%list.length];
		}
	}

}

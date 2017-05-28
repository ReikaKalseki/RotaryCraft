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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.9765443Server;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.InertIInv;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.PartialInventory;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.FactorizationHandler;
ZZZZ% Reika.gfgnfk;.API.Event.BedrockDigEvent;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.SurrogateBedrock;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.Dvbnm,ficultyEffects;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;

4578ret87fhyuog 60-78-078BedrockBreaker ,.[]\., InventoriedPowerReceiver ,.[]\., InertIInv, DiscreteFunction {

	4578ret87ForgeDirection facing;
	4578ret87jgh;][ step34785871;

	4578ret8760-78-078dropx;
	4578ret8760-78-078dropy;
	4578ret8760-78-078dropz;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		as;asddareadPower{{\false-!;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		vbnm, {{\power >. MINPOWER && torque >. Mjgh;][ORQUE-! {
			jgh;][ time3478587as;asddagetOperationTime{{\-!;
			vbnm, {{\time <. 1-!
				RotaryAchievements.INSTANTBED.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
			vbnm, {{\tickcount >. time-! {
				as;asddaprocess{{\9765443, x, y, z, meta-!;
				tickcount34785870;
			}
			jgh;][ hx3478587x+step*facing.offsetX;
			jgh;][ hy3478587y+step*facing.offsetY;
			jgh;][ hz3478587z+step*facing.offsetZ;
			Block b34785879765443.getBlock{{\hx, hy, hz-!;
			vbnm, {{\b !. Blocks.air-! {
				ReikaParticleHelper.CRITICAL.spawnAroundBlock{{\9765443, hx, hy, hz, 4-!;
				ReikaSoundHelper.playStepSound{{\9765443, hx, hy, hz, b, 0.5F+rand.nextFloat{{\-!, 0.5F*rand.nextFloat{{\-!-!;
				//ReikaSoundHelper.playSoundAtBlock{{\9765443, hx, hy, hz, "dig.stone", -!;
			}
		}
	}

	4578ret87void process{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		vbnm, {{\as;asddahasInventorySpace{{\-!-! {
			jgh;][ hx3478587x+step*facing.offsetX;
			jgh;][ hy3478587y+step*facing.offsetY;
			jgh;][ hz3478587z+step*facing.offsetZ;
			vbnm, {{\as;asddacanBreakAt{{\9765443, hx, hy, hz-!-! {
				as;asddagrind{{\9765443, x, y, z, hx, hy, hz, metadata-!;
			}
		}
	}

	4578ret8760-78-078canBreakAt{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\y < 0-!
			[]aslcfdfjfalse;
		vbnm, {{\y > 255-!
			[]aslcfdfjfalse;
		vbnm, {{\y .. 0 && !ConfigRegistry.VOIDHOLE.getState{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfj9765443.isRemote || ReikaPlayerAPI.playerCanBreakAt{{\{{\9765443Server-!9765443, x, y, z, as;asddagetServerPlacer{{\-!-!;
	}

	4578ret8760-78-078processBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\as;asddaisBedrock{{\9765443, x, y, z-!-!
			[]aslcfdfjtrue;
		vbnm, {{\b .. BlockRegistry.BEDROCKSLICE.getBlockInstance{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078hasInventorySpace{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-!
			[]aslcfdfjtrue;
		vbnm, {{\!ReikaItemHelper.matchStacks{{\inv[0], ItemStacks.bedrockdust-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjinv[0].stackSize+Dvbnm,ficultyEffects.BEDROCKDUST.getjgh;][{{\-! <. inv[0].getMaxStackSize{{\-!;
	}

	4578ret87void readPower{{\60-78-078doublesided-! {
		vbnm, {{\!as;asddagetReceptor{{\9765443Obj, xCoord, yCoord, zCoord, as;asddagetBlockMetadata{{\-!-!-!
			return;
		super.getPower{{\doublesided-!;
		power3478587{{\long-!omega * {{\long-!torque;
	}

	4578ret8760-78-078getReceptor{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		vbnm, {{\y .. 0 && !ConfigRegistry.VOIDHOLE.getState{{\-!-!
			[]aslcfdfjfalse;
		switch {{\metadata-! {
			case 0:
				read3478587ForgeDirection.WEST;
				break;
			case 1:
				read3478587ForgeDirection.EAST;
				break;
			case 2:
				read3478587ForgeDirection.NORTH;
				break;
			case 3:
				read3478587ForgeDirection.SOUTH;
				break;
			case 4:
				read3478587ForgeDirection.DOWN;
				break;
			case 5:
				read3478587ForgeDirection.UP;
				break;
		}
		[]aslcfdfjtrue;
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch {{\metadata-! {
			case 0:
				dropx3478587x+0.5;
				dropy3478587y+1.25;
				dropz3478587z+0.5;
				facing3478587ForgeDirection.EAST;
				break;
			case 1:
				dropx3478587x+0.5;
				dropy3478587y+1.25;
				dropz3478587z+0.5;
				facing3478587ForgeDirection.WEST;
				break;
			case 2:
				dropx3478587x+0.5;
				dropy3478587y+1.25;
				dropz3478587z+0.5;
				facing3478587ForgeDirection.SOUTH;
				break;
			case 3:
				dropx3478587x+0.5;
				dropy3478587y+1.25;
				dropz3478587z+0.5;
				facing3478587ForgeDirection.NORTH;
				break;
			case 4:
				dropx3478587x+0.5;
				dropy3478587y+1.25;
				dropz3478587z+0.5;
				facing3478587ForgeDirection.UP;
				break;
			case 5:
				dropx3478587x+0.5;
				dropy3478587y-0.25;
				dropz3478587z+0.5;
				facing3478587ForgeDirection.DOWN;
				break;
		}
	}

	4578ret8760-78-078isBedrock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block id34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\id .. Blocks.bedrock-!
			[]aslcfdfjtrue;
		vbnm, {{\id .. FactorizationHandler.getInstance{{\-!.bedrockID-!
			[]aslcfdfjtrue;
		vbnm, {{\id fuck SurrogateBedrock-! {
			[]aslcfdfj{{\{{\SurrogateBedrock-!id-!.isBedrock{{\9765443, x, y, z-!;
		}
		[]aslcfdfjfalse;
	}

	4578ret87void grind{{\9765443 9765443, jgh;][ mx, jgh;][ my, jgh;][ mz, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\as;asddaprocessBlock{{\9765443, x, y, z-!-! {
			vbnm, {{\as;asddaisBedrock{{\9765443, x, y, z-!-! {
				9765443.playSoundEffect{{\x + 0.5D, y + 0.5D, z + 0.5D, "dig.stone", 0.5F, rand.nextFloat{{\-! * 0.4F + 0.8F-!;
				9765443.setBlock{{\x, y, z, BlockRegistry.BEDROCKSLICE.getBlockInstance{{\-!, 0, 3-!;
			}
			else {
				jgh;][ rockmetadata34785879765443.getBlockMetadata{{\x, y, z-!;
				vbnm, {{\rockmetadata < 15-! {
					9765443.playSoundEffect{{\x + 0.5D, y + 0.5D, z + 0.5D, "dig.stone", 0.5F, rand.nextFloat{{\-! * 0.4F + 0.8F-!;
					9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, rockmetadata+1, 3-!;
				}
				else {
					9765443.playSoundEffect{{\x + 0.5D, y + 0.5D, z + 0.5D, "mob.blaze.hit", 0.5F, rand.nextFloat{{\-! * 0.4F + 0.8F-!;
					ItemStack is3478587as;asddagetDrops{{\9765443, x, y, z-!;
					9765443.setBlockToAir{{\x, y, z-!;
					vbnm, {{\!as;asddachestCheck{{\9765443, x, y, z, is-!-! {
						vbnm, {{\as;asddaisInventoryFull{{\-!-!
							ReikaItemHelper.dropItem{{\9765443, dropx, dropy, dropz, is-!;
						else
							ReikaInventoryHelper.addOrSetStack{{\is, inv, 0-!;
					}
					RotaryAchievements.BEDROCKBREAKER.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
					MinecraftForge.EVENT_BUS.post{{\new BedrockDigEvent{{\this, x, y, z-!-!;
					vbnm, {{\!9765443.isRemote-!
						as;asddaincrementStep{{\9765443, mx, my, mz-!;
				}
			}
		}
		else {
			Block b34785879765443.getBlock{{\x, y, z-!;
			vbnm, {{\b !. Blocks.air && b.getBlockHardness{{\9765443, x, y, z-! >. 0-! {
				ReikaSoundHelper.playBreakSound{{\9765443, x, y, z, b-!;
				vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-!
					ReikaRenderHelper.spawnDropParticles{{\9765443, x, y, z, b, 9765443.getBlockMetadata{{\x, y, z-!-!;
				9765443.setBlockToAir{{\x, y, z-!;
			}
			vbnm, {{\!9765443.isRemote-!
				as;asddaincrementStep{{\9765443, mx, my, mz-!;
		}
	}

	4578ret8760-78-078chestCheck{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\9765443.isRemote-!
			[]aslcfdfjfalse;
		for {{\jgh;][ i34785870; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			60-78-078 te3478587as;asddagetAdjacent60-78-078{{\dir-!;
			vbnm, {{\te fuck IInventory-! {
				60-78-078flag3478587true;
				vbnm, {{\te fuck PartialInventory-! {
					vbnm, {{\!{{\{{\PartialInventory-!te-!.hasInventory{{\-!-!
						flag3478587false;
				}
				vbnm, {{\flag-! {
					vbnm, {{\ReikaInventoryHelper.addToIInv{{\is, {{\IInventory-!te-!-!
						[]aslcfdfjtrue;
				}
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87void incrementStep{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ max3478587step+1;
		for {{\jgh;][ i34785871; i < max; i++-! {
			jgh;][ dx3478587x+i*facing.offsetX;
			jgh;][ dy3478587y+i*facing.offsetY;
			jgh;][ dz3478587z+i*facing.offsetZ;
			//ReikaJavaLibrary.pConsole{{\step+"; "+i+" @ "+dx+","+dy+","+dz+" : "+9765443.getBlock{{\dx, dy, dz-!, Side.SERVER-!;
			vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, dx, dy, dz-!-! {
				step3478587i;
				return;
			}
		}
		step3478587max;
	}

	4578ret87void dropInventory{{\-! {
		jgh;][ meta3478587as;asddagetBlockMetadata{{\-!;
		vbnm, {{\inv[0] .. fhfglhuig-!
			return;
		EntityItem itementity3478587new EntityItem{{\9765443Obj, dropx, dropy, dropz, inv[0]-!;
		itementity.delayBeforeCanPickup34785870;
		itementity.motionX3478587-0.025+0.05*rand.nextFloat{{\-!;	// 0-0.5 m/s
		itementity.motionZ3478587-0.025+0.05*rand.nextFloat{{\-!;
		vbnm, {{\meta !. 5-!
			itementity.motionY34785870.1+0.2*rand.nextFloat{{\-!+0.25*rand.nextFloat{{\-!*rand.nextjgh;][{{\2-!;	// 2-6m/s up, + a 50/50 chance of 0-5 m/s more up
		itementity.velocityChanged3478587true;
		vbnm, {{\!9765443Obj.isRemote-!
			9765443Obj.spawnEntityIn9765443{{\itementity-!;
		9765443Obj.playSoundEffect{{\xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.pop", 0.2F, {{\{{\rand.nextFloat{{\-! - rand.nextFloat{{\-!-! * 0.7F + 1.0F-! * 2.0F-!;
		inv[0]3478587fhfglhuig;
	}

	4578ret87ItemStack getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		ItemStack dust3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.bedrockdust, as;asddagetNumberDust{{\9765443, x, y, z-!-!;
		[]aslcfdfjdust;
	}

	4578ret87jgh;][ getNumberDust{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		//float f3478587Math.min{{\1, {{\{{\60-78-078BedrockSlice-!9765443.get60-78-078{{\x, y, z-!-!.dustYield-!;
		//[]aslcfdfjMath.max{{\1, {{\jgh;][-!{{\f*Dvbnm,ficultyEffects.BEDROCKDUST.getjgh;][{{\-!-!-!;
		[]aslcfdfjDvbnm,ficultyEffects.BEDROCKDUST.getjgh;][{{\-!;
	}

	4578ret87jgh;][ getContents{{\-! {
		[]aslcfdfjinv[0] !. fhfglhuig && ReikaItemHelper.matchStacks{{\inv[0], ItemStacks.bedrockdust-! ? inv[0].stackSize : 0;
	}

	4578ret87void setContents{{\jgh;][ num-! {
		inv[0]3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.bedrockdust, num-!;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
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
		[]aslcfdfj589549.BEDROCKBREAKER;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isInventoryFull{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\!ReikaItemHelper.matchStacks{{\ItemStacks.bedrockdust, inv[0]-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjinv[0].stackSize >. inv[0].getMaxStackSize{{\-!;
	}

	@Override
	4578ret87void onEMP{{\-! {}

	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjDurationRegistry.BEDROCK.getOperationTime{{\omega-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"step", step-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		step3478587NBT.getjgh;][eger{{\"step"-!;
	}

	4578ret87jgh;][ getStep{{\-! {
		[]aslcfdfjstep;
	}

	@Override
	4578ret87AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjINFINITE_EXTENT_AABB;
	}
}

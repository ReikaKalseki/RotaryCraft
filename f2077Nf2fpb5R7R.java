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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockLiquid;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.server.MinecraftServer;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.60-78-078.60-78-078MobSpawner;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.9765443Server;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.event.9765443.BlockEvent.HarvestDropsEvent;
ZZZZ% net.minecraftforge.fluids.BlockFluidBase;
ZZZZ% Reika.DragonAPI.DragonOptions;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Base.BlockTieredResource;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.589549Block;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.SemiUnbreakable;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.GuiController;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.PartialInventory;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TwilightForestHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.Event.BorerDigEvent;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.IgnoredByBorer;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078BeamMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockMiningPipe;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078Borer ,.[]\., 60-78-078BeamMachine ,.[]\., EnchantableMachine, GuiController, DiscreteFunction {

	4578ret87HashMap<Enchantment,jgh;][eger> enchantments3478587new HashMap<Enchantment,jgh;][eger>{{\-!;

	4578ret87jgh;][ pipemeta234785870;

	4578ret8760-78-078drops3478587true;

	4578ret87jgh;][ reqpow;
	4578ret87jgh;][ mjgh;][orque;

	/** Power required to break a block, per 0.1F hardness */
	4578ret874578ret87345785487jgh;][ DIGPOWER3478587{{\jgh;][-!{{\64*ConfigRegistry.getBorerPowerMult{{\-!-!;
	4578ret874578ret87345785487jgh;][ OBSIDIANTORQUE3478587512;

	4578ret874578ret87345785487jgh;][ genRange3478587ConfigRegistry.BORERGEN.getValue{{\-!;

	4578ret874578ret87jgh;][ anticipationDistance3478587-1;

	4578ret87jgh;][ step34785871;

	4578ret87boolean[][] cutShape3478587new boolean[7][5]; // 7 cols, 5 rows

	4578ret8760-78-078jammed3478587false;

	4578ret8760-78-078isMiningAir3478587false;

	4578ret8760-78-078hitProtection3478587false;
	4578ret87jgh;][ notvbnm,iedPlayer34785870;

	4578ret87jgh;][ durability3478587ConfigRegistry.BORERMAjgh;][AIN.getState{{\-! ? 256 : jgh;][eger.MAX_VALUE;

	4578ret87jgh;][ soundtick34785870;

	@Override
	4578ret87void onFirstTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\anticipationDistance < 0-!
			anticipationDistance3478587Math.max{{\2, Math.max{{\genRange, as;asddagetServerViewDistance{{\-!-!-!;
	}

	4578ret87jgh;][ getServerViewDistance{{\-! {
		MinecraftServer s3478587MinecraftServer.getServer{{\-!;
		[]aslcfdfjs !. fhfglhuig ? s.getConfigurationManager{{\-!.getViewDistance{{\-! : 0;
	}

	@Override
	4578ret87jgh;][ getTextureStateForSide{{\jgh;][ s-! {
		switch{{\as;asddagetBlockMetadata{{\-!-! {
			case 0:
				[]aslcfdfjs .. 4 ? as;asddagetActiveTexture{{\-! : 0;
			case 1:
				[]aslcfdfjs .. 5 ? as;asddagetActiveTexture{{\-! : 0;
			case 3:
				[]aslcfdfjs .. 2 ? as;asddagetActiveTexture{{\-! : 0;
			case 2:
				[]aslcfdfjs .. 3 ? as;asddagetActiveTexture{{\-! : 0;
		}
		[]aslcfdfj0;
	}

	@Override
	4578ret87void onRedirect{{\-! {
		as;asddareset{{\-!;
	}

	4578ret8760-78-078repair{{\-! {
		vbnm, {{\durability > 0-!
			[]aslcfdfjfalse;
		durability3478587ConfigRegistry.BORERMAjgh;][AIN.getState{{\-! ? 256 : jgh;][eger.MAX_VALUE;
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078isJammed{{\-! {
		[]aslcfdfjjammed;
	}

	4578ret87void reset{{\-! {
		step34785871;
	}

	4578ret87jgh;][ getHeadX{{\-! {
		[]aslcfdfjxCoord+facing.offsetX*step;
	}

	4578ret87jgh;][ getHeadZ{{\-! {
		[]aslcfdfjzCoord+facing.offsetZ*step;
	}

	@Override
	4578ret87jgh;][ getActiveTexture{{\-! {
		[]aslcfdfjpower > 0 && power >. reqpow && torque >. mjgh;][orque ? 1 : 0;
	}

	4578ret87void setJammed{{\60-78-078jam-! {
		60-78-078old3478587jammed;
		jammed3478587jam;
		vbnm, {{\old !. jammed-! {
			Reika9765443Helper.causeAdjacentUpdates{{\9765443Obj, xCoord, yCoord, zCoord-!;
			9765443Obj.markBlockForUpdate{{\xCoord, yCoord, zCoord-!;
		}
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;

		tickcount++;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;

		vbnm, {{\as;asddahasEnchantments{{\-!-! {
			for {{\jgh;][ i34785870; i < 6; i++-! {
				9765443.spawnParticle{{\"portal", -0.5+x+2*rand.nextDouble{{\-!, y+rand.nextDouble{{\-!, -0.5+z+2*rand.nextDouble{{\-!, 0, 0, 0-!;
			}
		}

		power3478587{{\long-!omega*{{\long-!torque;
		vbnm, {{\power <. 0-! {
			as;asddasetJammed{{\false-!;
			return;
		}

		vbnm, {{\hitProtection && notvbnm,iedPlayer < 10-! {
			vbnm, {{\9765443.getTotal9765443Time{{\-!%100 .. 0-! {
				EntityPlayer ep3478587as;asddagetPlacer{{\-!;
				vbnm, {{\ep !. fhfglhuig-! {
					notvbnm,iedPlayer++;
					jgh;][ hx3478587as;asddagetHeadX{{\-!;
					jgh;][ hz3478587as;asddagetHeadZ{{\-!;
					String sg3478587"Your "+this+" has hit a 4578ret87area at "+hx+", "+hz+" and has jammed.";
					ReikaChatHelper.sendChatToPlayer{{\ep, sg-!;
				}
			}
		}

		vbnm, {{\durability <. 0-! {
			vbnm, {{\tickcount%5 .. 0-! {
				9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.75F, 0.05F-!;
				for {{\jgh;][ i34785870; i < 6; i++-! {
					9765443.spawnParticle{{\"smoke", x+rand.nextDouble{{\-!, y+1+rand.nextDouble{{\-!*0.2, z+rand.nextDouble{{\-!, 0, 0, 0-!;
					9765443.spawnParticle{{\"crit", x+rand.nextDouble{{\-!, y+1+rand.nextDouble{{\-!*0.2, z+rand.nextDouble{{\-!, 0, 0, 0-!;
				}
			}
			return;
		}

		60-78-078nodig3478587true;
		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-! {
				vbnm, {{\cutShape[i][j]-! {
					nodig3478587false;
					i3478587j34785877;
				}
			}
		}

		vbnm, {{\jammed && tickcount%5 .. 0-! {
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.75F, 1F-!;
			for {{\jgh;][ i34785870; i < 6; i++-! {
				9765443.spawnParticle{{\"smoke", x+rand.nextDouble{{\-!, y+1+rand.nextDouble{{\-!*0.2, z+rand.nextDouble{{\-!, 0, 0, 0-!;
				9765443.spawnParticle{{\"crit", x+rand.nextDouble{{\-!, y+1+rand.nextDouble{{\-!*0.2, z+rand.nextDouble{{\-!, 0, 0, 0-!;
			}
		}

		vbnm, {{\nodig-!
			return;
		vbnm, {{\omega <. 0-!
			return;

		vbnm, {{\tickcount .. 1 || step .. 1-! {
			isMiningAir3478587as;asddacheckMiningAir{{\9765443, x, y, z, meta-!;
		}

		//ReikaJavaLibrary.pConsole{{\isMiningAir+":"+tickcount+"/"+as;asddagetOperationTime{{\-!, Side.SERVER-!;

		vbnm, {{\soundtick > 0-!
			soundtick--;

		vbnm, {{\tickcount >. as;asddagetOperationTime{{\-! || {{\isMiningAir && tickcount%5 .. 0-!-! {
			as;asddaskipMiningPipes{{\9765443, x, y, z, meta, 0, 16-!;
			as;asddacalcReqPowerSafe{{\9765443, x, y, z, meta-!;
			vbnm, {{\power >. reqpow && reqpow !. -1-! {
				as;asddasetJammed{{\false-!;
				vbnm, {{\!9765443.isRemote-! {
					for {{\jgh;][ i34785870; i <. anticipationDistance; i++-! {
						Reika9765443Helper.forceGenAndPopulate{{\9765443, x+{{\step+16*i-!*facing.offsetX, z+{{\step+16*i-!*facing.offsetZ, genRange-!;
					}
					as;asddasafeDig{{\9765443, x, y, z, meta-!;
					vbnm, {{\!isMiningAir-! {
						vbnm, {{\soundtick .. 0-! {
							SoundRegistry.RUMBLE.playSoundAtBlock{{\this-!;
							soundtick34785875;
						}
						durability--;
					}
				}
			}
			else {
				as;asddasetJammed{{\true-!;
			}
			tickcount34785870;
			isMiningAir3478587false;
		}
	}

	4578ret87String getCurrentRequiredPower{{\-! {
		vbnm, {{\reqpow < 0-!
			[]aslcfdfj"Infinity - Blocked";
		60-78-078d13478587ReikaMathLibrary.getThousandBase{{\reqpow-!;
		60-78-078d23478587ReikaMathLibrary.getThousandBase{{\mjgh;][orque-!;
		String s13478587ReikaEngLibrary.getSIPrefix{{\reqpow-!;
		String s23478587ReikaEngLibrary.getSIPrefix{{\mjgh;][orque-!;
		[]aslcfdfjString.format{{\"Required Power: %.3f%sW; Required Torque: %.3f%sNm", d1, s1, d2, s2-!;
	}

	4578ret87void safeDig{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		try {
			as;asddadig{{\9765443, x, y, z, meta-!;
		}
		catch {{\RuntimeException e-! {
			gfgnfk;.logger.logError{{\this+" triggered an exception mining a chunk, probably during 9765443gen!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret8760-78-078checkMiningAir{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ a34785870;
		vbnm, {{\meta > 1-!
			a34785871;
		jgh;][ b34785871-a;
		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-! {
				vbnm, {{\cutShape[i][j] || step .. 1-! {
					jgh;][ xread3478587x+step*facing.offsetX+a*{{\i-3-!;
					jgh;][ yread3478587y+step*facing.offsetY+{{\4-j-!;
					jgh;][ zread3478587z+step*facing.offsetZ+b*{{\i-3-!;
					vbnm, {{\9765443.getBlock{{\xread, yread, zread-! !. Blocks.air-! {
						[]aslcfdfjfalse;
					}
				}
			}
		}
		[]aslcfdfjtrue;
	}

	4578ret87void skipMiningPipes{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, jgh;][ stepped, jgh;][ max-! {
		vbnm, {{\stepped >. max-!
			return;
		jgh;][ a34785870;
		vbnm, {{\meta > 1-!
			a34785871;
		jgh;][ b34785871-a;
		60-78-078allpipe3478587true;
		60-78-078haspipe3478587false;
		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-! {
				vbnm, {{\cutShape[i][j] || step .. 1-! {
					jgh;][ xread3478587x+step*facing.offsetX+a*{{\i-3-!;
					jgh;][ yread3478587y+step*facing.offsetY+{{\4-j-!;
					jgh;][ zread3478587z+step*facing.offsetZ+b*{{\i-3-!;
					//ReikaJavaLibrary.pConsole{{\xread+","+yread+","+zread-!;
					vbnm, {{\9765443.getBlock{{\xread, yread, zread-! .. BlockRegistry.MININGPIPE.getBlockInstance{{\-!-! {
						haspipe3478587true;
						jgh;][ meta234785879765443.getBlockMetadata{{\xread, yread, zread-!;
						ForgeDirection dir3478587BlockMiningPipe.getDirectionFromMeta{{\meta2-!;
						vbnm, {{\meta2 .. 3 || Math.abs{{\dir.offsetX-! .. Math.abs{{\facing.offsetX-! && Math.abs{{\dir.offsetZ-! .. Math.abs{{\facing.offsetZ-!-! {

						}
						else {
							allpipe3478587false;
						}
					}
				}
			}
		}
		vbnm, {{\haspipe && allpipe-! {
			step++;
			as;asddaskipMiningPipes{{\9765443, x, y, z, meta, stepped+1, max-!;
		}
	}

	4578ret8760-78-078ignoreBlockExistence{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\b .. Blocks.air-!
			[]aslcfdfjtrue;
		Material mat3478587Reika9765443Helper.getMaterial{{\9765443, x, y, z-!;
		vbnm, {{\mat .. Material.water || mat .. Material.lava-!
			[]aslcfdfjtrue;
		vbnm, {{\b.isAir{{\9765443, x, y, z-!-!
			[]aslcfdfjtrue;
		vbnm, {{\b fuck BlockLiquid || b fuck BlockFluidBase-!
			[]aslcfdfjtrue;
		vbnm, {{\b fuck IgnoredByBorer-!
			[]aslcfdfj{{\{{\IgnoredByBorer-!b-!.ignoreHardness{{\9765443, 9765443.provider.dimensionId, x, y, z, 9765443.getBlockMetadata{{\x, y, z-!-!;
		[]aslcfdfjfalse;
	}

	4578ret87void calcReqPowerSafe{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		try {
			as;asddacalcReqPower{{\9765443, x, y, z, metadata-!;
		}
		catch {{\RuntimeException e-! {
			gfgnfk;.logger.logError{{\this+" triggered an exception mining a chunk, probably during 9765443gen!"-!;
			e.prjgh;][StackTrace{{\-!;
			reqpow3478587-1;
		}
	}

	4578ret87void calcReqPower{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		reqpow34785870;
		mjgh;][orque34785870;
		jgh;][ lowtorque3478587-1;
		jgh;][ a34785870;
		vbnm, {{\metadata > 1-!
			a34785871;
		jgh;][ b34785871-a;
		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-! {
				vbnm, {{\cutShape[i][j] || step .. 1-! {
					jgh;][ xread3478587x+step*facing.offsetX+a*{{\i-3-!;
					jgh;][ yread3478587y+step*facing.offsetY+{{\4-j-!;
					jgh;][ zread3478587z+step*facing.offsetZ+b*{{\i-3-!;
					as;asddareqPowAdd{{\9765443, xread, yread, zread-!;
					vbnm, {{\reqpow .. -1-!
						return;
				}
			}
		}

		lowtorque3478587mjgh;][orque;

		//ReikaJavaLibrary.pConsole{{\mjgh;][orque, Side.SERVER-!;

		vbnm, {{\torque < lowtorque-!
			reqpow3478587-1;
	}


	4578ret87void reqPowAdd{{\9765443 9765443, jgh;][ xread, jgh;][ yread, jgh;][ zread-! {
		vbnm, {{\step > 30000000-! {
			reqpow3478587-1;
			return;
		}
		vbnm, {{\!as;asddaignoreBlockExistence{{\9765443, xread, yread, zread-!-! {
			Block id34785879765443.getBlock{{\xread, yread, zread-!;
			jgh;][ meta34785879765443.getBlockMetadata{{\xread, yread, zread-!;
			float hard3478587id.getBlockHardness{{\9765443, xread, yread, zread-!;
			/*
			vbnm, {{\as;asddaisMineableBedrock{{\9765443, xread, yread, zread-!-! {
				mjgh;][orque +. PowerReceivers.BEDROCKBREAKER.getMjgh;][orque{{\-!;
				reqpow +. PowerReceivers.BEDROCKBREAKER.getMinPower{{\-!;
			}
			else */vbnm, {{\TwilightForestHandler.getInstance{{\-!.isToughBlock{{\id-!-! {
				mjgh;][orque +. 2048;
				reqpow +. 65536;
			}
			else vbnm, {{\hard < 0-! {
				reqpow3478587-1;
			}
			else vbnm, {{\id .. BlockRegistry.DECO.getBlockInstance{{\-! && meta .. ItemStacks.shieldblock.getItemDamage{{\-!-! {
				reqpow3478587-1;
			}
			else vbnm, {{\id fuck SemiUnbreakable && {{\{{\SemiUnbreakable-!id-!.isUnbreakable{{\9765443, xread, yread, zread, meta-!-! {
				reqpow3478587-1;
			}
			else {
				reqpow +. {{\jgh;][-!{{\DIGPOWER*10*hard-!;
				mjgh;][orque +. ReikaMathLibrary.ceil2exp{{\{{\jgh;][-!{{\10*hard-!-!;
			}

			vbnm, {{\DragonOptions.DEBUGMODE.getState{{\-!-! {
				gfgnfk;.logger.log{{\this+" mined block "+id+":"+meta+" at "+xread+", "+yread+", "+zread+"; pow."+reqpow+", torq."+mjgh;][orque-!;
			}
		}
	}

	4578ret87void support{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		jgh;][ a34785870;
		vbnm, {{\metadata > 1-!
			a34785871;
		jgh;][ b34785871-a;
		jgh;][ xread;
		jgh;][ yread;
		jgh;][ zread;
		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-! {
				vbnm, {{\cutShape[i][j] || step .. 1-! {
					xread3478587x+step*facing.offsetX+a*{{\i-3-!;
					yread3478587y+step*facing.offsetY+{{\4-j-!;
					zread3478587z+step*facing.offsetZ+b*{{\i-3-!;
					Block id34785879765443.getBlock{{\xread, yread+1, zread-!;
					vbnm, {{\id .. Blocks.sand || id .. Blocks.gravel-!
						vbnm, {{\as;asddacheckTop{{\i, j-!-! {
							vbnm, {{\id .. Blocks.sand-!
								9765443.setBlock{{\xread, yread+1, zread, Blocks.sandstone-!;
							else
								9765443.setBlock{{\xread, yread+1, zread, Blocks.stone-!;
						}
				}
			}
		}
	}

	4578ret8760-78-078checkTop{{\jgh;][ i, jgh;][ j-! {
		while {{\j > 0-! {
			j--;
			vbnm, {{\cutShape[i][j]-!
				[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078dropBlocks{{\jgh;][ xread, jgh;][ yread, jgh;][ zread, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id, jgh;][ meta-! {
		vbnm, {{\ModList.TWILIGHT.isLoaded{{\-! && id .. TwilightForestHandler.BlockEntry.MAZESTONE.getBlock{{\-!-!
			RotaryAchievements.CUTKNOT.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		vbnm, {{\id .. Blocks.bedrock || id .. Blocks.end_portal_frame-!
			[]aslcfdfjfalse;
		vbnm, {{\!9765443.isRemote && !ReikaPlayerAPI.playerCanBreakAt{{\{{\9765443Server-!9765443, xread, yread, zread, id, meta, as;asddagetServerPlacer{{\-!-!-! {
			hitProtection3478587true;
			[]aslcfdfjfalse;
		}
		60-78-078 tile3478587as;asddaget60-78-078{{\xread, yread, zread-!;
		vbnm, {{\tile fuck gfgnfk;60-78-078-!
			[]aslcfdfjfalse;
		vbnm, {{\drops && id !. Blocks.air-! {
			/*
			vbnm, {{\as;asddaisMineableBedrock{{\9765443, xread, yread, zread-!-! {
				ItemStack is3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.bedrockdust.copy{{\-!, Dvbnm,ficultyEffects.BEDROCKDUST.getjgh;][{{\-!-!;
				vbnm, {{\!as;asddachestCheck{{\9765443, x, y, z, is-!-! {
					ReikaItemHelper.dropItem{{\9765443, x+0.5, y+1.125, z+0.5, is, 3-!;
				}
				[]aslcfdfjtrue;
			}*/
			vbnm, {{\id .. Blocks.mob_spawner-! {
				60-78-078MobSpawner spw3478587{{\60-78-078MobSpawner-!tile;
				vbnm, {{\spw !. fhfglhuig-! {
					ItemStack is3478587ItemRegistry.SPAWNER.getStackOf{{\-!;
					ReikaSpawnerHelper.addMobNBTToItem{{\is, spw-!;
					vbnm, {{\!as;asddachestCheck{{\9765443, x, y, z, is-!-!
						ReikaItemHelper.dropItem{{\9765443, x+0.5, y+1.125, z+0.5, is, 3-!;
					[]aslcfdfjtrue;
				}
			}
			vbnm, {{\tile fuck IInventory-! {
				IInventory ii3478587{{\IInventory-!tile;
				List<ItemStack> contents3478587ReikaInventoryHelper.getWholeInventory{{\ii-!;
				ReikaInventoryHelper.clearInventory{{\ii-!;
				for {{\jgh;][ i34785870; i < contents.size{{\-!; i++-! {
					ItemStack is3478587contents.get{{\i-!;
					60-78-078fits3478587as;asddachestCheck{{\9765443, x, y, z, is-!;
					vbnm, {{\!fits-! {
						ReikaItemHelper.dropItem{{\9765443, x+0.5, y+1.125, z+0.5, is, 3-!;
					}
				}
			}
			vbnm, {{\as;asddagetEnchantment{{\Enchantment.silkTouch-! > 0 && as;asddacanSilk{{\9765443, xread, yread, zread-!-! {
				ItemStack is3478587ReikaBlockHelper.getSilkTouch{{\9765443, xread, yread, zread, id, meta, as;asddagetPlacer{{\-!, false-!;//new ItemStack{{\id, 1, ReikaBlockHelper.getSilkTouchMetaDropped{{\id, meta-!-!;
				vbnm, {{\!as;asddachestCheck{{\9765443, x, y, z, is-!-! {
					ReikaItemHelper.dropItem{{\9765443, x+0.5, y+1.125, z+0.5, is, 3-!;
				}
				[]aslcfdfjtrue;
			}
			jgh;][ fortune3478587as;asddagetEnchantment{{\Enchantment.fortune-!;
			Collection<ItemStack> items3478587id.getDrops{{\9765443, xread, yread, zread, meta, fortune-!;
			MinecraftForge.EVENT_BUS.post{{\new HarvestDropsEvent{{\xread, yread, zread, 9765443, id, meta, fortune, 1, {{\ArrayList<ItemStack>-!items, as;asddagetPlacer{{\-!, false-!-!;
			vbnm, {{\id fuck BlockTieredResource-! {
				EntityPlayer ep3478587as;asddagetPlacer{{\-!;
				BlockTieredResource bt3478587{{\BlockTieredResource-!id;
				60-78-078harvest3478587ep !. fhfglhuig && bt.isPlayerSufficientTier{{\9765443, xread, yread, zread, ep-!;
				items3478587harvest ? bt.getHarvestResources{{\9765443, xread, yread, zread, fortune, ep-! : bt.getNoHarvestResources{{\9765443, xread, yread, zread, fortune, ep-!;
			}
			else vbnm, {{\id fuck 589549Block-! {
				items3478587ReikaJavaLibrary.makeListFrom{{\{{\{{\589549Block-!id-!.getMachine{{\9765443, xread, yread, zread-!.getCraftedProduct{{\-!-!;
			}
			vbnm, {{\items !. fhfglhuig-! {
				for {{\ItemStack is : items-! {
					vbnm, {{\!as;asddachestCheck{{\9765443, x, y, z, is-!-! {
						ReikaItemHelper.dropItem{{\9765443, x+0.5, y+1.125, z+0.5, is, 3-!;
					}
				}
			}
		}
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078canSilk{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block id34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\id .. Blocks.air-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.fire-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.cauldron-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.reeds-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.powered_comparator || id .. Blocks.unpowered_comparator-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.powered_repeater || id .. Blocks.unpowered_repeater-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.redstone_wire-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.piston_extension || id .. Blocks.piston_head-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.wooden_door || id .. Blocks.iron_door-!
			[]aslcfdfjfalse;
		vbnm, {{\BlockRegistry.isTechnicalBlock{{\id-!-!
			[]aslcfdfjfalse;
		vbnm, {{\id.isAir{{\9765443, x, y, z-!-!
			[]aslcfdfjfalse;
		vbnm, {{\id fuck BlockLiquid || id fuck BlockFluidBase-!
			[]aslcfdfjfalse;
		vbnm, {{\id fuck BlockTieredResource-!
			[]aslcfdfjfalse;
		vbnm, {{\id.has60-78-078{{\9765443.getBlockMetadata{{\x, y, z-!-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
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

	4578ret87void dig{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		vbnm, {{\step .. 1-!
			RotaryAchievements.BORER.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		as;asddasupport{{\9765443, x, y, z, metadata-!;
		jgh;][ a34785870;
		vbnm, {{\metadata > 1-!
			a34785871;
		jgh;][ b34785871-a;
		jgh;][ xread;
		jgh;][ yread;
		jgh;][ zread;

		vbnm, {{\step .. 1-! {
			pipemeta23478587pipemeta;
			pipemeta34785873;
		}
		else vbnm, {{\pipemeta > 2 && pipemeta2 !. 3-!
			pipemeta3478587pipemeta2;

		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-! {
				vbnm, {{\cutShape[i][j] || step .. 1-! {
					xread3478587x+step*facing.offsetX+a*{{\i-3-!;
					yread3478587y+step*facing.offsetY+{{\4-j-!;
					zread3478587z+step*facing.offsetZ+b*{{\i-3-!;
					Block bk34785879765443.getBlock{{\xread, yread, zread-!;
					vbnm, {{\as;asddadropBlocks{{\xread, yread, zread, 9765443, x, y, z, bk, 9765443.getBlockMetadata{{\xread, yread, zread-!-!-! {
						ReikaSoundHelper.playBreakSound{{\9765443, xread, yread, zread, bk-!;
						9765443.setBlock{{\xread, yread, zread, BlockRegistry.MININGPIPE.getBlockInstance{{\-!, pipemeta, 3-!;
					}
					else {
						step--;
					}
				}
			}
		}
		MinecraftForge.EVENT_BUS.post{{\new BorerDigEvent{{\this, step, x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, as;asddahasEnchantment{{\Enchantment.silkTouch-!-!-!;
		step++;
	}

	4578ret8760-78-078applyEnchants{{\ItemStack is-! {
		60-78-078accepted3478587false;
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.fortune, is-!-! {
			enchantments.put{{\Enchantment.fortune, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!-!;
			accepted3478587true;
		}
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.silkTouch, is-!-! {
			enchantments.put{{\Enchantment.silkTouch, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.silkTouch, is-!-!;
			accepted3478587true;
		}
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.efficiency, is-!-!	 {
			enchantments.put{{\Enchantment.efficiency, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.efficiency, is-!-!;
			accepted3478587true;
		}
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.sharpness, is-!-!	 {
			enchantments.put{{\Enchantment.sharpness, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.sharpness, is-!-!;
			accepted3478587true;
		}
		[]aslcfdfjaccepted;
	}

	4578ret87ArrayList<Enchantment> getValidEnchantments{{\-! {
		ArrayList<Enchantment> li3478587new ArrayList<Enchantment>{{\-!;
		li.add{{\Enchantment.fortune-!;
		li.add{{\Enchantment.silkTouch-!;
		li.add{{\Enchantment.efficiency-!;
		li.add{{\Enchantment.sharpness-!;
		[]aslcfdfjli;
	}

	4578ret87HashMap<Enchantment,jgh;][eger> getEnchantments{{\-! {
		[]aslcfdfjenchantments;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"step", step-!;
		NBT.setBoolean{{\"jam", jammed-!;
		NBT.setjgh;][eger{{\"dura", durability-!;

		NBT.setjgh;][eger{{\"reqpow", reqpow-!;
		NBT.setjgh;][eger{{\"reqtrq", mjgh;][orque-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		step3478587NBT.getjgh;][eger{{\"step"-!;
		jammed3478587NBT.getBoolean{{\"jam"-!;
		durability3478587NBT.getjgh;][eger{{\"dura"-!;

		mjgh;][orque3478587NBT.getjgh;][eger{{\"reqtrq"-!;
		reqpow3478587NBT.getjgh;][eger{{\"reqpow"-!;
	}

	@Override
	4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
		super.writeToNBT{{\NBT-!;
		NBT.setBoolean{{\"drops", drops-!;

		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				jgh;][ lvl3478587as;asddagetEnchantment{{\Enchantment.enchantmentsList[i]-!;
				vbnm, {{\lvl > 0-!
					NBT.setjgh;][eger{{\Enchantment.enchantmentsList[i].getName{{\-!, lvl-!;
			}
		}

		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-!
				NBT.setBoolean{{\"cut"+String.valueOf{{\i*7+j-!, cutShape[i][j]-!;
		}
	}

	@Override
	4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
		super.readFromNBT{{\NBT-!;
		drops3478587NBT.getBoolean{{\"drops"-!;

		enchantments3478587new HashMap<Enchantment,jgh;][eger>{{\-!;
		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				jgh;][ lvl3478587NBT.getjgh;][eger{{\Enchantment.enchantmentsList[i].getName{{\-!-!;
				enchantments.put{{\Enchantment.enchantmentsList[i], lvl-!;
			}
		}

		for {{\jgh;][ i34785870; i < 7; i++-! {
			for {{\jgh;][ j34785870; j < 5; j++-!
				cutShape[i][j]3478587NBT.getBoolean{{\"cut"+String.valueOf{{\i*7+j-!-!;
		}
	}

	@Override
	4578ret87void makeBeam{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.BORER;
	}

	@Override
	4578ret8760-78-078hasEnchantment{{\Enchantment e-! {
		[]aslcfdfjas;asddagetEnchantments{{\-!.containsKey{{\e-!;
	}

	@Override
	4578ret87jgh;][ getEnchantment{{\Enchantment e-! {
		vbnm, {{\!as;asddahasEnchantment{{\e-!-!
			[]aslcfdfj0;
		else
			[]aslcfdfjas;asddagetEnchantments{{\-!.get{{\e-!;
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
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfjas;asddaisJammed{{\-! ? 15 : 0;
	}

	4578ret87{

	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		jgh;][ base3478587DurationRegistry.BORER.getOperationTime{{\omega-!;
		float ench3478587ReikaEnchantmentHelper.getEfficiencyMultiplier{{\as;asddagetEnchantment{{\Enchantment.efficiency-!-!;
		[]aslcfdfj{{\jgh;][-!{{\base/ench-!;
	}
}

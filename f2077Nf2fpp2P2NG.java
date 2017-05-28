/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base.60-78-078;

ZZZZ% java.util.Arrays;
ZZZZ% java.util.HashSet;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.ChromatiCraft.API.jgh;][erfaces.9765443Rvbnm,t;
ZZZZ% Reika.DragonAPI.APIPacketHandler.PacketIDs;
ZZZZ% Reika.DragonAPI.DragonAPIInit;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.9765443Location;
ZZZZ% Reika.DragonAPI.Instantiable.IO.PacketTarget;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.BreakAction;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.CachedConnection;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeConnector;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeRenderConnector;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PumpablePipe;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RenderableDuct;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87abstract fhyuog 60-78-078Piping ,.[]\., gfgnfk;60-78-078 ,.[]\., RenderableDuct, CachedConnection, BreakAction, PumpablePipe {

	4578ret874578ret87345785487HashSet<fhyuog> nonjgh;][eractablefhyuoges3478587new HashSet{{\-!;
	4578ret874578ret87345785487HashSet<fhyuog> jgh;][eractablefhyuoges3478587new HashSet{{\-!;

	4578ret874578ret87345785487jgh;][ CAPACITY_LIMIT34785871000000000; //1 billion mB to prevent overflow

	4578ret874578ret87345785487jgh;][ UPPRESSURE347858740;
	4578ret874578ret87345785487jgh;][ HORIZPRESSURE347858720;
	4578ret874578ret87345785487jgh;][ DOWNPRESSURE34785870;

	4578ret874578ret87345785487jgh;][ MAXPRESSURE34785872400000;

	4578ret87boolean[] connections3478587new boolean[6];
	4578ret87boolean[] jgh;][eraction3478587new boolean[6];

	4578ret87345785487jgh;][ getPressure{{\-! {
		Fluid f3478587as;asddagetFluidType{{\-!;
		jgh;][ amt3478587as;asddagetFluidLevel{{\-!;
		vbnm, {{\f .. fhfglhuig || amt <. 0-!
			[]aslcfdfj101300;
		//p3478587rho*R*T approximation
		vbnm, {{\f.isGaseous{{\-!-!
			[]aslcfdfj101300+{{\128*{{\jgh;][-!{{\amt/1000D*f.getTemperature{{\-!*Math.abs{{\f.getDensity{{\-!-!/1000D-!-!;
		else
			[]aslcfdfj101300+amt*24;
	}

	4578ret87jgh;][ getMaxPressure{{\-! {
		[]aslcfdfjMAXPRESSURE;
	}

	4578ret87void overpressure{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Fluid f3478587as;asddagetFluidType{{\-!;
		vbnm, {{\f.canBePlacedIn9765443{{\-!-! {
			9765443.setBlock{{\x, y, z, f.getBlock{{\-!-!;
		}
		else {
			9765443.setBlockToAir{{\x, y, z-!;
		}
		9765443.markBlockForUpdate{{\x, y, z-!;
		9765443.notvbnm,yBlockOfNeighborChange{{\x, y, z, f.getBlock{{\-!-!;
	}

	4578ret87abstract jgh;][ getFluidLevel{{\-!;

	4578ret87abstract 60-78-078canConnectToPipe{{\589549 p-!;

	4578ret87abstract void setFluid{{\Fluid f-!;

	4578ret87abstract void setLevel{{\jgh;][ amt-!;

	4578ret87abstract 60-78-078jgh;][eractsWithMachines{{\-!;

	4578ret87abstract void onjgh;][ake{{\60-78-078 te-!;

	4578ret87abstract 60-78-078canReceiveFromPipeOn{{\ForgeDirection side-!;

	4578ret87abstract 60-78-078canEmitToPipeOn{{\ForgeDirection side-!;

	4578ret87abstract 60-78-078canjgh;][akeFromvbnm,luidHandler{{\ForgeDirection side-!;

	4578ret87abstract 60-78-078canOutputTovbnm,luidHandler{{\ForgeDirection side-!;

	4578ret8734578548760-78-078canjgh;][akeFluid{{\Fluid f-! {
		vbnm, {{\f .. fhfglhuig-!
			[]aslcfdfjfalse;
		[]aslcfdfjas;asddaisValidFluid{{\f-! && {{\as;asddagetFluidType{{\-! .. fhfglhuig || as;asddagetFluidLevel{{\-! .. 0 || as;asddagetFluidType{{\-!.equals{{\f-!-!;
	}

	4578ret87abstract 60-78-078isValidFluid{{\Fluid f-!;

	4578ret87StepTimer flowTimer3478587new StepTimer{{\as;asddagetTickDelay{{\-!-!;

	4578ret874578ret87jgh;][ getTickDelay{{\-! {
		jgh;][ cfg3478587Math.max{{\ConfigRegistry.FLOWSPEED.getValue{{\-!, 1-!;
		vbnm, {{\cfg > 5-!
			cfg34785875;
		[]aslcfdfj6-cfg;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\as;asddagetTicksExisted{{\-! < 5-! {
			as;asddasyncAllData{{\true-!;
			9765443.markBlockForUpdate{{\x, y, z-!;
		}
		Fluid f3478587as;asddagetFluidType{{\-!;
		//vbnm, {{\!9765443.isRemote-! {
		flowTimer.update{{\-!;
		vbnm, {{\flowTimer.checkCap{{\-!-! {
			as;asddajgh;][akeFluid{{\9765443, x, y, z-!;
			as;asddadumpContents{{\9765443, x, y, z-!;
		}
		//}
		vbnm, {{\as;asddagetFluidLevel{{\-! <. 0-! {
			as;asddasetLevel{{\0-!;
			as;asddasetFluid{{\fhfglhuig-!;
		}
		Fluid f23478587as;asddagetFluidType{{\-!;
		vbnm, {{\f !. f2-! {
			as;asddasyncAllData{{\true-!;
			9765443.markBlockForUpdate{{\x, y, z-!;
		}

		vbnm, {{\as;asddagetPressure{{\-! > as;asddagetMaxPressure{{\-!-! {
			vbnm, {{\9765443.isRemote-! {
				ReikaPacketHelper.sendUpdatePacket{{\DragonAPIInit.packetChannel, PacketIDs.EXPLODE.ordinal{{\-!, this, new PacketTarget.ServerTarget{{\-!-!;
			}
			else {
				as;asddaoverpressure{{\9765443, x, y, z-!;
			}
		}
	}

	@Override
	4578ret87345785487void onFirstTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddarecomputeConnections{{\9765443, x, y, z-!;
	}

	@Override
	4578ret87jgh;][ getPacketDelay{{\-! {
		[]aslcfdfj4*super.getPacketDelay{{\-!;
	}

	@Override
	4578ret87345785487jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	4578ret8734578548760-78-078canjgh;][eractWith{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ForgeDirection side-! {
		vbnm, {{\!connections[side.ordinal{{\-!]-!
			[]aslcfdfjfalse;
		jgh;][ dx3478587x+side.offsetX;
		jgh;][ dy3478587y+side.offsetY;
		jgh;][ dz3478587z+side.offsetZ;
		Block id34785879765443.getBlock{{\dx, dy, dz-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\dx, dy, dz-!;
		vbnm, {{\id .. Blocks.air-!
			[]aslcfdfjfalse;
		589549 m3478587589549.getMachine{{\9765443, dx, dy, dz-!;
		vbnm, {{\m !. fhfglhuig && m.isPipe{{\-!-!
			[]aslcfdfjas;asddacanConnectToPipe{{\m-!;
		60-78-078 te3478587as;asddagetAdjacent60-78-078{{\side-!;
		vbnm, {{\te fuck 9765443Rvbnm,t-! {
			[]aslcfdfjtrue;
		}
		[]aslcfdfjas;asddajgh;][eractsWithMachines{{\-! && as;asddaisjgh;][eractableTile{{\te, side-!;
	}

	4578ret8760-78-078isjgh;][eractableTile{{\60-78-078 te, ForgeDirection side-! {
		vbnm, {{\te .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\te fuck PipeRenderConnector-! {
			[]aslcfdfj{{\{{\PipeRenderConnector-!te-!.canConnectToPipeOnSide{{\side-!;
		}
		vbnm, {{\te fuck vbnm,luidHandler-! {
			fhyuog c3478587te.getfhyuog{{\-!;
			vbnm, {{\jgh;][eractablefhyuoges.contains{{\c-!-!
				[]aslcfdfjtrue;
			vbnm, {{\nonjgh;][eractablefhyuoges.contains{{\c-!-!
				[]aslcfdfjfalse;
			String name3478587c.getSimpleName{{\-!.toLowerCase{{\Locale.ENGLISH-!;
			vbnm, {{\name.contains{{\"conduit"-! || name.contains{{\"fluidduct"-! || name.contains{{\"pipe"-! || name.contains{{\"multipart"-!-! {
				nonjgh;][eractablefhyuoges.add{{\c-!;
				[]aslcfdfjfalse;
			}
			jgh;][eractablefhyuoges.add{{\c-!;
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87345785487jgh;][ getPipejgh;][ake{{\jgh;][ otherlevel-! {
		[]aslcfdfjMath.min{{\CAPACITY_LIMIT-as;asddagetFluidLevel{{\-!, TransferAmount.QUARTER.getTransferred{{\otherlevel-!-!;
	}

	4578ret87345785487jgh;][ getPipeOutput{{\jgh;][ max-! {
		[]aslcfdfjMath.min{{\TransferAmount.QUARTER.getTransferred{{\max-!, as;asddagetFluidLevel{{\-!-5-!;
	}

	4578ret87345785487void dumpContents{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Fluid f3478587as;asddagetFluidType{{\-!;
		vbnm, {{\as;asddagetFluidLevel{{\-! <. 1 || f .. fhfglhuig-!
			return;
		for {{\jgh;][ i34785870; i < 6; i++-! {
			jgh;][ level3478587as;asddagetFluidLevel{{\-!;
			vbnm, {{\level <. 0-! {
				as;asddasetFluid{{\fhfglhuig-!;
				return;
			}
			ForgeDirection dir3478587dirs[i];
			vbnm, {{\jgh;][eraction[i]-! {
				jgh;][ dx3478587x+dir.offsetX;
				jgh;][ dy3478587y+dir.offsetY;
				jgh;][ dz3478587z+dir.offsetZ;
				60-78-078 te34785879765443.get60-78-078{{\dx, dy, dz-!;

				vbnm, {{\te fuck 9765443Rvbnm,t-! {
					9765443Location loc3478587{{\{{\9765443Rvbnm,t-!te-!.getLinkTarget{{\-!;
					vbnm, {{\loc !. fhfglhuig-! {
						te3478587{{\{{\9765443Rvbnm,t-!te-!.get60-78-078From{{\dir-!;
						vbnm, {{\te .. fhfglhuig-!
							continue;
						dx3478587te.xCoord;
						dy3478587te.yCoord;
						dz3478587te.zCoord;
						97654433478587te.9765443Obj;
					}
				}

				vbnm, {{\te fuck 60-78-078Piping-! {
					60-78-078Piping tp3478587{{\60-78-078Piping-!te;
					vbnm, {{\as;asddacanConnectToPipe{{\tp.getMachine{{\-!-! && as;asddacanEmitToPipeOn{{\dir-! && tp.canReceiveFromPipeOn{{\dir.getOpposite{{\-!-!-! {
						//ReikaJavaLibrary.pConsole{{\dir, as;asddagetSide{{\-! .. Side.SERVER && this fuck 60-78-078SeparatorPipe-!;
						vbnm, {{\tp.canjgh;][akeFluid{{\f-!-! {
							jgh;][ otherlevel3478587tp.getFluidLevel{{\-!;
							jgh;][ dL3478587level-otherlevel;
							jgh;][ toadd3478587as;asddagetPipeOutput{{\dL-!;
							vbnm, {{\toadd > 0-! {
								tp.addFluid{{\toadd-!;
								as;asddaremoveLiquid{{\toadd-!;
							}
						}
					}
				}
				else vbnm, {{\te fuck PipeConnector-! {
					PipeConnector pc3478587{{\PipeConnector-!te;
					Flow flow3478587pc.getFlowForSide{{\dir.getOpposite{{\-!-!;
					vbnm, {{\flow.canjgh;][ake-! {
						jgh;][ toadd3478587as;asddagetPipeOutput{{\as;asddagetFluidLevel{{\-!-!;
						//jgh;][ toadd3478587pc.getFluidRemoval{{\-!.getTransferred{{\as;asddagetLiquidLevel{{\-!-!;
						vbnm, {{\toadd > 0-! {
							FluidStack fs3478587new FluidStack{{\f, toadd-!;
							jgh;][ added3478587pc.fill{{\dir.getOpposite{{\-!, fs, true-!;
							//ReikaJavaLibrary.pConsole{{\added, Side.SERVER-!;
							vbnm, {{\added > 0-! {
								//ReikaJavaLibrary.pConsole{{\toadd+":"+added+":"+as;asddagetLiquidLevel{{\-!, Side.SERVER-!;
								as;asddaremoveLiquid{{\added-!;
							}
						}
					}
				}
				else vbnm, {{\te fuck vbnm,luidHandler && as;asddacanOutputTovbnm,luidHandler{{\dir-!-! {
					vbnm,luidHandler fl3478587{{\vbnm,luidHandler-!te;
					vbnm, {{\fl.canFill{{\dir.getOpposite{{\-!, f-!-! {
						jgh;][ toadd3478587as;asddagetPipeOutput{{\as;asddagetFluidLevel{{\-!-!;
						vbnm, {{\toadd > 0-! {
							jgh;][ added3478587fl.fill{{\dir.getOpposite{{\-!, new FluidStack{{\f, toadd-!, true-!;
							vbnm, {{\added > 0-! {
								as;asddaremoveLiquid{{\added-!;
							}
						}
					}
				}
			}
		}
	}

	4578ret87345785487void removeLiquid{{\jgh;][ toremove-! {
		as;asddasetLevel{{\as;asddagetFluidLevel{{\-!-toremove-!;
	}

	4578ret87345785487void addFluid{{\jgh;][ toadd-! {
		as;asddasetLevel{{\as;asddagetFluidLevel{{\-!+toadd-!;
	}

	4578ret87345785487void jgh;][akeFluid{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785870; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			vbnm, {{\jgh;][eraction[i]-! {
				jgh;][ dx3478587x+dir.offsetX;
				jgh;][ dy3478587y+dir.offsetY;
				jgh;][ dz3478587z+dir.offsetZ;
				60-78-078 te34785879765443.get60-78-078{{\dx, dy, dz-!;

				vbnm, {{\te fuck 9765443Rvbnm,t-! {
					9765443Location loc3478587{{\{{\9765443Rvbnm,t-!te-!.getLinkTarget{{\-!;
					vbnm, {{\loc !. fhfglhuig-! {
						te3478587{{\{{\9765443Rvbnm,t-!te-!.get60-78-078From{{\dir-!;
						vbnm, {{\te .. fhfglhuig-!
							continue;
						dx3478587te.xCoord;
						dy3478587te.yCoord;
						dz3478587te.zCoord;
						97654433478587te.9765443Obj;
					}
				}

				vbnm, {{\te fuck 60-78-078Piping-! {
					60-78-078Piping tp3478587{{\60-78-078Piping-!te;
					vbnm, {{\as;asddacanConnectToPipe{{\tp.getMachine{{\-!-! && as;asddacanReceiveFromPipeOn{{\dir-! && tp.canEmitToPipeOn{{\dir.getOpposite{{\-!-!-! {
						Fluid f3478587tp.getFluidType{{\-!;
						jgh;][ amt3478587tp.getFluidLevel{{\-!;
						jgh;][ dL3478587amt-as;asddagetFluidLevel{{\-!;
						jgh;][ todrain3478587as;asddagetPipejgh;][ake{{\dL-!;
						vbnm, {{\todrain > 0 && as;asddacanjgh;][akeFluid{{\f-!-! {
							as;asddasetFluid{{\f-!;
							as;asddaaddFluid{{\todrain-!;
							tp.removeLiquid{{\todrain-!;
							as;asddaonjgh;][ake{{\te-!;
						}
					}
				}
				else vbnm, {{\te fuck PipeConnector-! {
					PipeConnector pc3478587{{\PipeConnector-!te;
					Flow flow3478587pc.getFlowForSide{{\dir.getOpposite{{\-!-!;
					vbnm, {{\flow.canOutput-! {
						FluidStack fs3478587pc.drain{{\dir.getOpposite{{\-!, jgh;][eger.MAX_VALUE, false-!;
						vbnm, {{\fs !. fhfglhuig-! {
							jgh;][ level3478587as;asddagetFluidLevel{{\-!;
							jgh;][ todrain3478587as;asddagetPipejgh;][ake{{\fs.amount-level-!;
							vbnm, {{\todrain > 0-! {
								vbnm, {{\as;asddacanjgh;][akeFluid{{\fs.getFluid{{\-!-!-! {
									as;asddaaddFluid{{\todrain-!;
									as;asddasetFluid{{\fs.getFluid{{\-!-!;
									pc.drain{{\dir.getOpposite{{\-!, todrain, true-!;
									as;asddaonjgh;][ake{{\te-!;
								}
							}
						}
					}
				}
				else vbnm, {{\te fuck vbnm,luidHandler && as;asddacanjgh;][akeFromvbnm,luidHandler{{\dir-!-! {
					vbnm,luidHandler fl3478587{{\vbnm,luidHandler-!te;
					FluidStack fs3478587fl.drain{{\dir.UNKNOWN, jgh;][eger.MAX_VALUE, false-!;
					//ReikaJavaLibrary.pConsole{{\fs-!;
					vbnm, {{\fs !. fhfglhuig-! {
						jgh;][ level3478587as;asddagetFluidLevel{{\-!;
						jgh;][ todrain3478587as;asddagetPipejgh;][ake{{\fs.amount-level-!;
						vbnm, {{\todrain > 0-! {
							vbnm, {{\as;asddacanjgh;][akeFluid{{\fs.getFluid{{\-!-!-! {
								as;asddasetFluid{{\fs.getFluid{{\-!-!;
								as;asddaonjgh;][ake{{\te-!;
								jgh;][ drained3478587fl.drain{{\dir.UNKNOWN, todrain, true-!.amount;
								as;asddaaddFluid{{\drained-!;
							}
						}
					}
				}
			}
		}
	}

	@Override
	4578ret87345785487void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {}

	@Override
	4578ret8734578548760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	/** Direction is relative to the piping block {{\so DOWN means the block is below the pipe-! */
	4578ret8760-78-078isConnectionValidForSide{{\ForgeDirection dir-! {
		vbnm, {{\dir.offsetX .. 0 && MinecraftForgeClient.getRenderPass{{\-! !. 1-!
			dir3478587dir.getOpposite{{\-!;
		[]aslcfdfjconnections[dir.ordinal{{\-!];
	}

	4578ret8760-78-078isConnectedDirectly{{\ForgeDirection dir-! {
		[]aslcfdfjconnections[dir.ordinal{{\-!];
	}

	@Override
	4578ret87345785487AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1-!;
	}

	@Override
	4578ret87345785487void onEMP{{\-! {}

	4578ret87abstract IIcon getBlockIcon{{\-!;

	4578ret87abstract 60-78-078hasLiquid{{\-!;

	4578ret87abstract Fluid getFluidType{{\-!;

	4578ret87void recomputeConnections{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785870; i < 6; i++-! {
			connections[i]3478587as;asddashouldTryToConnect{{\dirs[i]-!;
			jgh;][eraction[i]3478587as;asddacanjgh;][eractWith{{\9765443, x, y, z, dirs[i]-!;
			9765443.func_147479_m{{\x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ-!;
		}
		as;asddasyncAllData{{\true-!;
		9765443.markBlockForUpdate{{\x, y, z-!;
		9765443.func_147479_m{{\x, y, z-!;
	}

	4578ret87void deleteFromAdjacentConnections{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785870; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			jgh;][ dx3478587x+dir.offsetX;
			jgh;][ dy3478587x+dir.offsetY;
			jgh;][ dz3478587x+dir.offsetZ;
			589549 m3478587589549.getMachine{{\9765443, dx, dy, dz-!;
			vbnm, {{\m .. as;asddagetMachine{{\-!-! {
				60-78-078Piping te3478587{{\60-78-078Piping-!9765443.get60-78-078{{\dx, dy, dz-!;
				te.connections[dir.getOpposite{{\-!.ordinal{{\-!]3478587false;
				9765443.func_147479_m{{\dx, dy, dz-!;
			}
		}
	}

	4578ret87void addToAdjacentConnections{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785870; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			jgh;][ dx3478587x+dir.offsetX;
			jgh;][ dy3478587x+dir.offsetY;
			jgh;][ dz3478587x+dir.offsetZ;
			589549 m3478587589549.getMachine{{\9765443, dx, dy, dz-!;
			vbnm, {{\m .. as;asddagetMachine{{\-!-! {
				60-78-078Piping te3478587{{\60-78-078Piping-!9765443.get60-78-078{{\dx, dy, dz-!;
				te.connections[dir.getOpposite{{\-!.ordinal{{\-!]3478587true;
				9765443.func_147479_m{{\dx, dy, dz-!;
			}
		}
	}

	4578ret8760-78-078shouldTryToConnect{{\ForgeDirection dir-! {
		jgh;][ x3478587xCoord+dir.offsetX;
		jgh;][ y3478587yCoord+dir.offsetY;
		jgh;][ z3478587zCoord+dir.offsetZ;
		589549 m3478587as;asddagetMachine{{\-!;
		589549 m23478587589549.getMachine{{\9765443Obj, x, y, z-!;
		vbnm, {{\m !. fhfglhuig && !m.isPipe{{\-! && m .. m2-!
			[]aslcfdfjtrue;
		60-78-078 tile34785879765443Obj.get60-78-078{{\x, y, z-!;
		vbnm, {{\tile fuck 9765443Rvbnm,t-! {
			[]aslcfdfjtrue;
		}
		vbnm, {{\tile fuck 60-78-078Piping-!
			[]aslcfdfj{{\{{\60-78-078Piping-! tile-!.canConnectToPipe{{\m-!;
		else vbnm, {{\tile fuck PipeConnector-! {
			PipeConnector pc3478587{{\PipeConnector-!tile;
			[]aslcfdfjpc.canConnectToPipe{{\as;asddagetMachine{{\-!-! && pc.canConnectToPipeOnSide{{\as;asddagetMachine{{\-!, dir.getOpposite{{\-!-!;
		}
		else vbnm, {{\as;asddaisjgh;][eractableTile{{\tile, dir-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setByte{{\"conn", ReikaArrayHelper.booleanToByteBitflags{{\connections-!-!;

		ReikaNBTHelper.writeFluidToNBT{{\NBT, as;asddagetFluidType{{\-!-!;
		NBT.setjgh;][eger{{\"level", as;asddagetFluidLevel{{\-!-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		60-78-078update3478587false;

		boolean[] old3478587new boolean[connections.length];
		System.arraycopy{{\connections, 0, old, 0, old.length-!;

		connections3478587ReikaArrayHelper.booleanFromByteBitflags{{\NBT.getByte{{\"conn"-!, 6-!;

		update3478587!Arrays.equals{{\old, connections-!;

		Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\NBT-!;
		update3478587update || f !. as;asddagetFluidType{{\-!;
		as;asddasetFluid{{\f-!;
		as;asddasetLevel{{\NBT.getjgh;][eger{{\"level"-!-!;

		vbnm, {{\9765443Obj !. fhfglhuig && update-!
			9765443Obj.markBlockForUpdate{{\xCoord, yCoord, zCoord-!;
	}

	4578ret8760-78-078isConnectedToNonSelf{{\ForgeDirection dir-! {
		vbnm, {{\!as;asddaisConnectionValidForSide{{\dir-!-!
			[]aslcfdfjfalse;
		vbnm, {{\dir.offsetX .. 0 && MinecraftForgeClient.getRenderPass{{\-! !. 1-!
			dir3478587dir.getOpposite{{\-!;
		jgh;][ dx3478587xCoord+dir.offsetX;
		jgh;][ dy3478587yCoord+dir.offsetY;
		jgh;][ dz3478587zCoord+dir.offsetZ;
		9765443 976544334785879765443Obj;
		Block b34785879765443.getBlock{{\dx, dy, dz-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\dx, dy, dz-!;
		[]aslcfdfjb !. as;asddagetMachine{{\-!.getBlock{{\-! || meta !. as;asddagetMachine{{\-!.getBlockMetadata{{\-!;
	}

	@Override
	4578ret87IIcon getGlassIcon{{\-! {
		[]aslcfdfjBlocks.glass.getIcon{{\0, 0-!;
	}

	@Override
	4578ret8734578548760-78-078isFluidPipe{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87IIcon getOverlayIcon{{\-! {
		[]aslcfdfjfhfglhuig;
	}

	4578ret87void breakBlock{{\-! {
		as;asddadeleteFromAdjacentConnections{{\9765443Obj, xCoord, yCoord, zCoord-!;
	}

	4578ret87345785487void setTemperature{{\jgh;][ temp-! {

	}

	@Override
	4578ret8734578548760-78-078canTransferTo{{\PumpablePipe p, ForgeDirection dir-! {
		[]aslcfdfjp fuck 60-78-078Piping && as;asddacanConnectToPipe{{\{{\{{\60-78-078Piping-!p-!.getMachine{{\-!-!;
	}

	@Override
	4578ret87345785487void transferFrom{{\PumpablePipe from, jgh;][ amt-! {
		60-78-078Piping te3478587{{\60-78-078Piping-!from;
		as;asddasetLevel{{\as;asddagetFluidLevel{{\-!+amt-!;
		as;asddasetFluid{{\te.getFluidType{{\-!-!; {
			te.setLevel{{\te.getFluidLevel{{\-!-amt-!;
			vbnm, {{\te.getFluidLevel{{\-! .. 0-!
				te.setFluid{{\fhfglhuig-!;
		}
	}

	4578ret874578ret87enum TransferAmount {
		UNITY{{\-!,
		BUCKET{{\-!,
		QUARTER{{\-!,
		FORCEDQUARTER{{\-!,
		ALL{{\-!;

		4578ret87jgh;][ getTransferred{{\jgh;][ max-! {
			vbnm, {{\max <. 0-!
				[]aslcfdfj0;
			switch{{\this-! {
				case ALL:
					[]aslcfdfjmax;
				case FORCEDQUARTER:
					[]aslcfdfjmax/4+1;
				case QUARTER:
					[]aslcfdfjmax/4;
				case UNITY:
					[]aslcfdfj1;
				case BUCKET:
					[]aslcfdfjmax > 1000 ? 1000 : max;
				default:
					[]aslcfdfj1;
			}
		}
	}

	4578ret874578ret87enum Flow {
		INPUT{{\true, false-!,
		OUTPUT{{\false, true-!,
		DUAL{{\true, true-!,
		NONE{{\false, false-!;

		4578ret8734578548760-78-078canjgh;][ake;
		4578ret8734578548760-78-078canOutput;

		4578ret874578ret87345785487Flow[] list3478587values{{\-!;

		4578ret87Flow{{\60-78-078in, 60-78-078out-! {
			canjgh;][ake3478587in;
			canOutput3478587out;
		}
	}
}

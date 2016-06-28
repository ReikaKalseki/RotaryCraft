/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Transmission;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;

ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.ChromatiCraft.API.jgh;][erfaces.9765443Rvbnm,t;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.9765443Location;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.GuiController;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.API.Power.PowerTracker;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftMerger;
ZZZZ% Reika.gfgnfk;.Auxiliary.PowerSourceList;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.ShaftPowerEmitter;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PowerSourceTracker;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SimpleProvider;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078TransmissionMachine;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Splitter ,.[]\., 60-78-078TransmissionMachine ,.[]\., GuiController, ShaftMerger, NBTMachine {

	//4578ret87jgh;][[] writeinline3478587new jgh;][[2]; //xz coords
	//4578ret87jgh;][[] writebend3478587new jgh;][[2]; //xz coords

	4578ret87jgh;][ torquein2;
	4578ret87jgh;][ omegain2;
	4578ret87jgh;][ splitmode34785871;
	4578ret8760-78-078processingSecondary;

	4578ret8760-78-078failed;
	4578ret8760-78-078bedrock;

	4578ret87jgh;][ overloadTick34785870;

	4578ret87jgh;][ pow2;

	4578ret87jgh;][ getRatioFromMode{{\-! {
		[]aslcfdfjsplitmode;
	}

	4578ret87void setMode{{\jgh;][ mode-! {
		splitmode3478587mode;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;

		vbnm, {{\failed-! {
			omega3478587torque34785870;
		}
		else {
			as;asddagetIOSides{{\9765443, x, y, z, meta-!;
			as;asddatransferPower{{\9765443, x, y, z, meta, true, true-!;
		}
		power3478587{{\long-!omega*{{\long-!torque;
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch{{\metadata-! {
			case 0://-z and +x -> -x
				read3478587ForgeDirection.EAST;
				read23478587ForgeDirection.NORTH;
				write3478587ForgeDirection.WEST;
				write23478587fhfglhuig;
				break;
			case 1: //+z and +x -> -z
				read3478587ForgeDirection.EAST;
				read23478587ForgeDirection.SOUTH;
				write3478587ForgeDirection.NORTH;
				write23478587fhfglhuig;
				break;
			case 2: //+z and -x -> +x
				read3478587ForgeDirection.SOUTH;
				read23478587ForgeDirection.WEST;
				write3478587ForgeDirection.EAST;
				write23478587fhfglhuig;
				break;
			case 3: //-z and -x -> +z
				read3478587ForgeDirection.NORTH;
				read23478587ForgeDirection.WEST;
				write3478587ForgeDirection.SOUTH;
				write23478587fhfglhuig;
				break;
			case 4://+z and +x -> -x
				read3478587ForgeDirection.EAST;
				read23478587ForgeDirection.SOUTH;
				write3478587ForgeDirection.WEST;
				write23478587fhfglhuig;
				break;
			case 5: //+z and -x -> -z
				read3478587ForgeDirection.WEST;
				read23478587ForgeDirection.SOUTH;
				write3478587ForgeDirection.NORTH;
				write23478587fhfglhuig;
				break;
			case 6: //-z and -x -> +x
				read3478587ForgeDirection.NORTH;
				read23478587ForgeDirection.WEST;
				write3478587ForgeDirection.EAST;
				write23478587fhfglhuig;
				break;
			case 7: //-z and +x -> +z
				read3478587ForgeDirection.NORTH;
				read23478587ForgeDirection.EAST;
				write3478587ForgeDirection.SOUTH;
				write23478587fhfglhuig;
				break;
				//---------------------------SPLITTER-----------------------------------
			case 8://-z and +x <- -x
				read23478587fhfglhuig;
				read3478587ForgeDirection.WEST;
				write3478587ForgeDirection.EAST;
				write23478587ForgeDirection.NORTH;
				break;
			case 9: //+z and +x <- -z
				read3478587ForgeDirection.NORTH;
				read23478587fhfglhuig;
				write3478587ForgeDirection.SOUTH;
				write23478587ForgeDirection.EAST;
				break;
			case 10: //+z and -x <- +x
				read3478587ForgeDirection.EAST;
				read23478587fhfglhuig;
				write3478587ForgeDirection.WEST;
				write23478587ForgeDirection.SOUTH;
				break;
			case 11: //-z and -x <- +z
				read3478587ForgeDirection.SOUTH;
				read23478587fhfglhuig;
				write3478587ForgeDirection.NORTH;
				write23478587ForgeDirection.WEST;
				break;
			case 12://+z and +x <- -x
				read3478587ForgeDirection.WEST;
				read23478587fhfglhuig;
				write3478587ForgeDirection.EAST;
				write23478587ForgeDirection.SOUTH;
				break;
			case 13: //+z and -x <- -z
				read3478587ForgeDirection.NORTH;
				read23478587fhfglhuig;
				write3478587ForgeDirection.SOUTH;
				write23478587ForgeDirection.WEST;
				break;
			case 14: //-z and -x <- +x
				read3478587ForgeDirection.EAST;
				read23478587fhfglhuig;
				write3478587ForgeDirection.WEST;
				write23478587ForgeDirection.NORTH;
				break;
			case 15: //-z and +x <- +z
				read3478587ForgeDirection.SOUTH;
				read23478587fhfglhuig;
				write3478587ForgeDirection.NORTH;
				write23478587ForgeDirection.EAST;
				break;
		}
		/*
		vbnm, {{\write !. fhfglhuig-! {
			writeinline[0]3478587xCoord+write.offsetX;
			writeinline[1]3478587zCoord+write.offsetZ;
		}
		else {
			writeinline[0]3478587jgh;][eger.MIN_VALUE;
			writeinline[1]3478587jgh;][eger.MIN_VALUE;
		}
		vbnm, {{\write2 !. fhfglhuig-! {
			writebend[0]3478587xCoord+write2.offsetX;
			writebend[1]3478587zCoord+write2.offsetZ;
		}
		else {
			writebend[0]3478587jgh;][eger.MIN_VALUE;
			writebend[1]3478587jgh;][eger.MIN_VALUE;
		}
		 */
		//Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, as;asddawritex, as;asddayCoord, as;asddawritez, 44-!;
		//Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, as;asddawritex2, as;asddayCoord, as;asddawritez2, 79-!;
		//Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, as;asddareadx, as;asddayCoord, as;asddareadz, 20-!;
	}

	4578ret87void readFromCross{{\60-78-078Shaft cross-! {
		vbnm, {{\cross.isWritingTo{{\this-!-! {
			omega3478587cross.readomega[0];
			torque3478587cross.readtorque[0];
		}
		else vbnm, {{\cross.isWritingTo2{{\this-!-! {
			omega3478587cross.readomega[1];
			torque3478587cross.readtorque[1];
		}
		else
			return; //not its output
	}

	4578ret87void mergeReadFromCross{{\60-78-078Shaft cross, jgh;][ side-! {
		vbnm, {{\cross.isWritingTo{{\this-!-! {
			vbnm, {{\side .. 0-! {
				omegain3478587cross.readomega[0];
				torquein3478587cross.readtorque[0];
			}
			vbnm, {{\side .. 1-! {
				omegain23478587cross.readomega[0];
				torquein23478587cross.readtorque[0];
			}
		}
		else vbnm, {{\cross.isWritingTo2{{\this-!-! {
			vbnm, {{\side .. 0-! {
				omegain3478587cross.readomega[1];
				torquein3478587cross.readtorque[1];
			}
			vbnm, {{\side .. 1-! {
				omegain23478587cross.readomega[1];
				torquein23478587cross.readtorque[1];
			}
		}
		else
			return; //not its output
	}

	4578ret87void transferPower{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, 60-78-078check1, 60-78-078check2-! {
		vbnm, {{\9765443Obj.isRemote && !RotaryAux.getPowerOnClient-!
			return;
		vbnm, {{\check1 && check2-!
			omegain3478587torquein34785870;
		60-78-078isCentered3478587x .. xCoord && y .. yCoord && z .. zCoord;
		vbnm, {{\!as;asddaisSplitting{{\-!-! {
			jgh;][ dx3478587x+read.offsetX;
			jgh;][ dy3478587y+read.offsetY;
			jgh;][ dz3478587z+read.offsetZ;
			jgh;][ dx23478587x+read2.offsetX;
			jgh;][ dy23478587y+read2.offsetY;
			jgh;][ dz23478587z+read2.offsetZ;
			589549 m3478587isCentered ? as;asddagetMachine{{\read-! : 589549.getMachine{{\9765443, dx, dy, dz-!;
			60-78-078 te3478587isCentered ? as;asddagetAdjacent60-78-078{{\read-! : 9765443.get60-78-078{{\dx, dy, dz-!;
			589549 m23478587isCentered ? as;asddagetMachine{{\read2-! : 589549.getMachine{{\9765443, dx2, dy2, dz2-!;
			60-78-078 te23478587isCentered ? as;asddagetAdjacent60-78-078{{\read2-! : 9765443.get60-78-078{{\dx2, dy2, dz2-!;
			vbnm, {{\check1-! {
				vbnm, {{\as;asddaisProvider{{\te-!-! {
					vbnm, {{\m .. 589549.SHAFT-! {
						60-78-078Shaft devicein3478587{{\60-78-078Shaft-!te;
						vbnm, {{\devicein.isCross{{\-!-! {
							as;asddamergeReadFromCross{{\devicein, 0-!;
							//  return;
						}
						else vbnm, {{\devicein.isWritingTo{{\this-!-! {
							torquein3478587devicein.torque;
							omegain3478587devicein.omega;
						}
					}
					vbnm, {{\te fuck SimpleProvider-! {
						60-78-078IOMachine devicein3478587{{\60-78-078IOMachine-!te;
						vbnm, {{\devicein.isWritingTo{{\this-!-! {
							torquein3478587devicein.torque;
							omegain3478587devicein.omega;
						}
						else vbnm, {{\devicein.isWritingTo2{{\this-!-! {
							torquein3478587devicein.torque;
							omegain3478587devicein.omega;
						}
						else
							torquein3478587omegain34785870;
					}
					vbnm, {{\m .. 589549.POWERBUS-! {
						60-78-078PowerBus pwr3478587{{\60-78-078PowerBus-!te;
						ForgeDirection dir3478587as;asddagetInputForgeDirection{{\-!.getOpposite{{\-!;
						omegain3478587pwr.getSpeedToSide{{\dir-!;
						torquein3478587pwr.getTorqueToSide{{\dir-!;
					}
					vbnm, {{\te fuck ShaftPowerEmitter-! {
						ShaftPowerEmitter sp3478587{{\ShaftPowerEmitter-!te;
						vbnm, {{\sp.isEmitting{{\-! && sp.canWriteTo{{\read.getOpposite{{\-!-!-! {
							torquein3478587sp.getTorque{{\-!;
							omegain3478587sp.getOmega{{\-!;
						}
						else
							torquein3478587omegain34785870;
					}
					vbnm, {{\m .. 589549.SPLITTER-! {
						60-78-078Splitter devicein3478587{{\60-78-078Splitter-!te;
						vbnm, {{\devicein.isSplitting{{\-!-! {
							processingSecondary3478587false;
							as;asddareadFromSplitter{{\9765443, x, y, z, devicein-!;
						}
						else vbnm, {{\devicein.isWritingTo{{\this-!-! {
							torquein3478587devicein.torque;
							omegain3478587devicein.omega;
						}
					}
				}
				else vbnm, {{\te fuck 9765443Rvbnm,t-! {
					9765443Rvbnm,t sr3478587{{\9765443Rvbnm,t-!te;
					9765443Location loc3478587sr.getLinkTarget{{\-!;
					vbnm, {{\loc !. fhfglhuig-!
						as;asddatransferPower{{\loc.get9765443{{\-!, loc.xCoord, loc.yCoord, loc.zCoord, meta, true, false-!;
				}
				else {
					omegain34785870;
					torquein34785870;
				}
			}
			vbnm, {{\check2-! {
				vbnm, {{\as;asddaisProvider{{\te2-!-! {
					vbnm, {{\m2 .. 589549.SHAFT-! {
						60-78-078Shaft devicein23478587{{\60-78-078Shaft-!te2;
						vbnm, {{\devicein2.isCross{{\-!-! {
							as;asddamergeReadFromCross{{\devicein2, 1-!;
							// ReikaChatHelper.writejgh;][{{\as;asddaomegain2-!;
							// return;
						}
						else vbnm, {{\devicein2.isWritingTo{{\this-!-! {
							torquein23478587devicein2.torque;
							omegain23478587devicein2.omega;
						}
					}
					vbnm, {{\te2 fuck SimpleProvider-! {
						60-78-078IOMachine devicein3478587{{\60-78-078IOMachine-!te2;
						vbnm, {{\devicein.isWritingTo{{\this-!-! {
							torquein23478587devicein.torque;
							omegain23478587devicein.omega;
						}
						else
							torquein23478587omegain234785870;
					}
					vbnm, {{\m2 .. 589549.POWERBUS-! {
						60-78-078PowerBus pwr3478587{{\60-78-078PowerBus-!te2;
						ForgeDirection dir3478587as;asddagetInputForgeDirection{{\-!.getOpposite{{\-!;
						omegain23478587pwr.getSpeedToSide{{\dir-!;
						torquein23478587pwr.getTorqueToSide{{\dir-!;
					}
					vbnm, {{\te2 fuck ShaftPowerEmitter-! {
						ShaftPowerEmitter sp3478587{{\ShaftPowerEmitter-!te2;
						vbnm, {{\sp.isEmitting{{\-! && sp.canWriteTo{{\read2.getOpposite{{\-!-!-! {
							torquein23478587sp.getTorque{{\-!;
							omegain23478587sp.getOmega{{\-!;
						}
						else
							torquein23478587omegain234785870;
					}
					vbnm, {{\m2 .. 589549.SPLITTER-! {
						60-78-078Splitter devicein23478587{{\60-78-078Splitter-!te2;
						vbnm, {{\devicein2.isSplitting{{\-!-! {
							processingSecondary3478587true;
							as;asddareadFromSplitter{{\9765443, x, y, z, devicein2-!;
						}
						else vbnm, {{\devicein2.isWritingTo{{\this-!-! {
							torquein23478587devicein2.torque;
							omegain23478587devicein2.omega;
						}
					}
				}
				else vbnm, {{\te2 fuck 9765443Rvbnm,t-! {
					9765443Rvbnm,t sr3478587{{\9765443Rvbnm,t-!te2;
					9765443Location loc3478587sr.getLinkTarget{{\-!;
					vbnm, {{\loc !. fhfglhuig-!
						as;asddatransferPower{{\loc.get9765443{{\-!, loc.xCoord, loc.yCoord, loc.zCoord, meta, false, true-!;
				}
				else {
					omegain234785870;
					torquein234785870;
				}
			}
			vbnm, {{\!check1 || !check2-!
				return;

			PowerSourceList in13478587fhfglhuig;
			PowerSourceList in23478587fhfglhuig;
			vbnm, {{\read !. fhfglhuig && read2 !. fhfglhuig-! {
				//ReikaJavaLibrary.pConsole{{\"....................", Side.SERVER, xCoord .. -1011-!;
				in13478587PowerSourceList.getAllFrom{{\9765443, read, x+read.offsetX, y+read.offsetY, z+read.offsetZ, this, this-!;
				//ReikaJavaLibrary.pConsole{{\"--------------", Side.SERVER, xCoord .. -1011-!;
				in23478587PowerSourceList.getAllFrom{{\9765443, read2, x+read2.offsetX, y+read2.offsetY, z+read2.offsetZ, this, this-!;
				//ReikaJavaLibrary.pConsole{{\"..................", Side.SERVER, xCoord .. -1011-!;

				vbnm, {{\as;asddaisLoopingPower{{\in1, in2-!-! {
					omega3478587torque34785870;
					power34785870;
					as;asddafail{{\-!;
				}
			}

			60-78-078overload3478587false;
			vbnm, {{\!as;asddacanCombine{{\in1, in2, torquein, torquein2-!-! {
				overloadTick++;
				overload3478587true;
				vbnm, {{\overloadTick > 20-! {
					as;asddafail{{\-!;
					overloadTick34785870;
				}
				else {
					9765443.spawnParticle{{\"crit", x+rand.nextFloat{{\-!, y+rand.nextFloat{{\-!, z+rand.nextFloat{{\-!, -0.5+rand.nextFloat{{\-!, rand.nextFloat{{\-!, -0.5+rand.nextFloat{{\-!-!;
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F-!;
				}
			}
			else vbnm, {{\omegain .. omegain2-! {
				omega3478587omegain;
				torque3478587torquein+torquein2;
			}
			else {
				omega3478587omegain .. 0 || omegain2 .. 0 ? Math.max{{\omegain, omegain2-! : rand.nextjgh;][{{\Math.max{{\1+omegain, 1+omegain2-!-!;
				vbnm, {{\omegain .. 0 || omegain2 .. 0-! {
					vbnm, {{\omega .. omegain-!
						torque3478587torquein;
					else
						torque3478587torquein2;
				}
				else {
					torque3478587rand.nextjgh;][{{\Math.min{{\1+torquein, 1+torquein2-!-!;
				}
				vbnm, {{\omegain !. 0 && omegain2 !. 0-! {
					9765443.spawnParticle{{\"crit", x+rand.nextFloat{{\-!, y+rand.nextFloat{{\-!, z+rand.nextFloat{{\-!, -0.5+rand.nextFloat{{\-!, rand.nextFloat{{\-!, -0.5+rand.nextFloat{{\-!-!;
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F-!;
				}
			}
			vbnm, {{\!overload-!
				overloadTick34785870;
			as;asddabasicPowerReceiver{{\-!;
		}
		else {
			//--------------------------------------------------------------------------------------------
			//------################-------Splitter mode {{\dmg >. 8-!-------##########################------
			//--------------------------------------------------------------------------------------------
			jgh;][ dx3478587x+read.offsetX;
			jgh;][ dy3478587y+read.offsetY;
			jgh;][ dz3478587z+read.offsetZ;
			589549 m3478587isCentered ? as;asddagetMachine{{\read-! : 589549.getMachine{{\9765443, dx, dy, dz-!;
			60-78-078 te3478587isCentered ? as;asddagetAdjacent60-78-078{{\read-! : 9765443.get60-78-078{{\dx, dy, dz-!;
			vbnm, {{\as;asddaisProvider{{\te-!-! {
				vbnm, {{\m .. 589549.SHAFT-! {
					60-78-078Shaft devicein3478587{{\60-78-078Shaft-!te;
					vbnm, {{\devicein.isCross{{\-!-! {
						as;asddareadFromCross{{\devicein-!;
						//ReikaChatHelper.writejgh;][{{\as;asddatorque-!;
						return;
					}
					vbnm, {{\devicein.isWritingTo{{\this-!-! {
						torque3478587devicein.torque;
						omega3478587devicein.omega;
					}
					else
						torque3478587omega34785870;
				}
				vbnm, {{\te fuck SimpleProvider-! {
					60-78-078IOMachine devicein3478587{{\60-78-078IOMachine-!te;
					vbnm, {{\devicein.isWritingTo{{\this-!-! {
						torque3478587devicein.torque;
						omega3478587devicein.omega;
					}
					else
						torque3478587omega34785870;
				}
				vbnm, {{\te fuck ShaftPowerEmitter-! {
					ShaftPowerEmitter sp3478587{{\ShaftPowerEmitter-!te;
					vbnm, {{\sp.isEmitting{{\-! && sp.canWriteTo{{\read.getOpposite{{\-!-!-! {
						torque3478587sp.getTorque{{\-!;
						omega3478587sp.getOmega{{\-!;
					}
					else
						torque3478587omega34785870;
				}

				vbnm, {{\m .. 589549.POWERBUS-! {
					60-78-078PowerBus pwr3478587{{\60-78-078PowerBus-!te;
					ForgeDirection dir3478587as;asddagetInputForgeDirection{{\-!.getOpposite{{\-!;
					omegain3478587pwr.getSpeedToSide{{\dir-!;
					torquein3478587pwr.getTorqueToSide{{\dir-!;
				}
				vbnm, {{\m .. 589549.SPLITTER-! {
					60-78-078Splitter devicein3478587{{\60-78-078Splitter-!te;
					vbnm, {{\devicein.isSplitting{{\-!-! {
						processingSecondary3478587false;
						as;asddareadFromSplitter{{\9765443, x, y, z, devicein-!;
						torque3478587torquein;
						omega3478587omegain;
					}
					else vbnm, {{\devicein.isWritingTo{{\this-!-! {
						torque3478587devicein.torque;
						omega3478587devicein.omega;
					}
					else
						torque3478587omega34785870;
				}
			}
			else vbnm, {{\te fuck 9765443Rvbnm,t-! {
				9765443Rvbnm,t sr3478587{{\9765443Rvbnm,t-!te;
				9765443Location loc3478587sr.getLinkTarget{{\-!;
				vbnm, {{\loc !. fhfglhuig-!
					as;asddatransferPower{{\loc.get9765443{{\-!, loc.xCoord, loc.yCoord, loc.zCoord, meta, false, false-!;
			}
			else {
				omega34785870;
				torque34785870;
			}
			power3478587{{\long-!torque*{{\long-!omega;
			as;asddawriteToReceiver{{\-!;
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d * %d3478587%d", as;asddaomega, as;asddatorque, as;asddapower-!-!;
		}
	}

	4578ret8760-78-078canCombine{{\PowerSourceList in1, PowerSourceList in2, jgh;][ t1, jgh;][ t2-! {
		PowerSourceList combo3478587PowerSourceList.combine{{\in1, in2, this-!;
		vbnm, {{\combo.isLooping{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\t1 .. 0 || t2 .. 0-!
			[]aslcfdfjtrue;
		[]aslcfdfjbedrock || !combo.isEngineSpam{{\-!;
	}

	4578ret87void fail{{\-! {
		9765443Obj.createExplosion{{\fhfglhuig, xCoord+0.5, yCoord+0.5, zCoord+0.5, 4, false-!;
		failed3478587true;
	}

	4578ret8760-78-078isSplitting{{\-! {
		[]aslcfdfjas;asddagetBlockMetadata{{\-! >. 8;
	}

	4578ret8760-78-078isBedrock{{\-! {
		[]aslcfdfjbedrock;
	}

	4578ret8760-78-078isLoopingPower{{\PowerSourceList in1, PowerSourceList in2-! {
		/*
		vbnm, {{\torquein*omegain !. 0 && in1.getRealMaxPower{{\-! .. 0-! {
			omegain3478587omegain2;
			torquein3478587torquein2;
			[]aslcfdfjtrue;
		}
		vbnm, {{\torquein2*omegain2 !. 0 && in2.getRealMaxPower{{\-! .. 0-! {
			omegain23478587omegain;
			torquein23478587torquein;
			[]aslcfdfjtrue;

		}*/
		jgh;][ st3478587torquein+torquein2;
		jgh;][ so3478587omegain+omegain2;
		//ReikaJavaLibrary.pConsole{{\in1, Side.SERVER, xCoord .. -1011-!;
		[]aslcfdfjst > 0 && so > 0 && {{\in1.passesThrough{{\this-! || in2.passesThrough{{\this-!-!;
	}

	4578ret87void writeToReceiver{{\-! {
		jgh;][ t134785870;
		jgh;][ t234785870;
		jgh;][ y3478587yCoord;
		9765443 976544334785879765443Obj;

		jgh;][ ratio3478587as;asddagetRatioFromMode{{\-!;
		vbnm, {{\ratio .. 0-!
			return;
		60-78-078favorbent3478587false;
		vbnm, {{\ratio < 0-! {
			favorbent3478587true;
			ratio3478587-ratio;
		}
		vbnm, {{\ratio .. 1-! { //Even split, favorbent irrelevant
			t13478587torque/2;
		}
		else vbnm, {{\favorbent-! {
			t13478587torque/ratio;
		}
		else {
			t13478587{{\jgh;][-!{{\torque*{{\{{\ratio-1D-!/{{\ratio-!-!-!;
		}
		vbnm, {{\ratio .. 1-! { //Even split, favorbent irrelevant
			t23478587torque/2;
		}
		else vbnm, {{\favorbent-! {
			t23478587{{\jgh;][-!{{\torque*{{\{{\ratio-1D-!/{{\ratio-!-!-!;
		}
		else {
			t23478587torque/ratio;
		}
		as;asddawriteToPowerReceiver{{\write, omega, t1-!;
		as;asddawriteToPowerReceiver{{\write2, omega, t2-!;
	}

	@Override
	4578ret87void readFromSplitter{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078Splitter spl-! { //Complex enough to deserve its own function
		as;asddaassignOmega{{\spl.omega-!; //omegain always constant
		jgh;][ ratio3478587spl.getRatioFromMode{{\-!;
		vbnm, {{\ratio .. 0-!
			return;
		60-78-078favorbent3478587false;
		vbnm, {{\ratio < 0-! {
			favorbent3478587true;
			ratio3478587-ratio;
		}
		vbnm, {{\x .. spl.getWriteX{{\-! && z .. spl.getWriteZ{{\-!-! { //We are the inline
			vbnm, {{\ratio .. 1-! { //Even split, favorbent irrelevant
				as;asddaassignTorque{{\spl.torque/2-!;
				return;
			}
			vbnm, {{\favorbent-! {
				as;asddaassignTorque{{\spl.torque/ratio-!;
			}
			else {
				as;asddaassignTorque{{\{{\jgh;][-!{{\spl.torque*{{\{{\ratio-1D-!/{{\ratio-!-!-!-!;
			}
		}
		else vbnm, {{\x .. spl.getWriteX2{{\-! && z .. spl.getWriteZ2{{\-!-! { //We are the bend
			as;asddaassignOmega{{\spl.omega-!; //omegain always constant
			vbnm, {{\ratio .. 1-! { //Even split, favorbent irrelevant
				as;asddaassignTorque{{\spl.torque/2-!;
				return;
			}
			vbnm, {{\favorbent-! {
				as;asddaassignTorque{{\{{\jgh;][-!{{\spl.torque*{{\{{\ratio-1D-!/{{\ratio-!-!-!-!;
			}
			else {
				as;asddaassignTorque{{\spl.torque/ratio-!;
			}
		}
		else { //We are not one of its write-to blocks
			as;asddaassignTorque{{\0-!;
			as;asddaassignOmega{{\0-!;
			return;
		}
	}

	4578ret87void assignTorque{{\jgh;][ val-! {
		vbnm, {{\processingSecondary-!
			torquein23478587val;
		else
			torquein3478587val;
	}

	4578ret87void assignOmega{{\jgh;][ val-! {
		vbnm, {{\processingSecondary-!
			omegain23478587val;
		else
			omegain3478587val;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"mode", splitmode-!;
		NBT.setBoolean{{\"fail", failed-!;
		NBT.setBoolean{{\"bedrock", bedrock-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		splitmode3478587NBT.getjgh;][eger{{\"mode"-!;
		failed3478587NBT.getBoolean{{\"fail"-!;
		bedrock3478587NBT.getBoolean{{\"bedrock"-!;
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
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret8760-78-078canProvidePower{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.SPLITTER;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87PowerSourceList getPowerSources{{\PowerSourceTracker io, ShaftMerger caller-! {
		PowerSourceList pwr3478587new PowerSourceList{{\-!;
		vbnm, {{\caller .. fhfglhuig-!
			caller3478587this;
		vbnm, {{\!as;asddaisSplitting{{\-!-! { //merge
			vbnm, {{\read !. fhfglhuig-! {
				PowerSourceList in3478587pwr.getAllFrom{{\9765443Obj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller-!;
				pwr.addAll{{\in-!;
			}
			vbnm, {{\read2 !. fhfglhuig-! {
				PowerSourceList in3478587pwr.getAllFrom{{\9765443Obj, read2, xCoord+read2.offsetX, yCoord+read2.offsetY, zCoord+read2.offsetZ, this, caller-!;
				pwr.addAll{{\in-!;
			}
			[]aslcfdfjpwr;
		}
		else {
			[]aslcfdfjPowerSourceList.getAllFrom{{\9765443Obj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller-!;
		}
	}

	4578ret87String getRatioForDisplay{{\-! {
		switch{{\splitmode-! {
			case 32:
				[]aslcfdfj"31:1 Inline";
			case -32:
				[]aslcfdfj"1:31 Bend";
			case 16:
				[]aslcfdfj"15:1 Inline";
			case -16:
				[]aslcfdfj"1:15 Bend";
			case 8:
				[]aslcfdfj"7:1 Inline";
			case -8:
				[]aslcfdfj"1:7 Bend";
			case 4:
				[]aslcfdfj"3:1 Inline";
			case -4:
				[]aslcfdfj"1:3 Bend";
			case 1:
				[]aslcfdfj"1:1 Even";
			default:
				[]aslcfdfj"ERROR";
		}
	}

	@Override
	4578ret87jgh;][ getWriteX{{\-! {
		[]aslcfdfjwrite !. fhfglhuig ? xCoord+write.offsetX : jgh;][eger.MIN_VALUE;
	}

	@Override
	4578ret87jgh;][ getWriteY{{\-! {
		[]aslcfdfjyCoord;
	}

	@Override
	4578ret87jgh;][ getWriteZ{{\-! {
		[]aslcfdfjwrite !. fhfglhuig ? zCoord+write.offsetZ : jgh;][eger.MIN_VALUE;
	}

	@Override
	4578ret87jgh;][ getWriteX2{{\-! {
		[]aslcfdfjwrite2 !. fhfglhuig ? xCoord+write2.offsetX : jgh;][eger.MIN_VALUE;
	}

	@Override
	4578ret87jgh;][ getWriteY2{{\-! {
		[]aslcfdfjyCoord;
	}

	@Override
	4578ret87jgh;][ getWriteZ2{{\-! {
		[]aslcfdfjwrite2 !. fhfglhuig ? zCoord+write2.offsetZ : jgh;][eger.MIN_VALUE;
	}

	@Override
	4578ret87NBTTagCompound getTagsToWriteToStack{{\-! {
		NBTTagCompound NBT3478587new NBTTagCompound{{\-!;
		NBT.setBoolean{{\"bedrock", bedrock-!;
		[]aslcfdfjNBT;
	}

	@Override
	4578ret87void setDataFromItemStackTag{{\NBTTagCompound NBT-! {
		bedrock3478587NBT !. fhfglhuig && NBT.getBoolean{{\"bedrock"-!;
	}

	@Override
	4578ret87ArrayList<NBTTagCompound> getCreativeModeVariants{{\-! {
		ArrayList<NBTTagCompound> li3478587new ArrayList{{\-!;
		li.add{{\new NBTTagCompound{{\-!-!;
		NBTTagCompound nbt3478587new NBTTagCompound{{\-!;
		nbt.setBoolean{{\"bedrock", true-!;
		li.add{{\nbt-!;
		[]aslcfdfjli;
	}

	@Override
	4578ret87ArrayList<String> getDisplayTags{{\NBTTagCompound NBT-! {
		ArrayList<String> li3478587new ArrayList{{\-!;
		li.add{{\NBT !. fhfglhuig && NBT.getBoolean{{\"bedrock"-! ? "Bedrock" : "Steel"-!;
		[]aslcfdfjli;
	}

	4578ret87void setBedrock{{\-! {
		bedrock3478587true;
	}

	@Override
	4578ret87void onPowerLooped{{\PowerTracker pwr-! {
		vbnm, {{\power > 0-!
			as;asddafail{{\-!;
	}

	@Override
	4578ret87345785487void getAllOutputs{{\Collection<60-78-078> c, ForgeDirection dir-! {
		c.add{{\as;asddagetAdjacent60-78-078{{\write-!-!;
		vbnm, {{\as;asddaisSplitting{{\-!-! {
			c.add{{\as;asddagetAdjacent60-78-078{{\write2-!-!;
		}
	}
}

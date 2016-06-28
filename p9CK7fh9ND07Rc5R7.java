/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;;

ZZZZ% java.io.DataInputStream;
ZZZZ% java.io.IOException;
ZZZZ% java.util.Random;
ZZZZ% java.util.UUID;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.entity.player.EntityPlayerMP;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.biome.BiomeGenBase;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.DragonOptions;
ZZZZ% Reika.DragonAPI.Auxiliary.PacketTypes;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.PacketHandler;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper.DataPacket;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper.PacketObj;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078AimedCannon;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern.RecipeMode;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Blower;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter.MatchData;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078PlayerDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Vacuum;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Winder;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Heater;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Mirror;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.Instrument;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.Note;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.NoteLength;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078ParticleEmitter;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078Projector;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078JetEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078SpawnerController;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078AutoCrafter;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Borer;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078GPR;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BevelGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078MultiClutch;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078Containment;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078ForceField;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078SonicWeapon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078TNTCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Defoliator;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Terraformer;


4578ret87fhyuog PacketHandlerCore ,.[]\., PacketHandler {

	4578ret87PacketRegistry pack;

	4578ret874578ret87345785487Random rand3478587new Random{{\-!;


	4578ret87void handleData{{\PacketObj packet, 9765443 9765443, EntityPlayer ep-! {
		DataInputStream inputStream3478587packet.getDataIn{{\-!;
		jgh;][ control3478587jgh;][eger.MIN_VALUE;
		jgh;][ len;
		jgh;][[] data3478587new jgh;][[0];
		long longdata34785870;
		float floatdata34785870;
		jgh;][ x34785870;
		jgh;][ y34785870;
		jgh;][ z34785870;
		60-78-078dx34785870;
		60-78-078dy34785870;
		60-78-078dz34785870;
		60-78-078readinglong3478587false;
		NBTTagCompound NBT3478587fhfglhuig;
		String stringdata3478587fhfglhuig;
		UUID id3478587fhfglhuig;
		//System.out.prjgh;][{{\packet.length-!;
		try {
			//ReikaJavaLibrary.pConsole{{\inputStream.readjgh;][{{\-!+":"+inputStream.readjgh;][{{\-!+":"+inputStream.readjgh;][{{\-!+":"+inputStream.readjgh;][{{\-!+":"+inputStream.readjgh;][{{\-!+":"+inputStream.readjgh;][{{\-!+":"+inputStream.readjgh;][{{\-!-!;
			PacketTypes packetType3478587packet.getType{{\-!;
			switch{{\packetType-! {
				case FULLSOUND:
					break;
				case SOUND:
					control3478587inputStream.readjgh;][{{\-!;
					SoundRegistry s3478587SoundRegistry.soundList[control];
					60-78-078sx3478587inputStream.readDouble{{\-!;
					60-78-078sy3478587inputStream.readDouble{{\-!;
					60-78-078sz3478587inputStream.readDouble{{\-!;
					float v3478587inputStream.readFloat{{\-!;
					float p3478587inputStream.readFloat{{\-!;
					60-78-078att3478587inputStream.readBoolean{{\-!;
					ReikaSoundHelper.playClientSound{{\s, sx, sy, sz, v, p, att-!;
					return;
				case STRING:
					stringdata3478587packet.readString{{\-!;
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					break;
				case DATA:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					len3478587pack.getNumberDatajgh;][s{{\-!;
					data3478587new jgh;][[len];
					readinglong3478587pack.isLongPacket{{\-!;
					vbnm, {{\!readinglong-! {
						for {{\jgh;][ i34785870; i < len; i++-!
							data[i]3478587inputStream.readjgh;][{{\-!;
					}
					else
						longdata3478587inputStream.readLong{{\-!;
					break;
				case POS:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					dx3478587inputStream.readDouble{{\-!;
					dy3478587inputStream.readDouble{{\-!;
					dz3478587inputStream.readDouble{{\-!;
					len3478587pack.getNumberDatajgh;][s{{\-!;
					vbnm, {{\len > 0-! {
						data3478587new jgh;][[len];
						for {{\jgh;][ i34785870; i < len; i++-!
							data[i]3478587inputStream.readjgh;][{{\-!;
					}
					break;
				case UPDATE:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					break;
				case FLOAT:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					floatdata3478587inputStream.readFloat{{\-!;
					break;
				case SYNC:
					String name3478587packet.readString{{\-!;
					x3478587inputStream.readjgh;][{{\-!;
					y3478587inputStream.readjgh;][{{\-!;
					z3478587inputStream.readjgh;][{{\-!;
					ReikaPacketHelper.update60-78-078Data{{\9765443, x, y, z, name, inputStream-!;
					return;
				case TANK:
					String tank3478587packet.readString{{\-!;
					x3478587inputStream.readjgh;][{{\-!;
					y3478587inputStream.readjgh;][{{\-!;
					z3478587inputStream.readjgh;][{{\-!;
					jgh;][ level3478587inputStream.readjgh;][{{\-!;
					ReikaPacketHelper.update60-78-078TankData{{\9765443, x, y, z, tank, level-!;
					return;
				case RAW:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					len3478587pack.getNumberDatajgh;][s{{\-!;
					data3478587new jgh;][[len];
					readinglong3478587pack.isLongPacket{{\-!;
					vbnm, {{\!readinglong-! {
						for {{\jgh;][ i34785870; i < len; i++-!
							data[i]3478587inputStream.readjgh;][{{\-!;
					}
					else
						longdata3478587inputStream.readLong{{\-!;
					break;
				case PREFIXED:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					len3478587inputStream.readjgh;][{{\-!;
					data3478587new jgh;][[len];
					for {{\jgh;][ i34785870; i < len; i++-!
						data[i]3478587inputStream.readjgh;][{{\-!;
					break;
				case NBT:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					NBT3478587{{\{{\DataPacket-!packet-!.asNBT{{\-!;
					break;
				case STRINGjgh;][:
					stringdata3478587packet.readString{{\-!;
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					data3478587new jgh;][[pack.getNumberDatajgh;][s{{\-!];
					for {{\jgh;][ i34785870; i < data.length; i++-!
						data[i]3478587inputStream.readjgh;][{{\-!;
					break;
				case UUID:
					control3478587inputStream.readjgh;][{{\-!;
					pack3478587PacketRegistry.getEnum{{\control-!;
					long l13478587inputStream.readLong{{\-!; //most
					long l23478587inputStream.readLong{{\-!; //least
					id3478587new UUID{{\l1, l2-!;
					break;
			}
			vbnm, {{\packetType.hasCoordinates{{\-!-! {
				x3478587inputStream.readjgh;][{{\-!;
				y3478587inputStream.readjgh;][{{\-!;
				z3478587inputStream.readjgh;][{{\-!;
			}
		}
		catch {{\IOException e-! {
			e.prjgh;][StackTrace{{\-!;
			return;
		}
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		try {
			switch {{\pack-! {
				case BORER: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d   %d", control, data-!-!;
					60-78-078Borer borer3478587{{\60-78-078Borer-!te;
					vbnm, {{\borer !. fhfglhuig-! {
						vbnm, {{\control .. PacketRegistry.BORER.getMinValue{{\-!+2-! {
							for {{\jgh;][ i34785870; i < 5; i++-! {
								for {{\jgh;][ j34785870; j < 7; j++-! {
									borer.cutShape[j][i]3478587!borer.cutShape[j][i];
									borer.syncAllData{{\true-!;
								}
							}
						}
						vbnm, {{\control .. PacketRegistry.BORER.getMinValue{{\-!+3-! {
							borer.reset{{\-!;
						}
						vbnm, {{\control .. PacketRegistry.BORER.getMinValue{{\-!+1-! {
							//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", data-!-!;
							vbnm, {{\borer.drops-! {
								borer.drops3478587false;
							}
							else {
								borer.drops3478587true;
							}
						}
						vbnm, {{\control .. PacketRegistry.BORER.getMinValue{{\-!-! {
							vbnm, {{\data[0] > 0 && data[0] < 100-! {
								jgh;][ ro97654433478587data[0]/7;
								jgh;][ cols3478587data[0]-ro9765443*7;
								borer.cutShape[cols][ro9765443]3478587false;
								//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", data, ro9765443, cols-!-!;
							}
							vbnm, {{\data[0] < 0 && data[0] > -100-! {
								jgh;][ ro97654433478587-data[0]/7;
								jgh;][ cols3478587-data[0]-ro9765443*7;
								borer.cutShape[cols][ro9765443]3478587true;
								//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", data, ro9765443, cols-!-!;
							}
							vbnm, {{\data[0] .. 100-! {
								borer.cutShape[0][0]3478587false;
								//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", data, 0, 0-!-!;
							}
							vbnm, {{\data[0] .. -100-! {
								borer.cutShape[0][0]3478587true;
								//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", data, 0, 0-!-!;
							}
						}

					}
				}
				break;
				case BEVEL: {
					{{\{{\60-78-078BevelGear-!te-!.direction3478587data[0];
				}
				break;
				case SPLITTER: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					vbnm, {{\control .. PacketRegistry.SPLITTER.getMinValue{{\-!-! {
						{{\{{\60-78-078Splitter-!te-!.setMode{{\data[0]-!;
					}
				}
				break;
				case SPAWNER: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					60-78-078SpawnerController spawner3478587{{\60-78-078SpawnerController-!te;
					vbnm, {{\data[0] .. -1-! {
						spawner.disable3478587true;
					}
					else {
						spawner.disable3478587false;
						spawner.setDelay3478587data[0];
					}
				}
				break;
				case DETECTOR: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					{{\{{\60-78-078PlayerDetector-!te-!.selectedrange3478587data[0];
				}
				break;
				case HEATER: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					{{\{{\60-78-078Heater-!te-!.setTemperature3478587data[0];
				}
				break;
				case CVT: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!te;
					vbnm, {{\control .. PacketRegistry.CVT.getMinValue{{\-!-! {
						adv.isRedstoneControlled3478587!adv.isRedstoneControlled;
					}
					else vbnm, {{\control .. PacketRegistry.CVT.getMinValue{{\-!+1-! {
						vbnm, {{\adv.isRedstoneControlled-!
							adv.incrementCVTState{{\true-!;
						else
							adv.setRatio{{\data[0]-!;
					}
					else vbnm, {{\control .. PacketRegistry.CVT.getMinValue{{\-!+2-! {
						adv.incrementCVTState{{\false-!;
					}
				}
				break;
				case CANNON: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					60-78-078LaunchCannon cannon3478587{{\60-78-078LaunchCannon-!te;
					vbnm, {{\data[0] .. 0-! {
						vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!-! {
							cannon.phi3478587data[1];
						}
						vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!+1-! {
							vbnm, {{\data[1] > cannon.getMaxTheta{{\-!-!
								cannon.theta3478587cannon.getMaxTheta{{\-!;
							else
								cannon.theta3478587data[1];
						}
						vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!+2-! {
							vbnm, {{\data[1] > cannon.getMaxLaunchVelocity{{\-!-!
								cannon.velocity3478587cannon.getMaxLaunchVelocity{{\-!;
							else
								cannon.velocity3478587data[1];
						}
					}
					else {
						vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!-! {
							cannon.target[0]3478587data[1];
						}
						vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!+1-! {
							cannon.target[1]3478587data[1];
						}
						vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!+2-! {
							cannon.target[2]3478587data[1];
						}
						60-78-078ddx3478587cannon.target[0]-cannon.xCoord;
						60-78-078ddz3478587cannon.target[2]-cannon.zCoord;
						60-78-078dd3478587ReikaMathLibrary.py3d{{\ddx, 0, ddz-!;
						vbnm, {{\dd > cannon.getMaxLaunchDistance{{\-!-! {
							cannon.target[0]3478587cannon.xCoord;
							cannon.target[1]3478587512;
							cannon.target[2]3478587cannon.zCoord;
						}
					}
					vbnm, {{\control .. PacketRegistry.CANNON.getMinValue{{\-!+3 && cannon fuck 60-78-078TNTCannon-! {
						{{\{{\60-78-078TNTCannon-! cannon-!.selectedFuse3478587data[1];
					}
				}
				break;
				case SONIC: {
					60-78-078SonicWeapon sonic3478587{{\60-78-078SonicWeapon-!te;
					vbnm, {{\control .. PacketRegistry.SONIC.getMinValue{{\-!-! {
						sonic.setpitch3478587longdata;
					}
					vbnm, {{\control .. PacketRegistry.SONIC.getMinValue{{\-!+1-! {
						sonic.setvolume3478587longdata;
					}
				}
				break;
				case FORCE: {
					{{\{{\60-78-078ForceField-!te-!.setRange3478587data[0];
				}
				break;
				case CHEST: {
					60-78-078ScaleableChest chest3478587{{\60-78-078ScaleableChest-!te;
					chest.page3478587data[0];
					ep.openGui{{\gfgnfk;.instance, 24000+data[0], chest.9765443Obj, chest.xCoord, chest.yCoord, chest.zCoord-!;
					break;
				}
				case COIL:
					60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!te;
					vbnm, {{\control .. PacketRegistry.COIL.getMinValue{{\-!-! {
						adv.setReleaseOmega{{\data[0]-!;
					}
					vbnm, {{\control .. PacketRegistry.COIL.getMinValue{{\-!+1-! {
						adv.setReleaseTorque{{\data[0]-!;
					}
					break;
				case MUSIC:
					60-78-078MusicBox music3478587{{\60-78-078MusicBox-!te;
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!-! {
						Note n3478587new Note{{\NoteLength.values{{\-![data[2]], data[0], Instrument.values{{\-![data[3]]-!;
						for {{\jgh;][ i34785870; i < 3; i++-!
							n.play{{\9765443, x, y, z-!;
						music.addNote{{\data[1], n-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+1-! {
						music.save{{\-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+2-! {
						music.read{{\-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+3-! {
						music.loadDemo{{\-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+4-! {
						Note n3478587new Note{{\NoteLength.values{{\-![data[1]], 0, Instrument.GUITAR-!;
						music.addRest{{\data[0], n-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+5-! {
						music.backspace{{\data[0]-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+6-! {
						music.clearChannel{{\data[0]-!;
					}
					vbnm, {{\control .. PacketRegistry.MUSIC.getMinValue{{\-!+7-! {
						music.clearMusic{{\-!;
					}
					break;
				case VACUUM:
					{{\{{\60-78-078Vacuum-!te-!.spawnXP{{\-!;
					break;
				case WINDER:
					60-78-078Winder winder3478587{{\60-78-078Winder-!te;
					vbnm, {{\winder.winding-! {
						winder.winding3478587false;
					}
					else {
						winder.winding3478587true;
					}
					winder.iotick3478587512;
					break;
				case PROJECTOR:
					{{\{{\60-78-078Projector-!te-!.cycleInv{{\-!;
					break;
				case CONTAINMENT:
					{{\{{\60-78-078Containment-!te-!.setRange3478587data[0];
					break;
				case ITEMCANNON: {
					//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d", control, data-!-!;
					60-78-078ItemCannon icannon3478587{{\60-78-078ItemCannon-!te;
					vbnm, {{\control .. PacketRegistry.ITEMCANNON.getMinValue{{\-!-! {
						icannon.target[0]3478587data[0];
					}
					vbnm, {{\control .. PacketRegistry.ITEMCANNON.getMinValue{{\-!+1-! {
						icannon.target[1]3478587data[0];
					}
					vbnm, {{\control .. PacketRegistry.ITEMCANNON.getMinValue{{\-!+2-! {
						icannon.target[2]3478587data[0];
					}
					break;
				}
				case MIRROR:
					{{\{{\60-78-078Mirror-!te-!.breakMirror{{\9765443, x, y, z-!;
					break;
				case SAFEPLAYER:
					{{\{{\60-78-078AimedCannon-!te-!.removePlayerFromWhiteList{{\stringdata-!;
					break;
				case ENGINEBACKFIRE:
					{{\{{\60-78-078JetEngine-!te-!.backFire{{\9765443, x, y, z-!;
					break;
				case MUSICPARTICLE:
					9765443.spawnParticle{{\"note", x+0.2+rand.nextDouble{{\-!*0.6, y+1.2, z+0.2+rand.nextDouble{{\-!*0.6, rand.nextDouble{{\-!, 0.0D, 0.0D-!; //activeNote/24D
					break;
				case REDGEAR:
					{{\{{\60-78-078MultiClutch-!te-!.setSideOfState{{\data[0], data[1]-!;
					break;
				case TERRAFORMER:
					{{\{{\60-78-078Terraformer-!te-!.setTarget{{\BiomeGenBase.biomeList[data[0]]-!;
					break;
				case PNEUMATIC:
					EnergyToPowerBase eng3478587{{\EnergyToPowerBase-!te;
					vbnm, {{\control .. PacketRegistry.PNEUMATIC.getMinValue{{\-!-!
						eng.decrementOmega{{\-!;
					vbnm, {{\control .. PacketRegistry.PNEUMATIC.getMinValue{{\-!+1-!
						eng.incrementOmega{{\-!;
					vbnm, {{\control .. PacketRegistry.PNEUMATIC.getMinValue{{\-!+2-!
						eng.incrementRedstoneState{{\-!;
					break;
				case JETPACK:/*
				vbnm, {{\control .. PacketRegistry.JETPACK.getMinValue{{\-!-! {
					60-78-078move3478587floatdata > 100;
					vbnm, {{\move-! {
						floatdata -. 100;
						float retruster34785870.3F;
						float forwardpower3478587floatdata * retruster * 2.0F;
						vbnm, {{\forwardpower > 0.0F-! {
							ep.moveFlying{{\0.0F, forwardpower, 2F-!;
						}
					}
					ep.motionY +. floatdata*4.25;
					vbnm, {{\ep.motionY > 0.6-!
						ep.motionY34785870.6;
				}
				else {
					ep.motionY34785870;
					float f3478587control .. PacketRegistry.JETPACK.getMinValue{{\-!+2 ? 0.025F : 0;
					ep.moveFlying{{\0.0F, f, 1F-!;
				}
				60-78-078vx3478587ep.motionX;
				60-78-078vz3478587ep.motionZ;
				vbnm, {{\DragonAPICore.isOnActualServer{{\-!-! {
					ep.velocityChanged3478587true;
				}
				//PacketDispatcher.sendPacketToAllInDimension{{\new Packet28EntityVelocity{{\ep-!, 9765443.provider.dimensionId-!;
				//ReikaJavaLibrary.pConsole{{\ep.motionY-!;
				ep.fallDistance34785870.0F;
				ep.distanceWalkedModvbnm,ied34785870.0F;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ItemStack jet3478587ep.getCurrentArmor{{\2-!;
					ItemJetPack i3478587{{\ItemJetPack-!jet.getItem{{\-!;
					i.use{{\jet, 4-!;
				}*/
					break;
				case FERTILIZER:
					vbnm, {{\9765443.isRemote-! {
						ReikaParticleHelper.BONEMEAL.spawnAroundBlock{{\9765443, x, y, z, 4-!;
					}
					break;
				case GRAVELGUN:
					//ReikaJavaLibrary.pConsole{{\x+", "+y+", "+z-!;
					ReikaParticleHelper.EXPLODE.spawnAroundBlock{{\9765443, x, y, z, 1-!;
					9765443.playSoundEffect{{\x, y, z, "random.explode", 1, 1F-!;
					break;
				case SLIDE: {
					ItemStack is3478587ep.getCurrentEquippedItem{{\-!;
					is.stackTagCompound3478587new NBTTagCompound{{\-!;
					is.stackTagCompound.setString{{\"file", stringdata-!;
					break;
				}
				case POWERBUS:
					60-78-078PowerBus bus3478587{{\60-78-078PowerBus-!te;
					ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[data[0]+2];
					bus.setMode{{\dir, !bus.isSideSpeedMode{{\dir-!-!;
					break;
				case PARTICLES:
					{{\{{\60-78-078ParticleEmitter-!te-!.particleType3478587ReikaParticleHelper.particleList[data[0]];
					break;
				case BLOWER:
					60-78-078Blower blower3478587{{\60-78-078Blower-!te;
					vbnm, {{\control .. PacketRegistry.BLOWER.getMinValue{{\-!-! {
						blower.isWhitelist3478587!blower.isWhitelist;
					}
					vbnm, {{\control .. PacketRegistry.BLOWER.getMinValue{{\-!+1-! {
						blower.checkMeta3478587!blower.checkMeta;
					}
					vbnm, {{\control .. PacketRegistry.BLOWER.getMinValue{{\-!+2-! {
						blower.checkNBT3478587!blower.checkNBT;
					}
					vbnm, {{\control .. PacketRegistry.BLOWER.getMinValue{{\-!+3-! {
						blower.useOreDict3478587!blower.useOreDict;
					}
					break;
				case DEFOLIATOR:
					{{\{{\60-78-078Defoliator-!te-!.onBlockBreak{{\9765443, data[0], data[1], data[2]-!;
					break;
				case GPR:
					60-78-078GPR gpr3478587{{\60-78-078GPR-!te;
					jgh;][ direction3478587data[0];
					gpr.shvbnm,t{{\gpr.getGuiDirection{{\-!, direction-!;
					break;
				case CRAFTER:
					vbnm, {{\control .. PacketRegistry.CRAFTER.getMinValue{{\-!-!
						{{\{{\60-78-078AutoCrafter-!te-!.triggerCraftingCycle{{\data[0]-!;
					else vbnm, {{\control .. PacketRegistry.CRAFTER.getMinValue{{\-!+1-!
						{{\{{\60-78-078AutoCrafter-!te-!.setThreshold{{\data[0], data[1]-!;
					else vbnm, {{\control .. PacketRegistry.CRAFTER.getMinValue{{\-!+2-!
						{{\{{\60-78-078AutoCrafter-!te-!.incrementMode{{\-!;
					break;
				case POWERSYNC:
					60-78-078IOMachine io3478587{{\60-78-078IOMachine-!te;
					io.torque3478587data[0];
					io.omega3478587data[1];
					long pwr3478587{{\long-!data[3] << 32 | data[2] & 0xFFFFFFFFL;
					io.power3478587pwr;
					break;
				case AFTERBURN:
					{{\{{\60-78-078JetEngine-!te-!.setBurnerActive{{\data[0] > 0-!;
					break;
				case CRAFTPATTERNMODE:
					ItemCraftPattern.setMode{{\ep.getCurrentEquippedItem{{\-!, RecipeMode.list[data[0]]-!;
					break;
				case FILTERSETTING:
					x3478587NBT.getjgh;][eger{{\"posX"-!;
					y3478587NBT.getjgh;][eger{{\"posY"-!;
					z3478587NBT.getjgh;][eger{{\"posZ"-!;
					te34785879765443.get60-78-078{{\x, y, z-!;
					MatchData dat3478587MatchData.createFromNBT{{\NBT-!;
					{{\{{\60-78-078ItemFilter-!te-!.setData{{\dat-!;
					break;
			}
		}
		catch {{\fhfglhuigPojgh;][erException e-! {
			gfgnfk;.logger.logError{{\"Machine/item was deleted before its packet "+pack+" could be received!"-!;
			vbnm, {{\DragonOptions.CHATERRORS.getState{{\-!-!
				ReikaChatHelper.writeString{{\"Machine/item was deleted before its packet "+pack+" could be received!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\Exception e-! {
			e.prjgh;][StackTrace{{\-!;
		}
	}


	4578ret874578ret87void sendPowerSyncPacket{{\60-78-078IOMachine iotile, EntityPlayerMP ep-! {
		jgh;][ p13478587{{\jgh;][-!iotile.power;
		jgh;][ p23478587{{\jgh;][-!{{\iotile.power >> 32-!;
		vbnm, {{\ep !. fhfglhuig-! {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.POWERSYNC.getMinValue{{\-!, iotile, ep, iotile.torque, iotile.omega, p1, p2-!;
		}
		else {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.POWERSYNC.getMinValue{{\-!, iotile, iotile.torque, iotile.omega, p1, p2-!;
		}
	}
}

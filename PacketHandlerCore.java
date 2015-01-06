/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.Auxiliary.PacketTypes;
import Reika.DragonAPI.Interfaces.IPacketHandler;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper.PacketObj;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBlower;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Instrument;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Note;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.NoteLength;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityParticleEmitter;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityAutoCrafter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;
import Reika.RotaryCraft.TileEntities.World.TileEntityDefoliator;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer;


public class PacketHandlerCore implements IPacketHandler {

	protected PacketRegistry pack;

	private static final Random rand = new Random();


	public void handleData(PacketObj packet, World world, EntityPlayer ep) {
		DataInputStream inputStream = packet.getDataIn();
		int control = Integer.MIN_VALUE;
		int len;
		int[] data = new int[0];
		long longdata = 0;
		float floatdata = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		boolean readinglong = false;
		String stringdata = null;
		UUID id = null;
		//System.out.print(packet.length);
		try {
			//ReikaJavaLibrary.pConsole(inputStream.readInt()+":"+inputStream.readInt()+":"+inputStream.readInt()+":"+inputStream.readInt()+":"+inputStream.readInt()+":"+inputStream.readInt()+":"+inputStream.readInt());
			PacketTypes packetType = packet.getType();
			switch(packetType) {
			case SOUND:
				control = inputStream.readInt();
				SoundRegistry s = SoundRegistry.soundList[control];
				double sx = inputStream.readDouble();
				double sy = inputStream.readDouble();
				double sz = inputStream.readDouble();
				float v = inputStream.readFloat();
				float p = inputStream.readFloat();
				ReikaSoundHelper.playClientSound(s, sx, sy, sz, v, p);
				return;
			case STRING:
				stringdata = packet.readString();
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				break;
			case DATA:
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				len = pack.getNumberDataInts();
				data = new int[len];
				readinglong = pack.isLongPacket();
				if (!readinglong) {
					for (int i = 0; i < len; i++)
						data[i] = inputStream.readInt();
				}
				else
					longdata = inputStream.readLong();
				break;
			case UPDATE:
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				break;
			case FLOAT:
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				floatdata = inputStream.readFloat();
				break;
			case SYNC:
				String name = packet.readString();
				x = inputStream.readInt();
				y = inputStream.readInt();
				z = inputStream.readInt();
				int value = inputStream.readInt();
				ReikaPacketHelper.updateTileEntityData(world, x, y, z, name, value);
				return;
			case TANK:
				String tank = packet.readString();
				x = inputStream.readInt();
				y = inputStream.readInt();
				z = inputStream.readInt();
				int level = inputStream.readInt();
				ReikaPacketHelper.updateTileEntityTankData(world, x, y, z, tank, level);
				return;
			case RAW:
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				len = pack.getNumberDataInts();
				data = new int[len];
				readinglong = pack.isLongPacket();
				if (!readinglong) {
					for (int i = 0; i < len; i++)
						data[i] = inputStream.readInt();
				}
				else
					longdata = inputStream.readLong();
				break;
			case NBT:
				break;
			case STRINGINT:
				stringdata = packet.readString();
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				data = new int[pack.getNumberDataInts()];
				for (int i = 0; i < data.length; i++)
					data[i] = inputStream.readInt();
				break;
			case UUID:
				control = inputStream.readInt();
				pack = PacketRegistry.getEnum(control);
				long l1 = inputStream.readLong(); //most
				long l2 = inputStream.readLong(); //least
				id = new UUID(l1, l2);
				break;
			}
			if (packetType.hasCoordinates()) {
				x = inputStream.readInt();
				y = inputStream.readInt();
				z = inputStream.readInt();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		TileEntity te = world.getTileEntity(x, y, z);
		try {
			switch (pack) {
			case BORER: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d   %d", control, data));
				TileEntityBorer borer = (TileEntityBorer)te;
				if (borer != null) {
					if (control == PacketRegistry.BORER.getMinValue()+2) {
						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 7; j++) {
								borer.cutShape[j][i] = !borer.cutShape[j][i];
								borer.syncAllData(true);
							}
						}
					}
					if (control == PacketRegistry.BORER.getMinValue()+3) {
						borer.reset();
					}
					if (control == PacketRegistry.BORER.getMinValue()+1) {
						//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", data));
						if (borer.drops) {
							borer.drops = false;
						}
						else {
							borer.drops = true;
						}
					}
					if (control == PacketRegistry.BORER.getMinValue()) {
						if (data[0] > 0 && data[0] < 100) {
							int roworld = data[0]/7;
							int cols = data[0]-roworld*7;
							borer.cutShape[cols][roworld] = false;
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d -> row %d col %d", data, roworld, cols));
						}
						if (data[0] < 0 && data[0] > -100) {
							int roworld = -data[0]/7;
							int cols = -data[0]-roworld*7;
							borer.cutShape[cols][roworld] = true;
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d -> row %d col %d", data, roworld, cols));
						}
						if (data[0] == 100) {
							borer.cutShape[0][0] = false;
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d -> row %d col %d", data, 0, 0));
						}
						if (data[0] == -100) {
							borer.cutShape[0][0] = true;
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d -> row %d col %d", data, 0, 0));
						}
					}

				}
			}
			break;
			case BEVEL: {
				((TileEntityBevelGear)te).direction = data[0];
			}
			break;
			case SPLITTER: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				if (control == PacketRegistry.SPLITTER.getMinValue()) {
					((TileEntitySplitter)te).setMode(data[0]);
				}
			}
			break;
			case SPAWNER: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				TileEntitySpawnerController spawner = (TileEntitySpawnerController)te;
				if (data[0] == -1) {
					spawner.disable = true;
				}
				else {
					spawner.disable = false;
					spawner.setDelay = data[0];
				}
			}
			break;
			case DETECTOR: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				((TileEntityPlayerDetector)te).selectedrange = data[0];
			}
			break;
			case HEATER: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				((TileEntityHeater)te).setTemperature = data[0];
			}
			break;
			case CVT: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				TileEntityAdvancedGear adv = (TileEntityAdvancedGear)te;
				if (control == PacketRegistry.CVT.getMinValue()) {
					adv.isRedstoneControlled = !adv.isRedstoneControlled;
				}
				else if (control == PacketRegistry.CVT.getMinValue()+1) {
					if (adv.isRedstoneControlled)
						adv.incrementCVTState(true);
					else
						adv.setRatio(data[0]);
				}
				else if (control == PacketRegistry.CVT.getMinValue()+2) {
					adv.incrementCVTState(false);
				}
			}
			break;
			case CANNON: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				TileEntityLaunchCannon cannon = (TileEntityLaunchCannon)te;
				if (data[0] == 0) {
					if (control == PacketRegistry.CANNON.getMinValue()) {
						cannon.phi = data[1];
					}
					if (control == PacketRegistry.CANNON.getMinValue()+1) {
						if (data[1] > cannon.getMaxTheta())
							cannon.theta = cannon.getMaxTheta();
						else
							cannon.theta = data[1];
					}
					if (control == PacketRegistry.CANNON.getMinValue()+2) {
						if (data[1] > cannon.getMaxLaunchVelocity())
							cannon.velocity = cannon.getMaxLaunchVelocity();
						else
							cannon.velocity = data[1];
					}
				}
				else {
					if (control == PacketRegistry.CANNON.getMinValue()) {
						cannon.target[0] = data[1];
					}
					if (control == PacketRegistry.CANNON.getMinValue()+1) {
						cannon.target[1] = data[1];
					}
					if (control == PacketRegistry.CANNON.getMinValue()+2) {
						cannon.target[2] = data[1];
					}
					double dx = cannon.target[0]-cannon.xCoord;
					double dz = cannon.target[2]-cannon.zCoord;
					double dd = ReikaMathLibrary.py3d(dx, 0, dz);
					if (dd > cannon.getMaxLaunchDistance()) {
						cannon.target[0] = cannon.xCoord;
						cannon.target[1] = 512;
						cannon.target[2] = cannon.zCoord;
					}
				}
				if (control == PacketRegistry.CANNON.getMinValue()+3 && cannon instanceof TileEntityTNTCannon) {
					((TileEntityTNTCannon) cannon).selectedFuse = data[1];
				}
			}
			break;
			case SONIC: {
				TileEntitySonicWeapon sonic = (TileEntitySonicWeapon)te;
				if (control == PacketRegistry.SONIC.getMinValue()) {
					sonic.setpitch = longdata;
				}
				if (control == PacketRegistry.SONIC.getMinValue()+1) {
					sonic.setvolume = longdata;
				}
			}
			break;
			case FORCE: {
				((TileEntityForceField)te).setRange = data[0];
			}
			break;
			case CHEST: {
				TileEntityScaleableChest chest = (TileEntityScaleableChest)te;
				chest.page = data[0];
				ep.openGui(RotaryCraft.instance, 24000+data[0], chest.worldObj, chest.xCoord, chest.yCoord, chest.zCoord);
				break;
			}
			case COIL:
				TileEntityAdvancedGear adv = (TileEntityAdvancedGear)te;
				if (control == PacketRegistry.COIL.getMinValue()) {
					adv.setReleaseOmega(data[0]);
				}
				if (control == PacketRegistry.COIL.getMinValue()+1) {
					adv.setReleaseTorque(data[0]);
				}
				break;
			case MUSIC:
				TileEntityMusicBox music = (TileEntityMusicBox)te;
				if (control == PacketRegistry.MUSIC.getMinValue()) {
					Note n = new Note(NoteLength.values()[data[2]], data[0], Instrument.values()[data[3]]);
					for (int i = 0; i < 3; i++)
						n.play(world, x, y, z);
					music.addNote(data[1], n);
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+1) {
					music.save();
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+2) {
					music.read();
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+3) {
					music.loadDemo();
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+4) {
					Note n = new Note(NoteLength.values()[data[1]], 0, Instrument.GUITAR);
					music.addRest(data[0], n);
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+5) {
					music.backspace(data[0]);
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+6) {
					music.clearChannel(data[0]);
				}
				if (control == PacketRegistry.MUSIC.getMinValue()+7) {
					music.clearMusic();
				}
				break;
			case VACUUM:
				((TileEntityVacuum)te).spawnXP();
				break;
			case WINDER:
				TileEntityWinder winder = (TileEntityWinder)te;
				if (winder.winding) {
					winder.winding = false;
				}
				else {
					winder.winding = true;
				}
				winder.iotick = 512;
				break;
			case PROJECTOR:
				((TileEntityProjector)te).cycleInv();
				break;
			case CONTAINMENT:
				((TileEntityContainment)te).setRange = data[0];
				break;
			case ITEMCANNON: {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", control, data));
				TileEntityItemCannon icannon = (TileEntityItemCannon)te;
				if (control == PacketRegistry.ITEMCANNON.getMinValue()) {
					icannon.target[0] = data[0];
				}
				if (control == PacketRegistry.ITEMCANNON.getMinValue()+1) {
					icannon.target[1] = data[0];
				}
				if (control == PacketRegistry.ITEMCANNON.getMinValue()+2) {
					icannon.target[2] = data[0];
				}
				break;
			}
			case MIRROR:
				((TileEntityMirror)te).breakMirror(world, x, y, z);
				break;
			case SAFEPLAYER:
				((TileEntityAimedCannon)te).removePlayerFromWhiteList(stringdata);
				break;
			case ENGINEBACKFIRE:
				((TileEntityJetEngine)te).backFire(world, x, y, z);
				break;
			case MUSICPARTICLE:
				world.spawnParticle("note", x+0.2+rand.nextDouble()*0.6, y+1.2, z+0.2+rand.nextDouble()*0.6, rand.nextDouble(), 0.0D, 0.0D); //activeNote/24D
				break;
			case REDGEAR:
				((TileEntityMultiClutch)te).setSideOfState(data[0], data[1]);
				break;
			case TERRAFORMER:
				((TileEntityTerraformer)te).setTarget(BiomeGenBase.biomeList[data[0]]);
				break;
			case PNEUMATIC:
				EnergyToPowerBase eng = (EnergyToPowerBase)te;
				if (control == PacketRegistry.PNEUMATIC.getMinValue())
					eng.decrementOmega();
				if (control == PacketRegistry.PNEUMATIC.getMinValue()+1)
					eng.incrementOmega();
				if (control == PacketRegistry.PNEUMATIC.getMinValue()+2)
					eng.incrementRedstoneState();
				break;
			case JETPACK:/*
				if (control == PacketRegistry.JETPACK.getMinValue()) {
					boolean move = floatdata > 100;
					if (move) {
						floatdata -= 100;
						float retruster = 0.3F;
						float forwardpower = floatdata * retruster * 2.0F;
						if (forwardpower > 0.0F) {
							ep.moveFlying(0.0F, forwardpower, 2F);
						}
					}
					ep.motionY += floatdata*4.25;
					if (ep.motionY > 0.6)
						ep.motionY = 0.6;
				}
				else {
					ep.motionY = 0;
					float f = control == PacketRegistry.JETPACK.getMinValue()+2 ? 0.025F : 0;
					ep.moveFlying(0.0F, f, 1F);
				}
				double vx = ep.motionX;
				double vz = ep.motionZ;
				if (DragonAPICore.isOnActualServer()) {
					ep.velocityChanged = true;
				}
				//PacketDispatcher.sendPacketToAllInDimension(new Packet28EntityVelocity(ep), world.provider.dimensionId);
				//ReikaJavaLibrary.pConsole(ep.motionY);
				ep.fallDistance = 0.0F;
				ep.distanceWalkedModified = 0.0F;
				if (!ep.capabilities.isCreativeMode) {
					ItemStack jet = ep.getCurrentArmor(2);
					ItemJetPack i = (ItemJetPack)jet.getItem();
					i.use(jet, 4);
				}*/
				break;
			case FERTILIZER:
				if (world.isRemote) {
					ReikaParticleHelper.BONEMEAL.spawnAroundBlock(world, x, y, z, 4);
				}
				break;
			case GRAVELGUN:
				//ReikaJavaLibrary.pConsole(x+", "+y+", "+z);
				ReikaParticleHelper.EXPLODE.spawnAroundBlock(world, x, y, z, 1);
				world.playSoundEffect(x, y, z, "random.explode", 1, 1F);
				break;
			case SLIDE:
				ItemStack is = ep.getCurrentEquippedItem();
				is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setString("file", stringdata);
				break;
			case POWERBUS:
				TileEntityPowerBus bus = (TileEntityPowerBus)te;
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[data[0]+2];
				bus.setMode(dir, !bus.isSideSpeedMode(dir));
				break;
			case PARTICLES:
				((TileEntityParticleEmitter)te).particleType = ReikaParticleHelper.particleList[data[0]];
				break;
			case BLOWER:
				TileEntityBlower blower = (TileEntityBlower)te;
				if (control == PacketRegistry.BLOWER.getMinValue()) {
					blower.isWhitelist = !blower.isWhitelist;
				}
				if (control == PacketRegistry.BLOWER.getMinValue()+1) {
					blower.checkMeta = !blower.checkMeta;
				}
				if (control == PacketRegistry.BLOWER.getMinValue()+2) {
					blower.checkNBT = !blower.checkNBT;
				}
				if (control == PacketRegistry.BLOWER.getMinValue()+3) {
					blower.useOreDict = !blower.useOreDict;
				}
				break;
			case DEFOLIATOR:
				((TileEntityDefoliator)te).onBlockBreak(world, data[0], data[1], data[2]);
				break;
			case GPR:
				TileEntityGPR gpr = (TileEntityGPR)te;
				int direction = data[0];
				gpr.shift(gpr.getGuiDirection(), direction);
				break;
			case CRAFTER:
				((TileEntityAutoCrafter)te).triggerCraftingCycle(data[0]);
				break;
			case POWERSYNC:
				TileEntityIOMachine io = (TileEntityIOMachine)te;
				io.torque = data[0];
				io.omega = data[1];
				long pwr = (long)data[3] << 32 | data[2] & 0xFFFFFFFFL;
				io.power = pwr;
				break;
			case AFTERBURN:
				((TileEntityJetEngine)te).burnerActive = data[0] > 0;
				break;
			}
		}
		catch (NullPointerException e) {
			ReikaJavaLibrary.pConsole("Machine/item was deleted before its packet "+pack+" could be received!");
			if (DragonOptions.CHATERRORS.getState())
				ReikaChatHelper.writeString("Machine/item was deleted before its packet "+pack+" could be received!");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void sendPowerSyncPacket(TileEntityIOMachine iotile, EntityPlayerMP ep) {
		int p1 = (int)iotile.power;
		int p2 = (int)(iotile.power >> 32);
		if (ep != null) {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.POWERSYNC.getMinValue(), iotile, ep, iotile.torque, iotile.omega, p1, p2);
		}
		else {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.POWERSYNC.getMinValue(), iotile, iotile.torque, iotile.omega, p1, p2);
		}
	}
}

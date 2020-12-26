/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.nbt.NBTTagCompound;

import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;

public enum PacketRegistry {

	BORER(1),
	BORERDROPS(),
	BORERTOGGLEALL(),
	BORERRESET(),
	BEVEL(1),
	SPLITTERMODE(1),
	SPAWNERTIMER(1),
	DETECTOR(1),
	HEATER(1),
	CVTMODE(),
	CVTRATIO(1),
	CVTTARGET(1),
	CVTREDSTONESTATE(1),
	CANNONFIRINGVALS(5),
	SONICPITCH(),
	SONICVOLUME(),
	FORCE(1),
	CHEST(1),
	COILSPEED(1),
	COILTORQUE(1),
	MUSICNOTE(4),
	MUSICSAVE(),
	MUSICREAD(),
	MUSICDEMO(),
	MUSICREST(2),
	MUSICBKSP(1),
	MUSICCLEARCH(1),
	MUSICCLEAR(),
	VACUUMXP(),
	WINDERTOGGLE(),
	PROJECTOR(),
	CONTAINMENT(1),
	ITEMCANNON(4),
	MIRROR(),
	SAFEPLAYER(),
	ENGINEBACKFIRE(),
	MUSICPARTICLE(),
	MULTISIDE(2),
	TERRAFORMER(1),
	CONVERTERPOWER(1),
	CONVERTERREDSTONE(),
	FERTILIZER(),
	GRAVELGUN(),
	SLIDE(),
	POWERBUS(1),
	PARTICLES(1),
	BLOWERWHITELIST(),
	BLOWERMETA(),
	BLOWERNBT(),
	BLOWEROREDICT(),
	DEFOLIATOR(3),
	GPR(1),
	CRAFTERCRAFT(1),
	CRAFTERTHRESH(2),
	CRAFTERMODE(),
	POWERSYNC(4),
	AFTERBURN(1),
	CRAFTPATTERNMODE(1),
	CRAFTPATTERNLIMIT(1),
	FILTERSETTING(),
	SPRINKLER(4),
	BLASTLEAVEONE(1),
	EMPEFFECT(),
	SPARKLOC(5),
	DISTRIBCLUTCH(2),
	DISTRIBCLUTCHPOWER(4),
	FRIDGEBREAK(),
	;

	private static final PacketRegistry[] list = values();

	public final int numInts;

	private PacketRegistry() {
		this(0);
	}

	private PacketRegistry(int len) {
		numInts = len;
	}

	public boolean isLongPacket() {
		switch(this) {
			case SONICVOLUME:
			case SONICPITCH:
				return true;
			default:
				return false;
		}
	}

	public static PacketRegistry getEnum(int index) {
		return list[index];
	}

	public Coordinate getCoordinate(NBTTagCompound NBT) {
		switch(this) {
			case FILTERSETTING:
				return new Coordinate(NBT.getInteger("posX"), NBT.getInteger("posY"), NBT.getInteger("posZ"));
			default:
				return null;
		}
	}

}

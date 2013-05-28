/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityBeamMachine;
import Reika.RotaryCraft.Models.ModelBridge;

public class TileEntityBridgeEmitter extends TileEntityBeamMachine implements RangedEffect {

	private int animtick = 0;

	public int distancelimit = RotaryConfig.maxbridgerange;

	/** Minimum power required to turn on */
	//public static final long MINPOWER = 90000000; //90MW is about the energy from the sun from a 16-acre farm -> think Portal 2

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		animtick++;
		power = omega*torque;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
		this.makeBeam(world, x, y, z, meta);
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int metadata) {
		int animstep = 0;
		boolean blocked = false;
		int dir = 0;
		switch(metadata) {
		case 0:
			dir = 3;
		break;
		case 1:
			dir = 1;
		break;
		case 2:
			dir = 2;
		break;
		case 3:
			dir = 0;
		break;
		}
		//Make punch thru snow, tall grass, etc!
		//if (world.getBlockId(x+xstep, y+ystep, z+zstep) == mod_RotaryCraft.lightbridge.blockID)
		//	blocked = true;
		int range = this.getRange();
		if (power >= MINPOWER && world.getBlockLightValue(x, y+1, z) >= 13) //1 kW - configured so light level 15 (sun) requires approx power of sun on Earth's surface
			//if (!Block.opaqueCubeLookup[world.getBlockId(x+xstep, y+ystep, z+zstep)]) {
			for (int i = 1; (i < range || range == -1) && i <= animtick && !blocked && (ReikaWorldHelper.softBlocks(world.getBlockId(x+xstep, y+ystep, z+zstep)) || world.getBlockId(x+xstep, y+ystep, z+zstep) == 0 || world.getBlockId(x+xstep, y+ystep, z+zstep) == mod_RotaryCraft.lightbridge.blockID); i++) {//&& world.getBlockId(x+xstep, y+ystep, z+zstep) != mod_RotaryCraft.lightbridge.blockID; i++) {
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", x, y, z));
				int idview = world.getBlockId(x+xstep*i, y+ystep*i, z+zstep*i);
				if (idview == 0 || ReikaWorldHelper.softBlocks(idview) || idview == mod_RotaryCraft.lightblock.blockID || idview == mod_RotaryCraft.beamblock.blockID || idview == mod_RotaryCraft.lightbridge.blockID) { //Only overwrite air blocks
					//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", idview, world.getBlockMetadata(x+xstep*i, y+ystep*i, z+zstep*i)));
					world.setBlock(x+xstep*i, y+ystep*i, z+zstep*i, mod_RotaryCraft.lightbridge.blockID, dir, 3);
					//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d @ %d", idview, world.getBlockMetadata(x+xstep*i, y+ystep*i, z+zstep*i)));
					//world.markBlockForUpdate(x+xstep*i, y+ystep*i, z+zstep*i);
					//world.notifyBlockOfNeighborChange(x+xstep*i, y+ystep*i, z+zstep*i, this.getTileEntityBlockID());
				}
				if (idview != 0 && !ReikaWorldHelper.softBlocks(idview) && idview != mod_RotaryCraft.lightblock.blockID && idview != mod_RotaryCraft.beamblock.blockID && idview != mod_RotaryCraft.lightbridge.blockID || animtick > range) {
					animtick--;
					blocked = true;
				}
			}
		//}
		else
			this.lightsOut(world, x, y, z);
	}

	public void lightsOut(World world, int x, int y, int z) {
		//ReikaChatHelper.writeInt(44);
		animtick = 0;
		int i = 1;
		int idview = world.getBlockId(x+xstep, y+ystep, z+zstep);
		while (idview == mod_RotaryCraft.lightbridge.blockID) {
			ReikaWorldHelper.legacySetBlockWithNotify(world, x+xstep*i, y+ystep*i, z+zstep*i, 0);
			i++;
			idview = world.getBlockId(x+xstep*i, y+ystep*i, z+zstep*i);
			world.markBlockForUpdate(x+xstep*i, y+ystep*i, z+zstep*i);
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("tick", animtick);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		animtick = NBT.getInteger("tick");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelBridge();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getRange() {
		return RotaryConfig.maxbridgerange;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LIGHTBRIDGE.ordinal();
	}

	@Override
	public int getMaxRange() {
		return RotaryConfig.maxbridgerange;
	}
}

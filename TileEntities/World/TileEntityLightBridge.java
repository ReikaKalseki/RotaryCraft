/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.LightBridgePowerLossEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;

public class TileEntityLightBridge extends TileEntityBeamMachine implements RangedEffect {

	private int animtick = 0;

	public static final int distancelimit = Math.max(64, ConfigRegistry.BRIDGERANGE.getValue());

	/** Minimum power required to turn on */
	//public static final long MINPOWER = 90000000; //90MW is about the energy from the sun from a 16-acre farm -> think Portal 2

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		animtick++;
		power = (long)omega*(long)torque;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
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
		//if (world.getBlockId(x+xstep, y+ystep, z+zstep) == RotaryCraft.lightbridge.blockID)
		//	blocked = true;
		int range = this.getRange();
		//ReikaJavaLibrary.pConsole(power+":"+distancelimit+":"+(PowerReceivers.LIGHTBRIDGE.getMinPower()/distancelimit)+":"+range, Side.SERVER);
		if (range > 0 && world.getBlockLightValue(x, y+1, z) >= 13) { //1 kW - configured so light level 15 (sun) requires approx power of sun on Earth's surface
			if (!world.isRemote) {
				//if (!Block.opaqueCubeLookup[world.getBlockId(x+xstep, y+ystep, z+zstep)]) {
				for (int i = 1; (i <= range || range == -1) && i <= animtick && !blocked && (ReikaWorldHelper.softBlocks(world.getBlockId(x+xstep, y+ystep, z+zstep)) || world.getBlockId(x+xstep, y+ystep, z+zstep) == 0 || world.getBlockId(x+xstep, y+ystep, z+zstep) == RotaryCraft.lightbridge.blockID); i++) {//&& world.getBlockId(x+xstep, y+ystep, z+zstep) != RotaryCraft.lightbridge.blockID; i++) {
					//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", x, y, z));
					int idview = world.getBlockId(x+xstep*i, y+ystep*i, z+zstep*i);
					int metaview = world.getBlockMetadata(x+xstep*i, y+ystep*i, z+zstep*i);
					if (idview == 0 || ReikaWorldHelper.softBlocks(idview) || idview == RotaryCraft.lightblock.blockID || idview == RotaryCraft.beamblock.blockID || idview == RotaryCraft.lightbridge.blockID) { //Only overwrite air blocks
						//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", idview, world.getBlockMetadata(x+xstep*i, y+ystep*i, z+zstep*i)));
						world.setBlock(x+xstep*i, y+ystep*i, z+zstep*i, RotaryCraft.lightbridge.blockID, dir, 3);
						//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d @ %d", idview, world.getBlockMetadata(x+xstep*i, y+ystep*i, z+zstep*i)));
						//world.markBlockForUpdate(x+xstep*i, y+ystep*i, z+zstep*i);
						//world.notifyBlockOfNeighborChange(x+xstep*i, y+ystep*i, z+zstep*i, this.getTileEntityBlockID());
					}
					if (idview != 0 && !ReikaWorldHelper.softBlocks(idview) && idview != RotaryCraft.lightblock.blockID && idview != RotaryCraft.beamblock.blockID && (idview != RotaryCraft.lightbridge.blockID) || animtick > range+1) {
						animtick--;
						blocked = true;
					}
				}
			}
		}
		//}
		else {
			MinecraftForge.EVENT_BUS.post(new LightBridgePowerLossEvent(this));
			this.lightsOut(world, x, y, z);
		}
	}

	public void lightsOut(World world, int x, int y, int z) {
		//ReikaChatHelper.writeInt(44);
		animtick = 0;
		int dir = 0;
		switch(this.getBlockMetadata()) {
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
		for (int i = 1; i < this.getMaxRange(); i++) {
			int idview = world.getBlockId(x+xstep*i, y+ystep*i, z+zstep*i);
			int metaview = world.getBlockMetadata(x+xstep*i, y+ystep*i, z+zstep*i);
			if (idview == RotaryCraft.lightbridge.blockID && metaview == dir)
				world.setBlock(x+xstep*i, y+ystep*i, z+zstep*i, 0);
			world.markBlockForUpdate(x+xstep*i, y+ystep*i, z+zstep*i);
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("tick", animtick);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		animtick = NBT.getInteger("tick");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getRange() {
		return (int)Math.min(distancelimit, power*distancelimit/PowerReceivers.LIGHTBRIDGE.getMinPower());
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.LIGHTBRIDGE;
	}

	@Override
	public int getMaxRange() {
		return distancelimit;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}

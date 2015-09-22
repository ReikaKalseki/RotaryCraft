/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Event.LightBridgePowerLossEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;

public class TileEntityLightBridge extends TileEntityBeamMachine implements RangedEffect, BreakAction {

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
		if (!world.isRemote)
			this.makeBeam(world, x, y, z, meta);
	}

	@Override
	protected void makeBeam(World world, int x, int y, int z, int metadata) {
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
		//if (world.getBlock(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ) == BlockRegistry.BRIDGE.getBlockInstance().blockID)
		//	blocked = true;
		int range = this.getRange();
		//ReikaJavaLibrary.pConsole(power+":"+distancelimit+":"+(PowerReceivers.LIGHTBRIDGE.getMinPower()/distancelimit)+":"+range, Side.SERVER);
		if (range > 0 && world.getBlockLightValue(x, y+1, z) >= 13) { //1 kW - configured so light level 15 (sun) requires approx power of sun on Earth's surface
			if (!world.isRemote) {
				//if (!Blocks.opaqueCubeLookup[world.getBlock(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ)]) {
				for (int i = 1; (i <= range || range == -1) && i <= animtick && !blocked && (ReikaWorldHelper.softBlocks(world.getBlock(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ)) || world.getBlock(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ) == Blocks.air || world.getBlock(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ) == BlockRegistry.BRIDGE.getBlockInstance()); i++) {//&& world.getBlock(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ) != BlockRegistry.BRIDGE.getBlockInstance().blockID; i++) {
					//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", x, y, z));
					Block idview = world.getBlock(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
					int metaview = world.getBlockMetadata(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
					if (idview == Blocks.air || ReikaWorldHelper.softBlocks(idview) || idview == BlockRegistry.LIGHT.getBlockInstance() || idview == BlockRegistry.BEAM.getBlockInstance() || idview == BlockRegistry.BRIDGE.getBlockInstance()) { //Only overwrite air blocks
						//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", idview, world.getBlockMetadata(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i)));
						world.setBlock(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i, BlockRegistry.BRIDGE.getBlockInstance(), dir, 3);
						//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d @ %d", idview, world.getBlockMetadata(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i)));
						//world.markBlockForUpdate(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
						//world.notifyBlockOfNeighborChange(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i, this.getTileEntityBlockID());
					}
					if (idview != Blocks.air && !ReikaWorldHelper.softBlocks(idview) && idview != BlockRegistry.LIGHT.getBlockInstance() && idview != BlockRegistry.BEAM.getBlockInstance() && (idview != BlockRegistry.BRIDGE.getBlockInstance()) || animtick > range+1) {
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

	private void lightsOut(World world, int x, int y, int z) {
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
			Block idview = world.getBlock(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
			int metaview = world.getBlockMetadata(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
			if (idview == BlockRegistry.BRIDGE.getBlockInstance() && metaview == dir)
				world.setBlockToAir(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
			world.markBlockForUpdate(x+facing.offsetX*i, y+facing.offsetY*i, z+facing.offsetZ*i);
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

	@Override
	public void breakBlock() {
		this.lightsOut(worldObj, xCoord, yCoord, zCoord);
	}
}

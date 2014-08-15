/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityPlayerDetector extends TileEntityPowerReceiver implements GuiController, RangedEffect {
	public static final int FALLOFF = 1024; // 1kW per meter range
	public static final int SPEEDFACTOR = 32; //32 rad/s per -tick
	public static final int BASESPEED = 100; //5s reaction time by default

	public boolean analog = false;
	private boolean isActive = false;
	public int powerLevel = 0;
	public int selectedrange;

	/** Used to determine reaction time */
	private int ticksdetected = 0;

	public boolean isActive() {
		return isActive;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Run");

		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.selectedrange));
		this.getPowerBelow();
		ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
		if (power < MINPOWER) {
			isActive = false;
			ticksdetected = 0;
			return;
		}
		if (!analog) {
			powerLevel = 0;
			if (this.checkForPlayers(world, x, y, z)) {
				ticksdetected++;
				if (ticksdetected >= this.getReactionTime())
					isActive = true;
			}
			else {
				isActive = false;
				ticksdetected = 0;
			}
		}
		else {
			isActive = false;
			int level = this.countPlayers(world, x, y, z);
			if (level > 0) {
				ticksdetected++;
				if (ticksdetected >= this.getReactionTime())
					powerLevel = level;
			}
			else {
				powerLevel = 0;
				ticksdetected = 0;
			}
		}
	}

	public int getReactionTime() { //with current numbers maxes to instant raction at 3200 rad/s
		int time = BASESPEED - (omega/SPEEDFACTOR);
		if (time < 1)
			time = 1;
		return time;
	}

	private int countPlayers(World world, int x, int y, int z) {
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		List inbox;
		//if (world.isRemote)
		//inbox = world.getEntitiesWithinAABB(EntityPlayerMP.class, box);
		//else
		inbox = world.getEntitiesWithinAABB(EntityPlayer.class, box);
		int count = inbox.size();
		if (count > 15)
			count = 15; //15 is max redstone
		return count;
	}

	private boolean checkForPlayers(World world, int x, int y, int z) {
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		List inbox;
		//if (world.isRemote)
		//inbox = world.getEntitiesWithinAABB(EntityPlayerMP.class, box);
		//else
		inbox = world.getEntitiesWithinAABB(EntityPlayer.class, box);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", inbox.size()));
		return (inbox.size() > 0);
	}

	public int getRange() {
		int range = (int)(power/FALLOFF);
		if (range > this.getMaxRange())
			range = this.getMaxRange();
		if (range > selectedrange)
			return selectedrange;
		return range;
	}

	public int getMaxRange() {
		int range = (int)(power/FALLOFF);
		int max = Math.max(64, ConfigRegistry.DETECTORRANGE.getValue());
		if (range > max)
			range = max;
		return range;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("range", selectedrange);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		selectedrange = NBT.getInteger("range");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PLAYERDETECTOR;
	}

	@Override
	public int getRedstoneOverride() {
		if (isActive)
			return 15;
		return 0;
	}
}
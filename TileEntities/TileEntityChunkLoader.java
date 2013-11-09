/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityChunkLoader extends TileEntityPowerReceiver implements LoadingCallback {

	public TileEntityChunkLoader() {
		ForgeChunkManager.setForcedChunkLoadingCallback(RotaryCraft.instance, this);
	}

	@Override
	public void onChunkUnload() {
		ReikaChatHelper.write("CHUNK UNLOADED AT:");
		ReikaChatHelper.writeCoords(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		Chunk ch = world.getChunkFromBlockCoords(x, z);
		Ticket tk = ForgeChunkManager.requestTicket(RotaryCraft.instance, world, ForgeChunkManager.Type.NORMAL);
		ChunkCoordIntPair chp = new ChunkCoordIntPair(ch.xPosition, ch.zPosition);
		ForgeChunkManager.forceChunk(tk, chp);
	}

	public void unLoad(World world, int x, int y, int z) {
		Chunk ch = world.getChunkFromBlockCoords(x, z);
		Ticket tk = ForgeChunkManager.requestTicket(RotaryCraft.class, world, ForgeChunkManager.Type.NORMAL);
		ChunkCoordIntPair chp = new ChunkCoordIntPair(ch.xPosition, ch.zPosition);
		ForgeChunkManager.unforceChunk(tk, chp);
	}

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {

	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.CHUNKLOADER.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}

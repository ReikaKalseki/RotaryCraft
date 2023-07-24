/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.init.Items;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import Reika.DragonAPI.Auxiliary.ChunkManager;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Interfaces.TileEntity.ChunkLoadingTile;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.EntityEnderFX;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityChunkLoader extends TileEntityPowerReceiver implements ChunkLoadingTile, BreakAction, RangedEffect {

	private boolean loaded;

	public static final int BASE_RADIUS = 0;
	public static final int FALLOFF = 524288;
	public static final int MAX_RADIUS = ConfigRegistry.CHUNKLOADERSIZE.getValue();

	public TileEntityChunkLoader() {

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		if (!world.isRemote) {
			if (omega >= MINSPEED) {
				this.load();
			}
			else {
				this.unload();
			}
		}
		else {
			if (omega >= MINSPEED) {
				this.doParticles(world, x, y, z);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void doParticles(World world, int x, int y, int z) {
		double[] r = {0.3125, 0.25, 0.1875, 0.125};
		double[] h = {0.25, 0.4375, 0.625, 0.8125};

		for (int i = 0; i < 4; i++) {
			double a = (4-i)*phi+40;
			double dr = r[i]+0.0625;
			double px = x+0.5+dr*Math.cos(Math.toRadians(a));
			double pz = z+0.5+dr*Math.sin(Math.toRadians(a));
			double py = y+h[i];

			double vy = 0.0625;
			float s = 0.375F;

			EntityFX fx = new EntityEnderFX(world, px, py, pz, 0, vy, 0, 0xffffff).setRapidExpand().setScale(s).setIcon(Items.nether_star.getIconFromDamage(0));
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);

			a += 180;
			px = x+0.5+dr*Math.cos(Math.toRadians(a));
			pz = z+0.5+dr*Math.sin(Math.toRadians(a));
			fx = new EntityEnderFX(world, px, py, pz, 0, vy, 0, 0xffffff).setRapidExpand().setScale(s);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}

	private void load() {
		if (loaded)
			return;
		loaded = true;
		ChunkManager.instance.loadChunks(this);
	}

	private void unload() {
		if (loaded) {
			loaded = false;
			ChunkManager.instance.unloadChunks(this);
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (omega > 0)
			phi -= 0.25F*ReikaMathLibrary.logbase(omega+1, 2);
	}

	@Override
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalid) {
		if (world.isRemote)
			return;
		if (invalid) {
			this.unload();
		}
	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.CHUNKLOADER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public Collection<ChunkCoordIntPair> getChunksToLoad() {
		return ChunkManager.getChunkSquare(xCoord, zCoord, this.getLoadingRadius());
	}

	private int getLoadingRadius() {
		return Math.min(MAX_RADIUS, BASE_RADIUS+(int)(power-MINSPEED)/FALLOFF);
	}

	@Override
	public void breakBlock() {
		if (!worldObj.isRemote)
			this.unload();
	}

	@Override
	public int getRange() {
		return this.getLoadingRadius()*16;
	}

	@Override
	public int getMaxRange() {
		return MAX_RADIUS*16;
	}

}

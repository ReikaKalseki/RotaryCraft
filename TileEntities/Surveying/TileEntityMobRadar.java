/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.RotaryCraft.API.RadarJammer;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMobRadar extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	public int[][] colors = new int[49][49]; // |<--- 24 ---- R ---- 24 --->|
	public int[][] mobs = new int[49][49];
	public List inzone;
	public String owner;

	public static final int FALLOFF = 1024; //1kW per extra meter

	public boolean hostile = true;
	public boolean animal = true;
	public boolean player = true;
	private boolean isJammed;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		this.getMobs(world, x, y, z);
	}

	public int getRange() {
		int range = (int)(8+(power-MINPOWER)/FALLOFF);
		if (range > 24)
			return 24;
		return range;
	}

	public boolean isJammed() {
		return isJammed;
	}

	public int[] getBounds() {
		int range = this.getRange();
		int[] bounds = {24-range, 24+range};
		return bounds;
	}

	public void getMobs(World world, int x, int y, int z) {
		colors = ReikaArrayHelper.fillMatrix(colors, 0);
		mobs = ReikaArrayHelper.fillMatrix(mobs, 0);
		isJammed = false;
		int range = this.getRange();
		AxisAlignedBB zone = AxisAlignedBB.getBoundingBox(x-range, 0, z-range, x+1+range, 255, z+1+range);
		inzone = world.getEntitiesWithinAABB(EntityLivingBase.class, zone);
		for (int i = 0; i < inzone.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)inzone.get(i);
			if (ent instanceof RadarJammer && ((RadarJammer)ent).jamRadar(worldObj, xCoord, yCoord, zCoord))
				isJammed = true;
			int ex = (int)ent.posX-x;
			int ey = (int)ent.posY-y;
			int ez = (int)ent.posZ-z;
			if (EntityList.getEntityID(ent) > 0 && Math.abs(ex) < 25 && Math.abs(ez) < 25 && ((ReikaEntityHelper.isHostile(ent) && hostile) || (!ReikaEntityHelper.isHostile(ent) && animal))) {
				colors[ex+24][ez+24] = ReikaEntityHelper.mobToColor(ent);
				mobs[ex+24][ez+24] = EntityList.getEntityID(ent);
			}
			else if (ent instanceof EntityPlayer && Math.abs(ex) < 25 && Math.abs(ez) < 25 && player) {
				colors[ex+24][ez+24] = ReikaEntityHelper.mobToColor(ent);
				mobs[ex+24][ez+24] = -1;
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		//this.phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(this.omega+1, 2), 1.05);
		phi += 4F;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		if (owner != null && !owner.isEmpty())
			NBT.setString("own", owner);
		NBT.setBoolean("jam", isJammed);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		owner = NBT.getString("own");
		isJammed = NBT.getBoolean("jam");
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.MOBRADAR;
	}

	@Override
	public int getMaxRange() {
		return 24;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.ConfigurableEntitySelector;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.RotaryCraft.API.RadarJammer;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMobRadar extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	private List<EntityLivingBase> inzone = new ArrayList();
	public String owner;

	public static final int FALLOFF = 1024; //1kW per extra meter

	public boolean hostile = true;
	public boolean animal = true;
	public boolean player = true;
	private boolean isJammed;

	private ConfigurableEntitySelector selector = new ConfigurableEntitySelector();

	public List<EntityLivingBase> getEntities() {
		return Collections.unmodifiableList(inzone);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		this.getMobs(world, x, y, z);
	}

	public int getRange() {
		int range = (int)(8+(power-MINPOWER)/FALLOFF);
		return Math.min(range, this.getMaxRange());
	}

	public boolean isJammed() {
		return isJammed;
	}

	public void getMobs(World world, int x, int y, int z) {
		isJammed = false;
		int range = this.getRange();
		AxisAlignedBB zone = AxisAlignedBB.getBoundingBox(x-range, 0, z-range, x+1+range, 255, z+1+range);
		inzone = world.selectEntitiesWithinAABB(EntityLivingBase.class, zone, this.getSelector());
		for (EntityLivingBase ent : inzone) {
			if (ent instanceof RadarJammer && ((RadarJammer)ent).jamRadar(worldObj, xCoord, yCoord, zCoord)) {
				isJammed = true;
				break;
			}
			int ex = (int)ent.posX-x;
			int ey = (int)ent.posY-y;
			int ez = (int)ent.posZ-z;
		}
	}

	private IEntitySelector getSelector() {
		selector.animals = animal;
		selector.players = player;
		selector.hostiles = hostile;
		return selector;
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
		return 256;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}

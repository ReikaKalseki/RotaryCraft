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

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelHarvester;

public class TileEntityMobHarvester extends TileEntityPowerReceiver {

	public String owner;
	public boolean laser;

	public List inbox;

    @Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
    	super.updateTileEntity();
		//this.tickcount++;
		this.getSummativeSidedPower();
		EntityPlayer ep = null;
		if (owner != null)
			ep = world.getPlayerEntityByName(owner);
		if (ep == null)
			ep = world.getClosestPlayer(x, y, z, -1);
		//if (this.tickcount < 5)
			//return;
		//this.tickcount = 0;
		AxisAlignedBB box = this.getBox();
		inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLiving ent = (EntityLiving)inbox.get(i);
			if (!(ent instanceof EntityPlayer || ent instanceof EntityVillager)) {
				//this.laser = true;
				world.markBlockForRenderUpdate(x, y, z);
				if (ep != null && this.getDamage() > 0)
					ent.attackEntityFrom(DamageSource.causePlayerDamage(ep), this.getDamage());
				ent.motionY = 0;
			}
		}
		if (inbox.size() == 0 && !(inbox.size() == 1 && (inbox.get(0) instanceof EntityPlayer || inbox.get(0) instanceof EntityVillager)))
			laser = false;
	}

	public int getDamage() {
		int pdiff = 2+(int)(power/MINPOWER);
		int ppdiff = (int)ReikaMathLibrary.intpow(pdiff, 6);
		return (int)ReikaMathLibrary.logbase(ppdiff, 2);
	}

	public AxisAlignedBB getBox() {
		//return AxisAlignedBB.getBoundingBox(this.xCoord-4, this.yCoord-4, this.zCoord-4, this.xCoord+5, this.yCoord+5, this.zCoord+5);
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord+1, zCoord, xCoord+1, yCoord+2, zCoord+1);
	}

	public AxisAlignedBB getLaser() {
		return AxisAlignedBB.getBoundingBox(xCoord+0.4, yCoord+1, zCoord+0.4, xCoord+0.6, yCoord+3, zCoord+0.6);
	}

    @Override
	public void writeToNBT(NBTTagCompound NBT)
    {
        super.writeToNBT(NBT);
        if (owner != null && !owner.isEmpty())
        	NBT.setString("sowner", owner);
    }

    @Override
	public void readFromNBT(NBTTagCompound NBT)
    {
        super.readFromNBT(NBT);
        owner = NBT.getString("sowner");
    }

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelHarvester();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.MOBHARVESTER.ordinal();
	}
}

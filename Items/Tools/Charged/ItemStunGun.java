/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

public class ItemStunGun extends ItemChargedTool {

	public ItemStunGun(int tex) {
		super(tex);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (ep.isSneaking()) {
			return is;
		}
		if (is.getItemDamage() <= 0) {
			this.noCharge();
			return is;
		}
		this.warnCharge(is);
		//if (!world.isRemote) {
		double[] part = ReikaVectorHelper.getPlayerLookCoords(ep, 1);
		for (int i = 0; i < 12; i++)
			world.spawnParticle("magicCrit", part[0]-0.3+0.6*par5Random.nextFloat(), part[1]-0.3+0.6*par5Random.nextFloat(), part[2]-0.3+0.6*par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
		//}
		Vec3 norm = ep.getLookVec();
		SoundRegistry.KNOCKBACK.playSound(world, ep.posX+norm.xCoord, ep.posY+norm.yCoord, ep.posZ+norm.zCoord, 2, 2F);
		for (float i = 1; i <= 5; i += 0.5) {
			double[] look = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			//ReikaChatHelper.writeString(String.format("%.3f", look.xCoord)+" "+String.format("%.3f", look.yCoord)+" "+String.format("%.3f", look.zCoord));
			AxisAlignedBB fov = AxisAlignedBB.getBoundingBox(look[0]-0.5, look[1]-0.5, look[2]-0.5, look[0]+0.5, look[1]+0.5, look[2]+0.5);
			List infov = world.getEntitiesWithinAABB(EntityLivingBase.class, fov);
			for (int k = 0; k < infov.size(); k++) {
				EntityLivingBase ent = (EntityLivingBase)infov.get(k);
				if (!(ent instanceof EntityPlayer) && ep.canEntityBeSeen(ent)) {
					for (int f = 0; i < 64; i++)
						world.spawnParticle("magicCrit", ent.posX-0.5+par5Random.nextFloat(), ent.posY-0.5+par5Random.nextFloat(), ent.posZ-0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaEntityHelper.knockbackEntity(ep, ent, 2);
				}
			}
			if (infov.size() > 0 && !(infov.size() == 1 && infov.get(0) instanceof EntityPlayer))
				return new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-1);
		}
		return new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-1);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float a, float b, float c) {
		if (!ep.isSneaking())
			return false;
		if (is.getItemDamage() <= 0)
			return false;
		if (is.getItemDamage() < 8192 && !ep.capabilities.isCreativeMode)
			return false;
		if (!world.isRemote) {
			MovingObjectPosition mov = new MovingObjectPosition(x, y, z, side, ep.getLookVec());
			//ReikaChatHelper.write(mov);
			//ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			Block id = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			Material mat = ReikaWorldHelper.getMaterial(world, x, y, z);
			if (id != Blocks.air && (id instanceof BlockLiquid || id instanceof BlockFluidBase) && (id == Blocks.web || id == Blocks.red_mushroom ||
					id == Blocks.gravel ||  id == Blocks.monster_egg  || id == Blocks.brown_mushroom ||
					id == Blocks.waterlily || id == Blocks.flower_pot ||
					ReikaBlockHelper.isOre(id, meta) || (ReikaWorldHelper.softBlocks(id) && id != Blocks.snow))) {
				for (int k = 0; k < 64; k++)
					world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
				ReikaWorldHelper.recursiveBreak(world, x, y, z, id, -1);
				ep.setCurrentItemOrArmor(0, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-2));
			}
			int leafrange = 4;
			if (mat == Material.glass || mat == Material.ice || mat == Material.leaves || id == Blocks.sand || id == Blocks.snow || id == Blocks.ice) {
				for (int k = 0; k < 64; k++)
					world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
				ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y, z, id, -1, x, y, z, leafrange);
				ep.setCurrentItemOrArmor(0, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-2));
			}
		}
		double[] part = ReikaVectorHelper.getPlayerLookCoords(ep, 1);
		for (int i = 0; i < 12; i++)
			world.spawnParticle("magicCrit", part[0]-0.3+0.6*par5Random.nextFloat(), part[1]-0.3+0.6*par5Random.nextFloat(), part[2]-0.3+0.6*par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
		//}
		Vec3 norm = ep.getLookVec();
		SoundRegistry.KNOCKBACK.playSound(world, ep.posX+norm.xCoord, ep.posY+norm.yCoord, ep.posZ+norm.zCoord, 2, 2F);
		return true;
	}

}
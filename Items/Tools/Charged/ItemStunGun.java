/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

import Reika.ChromatiCraft.API.Interfaces.EnchantableItem;
import Reika.DragonAPI.Instantiable.Data.Immutable.DecimalPosition;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.SoundRegistry;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemStunGun extends ItemChargedTool implements EnchantableItem {

	public ItemStunGun(int tex) {
		super(tex);
	}

	@Override
	public int getItemEnchantability() {
		return Items.iron_sword.getItemEnchantability();
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
		DecimalPosition part = ReikaVectorHelper.getPlayerLookCoords(ep, 1);
		for (int i = 0; i < 12; i++) {
			double px = ReikaRandomHelper.getRandomPlusMinus(part.xCoord, 0.3);
			double py = ReikaRandomHelper.getRandomPlusMinus(part.yCoord, 0.3);
			double pz = ReikaRandomHelper.getRandomPlusMinus(part.zCoord, 0.3);
			double vx = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			double vy = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			double vz = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			ReikaParticleHelper.ENCHANTMENT.spawnAt(world, px, py, pz, vx, vy, vz);
		}
		//}
		Vec3 norm = ep.getLookVec();
		SoundRegistry.KNOCKBACK.playSound(world, ep.posX+norm.xCoord, ep.posY+norm.yCoord, ep.posZ+norm.zCoord, 2, 2F);
		for (float i = 1; i <= 5; i += 0.5) {
			DecimalPosition look = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			//ReikaChatHelper.writeString(String.format("%.3f", look.xCoord)+" "+String.format("%.3f", look.yCoord)+" "+String.format("%.3f", look.zCoord));
			AxisAlignedBB fov = look.getAABB(0.5);
			List infov = world.getEntitiesWithinAABB(EntityLivingBase.class, fov);
			for (int k = 0; k < infov.size(); k++) {
				EntityLivingBase ent = (EntityLivingBase)infov.get(k);
				if (!(ent instanceof EntityPlayer) && ep.canEntityBeSeen(ent)) {
					for (int f = 0; i < 64; i++)
						world.spawnParticle("magicCrit", ent.posX-0.5+par5Random.nextFloat(), ent.posY-0.5+par5Random.nextFloat(), ent.posZ-0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaEntityHelper.knockbackEntity(ep, ent, 2*(1+ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.knockback, is)*0.25));
				}
			}
			if (infov.size() > 0 && !(infov.size() == 1 && infov.get(0) instanceof EntityPlayer))
				return new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-1);
		}
		is = is.copy();
		is.setItemDamage(is.getItemDamage()-1);
		return is;
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
			//ReikaChatHelper.write(mov);
			//ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			Block id = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			Material mat = ReikaWorldHelper.getMaterial(world, x, y, z);
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			if (id != Blocks.air && !(id instanceof BlockLiquid || id instanceof BlockFluidBase) && (id == Blocks.web || id == Blocks.red_mushroom ||
					id == Blocks.gravel ||  id == Blocks.monster_egg  || id == Blocks.brown_mushroom ||
					id == Blocks.waterlily || id == Blocks.flower_pot ||
					ReikaBlockHelper.isOre(id, meta) || (ReikaWorldHelper.softBlocks(world, x, y, z) && id != Blocks.snow))) {
				for (int k = 0; k < 64; k++)
					world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
				ReikaWorldHelper.recursiveBreak(world, x, y, z, id, -1, fortune);
				ep.setCurrentItemOrArmor(0, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-2));
			}
			int leafrange = 4;
			if (mat == Material.glass || mat == Material.ice || mat == Material.leaves || id == Blocks.sand || id == Blocks.snow || id == Blocks.ice) {
				for (int k = 0; k < 64; k++)
					world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
				ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y, z, id, -1, x, y, z, leafrange, fortune);
				ep.setCurrentItemOrArmor(0, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-2));
			}
		}
		DecimalPosition part = ReikaVectorHelper.getPlayerLookCoords(ep, 1);
		for (int i = 0; i < 12; i++) {
			double px = ReikaRandomHelper.getRandomPlusMinus(part.xCoord, 0.3);
			double py = ReikaRandomHelper.getRandomPlusMinus(part.yCoord, 0.3);
			double pz = ReikaRandomHelper.getRandomPlusMinus(part.zCoord, 0.3);
			double vx = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			double vy = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			double vz = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			ReikaParticleHelper.ENCHANTMENT.spawnAt(world, px, py, pz, vx, vy, vz);
		}
		//}
		Vec3 norm = ep.getLookVec();
		SoundRegistry.KNOCKBACK.playSound(world, ep.posX+norm.xCoord, ep.posY+norm.yCoord, ep.posZ+norm.zCoord, 2, 2F);
		return true;
	}

	@Override
	public Result getEnchantValidity(Enchantment e, ItemStack is) {
		return e == Enchantment.fortune || e == Enchantment.knockback ? Result.ALLOW : Result.DENY;
	}

	@Override
	public EnumEnchantmentType getEnchantingCategory() {
		return null;
	}

}

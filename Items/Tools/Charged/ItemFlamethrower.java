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

import Reika.RotaryCraft.Base.ItemChargedTool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemFlamethrower extends ItemChargedTool {

	public ItemFlamethrower(int index) {
		super(index);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		ep.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public void onUsingTick(ItemStack is, EntityPlayer ep, int count) {
		Vec3 vec = ep.getLookVec();
		ep.worldObj.spawnParticle("flame", ep.posX, ep.posY, ep.posZ, vec.xCoord/4, vec.yCoord/4, vec.zCoord/4);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 72000;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer ep)
	{
		return is;
	}
}
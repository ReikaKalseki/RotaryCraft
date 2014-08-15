/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAdvGear extends BlockModelledMachine {

	public BlockAdvGear(Material mat) {
		super(mat);
		////this.blockIndexInTexture = 8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs xCreativeTabs, List yList) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < 12; ++var4)
			if (var4%4 == 0)
				yList.add(new ItemStack(par1, 1, var4));
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityAdvancedGear();
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harv)
	{
		if (this.canHarvest(world, player, x, y, z));
		this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlockToAir(x, y, z);
	}

	private boolean canHarvest(World world, EntityPlayer ep, int x, int y, int z) {
		return RotaryAux.canHarvestSteelMachine(ep);
	}

	@Override
	public final void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta)
	{
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntityAdvancedGear te = (TileEntityAdvancedGear)world.getTileEntity(x, y, z);
		if (te != null) {
			ItemStack is = MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(te.getBlockMetadata()/4);
			if (te.getGearType().storesEnergy()) {
				long e = te.getEnergy();
				if (is.stackTagCompound == null)
					is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setLong("energy", e);
				is.stackTagCompound.setBoolean("bedrock", te.isBedrockCoil());
			}
			if (te.getGearType().isLubricated()) {
				int lube = te.getLubricant();
				if (is.stackTagCompound == null)
					is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setInteger("lube", lube);
			}
			ReikaItemHelper.dropItem(world, x+par5Random.nextDouble(), y+par5Random.nextDouble(), z+par5Random.nextDouble(), is);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		this.setFullBlockBounds();
		if (iba.getBlockMetadata(x, y, z) >= 8)
			maxY = 0.875;
	}

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntityAdvancedGear adv = (TileEntityAdvancedGear)world.getTileEntity(x, y, z);
		ItemStack is = ItemRegistry.ADVGEAR.getStackOfMetadata(adv.getBlockMetadata()/4);
		if (adv.getGearType().storesEnergy()) {
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setLong("energy", adv.getEnergy());
			is.stackTagCompound.setBoolean("bedrock", adv.isBedrockCoil());
		}
		ret.add(is);
		return ret;
	}
}
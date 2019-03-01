/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.Data.Immutable.BlockBounds;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasic;

public class BlockBedrockSlice extends BlockBasic
{
	private boolean last = false;
	public static IIcon icon;

	public BlockBedrockSlice()
	{
		super(Material.rock);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setBlockUnbreakable();
		this.setResistance(3600000F);
	}

	@Override
	protected boolean isAvailableInCreativeMode() {
		return false;
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity e)
	{
		return false;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		/*
		int var5 = world.getBlockMetadata(x, y, z);
		if (var5 != 0)
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + (15-var5)/16F, z + maxZ);
		else
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		 */
		return ((TileEntityBedrockSlice)world.getTileEntity(x, y, z)).getBounds().asAABB(x, y, z);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		/*
		int var5 = iba.getBlockMetadata(x, y, z);
		float var6 = 1F-(1 * (var5)) / 16.0F; //Get thinner each damageval++
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
		 */
		((TileEntityBedrockSlice)iba.getTileEntity(x, y, z)).getBounds().copyToBlock(this);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		Block var5 = world.getBlock(x, y - 1, z);
		return var5 != Blocks.air && (var5 == Blocks.leaves || var5 == Blocks.leaves2 || var5.isOpaqueCube()) ? ReikaWorldHelper.getMaterial(world, x, y - 1, z).blocksMovement() : false;
	}

	@Override
	public Item getItemDropped(int par1, Random xRandom, int y) {
		return null;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int par5) {
		return super.shouldSideBeRendered(iba, x, y, z, par5);//par5 == 1 ? true : super.shouldSideBeRendered(iba, x, y, z, par5);
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		icon = par1IconRegister.registerIcon("bedrock");
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 0;
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileEntityBedrockSlice();
	}

	public static class TileEntityBedrockSlice extends TileEntity {

		public float dustYield = 1;
		private ForgeDirection machineDirection = ForgeDirection.EAST;

		@Override
		public boolean canUpdate() {
			return false;
		}

		private BlockBounds getBounds() {
			return BlockBounds.block().cut(machineDirection, this.getBlockMetadata()/16D);
		}

		@Override
		public void readFromNBT(NBTTagCompound NBT) {
			super.readFromNBT(NBT);
			dustYield = NBT.getFloat("yield");
			machineDirection = ForgeDirection.VALID_DIRECTIONS[NBT.getInteger("dir")];
		}

		@Override
		public void writeToNBT(NBTTagCompound NBT) {
			super.writeToNBT(NBT);
			NBT.setFloat("yield", dustYield);
			NBT.setInteger("dir", machineDirection.ordinal());
		}

		@Override
		public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
			return oldBlock != newBlock;
		}

		public void setDirection(ForgeDirection dir) {
			machineDirection = dir;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound NBT = new NBTTagCompound();
			this.writeToNBT(NBT);
			S35PacketUpdateTileEntity pack = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, NBT);
			return pack;
		}

		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity p)  {
			this.readFromNBT(p.field_148860_e);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

	}
}

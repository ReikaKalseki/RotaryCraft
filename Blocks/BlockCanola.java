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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcp.mobius.waila.api.IWailaBlock;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.BlowableCrop;
import Reika.RotaryCraft.Base.BlockBasic;
import Reika.RotaryCraft.Registry.ItemRegistry;

public final class BlockCanola extends BlockBasic implements IPlantable, BlowableCrop, IWailaBlock {

	private final Random rand = new Random();

	private static final ArrayList<Integer> farmBlocks = new ArrayList();

	public static final int GROWN = 9;

	public BlockCanola(int ID) {
		super(ID, Material.plants);
		this.setHardness(0F);
		this.setResistance(0F);
		this.setLightValue(0F);
		this.setStepSound(soundGrassFootstep);
		this.setTickRandomly(true);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}

	@Override
	public final ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return ItemRegistry.CANOLA.getStackOf();
	}

	@Override
	public boolean isBlockFoliage(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(this.getDrops(metadata));
		return ret;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		if (par2 < 0)
		{
			par2 = 9;
		}
		if (par2 > 2)
			par2 += 2;
		if (par2 > 11)
			par2 = 11;

		return 0;//this.blockIndexInTexture + par2;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random par5Random) {
		if ((world.getBlockLightValue(x, y, z) < GROWN && !world.canBlockSeeTheSky(x, y, z)) || world.getBlockId(x, y-1, z) != Block.tilledField.blockID) {
			this.die(world, x, y, z);
		}
		else if (world.getBlockLightValue(x, y, z) >= 9)  {
			int metadata = world.getBlockMetadata(x, y, z);
			if (metadata < GROWN && world.getBlockMetadata(x, y-1, z) > 0) {
				if (par5Random.nextInt(3) == 0) {
					metadata++;
					world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
				}
			}
		}
	}

	public void die(World world, int x, int y, int z) {
		world.setBlock(x, y, z, 0);
		ReikaItemHelper.dropItem(world, x, y, z, ItemRegistry.CANOLA.getStackOf());
	}

	public ItemStack getDrops(int metadata) {
		int ndrops = 2+rand.nextInt(8)+rand.nextInt(5);
		if (metadata < GROWN)
			ndrops = 1;
		ItemStack items = ItemRegistry.CANOLA.getCraftedProduct(ndrops);
		return items;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par5, float f1, float f2, float f3) {
		if (par5EntityPlayer.getCurrentEquippedItem() != null) {
			if (par5EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemDye && par5EntityPlayer.getCurrentEquippedItem().getItemDamage() == 15) {
				if (world.getBlockMetadata(x, y, z) < GROWN) {
					world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z)+1, 3);
					for (int i = 0; i < 16; i++)
						world.spawnParticle("happyVillager", x+rand.nextDouble(), y+rand.nextDouble(), z+rand.nextDouble(), 0, 0, 0);
					if (!par5EntityPlayer.capabilities.isCreativeMode)
						par5EntityPlayer.getCurrentEquippedItem().stackSize--;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		this.testStable(world, x, y, z);
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int changedid) {
		this.testStable(world, x, y, z);
	}

	public void testStable(World world, int x, int y, int z) {
		int idbelow = world.getBlockId(x, y-1, z);
		if ((world.getBlockLightValue(x, y, z) < 9 && !world.canBlockSeeTheSky(x, y, z)) || !this.isValidFarmBlock(world, x, y, z, idbelow)) {
			this.die(world, x, y, z);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, meta/(float)GROWN, z + maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float var6 = var5/(float)GROWN;
		if (var6 < 0.125F)
			var6 = 0.125F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 6;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int damageDropped(int par1)
	{
		return 0;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[meta][s];
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int j = 0; j <= GROWN; j++) {
			for (int i = 0; i < 6; i++) {
				icons[j][i] = par1IconRegister.registerIcon("RotaryCraft:canola"+String.valueOf(j));
			}
		}
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z) {
		return blockID;
	}

	/** What is this <u>for?</u> Nothing calls it... */
	@Override
	public int getPlantMetadata(World world, int x, int y, int z) {
		return GROWN;
	}

	public static boolean isValidFarmBlock(World world, int x, int y, int z, int id) {
		if (id == Block.tilledField.blockID)
			return true;
		if (id == 0)
			return false;
		Block b = Block.blocksList[id];
		if (b == null)
			return false;
		return b.isFertile(world, x, y, z);
		//return farmBlocks.contains(id);
	}

	public static void addFarmBlock(int id) {
		if (!farmBlocks.contains(id))
			farmBlocks.add(id);
	}

	@Override
	public boolean isReadyToHarvest(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		return id == blockID && meta == GROWN;
	}

	@Override
	public void setPostHarvest(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(x, y, z, 0, 3);
	}

	@Override
	public ArrayList<ItemStack> getHarvestProducts(World world, int x, int y, int z) {
		ArrayList<ItemStack> li = new ArrayList();
		li.add(this.getDrops(world.getBlockMetadata(x, y, z)));
		return li;
	}

	@Override
	public float getHarvestingSpeed() {
		return 2F;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		return ItemRegistry.CANOLA.getStackOf();
	}

	@Override
	public List<String> getWailaHead(ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		currenttip.add(EnumChatFormatting.WHITE+"Canola Plant");
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		int meta = acc.getMetadata();
		currenttip.add(String.format("Growth Stage: %d%s", 100*meta/GROWN, "%"));
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		String s1 = EnumChatFormatting.ITALIC.toString();
		String s2 = EnumChatFormatting.BLUE.toString();
		currenttip.add(s2+s1+"RotaryCraft");
		return currenttip;
	}
}

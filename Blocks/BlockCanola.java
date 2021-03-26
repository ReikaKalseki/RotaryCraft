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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Event.GrassSustainCropEvent;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.LegacyCraft.LegacyOptions;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockBasic;
import Reika.RotaryCraft.Registry.BlockRegistry;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@Strippable(value = {"mcp.mobius.waila.api.IWailaDataProvider"})
public final class BlockCanola extends BlockBasic implements IPlantable, IGrowable, IWailaDataProvider {

	private final Random rand = new Random();

	private static final HashSet<BlockKey> farmBlocks = new HashSet();

	public static final int GROWN = 9;

	public BlockCanola() {
		super(Material.plants);
		this.setHardness(0F);
		this.setResistance(0F);
		this.setLightLevel(0F);
		this.setStepSound(soundTypeGrass);
		this.setTickRandomly(true);
	}

	@Override
	protected boolean isAvailableInCreativeMode() {
		return false;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;
	}

	@Override
	public final ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return ItemStacks.canolaSeeds;
	}

	@Override
	public boolean isFoliage(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(this.getDrops(metadata, fortune, rand));
		return ret;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
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
	public void updateTick(World world, int x, int y, int z, Random r) {
		if (!this.canSurvive(world, x, y, z)) {
			this.die(world, x, y, z);
		}
		else if (canGrowAt(world, x, y, z))  {
			int metadata = world.getBlockMetadata(x, y, z);
			if (metadata < GROWN) {
				if (r.nextInt(3) == 0) {
					metadata++;
					world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
				}
			}
		}
	}

	public void die(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		ReikaItemHelper.dropItem(world, x, y, z, ItemStacks.canolaSeeds);
	}

	/*
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int s, float f1, float f2, float f3) {
		ItemStack is = ep.getCurrentEquippedItem();
		if (!world.isRemote && ReikaItemHelper.matchStacks(is, ReikaItemHelper.bonemeal)) {
			if (world.getBlockMetadata(x, y, z) < GROWN) {
				int to = Math.min(GROWN, world.getBlockMetadata(x, y, z)+1+rand.nextInt(4));
				if (ModList.LEGACYCRAFT.isLoaded() && LegacyOptions.BONEMEAL.getState())
					to = GROWN;
				world.setBlockMetadataWithNotify(x, y, z, to, 3);
				for (int i = 0; i < 16; i++)
					world.spawnParticle("happyVillager", x+rand.nextDouble(), y+rand.nextDouble(), z+rand.nextDouble(), 0, 0, 0);
				if (!ep.capabilities.isCreativeMode)
					ep.getCurrentEquippedItem().stackSize--;
				return true;
			}
		}
		return false;
	}
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		this.testStable(world, x, y, z);
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block changedid) {
		this.testStable(world, x, y, z);
	}

	public void testStable(World world, int x, int y, int z) {
		if (!canSurvive(world, x, y, z)) {
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
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, getPlantHeight(iba.getBlockMetadata(x, y, z)), 1.0F);
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
	public IIcon getIcon(int s, int meta) {
		return icons[meta][s];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int j = 0; j <= GROWN; j++) {
			for (int i = 0; i < 6; i++) {
				icons[j][i] = par1IconRegister.registerIcon("RotaryCraft:canola/"+j);
			}
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return this;
	}

	/** What is this <u>for?</u> Nothing calls it... */
	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return GROWN;
	}
	/*
	@Override
	public boolean isReadyToHarvest(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		return b == this && meta == GROWN;
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
	}*/

	@Override
	@ModDependent(ModList.WAILA)
	public ItemStack getWailaStack(IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		return null;//ItemStacks.canolaSeeds;
	}

	@Override
	@ModDependent(ModList.WAILA)
	public List<String> getWailaHead(ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		//currenttip.add(EnumChatFormatting.WHITE+"Canola Plant");
		return currenttip;
	}

	@Override
	@ModDependent(ModList.WAILA)
	public List<String> getWailaBody(ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		if (/*LegacyWailaHelper.cacheAndReturn(acc)*/!currenttip.isEmpty())
			return currenttip;
		int meta = acc.getMetadata();
		currenttip.add(String.format("Growth Stage: %d%s", 100*meta/GROWN, "%"));
		return currenttip;
	}

	@Override
	@ModDependent(ModList.WAILA)
	public List<String> getWailaTail(ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg) {
		/*
		String s1 = EnumChatFormatting.ITALIC.toString();
		String s2 = EnumChatFormatting.BLUE.toString();
		currenttip.add(s2+s1+"RotaryCraft");*/
		return currenttip;
	}

	@Override
	@ModDependent(ModList.WAILA)
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		return tag;
	}

	@Override //Can apply
	public boolean func_149851_a(World world, int x, int y, int z, boolean client) {
		return world.getBlockMetadata(x, y, z) < GROWN;
	}

	@Override //shouldTryGrow
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		return true;
	}

	@Override //tryGrow
	public void func_149853_b(World world, Random rand, int x, int y, int z) {
		int to = Math.min(GROWN, world.getBlockMetadata(x, y, z)+1+rand.nextInt(4));
		if (ModList.LEGACYCRAFT.isLoaded() && LegacyOptions.BONEMEAL.getState())
			to = GROWN;
		world.setBlockMetadataWithNotify(x, y, z, to, 3);
		world.spawnParticle("happyVillager", x+rand.nextDouble(), y+rand.nextDouble(), z+rand.nextDouble(), 0, 0, 0);
	}

	public static boolean canSurvive(World world, int x, int y, int z) {
		return (world.getBlockLightValue(x, y, z) >= 6 || world.canBlockSeeTheSky(x, y, z)) && isValidFarmBlock(world, x, y-1, z);
	}

	public static boolean canGrowAt(World world, int x, int y, int z) {
		return world.getBlockLightValue(x, y, z) >= 9 && isValidFarmBlock(world, x, y-1, z) && !isBlockedUpwards(world, x, y, z);
	}

	private static boolean isBlockedUpwards(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y+1, z);
		return !(b.isOpaqueCube() && b.renderAsNormalBlock() && b.getLightOpacity(world, x, y+1, z) > 25) && b.getMaterial().isSolid();
	}

	public static boolean isValidFarmBlock(World world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		if (id == Blocks.air)
			return false;
		if (id instanceof BlockFarmland) {
			return world.getBlockMetadata(x, y, z) > 0;
		}
		if (id == Blocks.grass) {
			GrassSustainCropEvent evt = new GrassSustainCropEvent(world, x, y, z, ForgeDirection.UP, (IPlantable)BlockRegistry.CANOLA.getBlockInstance());
			MinecraftForge.EVENT_BUS.post(evt);
			return evt.getResult() == Result.ALLOW;
		}
		return id.isFertile(world, x, y, z) && id.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable)BlockRegistry.CANOLA.getBlockInstance());
		//return farmBlocks.contains(id);
	}

	public static ItemStack getDrops(int metadata, int fortune, Random rand) {
		int ndrops = 1;
		if (metadata == GROWN) {
			ndrops = getDrops(fortune, rand);
		}
		ItemStack items = ReikaItemHelper.getSizedItemStack(ItemStacks.canolaSeeds, ndrops);
		return items;
	}

	public static int getDrops(int fortune, Random rand) {
		int ndrops = Math.max(fortune*2, (1+rand.nextInt(2))*(2+rand.nextInt(8)+rand.nextInt(5)));
		if (fortune > 0) {
			ndrops = Math.max(ndrops, (int)(ndrops*rand.nextDouble()*(1+fortune)));
		}
		return ndrops;
	}

	public static float getPlantHeight(int meta) {
		return Math.max(0.125F, meta/(float)GROWN);
	}
}

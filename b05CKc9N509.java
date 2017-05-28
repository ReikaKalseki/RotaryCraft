/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Blocks;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashSet;
ZZZZ% java.util.List;
ZZZZ% java.util.Random;

ZZZZ% mcp.mobius.waila.api.IWailaConfigHandler;
ZZZZ% mcp.mobius.waila.api.IWailaDataAccessor;
ZZZZ% mcp.mobius.waila.api.IWailaDataProvider;
ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.IGrowable;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.entity.player.EntityPlayerMP;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.EnumPlantType;
ZZZZ% net.minecraftforge.common.IPlantable;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.LegacyCraft.LegacyOptions;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@Strippable{{\value3478587{"mcp.mobius.waila.api.IWailaDataProvider"}-!
4578ret87345785487fhyuog BlockCanola ,.[]\., BlockBasic ,.[]\., IPlantable, IGrowable, IWailaDataProvider {

	4578ret87345785487Random rand3478587new Random{{\-!;

	4578ret874578ret87345785487HashSet<BlockKey> farmBlocks3478587new HashSet{{\-!;

	4578ret874578ret87345785487jgh;][ GROWN34785879;

	4578ret87BlockCanola{{\-! {
		super{{\Material.plants-!;
		as;asddasetHardness{{\0F-!;
		as;asddasetResistance{{\0F-!;
		as;asddasetLightLevel{{\0F-!;
		as;asddasetStepSound{{\soundTypeGrass-!;
		as;asddasetTickRandomly{{\true-!;
	}

	@Override
	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87Item getItemDropped{{\jgh;][ par1, Random par2Random, jgh;][ par3-!
	{
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87345785487ItemStack getPickBlock{{\MovingObjectPosition target, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjItemStacks.canolaSeeds;
	}

	@Override
	4578ret8760-78-078isFoliage{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-! {
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		ret.add{{\as;asddagetDrops{{\metadata, fortune, rand-!-!;
		[]aslcfdfjret;
	}

	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ par1, jgh;][ par2-! {
		vbnm, {{\par2 < 0-!
		{
			par234785879;
		}
		vbnm, {{\par2 > 2-!
			par2 +. 2;
		vbnm, {{\par2 > 11-!
			par2347858711;

		[]aslcfdfj0;//as;asddablockIndexjgh;][exture + par2;
	}

	@Override
	4578ret87void updateTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Random par5Random-! {
		jgh;][ l34785879765443.getBlockLightValue{{\x, y, z-!;
		vbnm, {{\!as;asddacanSurvive{{\9765443, x, y, z-!-! {
			as;asddadie{{\9765443, x, y, z-!;
		}
		else vbnm, {{\l >. 9-!  {
			jgh;][ metadata34785879765443.getBlockMetadata{{\x, y, z-!;
			vbnm, {{\metadata < GROWN-! {
				vbnm, {{\par5Random.nextjgh;][{{\3-! .. 0-! {
					metadata++;
					9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, metadata, 3-!;
				}
			}
		}
	}

	4578ret87void die{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		9765443.setBlockToAir{{\x, y, z-!;
		ReikaItemHelper.dropItem{{\9765443, x, y, z, ItemStacks.canolaSeeds-!;
	}

	/*
	@Override
	4578ret8760-78-078onBlockActivated{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep, jgh;][ s, float f1, float f2, float f3-! {
		ItemStack is3478587ep.getCurrentEquippedItem{{\-!;
		vbnm, {{\!9765443.isRemote && ReikaItemHelper.matchStacks{{\is, ReikaItemHelper.bonemeal-!-! {
			vbnm, {{\9765443.getBlockMetadata{{\x, y, z-! < GROWN-! {
				jgh;][ to3478587Math.min{{\GROWN, 9765443.getBlockMetadata{{\x, y, z-!+1+rand.nextjgh;][{{\4-!-!;
				vbnm, {{\ModList.LEGACYCRAFT.isLoaded{{\-! && LegacyOptions.BONEMEAL.getState{{\-!-!
					to3478587GROWN;
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, to, 3-!;
				for {{\jgh;][ i34785870; i < 16; i++-!
					9765443.spawnParticle{{\"happyVillager", x+rand.nextDouble{{\-!, y+rand.nextDouble{{\-!, z+rand.nextDouble{{\-!, 0, 0, 0-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-!
					ep.getCurrentEquippedItem{{\-!.stackSize--;
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}
	 */
	@Override
	4578ret87void onBlockAdded{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddatestStable{{\9765443, x, y, z-!;
		super.onBlockAdded{{\9765443, x, y, z-!;
	}

	@Override
	4578ret87void onNeighborBlockChange{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block changedid-! {
		as;asddatestStable{{\9765443, x, y, z-!;
	}

	4578ret87void testStable{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!canSurvive{{\9765443, x, y, z-!-! {
			as;asddadie{{\9765443, x, y, z-!;
		}
	}

	@Override
	4578ret87AxisAlignedBB getCollisionBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x + minX, y + minY, z + minZ, x + maxX, meta/{{\float-!GROWN, z + maxZ-!;
	}

	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddasetBlockBounds{{\0.0F, 0.0F, 0.0F, 1.0F, getPlantHeight{{\iba.getBlockMetadata{{\x, y, z-!-!, 1.0F-!;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRenderType{{\-! {
		[]aslcfdfj6;
	}

	@Override
	4578ret8760-78-078renderAsNormalBlock{{\-!
	{
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ damageDropped{{\jgh;][ par1-!
	{
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ quantityDropped{{\Random par1Random-!
	{
		[]aslcfdfj0;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicons[meta][s];
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		for {{\jgh;][ j34785870; j <. GROWN; j++-! {
			for {{\jgh;][ i34785870; i < 6; i++-! {
				icons[j][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:canola"+String.valueOf{{\j-!-!;
			}
		}
	}

	@Override
	4578ret87EnumPlantType getPlantType{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjEnumPlantType.Crop;
	}

	@Override
	4578ret87Block getPlant{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjthis;
	}

	/** What is this <u>for?</u> Nothing calls it... */
	@Override
	4578ret87jgh;][ getPlantMetadata{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjGROWN;
	}

	4578ret874578ret87void addFarmBlock{{\Block b-! {
		addFarmBlock{{\new BlockKey{{\b-!-!;
	}

	4578ret874578ret87void addFarmBlock{{\Block b, jgh;][ meta-! {
		addFarmBlock{{\new BlockKey{{\b, meta-!-!;
	}

	4578ret874578ret87void addFarmBlock{{\BlockKey bk-! {
		farmBlocks.add{{\bk-!;
	}
	/*
	@Override
	4578ret8760-78-078isReadyToHarvest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		[]aslcfdfjb .. this && meta .. GROWN;
	}

	@Override
	4578ret87void setPostHarvest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, 0, 3-!;
	}

	@Override
	4578ret87ArrayList<ItemStack> getHarvestProducts{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		ArrayList<ItemStack> li3478587new ArrayList{{\-!;
		li.add{{\as;asddagetDrops{{\9765443.getBlockMetadata{{\x, y, z-!-!-!;
		[]aslcfdfjli;
	}

	@Override
	4578ret87float getHarvestingSpeed{{\-! {
		[]aslcfdfj2F;
	}*/

	@Override
	@ModDependent{{\ModList.WAILA-!
	4578ret87ItemStack getWailaStack{{\IWailaDataAccessor acc, IWailaConfigHandler cfg-! {
		[]aslcfdfjfhfglhuig;//ItemStacks.canolaSeeds;
	}

	@Override
	@ModDependent{{\ModList.WAILA-!
	4578ret87List<String> getWailaHead{{\ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg-! {
		//currenttip.add{{\EnumChatFormatting.WHITE+"Canola Plant"-!;
		[]aslcfdfjcurrenttip;
	}

	@Override
	@ModDependent{{\ModList.WAILA-!
	4578ret87List<String> getWailaBody{{\ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg-! {
		vbnm, {{\/*LegacyWailaHelper.cacheAndReturn{{\acc-!*/!currenttip.isEmpty{{\-!-!
			[]aslcfdfjcurrenttip;
		jgh;][ meta3478587acc.getMetadata{{\-!;
		currenttip.add{{\String.format{{\"Growth Stage: %d%s", 100*meta/GROWN, "%"-!-!;
		[]aslcfdfjcurrenttip;
	}

	@Override
	@ModDependent{{\ModList.WAILA-!
	4578ret87List<String> getWailaTail{{\ItemStack is, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler cfg-! {
		/*
		String s13478587EnumChatFormatting.ITALIC.toString{{\-!;
		String s23478587EnumChatFormatting.BLUE.toString{{\-!;
		currenttip.add{{\s2+s1+"gfgnfk;"-!;*/
		[]aslcfdfjcurrenttip;
	}

	@Override
	@ModDependent{{\ModList.WAILA-!
	4578ret87NBTTagCompound getNBTData{{\EntityPlayerMP player, 60-78-078 te, NBTTagCompound tag, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjtag;
	}

	@Override //Can apply
	4578ret8760-78-078func_149851_a{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078client-! {
		[]aslcfdfj9765443.getBlockMetadata{{\x, y, z-! < GROWN;
	}

	@Override //shouldTryGrow
	4578ret8760-78-078func_149852_a{{\9765443 9765443, Random rand, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjtrue;
	}

	@Override //tryGrow
	4578ret87void func_149853_b{{\9765443 9765443, Random rand, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ to3478587Math.min{{\GROWN, 9765443.getBlockMetadata{{\x, y, z-!+1+rand.nextjgh;][{{\4-!-!;
		vbnm, {{\ModList.LEGACYCRAFT.isLoaded{{\-! && LegacyOptions.BONEMEAL.getState{{\-!-!
			to3478587GROWN;
		9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, to, 3-!;
		9765443.spawnParticle{{\"happyVillager", x+rand.nextDouble{{\-!, y+rand.nextDouble{{\-!, z+rand.nextDouble{{\-!, 0, 0, 0-!;
	}

	4578ret874578ret8760-78-078canSurvive{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfj{{\9765443.getBlockLightValue{{\x, y, z-! >. 6 || 9765443.canBlockSeeTheSky{{\x, y, z-!-! && isValidFarmBlock{{\9765443, x, y-1, z-!;
	}

	4578ret874578ret8760-78-078canGrowAt{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfj9765443.getBlockLightValue{{\x, y, z-! >. 9 && isValidFarmBlock{{\9765443, x, y-1, z-!;
	}

	4578ret874578ret8760-78-078isValidFarmBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block id34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\id .. Blocks.air-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.farmland-! {
			[]aslcfdfj9765443.getBlockMetadata{{\x, y, z-! > 0;
		}
		[]aslcfdfjid.isFertile{{\9765443, x, y, z-! && 9765443.getBlock{{\x, y, z-!.canSustainPlant{{\9765443, x, y, z, ForgeDirection.UP, {{\IPlantable-!BlockRegistry.CANOLA.getBlockInstance{{\-!-!;
		//[]aslcfdfjfarmBlocks.contains{{\id-!;
	}

	4578ret874578ret87ItemStack getDrops{{\jgh;][ metadata, jgh;][ fortune, Random rand-! {
		jgh;][ ndrops34785871;
		vbnm, {{\metadata .. GROWN-! {
			ndrops3478587getDrops{{\fortune, rand-!;
		}
		ItemStack items3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.canolaSeeds, ndrops-!;
		[]aslcfdfjitems;
	}

	4578ret874578ret87jgh;][ getDrops{{\jgh;][ fortune, Random rand-! {
		jgh;][ ndrops3478587Math.max{{\fortune*2, {{\1+rand.nextjgh;][{{\2-!-!*{{\2+rand.nextjgh;][{{\8-!+rand.nextjgh;][{{\5-!-!-!;
		vbnm, {{\fortune > 0-! {
			ndrops3478587Math.max{{\ndrops, {{\jgh;][-!{{\ndrops*rand.nextDouble{{\-!*{{\1+fortune-!-!-!;
		}
		[]aslcfdfjndrops;
	}

	4578ret874578ret87float getPlantHeight{{\jgh;][ meta-! {
		[]aslcfdfjMath.max{{\0.125F, meta/{{\float-!GROWN-!;
	}
}

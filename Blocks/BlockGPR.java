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

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;

public class BlockGPR extends BlockBasicMachine {

	public BlockGPR(int ID, Material mat) {
		super(ID, mat);
		//this.blockIndexInTexture = 81;
	}

	public int getBlockTextureFromSideAndMetadata(int s, int dmg) {
		if (s == 1)
			return 82;
		if (s == 0)
			return 83;
		return 81;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityGPR();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack is)		//Directional code
	{
		//if (MathHelper.abs(par5EntityLiving.rotationPitch) < 45) {
		int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;
		getBiomeDesign(world, x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		TileEntityGPR tile = (TileEntityGPR)world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return;
		switch (i) {
		case 0:
		case 2:
			tile.xdir = true;
			break;
		case 1:
		case 3:
			tile.xdir = false;
			break;
		}
	}

	public static int getBiomeDesign(World world, int x, int y, int z) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biome);
		if (biome == BiomeGenBase.forest || biome == BiomeGenBase.forestHills || biome == BiomeGenBase.plains)
			return 0;
		else if (biome == BiomeGenBase.mushroomIsland || biome == BiomeGenBase.mushroomIslandShore)
			return 1;
		else if (biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills)
			return 2;
		else if (biome == BiomeGenBase.extremeHills || biome == BiomeGenBase.extremeHillsEdge)
			return 3;
		else if (biome == BiomeGenBase.ocean || biome == BiomeGenBase.river)
			return 4;
		else if (biome == BiomeGenBase.beach || biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills)
			return 5;
		else if (biome == BiomeGenBase.taiga || biome == BiomeGenBase.taigaHills || biome == BiomeGenBase.iceMountains || biome == BiomeGenBase.icePlains)
			return 6;
		else if (biome == BiomeGenBase.hell)
			return 7;
		else if (biome == BiomeGenBase.sky)
			return 8;
		else if (biome == BiomeGenBase.swampland)
			return 9;
		else if (biome == BiomeGenBase.frozenOcean || biome == BiomeGenBase.frozenRiver)
			return 10;
		else {
			for (int i = 0; i < types.length; i++) {
				if (types[i] == BiomeDictionary.Type.NETHER)
					return 7;
				if (types[i] == BiomeDictionary.Type.END)
					return 8;
				if (types[i] == BiomeDictionary.Type.SWAMP)
					return 9;
				if (types[i] == BiomeDictionary.Type.MUSHROOM)
					return 1;
				if (types[i] == BiomeDictionary.Type.FROZEN)
					return 6;
				if (types[i] == BiomeDictionary.Type.DESERT)
					return 5;
				if (types[i] == BiomeDictionary.Type.BEACH)
					return 5;
				if (types[i] == BiomeDictionary.Type.JUNGLE)
					return 2;
				if (types[i] == BiomeDictionary.Type.WATER)
					return 4;
				if (types[i] == BiomeDictionary.Type.MOUNTAIN)
					return 3;
				if (types[i] == BiomeDictionary.Type.PLAINS)
					return 0;
				if (types[i] == BiomeDictionary.Type.FOREST)
					return 0;
			}
		}
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
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 11; j++) {
				String biome;
				switch(j) {
				case 0:
					biome = "grass";
					break;
				case 1:
					biome = "mushroom";
					break;
				case 2:
					biome = "jungle";
					break;
				case 3:
					biome = "hills";
					break;
				case 4:
					biome = "ocean";
					break;
				case 5:
					biome = "desert";
					break;
				case 6:
					biome = "snow";
					break;
				case 7:
					biome = "nether";
					break;
				case 8:
					biome = "end";
					break;
				case 9:
					biome = "swamp";
					break;
				case 10:
					biome = "ice";
					break;
				default:
					biome = "";
				}
				icons[j][i] = par1IconRegister.registerIcon("RotaryCraft:steel");
				icons[j][1] = par1IconRegister.registerIcon("RotaryCraft:gpr_top_"+biome);
			}
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (this.canHarvest(world, player, x, y, z))
			;//this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlock(x, y, z, 0);
	}

	private boolean canHarvest(World world, EntityPlayer ep, int x, int y, int z) {
		return RotaryAux.canHarvestSteelMachine(ep);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		if (!world.isRemote) {
			ItemStack todrop = MachineRegistry.GPR.getCraftedProduct();
			EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
			item.delayBeforeCanPickup = 10;
			if (!world.isRemote)
				world.spawnEntityInWorld(item);
		}
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Interfaces.CustomCropHandler;
import Reika.DragonAPI.Interfaces.Registry.ModEntry;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

public final class CanolaHandler implements CustomCropHandler {

	public CanolaHandler() {

	}

	@Override
	public int getHarvestedMeta(World world, int x, int y, int z) {
		return 0;
	}

	@Override
	public boolean isCrop(Block id, int meta) {
		return ModList.ROTARYCRAFT.isLoaded() && id == BlockRegistry.CANOLA.getBlockInstance();
	}

	@Override
	public boolean isRipeCrop(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return ModList.ROTARYCRAFT.isLoaded() && this.isCrop(world.getBlock(x, y, z), meta) && meta == BlockCanola.GROWN;
	}

	@Override
	public void makeRipe(World world, int x, int y, int z) {
		if (ModList.ROTARYCRAFT.isLoaded())
			world.setBlockMetadataWithNotify(x, y, z, BlockCanola.GROWN, 3);
	}

	@Override
	public boolean isSeedItem(ItemStack is) {
		return ModList.ROTARYCRAFT.isLoaded() && ItemRegistry.CANOLA.matchItem(is) && is.getItemDamage() == 0;
	}

	@Override
	public ArrayList<ItemStack> getAdditionalDrops(World world, int x, int y, int z, Block id, int meta, int fortune) {
		return new ArrayList();
	}

	@Override
	public void editTileDataForHarvest(World world, int x, int y, int z) {

	}

	@Override
	public boolean initializedProperly() {
		return ModList.ROTARYCRAFT.isLoaded() && BlockRegistry.CANOLA.getBlockInstance() != null;
	}

	@Override
	public ArrayList<ItemStack> getDropsOverride(World world, int x, int y, int z, Block id, int meta, int fortune) {
		return null;
	}

	@Override
	public int getGrowthState(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public ModEntry getMod() {
		return ModList.ROTARYCRAFT;
	}

	@Override
	public int getColor() {
		return 0x00cc00;
	}

	@Override
	public String getEnumEntryName() {
		return "CANOLA";
	}

	@Override
	public boolean isTileEntity() {
		return false;
	}

	@Override
	public boolean neverDropsSecondSeed() {
		return false;
	}

}

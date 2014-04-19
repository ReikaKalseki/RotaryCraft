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
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConnectedTextureGlass;
import Reika.RotaryCraft.Base.BlockBasic;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class BlockObsidianGlass extends BlockBasic implements ConnectedTextureGlass {

	private final ArrayList<Integer> allDirs = new ArrayList();
	private Icon[] edges = new Icon[10];

	public BlockObsidianGlass(int ID) {
		super(ID, Material.glass);
		this.setHardness(10F);
		this.setResistance(6000F);
		this.setLightValue(0F);
		this.setStepSound(soundGlassFootstep);
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotary);

		//this.blockIndexInTexture = 74;

		for (int i = 1; i < 10; i++) {
			allDirs.add(i);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z, Entity e)
	{
		return false;
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		return 6000F;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];
		return iba.getBlockId(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ) != blockID;
	}

	@Override
	public int getRenderType() {
		return RotaryCraft.proxy.connectedRender;
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion) {
		return false;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{
		ItemStack item = ep.inventory.getCurrentItem();
		if (item == null)
			return false;
		if (item.itemID != Item.pickaxeDiamond.itemID && item.itemID != ItemRegistry.BEDPICK.getShiftedID())
			return false;
		return true;
	}

	/** This block can only be destroyed by the wither explosions - this in effect makes it witherproof */
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex) {
		world.setBlock(x, y, z, blockID);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return blockID;
	}

	@Override
	public int damageDropped(int par1)
	{
		return 0;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 74;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[0][s];
	}

	@Override
	public void registerIcons(IconRegister ico) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int i = 0; i < 6; i++)
			icons[0][i] = ico.registerIcon("RotaryCraft:obsidiglass");

		for (int i = 0; i < 10; i++) {
			edges[i] = ico.registerIcon("rotarycraft:glass/glass_"+i);
		}
	}

	public ArrayList<Integer> getEdgesForFace(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		ArrayList<Integer> li = new ArrayList();
		li.addAll(allDirs);

		if (world.getBlockMetadata(x, y, z) == 1) //clear version
			li.remove(new Integer(5)); //glass tex

		if (face.offsetX != 0) { //test YZ
			//sides; removed if have adjacent on side
			if (world.getBlockId(x, y, z+1) == blockID)
				li.remove(new Integer(2));
			if (world.getBlockId(x, y, z-1) == blockID)
				li.remove(new Integer(8));
			if (world.getBlockId(x, y+1, z) == blockID)
				li.remove(new Integer(4));
			if (world.getBlockId(x, y-1, z) == blockID)
				li.remove(new Integer(6));

			//Corners; only removed if have adjacent on side AND corner
			if (world.getBlockId(x, y+1, z+1) == blockID && !li.contains(4) && !li.contains(2))
				li.remove(new Integer(1));
			if (world.getBlockId(x, y-1, z-1) == blockID && !li.contains(6) && !li.contains(8))
				li.remove(new Integer(9));
			if (world.getBlockId(x, y+1, z-1) == blockID && !li.contains(4) && !li.contains(8))
				li.remove(new Integer(7));
			if (world.getBlockId(x, y-1, z+1) == blockID && !li.contains(2) && !li.contains(6))
				li.remove(new Integer(3));
		}
		if (face.offsetY != 0) { //test XZ
			//sides; removed if have adjacent on side
			if (world.getBlockId(x, y, z+1) == blockID)
				li.remove(new Integer(2));
			if (world.getBlockId(x, y, z-1) == blockID)
				li.remove(new Integer(8));
			if (world.getBlockId(x+1, y, z) == blockID)
				li.remove(new Integer(4));
			if (world.getBlockId(x-1, y, z) == blockID)
				li.remove(new Integer(6));

			//Corners; only removed if have adjacent on side AND corner
			if (world.getBlockId(x+1, y, z+1) == blockID && !li.contains(4) && !li.contains(2))
				li.remove(new Integer(1));
			if (world.getBlockId(x-1, y, z-1) == blockID && !li.contains(6) && !li.contains(8))
				li.remove(new Integer(9));
			if (world.getBlockId(x+1, y, z-1) == blockID && !li.contains(4) && !li.contains(8))
				li.remove(new Integer(7));
			if (world.getBlockId(x-1, y, z+1) == blockID && !li.contains(2) && !li.contains(6))
				li.remove(new Integer(3));
		}
		if (face.offsetZ != 0) { //test XY
			//sides; removed if have adjacent on side
			if (world.getBlockId(x, y+1, z) == blockID)
				li.remove(new Integer(4));
			if (world.getBlockId(x, y-1, z) == blockID)
				li.remove(new Integer(6));
			if (world.getBlockId(x+1, y, z) == blockID)
				li.remove(new Integer(2));
			if (world.getBlockId(x-1, y, z) == blockID)
				li.remove(new Integer(8));

			//Corners; only removed if have adjacent on side AND corner
			if (world.getBlockId(x+1, y+1, z) == blockID && !li.contains(2) && !li.contains(4))
				li.remove(new Integer(1));
			if (world.getBlockId(x-1, y-1, z) == blockID && !li.contains(8) && !li.contains(6))
				li.remove(new Integer(9));
			if (world.getBlockId(x+1, y-1, z) == blockID && !li.contains(2) && !li.contains(6))
				li.remove(new Integer(3));
			if (world.getBlockId(x-1, y+1, z) == blockID && !li.contains(4) && !li.contains(8))
				li.remove(new Integer(7));
		}
		return li;
	}

	public Icon getIconForEdge(int edge) {
		return edges[edge];
	}

	@Override
	public boolean renderCentralTextureForItem(int meta) {
		return true;
	}
}

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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Interfaces.Block.ConnectedTextureGlass;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.ConnectedGlassRenderer;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank.TankFlags;

public class BlockDecoTank extends Block implements ConnectedTextureGlass {

	private final ArrayList<Integer> allDirs = new ArrayList();
	private final IIcon[] icons = new IIcon[10];

	public BlockDecoTank() {
		super(Material.glass);
		this.setResistance(2);
		this.setHardness(0.35F);
		this.setLightOpacity(0);
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotary);

		for (int i = 1; i < 10; i++) {
			allDirs.add(i);
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		float h = super.getBlockHardness(world, x, y, z);
		return TankFlags.RESISTANT.apply(world, x, y, z) ? h*2 : h;
	}

	@Override
	public float getExplosionResistance(Entity e, World world, int x, int y, int z, double eX, double eY, double eZ) {
		float r = super.getExplosionResistance(e, world, x, y, z, eX, eY, eZ);
		return TankFlags.RESISTANT.apply(world, x, y, z) ? 600000 : r;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.func_147479_m(x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block id, int meta) {
		super.breakBlock(world, x, y, z, id, meta);
		world.func_147479_m(x, y, z);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> li = super.getDrops(world, x, y, z, meta, fortune);
		TileEntityDecoTank te = (TileEntityDecoTank)world.getTileEntity(x, y, z);
		if (te != null) {
			Fluid f = te.getFluid();

			ItemStack is = li.get(0);

			if (f != null) {
				is.stackTagCompound = new NBTTagCompound();
				ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
				is.stackTagCompound.setInteger("level", ItemBlockDecoTank.FILL);
			}

			int dropmeta = 0;
			for (int k = 0; k < TankFlags.list.length; k++) {
				TankFlags fg = TankFlags.list[k];
				if (te.getFlag(fg)) {
					dropmeta = dropmeta | fg.getItemMetadataBit();
				}
			}

			is.setItemDamage(dropmeta);
		}
		else
			li.clear();
		return li;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harv)
	{
		if (!player.capabilities.isCreativeMode)
			this.harvestBlock(world, player, x, y, z, world.getBlockMetadata(x, y, z));
		return world.setBlockToAir(x, y, z);
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
	public int getRenderType() {
		return RotaryCraft.proxy.connectedRender;
	}

	@Override
	public boolean canRenderInPass(int pass)
	{
		if (RotaryCraft.instance.isLocked())
			return false;
		ConnectedGlassRenderer.renderPass = pass;
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean renderCentralTextureForItem(int meta) {
		return !TankFlags.CLEAR.applyItem(meta);
	}

	public HashSet<Integer> getEdgesForFace(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		HashSet<Integer> li = new HashSet();
		li.addAll(allDirs);

		if (TankFlags.CLEAR.apply(world, x, y, z))
			li.remove(5); //glass tex

		if (face.offsetX != 0) { //test YZ
			//sides; removed if have adjacent on side
			if (world.getBlock(x, y, z+1) == this)
				li.remove(2);
			if (world.getBlock(x, y, z-1) == this)
				li.remove(8);
			if (world.getBlock(x, y+1, z) == this)
				li.remove(4);
			if (world.getBlock(x, y-1, z) == this)
				li.remove(6);

			//Corners; only removed if have adjacent on side AND corner
			if (world.getBlock(x, y+1, z+1) == this && !li.contains(4) && !li.contains(2))
				li.remove(1);
			if (world.getBlock(x, y-1, z-1) == this && !li.contains(6) && !li.contains(8))
				li.remove(9);
			if (world.getBlock(x, y+1, z-1) == this && !li.contains(4) && !li.contains(8))
				li.remove(7);
			if (world.getBlock(x, y-1, z+1) == this && !li.contains(2) && !li.contains(6))
				li.remove(3);
		}
		if (face.offsetY != 0) { //test XZ
			//sides; removed if have adjacent on side
			if (world.getBlock(x, y, z+1) == this)
				li.remove(2);
			if (world.getBlock(x, y, z-1) == this)
				li.remove(8);
			if (world.getBlock(x+1, y, z) == this)
				li.remove(4);
			if (world.getBlock(x-1, y, z) == this)
				li.remove(6);

			//Corners; only removed if have adjacent on side AND corner
			if (world.getBlock(x+1, y, z+1) == this && !li.contains(4) && !li.contains(2))
				li.remove(1);
			if (world.getBlock(x-1, y, z-1) == this && !li.contains(6) && !li.contains(8))
				li.remove(9);
			if (world.getBlock(x+1, y, z-1) == this && !li.contains(4) && !li.contains(8))
				li.remove(7);
			if (world.getBlock(x-1, y, z+1) == this && !li.contains(2) && !li.contains(6))
				li.remove(3);
		}
		if (face.offsetZ != 0) { //test XY
			//sides; removed if have adjacent on side
			if (world.getBlock(x, y+1, z) == this)
				li.remove(4);
			if (world.getBlock(x, y-1, z) == this)
				li.remove(6);
			if (world.getBlock(x+1, y, z) == this)
				li.remove(2);
			if (world.getBlock(x-1, y, z) == this)
				li.remove(8);

			//Corners; only removed if have adjacent on side AND corner
			if (world.getBlock(x+1, y+1, z) == this && !li.contains(2) && !li.contains(4))
				li.remove(1);
			if (world.getBlock(x-1, y-1, z) == this && !li.contains(8) && !li.contains(6))
				li.remove(9);
			if (world.getBlock(x+1, y-1, z) == this && !li.contains(2) && !li.contains(6))
				li.remove(3);
			if (world.getBlock(x-1, y+1, z) == this && !li.contains(4) && !li.contains(8))
				li.remove(7);
		}
		return li;
	}

	public IIcon getIconForEdge(IBlockAccess world, int x, int y, int z, int edge) {
		return TankFlags.RESISTANT.apply(world, x, y, z) ? ((BlockBlastGlass)BlockRegistry.BLASTGLASS.getBlockInstance()).getIconForEdge(world, x, y, z, edge) : icons[edge];
	}

	public IIcon getIconForEdge(int itemMeta, int edge) {
		return TankFlags.RESISTANT.applyItem(itemMeta) ? ((BlockBlastGlass)BlockRegistry.BLASTGLASS.getBlockInstance()).getIconForEdge(itemMeta, edge) : icons[edge];
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		return icons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister ico) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int i = 0; i < 10; i++) {
			icons[i] = ico.registerIcon("rotarycraft:tank/tank_"+i);
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];
		return iba.getBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ) != this;
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileEntityDecoTank();
	}

	public Fluid getFluid(IBlockAccess world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		if (id == this) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityDecoTank) {
				return ((TileEntityDecoTank)te).getFluid();
			}
		}
		return null;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if (TankFlags.LIGHTED.apply(world, x, y, z))
			return 15;
		Fluid f = this.getFluid(world, x, y, z);
		return f != null ? f.getLuminosity() : 0;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntityDecoTank te = (TileEntityDecoTank)world.getTileEntity(x, y, z);
		ItemStack is = this.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).get(0);
		return is;
	}

	@Override
	public int damageDropped(int dmg) {
		return 0;
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}

}

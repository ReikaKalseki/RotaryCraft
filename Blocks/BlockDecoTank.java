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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConnectedTextureGlass;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank;

public class BlockDecoTank extends Block implements ConnectedTextureGlass {

	private final ArrayList<Integer> allDirs = new ArrayList();
	private final Icon[] icons = new Icon[10];

	public BlockDecoTank(int par1) {
		super(par1, Material.glass);
		this.setResistance(6000);
		this.setHardness(0.35F);
		this.setLightOpacity(0);
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotary);

		for (int i = 1; i < 10; i++) {
			allDirs.add(i);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.markBlockForRenderUpdate(x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		super.breakBlock(world, x, y, z, id, meta);
		world.markBlockForRenderUpdate(x, y, z);
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> li = super.getBlockDropped(world, x, y, z, meta, fortune);
		TileEntityDecoTank te = (TileEntityDecoTank)world.getBlockTileEntity(x, y, z);
		if (te != null) {
			Fluid f = te.getFluid();
			if (f != null) {
				ItemStack is = li.get(0);
				is.stackTagCompound = new NBTTagCompound();
				ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
				is.stackTagCompound.setInteger("level", ItemBlockDecoTank.FILL);
			}
		}
		else
			li.clear();
		return li;
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (!player.capabilities.isCreativeMode)
			this.harvestBlock(world, player, x, y, z, world.getBlockMetadata(x, y, z));
		return world.setBlock(x, y, z, 0);
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
		ClientProxy.connected.renderPass = pass;
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean renderCentralTextureForItem(int meta) {
		return meta == 0;
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
		return icons[edge];
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return RotaryCraft.decoblock.getIcon(0, 0);
	}

	@Override
	public void registerIcons(IconRegister ico) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int i = 0; i < 10; i++) {
			icons[i] = ico.registerIcon("rotarycraft:tank/tank_"+i);
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];
		return iba.getBlockId(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ) != blockID;
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
		int id = world.getBlockId(x, y, z);
		if (id == blockID) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te instanceof TileEntityDecoTank) {
				return ((TileEntityDecoTank) te).getFluid();
			}
		}
		return null;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		Fluid f = this.getFluid(world, x, y, z);
		return f != null ? f.getLuminosity() : 0;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntityDecoTank te = (TileEntityDecoTank)world.getBlockTileEntity(x, y, z);
		ItemStack is = new ItemStack(blockID, 1, world.getBlockMetadata(x, y, z));
		if (te != null) {
			Fluid f = te.getFluid();
			if (f != null) {
				is.stackTagCompound = new NBTTagCompound();
				ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
				is.stackTagCompound.setInteger("level", ItemBlockDecoTank.FILL);
			}
		}
		return is;
	}

	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}

	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}

}

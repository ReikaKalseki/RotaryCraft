/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.Collection;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.OldTextureLoader;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Renders.MachineISBRH;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockModelledMultiTE extends BlockBasicMultiTE {

	public BlockModelledMultiTE(Material mat) {
		super(mat);
	}

	@Override
	public final int getRenderType() {
		return RotaryCraft.instance.isLocked() || OldTextureLoader.instance.loadOldTextures() ? 0 : MachineISBRH.renderID;
	}

	@Override
	public final IIcon getIcon(int s, int meta) {
		return OldTextureLoader.instance.loadOldTextures() ? OldTextureLoader.instance.getOldTexture(this, meta, s) : blockIcon;
	}

	@Override
	public final boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public final boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		return m.isOpaque() ? 255 : 0; //out of 255
	}

	@Override
	public final void registerBlockIcons(IIconRegister ico) {
		blockIcon = ico.registerIcon("rotarycraft:steel");
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List li, Entity e) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == null) {
			super.addCollisionBoxesToList(world, x, y, z, mask, li, e);
			return;
		}

		RotaryCraftTileEntity te = (RotaryCraftTileEntity)world.getTileEntity(x, y, z);
		if (m == MachineRegistry.RESERVOIR) {
			if (e instanceof EntityItem) {
				AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+0.0625, z+1);
				if (box.intersectsWith(mask))
					li.add(box);
			}
			else {
				Collection<AxisAlignedBB> c = ((TileEntityReservoir)te).getComplexHitbox();
				for (AxisAlignedBB box : c) {
					box = box.offset(x, y, z);
					if (box.intersectsWith(mask))
						li.add(box);
				}
			}
		}
		else {
			super.addCollisionBoxesToList(world, x, y, z, mask, li, e);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		AxisAlignedBB box = this.getAABB(world, x, y, z);
		this.setBounds(box, x, y, z);
	}

	@Override
	public final AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return this.getAABB(world, x, y, z);
	}

	private AxisAlignedBB getAABB(IBlockAccess world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == null)
			return ReikaAABBHelper.getBlockAABB(x, y, z);
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)world.getTileEntity(x, y, z);
		if (m == MachineRegistry.RESERVOIR) {
			return ((TileEntityReservoir)te).getHitbox();
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x+m.getMinX(te), y+m.getMinY(te), z+m.getMinZ(te), x+m.getMaxX(te), y+m.getMaxY(te), z+m.getMaxZ(te));
		if (te.isFlipped) {
			box = AxisAlignedBB.getBoundingBox(x+m.getMinX(te), y+(1-m.getMaxY(te)), z+m.getMinZ(te), x+m.getMaxX(te), y+(1-m.getMinY(te)), z+m.getMaxZ(te));
		}
		this.setBounds(box, x, y, z);
		return box;
	}

	@Override
	public final AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer eff)
	{
		return ReikaRenderHelper.addModelledBlockParticles("/Reika/RotaryCraft/Textures/TileEntityTex/", world, x, y, z, this, eff, ReikaJavaLibrary.makeListFrom(new double[]{0,0,1,1}), RotaryCraft.class);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final boolean addHitEffects(World world, MovingObjectPosition tg, EffectRenderer eff)
	{
		return ReikaRenderHelper.addModelledBlockParticles("/Reika/RotaryCraft/Textures/TileEntityTex/", world, tg, this, eff, ReikaJavaLibrary.makeListFrom(new double[]{0,0,1,1}), RotaryCraft.class);
	}
	/*
	@Override
	public float getBlockBrightness(IBlockAccess iba, int x, int y, int z)
	{
		return iba.getBrightness(x, y+1, z, this.getLightValue(iba, x, y+1, z));
	}*/

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
	 */
	public int getMixedBrightnessForBlock(IBlockAccess iba, int x, int y, int z)
	{
		return iba.getLightBrightnessForSkyBlocks(x, y+1, z, this.getLightValue(iba, x, y+1, z));
	}

	@Override
	public final boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)world.getTileEntity(x, y, z);
		if (m.isSolidBottom()) {
			return te.isFlipped ? dir == ForgeDirection.UP : dir == ForgeDirection.DOWN;
		}
		if (m == MachineRegistry.COMPACTOR)
			return dir != ((TileEntityCompactor)te).getReadDirection().getOpposite();
		return false;
	}

}

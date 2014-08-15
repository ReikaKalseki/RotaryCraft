/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockModelledMultiTE extends BlockBasicMultiTE {

	public BlockModelledMultiTE(Material mat) {
		super(mat);
	}

	@Override
	public final int getRenderType() {
		return RotaryCraft.instance.isLocked() ? 0 : -1;
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
	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
	{
		return 0; //out of 255
	}

	@Override
	public final void registerBlockIcons(IIconRegister ico) {
		blockIcon = ico.registerIcon("rotarycraft:steel");
	}

	@Override
	public final AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
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
	public final AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
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

}
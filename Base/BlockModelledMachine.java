/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.OldTextureLoader;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class BlockModelledMachine extends BlockBasicMachine {

	public BlockModelledMachine(Material par3Material) {
		super(par3Material);
		//this.blockIndexInTexture = 2;
	}

	@Override
	public final boolean isOpaqueCube() {
		return false;
	}

	@Override
	public final boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public final int getRenderType() {
		return RotaryCraft.instance.isLocked() || OldTextureLoader.instance.loadOldTextures() ? 0 : -1;
	}

	/** For disallowing this method in subclasses */
	public final int getBlockTextureFromSideAndMetadata(int s, int dmg) {
		return 255;
	}

	@Override
	public final IIcon getIcon(int s, int meta) {
		return OldTextureLoader.instance.loadOldTextures() ? OldTextureLoader.instance.getOldTexture(this, meta, s) : icons[0][0];
	}

	@Override
	public final void registerBlockIcons(IIconRegister par1IconRegister) {
		icons[0][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
	}

	@Override
	public final AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == null)
			return null;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)world.getTileEntity(x, y, z);
		return AxisAlignedBB.getBoundingBox(x+m.getMinX(te), y+m.getMinY(te), z+m.getMinZ(te), x+m.getMaxX(te), y+m.getMaxY(te), z+m.getMaxZ(te));
	}
}

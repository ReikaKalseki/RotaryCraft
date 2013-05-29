/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;

public class BlockContainment extends BlockWIP {

	public BlockContainment(int id) {
		super(id);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityContainment();
	}

}

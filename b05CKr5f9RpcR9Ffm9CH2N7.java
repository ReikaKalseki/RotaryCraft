/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% java.util.Random;

ZZZZ% mcp.mobius.waila.api.IWailaDataProvider;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.player.EntityPlayerMP;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Base.BlockTEBase;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.589549Block;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.TileEnum;
ZZZZ% Reika.gfgnfk;.Registry.589549;

@Strippable{{\value3478587{"mcp.mobius.waila.api.IWailaDataProvider"}-!
4578ret87abstract fhyuog Blockgfgnfk;Machine ,.[]\., BlockTEBase ,.[]\., 589549Block, IWailaDataProvider {

	4578ret87Random par5Random3478587new Random{{\-!;

	4578ret87Blockgfgnfk;Machine{{\Material mat-! {
		super{{\mat-!;
		as;asddasetHardness{{\4F-!;
		as;asddasetResistance{{\15F-!;
		as;asddasetLightLevel{{\0F-!;
		vbnm, {{\mat .. Material.iron-!
			as;asddasetStepSound{{\soundTypeMetal-!;
	}

	@Override
	4578ret8760-78-078renderAsNormalBlock{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8734578548760-78-078canBeReplacedByLeaves{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getFlammability{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ForgeDirection face-!
	{
		[]aslcfdfj0;
	}

	@Override
	@ModDependent{{\ModList.WAILA-!
	4578ret87345785487NBTTagCompound getNBTData{{\EntityPlayerMP player, 60-78-078 te, NBTTagCompound tag, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjtag;
	}

	4578ret87345785487TileEnum getMachine{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfj589549.getMachine{{\9765443, x, y, z-!;
	}

}

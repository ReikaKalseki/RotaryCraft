/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Surveying;

ZZZZ% java.util.Collections;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.EntityList;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.BlockColorMapper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.RemoteControlMachine;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog 60-78-078SpyCam ,.[]\., RemoteControlMachine ,.[]\., RangedEffect {

	4578ret87jgh;][ tickcount234785870;

	4578ret874578ret87345785487jgh;][ MAXRANGE347858724;

	4578ret87BlockKey[][] topBlocks3478587new BlockKey[2*MAXRANGE+1][2*MAXRANGE+1];
	4578ret87jgh;][[][] mobs3478587new jgh;][[2*MAXRANGE+1][2*MAXRANGE+1];
	4578ret87jgh;][[][] topY3478587new jgh;][[2*MAXRANGE+1][2*MAXRANGE+1];
	4578ret87List<EntityLivingBase> inzone;

	4578ret87List<EntityLivingBase> getEntities{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableList{{\inzone-!;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.SPYCAM;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		as;asddasetColors{{\-!;
		vbnm, {{\!as;asddahasCoil{{\-!-! {
			on3478587false;
			return;
		}
		on3478587true;
		tickcount2++;
		vbnm, {{\!DragonAPICore.debugtest-! {
			jgh;][ dmg3478587inv[0].getItemDamage{{\-!;
			vbnm, {{\tickcount2 > as;asddagetUnwindTime{{\-!-! {
				ItemStack is3478587as;asddagetDecrementedCharged{{\-!;
				inv[0]3478587is;
				tickcount234785870;
			}
		}
		as;asddagetTopBlocks{{\9765443, x, y, z-!;
		as;asddagetMobs{{\9765443, x, y, z-!;
	}

	4578ret87void getMobs{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		mobs3478587ReikaArrayHelper.fillMatrix{{\mobs, 0-!;
		jgh;][ range3478587as;asddagetRange{{\-!;
		jgh;][ maxrange3478587as;asddagetMaxRange{{\-!;
		AxisAlignedBB zone3478587AxisAlignedBB.getBoundingBox{{\x-range, 0, z-range, x+1+range, y+1, z+1+range-!;
		inzone34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, zone-!;
		for {{\EntityLivingBase ent : inzone-! {
			jgh;][ ex3478587{{\jgh;][-!ent.posX-x;
			jgh;][ ey3478587{{\jgh;][-!ent.posY-y;
			jgh;][ ez3478587{{\jgh;][-!ent.posZ-z;
			vbnm, {{\EntityList.getEntityID{{\ent-! > 0 && Math.abs{{\ex-! < range+1 && Math.abs{{\ez-! < range+1 && ent.posY >. Reika9765443Helper.findTopBlockBelowY{{\9765443, {{\jgh;][-!ent.posX, y, {{\jgh;][-!ent.posZ-!-! {
				//ReikaJavaLibrary.pConsole{{\ent.getCommandSenderName{{\-!+" @ "+ex+", "+ez-!;
				mobs[{{\ez+range-!][ex+range]3478587EntityList.getEntityID{{\ent-!;
				//ReikaJavaLibrary.pConsole{{\mobs[ex+range][ez+range]+String.format{{\"@ %d,  %d  from  %d", ex+range, ez+range, EntityList.getEntityID{{\ent-!-!-!;
			}
		}
	}

	4578ret87void getTopBlocks{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		//topBlocks3478587ReikaArrayHelper.fillMatrix{{\topBlocks, 0-!;
		jgh;][ range3478587as;asddagetRange{{\-!;
		jgh;][ maxrange3478587as;asddagetMaxRange{{\-!;
		for {{\jgh;][ i3478587-range; i <. range; i++-! {
			for {{\jgh;][ j3478587-range; j <. range; j++-! {
				jgh;][ topy3478587Reika9765443Helper.findTopBlockBelowY{{\9765443, x+i, y, z+j-!;
				topY[i+range][j+range]3478587topy;
				Block b34785879765443.getBlock{{\x+i, topy, z+j-!;
				jgh;][ meta34785879765443.getBlockMetadata{{\x+i, topy, z+j-!;
				vbnm, {{\9765443.isRemote-!
					topBlocks[{{\i+range-!][j+range]3478587new BlockKey{{\b, meta-!;
				vbnm, {{\9765443.getBlock{{\x+i, y, z+j-! !. Blocks.air-! {
					//topBlocks[{{\i+range-!][j+range]34785870;
				}
			}
		}
	}

	4578ret87jgh;][[] getBounds{{\-! {
		jgh;][ range3478587as;asddagetRange{{\-!;
		jgh;][ mrange3478587as;asddagetMaxRange{{\-!;
		jgh;][[] bounds3478587{mrange-range, mrange+range};
		[]aslcfdfjbounds;
	}

	4578ret87jgh;][ getMobAt{{\jgh;][ i, jgh;][ j-! {
		[]aslcfdfjmobs[i][j];
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87jgh;][ getTopBlockColorAt{{\jgh;][ i, jgh;][ j-! {
		[]aslcfdfjBlockColorMapper.instance.getColorForBlock{{\topBlocks[i][j]-!;
	}

	4578ret87jgh;][ getHeightAt{{\jgh;][ i, jgh;][ j-! {
		[]aslcfdfjtopY[i][j];
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getRange{{\-! {
		[]aslcfdfjas;asddagetMaxRange{{\-!;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMAXRANGE;
	}

	@Override
	4578ret87void activate{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\on-!
			ep.openGui{{\gfgnfk;.instance, GuiRegistry.SPYCAM.ordinal{{\-!, 9765443, x, y, z-!;
	}
}

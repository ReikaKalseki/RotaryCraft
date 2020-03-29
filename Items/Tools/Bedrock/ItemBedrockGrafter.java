/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.DragonAPI.APIPacketHandler.PacketIDs;
import Reika.DragonAPI.DragonAPIInit;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.ModInteract.Bees.ReikaBeeHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

import forestry.api.arboriculture.IToolGrafter;
import forestry.api.arboriculture.ITree;

@Strippable(value = {"forestry.api.arboriculture.IToolGrafter"})
public class ItemBedrockGrafter extends ItemRotaryTool implements IToolGrafter {

	public ItemBedrockGrafter(int index) {
		super(index);
	}

	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer ep) {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement(ep);
	}

	@Override
	public float getSaplingModifier(ItemStack stack, World world, EntityPlayer player, int x, int y, int z) {
		return 100;
	}

	@Override
	public float getDigSpeed(ItemStack is, Block b, int meta) {
		if (b == null)
			return 0;
		if (ForestryHandler.BlockEntry.LEAF.getBlock() == b)
			return 30;
		return super.getDigSpeed(is, b, meta);
	}

	@Override
	@ModDependent(ModList.FORESTRY)
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep) {
		if (ConfigRegistry.FAKEBEDROCK.getState() || !ReikaPlayerAPI.isFake(ep)) {
			World world = ep.worldObj;
			Block b = world.getBlock(x, y, z);
			if (ForestryHandler.BlockEntry.LEAF.getBlock() == b) {
				//ITree src = TreeManager.treeRoot.getTree(world, x, y, z); //only works on saplings
				TileEntity te = world.getTileEntity(x, y, z);
				ITree src = ReikaBeeHelper.getTree(te);
				if (src != null) {
					int r = ep.isSneaking() ? 0 : 4;
					for (int i = -r; i <= r; i++) {
						for (int j = -r; j <= r; j++) {
							for (int k = -r; k <= r; k++) {
								int dx = x+i;
								int dy = y+j;
								int dz = z+k;
								Block b2 = world.getBlock(dx, dy, dz);
								if (b2 == b) {
									//ITree other = TreeManager.treeRoot.getTree(world, dx, dy, dz);
									//if (other != null && src.isGeneticEqual(other)) {
									TileEntity te2 = world.getTileEntity(dx, dy, dz);
									ITree other = ReikaBeeHelper.getTree(te2);
									if (other != null && other.getGenome().getPrimary().getUID().equals(src.getGenome().getPrimary().getUID())) {
										int meta2 = world.getBlockMetadata(dx, dy, dz);
										b2.onBlockHarvested(world, dx, dy, dz, meta2, ep);
										b2.dropBlockAsItem(world, dx, dy, dz, meta2, 1);
										b2.removedByPlayer(world, ep, dx, dy, dz, false);
										ReikaSoundHelper.playBreakSound(world, dx, dy, dz, b2);
										ReikaPacketHelper.sendDataPacketWithRadius(DragonAPIInit.packetChannel, PacketIDs.BREAKPARTICLES.ordinal(), world, dx, dy, dz, 24, Block.getIdFromBlock(b2), meta2);
									}
								}
							}
						}
					}
					//return true;
				}
			}
		}
		return false;
	}

}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelWoodcutter;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWoodcutter extends TileEntityPowerReceiver {

	public int editx;
	public int edity;
	public int editz;
	public double dropx;
	public double dropz;

	/** For the 3x3 area of effect */
	public boolean varyx;
	public boolean varyz;
	public int stepx;
	public int stepz;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;

		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		if (this.operationComplete(tickcount, 0)) {
			tickcount = 0;
			if (RotaryConfig.gravtree) {
				for (int i = 0; i < 3; i++) {
					int editx2 = editx; int editz2 = editz;
					this.harvest(world, x, y, z, meta, editx2, y, editz2, (i == 0));
					if (varyx) {
						editx2--;
						//if (world.getBlockId(editx2, y, editz2) == Block.wood.blockID)
						this.harvest(world, x, y, z, meta, editx2, y, editz2, false);
						editx2 += 2;
						this.harvest(world, x, y, z, meta, editx2, y, editz2, false);
					}
					if (varyz) {
						editz2--;
						this.harvest(world, x, y, z, meta, editx2, y, editz2, false);
						editz2 += 2;
						this.harvest(world, x, y, z, meta, editx2, y, editz2, false);
					}
					if (stepx == -1)
						editx++;
					if (stepx == 1)
						editx--;
					if (stepz == -1)
						editz++;
					if (stepz == 1)
						editz--;
				}
				editx = x;
				edity = y+1;
				editz = z;
				this.harvest(world, x, y, z, meta, editx, edity, editz, false); //Top harvesting
			}
			else {
				this.instantHarvest(world, x, y, z, meta, editx, edity, editz);
			}
		}
	}

	public void instantHarvest(World world, int x, int y, int z, int meta, int editx2, int edity2, int editz2) {
		if (world.getBlockId(editx2, edity2, editz2) != Block.leaves.blockID && world.getBlockId(editx2, edity2, editz2) != Block.wood.blockID &&
				world.getBlockId(editx2, edity2, editz2) != RotaryCraft.gravleaves.blockID && world.getBlockId(editx2, edity2, editz2) != RotaryCraft.gravlog.blockID)
			return;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Valid "+String.format("%d %d", stepx, stepz));
		int[] logs = new int[4]; //Logs by metadata
		int[] leaves = new int[4]; //Leaves by metadata
		int numapples = 0;
		int heightlimit = 255;
		boolean blocked = false;
		for (int i = y; i < 256 && !blocked; i++) {
			int id = world.getBlockId(x, i, z);
			int readmeta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, i, z), 4);
			if (Block.opaqueCubeLookup[id] && id != Block.wood.blockID && id != RotaryCraft.gravlog.blockID &&
					id != Block.leaves.blockID && id != RotaryCraft.gravleaves.blockID) {
				blocked = true;
				heightlimit = i;
			}
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", heightlimit));
		if (stepx == -1 && stepz == 0) { //Reading +x side
			for (int k = x-2; k <= x+6; k++) {
				for (int m = z-4; m <= z+4; m++) {
					for (int h = y; h <= heightlimit; h++) {
						int blockid = world.getBlockId(k, h, m);
						int blockmeta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(k, h, m), 4);
						if (blockid == Block.wood.blockID || blockid == RotaryCraft.gravlog.blockID) {
							logs[blockmeta]++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
							if (h == y && (world.getBlockId(k, h-1, m) == 2 || world.getBlockId(k, h-1, m) == 3)) { // Ground level
								ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, k, h, m, Block.sapling.blockID, blockmeta);
							}
						}
						if (blockid == Block.leaves.blockID || blockid == RotaryCraft.gravleaves.blockID) {
							leaves[blockmeta]++;
							if (blockmeta == 0 && par5Random.nextInt(200) == 0)
								numapples++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
						}
					}
				}
			}
		}
		if (stepx == 1 && stepz == 0) { //Reading -x side
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Run");
			for (int k = x+2; k >= x-6; k--) {
				for (int m = z-4; m <= z+4; m++) {
					for (int h = y; h <= heightlimit; h++) {
						int blockid = world.getBlockId(k, h, m);
						int blockmeta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(k, h, m), 4);
						//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d:%d @ %d %d %d", blockid, blockmeta, k, h, m));
						if (blockid == Block.wood.blockID || blockid == RotaryCraft.gravlog.blockID) {
							logs[blockmeta]++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
							if (h == y && (world.getBlockId(k, h-1, m) == 2 || world.getBlockId(k, h-1, m) == 3)) { // Ground level
								ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, k, h, m, Block.sapling.blockID, blockmeta);
							}
						}
						if (blockid == Block.leaves.blockID || blockid == RotaryCraft.gravleaves.blockID) {
							leaves[blockmeta]++;
							if (blockmeta == 0 && par5Random.nextInt(200) == 0)
								numapples++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
						}
					}
				}
			}
		}
		if (stepx == 0 && stepz == -1) { //Reading +z side
			for (int k = x-4; k <= x+4; k++) {
				for (int m = z-2; m <= z+6; m++) {
					for (int h = y; h <= heightlimit; h++) {
						int blockid = world.getBlockId(k, h, m);
						int blockmeta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(k, h, m), 4);
						if (blockid == Block.wood.blockID || blockid == RotaryCraft.gravlog.blockID) {
							logs[blockmeta]++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
							if (h == y && (world.getBlockId(k, h-1, m) == 2 || world.getBlockId(k, h-1, m) == 3)) { // Ground level
								ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, k, h, m, Block.sapling.blockID, blockmeta);
							}
						}
						if (blockid == Block.leaves.blockID || blockid == RotaryCraft.gravleaves.blockID) {
							leaves[blockmeta]++;
							if (blockmeta == 0 && par5Random.nextInt(200) == 0)
								numapples++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
						}
					}
				}
			}
		}
		if (stepx == 0 && stepz == 1) { //Reading -z side
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Cutting");
			for (int k = x-4; k <= x+4; k++) {
				for (int m = z+2; m >= z-6; m--) {
					for (int h = y; h <= heightlimit; h++) {
						int blockid = world.getBlockId(k, h, m);
						int blockmeta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(k, h, m), 4);
						//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d", k, h, m));
						if (blockid == Block.wood.blockID || blockid == RotaryCraft.gravlog.blockID) {
							logs[blockmeta]++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
							if (h == y && (world.getBlockId(k, h-1, m) == 2 || world.getBlockId(k, h-1, m) == 3)) { // Ground level
								ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, k, h, m, Block.sapling.blockID, blockmeta);
							}
						}
						if (blockid == Block.leaves.blockID || blockid == RotaryCraft.gravleaves.blockID) {
							leaves[blockmeta]++;
							if (blockmeta == 0 && par5Random.nextInt(200) == 0)
								numapples++;
							ReikaWorldHelper.legacySetBlockWithNotify(world, k, h, m, 0);
						}
					}
				}
			}
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+par5Random.nextFloat(), 1F);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+par5Random.nextFloat(), 1F);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+par5Random.nextFloat(), 1F);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+par5Random.nextFloat(), 1F);
		if (world.isRemote)
			return;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", numapples));
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("Logs: %d %d %d %d", logs[0], logs[1], logs[2], logs[3]));
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("Leaves: %d %d %d %d", leaves[0], leaves[1], leaves[2], leaves[3]));
		int[] numsaplingstacks = new int[4]; //Number of sapling stacks by metadata
		int[] loosesaplings = new int[4]; //Leftovers from stacking process
		for (int i = 0; i < 4; i++) {
			int[] num;
			if (i != 3)
				num = ReikaMathLibrary.splitStacks(leaves[i]/20, 64);
			else // Jungle has half the drop rate
				num = ReikaMathLibrary.splitStacks(leaves[i]/40, 64);
			numsaplingstacks[i] = num[0];
			loosesaplings[i] = num[1];
		}
		int[] numlogstacks = new int[4]; //Number of sapling stacks by metadata
		int[] looselogs = new int[4]; //Leftovers from stacking process
		for (int i = 0; i < 4; i++) {
			int[] num = ReikaMathLibrary.splitStacks(logs[i], 64);
			numlogstacks[i] = num[0];
			looselogs[i] = num[1];
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < numlogstacks[i]; j++) {
				ItemStack fulllog = new ItemStack(Block.wood.blockID, 64, i);
				EntityItem logdrop = new EntityItem(world, dropx, y-0.25, dropz, fulllog);
				world.spawnEntityInWorld(logdrop);
			}
			for (int j = 0; j < numsaplingstacks[i]; j++) {
				ItemStack fullleaf = new ItemStack(Block.sapling.blockID, 64, i);
				EntityItem leafdrop = new EntityItem(world, dropx, y-0.25, dropz, fullleaf);
				world.spawnEntityInWorld(leafdrop);
			}
			if (looselogs[i] > 0) {
				ItemStack logleft = new ItemStack(Block.wood.blockID, looselogs[i], i);
				EntityItem logleftdrop = new EntityItem(world, dropx, y-0.25, dropz, logleft);
				world.spawnEntityInWorld(logleftdrop);
			}
			if (loosesaplings[i] > 0) {
				ItemStack saplingleft = new ItemStack(Block.sapling.blockID, loosesaplings[i], i);
				EntityItem saplingleftdrop = new EntityItem(world, dropx, y-0.25, dropz, saplingleft);
				world.spawnEntityInWorld(saplingleftdrop);
			}
		}
		int numapplestacks = ReikaMathLibrary.splitStacks(numapples, 64)[0];
		int looseapples = ReikaMathLibrary.splitStacks(numapples, 64)[1];
		for (int j = 0; j < numapplestacks; j++) {
			ItemStack fullapple = new ItemStack(Item.appleRed.itemID, 64, 0);
			EntityItem appledrop = new EntityItem(world, dropx, y-0.25, dropz, fullapple);
			world.spawnEntityInWorld(appledrop);
		}
		if (looseapples > 0) {
			ItemStack appleleft = new ItemStack(Item.appleRed.itemID, looseapples, 0);
			EntityItem appleleftdrop = new EntityItem(world, dropx, y-0.25, dropz, appleleft);
			world.spawnEntityInWorld(appleleftdrop);
		}
	}

	public void harvest(World world, int x, int y, int z, int meta, int editx2, int edity2, int editz2, boolean sapling) {
		int id = world.getBlockId(editx2, edity2, editz2);
		int editmeta = world.getBlockMetadata(editx2, edity2, editz2);
		if (id != Block.wood.blockID && id != RotaryCraft.gravlog.blockID && id != Block.leaves.blockID &&
				id != RotaryCraft.gravleaves.blockID)
			return;
		boolean top = true;
		if (!sapling && editmeta == 3)
			sapling  = true;

		int k = edity2+1;
		boolean blocked = false;
		while (k < 256 && !blocked) {
			int readid = world.getBlockId(editx2, k, editz2);
			if (readid != 0) {
				if (readid != Block.wood.blockID && readid != RotaryCraft.gravlog.blockID && Block.opaqueCubeLookup[readid])
					blocked = true;
				else if (readid == Block.leaves.blockID || readid == RotaryCraft.gravleaves.blockID)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx2, k, editz2, 0);
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d -> %d", readid, world.getBlockId(editx2, k, editz2)));
			}
			k++;
		}

		if (id == Block.wood.blockID || id == RotaryCraft.gravlog.blockID)
			world.playSoundEffect(editx2+0.5, edity+0.5, editz2+0.5, "dig.wood", 0.5F+par5Random.nextFloat(), 1F);
		if (id == Block.leaves.blockID || id == RotaryCraft.gravleaves.blockID)
			world.playSoundEffect(editx2+0.5, edity+0.5, editz2+0.5, "dig.grass", 0.5F+par5Random.nextFloat(), 1F);
		if (sapling) {
			for (int j = edity2+1; j < 256 && top; j++) {
				int id2 = world.getBlockId(editx2, j, editz2);
				if ((id2 == Block.wood.blockID || id2 == RotaryCraft.gravlog.blockID || id2 == Block.leaves.blockID ||
						id2 == RotaryCraft.gravleaves.blockID)) {
					top = false;
					//if (!world.isRemote)
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d:%d @ y=%d", id2, editmeta, j));
				}
			}
			AxisAlignedBB above = AxisAlignedBB.getBoundingBox(editx2+0.25, edity2, editz2+0.25, editx2+0.75, 256, editz2+0.75);
			List inzone = world.getEntitiesWithinAABB(EntityFallingSand.class, above);
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", inzone.size()));
			if (inzone.size() > 1)
				top = false;
		}
		if (sapling && (id == Block.wood.blockID || id == RotaryCraft.gravlog.blockID) && top &&
				(world.getBlockId(editx2, edity2-1, editz2) == Block.dirt.blockID ||
				world.getBlockId(editx2, edity2-1, editz2) == Block.grass.blockID))

			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, editx2, edity2, editz2, Block.sapling.blockID, editmeta);
		else
			ReikaWorldHelper.legacySetBlockWithNotify(world, editx2, edity2, editz2, 0);
		if (world.getBlockId(editx2, edity2+1, editz2) == Block.wood.blockID) {
			int logmeta = world.getBlockMetadata(editx2, edity2+1, editz2);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, editx2, edity2+1, editz2, RotaryCraft.gravlog.blockID, logmeta);
		}
		if (world.getBlockId(editx2, edity2+1, editz2) == Block.leaves.blockID) {
			int leafmeta = world.getBlockMetadata(editx2, edity2+1, editz2);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, editx2, edity2+1, editz2, RotaryCraft.gravleaves.blockID, leafmeta);
		}
		if (!world.isRemote && (id == Block.wood.blockID || id == RotaryCraft.gravlog.blockID)) {
			while (editmeta > 3)
				editmeta -= 4;
			ItemStack is = new ItemStack(Block.wood.blockID, 1, editmeta);
			EntityItem ent = new EntityItem(world, dropx, y-0.25, dropz, is);
			ent.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(ent);
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = x+1;
			readz = z;
			ready = y;
			editx = x-1;
			edity = y;
			editz = z;
			dropx = x+1+0.125;
			dropz = z+0.5;
			stepx = 1;
			stepz = 0;
			varyx = false;
			varyz = true;
			break;
		case 1:
			readx = x-1;
			readz = z;
			ready = y;
			editx = x+1;
			edity = y;
			editz = z;
			dropx = x-0.125;
			dropz = z+0.5;
			stepx = -1;
			stepz = 0;
			varyx = false;
			varyz = true;
			break;
		case 2:
			readz = z+1;
			readx = x;
			ready = y;
			editx = x;
			edity = y;
			editz = z-1;
			dropx = x+0.5;
			dropz = z+1+0.125;
			stepx = 0;
			stepz = 1;
			varyx = true;
			varyz = false;
			break;
		case 3:
			readz = z-1;
			readx = x;
			ready = y;
			editx = x;
			edity = y;
			editz = z+1;
			dropx = x+0.5;
			dropz = z-0.125;
			stepx = 0;
			stepz = -1;
			varyx = true;
			varyz = false;
			break;
		}
		dropx = x+0.5; dropz = z+0.5;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelWoodcutter();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.WOODCUTTER.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasWood())
			return 15;
		return 0;
	}

	private boolean hasWood() {
		if (worldObj.getBlockId(editx, edity, editz) == Block.wood.blockID)
			return true;
		if (worldObj.getBlockId(editx, edity, editz) == RotaryCraft.gravlog.blockID)
			return true;
		return false;
	}
}

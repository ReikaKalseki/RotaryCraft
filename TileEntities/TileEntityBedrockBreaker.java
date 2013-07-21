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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Models.ModelBedrockBreaker;
import Reika.RotaryCraft.Models.ModelBedrockBreakerV;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityBedrockBreaker extends TileEntityInventoriedPowerReceiver {
	private int harvestx;
	private int harvesty;
	private int harvestz;

	private double dropx;
	private double dropy;
	private double dropz;
	private ItemStack[] inv = new ItemStack[1];



	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("#@$@#$", (this.power)/1000.0D, omega));
		this.readPower(false);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d   %d   %d", this.power, this.torque, this.omega));
		if (this.operationComplete(tickcount, 0)) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("Hi %d", par5Random.nextInt(60)));
			this.process(world, x, y, z, meta);
			tickcount = 0;
		}
	}

	public void process(World world, int x, int y, int z, int metadata) {
		if (power >= MINPOWER && torque >= MINTORQUE) {
			if (this.getBlockInFront(world, x, y, z, metadata)) {
				//this.power -= CUTPOWER;
				this.grind(world, harvestx, harvesty, harvestz, metadata);
			}
			//if (this.power < CUTPOWER)
			//world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "mob.blaze.death", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F);
		}

	}

	public void readPower(boolean doublesided) {
		if (!this.getReceptor(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return;
		super.getPower(doublesided, false);
		power = (long)omega * (long)torque;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 1200-this.omega, "max")));
		return;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {

	}

	public boolean getReceptor(World world, int x, int y, int z, int metadata) {
		if (y == 0)
			return false;
		int id = 0;
		switch (metadata) {
		case 0:
			id = world.getBlockId(x-1, y, z);
			readx = x-1;
			readz = z;
			ready = y;
			break;
		case 1:
			id = world.getBlockId(x+1, y, z);
			readx = x+1;
			readz = z;
			ready = y;
			break;
		case 2:
			id = world.getBlockId(x, y, z-1);
			readx = x;
			readz = z-1;
			ready = y;
			break;
		case 3:
			id = world.getBlockId(x, y, z+1);
			readx = x;
			readz = z+1;
			ready = y;
			break;
		case 4:
			id = world.getBlockId(x, y-1, z);
			readx = x;
			readz = z;
			ready = y-1;
			break;
		case 5:
			id = world.getBlockId(x, y+1, z);
			readx = x;
			readz = z;
			ready = y+1;
			break;
		default:
			id = 0;
			break;
		}
		//ReikaWorldHelper.legacySetBlockWithNotify(world, readx, ready+1, readz, 4);
		return true;
	}

	public boolean getBlockInFront(World world, int x, int y, int z, int metadata) {
		if (y == 0)
			return false;
		int id;
		for (int a = 1; a < 6; a++) {
			switch (metadata) {
			case 0:
				id = world.getBlockId(x+a, y, z);
				harvestx = x+a;
				harvesty = y;
				harvestz = z;
				dropx = x+0.5;
				dropy = y+1.25;
				dropz = z+0.5;
				break;
			case 1:
				id = world.getBlockId(x-a, y, z);
				harvestx = x-a;
				harvesty = y;
				harvestz = z;
				dropx = x+0.5;
				dropy = y+1.25;
				dropz = z+0.5;
				break;
			case 2:
				id = world.getBlockId(x, y, z+a);
				harvestx = x;
				harvesty = y;
				harvestz = z+a;
				dropx = x+0.5;
				dropy = y+1.25;
				dropz = z+0.5;
				break;
			case 3:
				id = world.getBlockId(x, y, z-a);
				harvestx = x;
				harvesty = y;
				harvestz = z-a;
				dropx = x+0.5;
				dropy = y+1.25;
				dropz = z+0.5;
				break;
			case 4:
				id = world.getBlockId(x, y+a, z);
				harvestx = x;
				harvesty = y+a;
				harvestz = z;
				dropx = x+0.5;
				dropy = y+1.25;
				dropz = z+0.5;
				break;
			case 5:
				id = world.getBlockId(x, y-a, z);
				harvestx = x;
				harvesty = y-a;
				harvestz = z;
				dropx = x+0.5;
				dropy = y-0.25;
				dropz = z+0.5;
				break;
			default:
				id = 0;
				break;
			}
			if ((id == 7) || (id == RotaryCraft.bedrockslice.blockID))
				return true;
			if (id == 10 || id == 11) //If lava
				return false;
		}
		return false;
	}

	public void grind(World world, int x, int y, int z, int meta) {
		if (y <= 0)
			return;
		if (!world.isRemote) {
			if (world.getBlockId(harvestx, harvesty, harvestz) == 7) {
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.stone", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F);
				ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, harvestx, harvesty, harvestz, RotaryCraft.bedrockslice.blockID, 0);
			}
			else {
				int rockmetadata = world.getBlockMetadata(harvestx, harvesty, harvestz);
				if (rockmetadata < 15) {
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.stone", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F);
					ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, harvestx, harvesty, harvestz, RotaryCraft.bedrockslice.blockID, rockmetadata+1);
				}
				else {
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "mob.blaze.hit", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F);
					ReikaWorldHelper.legacySetBlockWithNotify(world, harvestx, harvesty, harvestz, 0);
					if (this.isInventoryFull())
						this.dropItem(world, x, y, z, meta, ItemStacks.bedrockdust.copy());
					else
						ReikaInventoryHelper.addOrSetStack(ItemStacks.bedrockdust, inv, 0);
					RotaryAchievements.BEDROCKBREAKER.triggerAchievement(this.getPlacer());
				}
			}
		}
	}

	private void dropItem(World world, int x, int y, int z, int meta, ItemStack is) {
		EntityItem itementity = new EntityItem(world, dropx, dropy, dropz, ItemStacks.bedrockdust.copy());
		ReikaJavaLibrary.pConsole(itementity.getEntityItem());
		itementity.delayBeforeCanPickup = 0;
		itementity.motionX = -0.025+0.05*par5Random.nextFloat();	// 0-0.5 m/s
		itementity.motionZ = -0.025+0.05*par5Random.nextFloat();
		if (meta != 5)
			itementity.motionY = 0.1+0.2*par5Random.nextFloat()+0.25*par5Random.nextFloat()*par5Random.nextInt(2);	// 2-6m/s up, + a 50/50 chance of 0-5 m/s more up
		itementity.velocityChanged = true;
		world.spawnEntityInWorld(itementity);
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.pop", 0.2F, ((par5Random.nextFloat() - par5Random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
	}

	public void dropItemFromInventory() {
		if (inv[0] == null)
			return;
		this.dropItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), ReikaItemHelper.getSizedItemStack(inv[0], 1));
		ReikaInventoryHelper.decrStack(0, inv);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		int dmg = world.getBlockMetadata(x, y, z);
		if (dmg < 4)
			return new ModelBedrockBreaker();
		else
			return new ModelBedrockBreakerV();
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
		return MachineRegistry.BEDROCKBREAKER.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.getBlockInFront(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return 15;
		return 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv [i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return false;
	}

	public boolean isInventoryFull() {
		if (inv[0] == null)
			return false;
		if (!ReikaItemHelper.matchStacks(ItemStacks.bedrockdust, inv[0]))
			return true;
		return inv[0].stackSize >= inv[0].getMaxStackSize();
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.FactorizationHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.BedrockDigEvent;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.InertIInv;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityBedrockBreaker extends InventoriedPowerReceiver implements InertIInv, DiscreteFunction {
	private int harvestx;
	private int harvesty;
	private int harvestz;

	private double dropx;
	private double dropy;
	private double dropz;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.readPower(false);
		if (tickcount >= this.getOperationTime()) {
			this.process(world, x, y, z, meta);
			tickcount = 0;
		}
		//ReikaJavaLibrary.pConsole(Arrays.toString(inv), Side.SERVER);
	}

	public void process(World world, int x, int y, int z, int metadata) {
		if (power >= MINPOWER && torque >= MINTORQUE && this.hasInventorySpace()) {
			if (this.getBlockInFront(world, x, y, z, metadata)) {
				this.grind(world, harvestx, harvesty, harvestz, metadata);
			}
		}
	}

	private boolean hasInventorySpace() {
		if (inv[0] == null)
			return true;
		if (!ReikaItemHelper.matchStacks(inv[0], ItemStacks.bedrockdust))
			return false;
		return inv[0].stackSize+DifficultyEffects.BEDROCKDUST.getInt() <= inv[0].getMaxStackSize();
	}

	public void readPower(boolean doublesided) {
		if (!this.getReceptor(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return;
		super.getPower(doublesided);
		power = (long)omega * (long)torque;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {

	}

	public boolean getReceptor(World world, int x, int y, int z, int metadata) {
		if (y == 0 && !ConfigRegistry.VOIDHOLE.getState())
			return false;
		int id = 0;
		switch (metadata) {
		case 0:
			id = world.getBlockId(x-1, y, z);
			read = ForgeDirection.WEST;
			break;
		case 1:
			id = world.getBlockId(x+1, y, z);
			read = ForgeDirection.EAST;
			break;
		case 2:
			id = world.getBlockId(x, y, z-1);
			read = ForgeDirection.NORTH;
			break;
		case 3:
			id = world.getBlockId(x, y, z+1);
			read = ForgeDirection.SOUTH;
			break;
		case 4:
			id = world.getBlockId(x, y-1, z);
			read = ForgeDirection.DOWN;
			break;
		case 5:
			id = world.getBlockId(x, y+1, z);
			read = ForgeDirection.UP;
			break;
		default:
			id = 0;
			break;
		}
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
			if (this.isBedrock(id))
				return true;
			if (id == RotaryCraft.bedrockslice.blockID)
				return true;
			if (id == 10 || id == 11) //If lava
				return false;
		}
		return false;
	}

	private boolean isBedrock(int id) {
		if (id == Block.bedrock.blockID)
			return true;
		if (id == FactorizationHandler.getInstance().bedrockID)
			return true;
		return false;
	}

	public void grind(World world, int x, int y, int z, int meta) {
		if (y <= 0)
			return;
		if (!world.isRemote) {
			if (this.isBedrock(world.getBlockId(harvestx, harvesty, harvestz))) {
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.stone", 0.5F, rand.nextFloat() * 0.4F + 0.8F);
				world.setBlock(harvestx, harvesty, harvestz, RotaryCraft.bedrockslice.blockID, 0, 3);
			}
			else {
				int rockmetadata = world.getBlockMetadata(harvestx, harvesty, harvestz);
				if (rockmetadata < 15) {
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.stone", 0.5F, rand.nextFloat() * 0.4F + 0.8F);
					world.setBlock(harvestx, harvesty, harvestz, RotaryCraft.bedrockslice.blockID, rockmetadata+1, 3);
				}
				else {
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "mob.blaze.hit", 0.5F, rand.nextFloat() * 0.4F + 0.8F);
					world.setBlock(harvestx, harvesty, harvestz, 0);
					if (this.isInventoryFull())
						this.dropItem(world, x, y, z, meta, this.getDrops());
					else
						ReikaInventoryHelper.addOrSetStack(this.getDrops(), inv, 0);
					RotaryAchievements.BEDROCKBREAKER.triggerAchievement(this.getPlacer());
					MinecraftForge.EVENT_BUS.post(new BedrockDigEvent(this, harvestx, harvesty, harvestz));
				}
			}
		}
	}

	private void dropItem(World world, int x, int y, int z, int meta, ItemStack is) {
		EntityItem itementity = new EntityItem(world, dropx, dropy, dropz, this.getDrops());
		itementity.delayBeforeCanPickup = 0;
		itementity.motionX = -0.025+0.05*rand.nextFloat();	// 0-0.5 m/s
		itementity.motionZ = -0.025+0.05*rand.nextFloat();
		if (meta != 5)
			itementity.motionY = 0.1+0.2*rand.nextFloat()+0.25*rand.nextFloat()*rand.nextInt(2);	// 2-6m/s up, + a 50/50 chance of 0-5 m/s more up
		itementity.velocityChanged = true;
		if (!world.isRemote)
			world.spawnEntityInWorld(itementity);
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
	}

	private ItemStack getDrops() {
		ItemStack dust = ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, DifficultyEffects.BEDROCKDUST.getInt());
		return dust;
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
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BEDROCKBREAKER;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.getBlockInFront(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return 15;
		return 0;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return false;
	}

	public boolean isInventoryFull() {
		if (inv[0] == null)
			return false;
		if (!ReikaItemHelper.matchStacks(ItemStacks.bedrockdust, inv[0]))
			return true;
		return inv[0].stackSize >= inv[0].getMaxStackSize();
	}

	@Override
	public void onEMP() {}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.BEDROCK.getOperationTime(omega);
	}
}

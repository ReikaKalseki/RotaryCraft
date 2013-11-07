/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.InertIInv;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntity1DTransmitter;
import Reika.RotaryCraft.Base.TileEntityPiping.Flow;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Models.ModelGearbox;
import Reika.RotaryCraft.Models.ModelGearbox16;
import Reika.RotaryCraft.Models.ModelGearbox4;
import Reika.RotaryCraft.Models.ModelGearbox8;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityGearbox extends TileEntity1DTransmitter implements ISidedInventory, PipeConnector, IFluidHandler {

	public boolean reduction = true; // Reduction gear if true, accelerator if false

	public int damage = 0;
	public static final int MAXLUBE = 24000;
	public ItemStack[] inv = new ItemStack[1];

	private HybridTank tank = new HybridTank("gear", MAXLUBE);

	public MaterialRegistry type;

	public void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (reduction) {
			if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
				omega = spl.omega/ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2*ratio;
					return;
				}
				if (favorbent) {
					torque = spl.torque/sratio*ratio;
				}
				else {
					torque = ratio*(int)(spl.torque*((sratio-1D)/(sratio)));
				}
			}
			else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
				omega = spl.omega/ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2*ratio;
					return;
				}
				if (favorbent) {
					torque = ratio*(int)(spl.torque*((sratio-1D)/(sratio)));
				}
				else {
					torque = spl.torque/sratio*ratio;
				}
			}
			else //We are not one of its write-to blocks
				return;
		}
		else {
			if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
				omega = spl.omega*ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2/ratio;
					return;
				}
				if (favorbent) {
					torque = spl.torque/sratio/ratio;
				}
				else {
					torque = (int)(spl.torque*((sratio-1D))/sratio)/(ratio);
				}
			}
			else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
				omega = spl.omega*ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2/ratio;
					return;
				}
				if (favorbent) {
					torque = (int)(spl.torque*((sratio-1D)/(sratio)));
				}
				else {
					torque = spl.torque/sratio/ratio;
				}
			}
			else //We are not one of its write-to blocks
				return;
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (type == null)
			type = MaterialRegistry.STEEL;
		tickcount++;
		power = omega*torque;
		this.getIOSides(world, x, y, z, this.getBlockMetadata());
		this.transferPower(world, x, y, z, meta);
		if (tickcount >= 20) { // Every 1s
			this.getLube(world, x, y, z, this.getBlockMetadata());
			tickcount = 0;
		}
		if (inv[0] != null && tank.getLevel()+ItemFuelLubeBucket.LUBE_VALUE*1000 <= MAXLUBE) {
			if (inv[0].itemID == ItemStacks.lubebucket.itemID && inv[0].getItemDamage() == ItemStacks.lubebucket.getItemDamage()) {
				inv[0] = new ItemStack(Item.bucketEmpty.itemID, 1, 0);
				tank.addLiquid(ItemFuelLubeBucket.LUBE_VALUE*1000, FluidRegistry.getFluid("lubricant"));
			}
		}

		this.basicPowerReceiver();
	}

	public void getLube(World world, int x, int y, int z, int metadata) {
		int oldlube = 0;

		if (rand.nextInt(3) == 0) {
			if (tank.isEmpty() && omega > 0 && rand.nextInt(16*omega/ratio+1) != 0) {
				switch(type) {
				case WOOD:
					damage += 6;	//2x original steel
					RotaryAchievements.DAMAGEGEARS.triggerAchievement(this.getPlacer());
					break;
				case STONE:
					damage += 3;	//== original steel
					RotaryAchievements.DAMAGEGEARS.triggerAchievement(this.getPlacer());
					break;
				case STEEL:
					damage++;		//1/3 original steel
					RotaryAchievements.DAMAGEGEARS.triggerAchievement(this.getPlacer());
					break;
				case DIAMOND:
					if (rand.nextInt(3) == 0) {
						damage++;
						RotaryAchievements.DAMAGEGEARS.triggerAchievement(this.getPlacer());
					}
					break;
				case BEDROCK:
					break;
				default:
					break;
				}//other types do not get damaged
				if (type.isDamageableGear()) {
					world.spawnParticle("crit", xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
					if (rand.nextInt(1+damage/3) > 0)
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
				}
			}
			else if (type.consumesLubricant()) {
				if (!tank.isEmpty() && omega > 0) {
					int chance = type.ordinal()+1;
					if (rand.nextInt(chance) == 0)
						tank.removeLiquid((int)ReikaMathLibrary.logbase(omega, 2));
				}
			}
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		while (metadata > 3)
			metadata -= 4;
		super.getIOSides(world, x, y, z, metadata, false);
	}

	public void getRatio() {
		int tratio = 1+this.getBlockMetadata()/4;
		ratio = (int)ReikaMathLibrary.intpow(2, tratio);
	}

	public void readFromCross(TileEntityShaft cross) {
		if (xCoord == cross.writex && zCoord == cross.writez) {
			if (reduction) {
				omega = cross.readomega[0]/ratio;
				torque = cross.readtorque[0]*ratio;
			}
			else {
				omega = cross.readomega[0]*ratio;
				torque = cross.readtorque[0]/ratio;
			}
		}
		else if (xCoord == cross.writex2 && zCoord == cross.writez2) {
			if (reduction) {
				omega = cross.readomega[1]/ratio;
				torque = cross.readtorque[1]*ratio;
			}
			else {
				omega = cross.readomega[1]*ratio;
				torque = cross.readtorque[1]/ratio;
			}
		}
		else
			return; //not its output
	}

	@Override
	public void transferPower(World world, int x, int y, int z, int meta) {
		this.getRatio();
		omegain = torquein = 0;
		ready = y;
		TileEntity te = world.getBlockTileEntity(readx, ready, readz);
		MachineRegistry m = MachineRegistry.getMachine(world, readx, ready, readz);
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft devicein = (TileEntityShaft)world.getBlockTileEntity(readx, y, readz);
			if (devicein.getBlockMetadata() >= 6) {
				this.readFromCross(devicein);
				return;
			}
			if (devicein.writex == x && devicein.writey == y && devicein.writez == z) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}
		if (te instanceof SimpleProvider) {
			this.copyStandardPower(worldObj, readx, ready, readz);
		}
		if (te instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
			if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
				torquein = sp.getTorque();
				omegain = sp.getOmega();
			}
		}
		if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter devicein = (TileEntitySplitter)world.getBlockTileEntity(readx, y, readz);
			if (devicein.getBlockMetadata() >= 8) {
				this.readFromSplitter(devicein);
				return;
			}
			else if (devicein.writex == x && devicein.writez == z) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}

		if (reduction) {
			omega = omegain / ratio;
			if (torquein <= RotaryConfig.torquelimit/ratio)
				torque = torquein * ratio;
			else {
				torque = RotaryConfig.torquelimit;
				world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
			}
		}
		else {
			if (omegain <= RotaryConfig.omegalimit/ratio)
				omega = omegain * ratio;
			else {
				omega = RotaryConfig.omegalimit;
				world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
			}
			torque = torquein / ratio;
		}
		torque *= ReikaMathLibrary.doubpow(0.99, damage);
	}

	public int getLubricantScaled(int par1)
	{
		return (tank.getLevel()*par1)/MAXLUBE;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setBoolean("reduction", reduction);
		NBT.setInteger("damage", damage);

		tank.writeToNBT(NBT);

		NBT.setInteger("type", type.ordinal());
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		reduction = NBT.getBoolean("reduction");
		damage = NBT.getInteger("damage");

		tank.readFromNBT(NBT);

		type = MaterialRegistry.setType(NBT.getInteger("type"));
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		switch(ratio) {
		case 2:
			return new ModelGearbox();
		case 4:
			return new ModelGearbox4();
		case 8:
			return new ModelGearbox8();
		case 16:
			return new ModelGearbox16();
		default:
			return null;
		}
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		if (inv[par1] != null)
		{
			if (inv[par1].stackSize <= par2)
			{
				ItemStack itemstack = inv[par1];
				inv[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inv[par1].splitStack(par2);

			if (inv[par1].stackSize <= 0)
			{
				inv[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	/**
	 *
	 *
	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inv[par1] != null)
		{
			ItemStack itemstack = inv[par1];
			inv[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 *
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (itemstack == null)
			return false;
		return (itemstack.itemID == ItemStacks.lubebucket.itemID && itemstack.getItemDamage() == ItemStacks.lubebucket.getItemDamage());
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.GEARBOX.ordinal();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return itemstack.itemID == Item.bucketEmpty.itemID;
	}

	@Override
	public int getRedstoneOverride() {
		return 15*tank.getLevel()/MAXLUBE;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side != ForgeDirection.DOWN;
	}

	@Override
	public void onEMP() {}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.canFill(from, resource.getFluid())) {
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP && fluid.equals(FluidRegistry.getFluid("lubricant"));
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public void setLubricant(int amt) {
		tank.setContents(amt, RotaryCraft.lubeFluid);
	}

	public int getLubricant() {
		return tank.getLevel();
	}

	@Override
	public boolean isFlipped() {
		return isFlipped;
	}

	@Override
	public void setFlipped(boolean set) {
		isFlipped = set;
	}

	public int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided(this);
	}

	public boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isItemValidForSlot(i, is);
	}

	public final String getInvName() {
		return this.getMultiValuedName();
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		return this.isPlayerAccessible(var1);
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side != ForgeDirection.UP ? Flow.INPUT : Flow.NONE;
	}
}

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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Interfaces.TextureFetcher;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.RotaryRenderList;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class RotaryCraftTileEntity extends TileEntity implements RenderFetcher {

	public final Random par5Random = new Random();
	protected RotaryModelBase rmb;
	protected int tickcount = 0;
	/** Rotational speed in radians per render tick. */
	public float phi = 0;
	public boolean shutDown = false;
	protected int pseudometa = 0;

	public int[] paint = {-1, -1, -1};

	public String placer;

	@Override
	public final boolean canUpdate() {
		return true;
	}

	public abstract RotaryModelBase getTEModel(World world, int x, int y, int z);

	public abstract void animateWithTick(World world, int x, int y, int z);

	public abstract int getMachineIndex();

	public final TextureFetcher getRenderer() {
		if (this.getMachine().hasRender())
			return RotaryRenderList.getRenderForMachine(this.getMachine());
		else
			return null;
	}

	public final String getInvName() {
		return this.getMultiValuedName();
	}

	public final MachineRegistry getMachine() {
		return MachineRegistry.machineList[this.getMachineIndex()];
	}

	public final String getName() {
		return MachineRegistry.machineList[this.getMachineIndex()].getName();
	}

	public final String getMultiValuedName() {
		if (MachineRegistry.machineList[this.getMachineIndex()].isMultiNamed())
			return MachineRegistry.machineList[this.getMachineIndex()].getMultiName(this);
		return MachineRegistry.machineList[this.getMachineIndex()].getName();
	}

	public final int getPseudoMeta() {
		return pseudometa;
	}

	@Override //Overwritten to allow use of original code
	public final int getBlockMetadata() {
		return this.getPseudoMeta();
	}

	public final void setBlockMetadata(int meta) {
		pseudometa = meta;
	}

	//public abstract int getMachineIndex();

	public final int getTileEntityBlockID() {
		String name = this.getName();
		//return RotaryConfig.IDLookup(name);
		return MachineRegistry.machineList[this.getMachineIndex()].getBlockID();
	}

	public void giveNoSuperWarning() {
		ReikaJavaLibrary.spamConsole("TileEntity "+this.getName()+" does not call super()!");
		ReikaChatHelper.write("TileEntity "+this.getName()+" does not call super()!");
	}

	public boolean isInWorld() {
		return worldObj != null;
	}

	public boolean isIDTEMatch(World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
			return false;
		if (!(te instanceof RotaryCraftTileEntity))
			return false;
		RotaryCraftTileEntity rc = (RotaryCraftTileEntity)te;
		int id = world.getBlockId(x, y, z);
		if (id != rc.getTileEntityBlockID())
			return false;
		return true;
	}

	@Override //To avoid null pointers in inventory
	public Block getBlockType() {
		//ReikaJavaLibrary.pConsole(this.blockType);
		if (blockType != null)
			return blockType;
		if (this.isInWorld()) {
			blockType = Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
		}
		else {
			return blockType = Block.blocksList[this.getTileEntityBlockID()];
		}
		return blockType;
	}

	//To avoid null pointers in inventory
	public int getRealBlockMetadata() {
		if (blockMetadata != -1)
			return blockMetadata;
		if (!this.isInWorld())
			return 0;
		blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		return blockMetadata;
	}

	@Override
	public final void updateEntity() {
		if (shutDown)
			return;
		//this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.updateTileEntity();
		this.updateEntity(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata());
	}

	private void updateTileEntity() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		rmb = this.getTEModel(worldObj, xCoord, yCoord, zCoord);
		this.animateWithTick(worldObj, xCoord, yCoord, zCoord);
	}

	public abstract void updateEntity(World world, int x, int y, int z, int meta);

	@Override
	public final Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToNBT(var1);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, var1);
	}

	@Override
	public final void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet)
	{
		this.readFromNBT(packet.customParam1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final double getMaxRenderDistanceSquared() {
		return 4096D;
	}

	@Override
	public final boolean shouldRenderInPass(int pass)
	{
		if (!this.isInWorld())
			return true;
		if (pass == 0)
			return true;
		if (pass == 1 && (this.hasModelTransparency() || (MachineRegistry.machineList[this.getMachineIndex()].hasModel() && this instanceof TileEntityIOMachine)))
			return true;
		return false;
	}

	public abstract boolean hasModelTransparency();

	public Block getTEBlock() {
		int id = this.getTileEntityBlockID();
		if (id == 0)
			ReikaJavaLibrary.pConsole("TileEntity "+this+" tried to register ID 0!");
		if (id >= Block.blocksList.length) {
			ReikaJavaLibrary.pConsole(id+" is an invalid block ID for "+this+"!");
			return null;
		}
		return Block.blocksList[id];
	}

	public static boolean isStandard8mReach(EntityPlayer ep, TileEntity te) {
		double dist = ReikaMathLibrary.py3d(te.xCoord+0.5-ep.posX, te.yCoord+0.5-ep.posY, te.zCoord+0.5-ep.posZ);
		return (dist <= 8);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setFloat("phi", phi);
		NBT.setInteger("tick", tickcount);
		NBT.setInteger("meta", pseudometa);
		if (placer != null && !placer.isEmpty())
			NBT.setString("place", placer);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		phi = NBT.getFloat("phi");
		tickcount = NBT.getInteger("tick");
		pseudometa = NBT.getInteger("meta");
		placer = NBT.getString("place");
	}

	public int[] getAccessibleSlotsFromSide(int var1) {
		return ReikaInventoryHelper.getWholeInventoryForISided((ISidedInventory)this);
	}

	public boolean canInsertItem(int i, ItemStack is, int side) {
		return ((IInventory)this).isStackValidForSlot(i, is);
	}
	/*
	public String getName() {
		return Block.blocksList[this.getTileEntityBlockID()].getLocalizedName();
	}*/

	public boolean isSelfBlock() {
		if (worldObj.getBlockId(xCoord, yCoord, zCoord) != this.getTileEntityBlockID())
			return false;
		int meta = this.getMachine().getMachineMetadata();
		return ReikaMathLibrary.isValueInsideBoundsIncl(meta, meta+this.getMachine().getNumberMetadatas()-1, worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}

	public abstract int getRedstoneOverride();

	public EntityPlayer getPlacer() {
		return worldObj.getPlayerEntityByName(placer);
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		if (RotaryConfig.lockMachines && !var1.getEntityName().equals(placer)) {
			ReikaChatHelper.write("This "+this.getName()+" is locked and can only be used by "+placer+"!");
			return false;
		}
		double dist = ReikaMathLibrary.py3d(xCoord+0.5-var1.posX, yCoord+0.5-var1.posY, zCoord+0.5-var1.posZ);
		return (dist <= 8) && worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this;
	}
}

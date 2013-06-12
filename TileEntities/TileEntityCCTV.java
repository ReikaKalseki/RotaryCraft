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

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.ReikaPhysicsHelper;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Models.ModelCCTV;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCCTV extends RotaryCraftTileEntity implements ISidedInventory {

	public boolean cameraIsMoved = false;
	private double[] playerCam = new double[5];
	private double[] cameraPos = new double[5];
	private Minecraft mc = Minecraft.getMinecraft();
	public String owner;
	public EntityPlayer clicked;
	public float theta;
	public static final boolean ISCAMERAMODE = true;
	private boolean on = false;
	private int tickcount2 = 0;
	public int[] colors = new int[3];

	public ItemStack[] inv = new ItemStack[4];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (theta > 60) {
			theta = -60;
		}
		if (theta < -60) {
			theta = 60;
		}
		if (theta > 0)
			this.setBlockMetadata(1);
		if (theta < 0)
			this.setBlockMetadata(0);
		this.setColors();
		if (inv[0] == null) {
			on = false;
			return;
		}
		if (inv[0].itemID != ItemRegistry.SPRING.getID()) {
			on = false;
			return;
		}
		if (inv[0].getItemDamage() <= 0) {
			on = false;
			return;
		}
		tickcount2++;
		int dmg = inv[0].getItemDamage();
		if (tickcount2 > 120) {
			ItemStack is = new ItemStack(ItemRegistry.SPRING.getID(), 1, dmg-1);
			inv[0] = is;
			tickcount2 = 0;
		}
		on = true;
		if (cameraIsMoved) {
			this.moveCameraToLook(clicked);
			EntityPlayer e = world.getPlayerEntityByName(owner);
			tickcount++;
		}
		//ReikaJavaLibrary.pConsole("X: "+e.posX+"  Y: "+e.posY+"  Z: "+e.posZ+"  Y: "+e.rotationYaw+"  P: "+e.rotationPitch);
		//ReikaJavaLibrary.pConsole("X: "+playerCam[0]+"  Y: "+playerCam[1]+"  Z: "+playerCam[2]+"  Y: "+playerCam[3]+"  P: "+playerCam[4]);
		//ReikaJavaLibrary.pConsole("X: "+cameraPos[0]+"  Y: "+cameraPos[1]+"  Z: "+cameraPos[2]+"  Y: "+cameraPos[3]+"  P: "+cameraPos[4]);
		double[] dd = ReikaPhysicsHelper.polarToCartesian(1, theta, phi);
		//ReikaJavaLibrary.pConsole(dd[0]+"  "+dd[1]+"  "+dd[2]);

		this.setLook(x+0.5+dd[2], y+0.25-dd[1], z+0.40625+dd[0], -phi, theta);
		if (tickcount < 20)
			;//return;
		if (!cameraIsMoved)
			return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_BACKSLASH) && inv[0] != null && inv[0].itemID == ItemRegistry.SPRING.getID() && inv[0].getItemDamage() > 0)
			return;
		tickcount = 0;
		this.moveCameraToPlayer();
	}

	private void setColors() {
		for (int i = 0; i < 3; i++) {
			if (inv[i+1] == null)
				colors[i] = -1;
			else if (inv[i+1].itemID != Item.dyePowder.itemID)
				colors[i] = -1;
			else
				colors[i] = inv[i+1].getItemDamage();
			if (colors[i] == -1)
				on = false;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelCCTV();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	public void setLook(double x, double y, double z, double t, double p) {
		cameraPos[0] = x;
		cameraPos[1] = y;
		cameraPos[2] = z;
		cameraPos[3] = t;
		cameraPos[4] = p;
	}

	public void moveCameraToLook(EntityPlayer ep) {
		if (!on)
			return;
		clicked = ep;
		if (!cameraIsMoved)
			this.setPlayerCam();
		cameraIsMoved = true;
		this.alignCameras(false);
	}

	private void setPlayerCam() {
		EntityLiving e = mc.renderViewEntity;
		if (e == null)
			return;
		playerCam[0] = e.posX;
		playerCam[1] = e.posY;
		playerCam[2] = e.posZ;
		playerCam[3] = e.rotationYaw;
		while(playerCam[3] < 0)
			playerCam[3] += 360;
		while(playerCam[3] >= 360)
			playerCam[3] -= 360;
		playerCam[4] = e.rotationPitch;
		//ReikaJavaLibrary.pConsole(playerCam[3]);
	}

	public void moveCameraToPlayer() {
		for (int i = 0; i < 5; i++)
			cameraPos[i] = playerCam[i];
		cameraIsMoved = false;
		this.alignCameras(true);
	}

	/** Actually moves the ingame camera to the preset coords */
	private void alignCameras(boolean toPlayer) {
		EntityLiving e = mc.renderViewEntity;
		if (toPlayer) {
			e.posX = playerCam[0];
			e.posY = playerCam[1]+e.getEyeHeight();
			e.posZ = playerCam[2];
			e.rotationYaw = (float)playerCam[3];
			e.rotationPitch = (float)playerCam[4];
		}
		else {
			e.posX = cameraPos[0];
			e.posY = cameraPos[1];
			e.posZ = cameraPos[2];
			e.rotationYaw = (float)cameraPos[3];
			e.rotationPitch = (float)cameraPos[4];
		}
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.CCTV.ordinal();
	}

	@Override
	public int getSizeInventory() {
		return 4;
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

			if (inv[par1].stackSize == 0)
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

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
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
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is) {
		if (i == 0)
			return is.itemID == ItemRegistry.SPRING.getID();
		return is.itemID == Item.dyePowder.itemID;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		theta = NBT.getFloat("thetad");
		owner = NBT.getString("sowner");
		cameraIsMoved = NBT.getBoolean("moved");
		colors = NBT.getIntArray("color");
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

	/**
	 * Writes a tile entity to NBT.  Maybe was not saving inv since seems to be acting like
	 * extends TileEntityPowerReceiver, NOT InventoriedPowerReceiver
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		if (owner != null && !owner.isEmpty())
			NBT.setString("sowner", owner);
		NBT.setFloat("thetad", theta);
		NBT.setBoolean("moved", cameraIsMoved);
		NBT.setIntArray("color", colors);
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

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}

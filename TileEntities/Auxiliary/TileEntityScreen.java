/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.RemoteControlMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityScreen extends InventoriedPowerReceiver {

	public int channel = 0;

	/** Stores things as key-value as colors[]-xyz[] */
	private HashMap<int[], int[]> cameras = new HashMap<int[], int[]>();

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SCREEN;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	public void activate(EntityPlayer ep) {
		if (!this.canRun())
			return;
		int[] cameraPos = this.getCameraFromColors(worldObj);
		if (!this.isValidCamera(cameraPos))
			return;
		RemoteControlMachine te = (RemoteControlMachine)worldObj.getBlockTileEntity(cameraPos[0], cameraPos[1], cameraPos[2]);
		//te.moveCameraToLook(ep);
		te.activate(worldObj, ep, cameraPos[0], cameraPos[1], cameraPos[2]);
	}

	private boolean isValidCamera(int[] cameraPos) {
		if (cameraPos[0] == xCoord && cameraPos[1] == yCoord && cameraPos[2] == zCoord)
			return false;
		return (worldObj.getBlockTileEntity(cameraPos[0], cameraPos[1], cameraPos[2]) instanceof RemoteControlMachine);
	}

	private boolean canRun() {
		if (power < MINPOWER)
			return false;
		if (torque < MINTORQUE)
			return false;
		if (inv[0] == null || inv[1] == null || inv[2] == null)
			return false;
		int dye = Item.dyePowder.itemID;
		if (inv[0].itemID != dye || inv[1].itemID != dye || inv[2].itemID != dye)
			return false;
		return true;
	}

	private int[] getCameraFromColors(World world) {
		int[] pos = {xCoord, yCoord, zCoord};
		int[] colors = {inv[0].getItemDamage(), inv[1].getItemDamage(), inv[2].getItemDamage()};
		if (!cameras.containsKey(colors))
			if (!this.searchForMatchingCamera(world, colors))
				return pos;
		pos = cameras.get(colors);
		return pos;
	}

	private boolean searchForMatchingCamera(World world, int[] colors) {
		int range = 64;
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				for (int k = -range; k <= range; k++) {
					TileEntity te = world.getBlockTileEntity(xCoord+i, yCoord+j, zCoord+k);
					if (te != null) {
						if (te instanceof RemoteControlMachine) {
							RemoteControlMachine cc = (RemoteControlMachine)te;
							int[] camcolor = {cc.colors[0], cc.colors[1], cc.colors[2]};
							if (camcolor[0] == colors[0] && camcolor[1] == colors[1] && camcolor[2] == colors[2]) {
								cameras.put(colors, new int[]{cc.xCoord, cc.yCoord, cc.zCoord});
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.itemID == Item.dyePowder.itemID;
	}

	@Override
	public int getRedstoneOverride() {
		int[] cam = this.getCameraFromColors(worldObj);
		if (this.isValidCamera(cam))
			return 0;
		return 15;
	}

}

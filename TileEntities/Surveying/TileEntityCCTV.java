/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.RotaryCraft.Base.TileEntity.RemoteControlMachine;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityCCTV extends RemoteControlMachine {

	public boolean cameraIsMoved = false;
	private double[] playerCam = new double[5];
	private double[] cameraPos = new double[5];
	public String owner;
	public EntityPlayer clicked;
	public float theta;
	public static final boolean ISCAMERAMODE = true;

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
		if (!this.hasCoil()) {
			on = false;
			return;
		}
		tickcount2++;
		if (tickcount2 > this.getUnwindTime()) {
			ItemStack is = this.getDecrementedCharged();
			inv[0] = is;
			tickcount2 = 0;
		}

		//EntityPlayer ep = world.getClosestPlayer(x, y, z, -1);
		//ReikaJavaLibrary.pConsole(String.format("%f,  %f, %f", ep.posX, ep.posY, ep.posZ)+" on "+FMLCommonHandler.instance().getEffectiveSide());


		EntityPlayer e = world.getPlayerEntityByName(owner);
		on = true;
		if (cameraIsMoved) {
			//this.moveCameraToLook(clicked);
			this.movePlayerToCamera(e);
			tickcount++;
		}
		//ReikaJavaLibrary.pConsole("X: "+e.posX+"  Y: "+e.posY+"  Z: "+e.posZ+"  Y: "+e.rotationYaw+"  P: "+e.rotationPitch);
		//ReikaJavaLibrary.pConsole("X: "+playerCam[0]+"  Y: "+playerCam[1]+"  Z: "+playerCam[2]+"  Y: "+playerCam[3]+"  P: "+playerCam[4]);
		//ReikaJavaLibrary.pConsole("X: "+cameraPos[0]+"  Y: "+cameraPos[1]+"  Z: "+cameraPos[2]+"  Y: "+cameraPos[3]+"  P: "+cameraPos[4]);
		double[] dd = ReikaPhysicsHelper.polarToCartesian(1, theta, phi);
		//ReikaJavaLibrary.pConsole(dd[0]+"  "+dd[1]+"  "+dd[2]);

		//this.setLook(x+0.5+dd[2], y+0.25-dd[1], z+0.40625+dd[0], -phi, theta);
		if (tickcount < 20)
			;//return;
		if (!cameraIsMoved)
			return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_BACKSLASH) && inv[0] != null && inv[0].itemID == ItemRegistry.SPRING.getShiftedID() && inv[0].getItemDamage() > 0)
			return;
		tickcount = 0;
		this.movePlayerBack(e);
		//this.moveCameraToPlayer();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

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
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase e = mc.renderViewEntity;
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
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase e = mc.renderViewEntity;
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
	public MachineRegistry getMachine() {
		return MachineRegistry.CCTV;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		theta = NBT.getFloat("thetad");
		owner = NBT.getString("sowner");
		cameraIsMoved = NBT.getBoolean("moved");
	}

	/**
	 * Writes a tile entity to NBT.  Maybe was not saving inv since seems to be acting like
	 * extends TileEntityPowerReceiver, NOT InventoriedPowerReceiver
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		if (owner != null && !owner.isEmpty())
			NBT.setString("sowner", owner);
		NBT.setFloat("thetad", theta);
		NBT.setBoolean("moved", cameraIsMoved);
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private void movePlayerToCamera(EntityPlayer ep) {
		if (ep == null)
			return;
		cameraIsMoved = true;
		double[] dd = ReikaPhysicsHelper.polarToCartesian(1, theta, phi);
		double dx = 0.5+dd[2];
		double dy = 0.25-dd[1]-ep.getEyeHeight();
		double dz = 0.40625+dd[0];
		playerCam[0] = ep.posX;
		playerCam[1] = ep.posY;
		playerCam[2] = ep.posZ;
		playerCam[3] = ep.rotationYaw;
		playerCam[4] = ep.rotationPitch;

		ep.posX = xCoord+dx;
		ep.posY = yCoord+dy;
		ep.posZ = zCoord+dz;
		ep.rotationYaw = -phi;
		ep.rotationPitch = theta;

		owner = ep.getEntityName();

		ep.capabilities.allowEdit = false;
		//ep.setGameType(EnumGameType.ADVENTURE);
		ep.capabilities.disableDamage = true;

		//ReikaJavaLibrary.pConsole(String.format("%f,  %f, %f", ep.posX, ep.posY, ep.posZ)+" on "+FMLCommonHandler.instance().getEffectiveSide());

		//ReikaChatHelper.writeCoords(ep.worldObj, ep.posX, ep.posY, ep.posZ);
	}

	private void movePlayerBack(EntityPlayer ep) {
		cameraIsMoved = false;
		ep.posX = playerCam[0];
		ep.posY = playerCam[1];
		ep.posZ = playerCam[2];
		ep.rotationYaw = (float) playerCam[3];
		ep.rotationPitch = (float) playerCam[4];
	}

	@Override
	public void activate(World world, EntityPlayer ep, int x, int y, int z) {
		this.movePlayerToCamera(ep);
	}
}

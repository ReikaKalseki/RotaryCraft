/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Component;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.ManagedPeripheral;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Interfaces.TextureFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RotaryRenderList;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;

public abstract class RotaryCraftTileEntity extends TileEntityBase implements RenderFetcher, IPeripheral, Environment, ManagedPeripheral {

	protected RotaryModelBase rmb;
	protected int tickcount = 0;
	/** Rotational position. */
	public float phi = 0;

	/** For emp */
	private boolean disabled;

	public int[] paint = {-1, -1, -1};

	protected StepTimer second = new StepTimer(20);

	public boolean isFlipped = false;

	private final HashMap<Integer, LuaMethod> luaMethods = new HashMap();
	private final HashMap<String, LuaMethod> methodNames = new HashMap();

	@Override
	public final boolean canUpdate() {
		return !RotaryCraft.instance.isLocked();
	}

	public int getTick() {
		return tickcount;
	}

	@Override
	protected abstract void animateWithTick(World world, int x, int y, int z);

	public abstract MachineRegistry getMachine();

	@Override
	@SideOnly(Side.CLIENT)
	public final TextureFetcher getRenderer() {
		return this.getTileRenderer();
	}

	@SideOnly(Side.CLIENT)
	public final RotaryTERenderer getTileRenderer() {
		if (this.getMachine().hasRender())
			return RotaryRenderList.getRenderForMachine(this.getMachine());
		else
			return null;
	}

	public final int getMachineIndex() {
		return this.getMachine().ordinal();
	}

	public final String getName() {
		return this.getMachine().getName();
	}

	public final String getMultiValuedName() {
		if (this.getMachine().isMultiNamed())
			return this.getMachine().getMultiName(this);
		return this.getMachine().getName();
	}

	//public abstract int getMachineIndex();

	@Override
	public final int getTileEntityBlockID() {
		return this.getMachine().getBlockID();
	}

	public final MachineRegistry getMachine(ForgeDirection dir) {
		int x = xCoord+dir.offsetX;
		int y = yCoord+dir.offsetY;
		int z = zCoord+dir.offsetZ;
		return MachineRegistry.getMachine(worldObj, x, y, z);
	}

	public void giveNoSuperWarning() {
		ReikaJavaLibrary.pConsole("TileEntity "+this.getName()+" does not call super()!");
		ReikaChatHelper.write("TileEntity "+this.getName()+" does not call super()!");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 4096D;
	}

	@Override
	public final boolean shouldRenderInPass(int pass)
	{
		if (!this.isInWorld())
			return true;
		if (pass == 0)
			return true;
		if (this.getMachine().hasModel() && this instanceof TileEntityIOMachine)
			return true;
		if (pass == 1 && (this.hasModelTransparency() || this.getMachine().renderInPass1()))
			return true;
		return false;
	}

	public abstract boolean hasModelTransparency();

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		if (phi >= 360)
			phi = phi%360;
		NBT.setFloat("phi", phi);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		phi = NBT.getFloat("phi");
		if (phi >= 360)
			phi = phi%360;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBT.setInteger("tick", tickcount);
		NBT.setBoolean("emp", disabled);

		if (node != null)
			node.save(NBT);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		tickcount = NBT.getInteger("tick");
		disabled = NBT.getBoolean("emp");

		if (node != null)
			node.load(NBT);
	}

	public boolean isSelfBlock() {
		if (worldObj.getBlockId(xCoord, yCoord, zCoord) != this.getTileEntityBlockID())
			return false;
		int meta = this.getMachine().getMachineMetadata();
		return meta == worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public boolean isPlayerAccessible(EntityPlayer var1) {
		if (ConfigRegistry.LOCKMACHINES.getState() && !var1.getEntityName().equals(placer)) {
			ReikaChatHelper.write("This "+this.getName()+" is locked and can only be used by "+placer+"!");
			return false;
		}
		double dist = ReikaMathLibrary.py3d(xCoord+0.5-var1.posX, yCoord+0.5-var1.posY, zCoord+0.5-var1.posZ);
		return (dist <= 8) && worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this;
	}

	@Override
	public String getTEName() {
		MachineRegistry m = this.getMachine();
		if (m.isMultiNamed())
			return m.getMultiName(this);
		return this.getName();
	}

	public boolean isShutdown() {
		return disabled;
	}

	public void onEMP() {
		disabled = true;
		if (this instanceof TileEntityIOMachine) {
			TileEntityIOMachine io = (TileEntityIOMachine)this;
			io.power = 0;
			io.torque = 0;
			io.omega = 0;
		}
	}

	public int getTextureStateForSide(int s) {
		switch(this.getBlockMetadata()) {
		case 0:
			return s == 4 ? this.getActiveTexture() : 0;
		case 1:
			return s == 5 ? this.getActiveTexture() : 0;
		case 2:
			return s == 2 ? this.getActiveTexture() : 0;
		case 3:
			return s == 3 ? this.getActiveTexture() : 0;
		}
		return 0;
	}

	protected int getActiveTexture() {
		return 0;
	}

	public Icon getIconForSide(ForgeDirection dir) {
		return RotaryCraft.decoblock.getIcon(0, 0);
	}

	public boolean hasIconOverride() {
		return false;
	}

	public boolean matchMachine(IBlockAccess world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		int id2 = this.getTileEntityBlockID();
		int meta2 = this.getMachine().getMachineMetadata();
		return id2 == id && meta2 == meta;
	}

	@Override
	public final int getPacketDelay() {
		return DragonAPICore.isSinglePlayer() ? 1 : Math.min(20, ConfigRegistry.PACKETDELAY.getValue());
	}


	/** ComputerCraft */
	@Override
	public final String[] getMethodNames() {
		ArrayList<LuaMethod> li = new ArrayList();
		List<LuaMethod> all = LuaMethod.getMethods();
		for (int i = 0; i < all.size(); i++) {
			LuaMethod l = all.get(i);
			if (l.isValidFor(this))
				li.add(l);
		}
		String[] s = new String[li.size()];
		for (int i = 0; i < s.length; i++) {
			LuaMethod l = li.get(i);
			s[i] = l.displayName;
			luaMethods.put(i, l);
			methodNames.put(l.displayName, l);
		}
		return s;
	}

	@Override
	public final Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return luaMethods.containsKey(method) ? luaMethods.get(method).invoke(this, arguments) : null;
	}

	@Override
	public final boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public final void attach(IComputerAccess computer) {}
	@Override
	public final void detach(IComputerAccess computer) {}

	@Override
	public final String getType() {
		return this.getName().replaceAll(" ", "");
	}

	/** OpenComputers */
	public final String getComponentName() {
		return this.getType();
	}

	@Override
	public final String[] methods() {
		return this.getMethodNames();
	}

	@Override
	public final Object[] invoke(String method, Context context, Arguments args) throws Exception {
		Object[] objs = new Object[args.count()];
		for (int i = 0; i < objs.length; i++) {
			objs[i] = args.checkAny(i);
		}
		return methodNames.containsKey(method) ? methodNames.get(method).invoke(this, objs) : null;
	}

	@Override
	public final void onChunkUnload() {
		super.onChunkUnload();
		if (node != null)
			node.remove();
	}

	@Override
	public final void invalidate() {
		super.invalidate();
		if (node != null)
			node.remove();
	}

	private final Component node = this.createNode();

	private Component createNode() {
		if (ModList.OPENCOMPUTERS.isLoaded())
			return Network.newNode(this, Visibility.Network).withComponent(this.getType(), this.getOCNetworkVisibility()).create();
		else
			return null;
	}

	protected final Visibility getOCNetworkVisibility() {
		if (this.getMachine().isTransmissionMachine())
			return this.getMachine().isAdvancedTransmission() ? Visibility.Network : Visibility.Neighbors;
		else if (this.getMachine().isPipe())
			return Visibility.Neighbors;
		else
			return this instanceof TileEntityBeltHub ? Visibility.Neighbors : Visibility.Network;
	}

	@Override
	public final Node node() {
		return node;
	}

	@Override
	public final void onConnect(Node node) {}
	@Override
	public final void onDisconnect(Node node) {}
	@Override
	public final void onMessage(Message message) {}

}

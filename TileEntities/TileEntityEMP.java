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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.aura.AuraNode;
import thaumcraft.api.aura.EnumNodeType;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.ModInteract.ReikaThaumHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityEMP extends TileEntityPowerReceiver implements RangedEffect {

	private List<TileEntity> blocks = new ArrayList<TileEntity>();

	private static List<Class<? extends TileEntity>> blacklist = new ArrayList<Class<? extends TileEntity>>();

	public static final double BLAST_ENERGY = 4.814*1000000000000D; //1 kiloton

	private long energy;

	static {
		addEntry(TileEntityChest.class);
		addEntry(TileEntityEnderChest.class);
		addEntry(TileEntityHopper.class);
		addEntry(TileEntityBrewingStand.class);
		addEntry(TileEntityEnchantmentTable.class);
		addEntry(TileEntityEndPortal.class);
		addEntry(TileEntitySign.class);
		addEntry(TileEntitySkull.class);

		addEntry("buildcraft.factory.TileTank");
		addEntry("buildcraft.transport.PipeTransportItems");
		addEntry("buildcraft.transport.PipeTransportLiquids");
		addEntry("buildcraft.transport.PipeTransportPower");

		addEntry("TE conduits");

		addEntry("IC2 Wires");
		addEntry("Ender Tank");

		addEntry("Thaum tables and cauldrons");

		/* IMMUNITY LIST (some are conditional)
		addEntry(TileEntityShaft.class);
		addEntry(TileEntityGearBevel.class);
		addEntry(TileEntityAdvancedGear.class);
		addEntry(TileEntityBeamMirror.class);
		addEntry(TileEntityBlastFurnace.class);
		addEntry(TileEntityBedrockBreaker.class);
		addEntry(TileEntityCoolingFin.class);
		addEntry(TileEntityFan.class);
		addEntry(TileEntityFlywheel.class);
		addEntry(TileEntityFurnaceHeater.class);
		addEntry(TileEntityGearbox.class);
		addEntry(TileEntityGrinder.class);
		addEntry(TileEntityHose.class);
		addEntry(TileEntityFuelLine.class);
		addEntry(TileEntityPipe.class);
		addEntry(TileEntityMirror.class);
		addEntry(TileEntityPump.class);
		addEntry(TileEntityReservoir.class);
		addEntry(TileEntityPurifier.class);
		addEntry(TileEntitySolar.class);
		addEntry(TileEntityVacuum.class);
		addEntry(TileEntityWinder.class);
		addEntry(TileEntityWoodcutter.class);
		addEntry(TileEntityWorktable.class);
		addEntry(TileEntityEngine.class);*/

		addEntry("Reika.FurryKingdoms.TileEntities.TileEntityFlag");
	}

	private static void addEntry(Class<? extends TileEntity> cl) {
		blacklist.add(cl);
	}

	private static void addEntry(String name) {
		Class cl;
		try {
			cl = Class.forName(name);
			blacklist.add(cl);
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.logError("Could not add EMP blacklist for "+name);
			e.printStackTrace();
		}
	}

	private boolean fired = false;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.EMP.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;

		this.getSummativeSidedPower();

		if (fired)
			return;

		this.createListing();

		energy += power;

		if (energy >= BLAST_ENERGY)
			this.fire(world, x, y, z);
	}

	private void createListing() {

	}

	private void fire(World world, int x, int y, int z) {
		energy = 0;
		fired = true;
		for (int i = 0; i < blocks.size(); i++) {
			TileEntity te = blocks.get(i);
			this.shutdownTE(te);
		}
		if (APIRegistry.THAUMCRAFT.conditionsMet())
			this.affectNearNodes(world, x, y, z);
	}

	private void affectNearNodes(World world, int x, int y, int z) {
		List<AuraNode> nodes = ReikaThaumHelper.getAllNodeCopiesNear(world, x+0.5, y+0.5, z+0.5, this.getRange());
		if (nodes == null)
			return;
		ObjectTags flux = new ObjectTags();
		flux.add(EnumTag.DESTRUCTION, 100);
		flux.add(EnumTag.FLUX, 200);
		flux.add(EnumTag.MECHANISM, 100);
		flux.add(EnumTag.POWER, 400);

		for (int i = 0; i < nodes.size(); i++) {
			AuraNode au = nodes.get(i);
			if (au != null) {
				int base = au.baseLevel;
				switch(au.type) {
				case NORMAL:
					ReikaThaumHelper.affectNode(au, 0, par5Random.nextInt(base/2), false, flux, 0, 0, 0);
					if (par5Random.nextInt(100) == 0) {
						ReikaThaumHelper.setNodeType(au, EnumNodeType.UNSTABLE);
					}
					break;
				case PURE:
					ReikaThaumHelper.affectNode(au, 0, -base/2, false, flux, 0, 0, 0);
					break;
				case UNSTABLE:
					ReikaThaumHelper.affectNode(au, 0, base/2, false, flux, 0, 0, 0);
					if (par5Random.nextInt(50) == 0) {
						ReikaThaumHelper.setNodeType(au, EnumNodeType.DARK);
					}
					break;
				case DARK:
					ReikaThaumHelper.affectNode(au, 0, -base/2+par5Random.nextInt(base)+1, false, flux, 0, 0, 0);
					if (par5Random.nextInt(1000) == 0) {
						ReikaThaumHelper.setNodeType(	au, EnumNodeType.PURE);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void shutdownTE(TileEntity te) {
		if (te == null)
			return;
		if (this.isBlacklisted(te))
			return;
		if (te instanceof RotaryCraftTileEntity) {
			RotaryCraftTileEntity rc = (RotaryCraftTileEntity)te;
			if (!rc.isShutdown())
				rc.shutdown();
		}
		else
			this.shutdownFallback(te);
	}

	private boolean isBlacklisted(TileEntity te) {
		return blacklist.contains(te.getClass());
	}

	private void shutdownFallback(TileEntity te) {
		int x = te.xCoord;
		int y = te.yCoord;
		int z = te.zCoord;
		int id = worldObj.getBlockId(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		Block b = Block.blocksList[id];
		ItemStack[] inv;
		if (te instanceof IInventory) {
			IInventory ii = (IInventory)te;
			inv = new ItemStack[ii.getSizeInventory()];
			for (int i = 0; i < inv.length; i++) {
				inv[i] = ii.getStackInSlot(i);
			}
		}
		else {
			inv = new ItemStack[0];
		}
		worldObj.setBlock(x, y, z, BlockRegistry.DEADMACHINE.getBlockID());
		TileEntityDeadMachine dead = (TileEntityDeadMachine)worldObj.getBlockTileEntity(x, y, z);
		dead.setBlock(b, id, meta);
		dead.setInvSize(inv.length);
		for (int i = 0; i < inv.length; i++) {
			dead.setInventorySlotContents(i, inv[i]);
		}
	}

	@Override
	public int getRange() {
		return 64;
	}

	@Override
	public int getMaxRange() {
		return 0;
	}

}

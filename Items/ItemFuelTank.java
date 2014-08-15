/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFuelTank extends ItemRotaryTool implements Fillable {

	private static final ArrayList<Fluid> creativeFluids = new ArrayList();

	public ItemFuelTank(int index) {
		super(index);
	}

	@Override
	public boolean isValidFluid(Fluid f, ItemStack is) {
		return is.stackTagCompound != null ? f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound)) : true;
	}

	@Override
	public int getCapacity(ItemStack is) {
		return 16000;
	}

	@Override
	public int getCurrentFillLevel(ItemStack is) {
		return is.stackTagCompound != null ? is.stackTagCompound.getInteger("fuel") : 0;
	}

	@Override
	public int addFluid(ItemStack is, Fluid f, int amt) {
		int fuel = 0;
		if (!this.isValidFluid(f)) {
			return 0;
		}
		if (is.stackTagCompound == null) {
			is.stackTagCompound = new NBTTagCompound();
			ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
		}
		else {
			fuel = is.stackTagCompound.getInteger("fuel");
			if (!f.equals(ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound))) {
				return 0;
			}
		}
		int toadd = Math.min(amt, this.getCapacity(is)-fuel);
		is.stackTagCompound.setInteger("fuel", fuel+toadd);
		return toadd;
	}

	public boolean isValidFluid(Fluid f) {
		if (f == null)
			return false;
		if (f.equals(FluidRegistry.getFluid("fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("bioethanol")))
			return true;
		if (f.equals(FluidRegistry.getFluid("biofuel")))
			return true;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(ItemRegistry.getEntryByID(par1).getStackOf());
		for (int i = 0; i < creativeFluids.size(); i++) {
			ItemStack is = ItemRegistry.getEntryByID(par1).getStackOf();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("fuel", this.getCapacity(is));
			ReikaNBTHelper.writeFluidToNBT(nbt, creativeFluids.get(i));
			is.stackTagCompound = nbt;
			par3List.add(is);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean verbose) {
		int i = is.getItemDamage();
		if (is.stackTagCompound != null)
			li.add(this.getDisplayTag(is.stackTagCompound));
	}

	private String getDisplayTag(NBTTagCompound nbt) {
		Fluid f = ReikaNBTHelper.getFluidFromNBT(nbt);
		String fluid = f != null ? f.getLocalizedName() : "Null Fluid";
		int amt = nbt.getInteger("fuel");
		String amount = String.format("%d", amt);
		return "Contents: "+amount+" mB of "+fluid;
	}

	private static void addCreativeFluid(String name) {
		Fluid f = FluidRegistry.getFluid(name);
		if (f != null)
			creativeFluids.add(f);
	}

	public static void initCreativeFluids() {
		creativeFluids.clear();
		addCreativeFluid("fuel");
		addCreativeFluid("jet fuel");
		addCreativeFluid("rc ethanol");
		addCreativeFluid("bioethanol");
		addCreativeFluid("biofuel");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.stackTagCompound != null) {
			Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
			int amt = this.getCurrentFillLevel(is);
			int slot = ReikaInventoryHelper.locateIDInInventory(ItemRegistry.JETPACK.getItemInstance(), ep.inventory);
			if (slot == -1) {
				slot = ReikaInventoryHelper.locateIDInInventory(ItemRegistry.BEDPACK.getItemInstance(), ep.inventory);
			}
			if (slot != -1) {
				ItemStack jet = ep.inventory.getStackInSlot(slot);
				ItemJetPack item = (ItemJetPack)jet.getItem();
				int fuel = this.getCurrentFillLevel(is);
				int added = item.addFluid(jet, f, fuel);
				int newfuel = fuel-added;
				is.stackTagCompound.setInteger("fuel", newfuel);
				if (newfuel <= 0)
					is.stackTagCompound = null;
			}
		}
		return is;
	}

	private void removeFuel(ItemStack is, int amt) {
		int newfuel = this.getCurrentFillLevel(is)-amt;
		if (newfuel > 0)
			is.stackTagCompound.setInteger("fuel", newfuel);
		else
			is.stackTagCompound = null;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)tile;
			EngineType eng = te.getEngineType();
			Fluid f = this.getCurrentFluid(is);
			if (f != null) {
				int amt = Math.min(this.getCurrentFillLevel(is), te.getFuelCapacity()-te.getFuelLevel());
				if (amt > 0) {
					boolean flag = false;
					if (eng.isJetFueled() && f.equals(FluidRegistry.getFluid("jet fuel"))) {
						te.addFuel(amt);
						flag = true;
					}
					else if (eng.isEthanolFueled() && f.equals(FluidRegistry.getFluid("rc ethanol"))) {
						te.addFuel(amt);
						flag = true;
					}
					if (flag) {
						ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "fuel");
						this.removeFuel(is, amt);
						return true;
					}
				}
			}
			else {
				int amt = Math.min(this.getCapacity(is), te.getFuelLevel());
				if (amt > 0) {
					boolean flag = false;
					if (eng.isJetFueled()) {
						this.addFluid(is, FluidRegistry.getFluid("jet fuel"), amt);
						flag = true;
					}
					else if (eng.isEthanolFueled()) {
						this.addFluid(is, FluidRegistry.getFluid("rc ethanol"), amt);
						flag = true;
					}
					if (flag) {
						te.subtractFuel(amt);
						ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "fuel");
						return true;
					}
				}
			}
		}
		if (m == MachineRegistry.FUELENGINE) {
			TileEntityFuelEngine te = (TileEntityFuelEngine)tile;
			Fluid f = this.getCurrentFluid(is);
			if (f == null) {
				int amt = Math.min(this.getCapacity(is), te.getFuelLevel());
				if (amt > 0) {
					this.addFluid(is, FluidRegistry.getFluid("fuel"), amt);
					te.removeFuel(amt);
					return true;
				}
			}
			else if (f.equals(FluidRegistry.getFluid("fuel"))) {
				int amt = Math.min(this.getCurrentFillLevel(is), te.CAPACITY-te.getFuelLevel());
				if (amt > 0) {
					te.addFuel(amt);
					ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "tank");
					this.removeFuel(is, amt);
					return true;
				}
			}
		}
		if (m == MachineRegistry.PULSEJET) {
			TileEntityPulseFurnace te = (TileEntityPulseFurnace)tile;
			Fluid f = this.getCurrentFluid(is);
			if (f == null) {
				int amt = Math.min(this.getCapacity(is), te.getFuel());
				if (amt > 0) {
					this.addFluid(is, FluidRegistry.getFluid("jet fuel"), amt);
					te.removeFuel(amt);
					ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "fuel");
					return true;
				}
			}
			else if (f.equals(FluidRegistry.getFluid("jet fuel"))) {
				int amt = Math.min(this.getCurrentFillLevel(is), te.MAXFUEL-te.getFuel());
				if (amt > 0) {
					te.addFuel(amt);
					ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "fuel");
					this.removeFuel(is, amt);
					return true;
				}
			}
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir te = (TileEntityReservoir)tile;
			Fluid f = this.getCurrentFluid(is);
			Fluid f2 = te.getFluid();
			if (f != null) {
				int amt = Math.min(this.getCurrentFillLevel(is), te.CAPACITY-te.getLevel());
				if (amt > 0 && te.canAcceptFluid(f)) {
					te.addLiquid(amt, f);
					ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "tank");
					this.removeFuel(is, amt);
					return true;
				}
			}
			else {
				if (this.isValidFluid(f2)) {
					int amt = Math.min(this.getCapacity(is), te.getLevel());
					if (amt > 0) {
						te.removeLiquid(amt);
						ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "tank");
						this.addFluid(is, f2, amt);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isFull(ItemStack is) {
		return this.getCurrentFillLevel(is) >= this.getCapacity(is);
	}

	@Override
	public Fluid getCurrentFluid(ItemStack is) {
		return is.stackTagCompound != null ? ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound) : null;
	}

}
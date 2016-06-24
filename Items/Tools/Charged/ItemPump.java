/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

public class ItemPump extends ItemChargedTool {

	public ItemPump(int index) {
		super(index);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (ep.isSneaking()) {
			this.incrementMode(is);
			return is;
		}
		if (is.getItemDamage() <= 0) {
			ReikaChatHelper.clearChat(); //clr
			this.noCharge();
			return is;
		}
		if (this.getMode(is) == Modes.DRAIN) {
			this.warnCharge(is);
			MovingObjectPosition mov = ReikaPlayerAPI.getLookedAtBlock(ep, 5, true);
			if (mov != null) {
				int x = mov.blockX;
				int y = mov.blockY;
				int z = mov.blockZ;
				Block id = world.getBlock(x, y, z);
				if (id != Blocks.air) {
					if (ReikaWorldHelper.isLiquidSourceBlock(world, x, y, z)) {
						Fluid f = FluidRegistry.lookupFluidForBlock(id);
						if (f != null && !world.isRemote) {
							Fluid f2 = is.stackTagCompound == null ? null : ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
							if (f2 == null) {
								if (is.stackTagCompound == null)
									is.stackTagCompound = new NBTTagCompound();
								is.stackTagCompound.setInteger("lvl", 1000);
								ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
								world.setBlockToAir(x, y, z);
								is.setItemDamage(is.getItemDamage()-1);
							}
							else {
								int amt = is.stackTagCompound.getInteger("lvl");
								if (f2.equals(f)) {
									if (amt < TileEntityReservoir.CAPACITY) {
										is.stackTagCompound.setInteger("lvl", amt+1000);
										world.setBlockToAir(x, y, z);
										is.setItemDamage(is.getItemDamage()-1);
									}
									else {
										RotaryCraft.logger.debug("Too little space");
									}
								}
								else {
									RotaryCraft.logger.debug("Fluid mismatch "+f+" != "+f2);
								}
							}
						}
						else {
							RotaryCraft.logger.debug("Null fluid for block "+id+", yet was marked as such!");
						}
					}
					else {
						RotaryCraft.logger.debug("Not a fluid block ("+id+")");
					}
				}
			}
		}
		return is;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float par8, float par9, float par10) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (ep.isSneaking())
			return true;
		if (te instanceof IFluidHandler) {
			IFluidHandler fl = (IFluidHandler)te;
			int amt = is.stackTagCompound != null ? is.stackTagCompound.getInteger("lvl") : 0;
			if (this.getMode(is) == Modes.PLACE) {
				Fluid f = is.stackTagCompound != null ? ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound) : null;
				FluidStack f2 = fl.drain(ForgeDirection.VALID_DIRECTIONS[s], 1, false);
				if (f2 != null) {
					if (f == null || f == f2.getFluid()) {
						int space = TileEntityReservoir.CAPACITY-amt;
						FluidStack fs = fl.drain(ForgeDirection.VALID_DIRECTIONS[s], space, true);
						if (fs != null) {
							if (is.stackTagCompound == null)
								is.stackTagCompound = new NBTTagCompound();
							is.stackTagCompound.setInteger("lvl", amt+fs.amount);
							ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, fs.getFluid());
						}
					}
				}
			}
			else {
				if (amt > 0) {
					Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
					for (int i = 0; i < 6; i++) {
						int d = fl.fill(ForgeDirection.VALID_DIRECTIONS[i], new FluidStack(f, amt), true);
						amt -= d;
					}
					is.stackTagCompound.setInteger("lvl", amt);
				}
			}
			return true;
		}
		else if (this.getMode(is) == Modes.PLACE) {
			if (is.getItemDamage() > 0) {
				this.warnCharge(is);
				int amt = is.stackTagCompound.getInteger("lvl");
				Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
				if (f != null && amt >= 1000) {
					Block b = f.getBlock();
					if (b != null) {
						ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[s];
						int dx = x+dir.offsetX;
						int dy = y+dir.offsetY;
						int dz = z+dir.offsetZ;
						if (world.getBlock(dx, dy, dz).isAir(world, dx, dy, dz) || (world.getBlock(dx, dy, dz) == b && !ReikaWorldHelper.isLiquidSourceBlock(world, dx, dy, dz))) {
							world.setBlock(dx, dy, dz, b);
							is.stackTagCompound.setInteger("lvl", amt-1000);
							is.setItemDamage(is.getItemDamage()-1);
							for (int i = -1; i <= 1; i++) {
								for (int k = -1; k <= 1; k++) {
									world.markBlockForUpdate(dx+i, dy, dz+k);
									world.getBlock(dx+i, dy, dz+k).onNeighborBlockChange(world, dx+i, dy, dz+k, b);
									ReikaWorldHelper.causeAdjacentUpdates(world, dx+i, dy, dz+k);
								}
							}
						}
					}
				}
			}
			else {
				ReikaChatHelper.clearChat(); //clr
				this.noCharge();
			}
		}
		return false;
	}

	private Modes getMode(ItemStack is) {
		//return Modes.list[is.getItemDamage()];
		if (is.stackTagCompound == null)
			return Modes.DRAIN;
		return Modes.list[is.stackTagCompound.getInteger("mode")];
	}

	private void setMode(ItemStack is, Modes m) {
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		//is.setItemDamage(m.ordinal());
		is.stackTagCompound.setInteger("mode", m.ordinal());
	}

	private void incrementMode(ItemStack is) {
		this.setMode(is, this.getMode(is).next());
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt != null) {
			Fluid f = ReikaNBTHelper.getFluidFromNBT(nbt);
			if (f != null) {
				String fluid = f.getLocalizedName();
				int amt = nbt.getInteger("lvl");
				String amount = String.format("%d", amt);
				String s = "Contents: "+amount+" mB of "+fluid;
				li.add(s);
			}
		}
		li.add("Mode: "+this.getMode(is).desc);
	}

	private static enum Modes {
		DRAIN("Drain"),
		PLACE("Place");

		private final String desc;

		private static final Modes[] list = values();

		private Modes(String s) {
			desc = s;
		}

		public Modes next() {
			return list[(this.ordinal()+1)%list.length];
		}
	}

}

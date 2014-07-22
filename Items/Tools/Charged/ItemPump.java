/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
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

	public ItemPump(int ID, int index) {
		super(ID, index);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.getItemDamage() <= 0) {
			ReikaChatHelper.clearChat(); //clr
			this.noCharge();
			return is;
		}
		this.warnCharge(is);
		MovingObjectPosition mov = ReikaPlayerAPI.getLookedAtBlock(ep, 5, true);
		if (mov != null) {
			int x = mov.blockX;
			int y = mov.blockY;
			int z = mov.blockZ;
			int id = world.getBlockId(x, y, z);
			if (id != 0) {
				Block b = Block.blocksList[id];
				if (ReikaWorldHelper.isLiquidSourceBlock(world, x, y, z)) {
					Fluid f = FluidRegistry.lookupFluidForBlock(b);
					if (f != null && !world.isRemote) {
						if (is.stackTagCompound == null) {
							is.stackTagCompound = new NBTTagCompound();
							is.stackTagCompound.setInteger("lvl", 1000);
							ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
							world.setBlock(x, y, z, 0);
							is.setItemDamage(is.getItemDamage()-1);
						}
						else {
							Fluid f2 = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
							int amt = is.stackTagCompound.getInteger("lvl");
							if (f2.equals(f)) {
								if (amt < TileEntityReservoir.CAPACITY) {
									is.stackTagCompound.setInteger("lvl", amt+1000);
									world.setBlock(x, y, z, 0);
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
						RotaryCraft.logger.debug("Null fluid for block "+id+":"+b+", yet was marked as such!");
					}
				}
				else {
					RotaryCraft.logger.debug("Not a fluid block ("+id+":"+b+")");
				}
			}
		}
		return is;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float par8, float par9, float par10) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof IFluidHandler && is.stackTagCompound != null) {
			IFluidHandler fl = (IFluidHandler)te;
			int amt = is.stackTagCompound.getInteger("lvl");
			Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
			for (int i = 0; i < 6; i++) {
				int d = fl.fill(ForgeDirection.VALID_DIRECTIONS[i], new FluidStack(f, amt), true);
				amt -= d;
			}
			is.stackTagCompound.setInteger("lvl", amt);
			if (amt <= 0) {
				is.stackTagCompound = null;
			}
			return true;
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt != null) {
			Fluid f = ReikaNBTHelper.getFluidFromNBT(nbt);
			String fluid = f.getLocalizedName();
			int amt = nbt.getInteger("lvl");
			String amount = String.format("%d", amt);
			String s = "Contents: "+amount+" mB of "+fluid;
			li.add(s);
		}
	}

}

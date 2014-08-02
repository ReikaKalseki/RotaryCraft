/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Placers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.ItemMachineRenderer;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Blocks.BlockGPR;
import Reika.RotaryCraft.Blocks.BlockModEngine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMachinePlacer extends ItemBlockPlacer {

	public ItemMachinePlacer(int ID) {
		super(ID);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!ReikaWorldHelper.softBlocks(world, x, y, z) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
			if (!ReikaWorldHelper.softBlocks(world, x, y, z) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava)
				return false;
		}
		this.clearBlocks(world, x, y, z);
		if (!this.checkValidBounds(is, ep, world, x, y, z))
			return false;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		List inblock = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		if (inblock.size() > 0)
			return false;
		MachineRegistry m = MachineRegistry.machineList[is.getItemDamage()];
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, m.getBlockID(), m.getMachineMetadata(), 3);
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)world.getBlockTileEntity(x, y, z);
		te.placer = ep.getEntityName();
		if (te instanceof TemperatureTE) {
			int Tb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
			((TemperatureTE)te).addTemperature(Tb);
		}
		if (te instanceof PressureTE) {
			((PressureTE)te).addPressure(101);
		}
		if (te instanceof EnchantableMachine) {
			EnchantableMachine e = (EnchantableMachine)te;
			e.applyEnchants(is);
		}
		if (te instanceof EnergyToPowerBase) {
			((EnergyToPowerBase)te).setTierFromItemTag(is.stackTagCompound);
		}
		if (te instanceof NBTMachine) {
			((NBTMachine)te).setDataFromItemStackTag(is.stackTagCompound);
		}
		if (m == MachineRegistry.POWERBUS) {
			((TileEntityPowerBus)te).findNetwork(world, x, y, z);
		}
		if (m == MachineRegistry.BUSCONTROLLER) {

		}
		if (m == MachineRegistry.SCALECHEST) {
			((TileEntityScaleableChest)te).readInventoryFromItem(is);
		}
		if (m == MachineRegistry.GPR) {
			TileEntityGPR tile = (TileEntityGPR)te;
			switch (RotaryAux.get2SidedMetadataFromPlayerLook(ep)) {
			case 0:
			case 2:
				tile.xdir = true;
				break;
			case 1:
			case 3:
				tile.xdir = false;
				break;
			}
			world.setBlockMetadataWithNotify(x, y, z, BlockGPR.getBiomeDesign(world, x, y, z), 3);
			return true;
		}
		if (m.isCannon())
			return true;
		if (m.isSidePlaced()) {
			switch(side) {
			case 0:
				te.setBlockMetadata(1);
				break;
			case 1:
				te.setBlockMetadata(0);
				break;
			case 2:
				te.setBlockMetadata(4);
				break;
			case 3:
				te.setBlockMetadata(2);
				break;
			case 4:
				te.setBlockMetadata(5);
				break;
			case 5:
				te.setBlockMetadata(3);
				break;
			}
			return true;
		}
		if (m.canFlip())
			te.isFlipped = RotaryAux.shouldSetFlipped(world, x, y, z);
		if (m == MachineRegistry.PNEUENGINE || m == MachineRegistry.STEAMTURBINE || m == MachineRegistry.GENERATOR || m == MachineRegistry.ELECTRICMOTOR || m == MachineRegistry.MAGNETIC) {
			te.setBlockMetadata(BlockModEngine.getDirectionMetadataFromPlayerLook(ep));
			return true;
		}
		if (!m.hasModel() && m.is4Sided() && !m.hasInv()) {
			switch(RotaryAux.get4SidedMetadataFromPlayerLook(ep)) {
			case 0:
				te.setBlockMetadata(0);
				break;
			case 1:
				te.setBlockMetadata(1);
				break;
			case 2:
				te.setBlockMetadata(3);
				break;
			case 3:
				te.setBlockMetadata(2);
				break;
			}
			return true;
		}
		if (m.hasModel()) {
			if (m.is6Sided())
				te.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
			else if (m.is4Sided())
				te.setBlockMetadata(RotaryAux.get4SidedMetadataFromPlayerLook(ep));
			else if (m.is2Sided())
				te.setBlockMetadata(RotaryAux.get2SidedMetadataFromPlayerLook(ep));
			if (m.isXFlipped())
				RotaryAux.flipXMetadatas(world.getBlockTileEntity(x, y, z));
			if (m.isZFlipped())
				RotaryAux.flipZMetadatas(world.getBlockTileEntity(x, y, z));
		}
		else {
			world.setBlockMetadataWithNotify(x, y, z, m.getMachineMetadata(), 3);

			if (m.is6Sided())
				te.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
			else if (m.is4Sided())
				te.setBlockMetadata(RotaryAux.get4SidedMetadataFromPlayerLook(ep));
			else if (m.is2Sided())
				te.setBlockMetadata(RotaryAux.get2SidedMetadataFromPlayerLook(ep));

			if (m.isXFlipped()) {
				RotaryAux.flipXMetadatas(world.getBlockTileEntity(x, y, z));
			}
			if (m.isZFlipped()) {
				RotaryAux.flipZMetadatas(world.getBlockTileEntity(x, y, z));
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (!m.hasCustomPlacerItem() && m.isAvailableInCreativeInventory()) {
				ItemMachineRenderer ir = ClientProxy.machineItems;
				TileEntity te = m.createTEInstanceForRender(0);
				ItemStack item = m.getCraftedProduct();
				par3List.add(item);
				if (m.isEnergyToPower()) {
					ItemStack item2 = item.copy();
					if (item2.stackTagCompound == null)
						item2.stackTagCompound = new NBTTagCompound();
					item2.stackTagCompound.setInteger("tier", EnergyToPowerBase.TIERS-1);
					par3List.add(item2);
				}
				if (m.hasNBTVariants()) {
					ArrayList<NBTTagCompound> li = ((NBTMachine)te).getCreativeModeVariants();
					for (int k = 0; k < li.size(); k++) {
						NBTTagCompound NBT = li.get(k);
						ItemStack is = m.getCraftedProduct();
						is.stackTagCompound = NBT;
						par3List.add(is);
					}
				}
			}
		}
	}

	@Override
	protected boolean checkValidBounds(ItemStack is, EntityPlayer ep, World world, int x, int y, int z) {
		if (is.getItemDamage() == MachineRegistry.SMOKEDETECTOR.ordinal()) {
			if (world.getBlockId(x, y+1, z) == 0)
				return false;
			return (Block.blocksList[world.getBlockId(x, y+1, z)].isOpaqueCube());
		}
		return super.checkValidBounds(is, ep, world, x, y, z);
	}

	@Override
	public int getMetadata(int meta) {
		return MachineRegistry.machineList[meta].getMachineMetadata();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean verbose) {
		int i = is.getItemDamage();
		MachineRegistry m = MachineRegistry.machineList[i];
		ItemMachineRenderer ir = ClientProxy.machineItems;
		TileEntity te = ir.getRenderingInstance(m, 0);
		if (m.isIncomplete()) {
			li.add("This machine is in development. Use at your own risk.");
		}
		if (m.hasNBTVariants() && is.stackTagCompound != null) {
			li.addAll(((NBTMachine)te).getDisplayTags(is.stackTagCompound));
		}
		if (m.isEnergyToPower()) {
			int tier = 0;
			if (is.stackTagCompound != null) {
				tier = is.stackTagCompound.getInteger("tier");
			}
			li.add(String.format("Tier %d", tier));
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				EnergyToPowerBase e = (EnergyToPowerBase)te;
				int torque = e.getGenTorque(tier);
				int speed = ReikaMathLibrary.intpow2(2, e.getMaxSpeedBase(tier));
				long power = (long)torque*(long)speed;
				double val = ReikaMathLibrary.getThousandBase(power);
				String exp = ReikaEngLibrary.getSIPrefix(power);
				li.add(String.format("Torque: %d Nm", torque));
				li.add(String.format("Max Speed: %d rad/s", speed));
				li.add(String.format("Max Power: %.3f%sW", val, exp));
			}
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("Hold ");
				sb.append(EnumChatFormatting.GREEN.toString());
				sb.append("Shift");
				sb.append(EnumChatFormatting.GRAY.toString());
				sb.append(" for power data");
				li.add(sb.toString());
			}
		}
		if (m.isPowerReceiver()) {
			PowerReceivers p = m.getPowerReceiverEntry();
			long pow = p.getMinPowerForDisplay();
			int trq = p.getMinTorqueForDisplay();
			int spd = p.getMinSpeedForDisplay();
			boolean minp = !p.hasNoDirectMinPower();
			boolean mint = !p.hasNoDirectMinTorque();
			boolean mins = !p.hasNoDirectMinSpeed();
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				if (minp)
					li.add(String.format("Minimum Power: %.3f %sW", ReikaMathLibrary.getThousandBase(pow), ReikaEngLibrary.getSIPrefix(pow)));
				if (mint)
					li.add(String.format("Minimum Torque: %.3f %sNm", ReikaMathLibrary.getThousandBase(trq), ReikaEngLibrary.getSIPrefix(trq)));
				if (mins)
					li.add(String.format("Minimum Speed: %.3f %srad/s", ReikaMathLibrary.getThousandBase(spd), ReikaEngLibrary.getSIPrefix(spd)));
			}
			else {
				if (minp || mint || mins) {
					StringBuilder sb = new StringBuilder();
					sb.append("Hold ");
					sb.append(EnumChatFormatting.GREEN.toString());
					sb.append("Shift");
					sb.append(EnumChatFormatting.GRAY.toString());
					sb.append(" for power data");
					li.add(sb.toString());
				}
			}
		}
	}
}

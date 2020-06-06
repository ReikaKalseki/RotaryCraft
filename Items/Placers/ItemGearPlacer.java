/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Placers;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGearPlacer extends ItemBlockPlacer {

	public ItemGearPlacer() {
		super();
		this.setCreativeTab(RotaryCraft.tabPower);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!ReikaWorldHelper.softBlocks(world, x, y, z) && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.water && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.lava) {
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
			if (!ReikaWorldHelper.softBlocks(world, x, y, z) && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.water && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.lava)
				return false;
		}
		this.clearBlocks(world, x, y, z);
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		List inblock = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		if (inblock.size() > 0)
			return false;
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, MachineRegistry.GEARBOX.getBlock(), is.getItemDamage()%5, 3);
			TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
			if (gbx != null) {
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
				//gbx.type = MaterialRegistry.setType(is.getItemDamage()%5);
				gbx.setBlockMetadata(is.getItemDamage()*4+RotaryAux.get4SidedMetadataFromPlayerLook(ep));
				gbx.setMaterialFromItem(is);
				gbx.setPlacer(ep);
			}
		}
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityGearbox) {
			if (is.stackTagCompound == null)
				is.setTagCompound(new NBTTagCompound());
			((TileEntityGearbox)tile).setDataFromItemStackTag(is.stackTagCompound);
			if (RotaryAux.shouldSetFlipped(world, x, y, z)) {
				((TileEntityGearbox)tile).isFlipped = true;
			}
			int Tb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
			((TemperatureTE)tile).setTemperature(Tb);
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		GearboxTypes mat = GearboxTypes.getMaterialFromGearboxItem(is);
		if (is.stackTagCompound.hasKey("damage") && mat.isDamageableGear()) {
			int dmg = TileEntityGearbox.getDamagePercent(is.stackTagCompound.getInteger("damage"));
			par3List.add("Damage: "+dmg+"%");
		}

		if (!is.stackTagCompound.hasKey("type")) {
			par3List.add("Legacy; craft into new version");
			par3List.add(EnumChatFormatting.RED+"DO NOT PLACE");
		}

		if (is.stackTagCompound.hasKey("lube") && mat.needsLubricant()) {
			int amt = is.stackTagCompound.getInteger("lube");
			String s = is.stackTagCompound.getBoolean("living") ? String.format("Mana: %d%%", amt*100/mat.getMaxLubricant()) : "Lubricant: "+amt+" mB";
			par3List.add(s);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		if (MachineRegistry.GEARBOX.isAvailableInCreativeInventory()) {
			for (GearboxTypes gear : GearboxTypes.typeList) {
				if (!gear.isLoadable())
					continue;
				for (int r = 2; r <= 16; r *= 2) {
					ItemStack item = gear.getGearboxItem(r);
					if (item.stackTagCompound == null)
						item.stackTagCompound = new NBTTagCompound();
					item.stackTagCompound.setInteger("damage", 0);
					item.stackTagCompound.setInteger("lube", 0);
					list.add(item);
				}
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		try {
			GearboxTypes type = GearboxTypes.getMaterialFromGearboxItem(is);
			return type != null ? type.getLocalizedGearboxName(ReikaMathLibrary.intpow2(2, is.getItemDamage()+1)) : "Gearbox";
		}
		catch (Exception e) {
			return "Invalid item: "+e.toString();
		}
	}

	@Override
	protected double getBrokenFraction(ItemStack is) {
		if (is.stackTagCompound != null) {
			int dmg = is.stackTagCompound.getInteger("damage");
			return TileEntityGearbox.getDamagePercent(dmg)/100D;
		}
		return 0;
	}
}

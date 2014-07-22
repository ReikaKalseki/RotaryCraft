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

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear.GearType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAdvGearPlacer extends ItemBlockPlacer {

	public ItemAdvGearPlacer(int id) {
		super(id);
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
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, MachineRegistry.ADVANCEDGEARS.getBlockID());
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
		TileEntityAdvancedGear adv = (TileEntityAdvancedGear)world.getBlockTileEntity(x, y, z);
		adv.setBlockMetadata(4*is.getItemDamage()+RotaryAux.get4SidedMetadataFromPlayerLook(ep));
		adv.placer = ep.getEntityName();
		if (RotaryAux.shouldSetFlipped(world, x, y, z)) {
			adv.isFlipped = true;
		}
		if (is.stackTagCompound != null) {
			if (adv.getGearType().storesEnergy()) {
				adv.setEnergyFromNBT(is.stackTagCompound);
				adv.setBedrock(is.stackTagCompound.getBoolean("bedrock"));
				adv.setCreative(is.stackTagCompound.getBoolean("creative"));
			}
			if (adv.getGearType().isLubricated()) {
				adv.setLubricantFromNBT(is.stackTagCompound);
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		if (MachineRegistry.ADVANCEDGEARS.isAvailableInCreativeInventory()) {
			for (int i = 0; i < TileEntityAdvancedGear.GearType.list.length; i++) {
				ItemStack item = new ItemStack(id, 1, i);
				list.add(item);
				if (GearType.list[i].storesEnergy()) {
					item = item.copy();
					item.stackTagCompound = new NBTTagCompound();
					item.stackTagCompound.setBoolean("bedrock", true);
					list.add(item);

					item = item.copy();
					item.stackTagCompound = new NBTTagCompound();
					item.stackTagCompound.setLong("energy", TileEntityAdvancedGear.getMaxStorageCapacity(true)*20);
					item.stackTagCompound.setBoolean("creative", true);
					item.stackTagCompound.setBoolean("bedrock", true);
					list.add(item);
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		GearType type = GearType.list[is.getItemDamage()];
		if (type.storesEnergy()) {
			boolean bedrock = is.stackTagCompound != null && is.stackTagCompound.getBoolean("bedrock");
			long max = TileEntityAdvancedGear.getMaxStorageCapacity(bedrock);
			li.add(String.format("Maximum Energy: %.0f %sJ", ReikaMathLibrary.getThousandBase(max), ReikaEngLibrary.getSIPrefix(max)));
			if (is.stackTagCompound == null || is.stackTagCompound.getLong("energy") <= 0)
				li.add("Stored Energy: 0 J");
			else {
				if (is.stackTagCompound.getBoolean("creative")) {
					li.add("Infinite power for creative mode:");
					li.add("This coil does not deplete.");
				}
				else {
					long e = is.stackTagCompound.getLong("energy")/20;
					li.add("Stored Energy: "+String.format("%.3f ", ReikaMathLibrary.getThousandBase(e))+ReikaEngLibrary.getSIPrefix(e)+"J");
				}
			}
		}
		if (type.isLubricated()) {
			if (is.stackTagCompound != null) {
				int lube = is.stackTagCompound.getInteger("lube");
				li.add("Lubricant: "+lube+" mB");
			}
		}
	}
}

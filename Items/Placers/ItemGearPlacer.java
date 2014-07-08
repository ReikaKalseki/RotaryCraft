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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGearPlacer extends ItemBlockPlacer {

	public ItemGearPlacer(int id) {
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
			world.setBlock(x, y, z, MachineRegistry.GEARBOX.getBlockID(), is.getItemDamage()%5, 3);
			TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
			if (gbx != null) {
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
				//gbx.type = MaterialRegistry.setType(is.getItemDamage()%5);
				gbx.setBlockMetadata(is.getItemDamage()/5*4+RotaryAux.get4SidedMetadataFromPlayerLook(ep));
				gbx.placer = ep.getEntityName();
			}
		}
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityGearbox) {
			if (is.stackTagCompound == null)
				is.setTagCompound(new NBTTagCompound());
			((TileEntityGearbox)tile).setDamage(is.stackTagCompound.getInteger("damage"));
			((TileEntityGearbox)tile).setLubricant(is.stackTagCompound.getInteger("lube"));
			if (RotaryAux.shouldSetFlipped(world, x, y, z)) {
				((TileEntityGearbox)tile).isFlipped = true;
			}
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		MaterialRegistry mat = MaterialRegistry.matList[is.getItemDamage()%MaterialRegistry.matList.length];
		if (is.stackTagCompound.hasKey("damage") && mat.isDamageableGear()) {
			int dmg = (int)(100*(1-ReikaMathLibrary.doubpow(0.99, is.stackTagCompound.getInteger("damage"))));
			par3List.add("Damage: "+dmg+"%");
		}

		if (is.stackTagCompound.hasKey("lube") && mat.isDamageableGear()) {
			par3List.add("Lubricant: "+is.stackTagCompound.getInteger("lube")+" mB");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		if (MachineRegistry.GEARBOX.isAvailableInCreativeInventory()) {
			for (int i = 0; i < RotaryNames.getNumberGearTypes(); i++) {
				ItemStack item = new ItemStack(id, 1, i);
				if (item.stackTagCompound == null)
					item.setTagCompound(new NBTTagCompound());
				item.stackTagCompound.setInteger("damage", 0);
				item.stackTagCompound.setInteger("lube", 0);
				list.add(item);
			}
		}
	}
}

/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.HashMap;
import java.util.List;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Base.ItemRailgunAmmoBase;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityRailGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemRailGunAmmo extends ItemRailgunAmmoBase {

	public ItemRailGunAmmo(int tex) {
		super(tex);
		hasSubtypes = true;
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < 16; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + String.valueOf(d);
	}

	@Override
	public RailGunAmmoType getAmmo(ItemStack is) {
		return getType(is.getItemDamage());
	}

	public static RailGunAmmoType getType(int pw) {
		BasicRailGunAmmo ret = BasicRailGunAmmo.cache.get(pw);
		if (ret == null) {
			ret = new BasicRailGunAmmo(pw);
			BasicRailGunAmmo.cache.put(pw, ret);
		}
		return ret;
	}

	@Override
	public int getItemSpriteIndex(ItemStack is) {
		int base = super.getItemSpriteIndex(is);
		int add = 0;
		switch(is.getItemDamage()) {
			case 7:
			case 8:
			case 9:
				add = 1;
				break;
			case 10:
			case 11:
			case 12:
				add = 2;
				break;
			case 13:
			case 14:
			case 15:
				add = 3;
				break;
		}
		return base+add;
	}

	public static class BasicRailGunAmmo implements RailGunAmmoType {

		private static final HashMap<Integer, BasicRailGunAmmo> cache = new HashMap();

		private final int power;

		private BasicRailGunAmmo(int p) {
			power = p;
		}

		@Override
		public Entity getProjectileEntity(World world, double x, double y, double z, double vx, double vy, double vz, TileEntity railgun) {
			return new EntityRailGunShot(world, x, y, z, vx, vy, vz, power, (TileEntityRailGun)railgun);
		}

		@Override
		public ItemStack getItem() {
			return ItemRegistry.RAILGUN.getStackOfMetadata(power);
		}

		public int getRequiredTorque() {
			return (int)Math.sqrt(512*ReikaMathLibrary.intpow2(2, power));
		}

		@Override
		public int compareTo(RailGunAmmoType type) {
			return 1+power;
		}

		@Override
		public boolean isExplosive() {
			return false;
		}

		@Override
		public boolean isMagic() {
			return false;
		}

		@Override
		public int getMass() {
			return ReikaMathLibrary.intpow2(2, power);
		}

	}

}

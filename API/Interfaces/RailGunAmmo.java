package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface RailGunAmmo {

	public RailGunAmmoType getAmmo(ItemStack is);

	public static interface RailGunAmmoType extends Comparable<RailGunAmmoType> {

		public int getRequiredTorque();

		public ItemStack getItem();

		public Entity getProjectileEntity(World world, double x, double y, double z, double vx, double vy, double vz, TileEntity railgun);

		/** Return > 0 for "more powerful than"; < 1 for "less powerful than", 0 for "equal" */
		public int compareTo(RailGunAmmoType type);

		public boolean isExplosive();
		public boolean isMagic();

		public int getMass();

	}

}

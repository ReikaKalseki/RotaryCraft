package Reika.RotaryCraft.Items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.RotaryCraft.Base.ItemRailgunAmmoBase;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityRailGun;


public class ItemExplosiveShell extends ItemRailgunAmmoBase {

	public ItemExplosiveShell(int tex) {
		super(tex);
	}

	public static class ExplosiveRailGunAmmo implements RailGunAmmoType {

		public static final ExplosiveRailGunAmmo instance = new ExplosiveRailGunAmmo();

		private ExplosiveRailGunAmmo() {

		}

		@Override
		public Entity getProjectileEntity(World world, double x, double y, double z, double vx, double vy, double vz, TileEntity railgun) {
			return new EntityExplosiveShell(world, x, y, z, vx, vy, vz, (TileEntityRailGun)railgun);
		}

		@Override
		public ItemStack getItem() {
			return ItemRegistry.SHELL.getStackOf();
		}

		@Override
		public int getRequiredTorque() {
			return 0;
		}

		@Override
		public int compareTo(RailGunAmmoType type) {
			return -1;
		}

		@Override
		public boolean isExplosive() {
			return true;
		}

		@Override
		public boolean isMagic() {
			return false;
		}

		@Override
		public int getMass() {
			return 6;
		}

	}

	@Override
	public RailGunAmmoType getAmmo(ItemStack is) {
		return ExplosiveRailGunAmmo.instance;
	}

}

package Reika.RotaryCraft.Items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.RotaryCraft.Base.ItemRailgunAmmoBase;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Items.ItemRailGunAmmo.BasicRailGunAmmo;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityRailGun;


public class ItemVoidMetalRailgunAmmo extends ItemRailgunAmmoBase {

	public static final int EQUIVALENT = 12;

	public ItemVoidMetalRailgunAmmo(int tex) {
		super(tex);
	}

	@Override
	public RailGunAmmoType getAmmo(ItemStack is) {
		return VoidMetalRailGunAmmo.instance;
	}

	public static class VoidMetalRailGunAmmo implements RailGunAmmoType {

		private static final VoidMetalRailGunAmmo instance = new VoidMetalRailGunAmmo();

		private static final BasicRailGunAmmo delegate = (BasicRailGunAmmo)((ItemRailGunAmmo)ItemRegistry.RAILGUN.getItemInstance()).getAmmo(ItemRegistry.RAILGUN.getStackOfMetadata(EQUIVALENT));

		private VoidMetalRailGunAmmo() {

		}

		@Override
		public Entity getProjectileEntity(World world, double x, double y, double z, double vx, double vy, double vz, TileEntity railgun) {
			EntityRailGunShot e = new EntityRailGunShot(world, x, y, z, vx, vy, vz, EQUIVALENT, (TileEntityRailGun)railgun, instance);
			return e;
		}

		@Override
		public ItemStack getItem() {
			return ItemRegistry.VOIDRAIL.getStackOf();
		}

		@Override
		public int getRequiredTorque() {
			return delegate.getRequiredTorque();
		}

		@Override
		public int compareTo(RailGunAmmoType type) {
			return 4;
		}

		@Override
		public boolean isExplosive() {
			return false;
		}

		@Override
		public boolean isMagic() {
			return true;
		}

		@Override
		public int getMass() {
			return delegate.getMass();
		}

	}

}

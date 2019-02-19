package Reika.RotaryCraft.Base;

import Reika.RotaryCraft.API.Interfaces.RailGunAmmo.RailGunAmmoType;
import Reika.RotaryCraft.Items.ItemRailGunAmmo;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityRailGun;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityRailgunShotBase extends EntityTurretShot {

	private RailGunAmmoType type;

	public EntityRailgunShotBase(World world) {
		super(world);
	}

	public EntityRailgunShotBase(World world, double x, double y, double z, double vx, double vy, double vz, TileEntityRailGun te, RailGunAmmoType type) {
		super(world, x, y, z, vx, vy, vz, te);
		this.type = type;
	}

	public final RailGunAmmoType getType() {
		return type;
	}

	@Override
	protected void writeData(ByteBuf buf) {
		if (type != null) {
			buf.writeBoolean(true);
			buf.writeInt(Item.getIdFromItem(type.getItem().getItem()));
			buf.writeInt(type.getItem().getItemDamage());
		}
		else {
			buf.writeBoolean(false);
		}
	}

	@Override
	protected void readData(ByteBuf buf) {
		if (buf.readBoolean()) {
			Item i = Item.getItemById(buf.readInt());
			if (i instanceof ItemRailGunAmmo) {
				ItemStack is = new ItemStack(i, 1, buf.readInt());
				type = ((ItemRailGunAmmo)i).getAmmo(is);
			}
		}
	}

}

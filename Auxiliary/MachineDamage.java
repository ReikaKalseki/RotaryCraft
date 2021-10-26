package Reika.RotaryCraft.Auxiliary;

import java.util.HashSet;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Instantiable.CustomStringDamageSource;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;


public class MachineDamage extends CustomStringDamageSource {

	private float armorFactor = 1;
	private int armorDamage = 0;

	private final HashSet<Integer> armorTypes = new HashSet();

	public TileEntityBase lastMachine;

	public MachineDamage(String msg) {
		super(msg);
	}

	public MachineDamage setArmorBlocking(float f, int mult, int... types) { //1 is feet, 4 is helmet
		armorFactor = f;
		armorDamage = mult;
		for (int type : types) {
			armorTypes.add(type);
		}
		return this;
	}

	public float onDamageDealt(float base, EntityLivingBase e) {
		for (int slot : armorTypes) {
			ItemStack armor = e.getEquipmentInSlot(slot);
			if (armor != null && armor.getItem() instanceof ItemArmor) {
				/*
				float prot = ((ItemArmor)armor.getItem()).getArmorMaterial().getDamageReductionAmount(4-slot);
				if (armor.getItem() instanceof ISpecialArmor) {
					((ISpecialArmor)armor.getItem()).getProperties(e, armor, this, base, slot);
				}*/
				ReikaEntityHelper.damageArmor(e, armorDamage, slot);
				base *= armorFactor;
			}
		}
		return base;
	}

}

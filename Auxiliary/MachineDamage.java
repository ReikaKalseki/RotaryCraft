package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Instantiable.CustomStringDamageSource;


public class MachineDamage extends CustomStringDamageSource {

	public TileEntityBase lastMachine;

	public MachineDamage(String msg) {
		super(msg);
	}

}

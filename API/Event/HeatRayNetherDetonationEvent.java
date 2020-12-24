package Reika.RotaryCraft.API.Event;

import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Event.Base.WorldPositionEvent;


public class HeatRayNetherDetonationEvent extends WorldPositionEvent {

	public HeatRayNetherDetonationEvent(World world, int x, int y, int z) {
		super(world, x, y, z);
	}

}

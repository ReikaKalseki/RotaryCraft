package Reika.RotaryCraft.API;

import java.util.ArrayList;

import net.minecraft.world.World;

public interface Transducerable {

	public ArrayList<String> getMessages(World world, int x, int y, int z, int side);

}

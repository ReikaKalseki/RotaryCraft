package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.EnumMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Instantiable.Data.Maps.PlayerMap;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TutorialTracker {

	public static final TutorialTracker instance = new TutorialTracker();

	private final PlayerMap<Hint> data = new PlayerMap();
	private final EnumMap<MachineRegistry, HintList> machineHints = new EnumMap(MachineRegistry.class);
	private final EnumMap<EngineType, HintList> engineHints = new EnumMap(EngineType.class);

	private TutorialTracker() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void placeMachine(MachineRegistry m, EntityPlayer ep) {

	}

	public void placeEngine(EngineType e, EntityPlayer ep) {

	}

	private static class Hint {

		public final String text;

		private Hint(String s) {
			text = s;
		}

	}

	private static class HintList {

		private final ArrayList<Hint> list = new ArrayList();

	}

}

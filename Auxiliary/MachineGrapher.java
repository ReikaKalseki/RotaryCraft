package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.Data.BarGraphData;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;

public class MachineGrapher {

	//cool graph of all machines and power inputs?

	public static final BarGraphData data;

	static {
		data = getPowerReceivers();
	}

	private static BarGraphData getPowerReceivers() {
		BarGraphData bdg = new BarGraphData();
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.isPowerReceiver()) {
				int min = -1;
				PowerReceivers pow = PowerReceivers.getEnumFromMachineIndex(i);
				if (pow.hasNoDirectMinPower()) {
					min = 0;
				}
				else if (pow.hasMultiValuedPower()) {
					int n = pow.getMultiValuedPowerTypes();
					for (int k = 0; k < n; k++) {
						int t = pow.getMinPower(k);
						if (t > min)
							min = t;
					}
				}
				else {
					min = pow.getMinPower();
				}
				data.addOneEntry(min);
			}
		}
		return bdg;
	}

}

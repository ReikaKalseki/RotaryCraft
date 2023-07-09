package Reika.RotaryCraft.ModInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import Reika.ChromatiCraft.API.AdjacencyUpgradeAPI.BlacklistReason;
import Reika.ChromatiCraft.API.ChromatiAPI;
import Reika.ChromatiCraft.API.Interfaces.CustomHealing.CustomTileHealing;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.Flywheels;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear.GearType;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

public class ChromaRC {

	public static void addHealingCoreEffects() {
		ChromatiAPI.getAPI().adjacency().addCustomHealing(TileEntityGearbox.class, new CustomTileHealing() {
			@Override
			public boolean runOnClient() {
				return false;
			}

			@Override
			public String getDescription() {
				return "Repair gearbox damage";
			}

			@Override
			public Collection<ItemStack> getItems() {
				ArrayList<ItemStack> li = new ArrayList();
				for (GearboxTypes gear : GearboxTypes.typeList)
					li.add(gear.getGearboxItem(16));
				return li;
			}

			@Override
			public void tick(TileEntity te, int tier) {
				((TileEntityGearbox)te).repairCC(tier);
			}
		});
		ChromatiAPI.getAPI().adjacency().addCustomHealing(TileEntityJetEngine.class, new CustomTileHealing() {
			@Override
			public boolean runOnClient() {
				return false;
			}

			@Override
			public String getDescription() {
				return "Repair turbine damage";
			}

			@Override
			public Collection<ItemStack> getItems() {
				return Arrays.asList(EngineType.JET.getCraftedProduct());
			}

			@Override
			public void tick(TileEntity te, int tier) {
				((TileEntityJetEngine)te).repairJetCC(tier);
			}
		});
	}

	public static void blacklistTileAccelerator(MachineRegistry m) {
		switch(m) {
			case ADVANCEDGEARS:
				for (GearType mat : GearType.list)
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), mat.getItem(), BlacklistReason.EXPLOIT);
				break;
			case FLYWHEEL:
				for (Flywheels mat : Flywheels.list)
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), mat.getFlywheelItem(), BlacklistReason.EXPLOIT);
				break;
			case GEARBOX:
				for (GearboxTypes mat : GearboxTypes.typeList) {
					for (int r = 2; r <= 16; r *= 2)
						ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), mat.getGearboxItem(r), BlacklistReason.EXPLOIT);
				}
				break;
			case SHAFT:
				for (MaterialRegistry mat : MaterialRegistry.matList)
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), mat.getShaftItem(), BlacklistReason.EXPLOIT);
				break;
			case ENGINE:
				break;
			default:
				ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), m.getCraftedProduct(), BlacklistReason.EXPLOIT);
				break;
		}
	}

}

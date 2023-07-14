package Reika.RotaryCraft.ModInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import Reika.ChromatiCraft.API.AdjacencyUpgradeAPI.BlacklistReason;
import Reika.ChromatiCraft.API.ChromatiAPI;
import Reika.ChromatiCraft.API.Interfaces.CustomHealing.CustomTileHealing;
import Reika.ChromatiCraft.Base.TileEntity.TileEntityAdjacencyUpgrade;
import Reika.ChromatiCraft.Base.TileEntity.TileEntityAdjacencyUpgrade.AdjacencyEffectDescription;
import Reika.ChromatiCraft.Items.Tools.ItemAuraPouch;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.ChromatiCraft.TileEntity.AOE.Effect.TileEntityAccelerator;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.GUI.GuiItemDisplay.GuiStackListDisplay;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.Flywheels;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear.GearType;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

public class ChromaRC {

	@ModDependent(ModList.CHROMATICRAFT)
	public static void addAuraPouchItems() {
		ItemAuraPouch.setSpecialEffect(ItemAuraPouch.WORKS_IN_POUCH_EFFECT_DESC, ItemRegistry.NVG.getItemInstance());
		ItemAuraPouch.setSpecialEffect(ItemAuraPouch.WORKS_IN_POUCH_EFFECT_DESC, ItemRegistry.IOGOGGLES.getItemInstance());
		ItemAuraPouch.registerProjectileFiringEffect(ItemRegistry.FIREBALL.getItemInstance());
	}

	@ModDependent(ModList.CHROMATICRAFT)
	public static void addHealingCoreEffects() {
		final String desc = "Repair gearbox damage";
		ChromatiAPI.getAPI().adjacency().addCustomHealing(TileEntityGearbox.class, new CustomTileHealing() {
			@Override
			public boolean runOnClient() {
				return false;
			}

			@Override
			public String getDescription() {
				return desc;
			}

			@Override
			public Collection<ItemStack> getItems() {
				return new ArrayList();
			}

			@Override
			public void tick(TileEntity te, int tier) {
				((TileEntityGearbox)te).repairCC(tier);
			}
		});
		AdjacencyEffectDescription eff = TileEntityAdjacencyUpgrade.registerEffectDescription(CrystalElement.MAGENTA, desc);
		for (GearboxTypes gear : GearboxTypes.typeList) {
			GuiStackListDisplay li = new GuiStackListDisplay();
			for (int r = 2; r <= 16; r *= 2)
				li.addItems(gear.getGearboxItem(r));
			eff.addDisplays(li);
		}
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

	@ModDependent(ModList.CHROMATICRAFT)
	public static void blacklistTileAccelerator(MachineRegistry m) {
		switch(m) {
			case ADVANCEDGEARS:
				for (GearType mat : GearType.list)
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), mat.getItem(), BlacklistReason.EXPLOIT);
				break;
			case FLYWHEEL:
				for (Flywheels mat : Flywheels.list)
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), null, BlacklistReason.EXPLOIT);
				break;
			case GEARBOX:
				for (GearboxTypes mat : GearboxTypes.typeList) {
					for (int r = 2; r <= 16; r *= 2)
						ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), null, BlacklistReason.EXPLOIT);
				}
				break;
			case SHAFT:
				for (MaterialRegistry mat : MaterialRegistry.matList)
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), null, BlacklistReason.EXPLOIT);
				break;
			case ENGINE: //handled externally
				break;
			default:
				ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(m.getTEClass(), m.getName(), m.getCraftedProduct(), BlacklistReason.EXPLOIT);
				break;
		}
	}

	@ModDependent(ModList.CHROMATICRAFT)
	public static void addAcceleratorGroupedBlacklistItems() {
		for (GearboxTypes gear : GearboxTypes.typeList) {
			ItemStack[] items = new ItemStack[4];
			for (int i = 0; i < 4; i++)
				items[i] = gear.getGearboxItem(ReikaMathLibrary.intpow2(2, i+1));
			TileEntityAccelerator.addBlacklistItems(items);
		}

		ItemStack[] items = new ItemStack[MaterialRegistry.matList.length];
		for (int i = 0; i < items.length; i++)
			items[i] = MaterialRegistry.matList[i].getShaftItem();
		TileEntityAccelerator.addBlacklistItems(items);

		items = new ItemStack[Flywheels.list.length];
		for (int i = 0; i < items.length; i++)
			items[i] = Flywheels.list[i].getFlywheelItem();
		TileEntityAccelerator.addBlacklistItems(items);
	}

}

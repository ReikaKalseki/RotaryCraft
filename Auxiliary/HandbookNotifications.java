/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import Reika.DragonAPI.Auxiliary.Trackers.PlayerHandler.PlayerTracker;
import Reika.DragonAPI.Instantiable.Alert;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ConfigRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HandbookNotifications {

	public static final HandbookNotifications instance = new HandbookNotifications();

	private final HashMap<UUID, ArrayList<Alert>> data = new HashMap();

	private final HashMap<UUID, Boolean> alert = new HashMap();

	private HandbookNotifications() {

	}

	@SideOnly(Side.CLIENT)
	public boolean newAlerts() {
		UUID uid = Minecraft.getMinecraft().thePlayer.getUniqueID();
		return alert.containsKey(uid);
	}

	@SideOnly(Side.CLIENT)
	public void clearAlert() {
		alert.remove(Minecraft.getMinecraft().thePlayer.getUniqueID());
	}

	@SideOnly(Side.CLIENT)
	public List<Alert> getNewAlerts() {
		EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
		ArrayList<Alert> li = data.get(ep.getUniqueID());
		return li != null ? Collections.unmodifiableList(li) : new ArrayList();
	}

	private void addAlert(EntityPlayer ep, ConfigRegistry c, Level lvl, String msg) {
		Alert a = new Alert(c.name().toLowerCase(Locale.ENGLISH), c, lvl, msg);
		ArrayList li = data.get(ep.getUniqueID());
		if (li == null) {
			li = new ArrayList();
			data.put(ep.getUniqueID(), li);
		}
		if (!li.contains(a))
			li.add(a);
	}

	public static class HandbookConfigVerifier implements PlayerTracker {

		public static final HandbookConfigVerifier instance = new HandbookConfigVerifier();

		private static final String NBT_TAG = "rc_config_alerts";

		private HashMap<ConfigRegistry, String> data = new HashMap();
		private HashMap<ConfigRegistry, Level> levels = new HashMap();

		private HandbookConfigVerifier() {
			this.addEntry(ConfigRegistry.ALLOWTNTCANNON, Level.INFO, "The TNT Cannon has been disabled.");
			this.addEntry(ConfigRegistry.ALLOWLIGHTBRIDGE, Level.INFO, "The Light Bridge has been disabled.");
			this.addEntry(ConfigRegistry.ALLOWEMP, Level.INFO, "The EMP has been disabled.");
			this.addEntry(ConfigRegistry.ATTACKBLOCKS, Level.WARNING, "Machines like the heat ray and EMP will not break blocks.");
			this.addEntry(ConfigRegistry.RAILGUNDAMAGE, Level.WARNING, "The Railgun will not cause block damage.");
			this.addEntry(ConfigRegistry.BANRAIN, Level.WARNING, "The Silver Iodide Cannon's ability to make rain has been disabled.");
			this.addEntry(ConfigRegistry.BEDPICKSPAWNERS, Level.WARNING, "The bedrock pickaxe's ability to harvest spawners has been disabled.");
			this.addEntry(ConfigRegistry.DIFFICULTY, Level.SEVERE, "The mod difficulty has been changed from the default.");
			this.addEntry(ConfigRegistry.EXTRACTORMAINTAIN, Level.SEVERE, "The Extractor has been set to require drill maintenance.");
			this.addEntry(ConfigRegistry.GRAVELPLAYER, Level.WARNING, "Gravel Gun PvP has been disabled.");
			this.addEntry(ConfigRegistry.HARDGRAVELGUN, Level.SEVERE, "Gravel Gun damage has been reduced.");
			this.addEntry(ConfigRegistry.HSLADICT, Level.INFO, "HSLA has been made usable in other mods' recipes.");
			this.addEntry(ConfigRegistry.INSTACUT, Level.WARNING, "The Woodcutter has been changed to not cut trees as cleanly or effectively.");
			this.addEntry(ConfigRegistry.MODORES, Level.SEVERE, "Forced mod ore compatibility has been disabled.");
			this.addEntry(ConfigRegistry.SPAWNERLEAK, Level.WARNING, "Mob spawn when breaking monster spawners has been disabled.");
			this.addEntry(ConfigRegistry.TABLEMACHINES, Level.INFO, "Machines can be crafted in tables other than the Worktable.");
			this.addEntry(ConfigRegistry.TURRETPLAYERS, Level.WARNING, "Turrets' ability to target players has been disabled.");
			this.addEntry(ConfigRegistry.VOIDHOLE, Level.WARNING, "The bedrock breaker has been made able to open holes to the Void.");
			this.addEntry(ConfigRegistry.NOMINERS, Level.SEVERE, "All automining machines have been disabled.");
			this.addEntry(ConfigRegistry.BORERMAINTAIN, Level.SEVERE, "The Borer has been set to require maintenance.");
			this.addEntry(ConfigRegistry.JETFUELPACK, Level.WARNING, "The jetpack requires jet fuel to operate.");
			this.addEntry(ConfigRegistry.CONVERTERLOSS, Level.SEVERE, ConfigRegistry.enableConverters() ? "RC to Mod Power Converter Losses Added." : "RC to Mod Power Converters Disabled.");
			this.addEntry(ConfigRegistry.BEEYEAST, Level.WARNING, "Yeast is a bee product, not a Fermenter output.");
		}

		private void addEntry(ConfigRegistry cfg, Level lvl, String sg) {
			data.put(cfg, sg);
			levels.put(cfg, lvl);

			if (this.isChanged(cfg)) {
				RotaryCraft.logger.log("Config Change: \""+cfg.getLabel()+"\" from default!");
			}
		}

		@Override
		public void onPlayerLogin(EntityPlayer ep) {
			NBTTagCompound eptag = ReikaPlayerAPI.getDeathPersistentNBT(ep);
			NBTTagCompound nbt = eptag.hasKey(NBT_TAG) ? eptag.getCompoundTag(NBT_TAG) : new NBTTagCompound();
			boolean empty = true;
			for (ConfigRegistry cfg : data.keySet()) {
				String tag = cfg.name().toLowerCase(Locale.ENGLISH);
				boolean mark = nbt.getBoolean(tag);
				boolean chg = this.isChanged(cfg);
				if (chg != mark)
					empty = false;
				if (chg) {
					HandbookNotifications.instance.addAlert(ep, cfg, levels.get(cfg), data.get(cfg));
				}
				else {

				}
				nbt.setBoolean(tag, chg);
			}
			if (!empty)
				HandbookNotifications.instance.alert.put(ep.getUniqueID(), true);
			eptag.setTag(NBT_TAG, nbt);
		}

		public boolean isChanged(ConfigRegistry option) {
			if (option.isBoolean()) {
				return option.getDefaultState() != option.getState();
			}
			else if (option.isNumeric()) {
				return option.getDefaultValue() != option.getValue();
			}
			else if (option.isDecimal()) {
				return option.getDefaultFloat() != option.getFloat();
			}
			else {
				return true;
			}
		}

		@Override
		public void onPlayerLogout(EntityPlayer player) {

		}

		@Override
		public void onPlayerChangedDimension(EntityPlayer player, int dimFrom, int dimTo) {

		}

		@Override
		public void onPlayerRespawn(EntityPlayer player) {

		}

	}

}

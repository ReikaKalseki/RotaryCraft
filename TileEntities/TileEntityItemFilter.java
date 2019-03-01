/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.ModularLogger;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Instantiable.ModInteract.BasicAEInterface;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.MESystemReader;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import appeng.api.AEApi;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.util.AECableType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;


@Strippable(value={"appeng.api.networking.IActionHost"})
public class TileEntityItemFilter extends InventoriedPowerReceiver implements IActionHost {

	private static final String LOGGER_ID = "ItemFilter";

	static {
		ModularLogger.instance.addLogger(RotaryCraft.instance, LOGGER_ID);
	}

	private MatchData data;

	@ModDependent(ModList.APPENG)
	private MESystemReader network;
	private Object aeGridBlock;
	private Object aeGridNode;

	private final StepTimer updateTimer = new StepTimer(200);

	private final ArrayList<ItemStack> MEStacks = new ArrayList();

	public static final int BLACKLIST_SLOTS = 16;
	private final HashSet<KeyedItemStack> blacklist = new HashSet();

	public TileEntityItemFilter() {
		if (ModList.APPENG.isLoaded()) {
			aeGridBlock = new BasicAEInterface(this, this.getMachine().getCraftedProduct());
			aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;

			//for (int i = 0; i < lock.length; i++) {
			//	lock[i] = new CraftingLock();
			//}
		}
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack is, int side) {
		return slot == 1;
	}

	@Override
	public int getSizeInventory() {
		return 2+BLACKLIST_SLOTS;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot == 1 && power >= MINPOWER && this.matchItem(is);
	}

	private boolean matchItem(ItemStack is) {
		return data != null && !blacklist.contains(new KeyedItemStack(is).setSimpleHash(true)) && data.match(is) != this.hasRedstoneSignal();
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	protected void onInventoryChanged(int slot) {
		this.reloadData();

		if (slot >= 2) {
			this.rebuildBlacklist();
		}
	}

	private void rebuildBlacklist() {
		blacklist.clear();
		for (int i = 2; i < inv.length; i++) {
			if (inv[i] != null)
				blacklist.add(new KeyedItemStack(inv[i]).setSimpleHash(true).setIgnoreNBT(false).setSized(false));
		}
	}

	public void reloadData() {
		data = inv[0] != null ? new MatchData(inv[0]).loadFrom(data) : null;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.ITEMFILTER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();

		if (ModList.APPENG.isLoaded() && power >= MINPOWER) {
			updateTimer.update();
			if (updateTimer.checkCap() && !world.isRemote) {
				this.buildCache();
			}

			if (network != null)
				network.tick();
			if (aeGridBlock != null && !world.isRemote) {
				((BasicAEInterface)aeGridBlock).setPowerCost(power >= MINPOWER ? 2 : 1);
			}

			if (network != null && !world.isRemote && inv[1] == null && !MEStacks.isEmpty() && data != null) {
				int idx = rand.nextInt(MEStacks.size());
				ItemStack is = MEStacks.get(idx);
				is = ReikaItemHelper.getSizedItemStack(is, is.getMaxStackSize());
				int ret = (int)network.removeItem(is, false, true);
				if (ret > 0) {
					inv[1] = ReikaItemHelper.getSizedItemStack(is, ret);
				}
			}
		}

		if (data == null && inv[0] != null) {
			this.onInventoryChanged(0);
		}
	}

	private void buildCache() {
		if (ModList.APPENG.isLoaded()) {
			Object oldNode = aeGridNode;
			if (aeGridNode == null) {
				aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;
			}
			if (aeGridNode != null)
				((IGridNode)aeGridNode).updateState();

			if (oldNode != aeGridNode || network == null) {
				if (aeGridNode == null)
					network = null;
				else if (network == null)
					network = new MESystemReader((IGridNode)aeGridNode, this);
				else
					network = new MESystemReader((IGridNode)aeGridNode, network);
			}

			if (network != null && data != null) {
				MEStacks.clear();
				if (ModularLogger.instance.isEnabled(LOGGER_ID))
					ModularLogger.instance.log(LOGGER_ID, "Found raw ME: "+network.getRawMESystemContents());
				for (ItemStack is : network.getRawMESystemContents()) {
					if (this.matchItem(is)) {
						if (ModularLogger.instance.isEnabled(LOGGER_ID))
							ModularLogger.instance.log(LOGGER_ID, "@ "+this+" ["+this.hasRedstoneSignal()+"], "+is.getDisplayName()+":"+is.getItemDamage()+" {"+is.stackTagCompound+"}"+" matches "+data);
						MEStacks.add(ReikaItemHelper.getSizedItemStack(is, 1));
					}
					else {
						if (ModularLogger.instance.isEnabled(LOGGER_ID))
							ModularLogger.instance.log(LOGGER_ID, is.getDisplayName()+" does not match.");
					}
				}
			}
		}
	}

	@Override
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalid) {
		super.onInvalidateOrUnload(world, x, y, z, invalid);
		if (ModList.APPENG.isLoaded() && aeGridNode != null)
			((IGridNode)aeGridNode).destroy();
	}

	public MatchData getData() {
		return data;
	}

	public void setData(MatchData dat) {
		data = dat;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		if (data != null) {
			NBTTagCompound tag = data.writeToNBT();
			NBT.setTag("data", tag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		if (NBT.hasKey("data")) {
			data = MatchData.createFromNBT(NBT.getCompoundTag("data"));
		}

		this.rebuildBlacklist();
	}

	@Override
	@ModDependent(ModList.APPENG)
	public IGridNode getGridNode(ForgeDirection dir) {
		return (IGridNode)aeGridNode;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.COVERED;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public void securityBreak() {

	}

	@Override
	@ModDependent(ModList.APPENG)
	public IGridNode getActionableNode() {
		return (IGridNode)aeGridNode;
	}

	public static class MatchData {

		private MatchType matchID = MatchType.MATCH;
		private MatchType matchMetadata = MatchType.MATCH;
		private MatchType matchMod = MatchType.MATCH;

		private MatchType doCheckNBT = MatchType.MATCH; //true if any of the matches in matchNBT are not 'ignore'
		private MatchType doCheckOre = MatchType.MATCH;

		private final MatchType[] matchOre;
		private final NBTTagCompound matchNBT; //compound and list matches if has tag, not tag.equals(); everything else equals
		private final HashMap<String, MatchType> matchClass;

		public final Item itemID;
		public final int metadata;
		public final String modID;
		private final String[] oreDict;
		private final NBTTagCompound nbt;
		private final Collection<String> classList;

		private MatchData(ItemStack is) {
			itemID = is.getItem();
			metadata = is.getItemDamage();
			modID = ReikaItemHelper.getRegistrantMod(is);
			oreDict = ReikaItemHelper.getOreNamesArray(is);
			nbt = is.stackTagCompound != null ? (NBTTagCompound)is.stackTagCompound.copy() : null;
			matchOre = ReikaArrayHelper.getArrayOf(MatchType.MATCH, oreDict.length);
			if (nbt != null) {
				matchNBT = (NBTTagCompound)nbt.copy();
				this.parseNBT(matchNBT);
			}
			else {
				matchNBT = null;
			}
			classList = this.calcClasses(is.getItem());
			matchClass = new HashMap();
			for (String s : classList) {
				matchClass.put(s, MatchType.MATCH);
			}
		}

		private Collection<String> calcClasses(Item item) {
			ArrayList<String> li = new ArrayList();
			Class c = item.getClass();
			int n = 0;
			do {
				String s = ReikaStringParser.getNOf(">", n);
				li.add(s+c.getSimpleName());
				Class[] ints = c.getInterfaces();
				for (int i = 0; i < ints.length; i++) {
					li.add(s+"%"+ints[i].getSimpleName());
				}
				c = c.getSuperclass();
				n++;
			} while (c != null && c != Item.class); //no point in showing Item (or Object)
			return li;
		}

		private MatchData(Item itemID, int metadata, String modID, NBTTagCompound nbt, String[] oreDict, Collection<String> classList, MatchType matchID, MatchType matchMetadata, MatchType matchMod, MatchType doCheckNBT, MatchType doCheckOre, MatchType[] matchOre, NBTTagCompound matchNBT, HashMap<String, MatchType> matchClass) {
			if (matchNBT.hasNoTags())
				matchNBT = null;
			if (nbt.hasNoTags())
				nbt = null;
			this.itemID = itemID;
			this.metadata = metadata;
			this.modID = modID;
			this.nbt = nbt;
			this.oreDict = oreDict;
			this.classList = classList;
			this.matchID = matchID;
			this.matchMetadata = matchMetadata;
			this.matchMod = matchMod;
			this.doCheckNBT = doCheckNBT;
			this.doCheckOre = doCheckOre;
			this.matchOre = matchOre;
			this.matchNBT = matchNBT;
			this.matchClass = matchClass;
		}

		public MatchData loadFrom(MatchData data) {
			if (data != null) {
				matchID = data.matchID;
				matchMetadata = data.matchMetadata;
				matchMod = data.matchMod;
				doCheckNBT = data.doCheckNBT;
			}
			return this;
		}

		public NBTTagCompound writeToNBT() {
			NBTTagCompound tag = new NBTTagCompound();

			NBTTagCompound settings = new NBTTagCompound();
			settings.setInteger("item", matchID.ordinal());
			settings.setInteger("dmg", matchMetadata.ordinal());
			settings.setInteger("mod", matchMod.ordinal());
			settings.setInteger("checknbt", doCheckNBT.ordinal());
			settings.setInteger("checkore", doCheckOre.ordinal());
			NBTTagCompound ore2 = new NBTTagCompound();
			for (int i = 0; i < oreDict.length; i++) {
				ore2.setInteger(oreDict[i], matchOre[i].ordinal());
			}
			settings.setTag("ores", ore2);
			if (nbt != null)
				settings.setTag("nbt", matchNBT);

			NBTTagCompound classes = new NBTTagCompound();
			for (String s : matchClass.keySet()) {
				MatchType m = matchClass.get(s);
				classes.setInteger(s, m.ordinal());
			}
			settings.setTag("classes", classes);

			tag.setTag("settings", settings);

			NBTTagCompound val = new NBTTagCompound();
			val.setString("item", Item.itemRegistry.getNameForObject(itemID));
			val.setInteger("dmg", metadata);
			val.setString("mod", modID);
			if (nbt != null)
				val.setTag("nbt", nbt);
			tag.setTag("value", val);
			NBTTagList ore = new NBTTagList();
			for (int i = 0; i < oreDict.length; i++) {
				ore.appendTag(new NBTTagString(oreDict[i]));
			}
			val.setTag("ores", ore);
			NBTTagList classTypes = new NBTTagList();
			for (String s : classList) {
				classTypes.appendTag(new NBTTagString(s));
			}
			val.setTag("classes", classTypes);

			return tag;
		}

		public static MatchData createFromNBT(NBTTagCompound tag) {

			NBTTagCompound val = tag.getCompoundTag("value");
			Item itemID = (Item)Item.itemRegistry.getObject(val.getString("item"));
			int metadata = val.getInteger("dmg");
			String modID = val.getString("mod");
			NBTTagCompound nbt = val.getCompoundTag("nbt");
			NBTTagList ore = val.getTagList("ores", NBTTypes.STRING.ID);
			ArrayList<String> li = new ArrayList();
			for (Object o : ore.tagList) {
				NBTTagString s = (NBTTagString)o;
				li.add(s.func_150285_a_());
			}
			String[] oreDict = li.toArray(new String[li.size()]);
			NBTTagList classTypes = val.getTagList("classes", NBTTypes.STRING.ID);
			ArrayList<String> classList = new ArrayList();
			for (Object o : classTypes.tagList) {
				String s = ((NBTTagString)o).func_150285_a_();
				classList.add(s);
			}

			NBTTagCompound settings = tag.getCompoundTag("settings");
			MatchType matchID = MatchType.list[settings.getInteger("item")];
			MatchType matchMetadata = MatchType.list[settings.getInteger("dmg")];
			MatchType matchMod = MatchType.list[settings.getInteger("mod")];
			MatchType doCheckNBT = MatchType.list[settings.getInteger("checknbt")];
			MatchType doCheckOre = MatchType.list[settings.getInteger("checkore")];
			NBTTagCompound ore2 = settings.getCompoundTag("ores");
			MatchType[] matchOre = new MatchType[oreDict.length];
			for (int i = 0; i < oreDict.length; i++) {
				matchOre[i] = MatchType.list[ore2.getInteger(oreDict[i])];
			}
			NBTTagCompound matchNBT = settings.getCompoundTag("nbt");
			NBTTagCompound classes = settings.getCompoundTag("classes");
			HashMap<String, MatchType> matchClasses = new HashMap();
			for (Object o : classes.func_150296_c()) {
				String s = (String)o;
				MatchType m = MatchType.list[classes.getInteger(s)];
				matchClasses.put(s, m);
			}
			return new MatchData(itemID, metadata, modID, nbt, oreDict, classList, matchID, matchMetadata, matchMod, doCheckNBT, doCheckOre, matchOre, matchNBT, matchClasses);
		}

		private void incrementSetting(MatchDisplay m) {
			switch(m.type) {
				case BASIC:
					switch(m.displayName) {
						case "Item ID":
							matchID = matchID.getNext();
							//ReikaJavaLibrary.pConsole(matchID+">"+matchID.ordinal());
							//ReikaJavaLibrary.pConsole(this+">"+FMLCommonHandler.instance().getEffectiveSide());
							break;
						case "Metadata":
							matchMetadata = matchMetadata.getNext();
							break;
						case "Mod ID":
							matchMod = matchMod.getNext();
							break;
						case "NBT Overall":
							doCheckNBT = doCheckNBT.getNext();
							break;
						case "OreDict Overall":
							doCheckOre = doCheckOre.getNext();
							break;
					}
					break;
				case NBT:
					NBTTagCompound b = matchNBT;
					//ReikaJavaLibrary.pConsole(m.displayName+" > "+m.tags+" == "+matchNBT);
					if (m.tags != null) {
						for (String s : m.tags) {
							b = b.getCompoundTag(s);
						}
					}
					NBTTagCompound tag = b.getCompoundTag(m.displayName);
					//ReikaJavaLibrary.pConsole(tag.func_150296_c()+" ("+m+") of "+b+" {"+b.func_150296_c()+"}");
					MatchType match = MatchType.list[tag.getInteger("type")];
					tag.setInteger("type", match.getNext().ordinal());
					//ReikaJavaLibrary.pConsole(match.getNext()+" for "+tag);
					break;
				case ORE:
					int i = Integer.parseInt(m.displayName);
					matchOre[i] = matchOre[i].getNext();
					break;
				case CLASS:
					matchClass.put(m.internalID, matchClass.get(m.internalID).getNext());
					break;
			}
		}

		public ArrayList<MatchDisplay> getMainDisplay() {
			ArrayList<MatchDisplay> li = new ArrayList();
			li.add(new MatchDisplay(this, SettingType.BASIC, "Item ID", Item.itemRegistry.getNameForObject(itemID), "id", matchID));
			li.add(new MatchDisplay(this, SettingType.BASIC, "Metadata", String.valueOf(metadata), "meta", matchMetadata));
			li.add(new MatchDisplay(this, SettingType.BASIC, "Mod ID", modID, "mod", matchMod));
			li.add(new MatchDisplay(this, SettingType.BASIC, "NBT Overall", "", "nbt", doCheckNBT));
			li.add(new MatchDisplay(this, SettingType.BASIC, "OreDict Overall", Arrays.toString(oreDict), "ore", doCheckOre));
			return li;
		}

		public ArrayList<MatchDisplay> getOreDisplay() {
			ArrayList<MatchDisplay> li = new ArrayList();
			for (int i = 0; i < oreDict.length; i++) {
				li.add(new MatchDisplay(this, SettingType.ORE, String.valueOf(i), oreDict[i], oreDict[i], matchOre[i]));
			}
			return li;
		}

		public ArrayList<MatchDisplay> getClassDisplay() {
			ArrayList<MatchDisplay> li = new ArrayList();
			for (String s : classList) {
				String orig = s;
				String id = "Item Class";
				if (s.startsWith(">")) {
					int n = 0;
					while (s.startsWith(">")) {
						s = s.substring(1);
						n++;
					}
					id = "Parent Class x"+n;
				}
				if (s.startsWith("%")) {
					s = s.substring(1);
					id = "Interface";
				}
				String val = s;
				li.add(new MatchDisplay(this, SettingType.CLASS, id, val, orig, matchClass.get(orig)));
			}
			return li;
		}

		public ArrayList<MatchDisplay> getNBTDisplay() {
			if (nbt == null || nbt.hasNoTags())
				return new ArrayList();
			return this.getNBTDisplay(nbt, matchNBT, new LinkedList());
		}

		private ArrayList<MatchDisplay> getNBTDisplay(NBTTagCompound tag, NBTTagCompound matchRef, LinkedList<String> tags) {
			ArrayList<MatchDisplay> li = new ArrayList();
			for (Object o : tag.func_150296_c()) {
				String s = (String)o;
				NBTBase b = tag.getTag(s);
				NBTTagCompound match = !tags.isEmpty() && tags.getLast().equals("tag") ? matchRef.getCompoundTag("tag").getCompoundTag(s) : matchRef.getCompoundTag(s);
				//ReikaJavaLibrary.pConsole(match+" from "+s+" in "+matchRef+" with "+tags);
				MatchType m = MatchType.list[match.getInteger("type")];
				if (b instanceof NBTTagList) {
					MatchDisplay md = new MatchDisplay(this, SettingType.NBT, s, "", "", m);
					md.tags = new LinkedList(tags);
					li.add(md);
					tags.add(s);
					li.addAll(this.getNBTDisplay((NBTTagList)b, match, tags));
				}
				else if (b instanceof NBTTagCompound) {
					MatchDisplay md = new MatchDisplay(this, SettingType.NBT, s, "", "", m);
					md.tags = new LinkedList(tags);
					li.add(md);
					tags.add(s);
					li.addAll(this.getNBTDisplay((NBTTagCompound)b, match, tags));
				}
				else {
					li.add(new MatchDisplay(this, SettingType.NBT, s, b, tags, "", m));
				}
			}
			if (!tags.isEmpty()) {
				if (tags.getLast().equals("tag"))
					tags.removeLast();
				tags.removeLast();
			}
			//ReikaJavaLibrary.pConsole(matchRef+" > "+li);
			return li;
		}

		private ArrayList<MatchDisplay> getNBTDisplay(NBTTagList tag, NBTTagCompound matchRef, LinkedList<String> tags) {
			ArrayList<MatchDisplay> li = new ArrayList();
			for (int i = 0; i < tag.tagList.size(); i++) {
				String s = "#"+i;
				NBTBase b = (NBTBase)tag.tagList.get(i);
				NBTTagCompound match = matchRef.getCompoundTag(s);
				MatchType m = MatchType.list[match.getInteger("type")];
				if (b instanceof NBTTagList) {
					MatchDisplay md = new MatchDisplay(this, SettingType.NBT, s, "", "", m);
					md.tags = new LinkedList(tags);
					li.add(md);
					tags.add(s);
					tags.add("tag");
					li.addAll(this.getNBTDisplay((NBTTagList)b, match, tags));
				}
				else if (b instanceof NBTTagCompound) {
					MatchDisplay md = new MatchDisplay(this, SettingType.NBT, s, "", "", m);
					md.tags = new LinkedList(tags);
					li.add(md);
					tags.add(s);
					tags.add("tag");
					li.addAll(this.getNBTDisplay((NBTTagCompound)b, match, tags));
				}
				else {
					li.add(new MatchDisplay(this, SettingType.NBT, s, b, tags, "", m));
				}
			}
			if (!tags.isEmpty()) {
				if (tags.getLast().equals("tag"))
					tags.removeLast();
				tags.removeLast();
			}
			return li;
		}

		public boolean match(ItemStack is) {
			if (!matchID.check(is.getItem() == itemID))
				return false;
			if (!matchMetadata.check(is.getItemDamage() == metadata))
				return false;
			if (!matchMod.check(ReikaItemHelper.getRegistrantMod(is).equals(modID)))
				return false;
			if (doCheckOre != MatchType.IGNORE) {
				for (int i = 0; i < matchOre.length; i++) {
					String s = oreDict[i];
					if (!matchOre[i].check(ReikaItemHelper.isInOreTag(is, s)))
						return false;
				}
			}
			Item item = is.getItem();
			Class c1 = itemID.getClass();
			Class c2 = item.getClass();
			int n = 0;
			do {
				String s = ReikaStringParser.getNOf(">", n);
				MatchType m = matchClass.get(s+c1.getSimpleName());
				if (!m.check(c1 == c2))
					return false;
				HashSet<Class> ints1 = ReikaJavaLibrary.makeSetFromArray(c1.getInterfaces());
				HashSet<Class> ints2 = ReikaJavaLibrary.makeSetFromArray(c2.getInterfaces());
				for (Class c : ints1) {
					m = matchClass.get(s+"%"+c.getSimpleName());
					if (!m.check(ints2.contains(ints1)))
						return false;
				}
				c1 = c1.getSuperclass();
				c2 = c2.getSuperclass();
				n++;
			} while (c1 != null && c1 != Item.class && c2 != null && c2 != Item.class);
			if (doCheckNBT != MatchType.IGNORE) {
				if (nbt == is.stackTagCompound) {
					if (doCheckNBT.check(true))
						return true;
					if (doCheckNBT == MatchType.MISMATCH)
						return false;
				}
				if (nbt == null && is.stackTagCompound != null) {
					if (!doCheckNBT.check(false))
						return false;
					if (doCheckNBT == MatchType.MISMATCH)
						return true;
				}
				if (nbt != null && is.stackTagCompound == null) {
					if (!doCheckNBT.check(false))
						return false;
					if (doCheckNBT == MatchType.MISMATCH)
						return true;
				}
				if (!this.tryMatchNBT(is.stackTagCompound, nbt, matchNBT))
					return false;
			}
			return true;
		}

		private boolean tryMatchNBT(NBTTagCompound NBT, NBTTagCompound parentRef, NBTTagCompound matchRef) {
			//ReikaJavaLibrary.pConsole(NBT+" + "+parentRef+" + "+matchRef);
			for (Object o : matchRef.func_150296_c()) {
				String s = (String)o;
				NBTBase b2 = NBT.getTag(s);
				NBTTagCompound match = matchRef.getCompoundTag(s);
				//ReikaJavaLibrary.pConsole(s+" > "+match+" & "+b2);
				MatchType m = MatchType.list[match.getInteger("type")];
				if (m == MatchType.IGNORE)
					continue;
				NBTBase val = match.getTag("tag");
				//ReikaJavaLibrary.pConsole(s+" > "+m+" : "+match+" & "+b2+" with "+val+" in "+NBT+" & "+parentRef+" & "+matchRef);
				if (val != null && b2 != null && val.getClass() != b2.getClass()) {
					//ReikaJavaLibrary.pConsole(val+" @ "+s);
					return m.check(false);
				}
				if (val instanceof NBTTagCompound) {
					if (!this.tryMatchNBT((NBTTagCompound)b2, (NBTTagCompound)val, match)) {
						if (!m.check(false))
							return false;
					}
				}
				else if (val instanceof NBTTagList) {
					if (!this.tryMatchNBT((NBTTagList)b2, (NBTTagList)val, match)) {
						//ReikaJavaLibrary.pConsole(val);
						if (!m.check(false))
							return false;
					}
				}
				else {
					if (val == b2) {
						return m.check(true);
					}
					if (val == null || b2 == null) {
						//ReikaJavaLibrary.pConsole(val+" @ "+s);
						return m.check(false);
					}
					if (val instanceof NBTTagCompound || val instanceof NBTTagList)
						;//return m.check(true);
					if (!m.check(val.equals(b2))) {
						//ReikaJavaLibrary.pConsole(val+" @ "+s);
						return false;
					}
				}
			}
			return true;
		}

		private boolean tryMatchNBT(NBTTagList NBT, NBTTagList parentRef, NBTTagCompound matchRef) {
			//ReikaJavaLibrary.pConsole(NBT+" + "+parentRef+" + "+matchRef);
			for (int i = 0; i < parentRef.tagList.size(); i++) {
				String s = "#"+i;
				NBTBase b2 = (NBTBase)parentRef.tagList.get(i);
				NBTTagCompound match = matchRef.getCompoundTag(s);
				MatchType m = MatchType.list[match.getInteger("type")];
				if (m == MatchType.IGNORE)
					continue;
				NBTBase val = match.getTag("tag");
				if (val == b2)
					return m.check(true);
				if (val == null || b2 == null)
					return m.check(false);
				if (val.getClass() != b2.getClass())
					return m.check(false);
				if (val instanceof NBTTagCompound) {
					if (!this.tryMatchNBT((NBTTagCompound)b2, (NBTTagCompound)val, match)) {
						//ReikaJavaLibrary.pConsole(val+" @ "+s);
						return false;
					}
				}
				else if (val instanceof NBTTagList) {
					if (!this.tryMatchNBT((NBTTagList)b2, (NBTTagList)val, match)) {
						//ReikaJavaLibrary.pConsole(val+" @ "+s);
						if (!m.check(false))
							return false;
					}
				}
				else {
					if (!m.check(val.equals(b2))) {
						//ReikaJavaLibrary.pConsole(val+" @ "+s);
						if (!m.check(false))
							return false;
					}
				}
			}
			return true;
		}

		private void parseNBT(NBTTagCompound NBT) {
			for (Object o : NBT.func_150296_c()) {
				String s = (String)o;
				NBTBase b = NBT.getTag(s);
				if (b instanceof NBTTagList) {
					this.parseNBT(NBT, s, (NBTTagList)b);
				}
				else if (b instanceof NBTTagCompound) {
					this.parseNBT((NBTTagCompound)b);
				}
				else {
					ReikaNBTHelper.replaceTag(NBT, s, NBTMatch.asTag(s, b));
				}
			}
		}

		private void parseNBT(NBTTagCompound parent, String parentName, NBTTagList NBT) {
			for (int i = 0; i < NBT.tagList.size(); i++) {
				String s = "#"+i;
				NBTBase b = (NBTBase)NBT.tagList.get(i);
				if (b instanceof NBTTagList) {
					this.parseNBT(NBT, i, (NBTTagList)b);
				}
				else if (b instanceof NBTTagCompound) {
					this.parseNBT((NBTTagCompound)b);
				}
				else {
					ReikaNBTHelper.replaceTag(NBT, i, NBTMatch.asTag(s, b));
				}
			}
			NBTTagCompound repl = new NBTTagCompound();
			for (int i = 0; i < NBT.tagList.size(); i++) {
				NBTBase b = (NBTBase)NBT.tagList.get(i);
				String s = "#"+i;
				repl.setTag(s, NBTMatch.asTag(s, b));
			}
			ReikaNBTHelper.combineNBT(repl, NBTMatch.asTag(parentName, NBT));
			ReikaNBTHelper.replaceTag(parent, parentName, repl);
		}

		private void parseNBT(NBTTagList parent, int idx, NBTTagList NBT) {
			for (int i = 0; i < NBT.tagList.size(); i++) {
				String s = "#"+i;
				NBTBase b = (NBTBase)NBT.tagList.get(i);
				if (b instanceof NBTTagList) {
					this.parseNBT(NBT, i, (NBTTagList)b);
				}
				else if (b instanceof NBTTagCompound) {
					this.parseNBT((NBTTagCompound)b);
				}
				else {
					ReikaNBTHelper.replaceTag(NBT, i, NBTMatch.asTag(s, b));
				}
			}
			NBTTagCompound repl = new NBTTagCompound();
			for (int i = 0; i < NBT.tagList.size(); i++) {
				NBTBase b = (NBTBase)NBT.tagList.get(i);
				String s = "#"+i;
				repl.setTag(s, b);
			}
			ReikaNBTHelper.combineNBT(repl, NBTMatch.asTag("#"+idx, NBT));
			ReikaNBTHelper.replaceTag(parent, idx, repl);
		}

		@Override
		public String toString() {
			return matchID+"/"+matchMetadata+"/"+matchMod+"/"+doCheckOre+"/"+doCheckNBT+" = "+itemID+" / "+metadata+" / "+modID+" / "+Arrays.toString(oreDict)+" / "+Arrays.toString(matchOre)+" / "+matchNBT;
		}
	}

	private static class NBTMatch {

		private MatchType match = MatchType.MATCH;
		private final NBTBase tag;

		private NBTMatch(NBTBase b) {
			tag = b;
		}

		public static NBTTagCompound asTag(String s, NBTBase b) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("name", s);
			tag.setTag("tag", b);
			tag.setInteger("type", MatchType.MATCH.ordinal());
			return tag;
		}

		public boolean match(NBTBase b) {
			if (match == MatchType.IGNORE)
				return true;
			if (tag != null && b == null)
				if (!match.check(false))
					return false;
			if (tag instanceof NBTTagCompound || tag instanceof NBTTagList)
				return match.check(b.getClass() == tag.getClass());
			return match.check(tag.equals(b));
		}

	}

	public static class MatchDisplay {

		private final MatchData source;
		private final SettingType type;

		private LinkedList<String> tags;

		private MatchType setting;
		public final String displayName;
		public final String value;

		private final String internalID;

		private MatchDisplay(MatchData src, SettingType type, String s, String val, String id, MatchType m) {
			setting = m;
			displayName = s;
			value = val;
			source = src;
			this.type = type;
			tags = null;
			internalID = id;
		}

		private MatchDisplay(MatchData src, SettingType type, String s, NBTBase b, LinkedList<String> li, String id, MatchType m) {
			this(src, type, s, b.toString(), id, m);
			tags = new LinkedList(li);
		}

		public MatchType getSetting() {
			return setting;
		}

		public void increment() {
			setting = setting.getNext();
			source.incrementSetting(this);
		}

		@Override
		public String toString() {
			return displayName+": "+setting.toString()+" & "+type.toString()+" % "+tags;
		}

	}

	public static enum MatchType {
		MATCH(0x008000, "Match"),
		MISMATCH(0xa00000, "Mismatch"),
		IGNORE(0xffd000, "Ignore");

		private static final MatchType[] list = values();
		public final String name;
		public final int color;

		private MatchType(int c, String n) {
			name = n;
			color = c;
		}

		public MatchType getNext() {
			return this.ordinal() == list.length-1 ? list[0] : list[this.ordinal()+1];
		}

		public boolean check(boolean match) {
			switch(this) {
				case IGNORE:
					return true;
				case MATCH:
					return match;
				case MISMATCH:
					return !match;
			}
			return false;
		}
	}

	public static enum SettingType {
		BASIC(),
		ORE(),
		NBT(),
		CLASS();

		private static final SettingType[] list = values();

		public SettingType previous() {
			return this.ordinal() == 0 ? this : list[this.ordinal()-1];
		}

		public SettingType next() {
			return this.ordinal() == list.length-1 ? this : list[this.ordinal()+1];
		}
	}


}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Interfaces.Registry.OreType;
import Reika.DragonAPI.Interfaces.Registry.OreType.OreRarity;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;

public class CustomExtractLoader {

	public static final CustomExtractLoader instance = new CustomExtractLoader();

	private final ArrayList<CustomExtractEntry> data = new ArrayList();

	private CustomExtractLoader() {

	}

	public static class CustomExtractEntry implements OreType {

		public final String displayName;
		public final OreRarity rarity;
		private final ArrayList<ItemStack> oreItems = new ArrayList();
		private final ArrayList<String> oreNames = new ArrayList();
		public final int color1;
		public final int color2;
		public final ProductType type;
		public final String productName;
		public final OreType nativeOre;
		public final int numberSmelted;
		public final int ordinal;

		private CustomExtractEntry(int idx, String name, OreRarity r, ProductType t, String prod, int n, int c1, int c2, OreType mod, String... ores) {
			displayName = name;
			for (int i = 0; i < ores.length; i++) {
				String s = ores[i];
				oreNames.add(s);
				oreItems.addAll(OreDictionary.getOres(s));
				if (oreItems.isEmpty())
					throw new IllegalStateException("Cannot have entries with no corresponding ores!");
			}
			rarity = r;
			color1 = c1;
			color2 = c2;
			productName = prod;
			type = t;
			nativeOre = mod;
			numberSmelted = n;
			ordinal = idx;
		}

		@Override
		public String toString() {
			return displayName+" to "+productName+" with rarity "+rarity+" ("+oreNames.size()+" ores found from "+oreNames+")";
		}

		@Override
		public OreRarity getRarity() {
			return rarity;
		}

		@Override
		public Collection<ItemStack> getAllOreBlocks() {
			return Collections.unmodifiableCollection(oreItems);
		}

		@Override
		public ItemStack getFirstOreBlock() {
			return oreItems.get(0).copy();
		}

		@Override
		public boolean canGenerateIn(Block b) {
			return false;
		}

		public EnumSet<OreLocation> getOreLocations() {
			return EnumSet.noneOf(OreLocation.class);
		}

		@Override
		public boolean existsInGame() {
			return true;
		}

		public int ordinal() {
			return ordinal;
		}

		@Override
		public String[] getOreDictNames() {
			return ReikaJavaLibrary.collectionToArray(oreNames);
		}
	}

	public final String getSaveFileName() {
		return "RotaryCraft_CustomExtracts.cfg";
	}

	public final String getFullSavePath() {
		return RotaryCraft.config.getConfigFolder().getAbsolutePath()+"/"+this.getSaveFileName();
	}

	public void loadFile() {
		RotaryCraft.logger.log("Loading custom extract config.");
		File f = new File(this.getFullSavePath());
		if (!f.exists())
			if (!this.createOreFile(f))
				return;
		try {
			BufferedReader p = ReikaFileReader.getReader(f);
			String line = "";
			while (line != null) {
				line = p.readLine();
				if (line != null && !line.isEmpty() && !line.startsWith("//")) {
					try {
						CustomExtractEntry entry = this.parseString(line);
						if (entry != null) {
							data.add(entry);
							RotaryCraft.logger.log("Added extract entry "+entry);
						}
						else {
							RotaryCraft.logger.logError("Malformed custom extract entry: "+line);
						}
					}
					catch (Exception e) {
						RotaryCraft.logger.logError("Malformed custom extract entry ["+e.getLocalizedMessage()+"]: '"+line+"'");
						e.printStackTrace();
					}
				}
			}
			p.close();
		}
		catch (Exception e) {
			RotaryCraft.logger.log(e.getMessage()+", and it caused the read to fail!");
			e.printStackTrace();
		}
	}

	private boolean createOreFile(File f) {
		try {
			f.createNewFile();
			PrintWriter p = new PrintWriter(f);
			this.writeCommentLine(p, "-------------------------------");
			this.writeCommentLine(p, " RotaryCraft Custom Extract Loader ");
			this.writeCommentLine(p, "-------------------------------");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Use this file to add custom ores and extracts to the extractor.");
			this.writeCommentLine(p, "Specify one per line, and format them as 'Name, Rarity, Product Type, Product Ore Name, Number, Color 1, Color 2, Native Ore, OreDictionary Name(s)'");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Ore rarity is the rarity of the ore blocks in the world, and affects the multiplication rates.");
			this.writeCommentLine(p, "Valid Rarity Values:");
			for (OreRarity o : OreRarity.values()) {
				this.writeCommentLine(p, "\t"+o.name()+" - "+o.desc+", like "+o.examples+"");
			}
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Valid Product Types:");
			for (ProductType o : ProductType.values()) {
				this.writeCommentLine(p, "\t"+o.displayName+" - "+o.desc);
			}
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Native ore is the native ore type of the output if you wish for the custom ore to produce the same smelted products as a native ore.");
			this.writeCommentLine(p, "Use 'null' for none to have the custom ore produce a unique smelted product.");
			this.writeCommentLine(p, "Valid Native Ores:");
			for (ReikaOreHelper o : ReikaOreHelper.values()) {
				this.writeCommentLine(p, "\t"+o.name()+" - "+o.getName());
			}
			for (ModOreList o : ModOreList.values()) {
				if (!o.isNetherOres())
					this.writeCommentLine(p, "\t"+o.name()+" - "+o.displayName+" "+o.getTypeName());
			}
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Capitalization for the ore dictionary names matters, but is ignored for rarities, types, and native ores.");
			this.writeCommentLine(p, "Ensure your OreDict names are correct; not all mods follow the 'oreName' and 'productName' convention.");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Colors must be hex codes; try to avoid conflicts with existing ores, including those natively handled by RC.");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "'Number' is the number of items normally obtained from the ore block, such as 1 for coal and 4 for redstone,");
			this.writeCommentLine(p, "and controls the number of items produced when smelting the flake. Use direct harvesting/smelting, not other processing.");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Sample Lines:");
			this.writeCommentLine(p, "\tSample Ore 1, SCARCE, INGOT, ingotSample, 1, 0xffffff, 0x73cc12, null, oreSample");
			this.writeCommentLine(p, "\tSample Ore 2, Common, dust, dustMetal, 4, 0x77003b, 0xb1a700, null, oreNotSample, oreSecondName, oreHasLotsOfVariants");
			this.writeCommentLine(p, "\tSample Ore 3, EVerYwHEre, gEm, ImproperIngot, 3, 0x1487a6, 0x27c61a, null, PoorlyNamedOre");
			this.writeCommentLine(p, "\tSample Ore 4, rare, Ingot, ingotEndCopper, 1, 0x16723d, 0xcb6faa, COPPER, oreEndCopper");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Entries missing names, rarities, types, products, or colors, or having less than one Ore Dictionary name, are incorrect.");
			this.writeCommentLine(p, "Incorrectly formatted lines will be ignored and will log an error in the console.");
			this.writeCommentLine(p, "Lines beginning with '//' are comments and will be ignored, as will empty lines. Spaces are stripped.");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "NOTE WELL: It is your responsibility to choose the ore blocks appropriately.");
			this.writeCommentLine(p, "\tWhile you can theoretically make anything processable in the Extractor,");
			this.writeCommentLine(p, "\tnull or missing blocks, and non-blocks are likely to crash and corrupt the");
			this.writeCommentLine(p, "\tworld. You may also create duplication exploits. No support will be provided in this case.");
			this.writeCommentLine(p, "====================================================================================");
			p.append("\n");
			p.close();
			return true;
		}
		catch (Exception e) {
			RotaryCraft.logger.logError("Could not generate CustomExtract Config.");
			e.printStackTrace();
			return false;
		}
	}

	private static void writeCommentLine(PrintWriter p, String line) {
		p.append("// "+line+"\n");
	}

	private CustomExtractEntry parseString(String s) throws Exception {
		String[] parts = s.split(",");
		for (int i = 1; i < parts.length; i++)
			parts[i] = ReikaStringParser.stripSpaces(parts[i]);
		if (parts.length < 8)
			throw new IllegalArgumentException("Invalid parameter count.");
		String name = parts[0];
		if (name.isEmpty())
			throw new IllegalArgumentException("Empty name is invalid.");
		OreRarity rarity = OreRarity.valueOf(parts[1].toUpperCase());
		ProductType type = ProductType.valueOf(parts[2].toUpperCase());
		String prod = parts[3];
		int smelt = Integer.parseInt(parts[4]);
		if (parts[5].startsWith("0x"))
			parts[5] = parts[5].substring(2);
		if (parts[6].startsWith("0x"))
			parts[6] = parts[6].substring(2);
		int c1 = Integer.parseInt(parts[5], 16);
		int c2 = Integer.parseInt(parts[6], 16);
		OreType ore = this.parseOreType(parts[7]);
		String[] ores = new String[parts.length-8];
		System.arraycopy(parts, 8, ores, 0, ores.length);
		return new CustomExtractEntry(data.size(), name, rarity, type, prod, smelt, c1, c2, ore, ores);
	}

	private OreType parseOreType(String tag) {
		if (tag.equals("null"))
			return null;
		OreType type = null;
		try {
			type = ModOreList.valueOf(tag.toUpperCase());
		}
		catch (IllegalArgumentException e) {

		}
		if (type == null) {
			try {
				type = ReikaOreHelper.valueOf(tag.toUpperCase());
			}
			catch (IllegalArgumentException e) {

			}
		}
		if (type == null)
			throw new IllegalArgumentException("Native ore type '"+tag+"' is invalid.");
		return type;
	}

	public List<CustomExtractEntry> getEntries() {
		return Collections.unmodifiableList(data);
	}

	public CustomExtractEntry getEntryFromOreBlock(ItemStack is) {
		for (CustomExtractEntry e : data) {
			if (ReikaItemHelper.collectionContainsItemStack(e.getAllOreBlocks(), is))
				return e;
		}
		return null;
	}

	public static enum ProductType {
		INGOT("Ingots like Iron and Copper"),
		DUST("Dusts like Redstone and Sulfur"),
		GEM("Gems like Diamonds and Amethyst"),
		ITEM("Anything else, like ThaumCraft shards");

		public final String displayName;
		private final String desc;

		private ProductType(String d) {
			displayName = ReikaStringParser.capFirstChar(this.name());
			desc = d;
		}
	}

}

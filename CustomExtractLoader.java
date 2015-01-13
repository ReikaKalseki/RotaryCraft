/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Interfaces.OreType;
import Reika.DragonAPI.Interfaces.OreType.OreRarity;

public class CustomExtractLoader {

	public static final CustomExtractLoader instance = new CustomExtractLoader();

	private final ArrayList<CustomExtractEntry> data = new ArrayList();

	public static class CustomExtractEntry implements OreType {

		public final String displayName;
		public final OreRarity rarity;
		private final ArrayList<ItemStack> oreItems = new ArrayList();
		private final ArrayList<String> oreNames = new ArrayList();
		public final int color1;
		public final int color2;
		public final String productName;

		private CustomExtractEntry(String name, OreRarity r, String prod, int c1, int c2, String... ores) {
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
		public boolean isNether() {
			return false;
		}

		@Override
		public boolean isEnd() {
			return false;
		}

		@Override
		public boolean canGenerateIn(Block b) {
			return false;
		}

		@Override
		public boolean existsInGame() {
			return true;
		}
	}

	public final String getSaveFileName() {
		return "RotaryCraft_CustomExtracts.cfg";
	}

	public final String getFullSavePath() {
		return RotaryCraft.config.getConfigFolder().getAbsolutePath()+"/"+this.getSaveFileName();
	}

	public void loadFile() {
		RotaryCraft.logger.log("Loading custom ore config.");
		File f = new File(this.getFullSavePath());
		if (!f.exists())
			if (!this.createOreFile(f))
				return;
		try {
			BufferedReader p = ReikaFileReader.getReader(f);
			String line = "";
			while (line != null) {
				line = p.readLine();
				if (line != null && !line.startsWith("//")) {
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
						RotaryCraft.logger.logError("Malformed custom extract entry: "+line);
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
			this.writeCommentLine(p, "Specify one per line, and format them as 'Name, Rarity, Product Name, Color 1, Color 2, OreDictionary Name(s)'");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Ore rarity is the rarity of the ore blocks in the world, and affects the multiplication rates.");
			this.writeCommentLine(p, "Valid Rarity Values:");
			for (OreRarity o : OreRarity.values()) {
				this.writeCommentLine(p, "\t"+o.name()+" - "+o.desc+", like "+o.examples+"");
			}
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Capitalization for the ore dictionary names matters, but is ignored for rarities.");
			this.writeCommentLine(p, "Ensure your OreDict names are correct; not all mods follow the 'oreName' and 'productName' convention.");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Colors must be hex codes; try to avoid conflicts with existing ores, including those natively handled by RC.");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Sample Lines:");
			this.writeCommentLine(p, "\tSample Ore 1, SCARCE, ingotSample, 0xffffff, 0x73cc12, oreSample");
			this.writeCommentLine(p, "\tSample Ore 2, Common, dustMetal, 0x77003b, 0xb1a700, oreNotSample, oreSecondName, oreHasLotsOfVariants");
			this.writeCommentLine(p, "\tSample Ore 3, EVerYwHEre, ImproperIngot, 0x1487a6, 0x27c61a, PoorlyNamedOre");
			this.writeCommentLine(p, "");
			this.writeCommentLine(p, "Entries missing names, rarities, products, or colors, or having less than one Ore Dictionary name, are incorrect.");
			this.writeCommentLine(p, "Incorrectly formatted lines will be ignored and will log an error in the console.");
			this.writeCommentLine(p, "Lines beginning with '//' are comments and will be ignored, as will empty lines.");
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

	private void writeCommentLine(PrintWriter p, String line) {
		p.append("// "+line+"\n");
	}

	private CustomExtractEntry parseString(String s) throws Exception {
		String[] parts = s.split(",");
		if (parts.length < 6)
			throw new IllegalArgumentException("Invalid parameter count.");
		String name = parts[0];
		if (name.isEmpty())
			throw new IllegalArgumentException("Empty name is invalid.");
		OreRarity rarity = OreRarity.valueOf(parts[1].toUpperCase());
		String prod = parts[2];
		int c1 = Integer.parseInt(parts[3], 16);
		int c2 = Integer.parseInt(parts[4], 16);
		String[] ores = new String[parts.length-5];
		System.arraycopy(parts, 5, ores, 0, ores.length);
		return new CustomExtractEntry(name, rarity, prod, c1, c2, ores);
	}

	public List<CustomExtractEntry> getEntries() {
		return Collections.unmodifiableList(data);
	}

}

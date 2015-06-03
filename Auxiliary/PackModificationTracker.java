package Reika.RotaryCraft.Auxiliary;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.RotaryCraft.RotaryCraft;

public class PackModificationTracker {

	public static final PackModificationTracker instance = new PackModificationTracker();

	private final ArrayList<PackModification> data = new ArrayList();

	private PackModificationTracker() {

	}

	public List<PackModification> getModifications() {
		return Collections.unmodifiableList(data);
	}

	public boolean modificationsExist() {
		return !data.isEmpty();
	}

	public void load() {
		RotaryCraft.logger.log("Loading pack modification log file.");
		try {
			File f = new File(this.getFullSavePath());
			if (!f.exists())
				this.createDataFile(f);

			BufferedReader p = ReikaFileReader.getReader(f);
			String line = "";
			while (line != null) {
				line = p.readLine();
				if (line != null && !line.isEmpty() && !line.startsWith("//")) {
					PackModification entry = this.parseString(line);
					if (entry != null) {
						data.add(entry);
					}
					else {
						throw new IllegalArgumentException("Invalid modification entry formatting: '"+line+"'");
					}
				}
			}
			p.close();
		}
		catch (Exception e) {
			throw new RuntimeException("Invalid pack modification file: "+e.getLocalizedMessage(), e);
		}
	}

	private final String getSaveFileName() {
		return "RotaryCraft_PackModifications.cfg";
	}

	private final String getFullSavePath() {
		return RotaryCraft.config.getConfigFolder().getAbsolutePath()+"/"+this.getSaveFileName();
	}

	private void createDataFile(File f) throws Exception {
		f.createNewFile();
		PrintWriter p = new PrintWriter(f);
		this.writeCommentLine(p, "-------------------------------");
		this.writeCommentLine(p, " RotaryCraft Pack Modification Log File ");
		this.writeCommentLine(p, "-------------------------------");
		this.writeCommentLine(p, "");
		this.writeCommentLine(p, "Use this file to specify any changes you are making to RotaryCraft for your modpack.");
		this.writeCommentLine(p, "Specify one per line, and format them in one of the following ways:");
		this.writeCommentLine(p, "Description OR Description:Reason");
		this.writeCommentLine(p, "");
		this.writeCommentLine(p, "Sample Lines:");
		this.writeCommentLine(p, "\tChanged compressor recipe to use GT steel");
		this.writeCommentLine(p, "\tReplaced gold ingot in ignition unit recipe with signalum:Small balance tweak");
		this.writeCommentLine(p, "");
		this.writeCommentLine(p, "Entries missing a description, or with more than one colon separator, are incorrect.");
		this.writeCommentLine(p, "Incorrectly formatted lines will throw an exception.");
		this.writeCommentLine(p, "Lines beginning with '//' are comments and will be ignored, as will empty lines.");
		this.writeCommentLine(p, "");
		this.writeCommentLine(p, "NOTE WELL: Any changes you make to the pack MUST be specified here to avoid confusing users.");
		this.writeCommentLine(p, "\tAny changes not explained here will be assumed to be intentionally hidden, and");
		this.writeCommentLine(p, "\tyou will lose permission to make the changes.");
		this.writeCommentLine(p, "====================================================================================");
		p.append("\n");
		p.close();
	}

	private static void writeCommentLine(PrintWriter p, String line) {
		p.append("// "+line+"\n");
	}

	private PackModification parseString(String s) throws Exception {
		s = s.replace("\n", "").replace("\r", "").replace("\t", "");
		String[] parts = s.split(":");
		int n = parts.length-1;
		switch(n) {
		case 0:
			return new PackModification(s);
		case 1:
			return new PackModification(parts[0], parts[1]);
		default:
			throw new IllegalArgumentException("Invalid line formatting: '"+s+"' has too many ("+n+") separators!");
		}
	}

	public static class PackModification {

		private final String desc;
		private final String reason;

		private PackModification(String s) {
			this(s, null);
		}

		private PackModification(String s, String r) {
			desc = s;
			reason = r;
		}

		@Override
		public String toString() {
			return EnumChatFormatting.RED+"# "+EnumChatFormatting.WHITE+(reason != null && !reason.isEmpty() ? desc+"; "+reason : desc);
		}

	}

}

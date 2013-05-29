/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.GUIs.GuiHandbook;

public final class HandbookAuxData {
    /** One GuiHandbook.SECOND in nanoGuiHandbook.SECONDs. */
    private static int tickcount;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static final String[] names = {

    	"Table of Contents", "", "", "", "", "", "", "",

    	"Basic Terms", "Relevant Physics", "", "", "", "", "", "",

    	"Important Notes", "Lubricant", "Lubricant Hose", "Canola", "Angular Transducer", "Screwdriver", "Pipe", "Fuel Line",

    	"Power Supply", "DC Engine", "Wind Turbine", "Steam Engine", "Gasoline Engine", "AC Engine", "Performance Engine",
    	"Hydrokinetic Engine",

    	"Microturbine", "Gas Turbine", "", "", "", "", "", "",

    	"Power Transfer", "Shaft", "Gearbox", "Bevel Gear", "Shaft Junction", "Clutch", "Dynamometer", "Flywheel",

    	"Worm Gear", "CVT", "Industrial Coil", "", "", "", "", "",

    	"Machines", "Bedrock Breaker", "Fermenter", "Grinder", "Floodlight", "Heat Ray", "Boring Machine", "Pile Driver",

    	"Aerosolizer", "Light Bridge", "Extractor", "Pulse Jet Furnace", "Pump", "Reservoir", "Fan", "Compactor",

    	"Auto Breeder", "Bait Box", "Firework Machine", "Fractionator", "Ground-Penetrating Radar", "Heater", "Obsidian Factory",
    	"Player Detector",

    	"Spawner Controller", "Sprinkler", "Item Vacuum", "Woodcutter", "Engine Controller", "Mob Radar", "Coil Winder", "TNT Cannon",

    	"Sonic Weapon", "Blast Furnace", "Force Field", "Music Box", "Mob Harvester", "Projector", "RailGun", "Silver Iodide Cannon",

    	"Item Refresher", "Cave Scanner", "Rotary Chest", "Flooder", "Smoke Detector", "Firestarter", "Freeze Gun", "Magnetizer",

    	"Containment Field", "CCTV Monitor", "CCTV Camera", "Steel Purifier", "", "", "", "",

    	"Tool Items", "Wind Spring", "Ultrasound", "Motion Tracker", "Vacuum", "Knockback Gun", "Gravel Gun", "Fireball Launcher",

    	"Handheld Crafting Unit", "Night Vision Goggles", "Buckets", "Bedrock Tools", "TNT Cannon Targeter", "", "", "",

    	"Basic Items", "Steel Ingot", "Base Panel", "Shaft", "Steel Gear", "Gear Unit", "Mount", "Scrap",

    	"Impeller", "Compressor", "Turbine", "Diffuser", "Combustor", "Radiator", "Condensor", "Gold Coil",

    	"Cylinder", "Paddle Panel", "Shaft Core", "Ignition Unit", "Propeller Blade", "Hub", "", "",

    	"", "Focusing Barrel", "Lens", "Power Module", "Laser Core", "Drill", "Pressure Head", "Flywheel Core",

    	"Radar", "Sonar", "Circuit Board", "Screen", "Mixer", "Saw", "Netherrack Dust and Tar", "Sawdust",

    	"Shaft Bearing", "Belt", "Ball Bearing", "Brake Disc", "Worm Gear", "Alternative Gears", "Alternative Shafts",
    	"Alternative Gearboxes",

    	"Linear Induction Motor", "Tension Coil", "", "", "", "", "", "",

    	"Resource Items", "Bedrock Dust", "Dust/Slurry/Solution", "Ore Flakes", "Anthracite/Prismane/Lonsdaleite", "Decorative Blocks",
    	"Blast Glass", "Monster Spawners",

    	"Yeast", "Ethanol", "Bedrock Ingot", "Silver Ingot", "Salt", "Ammonium Nitrate", "Silver Iodide", "Aluminum Powder",

    	"Railgun Ammunition", "Projector Slides", "", "", "", "", "", "",
	};

    public static void drawPage(FontRenderer f, byte screen, byte page, byte subpage, int dx, int dy) {
    	String[] page1 = {GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,
    	GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1};
    	String[] page2 = {GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,
    	GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2};
    	String[] i1o2 = {GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1,
    			GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons2};

    	if (screen == GuiHandbook.ENGINESTART && page == 1) {
    		int[] icons = {1, 1, 1, 1, 75, 2, 0, 75, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 192, 1, false, page1);
    	}
    	if (screen == GuiHandbook.ENGINESTART && page == 2) {
    		int[] icons = {38, 38, 38, 38, 39, 38, 38, 38, 38};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 38, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.ENGINESTART && page == 3) {
    		int[] icons = {1, 1, 1, 23, 16, 2, 0, 74, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 193, 1, false, page1);
    	}
    	if (screen == GuiHandbook.ENGINESTART && page == 4) {
    		int[] icons = {21, 74, 21, 31, 5, 2, 0, 16, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 194, 1, false, page1);
    	}
    	if (screen == GuiHandbook.ENGINESTART && page == 5) {
    		int[] icons = {74, 74, 74, 74, 24, 2, 0, 75, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 195, 1, false, page1);
    	}
    	if (screen == GuiHandbook.ENGINESTART && page == 6) {
    		int[] icons = {21, 22, 21, 31, 5, 2, 0, 16, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 196, 1, false, page1);
    	}
    	if (screen == GuiHandbook.ENGINESTART && page == 7) {
    		int[] icons = {29, 29, 29, 29, 30, 29, 29, 29, 29};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 37, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.ENGINESTART+1 && page == 0) {
    		int[] icons = {17, 1, 1, 20, 18, 2, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 197, 1, false, page1);
    	}
    	if (screen == GuiHandbook.ENGINESTART+1 && page == 1) {
    		int[] icons = {19, 20, 1, 1, 17, 1, 0, 18, 2};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 198, 1, false, page1);
    	}

    	if (screen == GuiHandbook.TRANSSTART && page == 1) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 5)
    				GuiHandbook.i = 0;
    		}
    		switch(GuiHandbook.i) {
    		case 0:
        		int[] icons5 = {-1, -1, -1, 67, 80, 67, 67, 67, 67};
        		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons5, 162+dx, 32+dy, 64, 1, false, i1o2);
        	break;
    		case 1:
        		int[] icons6 = {-1, -1, -1, 116, 61, 116, 116, 116, 116};
        		String[] tex = {GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons2,GuiHandbook.icons1,
        		    GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2};
        		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons6, 162+dx, 32+dy, 65, 1, false, tex);
        	break;
    		case 2:
        		int[] icons = {-1, -1, -1, -1, 2, -1, -1, 3, -1};
        		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 176, 1, false, page1);
        	break;
      		case 3:
        		int[] icons2 = {-1, -1, -1, -1, 62, -1, -1, 3, -1};
        		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons2, 162+dx, 32+dy, 66, 1, false, i1o2);
        	break;
      		case 4:
        		int[] icons3 = {-1, -1, -1, -1, 63, -1, -1, 3, -1};
        		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons3, 162+dx, 32+dy, 67, 1, false, i1o2);
        	break;
    		case 5:
        		int[] icons4 = {-1, 2, -1, 2, 2, 2, -1, 3, -1};
        		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons4, 162+dx, 32+dy, 164, 1, false, page1);
        	break;
    		}
    	}
    	if (screen == GuiHandbook.TRANSSTART && page == 2) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 3)
    				GuiHandbook.i = 0;
    		}
    		int[] icons = {-1, -1, -1, -1, GuiHandbook.i+5, -1, -1, 3, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 160+GuiHandbook.i, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART && page == 3) {
    		int[] icons = {1, 2, 0, 2, 5, 0, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 178, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART && page == 4) {
    		int[] icons = {1, 2, 0, 2, 5, 0, 1, 2, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 183, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART && page == 5) {
    		int[] icons = {-1, 2, -1, -1, 3, -1, -1, 75, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 177, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART && page == 6) {
    		int[] icons = {-1, 2, -1, -1, 81, 53, -1, 3, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 191, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART && page == 7) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 3)
    				GuiHandbook.i = 0;
    		}
    		int[] icons = {-1, -1, -1, -1, 11+GuiHandbook.i, -1, -1, 3, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 179+GuiHandbook.i, 1, false, page1);		//change output icon
    	}

    	if (screen == GuiHandbook.TRANSSTART+1 && page == 0) {
    		int[] icons = {2, 15, -1, -1, 4, 2, -1, 3, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 165, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART+1 && page == 1) {
    		int[] icons = {56, 2, 56, 56, 2, 56, -1, 3, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 166, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TRANSSTART+1 && page == 2) {
    		int[] icons = {-1, -1, -1, 59, 60, 2, -1, 3, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 35, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.MACHINESTART && page == 1) {
    		int[] icons = {0, 77, 68, 0, 1, 68, 0, 77, 68};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 199, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART && page == 2) {
    		int[] icons = {1, 0, 1, 0, 16, 0, 1, 0, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 200, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART && page == 3) {
    		int[] icons = {1, -1, 1, 55, 5, 55, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 201, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART && page == 4) {
    		int[] icons = {73, 1, 68, 70, 71, 74, 73, 0, 68};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 202, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART && page == 5) {
    		int[] icons = {68, 68, 68, 35, 33, 32, 0, 34, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 203, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART && page == 6) {
    		int[] icons = {1, 1, 1, 48, 5, 52, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 204, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART && page == 7) {
    		int[] icons = {0, 7, 0, 2, 13, 2, 0, 48, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 186, 1, false, page1);
    	}

    	if (screen == GuiHandbook.MACHINESTART+1 && page == 0) {
    		int[] icons = {0, 219, 0, 219, 16, 219, 0, 219, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 206, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+1 && page == 1) {
    		int[] icons = {74, 70, 74, 0, 70, 1, 0, 0, 77};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 207, 1, false, page1);
    	}

    	if (screen == GuiHandbook.MACHINESTART+1 && page == 2) {
    		int[] icons = {1, 67, 1, 69, 16, 1, 0, 2, 105};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 216, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+1 && page == 3) {
    		int[] icons = {68, 17, 19, 100, 20, 68, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 217, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+1 && page == 4) {
    		int[] icons = {1, 84, 1, 100, 16, 100, 0, 100, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 218, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+1 && page == 5) {
    		int[] icons = {0, -1, 0, 0, -1, 0, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 219, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+1 && page == 6) {
    		int[] icons = {1, 1, 1, 1, 16, 1, 0, 5, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 220, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+1 && page == 7) {
    		int[] icons = {1, 49, 1, 49, 8, 49, 0, 49, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 221, 1, false, page1);
    	}

    	if (screen == GuiHandbook.MACHINESTART+2 && page == 0) {
    		int[] icons = {-1, -1, -1, 0, -1, 0, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 205, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 1) {
    		int[] icons = {65, 65, 65, 65, 205, 65, 65, 65, 65};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 222, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 2) {
    		int[] icons = {0, 102, 0, 0, 107, 0, 0, 75, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 174, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 3) {
    		int[] icons = {74, 101, 74, 74, 54, 74, 74, 0, 74};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 184, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 4) {
    		int[] icons = {1, 53, 1, 0, 52, 0, 1, 50, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 190, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 5) {
    		int[] icons = {1, 65, 1, 0, 22, 0, 1, 20, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 173, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 6) {
    		int[] icons = {1, 84, 1, 100, 54, 100, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 185, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+2 && page == 7) {
    		int[] icons = {76, 50, 76, 68, 74, 68, 68, 0, 68};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 175, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 0) {
    		int[] icons = {0, 52, 0, 68, 74, 68, 71, -1, 71};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 223, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 1) {
    		int[] icons = {-1, 1, -1, -1, 100, -1, -1, 16, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 188, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 2) {
    		int[] icons = {1, 16, 1, 16, 106, 16, 1, 16, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 187, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 3) {
    		int[] icons = {1, 55, -1, 0, 5, 55, 0, 0, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 189, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 4) {
    		int[] icons = {1, 34, 1, 1, 52, 1, 1, 75, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 172, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 5) {
    		int[] icons = {-1, 50, 53, -1, 5, -1, 0, 52, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 32, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 6) {
    		int[] icons = {-1, 1, 1, -1, 2, 5, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 33, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.MACHINESTART+3 && page == 7) {
    		int[] icons = {1, -1, 53, 0, 52, 0, -1, 0, 106};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 34, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.MACHINESTART+4 && page == 0) {
    		int[] icons = {0, 51, 0, 51, 18, 51, 0, 51, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 171, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 1) {
    		int[] icons = {126, 126, 126, 126, 75, 126, 126, 126, 126};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 170, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 2) {
    		int[] icons = {76, 104, 76, 77, 77, 77, 0, 74, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 169, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 3) {
    		int[] icons = {1, 109, 1, 109, 52, 109, 1, 109, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 168, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 4) {
    		int[] icons = {-1, -1, -1, 0, 173, 0, 0, 81, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 167, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 5) {
    		int[] icons = {1, 1, 1, 70, 52, 71, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 159, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 6) {
    		int[] icons = {37, 37, 1, 37, 37, 5, 1, 5, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 158, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+4 && page == 7) {
    		int[] icons = {1, -1, 1, 1, 125, 1, 0, 52, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 157, 1, false, page1);
    	}

    	if (screen == GuiHandbook.MACHINESTART+5 && page == 0) {
    		int[] icons = {1, 124, 1, 124, 102, 124, 1, 124, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 156, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 1) {
    		int[] icons = {1, 0, 1, 0, 52, 0, 1, 51, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 155, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 2) {
    		int[] icons = {1, 1, 1, 1, 106, 1, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 154, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 3) {
    		int[] icons = {-1, 100, -1, 1, -1, 1, -1, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 112, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 4) {
    		int[] icons = {-1, 0, -1, -1, 69, -1, -1, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 153, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 5) {
    		int[] icons = {68, 74, 68, 74, 20, 74, 68, 34, 68};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 36, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 6) {
    		int[] icons = {-1, 1, 1, 118, 118, 5, 1, 5, 0};
        	String[] tex = {GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons2,GuiHandbook.icons2,
        	    	GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons2};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 39, 1, false, tex);
    	}
    	if (screen == GuiHandbook.MACHINESTART+5 && page == 7) {
    		int[] icons = {0, -1, 0, 24, 3, 24, 0, 75, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 40, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.MACHINESTART+6 && page == 0) {
    		int[] icons = {117, 104, 117, 77, 77, 77, 0, 74, 0};
        	String[] tex = {GuiHandbook.icons2,GuiHandbook.icons1,GuiHandbook.icons2,GuiHandbook.icons1,GuiHandbook.icons1,
        	    	GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons1,GuiHandbook.icons2};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 42, 1, false, tex);
    	}
    	if (screen == GuiHandbook.MACHINESTART+6 && page == 1) {
    		int[] icons = {1, 1, 1, 53, 52, 1, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 43, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.MACHINESTART+6 && page == 2) {
    		int[] icons = {-1, 74, -1, 84, 75, 1, -1, 0, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 44, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.MACHINESTART+6 && page == 3) {
    		int[] icons = {1, 65, 1, 0, 75, 0, 1, 0, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 45, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.TOOLSTART && page == 1) {
    		int[] icons = {-1, 1, -1, 1, -1, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 48, 4, false, i1o2);
    	}
    	if (screen == GuiHandbook.TOOLSTART && page == 2) {
    		int[] icons = {-1, 51, -1, 1, 53, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 49, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.TOOLSTART && page == 3) {
    		int[] icons = {-1, 51, 50, 1, 53, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 50, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.TOOLSTART && page == 4) {
    		int[] icons = {-1, 19, -1, 1, 16, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 51, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.TOOLSTART && page == 5) {
    		int[] icons = {-1, 19, -1, 1, 51, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 52, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.TOOLSTART && page == 6) {
    		int[] icons = {-1, 107, -1, 4, 106, 4, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 53, 1, false, i1o2);
    	}
    	if (screen == GuiHandbook.TOOLSTART && page == 7) {
    		int[] icons = {103, -1, 103, 1, 20, 1, 1, 75, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 54, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.TOOLSTART+1 && page == 0) {
    		int[] icons = {-1, 74, -1, 1, 114, 1, -1, 74, -1};
    		String[] tex = {GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons2,
    				GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons2};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 55, 1, false, tex);
    	}
    	if (screen == GuiHandbook.TOOLSTART+1 && page == 1) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 1)
    				GuiHandbook.i = 0;
    		}
    		if (GuiHandbook.i == 0) {
	    		int[] icons = {-1, -1, -1, 1, 53, 1, 102, 1, 102};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 56, 1, false, i1o2);
    		}
    		if (GuiHandbook.i == 1) {
	    		int[] icons = {41, 56, -1, -1, -1, -1, -1, -1, -1};
	    		String[] tex = {GuiHandbook.icons2, GuiHandbook.icons2, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1,
	    				GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons1, GuiHandbook.icons2};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 57, 1, true, tex);
    		}
    	}
    	if (screen == GuiHandbook.TOOLSTART+1 && page == 3) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 2)
    				GuiHandbook.i = 0;
    		}
    		int a = -1; int b = -1; int c = -1;
    		switch(GuiHandbook.i) {
    		case 0:
    			a = 113;
    			c = 113;
    		break;
    		case 1:
    			a = 113;
    			b = 113;
    		break;
    		}
    		int[] icons = {a, 113, b, c, 80, -1, -1, 80, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 114+GuiHandbook.i, 1, false, page1);
    	}
    	if (screen == GuiHandbook.TOOLSTART+1 && page == 4) {
    		int[] icons = {-1, 81, -1, 1, 75, 1, 1, 76, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 119, 1, false, page1);
    	}

    	if (screen == GuiHandbook.CRAFTSTART && page == 2) {
    		int[] icons = {-1, -1, -1, -1, -1, -1, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 0, 3, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART && page == 3) {
    		int[] icons = {1, -1, -1, -1, 1, -1, -1, -1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 2, 3, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART && page == 4) {
    		int[] icons = {-1, 1, -1, 1, 1, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 4, 3, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART && page == 5) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 3)
    				GuiHandbook.i = 0;
    		}
    		int[] icons = {-1, -1, -1, -1, 4+GuiHandbook.i, 2, 2, 4+GuiHandbook.i, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 5+GuiHandbook.i, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART && page == 6) {
    		int[] icons = {-1, -1, -1, 1, -1, 1, 1, 0, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 3, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART && page == 7) {
    		int product = 10;
    		int amount = 1;
    		int ing = 0;
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 9)
    				GuiHandbook.i = 0;
    		}
    		if (GuiHandbook.i > 1)
    			product = 9;
    		switch(GuiHandbook.i) {
    		case 0:
    			amount = 3;
    			ing = 65;
    		break;
    		case 1:
    			amount = 3;
    			ing = 66;
    		break;
    		case 2:
    			amount = 9;
    			ing = 2;
    		break;
    		case 3:
    			amount = 9;
    			ing = 0;
    		break;
    		case 4:
    			amount = 45;
    			ing = 3;
    		break;
    		case 5:
    			amount = 15;
    			ing = 4;
    		break;
    		case 6:
    			amount = 48;
    			ing = 5;
    		break;
    		case 7:
    			amount = 114;
    			ing = 6;
    		break;
    		case 8:
    			amount = 246;
    			ing = 7;
    		break;
    		case 9:
    			amount = 510;
    			ing = 8;
    		break;
    		}

    		int[] icons = {ing, -1, -1, -1, -1, -1, -1, -1, -1};
    		if (GuiHandbook.i <= 1) {
    			for (int i = 1; i < 8; i++)
    				icons[i] = ing;
    		}
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, product, amount, true, page1);
    	}

    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 0) {
    		int[] icons = {-1, 1, -1, 1, 4, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 16, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 1) {
    		int[] icons = {1, 1, 1, 1, 4, 1, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 17, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 2) {
    		int[] icons = {17, 17, 17, 17, 4, 17, 17, 17, 17};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 18, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 3) {
    		int[] icons = {-1, 1, 1, 1, -1, -1, -1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 19, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 4) {
    		int[] icons = {1, 1, 1, 1, 75, 1, 1, 31, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 20, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 5) {
    		int[] icons = {1, 1, 1, 100, 100, 100, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 22, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 6) {
    		int[] icons = {1, 100, 1, 100, 1, 100, 1, 100, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 23, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+1 && page == 7) {
    		int[] icons = {74, 74, 74, 74, 1, 74, 74, 74, 74};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 24, 1, false, page1);
    	}

    	if (screen == GuiHandbook.CRAFTSTART+2 && page == 0) {
    		int[] icons = {1, 1, 1, 1, -1, 1, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 21, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+2 && page == 1) {
    		int[] icons = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 29, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+2 && page == 2) {
    		int[] icons = {-1, -1, 2, -1, 1, -1, 2, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 30, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+2 && page == 3) {
    		int[] icons = {74, -1, 74, 1, 75, 1, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 31, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+2 && page == 4) {
    		int[] icons = {-1, 2, -1, -1, 1, -1, -1, 0, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 38, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+2 && page == 5) {
    		int[] icons = {-1, -1, 56, -1, 30, -1, 4, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 39, 1, false, page1);
    	}

    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 1) {
    		int[] icons = {68, 68, 68, 71, 71, 70, 68, 68, 68};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 32, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 3) {
    		int[] icons = {75, 102, 75, 74, 74, 74, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 34, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 2) {
    		int[] icons = {-1, 77, -1, 77, 70, 77, -1, 77, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 33, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 4) {
    		int[] icons = {71, 71, 71, 103, 104, 103, 103, 75, 103};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 35, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 5) {
    		int[] icons = {1, 1, 1, 1, 1, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 48, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 6) {
    		int[] icons = {1, 68, 77, 68, 77, 85, 77, 85, 85};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 49, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+3 && page == 7) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 3)
    				GuiHandbook.i = 0;
    		}
    		int item;
    		switch(GuiHandbook.i) {
    		case 0: item = 67; break;
    		case 1: item = 69; break;
    		case 2: item = 73; break;
    		case 3: item = 74; break;
    		default: item = 0; break;
    		}
    		int[] icons = {item, item, item, item, 1, item, item, item, item};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, GuiHandbook.i+11, 1, false, page1);
    	}

    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 0) {
    		int[] icons = {1, 1, 1, -1, 74, -1, 75, 192, 75};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 50, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 1) {
    		int[] icons = {-1, 1, -1, 1, 109, 1, 75, 52, 75};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 51, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 2) {
    		int[] icons = {0, 74, 0, 75, 81, 75, 74, 0, 74};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 52, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 3) {
    		int[] icons = {-1, -1, -1, 1, 84, 1, 1, 52, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 53, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 4) {
    		int[] icons = {-1, 1, -1, 1, 16, 1, -1, 1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 54, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 5) {
    		int[] icons = {1, -1, 1, -1, 17, -1, 1, -1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 55, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 6) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 1)
    				GuiHandbook.i = 0;
    		}
    		ReikaGuiAPI.instance.drawSmelting(f, 87+dx, 36+dy, 105+GuiHandbook.i*3, 141+dx, 32+dy, 128+GuiHandbook.i, 1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+4 && page == 7) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 1)
    				GuiHandbook.i = 0;
    		}
    		int[] icons = {131, 131, -1, 131, 131, -1, -1, -1, -1};
    		int[] icons2 = {-1, 110, -1, 131, 131, 131, -1+GuiHandbook.i*70, -1+GuiHandbook.i*70, -1+GuiHandbook.i*70};
    		if (GuiHandbook.i == 0)
    			ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 67, 1, false, page1); //change output icon
    		else
    			ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons2, 162+dx, 32+dy, 111, 1, false, page1); //change output icon
    	}

    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 0) {
    		int[] icons = {58, 58, 58, 58, 1, 58, 58, 58, 58};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 56, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 1) {
    		int[] icons = {127, 127, 127, 127, 1, 127, 127, 127, 127};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 57, 2, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 2) {
    		int[] icons = {1, -1, -1, -1, -1, -1, -1, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 58, 4, true, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 3) {
    		int[] icons = {-1, 5, -1, 2, 56, 2, -1, 4, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 59, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 4) {
    		int[] icons = {2, -1, -1, -1, 4, -1, -1, -1, 2};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 15, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 5) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 3)
    				GuiHandbook.i = 0;
    		}
    		switch(GuiHandbook.i) {
	    	case 0:
	    		int[] icons = {-1, 67, -1, 67, 67, 67, -1, 67, -1};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 25, 1, false, page1);
    		break;
	    	case 1:
	    		int[] icons2 = {-1, 69, -1, 69, 69, 69, -1, 69, -1};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons2, 162+dx, 32+dy, 26, 2, false, page1);
    		break;
	    	case 2:
	    		int[] icons3 = {-1, 77, -1, 77, 77, 77, -1, 77, -1};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons3, 162+dx, 32+dy, 27, 8, false, page1);
    		break;
	    	case 3:
	    		int[] icons4 = {85, 1, 85, 1, 1, 1, 85, 1, 85};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons4, 162+dx, 32+dy, 28, 8, false, page1);
    		break;
    		}
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 6) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 2)
    				GuiHandbook.i = 0;
    		}
    		switch(GuiHandbook.i) {
	    	case 0:
	    		int[] icons = {69, -1, -1, -1, 69, -1, -1, -1, 69};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 29+32, 2, false, page1);
    		break;
	    	case 1:
	    		int[] icons2 = {77, -1, -1, -1, 77, -1, -1, -1, 77};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons2, 162+dx, 32+dy, 30+32, 8, false, page1);
    		break;
	    	case 2:
	    		int[] icons3 = {-1, 85, -1, 85, 2, 85, -1, 85, -1};
	    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons3, 162+dx, 32+dy, 31+32, 4, false, page1);
    		break;
    		}
    	}
    	if (screen == GuiHandbook.CRAFTSTART+5 && page == 7) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 15)
    				GuiHandbook.i = 0;
    		}
    		int shaft = 0;
    		int gear = 0;
    		String[] tex = i1o2;
    		switch(GuiHandbook.i/4) {
    		case 0:
    			shaft = 80;
    			gear = 25;
    		break;
    		case 1:
    			shaft = 29+32;
    			gear = 26;
    		break;
    		case 2:
    			shaft = 30+32;
    			gear = 27;
    		break;
    		case 3:
    			shaft = 31+32;
    			gear = 28;
    		break;
    		}
    		if (GuiHandbook.i%4 != 0) {
    			tex[4] = GuiHandbook.icons2;
    			tex[7] = GuiHandbook.icons2;
    			gear = 15+GuiHandbook.i%4+GuiHandbook.i/4*4;
    		}
    		int[] icons = {-1, -1, -1, -1, gear, shaft, shaft, gear, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 16+GuiHandbook.i, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.CRAFTSTART+6 && page == 0) {
    		int[] icons = {-1, -1, -1, 24, 75, 24, 1, 1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 37, 1, false, page1);
    	}
    	if (screen == GuiHandbook.CRAFTSTART+6 && page == 1) {
    		int[] icons = {48, 48, 48, 48, 2, 48, 48, 48, 48};
    		String[] tex = {GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons1,
    			GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2,GuiHandbook.icons2, GuiHandbook.icons1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 60, 1, false, tex);
    	}

    	if (screen == GuiHandbook.RESOURCESTART && page == 3) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 7)
    				GuiHandbook.i = 0;
    		}
    		ReikaGuiAPI.instance.drawSmelting(f, 87+dx, 36+dy, 88+GuiHandbook.i, 141+dx, 32+dy, 72+GuiHandbook.i, 1);
    	}

    	if (screen == GuiHandbook.RESOURCESTART && page == 5) {
    		int item;
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 2)
    				GuiHandbook.i = 0;
    		}
    		switch(GuiHandbook.i) {
    		case 0: item = 1; break;
    		case 1: item = 96; break;
    		case 2: item = 98; break;
    		default: item = -1; break;
    		}
    		int[] icons = {item, item, item, item, item, item, item, item, item};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, GuiHandbook.i+150, 1, false, page1); //change output icon
    	}
    	if (screen == GuiHandbook.RESOURCESTART && page == 2) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 7)
    				GuiHandbook.i = 0;
    		}
    		int item = 240+GuiHandbook.i;
    		int item2 = 240+8+GuiHandbook.i;
    		int[] icons = {item-3*8-8, item-2*8, item-1*8, item};
    		int[] icons2 = {item2-3*8, item2-2*8, item2-1*8, item2};
    		ReikaGuiAPI.instance.drawExtractor(f, 66+dx, 17+dy, icons, 66+dx, 59+dy, icons2, 1); //change output icon
    	}
    	if (screen == GuiHandbook.RESOURCESTART && page == 6) {
    		ReikaGuiAPI.instance.drawSmelting(f, 87+dx, 36+dy, 68, 141+dx, 32+dy, 64, 1);	//change input and output icon
    	}
    	if (screen == GuiHandbook.RESOURCESTART && page == 4) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 3)
    				GuiHandbook.i = 0;
    		}
    		int j = GuiHandbook.i;
    		int k = GuiHandbook.i;
    		if (GuiHandbook.i == 0) {
    			k = -23;
    		}
    		if (GuiHandbook.i == 3) {
    			j = -19;
    		}
    		ReikaGuiAPI.instance.drawCompressor(f, 66+dx, 14+dy, k+95, 120+dx, 41+dy, j+96, 1); //change output icon
    	}

    	if (screen == GuiHandbook.RESOURCESTART+1 && page == 0) {
    		mc.renderEngine.bindTexture(GuiHandbook.icons2);
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 15)
    				GuiHandbook.i = 0;
    		}
    		if (GuiHandbook.i == 0) {
    			int[] icons = {113, 110, 112-2*GuiHandbook.i};
    			ReikaGuiAPI.instance.drawFermenter(f, 102+dx, 18+dy, icons, 159+dx, 32+dy, 133-GuiHandbook.i*3, 1);
    		}
    		else {
	    		int[] icons = {133, GuiHandbook.i-1, 110};
	    		ReikaGuiAPI.instance.drawFermenter(f, 102+dx, 18+dy, icons, 159+dx, 32+dy, 130, 1+GuiHandbook.i/9);
    		}
    	}
    	if (screen == GuiHandbook.RESOURCESTART+1 && page == 1) {
    		mc.renderEngine.bindTexture(GuiHandbook.icons2);
    		ReikaGuiAPI.instance.drawSmelting(f, 87+dx, 36+dy, 130, 141+dx, 32+dy, 134, 1);
    	}
    	if (screen == GuiHandbook.RESOURCESTART+1 && page == 2) {
    		int[] icons = {-1, 132, -1, 132, 1, 132, -1, 132, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 113, 1, false, page1);
    	}
    	if (screen == GuiHandbook.RESOURCESTART+1 && page == 3) {
    		ReikaGuiAPI.instance.drawSmelting(f, 87+dx, 36+dy, 118, 141+dx, 32+dy, 117, 1);
    	}
    	if (screen == GuiHandbook.RESOURCESTART+1 && page == 4) {
    		int[] icons = {110, -1, -1, -1, -1, -1, -1, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 133, 1, true, page1);
    	}
    	if (screen == GuiHandbook.RESOURCESTART+1 && page == 6) {
    		int[] icons = {133, 117, -1, -1, -1, -1, -1, -1, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 135, 1, true, page1);
    	}

    	if (screen == GuiHandbook.RESOURCESTART+2 && page == 0) {
    		int[] a = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    		int amt = 1;
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 15)
    				GuiHandbook.i = 0;
    		}
    		int ind = -1;
    		a[4] = 36;
    		switch(GuiHandbook.i) {
    		case 0:
    			a[0] = 1;
    			a[1] = 1;
    			a[3] = 1;
    			a[4] = -1;
    			amt = 3;
    		break;
    		case 1:
    		case 2:
    		case 3:
    			ind = 67;
    		break;
    		case 4:
    		case 5:
    		case 6:
    			ind = 69;
    		break;
    		case 7:
    		case 8:
    		case 9:
    			ind = 73;
    		break;
    		case 10:
    		case 11:
    		case 12:
    			ind = 74;
    		break;
    		case 13:
    		case 14:
    		case 15:
    			ind = 113;
    		break;
    		}
    		if (GuiHandbook.i != 0) {
				a[0] = ind;
				a[8] = ind;
	    		if ((GuiHandbook.i+1)%3 == 0 || GuiHandbook.i == 15) {
	    			a[2] = ind;
	    			a[6] = ind;
	    		}
	    		if ((GuiHandbook.i+0)%3 == 0 || GuiHandbook.i == 15) {
	    			a[2] = ind;
	    			a[6] = ind;
	    			a[1] = ind;
	    			a[3] = ind;
	    			a[5] = ind;
	    			a[7] = ind;
	    		}
    		}
    		int[] icons = {a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8]};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 36, amt, false, page1);
    	}
    	if (screen == GuiHandbook.RESOURCESTART+2 && page == 1) {
    		int[] icons = {111, 111, 111, 111, 84, 111, 111, 111, 111};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 61, 1, false, i1o2);
    	}

    	if (screen == GuiHandbook.MISCSTART && page == 2) {
    		int[] icons = {67, -1, 67, 67, -1, 67, 67, -1, 67};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 99, 16, false, page1);
    	}
    	if (screen == GuiHandbook.MISCSTART && page == 6) {
    		int[] icons = {1, -1, 1, 1, -1, 1, 1, -1, 1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 100, 16, false, page1);
    	}
    	if (screen == GuiHandbook.MISCSTART && page == 7) {
    		int[] icons = {68, -1, 68, 68, -1, 68, 68, -1, 68};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 101, 16, false, page1);
    	}
    	if (screen == GuiHandbook.MISCSTART && page == 4) {
    		int[] icons = {67, 67, 67, 67, 81, 67, -1, 80, -1};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 83, 1, false, page1);
    	}
    	if (screen == GuiHandbook.MISCSTART && page == 5) {
    		if (System.nanoTime()-GuiHandbook.time > GuiHandbook.SECOND) {
    			GuiHandbook.i++;
    			GuiHandbook.time = System.nanoTime();
    			if (GuiHandbook.i > 1)
    				GuiHandbook.i = 0;
    		}
    		int[] icons = {73-72*GuiHandbook.i, -1, -1, -1, 80, -1, -1, -1, 67};
    		ReikaGuiAPI.instance.drawRecipe(f, 72+dx, 18+dy, icons, 162+dx, 32+dy, 82, 1, false, page1);
    	}
    }
}

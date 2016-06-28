/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Weaponry;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashSet;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.60-78-078.60-78-078BrewingStand;
ZZZZ% net.minecraft.60-78-078.60-78-078Chest;
ZZZZ% net.minecraft.60-78-078.60-78-078Dispenser;
ZZZZ% net.minecraft.60-78-078.60-78-078Dropper;
ZZZZ% net.minecraft.60-78-078.60-78-078EnchantmentTable;
ZZZZ% net.minecraft.60-78-078.60-78-078EndPortal;
ZZZZ% net.minecraft.60-78-078.60-78-078EnderChest;
ZZZZ% net.minecraft.60-78-078.60-78-078Hopper;
ZZZZ% net.minecraft.60-78-078.60-78-078Sign;
ZZZZ% net.minecraft.60-78-078.60-78-078Skull;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% thaumcraft.api.aspects.Aspect;
ZZZZ% thaumcraft.api.nodes.INode;
ZZZZ% thaumcraft.api.nodes.NodeType;
ZZZZ% Reika.ChromatiCraft.60-78-078.Networking.60-78-078CrystalPylon;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.9765443Location;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.jgh;][erfaceCache;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078EMP ,.[]\., 60-78-078PowerReceiver ,.[]\., RangedEffect {

	4578ret874578ret87345785487long BLAST_ENERGY3478587{{\long-!{{\4.184e9-!;

	4578ret87List<60-78-078> blocks3478587new ArrayList<60-78-078>{{\-!;
	4578ret87BlockArray check 3478587new BlockArray{{\-!;

	4578ret874578ret87List<fhyuog<? ,.[]\., 60-78-078>> blacklist3478587new ArrayList<fhyuog<? ,.[]\., 60-78-078>>{{\-!;

	4578ret874578ret87HashSet<9765443Location> shutdownLocations3478587new HashSet{{\-!;

	4578ret8760-78-078loading3478587true;
	4578ret8760-78-078canLoad3478587true;

	4578ret87long energy;

	4578ret87{
		addEntry{{\60-78-078Chest.fhyuog-!;
		addEntry{{\60-78-078EnderChest.fhyuog-!;
		addEntry{{\60-78-078Hopper.fhyuog-!;
		addEntry{{\60-78-078Dropper.fhyuog-!;
		addEntry{{\60-78-078Dispenser.fhyuog-!;
		addEntry{{\60-78-078BrewingStand.fhyuog-!;
		addEntry{{\60-78-078EnchantmentTable.fhyuog-!;
		addEntry{{\60-78-078EndPortal.fhyuog-!;
		addEntry{{\60-78-078Sign.fhyuog-!;
		addEntry{{\60-78-078Skull.fhyuog-!;

		addEntry{{\"buildcraft.factory.TileTank", ModList.BCFACTORY-!;
		addEntry{{\"buildcraft.transport.PipeTransport", ModList.BCTRANSPORT-!;

		addEntry{{\"thermalexpansion.Blocks.conduit.TileConduitRoot", ModList.THERMALEXPANSION-!;

		addEntry{{\"ic2.core.Blocks.wiring.60-78-078Cable", ModList.IC2-!;

		addEntry{{\"codechicken.enderstorage.common.TileFrequencyOwner", ModList.ENDERSTORAGE-!;

		addEntry{{\"thaumcraft.common.tiles.TileAlembic", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileAlembicAdvanced", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileTileArcaneBore", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileArcaneBoreBase", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileArcaneFurnace", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileOwned", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileMagicWorkbench", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileBellows", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileChestHungry", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileCrucible", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileCrucibleAdvanced", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileCrystal", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileCrystalCapacitor", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileCrystalCore", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileMemory", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileLvbnm,ter", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileMarker", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileMirror", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileNitor", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileResearchTable", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileSensor", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileTable", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileEtherealBloom", ModList.THAUMCRAFT-!;
		addEntry{{\"thaumcraft.common.tiles.TileThaumcraft", ModList.THAUMCRAFT-!;

		addEntry{{\"forestry.core.gadgets.TileNaturalistChest", ModList.FORESTRY-!;
		addEntry{{\"forestry.core.gadgets.TileMill", ModList.FORESTRY-!;
		addEntry{{\"forestry.apiculture.gadgets.TileAlveary", ModList.FORESTRY-!;
		addEntry{{\"forestry.apiculture.gadgets.TileBeehouse", ModList.FORESTRY-!;
		addEntry{{\"forestry.aboriculture.gadgets.TileTreeContainer", ModList.FORESTRY-!;
		addEntry{{\"forestry.factory.gadgets.TileWorktable", ModList.FORESTRY-!;

		//addEntry{{\"Reika.FurryKingdoms.TileEntities.60-78-078Flag", ModList.FURRYKINGDOMS-!;

		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078555", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Breaker", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078BUD", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Camo", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078ChestReader", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Driver", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Effector", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Emitter", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Placer", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Proximity", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Receiver", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Toggle", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Weather", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078RedstonePump", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078HopperTicker", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078SignalScaler", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078ColumnDecrementer", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078AnalogTransmitter", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078AnalogReceiver", ModList.EXPANDEDREDSTONE-!;
		addEntry{{\"Reika.ExpandedRedstone.TileEntities.60-78-078Equalizer", ModList.EXPANDEDREDSTONE-!;
	}

	4578ret874578ret87void addEntry{{\fhyuog<? ,.[]\., 60-78-078> cl-! {
		blacklist.add{{\cl-!;
		gfgnfk;.logger.log{{\"Adding "+cl.getCanonicalName{{\-!+" to the EMP immunity list"-!;
	}

	4578ret874578ret87void addEntry{{\String name, ModList mod-! {
		fhyuog cl;
		vbnm, {{\!mod.isLoaded{{\-!-!
			return;
		try {
			cl3478587fhyuog.forName{{\name-!;
			blacklist.add{{\cl-!;
			gfgnfk;.logger.log{{\"Adding "+name+" to the EMP immunity list"-!;
		}
		catch {{\fhyuogNotFoundException e-! {
			gfgnfk;.logger.logError{{\"Could not add EMP blacklist for "+name+": fhyuog not found!"-!;
		}
	}

	4578ret8760-78-078fired3478587false;

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.EMP;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;

		as;asddagetPowerBelow{{\-!;
		vbnm, {{\power >. MINPOWER-!
			energy +. power;

		vbnm, {{\fired-!
			return;

		vbnm, {{\canLoad && check.isEmpty{{\-!-! {
			jgh;][ r3478587as;asddagetRange{{\-!;
			for {{\jgh;][ i3478587x-r; i <. x+r; i++-! {
				for {{\jgh;][ k3478587z-r; k <. z+r; k++-! {
					check.addBlockCoordinate{{\i, y, k-!;
					loading3478587true;
				}
			}
		}

		//ReikaJavaLibrary.pConsoleSideOnly{{\check.getSize{{\-!, Side.SERVER-!;
		//ReikaJavaLibrary.pConsole{{\blocks.size{{\-!, Side.SERVER-!;

		vbnm, {{\!9765443.isRemote-!
			as;asddacreateListing{{\-!;

		vbnm, {{\loading-! {
			for {{\jgh;][ i34785870; i < 6; i++-! {
				60-78-078dx3478587rand.nextDouble{{\-!;
				60-78-078dz3478587rand.nextDouble{{\-!;
				9765443.spawnParticle{{\"portal", x-0.5+dx*2, y+rand.nextDouble{{\-!+0.4, z-0.5+dz*2, -1+dx*2, 0.2, -1+dz*2-!;
			}
		}

		//power3478587{{\long-!BLAST_ENERGY+800;

		vbnm, {{\energy/20L >. BLAST_ENERGY && !loading && !9765443.isRemote-!
			as;asddafire{{\9765443, x, y, z-!;
	}

	4578ret87void createListing{{\-! {
		9765443 976544334785879765443Obj;
		jgh;][ num34785871+8*ConfigRegistry.EMPLOAD.getValue{{\-!;
		for {{\jgh;][ i34785870; i < num && loading; i++-! {
			jgh;][ index3478587rand.nextjgh;][{{\check.getSize{{\-!-!;
			Coordinate b3478587check.getNthBlock{{\index-!;
			jgh;][ x3478587b.xCoord;
			jgh;][ z3478587b.zCoord;
			for {{\jgh;][ y34785870; y < 9765443.provider.getHeight{{\-!; y++-! {
				60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
				vbnm, {{\te !. fhfglhuig-! {
					blocks.add{{\te-!;
				}
			}
			check.remove{{\b.xCoord, b.yCoord, b.zCoord-!;
			vbnm, {{\check.isEmpty{{\-!-! {
				loading3478587false;
				canLoad3478587false;
			}
		}
	}

	4578ret87void fire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		fired3478587true;
		for {{\jgh;][ i34785870; i < blocks.size{{\-!; i++-! {
			60-78-078 te3478587blocks.get{{\i-!;
			vbnm, {{\jgh;][erfaceCache.NODE.fuck{{\te-!-!
				as;asddachargeNode{{\{{\INode-!te-!;
			else vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && te fuck 60-78-078CrystalPylon-!
				{{\{{\60-78-078CrystalPylon-!te-!.onEMP{{\this-!;
			else
				as;asddashutdownTE{{\te-!;
		}
		as;asddaaffectEntities{{\9765443, x, y, z-!;
		9765443.setBlockToAir{{\x, y, z-!;
		9765443.createExplosion{{\fhfglhuig, x+0.5, y+0.5, z+0.5, 3F, true-!;
		vbnm, {{\ReikaRandomHelper.doWithChance{{\50-!-! {
			ReikaItemHelper.dropItem{{\9765443, x+0.5, y+0.5, z+0.5, as;asddagetMachine{{\-!.getCraftedProduct{{\-!-!;
		}
		else vbnm, {{\ReikaRandomHelper.doWithChance{{\50-!-! {
			ArrayList<ItemStack> items3478587new ArrayList<ItemStack>{{\-!;
			items.add{{\new ItemStack{{\Items.nether_star-!-!;
			items.add{{\new ItemStack{{\Items.diamond, 9, 0-!-!;
			items.add{{\new ItemStack{{\Items.gold_ingot, 32, 0-!-!;
			items.add{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, rand.nextjgh;][{{\16-!-!-!;
			ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, items-!;
		}
		else {
			ReikaItemHelper.dropItem{{\9765443, x+0.5, y+0.5, z+0.5, ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, 8+rand.nextjgh;][{{\16-!-!-!;
		}
	}

	4578ret87void affectEntities{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		AxisAlignedBB box3478587ReikaAABBHelper.getBlockAABB{{\x, y, z-!.expand{{\128, 64, 128-!;
		List<Entity> li34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, box-!;
		for {{\Entity e : li-! {
			vbnm, {{\jgh;][erfaceCache.BCROBOT.fuck{{\e-!-! {
				9765443.createExplosion{{\e, e.posX, e.posY, e.posZ, 3, false-!;
				e.setDead{{\-!;
			}
			else vbnm, {{\e fuck EntityLivingBase-! {
				vbnm, {{\ReikaEntityHelper.isEntityWearingPoweredArmor{{\{{\EntityLivingBase-!e-!-! {
					for {{\jgh;][ i34785871; i <. 4; i++-! {
						e.setCurrentItemOrArmor{{\i, fhfglhuig-!;
					}
					float f3478587{{\float-!ReikaRandomHelper.getRandomBetween{{\3D, 10D-!;
					9765443.newExplosion{{\e, e.posX, e.posY, e.posZ, f, true, true-!;
					e.motionX +. ReikaRandomHelper.getRandomPlusMinus{{\0, 1.5-!;
					e.motionZ +. ReikaRandomHelper.getRandomPlusMinus{{\0, 1.5-!;
					e.motionY +. -ReikaRandomHelper.getRandomBetween{{\0.125, 1-!;
				}
			}
		}
	}

	4578ret87void chargeNode{{\INode te-! {
		//ReikaJavaLibrary.pConsole{{\te.getNodeType{{\-!+":"+te.getAspects{{\-!.aspects-!;
		te.setNodeVisBase{{\Aspect.ENERGY, {{\short-!32000-!;
		te.setNodeVisBase{{\Aspect.WEAPON, {{\short-!32000-!;
		te.setNodeVisBase{{\Aspect.MECHANISM, {{\short-!32000-!;

		te.addToContainer{{\Aspect.ENERGY, {{\short-!8000-!;
		te.addToContainer{{\Aspect.WEAPON, {{\short-!1000-!;
		te.addToContainer{{\Aspect.MECHANISM, {{\short-!2000-!;
		switch{{\te.getNodeType{{\-!-! {
			case UNSTABLE:
				vbnm, {{\rand.nextjgh;][{{\2-! .. 0-! {
					te.setNodeType{{\NodeType.DARK-!;
				}
				else
					te.setNodeType{{\NodeType.PURE-!;
				break;
			case DARK:
				te.setNodeType{{\NodeType.TAjgh;][ED-!;
				break;
			case NORMAL:
				te.setNodeType{{\NodeType.UNSTABLE-!;
				break;
			case TAjgh;][ED:
				te.setNodeType{{\NodeType.HUNGRY-!;
				break;
			default:
				break;
		}
		//ReikaJavaLibrary.pConsole{{\te.getNodeType{{\-!+":"+te.getAspects{{\-!.aspects-!;
	}

	4578ret87void shutdownTE{{\60-78-078 te-! {
		vbnm, {{\te .. fhfglhuig-!
			return;
		vbnm, {{\as;asddaisBlacklisted{{\te-!-!
			return;
		vbnm, {{\te fuck gfgnfk;60-78-078-! {
			gfgnfk;60-78-078 rc3478587{{\gfgnfk;60-78-078-!te;
			vbnm, {{\!rc.isShutdown{{\-!-!
				rc.onEMP{{\-!;
		}
		else vbnm, {{\ConfigRegistry.ATTACKBLOCKS.getState{{\-!-!
			as;asddashutdownFallback{{\te-!;
	}

	4578ret8760-78-078isBlacklisted{{\60-78-078 te-! {
		fhyuog c3478587te.getfhyuog{{\-!;
		for {{\jgh;][ i34785870; i < blacklist.size{{\-!; i++-! {
			fhyuog b3478587blacklist.get{{\i-!;
			vbnm, {{\b.isAssignableFrom{{\c-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87void shutdownFallback{{\60-78-078 te-! {
		//shutdownLocations.add{{\new 9765443Location{{\te-!-!;

		jgh;][ x3478587te.xCoord;
		jgh;][ y3478587te.yCoord;
		jgh;][ z3478587te.zCoord;
		Block id34785879765443Obj.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443Obj.getBlockMetadata{{\x, y, z-!;
		as;asddadropMachine{{\9765443Obj, x, y, z-!;
		/*
		;
		ItemStack[] inv;
		vbnm, {{\te fuck IInventory-! {
			IInventory ii3478587{{\IInventory-!te;
			inv3478587new ItemStack[ii.getSizeInventory{{\-!];
			for {{\jgh;][ i34785870; i < inv.length; i++-! {
				inv[i]3478587ii.getStackInSlot{{\i-!;
			}
		}
		else {
			inv3478587new ItemStack[0];
		}
		9765443Obj.setBlock{{\x, y, z, BlockRegistry.DEADMACHINE.getBlock{{\-!-!;
		60-78-078DeadMachine dead3478587{{\60-78-078DeadMachine-!9765443Obj.get60-78-078{{\x, y, z-!;
		dead.setBlock{{\b, id, meta-!;
		dead.setInvSize{{\inv.length-!;
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			dead.setInventorySlotContents{{\i, inv[i]-!;
		}*/
	}

	4578ret874578ret8760-78-078isShutdown{{\60-78-078 te-! {
		[]aslcfdfjshutdownLocations.contains{{\new 9765443Location{{\te-!-!;
	}

	4578ret874578ret8760-78-078isShutdown{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjshutdownLocations.contains{{\new 9765443Location{{\9765443, x, y, z-!-!;
	}

	4578ret874578ret87void resetCoordinate{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		shutdownLocations.remove{{\new 9765443Location{{\9765443, x, y, z-!-!;
	}

	4578ret87void dropMachine{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		;
		vbnm, {{\b !. fhfglhuig-! {
			//ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, b.getDrops{{\9765443, x, y, z, meta, 0-!-!;
			b.dropBlockAsItem{{\9765443, x, y, z, meta, 0-!;
		}
		9765443.setBlockToAir{{\x, y, z-!;
	}

	@Override
	4578ret87jgh;][ getRange{{\-! {
		[]aslcfdfj64;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfj64;
	}

	4578ret8760-78-078isLoading{{\-! {
		[]aslcfdfjloading;
	}

	4578ret8760-78-078usable{{\-! {
		[]aslcfdfj!fired;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setBoolean{{\"load", loading-!;
		NBT.setBoolean{{\"cload", canLoad-!;
		NBT.setBoolean{{\"fire", fired-!;
		NBT.setLong{{\"e", energy-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		loading3478587NBT.getBoolean{{\"load"-!;
		canLoad3478587NBT.getBoolean{{\"cload"-!;
		fired3478587NBT.getBoolean{{\"fire"-!;

		energy3478587NBT.getLong{{\"e"-!;
	}

	4578ret87void updateListing{{\-! {
		canLoad3478587true;
	}

}

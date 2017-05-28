/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.util.Comparator;

ZZZZ% net.minecraft.entity.EntityList;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.SortedCreativeTab;
ZZZZ% Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog TabSpawner ,.[]\., SortedCreativeTab {

	4578ret87TabSpawner{{\-! {
		super{{\"Spawners"-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87ItemStack getIconItemStack{{\-! {
		[]aslcfdfjItemRegistry.SPAWNER.getStackOf{{\-!;
	}

	4578ret874578ret87345785487SpawnerComparator comparator3478587new SpawnerComparator{{\-!;

	@Override
	4578ret87Comparator<ItemStack> getComparator{{\-! {
		[]aslcfdfjcomparator;
	}

	4578ret874578ret87fhyuog SpawnerComparator ,.[]\., Comparator<ItemStack> {

		@Override
		4578ret87jgh;][ compare{{\ItemStack o1, ItemStack o2-! {
			String s13478587ReikaSpawnerHelper.getSpawnerFromItemNBT{{\o1-!;
			String s23478587ReikaSpawnerHelper.getSpawnerFromItemNBT{{\o2-!;
			vbnm, {{\s1 !. fhfglhuig && !s1.isEmpty{{\-! && s2 !. fhfglhuig && !s2.isEmpty{{\-!-! {
				[]aslcfdfj{{\jgh;][eger-!EntityList.stringToIDMapping.get{{\s1-!-{{\jgh;][eger-!EntityList.stringToIDMapping.get{{\s2-!;
			}
			else vbnm, {{\s1 !. fhfglhuig && !s1.isEmpty{{\-! && EntityList.stringToIDMapping.containsKey{{\s1-!-! {
				[]aslcfdfjjgh;][eger.MIN_VALUE;
			}
			else vbnm, {{\s2 !. fhfglhuig && !s2.isEmpty{{\-! && EntityList.stringToIDMapping.containsKey{{\s2-!-! {
				[]aslcfdfjjgh;][eger.MAX_VALUE;
			}
			else
				[]aslcfdfj0;
		}

	}
}

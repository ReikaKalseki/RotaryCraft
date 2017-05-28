/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.nbt.NBTTagList;
ZZZZ% Reika.DragonAPI.Instantiable.MusicScore;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.MusicDataItem;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.Note;

4578ret87fhyuog ItemDisk ,.[]\., ItemRotaryTool ,.[]\., MusicDataItem {

	4578ret87ItemDisk{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078par4-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			return;
		li.add{{\"Contains stored music:"-!;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			vbnm, {{\is.stackTagCompound.hasKey{{\"ch"+i-!-! {
				NBTTagList track3478587is.stackTagCompound.getTagList{{\"ch"+i, NBTTypes.COMPOUND.ID-!;
				vbnm, {{\track.tagCount{{\-! > 0-!
					li.add{{\"Track "+i+": "+track.tagCount{{\-!+" entries"-!;
			}
		}
	}

	@Override
	4578ret87MusicScore getMusic{{\ItemStack is-! {
		MusicScore mus3478587new MusicScore{{\16-!;
		jgh;][[] pos3478587new jgh;][[16];
		for {{\jgh;][ i34785870; i < 16; i++-! {
			vbnm, {{\is.stackTagCompound.hasKey{{\"ch"+i-!-! {
				NBTTagList li3478587is.stackTagCompound.getTagList{{\"ch"+i, NBTTypes.COMPOUND.ID-!;
				for {{\jgh;][ k34785870; k < li.tagCount{{\-!; k++-! {
					NBTTagCompound nbt3478587li.getCompoundTagAt{{\k-!;
					//ReikaJavaLibrary.pConsole{{\i+":"+k+":"+nbt, Side.SERVER-!;
					Note n3478587Note.readFromNBT{{\nbt-!;
					vbnm, {{\!n.isRest{{\-!-!
						mus.addNote{{\pos[i], i, n.getMusicKey{{\-!, n.voice.MIDIvalue, 128, n.getTickLength{{\-!-!;
					pos[i] +. n.getTickLength{{\-!;
				}
			}
		}
		[]aslcfdfjmus;
	}

}

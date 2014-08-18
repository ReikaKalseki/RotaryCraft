/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTMachine {

	/** Do not return null. */
	public NBTTagCompound getTagsToWriteToStack();

	/** May supply null. */
	public void setDataFromItemStackTag(NBTTagCompound NBT);

	/** Do not return null. */
	public ArrayList<NBTTagCompound> getCreativeModeVariants();

	/** Will never supply null. */
	public ArrayList<String> getDisplayTags(NBTTagCompound NBT);
}

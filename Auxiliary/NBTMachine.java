package Reika.RotaryCraft.Auxiliary;

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
	public String getDisplayTag(NBTTagCompound NBT);
}

/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Interfaces.XPProducer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityBlastFurnace extends InventoriedRCTileEntity implements TemperatureTE, XPProducer, FrictionHeatable, DiscreteFunction, ConditionalOperation {

	private int temperature;
	public ItemStack[] inv = new ItemStack[15];
	public int smeltTime = 0;

	public static final int SMELTTEMP = 600;
	public static final int BEDROCKTEMP = 1000;
	public static final int MAXTEMP = 1500;
	public static final float SMELT_XP = 0.6F;

	private float xp;

	@Override
	protected int getActiveTexture() {
		return (temperature >= SMELTTEMP && this.hasSteelIngredients() ? 1 : 0);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		if (tickcount >= 20) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
		}

		boolean steel = false;
		boolean bedrock = false;
		boolean scrap = false;

		if (this.canMakeSteel()) {
			steel = true;
		}
		else if (this.canMakeBedrock()) {
			bedrock = true;
		}
		else if (this.canMeltScrap()) {
			scrap = true;
		}
		else {
			smeltTime = 0;
			return;
		}

		smeltTime++;
		if (smeltTime >= this.getOperationTime()) {
			if (steel)
				this.makeSteel();
			else if (bedrock)
				this.makeBedrock();
			else if (scrap)
				this.meltScrap();
		}
	}

	private void meltScrap() {
		int num = 1;
		if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inv, 10))
			if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inv, 12))
				if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inv, 13))
					if (!this.checkSpreadFit(ItemStacks.steelingot, num))
						return;

		for (int i = 1; i < inv.length-1; i++) {
			ReikaInventoryHelper.decrStack(i, inv);
		}
		RotaryAchievements.RECYCLE.triggerAchievement(this.getPlacer());
	}

	private boolean canMeltScrap() {
		if (temperature < SMELTTEMP)
			return false;
		for (int i = 1; i < inv.length-1; i++) {
			ItemStack is = inv[i];
			if (!ReikaItemHelper.matchStacks(is, ItemStacks.scrap))
				return false;
		}
		return true;
	}

	private void makeBedrock() {
		smeltTime = 0;
		if (worldObj.isRemote)
			return;

		if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.bedingot.itemID, 1, ItemStacks.bedingot.getItemDamage(), inv, 10))
			if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.bedingot.itemID, 1, ItemStacks.bedingot.getItemDamage(), inv, 12))
				if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.bedingot.itemID, 1, ItemStacks.bedingot.getItemDamage(), inv, 13))
					if (!this.checkSpreadFit(ItemStacks.bedingot, 1))
						return;

		for (int i = 0; i < 4; i++)
			ReikaInventoryHelper.decrStack(0, inv);
		ReikaInventoryHelper.decrStack(1, inv);
	}

	public boolean canMakeSteel() {
		return temperature >= SMELTTEMP && this.hasSteelIngredients();
	}

	public boolean canMakeBedrock() {
		return temperature >= BEDROCKTEMP && this.hasBedrockIngredients();
	}

	private boolean hasBedrockIngredients() {
		if (inv[0] == null)
			return false;
		if (!ReikaItemHelper.matchStacks(inv[0], ItemStacks.bedrockdust))
			return false;
		if (inv[0].stackSize < 4)
			return false;
		if (inv[11] != null)
			return false;
		if (inv[1] == null)
			return false;
		if (!ReikaItemHelper.matchStacks(inv[1], ItemStacks.steelingot))
			return false;
		for (int i = 2; i < 10; i++) {
			if (inv[i] != null)
				return false;
		}

		if (inv[10] != null) {
			if (inv[10].itemID != ItemStacks.bedingot.itemID || inv[10].getItemDamage() != ItemStacks.bedingot.getItemDamage() || inv[10].stackSize+9 >= ItemStacks.bedingot.getMaxStackSize()) {
				if (inv[13] != null) {
					if (inv[13].itemID != ItemStacks.bedingot.itemID || inv[13].getItemDamage() != ItemStacks.bedingot.getItemDamage() || inv[13].stackSize+9 >= ItemStacks.bedingot.getMaxStackSize()) {
						if (inv[12] != null) {
							if (inv[12].itemID != ItemStacks.bedingot.itemID || inv[12].getItemDamage() != ItemStacks.bedingot.getItemDamage() || inv[12].stackSize+9 >= ItemStacks.bedingot.getMaxStackSize()) {
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

	private boolean hasSteelIngredients() {
		if (inv[0] == null)
			return false;
		if (inv[0].itemID != Item.coal.itemID)
			return false;
		if (inv[11] == null)
			return false;
		if (inv[11].itemID != Item.gunpowder.itemID)
			return false;
		if (inv[14] == null)
			return false;
		if (inv[14].itemID != Block.sand.blockID)
			return false;


		if (inv[10] != null) {
			if (inv[10].itemID != ItemStacks.steelingot.itemID || inv[10].getItemDamage() != ItemStacks.steelingot.getItemDamage() || inv[10].stackSize+9 >= ItemStacks.steelingot.getMaxStackSize()) {
				if (inv[13] != null) {
					if (inv[13].itemID != ItemStacks.steelingot.itemID || inv[13].getItemDamage() != ItemStacks.steelingot.getItemDamage() || inv[13].stackSize+9 >= ItemStacks.steelingot.getMaxStackSize()) {
						if (inv[12] != null) {
							if (inv[12].itemID != ItemStacks.steelingot.itemID || inv[12].getItemDamage() != ItemStacks.steelingot.getItemDamage() || inv[12].stackSize+9 >= ItemStacks.steelingot.getMaxStackSize()) {
								return false;
							}
						}
					}
				}
			}
		}
		if (!ReikaInventoryHelper.checkForItem(Item.ingotIron.itemID, inv))
			return false;
		return true;
	}

	public int getTemperatureScaled(int p1) {
		return ((p1*temperature)/MAXTEMP);
	}

	private void makeSteel() {
		smeltTime = 0;
		if (worldObj.isRemote)
			return;
		ReikaInventoryHelper.decrStack(0, inv);
		int num = ReikaInventoryHelper.countNumStacks(Item.ingotIron.itemID, -1, inv);
		if ((int)Math.sqrt(num) >= 1 && rand.nextInt(3) == 0) {
			ReikaInventoryHelper.decrStack(11, inv);
		}
		if ((int)Math.sqrt(num) >= 1 && rand.nextInt(3) == 0) {
			ReikaInventoryHelper.decrStack(14, inv);
		}
		if (ReikaRandomHelper.doWithChance(DifficultyEffects.BONUSSTEEL.getDouble()*(ReikaMathLibrary.intpow(1.005, num*num)-1))) {
			num *= 1+rand.nextFloat();
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(num));
		if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inv, 10))
			if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inv, 12))
				if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inv, 13))
					if (!this.checkSpreadFit(ItemStacks.steelingot, num))
						return;
		for (int i = 1; i < inv.length-1; i++) {
			if (inv[i] != null) {
				if (inv[i].itemID == Item.ingotIron.itemID) {
					ReikaInventoryHelper.decrStack(i, inv);
				}
			}
		}
		xp += SMELT_XP*num;
	}

	public void dropXP() {
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord+rand.nextFloat(), yCoord+1.25F, zCoord+rand.nextFloat(), (int)xp);
		xp = 0;
	}

	public float getXP() {
		return xp;
	}

	public void clearXP() {
		xp = 0;
	}

	private boolean checkSpreadFit(ItemStack is, int num) {
		int maxfit = 0;
		int f1 = is.getMaxStackSize()-inv[10].stackSize;
		int f2 = is.getMaxStackSize()-inv[12].stackSize;
		int f3 = is.getMaxStackSize()-inv[13].stackSize;
		maxfit = f1+f2+f3;
		if (num > maxfit)
			return false;
		if (f1 > num) {
			inv[10].stackSize += num;
			return true;
		}
		else {
			inv[10].stackSize = inv[10].getMaxStackSize();
			num -= f1;
		}
		if (f2 > num) {
			inv[12].stackSize += num;
			return true;
		}
		else {
			inv[12].stackSize = inv[12].getMaxStackSize();
			num -= f2;
		}
		if (f3 > num) {
			inv[12].stackSize += num;
			return true;
		}
		else {
			inv[13].stackSize = inv[13].getMaxStackSize();
			num -= f3;
		}
		return true;
	}

	public int getOperationTime() {
		int time = 2*((MAXTEMP-(temperature-SMELTTEMP))/12);
		if (time < 1)
			return 1;
		return time;
	}

	public int getCookScaled(int p1) {
		if (temperature < SMELTTEMP)
			return 0;
		return ((p1*smeltTime)/this.getOperationTime());
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaBiomeHelper.getBiomeTemp(world, x, z);

		ForgeDirection waterside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water);
		if (waterside != null) {
			Tamb /= 2;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
		if (iceside != null) {
			if (Tamb > 0)
				Tamb /= 4;
			ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Block.waterMoving.blockID, 0);
		}
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.fire.blockID);
		if (fireside != null) {
			Tamb += 200;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava);
		if (lavaside != null) {
			Tamb += 600;
		}
		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
		if (temperature > 100) {
			ForgeDirection side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.snow.blockID);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, 0, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Block.waterMoving.blockID, 0);
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inv.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inv[par1];
	}

	/**
	 *
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("melt", smeltTime);
		NBT.setInteger("temp", temperature);
		NBT.setFloat("exp", xp);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		smeltTime = NBT.getInteger("melt");
		temperature = NBT.getInteger("temp");
		xp = NBT.getFloat("exp");

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		if (is == null)
			return false;
		if (i == 0)
			return is.itemID == Item.coal.itemID;
		else if (i == 11)
			return is.itemID == Item.gunpowder.itemID;
		else if (i <= 9)
			return is.itemID == Item.ingotIron.itemID;
		else
			return false;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BLASTFURNACE;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 10 || i == 12 || i == 13;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasSteelIngredients())
			return 15;
		return 0;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public void onEMP() {}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	@Override
	public boolean areConditionsMet() {
		return this.canMakeBedrock() || this.canMakeSteel() || this.canMeltScrap();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}
}

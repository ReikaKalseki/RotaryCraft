/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import Reika.DragonAPI.Libraries.ReikaPotionHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityAerosolizer extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation {

	public static final int MAXRANGE = Math.max(64, ConfigRegistry.AERORANGE.getValue());
	public static final int CAPACITY = 64;

	public int potionLevel[] = new int[9];
	public int potionDamage[] = new int[9];
	private int potionIDs[] = new int[9];
	private int copies[] = new int[9];

	public int[] slotColor = new int[9];


	private int tickcount2 = 0;

	public boolean idle = false;

	public void testIdle() {
		boolean empty = true;
		for (int i = 0; i < 9; i++) {
			if (potionDamage[i] > 8192)
				empty = false;
		}
		idle = empty;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		power = (long)omega*(long)torque;
		//this.getIOSides(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		this.getSummativeSidedPower();
		tickcount++;
		tickcount2++;
		this.consumeBottles();
		this.colorize();
		for (int i = 0; i < 9; i++)
			potionIDs[i] = ReikaPotionHelper.getPotionID(potionDamage[i]);
		if (power < MINPOWER)
			return;
		this.testIdle();
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d  %d", this.potionDamage.length, this.potionLevel.length, 0, 0));
		for (int i = 0; i < 9; i++) {
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.potionLevel[i]));
			if (tickcount2 >= 20/ReikaMathLibrary.extrema(this.getMultiplier(i), 1, "max")) {
				//this.potionDamage[i] = this.getPotion(i);
				AxisAlignedBB room = this.getRoom(world, x, y, z, meta);
				if (potionLevel[i] > 0)
					this.dispense2(world, x, y, z, meta, room, i);
				if (i == 8)
					tickcount2 = 0;
			}
			if (tickcount >= 2400 && potionLevel[i] > 0) {
				potionLevel[i]--;
				if (i == 8)
					tickcount = 0;
			}
			if (potionLevel[i] <= 0) {
				potionLevel[i] = 0;
				if (i == 8)
					tickcount = 0;
			}
		}
	}

	public void colorize() {
		for (int i = 0; i < 9; i++) {
			if (potionIDs[i] > 0)
				slotColor[i] = Potion.potionTypes[potionIDs[i]].getLiquidColor();
			else
				slotColor[i] = 0xff000000; //solid black
		}
	}

	public void consumeBottles() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return;
		for (int i = 0; i < 9; i++) {
			ItemStack inslot = this.getStackInSlot(i);
			if (inslot != null) {
				int size = inslot.stackSize;
				if (inslot.getItem() == Items.potionitem && (inslot.getItemDamage() == potionDamage[i] || potionLevel[i] == 0) && potionLevel[i] < CAPACITY) {
					int potionID = ReikaPotionHelper.getPotionID(inslot.getItemDamage());
					if (potionID != -1) {
						if (!Potion.potionTypes[potionID].isInstant()) {
							boolean extended = PotionHelper.checkFlag(inslot.getItemDamage(), 6); //Bit 6 is enhanced
							boolean level2 = PotionHelper.checkFlag(inslot.getItemDamage(), 5); //Bit 5 is extended
							if (potionLevel[i] == 0) {
								potionDamage[i] = inslot.getItemDamage();
							}
							potionLevel[i] += size;
							if (extended && (potionLevel[i]+2 <= CAPACITY))
								potionLevel[i] += 2;
							//this.decrStackSize(i, 1);
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("+%d", size)+String.valueOf(FMLCommonHandler.instance().getEffectiveSide()));
							this.setInventorySlotContents(i, new ItemStack(Items.glass_bottle, size, 0));
							if (potionLevel[i] > CAPACITY)
								potionLevel[i] = CAPACITY;
						}
					}
				}
			}
		}
	}

	public int getLiquidScaled(int par1, int par2)
	{
		return (par2*par1)/CAPACITY;
	}

	public int getPotion(int i) { //add mod potion support!
		if (this.getStackInSlot(i) != null) {
			if (this.getStackInSlot(i).stackSize > 0 && this.getStackInSlot(i).getItem() instanceof ItemPotion) {
				int dmg = this.getStackInSlot(i).getItemDamage();
				return dmg;
			}
			else
				return 0;
		}
		else {
			return 0;
		}
	}

	public AxisAlignedBB getRoom(World world, int x, int y, int z, int meta) {
		int minx = x;
		int maxx = x+1;
		int miny = y;
		int maxy = y+1;
		int minz = z;
		int maxz = z+1;

		boolean exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (world.getBlock(x+i, y, z).isOpaqueCube())
				exit = true;
			else
				maxx = x+i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (world.getBlock(x-i, y, z).isOpaqueCube())
				exit = true;
			else
				minx = x-i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (world.getBlock(x, y+i, z).isOpaqueCube())
				exit = true;
			else
				maxy = y+i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (world.getBlock(x, y-i, z).isOpaqueCube())
				exit = true;
			else
				miny = x-i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (world.getBlock(x, y, z+i).isOpaqueCube())
				exit = true;
			else
				maxz = z+i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (world.getBlock(x, y, z-i).isOpaqueCube())
				exit = true;
			else
				minz = z-i;
		}
		exit = false;

		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz);
	}

	public void dispense2(World world, int x, int y, int z, int meta, AxisAlignedBB room, int i) { // id, duration, amplifier
		if (!worldObj.isRemote) {
			List effects = Items.potionitem.getEffects(potionDamage[i]);
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.potionDamage[i]));
			if (effects != null && !effects.isEmpty()) {
				List inroom = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, room);
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", inroom.size()));
				if (inroom != null && !inroom.isEmpty()) {
					Iterator iter = inroom.iterator();
					while (iter.hasNext()) {
						EntityLivingBase mob = (EntityLivingBase)iter.next();
						Iterator potioneffects = effects.iterator();
						while (potioneffects.hasNext()) {
							PotionEffect effect = (PotionEffect)potioneffects.next();
							int id = effect.getPotionID();
							if (!Potion.potionTypes[id].isInstant()) {
								int bonus = this.getMultiplier(i) - 1;  //-1 since adding
								if (effect.getAmplifier() == 1)
									bonus *= 2;
								mob.addPotionEffect(new PotionEffect(id, 100, effect.getAmplifier()+bonus));
								//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", effect.getAmplifier()));
							}
						}
					}
				}
			}
		}
	}

	public int getMultiplier(int i) {
		if (potionIDs[i] == -1)
			return 0;
		int[] number = new int[Potion.potionTypes.length];
		number = this.countCopies();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", this.potionIDs[i], number[this.potionIDs[i]]));
		return (number[potionIDs[i]]);
	}

	//method to return number of occurrences of the numbers in diceRolls
	public int[] countCopies() {
		int occurrence[] = new int[Potion.potionTypes.length]; //to hold the counts

		for(int i = 0; i < 9; i++) { //Loop over the dice rolls array
			int value = potionIDs[i]; //Get the value of the next roll
			if (value != -1) {
				occurrence[value]++; //Increment the value in the count array;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", occurrence[value]));
			}
		}
		return occurrence; //return the counts
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		potionDamage = NBT.getIntArray("damages");
		potionLevel = NBT.getIntArray("levels");
		potionIDs = NBT.getIntArray("IDs");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setIntArray("damages", potionDamage);
		NBT.setIntArray("levels", potionLevel);
		NBT.setIntArray("IDs", potionIDs);
	}

	public int getSizeInventory()
	{
		return 9;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getRange() {
		return MAXRANGE;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.AEROSOLIZER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.getItem() == Items.potionitem;
	}

	@Override
	public int getMaxRange() {
		return MAXRANGE;
	}

	@Override
	public int getRedstoneOverride() {
		return ((int)((ReikaArrayHelper.sumArray(potionLevel)/576D)*15));
	}

	@Override
	public boolean areConditionsMet() {
		for (int i = 0; i < potionLevel.length; i++) {
			if (potionLevel[i] > 0)
				return true;
		}
		return false;
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Potions";
	}
}
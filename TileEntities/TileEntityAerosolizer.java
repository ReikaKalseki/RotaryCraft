/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.ReikaPotionHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Interfaces.CustomPotion;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityAerosolizer extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation {

	public static final int MAXRANGE = Math.max(64, ConfigRegistry.AERORANGE.getValue());
	public static final int CAPACITY = 64;

	private PotionApplication[] potions = new PotionApplication[9];
	private int[] potionLevel = new int[9];

	private int tickcount2 = 0;

	public boolean idle = false;

	public void testIdle() {
		boolean empty = true;
		for (int i = 0; i < 9; i++) {
			if (potions[i] != null)
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
		this.getSummativeSidedPower();
		tickcount++;
		tickcount2++;
		this.consumeBottlesAndStorePotions();
		if (power < MINPOWER)
			return;
		this.testIdle();
		for (int i = 0; i < 9; i++) {
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

	private void consumeBottlesAndStorePotions() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return;
		for (int i = 0; i < 9; i++) {
			ItemStack inslot = this.getStackInSlot(i);
			if (inslot != null) {
				PotionApplication eff = this.getEffectFromItem(inslot);
				if (eff != null) {
					int num = inslot.stackSize*eff.amount;
					if (this.matchEffects(eff, potions[i]) && potionLevel[i]+num <= CAPACITY) {
						potions[i] = eff;
						potionLevel[i] += num;
						this.setInventorySlotContents(i, new ItemStack(Items.glass_bottle, inslot.stackSize, 0));
						if (potionLevel[i] > CAPACITY)
							potionLevel[i] = CAPACITY;
					}
				}
			}
		}
	}

	private boolean matchEffects(PotionApplication eff1, PotionApplication eff2) {
		return eff1 == eff2 || eff1 == null || eff2 == null || eff1.equals(eff2);
	}

	public int getPotionColor(int slot) {
		return potions[slot] != null ? (0xff000000 | potions[slot].renderColor) : 0xff000000;
	}

	public int getPotionLevel(int slot) {
		return potionLevel[slot];
	}

	private PotionApplication getEffectFromItem(ItemStack is) { // add mod potion support
		Item i = is.getItem();
		if (i instanceof ItemPotion) {
			int dmg = is.getItemDamage();
			/*
			int id = ReikaPotionHelper.getPotionID(dmg);
			if (id != -1) {
				Potion p = Potion.potionTypes[id];
				if (p != null && !p.isInstant()) {
					boolean extended = PotionHelper.checkFlag(dmg, 6); //Bit 6 is enhanced
					boolean level2 = PotionHelper.checkFlag(dmg, 5); //Bit 5 is extended
					return new PotionApplication(ReikaJavaLibrary.makeListFrom(new PotionEffect(p.id, 0)), extended ? 3 : 1, level2 ? 1 : 0);
				}
			}
			 */
			List<PotionEffect> li = ((ItemPotion)i).getEffects(is);
			for (PotionEffect p : li) {
				if (!Potion.potionTypes[p.getPotionID()].isInstant()) {
					boolean extended = PotionHelper.checkFlag(dmg, 6); //Bit 6 is enhanced
					boolean level2 = p.getAmplifier() > 0;
					return new PotionApplication(ReikaJavaLibrary.makeListFrom(new PotionEffect(p.getPotionID(), 0)), extended ? 3 : 1, level2 ? 1 : 0);
				}
			}
		}
		else if (i instanceof CustomPotion) {
			CustomPotion cp = (CustomPotion)i;
			Potion p = cp.getPotion(is);
			if (p != null && !p.isInstant()) {
				boolean extended = cp.isExtended(is);
				boolean level2 = cp.getAmplifier(is) > 0;
				return new PotionApplication(ReikaJavaLibrary.makeListFrom(new PotionEffect(p.id, 0)), extended ? 3 : 1, level2 ? 1 : 0);
			}
		}
		return null;
	}

	public int getLiquidScaled(int par1, int par2)
	{
		return (par2*par1)/CAPACITY;
	}

	private AxisAlignedBB getRoom(World world, int x, int y, int z, int meta) {
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

	private void dispense2(World world, int x, int y, int z, int meta, AxisAlignedBB room, int i) { // id, duration, amplifier
		if (!worldObj.isRemote) {
			if (potions[i] != null) {
				List<PotionEffect> effects = potions[i].effects;
				if (effects != null && !effects.isEmpty()) {
					List<EntityLivingBase> inroom = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, room);
					for (EntityLivingBase mob : inroom) {
						for (PotionEffect effect : effects) {
							int id = effect.getPotionID();
							int bonus = this.getMultiplier(i) - 1;  //-1 since adding
							if (effect.getAmplifier() == 1)
								bonus *= 2;
							mob.addPotionEffect(new PotionEffect(id, 100, effect.getAmplifier()+bonus));
						}
					}
				}
			}
		}
	}

	public int getMultiplier(int i) {
		if (potions[i] == null)
			return 0;
		return this.countCopies(potions[i]);
	}

	private int countCopies(PotionApplication p) {
		int c = 0;
		for (int i = 0; i < 9; i++) {
			PotionApplication in = potions[i];
			if (in != null && in.equals(p)) {
				c++;
			}
		}
		return c;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		for (int i = 0; i < 9; i++) {
			if (NBT.hasKey("potion_"+i)) {
				NBTTagCompound tag = NBT.getCompoundTag("potion_"+i);
				potions[i] = PotionApplication.readFromNBT(tag);
			}
			else {
				potions[i] = null;
			}
		}
		potionLevel = NBT.getIntArray("levels");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		for (int i = 0; i < 9; i++) {
			if (potions[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				potions[i].writeToNBT(tag);
				NBT.setTag("potion_"+i, tag);
			}
		}
		NBT.setIntArray("levels", potionLevel);
	}

	public int getSizeInventory()
	{
		return 9;
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

	private static class PotionApplication {

		private final List<PotionEffect> effects;
		private final int amount;
		private final int potionLevel;
		public final int renderColor;

		private PotionApplication(List<PotionEffect> li, int amt, int lvl) {
			effects = li;
			amount = amt;
			potionLevel = lvl;
			Collections.sort(effects, ReikaPotionHelper.effectSorter);

			renderColor = this.calcColor(effects);
		}

		private int calcColor(List<PotionEffect> li) {
			int sum = 0;
			for (PotionEffect p : li) {
				sum += Potion.potionTypes[p.getPotionID()].getLiquidColor();
			}
			return sum/li.size();
		}

		public void writeToNBT(NBTTagCompound NBT) {
			NBTTagList li = new NBTTagList();
			for (PotionEffect eff : effects) {
				NBTTagCompound tag = new NBTTagCompound();
				eff.writeCustomPotionEffectToNBT(tag);
				li.appendTag(tag);
			}
			NBT.setTag("effects", li);
			NBT.setInteger("amount", amount);
			NBT.setInteger("level", potionLevel);
			NBT.setInteger("color", renderColor);
		}

		public static PotionApplication readFromNBT(NBTTagCompound NBT) {
			int amt = NBT.getInteger("amount");
			int lvl = NBT.getInteger("level");
			int c = NBT.getInteger("color");
			ArrayList<PotionEffect> fx = new ArrayList();
			NBTTagList li = NBT.getTagList("effects", NBTTypes.COMPOUND.ID);
			for (Object o : li.tagList) {
				NBTTagCompound tag = (NBTTagCompound)o;
				PotionEffect p = PotionEffect.readCustomPotionEffectFromNBT(tag);
				fx.add(p);
			}
			return new PotionApplication(fx, amt, lvl);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof PotionApplication) {
				PotionApplication p = (PotionApplication)o;
				return p.potionLevel == potionLevel && this.matchEffects(p);
			}
			return false;
		}

		private boolean matchEffects(PotionApplication p) {
			if (effects.size() != p.effects.size())
				return false;
			for (int i = 0; i < effects.size(); i++) {
				PotionEffect p1 = effects.get(i);
				PotionEffect p2 = p.effects.get(i);
				if (!(p1.getPotionID() == p2.getPotionID() && p1.getAmplifier() == p2.getAmplifier())) {
					return false;
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			return (amount | (potionLevel << 8)) << 16 | effects.hashCode();
		}

	}
}

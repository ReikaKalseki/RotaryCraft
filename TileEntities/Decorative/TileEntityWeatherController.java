/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

import java.util.ArrayList;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.Event.WeatherControlEvent;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWeatherController extends InventoriedPowerReceiver implements ConditionalOperation {

	private int cooldown = 0;

	private RainMode rainmode = RainMode.NONE;

	private static enum RainMode {
		NONE(),
		SUN(),
		RAIN(),
		THUNDER(),
		SUPERSTORM();

		public boolean isRain() {
			return this.ordinal() > SUN.ordinal();
		}

		public boolean isThunder() {
			return this.ordinal() > RAIN.ordinal();
		}

		public boolean hasAction() {
			return this != NONE;
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		if (cooldown > 0)
			cooldown--;
		if (power < MINPOWER)
			return;
		if (!world.canBlockSeeTheSky(x, y+1, z))
			return;

		WorldInfo wi = world.getWorldInfo();
		//ReikaJavaLibrary.pConsole(rainmode, Side.SERVER);
		if (rainmode == RainMode.SUPERSTORM) {
			wi.setRaining(true);
			wi.setThundering(true);
			if (rand.nextInt(20) == 0) {
				int xl = x-64+rand.nextInt(129);
				int zl = z-64+rand.nextInt(129);
				int yl = world.getTopSolidOrLiquidBlock(xl, zl);
				world.addWeatherEffect(new EntityLightningBolt(world, xl, yl, zl));
			}
		}
		if (cooldown > 0)
			return;
		rainmode = this.getRainMode();
		//ReikaJavaLibrary.pConsole(this.rainmode);
		if (rainmode.isRain() && ConfigRegistry.BANRAIN.getState())
			rainmode = RainMode.NONE;
		if (!rainmode.hasAction())
			return;
		boolean isThunder = world.isThundering();
		if (this.isAlready(world))
			return;
		boolean rain = rainmode.isRain();
		boolean thunder = rainmode.isThunder();
		boolean storm = rainmode == RainMode.SUPERSTORM;
		wi.setRaining(rain);
		wi.setThundering(thunder);
		MinecraftForge.EVENT_BUS.post(new WeatherControlEvent(this, rain, thunder, storm));
	}

	private boolean isAlready(World world) {
		boolean rain = rainmode.isRain();
		boolean thunder = rainmode.isThunder();
		boolean rain2 = world.isRaining();
		boolean thunder2 = world.isThundering();
		return rain == rain2 && thunder == thunder2;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	private void fire(ItemStack is, ItemStack is2) {
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.explode", 1F, 1F);
		if (is != null) {
			EntityItem ei = new EntityItem(worldObj, xCoord+0.5, yCoord+1.0625, zCoord+0.5, new ItemStack(is.itemID, 1, is.getItemDamage()));
			ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
			ei.delayBeforeCanPickup = 5000;
			ei.age = 5900;
			ei.motionY = 3;
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(ei);
		}
		if (is2 != null) {
			EntityItem ei = new EntityItem(worldObj, xCoord+0.5, yCoord+1.0625, zCoord+0.5, new ItemStack(is2.itemID, 1, is2.getItemDamage()));
			ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
			ei.delayBeforeCanPickup = 5000;
			ei.age = 5900;
			ei.motionY = 3;
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(ei);
		}
	}

	private boolean hasSawdust() {
		boolean sawdust = ReikaInventoryHelper.checkForItemStack(ItemStacks.sawdust.itemID, ItemStacks.sawdust.getItemDamage(), inv);
		if (sawdust)
			return true;
		ArrayList<ItemStack> li = OreDictionary.getOres("dustWood");
		for (int i = 0; i < inv.length; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				if (ReikaItemHelper.listContainsItemStack(li, is))
					return true;
			}
		}
		return false;
	}

	public RainMode getRainMode() {
		RainMode rainmode;
		ItemStack is = null;
		ItemStack is2 = null;
		boolean sawdust = this.hasSawdust();
		boolean silverio = ReikaInventoryHelper.checkForItemStack(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inv);
		boolean redstone = ReikaInventoryHelper.checkForItem(Item.redstone.itemID, inv);
		boolean glowdust = ReikaInventoryHelper.checkForItem(Item.glowstone.itemID, inv);
		if (sawdust) {
			rainmode = RainMode.SUN;
			is = ItemStacks.sawdust;
		}
		else if (silverio) {
			rainmode = RainMode.RAIN;
			is = ItemStacks.silveriodide;
			if (redstone) {
				rainmode = RainMode.THUNDER;
				is2 = new ItemStack(Item.redstone.itemID, 1, 0);
			}
			else if (glowdust) {
				rainmode = RainMode.SUPERSTORM;
				is2 = new ItemStack(Item.glowstone.itemID, 1, 0);
			}
		}
		else
			rainmode = RainMode.NONE;
		//ReikaJavaLibrary.pConsole(rainmode, Side.SERVER);
		if (this.isAlready(worldObj))
			return this.rainmode;
		cooldown = 200+rand.nextInt(400);
		if (rainmode.hasAction())
			this.fire(is, is2);
		int slot = -1;
		switch(rainmode) {
		case NONE:
			break;
		case SUN:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.sawdust.itemID, ItemStacks.sawdust.getItemDamage(), inv);
			ReikaInventoryHelper.decrStack(slot, inv);
			break;
		case RAIN:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inv);
			ReikaInventoryHelper.decrStack(slot, inv);
			break;
		case THUNDER:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inv);
			ReikaInventoryHelper.decrStack(slot, inv);
			slot = ReikaInventoryHelper.locateInInventory(Item.redstone.itemID, inv);
			ReikaInventoryHelper.decrStack(slot, inv);
			break;
		case SUPERSTORM:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inv);
			ReikaInventoryHelper.decrStack(slot, inv);
			slot = ReikaInventoryHelper.locateInInventory(Item.glowstone.itemID, inv);
			ReikaInventoryHelper.decrStack(slot, inv);
			break;
		}
		return rainmode;
	}

	private boolean isValidWeatherItem(ItemStack is) {
		if (is.itemID == ItemStacks.sawdust.itemID && is.getItemDamage() == ItemStacks.sawdust.getItemDamage())
			return true;
		if (is.itemID == ItemStacks.silveriodide.itemID && is.getItemDamage() == ItemStacks.silveriodide.getItemDamage())
			return true;
		if (is.itemID == Item.redstone.itemID)
			return true;
		if (is.itemID == Item.glowstone.itemID)
			return true;
		ArrayList<ItemStack> li = OreDictionary.getOres("dustWood");
		if (ReikaItemHelper.listContainsItemStack(li, is))
			return true;
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.WEATHERCONTROLLER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isValidWeatherItem(is);
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean areConditionsMet() {
		return this.getRainMode().hasAction();
	}

	@Override
	public String getOperationalStatus() {
		return cooldown <= 0 ? this.areConditionsMet() ? "Operational" : "Empty Inventory" : "Idle";
	}
}

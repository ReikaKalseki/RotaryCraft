package Reika.RotaryCraft.ModInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.ClassReparenter.Reparent;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.AppEngHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;

@Reparent(value = {"thaumcraft.api.wands.ItemFocusBasic", "net.minecraft.item.Item"})
public class ItemScrewdriverFocus extends ItemFocusBasic {

	public ItemScrewdriverFocus(int idx) {
		this.setCreativeTab(RotaryCraft.tabRotaryTools);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected String getIconString() {
		return "rotarycraft:screw-focus";
	}

	@Override
	@ModDependent(ModList.THAUMCRAFT)
	public AspectList getVisCost(ItemStack focusStack) {
		return new AspectList().add(Aspect.ORDER, 1);
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack wand, World world, EntityPlayer player, MovingObjectPosition mov) {
		if (mov == null)
			return null;
		ItemStack is = ItemRegistry.SCREWDRIVER.getStackOf();
		boolean flag = false;
		int x = mov.blockX;
		int y = mov.blockY;
		int z = mov.blockZ;
		float a = (float)mov.hitVec.xCoord;
		float b = (float)mov.hitVec.yCoord;
		float c = (float)mov.hitVec.zCoord;
		if (ItemRegistry.SCREWDRIVER.getItemInstance().onItemUse(is, player, world, x, y, z, mov.sideHit, a, b, c))
			flag = MachineRegistry.getMachine(world, x, y, z) != null;
		if (this.fakeScrewclick(world, x, y, z, player, mov, a, b, c, is))
			flag = true;
		if (AppEngHandler.getInstance().tryRightClick(is, x, y, z, mov.sideHit, player, world, 0))
			flag = true;
		if (flag) {
			ReikaSoundHelper.playSoundFromServer(world, x+0.5, y+0.5, z+0.5, "thaumcraft:wand", 0.4F, 0.7F+world.rand.nextFloat()*0.6F, true);
			ReikaSoundHelper.playSoundFromServer(world, x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.5F, 0.75F, true);
		}
		return null;
	}

	private boolean fakeScrewclick(World world, int x, int y, int z, EntityPlayer player, MovingObjectPosition mov, float a, float b, float c, ItemStack is) {
		ItemStack hold = player.getCurrentEquippedItem();
		if (hold != null)
			hold = hold.copy();
		player.setCurrentItemOrArmor(0, is);
		boolean ret = world.getBlock(x, y, z).onBlockActivated(world, x, y, z, player, mov.sideHit, a, b, c);
		//MinecraftForge.EVENT_BUS.post(new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, x, y, z, mov.sideHit, world));
		player.setCurrentItemOrArmor(0, hold);
		return ret;
	}

	@Override
	public EnumRarity getRarity(ItemStack focusstack) {
		return EnumRarity.uncommon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int par1) {
		return itemIcon;
	}

	/**
	 * What color will the focus orb be rendered on the held wand
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getFocusColor(ItemStack focusstack) {
		return 0xCACBF2;
	}

	/**
	 * Does the focus have ornamentation like the focus of the nine hells. Ornamentation is a standard icon rendered in a cross around the focus
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getOrnament(ItemStack focusstack) {
		return null;
	}

	/**
	 * An icon to be rendered inside the focus itself
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
		return null;
	}

}

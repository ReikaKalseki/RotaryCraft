package Reika.RotaryCraft.TileEntities;


public class TileEntityMachineGun extends TileEntityInventoriedPowerReceiver implements RangedEffect {

private StepTimer timer = new StepTimer(5); // controls max fire rate

private ItemStack[] inv = new ItemStack[27];

public void updateEntity(World world, int x, int y, int z, int meta) {
super.updateTileEntity();
this.getIOSidesDefault(world, x, y, z, meta);
this.getPower(false, false);

this.timer.update();
if (timer.checkCap() && ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv)) {
AxisAlignedBB box = this.drawAABB(x, y, z, meta);
List<EntityLiving> li = world.getEntitiesWithinAABB(EntityLiving.class, box);
if (li.size() > 0) {
this.fire(world, x, y, z, meta);
}
}
}

private int getArrowSlot() {
return ReikaInventoryHelper.locateItem(Item.arrow.itemID, inv);
}

public ItemStack getStackInSlot(int sl) {
return inv[sl];
}

public void setStackInSlot(int i, ItemStack is) {
inv[i] = is;
}

@Override
public boolean isStackValidForSlot(int i, ItemStack is) {
return is.itemID == Item.arrow.itemID;
}

public int getSizeInventory() {
return 27;
}

private void fire(World world, int x, int y, int z, int meta) {
double vx = 0;
double vz = 0;
double v = 2;
switch(meta) {
case 0:
x++;
vx = v;
break;
case 1:
x--;
vx = -v;
break;
case 2:
z++;
vz = v;
break;
case 3:
z--;
vz = -v;
break;
}
EntityArrow ar = new EntityArrow(world);
ar.setLocationAndAngles(x, y, z, 0, 0);
ar.motionX = vx;
ar.motionZ = vz;
if (!world.isRemote) {
ar.motionChanged = true;
world.spawnEntityInWorld(ar);
}
ReikaInventoryHelper.decrStack(this.getArrowSlot(), inv);
ReikaSoundHelper.playSoundAtBlock(x, y, z, "random.bow", 1, 1);
}

private AxisAlignedBB drawAABB(int x, int y, int z, int meta) {
AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1);
switch(meta) {
case 0:
box.addOffset(1, 0, 0);
box.maxX += this.getRange();
break;
case 1:
box.addOffset(-1, 0, 0);
box.minX -= this.getRange();
break;
case 2:
box.addOffset(0, 0, 1);
box.maxZ += this.getRange();
break;
case 3:
box.addOffset(0, 0, -1);
box.minZ -= this.getRange();
break;
}

return box;
}

public int getMachineIndex() {
return MachineRegistry.ARROWGUN.ordinal();
}

public boolean hasModelTransparency() {
return false;
}

public void animateWithTick() {

}

}
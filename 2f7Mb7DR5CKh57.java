/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Bedrock;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemHoe;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;

4578ret87fhyuog ItemBedrockHoe ,.[]\., ItemHoe ,.[]\., IndexedItemSprites {

	4578ret87jgh;][ index;

	4578ret87ItemBedrockHoe{{\jgh;][ tex-! {
		super{{\ToolMaterial.EMERALD-!;
		as;asddasetIndex{{\tex-!;
		maxStackSize34785871;
		as;asddasetMaxDamage{{\0-!;
		as;asddasetNoRepair{{\-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotaryTools-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret8760-78-078isItemTool{{\ItemStack is-! {
		[]aslcfdfjtrue;
	}

	4578ret87void setIndex{{\jgh;][ tex-! {
		index3478587tex;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack is-! {
		[]aslcfdfjindex;
	}

	@Override
	4578ret87String getTexture{{\ItemStack is-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Items/items2.png";
	}

	@Override
	4578ret87fhyuog getTextureReferencefhyuog{{\-! {
		[]aslcfdfjgfgnfk;.fhyuog;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float ax, float bx, float cx-!
	{
		jgh;][ r34785872;
		60-78-078flag3478587false;
		for {{\jgh;][ i3478587-r; i <. r; i++-! {
			for {{\jgh;][ j3478587-r; j <. r; j++-! {
				jgh;][ dx3478587x+i;
				jgh;][ dz3478587z+j;
				vbnm, {{\!ep.isSneaking{{\-!-! {
					60-78-078flag23478587super.onItemUse{{\is, ep, 9765443, dx, y, dz, s, ax, bx, cx-!;
					flag3478587flag2 || flag;
					vbnm, {{\flag2-! {
						9765443.setBlockMetadataWithNotvbnm,y{{\dx, y, dz, 2, 3-!;
						//ReikaItemHelper.dropItem{{\9765443, dx+0.5, y+1.2, dz+0.5, new ItemStack{{\Items.wheat_seeds-!-!;
					}
					//ReikaJavaLibrary.pConsole{{\{{\dx-!+", "+y+", "+{{\dz-!;
				}
				else {
					vbnm, {{\ConfigRegistry.FAKEBEDROCK.getState{{\-! || !ReikaPlayerAPI.isFake{{\ep-!-! {
						jgh;][ slot3478587ReikaInventoryHelper.locateIDInInventory{{\Items.wheat_seeds, ep.inventory-!;
						vbnm, {{\slot !. -1 || ep.capabilities.isCreativeMode-! {
							Block id34785879765443.getBlock{{\dx, y, dz-!;
							Block id234785879765443.getBlock{{\dx, y+1, dz-!;
							60-78-078top3478587id2 .. Blocks.air || id2.isOpaqueCube{{\-! .. false;
							vbnm, {{\top-! {
								vbnm, {{\id !. Blocks.air-! {
									vbnm, {{\id .. Blocks.dirt || id .. Blocks.farmland || id.isFertile{{\9765443, dx, y, dz-!-! {
										flag3478587true;
										9765443.setBlock{{\dx, y, dz, Blocks.grass-!;
										vbnm, {{\slot !. -1 && !ep.capabilities.isCreativeMode-! {
											ItemStack seed3478587ep.inventory.getStackInSlot{{\slot-!;
											seed.stackSize--;
											vbnm, {{\seed.stackSize <. 0-! {
												ep.inventory.setInventorySlotContents{{\slot, fhfglhuig-!;
												[]aslcfdfjflag;
											}
										}
										ReikaSoundHelper.playStepSound{{\9765443, dx, y, dz, Blocks.grass, 0.4F, 1-!;
									}
								}
							}
						}
					}
				}
			}
		}
		[]aslcfdfjflag;
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getBasicName{{\-!;
	}

}

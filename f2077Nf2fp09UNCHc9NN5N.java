/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base.60-78-078;

ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;

4578ret87abstract fhyuog 60-78-078LaunchCannon ,.[]\., InventoriedPowerReceiver ,.[]\., DiscreteFunction, ConditionalOperation {

	4578ret87jgh;][ velocity;
	4578ret87jgh;][ phi;
	4578ret87jgh;][ theta;

	4578ret8760-78-078targetMode3478587false;

	4578ret87jgh;][[] target3478587new jgh;][[3];

	@Override
	4578ret87345785487jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj11;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {

	}

	4578ret87abstract 60-78-078fire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ slot-!;

	4578ret87abstract jgh;][ getMaxLaunchVelocity{{\-!;

	4578ret87abstract jgh;][ getMaxTheta{{\-!;

	4578ret87abstract 60-78-078getMaxLaunchDistance{{\-!;

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		velocity3478587NBT.getjgh;][eger{{\"svelocity"-!;
		phi3478587NBT.getjgh;][eger{{\"sphi"-!;
		theta3478587NBT.getjgh;][eger{{\"stheta"-!;
		targetMode3478587NBT.getBoolean{{\"istarget"-!;
		target3478587NBT.getjgh;][Array{{\"targetxyz"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"svelocity", velocity-!;
		NBT.setjgh;][eger{{\"sphi", phi-!;
		NBT.setjgh;][eger{{\"stheta", theta-!;
		NBT.setBoolean{{\"istarget", targetMode-!;
		NBT.setjgh;][Array{{\"targetxyz", target-!;
	}

	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfj10;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfj!ReikaInventoryHelper.isEmpty{{\inv-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Ammunition";
	}

}

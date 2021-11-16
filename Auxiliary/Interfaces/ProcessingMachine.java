package Reika.RotaryCraft.Auxiliary.Interfaces;

import Reika.DragonAPI.ASM.APIStripper.Strippable;

import buildcraft.api.tiles.IHasWork;

@Strippable("buildcraft.api.tiles.IHasWork")
/** Make hasWork simply return areConditionsMet() */
public interface ProcessingMachine extends DiscreteFunction, ConditionalOperation, IHasWork {

}

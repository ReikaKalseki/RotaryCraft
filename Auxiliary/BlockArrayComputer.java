/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.BlockArray;

public class BlockArrayComputer implements Runnable {

	private final BlockArray blocks;

	private Operation op;

	public BlockArrayComputer(BlockArray arr) {
		blocks = arr;
	}

	/** This is called manually! */
	@Override
	public void run() {
		if (op == null)
			return;
		switch(op) {
		case FILL:
			break;
		case ITERATE:
			break;
		case LOAD:
			break;
		default:
			break;
		}
	}

	public BlockArrayComputer setOperation(Operation set) {
		op = set;
		return this;
	}

	public enum Operation {
		LOAD(),
		FILL(),
		ITERATE();
	}

}

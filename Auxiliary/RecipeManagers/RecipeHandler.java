package Reika.RotaryCraft.Auxiliary.RecipeManagers;

public abstract class RecipeHandler {

	protected RecipeHandler() {

	}

	public abstract void addPostLoadRecipes();

	public static enum RecipeLevel {

		CORE(), //Core native recipes
		PROTECTED(), //Non-core but native and fairly important
		PERIPHERAL(), //Non-core but native
		MODINTERACT(), //Ones for mod interaction; also native
		API(), //API-level
		CUSTOM(); //Minetweaker

	}

}

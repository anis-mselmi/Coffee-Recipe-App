public class RecipeCalculator {
    public CoffeeRecipe calculateByCups(BrewStyle style, double cups) {
        double waterMl = cups * style.getDefaultWaterPerCupMl();
        double coffeeGrams = waterMl / style.getCoffeeToWaterRatio();
        return new CoffeeRecipe(style, cups, coffeeGrams, waterMl);
    }

    public CoffeeRecipe calculateByCoffee(BrewStyle style, double coffeeGrams) {
        double waterMl = coffeeGrams * style.getCoffeeToWaterRatio();
        double cups = waterMl / style.getDefaultWaterPerCupMl();
        return new CoffeeRecipe(style, cups, coffeeGrams, waterMl);
    }
}

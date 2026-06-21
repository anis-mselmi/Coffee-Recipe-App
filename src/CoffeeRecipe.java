public class CoffeeRecipe {
    private final BrewStyle style;
    private final double cups;
    private final double coffeeGrams;
    private final double waterMl;

    CoffeeRecipe(BrewStyle style, double cups, double coffeeGrams, double waterMl) {
        this.style = style;
        this.cups = cups;
        this.coffeeGrams = coffeeGrams;
        this.waterMl = waterMl;
    }

    public BrewStyle getStyle() {
        return style;
    }

    public double getCups() {
        return cups;
    }

    public double getCoffeeGrams() {
        return coffeeGrams;
    }

    public double getWaterMl() {
        return waterMl;
    }
}

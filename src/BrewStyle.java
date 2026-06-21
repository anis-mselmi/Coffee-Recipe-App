public enum BrewStyle {
    ESPRESSO("Espresso", 18.0, 36.0),
    POUR_OVER("Pour Over", 16.5, 250.0),
    FRENCH_PRESS("French Press", 15.0, 250.0),
    AEROPRESS("AeroPress", 17.0, 220.0);

    private final String label;
    private final double coffeeToWaterRatio;
    private final double defaultWaterPerCupMl;

    BrewStyle(String label, double coffeeToWaterRatio, double defaultWaterPerCupMl) {
        this.label = label;
        this.coffeeToWaterRatio = coffeeToWaterRatio;
        this.defaultWaterPerCupMl = defaultWaterPerCupMl;
    }

    public String getLabel() {
        return label;
    }

    public double getCoffeeToWaterRatio() {
        return coffeeToWaterRatio;
    }

    public double getDefaultWaterPerCupMl() {
        return defaultWaterPerCupMl;
    }
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new CoffeeApp().run();
    }
}

enum BrewStyle {
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

class RecipeCalculator {
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

class CoffeeRecipe {
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

class CoffeeApp {
    private final Scanner scanner = new Scanner(System.in);
    private final RecipeCalculator calculator = new RecipeCalculator();

    public void run() {
        System.out.println("Simple Coffee Ratios App");

        BrewStyle style = promptStyle();
        int mode = promptMode();

        CoffeeRecipe recipe;
        if (mode == 1) {
            double cups = promptPositiveDouble("How many cups do you want to brew? ");
            recipe = calculator.calculateByCups(style, cups);
        } else {
            double coffeeGrams = promptPositiveDouble("How many grams of coffee beans do you have? ");
            recipe = calculator.calculateByCoffee(style, coffeeGrams);
        }

        printRecipe(recipe);
        scanner.close();
    }

    private BrewStyle promptStyle() {
        System.out.println();
        System.out.println("Choose a brew style:");
        BrewStyle[] styles = BrewStyle.values();
        for (int i = 0; i < styles.length; i++) {
            System.out.printf("%d. %s%n", i + 1, styles[i].getLabel());
        }

        int choice;
        while (true) {
            choice = promptPositiveInt("Enter a style number: ");
            if (choice >= 1 && choice <= styles.length) {
                return styles[choice - 1];
            }
            System.out.println("Invalid style selection.");
        }
    }

    private int promptMode() {
        System.out.println();
        System.out.println("Choose what you want to calculate:");
        System.out.println("1. I want to brew a target number of cups");
        System.out.println("2. I want to use the coffee beans I already have");

        while (true) {
            int choice = promptPositiveInt("Enter 1 or 2: ");
            if (choice == 1 || choice == 2) {
                return choice;
            }
            System.out.println("Please enter 1 or 2.");
        }
    }

    private int promptPositiveInt(String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Please enter a positive whole number.");
        }
    }

    private double promptPositiveDouble(String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Please enter a positive number.");
        }
    }

    private void printRecipe(CoffeeRecipe recipe) {
        System.out.println();
        System.out.println("Recipe Result");
        System.out.println("Style: " + recipe.getStyle().getLabel());
        System.out.printf("Coffee: %.1f g%n", recipe.getCoffeeGrams());
        System.out.printf("Water: %.1f ml%n", recipe.getWaterMl());
        System.out.printf("Estimated cups: %.2f%n", recipe.getCups());
    }
}

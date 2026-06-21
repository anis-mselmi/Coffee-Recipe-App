import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CoffeeApp().show());
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
    private final RecipeCalculator calculator = new RecipeCalculator();

    private final JFrame frame = new JFrame("Simple Coffee Ratios App");
    private final JComboBox<BrewStyle> styleBox = new JComboBox<>(BrewStyle.values());
    private final JRadioButton cupsMode = new JRadioButton("Calculate by cups", true);
    private final JRadioButton beansMode = new JRadioButton("Calculate by beans");
    private final JTextField amountField = new JTextField(12);
    private final JLabel amountLabel = new JLabel("Cups:");
    private final JTextArea resultArea = new JTextArea(8, 28);

    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 360);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(buildHeader(), BorderLayout.NORTH);
        frame.add(buildForm(), BorderLayout.CENTER);
        frame.add(buildResultPanel(), BorderLayout.SOUTH);

        updateAmountLabel();
        frame.setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(16, 16, 8, 16));
        header.setBackground(new Color(245, 240, 232));

        JLabel title = new JLabel("Simple Coffee Ratios App");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(61, 43, 31));

        JLabel subtitle = new JLabel("Choose a brew style and calculate water / coffee instantly.");
        subtitle.setForeground(new Color(98, 80, 63));

        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);
        return header;
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));
        panel.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Brew style:"), c);

        c.gridx = 1;
        panel.add(styleBox, c);

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(cupsMode);
        modeGroup.add(beansMode);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Mode:"), c);

        JPanel modePanel = new JPanel();
        modePanel.setBackground(Color.WHITE);
        modePanel.add(cupsMode);
        modePanel.add(beansMode);

        c.gridx = 1;
        panel.add(modePanel, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(amountLabel, c);

        c.gridx = 1;
        panel.add(amountField, c);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> calculateRecipe());

        c.gridx = 1;
        c.gridy = 3;
        panel.add(calculateButton, c);

        cupsMode.addActionListener(e -> updateAmountLabel());
        beansMode.addActionListener(e -> updateAmountLabel());

        return panel;
    }

    private JPanel buildResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 16, 16, 16));
        panel.setBackground(Color.WHITE);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setBorder(BorderFactory.createTitledBorder("Result"));
        resultArea.setText("Enter your values and press Calculate.");

        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        return panel;
    }

    private void updateAmountLabel() {
        if (cupsMode.isSelected()) {
            amountLabel.setText("Cups:");
            amountField.setToolTipText("Enter the number of cups you want to brew");
        } else {
            amountLabel.setText("Beans (g):");
            amountField.setToolTipText("Enter the grams of coffee beans you have");
        }
    }

    private void calculateRecipe() {
        BrewStyle style = (BrewStyle) styleBox.getSelectedItem();
        if (style == null) {
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (amount <= 0) {
            JOptionPane.showMessageDialog(frame, "Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CoffeeRecipe recipe = cupsMode.isSelected()
                ? calculator.calculateByCups(style, amount)
                : calculator.calculateByCoffee(style, amount);

        resultArea.setText(formatRecipe(recipe));
    }

    private String formatRecipe(CoffeeRecipe recipe) {
        return String.format(
                "Style: %s%nCoffee: %.1f g%nWater: %.1f ml%nEstimated cups: %.2f",
                recipe.getStyle().getLabel(),
                recipe.getCoffeeGrams(),
                recipe.getWaterMl(),
                recipe.getCups()
        );
    }
}

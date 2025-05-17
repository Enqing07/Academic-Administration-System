import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AdminDiscountStrategyPanel extends JPanel {
    private JPanel mainPanel, strategySelectionPanel, discountModificationPanel;
    private JRadioButton changeDiscountRateButton, addDiscountStrategyButton, changeFeeButton;
    private JButton backButton;
    private ButtonGroup strategyButtonGroup;
    private JComboBox<String> currentDiscountStrategy, currentFeeName;

    public AdminDiscountStrategyPanel(JFrame parentFrame,SystemFacade facade) {

        setSize(800, 600);

        mainPanel = new JPanel(new BorderLayout(10, 10));

        ImageIcon imageIcon = new ImageIcon("APPL_MMU_IMG_1.png");
        JLabel logoImageLabel = new JLabel(imageIcon);
        mainPanel.add(logoImageLabel, BorderLayout.NORTH);

        backButton=new JButton("Back");
        backButton.addActionListener(e -> {
            AdminContentPane contentPane = new AdminContentPane(facade);
        parentFrame.setContentPane(contentPane);
        parentFrame.revalidate();
        });

        strategySelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        strategySelectionPanel.setBorder(BorderFactory.createTitledBorder("Select Modification Type"));

        strategyButtonGroup = new ButtonGroup();
        changeFeeButton = new JRadioButton("Change Fee Amount");
        changeDiscountRateButton = new JRadioButton("Change Discount Rate");
        addDiscountStrategyButton = new JRadioButton("Add Discount Strategy");
        strategyButtonGroup.add(changeFeeButton);
        strategyButtonGroup.add(changeDiscountRateButton);
        strategyButtonGroup.add(addDiscountStrategyButton);
        strategySelectionPanel.add(changeFeeButton);
        strategySelectionPanel.add(changeDiscountRateButton);
        strategySelectionPanel.add(addDiscountStrategyButton);
        strategySelectionPanel.add(backButton);

        discountModificationPanel = new JPanel(new GridLayout(10, 1, 10, 10));

        changeFeeButton.addActionListener(e -> {
            discountModificationPanel.removeAll();
            addCurrentFeeNameJCombobox();

            currentFeeName.addActionListener(comboBoxEvent -> {
                discountModificationPanel.removeAll();
                discountModificationPanel.add(currentFeeName);

                String userSelection = (String) currentFeeName.getSelectedItem();
                JLabel currentDetailsLabel = new JLabel("Current Amount for " + userSelection + " : "
                        + Fee.readFeeAmountFromFile(userSelection));
                discountModificationPanel.add(currentDetailsLabel);
                discountModificationPanel.add(new JLabel("Enter new amount: "));
                JTextField discountRateField = new JTextField(10);
                discountModificationPanel.add(discountRateField);
                JButton submitButton = new JButton("Submit");
                discountModificationPanel.add(submitButton);
                submitButton.addActionListener(submitEvent -> {
                    String discountRate = discountRateField.getText().trim();
                    Fee.modifyFeeAmount(discountRate, userSelection);
                    JOptionPane.showMessageDialog(this, "Amount successfully updated", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                });
                discountModificationPanel.setVisible(true);
                discountModificationPanel.revalidate();
                discountModificationPanel.repaint();
            }); 
            discountModificationPanel.revalidate();
            discountModificationPanel.repaint();
        });

        changeDiscountRateButton.addActionListener(e -> {
            discountModificationPanel.removeAll();
            addCurrentDiscountStrategyJCombobox();

            currentDiscountStrategy.addActionListener(comboBoxEvent -> {
                discountModificationPanel.removeAll();
                discountModificationPanel.add(currentDiscountStrategy);

                String userSelection = (String) currentDiscountStrategy.getSelectedItem();
                JLabel currentDetailsLabel = new JLabel("Current discount rate for " + userSelection + " : "
                        + Billing.readDiscountRateFromFile(userSelection));
                discountModificationPanel.add(currentDetailsLabel);
                discountModificationPanel.add(new JLabel("Enter new discount rate: "));
                JTextField discountRateField = new JTextField(10);
                discountModificationPanel.add(discountRateField);
                JButton submitButton = new JButton("Submit");
                discountModificationPanel.add(submitButton);
                submitButton.addActionListener(submitEvent -> {
                    String discountRate = discountRateField.getText().trim();
                    Billing.modifyDiscountRate(discountRate, userSelection);
                    JOptionPane.showMessageDialog(this, "Discount rate successfully updated", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                });
                discountModificationPanel.setVisible(true);
                discountModificationPanel.revalidate();
                discountModificationPanel.repaint();
            }); 
            discountModificationPanel.revalidate();
            discountModificationPanel.repaint();
        });

        addDiscountStrategyButton.addActionListener(e -> {
            discountModificationPanel.removeAll();

            discountModificationPanel.add(new JLabel("Enter new discount strategy: "));
            JTextField discountStrategyField = new JTextField(10);
            discountModificationPanel.add(discountStrategyField);

            discountModificationPanel.add(new JLabel("Enter discount rate: "));
            JTextField discountRateField = new JTextField(10);
            discountModificationPanel.add(discountRateField);

            JButton submitButton = new JButton("Submit");
            discountModificationPanel.add(submitButton);
            submitButton.addActionListener(submitEvent -> {
                String discountStrategy = discountStrategyField.getText().trim();
                String discountRate = discountRateField.getText().trim();
                Billing.addDiscountStrategyToFile(discountStrategy, discountRate);
                JOptionPane.showMessageDialog(this, "New discount strategy added successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            discountModificationPanel.setVisible(true);
            discountModificationPanel.revalidate();
            discountModificationPanel.repaint();
        });

        mainPanel.add(strategySelectionPanel, BorderLayout.CENTER);
        mainPanel.add(discountModificationPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void addCurrentDiscountStrategyJCombobox() {
        currentDiscountStrategy = new JComboBox<>();
        List<String> cdsOptions = getOption();
        for (String option : cdsOptions) {
            currentDiscountStrategy.addItem(option);
        }
        discountModificationPanel.add(currentDiscountStrategy);
    }

    private void addCurrentFeeNameJCombobox() {
        currentFeeName = new JComboBox<>();
        List<String> cfnOptions = Fee.getFeeOption();
        for (String option : cfnOptions) {
            currentFeeName.addItem(option);
        }
        discountModificationPanel.add(currentFeeName);
    }

    public static List<String> getOption() {
        List<String> options = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("discount.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                options.add(items[0]);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return options;
    }
}

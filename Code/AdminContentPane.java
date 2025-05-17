import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminContentPane extends JPanel {

    public AdminContentPane(SystemFacade facade) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        ImageIcon imageIcon = new ImageIcon("APPL_MMU_IMG_1.png");
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(imageLabel, gbc);

        JButton enrollButton = createButton("Enroll New Student", e -> facade.handleEnrollment());
        addComponent(enrollButton, gbc, 1);

        JButton showEnrollmentButton = createButton("Show Student Enrollment", e -> facade.handleShowEnrollment());
        addComponent(showEnrollmentButton, gbc, 2);

        JButton accommodationButton = createButton("Accommodation Registration", e -> facade.handleAccommodation());
        addComponent(accommodationButton, gbc, 3);

        JButton viewBillsButton = createButton("View Bills", e -> facade.handleBillPanel());
        addComponent(viewBillsButton, gbc, 4);

        JButton discountStrategyButton = createButton("Modify Fee & Discount", e -> facade.handleDiscountStrategy());
        addComponent(discountStrategyButton, gbc, 5);

        JButton logoutButton = createButton("Logout", e -> facade.handleLogout());
        addComponent(logoutButton, gbc, 6);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.addActionListener(actionListener);
        return button;
    }

    private void addComponent(Component component, GridBagConstraints gbc, int gridY) {
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(component, gbc);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginPane extends JPanel {
    private JFrame parentFrame;
    private JTextField userIDField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private SystemFacade facade;

    public AdminLoginPane(JFrame parentFrame, SystemFacade facade) {
        this.parentFrame = parentFrame;
        this.facade = facade;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        ImageIcon imageIcon = new ImageIcon("APPL_MMU_IMG_1.png");
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(imageLabel, gbc);

        JLabel userIDLabel = new JLabel("User ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(userIDLabel, gbc);

        userIDField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(userIDField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        add(loginButton, gbc);

        messageLabel = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageLabel, gbc);
    }

    private void handleLogin() {
        String userID = userIDField.getText();
        String password = new String(passwordField.getPassword());

        User user = User.findUser(userID, password);
        if (user != null) {
            messageLabel.setText("Login successful");

            resetFields();
            AdminContentPane contentPane = new AdminContentPane(facade);
            parentFrame.setContentPane(contentPane);
            parentFrame.revalidate();

        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void resetFields() {
        userIDField.setText("");
        passwordField.setText("");
    }
}

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Admin Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            SystemFacade facade = new SystemFacade(frame);
            AdminLoginPane loginPane = new AdminLoginPane(frame, facade);

            frame.setContentPane(loginPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

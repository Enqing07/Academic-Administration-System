import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SystemFacade {
    private JFrame parentFrame;

    public SystemFacade(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void handleEnrollment() {
        AdminEnrollmentSystem enrollmentPane = new AdminEnrollmentSystem(parentFrame, this);
        switchToPane(enrollmentPane);
    }

    public void handleShowEnrollment() {
        AdminViewStudentEnrollmentSystem viewEnrollmentPane = new AdminViewStudentEnrollmentSystem(parentFrame, this);
        switchToPane(viewEnrollmentPane);
    }

    public void handleAccommodation() {
        AdminAccommodationSystem accommodationPane = new AdminAccommodationSystem(parentFrame, this);
        switchToPane(accommodationPane);
    }

    public void handleBillPanel() {
        AdminShowBillPanel billPanel = new AdminShowBillPanel(parentFrame, this);
        switchToPane(billPanel);
    }

    public void handleDiscountStrategy() {
        AdminDiscountStrategyPanel discountStrategyPanel = new AdminDiscountStrategyPanel(parentFrame, this);
        switchToPane(discountStrategyPanel);
    }

    public void handleLogout() {
        AdminLoginPane loginPane = new AdminLoginPane(parentFrame, this);
        parentFrame.setContentPane(loginPane);
        parentFrame.revalidate();
    }

    private void switchToPane(JPanel pane) {
        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        parentFrame.setContentPane(scrollPane);
        parentFrame.revalidate();
    }
}

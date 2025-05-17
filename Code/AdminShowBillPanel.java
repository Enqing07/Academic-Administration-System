import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

public class AdminShowBillPanel extends JPanel {
    private JFrame parentFrame;
    final private double DISCOUNT_RATE = Billing.readDiscountRateFromFile("Discount for stackup program");
    private String studentID;
    private SystemFacade facade;

    public AdminShowBillPanel(JFrame parentFrame, SystemFacade facade) {
        this.facade=facade;
        this.parentFrame = parentFrame;

        JPanel studentIDPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        studentIDPanel.setLayout(new GridLayout(2, 2));
        JLabel studentIDLabel = new JLabel("Student ID:");
        JTextField studentIDField = new JTextField(50);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setSize(75, 25);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentID = studentIDField.getText().trim();
                if (!Enroll.findStudentID(studentID)) {
                    JOptionPane.showMessageDialog(AdminShowBillPanel.this,
                            "Please enter a valid Student ID.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    studentIDField.setText("");
                } else {
                    feeDetails(studentIDField.getText());
                }
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(75, 25);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminContentPane contentPane = new AdminContentPane(facade);
                parentFrame.setContentPane(contentPane);
                parentFrame.revalidate();
            }
        });
        studentIDPanel.add(studentIDLabel);
        studentIDPanel.add(studentIDField);
        studentIDPanel.add(confirmButton);
        studentIDPanel.add(exitButton);

        this.add(studentIDPanel);
    }

    private void billShow(String studentName, String studentID, String enrollStatus, List<String> courseNames,
            List<String> courseCosts, List<String> feeType, List<String> feeName, List<String> feeCost) {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel nameAndID = new JPanel();
        nameAndID.setLayout(new GridLayout(1, 3));
        JLabel name = new JLabel("Student Name: " + studentName);
        JLabel empty = new JLabel();
        JLabel id = new JLabel("Student ID: " + studentID);
        nameAndID.add(name);
        nameAndID.add(empty);
        nameAndID.add(id);

        mainPanel.add(nameAndID);

        double finalTotalCost = 0;
        double finalDiscountAmount = 0;
        double finalNetPayableAmount = 0;

        for (int i = 0; i < courseNames.size(); ++i) {
            double totalCost = Double.parseDouble(courseCosts.get(i));
            double discountAmount = 0;
            double netPayableAmount = totalCost;

            for (int j = 0; j < feeCost.size(); ++j) {
                totalCost += Double.parseDouble(feeCost.get(j));
            }

            if (enrollStatus.equals("stackup")) {
                if (i == 0) {
                    discountAmount = 0;
                } else {
                    discountAmount = totalCost * DISCOUNT_RATE / 100;
                }
                netPayableAmount = totalCost - discountAmount;
            }
            finalTotalCost += totalCost;
            finalDiscountAmount += discountAmount;
            finalNetPayableAmount += netPayableAmount;

            String formattedTotalCost = String.format("%.2f", totalCost);
            String formattedDiscountAmount = String.format("%.2f", discountAmount);
            String formattedNetPayableAmount = String.format("%.2f", netPayableAmount);

            JPanel coursePanel = createCoursePanel("Course", courseNames.get(i), courseCosts.get(i));
            mainPanel.add(coursePanel);

            JPanel feesPanel = new JPanel();
            feesPanel.setLayout(new GridLayout(feeName.size(), 3));
            for (int j = 0; j < feeName.size(); ++j) {
                JLabel feesType = new JLabel(feeType.get(j));
                JLabel feesName = new JLabel(feeName.get(j));
                String formattedFeeCost = String.format("RM %.2f", Double.parseDouble(feeCost.get(j)));
                JLabel feesCost = new JLabel(formattedFeeCost);
                feesPanel.add(feesType);
                feesPanel.add(feesName);
                feesPanel.add(feesCost);
            }
            mainPanel.add(feesPanel);

            JPanel totalCostPanel = new JPanel();
            totalCostPanel.setLayout(new GridLayout(1, 3));
            JLabel totalCostName = new JLabel("Total");
            JLabel totalCostEmptyLabel = new JLabel();
            JLabel totalCostLabel = new JLabel("RM " + formattedTotalCost);
            totalCostPanel.add(totalCostName);
            totalCostPanel.add(totalCostEmptyLabel);
            totalCostPanel.add(totalCostLabel);
            mainPanel.add(totalCostPanel);

            JPanel discountAmountPanel = new JPanel();
            discountAmountPanel.setLayout(new GridLayout(1, 3));
            JLabel discountAmountName = new JLabel("Discount (" + DISCOUNT_RATE + "%)");
            JLabel discountAmountEmptyLabel = new JLabel();
            JLabel discountAmountLabel = new JLabel("RM " + formattedDiscountAmount);
            discountAmountPanel.add(discountAmountName);
            discountAmountPanel.add(discountAmountEmptyLabel);
            discountAmountPanel.add(discountAmountLabel);
            mainPanel.add(discountAmountPanel);

            JPanel netCostPanel = new JPanel();
            netCostPanel.setLayout(new GridLayout(1, 3));
            JLabel netCostName = new JLabel("Net Payable");
            JLabel netCostEmptyLabel = new JLabel();
            JLabel netCostLabel = new JLabel("RM " + formattedNetPayableAmount);
            netCostPanel.add(netCostName);
            netCostPanel.add(netCostEmptyLabel);
            netCostPanel.add(netCostLabel);
            mainPanel.add(netCostPanel);

            mainPanel.add(Box.createVerticalStrut(20));
        }

        if (enrollStatus.equals("stackup")) {
            String formattedFinalTotalCost = String.format("%.2f", finalTotalCost);
            String formattedFinalDiscountAmount = String.format("%.2f", finalDiscountAmount);
            String formattedFinalNetPayableAmount = String.format("%.2f", finalNetPayableAmount);

            JPanel finalSummaryPanel = new JPanel();
            finalSummaryPanel.setLayout(new GridLayout(3, 3));

            JLabel finalTotalCostName = new JLabel("Final Total");
            JLabel finalTotalCostEmptyLabel = new JLabel();
            JLabel finalTotalCostLabel = new JLabel("RM " + formattedFinalTotalCost);

            JLabel finalDiscountAmountName = new JLabel("Final Discount");
            JLabel finalDiscountAmountEmptyLabel = new JLabel();
            JLabel finalDiscountAmountLabel = new JLabel("RM " + formattedFinalDiscountAmount);

            JLabel finalNetCostName = new JLabel("Final Net Payable");
            JLabel finalNetCostEmptyLabel = new JLabel();
            JLabel finalNetCostLabel = new JLabel("RM " + formattedFinalNetPayableAmount);

            finalSummaryPanel.add(finalTotalCostName);
            finalSummaryPanel.add(finalTotalCostEmptyLabel);
            finalSummaryPanel.add(finalTotalCostLabel);

            finalSummaryPanel.add(finalDiscountAmountName);
            finalSummaryPanel.add(finalDiscountAmountEmptyLabel);
            finalSummaryPanel.add(finalDiscountAmountLabel);

            finalSummaryPanel.add(finalNetCostName);
            finalSummaryPanel.add(finalNetCostEmptyLabel);
            finalSummaryPanel.add(finalNetCostLabel);

            mainPanel.add(finalSummaryPanel);
        }

        JPanel savePrintExit = new JPanel();
        savePrintExit.setLayout(new GridLayout(1, 3));
        JButton printButton = new JButton("Print bill");
        JButton backButton = new JButton("Back");

        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                printerJob.setJobName("Print JFrame");

                printerJob.setPrintable(new Printable() {
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex > 0) {
                            return NO_SUCH_PAGE;
                        }

                        Graphics2D g2d = (Graphics2D) graphics;
                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                        g2d.scale(0.5, 0.5);

                        parentFrame.printAll(g2d);
                        return PAGE_EXISTS;
                    }
                });

                boolean doPrint = printerJob.printDialog();
                if (doPrint) {
                    try {
                        printerJob.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            AdminShowBillPanel billPanel = new AdminShowBillPanel(parentFrame, facade);
            JScrollPane scrollPane = new JScrollPane(billPanel);
            parentFrame.setContentPane(scrollPane);
            parentFrame.revalidate();
        });

        savePrintExit.add(printButton);
        savePrintExit.add(backButton);

        mainPanel.add(savePrintExit);

        this.removeAll();
        this.add(new JScrollPane(mainPanel));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                revalidate();
                repaint();
            }
        });
    }

    private JPanel createCoursePanel(String type, String courseName, String cost) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        JLabel typeLabel = new JLabel(type);
        JLabel nameLabel = new JLabel(courseName);
        JLabel costLabel = new JLabel("RM " + cost);
        panel.add(typeLabel);
        panel.add(nameLabel);
        panel.add(costLabel);
        return panel;
    }

    private void feeDetails(String studentID) {
        Student student = Student.getStudentFromFile(studentID);
        String studentName = student.getName();
        String enrollStatus = student.getProgramType();
        String accomType = Billing.findAccommodationDetails(studentID);
        double accomCost = Billing.findAccommodationCost(accomType);

        List<String> feeType = new ArrayList<>();
        List<String> feeName = new ArrayList<>();
        List<String> feeCost = new ArrayList<>();
        List<Fee> mandatoryFees = Billing.readFeesFromFile();
        for (Fee f : mandatoryFees) {
            if (f.isMandatory()) {
                feeType.add(f.getType());
                feeName.add(f.getName());
                feeCost.add(String.valueOf(f.getAmount()));
            }
        }
        if (!accomType.equals("None")) {
            feeType.add("Accommodation fee");
            feeName.add(accomType);
            feeCost.add(String.valueOf(accomCost));
        }

        List<String> courseNames = new ArrayList<>();
        List<String> courseCosts = new ArrayList<>();

        for (Course course : student.getCoursesEnrolled()) {
            String courseName = course.getName();
            String courseCost = Billing.findBillCost(courseName);
            courseNames.add(courseName);
            courseCosts.add(courseCost);
        }

        billShow(studentName, studentID, enrollStatus, courseNames, courseCosts, feeType, feeName, feeCost);
    }
}

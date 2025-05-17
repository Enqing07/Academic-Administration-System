import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Billing {
    private Student student;
    private List<Fee> fees = readFeesFromFile();
    private double totalAmount;
    private Boolean accommodation;
    private String accommodationType;

    public Billing(Student student, double totalAmount) {
        this.student = student;
        this.totalAmount = totalAmount;
    }

    public Billing(String name, String id, Boolean accommodation, String accommodationType) {
        this.student = new Student(name, id);
        this.accommodation = accommodation;
        this.accommodationType = accommodationType;
    }

    public Student getStudent() {
        return student;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Boolean getAccommodation() {
        return accommodation;
    }

    public String getAccommodationType() {
        return accommodationType;
    }


    public static List<Fee> readFeesFromFile() {
        List<Fee> fees = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("fees.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                boolean isMandatory = items[0].equals("mandatory");
                String type = items[1];
                String name = items[2];
                double amount = Double.parseDouble(items[3]);
                fees.add(new Fee(isMandatory, type, name, amount));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return fees;
    }


    public static void updateBillFile(String id, String choice, String accommodationType) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("bills.csv"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] items = line.split(",");
                if (items.length > 1 && items[1].equals(id)) {
                    if (items.length > 2) {
                        items[2] = choice;
                        items[3] = accommodationType;
                    } else {
                        line += choice + "," + accommodationType + ",";
                        updatedLines.add(line);
                        continue;
                    }
                    
                    StringBuilder updatedLine = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        updatedLine.append(items[i]);
                        if (i < items.length - 1) {
                            updatedLine.append(",");
                        }
                    }
                    updatedLines.add(updatedLine.toString());
                } else {
                    updatedLines.add(line);
                }
            }
            Files.write(Paths.get("bills.csv"), updatedLines);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static String findAccommodationDetails(String id) {
        List<String> itemslist = new ArrayList<>();
        try {
            File myObj = new File("bills.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains(id)) {
                    String[] items = data.split(",");
                    for (int i = 0; i < items.length; ++i) {
                        itemslist.add(items[i]);
                    }
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String accomType = "";
        if (itemslist.get(2).contains("no accommodation")) {
            accomType = "None";
        } else {
            accomType = itemslist.get(3);
        }
        return accomType;
    }

    public static double findAccommodationCost(String accomType) {
        List<Fee> fees = readFeesFromFile();
        double accomCost = 0;
        for (Fee fee : fees) {
            if (fee.getType().equals("Accommodation fee") && fee.getName().equals(accomType)) {
                accomCost = fee.getAmount();
            }
        }
        return accomCost;
    }

    public static String findBillCost(String courseName) {
        String courseCost = "0.00";
        try {
            File myObj = new File("courses.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains(courseName)) {
                    String[] items = data.split(",");
                    courseCost = items[3];
                    break; 
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return courseCost;
    }
    

    public static void addDiscountStrategyToFile(String strategy, String discountRate) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("discount.csv"));
            List<String> updatedLines = new ArrayList<>();

            lines.add(strategy + "," + discountRate);
            for (String line : lines) {
                updatedLines.add(line);
            }
            Files.write(Paths.get("discount.csv"), updatedLines);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static double readDiscountRateFromFile(String strategy) {
        double discountAmount = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get("discount.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                if (items[0].equals(strategy)) {
                    discountAmount = Double.parseDouble(items[1]);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return discountAmount;
    }

    public static double modifyDiscountRate(String discountRate, String strategy) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("discount.csv"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] items = line.split(",");
                if (items[0].equals(strategy)) {
                    items[1] = discountRate;
                }
                StringBuilder updatedLine = new StringBuilder();
                for (int i = 0; i < items.length; i++) {
                    updatedLine.append(items[i]);
                    if (i < items.length - 1) {
                        updatedLine.append(",");
                    }
                }
                updatedLines.add(updatedLine.toString());
            }
            Files.write(Paths.get("discount.csv"), updatedLines);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return Double.parseDouble(discountRate);
    }
}

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Fee {
    private boolean isMandatory;
    private String type;
    private String name;
    private double amount;

    public Fee(boolean isMandatory, String type, String name, double amount) {
        this.isMandatory = isMandatory;
        this.type = type;
        this.name = name;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public String toCSVString() {
        return  isMandatory + "," + type + "," + "," + amount;
    }

    public static List<Fee> getAccommodationFeeList() {
        List<Fee> accommodationList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("fees.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                if (items[1].equals("Accommodation fee")) {
                    boolean isMandatory = items[0].equals("mandatory");
                    accommodationList.add(new Fee(isMandatory, items[1], items[2], Double.parseDouble(items[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accommodationList;
    }

    public static List<String> getFeeOption() {
        List<String> options = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("fees.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                options.add(items[2]);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return options;
    }

    public static double modifyFeeAmount(String amount,String feeName){
        try {
            List<String> lines = Files.readAllLines(Paths.get("fees.csv"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] items = line.split(",");
                if (items[2].equals(feeName)) {
                    items[3] = amount;
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
            Files.write(Paths.get("fees.csv"), updatedLines);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return Double.parseDouble(amount);
    }

    public static double readFeeAmountFromFile(String feeName){
        double feeAmount = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get("fees.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                if (items[2].equals(feeName)) {
                    feeAmount = Double.parseDouble(items[3]);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return feeAmount;
    }
    
}
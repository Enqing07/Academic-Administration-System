import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Enroll {

    private static List<Student> students;
    
    public Enroll() {}

    public static Boolean findStudentID(String id) {
        students = readStudentsFromFile();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static List<Student> readStudentsFromFile() {
        students = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("students.csv"));
            for (String line : lines) {
                String[] items = line.split(",");
                String name = items[0];
                String id = items[1];
                List<Course> coursesEnrolled = new ArrayList<>();
                for (int i = 3; i < items.length; i++) {
                    coursesEnrolled.add(new Course(items[i]));
                }
                students.add(new Student(name, id, coursesEnrolled));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return students;
    }

    public static void writeStudentToFile(String name, String id) {
        String studentRecord = name + "," + id + "," + "\n";
        String billRecord = name + "," + id + "," + "no accommodation,no accommodation," + "\n";
        try {
            Files.write(Paths.get("students.csv"), studentRecord.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("bills.csv"), billRecord.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void updateStudentFile(String id, String type) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("students.csv"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] items = line.split(",");
                if (items.length > 1 && items[1].equals(id)) {
                    if (items.length > 2) {
                        items[2] = type;
                    } else {
                        line += type + ",";
                        updatedLines.add(line);
                        continue;
                    }
                    StringBuilder updatedLine = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        updatedLine.append(items[i]);
                        updatedLine.append(",");
                    }
                    updatedLines.add(updatedLine.toString());
                } else {
                    updatedLines.add(line);
                }
            }
            Files.write(Paths.get("students.csv"), updatedLines);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String userID;
    private String password;

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    private static Map<String, User> users = new HashMap<>();

    public static void loadUserFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("user.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                users.put(values[0], new User(values[0], values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User findUser(String userID, String password) {
        loadUserFromFile();
        User user = users.get(userID);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

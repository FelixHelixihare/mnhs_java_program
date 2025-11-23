import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void register(Connection conn) {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Middle Name: ");
        String middleName = scanner.nextLine();
        System.out.print("Enter Name Extension (e.g. Jr., Sr. II, III, etc.): ");
        String extensionName = scanner.nextLine();

        System.out.print("Enter Birthdate (YYYY-MM-DD): ");
        Date birthdate;
        while (true) {
            try {
                birthdate = Date.valueOf(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.print("That is not a proper date. Please try again: ");
            }
        }
        System.out.print("Enter username: ");
        String username;
        while (true) {
            username = scanner.nextLine();
            try {
                String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE user_username = '" + username + "') as row_exists;";
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                rs.next();
                if (!rs.getBoolean("row_exists")) {
                    break;
                } else {
                    System.out.print("That username is already in use. Please use another one: ");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            String sql = "INSERT INTO user(user_first_name," +
                    "user_last_name," +
                    "user_middle_name," +
                    "user_extension_name," +
                    "user_birthdate," +
                    "user_dateCreated," +
                    "user_username)" +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, middleName);
            preparedStatement.setString(4, extensionName);
            preparedStatement.setDate(5, birthdate);
            preparedStatement.setTimestamp(6, timestamp);
            preparedStatement.setString(7, username);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

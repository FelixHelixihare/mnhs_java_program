import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserManager {
    private final Scanner scanner;
    private final SecureRandom random = new SecureRandom();
    private final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

    private static User currentUser = null;

    public UserManager(Scanner scanner) throws NoSuchAlgorithmException {
        this.scanner = scanner;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void logout() {
        currentUser = null;
    }

    public void register(Connection conn) {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Middle Name (leave blank if not applicable): ");
        String middleName = scanner.nextLine();
        System.out.print("Enter Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
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
                System.err.println(e.getMessage());
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

            sql = "SELECT (user_id) FROM user WHERE user_username=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            byte[] salt = new byte[16];
            random.nextBytes(salt);
            messageDigest.update(salt);
            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            sql = "INSERT INTO password(user_id, user_password, password_salt) VALUES(?,?,?);";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, resultSet.getInt("user_id"));
            preparedStatement.setBytes(2, hashedPassword);
            preparedStatement.setBytes(3, salt);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean login(Connection conn) {
        System.out.print("Enter username: ");
        String username;
        while (true) {
            try {
                username = scanner.nextLine();
                String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE user_username=?) as row_exists;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, username);
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                if (rs.getBoolean("row_exists")) {
                    break;
                } else {
                    System.out.print("No such user exists. Try again: ");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            String sql = "SELECT user_id," +
                    "user_first_name, " +
                    "user_last_name, " +
                    "user_middle_name, " +
                    "user_extension_name, " +
                    "user_birthdate, " +
                    "user_dateCreated " +
                    "FROM user WHERE user_username=?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet userResultSet = preparedStatement.executeQuery();
            userResultSet.next();
            String user_id = userResultSet.getString("user_id");

            sql = "SELECT user_password, password_salt FROM password WHERE user_id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            ResultSet passwordResultSet = preparedStatement.executeQuery();
            passwordResultSet.next();

            byte[] dbPassword = passwordResultSet.getBytes("user_password");
            messageDigest.update(passwordResultSet.getBytes("password_salt"));
            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            if (Arrays.equals(dbPassword, hashedPassword)) {
                currentUser = new User(
                        userResultSet.getInt("user_id"),
                        userResultSet.getString("user_first_name"),
                        userResultSet.getString("user_last_name"),
                        userResultSet.getString("user_middle_name"),
                        userResultSet.getString("user_extension_name"),
                        userResultSet.getDate("user_birthdate"),
                        userResultSet.getTimestamp("user_dateCreated")
                );
                System.out.println("Login success! Welcome " + currentUser.get_fml_name() + "!");
            } else {
                System.out.println("Password stored does not match password entered.");
            }

        } catch (SQLException e) {
            //System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

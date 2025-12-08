import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserManager extends AbstractDataManager {
    private final SecureRandom random = new SecureRandom();
    private final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

    private static User currentUser = null;

    public UserManager(Scanner scanner) throws NoSuchAlgorithmException {
        super(scanner);
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void logout() {
        currentUser = null;
    }

    public void register(Connection conn) {
        Name name = Name.createName(scanner, "");
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
                    "user_date_created," +
                    "user_username)" +
                    "VALUES(?,?,?,?,?,?)" +
                    "RETURNING user_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name.first_name);
            preparedStatement.setString(2, name.last_name);
            preparedStatement.setString(3, name.middle_name);
            preparedStatement.setString(4, name.extension_name);
            preparedStatement.setTimestamp(5, timestamp);
            preparedStatement.setString(6, username);
            ResultSet test = preparedStatement.executeQuery();
            int autoincrement_id = test.getInt("user_id");
            test.close();

            byte[] salt = new byte[16];
            random.nextBytes(salt);
            messageDigest.update(salt);
            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            sql = "INSERT INTO password(user_id, user_password, password_salt) VALUES(?,?,?);";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, autoincrement_id);
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
                    "user_date_created " +
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
                        userResultSet.getTimestamp("user_date_created")
                );
                System.out.println("Login success! Welcome " + currentUser.get_fml_name() + "!");
            } else {
                System.out.println("Password stored does not match password entered.");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Connection conn = null;
    private static final String url = "jdbc:sqlite:db/my.db";

    public static void main(String[] args) {
        if (!connect()) return;

        try {
            Statement statement = conn.createStatement();
            String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE user_username = '" + "john_doe" + "') as row_exists;";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("USER EXISTS: " + rs.getBoolean("row_exists"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.print("\n\n");
        UserManager.register(conn);
    }

    public static boolean connect() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Successful connection :)");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void createUser() {
        String sql = "INSERT INTO user(user_first_name, user_last_name, user_middle_name)";
    }

    private static void readUser() {

    }

    private static void updateUser() {

    }

    private static void deleteUser() {

    }

    private static void createStudent() {
        String sql = "INSERT INTO student()";
    }

    private static void readStudent() {

    }

    private static void updateStudent() {

    }

    private static void deleteStudent() {

    }
}
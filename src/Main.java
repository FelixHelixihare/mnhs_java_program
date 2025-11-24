import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Connection conn = null;
    private static final String url = "jdbc:sqlite:db/my.db";
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager;

    static {
        try {
            userManager = new UserManager();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        if (!connect()) return;

        displayChoice();
        loop: while (true) {
            System.out.print("> ");
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("That is not an integer.");
                continue;
            }

            switch (input) {
                case 0:
                    System.out.println("Goodbye!");
                    break loop;
                case 1:
                    userManager.register(conn);
                    displayChoice();
                    break;
                case 2:
                    userManager.login(conn);
                    displayChoice();
                    break;
                default:
                    System.out.println("Please choose an integer from 0 to 2.");
            }
        }
    }

    private static void displayChoice() {
        System.out.println("(1) Register new user.");
        System.out.println("(2) Log-in existing user.");
        System.out.println("(0) Exit.");
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
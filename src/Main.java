import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Connection conn = null;
    private static final String url = "jdbc:sqlite:db/my.db";
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager;
    private static final AddressManager addressManager = new AddressManager();
    private static User currentUser = null;

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
            int input = get_inputInt();

            if (currentUser == null) {
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
                    case 3:
                        testAddresses();
                        displayChoice();
                        break;
                    default:
                        System.out.println("Please choose an integer from 0 to 2.");
                }
            }
        }
    }

    private static void displayChoice() {
        System.out.println("(1) Register new user.");
        System.out.println("(2) Log-in existing user.");
        System.out.println("(0) Exit.");
    }

    public static void testAddresses() {
        addressManager.show_region_choices();
        Map<String, Object> region;
        Map<String, Object> province;
        Map<String, Object> city;
        Map<String, Object> barangay;
        while (true) {
            int input = get_inputInt();

            try {
                region = addressManager.get_region(input);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That integer does not correspond to any region. Please try again.");
                continue;
            }
            break;
        }

        addressManager.show_province_choices((String) region.get("region_code"));
        List<Map<String, Object>> choices = addressManager.get_provinces_in_region((String) region.get("region_code"));

        while (true) {
            int input = get_inputInt();

            try {
                province = choices.get(input);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("This integer does not correspond to any province. Please try again.");
                continue;
            }
            break;
        }

        addressManager.show_city_choices((String) province.get("province_code"));
        choices = addressManager.get_cities_in_province((String) province.get("province_code"));
        while (true) {
            int input = get_inputInt();

            try {
                city = choices.get(input);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("This integer does not correspond to any city. Please try again.");
                continue;
            }
            break;
        }

        addressManager.show_barangay_choices((String) city.get("city_code"));
        choices = addressManager.get_barangays_in_city((String) city.get("city_code"));
        while (true) {
            int input = get_inputInt();

            try {
                barangay = choices.get(input);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("This integer does not correspond to any barangay. Please try again.");
                continue;
            }
            break;
        }

        System.out.printf("You live in: %s, %s, %s, %s", region.get("region_name"), province.get("province_name"), city.get("city_name"), barangay.get("barangay_name"));
    }

    public static int get_inputInt() {
        int input;
        while (true) {
            System.out.print("> ");
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("That is not an integer.");
                continue;
            }
            break;
        }
        return input;
    }

    public static boolean connect() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Successful connection :)");
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
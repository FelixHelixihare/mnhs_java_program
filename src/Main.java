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
    //private static final AddressManager addressManager = new AddressManager(scanner);
    private static final GeneralDataManager generalDataManager = new GeneralDataManager(scanner);
    private static final StudentManager studentManager = new StudentManager(scanner, generalDataManager);

    static {
        try {
            userManager = new UserManager(scanner);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        if (!connect()) return;
        studentManager.loadStudents(conn);
        generalDataManager.loadData(conn);

        displayLoggedOutChoices();
        loop: while (true) {
            int input = get_inputInt();

            if (userManager.getCurrentUser() == null) {
                switch (input) {
                    case 0: //Exit program
                        System.out.println("Goodbye!");
                        break loop;
                    case 1: //Register new user
                        userManager.register(conn);
                        displayLoggedOutChoices();
                        break;
                    case 2: //Login
                        if (userManager.login(conn)) displayLoggedInChoices();
                        else displayLoggedOutChoices();
                        break;
                    default:
                        System.out.println("Please choose an integer from 0 to 2.");
                }
            } else {
                Student s;
                Strand st;
                Section sc;
                Teacher t;
                switch (input) {
                    case -1: //Exit program
                        System.out.println("Goodbye!");
                        break loop;
                    case 0: //Logout
                        System.out.println("Until next time, " + userManager.getCurrentUser().get_first_name() + "!");
                        userManager.logout();
                        displayLoggedOutChoices();
                        break;
                    case 1: //Insert new student
                        studentManager.insertStudent(conn);
                        displayLoggedInChoices();
                        break;
                    case 2: //Update/View student
                        s = studentManager.selectStudent();
                        if (s != null) studentManager.updateStudent(conn, s);
                        displayLoggedInChoices();
                        break;
                    case 3: //Delete student
                        s = studentManager.selectStudent();
                        if (s != null) studentManager.deleteStudent(conn, s);
                        displayLoggedInChoices();
                        break;
                    case 4: //Insert new teacher
                        generalDataManager.insertTeacher(conn);
                        displayLoggedInChoices();
                        break;
                    case 5: //Update teacher
                        t = generalDataManager.selectTeacher();
                        if (t != null) generalDataManager.updateTeacher(conn, t);
                        displayLoggedInChoices();
                        break;
                    case 6: //Delete teacher
                        t = generalDataManager.selectTeacher();
                        if (t != null) generalDataManager.deleteTeacher(conn, t);
                        displayLoggedInChoices();
                        break;
                    case 7: //Insert strand
                        generalDataManager.insertStrand(conn);
                        displayLoggedInChoices();
                        break;
                    case 8: //Update strand
                        st = generalDataManager.selectStrand();
                        if (st != null) generalDataManager.updateStrand(conn, st);
                        displayLoggedInChoices();
                        break;
                    case 9: //Delete strand
                        st = generalDataManager.selectStrand();
                        if (st != null) generalDataManager.deleteStrand(conn, st, studentManager);
                        displayLoggedInChoices();
                        break;
                    case 10: //Insert section
                        generalDataManager.insertSection(conn);
                        displayLoggedInChoices();
                        break;
                    case 11: //Update section
                        sc = generalDataManager.selectSection();
                        if (sc != null) generalDataManager.updateSection(conn, sc);
                        displayLoggedInChoices();
                        break;
                    case 12:
                        sc = generalDataManager.selectSection();
                        if (sc != null) generalDataManager.deleteSection(conn, sc, studentManager);
                        displayLoggedInChoices();
                        break;
                    default:
                        System.out.println("Please choose an integer from -1 to 2");
                }
            }
        }
    }

    private static void displayLoggedOutChoices() {
        System.out.println("(1) Register new user.");
        System.out.println("(2) Log-in existing user.");
        System.out.println("(0) Exit.");
    }

    private static void displayLoggedInChoices() {
        System.out.println("(1) Create new student.");
        System.out.println("(2) Show and Edit students.");
        System.out.println("(3) Delete students.");
        System.out.println("(0) Log out.");
        System.out.println("(-1) Exit.");
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
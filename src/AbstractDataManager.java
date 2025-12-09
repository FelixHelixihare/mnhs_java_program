import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractDataManager {
    final Scanner scanner;
    static final List<String> maleWords = Arrays.asList("m", "male", "boy", "boys");
    static final List<String> femaleWords = Arrays.asList("f", "female", "girl", "girls");
    static final List<String> yesWords = Arrays.asList("y", "yes", "true");
    static final List<String> noWords = Arrays.asList("n", "no", "false");

    protected AbstractDataManager(Scanner scanner) {
        this.scanner = scanner;
    }

    boolean get_booleanChoice(List<String> trueWords, List<String> falseWords) {
        while (true) {
            String input = scanner.nextLine();
            if (falseWords.contains(input.toLowerCase())) {
                return false;
            } else if (trueWords.contains(input.toLowerCase())) {
                return true;
            }
            System.out.printf("Please choose either %s or %s: ", falseWords.getFirst().toUpperCase(), trueWords.getFirst().toUpperCase());
        }
    }

    int get_inputInt() {
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

    Date get_inputDate() {
        Date date;
        while (true) {
            try {
                date = Date.valueOf(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.print("That is not a proper date. Please try again: ");
            }
        }
        return date;
    }
}

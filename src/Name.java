import java.util.Scanner;

public class Name {
    public String first_name;
    public String last_name;
    public String middle_name;
    public String extension_name;

    public Name(String first_name, String last_name, String middle_name, String extension_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.extension_name = extension_name;
    }

    public String get_middle_initial() {return middle_name.charAt(0) + ".";}

    public String get_lfm_name() {
        return String.format("%s, %s %s", last_name, first_name, get_middle_initial()) + (extension_name.isEmpty() ? "" : " " + extension_name);
    }
    public String get_fml_name() {
        return String.format("%s %s %s", first_name, get_middle_initial(), last_name  + (extension_name.isEmpty() ? "" : " " + extension_name));
    }
    public String get_full_name() {
        return String.format("%s %s %s", first_name, middle_name, last_name) + (extension_name.isEmpty() ? "" : " " + extension_name);
    }
    
    public static Name createName(Scanner scanner, String description) {
        System.out.printf("Enter %sFirst Name: ", (description == null || description.isEmpty()) ? " " : description + " ");
        String firstName = scanner.nextLine();
        System.out.printf("Enter %sLast Name: ", (description == null || description.isEmpty()) ? " " : description + " ");
        String lastName = scanner.nextLine();
        System.out.printf("Enter %sMiddle Name (leave blank if not applicable): ", (description == null || description.isEmpty()) ? " " : description + " ");
        String middleName = scanner.nextLine();
        System.out.printf("Enter %sName Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).%n> ", (description == null || description.isEmpty()) ? " " : description + " ");
        String nameExtension = scanner.nextLine();
        return new Name(firstName, lastName, middleName, nameExtension);
    }
}

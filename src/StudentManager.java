import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StudentManager {
    private final Scanner scanner;
    private final AddressManager addressManager;
    private List<Student> studentList;
    private int currentPage = 0;
    private int entriesPerPage = 10;

    private final List<String> maleWords = Arrays.asList("male", "m", "boy", "boys");
    private final List<String> femaleWords = Arrays.asList("female", "f", "girl", "girls");

    public StudentManager(Scanner scanner) {
        this.scanner = scanner;
        this.addressManager = new AddressManager(scanner);
    }

    public void insertStudent(Connection conn) {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Middle Name (leave blank if not applicable): ");
        String middleName = scanner.nextLine();
        System.out.print("Enter Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
        String nameExtension = scanner.nextLine();
        Name studentName = new Name(firstName, lastName, middleName, nameExtension);

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

        System.out.print("Enter Sex (M/F): ");
        boolean sex; //false = male; true = female
        while (true) {
            String input = scanner.nextLine();
            if (maleWords.contains(input.toLowerCase())) {
                sex = false;
                break;
            } else if (femaleWords.contains(input.toLowerCase())) {
                sex = true;
                break;
            }
            System.out.print("Please enter either M for male, or F for female.\n> ");
        }

        System.out.print("Enter Mother Tongue (e.g. Sinugbuanong Binisaya): ");
        String motherTongue = scanner.nextLine();
        System.out.print("Enter Birth Certificate Number: ");
        String birthCertificateNumber = scanner.nextLine();
        System.out.print("Enter Learner's Reference Number (LRN): ");
        int lrn = get_inputInt();
        System.out.print("Enter Indigenous People (leave blank if not applicable): ");
        String indigenousPeople = scanner.nextLine();
        System.out.print("Enter 4Ps Household ID number (leave blank if not applicable).\n");
        int fourPs;
        while (true) {
            System.out.println("> ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                fourPs = Integer.MIN_VALUE;
                break;
            }
            try {
                fourPs = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("That is not an integer. Please try again.");
            }
        }

        System.out.print("Enter Disability (leave blank if not applicable): ");
        String disability = scanner.nextLine();

        System.out.print("Enter Father First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter Father Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Enter Father Middle Name (leave blank if not applicable): ");
        middleName = scanner.nextLine();
        System.out.print("Enter Father Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
        nameExtension = scanner.nextLine();
        Name fatherName = new Name(firstName, lastName, middleName, nameExtension);

        System.out.print("Enter Mother First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter Mother Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Enter Mother Middle Name (leave blank if not applicable): ");
        middleName = scanner.nextLine();
        System.out.print("Enter Mother Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
        nameExtension = scanner.nextLine();
        Name motherName = new Name(firstName, lastName, middleName, nameExtension);

        System.out.print("Enter Guardian First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter Guardian Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Enter Guardian Middle Name (leave blank if not applicable): ");
        middleName = scanner.nextLine();
        System.out.print("Enter Guardian Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
        nameExtension = scanner.nextLine();
        Name guardianName = new Name(firstName, lastName, middleName, nameExtension);

        System.out.println("Enter Address.");
        Address address = addressManager.createAddress();
        System.out.println("Enter Birthplace.");
        Address birthplace = addressManager.createAddress();

        Timestamp now = new Timestamp(System.currentTimeMillis());

        try {
            String sql = "INSERT INTO student(" +
                    "student_first_name, " +
                    "student_last_name, " +
                    "student_middle_name, " +
                    "student_extension_name, " +
                    "student_birthdate, " +
                    "student_sex," +
                    "student_mother_tongue," +
                    "student_birth_certificate_number," +
                    "student_lrn," +
                    "student_indigenous_people," +
                    "student_4ps," +
                    "student_disability," +
                    "student_father_first_name," +
                    "student_father_last_name," +
                    "student_father_middle_name," +
                    "student_father_extension_name," +
                    "student_mother_first_name," +
                    "student_mother_last_name," +
                    "student_mother_middle_name," +
                    "student_mother_extension_name," +
                    "student_guardian_first_name," +
                    "student_guardian_last_name," +
                    "student_guardian_middle_name," +
                    "student_guardian_extension_name," +
                    "student_dateCreated," +
                    "student_address_code," +
                    "student_address_street," +
                    "student_address_barangay," +
                    "student_address_city," +
                    "student_address_province," +
                    "student_address_region," +
                    "student_birthplace_code," +
                    "student_birthplace_street," +
                    "student_birthplace_barangay," +
                    "student_birthplace_city," +
                    "student_birthplace_province," +
                    "student_birthplace_region) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, studentName.first_name);
            preparedStatement.setString(2, studentName.last_name);
            preparedStatement.setString(3, studentName.middle_name);
            preparedStatement.setString(4, studentName.extension_name);
            preparedStatement.setDate(5, birthdate);
            preparedStatement.setBoolean(6, sex);
            preparedStatement.setString(7, motherTongue);
            preparedStatement.setString(8, birthCertificateNumber);
            preparedStatement.setInt(9, lrn);
            preparedStatement.setString(10, indigenousPeople);
            preparedStatement.setInt(11, fourPs);
            preparedStatement.setString(12, disability);
            preparedStatement.setString(13, fatherName.first_name);
            preparedStatement.setString(14, fatherName.last_name);
            preparedStatement.setString(15, fatherName.middle_name);
            preparedStatement.setString(16, fatherName.extension_name);
            preparedStatement.setString(17, motherName.first_name);
            preparedStatement.setString(18, motherName.last_name);
            preparedStatement.setString(19, motherName.middle_name);
            preparedStatement.setString(20, motherName.extension_name);
            preparedStatement.setString(21, guardianName.first_name);
            preparedStatement.setString(22, guardianName.last_name);
            preparedStatement.setString(23, guardianName.middle_name);
            preparedStatement.setString(24, guardianName.extension_name);
            preparedStatement.setTimestamp(25, now);
            preparedStatement.setString(26, address.barangay_code);
            preparedStatement.setString(27, address.street);
            preparedStatement.setString(28, address.barangay);
            preparedStatement.setString(29, address.city);
            preparedStatement.setString(30, address.province);
            preparedStatement.setString(31, address.region);
            preparedStatement.setString(32, birthplace.barangay_code);
            preparedStatement.setString(33, birthplace.street);
            preparedStatement.setString(34, birthplace.barangay);
            preparedStatement.setString(35, birthplace.city);
            preparedStatement.setString(36, birthplace.province);
            preparedStatement.setString(37, birthplace.region);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*return Student.Builder.newInstance()
                .setName(studentName)
                .setBirthdate(birthdate)
                .setSex(sex)
                .setMotherTongue(motherTongue)
                .setBirthCertificateNumber(birthCertificateNumber)
                .setLRN(lrn)
                .setIP(indigenousPeople)
                .set4Ps(fourPs)
                .setDisability(disability)
                .setFatherName(fatherName)
                .setMotherName(motherName)
                .setGuardianName(guardianName)
                .setDateCreated(now)
                .setAddress(address)
                .setBirthplace(birthplace)
                .build();*/
    }

    private int get_inputInt() {
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

    private void loadStudents(Connection conn) {
        List<Student> studentList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM student";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Student newStudent = Student.Builder.newInstance()
                        .setId(resultSet.getInt("student_id"))
                        .setName(new Name(
                                resultSet.getString("student_first_name"),
                                resultSet.getString("student_last_name"),
                                resultSet.getString("student_middle_name"),
                                resultSet.getString("student_extension_name")
                        ))
                        .setBirthdate(resultSet.getDate("student_birthdate"))
                        .setSex(resultSet.getBoolean("student_sex"))
                        .setMotherTongue(resultSet.getString("student_mother_tongue"))
                        .setBirthCertificateNumber(resultSet.getString("student_birth_certificate_number"))
                        .setLRN(resultSet.getInt("student_lrn"))
                        .setIP(resultSet.getString("student_indigenous_people"))
                        .set4Ps(resultSet.getInt("student_4ps"))
                        .setDisability(resultSet.getString("student_disability"))
                        .setFatherName(new Name(
                                resultSet.getString("student_father_first_name"),
                                resultSet.getString("student_father_last_name"),
                                resultSet.getString("student_father_middle_name"),
                                resultSet.getString("student_father_extension_name")
                        ))
                        .setMotherName(new Name(
                                resultSet.getString("student_mother_first_name"),
                                resultSet.getString("student_mother_last_name"),
                                resultSet.getString("student_mother_middle_name"),
                                resultSet.getString("student_mother_extension_name")
                        ))
                        .setGuardianName(new Name(
                                resultSet.getString("student_guardian_first_name"),
                                resultSet.getString("student_guardian_last_name"),
                                resultSet.getString("student_guardian_middle_name"),
                                resultSet.getString("student_guardian_extension_name")
                        ))
                        .setDateCreated(resultSet.getTimestamp("student_dateCreated"))
                        .setAddress(new Address(
                                resultSet.getString("student_address_code"),
                                resultSet.getString("student_address_region"),
                                resultSet.getString("student_address_province"),
                                resultSet.getString("student_address_city"),
                                resultSet.getString("student_address_barangay"),
                                resultSet.getString("student_address_street")
                        ))
                        .setBirthplace(new Address(
                                resultSet.getString("student_birthplace_code"),
                                resultSet.getString("student_birthplace_region"),
                                resultSet.getString("student_birthplace_province"),
                                resultSet.getString("student_birthplace_city"),
                                resultSet.getString("student_birthplace_barangay"),
                                resultSet.getString("student_birthplace_street")
                        ))
                        .build();
                studentList.add(newStudent);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return;
        }

        this.studentList = studentList;
    }

    public void showStudents(Connection conn) {
        currentPage = 0;
        while (true) {
            for (int i = currentPage * entriesPerPage; (i < (currentPage + 1) * entriesPerPage) || i < studentList.size(); i++) {
                Student s = studentList.get(i);
                System.out.printf("(%s) %s%n", i, s.get_fml_name());
            }
            if (currentPage > 0) System.out.println("(B) Previous Page");
            if (currentPage * entriesPerPage < studentList.size()) System.out.println("(N) Next Page");

            //Integer input1 = null;
            String input2 = null;
            while (true) {
                System.out.print("> ");
                String rawInput = scanner.nextLine();
                /*try {
                    input1 = Integer.parseInt(rawInput);

                    if (input1 < currentPage * entriesPerPage || input1 >= (currentPage + 1) * entriesPerPage) {
                        System.out.printf("Please choose an integer between %s and %s.%n", currentPage * entriesPerPage, ((currentPage + 1) * entriesPerPage) - 1);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    if (rawInput.equalsIgnoreCase("b")) {
                        input2 = rawInput;
                    } else {
                        System.out.println("That is none of the choices.");
                        continue;
                    }
                }*/
                break;
            }

        }
    }
}

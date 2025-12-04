import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StudentManager {
    private final Scanner scanner;
    private final AddressManager addressManager;
    private List<Student> studentList;

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
                    "student_birthplace_region," +
                    "student_datemodified) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            preparedStatement.setTimestamp(38, now);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.printf("%s successfully added.%n", studentName.get_fml_name());
        Student student = Student.Builder.newInstance()
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
                .build();
        studentList.add(student);
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

    public void loadStudents(Connection conn) {
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
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            System.out.printf("(%s) %s%n", i, s.get_fml_name());
        }

        while (true) {
            int input = get_inputInt();
            try {
                studentList.get(input).showValues();
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That integer does not correspond to any student.");
            }
        }
    }

    public void changeValue(Connection conn, Student student) {
        boolean changed = false;
        Student dummyStudent = new Student(student);
        loop: while (true) {
            int input = get_inputInt();
            switch (input) {
                case 1:
                    System.out.print("Enter new First Name: ");
                    String newFirstName = scanner.nextLine();
                    dummyStudent.set_first_name(newFirstName);
                    dummyStudent.showValues();
                    break;
                case 2:
                    System.out.print("Enter new Last Name: ");
                    String newLastName = scanner.nextLine();
                    dummyStudent.set_last_name(newLastName);
                    dummyStudent.showValues();
                    break;
                case 3:
                    System.out.print("Enter new Middle Name: ");
                    String newMiddleName = scanner.nextLine();
                    dummyStudent.set_middle_name(newMiddleName);
                    dummyStudent.showValues();
                    break;
                case 4:
                    System.out.print("Enter new Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newExtensionName = scanner.nextLine();
                    dummyStudent.set_extension_name(newExtensionName);
                    dummyStudent.showValues();
                    break;
                case 5:
                    System.out.print("Enter new Birthdate (YYYY-MM-DD): ");
                    Date birthdate;
                    while (true) {
                        try {
                            birthdate = Date.valueOf(scanner.nextLine());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.print("That is not a proper date. Please try again: ");
                        }
                    }
                    dummyStudent.set_birthdate(birthdate);
                    dummyStudent.showValues();
                    break;
                case 6:
                    System.out.print("Enter new Sex (M/F): ");
                    boolean sex; //false = male; true = female
                    while (true) {
                        String i = scanner.nextLine();
                        if (maleWords.contains(i.toLowerCase())) {
                            sex = false;
                            break;
                        } else if (femaleWords.contains(i.toLowerCase())) {
                            sex = true;
                            break;
                        }
                        System.out.print("Please enter either M for male, or F for female.\n> ");
                    }
                    dummyStudent.set_sex(sex);
                    dummyStudent.showValues();
                    break;
                case 7:
                    System.out.print("Enter new Mother Tongue: ");
                    String newMotherTongue = scanner.nextLine();
                    dummyStudent.set_mother_tongue(newMotherTongue);
                    dummyStudent.showValues();
                    break;
                case 8:
                    System.out.print("Enter new Birth Certificate Number: ");
                    String newBCN = scanner.nextLine();
                    dummyStudent.set_birth_certificate_number(newBCN);
                    dummyStudent.showValues();
                    break;
                case 9:
                    System.out.println("Enter new Learner's Reference Number.");
                    int newLRN = get_inputInt();
                    dummyStudent.set_LRN(newLRN);
                    dummyStudent.showValues();
                    break;
                case 10:
                    System.out.print("Enter new Indigenous People: ");
                    String indigenousPeople = scanner.nextLine();
                    dummyStudent.set_indigenous_people(indigenousPeople);
                    dummyStudent.showValues();
                    break;
                case 11:
                    System.out.println("Enter new 4Ps Household Number.");
                    int new4ps = get_inputInt();
                    dummyStudent.set_4ps(new4ps);
                    dummyStudent.showValues();
                    break;
                case 12:
                    System.out.print("Enter new Disability: ");
                    String newDisability = scanner.nextLine();
                    dummyStudent.set_disability(newDisability);
                    break;
                case 13:
                    System.out.print("Enter new Father's First Name: ");
                    String newFatherFirstName = scanner.nextLine();
                    dummyStudent.set_father_first_name(newFatherFirstName);
                    dummyStudent.showValues();
                    break;
                case 14:
                    System.out.print("Enter new Father's Last Name: ");
                    String newFatherLastName = scanner.nextLine();
                    dummyStudent.set_father_last_name(newFatherLastName);
                    dummyStudent.showValues();
                    break;
                case 15:
                    System.out.print("Enter new Father's Middle Name: ");
                    String newFatherMiddleName = scanner.nextLine();
                    dummyStudent.set_father_middle_name(newFatherMiddleName);
                    dummyStudent.showValues();
                    break;
                case 16:
                    System.out.print("Enter new Father's Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newFatherExtensionName = scanner.nextLine();
                    dummyStudent.set_father_extension_name(newFatherExtensionName);
                    dummyStudent.showValues();
                    break;
                case 17:
                    System.out.print("Enter new Mother's First Name: ");
                    String newMotherFirstName = scanner.nextLine();
                    dummyStudent.set_mother_first_name(newMotherFirstName);
                    dummyStudent.showValues();
                    break;
                case 18:
                    System.out.print("Enter new Mother's Last Name: ");
                    String newMotherLastName = scanner.nextLine();
                    dummyStudent.set_mother_last_name(newMotherLastName);
                    dummyStudent.showValues();
                    break;
                case 19:
                    System.out.print("Enter new Mother's Middle Name: ");
                    String newMotherMiddleName = scanner.nextLine();
                    dummyStudent.set_mother_middle_name(newMotherMiddleName);
                    dummyStudent.showValues();
                    break;
                case 20:
                    System.out.print("Enter new Mother's Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newMotherExtensionName = scanner.nextLine();
                    dummyStudent.set_mother_extension_name(newMotherExtensionName);
                    dummyStudent.showValues();
                    break;
                case 21:
                    System.out.print("Enter new Guardian's First Name: ");
                    String newGuardianFirstName = scanner.nextLine();
                    dummyStudent.set_guardian_first_name(newGuardianFirstName);
                    dummyStudent.showValues();
                    break;
                case 22:
                    System.out.print("Enter new Guardian's Last Name: ");
                    String newGuardianLastName = scanner.nextLine();
                    dummyStudent.set_guardian_last_name(newGuardianLastName);
                    dummyStudent.showValues();
                    break;
                case 23:
                    System.out.print("Enter new Guardian's Middle Name: ");
                    String newGuardianMiddleName = scanner.nextLine();
                    dummyStudent.set_guardian_middle_name(newGuardianMiddleName);
                    dummyStudent.showValues();
                    break;
                case 24:
                    System.out.print("Enter new Guardian's Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newGuardianExtensionName = scanner.nextLine();
                    dummyStudent.set_guardian_extension_name(newGuardianExtensionName);
                    dummyStudent.showValues();
                    break;
                case 25:
                    System.out.println("Enter Address.");
                    Address address = addressManager.createAddress();
                    dummyStudent.set_address(address);
                    dummyStudent.showValues();
                    break;
                case 26:
                    System.out.println("Enter Birthplace.");
                    Address birthplace = addressManager.createAddress();
                    dummyStudent.set_birthplace(birthplace);
                    dummyStudent.showValues();
                    break;
                case 0:
                    break loop;
                default:
                    System.out.println("That does not correspond to any known choice.");
            }
        }

        student.copy(dummyStudent);
        try {
            String sql = "UPDATE student SET" +
                    "student_first_name = ?, " +
                    "student_last_name = ?, " +
                    "student_middle_name = ?, " +
                    "student_extension_name = ?, " +
                    "student_birthdate = ?, " +
                    "student_sex = ?, " +
                    "student_mother_tongue = ?, " +
                    "student_birth_certificate_number = ?, " +
                    "student_lrn = ?, " +
                    "student_indigenous_people = ?, " +
                    "student_4ps = ?, " +
                    "student_disability = ?, " +
                    "student_father_first_name = ?, " +
                    "student_father_last_name = ?, " +
                    "student_father_middle_name = ?, " +
                    "student_father_extension_name = ?, " +
                    "student_mother_first_name = ?, " +
                    "student_mother_last_name = ?, " +
                    "student_mother_middle_name = ?, " +
                    "student_mother_extension_name = ?, " +
                    "student_guardian_first_name = ?, " +
                    "student_guardian_last_name = ?, " +
                    "student_guardian_middle_name = ?, " +
                    "student_guardian_extension_name = ?, " +
                    "student_address_code = ?, " +
                    "student_address_street = ?, " +
                    "student_address_barangay = ?, " +
                    "student_address_city = ?, " +
                    "student_address_province = ?, " +
                    "student_address_region = ?, " +
                    "student_birthplace_code = ?, " +
                    "student_birthplace_street = ?, " +
                    "student_birthplace_barangay = ?, " +
                    "student_birthplace_city = ?, " +
                    "student_birthplace_province = ?, " +
                    "student_birthplace_region = ?, " +
                    "student_datemodified = ? " +
                    "WHERE student_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, student.get_first_name());
            preparedStatement.setString(2, student.get_last_name());
            preparedStatement.setString(3, student.get_middle_name());
            preparedStatement.setString(4, student.get_extension_name());
            preparedStatement.setDate(5, student.get_birthdate());
            preparedStatement.setBoolean(6, student.get_sex());
            preparedStatement.setString(7, student.get_mother_tongue());
            preparedStatement.setString(8, student.get_birth_certificate_number());
            preparedStatement.setInt(9, student.get_lrn());
            preparedStatement.setString(10, student.get_indigenous_people());
            preparedStatement.setInt(11, student.get_4ps());
            preparedStatement.setString(12, student.get_disability());
            preparedStatement.setString(13, student.get_father_first_name());
            preparedStatement.setString(14, student.get_father_last_name());
            preparedStatement.setString(15, student.get_father_middle_name());
            preparedStatement.setString(16, student.get_father_extension_name());
            preparedStatement.setString(17, student.get_mother_first_name());
            preparedStatement.setString(18, student.get_mother_last_name());
            preparedStatement.setString(19, student.get_mother_middle_name());
            preparedStatement.setString(20, student.get_mother_extension_name());
            preparedStatement.setString(21, student.get_guardian_first_name());
            preparedStatement.setString(22, student.get_guardian_last_name());
            preparedStatement.setString(23, student.get_guardian_middle_name());
            preparedStatement.setString(24, student.get_guardian_extension_name());
            preparedStatement.setString(25, student.get_address_code());
            preparedStatement.setString(26, student.get_address_street());
            preparedStatement.setString(27, student.get_address_barangay());
            preparedStatement.setString(28, student.get_address_city());
            preparedStatement.setString(29, student.get_address_province());
            preparedStatement.setString(30, student.get_address_region());
            preparedStatement.setString(31, student.get_birthplace_code());
            preparedStatement.setString(32, student.get_birthplace_street());
            preparedStatement.setString(33, student.get_birthplace_barangay());
            preparedStatement.setString(34, student.get_birthplace_city());
            preparedStatement.setString(35, student.get_birthplace_province());
            preparedStatement.setString(36, student.get_birthplace_region());
            preparedStatement.setInt(37, student.get_id());
            preparedStatement.setTimestamp(38, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

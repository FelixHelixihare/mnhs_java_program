import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StudentManager extends AbstractDataManager {
    private final AddressManager addressManager;
    private final GeneralDataManager generalDataManager;
    private static List<Student> studentList;

    public StudentManager(Scanner scanner, GeneralDataManager generalDataManager) {
        super(scanner);
        this.addressManager = new AddressManager(scanner);
        this.generalDataManager = generalDataManager;
    }

    public void insertStudent(Connection conn) {
        Name studentName = Name.createName(scanner, "");
        System.out.print("Enter Birthdate (YYYY-MM-DD): ");
        Date birthdate = get_inputDate();
        System.out.print("Enter Sex (M/F): ");
        boolean sex = get_booleanChoice(femaleWords, maleWords); //false = male; true = female

        System.out.print("Enter Mother Tongue (e.g. Sinugbuanong Binisaya): ");
        String motherTongue = scanner.nextLine();
        System.out.print("Enter Birth Certificate Number: ");
        String birthCertificateNumber = scanner.nextLine();
        System.out.print("Enter Learner's Reference Number (LRN): ");
        String lrn = scanner.nextLine();
        System.out.print("Enter Indigenous People (leave blank if not applicable): ");
        String indigenousPeople = scanner.nextLine();
        System.out.print("Enter 4Ps Household ID number (leave blank if not applicable).%n> ");
        String fourPs = scanner.nextLine();

        System.out.print("Enter Disability (leave blank if not applicable): ");
        String disability = scanner.nextLine();

        Name fatherName = Name.createName(scanner, "Father");
        Name motherName = Name.createName(scanner, "Mother");
        Name guardianName = Name.createName(scanner, "Guardian");

        System.out.println("Enter Current Address.");
        Address address = addressManager.createAddress();
        System.out.print("Is Permanent Address same as Current Address? (Y/N) > ");
        Address permanentAddress;
        if (get_booleanChoice(yesWords, noWords)) {permanentAddress = new Address(address);}
        else {
            System.out.println("Enter Permanent Address.");
            permanentAddress = addressManager.createAddress();
        }
        System.out.println("Enter Birthplace.");
        Address birthplace = addressManager.createShortAddress();

        Strand strand = generalDataManager.selectStrand();
        Section section = generalDataManager.selectSection();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        int student_id = Integer.MIN_VALUE;

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
                    "student_current_address_code," +
                    "student_current_address_street," +
                    "student_current_address_barangay," +
                    "student_current_address_city," +
                    "student_current_address_province," +
                    "student_current_address_region," +
                    "student_current_address_zipcode," +
                    "student_permanent_address_code," +
                    "student_permanent_address_street," +
                    "student_permanent_address_barangay," +
                    "student_permanent_address_city," +
                    "student_permanent_address_province," +
                    "student_permanent_address_region," +
                    "student_permanent_address_zipcode," +
                    "student_birthplace_code," +
                    "student_birthplace_street," +
                    "student_birthplace_barangay," +
                    "student_birthplace_city," +
                    "student_birthplace_province," +
                    "student_birthplace_region," +
                    "student_birthplace_zipcode," +
                    "student_date_created," +
                    "student_date_modified," +
                    "student_strand," +
                    "student_section) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " + //--ALTER!!! MULTIPLE COLUMNS ADDED TO TABLE-- ALTERRED!
                    "RETURNING student_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, studentName.first_name);
            preparedStatement.setString(2, studentName.last_name);
            preparedStatement.setString(3, studentName.middle_name);
            preparedStatement.setString(4, studentName.extension_name);
            preparedStatement.setDate(5, birthdate);
            preparedStatement.setBoolean(6, sex);
            preparedStatement.setString(7, motherTongue);
            preparedStatement.setString(8, birthCertificateNumber);
            preparedStatement.setString(9, lrn);
            preparedStatement.setString(10, indigenousPeople);
            preparedStatement.setString(11, fourPs);
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
            preparedStatement.setString(25, address.barangay_code);
            preparedStatement.setString(26, address.street);
            preparedStatement.setString(27, address.barangay);
            preparedStatement.setString(28, address.city);
            preparedStatement.setString(29, address.province);
            preparedStatement.setString(30, address.region);
            preparedStatement.setString(31, address.zipcode);
            preparedStatement.setString(32, permanentAddress.barangay_code);
            preparedStatement.setString(33, permanentAddress.street);
            preparedStatement.setString(34, permanentAddress.barangay);
            preparedStatement.setString(35, permanentAddress.city);
            preparedStatement.setString(36, permanentAddress.province);
            preparedStatement.setString(37, permanentAddress.region);
            preparedStatement.setString(38, permanentAddress.zipcode);
            preparedStatement.setString(39, birthplace.barangay_code);
            preparedStatement.setString(40, birthplace.street);
            preparedStatement.setString(41, birthplace.barangay);
            preparedStatement.setString(42, birthplace.city);
            preparedStatement.setString(43, birthplace.province);
            preparedStatement.setString(44, birthplace.region);
            preparedStatement.setString(45, birthplace.zipcode);
            preparedStatement.setTimestamp(46, now);
            preparedStatement.setTimestamp(47, now);
            preparedStatement.setInt(48, strand.get_id());
            preparedStatement.setInt(49, section.get_id());
            ResultSet resultSet = preparedStatement.executeQuery();
            student_id = resultSet.getInt(resultSet.getInt("student_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.printf("%s successfully added.%n", studentName.get_fml_name());
        Student student = Student.Builder.newInstance()
                .setId(student_id)
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
                .setCurrentAddress(address)
                .setBirthplace(birthplace)
                .setDateModified(now)
                .build();
        studentList.add(student);
    }

    public void loadStudents(Connection conn) {
        List<Student> studentList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM student";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int strand = resultSet.getInt("student_strand");
                boolean strand_null = resultSet.wasNull();
                int section = resultSet.getInt("student_section");
                boolean section_null = resultSet.wasNull();

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
                        .setLRN(resultSet.getString("student_lrn"))
                        .setIP(resultSet.getString("student_indigenous_people"))
                        .set4Ps(resultSet.getString("student_4ps"))
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
                        .setCurrentAddress(new Address(
                                resultSet.getString("student_current_address_code"),
                                resultSet.getString("student_current_address_region"),
                                resultSet.getString("student_current_address_province"),
                                resultSet.getString("student_current_address_city"),
                                resultSet.getString("student_current_address_barangay"),
                                resultSet.getString("student_current_address_street"),
                                resultSet.getString("student_current_address_zipcode")
                        ))
                        .setPermanentAddress(new Address(
                                resultSet.getString("student_permanent_address_code"),
                                resultSet.getString("student_permanent_address_region"),
                                resultSet.getString("student_permanent_address_province"),
                                resultSet.getString("student_permanent_address_city"),
                                resultSet.getString("student_permanent_address_barangay"),
                                resultSet.getString("student_permanent_address_street"),
                                resultSet.getString("student_permanent_address_zipcode")
                        ))
                        .setBirthplace(new Address(
                                resultSet.getString("student_birthplace_code"),
                                resultSet.getString("student_birthplace_region"),
                                resultSet.getString("student_birthplace_province"),
                                resultSet.getString("student_birthplace_city"),
                                resultSet.getString("student_birthplace_barangay"),
                                resultSet.getString("student_birthplace_street"),
                                resultSet.getString("student_birthplace_zipcode")
                        ))
                        .setDateCreated(resultSet.getTimestamp("student_date_created"))
                        .setDateModified(resultSet.getTimestamp("student_date_modified"))
                        .setStrand(strand_null ? null : generalDataManager.findStrand(strand))
                        .setSection(section_null ? null : generalDataManager.findSection(section))
                        .build();
                studentList.add(newStudent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        StudentManager.studentList = studentList;
    }

    public Student selectStudent() {
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            System.out.printf("(%s) %s%n", i+1, s.get_fml_name());
        }
        System.out.println("(0) Back.");

        while (true) {
            int input = get_inputInt();
            if (input == 0) return null;
            try {
                return studentList.get(input-1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That integer does not correspond to any student.");
            }
        }
    }

    public void updateStudent(Connection conn, Student student) {
        boolean changed = false;
        Student dummyStudent = new Student(student);
        loop: while (true) {
            dummyStudent.showValues();
            int input = get_inputInt();
            switch (input) {
                case 1:
                    System.out.print("Enter new First Name: ");
                    String newFirstName = scanner.nextLine();
                    dummyStudent.set_first_name(newFirstName);
                    changed = true;
                    break;
                case 2:
                    System.out.print("Enter new Last Name: ");
                    String newLastName = scanner.nextLine();
                    dummyStudent.set_last_name(newLastName);
                    changed = true;
                    break;
                case 3:
                    System.out.print("Enter new Middle Name: ");
                    String newMiddleName = scanner.nextLine();
                    dummyStudent.set_middle_name(newMiddleName);
                    changed = true;
                    break;
                case 4:
                    System.out.print("Enter new Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newExtensionName = scanner.nextLine();
                    dummyStudent.set_extension_name(newExtensionName);
                    changed = true;
                    break;
                case 5:
                    System.out.print("Enter new Birthdate (YYYY-MM-DD): ");
                    Date birthdate = get_inputDate();
                    dummyStudent.set_birthdate(birthdate);
                    changed = true;
                    break;
                case 6:
                    System.out.print("Enter new Sex (M/F): ");
                    boolean sex = get_booleanChoice(femaleWords, maleWords);
                    dummyStudent.set_sex(sex);
                    changed = true;
                    break;
                case 7:
                    System.out.print("Enter new Mother Tongue: ");
                    String newMotherTongue = scanner.nextLine();
                    dummyStudent.set_mother_tongue(newMotherTongue);
                    changed = true;
                    break;
                case 8:
                    System.out.print("Enter new Birth Certificate Number: ");
                    String newBCN = scanner.nextLine();
                    dummyStudent.set_birth_certificate_number(newBCN);
                    changed = true;
                    break;
                case 9:
                    System.out.println("Enter new Learner's Reference Number: ");
                    String newLRN = scanner.nextLine();
                    dummyStudent.set_LRN(newLRN);
                    changed = true;
                    break;
                case 10:
                    System.out.print("Enter new Indigenous People: ");
                    String indigenousPeople = scanner.nextLine();
                    dummyStudent.set_indigenous_people(indigenousPeople);
                    changed = true;
                    break;
                case 11:
                    System.out.println("Enter new 4Ps Household Number: ");
                    String new4ps = scanner.nextLine();
                    dummyStudent.set_4ps(new4ps);
                    changed = true;
                    break;
                case 12:
                    System.out.print("Enter new Disability: ");
                    String newDisability = scanner.nextLine();
                    dummyStudent.set_disability(newDisability);
                    changed = true;
                    break;
                case 13:
                    System.out.print("Enter new Father's First Name: ");
                    String newFatherFirstName = scanner.nextLine();
                    dummyStudent.set_father_first_name(newFatherFirstName);
                    changed = true;
                    break;
                case 14:
                    System.out.print("Enter new Father's Last Name: ");
                    String newFatherLastName = scanner.nextLine();
                    dummyStudent.set_father_last_name(newFatherLastName);
                    changed = true;
                    break;
                case 15:
                    System.out.print("Enter new Father's Middle Name: ");
                    String newFatherMiddleName = scanner.nextLine();
                    dummyStudent.set_father_middle_name(newFatherMiddleName);
                    changed = true;
                    break;
                case 16:
                    System.out.print("Enter new Father's Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newFatherExtensionName = scanner.nextLine();
                    dummyStudent.set_father_extension_name(newFatherExtensionName);
                    changed = true;
                    break;
                case 17:
                    System.out.print("Enter new Mother's First Name: ");
                    String newMotherFirstName = scanner.nextLine();
                    dummyStudent.set_mother_first_name(newMotherFirstName);
                    changed = true;
                    break;
                case 18:
                    System.out.print("Enter new Mother's Last Name: ");
                    String newMotherLastName = scanner.nextLine();
                    dummyStudent.set_mother_last_name(newMotherLastName);
                    changed = true;
                    break;
                case 19:
                    System.out.print("Enter new Mother's Middle Name: ");
                    String newMotherMiddleName = scanner.nextLine();
                    dummyStudent.set_mother_middle_name(newMotherMiddleName);
                    changed = true;
                    break;
                case 20:
                    System.out.print("Enter new Mother's Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newMotherExtensionName = scanner.nextLine();
                    dummyStudent.set_mother_extension_name(newMotherExtensionName);
                    changed = true;
                    break;
                case 21:
                    System.out.print("Enter new Guardian's First Name: ");
                    String newGuardianFirstName = scanner.nextLine();
                    dummyStudent.set_guardian_first_name(newGuardianFirstName);
                    changed = true;
                    break;
                case 22:
                    System.out.print("Enter new Guardian's Last Name: ");
                    String newGuardianLastName = scanner.nextLine();
                    dummyStudent.set_guardian_last_name(newGuardianLastName);
                    changed = true;
                    break;
                case 23:
                    System.out.print("Enter new Guardian's Middle Name: ");
                    String newGuardianMiddleName = scanner.nextLine();
                    dummyStudent.set_guardian_middle_name(newGuardianMiddleName);
                    changed = true;
                    break;
                case 24:
                    System.out.print("Enter new Guardian's Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newGuardianExtensionName = scanner.nextLine();
                    dummyStudent.set_guardian_extension_name(newGuardianExtensionName);
                    changed = true;
                    break;
                case 25:
                    System.out.println("Enter Current Address.");
                    Address address = addressManager.createAddress();
                    dummyStudent.set_current_address(address);
                    changed = true;
                    break;
                case 26:
                    System.out.println("Enter Permanent Address.");
                    Address permanentAddress = addressManager.createAddress();
                    dummyStudent.set_permanent_address(permanentAddress);
                    changed = true;
                    break;
                case 27:
                    System.out.println("Enter Birthplace.");
                    Address birthplace = addressManager.createAddress();
                    dummyStudent.set_birthplace(birthplace);
                    changed = true;
                    break;
                case 0:
                    break loop;
                default:
                    System.out.println("That does not correspond to any known choice.");
            }
        }

        if (!changed) return;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        student.copy(dummyStudent);
        student.set_date_modified(now);
        try {
            String sql = "UPDATE student SET " +
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
                    "student_current_address_code = ?, " +
                    "student_current_address_street = ?, " +
                    "student_current_address_barangay = ?, " +
                    "student_current_address_city = ?, " +
                    "student_current_address_province = ?, " +
                    "student_current_address_region = ?, " +
                    "student_permanent_address_code = ?, " +
                    "student_permanent_address_street = ?, " +
                    "student_permanent_address_barangay = ?, " +
                    "student_permanent_address_city = ?, " +
                    "student_permanent_address_province = ?, " +
                    "student_permanent_address_region = ?, " +
                    "student_birthplace_code = ?, " +
                    "student_birthplace_street = ?, " +
                    "student_birthplace_barangay = ?, " +
                    "student_birthplace_city = ?, " +
                    "student_birthplace_province = ?, " +
                    "student_birthplace_region = ?, " +
                    "student_date_modified = ? " +
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
            preparedStatement.setString(9, student.get_lrn());
            preparedStatement.setString(10, student.get_indigenous_people());
            preparedStatement.setString(11, student.get_4ps());
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
            preparedStatement.setString(25, student.get_current_address_code());
            preparedStatement.setString(26, student.get_current_address_street());
            preparedStatement.setString(27, student.get_current_address_barangay());
            preparedStatement.setString(28, student.get_current_address_city());
            preparedStatement.setString(29, student.get_current_address_province());
            preparedStatement.setString(30, student.get_current_address_region());
            preparedStatement.setString(25, student.get_permanent_address_code());
            preparedStatement.setString(26, student.get_permanent_address_street());
            preparedStatement.setString(27, student.get_permanent_address_barangay());
            preparedStatement.setString(28, student.get_permanent_address_city());
            preparedStatement.setString(29, student.get_permanent_address_province());
            preparedStatement.setString(30, student.get_permanent_address_region());
            preparedStatement.setString(31, student.get_birthplace_code());
            preparedStatement.setString(32, student.get_birthplace_street());
            preparedStatement.setString(33, student.get_birthplace_barangay());
            preparedStatement.setString(34, student.get_birthplace_city());
            preparedStatement.setString(35, student.get_birthplace_province());
            preparedStatement.setString(36, student.get_birthplace_region());
            preparedStatement.setTimestamp(37, now);
            preparedStatement.setInt(38, student.get_id());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteStudent(Connection conn, Student student) {
        System.out.printf("Do you really wish to delete %s? (Y/N): ", student.get_fml_name());
        boolean confirm = get_booleanChoice(yesWords, noWords);
        if (!confirm) return;

        try {
            String sql = "DELETE FROM student WHERE student_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, student.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.printf("Successfully deleted %s.%n", student.get_fml_name());
        studentList.remove(student);
    }

    public void onSectionDelete(Section section) {
        for (Student i : studentList) {
            if (i.get_section().equals(section)) i.set_section(null);
        }
    }

    public void onStrandDelete(Strand strand) {
        for (Student i : studentList) {
            if (i.get_strand().equals(strand)) i.set_strand(null);
        }
    }
}

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneralDataManager extends AbstractDataManager {
    private static List<Strand> strandList;
    private static List<Section> sectionList;
    private static List<Teacher> teacherList;

    public GeneralDataManager(Scanner scanner) {
        super(scanner);
    }

    public void loadData(Connection conn) {
        List<Strand> strandList = new ArrayList<>();
        List<Teacher> teacherList = new ArrayList<>();
        List<Section> sectionList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM strand";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Strand newStrand = new Strand(
                        resultSet.getInt("strand_id"),
                        resultSet.getString("strand_strand"),
                        resultSet.getString("strand_track"),
                        resultSet.getString("strand_description")
                );
                strandList.add(newStrand);
            }
        } catch (SQLException e) {
            System.err.println("LOADING STRANDS: " + e.getMessage());
        }

        try {
            String sql = "SELECT * FROM teacher";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Teacher newTeacher = new Teacher(
                        resultSet.getInt("teacher_id"),
                        new Name(
                                resultSet.getString("teacher_first_name"),
                                resultSet.getString("teacher_last_name"),
                                resultSet.getString("teacher_middle_name"),
                                resultSet.getString("teacher_extension_name")
                        ),
                        resultSet.getBoolean("teacher_sex"),
                        resultSet.getBoolean("teacher_married"),
                        resultSet.getTimestamp("teacher_date_created"),
                        resultSet.getTimestamp("teacher_date_modified")
                );
                teacherList.add(newTeacher);
            }
        } catch (SQLException e) {
            System.err.println("LOADING TEACHERS: " + e.getMessage());
        }

        try {
            String sql = "SELECT * FROM section";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Section newSection = new Section(
                        resultSet.getInt("section_id"),
                        resultSet.getString("section_name"),
                        resultSet.getInt("section_grade"),
                        findStrand(resultSet.getInt("section_strand")),
                        findTeacher(resultSet.getInt("section_teacher"))
                );
                sectionList.add(newSection);
            }
        } catch (SQLException e) {
            System.err.println("LOADING SECTIONS: " + e.getMessage());
        }

        GeneralDataManager.strandList = strandList;
        GeneralDataManager.teacherList = teacherList;
        GeneralDataManager.sectionList = sectionList;
    }

    public Strand findStrand(int strand_id) {
        for (Strand i : strandList) {
            if (i.get_id() == strand_id) return i;
        }
        return null;
    }

    public Teacher findTeacher(int teacher_id) {
        for (Teacher i : teacherList) {
            if (i.get_id() == teacher_id) return i;
        }
        return null;
    }

    public void insertStrand(Connection conn) {
        System.out.print("Enter Strand: ");
        String strand_strand = scanner.nextLine();
        System.out.print("Enter Track: ");
        String strand_track = scanner.nextLine();
        System.out.print("Enter Description");
        String strand_description = scanner.nextLine();

        try {
            String sql = "INSERT INTO strand(" +
                    "strand_strand," +
                    "strand_track," +
                    "strand_description) " +
                    "VALUES(?,?,?)" +
                    "RETURNING strand_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, strand_strand);
            preparedStatement.setString(2, strand_track);
            preparedStatement.setString(3, strand_description);
            ResultSet resultSet = preparedStatement.executeQuery();

            Strand newStrand = new Strand(
                    resultSet.getInt("strand_id"),
                    strand_strand,
                    strand_track,
                    strand_description
            );
            strandList.add(newStrand);
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void insertTeacher(Connection conn) {
        Name teacher_name = Name.createName(scanner, "");
        System.out.print("Enter Sex (M/F): ");
        boolean teacher_sex = get_booleanChoice(femaleWords, maleWords);
        System.out.print("Is married (Y/N): ");
        boolean teacher_married = get_booleanChoice(yesWords, noWords);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        try {
            String sql = "INSERT INTO teacher(" +
                    "teacher_first_name," +
                    "teacher_last_name," +
                    "teacher_middle_name," +
                    "teacher_extension_name," +
                    "teacher_sex," +
                    "teacher_married," +
                    "teacher_date_created," +
                    "teacher_date_modified) " +
                    "VALUES(?,?,?,?,?,?,?,?) " +
                    "RETURNING teacher_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, teacher_name.first_name);
            preparedStatement.setString(2, teacher_name.last_name);
            preparedStatement.setString(3, teacher_name.middle_name);
            preparedStatement.setString(4, teacher_name.extension_name);
            preparedStatement.setBoolean(5, teacher_sex);
            preparedStatement.setBoolean(6, teacher_married);
            preparedStatement.setTimestamp(7, now);
            preparedStatement.setTimestamp(8, now);
            ResultSet resultSet = preparedStatement.executeQuery();

            Teacher newTeacher = new Teacher(
                    resultSet.getInt("teacher_id"),
                    teacher_name,
                    teacher_sex,
                    teacher_married,
                    now,
                    now
            );
            teacherList.add(newTeacher);
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Strand selectStrand() {
        for (int i = 0; i < strandList.size(); i++) {
            Strand s = strandList.get(i);
            System.out.printf("(%s) %s, %s%n", i+1, s.get_strand(), s.get_description());
        }
        System.out.println("(0) Back.");

        while (true) {
            int input = get_inputInt();
            if (input == 0) return null;
            try {
                return strandList.get(input-1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That integer does not correspond to any strand.");
            }
        }
    }

    public Teacher selectTeacher() {
        for (int i = 0; i < teacherList.size(); i++) {
            Teacher t = teacherList.get(i);
            System.out.printf("(%s) %s%n", i+1, t.get_fml_name_with_title());
        }
        System.out.println("(0) Back.");

        while (true) {
            int input = get_inputInt();
            if (input == 0) return null;
            try {
                return teacherList.get(input-1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That integer does not correspond to any teacher.");
            }
        }
    }

    public void insertSection(Connection conn) {
        System.out.print("Enter Section Name: ");
        String section_name = scanner.nextLine();
        System.out.print("Enter Grade Level: ");
        int section_grade = get_inputInt();

        System.out.println("Select Strand.");
        Strand section_strand = selectStrand();
        if (section_strand == null) {
            System.out.println("No strand selected. Section creation cancelled.");
            return;
        }

        System.out.println("Select Adviser.");
        Teacher section_adviser = selectTeacher();
        if (section_adviser == null) {
            System.out.println("No teacher selected. Section creation cancelled.");
            return;
        }

        try {
            String sql = "INSERT INTO section(" +
                    "section_name," +
                    "section_grade," +
                    "section_strand," +
                    "section_adviser) " +
                    "VALUES(?,?,?,?) " +
                    "RETURNING section_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, section_name);
            preparedStatement.setInt(2, section_grade);
            preparedStatement.setInt(3, section_strand.get_id());
            preparedStatement.setInt(4, section_adviser.get_id());
            ResultSet resultSet = preparedStatement.executeQuery();

            Section newSection = new Section(
                    resultSet.getInt("section_id"),
                    section_name,
                    section_grade,
                    section_strand,
                    section_adviser
            );
            sectionList.add(newSection);
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

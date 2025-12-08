import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneralDataManager {
    private final Scanner scanner;
    private static List<Strand> strandList;
    private static List<Section> sectionList;
    private static List<Teacher> teacherList;

    public GeneralDataManager(Scanner scanner) {
        this.scanner = scanner;
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
                    "VALUES(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, strand_strand);
            preparedStatement.setString(2, strand_track);
            preparedStatement.setString(3, strand_description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void insertTeacher() {

    }
}

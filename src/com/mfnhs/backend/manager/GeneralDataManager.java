package com.mfnhs.backend.manager;

import com.mfnhs.backend.data.Name;
import com.mfnhs.backend.data.Section;
import com.mfnhs.backend.data.Strand;
import com.mfnhs.backend.data.Teacher;

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
            throw new RuntimeException();
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
            throw new RuntimeException();
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
            throw new RuntimeException();
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
    public Section findSection(int section_id) {
        for (Section i : sectionList) {
            if (i.get_id() == section_id) return i;
        }
        return null;
    }

    public void insertStrand(Connection conn) {
        System.out.print("Enter com.mfnhs.backend.data.Strand: ");
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
    public void updateStrand(Connection conn, Strand strand) {
        boolean changed = false;
        Strand dummyStrand = new Strand(strand);
        loop: while (true) {
            dummyStrand.showValues();
            int input = get_inputInt();
            switch (input) {
                case 1:
                    System.out.print("Enter new strand name: ");
                    String newStrandName = scanner.nextLine();
                    dummyStrand.set_strand(newStrandName);
                    changed = true;
                    break;
                case 2:
                    System.out.print("Enter new track: ");
                    String newTrack = scanner.nextLine();
                    dummyStrand.set_strand(newTrack);
                    changed = true;
                    break;
                case 3:
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    dummyStrand.set_description(newDescription);
                    changed = true;
                    break;
                case 0:
                    break loop;
                default:
                    System.out.println("That does not correspond to any known choice.");
            }
        }

        if (changed) return;
        strand.copy(dummyStrand);
        try {
            String sql = "UPDATE strand SET " +
                    "strand_strand = ?," +
                    "strand_track = ?," +
                    "strand_description = ? " +
                    "WHERE strand_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, strand.get_strand());
            preparedStatement.setString(2, strand.get_track());
            preparedStatement.setString(3, strand.get_description());
            preparedStatement.setInt(4, strand.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void deleteStrand(Connection conn, Strand strand, StudentManager studentManager) {
        System.out.printf("Do you really wish to delete %s, %s? (Y/N): ", strand.get_strand(), strand.get_description());
        boolean confirm = get_booleanChoice(yesWords, noWords);
        if (!confirm) return;

        try {
            String sql = "DELETE FROM strand WHERE strand_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, strand.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.printf("Successfully deleted %s, %s.%n", strand.get_strand(), strand.get_description());
        for (Section i : sectionList) {
            if (i.get_strand().equals(strand)) i.set_strand(null);
        }
        studentManager.onStrandDelete(strand);
        strandList.remove(strand);
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

    public void updateTeacher(Connection conn, Teacher teacher) {
        boolean changed = false;
        Teacher dummyTeacher = new Teacher(teacher);
        loop: while (true) {
            dummyTeacher.showValues();
            int input = get_inputInt();
            switch (input) {
                case 1:
                    System.out.print("Enter new First com.mfnhs.backend.data.Name: ");
                    String newFirstName = scanner.nextLine();
                    dummyTeacher.set_first_name(newFirstName);
                    changed = true;
                    break;
                case 2:
                    System.out.print("Enter new Last com.mfnhs.backend.data.Name: ");
                    String newLastName = scanner.nextLine();
                    dummyTeacher.set_last_name(newLastName);
                    changed = true;
                    break;
                case 3:
                    System.out.print("Enter new Middle com.mfnhs.backend.data.Name: ");
                    String newMiddleName = scanner.nextLine();
                    dummyTeacher.set_middle_name(newMiddleName);
                    changed = true;
                    break;
                case 4:
                    System.out.print("Enter new com.mfnhs.backend.data.Name Extension (e.g. Jr., Sr. II, III, etc.; leave blank if not applicable).\n> ");
                    String newExtensionName = scanner.nextLine();
                    dummyTeacher.set_extension_name(newExtensionName);
                    changed = true;
                    break;
                case 5:
                    System.out.print("Enter new Sex (M/F): ");
                    boolean newSex = get_booleanChoice(femaleWords, maleWords);
                    dummyTeacher.set_sex(newSex);
                    changed = true;
                    break;
                case 6:
                    System.out.print("Enter new marital status. Is " + dummyTeacher.get_last_name_with_title() + " married? (Y/N) ");
                    boolean newMarried = get_booleanChoice(yesWords, noWords);
                    dummyTeacher.set_married(newMarried);
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
        teacher.copy(dummyTeacher);
        teacher.set_date_modified(now);
        try {
            String sql = "UPDATE teacher SET " +
                    "teacher_first_name = ?, " +
                    "teacher_last_name = ?," +
                    "teacher_middle_name = ?," +
                    "teacher_extension_name = ?," +
                    "teacher_sex = ?," +
                    "teacher_married = ?," +
                    "teacher_date_modified = ?," +
                    "WHERE teacher_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, teacher.get_first_name());
            preparedStatement.setString(2, teacher.get_last_name());
            preparedStatement.setString(3, teacher.get_middle_name());
            preparedStatement.setString(4, teacher.get_extension_name());
            preparedStatement.setBoolean(5, teacher.get_sex());
            preparedStatement.setBoolean(6, teacher.get_married());
            preparedStatement.setTimestamp(7, now);
            preparedStatement.setInt(8, teacher.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void deleteTeacher(Connection conn, Teacher teacher) {
        System.out.printf("Do you really wish to delete %s? (Y/N): ", teacher.get_fml_name_with_title());
        boolean confirm = get_booleanChoice(yesWords, noWords);
        if (!confirm) return;

        try {
            String sql = "DELETE FROM teacher WHERE teacher_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.printf("Successfully deleted %s.%n", teacher.get_fml_name_with_title());
        for (Section i : sectionList) {
            if (i.get_adviser().equals(teacher)) i.set_adviser(null);
        }
        teacherList.remove(teacher);
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

    public Section selectSection() {
        for (int i = 0; i < sectionList.size(); i++) {
            Section s = sectionList.get(i);
            System.out.printf("(%s) Grade %s - %s%n", i+1, s.get_grade(), s.get_name());
        }
        System.out.println("(0) Back.");

        while (true) {
            int input = get_inputInt();
            if (input == 0) return null;
            try {
                return sectionList.get(input-1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That integer does not correspond to any section.");
            }
        }
    }
    public void insertSection(Connection conn) {
        System.out.print("Enter com.mfnhs.backend.data.Section com.mfnhs.backend.data.Name: ");
        String section_name = scanner.nextLine();
        System.out.print("Enter Grade Level: ");
        int section_grade = get_inputInt();

        System.out.println("Select com.mfnhs.backend.data.Strand.");
        Strand section_strand = selectStrand();
        if (section_strand == null) {
            System.out.println("No strand selected. com.mfnhs.backend.data.Section creation cancelled.");
            return;
        }

        System.out.println("Select Adviser.");
        Teacher section_adviser = selectTeacher();
        if (section_adviser == null) {
            System.out.println("No teacher selected. com.mfnhs.backend.data.Section creation cancelled.");
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
    public void updateSection(Connection conn, Section section) {
        boolean changed = false;
        Section dummySection = new Section(section);
        loop: while (true) {
            dummySection.showValues();
            int input = get_inputInt();
            switch (input) {
                case 1:
                    System.out.print("Enter new com.mfnhs.backend.data.Section com.mfnhs.backend.data.Name: ");
                    String newName = scanner.nextLine();
                    dummySection.set_name(newName);
                    changed = true;
                    break;
                case 2:
                    System.out.print("Enter new Grade Level: ");
                    int newGrade = get_inputInt();
                    dummySection.set_grade(newGrade);
                    changed = true;
                    break;
                case 3:
                    System.out.println("Select new com.mfnhs.backend.data.Strand.");
                    Strand newStrand = selectStrand();
                    dummySection.set_strand(newStrand);
                    changed = true;
                    break;
                case 4:
                    System.out.println("Select new Adviser.");
                    Teacher newTeacher = selectTeacher();
                    dummySection.set_adviser(newTeacher);
                    changed = true;
                    break;
                case 0:
                    break loop;
                default:
                    System.out.println("That does not correspond to any known choice.");
            }
        }

        if (!changed) return;
        //Timestamp now = new Timestamp(System.currentTimeMillis());
        section.copy(dummySection);
        try {
            String sql = "UPDATE section SET " +
                    "section_name = ?," +
                    "section_grade = ?," +
                    "section_strand = ?," +
                    "section_adviser = ? " +
                    "WHERE section_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, section.get_name());
            preparedStatement.setInt(2, section.get_grade());
            preparedStatement.setInt(3, section.get_strand().get_id());
            preparedStatement.setInt(4, section.get_adviser().get_id());
            preparedStatement.setInt(5, section.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void deleteSection(Connection conn, Section section, StudentManager studentManager) {
        System.out.printf("Do you really wish to delete Grade %s - %s? (Y/N): ", section.get_grade(), section.get_name());
        boolean confirm = get_booleanChoice(yesWords, noWords);
        if (!confirm) return;

        try {
            String sql = "DELETE FROM section WHERE section_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, section.get_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.printf("Successfully deleted Grade %s - %s.", section.get_grade(), section.get_name());
        studentManager.onSectionDelete(section);
        sectionList.remove(section);
    }

    public static int getSectionCount() {
        return sectionList.size();
    }
}

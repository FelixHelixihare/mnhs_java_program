import java.util.List;

public class Section {
    private int section_id;
    private String section_name;
    private int section_grade;
    private Strand section_strand;
    private Teacher section_adviser;

    private List<Student> studentList;

    public Section (int section_id, String section_name, int section_grade, Strand section_strand, Teacher section_adviser) {
        this.section_id = section_id;
        this.section_name = section_name;
        this.section_grade = section_grade;
        this.section_strand = section_strand;
        this.section_adviser = section_adviser;
    }

    public int get_id() {return section_id;}
    public String get_name() {return section_name;}
    public int get_grade() {return section_grade;}
    public Strand get_strand() {return section_strand;}
    public Teacher get_adviser() {return section_adviser;}
}

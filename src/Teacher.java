import java.sql.Timestamp;

public class Teacher {
    private int teacher_id;
    private Name teacher_name;
    private boolean teacher_sex;
    private boolean teacher_married;
    private Timestamp teacher_date_created;
    private Timestamp teacher_date_modified;

    public Teacher(int teacher_id, Name teacher_name, boolean teacher_sex, boolean teacher_married, Timestamp teacher_date_created, Timestamp teacher_date_modified) {
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.teacher_sex = teacher_sex;
        this.teacher_married = teacher_married;
        this.teacher_date_created = teacher_date_created;
        this.teacher_date_modified = teacher_date_modified;
    }

    public int get_id() {return teacher_id;}
    public String get_first_name() {return teacher_name.first_name;}
    public String get_last_name() {return teacher_name.last_name;}
    public String get_middle_name() {return teacher_name.middle_name;}
    public String get_middle_initial() {return teacher_name.get_middle_initial();}
    public String get_extension_name() {return teacher_name.extension_name;}
    public String get_lfm_name() {return teacher_name.get_lfm_name();}
    public String get_fml_name() {return teacher_name.get_fml_name();}
    public String get_full_name() {return teacher_name.get_full_name();}
    public boolean get_sex() {return teacher_sex;}
    public boolean get_married() {return teacher_married;}
    public Timestamp get_date_created() {return teacher_date_created;}
    public Timestamp get_date_modified() {return teacher_date_modified;}
}

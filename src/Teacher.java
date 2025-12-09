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

    public Teacher(Teacher teacher) {
        this.teacher_id = teacher.teacher_id;
        this.teacher_name = teacher.teacher_name;
        this.teacher_sex = teacher.teacher_sex;
        this.teacher_married = teacher.teacher_married;
        this.teacher_date_created = teacher.teacher_date_created;
        this.teacher_date_modified = teacher.teacher_date_modified;
    }

    public void copy(Teacher teacher) {
        this.teacher_id = teacher.teacher_id;
        this.teacher_name = teacher.teacher_name;
        this.teacher_sex = teacher.teacher_sex;
        this.teacher_married = teacher.teacher_married;
        this.teacher_date_created = teacher.teacher_date_created;
        this.teacher_date_modified = teacher.teacher_date_modified;
    }

    public int get_id() {return teacher_id;}
    public String get_first_name() {return teacher_name.first_name;}
    public String get_last_name() {return teacher_name.last_name;}
    public String get_middle_name() {return teacher_name.middle_name;}
    public String get_middle_initial() {return teacher_name.get_middle_initial();}
    public String get_extension_name() {return teacher_name.extension_name;}
    public String get_lfm_name() {return teacher_name.get_lfm_name();}
    public String get_fml_name() {return teacher_name.get_fml_name();}
    public String get_fml_name_with_title() {return get_title() + " " + get_fml_name();}
    public String get_last_name_with_title() {return get_title() + " " + get_last_name();}

    public String get_full_name() {return teacher_name.get_full_name();}
    public boolean get_sex() {return teacher_sex;}
    public boolean get_married() {return teacher_married;}
    public Timestamp get_date_created() {return teacher_date_created;}
    public Timestamp get_date_modified() {return teacher_date_modified;}

    public void set_name(Name teacher_name) {this.teacher_name = teacher_name;}
    public void set_first_name(String firstName) {this.teacher_name.first_name = firstName;}
    public void set_last_name(String lastName) {this.teacher_name.last_name = lastName;}
    public void set_middle_name(String middleName) {this.teacher_name.middle_name = middleName;}
    public void set_extension_name(String extensionName) {this.teacher_name.extension_name = extensionName;}
    public void set_sex(boolean sex) {this.teacher_sex = sex;}
    public void set_married(boolean married) {this.teacher_married = married;}
    public void set_date_modified(Timestamp dateModified) {this.teacher_date_modified = dateModified;}

    private String get_title() {
        if (teacher_sex) {
            if (teacher_married) return "Mrs.";
            else return "Ms.";
        } else {
            return"Mr.";
        }
    }

    public void showValues() {
        System.out.println("(1) First Name: " + get_first_name());
        System.out.println("(2) Last Name: " + get_last_name());
        System.out.println("(3) Middle Name: " + get_middle_name());
        System.out.println("(4) Name Extension: " + get_extension_name());
        System.out.println("(5) Sex: " + (teacher_sex ? "Female" : "Male"));
        System.out.println("(6) Marital Status: " + (teacher_married ? "Married" : "Single"));
        System.out.println("(0) Back / Apply Changes.");
    }
}

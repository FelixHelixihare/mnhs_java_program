import java.sql.Date;
import java.sql.Timestamp;

public class Student {
    private int student_id;
    private Name student_name;
    private Date student_birthdate;
    private boolean student_sex;
    private String student_mother_tongue;
    private String student_birth_certificate_number;
    private int student_lrn;
    private String student_indigenous_people;
    private int student_4ps;
    private String student_disability;
    private Name student_father_name;
    private Name student_mother_name;
    private Name student_guardian_name;
    private Timestamp student_dateCreated;
    private Address student_address;
    private Address student_birthplace;

    public Student(Builder builder) {
        this.student_id = builder.student_id;
        this.student_name = builder.student_name;
        this.student_birthdate = builder.student_birthdate;
        this.student_sex = builder.student_sex;
        this.student_mother_tongue = builder.student_mother_tongue;
        this.student_birth_certificate_number = builder.student_birth_certificate_number;
        this.student_lrn = builder.student_lrn;
        this.student_indigenous_people = builder.student_indigenous_people;
        this.student_4ps = builder.student_4ps;
        this.student_disability = builder.student_disability;
        this.student_father_name = builder.student_father_name;
        this.student_mother_name = builder.student_mother_name;
        this.student_guardian_name = builder.student_guardian_name;
        this.student_dateCreated = builder.student_dateCreated;
        this.student_address = builder.student_address;
        this.student_birthplace = builder.student_birthplace;
    }

    public static class Builder {
        private int student_id;
        private Name student_name;
        private Date student_birthdate;
        private boolean student_sex;
        private String student_mother_tongue;
        private String student_birth_certificate_number;
        private int student_lrn;
        private String student_indigenous_people;
        private int student_4ps;
        private String student_disability;
        private Name student_father_name;
        private Name student_mother_name;
        private Name student_guardian_name;
        private Timestamp student_dateCreated;
        private Address student_address;
        private Address student_birthplace;

        public static Builder newInstance() {
            return new Builder();
        }
        private Builder() {}
        public Builder setId(int id) {
            this.student_id = id;
            return this;
        }
        public Builder setName(Name name) {
            this.student_name = name;
            return this;
        }
        public Builder setBirthdate(Date birthdate) {
            this.student_birthdate = birthdate;
            return this;
        }
        //false = male, true = female
        public Builder setSex(boolean sex) {
            this.student_sex = sex;
            return this;
        }
        public Builder setMotherTongue(String motherTongue) {
            this.student_mother_tongue = motherTongue;
            return this;
        }
        public Builder setBirthCertificateNumber(String birthCertificateNumber) {
            this.student_birth_certificate_number = birthCertificateNumber;
            return this;
        }
        public Builder setLRN(int lrn) {
            this.student_lrn = lrn;
            return this;
        }
        public Builder setIP(String ip) {
            this.student_indigenous_people = ip;
            return this;
        }
        public Builder set4Ps(int fourPs) {
            this.student_4ps = fourPs;
            return this;
        }
        public Builder setDisability(String disability) {
            this.student_disability = disability;
            return this;
        }
        public Builder setFatherName(Name fatherName) {
            this.student_father_name = fatherName;
            return this;
        }
        public Builder setMotherName(Name motherName) {
            this.student_mother_name = motherName;
            return this;
        }
        public Builder setGuardianName(Name guardianName) {
            this.student_guardian_name = guardianName;
            return this;
        }
        public Builder setDateCreated(Timestamp dateCreated) {
            this.student_dateCreated = dateCreated;
            return this;
        }
        public Builder setAddress(Address address) {
            this.student_address = address;
            return this;
        }
        public Builder setBirthplace(Address birthplace) {
            this.student_birthplace = birthplace;
            return this;
        }
        public Student build() {
            return new Student(this);
        }
    }

    public String get_first_name() {return student_name.first_name;}
    public String get_last_name() {return student_name.last_name;}
    public String get_middle_name() {return student_name.middle_name;}
    public String get_middle_initial() {return student_name.get_middle_initial();}
    public String get_extension_name() {return student_name.extension_name;}

    public String get_lfm_name() {return student_name.get_lfm_name();}
    public String get_fml_name() {return student_name.get_fml_name();}
    public String get_full_name() {return student_name.get_full_name();}

    public String getStudent_birthdate() {return student_birthdate.toString();}
    public String getStudent_dateCreated() {return student_dateCreated.toString();}
}

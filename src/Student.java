import java.sql.Date;
import java.sql.Timestamp;

public class Student {
    private int student_id;
    private Name student_name;
    private Date student_birthdate;
    private boolean student_sex;
    private String student_mother_tongue;
    private String student_birth_certificate_number;
    private String student_lrn;
    private String student_indigenous_people;
    private String student_4ps;
    private String student_disability;
    private Name student_father_name;
    private Name student_mother_name;
    private Name student_guardian_name;
    private Address student_current_address;
    private Address student_permanent_address;
    private Address student_birthplace;

    private Strand student_strand;
    private Section student_section;

    private Timestamp student_date_created;
    private Timestamp student_date_modified;
    private User student_user_created;
    private User student_user_modified;

    public Student(Student student) {
        this.student_id = student.student_id;
        this.student_name = student.student_name;
        this.student_birthdate = student.student_birthdate;
        this.student_sex = student.student_sex;
        this.student_mother_tongue = student.student_mother_tongue;
        this.student_birth_certificate_number = student.student_birth_certificate_number;
        this.student_lrn = student.student_lrn;
        this.student_indigenous_people = student.student_indigenous_people;
        this.student_4ps = student.student_4ps;
        this.student_disability = student.student_disability;
        this.student_father_name = student.student_father_name;
        this.student_mother_name = student.student_mother_name;
        this.student_guardian_name = student.student_guardian_name;
        this.student_current_address = student.student_current_address;
        this.student_permanent_address = student.student_permanent_address;
        this.student_birthplace = student.student_birthplace;
        this.student_strand = student.student_strand;
        this.student_section = student.student_section;
        this.student_date_created = student.student_date_created;
        this.student_date_modified = student.student_date_modified;
        this.student_user_created = student.student_user_created;
        this.student_user_modified = student.student_user_modified;
    }

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
        this.student_current_address = builder.student_current_address;
        this.student_permanent_address = builder.student_permanent_address;
        this.student_birthplace = builder.student_birthplace;
        this.student_strand = builder.student_strand;
        this.student_section = builder.student_section;
        this.student_date_created = builder.student_date_created;
        this.student_date_modified = builder.student_date_modified;
        this.student_user_created = builder.student_user_created;
        this.student_user_modified = builder.student_user_modified;
    }

    public void copy(Student student) {
        this.student_id = student.student_id;
        this.student_name = student.student_name;
        this.student_birthdate = student.student_birthdate;
        this.student_sex = student.student_sex;
        this.student_mother_tongue = student.student_mother_tongue;
        this.student_birth_certificate_number = student.student_birth_certificate_number;
        this.student_lrn = student.student_lrn;
        this.student_indigenous_people = student.student_indigenous_people;
        this.student_4ps = student.student_4ps;
        this.student_disability = student.student_disability;
        this.student_father_name = student.student_father_name;
        this.student_mother_name = student.student_mother_name;
        this.student_guardian_name = student.student_guardian_name;
        this.student_current_address = student.student_current_address;
        this.student_permanent_address = student.student_permanent_address;
        this.student_birthplace = student.student_birthplace;
        this.student_strand = student.student_strand;
        this.student_section = student.student_section;
        this.student_date_created = student.student_date_created;
        this.student_date_modified = student.student_date_modified;
        this.student_user_created = student.student_user_created;
        this.student_user_modified = student.student_user_modified;

    }

    public static class Builder {
        private int student_id;
        private Name student_name;
        private Date student_birthdate;
        private boolean student_sex;
        private String student_mother_tongue;
        private String student_birth_certificate_number;
        private String student_lrn;
        private String student_indigenous_people;
        private String student_4ps;
        private String student_disability;
        private Name student_father_name;
        private Name student_mother_name;
        private Name student_guardian_name;
        private Address student_current_address;
        private Address student_permanent_address;
        private Address student_birthplace;

        private Strand student_strand;
        private Section student_section;

        private Timestamp student_date_created;
        private Timestamp student_date_modified;
        private User student_user_created;
        private User student_user_modified;

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
        public Builder setLRN(String lrn) {
            this.student_lrn = lrn;
            return this;
        }
        public Builder setIP(String ip) {
            this.student_indigenous_people = ip;
            return this;
        }
        public Builder set4Ps(String fourPs) {
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
            this.student_date_created = dateCreated;
            return this;
        }
        public Builder setCurrentAddress(Address address) {
            this.student_current_address = address;
            return this;
        }
        public Builder setPermanentAddress(Address address) {
            this.student_permanent_address = address;
            return this;
        }
        public Builder setBirthplace(Address birthplace) {
            this.student_birthplace = birthplace;
            return this;
        }
        public Builder setDateModified(Timestamp dateModified) {
            this.student_date_modified = dateModified;
            return this;
        }
        public Student build() {
            return new Student(this);
        }
    }

    public int get_id() {return student_id;}
    public String get_first_name() {return student_name.first_name;}
    public String get_last_name() {return student_name.last_name;}
    public String get_middle_name() {return student_name.middle_name;}
    public String get_middle_initial() {return student_name.get_middle_initial();}
    public String get_extension_name() {return student_name.extension_name;}
    public String get_lfm_name() {return student_name.get_lfm_name();}
    public String get_fml_name() {return student_name.get_fml_name();}
    public String get_full_name() {return student_name.get_full_name();}

    public String get_birthdate_asString() {return student_birthdate.toString();}
    public String get_dateCreated_asString() {return student_date_created.toString();}

    public Date get_birthdate() {return student_birthdate;}
    public boolean get_sex() {return student_sex;}
    public String get_mother_tongue() {return student_mother_tongue;}
    public String get_birth_certificate_number() {return student_birth_certificate_number;}
    public String get_lrn() {return student_lrn;}
    public String get_indigenous_people() {return student_indigenous_people;}
    public String get_4ps() {return student_4ps;}
    public String get_disability() {return student_disability;}

    public String get_father_first_name() {return student_father_name.first_name;}
    public String get_father_last_name() {return student_father_name.last_name;}
    public String get_father_middle_name() {return student_father_name.middle_name;}
    public String get_father_middle_initial() {return student_father_name.get_middle_initial();}
    public String get_father_extension_name() {return student_father_name.extension_name;}
    public String get_father_lfm_name() {return student_father_name.get_lfm_name();}
    public String get_father_fml_name() {return student_father_name.get_fml_name();}
    public String get_father_full_name() {return student_father_name.get_full_name();}

    public String get_mother_first_name() {return student_mother_name.first_name;}
    public String get_mother_last_name() {return student_mother_name.last_name;}
    public String get_mother_middle_name() {return student_mother_name.middle_name;}
    public String get_mother_middle_initial() {return student_mother_name.get_middle_initial();}
    public String get_mother_extension_name() {return student_mother_name.extension_name;}
    public String get_mother_lfm_name() {return student_mother_name.get_lfm_name();}
    public String get_mother_fml_name() {return student_mother_name.get_fml_name();}
    public String get_mother_full_name() {return student_mother_name.get_full_name();}

    public String get_guardian_first_name() {return student_guardian_name.first_name;}
    public String get_guardian_last_name() {return student_guardian_name.last_name;}
    public String get_guardian_middle_name() {return student_guardian_name.middle_name;}
    public String get_guardian_middle_initial() {return student_guardian_name.get_middle_initial();}
    public String get_guardian_extension_name() {return student_guardian_name.extension_name;}
    public String get_guardian_lfm_name() {return student_guardian_name.get_lfm_name();}
    public String get_guardian_fml_name() {return student_guardian_name.get_fml_name();}
    public String get_guardian_full_name() {return student_guardian_name.get_full_name();}

    public Address get_current_address() {return student_current_address;}
    public String get_current_address_code() {return student_current_address.barangay_code;}
    public String get_current_address_street() {return student_current_address.street;}
    public String get_current_address_barangay() {return student_current_address.barangay;}
    public String get_current_address_city() {return student_current_address.city;}
    public String get_current_address_province() {return student_current_address.province;}
    public String get_current_address_region() {return student_current_address.region;}

    public Address get_permanent_address() {return student_permanent_address;}
    public String get_permanent_address_code() {return student_permanent_address.barangay_code;}
    public String get_permanent_address_street() {return student_permanent_address.street;}
    public String get_permanent_address_barangay() {return student_permanent_address.barangay;}
    public String get_permanent_address_city() {return student_permanent_address.city;}
    public String get_permanent_address_province() {return student_permanent_address.province;}
    public String get_permanent_address_region() {return student_permanent_address.region;}

    public Address get_birthplace() {return student_birthplace;}
    public String get_birthplace_code() {return student_birthplace.barangay_code;}
    public String get_birthplace_street() {return student_birthplace.street;}
    public String get_birthplace_barangay() {return student_birthplace.barangay;}
    public String get_birthplace_city() {return student_birthplace.city;}
    public String get_birthplace_province() {return student_birthplace.province;}
    public String get_birthplace_region() {return student_birthplace.region;}

    public void set_first_name(String firstName) {student_name.first_name = firstName;}
    public void set_last_name(String lastName) {student_name.last_name = lastName;}
    public void set_middle_name(String middleName) {student_name.middle_name = middleName;}
    public void set_extension_name(String extensionName) {student_name.extension_name = extensionName;}
    public void set_name(Name name) {student_name = name;}
    public void set_birthdate(Date birthdate) {student_birthdate = birthdate;}
    public void set_sex(boolean sex) {student_sex = sex;}
    public void set_mother_tongue(String motherTongue) {student_mother_tongue = motherTongue;}
    public void set_birth_certificate_number(String birthCertificateNumber) {student_birth_certificate_number = birthCertificateNumber;}
    public void set_LRN(String lrn) {student_lrn = lrn;}
    public void set_indigenous_people(String indigenousPeople) {student_indigenous_people = indigenousPeople;}
    public void set_4ps(String fourPs) {student_4ps = fourPs;}
    public void set_disability(String disability) {student_disability = disability;}
    public void set_father_first_name(String firstName) {student_father_name.first_name = firstName;}
    public void set_father_last_name(String lastName) {student_father_name.last_name = lastName;}
    public void set_father_middle_name(String middleName) {student_father_name.middle_name = middleName;}
    public void set_father_extension_name(String extensionName) {student_father_name.extension_name = extensionName;}
    public void set_father_name(Name name) {student_father_name = name;}
    public void set_mother_first_name(String firstName) {student_mother_name.first_name = firstName;}
    public void set_mother_last_name(String lastName) {student_mother_name.last_name = lastName;}
    public void set_mother_middle_name(String middleName) {student_mother_name.middle_name = middleName;}
    public void set_mother_extension_name(String extensionName) {student_mother_name.extension_name = extensionName;}
    public void set_mother_name(Name name) {student_mother_name = name;}
    public void set_guardian_first_name(String firstName) {student_guardian_name.first_name = firstName;}
    public void set_guardian_last_name(String lastName) {student_guardian_name.last_name = lastName;}
    public void set_guardian_middle_name(String middleName) {student_guardian_name.middle_name = middleName;}
    public void set_guardian_extension_name(String extensionName) {student_guardian_name.extension_name = extensionName;}
    public void set_guardian_name(Name name) {student_guardian_name = name;}
    public void set_current_address(Address address) {student_current_address = address;}
    public void set_permanent_address(Address address) {student_permanent_address = address;}
    public void set_birthplace(Address birthplace) {student_birthplace = birthplace;}

    public void showValues() {
        System.out.println("(1) First Name: " + student_name.first_name);
        System.out.println("(2) Last Name: " + student_name.last_name);
        System.out.println("(3) Middle Name: " + student_name.middle_name);
        System.out.println("(4) Name Extension: " + (student_name.extension_name.isEmpty() ? "N/A" : student_name.extension_name));
        System.out.println("(5) Birthdate: " + student_birthdate.toString());
        System.out.println("(6) Sex: " + (student_sex ? "Female" : "Male"));
        System.out.println("(7) Mother Tongue: " + student_mother_tongue);
        System.out.println("(8) Birth Certificate Number: " + student_birth_certificate_number);
        System.out.println("(9) Learner's Reference Number (LRN): " + student_lrn);
        System.out.println("(10) Indigenous People: " + (student_indigenous_people.isEmpty() ? "N/A" : student_indigenous_people));
        System.out.println("(11) 4Ps Household Number: " + (student_4ps.isEmpty() ? "N/A" : student_4ps));
        System.out.println("(12) Disability: " + (student_disability.isEmpty() ? "N/A" : student_disability));
        System.out.println("(13) Father First Name: " + student_father_name.first_name);
        System.out.println("(14) Father Last Name: " + student_father_name.last_name);
        System.out.println("(15) Father Middle Name: " + student_father_name.middle_name);
        System.out.println("(16) Father Name Extension: " + (student_father_name.extension_name.isEmpty() ? "N/A" : student_father_name.extension_name));
        System.out.println("(17) Mother First Name: " + student_mother_name.first_name);
        System.out.println("(18) Mother Last Name: " + student_mother_name.last_name);
        System.out.println("(19) Mother Middle Name: " + student_mother_name.middle_name);
        System.out.println("(20) Mother Name Extension: " + (student_mother_name.extension_name.isEmpty() ? "N/A" : student_mother_name.extension_name));
        System.out.println("(21) Guardian First Name: " + student_guardian_name.first_name);
        System.out.println("(22) Guardian Last Name: " + student_guardian_name.last_name);
        System.out.println("(23) Guardian Middle Name: " + student_guardian_name.middle_name);
        System.out.println("(24) Guardian Name Extension: " + (student_guardian_name.extension_name.isEmpty() ? "N/A" : student_guardian_name.extension_name));
        System.out.println("(25) Current Address: " + student_current_address.toString());
        System.out.println("(26) Permanent Address: " + student_permanent_address.toString());
        System.out.println("(27) Birthplace: " + student_birthplace.toString());
        System.out.println("(0) Back / Apply Changes.");
    }
}

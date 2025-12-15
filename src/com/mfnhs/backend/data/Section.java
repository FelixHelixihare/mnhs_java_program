package com.mfnhs.backend.data;

import java.util.List;

public class Section {
    private int section_id;
    private String section_name;
    private int section_grade;
    private Strand section_strand;
    private Teacher section_adviser;
    //NOTE: MAYBE ADD date_created AND date_modified FIELDS

    private List<Student> studentList;

    public Section (int section_id, String section_name, int section_grade, Strand section_strand, Teacher section_adviser) {
        this.section_id = section_id;
        this.section_name = section_name;
        this.section_grade = section_grade;
        this.section_strand = section_strand;
        this.section_adviser = section_adviser;
    }

    public Section(Section section) {
        this.section_id = section.section_id;
        this.section_name = section.section_name;
        this.section_grade = section.section_grade;
        this.section_strand = section.section_strand;
        this.section_adviser = section.section_adviser;
    }

    public void copy(Section section) {
        this.section_id = section.section_id;
        this.section_name = section.section_name;
        this.section_grade = section.section_grade;
        this.section_strand = section.section_strand;
        this.section_adviser = section.section_adviser;
    }

    public int get_id() {return section_id;}
    public String get_name() {return section_name;}
    public int get_grade() {return section_grade;}
    public Strand get_strand() {return section_strand;}
    public Teacher get_adviser() {return section_adviser;}

    public void set_id(int id) {this.section_id = id;}
    public void set_name(String name) {this.section_name = name;}
    public void set_grade(int grade) {this.section_grade = grade;}
    public void set_strand(Strand strand) {this.section_strand = strand;}
    public void set_adviser(Teacher teacher) {this.section_adviser = teacher;}

    public void showValues() {
        System.out.println("(1) com.mfnhs.backend.data.Section com.mfnhs.backend.data.Name: " + get_name());
        System.out.println("(2) Grade Level: " + get_grade());
        System.out.printf("(3) com.mfnhs.backend.data.Strand: %s, %s%n", section_strand.get_strand(), section_strand.get_description());
        System.out.println("(4) Adviser: " + get_adviser().get_fml_name_with_title());
        System.out.println("(0) Back / Apply Changes.");
    }
}

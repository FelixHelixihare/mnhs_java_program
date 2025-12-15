package com.mfnhs.backend.data;

import java.util.List;

public class Strand {
    private int strand_id;
    private String strand_strand;
    private String strand_track;
    private String strand_description;

    private List<Student> studentList;

    public Strand(int strand_id, String strand_strand, String strand_track, String strand_description) {
        this.strand_id = strand_id;
        this.strand_strand = strand_strand;
        this.strand_track = strand_track;
        this.strand_description = strand_description;
    }

    public Strand(Strand strand) {
        this.strand_id = strand.strand_id;
        this.strand_strand = strand.strand_strand;
        this.strand_track = strand.strand_track;
        this.strand_description = strand.strand_description;
    }

    public void copy(Strand strand) {
        this.strand_id = strand.strand_id;
        this.strand_strand = strand.strand_strand;
        this.strand_track = strand.strand_track;
        this.strand_description = strand.strand_description;
    }

    public void set_strand(String strand_strand) {this.strand_strand = strand_strand;}
    public void set_track(String strand_track) {this.strand_track = strand_track;}
    public void set_description(String strand_description) {this.strand_description = strand_description;}

    public int get_id() {return strand_id;}
    public String get_strand() {return strand_strand;}
    public String get_track() {return strand_track;}
    public String get_description() {return strand_description;}

    public void showValues() {
        System.out.println("(1) com.mfnhs.backend.data.Strand: " + strand_strand);
        System.out.println("(2) Track: " + strand_track);
        System.out.println("(3) Description: " + strand_description);
        System.out.println("(0) Back / Apply Changes.");
    }
}

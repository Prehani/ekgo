package ekgo.patient;

import java.util.*;

public class PatientData {

    private int id;
    private Date dob;
    private int height;
    private int weight;
    private int heartRate;
    private String medications = "";
    private String conditions = "";
    private String notes = "";

    public PatientData(int id, Date dob, int height, int weight, String medications,
                                   String conditions, String notes) {

        this.id = id;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.medications = medications;
        this.conditions = conditions;
        this.notes = notes;

    }

    public PatientData(Date dob, int height, int weight) {

        this.dob = dob;
        this.height = height;
        this.weight = weight;

    }

    public int getId() { return this.id; }

    public Date getDob() { return this.dob; }

    public int getHeight() { return this.height; }

    public int getWeight() { return this.weight; }

    public int getHeartRate() { return this.heartRate; }

    public String getMedications() { return this.medications; }

    public String getConditions() { return this.conditions; }

    public String getNotes() { return this.notes; }

    public void setId(int id) { this.id = id; }

    public void setDob(Date dob) { this.dob = dob; }

    public void setHeight(int height) { this.height = height; }

    public void setWeight(int weight) { this.weight = weight; }

    public void setMedications(String medications) { this.medications = medications; }

    public void setConditions(String conditions) { this.conditions = conditions; }

    public void setNotes(String notes) { this.notes = notes; }

    //TODO: write patient to DB
}

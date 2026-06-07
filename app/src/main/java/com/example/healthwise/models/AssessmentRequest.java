package com.example.healthwise.models;

public class AssessmentRequest {

    private long userId;
    private String symptoms;
    private String duration;
    private int severity;
    private int age;
    private String gender;

    public AssessmentRequest(long userId, String symptoms, String duration, int severity, int age, String gender) {
        this.userId = userId;
        this.symptoms = symptoms;
        this.duration = duration;
        this.severity = severity;
        this.age = age;
        this.gender = gender;
    }

    public long getUserId() {
        return userId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDuration() {
        return duration;
    }

    public int getSeverity() {
        return severity;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}

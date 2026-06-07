package com.example.healthwise.models;

public class SymptomHistoryEntry {

    private final String title;
    private final String time;
    private final String risk;
    private final String summary;

    public SymptomHistoryEntry(String title, String time, String risk, String summary) {
        this.title = title;
        this.time = time;
        this.risk = risk;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getRisk() {
        return risk;
    }

    public String getSummary() {
        return summary;
    }
}

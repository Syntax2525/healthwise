package com.example.healthwise.utils;

import com.example.healthwise.models.Article;
import com.example.healthwise.models.DiagnosisResult;
import com.example.healthwise.models.SymptomHistoryEntry;

import java.util.Arrays;
import java.util.List;

public final class SampleDataProvider {

    private SampleDataProvider() {
    }

    public static List<Article> getArticles() {
        return Arrays.asList(
                new Article(
                        "Nutrition",
                        "Eat Better Every Day",
                        "Simple habits for balanced meals and steady energy.",
                        "5 min",
                        "Simple habits for balanced meals and steady energy.\n\nTap articles from the dashboard or bottom navigation to explore more health topics."
                ),
                new Article(
                        "Sleep",
                        "Improve Your Sleep Quality",
                        "Wind-down routines that help you rest and recover.",
                        "4 min",
                        "Wind-down routines that help you rest and recover.\n\nTap articles from the dashboard or bottom navigation to explore more health topics."
                ),
                new Article(
                        "Fitness",
                        "Stay Active at Home",
                        "Low-impact exercises you can do without equipment.",
                        "6 min",
                        "Low-impact exercises you can do without equipment.\n\nTap articles from the dashboard or bottom navigation to explore more health topics."
                ),
                new Article(
                        "Hydration",
                        "Staying Hydrated",
                        "Why water matters and how much you may need daily.",
                        "4 min",
                        "Why water matters and how much you may need daily.\n\nTap articles from the dashboard or bottom navigation to explore more health topics."
                )
        );
    }

    public static List<SymptomHistoryEntry> getHistoryEntries() {
        return Arrays.asList(
                new SymptomHistoryEntry(
                        "Headache & Fatigue",
                        "2 days ago",
                        "Low Risk",
                        "Rest and hydration recommended."
                ),
                new SymptomHistoryEntry(
                        "Sore Throat",
                        "1 week ago",
                        "Moderate Risk",
                        "Monitor fever and seek care if it worsens."
                )
        );
    }

    public static DiagnosisResult getSampleDiagnosis() {
        return new DiagnosisResult(
                "Possible Viral Infection",
                "Low to Moderate Risk",
                "Confidence: 78%",
                "Based on your symptoms, this may be a mild viral illness. Monitor your temperature and rest.",
                "• Stay hydrated and get plenty of rest\n"
                        + "• Use over-the-counter pain relief if needed\n"
                        + "• Seek care if symptoms worsen or persist beyond 5 days"
        );
    }
}

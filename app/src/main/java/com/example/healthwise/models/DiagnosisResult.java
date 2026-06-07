package com.example.healthwise.models;

import android.os.Bundle;

import com.example.healthwise.utils.NavigationArgs;

public class DiagnosisResult {

    private final String resultTitle;
    private final String riskLevel;
    private final String confidence;
    private final String resultSummary;
    private final String recommendations;

    public DiagnosisResult(String resultTitle, String riskLevel, String confidence,
                           String resultSummary, String recommendations) {
        this.resultTitle = resultTitle;
        this.riskLevel = riskLevel;
        this.confidence = confidence;
        this.resultSummary = resultSummary;
        this.recommendations = recommendations;
    }

    public String getResultTitle() {
        return resultTitle;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getConfidence() {
        return confidence;
    }

    public String getResultSummary() {
        return resultSummary;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(NavigationArgs.RESULT_TITLE, resultTitle);
        bundle.putString(NavigationArgs.RISK_LEVEL, riskLevel);
        bundle.putString(NavigationArgs.CONFIDENCE, confidence);
        bundle.putString(NavigationArgs.RESULT_SUMMARY, resultSummary);
        bundle.putString(NavigationArgs.RECOMMENDATIONS, recommendations);
        return bundle;
    }

    public static DiagnosisResult fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return new DiagnosisResult(
                bundle.getString(NavigationArgs.RESULT_TITLE),
                bundle.getString(NavigationArgs.RISK_LEVEL),
                bundle.getString(NavigationArgs.CONFIDENCE),
                bundle.getString(NavigationArgs.RESULT_SUMMARY),
                bundle.getString(NavigationArgs.RECOMMENDATIONS)
        );
    }
}

package com.example.healthwise.utils;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

public final class EmergencyDetector {

    private static final List<String> CHEST_PAIN_KEYWORDS = Arrays.asList(
            "chest pain", "chest pressure", "chest tightness", "crushing chest",
            "pain in chest", "heart attack"
    );

    private static final List<String> BREATHING_KEYWORDS = Arrays.asList(
            "difficulty breathing", "difficulty breath", "shortness of breath",
            "short of breath", "can't breathe", "cannot breathe", "trouble breathing",
            "gasping for air", "choking", "severe breathing"
    );

    private static final List<String> BLEEDING_KEYWORDS = Arrays.asList(
            "severe bleeding", "heavy bleeding", "uncontrolled bleeding",
            "bleeding heavily", "blood loss", "hemorrhage", "hemorrhaging"
    );

    private static final List<String> CONSCIOUSNESS_KEYWORDS = Arrays.asList(
            "loss of consciousness", "lost consciousness", "passed out",
            "fainting", "unconscious", "not responding", "unresponsive",
            "blacked out", "collapsed"
    );

    private static final List<String> STROKE_KEYWORDS = Arrays.asList(
            "stroke", "face drooping", "drooping face", "arm weakness",
            "weakness on one side", "slurred speech", "speech difficulty",
            "sudden numbness", "sudden confusion", "sudden severe headache",
            "one side of body", "facial droop"
    );

    private EmergencyDetector() {
    }

    public static boolean isEmergency(String symptoms) {
        if (TextUtils.isEmpty(symptoms)) {
            return false;
        }
        String normalized = symptoms.toLowerCase().trim();
        return containsAny(normalized, CHEST_PAIN_KEYWORDS)
                || containsAny(normalized, BREATHING_KEYWORDS)
                || containsAny(normalized, BLEEDING_KEYWORDS)
                || containsAny(normalized, CONSCIOUSNESS_KEYWORDS)
                || containsAny(normalized, STROKE_KEYWORDS);
    }

    public static String getEmergencyReason(String symptoms) {
        if (TextUtils.isEmpty(symptoms)) {
            return "Potential emergency symptoms detected.";
        }
        String normalized = symptoms.toLowerCase().trim();
        if (containsAny(normalized, CHEST_PAIN_KEYWORDS)) {
            return "Chest pain may indicate a medical emergency.";
        }
        if (containsAny(normalized, BREATHING_KEYWORDS)) {
            return "Difficulty breathing requires immediate attention.";
        }
        if (containsAny(normalized, BLEEDING_KEYWORDS)) {
            return "Severe bleeding requires emergency care.";
        }
        if (containsAny(normalized, CONSCIOUSNESS_KEYWORDS)) {
            return "Loss of consciousness is a medical emergency.";
        }
        if (containsAny(normalized, STROKE_KEYWORDS)) {
            return "Possible stroke symptoms detected.";
        }
        return "Potential emergency symptoms detected.";
    }

    private static boolean containsAny(String text, List<String> keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}

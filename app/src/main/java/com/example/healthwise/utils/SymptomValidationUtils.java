package com.example.healthwise.utils;

import android.text.TextUtils;

public final class SymptomValidationUtils {

    private static final int MIN_SYMPTOM_LENGTH = 3;
    private static final int MIN_DURATION_LENGTH = 2;
    private static final int MIN_GENDER_LENGTH = 2;
    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 120;
    private static final int MIN_SEVERITY = 1;
    private static final int MAX_SEVERITY = 10;

    private SymptomValidationUtils() {
    }

    public static String validate(String symptoms, String duration, int severity, int age, String gender) {
        if (TextUtils.isEmpty(symptoms) || symptoms.trim().length() < MIN_SYMPTOM_LENGTH) {
            return "Describe your symptoms (at least " + MIN_SYMPTOM_LENGTH + " characters).";
        }
        if (TextUtils.isEmpty(duration) || duration.trim().length() < MIN_DURATION_LENGTH) {
            return "Enter how long you have had these symptoms (e.g. 2 days, 3 hours).";
        }
        if (severity < MIN_SEVERITY || severity > MAX_SEVERITY) {
            return "Select a severity level between " + MIN_SEVERITY + " and " + MAX_SEVERITY + ".";
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            return "Enter a valid age between " + MIN_AGE + " and " + MAX_AGE + ".";
        }
        if (TextUtils.isEmpty(gender) || gender.trim().length() < MIN_GENDER_LENGTH) {
            return "Enter your gender.";
        }
        return null;
    }
}

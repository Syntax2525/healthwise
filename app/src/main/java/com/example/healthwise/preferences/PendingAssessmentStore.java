package com.example.healthwise.preferences;

import com.example.healthwise.models.AssessmentRequest;

/**
 * In-memory store for the current symptom assessment awaiting AI analysis.
 */
public final class PendingAssessmentStore {

    private static AssessmentRequest pendingRequest;

    private PendingAssessmentStore() {
    }

    public static void save(AssessmentRequest request) {
        pendingRequest = request;
    }

    public static AssessmentRequest get() {
        return pendingRequest;
    }

    public static boolean hasPendingRequest() {
        return pendingRequest != null;
    }

    public static void clear() {
        pendingRequest = null;
    }
}

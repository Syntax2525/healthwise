package com.example.healthwise.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.database.entities.Assessment;
import com.example.healthwise.database.entities.User;
import com.example.healthwise.preferences.SessionPreferences;
import com.example.healthwise.repositories.AssessmentRepository;
import com.example.healthwise.repositories.UserRepository;
import com.example.healthwise.utils.DateTimeUtils;
import com.example.healthwise.utils.HealthTipsProvider;

public class DashboardViewModel extends AndroidViewModel {

    private final SessionPreferences sessionPreferences;
    private final UserRepository userRepository;
    private final AssessmentRepository assessmentRepository;

    private final MutableLiveData<String> greeting = new MutableLiveData<>();
    private final MutableLiveData<String> healthTip = new MutableLiveData<>();
    private final MediatorLiveData<AssessmentUiState> latestAssessment = new MediatorLiveData<>();

    private boolean loaded;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        HealthWiseApplication app = (HealthWiseApplication) application;
        sessionPreferences = new SessionPreferences(application);
        userRepository = app.getUserRepository();
        assessmentRepository = app.getAssessmentRepository();
        healthTip.setValue(HealthTipsProvider.getDailyTipFormatted());
    }

    public LiveData<String> getGreeting() {
        return greeting;
    }

    public LiveData<String> getHealthTip() {
        return healthTip;
    }

    public LiveData<AssessmentUiState> getLatestAssessment() {
        return latestAssessment;
    }

    public void loadDashboardData() {
        if (loaded) {
            return;
        }
        loaded = true;

        if (!sessionPreferences.isLoggedIn()) {
            greeting.setValue("Hello, guest!");
            latestAssessment.setValue(AssessmentUiState.empty());
            return;
        }

        long userId = sessionPreferences.getUserId();
        greeting.setValue(buildGreeting(sessionPreferences.getUserName()));

        LiveData<User> userSource = userRepository.observeUserById(userId);
        latestAssessment.addSource(userSource, user -> {
            if (user != null) {
                greeting.setValue(buildGreeting(user.getFullName()));
            }
        });

        LiveData<Assessment> assessmentSource = assessmentRepository.observeLatestAssessmentByUserId(userId);
        latestAssessment.addSource(assessmentSource, assessment -> {
            if (assessment == null) {
                latestAssessment.setValue(AssessmentUiState.empty());
            } else {
                latestAssessment.setValue(mapAssessment(assessment));
            }
        });
    }

    private String buildGreeting(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Hello!";
        }
        String firstName = fullName.trim().split("\\s+")[0];
        return "Hello, " + firstName + "!";
    }

    private AssessmentUiState mapAssessment(Assessment assessment) {
        String title = assessment.getSymptoms();
        if (title == null || title.trim().isEmpty()) {
            title = "Recent symptom check";
        }
        String summary = assessment.getRecommendations();
        if (summary == null || summary.trim().isEmpty()) {
            summary = assessment.getConditions();
        }
        if (summary == null || summary.trim().isEmpty()) {
            summary = "Open history to review your full assessment details.";
        }
        return new AssessmentUiState(
                title,
                DateTimeUtils.formatRelativeTime(assessment.getDate()),
                assessment.getRiskLevel(),
                summary,
                true
        );
    }

    public static class AssessmentUiState {
        private final String title;
        private final String time;
        private final String riskLevel;
        private final String summary;
        private final boolean hasAssessment;

        public AssessmentUiState(String title, String time, String riskLevel, String summary, boolean hasAssessment) {
            this.title = title;
            this.time = time;
            this.riskLevel = riskLevel;
            this.summary = summary;
            this.hasAssessment = hasAssessment;
        }

        public static AssessmentUiState empty() {
            return new AssessmentUiState(
                    "No assessments yet",
                    "",
                    "Start your first check",
                    "Use the Symptom Checker to get AI-powered health guidance.",
                    false
            );
        }

        public String getTitle() {
            return title;
        }

        public String getTime() {
            return time;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public String getSummary() {
            return summary;
        }

        public boolean hasAssessment() {
            return hasAssessment;
        }
    }
}

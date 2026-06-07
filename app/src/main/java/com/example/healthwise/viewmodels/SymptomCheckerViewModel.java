package com.example.healthwise.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.database.entities.User;
import com.example.healthwise.models.AssessmentRequest;
import com.example.healthwise.preferences.PendingAssessmentStore;
import com.example.healthwise.preferences.SessionPreferences;
import com.example.healthwise.repositories.UserRepository;
import com.example.healthwise.utils.EmergencyDetector;
import com.example.healthwise.utils.SymptomValidationUtils;

public class SymptomCheckerViewModel extends AndroidViewModel {

    private final SessionPreferences sessionPreferences;
    private final UserRepository userRepository;

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToAnalysis = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToEmergency = new MutableLiveData<>();
    private final MutableLiveData<PatientPrefill> patientPrefill = new MutableLiveData<>();

    public SymptomCheckerViewModel(@NonNull Application application) {
        super(application);
        HealthWiseApplication app = (HealthWiseApplication) application;
        sessionPreferences = new SessionPreferences(application);
        userRepository = app.getUserRepository();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getNavigateToAnalysis() {
        return navigateToAnalysis;
    }

    public LiveData<Boolean> getNavigateToEmergency() {
        return navigateToEmergency;
    }

    public LiveData<PatientPrefill> getPatientPrefill() {
        return patientPrefill;
    }

    public void loadPatientDetails() {
        if (!sessionPreferences.isLoggedIn()) {
            patientPrefill.setValue(new PatientPrefill("", ""));
            return;
        }

        User user = userRepository.getUserByIdSync(sessionPreferences.getUserId());
        if (user == null) {
            patientPrefill.setValue(new PatientPrefill("", ""));
            return;
        }

        String ageText = user.getAge() > 0 ? String.valueOf(user.getAge()) : "";
        String gender = user.getGender() != null ? user.getGender() : "";
        patientPrefill.setValue(new PatientPrefill(ageText, gender));
    }

    public void submitAssessment(String symptoms, String duration, int severity, String ageText, String gender) {
        errorMessage.setValue(null);
        navigateToAnalysis.setValue(null);
        navigateToEmergency.setValue(null);

        int age;
        try {
            age = Integer.parseInt(ageText.trim());
        } catch (NumberFormatException exception) {
            errorMessage.setValue("Enter a valid age.");
            return;
        }

        String validationError = SymptomValidationUtils.validate(
                symptoms,
                duration,
                severity,
                age,
                gender
        );
        if (validationError != null) {
            errorMessage.setValue(validationError);
            return;
        }

        String trimmedSymptoms = symptoms.trim();
        if (EmergencyDetector.isEmergency(trimmedSymptoms)) {
            navigateToEmergency.setValue(true);
            return;
        }

        long userId = sessionPreferences.isLoggedIn() ? sessionPreferences.getUserId() : -1L;
        AssessmentRequest request = new AssessmentRequest(
                userId,
                trimmedSymptoms,
                duration.trim(),
                severity,
                age,
                gender.trim()
        );
        PendingAssessmentStore.save(request);
        navigateToAnalysis.setValue(true);
    }

    public void clearNavigationEvents() {
        navigateToAnalysis.setValue(null);
        navigateToEmergency.setValue(null);
    }

    public static class PatientPrefill {
        private final String age;
        private final String gender;

        public PatientPrefill(String age, String gender) {
            this.age = age;
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }
    }
}

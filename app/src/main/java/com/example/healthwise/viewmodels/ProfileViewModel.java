package com.example.healthwise.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.database.entities.User;
import com.example.healthwise.preferences.SessionPreferences;
import com.example.healthwise.repositories.UserRepository;

public class ProfileViewModel extends AndroidViewModel {

    private final SessionPreferences sessionPreferences;
    private final UserRepository userRepository;

    private final MediatorLiveData<ProfileUiState> profile = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> logoutComplete = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        HealthWiseApplication app = (HealthWiseApplication) application;
        sessionPreferences = new SessionPreferences(application);
        userRepository = app.getUserRepository();
    }

    public LiveData<ProfileUiState> getProfile() {
        return profile;
    }

    public LiveData<Boolean> getLogoutComplete() {
        return logoutComplete;
    }

    public void loadProfile() {
        if (!sessionPreferences.isLoggedIn()) {
            profile.setValue(new ProfileUiState("Guest User", ""));
            return;
        }

        profile.setValue(new ProfileUiState(
                sessionPreferences.getUserName(),
                sessionPreferences.getUserEmail()
        ));

        LiveData<User> userSource = userRepository.observeUserById(sessionPreferences.getUserId());
        profile.addSource(userSource, user -> {
            if (user != null) {
                profile.setValue(new ProfileUiState(user.getFullName(), user.getEmail()));
            }
        });
    }

    public void logout() {
        sessionPreferences.clearSession();
        logoutComplete.setValue(true);
    }

    public static class ProfileUiState {
        private final String fullName;
        private final String email;

        public ProfileUiState(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }
    }
}

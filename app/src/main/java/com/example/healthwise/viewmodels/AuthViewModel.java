package com.example.healthwise.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.database.entities.User;
import com.example.healthwise.preferences.SessionPreferences;
import com.example.healthwise.repositories.RepositoryCallback;
import com.example.healthwise.repositories.UserRepository;
import com.example.healthwise.utils.ValidationUtils;

public class AuthViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final SessionPreferences sessionPreferences;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authSuccess = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        HealthWiseApplication app = (HealthWiseApplication) application;
        userRepository = app.getUserRepository();
        sessionPreferences = new SessionPreferences(application);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getAuthSuccess() {
        return authSuccess;
    }

    public boolean hasActiveSession() {
        return sessionPreferences.isLoggedIn();
    }

    public void login(String email, String password) {
        String validationError = ValidationUtils.validateLoginInput(email, password);
        if (validationError != null) {
            errorMessage.setValue(validationError);
            return;
        }

        loading.setValue(true);
        userRepository.login(email.trim(), password, new RepositoryCallback<User>() {
            @Override
            public void onSuccess(User result) {
                sessionPreferences.saveSession(result);
                loading.postValue(false);
                authSuccess.postValue(true);
            }

            @Override
            public void onError(Exception exception) {
                loading.postValue(false);
                errorMessage.postValue(exception.getMessage());
            }
        });
    }

    public void register(String fullName, String email, String password) {
        String validationError = ValidationUtils.validateRegistrationInput(fullName, email, password);
        if (validationError != null) {
            errorMessage.setValue(validationError);
            return;
        }

        User user = new User(
                fullName.trim(),
                email.trim().toLowerCase(),
                password,
                0,
                "Not specified"
        );

        loading.setValue(true);
        userRepository.register(user, new RepositoryCallback<Long>() {
            @Override
            public void onSuccess(Long result) {
                user.setId(result);
                sessionPreferences.saveSession(user);
                loading.postValue(false);
                authSuccess.postValue(true);
            }

            @Override
            public void onError(Exception exception) {
                loading.postValue(false);
                errorMessage.postValue(exception.getMessage());
            }
        });
    }

    public void clearMessages() {
        errorMessage.setValue(null);
        authSuccess.setValue(null);
    }
}

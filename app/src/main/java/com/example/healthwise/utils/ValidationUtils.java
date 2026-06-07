package com.example.healthwise.utils;

import android.text.TextUtils;
import android.util.Patterns;

public final class ValidationUtils {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_NAME_LENGTH = 2;

    private ValidationUtils() {
    }

    public static String validateLoginInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            return "Email is required.";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return "Enter a valid email address.";
        }
        if (TextUtils.isEmpty(password)) {
            return "Password is required.";
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters.";
        }
        return null;
    }

    public static String validateRegistrationInput(String fullName, String email, String password) {
        if (TextUtils.isEmpty(fullName) || fullName.trim().length() < MIN_NAME_LENGTH) {
            return "Enter your full name (at least " + MIN_NAME_LENGTH + " characters).";
        }
        if (TextUtils.isEmpty(email)) {
            return "Email is required.";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return "Enter a valid email address.";
        }
        if (TextUtils.isEmpty(password)) {
            return "Password is required.";
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters.";
        }
        return null;
    }
}

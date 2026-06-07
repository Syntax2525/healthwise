package com.example.healthwise.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthwise.database.entities.User;

public class SessionPreferences {

    private static final String PREFS_NAME = "healthwise_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_LOGGED_IN = "logged_in";

    private final SharedPreferences preferences;

    public SessionPreferences(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(User user) {
        preferences.edit()
                .putLong(KEY_USER_ID, user.getId())
                .putString(KEY_USER_NAME, user.getFullName())
                .putString(KEY_USER_EMAIL, user.getEmail())
                .putBoolean(KEY_LOGGED_IN, true)
                .apply();
    }

    public void clearSession() {
        preferences.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_LOGGED_IN, false) && getUserId() > 0;
    }

    public long getUserId() {
        return preferences.getLong(KEY_USER_ID, -1L);
    }

    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, "");
    }

    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }
}

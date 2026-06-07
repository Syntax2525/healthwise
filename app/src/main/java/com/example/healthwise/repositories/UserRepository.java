package com.example.healthwise.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.healthwise.database.AppDatabase;
import com.example.healthwise.database.dao.UserDao;
import com.example.healthwise.database.entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final ExecutorService executor;

    public UserRepository(Context context) {
        this(AppDatabase.getInstance(context));
    }

    public UserRepository(AppDatabase database) {
        userDao = database.userDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void register(User user, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            try {
                if (userDao.emailExists(user.getEmail())) {
                    callback.onError(new IllegalStateException("Email is already registered."));
                    return;
                }
                long id = userDao.insert(user);
                callback.onSuccess(id);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void updateUser(User user, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                userDao.update(user);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void deleteUser(User user, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                userDao.delete(user);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void login(String email, String password, RepositoryCallback<User> callback) {
        executor.execute(() -> {
            try {
                User user = userDao.login(email, password);
                if (user == null) {
                    callback.onError(new IllegalStateException("Invalid email or password."));
                } else {
                    callback.onSuccess(user);
                }
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void getUserByEmail(String email, RepositoryCallback<User> callback) {
        executor.execute(() -> {
            try {
                callback.onSuccess(userDao.getUserByEmail(email));
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public User getUserByIdSync(long userId) {
        return userDao.getUserById(userId);
    }

    public LiveData<User> observeUserById(long userId) {
        return userDao.observeUserById(userId);
    }

    public LiveData<User> observeUserByEmail(String email) {
        return userDao.observeUserByEmail(email);
    }

    public LiveData<List<User>> observeAllUsers() {
        return userDao.observeAllUsers();
    }

    public boolean emailExists(String email) {
        return userDao.emailExists(email);
    }
}

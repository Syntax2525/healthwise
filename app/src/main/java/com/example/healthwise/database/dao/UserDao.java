package com.example.healthwise.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.healthwise.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(long userId);

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> observeUserById(long userId);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    LiveData<User> observeUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    boolean emailExists(String email);

    @Query("SELECT * FROM users ORDER BY fullName ASC")
    LiveData<List<User>> observeAllUsers();
}

package com.example.healthwise.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.healthwise.database.entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments WHERE id = :assessmentId")
    Assessment getAssessmentById(long assessmentId);

    @Query("SELECT * FROM assessments WHERE id = :assessmentId")
    LiveData<Assessment> observeAssessmentById(long assessmentId);

    @Query("SELECT * FROM assessments WHERE userId = :userId ORDER BY date DESC")
    List<Assessment> getAssessmentsByUserId(long userId);

    @Query("SELECT * FROM assessments WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<Assessment>> observeAssessmentsByUserId(long userId);

    @Query("SELECT * FROM assessments WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    LiveData<Assessment> observeLatestAssessmentByUserId(long userId);

    @Query("DELETE FROM assessments WHERE userId = :userId")
    void deleteAllByUserId(long userId);

    @Query("SELECT COUNT(*) FROM assessments WHERE userId = :userId")
    LiveData<Integer> observeAssessmentCountByUserId(long userId);
}

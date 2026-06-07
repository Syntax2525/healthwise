package com.example.healthwise.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.healthwise.database.AppDatabase;
import com.example.healthwise.database.dao.AssessmentDao;
import com.example.healthwise.database.entities.Assessment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssessmentRepository {

    private final AssessmentDao assessmentDao;
    private final ExecutorService executor;

    public AssessmentRepository(Context context) {
        this(AppDatabase.getInstance(context));
    }

    public AssessmentRepository(AppDatabase database) {
        assessmentDao = database.assessmentDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertAssessment(Assessment assessment, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            try {
                long id = assessmentDao.insert(assessment);
                callback.onSuccess(id);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void updateAssessment(Assessment assessment, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                assessmentDao.update(assessment);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void deleteAssessment(Assessment assessment, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                assessmentDao.delete(assessment);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void deleteAllByUserId(long userId, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                assessmentDao.deleteAllByUserId(userId);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public Assessment getAssessmentByIdSync(long assessmentId) {
        return assessmentDao.getAssessmentById(assessmentId);
    }

    public List<Assessment> getAssessmentsByUserIdSync(long userId) {
        return assessmentDao.getAssessmentsByUserId(userId);
    }

    public LiveData<Assessment> observeAssessmentById(long assessmentId) {
        return assessmentDao.observeAssessmentById(assessmentId);
    }

    public LiveData<List<Assessment>> observeAssessmentsByUserId(long userId) {
        return assessmentDao.observeAssessmentsByUserId(userId);
    }

    public LiveData<Assessment> observeLatestAssessmentByUserId(long userId) {
        return assessmentDao.observeLatestAssessmentByUserId(userId);
    }

    public LiveData<Integer> observeAssessmentCountByUserId(long userId) {
        return assessmentDao.observeAssessmentCountByUserId(userId);
    }
}

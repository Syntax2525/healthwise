package com.example.healthwise;

import android.app.Application;

import com.example.healthwise.database.AppDatabase;
import com.example.healthwise.repositories.ArticleRepository;
import com.example.healthwise.repositories.AssessmentRepository;
import com.example.healthwise.repositories.BookmarkRepository;
import com.example.healthwise.repositories.UserRepository;

public class HealthWiseApplication extends Application {

    private static HealthWiseApplication instance;

    private AppDatabase database;
    private UserRepository userRepository;
    private AssessmentRepository assessmentRepository;
    private ArticleRepository articleRepository;
    private BookmarkRepository bookmarkRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);
    }

    public static HealthWiseApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository(database);
        }
        return userRepository;
    }

    public AssessmentRepository getAssessmentRepository() {
        if (assessmentRepository == null) {
            assessmentRepository = new AssessmentRepository(database);
        }
        return assessmentRepository;
    }

    public ArticleRepository getArticleRepository() {
        if (articleRepository == null) {
            articleRepository = new ArticleRepository(database);
        }
        return articleRepository;
    }

    public BookmarkRepository getBookmarkRepository() {
        if (bookmarkRepository == null) {
            bookmarkRepository = new BookmarkRepository(database);
        }
        return bookmarkRepository;
    }
}

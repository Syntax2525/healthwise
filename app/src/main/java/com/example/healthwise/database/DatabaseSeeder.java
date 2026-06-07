package com.example.healthwise.database;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.healthwise.database.entities.Article;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

final class DatabaseSeeder {

    static final RoomDatabase.Callback CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(DatabaseSeeder::seedArticles);
        }
    };

    private DatabaseSeeder() {
    }

    private static void seedArticles() {
        AppDatabase database = AppDatabase.getInstance(
                com.example.healthwise.HealthWiseApplication.getInstance()
        );
        List<Article> articles = Arrays.asList(
                new Article(
                        "Eat Better Every Day",
                        "Nutrition",
                        "Simple habits for balanced meals and steady energy throughout the day.",
                        ""
                ),
                new Article(
                        "Improve Your Sleep Quality",
                        "Sleep",
                        "Wind-down routines that help you rest and recover more effectively.",
                        ""
                ),
                new Article(
                        "Stay Active at Home",
                        "Fitness",
                        "Low-impact exercises you can do without equipment at home.",
                        ""
                ),
                new Article(
                        "Staying Hydrated",
                        "Hydration",
                        "Why water matters and how much you may need daily for optimal health.",
                        ""
                )
        );
        database.articleDao().insertAll(articles);
    }
}

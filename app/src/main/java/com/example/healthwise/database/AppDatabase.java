package com.example.healthwise.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.healthwise.database.dao.ArticleDao;
import com.example.healthwise.database.dao.AssessmentDao;
import com.example.healthwise.database.dao.BookmarkDao;
import com.example.healthwise.database.dao.UserDao;
import com.example.healthwise.database.entities.Article;
import com.example.healthwise.database.entities.Assessment;
import com.example.healthwise.database.entities.Bookmark;
import com.example.healthwise.database.entities.User;

@Database(
        entities = {User.class, Assessment.class, Article.class, Bookmark.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "healthwise_db";
    private static volatile AppDatabase instance;

    public abstract UserDao userDao();

    public abstract AssessmentDao assessmentDao();

    public abstract ArticleDao articleDao();

    public abstract BookmarkDao bookmarkDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(DatabaseSeeder.CALLBACK)
                            .build();
                }
            }
        }
        return instance;
    }
}

package com.example.healthwise.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.healthwise.database.AppDatabase;
import com.example.healthwise.database.dao.BookmarkDao;
import com.example.healthwise.database.entities.Bookmark;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarkRepository {

    private final BookmarkDao bookmarkDao;
    private final ExecutorService executor;

    public BookmarkRepository(Context context) {
        this(AppDatabase.getInstance(context));
    }

    public BookmarkRepository(AppDatabase database) {
        bookmarkDao = database.bookmarkDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void addBookmark(long userId, long articleId, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            try {
                long id = bookmarkDao.insert(new Bookmark(userId, articleId));
                callback.onSuccess(id);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void removeBookmark(long userId, long articleId, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                bookmarkDao.deleteByUserAndArticle(userId, articleId);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void toggleBookmark(long userId, long articleId, RepositoryCallback<Boolean> callback) {
        executor.execute(() -> {
            try {
                if (bookmarkDao.isBookmarked(userId, articleId)) {
                    bookmarkDao.deleteByUserAndArticle(userId, articleId);
                    callback.onSuccess(false);
                } else {
                    bookmarkDao.insert(new Bookmark(userId, articleId));
                    callback.onSuccess(true);
                }
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void deleteAllByUserId(long userId, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                bookmarkDao.deleteAllByUserId(userId);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public Bookmark getBookmarkSync(long userId, long articleId) {
        return bookmarkDao.getBookmark(userId, articleId);
    }

    public boolean isBookmarkedSync(long userId, long articleId) {
        return bookmarkDao.isBookmarked(userId, articleId);
    }

    public LiveData<Boolean> observeIsBookmarked(long userId, long articleId) {
        return bookmarkDao.observeIsBookmarked(userId, articleId);
    }

    public LiveData<List<Bookmark>> observeBookmarksByUserId(long userId) {
        return bookmarkDao.observeBookmarksByUserId(userId);
    }
}

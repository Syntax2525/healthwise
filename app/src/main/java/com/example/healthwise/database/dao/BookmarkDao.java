package com.example.healthwise.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.healthwise.database.entities.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);

    @Query("SELECT * FROM bookmarks WHERE userId = :userId AND articleId = :articleId LIMIT 1")
    Bookmark getBookmark(long userId, long articleId);

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE userId = :userId AND articleId = :articleId)")
    LiveData<Boolean> observeIsBookmarked(long userId, long articleId);

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE userId = :userId AND articleId = :articleId)")
    boolean isBookmarked(long userId, long articleId);

    @Query("SELECT * FROM bookmarks WHERE userId = :userId ORDER BY id DESC")
    LiveData<List<Bookmark>> observeBookmarksByUserId(long userId);

    @Query("DELETE FROM bookmarks WHERE userId = :userId AND articleId = :articleId")
    void deleteByUserAndArticle(long userId, long articleId);

    @Query("DELETE FROM bookmarks WHERE userId = :userId")
    void deleteAllByUserId(long userId);
}

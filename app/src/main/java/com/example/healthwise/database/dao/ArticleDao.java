package com.example.healthwise.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.healthwise.database.entities.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Article> articles);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM articles WHERE id = :articleId")
    Article getArticleById(long articleId);

    @Query("SELECT * FROM articles WHERE id = :articleId")
    LiveData<Article> observeArticleById(long articleId);

    @Query("SELECT * FROM articles ORDER BY title ASC")
    LiveData<List<Article>> observeAllArticles();

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY title ASC")
    LiveData<List<Article>> observeArticlesByCategory(String category);

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' ORDER BY title ASC")
    LiveData<List<Article>> searchArticles(String query);

    @Query("SELECT a.* FROM articles a INNER JOIN bookmarks b ON a.id = b.articleId WHERE b.userId = :userId ORDER BY b.id DESC")
    LiveData<List<Article>> observeBookmarkedArticles(long userId);

    @Query("DELETE FROM articles")
    void deleteAll();
}

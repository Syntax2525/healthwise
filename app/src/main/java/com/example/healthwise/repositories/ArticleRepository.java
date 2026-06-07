package com.example.healthwise.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.healthwise.database.AppDatabase;
import com.example.healthwise.database.dao.ArticleDao;
import com.example.healthwise.database.entities.Article;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleRepository {

    private final ArticleDao articleDao;
    private final ExecutorService executor;

    public ArticleRepository(Context context) {
        this(AppDatabase.getInstance(context));
    }

    public ArticleRepository(AppDatabase database) {
        articleDao = database.articleDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertArticle(Article article, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            try {
                long id = articleDao.insert(article);
                callback.onSuccess(id);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void insertArticles(List<Article> articles, RepositoryCallback<List<Long>> callback) {
        executor.execute(() -> {
            try {
                List<Long> ids = articleDao.insertAll(articles);
                callback.onSuccess(ids);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void updateArticle(Article article, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                articleDao.update(article);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public void deleteArticle(Article article, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                articleDao.delete(article);
                callback.onSuccess(null);
            } catch (Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public Article getArticleByIdSync(long articleId) {
        return articleDao.getArticleById(articleId);
    }

    public LiveData<Article> observeArticleById(long articleId) {
        return articleDao.observeArticleById(articleId);
    }

    public LiveData<List<Article>> observeAllArticles() {
        return articleDao.observeAllArticles();
    }

    public LiveData<List<Article>> observeArticlesByCategory(String category) {
        return articleDao.observeArticlesByCategory(category);
    }

    public LiveData<List<Article>> searchArticles(String query) {
        return articleDao.searchArticles(query);
    }

    public LiveData<List<Article>> observeBookmarkedArticles(long userId) {
        return articleDao.observeBookmarkedArticles(userId);
    }
}

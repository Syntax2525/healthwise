package com.example.healthwise.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "bookmarks",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Article.class,
                        parentColumns = "id",
                        childColumns = "articleId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("userId"),
                @Index("articleId"),
                @Index(value = {"userId", "articleId"}, unique = true)
        }
)
public class Bookmark {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;
    private long articleId;

    public Bookmark(long userId, long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}

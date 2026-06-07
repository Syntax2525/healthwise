package com.example.healthwise.utils;

import com.example.healthwise.models.Article;

public final class ArticleMapper {

    private static final int SUMMARY_MAX_LENGTH = 120;

    private ArticleMapper() {
    }

    public static Article toUiModel(com.example.healthwise.database.entities.Article entity) {
        String content = entity.getContent() == null ? "" : entity.getContent();
        String summary = content.length() > SUMMARY_MAX_LENGTH
                ? content.substring(0, SUMMARY_MAX_LENGTH) + "..."
                : content;
        return new Article(
                entity.getCategory(),
                entity.getTitle(),
                summary,
                "4 min",
                content
        );
    }
}

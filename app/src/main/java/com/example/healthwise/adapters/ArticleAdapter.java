package com.example.healthwise.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthwise.databinding.ItemArticleBinding;
import com.example.healthwise.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    private final List<Article> articles = new ArrayList<>();
    private final OnArticleClickListener listener;

    public ArticleAdapter(OnArticleClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Article> newArticles) {
        articles.clear();
        if (newArticles != null) {
            articles.addAll(newArticles);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArticleBinding binding = ItemArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        private final ItemArticleBinding binding;

        ArticleViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Article article) {
            binding.articleCategory.setText(article.getCategory());
            binding.articleTitle.setText(article.getTitle());
            binding.articleSummary.setText(article.getSummary());
            binding.articleReadTime.setText(article.getReadTime() + " read");
            binding.getRoot().setOnClickListener(v -> listener.onArticleClick(article));
        }
    }
}

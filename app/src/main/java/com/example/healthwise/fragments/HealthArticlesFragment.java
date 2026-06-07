package com.example.healthwise.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.R;
import com.example.healthwise.adapters.ArticleAdapter;
import com.example.healthwise.databinding.FragmentHealthArticlesBinding;
import com.example.healthwise.models.Article;
import com.example.healthwise.repositories.ArticleRepository;
import com.example.healthwise.utils.ArticleMapper;
import com.example.healthwise.utils.NavigationArgs;

import java.util.ArrayList;
import java.util.List;

public class HealthArticlesFragment extends Fragment {

    private FragmentHealthArticlesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthArticlesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArticleAdapter adapter = new ArticleAdapter(this::openArticleDetail);
        binding.articlesRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.articlesRecycler.setAdapter(adapter);

        ArticleRepository articleRepository = ((HealthWiseApplication) requireActivity().getApplication())
                .getArticleRepository();

        String query = getArguments() != null
                ? getArguments().getString(NavigationArgs.SEARCH_QUERY, "")
                : "";

        if (TextUtils.isEmpty(query)) {
            articleRepository.observeAllArticles().observe(getViewLifecycleOwner(), articles ->
                    adapter.submitList(mapArticles(articles)));
        } else {
            articleRepository.searchArticles(query).observe(getViewLifecycleOwner(), articles ->
                    adapter.submitList(mapArticles(articles)));
        }
    }

    private List<Article> mapArticles(List<com.example.healthwise.database.entities.Article> articles) {
        List<Article> mapped = new ArrayList<>();
        if (articles == null) {
            return mapped;
        }
        for (com.example.healthwise.database.entities.Article article : articles) {
            mapped.add(ArticleMapper.toUiModel(article));
        }
        return mapped;
    }

    private void openArticleDetail(Article article) {
        Bundle args = new Bundle();
        args.putString(NavigationArgs.ARTICLE_TITLE, article.getTitle());
        args.putString(NavigationArgs.ARTICLE_META, article.getMetaLabel());
        args.putString(NavigationArgs.ARTICLE_BODY, article.getBody());
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_healthArticlesFragment_to_articleDetailFragment, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

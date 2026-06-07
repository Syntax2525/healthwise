package com.example.healthwise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthwise.databinding.FragmentArticleDetailBinding;
import com.example.healthwise.utils.NavigationArgs;

public class ArticleDetailFragment extends Fragment {

    private FragmentArticleDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        setTextIfPresent(binding.articleTitle, args.getString(NavigationArgs.ARTICLE_TITLE));
        setTextIfPresent(binding.articleMeta, args.getString(NavigationArgs.ARTICLE_META));
        setTextIfPresent(binding.articleBody, args.getString(NavigationArgs.ARTICLE_BODY));
    }

    private void setTextIfPresent(android.widget.TextView textView, String value) {
        if (value != null) {
            textView.setText(value);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

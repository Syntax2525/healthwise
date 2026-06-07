package com.example.healthwise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.healthwise.adapters.HistoryAdapter;
import com.example.healthwise.databinding.FragmentSymptomHistoryBinding;
import com.example.healthwise.utils.SampleDataProvider;

public class SymptomHistoryFragment extends Fragment {

    private FragmentSymptomHistoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSymptomHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryAdapter adapter = new HistoryAdapter();
        binding.historyRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecycler.setAdapter(adapter);
        adapter.submitList(SampleDataProvider.getHistoryEntries());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

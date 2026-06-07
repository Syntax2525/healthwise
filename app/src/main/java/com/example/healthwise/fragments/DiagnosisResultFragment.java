package com.example.healthwise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthwise.databinding.FragmentDiagnosisResultBinding;
import com.example.healthwise.models.DiagnosisResult;

public class DiagnosisResultFragment extends Fragment {

    private FragmentDiagnosisResultBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiagnosisResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DiagnosisResult result = DiagnosisResult.fromBundle(getArguments());
        if (result == null) {
            return;
        }

        binding.resultTitle.setText(result.getResultTitle());
        binding.riskLevel.setText(result.getRiskLevel());
        binding.confidence.setText(result.getConfidence());
        binding.resultSummary.setText(result.getResultSummary());
        binding.recommendations.setText(result.getRecommendations());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

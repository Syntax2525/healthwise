package com.example.healthwise.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthwise.R;
import com.example.healthwise.databinding.FragmentAnalysisLoadingBinding;
import com.example.healthwise.models.AssessmentRequest;
import com.example.healthwise.models.DiagnosisResult;
import com.example.healthwise.preferences.PendingAssessmentStore;
import com.example.healthwise.utils.SampleDataProvider;

public class AnalysisLoadingFragment extends Fragment {

    private static final long ANALYSIS_DELAY_MS = 2500L;

    private FragmentAnalysisLoadingBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable navigateToResultRunnable = this::navigateToResult;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnalysisLoadingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!PendingAssessmentStore.hasPendingRequest()) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
            return;
        }

        handler.postDelayed(navigateToResultRunnable, ANALYSIS_DELAY_MS);
    }

    private void navigateToResult() {
        if (!isAdded() || binding == null) {
            return;
        }

        AssessmentRequest request = PendingAssessmentStore.get();
        DiagnosisResult result = request != null
                ? buildPlaceholderResult(request)
                : SampleDataProvider.getSampleDiagnosis();

        Navigation.findNavController(binding.getRoot())
                .navigate(
                        R.id.action_analysisLoadingFragment_to_diagnosisResultFragment,
                        result.toBundle()
                );
    }

    private DiagnosisResult buildPlaceholderResult(AssessmentRequest request) {
        return new DiagnosisResult(
                "Analyzing: " + request.getSymptoms(),
                "Severity " + request.getSeverity() + "/10",
                "Pending AI analysis",
                "Duration: " + request.getDuration()
                        + " | Age: " + request.getAge()
                        + " | Gender: " + request.getGender(),
                "Full AI recommendations will appear after Gemini integration."
        );
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacks(navigateToResultRunnable);
        super.onDestroyView();
        binding = null;
    }
}

package com.example.healthwise.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.R;
import com.example.healthwise.databinding.FragmentSymptomCheckerBinding;
import com.example.healthwise.viewmodels.SymptomCheckerViewModel;
import com.example.healthwise.viewmodels.ViewModelFactory;
import com.google.android.material.slider.Slider;

public class SymptomCheckerFragment extends Fragment {

    private FragmentSymptomCheckerBinding binding;
    private SymptomCheckerViewModel symptomCheckerViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSymptomCheckerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(
                (HealthWiseApplication) requireActivity().getApplication()
        );
        symptomCheckerViewModel = new ViewModelProvider(this, factory).get(SymptomCheckerViewModel.class);
        symptomCheckerViewModel.loadPatientDetails();

        binding.severitySlider.addOnChangeListener((Slider slider, float value, boolean fromUser) ->
                binding.severityValue.setText(((int) value) + "/10"));

        binding.analyzeButton.setOnClickListener(v -> {
            String symptoms = getText(binding.symptomsEditText);
            String duration = getText(binding.durationEditText);
            int severity = (int) binding.severitySlider.getValue();
            String age = getText(binding.ageEditText);
            String gender = getText(binding.genderEditText);
            symptomCheckerViewModel.submitAssessment(symptoms, duration, severity, age, gender);
        });

        symptomCheckerViewModel.getPatientPrefill().observe(getViewLifecycleOwner(), prefill -> {
            if (prefill == null) {
                return;
            }
            if (!TextUtils.isEmpty(prefill.getAge()) && TextUtils.isEmpty(getText(binding.ageEditText))) {
                binding.ageEditText.setText(prefill.getAge());
            }
            if (!TextUtils.isEmpty(prefill.getGender()) && TextUtils.isEmpty(getText(binding.genderEditText))) {
                binding.genderEditText.setText(prefill.getGender());
            }
        });

        symptomCheckerViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        symptomCheckerViewModel.getNavigateToEmergency().observe(getViewLifecycleOwner(), navigate -> {
            if (Boolean.TRUE.equals(navigate)) {
                symptomCheckerViewModel.clearNavigationEvents();
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_symptomCheckerFragment_to_emergencyAlertFragment);
            }
        });

        symptomCheckerViewModel.getNavigateToAnalysis().observe(getViewLifecycleOwner(), navigate -> {
            if (Boolean.TRUE.equals(navigate)) {
                symptomCheckerViewModel.clearNavigationEvents();
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_symptomCheckerFragment_to_analysisLoadingFragment);
            }
        });
    }

    private String getText(android.widget.EditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

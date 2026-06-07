package com.example.healthwise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthwise.R;
import com.example.healthwise.databinding.FragmentOnboardingBinding;
import com.example.healthwise.preferences.SessionPreferences;

public class OnboardingFragment extends Fragment {

    private FragmentOnboardingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionPreferences sessionPreferences = new SessionPreferences(requireContext());
        if (sessionPreferences.isLoggedIn()) {
            Navigation.findNavController(view).navigate(R.id.action_onboardingFragment_to_dashboardFragment);
            return;
        }

        binding.getStartedButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_onboardingFragment_to_loginFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

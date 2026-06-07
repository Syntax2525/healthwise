package com.example.healthwise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.R;
import com.example.healthwise.databinding.FragmentProfileBinding;
import com.example.healthwise.viewmodels.ProfileViewModel;
import com.example.healthwise.viewmodels.ViewModelFactory;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(
                (HealthWiseApplication) requireActivity().getApplication()
        );
        profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        profileViewModel.loadProfile();

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile == null) {
                return;
            }
            binding.profileName.setText(profile.getFullName());
            binding.profileEmail.setText(profile.getEmail());
        });

        binding.profileSettings.setOnClickListener(v -> profileViewModel.logout());

        profileViewModel.getLogoutComplete().observe(getViewLifecycleOwner(), loggedOut -> {
            if (Boolean.TRUE.equals(loggedOut)) {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

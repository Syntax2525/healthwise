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
import com.example.healthwise.databinding.FragmentRegisterBinding;
import com.example.healthwise.viewmodels.AuthViewModel;
import com.example.healthwise.viewmodels.ViewModelFactory;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(
                (HealthWiseApplication) requireActivity().getApplication()
        );
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        binding.createAccountButton.setOnClickListener(v -> {
            String fullName = getText(binding.fullNameEditText);
            String email = getText(binding.emailEditText);
            String password = getText(binding.passwordEditText);
            authViewModel.clearMessages();
            authViewModel.register(fullName, email, password);
        });

        binding.loginLink.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment));

        authViewModel.getLoading().observe(getViewLifecycleOwner(), loading ->
                binding.createAccountButton.setEnabled(!Boolean.TRUE.equals(loading)));

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        authViewModel.getAuthSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_registerFragment_to_dashboardFragment);
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

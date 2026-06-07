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
import com.example.healthwise.databinding.FragmentLoginBinding;
import com.example.healthwise.viewmodels.AuthViewModel;
import com.example.healthwise.viewmodels.ViewModelFactory;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(
                (HealthWiseApplication) requireActivity().getApplication()
        );
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        if (authViewModel.hasActiveSession()) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dashboardFragment);
            return;
        }

        binding.loginButton.setOnClickListener(v -> {
            String email = getText(binding.emailEditText);
            String password = getText(binding.passwordEditText);
            authViewModel.clearMessages();
            authViewModel.login(email, password);
        });

        binding.registerLink.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));

        authViewModel.getLoading().observe(getViewLifecycleOwner(), loading ->
                binding.loginButton.setEnabled(!Boolean.TRUE.equals(loading)));

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        authViewModel.getAuthSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_loginFragment_to_dashboardFragment);
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

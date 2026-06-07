package com.example.healthwise.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.healthwise.HealthWiseApplication;
import com.example.healthwise.R;
import com.example.healthwise.databinding.FragmentDashboardBinding;
import com.example.healthwise.utils.NavigationArgs;
import com.example.healthwise.viewmodels.DashboardViewModel;
import com.example.healthwise.viewmodels.ViewModelFactory;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(
                (HealthWiseApplication) requireActivity().getApplication()
        );
        dashboardViewModel = new ViewModelProvider(this, factory).get(DashboardViewModel.class);
        dashboardViewModel.loadDashboardData();

        binding.emergencyCard.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_emergencyAlertFragment));
        binding.symptomAction.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_symptomCheckerFragment));
        binding.articlesAction.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_healthArticlesFragment));
        binding.historyAction.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_symptomHistoryFragment));
        binding.profileAction.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_profileFragment));

        binding.searchEditText.setOnEditorActionListener(this::onSearchSubmitted);

        dashboardViewModel.getGreeting().observe(getViewLifecycleOwner(), greeting -> {
            if (greeting != null) {
                binding.greetingText.setText(greeting);
            }
        });

        dashboardViewModel.getHealthTip().observe(getViewLifecycleOwner(), tip -> {
            if (tip != null) {
                binding.healthTipText.setText(tip);
            }
        });

        dashboardViewModel.getLatestAssessment().observe(getViewLifecycleOwner(), state -> {
            if (state == null) {
                return;
            }
            binding.recentTitle.setText(state.getTitle());
            binding.recentRisk.setText(state.getRiskLevel());
            binding.recentSummary.setText(state.getSummary());
            if (state.hasAssessment()) {
                binding.recentTime.setText(state.getTime());
                binding.recentTime.setVisibility(View.VISIBLE);
            } else {
                binding.recentTime.setVisibility(View.GONE);
            }
        });
    }

    private boolean onSearchSubmitted(TextView textView, int actionId, KeyEvent event) {
        boolean submitted = actionId == EditorInfo.IME_ACTION_SEARCH
                || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN);
        if (!submitted) {
            return false;
        }

        String query = binding.searchEditText.getText() == null
                ? ""
                : binding.searchEditText.getText().toString().trim();

        Bundle args = new Bundle();
        args.putString(NavigationArgs.SEARCH_QUERY, query);
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_dashboardFragment_to_healthArticlesFragment, args);
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

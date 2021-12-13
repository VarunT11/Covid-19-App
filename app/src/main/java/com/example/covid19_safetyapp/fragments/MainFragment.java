package com.example.covid19_safetyapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covid19_safetyapp.R;
import com.example.covid19_safetyapp.interfaces.ActivityFragmentInterface;
import com.example.covid19_safetyapp.viewmodel.MainViewModel;

import java.util.Locale;

public class MainFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private MainViewModel mainViewModel;
    private NavController navController;

    private TextView tvTotalCases, tvNewTotalCases, tvActiveCases, tvNewActiveCases, tvRecoveredCases, tvNewRecoveredCases, tvDeceasedCases, tvNewDeceasedCases, tvLastUpdatedTime;
    private Button btnViewStateStats;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        setupViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvTotalCases = view.findViewById(R.id.tvTotalCases);
        tvNewTotalCases = view.findViewById(R.id.tvNewTotalCases);
        tvActiveCases = view.findViewById(R.id.tvActiveCases);
        tvNewActiveCases = view.findViewById(R.id.tvNewActiveCases);
        tvRecoveredCases = view.findViewById(R.id.tvRecoveredCases);
        tvNewRecoveredCases = view.findViewById(R.id.tvNewRecoveredCases);
        tvDeceasedCases = view.findViewById(R.id.tvDeceasedCases);
        tvNewDeceasedCases = view.findViewById(R.id.tvNewDeceasedCases);
        tvLastUpdatedTime = view.findViewById(R.id.tvLastUpdatedTime);
        btnViewStateStats = view.findViewById(R.id.btnMainViewStateStats);

        btnViewStateStats.setOnClickListener(view1 -> {
            navController.navigate(R.id.action_mainFragment_to_stateListFragment);
        });
    }

    @Override
    public void setupViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getOverallData().observe(getViewLifecycleOwner(), regionData -> {
            tvTotalCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getTotalCases()));
            tvNewTotalCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getNewTotalCases()));
            tvActiveCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getActiveCases()));
            tvNewActiveCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getNewActiveCases()));
            tvRecoveredCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getRecoveredCases()));
            tvNewRecoveredCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getNewRecoveredCases()));
            tvDeceasedCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getDeceasedCases()));
            tvNewDeceasedCases.setText(String.format(Locale.ENGLISH, "%d", regionData.getNewDeceasedCases()));
        });

        mainViewModel.getLastUpdatedTime().observe(getViewLifecycleOwner(), s -> {
            tvLastUpdatedTime.setText(s);
        });
    }
}
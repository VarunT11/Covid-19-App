package com.example.covid19_safetyapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19_safetyapp.R;
import com.example.covid19_safetyapp.adapters.StateListAdapter;
import com.example.covid19_safetyapp.classes.RegionData;
import com.example.covid19_safetyapp.interfaces.ActivityFragmentInterface;
import com.example.covid19_safetyapp.viewmodel.MainViewModel;

import java.util.ArrayList;

public class StateListFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state_list, container, false);
    }

    private MainViewModel mainViewModel;
    private RecyclerView rcvStateList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewsAndAttachListeners(view);
        setupViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        rcvStateList = view.findViewById(R.id.rcvStateList);
    }

    @Override
    public void setupViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        mainViewModel.getRegionDataList().observe(getViewLifecycleOwner(), regionData -> {
            StateListAdapter stateListAdapter = new StateListAdapter(requireActivity(), regionData);

            LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rcvStateList.setLayoutManager(layoutManager);
            rcvStateList.setAdapter(stateListAdapter);
        });
    }
}
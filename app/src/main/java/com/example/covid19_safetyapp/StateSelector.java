package com.example.covid19_safetyapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;


public class StateSelector extends Fragment {

    RecyclerView rcvStateList;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    public StateSelector() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_state_selector, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rcvStateList=view.findViewById(R.id.rcvStateList);
        rcvStateList.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this.getActivity());
        rcvStateList.setLayoutManager(layoutManager);

        myAdapter=new StateAdapter(this.getActivity(),ApplicationClass.stateDataList);
        rcvStateList.setAdapter(myAdapter);

    }
}

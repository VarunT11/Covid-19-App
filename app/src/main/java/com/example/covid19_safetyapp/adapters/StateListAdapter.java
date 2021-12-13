package com.example.covid19_safetyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19_safetyapp.R;
import com.example.covid19_safetyapp.classes.RegionData;

import java.util.ArrayList;
import java.util.Locale;

public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RegionData> regionDataList;

    public StateListAdapter(Context context, ArrayList<RegionData> regionDataList){
        this.context = context;
        this.regionDataList = regionDataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvActiveCase, tvNewActiveCase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStateListItemName);
            tvActiveCase = itemView.findViewById(R.id.tvStateListItemActiveCase);
            tvNewActiveCase = itemView.findViewById(R.id.tvStateListItemNewActiveCase);
        }
    }

    @NonNull
    @Override
    public StateListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StateListAdapter.ViewHolder holder, int position) {
        RegionData data = regionDataList.get(position);
        holder.tvName.setText(data.getStateName());
        holder.tvActiveCase.setText(String.format(Locale.ENGLISH, "%d", data.getActiveCases()));
        holder.tvNewActiveCase.setText(String.format(Locale.ENGLISH, "%d", data.getNewActiveCases()));
    }

    @Override
    public int getItemCount() {
        return regionDataList.size();
    }
}

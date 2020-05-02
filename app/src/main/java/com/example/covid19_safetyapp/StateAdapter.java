package com.example.covid19_safetyapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private ArrayList<IndianState> stateList;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    StateAdapter(Context context, ArrayList<IndianState> list){
        stateList=list;
        activity=(ItemClicked)context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvStateListItemName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(stateList.indexOf((IndianState)v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.state_selector_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(stateList.get(position));
        holder.tvName.setText(stateList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }
}

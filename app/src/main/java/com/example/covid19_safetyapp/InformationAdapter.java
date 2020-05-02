package com.example.covid19_safetyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {

    private ArrayList<String> information;
    private String type;
    private Context context;

    InformationAdapter(Context context, ArrayList<String> list, String type){
        this.context=context;
        this.information=list;
        this.type=type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgInformation;
        TextView tvInformation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInformation=itemView.findViewById(R.id.imgInformation);
            tvInformation=itemView.findViewById(R.id.tvInformation);
        }
    }

    @NonNull
    @Override
    public InformationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.information_list_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationAdapter.ViewHolder holder, int position) {
        holder.tvInformation.setText(information.get(position));
        int imgID = 0;
        if(type.equals("Symptoms")) {
            switch (position) {
                case 0: {
                    imgID = R.drawable.symptom_0;
                    break;
                }
                case 1: {
                    imgID = R.drawable.symptom_1;
                    break;
                }
                case 2: {
                    imgID = R.drawable.symptom_2;
                    break;
                }
                case 3: {
                    imgID = R.drawable.symptom_3;
                    break;
                }
                case 4: {
                    imgID = R.drawable.symptom_4;
                    break;
                }

                case 5: {
                    imgID = R.drawable.symptom_5;
                    break;
                }
            }
        }
        else {
            switch (position) {
                case 0: {
                    imgID = R.drawable.prevention_0;
                    break;
                }
                case 1: {
                    imgID = R.drawable.prevention_1;
                    break;
                }
                case 2: {
                    imgID = R.drawable.prevention_2;
                    break;
                }
                case 3: {
                    imgID = R.drawable.prevention_3;
                    break;
                }
                case 4: {
                    imgID = R.drawable.prevention_4;
                    break;
                }

                case 5: {
                    imgID = R.drawable.prevention_5;
                    break;
                }
            }
        }
        holder.imgInformation.setImageResource(imgID);

    }

    @Override
    public int getItemCount() {
        return information.size();
    }
}

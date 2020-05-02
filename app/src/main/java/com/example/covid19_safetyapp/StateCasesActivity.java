package com.example.covid19_safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StateCasesActivity extends AppCompatActivity implements StateAdapter.ItemClicked {

    ImageView btnReturn;

    TextView tvStateLabel, tvStateName, tvStateCapital, tvStateTotalCases, tvStateRecovered, tvStateDeceased, tvDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_cases);

        btnReturn=findViewById(R.id.imgStateCasesReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateCasesActivity.this.finish();
            }
        });

        tvStateLabel=findViewById(R.id.tvStateDataNameLabel);
        tvStateName=findViewById(R.id.tvStateDataName);
        tvStateCapital=findViewById(R.id.tvStateDataCapital);
        tvStateTotalCases=findViewById(R.id.tvStateDataTotal);
        tvStateRecovered=findViewById(R.id.tvStateDataRecovered);
        tvStateDeceased=findViewById(R.id.tvStateDataDeceased);
        tvDataSource=findViewById(R.id.tvStateDataSource);

        tvStateLabel.setText("Country");
        tvStateName.setText("India");
        tvStateCapital.setText("New Delhi");
        tvStateTotalCases.setText(Integer.toString(ApplicationClass.TotalCasesIndia));
        tvStateRecovered.setText(Integer.toString(ApplicationClass.RecoveredCasesIndia));
        tvStateDeceased.setText(Integer.toString(ApplicationClass.DeceasedCasesIndia));
        tvDataSource.setText(ApplicationClass.SourceUrl);

        tvDataSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationClass.SourceUrl)));
            }
        });

    }

    @Override
    public void onItemClicked(int index) {
        IndianState tempState=ApplicationClass.stateDataList.get(index);
        tvStateLabel.setText("Name of State");
        tvStateName.setText(tempState.getName());
        tvStateCapital.setText(tempState.getCapital());
        tvStateTotalCases.setText(Integer.toString(tempState.getTotalInfected()));
        tvStateRecovered.setText(Integer.toString(tempState.getRecovered()));
        tvStateDeceased.setText(Integer.toString(tempState.getDeceased()));
        tvDataSource.setText(ApplicationClass.SourceUrl);
    }
}

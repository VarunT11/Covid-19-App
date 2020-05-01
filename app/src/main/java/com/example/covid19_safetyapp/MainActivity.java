package com.example.covid19_safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FetchJsonData.FetchDataCallbackInterface {

    TextView tvCasesTotal,tvCasesActive,tvCasesRecovered,tvCasesDeceased,tvLastUpdated;
    Button btnCheckStateStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCasesTotal=findViewById(R.id.tvMainStatsTotalData);
        tvCasesActive=findViewById(R.id.tvMainStatsActiveData);
        tvCasesRecovered=findViewById(R.id.tvMainStatsRecoveredData);
        tvCasesDeceased=findViewById(R.id.tvMainStatsDeceasedData);
        tvLastUpdated=findViewById(R.id.tvMainStatsLastUpdated);

        UpdateData();

        btnCheckStateStats=findViewById(R.id.btnMainStatsCheckStateStats);

        btnCheckStateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StateCasesActivity.class));
            }
        });

        if(ApplicationClass.isInternetConnection(this))
            new FetchJsonData(ApplicationClass.SourceApiUrl,this).execute();

    }


    public void UpdateData(){
        tvCasesTotal.setText(Integer.toString(ApplicationClass.TotalCasesIndia));
        tvCasesActive.setText(Integer.toString(ApplicationClass.ActiveCasesIndia));
        tvCasesRecovered.setText(Integer.toString(ApplicationClass.RecoveredCasesIndia));
        tvCasesDeceased.setText(Integer.toString(ApplicationClass.DeceasedCasesIndia));
        tvLastUpdated.setText(ApplicationClass.LastUpdatedTime);
    }


    @Override
    public void fetchDataCallback(String result) {
        UpdateData();
    }
}

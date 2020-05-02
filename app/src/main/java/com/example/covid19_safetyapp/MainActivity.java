package com.example.covid19_safetyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchJsonData.FetchDataCallbackInterface {

    TextView tvCasesTotal,tvCasesActive,tvCasesRecovered,tvCasesDeceased,tvLastUpdated;
    Button btnCheckStateStats,btnContactUs;
    ImageView imgCallHelpline;

    RecyclerView rcvSymptoms,rcvPrevention;
    RecyclerView.Adapter symptomAdapter,preventionAdapter;
    RecyclerView.LayoutManager symptomLayoutManager,preventionLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCasesTotal=findViewById(R.id.tvMainStatsTotalData);
        tvCasesActive=findViewById(R.id.tvMainStatsActiveData);
        tvCasesRecovered=findViewById(R.id.tvMainStatsRecoveredData);
        tvCasesDeceased=findViewById(R.id.tvMainStatsDeceasedData);
        tvLastUpdated=findViewById(R.id.tvMainStatsLastUpdated);

        rcvSymptoms=findViewById(R.id.rcvSymptomList);
        rcvSymptoms.setHasFixedSize(true);
        rcvPrevention=findViewById(R.id.rcvPreventionList);
        rcvPrevention.setHasFixedSize(true);

        symptomLayoutManager=new LinearLayoutManager(this);
        rcvSymptoms.setLayoutManager(symptomLayoutManager);
        preventionLayoutManager=new LinearLayoutManager(this);
        rcvPrevention.setLayoutManager(preventionLayoutManager);

        symptomAdapter=new InformationAdapter(this,ApplicationClass.SymptomsList,"Symptoms");
        rcvSymptoms.setAdapter(symptomAdapter);
        preventionAdapter=new InformationAdapter(this,ApplicationClass.PreventionList,"Prevention");
        rcvPrevention.setAdapter(preventionAdapter);


        UpdateData();

        btnCheckStateStats=findViewById(R.id.btnMainStatsCheckStateStats);
        btnContactUs=findViewById(R.id.btnContactUs);
        imgCallHelpline=findViewById(R.id.imgCallHelpline);


        btnCheckStateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StateCasesActivity.class));
            }
        });

        if(ApplicationClass.isInternetConnection(this))
            new FetchJsonData(ApplicationClass.SourceApiUrl,this).execute();

        imgCallHelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ApplicationClass.HelpLineNumber)));
            }
        });

        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ContactUs.class));
            }
        });

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

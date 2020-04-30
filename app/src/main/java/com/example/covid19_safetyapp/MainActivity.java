package com.example.covid19_safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FetchJsonData.FetchDataCallbackInterface {

    TextView tvCasesTotal,tvCasesActive,tvCasesRecovered,tvCasesDeceased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCasesTotal=findViewById(R.id.tvMainStatsTotalData);
        tvCasesActive=findViewById(R.id.tvMainStatsActiveData);
        tvCasesRecovered=findViewById(R.id.tvMainStatsRecoveredData);
        tvCasesDeceased=findViewById(R.id.tvMainStatsDeceasedData);

        if(COVIDStats.fetchedJsonData==null){
            new FetchJsonData(COVIDStats.sourceApiUrl,this).execute();
        }
        else {
            renderFetchedJsonData();
        }


    }

    @Override
    public void fetchDataCallback(String result) {
        COVIDStats.fetchedJsonData=result;
        renderFetchedJsonData();
    }

    public void renderFetchedJsonData(){
        try {
            COVIDStats.JsonDataObject=new JSONObject(COVIDStats.fetchedJsonData);
            COVIDStats.TotalCasesIndia=COVIDStats.JsonDataObject.getInt(COVIDStats.KEY_TOTAL_CASES);
            COVIDStats.ActiveCasesIndia=COVIDStats.JsonDataObject.getInt(COVIDStats.KEY_ACTIVE_CASES);
            COVIDStats.RecoveredCasesIndia=COVIDStats.JsonDataObject.getInt(COVIDStats.KEY_RECOVERED_CASES);
            COVIDStats.DeceasedCasesIndia=COVIDStats.JsonDataObject.getInt(COVIDStats.KEY_DECEASED_CASES);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        tvCasesTotal.setText(Integer.toString(COVIDStats.TotalCasesIndia));
        tvCasesActive.setText(Integer.toString(COVIDStats.ActiveCasesIndia));
        tvCasesRecovered.setText(Integer.toString(COVIDStats.RecoveredCasesIndia));
        tvCasesDeceased.setText(Integer.toString(COVIDStats.DeceasedCasesIndia));

    }
}

package com.example.covid19_safetyapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationClass extends Application implements FetchJsonData.FetchDataCallbackInterface {

    public static final String localJsonFileName="IndianStates.json";
    public static String SourceApiUrl="https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
    public static String FetchedJsonData;
    DBStatsHelper dbStatsHelper;

    public static int TotalCasesIndia=0;
    public static int RecoveredCasesIndia=0;
    public static int ActiveCasesIndia=0;
    public static int DeceasedCasesIndia=0;
    public static String SourceUrl;
    public static String LastUpdatedTime;

    public static ArrayList<String> stateList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        dbStatsHelper=new DBStatsHelper(this);

        if(dbStatsHelper.isEmpty()){
            String JsonStateInfo;
            JsonStateInfo=FetchJsonData.getJsonFromAssets(this, ApplicationClass.localJsonFileName);
            dbStatsHelper.AddInitialData(JsonStateInfo);
        }

        if(isInternetConnection(this)){
            if(FetchedJsonData==null){
                new FetchJsonData(SourceApiUrl,this).execute();
            }

        }

        dbStatsHelper.UpdateWithDbData();
        stateList=dbStatsHelper.getStateList();

    }


    @Override
    public void fetchDataCallback(String result) {
        dbStatsHelper.updateDbWithFetchedData(result);
        dbStatsHelper.UpdateWithDbData();

    }

    public static boolean isInternetConnection(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            return true;
        else
            return false;
    }

    //2020-05-01T17:45:00.000Z


}

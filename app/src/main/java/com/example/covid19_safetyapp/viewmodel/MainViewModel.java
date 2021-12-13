package com.example.covid19_safetyapp.viewmodel;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid19_safetyapp.api.ApiParser;
import com.example.covid19_safetyapp.classes.RegionData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Comparator;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<RegionData> overallData;
    private final MutableLiveData<ArrayList<RegionData>> regionDataList;
    private final MutableLiveData<String> lastUpdatedTime;

    public MainViewModel(){
        overallData = new MutableLiveData<>();
        regionDataList = new MutableLiveData<>();
        lastUpdatedTime = new MutableLiveData<>();
    }

    public void updateData(String response) throws JSONException {
        ApiParser apiParser = new ApiParser(response);
        overallData.setValue(apiParser.getOverallData());

        ArrayList<RegionData> dataList = apiParser.getRegionDataList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataList.sort((regionData, t1) -> {
                if (regionData.getActiveCases() > t1.getActiveCases())
                    return 1;
                return 0;
            });
        }

        regionDataList.setValue(dataList);
        lastUpdatedTime.setValue(apiParser.getLastUpdatedTime());
    }

    public LiveData<RegionData> getOverallData(){
        return overallData;
    }

    public LiveData<ArrayList<RegionData>> getRegionDataList(){
        return regionDataList;
    }

    public LiveData<String> getLastUpdatedTime(){
        return lastUpdatedTime;
    }

}

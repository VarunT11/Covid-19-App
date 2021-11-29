package com.example.covid19_safetyapp.api;

import com.example.covid19_safetyapp.classes.RegionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;

public class ApiParser {

    private final String response;

    private RegionData overallData;
    private ArrayList<RegionData> regionDataList;
    private String lastUpdatedTime;

    public ApiParser(String response) throws JSONException {
        this.response=response;
        processData();
    }

    public RegionData getOverallData() {
        return overallData;
    }

    public ArrayList<RegionData> getRegionDataList() {
        return regionDataList;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    private void processData() throws JSONException {
        JSONObject jsonObject = new JSONObject(response);

        overallData = new RegionData(
                "India",
                jsonObject.getInt("totalCases"),
                jsonObject.getInt("activeCases"),
                jsonObject.getInt("activeCasesNew"),
                jsonObject.getInt("recovered"),
                jsonObject.getInt("recoveredNew"),
                jsonObject.getInt("deaths"),
                jsonObject.getInt("deathsNew")
        );

        lastUpdatedTime = jsonObject.getString("lastUpdatedAtApify");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Instant instant = Instant.parse(lastUpdatedTime);
            lastUpdatedTime = instant.toString();
        }

        JSONArray regionJsonArray = jsonObject.getJSONArray("regionData");
        regionDataList = new ArrayList<>();

        for(int i=0;i<regionJsonArray.length();i++){
            JSONObject regionJSON = regionJsonArray.getJSONObject(i);
            RegionData regionData = new RegionData(
                    regionJSON.getString("region"),
                    regionJSON.getInt("totalInfected"),
                    regionJSON.getInt("activeCases"),
                    regionJSON.getInt("newInfected"),
                    regionJSON.getInt("recovered"),
                    regionJSON.getInt("newRecovered"),
                    regionJSON.getInt("deceased"),
                    regionJSON.getInt("newDeceased")
            );
            regionDataList.add(regionData);
        }
    }
}

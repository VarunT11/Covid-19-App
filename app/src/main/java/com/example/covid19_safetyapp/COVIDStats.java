package com.example.covid19_safetyapp;

import org.json.JSONObject;

public class COVIDStats {

    public static String KEY_ACTIVE_CASES="activeCases";
    public static String KEY_RECOVERED_CASES="recovered";
    public static String KEY_DECEASED_CASES="deaths";
    public static String KEY_TOTAL_CASES="totalCases";
    public static String KEY_DATA_SOURCE="sourceUrl";

    public static String sourceApiUrl="https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
    public static String fetchedJsonData=null;
    public static JSONObject JsonDataObject;

    public static int TotalCasesIndia;
    public static int RecoveredCasesIndia;
    public static int ActiveCasesIndia;
    public static int DeceasedCasesIndia;
    public static String DataSource;
}

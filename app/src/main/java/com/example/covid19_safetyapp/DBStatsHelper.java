package com.example.covid19_safetyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class DBStatsHelper extends SQLiteOpenHelper {

    public static final String JSON_KEY_STATE_KEY="states";
    public static final String JSON_KEY_STATE_NAME="name";
    public static final String JSON_KEY_STATE_CAPITAL="capital";

    public static final String DATA_JSON_KEY_ACTIVE_CASES="activeCases";
    public static final String DATA_JSON_KEY_RECOVERED_CASES="recovered";
    public static final String DATA_JSON_KEY_DECEASED_CASES="deaths";
    public static final String DATA_JSON_KEY_TOTAL_CASES="totalCases";
    public static final String DATA_JSON_KEY_SOURCE_URL="sourceUrl";
    public static final String DATA_JSON_KEY_LAST_UPDATED="lastUpdatedAtApify";

    public static final String DATA_JSON_KEY_STATE="regionData";
    public static final String DATA_JSON_KEY_STATE_REGION_NAME="region";
    public static final String DATA_JSON_KEY_STATE_TOTAL_INFECTED="totalInfected";
    public static final String DATA_JSON_KEY_STATE_RECOVERED="recovered";
    public static final String DATA_JSON_KEY_STATE_DECEASED="deceased";

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="CoronavirusStatsDB";
    private static final String TABLE_NAME_INDIA="IndianStatsTable";
    private static final String TABLE_NAME_STATES="StateStatsTable";

    private static final String INDIA_KEY_TOTAL_CASES="totalCases";
    private static final String INDIA_KEY_ACTIVE_CASES="activeCases";
    private static final String INDIA_KEY_RECOVERED_CASES="recoveredCases";
    private static final String INDIA_KEY_DECEASED_CASES="deceasedCases";
    private static final String INDIA_KEY_SOURCE_URL="sourceUrl";
    private static final String INDIA_KEY_LAST_UPDATED="lastUpdated";

    private static final String STATE_KEY_NAME="name";
    private static final String STATE_KEY_CAPITAL="capital";
    private static final String STATE_KEY_TOTAL_CASES="totalCases";
    private static final String STATE_KEY_RECOVERED_CASES="recoveredCases";
    private static final String STATE_KEY_DECEASED_CASES="deceasedCases";


    public DBStatsHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery1="CREATE TABLE "+TABLE_NAME_INDIA+"("+INDIA_KEY_TOTAL_CASES+" INTEGER, "+INDIA_KEY_ACTIVE_CASES+" INTEGER, " +INDIA_KEY_RECOVERED_CASES
                +" INTEGER, "+INDIA_KEY_DECEASED_CASES+" INTEGER, "+INDIA_KEY_SOURCE_URL+" VARCHAR(50), "+INDIA_KEY_LAST_UPDATED+" VARCHAR(50)"+")" ;
        db.execSQL(createTableQuery1);

        String createTableQuery2="CREATE TABLE "+TABLE_NAME_STATES+"("+STATE_KEY_NAME+" VARCHAR(25) PRIMARY KEY, "+STATE_KEY_CAPITAL+" VARCHAR(50), "
                +STATE_KEY_TOTAL_CASES+" INTEGER, "+STATE_KEY_RECOVERED_CASES+" INTEGER, "+STATE_KEY_DECEASED_CASES+" INTEGER"+")";
        db.execSQL(createTableQuery2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_INDIA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_STATES);
        onCreate(db);
    }

    public boolean isEmpty(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME_INDIA,null);
        if(cursor.getCount()==0)
            return true;
        else
            return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddInitialData(String jsonString){
        SQLiteDatabase db=this.getWritableDatabase();
        Instant instant=(new Date()).toInstant();
        String initialTime=instant.toString();

        db.execSQL("INSERT INTO "+TABLE_NAME_INDIA+" VALUES(0,0,0,0,'','"+initialTime+"')");
        try {
            JSONObject stateInfoObject=new JSONObject(jsonString);
            JSONArray stateInfoArray=stateInfoObject.getJSONArray(JSON_KEY_STATE_KEY);
            for (int i=0;i<stateInfoArray.length();i++){
                JSONObject tempObject=stateInfoArray.getJSONObject(i);
                String name=tempObject.getString(JSON_KEY_STATE_NAME);
                String capital=tempObject.getString(JSON_KEY_STATE_CAPITAL);
                db.execSQL("INSERT INTO "+TABLE_NAME_STATES+" VALUES('"+name+"', '"+capital+"',0,0,0)");
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        db.close();
    }

    public void updateDbWithFetchedData(String fetchedJsonData){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_INDIA);

        try {
            JSONObject totalData=new JSONObject(fetchedJsonData);
            int totalCases=totalData.getInt(DATA_JSON_KEY_TOTAL_CASES),
                activeCases=totalData.getInt(DATA_JSON_KEY_ACTIVE_CASES),
                    recoveredCases=totalData.getInt(DATA_JSON_KEY_RECOVERED_CASES),
                    deceasedCases=totalData.getInt(DATA_JSON_KEY_DECEASED_CASES);
            String sourceUrl=totalData.get(DATA_JSON_KEY_SOURCE_URL).toString(), lastUpdated=totalData.getString(DATA_JSON_KEY_LAST_UPDATED);
            db.execSQL("INSERT INTO "+TABLE_NAME_INDIA+" VALUES("+totalCases+","+activeCases+","+recoveredCases+","+deceasedCases+",'"+sourceUrl+"','"+lastUpdated+"')");

            JSONArray stateData=totalData.getJSONArray(DATA_JSON_KEY_STATE);

            for(int i=0;i<stateData.length();i++){
                JSONObject tempObject=stateData.getJSONObject(i);
                if(tempObject.isNull(DATA_JSON_KEY_STATE_REGION_NAME))
                    continue;

                String stateName=tempObject.getString(DATA_JSON_KEY_STATE_REGION_NAME);
                int cases,recovered,deceased;
                cases=tempObject.getInt(DATA_JSON_KEY_STATE_TOTAL_INFECTED);
                recovered=tempObject.getInt(DATA_JSON_KEY_STATE_RECOVERED);
                deceased=tempObject.getInt(DATA_JSON_KEY_STATE_DECEASED);

                db.execSQL("UPDATE "+TABLE_NAME_STATES+" SET "+STATE_KEY_TOTAL_CASES+"="+cases+" WHERE "+STATE_KEY_NAME+"='"+stateName+"'");
                db.execSQL("UPDATE "+TABLE_NAME_STATES+" SET "+STATE_KEY_RECOVERED_CASES+"="+recovered+" WHERE "+STATE_KEY_NAME+"='"+stateName+"'");
                db.execSQL("UPDATE "+TABLE_NAME_STATES+" SET "+STATE_KEY_DECEASED_CASES+"="+deceased+" WHERE "+STATE_KEY_NAME+"='"+stateName+"'");

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void UpdateWithDbData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME_INDIA,null);
        cursor.moveToFirst();

        ApplicationClass.TotalCasesIndia=Integer.parseInt(cursor.getString(0));
        ApplicationClass.ActiveCasesIndia=Integer.parseInt(cursor.getString(1));
        ApplicationClass.RecoveredCasesIndia=Integer.parseInt(cursor.getString(2));
        ApplicationClass.DeceasedCasesIndia=Integer.parseInt(cursor.getString(3));
        ApplicationClass.SourceUrl=cursor.getString(4);

        if(!(cursor.getString(5).isEmpty())) {
            Date lastUpdated = Date.from(Instant.parse(cursor.getString(5)));
            SimpleDateFormat ft = new SimpleDateFormat("h:mm a, dd MMMM, y");
            ApplicationClass.LastUpdatedTime = ft.format(lastUpdated);
        }
        else {
            ApplicationClass.LastUpdatedTime="";
        }
        ApplicationClass.stateDataList=new ArrayList<IndianState>();
        ArrayList<String> stateList=getStateList();

        for (int i=0;i<stateList.size();i++){
            ApplicationClass.stateDataList.add(getStateData(stateList.get(i)));
        }

    }

    public ArrayList<String> getStateList(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<String> stateList=new ArrayList<String>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME_STATES,null);
        cursor.moveToFirst();
        stateList.add(cursor.getString(0));
        while (cursor.moveToNext()){
            stateList.add(cursor.getString(0));
        }
        return stateList;
    }

    public IndianState getStateData(String stateName){
        SQLiteDatabase db=this.getReadableDatabase();
        IndianState newState=new IndianState();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME_STATES+" WHERE "+STATE_KEY_NAME+"='"+stateName+"'",null);
        cursor.moveToFirst();
        newState.setName(cursor.getString(0));
        newState.setCapital(cursor.getString(1));
        newState.setTotalInfected(Integer.parseInt(cursor.getString(2)));
        newState.setRecovered(Integer.parseInt(cursor.getString(3)));
        newState.setDeceased(Integer.parseInt(cursor.getString(4)));
        return newState;
    }

    public void AddSymptomsAndPrevention(){
        ApplicationClass.SymptomsList=new ArrayList<String>();
        ApplicationClass.SymptomsList.add("Fever");
        ApplicationClass.SymptomsList.add("Breathing Difficulty");
        ApplicationClass.SymptomsList.add("Headache");
        ApplicationClass.SymptomsList.add("Runny Nose");
        ApplicationClass.SymptomsList.add("Cough");
        ApplicationClass.SymptomsList.add("Dizziness");

        ApplicationClass.PreventionList=new ArrayList<String>();
        ApplicationClass.PreventionList.add("Wash your Hands Regularly");
        ApplicationClass.PreventionList.add("Avoid Touching your Mouth");
        ApplicationClass.PreventionList.add("Work from your Home");
        ApplicationClass.PreventionList.add("Practice Social Distancing");
        ApplicationClass.PreventionList.add("Stay at your Home only");
        ApplicationClass.PreventionList.add("Download the Arogya Setu App");
    }

}

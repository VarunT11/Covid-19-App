package com.example.covid19_safetyapp;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchJsonData extends AsyncTask {

    HttpURLConnection httpURLConnection;
    String  url;
    FetchDataCallbackInterface callbackInterface;

    public FetchJsonData(String url, FetchDataCallbackInterface callbackInterface){
        this.url=url;
        this.callbackInterface=callbackInterface;
    }

    public interface FetchDataCallbackInterface{
        public void fetchDataCallback(String result);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        StringBuilder result=new StringBuilder();
        try{
            URL url=new URL(this.url);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            InputStream in=new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=reader.readLine())!=null){
                result.append(line);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            httpURLConnection.disconnect();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        this.callbackInterface.fetchDataCallback(o.toString());
    }
}

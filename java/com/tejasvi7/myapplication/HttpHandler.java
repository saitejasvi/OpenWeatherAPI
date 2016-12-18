package com.tejasvi7.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tejasvi7 on 10/18/2016.
 */

public class HttpHandler extends AsyncTask<String, String, String> {
    public String data;
    public  static float temp;
   // private  String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
   private  String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    LatLng point;
    public HttpHandler(LatLng point) {
        this.point=point;
    }

    @Override
    protected String doInBackground(String... params) {
        // sending http request
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ( new URL(BASE_URL + "lat="+ ""+point.latitude+ "&"+ "lon=" +""+point.longitude + "&&APPID=887b2e34f2c09de8280e40bac6eb27db")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            Log.d("try block","1");
            con.connect();
            Log.d("try block","2");

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            data=buffer.toString();
            //parse the data for temp
            JSONObject jObj = new JSONObject(data);
            JSONObject mainObj = jObj.getJSONObject("main");
            temp=((float) mainObj.getDouble("temp"));
            Log.d("httpclass", ""+temp);
            }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return data;
    }

}




package com.dan.team.eventapp.library;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 10/24/2015.
 */
public class JsonParser
{
    static InputStream is = null;
    static JSONObject job = null;
    static String json = "";

    public JsonParser()
    {

    }

    public JSONObject getJSONFromUrl(String url, Map<String, String> params) throws IOException {
        // Making HTTP request
        URL openUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) openUrl.openConnection();
        try {
            is = new BufferedInputStream(urlConnection.getInputStream());
            readStream(is);
        } finally {
            urlConnection.disconnect();
        }

        try {
            //iso-8859-1 includes all 191 characters from the latin script
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            job = new JSONObject(json);
        } catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return job;
    }
    private String readStream(InputStream is)
    {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}

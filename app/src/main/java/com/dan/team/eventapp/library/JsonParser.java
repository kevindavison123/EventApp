package com.dan.team.eventapp.library;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 10/24/2015.
 */
public class JsonParser
{
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result = new StringBuilder();
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;



    public JsonParser()
    {

    }

    public JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params)
    {
        sbParams = new StringBuilder();
        int i = 0;
        for(String key: params.keySet())
        {
            try{
                if(i != 0)
                {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=").append(URLEncoder.encode(params.get(key), charset));
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            i++;
        }
        if(method.equals("POST"))
        {
            try{
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();
                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET"))
        {
            if(sbParams.length() != 0)
            {
                url += "?" + sbParams.toString();
            }
            try{
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();
            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        try{
            //receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine())!= null)
            {
                result.append(line);
            }
            Log.d("JSON Parser", "result: " + result.toString());
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        conn.disconnect();

        //try to pare the string to a JSON object

        try{
            jObj = new JSONObject(result.toString());

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;

    }

//    public JSONObject getJSONFromUrl(String url, Map<String, String> params) throws IOException {
//        // Making HTTP request
//        URL openUrl = new URL(url);
//        HttpURLConnection urlConnection = (HttpURLConnection) openUrl.openConnection();
//        try {
//            is = new BufferedInputStream(urlConnection.getInputStream());
//            readStream(is);
//        } finally {
//            urlConnection.disconnect();
//        }
//
//        try {
//            //iso-8859-1 includes all 191 characters from the latin script
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "n");
//            }
//            is.close();
//            json = sb.toString();
//            Log.e("JSON", json);
//
//        } catch (Exception e) {
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//        }
//
//        try {
//            job = new JSONObject(json);
//        } catch (JSONException e)
//        {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        return job;
//    }
//    private String readStream(InputStream is)
//    {
//        try {
//            ByteArrayOutputStream bo = new ByteArrayOutputStream();
//            int i = is.read();
//            while(i != -1) {
//                bo.write(i);
//                i = is.read();
//            }
//            return bo.toString();
//        } catch (IOException e) {
//            return "";
//        }
//    }

}

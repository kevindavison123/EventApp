package com.dan.team.eventapp.webclient;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.team.eventapp.MainPage;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Kevin on 10/28/2015.
 */
public class AsyncOperations {
    private static final String FORM_URL = "http://10.0.3.2:8080/";
    private AsyncHttpClient client = new AsyncHttpClient();
    private static Context context = MainPage.getContext();

    public void postJSON(JSONObject jsonObject, String url) {
        String restUrl = FORM_URL + url;

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(context, restUrl, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {

            }
        });
    }

    public void deleteEvent(int eventId, String url) {
        String restUrl = FORM_URL + url + "/" + eventId;

        client.delete(restUrl, new JsonHttpResponseHandler() {

        });

    }

    public void get(final LinearLayout linearLayout, String url) {
        String restUrl = FORM_URL + url;


        client.get(restUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();

                Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();

            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {


                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Iterator<String> it = jsonObject.keys();
                        String value = "";
                        List<TextView> textViews = new ArrayList();
                        while (it.hasNext()) {
                            String name = it.next();
                            value = jsonObject.get(name).toString();
                            TextView rowTextView = new TextView(context);
                            rowTextView.setText(value);
                            textViews.add(rowTextView);
                            linearLayout.addView(rowTextView);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                Log.d("omg android", jsonArray.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error) {
                Toast.makeText(context, "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray error) {
                Toast.makeText(context, "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }
}
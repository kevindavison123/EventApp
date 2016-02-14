package com.dan.team.eventapp.webclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.team.eventapp.App;
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

    private static final String FORM_URL = "http://146.113.112.18:8080/";

    private AsyncHttpClient client = new AsyncHttpClient();
    private Context context = App.getContext();

    public void postJSON(JSONObject jsonObject, String url) {
        String restUrl = FORM_URL + url;

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(context, restUrl, entity, "application/json", new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object)
            {

            }
        });
    }

    private void getImage(String url, final ImageButton imgButton)
    {
        client.get(FORM_URL + url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap image = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                imgButton.setImageBitmap(image);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void postImage(RequestParams params, String url)
    {
        client.post(FORM_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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

                List<ImageButton> eventImages = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        ImageButton imageButton = new ImageButton(context);
                        eventImages.add(imageButton);
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Iterator<String> it = jsonObject.keys();

                        List<TextView> textViews = new ArrayList();
                        while (it.hasNext()) {
                            String name = it.next();
                            String value = jsonObject.get(name).toString();
                            if(name.equals("photo_loc"))
                            {

                                getImage(value, imageButton);
                            }

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
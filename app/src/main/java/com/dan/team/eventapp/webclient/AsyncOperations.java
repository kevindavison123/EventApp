package com.dan.team.eventapp.webclient;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.team.eventapp.App;
import com.dan.team.eventapp.PageView;
import com.dan.team.eventapp.R;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        client.post(context, restUrl, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {

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

    public void get(final TableLayout tableLayout, String url, final int width) {
        final String restUrl = FORM_URL + url;
        tableLayout.removeAllViews();

        client.get(restUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();

                Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                Toast.makeText(context, context.toString(), Toast.LENGTH_SHORT);
                ArrayList<ImageButton> imageButtons = new ArrayList<>();
                ArrayList<TableRow> tableRows = new ArrayList<>();
                int size = jsonArray.length();
                final Bundle jsonPacket = new Bundle();
                TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(width/2,width/2);
                tableRowParams.setMargins(0,0,0,0);
                TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tableLayoutParams.setMargins(0,0,0,0);
                for (int i = 0; i < size; i++)
                {

                    ArrayList<String> dataPacket = new ArrayList<String>();
                    String packetName ="";
                    ImageButton newButton = new ImageButton(context);
                    imageButtons.add(newButton);
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Iterator<String> fieldIterator = jsonObject.keys();
                        while(fieldIterator.hasNext())
                        {
                            String key = fieldIterator.next();
                            String value = jsonObject.get(key).toString();
                            dataPacket.add(value);
                            if(key.equals("eventId"))
                            {

                                packetName = jsonObject.get(key).toString();
                                Log.d("Packet name is ", ""+packetName);
                            }
                        }
                        jsonPacket.putStringArrayList(packetName, dataPacket);
                        Log.d("jsonPacket: ", jsonPacket.get(packetName).toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                if(size%2==0)
                {
                    int count =0;
                    while(count<=size/2)
                    {
                        TableRow newRow = new TableRow(context);
                        tableRows.add(newRow);
                        count++;
                    }
                }
                else
                {
                    int count =0;
                    while(count<=(size/2)+1)
                    {
                        TableRow newRow = new TableRow(context);
                        tableRows.add(newRow);
                        count++;
                    }
                }
                TableRow row = new TableRow(context);
                tableLayout.addView(row);
                for(int i = 0; i<imageButtons.size(); i++)
                {

                    int id = i+1;
                    imageButtons.get(i).setId(id);
                    imageButtons.get(i).setCropToPadding(true);
                    imageButtons.get(i).setAdjustViewBounds(true);
                    imageButtons.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
                    imageButtons.get(i).setBackground(null);
                    imageButtons.get(i).setLayoutParams(tableRowParams);
                    getImage("events/getImageThumb/" + id, imageButtons.get(i));


                    final String ID = String.valueOf(id);
                    imageButtons.get(i).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(tableLayout.getContext(), PageView.class);
                            intent.putStringArrayListExtra(ID, (ArrayList<String>) jsonPacket.get(ID));
                            Log.d("THIS IS THE DATA", jsonPacket.get(ID).toString());
                            
                            tableLayout.getContext().startActivity(intent);
                        }
                    });
                    Log.d("This is the i", " " + i);

                    if(i%2==0)
                    {
                        tableRows.get(i/2).addView(imageButtons.get(i));
                    }
                    else
                    {
                        tableRows.get((i-1)/2).addView(imageButtons.get(i));
                        tableLayout.addView(tableRows.get((i-1)/2));
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
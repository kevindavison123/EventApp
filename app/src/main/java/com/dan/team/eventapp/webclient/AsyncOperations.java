package com.dan.team.eventapp.webclient;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String restUrl = FORM_URL + url;


        client.get(restUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();

                Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                Toast.makeText(context,context.toString(),Toast.LENGTH_SHORT);
                TableRow[] rows;
                ArrayList<ImageButton> imageButtons = new ArrayList<>();
                ArrayList<TableRow> tableRows;
                int size = jsonArray.length();
                TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(width/2,width/2);
                tableRowParams.setMargins(0,0,0,0);
                TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tableLayoutParams.setMargins(0,0,0,0);

                for (int i = 0; i < size; i++)
                {
                    ImageButton newButton = new ImageButton(context);
                    imageButtons.add(newButton);
                }

                TableRow row = new TableRow(context);
                tableLayout.addView(row);
                for(int i = 0; i<imageButtons.size(); i++)
                {
                    int id = 0;
                    imageButtons.get(i).setId(id);
                    imageButtons.get(i).setCropToPadding(true);
                    imageButtons.get(i).setAdjustViewBounds(true);
                    imageButtons.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
                    imageButtons.get(i).setBackground(null);
                    imageButtons.get(i).setLayoutParams(tableRowParams);
                    imageButtons.get(i).setImageResource(R.drawable.americanultra);
                    imageButtons.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    Log.d("This is the i", " " + i);
                    if(i%2==0)
                    {
                        TableRow tableRow = new TableRow(context);
                        tableRow.addView(imageButtons.get(i),tableRowParams);
                        tableRow.addView(imageButtons.get(i+1),tableRowParams);
                        tableLayout.addView(tableRow,tableLayoutParams);
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
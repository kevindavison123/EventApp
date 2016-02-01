package com.angry.tech.eventapp.webclient;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.angry.tech.eventapp.App;
import com.angry.tech.eventapp.PageView;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by Kevin on 10/28/2015.
 * This class is the work horse of the app. It connects the database on the server to the android client.
 * It can GET and POST using JSONObjects or arrays.
 * posting an image cannot happen at this moment as we ran into issues in the backend, but that is for future designs.
 */
public class AsyncOperations {

    private static final String FORM_URL = "http://146.113.112.18:8080/";

    private AsyncHttpClient client = new AsyncHttpClient();
    private Context context = App.getContext();

    /*
        This will post information to the server in a JSONObject
     */
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
    /*
    this will post an event to the database that a user made, it first goes through authentication first.
     */
    public void postEvent(JSONObject jsonObject, String url) {
        String restUrl = FORM_URL + url;

        client.setBasicAuth("ee","qqq");

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

    /*
        This checks if a user is logged in using cookies to keep track
     */

    public void login(JSONObject user) {
        String restUrl = FORM_URL + "user/login";

        if (ServiceClass.getCookieStore().getCookies().size() != 0) {
            Toast.makeText(context, "A user is already logged in. Please logout before logging in as a new user", Toast.LENGTH_LONG).show();
            return;
        }

        StringEntity entity = null;
        try {
            entity = new StringEntity(user.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(context, restUrl, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                PersistentCookieStore cookieStore = ServiceClass.getCookieStore();
                client.setCookieStore(cookieStore);
                if (object != null && object.length() != 0) {
                    try {
                        BasicClientCookie id = new BasicClientCookie("id", String.valueOf(object.getInt("userId")));
                        id.setDomain("");
                        BasicClientCookie email = new BasicClientCookie("email", object.getString("email"));
                        email.setDomain("");
                        BasicClientCookie firstName = new BasicClientCookie("first_name", object.getString("firstName"));
                        firstName.setDomain("");
                        BasicClientCookie lastName = new BasicClientCookie("last_name", object.getString("lastName"));
                        lastName.setDomain("");
                        BasicClientCookie password = new BasicClientCookie("password", object.getString("password"));
                        password.setDomain("");
                        BasicClientCookie isAdmin = new BasicClientCookie("is_admin", object.getString("isAdmin"));
                        isAdmin.setDomain("");
                        cookieStore.addCookie(id);
                        cookieStore.addCookie(email);
                        cookieStore.addCookie(firstName);
                        cookieStore.addCookie(lastName);
                        cookieStore.addCookie(password);
                        cookieStore.addCookie(isAdmin);
                        Toast.makeText(context, "Welcome, " + firstName.getValue() + "!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    this retrieves an image from the database, and creates a bitmap of the contents from the path
     */
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
    /*
        Not yet implemented!
     */
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





    /*
    Also not really used, but able to delete events
     */
    public void deleteEvent(int eventId, String url) {
        String restUrl = FORM_URL + url + "/" + eventId;

        client.delete(restUrl, new JsonHttpResponseHandler() {

        });

    }

    /*
        The workhorse method, gets all the event info, and creates image buttons and sets the right contents to be displayed
     */

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
                    //When a button is pressed, sent the json packet to the next activity
                    imageButtons.get(i).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(tableLayout.getContext(), PageView.class);
                            intent.putStringArrayListExtra("dataPacket", (ArrayList<String>) jsonPacket.get(ID));
                            Log.d("THIS IS THE ID", ID);
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
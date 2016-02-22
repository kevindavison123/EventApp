package com.dan.team.eventapp.webclient;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.dan.team.eventapp.BackendParcelable;
import com.dan.team.eventapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/*
* Author: Jakob Rodseth
* Date: 10/15/2015
*
* TODO: Complete Documentation
* */

public class DatabaseServices extends Service
{
    private final IBinder mBinder = new DatabaseBinder();
    int eventsDBVersion;//TODO:Implement versioning
    private boolean debug = true;

    public class DatabaseBinder extends Binder
    {
        public DatabaseServices getService()
        {
            // Return this instance of LocalService so clients can call public methods
            return DatabaseServices.this;
        }
    }

    public DatabaseServices()
    {
        super();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // Let it continue running until it is stopped.
        if(debug)
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show(); //TODO:Debug
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        if(debug)
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show(); //TODO:Debug
        super.onDestroy();
    }

    /*
        * Public methods
        * */

    public void post(BackendParcelable postObject)
    {
        RequestParams params = new RequestParams();
        params.add("type", postObject.getClass().toString());
        ArrayList<String> parameters = postObject.getParameters();
        params.put("Parameters", parameters);
        invokeWebServices(params, getResources().getString(R.string.post));
    }

    /*
        * Private methods
        * */

    private void sendMessage(String message, String toastMessage, Parcelable extra)
    {
        Intent intent = new Intent(message);
        intent.putExtra(message, extra);

        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();//TODO:Debug
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMessage(String message, String toastMessage, ArrayList<? extends Parcelable> extras)
    {
        Intent intent = new Intent(message);
        intent.putParcelableArrayListExtra(message, extras);

        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();//TODO:Debug
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private boolean queryDBVersion()
    {
        return true; //TODO: implement
    }


    private void invokeWebServices(RequestParams params, String HTTPSrequest, final OnJSONResponseCallback callback)
    {
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTPSrequest, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] bytes)
            {
                try
                {
                    // JSON Object
                    String response = new String(bytes);
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has validation status set to true
                    Toast.makeText(getApplicationContext(), "Data Received", Toast.LENGTH_SHORT).show();//TODO:Debug
                    callback.onJSONResponse(true, getApplicationContext(), obj);

                       /* Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_SHORT).show();
                        //TODO: catch callback error*/

                } catch (JSONException e)
                {
                    //Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();//TODO:Debug
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable)
            {
                // Hide Progress Dialog
                //prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404)
                {
                    //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code is '500'
                else if (statusCode == 500)
                {
                    //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code other than 404, 500
                else
                {
                    //Toast.makeText(getApplicationContext(), "Unexpected Error occurred! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();//TODO:Debug
                }
            }
        });
    }

    private void invokeWebServices(RequestParams params, String HTTPSrequest)
    {
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(HTTPSrequest, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] bytes)
            {
                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable)
            {
                if (statusCode == 404)
                {
                    //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code is '500'
                else if (statusCode == 500)
                {
                    //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code other than 404, 500
                else
                {
                    //Toast.makeText(getApplicationContext(), "Unexpected Error occurred! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();//TODO:Debug
                }
            }
        });
    }

    public interface OnJSONResponseCallback
    {
        public void onJSONResponse(boolean success, Context context, JSONObject response);
    }
}
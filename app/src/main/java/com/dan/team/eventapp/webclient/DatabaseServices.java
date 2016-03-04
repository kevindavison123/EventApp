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
import android.util.Log;
import android.widget.Toast;

import com.dan.team.eventapp.BackendParcelable;
import com.dan.team.eventapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;

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
    private static String url;
    private static String createUserAppend;
    private static Context applicationContext;

    public Users users;

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
        users = new Users();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        applicationContext = getApplicationContext();
        url = applicationContext.getResources().getString(R.string.backend_URL);
        createUserAppend = applicationContext.getResources().getString(R.string.createUserAppend);
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
    public final class Users implements supportedDBDataType
    {
        @Override
        public <User extends BackendParcelable> void create(User user)
        {
            DatabaseServices.create(user, createUserAppend, new OnJSONResponseCallback()
            {
                @Override
                public void onJSONResponse(boolean success, Context context, JSONObject response)
                {
                    if(success)
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show(); //TODO:Debug //do something
                    else
                        Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT).show(); //TODO:Debug //do something
                }
            });
            Toast.makeText(getApplicationContext(), "Asked DBServices to create user", Toast.LENGTH_SHORT).show(); //TODO:Debug //do something
        }

        @Override
        public <User extends BackendParcelable> void read(User user)
        {
            DatabaseServices.read(user, "temp", new OnJSONResponseCallback()
            {
                @Override
                public void onJSONResponse(boolean success, Context context, JSONObject response)
                {
                    //do something
                }
            });
        }

        @Override
        public <User extends BackendParcelable> void update(User user)
        {
            DatabaseServices.update(user, "temp", new OnJSONResponseCallback()
            {
                @Override
                public void onJSONResponse(boolean success, Context context, JSONObject response)
                {
                    //do something
                }
            });
        }

        @Override
        public <User extends BackendParcelable> void delete(User user)
        {
            DatabaseServices.remove(user, "temp", new OnJSONResponseCallback()
            {
                @Override
                public void onJSONResponse(boolean success, Context context, JSONObject response)
                {
                    //do something
                }
            });
        }
    }

    public final class Events implements supportedDBDataType
    {

        @Override
        public <T extends BackendParcelable> void create(T object)
        {

        }

        @Override
        public <T extends BackendParcelable> void read(T object)
        {

        }

        @Override
        public <T extends BackendParcelable> void update(T object)
        {

        }

        @Override
        public <T extends BackendParcelable> void delete(T object)
        {

        }

        public void getAllEvents()
        {

        }
    }

    public final class Groups implements supportedDBDataType
    {
        @Override
        public <T extends BackendParcelable> void create(T object)
        {

        }

        @Override
        public <T extends BackendParcelable> void read(T object)
        {

        }

        @Override
        public <T extends BackendParcelable> void update(T object)
        {

        }

        @Override
        public <T extends BackendParcelable> void delete(T object)
        {

        }
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


    private static void invokeWebServices(RequestParams params, String HTTPSrequest, final OnJSONResponseCallback callback)
    {
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d(DatabaseServices.class.getSimpleName(), HTTPSrequest);
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
                    Toast.makeText(applicationContext, "Data Received", Toast.LENGTH_SHORT).show();//TODO:Debug
                    callback.onJSONResponse(true, applicationContext, obj);

                       Toast.makeText(applicationContext, obj.getString("error_msg"), Toast.LENGTH_SHORT).show();
                        //TODO: catch callback error*/

                } catch (JSONException e)
                {
                    Toast.makeText(applicationContext, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();//TODO:Debug
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
                    Toast.makeText(applicationContext, "Requested resource not found", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code is '500'
                else if (statusCode == 500)
                {
                    Toast.makeText(applicationContext, "Something went wrong at server end", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code other than 404, 500
                else
                {
                    Toast.makeText(applicationContext, "Unexpected Error occurred! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();//TODO:Debug
                }
            }
        });
    }

    private static void invokeWebServices(RequestParams params, String HTTPSrequest)
    {
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d(DatabaseServices.class.getSimpleName(), HTTPSrequest);
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

    private interface supportedDBDataType
    {
        public <T extends BackendParcelable> void create(T object);
        public <T extends BackendParcelable> void read(T object);
        public <T extends BackendParcelable> void update(T object);
        public <T  extends BackendParcelable> void delete(T object);
    }

    private static void create(BackendParcelable obj, String httpAppend, final OnJSONResponseCallback callback)
    {
        RequestParams params = new RequestParams();
        try
        {
            params.put(obj.getClass().toString(), new StringEntity(obj.makeJSON().toString()));
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        invokeWebServices(params, url + httpAppend);
    }

    private static void read(BackendParcelable obj, String httpAppend, final OnJSONResponseCallback callback)
    {

    }

    private static void update(BackendParcelable obj, String httpAppend, final OnJSONResponseCallback callback)
    {

    }

    private static void remove(BackendParcelable obj, String httpAppend, final OnJSONResponseCallback callback)
    {

    }
}
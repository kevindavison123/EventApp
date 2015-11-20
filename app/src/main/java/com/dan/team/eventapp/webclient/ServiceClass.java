package com.dan.team.eventapp.webclient;

import android.widget.LinearLayout;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by kevin on 10/27/2015.
 * This is
 */
public final class ServiceClass {

    public static void formGetAll(LinearLayout linearLayout) {
        String url = "events/allevents";
        AsyncOperations get = new AsyncOperations();
        get.get(linearLayout, url);
    }

    public static void formGetWeekly(LinearLayout linearLayout) {
        String url = "events/weeklyevents";
        AsyncOperations get = new AsyncOperations();
        get.get(linearLayout, url);
    }

    public static void postUser(String email, boolean isAdmin, String firstName, String lastName,
    String password)
    {
        String url = "users";
        AsyncOperations async = new AsyncOperations();
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("email", email);
            jsonObject.put("isAdmin", isAdmin);
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("password", password);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        async.postJSON(jsonObject, url);
    }

    public static void login(String email, String password)
    {
        String url = "users";
        AsyncOperations async = new AsyncOperations();
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("email", email);
            jsonObject.put("password", password);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        async.postJSON(jsonObject, url);
    }


    public static void postNewEvent(int authorId, String photoLocation, String description,
                             String title, String location, String date, String time) {
        String url = "events/create";
        AsyncOperations async = new AsyncOperations();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("authorId", authorId);
            jsonObject.put("photoLocation", photoLocation);
            jsonObject.put("description", description);
            jsonObject.put("title", title);
            jsonObject.put("location", location);
            jsonObject.put("date", date);
            jsonObject.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async.postJSON(jsonObject, url);
    }

    public static void postImage(String name, String path) {
        String url = "pictures";
        AsyncOperations async = new AsyncOperations();
        File picture = new File(path);
        RequestParams params = new RequestParams();
        try {
            params.put(name, picture);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        async.postImage(params, url);
    }

    public static void deleteEvent(int eventId) {
        String url = "events/delete";
        AsyncOperations async = new AsyncOperations();
        async.deleteEvent(eventId, url);
    }

}

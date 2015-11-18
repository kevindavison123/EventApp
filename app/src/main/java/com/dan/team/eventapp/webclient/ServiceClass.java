package com.dan.team.eventapp.webclient;

import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static void postNewUser(String email, boolean isAdmin, String firstName, String lastName,
                                   String password) {
        String url = "user/create";
        AsyncOperations async = new AsyncOperations();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("isAdmin", isAdmin);
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async.postJSON(jsonObject, url);
    }

    public static void deleteEvent(int eventId) {
        String url = "events/delete";
        AsyncOperations async = new AsyncOperations();
        async.deleteEvent(eventId, url);
    }

}

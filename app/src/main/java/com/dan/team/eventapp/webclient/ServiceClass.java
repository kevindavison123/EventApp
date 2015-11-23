package com.dan.team.eventapp.webclient;

import android.graphics.Bitmap;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.dan.team.eventapp.App;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by kevin on 10/27/2015.
 * This is the class the customizes the calls to the async class GETs and POSTs. This will be a singleton class call in each Activity
 * See AsyncOperations for more details on POST and GET
 */
public final class ServiceClass {

    private static PersistentCookieStore cookieStore;

    private static ServiceClass serviceClass = new ServiceClass();

    private ServiceClass() {
        cookieStore = new PersistentCookieStore(App.getContext());
    }

    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    public static Cookie getCookieValue(String name) {
        for (Cookie cookie : cookieStore.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public static void formGetAll(TableLayout tableLayout, int width) {
        String url = "events/allevents";
        AsyncOperations get = new AsyncOperations();
        //This needs to be TableLayout
        get.get(tableLayout, url, width);
    }

    public static void formGetWeekly(TableLayout tableLayout, int width) {
        String url = "events/weeklyevents";
        AsyncOperations get = new AsyncOperations();
        //This needs to be TableLayout
        get.get(tableLayout, url, width);
    }


    public static void postUser(String firstName, String lastName, String email, String password) {
        String url = "user/create";
        AsyncOperations async = new AsyncOperations();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("isAdmin", false);
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("password", password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        async.postJSON(jsonObject, url);
    }

    public static void login(String email, String password) {
        AsyncOperations async = new AsyncOperations();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("isAdmin", null);
            jsonObject.put("firstName", null);
            jsonObject.put("lastName", null);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        async.login(jsonObject);
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
        async.postEvent(jsonObject, url);
    }

    public static void postImage(String imageName, Bitmap image, Bitmap thumbnail) {
        String url = "Some url Liam will make";
        String thumbName = imageName + "_thumb";
        AsyncOperations async = new AsyncOperations();
        RequestParams params = new RequestParams();

        params.put("name", imageName);
        params.put("image", image);
        params.put("thumbnail", thumbName);
        params.put("thumbnailImage", thumbnail);
        async.postImage(params, url);
    }

    public static void deleteEvent(int eventId) {
        String url = "events/delete";
        AsyncOperations async = new AsyncOperations();
        async.deleteEvent(eventId, url);
    }

}

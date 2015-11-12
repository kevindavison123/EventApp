package com.dan.team.eventapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by kevin on 11/11/2015.
 */
public class App extends Application {
    private static Context context;

    public static Context getContext()
    {
        return App.context;
    }

    public static void setContext(Context context)
    {
        App.context = context;
    }
}

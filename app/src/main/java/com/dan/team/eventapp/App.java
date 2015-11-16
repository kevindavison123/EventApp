package com.dan.team.eventapp;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kevin on 11/11/2015.
 */
public class App extends AppCompatActivity {
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

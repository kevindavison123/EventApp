package com.dan.team.eventapp;

import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Jakob on 2/14/2016.
 */
public interface BackendParcelable extends Parcelable
{
    public ArrayList<String> getParameters();
    public JSONObject makeJSON();
}

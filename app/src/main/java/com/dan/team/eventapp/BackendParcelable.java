package com.dan.team.eventapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Jakob on 2/14/2016.
 */
public abstract class BackendParcelable implements Parcelable
{
    public abstract ArrayList<String> getParameters();
    public JSONObject makeJSON()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("Parameters", getParameters());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

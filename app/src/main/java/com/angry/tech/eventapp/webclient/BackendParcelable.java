package com.angry.tech.eventapp.webclient;

import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by Jakob on 2/14/2016.
 */
public interface BackendParcelable extends Parcelable
{
    public ArrayList<String> getParameters();
}

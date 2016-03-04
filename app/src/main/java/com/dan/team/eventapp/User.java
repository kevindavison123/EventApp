package com.dan.team.eventapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakob on 2/14/2016.
 */
public class User implements BackendParcelable
{
    String firstName, lastName, email, password;

    public User(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(email);
        out.writeString(password);
    }

    @Override
    public String toString()
    {
        String s = "First Name: "
                + firstName
                + ", Last Name: "
                + lastName
                + "Email: "
                + email;
        return s;
    }

    public Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>()
    {
        public User createFromParcel(Parcel in)
        {
            return new User(in.readString(), in.readString(), in.readString(), in.readString());
        }

        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()//ToDo: This seems dangerous
    {
        return password;
    }

    public ArrayList<String> getParameters()
    {
        ArrayList<String> parameters = new ArrayList<String>();

        parameters.add(getFirstName());
        parameters.add(getLastName());
        parameters.add(getEmail());
        parameters.add(getPassword());

        return parameters;
    }

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
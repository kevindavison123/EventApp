package com.dan.team.eventapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakob on 2/14/2016.
 */
public class User extends BackendParcelable
{
    String firstName, lastName, email, password;
    int id;

    public User(String firstName, String lastName, String email, String password, int id)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.id = id;
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
        out.writeInt(id);
    }

    @Override
    public String toString()
    {
        String s = "First Name: "
                + firstName
                + ", Last Name: "
                + lastName
                + "Email: "
                + email
                + "ID: "
                + String.valueOf(id);
        return s;
    }

    public Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>()
    {
        public User createFromParcel(Parcel in)
        {
            return new User(in.readString(), in.readString(), in.readString(), in.readString(), in.readInt());
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

    public int getId()
    {
        return id;
    }

    public ArrayList<String> getParameters()
    {
        ArrayList<String> parameters = new ArrayList<String>();

        parameters.add(getFirstName());
        parameters.add(getLastName());
        parameters.add(getEmail());
        parameters.add(getPassword());
        parameters.add(String.valueOf(getId()));

        return parameters;
    }

    public static User makeFromJSON(JSONObject response, Context context) throws JSONException
    {
        User user;
        try
        {
            user = new User(response.getString(context.getString(R.string.User_FirstName)),
                    response.getString(context.getString(R.string.User_LastName)),
                    response.getString(context.getString(R.string.User_Email)),
                    response.getString(context.getString(R.string.User_Password)),
                    response.getInt(context.getString(R.string.User_ID)));
        } catch (Exception e)
        {
            throw new JSONException("Parsing error: " + response.toString());
        }
        return user;
    }
}
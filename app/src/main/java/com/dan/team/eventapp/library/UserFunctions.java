package com.dan.team.eventapp.library;

/**
 * Created by Kevin on 10/24/2015.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UserFunctions {

    private JsonParser jsonParser;

    //URL of the PHP API
    private static String loginURL = "http://10.0.2.2/learn2crack_login_api/";
    private static String registerURL = "http://10.0.2.2/learn2crack_login_api/";
    private static String forpassURL = "http://10.0.2.2/learn2crack_login_api/";
    private static String chgpassURL = "http://10.0.2.2/learn2crack_login_api/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";

    // constructor
    public UserFunctions(){
        jsonParser = new JsonParser();
    }

    /**
     * Function to Login
     **/

    public JSONObject loginUser(String email, String password) throws IOException {
        // Building Parameters

        HashMap<String, String> values = new HashMap<>();
        values.put("tag", login_tag);
        values.put("email", email);
        values.put("password", password);


        JSONObject json = jsonParser.makeHttpRequest(loginURL,"GET", values);
        return json;
    }

    /**
     * Function to change password
     **/

    public JSONObject chgPass(String newpas, String email){
        HashMap<String, String> values = new HashMap<>();
        values.put("tag", chgpass_tag);
        values.put("newpass", newpas);
        values.put("email", email);
        JSONObject json = jsonParser.makeHttpRequest(chgpassURL, "POST", values);
        return json;
    }

    /**
     * Function to reset the password
     **/

    public JSONObject forPass(String forgotpassword){
        HashMap<String, String> values = new HashMap<>();
        values.put("tag", forpass_tag);
        values.put("forgotpassword", forgotpassword);

        JSONObject json = jsonParser.makeHttpRequest(forpassURL, "GET", values);
        return json;
    }

    /**
     * Function to  Register
     **/
    public JSONObject registerUser(String fname, String lname, String email, String uname, String password){
        // Building Parameters
        HashMap<String, String> values = new HashMap<>();
        values.put("tag", register_tag);
        values.put("fname", fname);
        values.put("lname", lname);
        values.put("email", email);
        values.put("uname", uname);
        values.put("password", password);

        JSONObject json = jsonParser.makeHttpRequest(registerURL, "POST", values);
        return json;
    }

    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

}
package com.dan.team.eventapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginMain extends Activity {
    Button loginButton;
    Button registerButton;
    Button forgotpassButton;
    EditText inputUsername;
    EditText inputPassword;
    private TextView loginErrorMessage;

    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        App.setContext(this);
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.lRegister);
        forgotpassButton = (Button) findViewById(R.id.forgotPass);
        loginErrorMessage = (TextView) findViewById(R.id.loginErrorMessage);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetIntent = new Intent(v.getContext(),Registration.class);
                startActivityForResult(forgetIntent,0);
                finish();
            }});
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if((!inputUsername.getText().toString().equals("")) && (!inputPassword.getText().toString().equals("")))
                {
                    Toast.makeText(getApplicationContext(),"implement later! ", Toast.LENGTH_SHORT).show();
                }
                else if(inputUsername.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Username field is empty", Toast.LENGTH_SHORT).show();
                }
                else if(inputPassword.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Password field is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username and Password fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





}

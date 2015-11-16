package com.dan.team.eventapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;





public class LoginMain extends AppCompatActivity {

    private Toolbar toolbar;

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


        //Creates the toolbar and gives the toolbar its title.
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        // Not needed for some reason getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);


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


        /* Checks to see if the username and/or password fields are empty. */
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if(id == R.id.adding_button)
        {
            addEvent();
            return true;
        }
        if(id == R.id.login_button)
        {
            loginPage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addEvent()
    {
        Intent intent = new Intent(LoginMain.this, SubmitForm.class);
        LoginMain.this.startActivity(intent);
    }

    public void loginPage()
    {
        Intent intent = new Intent(LoginMain.this, LoginMain.class);
        LoginMain.this.startActivity(intent);
    }





}

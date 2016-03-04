package com.dan.team.eventapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.team.eventapp.webclient.DatabaseServices;
import com.dan.team.eventapp.webclient.ServiceClass;


public class Registration extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText cPassword;
    Button register;
    Button backToLogin;


    DatabaseServices dbServices; //The database service instance the activity binds to.
    boolean dbBound = false; //Is the activity bound to the service

    /* When the activity binds to a service, the ServiceConnection moderates the communication.
    *  This is where you should put all of your async data requests through DatabaseServices methods
    */
    private ServiceConnection dbConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service)
        {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DatabaseServices.DatabaseBinder binder = (DatabaseServices.DatabaseBinder) service;
            dbServices = binder.getService(); //Store reference to the instance of the service that we bound to.
            dbBound = true;

            Toast.makeText(getApplicationContext(), "Database Services Requested", Toast.LENGTH_SHORT).show(); //TODO:Debug
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            dbBound = false;
        }
    };

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, DatabaseServices.class);
        bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        App.setContext(Registration.this);
        final ServiceClass serviceClass = new ServiceClass();
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.rPassword);
        cPassword = (EditText) findViewById(R.id.cPassword);
        register = (Button) findViewById(R.id.rRegister);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String eName = email.getText().toString();
                String passName = password.getText().toString();
                String confirmPassName =  cPassword.getText().toString();
                if((fName.equals("")) || (lName.equals(""))|| (eName.equals("")))
                {
                    Toast.makeText(Registration.this,"One or more fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(passName.equals(""))
                {
                    Toast.makeText(Registration.this,"Please enter a password",Toast.LENGTH_SHORT).show();
                }
                else if(passName.equals(confirmPassName))
                {
                    if(dbBound)
                    {
                        dbServices.users.create(new User(fName,lName,eName,passName));
                    }
                }
                else
                {
                    Toast.makeText(Registration.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });



        //Creates the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        // Not needed for some reason getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.adding_button:
                addEvent();
                return true;
            case R.id.login_button:
                loginPage();
                return true;
            case R.id.register_button:
                registerPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addEvent() {
        Intent intent = new Intent(Registration.this, SubmitForm.class);
        Registration.this.startActivity(intent);
    }

    public void loginPage() {
        Intent intent = new Intent(Registration.this, LoginMain.class);
        Registration.this.startActivity(intent);
    }

    public void registerPage()
    {
        Intent intent = new Intent(Registration.this, Registration.class);
        Registration.this.startActivity(intent);
    }
}

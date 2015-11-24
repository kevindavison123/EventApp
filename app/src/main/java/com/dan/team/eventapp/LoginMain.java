package com.dan.team.eventapp;

import android.content.Intent;
import android.graphics.Typeface;
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

import com.dan.team.eventapp.webclient.ServiceClass;

/*
Login page for the EventApp, checks login authentication
 */
public class LoginMain extends AppCompatActivity {


    Button loginButton;
    Button registerButton;
    Button logoutButton;
    EditText inputEmail;
    EditText inputPassword;
    private TextView loginErrorMessage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        App.setContext(this);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        //Used to navigate back on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.lRegister);
        logoutButton = (Button) findViewById(R.id.logout);
        loginErrorMessage = (TextView) findViewById(R.id.loginErrorMessage);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetIntent = new Intent(v.getContext(), Registration.class);
                startActivityForResult(forgetIntent, 0);
                finish();
            }
        });


        /* Checks to see if the username and/or password fields are empty. */
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if ((!email.equals("")) && (!password.equals(""))) {
                    ServiceClass.login(email, password);
                } else if (email.equals("") && password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Email and password fields are empty", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Password field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ServiceClass.getCookieStore().clear();
                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
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

        //This is used to navigate between each supplementary
        //activity via the action bar.
        Intent intent;
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.adding_button:
                intent = new Intent(LoginMain.this, SubmitForm.class);
                LoginMain.this.startActivity(intent);
                return true;
            case R.id.login_button:
                intent = new Intent(LoginMain.this, LoginMain.class);
                LoginMain.this.startActivity(intent);
                return true;
            case R.id.register_button:
                intent = new Intent(LoginMain.this, Registration.class);
                LoginMain.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}

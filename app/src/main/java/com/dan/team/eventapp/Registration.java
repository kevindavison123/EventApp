package com.dan.team.eventapp;

import android.content.Intent;

import android.graphics.Typeface;
import android.provider.MediaStore;
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
import com.dan.team.eventapp.webclient.ServiceClass;

/*
The Registration for users. This will take in user input and send it to the server. The user will be
able to login and view the main page.
 */
public class Registration extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText cPassword;
    Button register;
    Button backToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        //Used to navigate back on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);



        App.setContext(Registration.this);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.rPassword);
        cPassword = (EditText) findViewById(R.id.cPassword);
        register = (Button) findViewById(R.id.rRegister);
        register.setOnClickListener(new View.OnClickListener() {
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
                    ServiceClass.postUser(fName,lName,eName, passName);
                }
                else
                {
                    Toast.makeText(Registration.this,"Passwords do not match",Toast.LENGTH_SHORT).show();

                }
            }
        });

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

        //This is used to navigate between each supplementary
        //activity via the action bar.
        Intent intent;
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.adding_button:
                intent = new Intent(Registration.this, SubmitForm.class);
                Registration.this.startActivity(intent);
                return true;
            case R.id.login_button:
                intent = new Intent(Registration.this, LoginMain.class);
                Registration.this.startActivity(intent);
                return true;
            case R.id.register_button:
                intent = new Intent(Registration.this, Registration.class);
                Registration.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

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

public class Registration extends AppCompatActivity{

    EditText editFirstName, editLastName, editEmail, editPassword, editConfirmPassword;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Creates the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        // Not needed for some reason getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);



        editFirstName = (EditText) findViewById(R.id.firstName);
        editLastName = (EditText) findViewById(R.id.lastName);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.rPassword);
        editConfirmPassword = (EditText) findViewById(R.id.cPassword);

        registerButton = (Button) findViewById(R.id.rRegister);

        registerButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();


                if((!firstName.equals("")) && (!lastName.equals("")) && (!email.equals("")) &&
                        (!password.equals("")) && (!confirmPassword.equals("")) && (password.equals(confirmPassword))) {
                    ServiceClass.postNewUser(email, false, firstName, lastName, password);
                    Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(),"Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if(firstName.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"First name field is empty", Toast.LENGTH_SHORT).show();
                }
                else if(lastName.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Last name field is empty", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Password field is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Confirm password field is empty", Toast.LENGTH_SHORT).show();
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

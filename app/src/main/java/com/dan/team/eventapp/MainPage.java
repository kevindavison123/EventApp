package com.dan.team.eventapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
=======
import android.util.Log;
>>>>>>> 8b208abd75f82ea8c36d0f9e8805e3f5795ed043
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainPage extends AppCompatActivity {

    private static Context context;

    public static Context getContext()
    {
        return MainPage.context;
    }

    public static void setContext(Context context)
    {
        MainPage.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        /*
         Layout creator, adds three buttons per line, if it can.
         A button is associated with a text file from the database.
         The event is fetched from the database, a button is created, and the link is made.
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
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
        Intent intent = new Intent(MainPage.this, SubmitForm.class);
        MainPage.this.startActivity(intent);
    }

    public void loginPage()
    {
        Intent intent = new Intent(MainPage.this, LoginMain.class);
        MainPage.this.startActivity(intent);
    }

    /* Demonstrates what happens when the user clicks an image button.
     * This is for demonstration purposes. The full implementation will
     * be dynamically generated and use fragments but for demonstration
     * it is just an image in another activity.
     */
    public void onUserClickConcept(View view) {
        Intent intent = new Intent(MainPage.this, ConceptPageView.class);
        MainPage.this.startActivity(intent);
    }

}

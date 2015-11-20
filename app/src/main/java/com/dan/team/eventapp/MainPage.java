package com.dan.team.eventapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_main_page);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);

        //Finds the tab layout and the viewpager, which shows the content for each tab
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        //Essentially this displays the tabs and the content each tab's fragment holds.
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

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

        //This is used to navigate between each supplementary
        //activity via the action bar.
        Intent intent;
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.adding_button:
                intent = new Intent(MainPage.this, SubmitForm.class);
                MainPage.this.startActivity(intent);
                return true;
            case R.id.login_button:
                intent = new Intent(MainPage.this, LoginMain.class);
                MainPage.this.startActivity(intent);
                return true;
            case R.id.register_button:
                intent = new Intent(MainPage.this, Registration.class);
                MainPage.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /* Demonstrates what happens when the user clicks an image button.
     * This is for demonstration purposes. The full implementation will
     * be dynamically generated and use fragments but for demonstration
     * it is just an image in another activity.
     */
    public void onUserClickConcept(View view) {
        Intent intent = new Intent(MainPage.this, PageView.class);
        MainPage.this.startActivity(intent);
    }

}


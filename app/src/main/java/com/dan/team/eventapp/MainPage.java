package com.dan.team.eventapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

public class MainPage extends AppCompatActivity {

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);


        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);


//        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
//                MainPage.this));
//
//        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);

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
            login();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addEvent()
    {
        Intent intent = new Intent(MainPage.this, SubmitForm.class);
        MainPage.this.startActivity(intent);
    }

    public void login()
    {
        Intent intent = new Intent(MainPage.this, LoginPage.class);
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


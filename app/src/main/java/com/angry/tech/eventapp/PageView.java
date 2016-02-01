package com.angry.tech.eventapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.team.eventapp.R;

import java.util.ArrayList;

/* This page provides a larger image for the selected event. */
public class PageView extends AppCompatActivity {
    ImageView eventPicture;
    Intent intent = getIntent();
    ArrayList<String> data = getIntent().getStringArrayListExtra("dataPacket");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        //Used to navigate back on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        for(String dataString: data)
        {
            Log.d("IN PAGEVIEW", dataString);
        }

//        eventPicture = (ImageView) findViewById(R.id.eventPicture);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent;

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.adding_button:
                intent = new Intent(PageView.this, SubmitForm.class);
                PageView.this.startActivity(intent);
                return true;
            case R.id.login_button:
                intent = new Intent(PageView.this, LoginMain.class);
                PageView.this.startActivity(intent);
                return true;
            case R.id.register_button:
                intent = new Intent(PageView.this, Registration.class);
                PageView.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onImageClick(View v) {
            Intent intent = new Intent(PageView.this, PageBackView.class);
            PageView.this.startActivity(intent);
    }
}
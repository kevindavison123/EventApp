package com.dan.team.eventapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PageBackView extends AppCompatActivity {

    private String time;
    private String date;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_back_view);
        Bundle extras = getIntent().getExtras();
        time = extras.getString("Time");
        date = extras.getString("Date");
        eventName = extras.getString("EventName");
        TextView eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventTitle.setText(eventName);
        TextView eventTime = (TextView) findViewById(R.id.eventTime);
        eventTime.setText(time);
        TextView eventDate = (TextView) findViewById(R.id.eventDate);
        eventDate.setText(date);

        Intent intent = getIntent();
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

        return super.onOptionsItemSelected(item);
    }
    public void onImageClick(View v) {
        Intent intent = new Intent(PageBackView.this, PageView.class);
        intent.putExtra("Time",time);
        intent.putExtra("Date", date);
        intent.putExtra("EventName", eventName);
        PageBackView.this.startActivity(intent);
    }

}
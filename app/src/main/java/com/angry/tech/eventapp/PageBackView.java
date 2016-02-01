package com.angry.tech.eventapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dan.team.eventapp.R;

/*If an event is clicked the user will see the PAgeView, a large image of the event. If
  the PageVIew is clicked a transparent overlay will provide more information. This is that overlay*/
public class PageBackView extends AppCompatActivity {

    private String time;
    private String date;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_back_view);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        //Used to navigate back on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);


        /* Currently unimplemented dynamic functionality*/
//        Bundle extras = getIntent().getExtras();
//        time = extras.getString("Time");
//        date = extras.getString("Date");
//        eventName = extras.getString("EventName");
//        TextView eventTitle = (TextView) findViewById(R.id.eventTitle);
//        eventTitle.setText(eventName);
//        TextView eventTime = (TextView) findViewById(R.id.eventTime);
//        eventTime.setText(time);
//        TextView eventDate = (TextView) findViewById(R.id.eventDate);
//        eventDate.setText(date);
//          Intent intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_back_view, menu);
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
                intent = new Intent(PageBackView.this, SubmitForm.class);
                PageBackView.this.startActivity(intent);
                return true;
            case R.id.login_button:
                intent = new Intent(PageBackView.this, LoginMain.class);
                PageBackView.this.startActivity(intent);
                return true;
            case R.id.register_button:
                intent = new Intent(PageBackView.this, Registration.class);
                PageBackView.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //If the overlay is pressed the viewer will return to the original PageView
    public void onImageClick(View v) {
        Intent intent = new Intent(PageBackView.this, PageView.class);
//        intent.putExtra("Time",time);
//        intent.putExtra("Date", date);
//        intent.putExtra("EventName", eventName);
        PageBackView.this.startActivity(intent);
    }

}
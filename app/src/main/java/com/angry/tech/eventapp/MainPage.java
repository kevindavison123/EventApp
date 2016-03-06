package com.angry.tech.eventapp;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.angry.tech.eventapp.webclient.ServiceClass;
import com.dan.team.eventapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;


public class MainPage extends AppCompatActivity {



    private int width = 0;
    // Declaring the Toolbar Object
    private Toolbar toolbar;
    private TableLayout mainPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The App title is inherited from the main label so this allows the page to use the toolbar title instead.
        setTitle("");
        setContentView(R.layout.activity_main_page);
        App.setContext(MainPage.this);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);

        //Finds the tab layout and the viewpager, which shows the content for each tab
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs); //Creates the tab
        ViewPager pager = (ViewPager) findViewById(R.id.pager); //Show the correct page for a certain tab

        //Essentially this displays the tabs and the content each tab's fragment holds.
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);



         /*
         Layout creator, adds three buttons per line, if it can.
         A button is associated with a text file from the database.
         The event is fetched from the database, a button is created, and the link is made.
         */

        mainPage = (TableLayout) findViewById(R.id.tableLayout);



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
        //activity via the action bar and overflow menu.
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
    //TODO make sure filter is set correctly once the backend have their shit together
    public void allEvents()
    {
//        ServiceClass.formGetAll(mainPage, phoneWidth());
        LocalBroadcastManager.getInstance(this).registerReceiver(GetAllEventsReciever,
                new IntentFilter(getApplicationContext().getResources().getString(Integer.parseInt("http://localhost:8080/events/allevents/")))); //possibly right?
    }
    public void getWeekly()
    {
        ServiceClass.formGetWeekly(mainPage, phoneWidth());
    }
    public void getMovie()
    {
        ServiceClass.formGetWeekly(mainPage,phoneWidth());
    }


    private int phoneWidth()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        return width;
    }

    private BroadcastReceiver GetAllEventsReciever= new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            int phoneWidth = phoneWidth();
            Bundle jsonBundle = getIntent().getExtras();
            JSONArray allEvents = (JSONArray) jsonBundle.get("events"); //Todo: events is dependent on how/where this intent is coming from and how its packaged
            int size = allEvents.length();
            //setup LayoutParams
            setupTableLayoutParams();
            //setup RowParams (needed for imageButtons layout)
            TableRow.LayoutParams tableRowParams = setupTableRowParams(phoneWidth);
            //Make image buttons
            ArrayList<ImageButton> imageButtons = createImageButtons(size, tableRowParams, allEvents);
            //put buttons in correct spot in table
            setupTableLayoutWithRows(size,imageButtons,context);

            Log.d("Got ALL EVENTS???", allEvents.toString());

        }
    };

    private BroadcastReceiver getImage(final ImageButton imgButton) //Todo: Does this need its own receiver? How is the image bundled with the rest of the JSON?
    {
        BroadcastReceiver imageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle imageBundle = getIntent().getExtras();
                byte[] imageBits = (byte[]) imageBundle.get("image");
                Bitmap image = BitmapFactory.decodeByteArray(imageBits, 0, imageBits.length);
                imgButton.setImageBitmap(image);
            }
        };

        return imageReceiver;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private ArrayList<ImageButton> createImageButtons(int size,TableRow.LayoutParams tableRowParams, JSONArray jsonArray)
    {
        ArrayList<ImageButton> imageButtons = new ArrayList<>();
        for(int id = 0; id<size; id++)
        {
            Intent sendingToPage = new Intent(this,PageView.class);
            ImageButton imageButton = new ImageButton(this);
            imageButton.setId(id);
            imageButton.setCropToPadding(true);
            imageButton.setAdjustViewBounds(true);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
            imageButton.setBackground(null);
            imageButton.setLayoutParams(tableRowParams);
            getImage(imageButton);
            imageButtons.add(imageButton);
//TODO: get or iterate through to get the correct JSONshit, create an event class, set all the right shit and then put it into the intent and send it through for the next activitiy/fragment
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    /*
                    Event event = new Event()
                    event.setTitle(jsonArray.get(i).getJSONObject("Title").opt("val").toString()
                    event.setTime(jsonArray.get(i).getJSONObject("Time").opt("val").toString()
                    event.setDescription(jsonArray.get(i).getJSONObject("Description").opt("val").toString()
                    event.setLocation(jsonArray.get(i).getJSONObject("Location").opt("val").toString()
                    sendingToPage.putExtra("event",event);
                    startActivity(sendingToPage);
                     */
                }
            });
        }
        return imageButtons;
    }

    private void setupTableLayoutParams()
    {
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableLayoutParams.setMargins(0,0,0,0);
    }
    private TableRow.LayoutParams setupTableRowParams(int phoneWidth)
    {
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(phoneWidth/2,phoneWidth/2);
        tableRowParams.setMargins(0,0,0,0);
        return tableRowParams;
    }

    private void setupTableLayoutWithRows(int size, ArrayList<ImageButton> imageButtons, Context context)
    {
        ArrayList<TableRow> tableRows = new ArrayList<>();

        if(size%2==0)
        {
            int count =0;
            while(count<=size/2)
            {
                TableRow newRow = new TableRow(context);
                tableRows.add(newRow);
                count++;
            }
        }
        else
        {
            int count =0;
            while(count<=(size/2)+1)
            {
                TableRow newRow = new TableRow(context);
                tableRows.add(newRow);
                count++;
            }
        }
        //
        TableRow row = new TableRow(context);
        mainPage.addView(row);
        // Is this block necessary?
        for(int i = 0; i<imageButtons.size(); i++) {
            if (i % 2 == 0) {
                tableRows.get(i / 2).addView(imageButtons.get(i));
            } else {
                tableRows.get((i - 1) / 2).addView(imageButtons.get(i));
                mainPage.addView(tableRows.get((i - 1) / 2));
            }

        }
    }


}


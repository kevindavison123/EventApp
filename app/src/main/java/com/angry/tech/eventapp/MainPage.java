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
    public void allEvents()
    {
        ServiceClass.formGetAll(mainPage, phoneWidth());
        LocalBroadcastManager.getInstance(this).registerReceiver(GetAllEventsReciever,
                new IntentFilter(getApplicationContext().getResources().getString(Integer.parseInt("http://localhost:8080/events/allevents/"))));
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
            JSONArray allEvents = (JSONArray) jsonBundle.get("events");
            ArrayList<ImageButton> imageButtons = new ArrayList<>();
            ArrayList<TableRow> tableRows = new ArrayList<>();
            int size = allEvents.length();
            final Bundle jsonPacket = new Bundle();
            TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(phoneWidth/2,phoneWidth/2);
            tableRowParams.setMargins(0,0,0,0);
            TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            tableLayoutParams.setMargins(0,0,0,0);
            for (int i = 0; i < size; i++)
            {

                ArrayList<String> dataPacket = new ArrayList<String>();
                String packetName ="";
                ImageButton newButton = new ImageButton(context);
                imageButtons.add(newButton);
                try {
                    JSONObject jsonObject = allEvents.getJSONObject(i);
                    Iterator<String> fieldIterator = jsonObject.keys();
                    while(fieldIterator.hasNext())
                    {
                        String key = fieldIterator.next();
                        String value = jsonObject.get(key).toString();
                        dataPacket.add(value);
                        if(key.equals("eventId"))
                        {

                            packetName = jsonObject.get(key).toString();
                            Log.d("Packet name is ", "" + packetName);
                        }
                    }
                    jsonPacket.putStringArrayList(packetName, dataPacket);
                    Log.d("jsonPacket: ", jsonPacket.get(packetName).toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
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
            TableRow row = new TableRow(context);
            mainPage.addView(row);
            for(int i = 0; i<imageButtons.size(); i++)
            {

                int id = i+1;
                imageButtons.get(i).setId(id);
                imageButtons.get(i).setCropToPadding(true);
                imageButtons.get(i).setAdjustViewBounds(true);
                imageButtons.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
                imageButtons.get(i).setBackground(null);
                imageButtons.get(i).setLayoutParams(tableRowParams);
                getImage(imageButtons.get(i));

                final String ID = String.valueOf(id);
                //When a button is pressed, sent the json packet to the next activity
                imageButtons.get(i).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(mainPage.getContext(), PageView.class);
                        intent.putStringArrayListExtra("dataPacket", (ArrayList<String>) jsonPacket.get(ID));
                        Log.d("THIS IS THE ID", ID);
                        Log.d("THIS IS THE DATA", jsonPacket.get(ID).toString());

                        mainPage.getContext().startActivity(intent);
                    }
                });
                Log.d("This is the i", " " + i);

                if(i%2==0)
                {
                    tableRows.get(i/2).addView(imageButtons.get(i));
                }
                else
                {
                    tableRows.get((i-1)/2).addView(imageButtons.get(i));
                    mainPage.addView(tableRows.get((i-1)/2));
                }
            }

            Log.d("omg android", allEvents.toString());

        }
    };

    private BroadcastReceiver getImage(final ImageButton imgButton)
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

}


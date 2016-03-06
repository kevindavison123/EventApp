package com.angry.tech.eventapp.webclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dan.team.eventapp.R;
import com.dan.team.eventapp.webclient.DatabaseServices;

/*
 * Author: Jakob Rodseth
 * Date: 10/15/2015
 *
 * This is an example bound activity that requests an ArrayList of Event objects
 * from DatabaseServices.
 *
 * Use this as a template to add database communication to your activity.
 * It may be confusing at first!
 * That's okay!
 *
 */

public class BindingClassExample extends Activity
{

    DatabaseServices dbService; //The database service instance the activity binds to.
    boolean dbBound = false; //Is the activity bound to the service

    /* When the activity binds to a service, the ServiceConnection moderates the communication.
    */
    private ServiceConnection dbConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_class_example);

        dbConnection = new ServiceConnection()
        {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service)
            {
                // We've bound to LocalService, cast the IBinder and get LocalService instance
                DatabaseServices.DatabaseBinder binder = (DatabaseServices.DatabaseBinder) service;
                dbService = binder.getService(); //Store reference to the instance of the service that we bound to.
                dbBound = true;

                testDBServices();

                Toast.makeText(getApplicationContext(), "Database Services Requested", Toast.LENGTH_SHORT).show(); //TODO:Debug
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0)
            {
                dbBound = false;
            }
        };

        Log.d(BindingClassExample.class.getSimpleName(), "created");


        /*
        * The LocalBroadcastManager will register your activity as a listener for a specific message from DatabaseServices.
        *
        * //TODO: replace DatabaseProfileEnumerationMessageReceiver with your listener.
        * //TODO: replace R.string.EventProfilesEnumeratedMessage with the message you want.
        *
        * IMPORTANT: If you need to listen for several different types of updates, you will need to register multiple
        * different BroadcastReceivers with different IntentFilters.
        *
        * Example: I need to get both schedule information and GPS location information.
        * 1. Create two different BroadcastReceivers that will parse your request correctly.
        *       One will parse an array of scheduleInfo objects, called DatabaseScheduleUpdateMessageReceiver
        *       One will parse a GPSCoords object, called DatabaseGPSUpdateMessageReceiver
        *
        * 2. Register them in the manner below
        * LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseScheduleUpdateMessageReceiver,
        *        new IntentFilter(getApplicationContext().getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage)));
        *  LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseGPSUpdateMessageReceiver,
        *        new IntentFilter(getApplicationContext().getResources().getString(R.string.EventGPSUpdateMessageEnumeratedMessage)));
        *
        LocalBroadcastManager.getInstance(this).registerReceiver(NAME OF RECEIVER,
                new IntentFilter(getApplicationContext().getResources().getString(String for get or post)));



       /* setListAdapter(new ArrayAdapter<String>(this, R.layout., currentProfiles));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        /*
        * To trigger your requests from onServiceConnected, the activity needs to bind to the service.
        * It will make calls to Database methods and then unbind.
        * TODO: Include this code anytime you want to trigger onServiceConnected request code
        * */
        Intent intent = new Intent(this, DatabaseServices.class);
        bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (dbBound)
        {
            unbindService(dbConnection);
            dbBound = false;
        }
    }

    public void testDBServices() //Example situation in which you would bind to the service
    {
        if (dbBound)
        {
            dbService.post(new User("Bob", "Ross", "Bob_Ross@Email.com", "LittleFriend"));
            Toast.makeText(getApplicationContext(), "Testing", Toast.LENGTH_SHORT).show();//TODO:Debug
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * Example BroadcastReceiver. You should make your own BroadcastReceiver to get info in a way that makes sense for your activity
    *
    private BroadcastReceiver DatabaseProfileEnumerationMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Toast.makeText(context, "Broadcast Received, Attempting Reassembly", Toast.LENGTH_SHORT).show();//TODO:Debug
            //TODO: Replace this line. Example: intent.getParcelable(context.getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage));
            ArrayList<Event> updatedProfiles = intent.getParcelableArrayListExtra(context.getResources().getString(R.string.EventArrayMessage));

            String output = "";

            if (updatedProfiles == null)
            {
                output = "GET FAILURE";
                Toast.makeText(context, "Reassembly Failure, Probable Extra Error", Toast.LENGTH_SHORT).show();//TODO:Debug
            }
            else
            {
                output = "GET PASS:\n";
                Toast.makeText(context, "Reassembly Success", Toast.LENGTH_SHORT).show();//TODO:Debug


                currentProfiles = updatedProfiles;

                for (Event e : currentProfiles)
                {
                    Toast.makeText(context, "Event Created: " + e.toString(), Toast.LENGTH_SHORT).show();//TODO:Debug
                    output += e.toString() + "\n\n";
                }
            }


            TextView textView = (TextView) findViewById(R.id.event_enumerate_text);
            textView.setText(output);

            //TODO: Make sure to update the display after info has been updated
            /*
            * Example call to imaginary method:
            * UpdateEventPictures(currentProfiles);
            * *
        }
    };
            */
}

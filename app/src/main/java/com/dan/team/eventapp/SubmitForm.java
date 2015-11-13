package com.dan.team.eventapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SubmitForm extends AppCompatActivity {

    Button buttonLoadImage;
    private TextView pDate, pTime;
    ImageView uploadImage;
    private static int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_submit_form);

        //Creates the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        // Not needed for some reason getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        TextView pDate = (TextView) findViewById(R.id.pickDate);
        pDate.setTypeface(Typeface.SERIF);

        TextView pTime = (TextView) findViewById(R.id.pickTime);
        pTime.setTypeface(Typeface.SERIF);


        uploadImage = (ImageView) findViewById(R.id.imgView);
        buttonLoadImage =(Button) findViewById(R.id.loadImageButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            uploadImage.setImageURI(selectedImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_form, menu);
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
        Intent intent = new Intent(SubmitForm.this, SubmitForm.class);
        SubmitForm.this.startActivity(intent);
    }

    public void login()
    {
        Intent intent = new Intent(SubmitForm.this, LoginPage.class);
        SubmitForm.this.startActivity(intent);
    }

}

package com.dan.team.eventapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;


import com.dan.team.eventapp.webclient.ServiceClass;

import java.util.Date;
import java.util.Random;


public class SubmitForm extends AppCompatActivity {


    Button buttonLoadImage;
    Button submitForm;
    ImageView uploadImage;
    EditText author;
    EditText photoLoc;
    EditText groupName;
    EditText titleName;
    DatePicker date;
    TimePicker time;
    EditText details;
   private Uri photo;

    private ServiceClass serviceClass = new ServiceClass();
    private static int RESULT_LOAD_IMAGE = 1;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setContext(SubmitForm.this);
        Intent intent = getIntent();
        setContentView(R.layout.activity_submit_form);
        uploadImage = (ImageView) findViewById(R.id.imgView);
        buttonLoadImage =(Button) findViewById(R.id.loadImageButton);
        submitForm = (Button) findViewById(R.id.submitButton);
        layout = (LinearLayout) findViewById(R.id.formLayout);
        author = (EditText) findViewById(R.id.yourName);
        details = (EditText) findViewById(R.id.editText);
        groupName = (EditText) findViewById(R.id.groupName);
        titleName = (EditText) findViewById(R.id.subjectName);
        date = (DatePicker) findViewById(R.id.datePicker);
        time = (TimePicker) findViewById(R.id.timePicker);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
        submitForm.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Random random = new Random();
                int author = random.nextInt(300);

                String group = groupName.getText().toString();
                String title = titleName.getText().toString();
                int month = date.getMonth()+1;
                int day = date.getDayOfMonth();
                String photoLoc = getURIPath();
                int year = date.getYear();
                String getDate = month + "/" + day + "/" + year;
                int hour = time.getCurrentHour();
                int minute = time.getCurrentMinute();
                String location = "hicks";
                String description = details.getText().toString();
                String time = hour + ":" + minute;
//                Toast.makeText(SubmitForm.this,photoLoc, Toast.LENGTH_LONG).show();
                sendImageToServer(title + getDate, photoLoc);
                serviceClass.postNewEvent(author,photoLoc,description,title,location,getDate,time);
            }
        });

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
           createImage(data);
        }
        Toast.makeText(SubmitForm.this,getURIPath(), Toast.LENGTH_LONG).show();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Uri createImage(Intent data)
    {
        Uri selectedImage = data.getData();
        setUriPath(selectedImage);
        uploadImage.setImageURI(selectedImage);
        uploadImage.setAdjustViewBounds(true);
        uploadImage.setCropToPadding(true);

        return selectedImage;
    }
    private void setUriPath(Uri image)
    {
       this.photo = image;

    }
    private String getURIPath()
    {
        Cursor cursor = null;
        try {

            Context context = App.getContext();
            Uri imagePicked = this.photo;
            String[] proj = { MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(imagePicked, proj, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        }finally {
            if(cursor != null)
            {
                cursor.close();
            }
        }
    }


    private void sendImageToServer(String name, String photoLocation)
    {
//        Bitmap image = ((BitmapDrawable)uploadImage.getDrawable()).getBitmap();
        serviceClass.postImage(name,photoLocation);

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
        if(id == R.id.back_button)
        {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void back()
    {
        Intent intent = new Intent(SubmitForm.this, MainPage.class);
        SubmitForm.this.startActivity(intent);
    }
}

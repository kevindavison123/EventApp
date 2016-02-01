package com.angry.tech.eventapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.angry.tech.eventapp.webclient.ServiceClass;
import com.dan.team.eventapp.R;

import cz.msebera.android.httpclient.cookie.Cookie;

/*This page is used for submitting events*/
public class SubmitForm extends AppCompatActivity {

    Button buttonLoadImage;
    Button submitForm;

    ImageView uploadImage;
    ImageView thumbView;

    EditText eventName;
    EditText eventLocation;
    EditText editDescription;

    DatePicker date;
    TimePicker time;

    private Uri photo;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setContext(SubmitForm.this);
        setContentView(R.layout.activity_submit_form);

        //Finds the toolbar and sets it up as an actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //Sets the toolbar title and font.
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.SERIF);
        //Used to navigate back on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);


        TextView pDate = (TextView) findViewById(R.id.pickDate);
        pDate.setTypeface(Typeface.SERIF);
        TextView pTime = (TextView) findViewById(R.id.pickTime);
        pTime.setTypeface(Typeface.SERIF);


        uploadImage = (ImageView) findViewById(R.id.imgView);
        thumbView = (ImageView) findViewById(R.id.imgThumb);
        buttonLoadImage = (Button) findViewById(R.id.loadImageButton);
        submitForm = (Button) findViewById(R.id.submitButton);

        eventName = (EditText) findViewById(R.id.eventName);
        eventLocation = (EditText) findViewById(R.id.eventLocation);
        editDescription = (EditText) findViewById(R.id.editDescription);

        date = (DatePicker) findViewById(R.id.datePicker);
        time = (TimePicker) findViewById(R.id.timePicker);

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        submitForm.setOnClickListener(new View.OnClickListener() {

            //Creates the event.
            @Override
            public void onClick(View v) {
                Cookie authorId = ServiceClass.getCookieValue("id");

                if (authorId == null) {
                    Toast.makeText(getApplicationContext(),"You must be logged in to create an event", Toast.LENGTH_SHORT).show();
                } else {

                    int author = Integer.parseInt(authorId.getValue());
                    String title = eventName.getText().toString();
                    String description = editDescription.getText().toString();
                    String location = eventLocation.getText().toString();

                    int month = date.getMonth() + 1;
                    int day = date.getDayOfMonth();
                    int year = date.getYear();
                    String dateSting = year + "-" + month + "-" + day;

                    int hour = time.getCurrentHour();
                    int minute = time.getCurrentMinute();
                    String timeString = hour + ":" + minute + ":00";


                    //                sendImageToServer(dateString);
                    ServiceClass.postNewEvent(author, "", description, title, location, dateSting,
                            timeString);

                    Toast.makeText(getApplicationContext(),"Event Created!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            createImage(data);
        }
        Toast.makeText(SubmitForm.this, getURIPath(), Toast.LENGTH_LONG).show();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Uri createImage(Intent data) {
        Uri selectedImage = data.getData();
        setUriPath(selectedImage);
        uploadImage.setImageURI(selectedImage);
        uploadImage.setAdjustViewBounds(true);
        uploadImage.setCropToPadding(true);

        return selectedImage;
    }

    private void setUriPath(Uri image) {
        this.photo = image;

    }

    private String getURIPath() {
        Cursor cursor = null;
        try {

            Context context = App.getContext();
            Uri imagePicked = this.photo;
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(imagePicked, proj, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void sendImageToServer(String name)
    {
        Bitmap image = ((BitmapDrawable)uploadImage.getDrawable()).getBitmap();
        Bitmap thumbnail = resizeImage(image,200,200);
        //Teessttingggg permissions
        thumbView.setImageBitmap(thumbnail);
//        serviceClass.postImage(name,image,thumbnail);
    }

    private Bitmap resizeImage(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
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

        //This is used to navigate between each supplementary
        //activity via the action bar.
        Intent intent;
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.adding_button:
                intent = new Intent(SubmitForm.this, SubmitForm.class);
                SubmitForm.this.startActivity(intent);
                return true;
            case R.id.login_button:
                intent = new Intent(SubmitForm.this, LoginMain.class);
                SubmitForm.this.startActivity(intent);
                return true;
            case R.id.register_button:
                intent = new Intent(SubmitForm.this, Registration.class);
                SubmitForm.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}

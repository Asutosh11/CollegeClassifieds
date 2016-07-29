package com.example.asu.collegeclassifieds;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class NewAd extends AppCompatActivity {

    EditText title;
    EditText description;

    Button imageAttach;
    Button submit;

    String titleText;
    String descriptionText;

    RadioGroup category;
    int RadioSelectedId;
    RadioButton selectedRadioButton;
    String categoryText;
    ParseObject entry;
    boolean imageUloaded;
    String timestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ad);

        timestamp = "" + System.currentTimeMillis() / 1000;

        imageUloaded = false;


        // Create a object of "ClassifiedsEntry" class in Parse.
        // A new object(new row in Parse.com cloud) is created everytime this activity is run.
        entry = new ParseObject("ClassifiedsEntry");


        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);

        category = (RadioGroup)findViewById(R.id.category);

        imageAttach = (Button)findViewById(R.id.imageAttach);
        submit = (Button)findViewById(R.id.submit);

    }



    public String GetTitle(){
        titleText = title.getText().toString();
        return  titleText;
    }



    public void DataSubmit(View view){

        titleText = title.getText().toString();
        descriptionText = description.getText().toString();

        RadioSelectedId = category.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton)findViewById(RadioSelectedId);

        categoryText = selectedRadioButton.getText().toString();


        entry.put("AdTitle", titleText);
        entry.put("DescriptionText", descriptionText);
        entry.put("Category", categoryText);
        entry.put("Timestamp", timestamp);

        // Create the class and the columns
        try {
            entry.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

       // Log.d("the msg", titleText + " " + descriptionText + " " + categoryText);

        if(imageUloaded == true){
            Intent c = new Intent(getApplicationContext(),UploadThanks.class);
            startActivity(c);
        }

    }

    public void UploadImage(View view){

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);


            // Convert it to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            if(bitmap.getWidth() >= 700 && bitmap.getHeight() >= 700){
                bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
            }

            byte[] image = stream.toByteArray();

            Log.d("amkalsd", timestamp);

            String tit = GetTitle();

            // Create the ParseFile
            ParseFile file = new ParseFile(timestamp+"-"+tit+".jpg", image);


            String PATH = "/data/data/com.example.asu.collegeclassifieds/";
            OutputStream fOut = null;
            File file2 = new File(PATH, timestamp+"-"+tit+".jpg"); // the File to save to
            try {
                fOut = new FileOutputStream(file2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close(); // do not forget to close the stream
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        file2.getAbsolutePath(),file2.getName(),file2.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            // Create a column named "ImageFile" and insert the image
            entry.put("ImageFile", file);


            imageUloaded = true;


          }
        }


    public void GoBack(){
        Intent c = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.go_to_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.go_back) {
            GoBack();
        }

        return super.onOptionsItemSelected(item);
    }





}

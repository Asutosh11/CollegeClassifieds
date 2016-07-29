package com.example.asu.collegeclassifieds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class UploadThanks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.upload_thanks);

    }

    public void GoToListings(View view){
        Intent g2 = new Intent(getApplicationContext(),ImageDownloadFromParseDB.class);
        startActivity(g2);
    }

}

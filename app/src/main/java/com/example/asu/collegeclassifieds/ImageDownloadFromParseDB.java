package com.example.asu.collegeclassifieds;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by asu on 14-06-2015.
 */
public class ImageDownloadFromParseDB extends Activity{



    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_download_from_parse);

        // progress bar (spinner) code start

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading Ads...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        // progress bar (spinner) code end


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // Intent Service code starts here

        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

        // Intent service code ends here

        /*

        SharedPreferences sharedPref = getSharedPreferences("statusInfo", Context.MODE_PRIVATE);

        String st = sharedPref.getString("Username", "");

        if (st.equals("over")){
            Intent h = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(h);
        }

        */

    }


}








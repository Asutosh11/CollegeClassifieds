package com.example.asu.collegeclassifieds;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");

    }

    List<ParseObject> ob;

    ParseFile[] imagesFromDB =  new ParseFile[500];
    String[] ParseDBurl =  new String[500];

    String fileName;
    URL url;
    URLConnection ucon;
    InputStream is;
    BufferedInputStream bis;
    FileOutputStream fos;
    String PATH;
    String Fname;


    @Override
    protected void onHandleIntent(Intent intent) {

        // your code goes here

        GetImageLinks();


    }

    public void DownloadFromUrl(String ImageLinkString, String fileName) {

        try {

            URL url = new URL(ImageLinkString);
            PATH = "/data/data/com.example.asu.collegeclassifieds/";
            File file = new File(PATH+fileName);

            long startTime = System.currentTimeMillis();
            Log.d("ImageManager", "download begining");
            Log.d("ImageManager", "download url:" + url);
            Log.d("ImageManager", "downloaded file name:" + fileName);
                      
            URLConnection ucon = url.openConnection();
                        
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
                        
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(baf.toByteArray());
            fos.close();
            Log.d("ImageManager", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");

        } catch (IOException e) {
            Log.d("ImageManager", "Error: " + e);
        }

    }




    void GetImageLinks() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ClassifiedsEntry");
        query.orderByDescending("_created_at");
        try {
            ob = query.find(); // 'List<ParseObject> ob' initialzed above OnCreate
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject images : ob) {
            imagesFromDB[i] = (ParseFile) images.getParseFile("ImageFile");

            String tit = (String)images.get("AdTitle");
            String timestamp = (String)images.get("Timestamp");

            // Its getting image URLS and getting saved in variable 'url' successfully.

            try {
                url = new URL(ParseDBurl[i]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            PATH = "/data/data/com.example.asu.collegeclassifieds/";
            String fileName = timestamp+"-"+tit+".jpg";
            String fullPath = PATH+fileName;
            File file = new File(fullPath);

            Log.d("the check", fileName);

            if(!file.exists()){

                // If the image is not saved in phone, download it. Otherwise not.
                Fname = timestamp+"-"+tit+".jpg";
                DownloadFromUrl(ParseDBurl[i], Fname);

            }

            i++;
        }


        Intent dialogIntent = new Intent(this, MainActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);

    }



}

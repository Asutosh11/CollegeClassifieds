package com.example.asu.collegeclassifieds;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClassifiedsListingFragment3 extends Fragment {

    ListView listView;
    SimpleAdapter adapter;
    List<ParseObject> ob;

    public String[] titlesFromDB =  new String[500];

    public String[] descFromDB =  new String[500];


    public String[] titlesPassedValues =  new String[500];

    public String[] descPassedValues =  new String[500];


    public String[] getTimestamp =  new String[500];

    public String[] TimestampPassed =  new String[500];


    public int[] categoryFromDBcheck =  new int[500];

    public int[] getCategoryCheck =  new int[500];

    String PATH;

    int noOfRows = 0;

    SharedPreferences sharedPref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab3.xml
        View view = inflater.inflate(R.layout.fragment_classifieds_listing, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        titlesPassedValues = getTitlesFromParseCloudDB();

        descPassedValues = getDescriptionFromParseCloudDB();

        getCategoryCheck = CheckCategoryFromParseCloudDB();

        TimestampPassed = getTimestampFunc();


        // ListView code starts here.
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<noOfRows;i++){

            if(getCategoryCheck[i] == 1){
                HashMap<String, String> hm = new HashMap<String,String>();

                PATH = "/data/data/com.example.asu.collegeclassifieds/";

                String fileName = TimestampPassed[i]+"-"+titlesPassedValues[i]+".jpg";
                String fullPath = PATH+fileName;

                hm.put("listViewImage", "" + fullPath);
                hm.put("listViewTitle", "" + descPassedValues[i]);

                aList.add(hm);
            }

        }

        // Keys used in Hashmap
        String[] from = { "listViewImage","listViewTitle" };

        // Ids of views in listview_layout
        int[] to = { R.id.listViewImage,R.id.listViewTitle};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        adapter = new SimpleAdapter(getActivity(), aList, R.layout.list_view_layout, from, to);


        // Getting a reference to listview of main.xml layout file
        listView = (ListView)view.findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Do something here


            }
        });

        return view;



    }


    String[] getTimestampFunc() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ClassifiedsEntry");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        }
        catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {
            getTimestamp[i] = (String) country.get("Timestamp");
            Log.d("", getTimestamp[i]);
            i++;

        }

        return getTimestamp;
    }



    String[] getTitlesFromParseCloudDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ClassifiedsEntry");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        }
        catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {
            titlesFromDB[i] = (String) country.get("AdTitle");
            Log.d("", titlesFromDB[i]);
            i++;
            noOfRows++;

        }

        return titlesFromDB;
    }


    int[] CheckCategoryFromParseCloudDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ClassifiedsEntry");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        }
        catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {

            String categor = (String) country.get("Category");
            if (categor.equals("Other Things")){
                categoryFromDBcheck[i] = 1;
            }

            else{
                categoryFromDBcheck[i] = 0;
            }
            i++;
        }

        return categoryFromDBcheck;
    }




    String[] getDescriptionFromParseCloudDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ClassifiedsEntry");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        }
        catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {
            descFromDB[i] = (String) country.get("DescriptionText");
            Log.d("", descFromDB[i]);
            i++;

        }

        return descFromDB;
    }


}
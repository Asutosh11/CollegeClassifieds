package com.example.asu.collegeclassifieds;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "ZyVatMWOK09umeJ9cEpCsmEtqbVe35462jb06O6x",
                "XpuKPUiNpZxVF5VeRMRBEMFe6GDd7mlUgd1CLG6Z");
    }
}

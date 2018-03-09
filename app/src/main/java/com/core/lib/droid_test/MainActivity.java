package com.core.lib.droid_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.core.lib.CoreView;
import com.core.lib.Droid;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           Droid droid = new Droid(this);



    }
}

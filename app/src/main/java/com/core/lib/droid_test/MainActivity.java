package com.core.lib.droid_test;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.core.lib.CoreFile;
import com.core.lib.CoreFileFolder;
import com.core.lib.CoreHttp;
import com.core.lib.CoreHttpResponsed;
import com.core.lib.CoreView;
import com.core.lib.Dialog;
import com.core.lib.Droid;
import com.core.lib.HttpFileRequest;
import com.core.lib.HttpRequest;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Droid droid = new Droid(this);


    }
}

package com.core.lib.droid_test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.core.lib.CoreFile;
import com.core.lib.CoreFileFolder;
import com.core.lib.CoreHttp;
import com.core.lib.CoreHttpResponsed;
import com.core.lib.CoreView;
import com.core.lib.Droid;
import com.core.lib.Event;
import com.core.lib.EventResult;
import com.core.lib.HttpHolder;
import com.core.lib.HttpRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int PERMISSION_REQUEST_CODE = 1;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {


                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }


            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {


                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }



        final Droid droid = new Droid(this);


      final  CoreView view = new CoreView(droid,findViewById(R.id.mtext));
     final CoreView btn = new CoreView(droid,findViewById(R.id.button));

        CoreFile file = new CoreFile(droid,"");

        CoreFileFolder images = file.$Folder("/images");


         images.$Read("");

        CoreHttp xhr  = new CoreHttp(droid);



          xhr.$Get("fewfew", new HttpRequest() {
              @Override
              public void OnSuccess(CoreHttpResponsed response) {

              }

              @Override
              public void OnError(CoreHttpResponsed response) {

              }

              @Override
              public void OnHttpError(Exception e) {

              }
          });




    }
}

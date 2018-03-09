package com.core.lib;

import java.io.File;
import java.util.HashMap;

/**
 * Created by l on 07/01/2018.
 */

public class CoreHttp {

    final private Droid core;

    public CoreHttp(Droid core){

        this.core = core;

    }


    public void $Post(String REQUEST_ID, String URL, HashMap<String,String> POSTDATA, Runnable ONSUCCESS, Runnable ONERROR){

         core.$PostRequest(REQUEST_ID,URL,POSTDATA,ONSUCCESS,ONERROR);

    }


    public void $Put(String REQUEST_ID, String URL, HashMap<String,String> POSTDATA, Runnable ONSUCCESS, Runnable ONERROR){

        core.$PutRequest(REQUEST_ID,URL,POSTDATA,ONSUCCESS,ONERROR);

    }

    public void $Get(String REQUEST_ID, String URL, Runnable ONSUCCESS, Runnable ONERROR){

        core.$GetRequest(REQUEST_ID,URL,ONSUCCESS,ONERROR);

    }

    public void $Delete(String REQUEST_ID, String URL, Runnable ONSUCCESS, Runnable ONERROR){

        core.$GetRequest(REQUEST_ID,URL,ONSUCCESS,ONERROR);

    }

    public void $Dowload(String URL,CoreFileFolder FOLDER,String FILENAME,Runnable r){

        core.$Download(URL,FOLDER.$Path(),FILENAME,r);

    }

    public void $Upload(String ID, String URL, String FILEPATH, HashMap<String,String> POSTDATA, Runnable ONPROGRESS, Runnable ONLOAD,Runnable ERROR){

        File FILE = new File(FILEPATH);

        core.$Upload(ID,URL,FILE,POSTDATA,ONPROGRESS,ONLOAD,ERROR);

    }


}

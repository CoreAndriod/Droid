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


    public void $Post(String URL, HashMap<String,String> POSTDATA,HttpRequest request){

         core.$PostRequest(URL,POSTDATA,request);

    }


    public void $Put( String URL, HashMap<String,String> POSTDATA, HttpRequest request){

        core.$PutRequest(URL,POSTDATA,request);

    }

    public void $Get( String URL, HttpRequest request){

        core.$GetRequest(URL,request);

    }

    public void $Delete( String URL, HttpRequest request){

        core.$DeleteRequest(URL,request);

    }

    public void $Dowload(String URL,String FILENAME,HttpFileRequest request){

        core.$Download(URL,FILENAME,request);

    }

    public void $Upload( String URL, String FILEPATH, HashMap<String,String> POSTDATA, HttpFileRequest request){

        File FILE = new File(FILEPATH);

        core.$Upload(URL,FILE,POSTDATA,request);

    }


}

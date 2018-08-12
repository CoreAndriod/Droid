package com.core.lib;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

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


    public HttpHolder $Post(String URL, HashMap<String,String> POSTDATA,HttpRequest request){

        HttpPost http = new HttpPost(URL);

         core.$PostRequest(URL,POSTDATA,request,http);

         return new HttpHolder(null,http,null,null);

    }


    public HttpHolder $Put( String URL, HashMap<String,String> POSTDATA, HttpRequest request){

        HttpPut http = new HttpPut(URL);

        core.$PutRequest(URL,POSTDATA,request,http);

        return new HttpHolder(null,null,http,null);

    }

    public HttpHolder $Get(String URL, HttpRequest request){

        HttpGet  http = new HttpGet(URL);

        core.$GetRequest(URL,request,http);

        return new HttpHolder(http,null,null,null);
    }

    public HttpHolder $Delete( String URL, HttpRequest request){

        HttpDelete http = new HttpDelete(URL);

        core.$DeleteRequest(URL,request,http);

        return new HttpHolder(null,null,null,http);

    }

    public void $Dowload(String URL,String FILENAME,HttpFileRequest request){

        core.$Download(URL,FILENAME,request);

    }

    public HttpHolder $Upload( String URL, String FILEPATH, HashMap<String,String> POSTDATA, HttpFileRequest request){

        File FILE = new File(FILEPATH);

        HttpPost http = new HttpPost(URL);

        core.$Upload(URL,FILE,POSTDATA,request,http);

        return new HttpHolder(null,http,null,null);
    }


}

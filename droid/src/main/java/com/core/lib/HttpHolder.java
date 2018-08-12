package com.core.lib;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

/**
 * Created by l on 30/03/2018.
 */

public class HttpHolder {


    public HttpGet Get = null;
    public HttpPut Put = null;
    public HttpDelete Delete = null;
    public HttpPost Post = null;
    public String HttpVerb = "";


    public  HttpHolder(HttpGet get,HttpPost post,HttpPut put,HttpDelete delete){

        if(get != null) Store(get);
        if(post != null) Store(post);
        if(put != null) Store(put);
        if(delete != null) Store(delete);

    }

    private void Store(HttpGet httpobject){

       this.Get = httpobject;
       this.HttpVerb = "GET";

    }

    public void Store(HttpPost httpobject){

        this.Post =  httpobject;
        this.HttpVerb = "POST";

    }

    public  void Store(HttpDelete httpobject){

        this.Delete = httpobject;
        this.HttpVerb = "DELETE";

    }

    public  void Store(HttpPut httpobject){

        this.Put = httpobject;
        this.HttpVerb = "PUT";

    }

    public void $Abort(){

        switch (this.HttpVerb.toUpperCase()){

            case"GET":

                this.Get.abort();

                break;

            case"POST":

                this.Post.abort();

                break;

            case"PUT":

                this.Put.abort();

                break;

            case"DELETE":

                this.Delete.abort();

                break;

        }

    }

}

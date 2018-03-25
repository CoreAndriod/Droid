package com.core.lib;

/**
 * Created by l on 24/03/2018.
 */

public  interface HttpFileRequest {

    void OnSuccess(CoreHttpResponsed response);
    void OnProgress(Integer ProgressCount);
    void OnError(CoreHttpResponsed response);
    void OnHttpError(Exception e);

}

package com.core.lib;

/**
 * Created by l on 18/03/2018.
 */

public interface HttpRequest {


    void OnSuccess(CoreHttpResponsed response);
    void OnError(CoreHttpResponsed response);
    void OnHttpError(Exception e);

}

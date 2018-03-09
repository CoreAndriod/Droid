package com.core.lib;

import android.view.View;

/**
 * Created by l on 23/01/2018.
 */

public class CoreView {

    private View v;
    private Droid core;
    public  CoreView(Droid core,View v){

      this.v = v;
      this.core = core;

    }


    public CoreEvent $Event(){

        return new CoreEvent(core,v);

    }

    public void $SetUrlImage(String URL,int BORDER_RADIUS){

        core.$GetUrlImage(v,URL,BORDER_RADIUS);

    }


    public View $GetView(){

        return this.v;

    }


}

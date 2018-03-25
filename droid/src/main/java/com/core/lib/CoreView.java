package com.core.lib;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by l on 23/01/2018.
 */

public class CoreView {

    private View v;
    private Droid core;
    public  CoreView(Droid core,View v){

      this.v = (v);
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

    public View $Text(String Text){

        ((TextView)this.v).setText(Text);

        return this.v;

    }

    public View $TextValue(String Text){

        ((EditText)this.v).setText(Text);

        return this.v;

    }

    public View $Image(Bitmap bmp){

        ((ImageView)this.v).setImageBitmap(bmp);

        return this.v;

    }


}

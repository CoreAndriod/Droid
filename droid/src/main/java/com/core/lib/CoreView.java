package com.core.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by l on 23/01/2018.
 */

public class CoreView   {

    private View Layout;
    private View v;
    private Droid core;

    public  CoreView(Droid core,View v){


      this.core = core;
      this.v = v;

    }


    public void $SetUrlImage(String URL,int BORDER_RADIUS){

        core.$GetUrlImage(v,URL,BORDER_RADIUS);

    }


    public View $GetView(){






        return this.v;

    }



    public String $Text(String Text){

        if(Text != null)((TextView)this.v).setText(Text);
        return ((TextView)this.v).getText().toString();
//
    }

    public String $Value(String Text){

        if(Text != null) ((EditText)this.v).setText(Text);

        return ((EditText)this.v).getText().toString();

    }

    public View $Image(Bitmap bmp){

        ((ImageView)this.v).setImageBitmap(bmp);

        return this.v;

    }

    public void $Click(final Event e){

        core.$Click(this.v,e);

    }

    public void $Keypress(final Event e){

        core.$KeyPress(this.v,e);

    }

    public void $Focus(final Event e){

        core.$Focus(this.v,e);

    }

    public void $Touch(final Event e){

        core.$Touch(this.v,e);

    }



}

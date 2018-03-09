package com.core.lib;

import android.view.View;

/**
 * Created by l on 23/01/2018.
 */

public class CoreEvent {

    private View v;
    private Droid core;


    public CoreEvent(Droid core,View v){

        this.v =v;
        this.core = core;
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



package com.core.lib;

/**
 * Created by l on 21/01/2018.
 */

public class CoreSession {

    final private Droid core;

    public CoreSession(Droid core){

        this.core = core;

    }

    public int $IsActive = 1;

    public int $Check(String KEY){

        return core.$session(KEY);

    }

    public void $Set(String KEY,String VALUE){


        core.$SetSession(KEY,VALUE);

    }

    public String $Get(String KEY){


       return core.$GetSession(KEY);

    }

    public void $Remove(String KEY){


        core.$RemoveSession(KEY);

    }


}

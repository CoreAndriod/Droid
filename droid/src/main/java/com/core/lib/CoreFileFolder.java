package com.core.lib;

/**
 * Created by l on 14/01/2018.
 */

public class CoreFileFolder  {



    private String folder;
    private Droid core;
    private String Storage;

    public CoreFileFolder(String STORAGE,String FOLDER,Droid CORE){


        this.folder = FOLDER;
        this.core = CORE;
        this.Storage = STORAGE;


    }


    public String $Path(){

        return this.Storage+this.folder;

    }

    public byte[] $Read(String FILENAME){

        return core.$ReadFile(Storage+folder,FILENAME);

    }


    public boolean $Write(byte[] FILE,String FILENAME){

        return core.$WriteFile(FILE,Storage+folder,FILENAME);

    }

    public String $ReadText(String FILENAME,Boolean APPEND){

        return core.$ReadTextFile(Storage,folder+FILENAME,APPEND);
    }

    public Boolean $WriteText(String FILENAME,String CONTENT,Boolean APPEND){

        return core.$WriteTextFile(Storage,folder+FILENAME,CONTENT,APPEND);

    }

    public boolean $Copy(String FILENAME, CoreFileFolder DST,String dFileName){

      return  core.$CopyFile(Storage,folder+FILENAME,DST.$Path()+dFileName);

    }

    public boolean $CopyDir(CoreFileFolder DST){

         return  core.$CopyDirectory(this.$Path(), DST.$Path());

    }

    public boolean $Delete(String FILENAME){

       return core.$DeleteFile(Storage,folder+FILENAME);

    }

    public boolean $DeleteDir(){

        return core.$DeleteDirectory(Storage+this.folder);

    }

    public boolean $CleanDir(){

        return core.$CleanDirectory(Storage+this.folder);

    }

}
